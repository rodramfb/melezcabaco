/* 
   javabean para la entidad (Formulario): globaltiposdocumentos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Mar 28 09:45:19 ART 2008 
   
   Para manejar la pagina: globaltiposdocumentosFrm.jsp
      
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

public class BeanGlobaltiposdocumentosFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanGlobaltiposdocumentosFrm.class);        
 private String validar = "";
 private BigDecimal idtipodocumento= BigDecimal.valueOf(-1);
 private String tipodocumento ="";
 private BigDecimal idempresa;
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanGlobaltiposdocumentosFrm() { super();}
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
   General globaltiposdocumentos = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = globaltiposdocumentos.globaltiposdocumentosCreate( this.tipodocumento,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = globaltiposdocumentos.globaltiposdocumentosUpdate( this.idtipodocumento,  this.tipodocumento,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = globaltiposdocumentos.globaltiposdocumentosDelete(this.idtipodocumento,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosGlobaltiposdocumentos() {
   try {
	 General globaltiposdocumentos = Common.getGeneral();
     List listGlobaltiposdocumentos = globaltiposdocumentos.getGlobaltiposdocumentosPK(this.idtipodocumento, this.idempresa );
     Iterator iterGlobaltiposdocumentos = listGlobaltiposdocumentos.iterator();
     if (iterGlobaltiposdocumentos.hasNext()) {
        String[] uCampos = (String[]) iterGlobaltiposdocumentos.next();
        //TODO: Constructores para cada tipo de datos
        this.idtipodocumento= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.tipodocumento= uCampos[1];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[2]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosGlobaltiposdocumentos()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("globaltiposdocumentosAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
       // 2. len 0 para campos nulos
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosGlobaltiposdocumentos();
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
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
}






