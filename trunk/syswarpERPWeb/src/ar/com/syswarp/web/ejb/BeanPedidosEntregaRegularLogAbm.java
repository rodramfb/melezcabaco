/* 
   javabean para la entidad: pedidosEntregaRegularLog
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Apr 07 14:06:19 GYT 2009 
   
   Para manejar la pagina: pedidosEntregaRegularLogAbm.jsp
      
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

public class BeanPedidosEntregaRegularLogAbm implements SessionBean,
		Serializable {

	static Logger log = Logger.getLogger(BeanPedidosEntregaRegularLogAbm.class);

	private BigDecimal idempresa = new BigDecimal(-1);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List pedidosEntregaRegularLogList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private Long idlog;

	private int anio = -1;

	private int idmes = -1;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanPedidosEntregaRegularLogAbm() {
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
		String filtro = "  WHERE anio = " + anio + " AND idmes = " + idmes;
		String entidad = ""
				+ "(SELECT l.idlog,l.idcliente, cl.razon, l.anio, m.idmes, m.mes,l.error,l.idempresa, l.usuarioalt,l.usuarioact,l.fechaalt,l.fechaact "
				+ "   FROM pedidosentregaregularlog l"
				+ "        INNER JOIN clientesclientes cl ON l.idcliente = cl.idcliente AND l.idempresa = cl.idempresa "
				+ "        INNER JOIN globalmeses m ON l.mes =  m.idmes "
				+ ") entidad ";
		Clientes clientes = Common.getClientes();
		try {

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			if (buscar) {

				filtro += " AND razon LIKE '%" + ocurrencia.toUpperCase()
						+ "%'";
				this.totalRegistros = clientes.getTotalEntidadFiltro(entidad,
						filtro, idempresa);
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
				this.pedidosEntregaRegularLogList = clientes
						.getPedidosEntregaRegularLogOcu(this.limit,
								this.offset, this.ocurrencia, this.anio,
								this.idmes, this.idempresa);

			} else {

				this.totalRegistros = clientes.getTotalEntidadFiltro(entidad,
						filtro, idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.pedidosEntregaRegularLogList = clientes
						.getPedidosEntregaRegularLogAll(this.limit,
								this.offset, this.anio, this.idmes,
								this.idempresa);

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

	public List getPedidosEntregaRegularLogList() {
		return pedidosEntregaRegularLogList;
	}

	public void setPedidosEntregaRegularLogList(
			List pedidosEntregaRegularLogList) {
		this.pedidosEntregaRegularLogList = pedidosEntregaRegularLogList;
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

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getIdmes() {
		return idmes;
	}

	public void setIdmes(int idmes) {
		this.idmes = idmes;
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
