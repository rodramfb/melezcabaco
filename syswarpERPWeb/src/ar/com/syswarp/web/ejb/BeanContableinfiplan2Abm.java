/* 
   javabean para la entidad: contableinfiplan
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 08 10:21:10 ART 2008 
   
   Para manejar la pagina: contableinfiplanAbm.jsp
      
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

public class BeanContableinfiplan2Abm implements SessionBean, Serializable {

	/*
	 * EJV - 2009-06-08 / <<< Por falta de BeanContableInfiPlanAbm se modifico
	 * este, y se utiliza desde ../Clientes/lov_contableInfiPlan.jsp Se agrego
	 * propiedad campos. Se inicializo variables nulas. >>>
	 * 
	 */

	static Logger log = Logger.getLogger(BeanContableinfiplan2Abm.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List contableinfiplanList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal ejercicio = new BigDecimal(-1);

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal idcuenta = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	// 20090608 - EJV

	private String campos = "";

	public BeanContableinfiplan2Abm() {
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
		Contable contableinfiplan = Common.getContable();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idcuenta == null
						|| idcuenta.compareTo(new BigDecimal(0)) == 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = contableinfiplan.contableinfiplanDelete(
							this.ejercicio, this.idcuenta, this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idcuenta == null
						|| idcuenta.compareTo(new BigDecimal(0)) == 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("contableinfiplan2Frm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("contableinfiplan2Frm.jsp");
				dispatcher.forward(request, response);
				return true;
			}
			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			String filtro = " WHERE ejercicio = " + this.ejercicio;
			if (buscar) {
				filtro += " AND (idcuenta::VARCHAR LIKE '%"
						+ this.ocurrencia.toUpperCase()
						+ "%' OR UPPER(cuenta) LIKE '%"
						+ this.ocurrencia.toUpperCase() + "%') ";
				this.totalRegistros = contableinfiplan.getTotalEntidadFiltro(
						"contableinfiplan", filtro, this.idempresa);
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
				this.contableinfiplanList = contableinfiplan
						.getContableinfiplanOcu(this.limit, this.offset,
								this.ejercicio, this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = contableinfiplan.getTotalEntidadFiltro(
						"contableinfiplan", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.contableinfiplanList = contableinfiplan
						.getContableinfiplanAll(this.limit, this.offset,
								this.ejercicio, this.idempresa);
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
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

	public List getContableinfiplanList() {
		return contableinfiplanList;
	}

	public void setContableinfiplanList(List contableinfiplanList) {
		this.contableinfiplanList = contableinfiplanList;
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

	public BigDecimal getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(BigDecimal ejercicio) {
		this.ejercicio = ejercicio;
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

	public BigDecimal getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(BigDecimal idcuenta) {
		this.idcuenta = idcuenta;
	}

	public String getCampos() {
		return campos;
	}

	public void setCampos(String campos) {
		this.campos = campos;
	}

}
