/* 
 javabean para la entidad: pedidos_deta
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Mar 29 16:53:13 GMT-03:00 2007 
 
 Para manejar la pagina: pedidos_detaDetalleAbm.jsp
 
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

public class BeanPedidos_detaDetalleAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanPedidos_detaDetalleAbm.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List pedidos_detaList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idpedido_deta;

	private String idpedido_cabe = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanPedidos_detaDetalleAbm() {
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
		Clientes pedidos_deta = Common.getClientes();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idpedido_deta == null || idpedido_deta.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = pedidos_deta
							.pedidos_detaDetalleDelete(idpedido_deta);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idpedido_deta == null || idpedido_deta.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("pedidos_detaDetalleFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("pedidos_detaDetalleFrm.jsp");
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
				String[] campos = { "codigo_st" };
				this.totalRegistros = pedidos_deta
						.getPedidos_detaDetalleOcuTotal(this.idpedido_cabe,
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
				this.pedidos_detaList = pedidos_deta.getPedidos_detaDetalleOcu(
						this.idpedido_cabe, this.limit, this.offset,
						this.ocurrencia);
			} else {
				this.totalRegistros = pedidos_deta
						.getPedidos_detaDetalleAllTotal(this.idpedido_cabe);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.pedidos_detaList = pedidos_deta.getPedidos_detaDetalleAll(
						this.idpedido_cabe, this.limit, this.offset);
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

	public List getPedidos_detaList() {
		return pedidos_detaList;
	}

	public void setPedidos_detaList(List pedidos_detaList) {
		this.pedidos_detaList = pedidos_detaList;
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

	public BigDecimal getIdpedido_deta() {
		return idpedido_deta;
	}

	public void setIdpedido_deta(BigDecimal idpedido_deta) {
		this.idpedido_deta = idpedido_deta;
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

	public String getIdpedido_cabe() {
		return idpedido_cabe;
	}

	public void setIdpedido_cabe(String idpedido_cabe) {
		this.idpedido_cabe = idpedido_cabe;
	}
}
