/* 
   javabean para la entidad: pedidosAnulacionRemitosLog
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Feb 23 17:03:04 GMT-03:00 2010 
   
   Para manejar la pagina: pedidosAnulacionRemitosLogAbm.jsp
      
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

public class BeanPedidosAnulacionRemitosLogAbm implements SessionBean,
		Serializable {

	static Logger log = Logger
			.getLogger(BeanPedidosAnulacionRemitosLogAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List pedidosAnulacionRemitosLogList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private Long idlog;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String tipopedido = "N";

	public BeanPedidosAnulacionRemitosLogAbm() {
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

		Clientes pedidosAnulacionRemitosLog = Common.getClientes();
		String entidad = "";
		String filtro = " WHERE TRUE ";
		if (tipopedido.equalsIgnoreCase("N"))
			entidad = "("
					+ "SELECT lg.idlog, lg.idtransaccion, lg.idpedido, cr.nrosucursal, cr.nroremitocliente, "
					+ "       cl.idcliente, cl.razon, lg.tipopedido, lg.usuarioalt, lg.fechaalt, cr.idempresa "
					+ "  FROM clientesremitos cr "
					+ "       INNER JOIN pedidosanulacionremitoslog lg ON cr.idremitocliente = lg.idremitocliente AND cr.idempresa = lg.idempresa "
					+ "       INNER JOIN pedidos_cabe pc ON lg.idpedido = pc.idpedido_cabe AND lg.idempresa = pc.idempresa "
					+ "       INNER JOIN clientesclientes cl ON pc.idcliente = cl.idcliente AND pc.idempresa = cl.idempresa )entidad ";

		else if (tipopedido.equalsIgnoreCase("R"))
			entidad = "("
					+ "SELECT lg.idlog, lg.idtransaccion, lg.idpedido, cr.nrosucursal, cr.nroremitocliente, "
					+ "       cl.idcliente, cl.razon, lg.tipopedido, lg.usuarioalt, lg.fechaalt, cr.idempresa "
					+ "  FROM clientesremitos cr "
					+ "       INNER JOIN pedidosanulacionremitoslog lg ON cr.idremitocliente = lg.idremitocliente AND cr.idempresa = lg.idempresa "
					+ "       INNER JOIN pedidos_regalos_entregas_cabe pc ON lg.idpedido = pc.idpedido_regalos_entrega_cabe AND lg.idempresa = pc.idempresa "
					+ "       INNER JOIN clientesclientes cl ON pc.idcliente = cl.idcliente AND pc.idempresa = cl.idempresa ) entidad ";

		try {

			if (this.accion.equalsIgnoreCase("alta")) {

				response.sendRedirect("clientesRemitosAnular.jsp");
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

				filtro += "  AND (idcliente::varchar = '"
						+ ocurrencia.toUpperCase().trim()
						+ "' OR nroremitocliente::varchar = '"
						+ ocurrencia.toUpperCase().trim()
						+ "' OR idpedido::varchar = '"
						+ ocurrencia.toUpperCase().trim()
						+ "') AND tipopedido = '" + tipopedido.toUpperCase()
						+ "'  ";

				this.totalRegistros = pedidosAnulacionRemitosLog
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
				this.pedidosAnulacionRemitosLogList = pedidosAnulacionRemitosLog
						.getPedidosAnulacionRemitosLogOcu(this.limit,
								this.offset, this.tipopedido, this.ocurrencia,
								this.idempresa);

			} else {

				this.totalRegistros = pedidosAnulacionRemitosLog
						.getTotalEntidadFiltro(entidad, filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.pedidosAnulacionRemitosLogList = pedidosAnulacionRemitosLog
						.getPedidosAnulacionRemitosLogAll(this.limit,
								this.offset, this.tipopedido, this.idempresa);

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

	public List getPedidosAnulacionRemitosLogList() {
		return pedidosAnulacionRemitosLogList;
	}

	public void setPedidosAnulacionRemitosLogList(
			List pedidosAnulacionRemitosLogList) {
		this.pedidosAnulacionRemitosLogList = pedidosAnulacionRemitosLogList;
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

	public Long getIdlog() {
		return idlog;
	}

	public void setIdlog(Long idlog) {
		this.idlog = idlog;
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

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

}
