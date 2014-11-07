/* 
   javabean para la entidad (Formulario): rrhhestadosot
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Apr 04 10:22:27 ART 2008 
   
   Para manejar la pagina: rrhhestadosotFrm.jsp
      
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

public class BeanRrhhestadosotFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanRrhhestadosotFrm.class);        
 private String validar = "";
 private BigDecimal idestadoot= BigDecimal.valueOf(-1);
 private String estadoot ="";
 private BigDecimal idempresa;
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanRrhhestadosotFrm() { super();}
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
   RRHH rrhhestadosot = Common.getRrhh();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = rrhhestadosot.rrhhestadosotCreate( this.estadoot,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = rrhhestadosot.rrhhestadosotUpdate( this.idestadoot,  this.estadoot,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = rrhhestadosot.rrhhestadosotDelete(this.idestadoot,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosRrhhestadosot() {
   try {
     RRHH rrhhestadosot = Common.getRrhh();
     List listRrhhestadosot = rrhhestadosot.getRrhhestadosotPK(this.idestadoot, this.idempresa );
     Iterator iterRrhhestadosot = listRrhhestadosot.iterator();
     if (iterRrhhestadosot.hasNext()) {
        String[] uCampos = (String[]) iterRrhhestadosot.next();
        //TODO: Constructores para cada tipo de datos
        this.idestadoot= BigDecimal.valueOf(Long
				.parseLong(uCampos[0]));
        this.estadoot= uCampos[1];
        this.idempresa= BigDecimal.valueOf(Long
				.parseLong(uCampos[2]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosRrhhestadosot()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("rrhhestadosotAbm.jsp");
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
      getDatosRrhhestadosot();
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
 public BigDecimal getIdestadoot(){ return idestadoot; }
 public void setIdestadoot(BigDecimal idestadoot){ this.idestadoot=idestadoot; }
 public String getEstadoot(){ return estadoot; }
 public void setEstadoot(String estadoot){ this.estadoot=estadoot; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
}






