/* 
   javabean para la entidad (Formulario): wkftransacciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 23 08:41:21 ACT 2009 
   
   Para manejar la pagina: wkftransaccionesFrm.jsp
      
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

public class BeanWkftransaccionesFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanWkftransaccionesFrm.class);        
 private String validar = "";
 private BigDecimal idtransaccion= BigDecimal.valueOf(-1);
 private String transaccion ="";
 private BigDecimal idtipotransaccion= BigDecimal.valueOf(0);
 private String tipotransaccion ="";
 private BigDecimal idproximatransaccion= BigDecimal.valueOf(0);
 private String proximatransaccion ="";
 private String descripcion ="";
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanWkftransaccionesFrm() { super();}
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
   General wkftransacciones = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = wkftransacciones.wkftransaccionesCreate( this.transaccion,  this.idtipotransaccion,  this.idproximatransaccion,  this.descripcion,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = wkftransacciones.wkftransaccionesUpdate( this.idtransaccion,  this.transaccion,  this.idtipotransaccion,  this.idproximatransaccion,  this.descripcion,  this.usuarioact,this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = wkftransacciones.wkftransaccionesDelete(this.idtransaccion,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosWkftransacciones() {
   try {
	 General wkftransacciones = Common.getGeneral();
     List listWkftransacciones = wkftransacciones.getWkftransaccionesPK(this.idtransaccion, this.idempresa );
     Iterator iterWkftransacciones = listWkftransacciones.iterator();
     if (iterWkftransacciones.hasNext()) {
        String[] uCampos = (String[]) iterWkftransacciones.next();
        //TODO: Constructores para cada tipo de datos
        this.idtransaccion= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.transaccion= uCampos[1];
        this.idtipotransaccion= BigDecimal.valueOf(Long.parseLong(uCampos[2]));
        this.tipotransaccion= uCampos[3];
        //this.idproximatransaccion= BigDecimal.valueOf(Long.parseLong(uCampos[4]));
        this.idproximatransaccion = new BigDecimal(uCampos[4] == null ? "-1" : uCampos[4]);
        this.proximatransaccion = uCampos[5];
        this.descripcion= uCampos[6];
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosWkftransacciones()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("wkftransaccionesAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(transaccion == null ){
       this.mensaje = "No se puede dejar vacio el campo transaccion ";
       return false;
    }
    if(idtipotransaccion == null ){
       this.mensaje = "No se puede dejar vacio el campo idtipotransaccion ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(transaccion.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Transaccion  ";
       return false;
    }
    
    if(tipotransaccion.trim().length() == 0  ){
        this.mensaje = "No se puede dejar vacio el campo Tipo Transaccion  ";
        return false;
     }
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosWkftransacciones();
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
 public BigDecimal getIdtransaccion(){ return idtransaccion; }
 public void setIdtransaccion(BigDecimal idtransaccion){ this.idtransaccion=idtransaccion; }
 public String getTransaccion(){ return transaccion; }
 public void setTransaccion(String transaccion){ this.transaccion=transaccion; }
 public BigDecimal getIdtipotransaccion(){ return idtipotransaccion; }
 public void setIdtipotransaccion(BigDecimal idtipotransaccion){ this.idtipotransaccion=idtipotransaccion; }
 public BigDecimal getIdproximatransaccion(){ return idproximatransaccion; }
 public void setIdproximatransaccion(BigDecimal idproximatransaccion){ this.idproximatransaccion=idproximatransaccion; }
 public String getDescripcion(){ return descripcion; }
 public void setDescripcion(String descripcion){ this.descripcion=descripcion; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public String getTipotransaccion() {
	return tipotransaccion;
}
public void setTipotransaccion(String tipotransaccion) {
	this.tipotransaccion = tipotransaccion;
}
public String getProximatransaccion() {
	return proximatransaccion;
}
public void setProximatransaccion(String proximatransaccion) {
	this.proximatransaccion = proximatransaccion;
}
public BigDecimal getIdempresa() {
	return idempresa;
}
public void setIdempresa(BigDecimal idempresa) {
	this.idempresa = idempresa;
}
}






