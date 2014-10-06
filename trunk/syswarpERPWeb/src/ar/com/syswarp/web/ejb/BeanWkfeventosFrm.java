/* 
   javabean para la entidad (Formulario): wkfeventos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 22 10:12:11 ACT 2009 
   
   Para manejar la pagina: wkfeventosFrm.jsp
      
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

public class BeanWkfeventosFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanWkfeventosFrm.class);        
 private String validar = "";
 private BigDecimal idevento= BigDecimal.valueOf(-1);
 private String evento ="";
 private BigDecimal idtipoevento= BigDecimal.valueOf(0);
 private String tipoevento ="";
 private BigDecimal idproximoevento= BigDecimal.valueOf(0);
 private String proximoevento= "";
 private String descripcion= "";
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanWkfeventosFrm() { super();}
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
	  
	  
	  
   General wkfeventos = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = wkfeventos.wkfeventosCreate( this.evento,  this.idtipoevento,  this.idproximoevento,  this.descripcion,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = wkfeventos.wkfeventosUpdate( this.idevento,  this.evento,  this.idtipoevento,  this.idproximoevento,  this.descripcion,  this.usuarioact,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = wkfeventos.wkfeventosDelete(this.idevento,  this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosWkfeventos() {
   try {
     General wkfeventos = Common.getGeneral();
     List listWkfeventos = wkfeventos.getWkfeventosPK(this.idevento, this.idempresa );
     Iterator iterWkfeventos = listWkfeventos.iterator();
     if (iterWkfeventos.hasNext()) {
        String[] uCampos = (String[]) iterWkfeventos.next();
        //TODO: Constructores para cada tipo de datos
        this.idevento= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.evento= uCampos[1];
        this.idtipoevento= BigDecimal.valueOf(Long.parseLong(uCampos[2]));
        this.tipoevento= uCampos[3];
        //this.idproximoevento=  BigDecimal.valueOf(Long.parseLong(uCampos[4]));
        
        this.idproximoevento = new BigDecimal(uCampos[4] == null ? "-1" : uCampos[4]);
        
        this.proximoevento= uCampos[5];
        this.descripcion= uCampos[6];
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosWkfeventos()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("wkfeventosAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(evento == null ){
       this.mensaje = "No se puede dejar vacio el campo evento ";
       return false;
    }
    if(idtipoevento == null ){
       this.mensaje = "No se puede dejar vacio el campo idtipoevento ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(evento.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Evento  ";
       return false;
    }
    if(tipoevento.trim().length() == 0  ){
        this.mensaje = "No se puede dejar vacio el campo Tipo Evento  ";
        return false;
     }
    
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosWkfeventos();
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
 public BigDecimal getIdevento(){ return idevento; }
 public void setIdevento(BigDecimal idevento){ this.idevento=idevento; }
 public String getEvento(){ return evento; }
 public void setEvento(String evento){ this.evento=evento; }
 public BigDecimal getIdtipoevento(){ return idtipoevento; }
 public void setIdtipoevento(BigDecimal idtipoevento){ this.idtipoevento=idtipoevento; }
 public BigDecimal getIdproximoevento(){ return idproximoevento; }
 public void setIdproximoevento(BigDecimal idproximoevento){ this.idproximoevento=idproximoevento; }
 public String getDescripcion(){ return descripcion; }
 public void setDescripcion(String descripcion){ this.descripcion=descripcion; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public String getTipoevento() {
	return tipoevento;
}
public void setTipoevento(String tipoevento) {
	this.tipoevento = tipoevento;
}
public String getProximoevento() {
	return proximoevento;
}
public void setProximoevento(String proximoevento) {
	this.proximoevento = proximoevento;
}
public BigDecimal getIdempresa() {
	return idempresa;
}
public void setIdempresa(BigDecimal idempresa) {
	this.idempresa = idempresa;
}
}






