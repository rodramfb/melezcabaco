/* 
   javabean para la entidad (Formulario): rrhhtipodocumento
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Apr 26 15:01:24 GMT-03:00 2007 
   
   Para manejar la pagina: rrhhtipodocumentoFrm.jsp
      
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

public class BeanRrhhtipodocumentoFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanRrhhtipodocumentoFrm.class);        
 private String validar = "";
 private BigDecimal idtipodocumento= BigDecimal.valueOf(-1);
 private BigDecimal idempresa;
 private String tipodocumento="" ;
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanRrhhtipodocumentoFrm() { super();}
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
   RRHH rrhhtipodocumento = Common.getRrhh();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = rrhhtipodocumento.rrhhtipodocumentoCreate( this.tipodocumento,  this.usuarioalt,this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = rrhhtipodocumento.rrhhtipodocumentoUpdate( this.idtipodocumento,  this.tipodocumento,  this.usuarioact,this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = rrhhtipodocumento.rrhhtipodocumentoDelete(this.idtipodocumento,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosRrhhtipodocumento() {
   try {
	 RRHH rrhhtipodocumento = Common.getRrhh();
     List listRrhhtipodocumento = rrhhtipodocumento.getRrhhtipodocumentoPK(this.idtipodocumento,this.idempresa);
     Iterator iterRrhhtipodocumento = listRrhhtipodocumento.iterator();
     if (iterRrhhtipodocumento.hasNext()) {
        String[] uCampos = (String[]) iterRrhhtipodocumento.next();
        //TODO: Constructores para cada tipo de datos
        this.idtipodocumento = BigDecimal
		.valueOf(Long.parseLong(uCampos[0]));
        this.tipodocumento= uCampos[1];
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosRrhhtipodocumento()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("rrhhtipodocumentoAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
       // 2. len 0 para campos nulos
    	   
			if (tipodocumento.trim().length() == 0) {
				this.mensaje = "No se puede dejar vacio el campo Tipo Documento  ";
				return false;
			}	   
    	   
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosRrhhtipodocumento();
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
 public BigDecimal getIdtipodocumento(){ return idtipodocumento; }
 public void setIdtipodocumento(BigDecimal idtipodocumento){ this.idtipodocumento=idtipodocumento; }
 public String getTipodocumento(){ return tipodocumento; }
 public void setTipodocumento(String tipodocumento){ this.tipodocumento=tipodocumento; }
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






