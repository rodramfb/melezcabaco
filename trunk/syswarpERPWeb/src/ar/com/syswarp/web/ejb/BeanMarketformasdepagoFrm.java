/* 
   javabean para la entidad (Formulario): marketformasdepago
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Mar 11 13:29:00 ART 2008 
   
   Para manejar la pagina: marketformasdepagoFrm.jsp
      
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

public class BeanMarketformasdepagoFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanMarketformasdepagoFrm.class);        
 private String validar = "";
 private BigDecimal idformapago= BigDecimal.valueOf(-1);
 private String formapago ="";
 private String leyenda ="";
 private String enviodatos ="";
 private BigDecimal idempresa;
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanMarketformasdepagoFrm() { super();}
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
   Stock marketformasdepago = Common.getStock();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = marketformasdepago.marketformasdepagoCreate( this.formapago,  this.leyenda,  this.enviodatos,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = marketformasdepago.marketformasdepagoUpdate( this.idformapago,  this.formapago,  this.leyenda,  this.enviodatos,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = marketformasdepago.marketformasdepagoDelete(this.idformapago,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosMarketformasdepago() {
   try {
	   Stock marketformasdepago = Common.getStock();
     List listMarketformasdepago = marketformasdepago.getMarketformasdepagoPK(this.idformapago, this.idempresa );
     Iterator iterMarketformasdepago = listMarketformasdepago.iterator();
     if (iterMarketformasdepago.hasNext()) {
        String[] uCampos = (String[]) iterMarketformasdepago.next();
        //TODO: Constructores para cada tipo de datos
        this.idformapago= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.formapago= uCampos[1];
        this.leyenda= uCampos[2];
        this.enviodatos= uCampos[3];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[4]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosMarketformasdepago()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("marketformasdepagoAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(formapago == null ){
       this.mensaje = "No se puede dejar vacio el campo formapago ";
       return false;
    }
    if(leyenda == null ){
       this.mensaje = "No se puede dejar vacio el campo leyenda ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(formapago.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Forma de Pago  ";
       return false;
    }
    if(leyenda.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Leyenda  ";
       return false;
    }
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosMarketformasdepago();
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
 public BigDecimal getIdformapago(){ return idformapago; }
 public void setIdformapago(BigDecimal idformapago){ this.idformapago=idformapago; }
 public String getFormapago(){ return formapago; }
 public void setFormapago(String formapago){ this.formapago=formapago; }
 public String getLeyenda(){ return leyenda; }
 public void setLeyenda(String leyenda){ this.leyenda=leyenda; }
 public String getEnviodatos(){ return enviodatos; }
 public void setEnviodatos(String enviodatos){ this.enviodatos=enviodatos; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
}






