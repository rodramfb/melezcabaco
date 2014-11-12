/* 
   javabean para la entidad (Formulario): wkftipoeventos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jul 21 13:50:34 ACT 2009 
   
   Para manejar la pagina: wkftipoeventosFrm.jsp
      
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

public class BeanWkftipoeventosFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanWkftipoeventosFrm.class);        
 private String validar = "";
 private BigDecimal idtipoevento= BigDecimal.valueOf(-1);
 private String tipoevento ="";
 private String usuarioalt;
 private String usuarioact;
 private BigDecimal idempresa= BigDecimal.valueOf(0); 
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanWkftipoeventosFrm() { super();}
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
   General wkftipoeventos = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = wkftipoeventos.wkftipoeventosCreate( this.tipoevento,this.usuarioalt ,this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = wkftipoeventos.wkftipoeventosUpdate( this.idtipoevento,  this.tipoevento,  this.usuarioact,this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = wkftipoeventos.wkftipoeventosDelete(this.idtipoevento,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosWkftipoeventos() {
   try {
     General wkftipoeventos = Common.getGeneral();
     List listWkftipoeventos = wkftipoeventos.getWkftipoeventosPK(this.idtipoevento, this.idempresa );
     Iterator iterWkftipoeventos = listWkftipoeventos.iterator();
     if (iterWkftipoeventos.hasNext()) {
        String[] uCampos = (String[]) iterWkftipoeventos.next();
        //TODO: Constructores para cada tipo de datos
        this.idtipoevento= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.tipoevento= uCampos[1];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[2]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosWkftipoeventos()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("wkftipoeventosAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
       // 2. len 0 para campos nulos
    	   
			if (tipoevento.trim().length() == 0) {
				this.mensaje = "No se puede dejar vacio el campo Tipo Evento  ";
				return false;
			} 
    	   
    	   
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosWkftipoeventos();
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
 public BigDecimal getIdtipoevento(){ return idtipoevento; }
 public void setIdtipoevento(BigDecimal idtipoevento){ this.idtipoevento=idtipoevento; }
 public String getTipoevento(){ return tipoevento; }
 public void setTipoevento(String tipoevento){ this.tipoevento=tipoevento; }
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






