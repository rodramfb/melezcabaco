/* 
   javabean para la entidad (Formulario): pedidoscanones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jul 29 16:09:34 ART 2008 
   
   Para manejar la pagina: pedidoscanonesFrm.jsp
      
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

public class BeanPedidoscanonesFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanPedidoscanonesFrm.class);        
 private String validar = "";
 private BigDecimal idcanon = BigDecimal.valueOf(-1);
 private String canon ="";
 private Double por_desc= Double.valueOf("0");
 private String formula="";
 private BigDecimal precedencia= BigDecimal.valueOf(0);
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanPedidoscanonesFrm() { super();}
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
   General pedidoscanones = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = pedidoscanones.pedidoscanonesCreate( this.canon,  this.por_desc,  this.formula,  this.precedencia,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = pedidoscanones.pedidoscanonesUpdate( this.idcanon,  this.canon,  this.por_desc,  this.formula,  this.precedencia,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = pedidoscanones.pedidoscanonesDelete(this.idcanon,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosPedidoscanones() {
   try {
	 General pedidoscanones = Common.getGeneral();
     List listPedidoscanones = pedidoscanones.getPedidoscanonesPK(this.idcanon, this.idempresa );
     Iterator iterPedidoscanones = listPedidoscanones.iterator();
     if (iterPedidoscanones.hasNext()) {
        String[] uCampos = (String[]) iterPedidoscanones.next();
        //TODO: Constructores para cada tipo de datos
        this.idcanon= BigDecimal
		.valueOf(Long.parseLong(uCampos[0]));
        this.canon= uCampos[1];
        this.por_desc= Double.valueOf(uCampos[2]);
        this.formula= uCampos[3];
        this.precedencia= BigDecimal.valueOf(Long.parseLong(uCampos[4]));
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[5]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosPedidoscanones()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("pedidoscanonesAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(canon == null ){
       this.mensaje = "No se puede dejar vacio el campo canon ";
       return false;
    }
    if(por_desc == null ){
       this.mensaje = "No se puede dejar vacio el campo por_desc ";
       return false;
    }
    if(formula == null ){
       this.mensaje = "No se puede dejar vacio el campo formula ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(canon.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Canon  ";
       return false;
    }
    
    if(formula.trim().length() == 0  ){
        this.mensaje = "No se puede dejar vacio el campo Formula  ";
        return false;
     }
    
    
    
    
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosPedidoscanones();
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
 public BigDecimal getIdcanon(){ return idcanon; }
 public void setIdcanon(BigDecimal idcanon){ this.idcanon=idcanon; }
 public String getCanon(){ return canon; }
 public void setCanon(String canon){ this.canon=canon; }
 public Double getPor_desc(){ return por_desc; }
 public void setPor_desc(Double por_desc){ this.por_desc=por_desc; }
 public String getFormula(){ return formula; }
 public void setFormula(String formula){ this.formula=formula; }
 public BigDecimal getPrecedencia(){ return precedencia; }
 public void setPrecedencia(BigDecimal precedencia){ this.precedencia=precedencia; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
}






