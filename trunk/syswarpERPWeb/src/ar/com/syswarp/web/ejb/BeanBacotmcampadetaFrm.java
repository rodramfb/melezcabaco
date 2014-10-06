/* 
   javabean para la entidad (Formulario): bacotmcampadeta
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Apr 03 15:29:07 GMT-03:00 2007 
   
   Para manejar la pagina: bacotmcampadetaFrm.jsp
      
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

public class BeanBacotmcampadetaFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanBacotmcampadetaFrm.class);        
 private String validar = "";
 private BigDecimal idcampadeta= BigDecimal.valueOf(-1);
 private BigDecimal idcampacabe= BigDecimal.valueOf(0);
 private String campacabe ="";
 private String codigo_st ="";
 private String descrip_st ="";
 private String observaciones ="";
 private BigDecimal stock_estimado= BigDecimal.valueOf(0);
 private BigDecimal idempresa;
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanBacotmcampadetaFrm() { super();}
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
   Clientes bacotmcampadeta = Common.getClientes();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = bacotmcampadeta.bacotmcampadetaCreate( this.idcampacabe,  this.codigo_st,  this.observaciones,  this.stock_estimado,  this.usuarioalt, this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = bacotmcampadeta.bacotmcampadetaUpdate( this.idcampadeta,  this.idcampacabe,  this.codigo_st,  this.observaciones,  this.stock_estimado,  this.usuarioact,this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = bacotmcampadeta.bacotmcampadetaDelete(this.idcampadeta,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosBacotmcampadeta() {
   try {
	 Clientes bacotmcampadeta = Common.getClientes();
     List listBacotmcampadeta = bacotmcampadeta.getBacotmcampadetaPK(this.idcampadeta,this.idempresa);
     Iterator iterBacotmcampadeta = listBacotmcampadeta.iterator();
     if (iterBacotmcampadeta.hasNext()) {
        String[] uCampos = (String[]) iterBacotmcampadeta.next();
        //TODO: Constructores para cada tipo de datos
        this.idcampadeta= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.idcampacabe= BigDecimal.valueOf(Long.parseLong(uCampos[1]));
        this.codigo_st= uCampos[2];
        this.observaciones= uCampos[3];
        this.stock_estimado= BigDecimal.valueOf(Long.parseLong(uCampos[4]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosBacotmcampadeta()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("bacotmcampadetaAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(idcampacabe == null ){
       this.mensaje = "No se puede dejar vacio el campo idcampacabe ";
       return false;
    }
    if(codigo_st == null ){
       this.mensaje = "No se puede dejar vacio el campo codigo_st ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(codigo_st.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo codigo_st  ";
       return false;
    }
	if (observaciones.trim().length() == 0) {
		this.mensaje = "No se puede dejar vacio el campo Observaciones";
		return false;
	} 
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosBacotmcampadeta();
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
 public BigDecimal getIdcampadeta(){ return idcampadeta; }
 public void setIdcampadeta(BigDecimal idcampadeta){ this.idcampadeta=idcampadeta; }
 public BigDecimal getIdcampacabe(){ return idcampacabe; }
 public void setIdcampacabe(BigDecimal idcampacabe){ this.idcampacabe=idcampacabe; }
 public String getCodigo_st(){ return codigo_st; }
 public void setCodigo_st(String codigo_st){ this.codigo_st=codigo_st; }
 public String getObservaciones(){ return observaciones; }
 public void setObservaciones(String observaciones){ this.observaciones=observaciones; }
 public BigDecimal getStock_estimado(){ return stock_estimado; }
 public void setStock_estimado(BigDecimal stock_estimado){ this.stock_estimado=stock_estimado; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
public String getDescrip_st() {
	return descrip_st;
}
public void setDescrip_st(String descrip_st) {
	this.descrip_st = descrip_st;
}
public String getCampacabe() {
	return campacabe;
}
public void setCampacabe(String campacabe) {
	this.campacabe = campacabe;
}
public BigDecimal getIdempresa() {
	return idempresa;
}
public void setIdempresa(BigDecimal idempresa) {
	this.idempresa = idempresa;
}
}






