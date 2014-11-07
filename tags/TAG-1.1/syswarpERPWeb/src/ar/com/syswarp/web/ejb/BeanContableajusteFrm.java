/* 
   javabean para la entidad (Formulario): contableajuste
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 08 14:36:00 ART 2008 
   
   Para manejar la pagina: contableajusteFrm.jsp
      
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

public class BeanContableajusteFrm implements SessionBean, Serializable {
 private SessionContext context;
 static Logger log = Logger.getLogger(BeanContableajusteFrm.class);        
 private String validar = "";
 private BigDecimal cod_ajuste= BigDecimal.valueOf(-1);
 private BigDecimal idempresa= BigDecimal.valueOf(0);
 private Integer anio= Integer.valueOf("0");
 private String mes= "";
 private String des_mes = "";
 private Double indice= Double.valueOf("0");
 private String usuarioalt;
 private String usuarioact;
 private String mensaje = "";
 private String volver = "";
 private HttpServletRequest request;
 private HttpServletResponse response;
 private String accion = "";
 public BeanContableajusteFrm() { super();}
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
   Contable contableajuste = Common.getContable();
   if (this.accion.equalsIgnoreCase("alta")) 
      this.mensaje = contableajuste.contableajusteCreate( this.anio,  this.mes,  this.indice,  this.usuarioalt,  this.idempresa);
   else if (this.accion.equalsIgnoreCase("modificacion"))
      this.mensaje = contableajuste.contableajusteUpdate( this.cod_ajuste,  this.anio,  this.mes,  this.indice,  this.usuarioact,this.idempresa);
   else if (this.accion.equalsIgnoreCase("baja"))
      this.mensaje = contableajuste.contableajusteDelete(this.cod_ajuste,this.idempresa);
  } catch (Exception ex) {
    log.error(" ejecutarSentenciaDML() : " + ex);
  }
 }

 public void getDatosContableajuste() {
   try {
     Contable contableajuste = Common.getContable();
     List listContableajuste = contableajuste.getContableajustePK(this.cod_ajuste, this.idempresa );
     Iterator iterContableajuste = listContableajuste.iterator();
     if (iterContableajuste.hasNext()) {
        String[] uCampos = (String[]) iterContableajuste.next();
        //TODO: Constructores para cada tipo de datos
        this.cod_ajuste= BigDecimal.valueOf(Long.parseLong(uCampos[0]));
        this.anio = Integer.valueOf(uCampos[1]);
        this.mes= uCampos[2];
        this.des_mes= uCampos[3];
        this.indice= Double.valueOf(uCampos[4]);
      } else {
         this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
      }
   } catch (Exception e) {
       log.error("getDatosContableajuste()" + e);
   }
 }

 public boolean ejecutarValidacion() {
  try {
    if (!this.volver.equalsIgnoreCase("")) {
       response.sendRedirect("contableajusteAbm.jsp");
       return true;
    }
    if (!this.validar.equalsIgnoreCase("")) {
       if (!this.accion.equalsIgnoreCase("baja")) {
       // 1. nulidad de campos
    if(anio == null ){
       this.mensaje = "No se puede dejar vacio el campo Año ";
       return false;
    }
    if(mes == null ){
       this.mensaje = "No se puede dejar vacio el campo mes ";
       return false;
    }
	if (mes.trim().length() == 0) {
		this.mensaje = "No se puede dejar vacio el campo Mes  ";
		return false;
	}
      
       // 2. len 0 para campos nulos
   }
   this.ejecutarSentenciaDML();
   } else {
   if (!this.accion.equalsIgnoreCase("alta")) {
      getDatosContableajuste();
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
 public BigDecimal getCod_ajuste(){ return cod_ajuste; }
 public void setCod_ajuste(BigDecimal cod_ajuste){ this.cod_ajuste=cod_ajuste; }
 public Integer getAnio(){ return anio; }
 public void setAnio(Integer anio){ this.anio=anio; }
 public Double getIndice(){ return indice; }
 public void setIndice(Double indice){ this.indice=indice; }
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
public String getDes_mes() {
	return des_mes;
}
public void setDes_mes(String des_mes) {
	this.des_mes = des_mes;
}
public void setMes(String mes) {
	this.mes = mes;
}
public String getMes() {
	return mes;
}
}






