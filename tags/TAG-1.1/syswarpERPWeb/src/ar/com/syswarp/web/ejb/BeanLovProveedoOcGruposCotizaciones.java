/* 
 javabean para la entidad: proveedo_Oc_Grupos_Cotizaciones
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Mar 29 15:29:34 ART 2007 
 
 Para manejar la pagina: proveedo_Oc_Grupos_CotizacionesAbm.jsp
 
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

public class BeanLovProveedoOcGruposCotizaciones implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanLovProveedoOcGruposCotizaciones.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List proveedo_Oc_Grupos_CotizacionesList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idgrupooc;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanLovProveedoOcGruposCotizaciones() {
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
		Proveedores proveedo_Oc_Grupos_Cotizaciones = Common.getProveedores();
		String filtro = " WHERE current_date BETWEEN fechadesde and fechahasta ";
		try {

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			if (buscar) {

				filtro += " AND UPPER(grupooc) LIKE '%"
						+ ocurrencia.toUpperCase() + "%'";

				this.totalRegistros = proveedo_Oc_Grupos_Cotizaciones
						.getTotalEntidadFiltro(
								"proveedo_Oc_Grupos_Cotizaciones", filtro,
								this.idempresa);
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
				this.proveedo_Oc_Grupos_CotizacionesList = proveedo_Oc_Grupos_Cotizaciones
						.getProveedo_Oc_Grupos_CotizacionesActivasOcu(
								this.limit, this.offset, this.ocurrencia,
								this.idempresa);
			} else {
				this.totalRegistros = proveedo_Oc_Grupos_Cotizaciones
						.getTotalEntidadFiltro(
								"proveedo_Oc_Grupos_Cotizaciones", filtro,
								this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.proveedo_Oc_Grupos_CotizacionesList = proveedo_Oc_Grupos_Cotizaciones
						.getProveedo_Oc_Grupos_CotizacionesActivasAll(
								this.limit, this.offset, this.idempresa);
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

	public List getProveedo_Oc_Grupos_CotizacionesList() {
		return proveedo_Oc_Grupos_CotizacionesList;
	}

	public void setProveedo_Oc_Grupos_CotizacionesList(
			List proveedo_Oc_Grupos_CotizacionesList) {
		this.proveedo_Oc_Grupos_CotizacionesList = proveedo_Oc_Grupos_CotizacionesList;
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

	public BigDecimal getIdgrupooc() {
		return idgrupooc;
	}

	public void setIdgrupooc(BigDecimal idgrupooc) {
		this.idgrupooc = idgrupooc;
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
}
