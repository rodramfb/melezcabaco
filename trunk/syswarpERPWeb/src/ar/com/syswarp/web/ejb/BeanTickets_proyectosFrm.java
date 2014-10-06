/* 
   javabean para la entidad (Formulario): tickets_proyectos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Feb 25 11:21:53 ART 2008 
   
   Para manejar la pagina: tickets_proyectosFrm.jsp
      
*/ 
package ar.com.syswarp.web.ejb;
import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanTickets_proyectosFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanTickets_proyectosFrm.class);        
 private String validar = "";
 private BigDecimal idproyecto= BigDecimal.valueOf(-1);
 private String nombre ="";
 private String description="";
 private BigDecimal idestado= BigDecimal.valueOf(0);
 private String estado ="";
 private String activo ="";
 private BigDecimal idestadovista= BigDecimal.valueOf(0);
 private String desc_estadovista ="";
 private BigDecimal idempresa;
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanTickets_proyectosFrm() { super();}
 public void setSessionContext(SessionContext newContext)
         throws EJBException {
 context = newContext;
 }
 
 public void ejbRemove() throws EJBException, RemoteException {
 // TODO Auto-generated method stub
 }
 
 public void ejbActivate() throws EJBException, RemoteException {
// TODO Auto-generated method stub
 }
 
 public void ejbPassivate() throws EJBException, RemoteException {
// TODO Auto-generated method stub
 }
 
 public void ejecutarSentenciaDML() {
  try {
   General tickets_proyectos = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = tickets_proyectos.tickets_proyectosCreate( this.nombre,  this.description,  this.idestado,  this.activo,  this.idestadovista,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = tickets_proyectos.tickets_proyectosUpdate( this.idproyecto,  this.nombre,  this.description,  this.idestado,  this.activo,  this.idestadovista,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = tickets_proyectos.tickets_proyectosDelete(this.idproyecto,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosTickets_proyectos() {
   try {
	 General tickets_proyectos = Common.getGeneral();
     List listTickets_proyectos = tickets_proyectos.getTickets_proyectosPK(this.idproyecto, this.idempresa );
     Iterator iterTickets_proyectos = listTickets_proyectos.iterator();
     if (iterTickets_proyectos.hasNext()) {
        String[] uCampos = (String[]) iterTickets_proyectos.next();
        //TODO: Constructores para cada tipo de datos
        this.idproyecto= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.nombre= uCampos[1];
        this.description= uCampos[2];
        this.idestado= BigDecimal.valueOf(Long.parseLong(uCampos[3]));
        this.estado =uCampos[4];
        this.activo= uCampos[5];
        this.idestadovista= BigDecimal.valueOf(Long.parseLong(uCampos[6]));
        this.desc_estadovista= uCampos[7];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[8]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosTickets_proyectos()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("tickets_proyectosAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(nombre == null ){
       this.mensaje = "No se puede dejar vacio el campo nombre ";
       return false;
    }
    if(description == null ){
       this.mensaje = "No se puede dejar vacio el campo description ";
       return false;
    }
    if(idestado == null ){
       this.mensaje = "No se puede dejar vacio el campo idestado ";
       return false;
    }
    if(activo == null ){
       this.mensaje = "No se puede dejar vacio el campo activo ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(nombre.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Nombre  ";
       return false;
    }
    if(description.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Descripcion  ";
       return false;
    }
    if(activo.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Activo  ";
       return false;
    }
    
    
    if(estado.trim().length() == 0  ){
        this.mensaje = "No se puede dejar vacio el campo Estado  ";
        return false;
     }
    if(desc_estadovista.trim().length() == 0  ){
        this.mensaje = "No se puede dejar vacio el campo Estado Vista  ";
        return false;
     }   
    
    
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosTickets_proyectos();
   }
  }
 } catch (Exception e) {
      log.error(" ejecutarValidacion(): " + e);
 }
 return true;
}

 public String getValidar() {return validar;}
 public void setValidar(String validar) { this.validar = validar; }
 public String getMensaje() { return mensaje; }
 public void setMensaje(String mensaje) { this.mensaje = mensaje; }
 public String getAccion() { return accion; }
 public void setAccion(String accion) { this.accion = accion; }
 public String getVolver() { return volver; }
 public void setVolver(String volver) { this.volver = volver; }
 public HttpServletRequest getRequest() { return request; }
 public void setRequest(HttpServletRequest request) {this.request = request;}
 public HttpServletResponse getResponse() {return response;}
 public void setResponse(HttpServletResponse response) { this.response = response; }
 // metodos para cada atributo de la entidad
 public BigDecimal getIdproyecto(){ return idproyecto; }
 public void setIdproyecto(BigDecimal idproyecto){ this.idproyecto=idproyecto; }
 public String getNombre(){ return nombre; }
 public void setNombre(String nombre){ this.nombre=nombre; }
 public String getDescription(){ return description; }
 public void setDescription(String description){ this.description=description; }
 public BigDecimal getIdestado(){ return idestado; }
 public void setIdestado(BigDecimal idestado){ this.idestado=idestado; }
 public String getActivo(){ return activo; }
 public void setActivo(String activo){ this.activo=activo; }
 public BigDecimal getIdestadovista(){ return idestadovista; }
 public void setIdestadovista(BigDecimal idestadovista){ this.idestadovista=idestadovista; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }

public String getDesc_estadovista() {
	return desc_estadovista;
}
public void setDesc_estadovista(String desc_estadovista) {
	this.desc_estadovista = desc_estadovista;
}
public String getEstado() {
	return estado;
}
public void setEstado(String estado) {
	this.estado = estado;
}
}






