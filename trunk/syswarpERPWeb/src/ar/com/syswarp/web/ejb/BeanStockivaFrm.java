/* 
   javabean para la entidad (Formulario): stockiva
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Aug 10 13:02:55 ACT 2009 
   
   Para manejar la pagina: stockivaFrm.jsp
      
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

public class BeanStockivaFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanStockivaFrm.class);        
 private String validar = "";
 private BigDecimal idstockiva= new BigDecimal(-1);
 private String descripcion ="";
 private Double porcentaje= Double.valueOf("0");
 private BigDecimal idempresa= new BigDecimal(0);
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanStockivaFrm() { super();}
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
   Stock stockiva = Common.getStock();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = stockiva.stockivaCreate( this.descripcion,  this.porcentaje,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = stockiva.stockivaUpdate( this.idstockiva,  this.descripcion,  this.porcentaje,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = stockiva.stockivaDelete(this.idstockiva,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosStockiva() {
   try {
	   Stock stockiva = Common.getStock();
     List listStockiva = stockiva.getStockivaPK(this.idstockiva, this.idempresa );
     Iterator iterStockiva = listStockiva.iterator();
     if (iterStockiva.hasNext()) {
        String[] uCampos = (String[]) iterStockiva.next();
        //TODO: Constructores para cada tipo de datos
        this.idstockiva= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.descripcion= uCampos[1];
        this.porcentaje= Double.valueOf(uCampos[2]);
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[3]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosStockiva()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("stockivaAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(descripcion == null ){
       this.mensaje = "No se puede dejar vacio el campo descripcion ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(descripcion.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Descripcion  ";
       return false;
    }
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosStockiva();
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
 public BigDecimal getIdstockiva(){ return idstockiva; }
 public void setIdstockiva(BigDecimal idstockiva){ this.idstockiva=idstockiva; }
 public String getDescripcion(){ return descripcion; }
 public void setDescripcion(String descripcion){ this.descripcion=descripcion; }
 public Double getPorcentaje(){ return porcentaje; }
 public void setPorcentaje(Double porcentaje){ this.porcentaje=porcentaje; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
}






