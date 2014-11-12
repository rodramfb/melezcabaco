/* 
   javabean para la entidad (Formulario): clientesexpresoszonas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 30 10:10:49 ART 2008 
   
   Para manejar la pagina: clientesexpresoszonasFrm.jsp
      
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

public class BeanClientesexpresoszonasFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanClientesexpresoszonasFrm.class);        
 private String validar = "";
 private BigDecimal codigo= BigDecimal.valueOf(-1);
 private BigDecimal idexpreso= BigDecimal.valueOf(0);
 private String expreso = "";
 private BigDecimal idzona= BigDecimal.valueOf(0);
 private String zona = "";
 private BigDecimal idempresa;
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanClientesexpresoszonasFrm() { super();}
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
   General clientesexpresoszonas = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = clientesexpresoszonas.clientesexpresoszonasCreate( this.idexpreso,  this.idzona,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = clientesexpresoszonas.clientesexpresoszonasUpdate( this.codigo,  this.idexpreso,  this.idzona,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = clientesexpresoszonas.clientesexpresoszonasDelete(this.codigo,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosClientesexpresoszonas() {
   try {
	 General clientesexpresoszonas = Common.getGeneral();
     List listClientesexpresoszonas = clientesexpresoszonas.getClientesexpresoszonasPK(this.codigo, this.idempresa );
     Iterator iterClientesexpresoszonas = listClientesexpresoszonas.iterator();
     if (iterClientesexpresoszonas.hasNext()) {
        String[] uCampos = (String[]) iterClientesexpresoszonas.next();
        //TODO: Constructores para cada tipo de datos
        this.codigo= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.idexpreso= BigDecimal.valueOf(Long.parseLong(uCampos[1]));
        this.expreso =  uCampos[2];
        this.idzona= BigDecimal.valueOf(Long.parseLong(uCampos[3]));
        this.zona=  uCampos[4];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[5]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosClientesexpresoszonas()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("clientesexpresoszonasAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
 
    
	if (expreso.trim().length() == 0) {
		this.mensaje = "No se puede dejar vacio el campo Expreso  ";
		return false;
	}
	if (zona.trim().length() == 0) {
		this.mensaje = "No se puede dejar vacio el campo Zona  ";
		return false;
	}   
    
    
       // 2. len 0 para campos nulos
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosClientesexpresoszonas();
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
 public BigDecimal getCodigo(){ return codigo; }
 public void setCodigo(BigDecimal codigo){ this.codigo=codigo; }
 public BigDecimal getIdexpreso(){ return idexpreso; }
 public void setIdexpreso(BigDecimal idexpreso){ this.idexpreso=idexpreso; }
 public BigDecimal getIdzona(){ return idzona; }
 public void setIdzona(BigDecimal idzona){ this.idzona=idzona; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public String getExpreso() {
	return expreso;
}
public void setExpreso(String expreso) {
	this.expreso = expreso;
}
public String getZona() {
	return zona;
}
public void setZona(String zona) {
	this.zona = zona;
}
}






