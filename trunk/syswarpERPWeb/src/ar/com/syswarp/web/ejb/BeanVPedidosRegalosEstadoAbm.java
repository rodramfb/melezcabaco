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

public class BeanVPedidosRegalosEstadoAbm implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanVPedidosRegalosEstadoAbm.class);

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

	private BigDecimal idpedido_cabe;

	private BigDecimal idestado = new BigDecimal(-1);

	private String fdesde = "";
	private String fhasta = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private List estadosList = new ArrayList();

	private String tipopedido = "";

	private String filtroIdpedido = "";

	private String filtroIdclie = "";

	private String filtroSucursal = "";

	private String filtroComprob = "";

	public BeanVPedidosRegalosEstadoAbm() {
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

			if (this.accion.equalsIgnoreCase("cambiarestado")) {
				dispatcher = request
						.getRequestDispatcher("pedidosRegalosCambioEstadoFrm.jsp");
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

			String filtro = " WHERE tipopedido =  'R' "
					+ (idestado.longValue() > 0 ? " AND idestado = "
							+ idestado.toString() : "  ");

			String auxFiltro = "";

			if (!this.filtroIdpedido.equals("")) {
				auxFiltro = " AND idpedido_cabe = " + this.filtroIdpedido;
			} else {

				if (this.idestado.intValue() > 0) {

					auxFiltro += " AND idestado = " + this.idestado;

				}

				if (!this.filtroIdclie.equals("")) {

					auxFiltro += " AND idcliente = " + this.filtroIdclie;

				}

				if (!this.filtroSucursal.equals("")
						|| !this.filtroComprob.equals("")) {

					if (!Common.esEntero(this.filtroSucursal)) {
						this.mensaje = "Sucursal debe ser un valor numerico.";
					} else if (!Common.esEntero(this.filtroComprob)) {
						this.mensaje = "Comprobante debe ser un valor numerico.";
					} else {

						this.filtroSucursal = Common.strZero(
								this.filtroSucursal, 4);
						this.filtroComprob = Common.strZero(this.filtroComprob,
								8);

						auxFiltro += " AND idpedido_cabe IN  ( "

								+ " SELECT d.idpedido_regalos_cabe  "
								+ "   FROM pedidos_regalos_deta d  "
								+ "        INNER JOIN clientesremitos r ON d.idremitocliente = r.idremitocliente AND d.idempresa = r.idempresa "
								+ "  WHERE r.nrosucursal =  "
								+ Integer.parseInt(this.filtroSucursal)
								+ "    AND r.nroremitocliente =  "
								+ Integer.parseInt(this.filtroComprob) +

								" )";

					}

				}

				if (fechadesde != null || fechahasta != null) {

					if (fechadesde != null) {

						if (fechahasta != null) {

							auxFiltro += " AND fechapedido::DATE BETWEEN '"
									+ fechadesde + "'::DATE AND '" + fechahasta
									+ "'::DATE";

						} else
							this.mensaje = "Es necesario ingresar Fecha Hasta";

					} else
						this.mensaje = "Es necesario ingresar Fecha Desde";

				}

			}

			filtro += auxFiltro;

			this.totalRegistros = clie.getTotalEntidadFiltro(
					"vpedidosregalosestado", filtro, this.idempresa);
			this.totalPaginas = (this.totalRegistros / this.limit) + 1;
			if (this.totalPaginas < this.paginaSeleccion)
				this.paginaSeleccion = this.totalPaginas;
			this.offset = (this.paginaSeleccion - 1) * this.limit;
			if (this.totalRegistros == this.limit) {
				this.offset = 0;
				this.totalPaginas = 1;
			}

			this.vPedidosEstadoList = clie.getVPedidosRegalosEstadoAll(this.limit,
					this.offset, filtro, this.idempresa);

			if (this.totalRegistros < 1 && this.mensaje.equals(""))
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

	public BigDecimal getIdpedido_cabe() {
		return idpedido_cabe;
	}

	public void setIdpedido_cabe(BigDecimal idpedido_cabe) {
		this.idpedido_cabe = idpedido_cabe;
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

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

	public String getFiltroIdpedido() {
		return filtroIdpedido;
	}

	public void setFiltroIdpedido(String filtroIdpedido) {
		this.filtroIdpedido = filtroIdpedido;
	}

	public String getFiltroIdclie() {
		return filtroIdclie;
	}

	public void setFiltroIdclie(String filtroIdclie) {
		this.filtroIdclie = filtroIdclie;
	}

	public String getFiltroSucursal() {
		return filtroSucursal;
	}

	public void setFiltroSucursal(String filtroSucursal) {
		this.filtroSucursal = filtroSucursal;
	}

	public String getFiltroComprob() {
		return filtroComprob;
	}

	public void setFiltroComprob(String filtroComprob) {
		this.filtroComprob = filtroComprob;
	}

}
