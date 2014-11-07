/* 
   javabean para la entidad (Formulario): tickets_severidad
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Feb 26 12:37:18 ART 2008 
   
   Para manejar la pagina: tickets_severidadFrm.jsp
      
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

public class BeanTickets_severidadFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanTickets_severidadFrm.class);        
 private String validar = "";
 private BigDecimal idseveridad= BigDecimal.valueOf(-1);
 private String severidad="";
 private BigDecimal idempresa;
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanTickets_severidadFrm() { super();}
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
   General tickets_severidad = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = tickets_severidad.tickets_severidadCreate( this.severidad,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = tickets_severidad.tickets_severidadUpdate( this.idseveridad,  this.severidad,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = tickets_severidad.tickets_severidadDelete(this.idseveridad,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosTickets_severidad() {
   try {
	 General tickets_severidad = Common.getGeneral();
     List listTickets_severidad = tickets_severidad.getTickets_severidadPK(this.idseveridad, this.idempresa );
     Iterator iterTickets_severidad = listTickets_severidad.iterator();
     if (iterTickets_severidad.hasNext()) {
        String[] uCampos = (String[]) iterTickets_severidad.next();
        //TODO: Constructores para cada tipo de datos
        this.idseveridad= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.severidad= uCampos[1];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[2]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosTickets_severidad()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("tickets_severidadAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
       // 2. len 0 para campos nulos
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosTickets_severidad();
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
 public BigDecimal getIdseveridad(){ return idseveridad; }
 public void setIdseveridad(BigDecimal idseveridad){ this.idseveridad=idseveridad; }
 public String getSeveridad(){ return severidad; }
 public void setSeveridad(String severidad){ this.severidad=severidad; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
}






