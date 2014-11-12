/* 
   javabean para la entidad (Formulario): contablebienuso
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 04 09:30:32 ACT 2009 
   
   Para manejar la pagina: contablebienusoFrm.jsp
      
*/ 
package ar.com.syswarp.web.ejb;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

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

public class BeanContablebienusoFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanContablebienusoFrm.class);        
 private String validar = "";
 private BigDecimal idbienuso= BigDecimal.valueOf(-1);
 private String bienuso ="";
 private BigDecimal idrubro= BigDecimal.valueOf(0);
 private String rubro = "";
 private Timestamp fechacompra= new Timestamp(Common.initObjectTime());
 private String fechacompraStr = Common.initObjectTimeStr();
 private Double valorori= Double.valueOf("0");
 private BigDecimal anios= BigDecimal.valueOf(0);
 private BigDecimal meses= BigDecimal.valueOf(0);
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanContablebienusoFrm() { super();}
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
   Contable contablebienuso = Common.getContable();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = contablebienuso.contablebienusoCreate( this.bienuso,  this.idrubro,  this.fechacompra,  this.valorori,this.anios,  this.meses,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = contablebienuso.contablebienusoUpdate( this.idbienuso,  this.bienuso,  this.idrubro,  this.fechacompra,  this.valorori, this.anios,  this.meses,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = contablebienuso.contablebienusoDelete(this.idbienuso,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosContablebienuso() {
   try {
     Contable contablebienuso = Common.getContable();
     List listContablebienuso = contablebienuso.getContablebienusoPK(this.idbienuso, this.idempresa );
     Iterator iterContablebienuso = listContablebienuso.iterator();
     if (iterContablebienuso.hasNext()) {
        String[] uCampos = (String[]) iterContablebienuso.next();
        //TODO: Constructores para cada tipo de datos
        this.idbienuso= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.bienuso= uCampos[1];
        this.idrubro= BigDecimal.valueOf(Long.parseLong(uCampos[2]));
        this.rubro= uCampos[3];
        this.fechacompra= Timestamp.valueOf(uCampos[4]);
        this.fechacompraStr = Common.setObjectToStrOrTime(fechacompra, "JSTsToStr").toString();
        this.valorori= Double.valueOf(uCampos[5]);
        this.anios= BigDecimal.valueOf(Long.parseLong(uCampos[6]));
        this.meses= BigDecimal.valueOf(Long.parseLong(uCampos[7]));
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[8]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosContablebienuso()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("contablebienusoAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(bienuso == null ){
       this.mensaje = "No se puede dejar vacio el campo bienuso ";
       return false;
    }
    if(idrubro == null ){
       this.mensaje = "No se puede dejar vacio el campo idrubro ";
       return false;
    }
    if(fechacompra == null ){
       this.mensaje = "No se puede dejar vacio el campo fechacompra ";
       return false;
    }
    if(valorori == null ){
       this.mensaje = "No se puede dejar vacio el campo valorori ";
       return false;
    }
       // 2. len 0 para campos nulos
	if (bienuso.trim().length() == 0) {
        this.mensaje = "No se puede dejar vacio el campo Bienes de Uso";
		return false;
	} 
    
	if (rubro.trim().length() == 0) {
        this.mensaje = "No se puede dejar vacio el campo Rubro";
		return false;
	} 
    
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosContablebienuso();
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
 public BigDecimal getIdbienuso(){ return idbienuso; }
 public void setIdbienuso(BigDecimal idbienuso){ this.idbienuso=idbienuso; }
 public String getBienuso(){ return bienuso; }
 public void setBienuso(String bienuso){ this.bienuso=bienuso; }
 public BigDecimal getIdrubro(){ return idrubro; }
 public void setIdrubro(BigDecimal idrubro){ this.idrubro=idrubro; }
 public Timestamp getFechacompra(){ return fechacompra; }
 public void setFechacompra(Timestamp fechacompra){ this.fechacompra=fechacompra; }
 public Double getValorori(){ return valorori; }
 public void setValorori(Double valorori){ this.valorori=valorori; }
 public BigDecimal getAnios(){ return anios; }
 public void setAnios(BigDecimal anios){ this.anios=anios; }
 public BigDecimal getMeses(){ return meses; }
 public void setMeses(BigDecimal meses){ this.meses=meses; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public String getFechacompraStr() {
	return fechacompraStr;
}

public void setFechacompraStr(String fechacompraStr) {
	this.fechacompraStr = fechacompraStr;
	this.fechacompra = (java.sql.Timestamp) Common.setObjectToStrOrTime(
			fechacompraStr, "StrToJSTs");
}
public String getRubro() {
	return rubro;
}
public void setRubro(String rubro) {
	this.rubro = rubro;
}







}






