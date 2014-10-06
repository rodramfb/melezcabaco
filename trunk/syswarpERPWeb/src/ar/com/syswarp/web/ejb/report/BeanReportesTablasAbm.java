package ar.com.syswarp.web.ejb.report;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.math.*;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.com.syswarp.api.Common;
import ar.com.syswarp.ejb.Report;

/**
 * XDoclet-based session bean. The class must be declared public according to
 * the EJB specification.
 * 
 * To generate the EJB related files to this EJB: - Add Standard EJB module to
 * XDoclet project properties - Customize XDoclet configuration for your
 * appserver - Run XDoclet
 * 
 * Below are the xdoclet-related tags needed for this EJB.
 * 
 * @ejb.bean name="BeanReportesTablasAbm" display-name="Name for
 *           BeanReportesTablasAbm" description="Description for
 *           BeanReportesTablasAbm" jndi-name="ejb/BeanReportesTablasAbm"
 *           type="Stateful" view-type="remote"
 */
public class BeanReportesTablasAbm implements SessionBean, Serializable {

	/** The session context */
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanReportesTablasAbm.class);

	private BigDecimal idreporte = BigDecimal.valueOf(-1);

	private List listReporte = new ArrayList();

	private String reporte = "";

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List tablasList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idtabla = BigDecimal.valueOf(-1l);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String volver_reportes = "";

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation.
	 * 
	 * The enterprise bean instance should store the reference to the context
	 * object in an instance variable.
	 * 
	 * This method is called with no transaction context.
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error.
	 */

	public BeanReportesTablasAbm() {
		super();
		// TODO Auto-generated constructor stub
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

	/**
	 * An example business method
	 * 
	 * @ejb.interface-method view-type = "remote"
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error.
	 */

	public boolean ejecutarValidacion() {
		RequestDispatcher dispatcher = null;
		Report reporting = Common.getReport();
		try {

			if (!this.volver_reportes.equalsIgnoreCase("")) {
				dispatcher = request.getRequestDispatcher("reportesAbm.jsp");
				dispatcher.forward(request, response);
				return true;
			}

			if (this.accion.equalsIgnoreCase("baja")) {
				if (idtabla == null || idtabla.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = reporting.reporteTablaDelete(this.idreporte,
							this.idtabla);
				}

			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idtabla == null || idtabla.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {

					dispatcher = request
							.getRequestDispatcher("reportesTablasFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("reportesTablasFrm.jsp");
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

			listReporte = reporting.getReportesPK(this.idreporte.longValue());
			Iterator iterReporte = listReporte.iterator();
			if (iterReporte.hasNext()) {
				String[] uCampos = (String[]) iterReporte.next();
				this.reporte = uCampos[1];
			}
			/*
			 * POR EL MOMENTO NO SE REALIZAN BUSQUEDAS SIEMPRE ENTRA POR ELSE.
			 */
			buscar = false;
			if (buscar) {

				String[] campos = { " idtabla ", " tabla " };
				this.totalRegistros = reporting.getTotalEntidadOcu("tablas",
						campos, this.ocurrencia);
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
				this.tablasList = reporting.getTablasOcu(this.limit,
						this.offset, this.ocurrencia);
			} else {
				String[] campos = { "idreporte" };
				String[] valores = { this.idreporte + "" };
				this.totalRegistros = reporting.getTotalEntidadRelacion(
						"reportes_tablas", campos, valores);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.tablasList = reporting.getReportesTablasAll(this.limit,
						this.offset, this.idreporte);
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

	public List getTablasList() {
		return tablasList;
	}

	public void setTablasList(List tablasList) {
		this.tablasList = tablasList;
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

	public BigDecimal getIdtabla() {
		return idtabla;
	}

	public void setIdtabla(BigDecimal codigo) {
		this.idtabla = codigo;
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

	public BigDecimal getIdreporte() {
		return idreporte;
	}

	public void setIdreporte(BigDecimal idreporte) {
		this.idreporte = idreporte;
	}

	public String getReporte() {
		return reporte;
	}

	public void setReporte(String reporte) {
		this.reporte = reporte;
	}

	public String getVolver_reportes() {
		return volver_reportes;
	}

	public void setVolver_reportes(String volver_reportes) {
		this.volver_reportes = volver_reportes;
	}

}
