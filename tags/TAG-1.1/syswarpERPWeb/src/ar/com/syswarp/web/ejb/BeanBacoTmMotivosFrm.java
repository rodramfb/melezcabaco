/* 
   javabean para la entidad (Formulario): bacoTmMotivos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 04 16:13:28 ART 2007 
   
   Para manejar la pagina: bacoTmMotivosFrm.jsp
      
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

public class BeanBacoTmMotivosFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanBacoTmMotivosFrm.class);        
 private String validar = "";
 private BigDecimal idmotivo= BigDecimal.valueOf(-1);
 private BigDecimal idempresa;
 private String motivo ="";
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanBacoTmMotivosFrm() { super();}
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
   Clientes bacoTmMotivos = Common.getClientes();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = bacoTmMotivos.bacoTmMotivosCreate( this.motivo,this.idempresa, this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = bacoTmMotivos.bacoTmMotivosUpdate( this.idmotivo,  this.motivo,  this.usuarioact,this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = bacoTmMotivos.bacoTmMotivosDelete(this.idmotivo,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosBacoTmMotivos() {
   try {
	   Clientes bacoTmMotivos = Common.getClientes();
     List listBacoTmMotivos = bacoTmMotivos.getBacoTmMotivosPK(this.idmotivo,this.idempresa);
     Iterator iterBacoTmMotivos = listBacoTmMotivos.iterator();
     if (iterBacoTmMotivos.hasNext()) {
        String[] uCampos = (String[]) iterBacoTmMotivos.next();
        //TODO: Constructores para cada tipo de datos
        this.idmotivo= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.motivo= uCampos[1];
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosBacoTmMotivos()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("bacoTmMotivosAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
       // 2. len 0 para campos nulos
    	   
			if (motivo.trim().length() == 0) {
				this.mensaje = "No se puede dejar vacio el campo Motivo  ";
				return false;
			}	   
    	   
    	   
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosBacoTmMotivos();
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
 public BigDecimal getIdmotivo(){ return idmotivo; }
 public void setIdmotivo(BigDecimal idmotivo){ this.idmotivo=idmotivo; }
 public String getMotivo(){ return motivo; }
 public void setMotivo(String motivo){ this.motivo=motivo; }
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






