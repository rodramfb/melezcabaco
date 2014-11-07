/* 
   javabean para la entidad (Formulario): contablerubros
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 04 12:46:53 ACT 2009 
   
   Para manejar la pagina: contablerubrosFrm.jsp
      
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

public class BeanContablerubrosFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanContablerubrosFrm.class);        
 private String validar = "";
 private BigDecimal cod_rubro= BigDecimal.valueOf(-1);
 private String desc_rubro = "";
 private String ubicacion = "";
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanContablerubrosFrm() { super();}
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
   Contable contablerubros = Common.getContable();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = contablerubros.contablerubrosCreate( this.desc_rubro,  this.ubicacion,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = contablerubros.contablerubrosUpdate( this.cod_rubro,  this.desc_rubro,  this.ubicacion,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = contablerubros.contablerubrosDelete(this.cod_rubro,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosContablerubros() {
   try {
     Contable contablerubros = Common.getContable();
     List listContablerubros = contablerubros.getContablerubrosPK(this.cod_rubro, this.idempresa );
     Iterator iterContablerubros = listContablerubros.iterator();
     if (iterContablerubros.hasNext()) {
        String[] uCampos = (String[]) iterContablerubros.next();
        //TODO: Constructores para cada tipo de datos
        this.cod_rubro= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.desc_rubro= uCampos[1];
        this.ubicacion= uCampos[2];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[3]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosContablerubros()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("contablerubrosAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(desc_rubro == null ){
       this.mensaje = "No se puede dejar vacio el campo desc_rubro ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(desc_rubro.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Rubro  ";
       return false;
    }
     
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosContablerubros();
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
 public BigDecimal getCod_rubro(){ return cod_rubro; }
 public void setCod_rubro(BigDecimal cod_rubro){ this.cod_rubro=cod_rubro; }
 public String getDesc_rubro(){ return desc_rubro; }
 public void setDesc_rubro(String desc_rubro){ this.desc_rubro=desc_rubro; }
 public String getUbicacion(){ return ubicacion; }
 public void setUbicacion(String ubicacion){ this.ubicacion=ubicacion; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
}






