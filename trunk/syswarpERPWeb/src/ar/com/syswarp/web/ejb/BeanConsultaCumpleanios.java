/* 
 javabean para la entidad (Formulario): Cajaferiados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
 
 Para manejar la pagina: CajaferiadosFrm.jsp
 
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

public class BeanConsultaCumpleanios implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanConsultaCumpleanios.class);

	private String validar = "";

	private int limit = 15;
	
	private long offset = 0l;
	
	private String anio = "";
	
	private String mes = "";
	
	private String des_mes = "";
	
	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanConsultaCumpleanios() {
		super();
	}

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
			General Consulta = Common.getGeneral();

			if (this.accion.equalsIgnoreCase("consulta"))
				// aca va la sentencia que me devuelva el listado

			if (this.anio == null || this.anio.equalsIgnoreCase(""))
				this.mensaje = "No se puede dejar vacio el campo Año";
				
			if (this.mes == null || this.mes.equalsIgnoreCase(""))
		    	this.mensaje = "No se puede dejar vacio el campo Mes";
			
			
			
			if (this.mes.equalsIgnoreCase("1")){
				this.mes = "01";
			}
			if (this.mes.equalsIgnoreCase("2")){
				this.mes = "02";
			}
			if (this.mes.equalsIgnoreCase("3")){
				this.mes = "03";
			}
			if (this.mes.equalsIgnoreCase("4")){
				this.mes = "04";
			}
			if (this.mes.equalsIgnoreCase("5")){
				this.mes = "05";
			}
			if (this.mes.equalsIgnoreCase("6")){
				this.mes = "06";
			}
			if (this.mes.equalsIgnoreCase("7")){
				this.mes = "07";
			}
			if (this.mes.equalsIgnoreCase("8")){
				this.mes = "08";
			}
		    if (this.mes.equalsIgnoreCase("9")){
				this.mes = "09";
			}
			
			
			
			
			this.MovimientosList = Consulta.getConsultaProcesodeCumpleanios(
					this.anio , this.mes, this.idempresa);
 
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("#"); // no tiene volver
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}
				
				this.mensaje = "";

				if (this.anio == null || this.anio.equalsIgnoreCase("")){
					this.mensaje = "No se puede dejar vacio el campo Año";
				    return false;
			   }
				if (this.mes == null || this.mes.equalsIgnoreCase("")){
					this.mensaje = "No se puede dejar vacio el campo Mes";
				    return false;
		       }
				
				this.ejecutarSentenciaDML();
			} else {
				/*
				 * if (!this.accion.equalsIgnoreCase("alta")) {
				 * getDatosCajaferiados(); }
				 */
			}
		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	public String getValidar() {
		return validar;
	}

	public void setValidar(String validar) {
		this.validar = validar;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getVolver() {
		return volver;
	}

	public void setVolver(String volver) {
		this.volver = volver;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	// metodos para cada atributo de la entidad

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}


	public List getMovimientosList() {
		return MovimientosList;
	}

	public void setMovimientosList(List movimientosList) {
		MovimientosList = movimientosList;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getDes_mes() {
		return des_mes;
	}

	public void setDes_mes(String des_mes) {
		this.des_mes = des_mes;
	}
	
}
