/* 
   javabean para la entidad (Formulario): clientespreferencias
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Oct 27 08:30:15 ACT 2008 
   
   Para manejar la pagina: clientespreferenciasFrm.jsp
      
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

public class BeanClientespreferenciasFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanClientespreferenciasFrm.class);        
 private String validar = "";
 private BigDecimal idpreferencia= BigDecimal.valueOf(-1);
 private String preferencia = "";
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanClientespreferenciasFrm() { super();}
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
   General clientespreferencias = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = clientespreferencias.clientespreferenciasCreate( this.preferencia,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = clientespreferencias.clientespreferenciasUpdate( this.idpreferencia,  this.preferencia,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = clientespreferencias.clientespreferenciasDelete(this.idpreferencia,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosClientespreferencias() {
   try {
     General clientespreferencias = Common.getGeneral();
     List listClientespreferencias = clientespreferencias.getClientespreferenciasPK(this.idpreferencia, this.idempresa );
     Iterator iterClientespreferencias = listClientespreferencias.iterator();
     if (iterClientespreferencias.hasNext()) {
        String[] uCampos = (String[]) iterClientespreferencias.next();
        //TODO: Constructores para cada tipo de datos
        this.idpreferencia= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.preferencia= uCampos[1];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[2]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosClientespreferencias()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("clientespreferenciasAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
       // 2. len 0 para campos nulos
			if (preferencia.trim().length() == 0) {
				this.mensaje = "No se puede dejar vacio el campo Preferencia";
				return false;
			}	   
 	   
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosClientespreferencias();
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
 public BigDecimal getIdpreferencia(){ return idpreferencia; }
 public void setIdpreferencia(BigDecimal idpreferencia){ this.idpreferencia=idpreferencia; }
 public String getPreferencia(){ return preferencia; }
 public void setPreferencia(String preferencia){ this.preferencia=preferencia; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
}






