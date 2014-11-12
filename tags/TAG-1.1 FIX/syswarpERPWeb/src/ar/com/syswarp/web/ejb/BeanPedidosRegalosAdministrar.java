/* 
   javabean para la entidad: vPedidosEstado
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Apr 16 08:42:23 GYT 2009 
   
   Para manejar la pagina: vPedidosEstadoAbm.jsp
      
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

public class BeanPedidosRegalosAdministrar implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanPedidosRegalosAdministrar.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vPedidosEstadoList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idpedido_regalos_cabe = new BigDecimal(-1);

	private BigDecimal idestado = new BigDecimal(-1);

	private String fdesde = "";

	private String fhasta = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private List estadosList = new ArrayList();

	public BeanPedidosRegalosAdministrar() {
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
		Clientes clie = Common.getClientes();
		try {

			java.sql.Date fechadesde = (java.sql.Date) Common
					.setObjectToStrOrTime(this.fdesde, "StrToJSDate");
			java.sql.Date fechahasta = (java.sql.Date) Common
					.setObjectToStrOrTime(this.fhasta, "StrToJSDate");

			if (this.accion.equalsIgnoreCase("generarentregas")) {
				dispatcher = request
						.getRequestDispatcher("pedidosRegalosEntregasAdministrar.jsp");
				dispatcher.forward(request, response);

				return true;
			}

			this.estadosList = clie.getPedidosRegalosEstadosAll(250, 0,
					this.idempresa);

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			String filtro = " WHERE  "
					+ (idestado.longValue() > 0 ? " idestado = "
							+ idestado.toString()
							: " TRUE AND tipopedido = 'R' AND idestado IN (1, 3) ");

			if (buscar) {

				filtro += " AND ( UPPER(razon) LIKE '%"
						+ this.ocurrencia.toUpperCase()
						+ "%' OR idcliente::VARCHAR LIKE '%" + this.ocurrencia
						+ "%' ) ";

				this.totalRegistros = clie.getTotalEntidadFiltro(
						"vPedidosRegalosEstado", filtro, this.idempresa);
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

				this.vPedidosEstadoList = clie.getPedidosRegalosAdministrarOcu(
						this.limit, this.offset, this.idestado,
						this.ocurrencia, this.idempresa);

			} else {
				this.totalRegistros = clie.getTotalEntidadFiltro(
						"vPedidosRegalosEstado", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.vPedidosEstadoList = clie.getPedidosRegalosAdministrarAll(
						this.limit, this.offset, this.idestado, this.idempresa);

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

	public List getVPedidosEstadoList() {
		return vPedidosEstadoList;
	}

	public void setVPedidosEstadoList(List vPedidosEstadoList) {
		this.vPedidosEstadoList = vPedidosEstadoList;
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

	public BigDecimal getIdpedido_regalos_cabe() {
		return idpedido_regalos_cabe;
	}

	public void setIdpedido_regalos_cabe(BigDecimal idpedido_regalos_cabe) {
		this.idpedido_regalos_cabe = idpedido_regalos_cabe;
	}

	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
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

	public List getEstadosList() {
		return estadosList;
	}

	public void setEstadosList(List estadosList) {
		this.estadosList = estadosList;
	}

	public String getFdesde() {
		return fdesde;
	}

	public void setFdesde(String fdesde) {
		this.fdesde = fdesde;
	}

	public String getFhasta() {
		return fhasta;
	}

	public void setFhasta(String fhasta) {
		this.fhasta = fhasta;
	}
}
