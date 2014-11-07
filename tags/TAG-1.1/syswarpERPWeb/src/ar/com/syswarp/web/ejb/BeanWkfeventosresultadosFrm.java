/* 
   javabean para la entidad (Formulario): wkfeventosresultados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Jul 24 10:36:58 ACT 2009 
   
   Para manejar la pagina: wkfeventosresultadosFrm.jsp
      
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

public class BeanWkfeventosresultadosFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanWkfeventosresultadosFrm.class);        
 private String validar = "";
 private BigDecimal ideventoresultado= BigDecimal.valueOf(-1);
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String eventoresultado ="";
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanWkfeventosresultadosFrm() { super();}
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
   General wkfeventosresultados = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = wkfeventosresultados.wkfeventosresultadosCreate( this.eventoresultado,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = wkfeventosresultados.wkfeventosresultadosUpdate( this.ideventoresultado,  this.eventoresultado,  this.usuarioact,this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = wkfeventosresultados.wkfeventosresultadosDelete(this.ideventoresultado,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosWkfeventosresultados() {
   try {
	 General wkfeventosresultados = Common.getGeneral();
     List listWkfeventosresultados = wkfeventosresultados.getWkfeventosresultadosPK(this.ideventoresultado, this.idempresa );
     Iterator iterWkfeventosresultados = listWkfeventosresultados.iterator();
     if (iterWkfeventosresultados.hasNext()) {
        String[] uCampos = (String[]) iterWkfeventosresultados.next();
        //TODO: Constructores para cada tipo de datos
        this.ideventoresultado= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.eventoresultado= uCampos[1];
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosWkfeventosresultados()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("wkfeventosresultadosAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
       // 2. len 0 para campos nulos
    	   
    	    if(eventoresultado.trim().length() == 0  ){
    	        this.mensaje = "No se puede dejar vacio el campo Evento Resultado  ";
    	        return false;
    	     }	   
    	   
    	   
    	   
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosWkfeventosresultados();
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
 public BigDecimal getIdeventoresultado(){ return ideventoresultado; }
 public void setIdeventoresultado(BigDecimal ideventoresultado){ this.ideventoresultado=ideventoresultado; }
 public String getEventoresultado(){ return eventoresultado; }
 public void setEventoresultado(String eventoresultado){ this.eventoresultado=eventoresultado; }
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






