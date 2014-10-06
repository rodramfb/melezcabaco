/* 
   javabean para la entidad (Formulario): rrhhliq_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 26 14:23:25 ART 2012 
   
   Para manejar la pagina: rrhhliq_cabeFrm.jsp
      
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.SimpleDateFormat;

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

public class BeanRrhhLiquidacionSueldosJornales implements SessionBean,
		Serializable {
	private SessionContext context;
	static Logger log = Logger
			.getLogger(BeanRrhhLiquidacionSueldosJornales.class);
	private String validar = "";

	private String desdeLegajo = "";

	private String hastaLegajo = "";

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String anioliquidacion = "";

	private String mesliquidacion = "";
	
	private String ir = "";
	
	private boolean salida = false;

	public BeanRrhhLiquidacionSueldosJornales() {
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
			RRHH rrhh = Common.getRrhh();
			log.info("AÑO: " + anioliquidacion);
			log.info("MES:" +mesliquidacion);
			String[] args = new String[] { anioliquidacion, mesliquidacion };
			salida = rrhh.liquidacionSueldoEmpleados(args, idempresa);
			/*
			 * this.mensaje = rrhh.LiquidacionSueldoXLegajo(new BigDecimal(
			 * desdeLegajo), new BigDecimal(hastaLegajo), usuarioalt,
			 * idempresa);
			 */
			if (salida)
			{
				this.mensaje = "Liquidacion para el periodo " + mesliquidacion + "  -  " + anioliquidacion + "  exitosa" ;
			}else{
				this.mensaje ="Imposible liquidar periodo";
			}
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!Common.esNumerico(anioliquidacion)
					|| !Common.esEntero(anioliquidacion)
					|| new BigDecimal(anioliquidacion).longValue() < 1) {
				
				this.mensaje = "No puede ingresar un numero invalido para el año a liquidar";
				return false;
			}
			if (!Common.esNumerico(mesliquidacion)
					|| !Common.esEntero(anioliquidacion)
					|| new BigDecimal(anioliquidacion).longValue() < 1) {
				this.mensaje = "No puede ingresar un numero invalido para el mes a liquidar";
				return false;
			}
			this.ejecutarSentenciaDML();

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

	public String getDesdeLegajo() {
		return desdeLegajo;
	}

	public void setDesdeLegajo(String desdeLegajo) {
		this.desdeLegajo = desdeLegajo;
	}

	public String getHastaLegajo() {
		return hastaLegajo;
	}

	public void setHastaLegajo(String hastaLegajo) {
		this.hastaLegajo = hastaLegajo;
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

	public String getAnioliquidacion() {
		return anioliquidacion;
	}

	public void setAnioliquidacion(String anioliquidacion) {
		this.anioliquidacion = anioliquidacion;
	}

	public String getMesliquidacion() {
		return mesliquidacion;
	}

	public void setMesliquidacion(String mesliquidacion) {
		this.mesliquidacion = mesliquidacion;
	}

	public String getIr() {
		return ir;
	}

	public void setIr(String ir) {
		this.ir = ir;
	}

	
}
