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
 * @ejb.bean name="BeanReportesTablasFrm" display-name="Name for
 *           BeanReportesTablasFrm" description="Description for
 *           BeanReportesTablasFrm" jndi-name="ejb/BeanReportesTablasFrm"
 *           type="Stateful" view-type="remote"
 */
public class BeanReportesTablasFrm implements SessionBean, Serializable {

	/** The session context */
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanReportesTablasFrm.class);

	private BigDecimal idreporte = BigDecimal.valueOf(0);

	private List listReporte = new ArrayList();

	private String reporte = "";

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List tablasList = new ArrayList();

	private String usuarioalt = "";

	private String usuarioact = "";

	private String accion = "";

	private String ocurrencia = "";

	private String mensaje = "";

	private String agregar = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	long[] idtablas = null;

	private String volver = "";

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

	public BeanReportesTablasFrm() {
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

			if (!this.volver.equalsIgnoreCase("")) {
				dispatcher = request
						.getRequestDispatcher("reportesTablasAbm.jsp");
				dispatcher.forward(request, response);
				return true;
			}
			if (!this.agregar.equalsIgnoreCase("")) {
				if (idtablas != null) {
					this.mensaje = reporting.reporteTablaCreate(this.idreporte,
							this.idtablas, this.usuarioalt);
				} else
					this.mensaje = "Debe seleccionar alg�n tabla.";
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
				this.totalRegistros = reporting.getTotalEntidad("tablas")
						- reporting.getTotalEntidadRelacion("reportes_tablas",
								campos, valores);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.tablasList = reporting.getReportesTablasAsociar(
						this.limit, this.offset, this.idreporte);
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

	public String getAgregar() {
		return agregar;
	}

	public void setAgregar(String agregar) {
		this.agregar = agregar;
	}

	public long[] getIdtablas() {
		return idtablas;
	}

	public void setIdtablas(long[] idtablas) {
		this.idtablas = idtablas;
	}

	public String getVolver() {
		return volver;
	}

	public void setVolver(String volver) {
		this.volver = volver;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

}
