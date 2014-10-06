/* 
   javabean para la entidad (Formulario): contableejercicios
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Sep 09 14:46:45 ART 2008 
   
   Para manejar la pagina: contableejerciciosFrm.jsp
      
*/ 
package ar.com.syswarp.web.ejb;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

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

public class BeanContableejerciciosFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanContableejerciciosFrm.class);        
 private String validar = "";
 private BigDecimal ejercicio= BigDecimal.valueOf(0);
 private Timestamp fechadesde= new Timestamp(Common.initObjectTime());
 private String fechadesdeStr = Common.initObjectTimeStr();
 
 private Timestamp fechahasta= new Timestamp(Common.initObjectTime());
 private String fechahastaStr = Common.initObjectTimeStr();
 
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String activo ="";
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanContableejerciciosFrm() { super();}
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
   Contable contableejercicios = Common.getContable(); 
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = contableejercicios.contableejerciciosCreate( this.ejercicio,this.fechadesde,  this.fechahasta,  this.activo,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion")) 
      this.mensaje = contableejercicios.contableejerciciosUpdate( this.ejercicio,  this.fechadesde,  this.fechahasta,  this.activo,  this.usuarioact,this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = contableejercicios.contableejerciciosDelete(this.ejercicio,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosContableejercicios() {
   try {
     Contable contableejercicios = Common.getContable();
     List listContableejercicios = contableejercicios.getContableejerciciosPK(this.ejercicio, this.idempresa );
     Iterator iterContableejercicios = listContableejercicios.iterator();
     if (iterContableejercicios.hasNext()) {
        String[] uCampos = (String[]) iterContableejercicios.next();
        //TODO: Constructores para cada tipo de datos
        this.ejercicio= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.fechadesde= Timestamp.valueOf(uCampos[1]);
        this.fechadesdeStr = Common.setObjectToStrOrTime(fechadesde, "JSTsToStr").toString();
        this.fechahasta= Timestamp.valueOf(uCampos[2]);
        this.fechahastaStr = Common.setObjectToStrOrTime(fechahasta, "JSTsToStr").toString();
        this.activo= uCampos[3];
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosContableejercicios()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("contableejerciciosAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(fechadesde == null ){
       this.mensaje = "No se puede dejar vacio el campo fechadesde ";
       return false;
    }
    if(fechahasta == null ){
       this.mensaje = "No se puede dejar vacio el campo fechahasta ";
       return false;
    }
    
	if (ejercicio.compareTo(new BigDecimal(0)) == 0) {
		this.mensaje = "No se puede dejar vacio el campo Ejercicio ";
		return false;
	}
    
    
    
       // 2. len 0 para campos nulos
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosContableejercicios();
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
 public BigDecimal getEjercicio(){ return ejercicio; }
 public void setEjercicio(BigDecimal ejercicio){ this.ejercicio=ejercicio; }
 public Timestamp getFechadesde(){ return fechadesde; }
 public void setFechadesde(Timestamp fechadesde){ this.fechadesde=fechadesde; }
 public Timestamp getFechahasta(){ return fechahasta; }
 public void setFechahasta(Timestamp fechahasta){ this.fechahasta=fechahasta; }
 public String getActivo(){ return activo; }
 public void setActivo(String activo){ this.activo=activo; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
 
 
 
 
 
	public void setFechadesdeStr(String fechadesdeStr) {
		this.fechadesdeStr = fechadesdeStr;
		this.fechadesde = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fechadesdeStr, "StrToJSTs");
	}
 
 
	public void setFechahastaStr(String fechahastaStr) {
		this.fechahastaStr = fechahastaStr;
		this.fechahasta = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fechahastaStr, "StrToJSTs");
	}
	public BigDecimal getIdempresa() {
		return idempresa;
	}
	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
	public String getFechadesdeStr() {
		return fechadesdeStr;
	}
	public String getFechahastaStr() {
		return fechahastaStr;
	}
}






