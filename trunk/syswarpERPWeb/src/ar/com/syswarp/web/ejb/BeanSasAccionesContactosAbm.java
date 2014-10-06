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

public class BeanSasAccionesContactosAbm implements SessionBean, Serializable {


	private static final long serialVersionUID = -3585872763024915969L;

	static Logger log = Logger.getLogger(BeanSasAccionesContactosAbm.class);

	@SuppressWarnings("unused")
	private SessionContext context;
	
	private BigDecimal idempresa = new BigDecimal(-1);
	private BigDecimal idAccionContacto = new BigDecimal(-1);

	private List<String[]> sasAccionesContactosList = new ArrayList<String[]>();

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

	public BeanSasAccionesContactosAbm() {
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

				if (idAccionContacto == null
						|| idAccionContacto.longValue() < 0) {

					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {

					this.mensaje = clientes.sasAccionesContactosDelete(
							idAccionContacto, this.idempresa);
				}
			}

			if (this.accion.equalsIgnoreCase(IAcciones.MODIFICACION)) {

				if (idAccionContacto == null
						|| idAccionContacto.longValue() < 0) {

					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {

					dispatcher = request
							.getRequestDispatcher("sasAccionesContactosFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}

			if (this.accion.equalsIgnoreCase(IAcciones.ALTA)) {

				dispatcher = request
						.getRequestDispatcher("sasAccionesContactosFrm.jsp");
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
						"  SELECT ac.* " +
						"  FROM SASACCIONESCONTACTOS ac " +
						"    inner join sasmotivoscontactos cc " +
						"      on (ac.idmotivocontacto = mc.idmotivocontacto) " +
						"    inner join sascanalescontactos cc " +
						"      on (ac.idcanalcontacto = cc.idcanalcontacto) " +
						"    inner join sastiposcontactos tc " +
						"      on (ac.idtipocontacto = tc.idtipocontacto) " +
						"  WHERE (" +
						"    UPPER(ac.accioncontacto) LIKE '%" + ocurrencia.toUpperCase().trim() + "%'" +
						"    or upper(tc.tipocontacto) like '%" + ocurrencia.toUpperCase().trim() + "%'" +
						"  ) AND ac.idempresa = " + idempresa.toString() +
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

				this.sasAccionesContactosList = clientes
						.getSasAccionesContactosOcu(this.limit, this.offset,
								this.ocurrencia, this.idempresa);

			} else {

				this.totalRegistros = clientes.getTotalEntidad(
						"sasaccionescontactos", this.idempresa);

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;

				if (this.totalPaginas < this.paginaSeleccion) {
					this.paginaSeleccion = this.totalPaginas;
				}

				this.offset = (this.paginaSeleccion - 1) * this.limit;

				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.sasAccionesContactosList = clientes
						.getSasAccionesContactosAll(this.limit, this.offset,
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

	public List<String[]> getSasAccionesContactosList() {
		return sasAccionesContactosList;
	}

	public void setSasAccionesContactosList(
			List<String[]> sasAccionesContactosList) {
		this.sasAccionesContactosList = sasAccionesContactosList;
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

	public BigDecimal getIdAccionContacto() {
		return idAccionContacto;
	}

	public void setIdAccionContacto(BigDecimal idAccionContacto) {
		this.idAccionContacto = idAccionContacto;
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
