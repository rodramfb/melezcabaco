/* 
   javabean para la entidad: pedidos_Cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Sep 04 11:02:27 GMT-03:00 2008 
   
   Para manejar la pagina: pedidos_CabeAbm.jsp
      
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

public class BeanPedidosRegalosHijos implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanPedidosRegalosHijos.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	// private BigDecimal idcliente = new BigDecimal(-1);

	// private String cliente = "";

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List pedidos_CabeList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idpedido_cabe = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanPedidosRegalosHijos() {
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
		Clientes pedidos_Cabe = Common.getClientes();
		try {

			if (this.accion.equalsIgnoreCase("verdetalle")) {
				if (idpedido_cabe.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a consultar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("pedidosHistoricoClienteDetalle.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			// String filtro = " WHERE idcliente = " + idcliente.toString();
			String filtro = " WHERE idpedido_regalos_padre IS NOT NULL  AND idpedido_regalos_cabe <> idpedido_regalos_padre ";
			String entidad = ""
					+ "(SELECT pc.idpedido_regalos_cabe, idpedido_regalos_padre, pc.idcliente, es.estado, pc.idempresa "
					+ "  FROM pedidos_regalos_cabe pc "
					+ "       INNER JOIN pedidosregalosestados es ON pc.idestado = es.idestado AND pc.idempresa = es.idempresa   ) pedidos";

			if (buscar) {
				filtro += " AND (idpedido_cabe::VARCHAR LIKE '%"
						+ this.ocurrencia + "%' OR UPPER (estado) LIKE '%"
						+ ocurrencia.toUpperCase().trim() + "%')";

				this.totalRegistros = pedidos_Cabe.getTotalEntidadFiltro(
						entidad, filtro, this.idempresa);
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
				this.pedidos_CabeList = pedidos_Cabe.getPedidosRegalosHijosOcu(
						this.limit, this.offset, this.ocurrencia,
						this.idempresa);
			} else {
				this.totalRegistros = pedidos_Cabe.getTotalEntidadFiltro(
						entidad, filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.pedidos_CabeList = pedidos_Cabe.getPedidosRegalosHijosAll(
						this.limit, this.offset, this.idempresa);
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
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

	public List getPedidos_CabeList() {
		return pedidos_CabeList;
	}

	public void setPedidos_CabeList(List pedidos_CabeList) {
		this.pedidos_CabeList = pedidos_CabeList;
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

	public BigDecimal getIdpedido_cabe() {
		return idpedido_cabe;
	}

	public void setIdpedido_cabe(BigDecimal idpedido_cabe) {
		this.idpedido_cabe = idpedido_cabe;
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
