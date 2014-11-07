/* 
   javabean para la entidad (Formulario): suborigenprospecto
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Oct 27 13:04:08 ACT 2008 
   
   Para manejar la pagina: suborigenprospectoFrm.jsp
      
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

public class BeanSuborigenprospectoFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanSuborigenprospectoFrm.class);        
 private String validar = "";
 private BigDecimal idsuborigen= BigDecimal.valueOf(-1);
 private String suborigen="";
 private BigDecimal idorigen= BigDecimal.valueOf(0);
 private String origen="";
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanSuborigenprospectoFrm() { super();}
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
   General suborigenprospecto = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = suborigenprospecto.suborigenprospectoCreate( this.suborigen,  this.idorigen,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = suborigenprospecto.suborigenprospectoUpdate( this.idsuborigen,  this.suborigen,  this.idorigen,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = suborigenprospecto.suborigenprospectoDelete(this.idsuborigen,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosSuborigenprospecto() {
   try {
	 General suborigenprospecto = Common.getGeneral();
     List listSuborigenprospecto = suborigenprospecto.getSuborigenprospectoPK(this.idsuborigen, this.idempresa );
     Iterator iterSuborigenprospecto = listSuborigenprospecto.iterator();
     if (iterSuborigenprospecto.hasNext()) {
        String[] uCampos = (String[]) iterSuborigenprospecto.next();
        //TODO: Constructores para cada tipo de datos
        this.idsuborigen= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.suborigen= uCampos[1];
        this.idorigen= BigDecimal.valueOf(Long.parseLong(uCampos[2]));
        this.origen= uCampos[3];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[4]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosSuborigenprospecto()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("suborigenprospectoAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(suborigen == null ){
       this.mensaje = "No se puede dejar vacio el campo suborigen ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(suborigen.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Suborigen  ";
       return false;
    }
    if(origen.trim().length() == 0  ){
        this.mensaje = "No se puede dejar vacio el campo Origen  ";
        return false;
     }
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosSuborigenprospecto();
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
 public BigDecimal getIdsuborigen(){ return idsuborigen; }
 public void setIdsuborigen(BigDecimal idsuborigen){ this.idsuborigen=idsuborigen; }
 public String getSuborigen(){ return suborigen; }
 public void setSuborigen(String suborigen){ this.suborigen=suborigen; }
 public BigDecimal getIdorigen(){ return idorigen; }
 public void setIdorigen(BigDecimal idorigen){ this.idorigen=idorigen; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public String getOrigen() {
	return origen;
}
public void setOrigen(String origen) {
	this.origen = origen;
}
}






