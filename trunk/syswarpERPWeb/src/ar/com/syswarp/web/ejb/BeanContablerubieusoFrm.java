/* 
   javabean para la entidad (Formulario): contablerubieuso
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 04 08:31:29 ACT 2009 
   
   Para manejar la pagina: contablerubieusoFrm.jsp
      
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

public class BeanContablerubieusoFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanContablerubieusoFrm.class);        
 private String validar = "";
 private BigDecimal idrubro= BigDecimal.valueOf(-1);
 private String rubro ="";
 private BigDecimal anios= BigDecimal.valueOf(0);
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanContablerubieusoFrm() { super();}
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
   Contable contablerubieuso = Common.getContable();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = contablerubieuso.contablerubieusoCreate( this.rubro,  this.anios,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = contablerubieuso.contablerubieusoUpdate( this.idrubro,  this.rubro,  this.anios,  this.usuarioact,this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = contablerubieuso.contablerubieusoDelete(this.idrubro,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosContablerubieuso() {
   try {
     Contable contablerubieuso = Common.getContable();
     List listContablerubieuso = contablerubieuso.getContablerubieusoPK(this.idrubro, this.idempresa );
     Iterator iterContablerubieuso = listContablerubieuso.iterator();
     if (iterContablerubieuso.hasNext()) {
        String[] uCampos = (String[]) iterContablerubieuso.next();
        //TODO: Constructores para cada tipo de datos
        this.idrubro= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.rubro= uCampos[1];
        this.anios= BigDecimal.valueOf(Long.parseLong(uCampos[2]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosContablerubieuso()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("contablerubieusoAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
       // 2. len 0 para campos nulos
			if (rubro.trim().length() == 0) {
				this.mensaje = "No se puede dejar vacio el campo Rubro  ";
				return false;
			}   
    	   
    	   
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosContablerubieuso();
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
 public BigDecimal getIdrubro(){ return idrubro; }
 public void setIdrubro(BigDecimal idrubro){ this.idrubro=idrubro; }
 public String getRubro(){ return rubro; }
 public void setRubro(String rubro){ this.rubro=rubro; }
 public BigDecimal getAnios(){ return anios; }
 public void setAnios(BigDecimal anios){ this.anios=anios; }
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






