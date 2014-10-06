/* 
   javabean para la entidad (Formulario): wkfprocesoseventos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Jul 24 08:30:58 ACT 2009 
   
   Para manejar la pagina: wkfprocesoseventosFrm.jsp
      
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

public class BeanWkfprocesoseventosFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanWkfprocesoseventosFrm.class);        
 private String validar = "";
 private BigDecimal idprocesoevento= BigDecimal.valueOf(-1);
 private BigDecimal idproceso= BigDecimal.valueOf(0);
 private String proceso = "";
 private BigDecimal idevento= BigDecimal.valueOf(0);
 private String evento ="";
 private String descripcion ="";
 private BigDecimal idprocesonegocionext= BigDecimal.valueOf(0);
 private String procesonegocionext= "";
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanWkfprocesoseventosFrm() { super();}
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
   General wkfprocesoseventos = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = wkfprocesoseventos.wkfprocesoseventosCreate( this.idproceso,  this.idevento,  this.descripcion,  this.idprocesonegocionext,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = wkfprocesoseventos.wkfprocesoseventosUpdate( this.idprocesoevento,  this.idproceso,  this.idevento,  this.descripcion,  this.idprocesonegocionext,  this.usuarioact,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = wkfprocesoseventos.wkfprocesoseventosDelete(this.idprocesoevento,  this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosWkfprocesoseventos() {
   try {
	 General wkfprocesoseventos = Common.getGeneral();
     List listWkfprocesoseventos = wkfprocesoseventos.getWkfprocesoseventosPK(this.idprocesoevento, this.idempresa );
     Iterator iterWkfprocesoseventos = listWkfprocesoseventos.iterator();
     if (iterWkfprocesoseventos.hasNext()) {
        String[] uCampos = (String[]) iterWkfprocesoseventos.next();
        //TODO: Constructores para cada tipo de datos
        this.idprocesoevento= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.idproceso= BigDecimal.valueOf(Long.parseLong(uCampos[1]));
        this.proceso= uCampos[2];
        this.idevento= BigDecimal.valueOf(Long.parseLong(uCampos[3]));
        this.evento= uCampos[4];
        this.descripcion= uCampos[5];
        this.idprocesonegocionext= new BigDecimal(uCampos[6] == null ? "-1" : uCampos[6]);
        this.procesonegocionext = uCampos[7];
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosWkfprocesoseventos()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("wkfprocesoseventosAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(idproceso == null ){
       this.mensaje = "No se puede dejar vacio el campo idproceso ";
       return false;
    }
    if(idevento == null ){
       this.mensaje = "No se puede dejar vacio el campo idevento ";
       return false;
    }
    if(descripcion == null ){
       this.mensaje = "No se puede dejar vacio el campo descripcion ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(descripcion.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo descripcion  ";
       return false;
    }
    
    if(proceso.trim().length() == 0  ){
        this.mensaje = "No se puede dejar vacio el campo Proceso  ";
        return false;
     }
    
    if(evento.trim().length() == 0  ){
        this.mensaje = "No se puede dejar vacio el campo Evento  ";
        return false;
     }
    
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosWkfprocesoseventos();
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
 public BigDecimal getIdprocesoevento(){ return idprocesoevento; }
 public void setIdprocesoevento(BigDecimal idprocesoevento){ this.idprocesoevento=idprocesoevento; }
 public BigDecimal getIdproceso(){ return idproceso; }
 public void setIdproceso(BigDecimal idproceso){ this.idproceso=idproceso; }
 public BigDecimal getIdevento(){ return idevento; }
 public void setIdevento(BigDecimal idevento){ this.idevento=idevento; }
 public String getDescripcion(){ return descripcion; }
 public void setDescripcion(String descripcion){ this.descripcion=descripcion; }
 public BigDecimal getIdprocesonegocionext(){ return idprocesonegocionext; }
 public void setIdprocesonegocionext(BigDecimal idprocesonegocionext){ this.idprocesonegocionext=idprocesonegocionext; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public String getProceso() {
	return proceso;
}
public void setProceso(String proceso) {
	this.proceso = proceso;
}
public String getEvento() {
	return evento;
}
public void setEvento(String evento) {
	this.evento = evento;
}
public String getProcesonegocionext() {
	return procesonegocionext;
}
public void setProcesonegocionext(String procesonegocionext) {
	this.procesonegocionext = procesonegocionext;
}
public BigDecimal getIdempresa() {
	return idempresa;
}
public void setIdempresa(BigDecimal idempresa) {
	this.idempresa = idempresa;
}
}






