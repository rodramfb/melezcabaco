/* 
 javabean para la entidad: produccionEsquemas_Cabe
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Feb 13 09:18:39 GMT-03:00 2007 
 
 Para manejar la pagina: produccionEsquemasAbm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanProduccionCalculoNecesidadImprime implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanProduccionCalculoNecesidadImprime.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private String accion = "";

	private String ocurrencia = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String validar = "";

	private List produccionEsquemasList = new ArrayList();

	private String codigo_st = "";

	private String descrip_st = "";

	private String cantidad = "0";

	private BigDecimal idesquema = new BigDecimal(0);

	//

	private BigDecimal idop = new BigDecimal(0);

	private String fecha_prometida = null;

	private String fecha_emision = null;

	private String observaciones = "";

	private String fechaImpresion = "";

	private BigDecimal cantre_op = null;

	private String estado = "PENDIENTE";

	private BigDecimal codigo_dt = new BigDecimal(-1);

	private String descrip_dt = "";

	// 20101117-EJV-Mantis-602
	private String tipoPedido = "N";

	public BeanProduccionCalculoNecesidadImprime() {
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

	public boolean ejecutarValidacion() {

		Produccion produccionEsquemas = Common.getProduccion();

		try {

			this.fechaImpresion = Common.setObjectToStrOrTime(
					new Timestamp(Calendar.getInstance().getTimeInMillis()),
					"JSTsToStrWithHM").toString();

			this.getDatosProduccionMovProdu();

			if (this.cantre_op.compareTo(new BigDecimal(0)) > 0) {
				this.estado = "EN EJECUCION";
			}

			if (this.codigo_st.equalsIgnoreCase("")) {
				this.mensaje = "Ingrese un articulo para calcular necesidad.";
				return false;
			}

			this.cantidad = this.cantidad.trim();

			if (!Common.esNumerico(this.cantidad)) {
				this.mensaje = "Ingrese valores numericos validos para cantidad.";
				return false;
			}

			if (new BigDecimal(this.cantidad).compareTo(new BigDecimal(0)) <= 0) {
				this.mensaje = "Cantidad debe ser mayor a cero.";
				return false;
			}
			// 20101117-EJV-Mantis-602: Se agrego parametro tipoPedido
			this.produccionEsquemasList = produccionEsquemas
					.getRecursividadEsquema(this.idesquema, new BigDecimal(
							this.cantidad), 1, this.tipoPedido, this.idempresa);

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	/**/

	public void getDatosProduccionMovProdu() {
		try {
			Produccion produccionMovProdu = Common.getProduccion();
			Stock stock = Common.getStock();

			List listProduccionMovProdu = produccionMovProdu
					.getProduccionMovProduPK(this.idop, this.idempresa);
			Iterator iterProduccionMovProdu = listProduccionMovProdu.iterator();
			if (iterProduccionMovProdu.hasNext()) {
				String[] uCampos = (String[]) iterProduccionMovProdu.next();
				// TODO: Constructores para cada tipo de datos

				/*
				 * 0 mp.idop, 1 mp.idesquema, 2 ec.esquema, 3 mp.idcliente,4
				 * mp.cantre_op, 5 mp.cantest_op,6 mp.fecha_prometida, 7
				 * mp.fecha_emision, 8 mp.observaciones, 9 mp.codigo_st,10
				 * sk.descrip_st, 11 mp.idcontador,12 mp.nrointerno, 13
				 * ed.codigo_dt
				 */

				this.idop = new BigDecimal(uCampos[0]);
				this.idesquema = new BigDecimal(uCampos[1]);
				// this.esquema = uCampos[2];
				// this.idcliente = null;// uCampos[3]
				this.cantre_op = new BigDecimal(uCampos[4]);
				// this.cantest_op = uCampos[5];
				this.cantidad = uCampos[5];
				this.fecha_prometida = Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[6]), "JSDateToStr")
						.toString();
				this.fecha_emision = Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[7]), "JSDateToStr")
						.toString();
				this.observaciones = uCampos[8];
				this.codigo_st = uCampos[9];
				this.descrip_st = uCampos[10];
				// this.idcontador = new BigDecimal(uCampos[11]);
				// this.nrointerno = new BigDecimal(uCampos[12]);
				this.codigo_dt = new BigDecimal(uCampos[13]);

				listProduccionMovProdu = stock.getStockdepositosPK(
						this.codigo_dt, this.idempresa);
				iterProduccionMovProdu = listProduccionMovProdu.iterator();
				if (iterProduccionMovProdu.hasNext()) {
					uCampos = (String[]) iterProduccionMovProdu.next();
					this.descrip_dt = uCampos[1];
				}

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}

		} catch (Exception e) {
			log.error("getDatosProduccionMovProdu()" + e);
		}
	}

	/**/

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

	public long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(long total) {
		this.totalRegistros = total;
	}

	public long getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(long totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public long getPaginaSeleccion() {
		return paginaSeleccion;
	}

	public void setPaginaSeleccion(long paginaSeleccion) {
		this.paginaSeleccion = paginaSeleccion;
	}

	public List getProduccionEsquemasList() {
		return produccionEsquemasList;
	}

	public void setProduccionEsquemasList(List produccionEsquemasList) {
		this.produccionEsquemasList = produccionEsquemasList;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String buscar) {
		this.ocurrencia = buscar;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getValidar() {
		return validar;
	}

	public void setValidar(String validar) {
		this.validar = validar;
	}

	public BigDecimal getIdesquema() {
		return idesquema;
	}

	public void setIdesquema(BigDecimal idesquema) {
		this.idesquema = idesquema;
	}

	public BigDecimal getIdop() {
		return idop;
	}

	public void setIdop(BigDecimal idop) {
		this.idop = idop;
	}

	public String getFecha_emision() {
		return fecha_emision;
	}

	public String getFecha_prometida() {
		return fecha_prometida;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public String getFechaImpresion() {
		return fechaImpresion;
	}

	public String getEstado() {
		return estado;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getDescrip_dt() {
		return descrip_dt;
	}

	public void setDescrip_dt(String descrip_dt) {
		this.descrip_dt = descrip_dt;
	}

	// 20101117-EJV-Mantis-602 -->
	public String getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}
	// <--

}
