/* 
   javabean para la entidad (Formulario): rrhhzonatrabajo
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Apr 21 15:44:14 ACT 2009 
   
   Para manejar la pagina: rrhhzonatrabajoFrm.jsp
      
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

public class BeanRrhhzonatrabajoFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanRrhhzonatrabajoFrm.class);        
 private String validar = "";
 private BigDecimal idzonatrabajo= BigDecimal.valueOf(-1);
 private String zonatrabajo = "";
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanRrhhzonatrabajoFrm() { super();}
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
   RRHH rrhhzonatrabajo = Common.getRrhh();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = rrhhzonatrabajo.rrhhzonatrabajoCreate( this.zonatrabajo,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = rrhhzonatrabajo.rrhhzonatrabajoUpdate( this.idzonatrabajo,  this.zonatrabajo,  this.usuarioact,this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = rrhhzonatrabajo.rrhhzonatrabajoDelete(this.idzonatrabajo,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosRrhhzonatrabajo() {
   try {
     RRHH rrhhzonatrabajo = Common.getRrhh();
     List listRrhhzonatrabajo = rrhhzonatrabajo.getRrhhzonatrabajoPK(this.idzonatrabajo, this.idempresa );
     Iterator iterRrhhzonatrabajo = listRrhhzonatrabajo.iterator();
     if (iterRrhhzonatrabajo.hasNext()) {
        String[] uCampos = (String[]) iterRrhhzonatrabajo.next();
        //TODO: Constructores para cada tipo de datos
        this.idzonatrabajo= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.zonatrabajo= uCampos[1];
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosRrhhzonatrabajo()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("rrhhzonatrabajoAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
       // 2. len 0 para campos nulos
    	   
    if(zonatrabajo.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Zona Trabajo  ";
       return false;
     }	   
    	   
    	   
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosRrhhzonatrabajo();
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
 public BigDecimal getIdzonatrabajo(){ return idzonatrabajo; }
 public void setIdzonatrabajo(BigDecimal idzonatrabajo){ this.idzonatrabajo=idzonatrabajo; }
 public String getZonatrabajo(){ return zonatrabajo; }
 public void setZonatrabajo(String zonatrabajo){ this.zonatrabajo=zonatrabajo; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public BigDecimal getIdempresa() {
	return idempresa;
}
public void setIdempresa(BigDecimal idempresa) {
	this.idempresa = idempresa;
}
}






