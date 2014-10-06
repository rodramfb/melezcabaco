/* 
   javabean para la entidad (Formulario): wkftipotransaccion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 22 08:59:34 ACT 2009 
   
   Para manejar la pagina: wkftipotransaccionFrm.jsp
      
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

public class BeanWkftipotransaccionFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanWkftipotransaccionFrm.class);        
 private String validar = "";
 private BigDecimal idtipotransaccion= BigDecimal.valueOf(-1);
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String tipotransaccion ="";
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanWkftipotransaccionFrm() { super();}
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
   General wkftipotransaccion = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = wkftipotransaccion.wkftipotransaccionCreate( this.tipotransaccion,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = wkftipotransaccion.wkftipotransaccionUpdate( this.idtipotransaccion,  this.tipotransaccion,  this.usuarioact,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = wkftipotransaccion.wkftipotransaccionDelete(this.idtipotransaccion,  this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosWkftipotransaccion() {
   try {
	 General wkftipotransaccion = Common.getGeneral();
     List listWkftipotransaccion = wkftipotransaccion.getWkftipotransaccionPK(this.idtipotransaccion, this.idempresa );
     Iterator iterWkftipotransaccion = listWkftipotransaccion.iterator();
     if (iterWkftipotransaccion.hasNext()) {
        String[] uCampos = (String[]) iterWkftipotransaccion.next();
        //TODO: Constructores para cada tipo de datos
        this.idtipotransaccion= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.tipotransaccion= uCampos[1];
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosWkftipotransaccion()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("wkftipotransaccionAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
       // 2. len 0 para campos nulos
    	   
			if (tipotransaccion.trim().length() == 0) {
				this.mensaje = "No se puede dejar vacio el campo Tipo Transaccion  ";
				return false;
			} 	   
    	   
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosWkftipotransaccion();
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
 public BigDecimal getIdtipotransaccion(){ return idtipotransaccion; }
 public void setIdtipotransaccion(BigDecimal idtipotransaccion){ this.idtipotransaccion=idtipotransaccion; }
 public String getTipotransaccion(){ return tipotransaccion; }
 public void setTipotransaccion(String tipotransaccion){ this.tipotransaccion=tipotransaccion; }
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






