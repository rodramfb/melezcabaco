/* 
   javabean para la entidad (Formulario): globalusuariosdepositos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 25 15:50:16 CEST 2008 
   
   Para manejar la pagina: globalusuariosdepositosFrm.jsp
      
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

public class BeanGlobalusuariosdepositosFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanGlobalusuariosdepositosFrm.class);        
 private String validar = "";
 private BigDecimal idusuariosdepositos= BigDecimal.valueOf(-1);
 private BigDecimal idusuario= BigDecimal.valueOf(0);
 private String usuario = "";
 private BigDecimal codigo_dt= BigDecimal.valueOf(0);
 private String descrip_dt ="";
 private String observaciones ="";
 private BigDecimal idempresa;
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanGlobalusuariosdepositosFrm() { super();}
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
   General globalusuariosdepositos = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = globalusuariosdepositos.globalusuariosdepositosCreate( this.idusuario,  this.codigo_dt,  this.observaciones,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = globalusuariosdepositos.globalusuariosdepositosUpdate( this.idusuariosdepositos,  this.idusuario,  this.codigo_dt,  this.observaciones,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = globalusuariosdepositos.globalusuariosdepositosDelete(this.idusuariosdepositos,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosGlobalusuariosdepositos() {
   try {
	 General globalusuariosdepositos = Common.getGeneral();
     List listGlobalusuariosdepositos = globalusuariosdepositos.getGlobalusuariosdepositosPK(this.idusuariosdepositos, this.idempresa );
     Iterator iterGlobalusuariosdepositos = listGlobalusuariosdepositos.iterator();
     if (iterGlobalusuariosdepositos.hasNext()) {
        String[] uCampos = (String[]) iterGlobalusuariosdepositos.next();
        //TODO: Constructores para cada tipo de datos
        this.idusuariosdepositos= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.idusuario= BigDecimal.valueOf(Long.parseLong(uCampos[1]));
        this.usuario= uCampos[2];
        this.codigo_dt= BigDecimal.valueOf(Long.parseLong(uCampos[3]));
        this.descrip_dt= uCampos[4];
        this.observaciones= uCampos[5];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[6]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosGlobalusuariosdepositos()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("globalusuariosdepositosAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(idusuario == null ){
       this.mensaje = "No se puede dejar vacio el campo idusuario ";
       return false;
    }
    if(codigo_dt == null ){
       this.mensaje = "No se puede dejar vacio el campo codigo_dt ";
       return false;
    }
    
	if (usuario.trim().length() == 0) {
		this.mensaje = "No se puede dejar vacio el campo Usuario  ";
		return false;
	}
	if (descrip_dt.trim().length() == 0) {
		this.mensaje = "No se puede dejar vacio el campo Deposito  ";
		return false;
	} 
    
    
       // 2. len 0 para campos nulos
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosGlobalusuariosdepositos();
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
 public BigDecimal getIdusuariosdepositos(){ return idusuariosdepositos; }
 public void setIdusuariosdepositos(BigDecimal idusuariosdepositos){ this.idusuariosdepositos=idusuariosdepositos; }
 public BigDecimal getIdusuario(){ return idusuario; }
 public void setIdusuario(BigDecimal idusuario){ this.idusuario=idusuario; }
 public BigDecimal getCodigo_dt(){ return codigo_dt; }
 public void setCodigo_dt(BigDecimal codigo_dt){ this.codigo_dt=codigo_dt; }
 public String getObservaciones(){ return observaciones; }
 public void setObservaciones(String observaciones){ this.observaciones=observaciones; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public String getUsuario() {
	return usuario;
}
public void setUsuario(String usuario) {
	this.usuario = usuario;
}
public String getDescrip_dt() {
	return descrip_dt;
}
public void setDescrip_dt(String descrip_dt) {
	this.descrip_dt = descrip_dt;
}
}






