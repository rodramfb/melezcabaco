/* 
   javabean para la entidad (Formulario): rrrhhtokenkizer
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Jul 31 13:15:14 ACT 2009 
   
   Para manejar la pagina: rrrhhtokenkizerFrm.jsp
      
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

public class BeanRrrhhtokenkizerFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanRrrhhtokenkizerFrm.class);        
 private String validar = "";
 private BigDecimal idtokenkizer= BigDecimal.valueOf(-1);
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String tokenkizer ="";
 private String parametro ="";
 private String descripcion ="";
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanRrrhhtokenkizerFrm() { super();}
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
   RRHH rrrhhtokenkizer = Common.getRrhh();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = rrrhhtokenkizer.rrrhhtokenkizerCreate( this.tokenkizer,  this.parametro,  this.descripcion,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = rrrhhtokenkizer.rrrhhtokenkizerUpdate( this.idtokenkizer,  this.tokenkizer,  this.parametro,  this.descripcion,  this.usuarioact,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = rrrhhtokenkizer.rrrhhtokenkizerDelete(this.idtokenkizer,  this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosRrrhhtokenkizer() {
   try {
	   RRHH rrrhhtokenkizer = Common.getRrhh();
     List listRrrhhtokenkizer = rrrhhtokenkizer.getRrrhhtokenkizerPK(this.idtokenkizer, this.idempresa );
     Iterator iterRrrhhtokenkizer = listRrrhhtokenkizer.iterator();
     if (iterRrrhhtokenkizer.hasNext()) {
        String[] uCampos = (String[]) iterRrrhhtokenkizer.next();
        //TODO: Constructores para cada tipo de datos
        this.idtokenkizer= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.tokenkizer= uCampos[1];
        this.parametro= uCampos[2];
        this.descripcion= uCampos[3];
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosRrrhhtokenkizer()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("rrrhhtokenkizerAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(tokenkizer == null ){
       this.mensaje = "No se puede dejar vacio el campo tokenkizer ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(tokenkizer.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Tokenkizer  ";
       return false;
    }
    if(descripcion.trim().length() == 0  ){
        this.mensaje = "No se puede dejar vacio el campo Descripcion  ";
        return false;
     }

   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosRrrhhtokenkizer();
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
 public BigDecimal getIdtokenkizer(){ return idtokenkizer; }
 public void setIdtokenkizer(BigDecimal idtokenkizer){ this.idtokenkizer=idtokenkizer; }
 public String getTokenkizer(){ return tokenkizer; }
 public void setTokenkizer(String tokenkizer){ this.tokenkizer=tokenkizer; }
 public String getParametro(){ return parametro; }
 public void setParametro(String parametro){ this.parametro=parametro; }
 public String getDescripcion(){ return descripcion; }
 public void setDescripcion(String descripcion){ this.descripcion=descripcion; }
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
}






