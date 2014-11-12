/* 
   javabean para la entidad (Formulario): TMCategoriasSocios
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 04 14:26:06 ART 2007 
   
   Para manejar la pagina: TMCategoriasSociosFrm.jsp
      
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

public class BeanTMCategoriasSociosFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanTMCategoriasSociosFrm.class);        
 private String validar = "";
 private BigDecimal idcategoriasocio = BigDecimal.valueOf(-1);
 private BigDecimal idempresa;
 private String categoriasocio ="";
 private String observaciones ="";
 private Double adidesc= Double.valueOf("0");
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanTMCategoriasSociosFrm() { super();}
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
   Clientes TMCategoriasSocios = Common.getClientes();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = TMCategoriasSocios.TMCategoriasSociosCreate( this.categoriasocio,  this.observaciones,  this.adidesc,this.idempresa,  this.usuarioalt);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = TMCategoriasSocios.TMCategoriasSociosUpdate( this.idcategoriasocio,  this.categoriasocio,  this.observaciones,  this.adidesc,this.idempresa,  this.usuarioact);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = TMCategoriasSocios.TMCategoriasSociosDelete(this.idcategoriasocio,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosTMCategoriasSocios() {
   try {
	 Clientes TMCategoriasSocios = Common.getClientes();
     List listTMCategoriasSocios = TMCategoriasSocios.getTMCategoriasSociosPK(this.idcategoriasocio,this.idempresa);
     Iterator iterTMCategoriasSocios = listTMCategoriasSocios.iterator();
     if (iterTMCategoriasSocios.hasNext()) {
        String[] uCampos = (String[]) iterTMCategoriasSocios.next();
        //TODO: Constructores para cada tipo de datos
        this.idcategoriasocio= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.categoriasocio= uCampos[1];
        this.observaciones= uCampos[2];
        this.adidesc= Double.valueOf(uCampos[3]);
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosTMCategoriasSocios()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("TMCategoriasSociosAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(categoriasocio == null ){
       this.mensaje = "No se puede dejar vacio el campo categoriasocio ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(categoriasocio.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Categoria Socio  ";
       return false;
    }
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosTMCategoriasSocios();
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
 public BigDecimal getIdcategoriasocio(){ return idcategoriasocio; }
 public void setIdcategoriasocio(BigDecimal idcategoriasocio){ this.idcategoriasocio=idcategoriasocio; }
 public String getCategoriasocio(){ return categoriasocio; }
 public void setCategoriasocio(String categoriasocio){ this.categoriasocio=categoriasocio; }
 public String getObservaciones(){ return observaciones; }
 public void setObservaciones(String observaciones){ this.observaciones=observaciones; }
 public Double getAdidesc(){ return adidesc; }
 public void setAdidesc(Double adidesc){ this.adidesc=adidesc; }
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






