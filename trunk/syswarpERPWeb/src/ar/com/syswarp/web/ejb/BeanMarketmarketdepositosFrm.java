/* 
   javabean para la entidad (Formulario): Marketmarketdepositos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Mar 11 14:30:53 ART 2008 
   
   Para manejar la pagina: MarketmarketdepositosFrm.jsp
      
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

public class BeanMarketmarketdepositosFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanMarketmarketdepositosFrm.class);        
 private String validar = "";
 private BigDecimal idmarketdeposito= BigDecimal.valueOf(-1);
 private BigDecimal codigo_dt= BigDecimal.valueOf(0);
 private String d_codigo_dt ="";
 private BigDecimal idempresa;
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanMarketmarketdepositosFrm() { super();}
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
   Stock Marketmarketdepositos = Common.getStock();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = Marketmarketdepositos.MarketmarketdepositosCreate( this.codigo_dt,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = Marketmarketdepositos.MarketmarketdepositosUpdate( this.idmarketdeposito,  this.codigo_dt,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = Marketmarketdepositos.MarketmarketdepositosDelete(this.idmarketdeposito,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosMarketmarketdepositos() {
   try {
	 Stock Marketmarketdepositos = Common.getStock();
     List listMarketmarketdepositos = Marketmarketdepositos.getMarketmarketdepositosPK(this.idmarketdeposito, this.idempresa );
     Iterator iterMarketmarketdepositos = listMarketmarketdepositos.iterator();
     if (iterMarketmarketdepositos.hasNext()) {
        String[] uCampos = (String[]) iterMarketmarketdepositos.next();
        //TODO: Constructores para cada tipo de datos
        this.idmarketdeposito= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.codigo_dt= BigDecimal.valueOf(Long.parseLong(uCampos[1]));
        this.d_codigo_dt = uCampos[2];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[3]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosMarketmarketdepositos()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("MarketmarketdepositosAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
    	   if(d_codigo_dt.trim().length() == 0  ){
    	       this.mensaje = "No se puede dejar vacio el campo Deposito  ";
    	       return false;
    	    }
       // 1. nulidad de campos
       // 2. len 0 para campos nulos
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosMarketmarketdepositos();
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
 public BigDecimal getIdmarketdeposito(){ return idmarketdeposito; }
 public void setIdmarketdeposito(BigDecimal idmarketdeposito){ this.idmarketdeposito=idmarketdeposito; }
 public BigDecimal getCodigo_dt(){ return codigo_dt; }
 public void setCodigo_dt(BigDecimal codigo_dt){ this.codigo_dt=codigo_dt; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public String getD_codigo_dt() {
	return d_codigo_dt;
}
public void setD_codigo_dt(String d_codigo_dt) {
	this.d_codigo_dt = d_codigo_dt;
}
}






