/* 
   javabean para la entidad (Formulario): bacotmcampacabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Apr 03 14:45:20 GMT-03:00 2007 
   
   Para manejar la pagina: bacotmcampacabeFrm.jsp
      
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

public class BeanBacotmcampacabeFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanBacotmcampacabeFrm.class);        
 private String validar = "";
 private BigDecimal idcampacabe= BigDecimal.valueOf(-1);
 private BigDecimal idempresa ;
 private BigDecimal idusuario = new BigDecimal(0) ;
 private String campacabe ="";
 private Timestamp fdesde= new Timestamp(Common.initObjectTime());
 private String fdesdeStr = Common.initObjectTimeStr();
 private Timestamp fhasta= new Timestamp(Common.initObjectTime());
 private String fhastaStr = Common.initObjectTimeStr();
 private BigDecimal descuento= BigDecimal.valueOf(0);
 private BigDecimal total= BigDecimal.valueOf(0);
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanBacotmcampacabeFrm() { super();}
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
   Clientes bacotmcampacabe = Common.getClientes();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = bacotmcampacabe.bacotmcampacabeCreate( this.campacabe,  this.fdesde,  this.fhasta,  this.descuento,  this.total,  this.usuarioalt, this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = bacotmcampacabe.bacotmcampacabeUpdate( this.idcampacabe,  this.campacabe,  this.fdesde,  this.fhasta,  this.descuento,  this.total,  this.usuarioact, this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = bacotmcampacabe.bacotmcampacabeDelete(this.idcampacabe,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosBacotmcampacabe() {
   try {
	 Clientes bacotmcampacabe = Common.getClientes();
     List listBacotmcampacabe = bacotmcampacabe.getBacotmcampacabePK(this.idcampacabe,this.idempresa);
     Iterator iterBacotmcampacabe = listBacotmcampacabe.iterator();
     if (iterBacotmcampacabe.hasNext()) {
        String[] uCampos = (String[]) iterBacotmcampacabe.next();
        //TODO: Constructores para cada tipo de datos
        this.idcampacabe= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.campacabe= uCampos[1];
        this.fdesde= Timestamp.valueOf(uCampos[2]);
        this.fdesdeStr = Common.setObjectToStrOrTime(fdesde, "JSTsToStr").toString();
        this.fhasta= Timestamp.valueOf(uCampos[3]);
        this.fhastaStr = Common.setObjectToStrOrTime(fhasta, "JSTsToStr").toString();
        this.descuento= BigDecimal.valueOf(Long
				.parseLong(uCampos[4]));
        this.total= BigDecimal.valueOf(Long
				.parseLong(uCampos[5]));
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosBacotmcampacabe()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("bacotmcampacabeAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(campacabe == null ){
       this.mensaje = "No se puede dejar vacio el campo campacabe ";
       return false;
    }
    if(fdesde == null ){
       this.mensaje = "No se puede dejar vacio el campo fdesde ";
       return false;
    }
    if(fhasta == null ){
       this.mensaje = "No se puede dejar vacio el campo fhasta ";
       return false;
    }
       // 2. len 0 para campos nulos
    if(campacabe.trim().length() == 0  ){
       this.mensaje = "No se puede dejar vacio el campo Campa cabe  ";
       return false;
    }
    
	if (descuento.compareTo(new BigDecimal(0)) == 0) {
		this.mensaje = "No se puede dejar vacio el campo Descuento ";
		return false;
	}
	if (total.compareTo(new BigDecimal(0)) == 0) {
		this.mensaje = "No se puede dejar vacio el campo Total ";
		return false;
	} 
    
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosBacotmcampacabe();
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
 public BigDecimal getIdcampacabe(){ return idcampacabe; }
 public void setIdcampacabe(BigDecimal idcampacabe){ this.idcampacabe=idcampacabe; }
 public String getCampacabe(){ return campacabe; }
 public void setCampacabe(String campacabe){ this.campacabe=campacabe; }
 public Timestamp getFdesde(){ return fdesde; }
 public void setFdesde(Timestamp fdesde){ this.fdesde=fdesde; }
 public Timestamp getFhasta(){ return fhasta; }
 public void setFhasta(Timestamp fhasta){ this.fhasta=fhasta; }
 public BigDecimal getDescuento(){ return descuento; }
 public void setDescuento(BigDecimal descuento){ this.descuento=descuento; }
 public BigDecimal getTotal(){ return total; }
 public void setTotal(BigDecimal total){ this.total=total; }
 public String getUsuarioalt(){ return usuarioalt; }
 public void setUsuarioalt(String usuarioalt){ this.usuarioalt=usuarioalt; }
 public String getUsuarioact(){ return usuarioact; }
 public void setUsuarioact(String usuarioact){ this.usuarioact=usuarioact; }
 
 
	public String getFdesdeStr() {
		return fdesdeStr;
	}

	public void setFdesdeStr(String fdesdeStr) {
		this.fdesdeStr = fdesdeStr;
		this.fdesde = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fdesdeStr, "StrToJSTs");
	}
	
	public String getFhastaStr() {
		return fhastaStr;
	}

	public void setFhastaStr(String fhastaStr) {
		this.fhastaStr = fhastaStr;
		this.fhasta = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fhastaStr, "StrToJSTs");
	}
	public BigDecimal getIdempresa() {
		return idempresa;
	}
	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
	public BigDecimal getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(BigDecimal idusuario) {
		this.idusuario = idusuario;
	}
	
 
 
}






