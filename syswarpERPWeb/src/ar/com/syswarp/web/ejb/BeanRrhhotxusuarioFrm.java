/* 
   javabean para la entidad (Formulario): rrhhotxusuario
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Apr 25 11:11:52 ART 2008 
   
   Para manejar la pagina: rrhhotxusuarioFrm.jsp
      
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

public class BeanRrhhotxusuarioFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanRrhhotxusuarioFrm.class);        
 private String validar = "";
 private BigDecimal idcodigo= BigDecimal.valueOf(-1);
 private BigDecimal idordendetrabajo= BigDecimal.valueOf(0);
 private String ordendetrabajo ="";
 private String idusuario="";
 private BigDecimal idempresa;
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanRrhhotxusuarioFrm() { super();}
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
   RRHH rrhhotxusuario = Common.getRrhh();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = rrhhotxusuario.rrhhotxusuarioCreate( this.idordendetrabajo,  this.idusuario,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = rrhhotxusuario.rrhhotxusuarioUpdate( this.idcodigo,  this.idordendetrabajo,  this.idusuario,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = rrhhotxusuario.rrhhotxusuarioDelete(this.idcodigo,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosRrhhotxusuario() {
   try {
	 RRHH rrhhotxusuario = Common.getRrhh();
     List listRrhhotxusuario = rrhhotxusuario.getRrhhotxusuarioPK(this.idcodigo, this.idempresa );
     Iterator iterRrhhotxusuario = listRrhhotxusuario.iterator();
     if (iterRrhhotxusuario.hasNext()) {
        String[] uCampos = (String[]) iterRrhhotxusuario.next();
        //TODO: Constructores para cada tipo de datos
        this.idcodigo= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.idordendetrabajo= BigDecimal.valueOf(Long.parseLong(uCampos[1]));
        this.ordendetrabajo= uCampos[2];
        this.idusuario= uCampos[3];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[4]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosRrhhotxusuario()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("rrhhotxusuarioAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos


    if(idusuario.trim().length() == 0  ){
        this.mensaje = "No se puede dejar vacio el campo Usuario  ";
        return false;
     }
    
	if (ordendetrabajo.trim().length() == 0) {
		this.mensaje = "No se puede dejar vacio el campo Orden de Trabajo  ";
		return false;
	}	   


       // 2. len 0 para campos nulos
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosRrhhotxusuario();
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
 public BigDecimal getIdcodigo(){ return idcodigo; }
 public void setIdcodigo(BigDecimal idcodigo){ this.idcodigo=idcodigo; }
 public BigDecimal getIdordendetrabajo(){ return idordendetrabajo; }
 public void setIdordendetrabajo(BigDecimal idordendetrabajo){ this.idordendetrabajo=idordendetrabajo; }
 public String getIdusuario() {
	return idusuario;
}
public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public String getOrdendetrabajo() {
	return ordendetrabajo;
}
public void setOrdendetrabajo(String ordendetrabajo) {
	this.ordendetrabajo = ordendetrabajo;
}

public void setIdusuario(String idusuario) {
	this.idusuario = idusuario;
}
}






