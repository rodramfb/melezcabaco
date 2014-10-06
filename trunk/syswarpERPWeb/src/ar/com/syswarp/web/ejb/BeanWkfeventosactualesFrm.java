/* 
   javabean para la entidad (Formulario): Wkfeventosactuales
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Jul 27 12:38:47 ACT 2009 
   
   Para manejar la pagina: WkfeventosactualesFrm.jsp
      
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

public class BeanWkfeventosactualesFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanWkfeventosactualesFrm.class);        
 private String validar = "";
 private BigDecimal idventoactual= new BigDecimal(-1);
 private BigDecimal ideventoresultado= new BigDecimal(0);
 private String eventoresultado ="";
 private BigDecimal idprocesoevento= new BigDecimal(0);
 private String procesoevento= "";
 private BigDecimal idtransaccion= new BigDecimal(0);
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String destransaccion= "";
 private Timestamp fecha = new Timestamp(Common.initObjectTime());
 private String fechaStr = Common.initObjectTimeStr();
 private String transaccion ="";
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanWkfeventosactualesFrm() { super();}
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
   General Wkfeventosactuales = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = Wkfeventosactuales.WkfeventosactualesCreate( this.ideventoresultado,  this.idprocesoevento,  this.idtransaccion,  this.fecha,  this.transaccion,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = Wkfeventosactuales.WkfeventosactualesUpdate( this.idventoactual,  this.ideventoresultado,  this.idprocesoevento,  this.idtransaccion,  this.fecha,  this.transaccion,  this.usuarioact,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = Wkfeventosactuales.WkfeventosactualesDelete(this.idventoactual,  this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosWkfeventosactuales() {
   try {
	 General Wkfeventosactuales = Common.getGeneral();
     List listWkfeventosactuales = Wkfeventosactuales.getWkfeventosactualesPK(this.idventoactual, this.idempresa );
     Iterator iterWkfeventosactuales = listWkfeventosactuales.iterator();
     if (iterWkfeventosactuales.hasNext()) {
        String[] uCampos = (String[]) iterWkfeventosactuales.next();
        //TODO: Constructores para cada tipo de datos
        this.idventoactual= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.ideventoresultado= BigDecimal.valueOf(Long.parseLong(uCampos[1]));
        this.eventoresultado= uCampos[2];
        this.idprocesoevento= BigDecimal.valueOf(Long.parseLong(uCampos[3]));
        this.procesoevento= uCampos[4];
        this.idtransaccion= BigDecimal.valueOf(Long.parseLong(uCampos[5]));
        this.destransaccion= uCampos[6];
        this.fecha= Timestamp.valueOf(uCampos[7]);
        this.fechaStr = Common.setObjectToStrOrTime(fecha, "JSTsToStr").toString();
        this.transaccion= uCampos[8];
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosWkfeventosactuales()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("WkfeventosactualesAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(ideventoresultado == null ){
       this.mensaje = "No se puede dejar vacio el campo ideventoresultado ";
       return false;
    }
    if(idprocesoevento == null ){
       this.mensaje = "No se puede dejar vacio el campo idprocesoevento ";
       return false;
    }
    if(idtransaccion == null ){
       this.mensaje = "No se puede dejar vacio el campo idtransaccion ";
       return false;
    }
    if(fecha == null ){
       this.mensaje = "No se puede dejar vacio el campo fecha ";
       return false;
    }
    
	if (ideventoresultado.compareTo(new BigDecimal(0)) == 0) {
		this.mensaje = "No se puede dejar vacio el campo Evento Resultado ";
		return false;
	}
    
	if (idprocesoevento.compareTo(new BigDecimal(0)) == 0) {
		this.mensaje = "No se puede dejar vacio el campo Proceso Evento ";
		return false;
	}
    
	if (idtransaccion.compareTo(new BigDecimal(0)) == 0) {
		this.mensaje = "No se puede dejar vacio el campo Desc Transaccion  ";
		return false;
	}
    
       // 2. len 0 para campos nulos
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosWkfeventosactuales();
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
 public BigDecimal getIdventoactual(){ return idventoactual; }
 public void setIdventoactual(BigDecimal idventoactual){ this.idventoactual=idventoactual; }
 public BigDecimal getIdeventoresultado(){ return ideventoresultado; }
 public void setIdeventoresultado(BigDecimal ideventoresultado){ this.ideventoresultado=ideventoresultado; }
 public BigDecimal getIdprocesoevento(){ return idprocesoevento; }
 public void setIdprocesoevento(BigDecimal idprocesoevento){ this.idprocesoevento=idprocesoevento; }
 public BigDecimal getIdtransaccion(){ return idtransaccion; }
 public void setIdtransaccion(BigDecimal idtransaccion){ this.idtransaccion=idtransaccion; }
 public Timestamp getFecha(){ return fecha; }
 public void setFecha(Timestamp fecha){ this.fecha=fecha; }
 public String getTransaccion(){ return transaccion; }
 public void setTransaccion(String transaccion){ this.transaccion=transaccion; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public String getEventoresultado() {
	return eventoresultado;
}
public void setEventoresultado(String eventoresultado) {
	this.eventoresultado = eventoresultado;
}
public String getProcesoevento() {
	return procesoevento;
}
public void setProcesoevento(String procesoevento) {
	this.procesoevento = procesoevento;
}
public String getDestransaccion() {
	return destransaccion;
}
public void setDestransaccion(String destransaccion) {
	this.destransaccion = destransaccion;
}
public BigDecimal getIdempresa() {
	return idempresa;
}
public void setIdempresa(BigDecimal idempresa) {
	this.idempresa = idempresa;
}
public String getFechaStr() {
	return fechaStr;
}


public void setFechaStr(String fechaStr) {
	this.fechaStr = fechaStr;
	this.fecha = (java.sql.Timestamp) Common.setObjectToStrOrTime(
			fechaStr, "StrToJSTs");
}


}






