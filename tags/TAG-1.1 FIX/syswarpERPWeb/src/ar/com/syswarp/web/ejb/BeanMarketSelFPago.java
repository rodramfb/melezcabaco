/* 
 javabean para la entidad: marketformasdepago
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Mar 11 13:29:00 ART 2008 
 
 Para manejar la pagina: marketSelFPago.jsp
 
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

public class BeanMarketSelFPago implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanMarketSelFPago.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List marketformasdepagoList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idformapago = new BigDecimal(-1);

	private BigDecimal idempresa = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String generarPedido = "";

	private String email = "";

	private String pass = "";

	private BigDecimal total = new BigDecimal(0);

	private BigDecimal idcliente = new BigDecimal(-1);

	private String comentarios = "";

	private String obsentrega = "";

	private String usuarioalt = "";

	private Hashtable htCarrito = new Hashtable();

	public BeanMarketSelFPago() {
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

	public void ejecutarSentenciaDML() {
		try {
			Stock marketRegistroDireccion = Common.getStock();

			Hashtable htDireccion = (Hashtable) request.getSession()
					.getAttribute("htDireccion");

			Hashtable htVarios = (Hashtable) request.getSession().getAttribute(
					"htVarios");

			this.idcliente = new BigDecimal(htVarios.get("idcliente")
					.toString());
			this.comentarios = (String) htVarios.get("comentarios");
			this.obsentrega = (String) htVarios.get("obsentrega");

			String nombre = htDireccion.get("nombreFact") + " "
					+ htDireccion.get("apellidoFact");

			this.mensaje = marketRegistroDireccion
					.marketCreateRegistroAndPedido(this.idcliente, this.email,
							this.pass, "S", this.total, this.idformapago,
							this.comentarios, this.obsentrega, htDireccion,
							this.htCarrito, this.idempresa, usuarioalt);

			if (Common.esEntero(this.mensaje)) {
				request.getSession().setAttribute("accionCarrito",
						"modificacion");

				request.getSession().removeAttribute("htCarrito");
				request.getSession().removeAttribute("htVarios");
				request.getSession().removeAttribute("totalCarrito");
				request.getSession().setAttribute("ordenCarrito", this.mensaje);
				response.sendRedirect("marketDetallePedido.jsp");

			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		RequestDispatcher dispatcher = null;
		Stock marketformasdepago = Common.getStock();
		try {

			this.htCarrito = (Hashtable) request.getSession().getAttribute(
					"htCarrito");

			if (this.htCarrito == null || this.htCarrito.isEmpty()) {
				request
						.getSession()
						.setAttribute("mensaje",
								"Su carrito se encuentra vacio, por favor selecciones algún producto.");
				response.sendRedirect("marketAviso.jsp");
			}

			this.total = (BigDecimal) request.getSession().getAttribute(
					"totalCarrito");
			this.email = (String) request.getSession().getAttribute(
					"emailCarrito");
			this.pass = (String) request.getSession().getAttribute(
					"passCarrito");

			this.totalRegistros = marketformasdepago.getTotalEntidad(
					"marketformasdepago", this.idempresa);
			this.totalPaginas = (this.totalRegistros / this.limit) + 1;
			if (this.totalPaginas < this.paginaSeleccion)
				this.paginaSeleccion = this.totalPaginas;
			this.offset = (this.paginaSeleccion - 1) * this.limit;
			if (this.totalRegistros == this.limit) {
				this.offset = 0;
				this.totalPaginas = 1;
			}
			this.marketformasdepagoList = marketformasdepago
					.getMarketformasdepagoAll(this.limit, this.offset,
							this.idempresa);

			if (this.totalRegistros < 1)
				this.mensaje = "Formas de Pago Inexistentes.";

			if (!this.generarPedido.equals("")) {

				if (this.idformapago == null
						|| this.idformapago.compareTo(new BigDecimal(0)) < 1) {
					this.mensaje = "Por favor seleccione una forma de pago, e intente nuevamente. ";
					return false;
				}

				this.ejecutarSentenciaDML();
			}

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

	public List getMarketformasdepagoList() {
		return marketformasdepagoList;
	}

	public void setMarketformasdepagoList(List marketformasdepagoList) {
		this.marketformasdepagoList = marketformasdepagoList;
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

	public BigDecimal getIdformapago() {
		return idformapago;
	}

	public void setIdformapago(BigDecimal idformapago) {
		this.idformapago = idformapago;
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

	public String getGenerarPedido() {
		return generarPedido;
	}

	public void setGenerarPedido(String generarPedido) {
		this.generarPedido = generarPedido;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}
}
