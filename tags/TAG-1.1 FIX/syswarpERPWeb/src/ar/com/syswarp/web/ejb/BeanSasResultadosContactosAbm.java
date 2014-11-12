package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.com.syswarp.api.Common;
import ar.com.syswarp.api.IAcciones;
import ar.com.syswarp.ejb.Clientes;

public class BeanSasResultadosContactosAbm implements SessionBean, Serializable {

	private static final long serialVersionUID = 7106185536541099468L;

	static Logger log = Logger.getLogger(BeanSasResultadosContactosAbm.class);

	@SuppressWarnings("unused")
	private SessionContext context;
	
	private BigDecimal idempresa = new BigDecimal(-1);
	private BigDecimal idResultadoContacto = new BigDecimal(-1);

	private List<String[]> sasResultadosContactosList = new ArrayList<String[]>();

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

	boolean buscar = false;

	public BeanSasResultadosContactosAbm() {
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
		Clientes clientes = Common.getClientes();

		try {

			if (this.accion.equalsIgnoreCase(IAcciones.BAJA)) {

				if (idResultadoContacto == null
						|| idResultadoContacto.longValue() < 0) {

					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {

					this.mensaje = clientes.sasResultadosContactosDelete(
							idResultadoContacto, this.idempresa);
				}
			}

			if (this.accion.equalsIgnoreCase(IAcciones.MODIFICACION)) {

				if (idResultadoContacto == null
						|| idResultadoContacto.longValue() < 0) {

					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {

					dispatcher = request
							.getRequestDispatcher("sasResultadosContactosFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}

			if (this.accion.equalsIgnoreCase(IAcciones.ALTA)) {

				dispatcher = request
						.getRequestDispatcher("sasResultadosContactosFrm.jsp");
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

			if (buscar) {

				String entidad = "" +
						" ( " +
						"  SELECT rc.* " +
						"  FROM SASRESULTADOSCONTACTOS rc " +
						"    inner join sasaccionescontactos ac " +
						"      on (rc.idaccioncontacto = ac.idaccioncontacto) " +
						"    inner join sasmotivoscontactos cc " +
						"      on (rc.idmotivocontacto = mc.idmotivocontacto) " +
						"    inner join sascanalescontactos cc " +
						"      on (rc.idcanalcontacto = cc.idcanalcontacto) " +
						"    inner join sastiposcontactos tc " +
						"      on (rc.idtipocontacto = tc.idtipocontacto) " +
						"  WHERE (" +
						"    UPPER(rc.resultadocontacto) LIKE '%" + ocurrencia.toUpperCase().trim() + "%'" +
						"    or upper(tc.tipocontacto) like '%" + ocurrencia.toUpperCase().trim() + "%'" +
						"  ) AND rc.idempresa = " + idempresa.toString() +
						" ) entidad ";
				String filtro = " WHERE 1 = 1 ";

				this.totalRegistros = clientes.getTotalEntidadFiltro(entidad,
						filtro, idempresa);

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;

				if (this.totalPaginas < this.paginaSeleccion) {
					this.paginaSeleccion = this.totalPaginas;
				}

				if (this.totalRegistros == this.limit) {
					this.offset = 0;
				}

				this.offset = (this.paginaSeleccion - 1) * this.limit;

				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.sasResultadosContactosList = clientes
						.getSasResultadosContactosOcu(this.limit, this.offset,
								this.ocurrencia, this.idempresa);

			} else {

				this.totalRegistros = clientes.getTotalEntidad(
						"sasresultadoscontactos", this.idempresa);

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;

				if (this.totalPaginas < this.paginaSeleccion) {
					this.paginaSeleccion = this.totalPaginas;
				}

				this.offset = (this.paginaSeleccion - 1) * this.limit;

				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.sasResultadosContactosList = clientes
						.getSasResultadosContactosAll(this.limit, this.offset,
								this.idempresa);
			}

			if (this.totalRegistros < 1) {
				this.mensaje = "No existen registros.";
			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}

		return true;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
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

	public List<String[]> getSasResultadosContactosList() {
		return sasResultadosContactosList;
	}

	public void setSasResultadosContactosList(
			List<String[]> sasResultadosContactosList) {
		this.sasResultadosContactosList = sasResultadosContactosList;
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

	public BigDecimal getIdResultadoContacto() {
		return idResultadoContacto;
	}

	public void setIdResultadoContacto(BigDecimal idResultadoContacto) {
		this.idResultadoContacto = idResultadoContacto;
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
}
