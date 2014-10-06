/* 
 javabean para la entidad (Formulario): produccionEsquemas_Cabe
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Feb 13 09:18:39 GMT-03:00 2007 
 
 Para manejar la pagina: produccionEsquemas_CabeFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanINTERFACESProduccionAnulaOrden implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanINTERFACESProduccionAnulaOrden.class);

	private String validar = "";

	private BigDecimal idesquema = new BigDecimal(0);

	private String esquema = "";

	private String observaciones = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	private String accion = "";

	/**
	 * DATOS DETALLE
	 */

	private String codigo_st = "";

	private String descrip_st = "";

	private String cantidad = "";

	private BigDecimal codigo_dt = new BigDecimal(-1);

	private String descrip_dt = "";

	private int recursivo = 0;

	private BigDecimal idmotivodesarma = new BigDecimal(-1);

	private List produccionEsquemas_DetaList = null;

	private List stockMotivosDesarma = null;

	private BigDecimal idcontadorcomprobante = new BigDecimal(-1);

	private String sistema_ms = "U";

	private int ejercicioactivo = 0;

	private BigDecimal idop = new BigDecimal(-1);

	private boolean anulada = false;

	/**
	 * */

	public BeanINTERFACESProduccionAnulaOrden() {
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
			String[] resultado = null;
			BC bc = Common.getBc();
			if (this.accion.equalsIgnoreCase("desarmar")) {

				this.recursivo = 0;
				this.observaciones = "[INTERFACES] - ORDEN: " + this.idop + " / " + this.observaciones;
				resultado = bc.callINTFAnulaOrdenProduccion(this.idop,
						this.idesquema, this.codigo_st, this.codigo_dt,
						new BigDecimal(this.cantidad), this.idmotivodesarma,
						this.observaciones, this.recursivo,
						this.ejercicioactivo, this.idcontadorcomprobante,
						this.sistema_ms, this.idempresa, this.usuarioalt);

				this.mensaje = resultado[0].equalsIgnoreCase("OK") ? "Orden Anulada correctamente."
						: resultado[0];

			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	/*
	 * 
	 * public void getDatosProduccionEsquemas_Cabe() { try { Produccion
	 * produccionEsquemas = Common.getProduccion(); List listProduccionEsquemas =
	 * produccionEsquemas .getProduccionEsquemas_CabePK(this.idesquema,
	 * this.idempresa); Iterator iterProduccionEsquemas =
	 * listProduccionEsquemas.iterator(); if (iterProduccionEsquemas.hasNext()) {
	 * String[] uCampos = (String[]) iterProduccionEsquemas.next(); // TODO:
	 * Constructores para cada tipo de datos } else { this.mensaje = "Imposible
	 * recuperar datos para el registro seleccionado."; } } catch (Exception e) {
	 * log.error("getDatosProduccionEsquemas()" + e); } }
	 * 
	 */

	private void getDatosOrden() {
		try {
			Produccion prod = Common.getProduccion();
			List listDatosOrden = prod.getOrdenProduccionAnular(this.idop,
					this.idempresa);

			if (listDatosOrden != null && !listDatosOrden.isEmpty()) {

				String[] datos = (String[]) listDatosOrden.get(0);
				this.idesquema = new BigDecimal(datos[1]);
				this.esquema = datos[2];
				this.codigo_st = datos[3];
				this.descrip_st = datos[4];
				this.codigo_dt = new BigDecimal(datos[5]);
				this.descrip_dt = datos[6];
				this.cantidad = datos[7];
				this.anulada = datos[9] == null ? false : true;
				log.info("datos[9]: " + datos[9]);
				log.info("anulada : " + this.anulada);

			}

		} catch (Exception e) {
			log.error("getDatosOrden: " + e);
		}

	}

	public boolean ejecutarValidacion() {
		try {

			Stock stock = Common.getStock();

			this.getDatosOrden();

			this.stockMotivosDesarma = stock.getStockMotivosDesarmaAll(100, 0,
					this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {

				if (this.anulada) {
					this.mensaje = "La orden ya fue anulada.";
					return false;
				}

				// 1. nulidad de campos
				if (esquema == null) {
					this.mensaje = "No se puede dejar vacio el campo esquema ";
					return false;
				}
				// 2. len 0 para campos nulos
				if (esquema.trim().length() == 0) {
					this.mensaje = "No se puede dejar con longitud 0 el campo esquema  ";
					return false;
				}

				if (this.codigo_st == null || this.codigo_st.trim().equals("")) {
					this.mensaje = "Es necesario seleccionar artículo / producto.  ";
					return false;
				}

				if (this.codigo_dt == null || this.codigo_dt.longValue() < 0) {
					this.mensaje = "Es necesario seleccionar depósito.  ";
					return false;
				}

				if (!Common.esNumerico(this.cantidad)) {
					this.mensaje = "Ingrese valores validos para cantidad.";
					return false;
				}

				
				/*
				if (Double.parseDouble(this.cantidad) <= 0) {
					this.mensaje = "Cantidad debe ser mayor a cero (0).";
					return false;
				}
				*/

				if (this.idmotivodesarma.longValue() < 0) {
					this.mensaje = "Es necesario seleccionar un motivo.";
					return false;
				}

				this.ejecutarSentenciaDML();
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
	public BigDecimal getIdesquema() {
		return idesquema;
	}

	public void setIdesquema(BigDecimal idesquema) {
		this.idesquema = idesquema;
	}

	public String getEsquema() {
		return esquema;
	}

	public void setEsquema(String esquema) {
		this.esquema = esquema;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

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

	/**
	 * DATOS DETALLE
	 */

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public List getProduccionEsquemas_DetaList() {
		return produccionEsquemas_DetaList;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpSession getSession() {
		return session;
	}

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getDescrip_dt() {
		return descrip_dt;
	}

	public void setDescrip_dt(String descrip_dt) {
		this.descrip_dt = descrip_dt;
	}

	public int getRecursivo() {
		return recursivo;
	}

	public void setRecursivo(int recursivo) {
		this.recursivo = recursivo;
	}

	public List getStockMotivosDesarma() {
		return stockMotivosDesarma;
	}

	public void setStockMotivosDesarma(List stockMotivosDesarma) {
		this.stockMotivosDesarma = stockMotivosDesarma;
	}

	public BigDecimal getIdmotivodesarma() {
		return idmotivodesarma;
	}

	public void setIdmotivodesarma(BigDecimal idmotivodesarma) {
		this.idmotivodesarma = idmotivodesarma;
	}

	public BigDecimal getIdcontadorcomprobante() {
		return idcontadorcomprobante;
	}

	public void setIdcontadorcomprobante(BigDecimal idcontadorcomprobante) {
		this.idcontadorcomprobante = idcontadorcomprobante;
	}

	public int getEjercicioactivo() {
		return ejercicioactivo;
	}

	public void setEjercicioactivo(int ejercicioactivo) {
		this.ejercicioactivo = ejercicioactivo;
	}

	public BigDecimal getIdop() {
		return idop;
	}

	public void setIdop(BigDecimal idop) {
		this.idop = idop;
	}

	public boolean isAnulada() {
		return anulada;
	}

	public void setAnulada(boolean anulada) {
		this.anulada = anulada;
	}

}
