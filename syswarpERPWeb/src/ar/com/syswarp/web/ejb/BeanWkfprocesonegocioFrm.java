/* 
   javabean para la entidad (Formulario): wkfprocesonegocio
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 23 10:29:08 ACT 2009 
   
   Para manejar la pagina: wkfprocesonegocioFrm.jsp
      
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

public class BeanWkfprocesonegocioFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanWkfprocesonegocioFrm.class);        
 private String validar = "";
 private BigDecimal idprocesonegocio= BigDecimal.valueOf(-1);
 private String procesonegocio ="";
 private BigDecimal idprocesonegocionext= BigDecimal.valueOf(0);
 private BigDecimal idempresa = new BigDecimal(0);
 private String procesonegocionext= "";
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanWkfprocesonegocioFrm() { super();}
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
   General wkfprocesonegocio = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = wkfprocesonegocio.wkfprocesonegocioCreate( this.procesonegocio,  this.idprocesonegocionext,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = wkfprocesonegocio.wkfprocesonegocioUpdate( this.idprocesonegocio,  this.procesonegocio,  this.idprocesonegocionext,  this.usuarioact,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = wkfprocesonegocio.wkfprocesonegocioDelete(this.idprocesonegocio,  this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosWkfprocesonegocio() {
   try {
	 General wkfprocesonegocio = Common.getGeneral();
     List listWkfprocesonegocio = wkfprocesonegocio.getWkfprocesonegocioPK(this.idprocesonegocio, this.idempresa );
     Iterator iterWkfprocesonegocio = listWkfprocesonegocio.iterator();
     if (iterWkfprocesonegocio.hasNext()) {
        String[] uCampos = (String[]) iterWkfprocesonegocio.next();
        //TODO: Constructores para cada tipo de datos
        this.idprocesonegocio= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.procesonegocio= uCampos[1];
        this.idprocesonegocionext= new BigDecimal(uCampos[2] == null ? "-1" : uCampos[2]);
        this.procesonegocionext = uCampos[3];    
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosWkfprocesonegocio()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("wkfprocesonegocioAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(procesonegocio == null ){
       this.mensaje = "No se puede dejar vacio el campo procesonegocio ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(procesonegocio.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Proceso negocio  ";
       return false;
    }
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosWkfprocesonegocio();
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
 public BigDecimal getIdprocesonegocio(){ return idprocesonegocio; }
 public void setIdprocesonegocio(BigDecimal idprocesonegocio){ this.idprocesonegocio=idprocesonegocio; }
 public String getProcesonegocio(){ return procesonegocio; }
 public void setProcesonegocio(String procesonegocio){ this.procesonegocio=procesonegocio; }
 public BigDecimal getIdprocesonegocionext(){ return idprocesonegocionext; }
 public void setIdprocesonegocionext(BigDecimal idprocesonegocionext){ this.idprocesonegocionext=idprocesonegocionext; }
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
public String getProcesonegocionext() {
	return procesonegocionext;
}
public void setProcesonegocionext(String procesonegocionext) {
	this.procesonegocionext = procesonegocionext;
}
}






