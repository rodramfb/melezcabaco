/* 
 javabean para la entidad: clientesestadosclientes
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Mar 02 14:15:19 GMT-03:00 2007 
 
 Para manejar la pagina: clientesestadosclientesAbm.jsp
 
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

public class BeanClientesestadosclientesXClienteAbm implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanClientesestadosclientesXClienteAbm.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List clientesestadosclientesList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idestadocliente = new BigDecimal(-1);

	private BigDecimal idestadoclienteactual = new BigDecimal(-1);

	private BigDecimal idcliente = new BigDecimal(-1);

	private String razon = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	boolean existePedidoPentiente = true;

	public BeanClientesestadosclientesXClienteAbm() {
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

		
			this.existePedidoPentiente = clientes
					.isClienteConPedidosPendientes(this.idcliente,
							this.idempresa);

			String usuarioact = request.getSession().getAttribute("usuario")
					.toString();
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idestadocliente == null || idestadocliente.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					java.sql.Timestamp fbaja = new java.sql.Timestamp(Calendar
							.getInstance().getTime().getTime());
					this.mensaje = clientes
							.clientesEstadosClientesSetFBajaPK(this.idcliente,
									this.idestadocliente, fbaja, usuarioact,
									this.idempresa);
					if (this.mensaje.equalsIgnoreCase("OK")) {
						this.mensaje = "Baja correcta.";
					}
				}
			}
			log.info("idcliente: " + idcliente);
			this.idestadoclienteactual = clientes.getIdestadoclienteActualCliente(idcliente, idempresa);
				
			/*
			 * if (this.accion.equalsIgnoreCase("modificacion")) { if
			 * (idestadocliente == null || idestadocliente.longValue() < 0) {
			 * this.mensaje = "Debe seleccionar un registro a modificar."; }
			 * else { dispatcher = request
			 * .getRequestDispatcher("clientesestadosclientesXClienteFrm.jsp");
			 * dispatcher.forward(request, response); return true; } }
			 */

			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("clientesestadosclientesXClienteFrm.jsp");
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

			String entidad = "("
					+ "SELECT CEC.idcliente,CLI.razon,CE.estado,CM.motivo,CEC.fechadesde,CEC.idempresa"
					+ "  FROM CLIENTESESTADOSCLIENTES CEC "
					+ "       INNER JOIN clientesclientes CLI  ON (CEC.idcliente = CLI.idcliente and CEC.idempresa   = CLI.idempresa ) "
					+ "       INNER JOIN clientesestados CE ON (CEC.idestado  = CE.idestado and CEC.idempresa    = CE.idempresa)"
					+ "       INNER JOIN clientesmotivos CM ON (CEC.idmotivo  = CM.idmotivo and CEC.idempresa   = CM.idempresa )"

					+ " ) entidad ";

			String filtro = " WHERE idempresa= " + idempresa.toString()
					+ " and idcliente= " + idcliente.toString();

			if (buscar) {
				filtro += " AND(UPPER(razon) LIKE '%"
						+ ocurrencia.toUpperCase()
						+ "%' OR idcliente::VARCHAR LIKE  '%"
						+ ocurrencia.toUpperCase() + "%' )  ";

				this.totalRegistros = clientes
						.getTotalEntidadFiltro(entidad, filtro, this.idempresa);
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
				this.clientesestadosclientesList = clientes
						.getClientesestadosclientesXClienteOcu(this.idcliente,
								this.limit, this.offset, this.ocurrencia,
								this.idempresa);
			} else {

				this.totalRegistros = clientes
						.getTotalEntidadFiltro(entidad, filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.clientesestadosclientesList = clientes
						.getClientesestadosclientesXClienteAll(this.idcliente,
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

	public List getClientesestadosclientesList() {
		return clientesestadosclientesList;
	}

	public void setClientesestadosclientesList(List clientesestadosclientesList) {
		this.clientesestadosclientesList = clientesestadosclientesList;
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

	public BigDecimal getIdestadocliente() {
		return idestadocliente;
	}

	public void setIdestadocliente(BigDecimal idestadocliente) {
		this.idestadocliente = idestadocliente;
	}

	public BigDecimal getIdestadoclienteactual() {
		return idestadoclienteactual;
	}

	public void setIdestadoclienteactual(BigDecimal idestadoclienteactual) {
		this.idestadoclienteactual = idestadoclienteactual;
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

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public boolean isExistePedidoPentiente() {
		return existePedidoPentiente;
	}

	public void setExistePedidoPentiente(boolean existePedidoPentiente) {
		this.existePedidoPentiente = existePedidoPentiente;
	}

}
