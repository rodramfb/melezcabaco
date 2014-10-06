/* 
 javabean para la entidad: produccionMovProdu
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Feb 21 13:30:17 GMT-03:00 2007 
 
 Para manejar la pagina: produccionMovProduAbm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
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

public class BeanProduccionMovProduEntreFechas implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanProduccionMovProduEntreFechas.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List produccionMovProduList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idop;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String fechaDesde = "";

	private String fechaHasta = "";

	public BeanProduccionMovProduEntreFechas() {
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
		RequestDispatcher dispatcher = null;
		Produccion produccionMovProdu = Common.getProduccion();
		try {

			java.sql.Date fDesde = null;
			java.sql.Date fHasta = null;
			
			if (this.accion.equalsIgnoreCase("consulta")) {

				if (!Common.isFechaValida(this.fechaDesde)
						|| !Common.isFormatoFecha(this.fechaDesde)) {
					this.mensaje = "Ingrese fecha desde valida.";
					return false;
				}

				if (!Common.isFechaValida(this.fechaHasta)
						|| !Common.isFormatoFecha(this.fechaHasta)) {
					this.mensaje = "Ingrese fecha hasta valida.";
					return false;
				}

				fDesde = (java.sql.Date) Common.setObjectToStrOrTime(
						this.fechaDesde, "StrToJSDate");
				fHasta = (java.sql.Date) Common.setObjectToStrOrTime(
						this.fechaHasta, "StrToJSDate");

				if (fDesde.after(fHasta)) {
					this.mensaje = "Fecha desde debe ser menor a fecha hasta.";
					return false;
				}

				if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
					if (ocurrencia.indexOf("'") >= 0) {
						this.mensaje = "Caracteres invalidos en campo busqueda.";
					} else {
						buscar = true;
					}
				}

				String filtro = " WHERE fecha_emision BETWEEN '" + fDesde
						+ "'::date AND '" + fHasta + "'::date ";

				if (buscar) {
					filtro += " AND (codigo_st LIKE '%"
							+ this.ocurrencia.toUpperCase()
							+ "%' OR idop::VARCHAR LIKE '%" + this.ocurrencia + "%') ";
				}
				
				this.totalRegistros = produccionMovProdu.getTotalEntidadFiltro(
						"produccionMovProdu", filtro, this.idempresa);

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				if (this.totalRegistros == this.limit)
					this.offset = 0;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.produccionMovProduList = produccionMovProdu
						.getProduccionMovProduFiltro(this.limit, this.offset,
								filtro, this.idempresa);

				if (this.totalRegistros < 1)
					this.mensaje = "No existen registros.";
			} else {

				this.mensaje = "Seleccionar rango de fechas.";

			}
		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
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

	public List getProduccionMovProduList() {
		return produccionMovProduList;
	}

	public void setProduccionMovProduList(List produccionMovProduList) {
		this.produccionMovProduList = produccionMovProduList;
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

	public BigDecimal getIdop() {
		return idop;
	}

	public void setIdop(BigDecimal idop) {
		this.idop = idop;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
}
