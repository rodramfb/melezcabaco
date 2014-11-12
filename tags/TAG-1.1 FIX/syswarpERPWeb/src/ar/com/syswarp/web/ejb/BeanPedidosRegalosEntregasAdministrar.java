/* 
   javabean para la entidad: pedidos_Regalos_Entregas_Cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Nov 13 10:00:59 GMT-03:00 2009 
   
   Para manejar la pagina: pedidos_Regalos_Entregas_CabeAbm.jsp
      
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

public class BeanPedidosRegalosEntregasAdministrar implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanPedidosRegalosEntregasAdministrar.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List pedidos_Regalos_Entregas_CabeList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idpedido_regalos_cabe = new BigDecimal(-1);

	private BigDecimal idpedido_regalos_entrega_cabe = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;
	private String usuarioact = "";

	// --

	private BigDecimal idcliente = new BigDecimal(-1);

	private String razon = "";

	private BigDecimal idestadopedido_ppal = new BigDecimal(-1);

	private String estadopedido_ppal = "";

	private BigDecimal idprioridadpedido_ppal = new BigDecimal(-1);

	private String prioridadpedido_ppal = "";

	private String obsarmadopedido_ppal = "";

	private String obsentregapedido_ppal = "";

	private String fechapedido_ppal = null;

	private BigDecimal idestadoanterior = new BigDecimal(-1);

	public BeanPedidosRegalosEntregasAdministrar() {
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

		Clientes pedidos_Regalos_Entregas_Cabe = Common.getClientes();
		try {

			this.getDatosPedidoRegalos();


			

			if (this.accion.equalsIgnoreCase("baja")) {
				if (idpedido_regalos_entrega_cabe.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					BigDecimal idestadonuevo = new BigDecimal(4);
					String[] resultado;
					
					log.info("EL PEDIDO DE REGALOS ES : " + this.idpedido_regalos_cabe);
					
					resultado = pedidos_Regalos_Entregas_Cabe
							.callPedidosRegalosEntregasCabeUpdateEstado(
									this.idpedido_regalos_cabe,
									this.idpedido_regalos_entrega_cabe,
									this.idestadoanterior, idestadonuevo,
									this.idempresa, this.usuarioact);

					if (Common.setNotNull(resultado[0]).equalsIgnoreCase("OK")) {
						this.mensaje = "Entrega anulada correctamente.";
					}

				}
			}

			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idpedido_regalos_entrega_cabe.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("pedidos_Regalos_Entregas_CabeFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}

			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("pedidos_Regalos_Entregas_CabeFrm.jsp?idpedido_regalos_cabe="
								+ this.idpedido_regalos_cabe);
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
					+ "SELECT ec.idpedido_regalos_cabe,de.iddomicilio, de.idcliente, de.calle, de.nro, de.piso, de.depto, "
					+ "       de.cpa, de.postal, de.contacto, de.cargocontacto, de.telefonos, "
					+ "       de.celular, de.fax, de.web, de.idanexolocalidad, de.idempresa "
					+ "  FROM pedidos_regalos_entregas_cabe ec "
					+ "       INNER JOIN pedidosdomiciliosentrega de ON ec.idsucuclie = de.iddomicilio AND ec.idempresa = de.idempresa "
					+ ") entidad ";

			String filtro = " WHERE idpedido_regalos_cabe = "
					+ this.idpedido_regalos_cabe;

			if (buscar) {

				filtro += " AND ( UPPER(contacto) LIKE '%"
						+ ocurrencia.toUpperCase() + "%'  OR UPPER(calle) LIKE '%"
						+ ocurrencia.toUpperCase() + "%' )" ;;
				this.totalRegistros = pedidos_Regalos_Entregas_Cabe
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
				this.pedidos_Regalos_Entregas_CabeList = pedidos_Regalos_Entregas_Cabe
						.getPedidos_Regalos_Entregas_CabeOcu(this.limit,
								this.offset, this.ocurrencia,
								this.idpedido_regalos_cabe, this.idempresa);
			} else {
				this.totalRegistros = pedidos_Regalos_Entregas_Cabe
						.getTotalEntidadFiltro(entidad, filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.pedidos_Regalos_Entregas_CabeList = pedidos_Regalos_Entregas_Cabe
						.getPedidos_Regalos_Entregas_CabeAll(this.limit,
								this.offset, this.idpedido_regalos_cabe,
								this.idempresa);
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	private void getDatosPedidoRegalos() {

		try {

			List listDatosPedidoPpal = Common.getClientes()
					.getPedidosRegalosCabeHeader(this.idpedido_regalos_cabe,
							this.idempresa);

			if (listDatosPedidoPpal != null && !listDatosPedidoPpal.isEmpty()) {

				String[] datos = (String[]) listDatosPedidoPpal.get(0);

				this.idcliente = new BigDecimal(datos[4]);
				this.razon = datos[5];
				this.idestadopedido_ppal = new BigDecimal(datos[2]);
				this.estadopedido_ppal = datos[3];
				this.fechapedido_ppal = (String) Common.setObjectToStrOrTime(
						java.sql.Timestamp.valueOf(datos[6]), "JSTsToStr");
				this.idprioridadpedido_ppal = new BigDecimal(datos[9]);
				this.obsarmadopedido_ppal = datos[7];
				this.obsentregapedido_ppal = datos[8];
				this.prioridadpedido_ppal = datos[10];

			}

		} catch (Exception e) {
			log.error("getDatosPedidoRegalos(): " + e);
		}

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

	public List getPedidos_Regalos_Entregas_CabeList() {
		return pedidos_Regalos_Entregas_CabeList;
	}

	public void setPedidos_Regalos_Entregas_CabeList(
			List pedidos_Regalos_Entregas_CabeList) {
		this.pedidos_Regalos_Entregas_CabeList = pedidos_Regalos_Entregas_CabeList;
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

	public BigDecimal getIdpedido_regalos_entrega_cabe() {
		return idpedido_regalos_entrega_cabe;
	}

	public void setIdpedido_regalos_entrega_cabe(
			BigDecimal idpedido_regalos_entrega_cabe) {
		this.idpedido_regalos_entrega_cabe = idpedido_regalos_entrega_cabe;
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

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
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

	public BigDecimal getIdestadopedido_ppal() {
		return idestadopedido_ppal;
	}

	public void setIdestadopedido_ppal(BigDecimal idestadopedido_ppal) {
		this.idestadopedido_ppal = idestadopedido_ppal;
	}

	public String getEstadopedido_ppal() {
		return estadopedido_ppal;
	}

	public void setEstadopedido_ppal(String estadopedido_ppal) {
		this.estadopedido_ppal = estadopedido_ppal;
	}

	public BigDecimal getIdprioridadpedido_ppal() {
		return idprioridadpedido_ppal;
	}

	public void setIdprioridadpedido_ppal(BigDecimal idprioridadpedido_ppal) {
		this.idprioridadpedido_ppal = idprioridadpedido_ppal;
	}

	public String getPrioridadpedido_ppal() {
		return prioridadpedido_ppal;
	}

	public void setPrioridadpedido_ppal(String prioridadpedido_ppal) {
		this.prioridadpedido_ppal = prioridadpedido_ppal;
	}

	public String getObsarmadopedido_ppal() {
		return obsarmadopedido_ppal;
	}

	public void setObsarmadopedido_ppal(String obsarmadopedido_ppal) {
		this.obsarmadopedido_ppal = obsarmadopedido_ppal;
	}

	public String getObsentregapedido_ppal() {
		return obsentregapedido_ppal;
	}

	public void setObsentregapedido_ppal(String obsentregapedido_ppal) {
		this.obsentregapedido_ppal = obsentregapedido_ppal;
	}

	public String getFechapedido_ppal() {
		return fechapedido_ppal;
	}

	public void setFechapedido_ppal(String fechapedido_ppal) {
		this.fechapedido_ppal = fechapedido_ppal;
	}

	public BigDecimal getIdestadoanterior() {
		return idestadoanterior;
	}

	public void setIdestadoanterior(BigDecimal idestadoanterior) {
		this.idestadoanterior = idestadoanterior;
	}

}
