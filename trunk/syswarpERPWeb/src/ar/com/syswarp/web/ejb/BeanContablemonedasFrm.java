/* 
   javabean para la entidad (Formulario): contablemonedas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 15 12:14:05 ART 2008 
   
   Para manejar la pagina: contablemonedasFrm.jsp
      
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

public class BeanContablemonedasFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanContablemonedasFrm.class);        
 private String validar = "";
 private BigDecimal idmoneda= BigDecimal.valueOf(-1);
 private BigDecimal idempresa = BigDecimal.valueOf(0);
 private String moneda ="";
 private Timestamp hasta_mo = new Timestamp(Common.initObjectTime());
 private String hasta_moStr = Common.initObjectTimeStr();
 private BigDecimal ceros_mo= BigDecimal.valueOf(0);
 private String detalle ="";
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanContablemonedasFrm() { super();}
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
   Contable contablemonedas = Common.getContable();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = contablemonedas.contablemonedasCreate( this.moneda,  this.hasta_mo,  this.ceros_mo,  this.detalle,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = contablemonedas.contablemonedasUpdate( this.idmoneda,  this.moneda,  this.hasta_mo,  this.ceros_mo,  this.detalle,  this.usuarioact,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = contablemonedas.contablemonedasDelete(this.idmoneda,  this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosContablemonedas() {
   try {
     Contable contablemonedas = Common.getContable();
     List listContablemonedas = contablemonedas.getContablemonedasPK(this.idmoneda, this.idempresa );
     Iterator iterContablemonedas = listContablemonedas.iterator();
     if (iterContablemonedas.hasNext()) {
        String[] uCampos = (String[]) iterContablemonedas.next();
        //TODO: Constructores para cada tipo de datos
        this.idmoneda= BigDecimal.valueOf(Long
				.parseLong(uCampos[0]));
        this.moneda= uCampos[1];
        this.hasta_mo= Timestamp.valueOf(uCampos[2]);
        this.hasta_moStr = Common.setObjectToStrOrTime(hasta_mo,
		"JSTsToStr").toString();
        this.ceros_mo= BigDecimal.valueOf(Long
				.parseLong(uCampos[3]));
        this.detalle= uCampos[4];
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosContablemonedas()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("contablemonedasAbm.jsp");
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
      getDatosContablemonedas();
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
 public BigDecimal getIdmoneda(){ return idmoneda; }
 public void setIdmoneda(BigDecimal idmoneda){ this.idmoneda=idmoneda; }
 public String getMoneda(){ return moneda; }
 public void setMoneda(String moneda){ this.moneda=moneda; }
 public Timestamp getHasta_mo(){ return hasta_mo; }
 public void setHasta_mo(Timestamp hasta_mo){ this.hasta_mo=hasta_mo; }
 public BigDecimal getCeros_mo(){ return ceros_mo; }
 public void setCeros_mo(BigDecimal ceros_mo){ this.ceros_mo=ceros_mo; }
 public String getDetalle(){ return detalle; }
 public void setDetalle(String detalle){ this.detalle=detalle; }
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





public String getHasta_moStr() {
	return hasta_moStr;
}

public void setHasta_moStr(String hasta_moStr) {
	this.hasta_moStr = hasta_moStr;
	this.hasta_mo = (java.sql.Timestamp) Common.setObjectToStrOrTime(
			hasta_moStr, "StrToJSTs");
}




}






