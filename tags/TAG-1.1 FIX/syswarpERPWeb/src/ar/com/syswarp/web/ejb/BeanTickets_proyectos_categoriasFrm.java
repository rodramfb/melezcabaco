/* 
   javabean para la entidad (Formulario): tickets_proyectos_categorias
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Feb 22 12:03:02 ART 2008 
   
   Para manejar la pagina: tickets_proyectos_categoriasFrm.jsp
      
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

public class BeanTickets_proyectos_categoriasFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanTickets_proyectos_categoriasFrm.class);        
 private String validar = "";
 private BigDecimal idproyectocat= BigDecimal.valueOf(-1);
 private String proyectocat="";
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanTickets_proyectos_categoriasFrm() { super();}
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
   General tickets_proyectos_categorias = Common.getGeneral();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = tickets_proyectos_categorias.tickets_proyectos_categoriasCreate( this.proyectocat,  this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = tickets_proyectos_categorias.tickets_proyectos_categoriasUpdate( this.idproyectocat,  this.proyectocat,  this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = tickets_proyectos_categorias.tickets_proyectos_categoriasDelete(this.idproyectocat,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosTickets_proyectos_categorias() {
   try {
	 General tickets_proyectos_categorias = Common.getGeneral();
     List listTickets_proyectos_categorias = tickets_proyectos_categorias.getTickets_proyectos_categoriasPK(this.idproyectocat, this.idempresa );
     Iterator iterTickets_proyectos_categorias = listTickets_proyectos_categorias.iterator();
     if (iterTickets_proyectos_categorias.hasNext()) {
        String[] uCampos = (String[]) iterTickets_proyectos_categorias.next();
        //TODO: Constructores para cada tipo de datos
        this.idproyectocat= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.proyectocat= uCampos[1];
        this.idempresa= BigDecimal.valueOf(Long.parseLong(uCampos[2]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosTickets_proyectos_categorias()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("tickets_proyectos_categoriasAbm.jsp");
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
      getDatosTickets_proyectos_categorias();
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
 public String getProyectocat(){ return proyectocat; }
 public void setProyectocat(String proyectocat){ this.proyectocat=proyectocat; }
 public BigDecimal getIdempresa(){ return idempresa; }
 public void setIdempresa(BigDecimal idempresa){ this.idempresa=idempresa; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public void setIdproyectocat(BigDecimal idproyectocat) {
	this.idproyectocat = idproyectocat;
}
public BigDecimal getIdproyectocat() {
	return idproyectocat;
}
}






