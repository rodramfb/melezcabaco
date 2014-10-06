/* 
   javabean para la entidad (Formulario): origenprospecto
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Oct 27 09:10:06 ACT 2008 
   
   Para manejar la pagina: origenprospectoFrm.jsp
      
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

public class BeanOrigenprospectoFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanOrigenprospectoFrm.class);        
 private String validar = "";
 private BigDecimal idorigen= BigDecimal.valueOf(-1);
 private String origen = "";
 private BigDecimal idempresa;
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanOrigenprospectoFrm() { super();}
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
   General origenprospecto = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = origenprospecto.origenprospectoCreate( this.origen,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = origenprospecto.origenprospectoUpdate( this.idorigen,  this.origen,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = origenprospecto.origenprospectoDelete(this.idorigen,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosOrigenprospecto() {
   try {
	 General origenprospecto = Common.getGeneral();
     List listOrigenprospecto = origenprospecto.getOrigenprospectoPK(this.idorigen, this.idempresa );
     Iterator iterOrigenprospecto = listOrigenprospecto.iterator();
     if (iterOrigenprospecto.hasNext()) {
        String[] uCampos = (String[]) iterOrigenprospecto.next();
        //TODO: Constructores para cada tipo de datos
        this.idorigen= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.origen= uCampos[1];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[2]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosOrigenprospecto()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("origenprospectoAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
       // 2. len 0 para campos nulos
	   if (origen.trim().length() == 0) {
		   this.mensaje = "No se puede dejar vacio el campo Origen";
		   return false;
	   }	   
 	   	   
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosOrigenprospecto();
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
 public BigDecimal getIdorigen(){ return idorigen; }
 public void setIdorigen(BigDecimal idorigen){ this.idorigen=idorigen; }
 public String getOrigen(){ return origen; }
 public void setOrigen(String origen){ this.origen=origen; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
}






