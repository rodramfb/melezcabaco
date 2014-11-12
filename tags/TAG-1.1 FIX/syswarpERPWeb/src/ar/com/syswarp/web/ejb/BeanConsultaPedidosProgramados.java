/* 
   javabean para la entidad: pedidos_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Feb 12 08:43:04 GYT 2009 
   
   Para manejar la pagina: pedidos_cabeAbm.jsp
      
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

import java.text.SimpleDateFormat;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanConsultaPedidosProgramados implements SessionBean,
		Serializable {
	static Logger log = Logger.getLogger(BeanConsultaPedidosProgramados.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal idestado = new BigDecimal(1);

	private String sort = " 11 DESC ";

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List pedidos_cabeList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String[] idpedido_cabe = new String[] {};

	private BigDecimal idpuesto = new BigDecimal(-1);

	private BigDecimal idcontadorcomprobante = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String filtroIdpedido = "";

	private String filtroIdclie = "";

	private String filtroCliente = "";

	private String radioFechaPeriodo = "F";

	private String filtroFecha = "";

	private String filtroEstadoPedido = "";

	private String filtroEstadoRemito = "";

	// private String filtroPrioridad = "2";

	private String filtroZona = "";

	private String filtroLocalidad = "";

	private String filtroExpresoZona = "";

	private String usuarioalt;

	private List listEstadosPedido = new ArrayList();

	private List listEstadosRemito = new ArrayList();

	private List listZonas = new ArrayList();

	private String tipopedido = "N";

	// EJV - Mantis 496 - 20100204
	private List listExpresosZonas = new ArrayList();

	// EJV - Mantis 540 - 20100623 -->

	private String sucursalTipoPedido = "";

	private String contador = "nroremitoclientes-suc-";

	private String valorContador = "";

	// <--

	private String fechaDesde = "";

	private String fechaHasta = "";

	private BigDecimal idprioridad = new BigDecimal(2);

	public BeanConsultaPedidosProgramados() {
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
		// RequestDispatcher dispatcher = null;
		Clientes clie = Common.getClientes();
		String entidad = "";

		if (this.tipopedido.equalsIgnoreCase("N")) {

			entidad = "(        "
					+ "        SELECT pc.idpedido_cabe, cre.idremitocliente, cre.nrosucursal, cre.nroremitocliente, "
					+ "                    es.idestado AS idestadopedido, es.estado AS estadopedido, cre.idestadoremito, cre.estadoremito,  "
					+ "                    cl.idcliente, cl.razon, pc.fechapedido, pr.idprioridad, pr.prioridad, "
					+ "                    zo.idzona, zo.zona, lo.localidad, ex.idexpreso, ex.expreso, cd.iddomicilio, an.idexpresozona, pc.idempresa  "
					+ "          FROM pedidos_cabe pc  "
					+ "                    INNER JOIN clientesdomicilios cd ON pc.idsucuclie = cd.iddomicilio AND  pc.idempresa = cd.idempresa  "
					+ "                    INNER JOIN clientesclientes cl ON pc.idcliente = cl.idcliente AND pc.idempresa = cl.idempresa  "
					+ "                    INNER JOIN pedidosestados es ON pc.idestado = es.idestado AND pc.idempresa = es.idempresa  "
					+ "                    INNER JOIN clientesanexolocalidades an ON cd.idanexolocalidad = an.idanexolocalidad  AND  cd.idempresa = an.idempresa  "
					+ "                    INNER JOIN globallocalidades lo ON an.idlocalidad = lo.idlocalidad   "
					+ "                    INNER JOIN clientesexpresoszonas ez ON  an.idexpresozona = ez.codigo AND an.idempresa = ez.idempresa  "
					+ "                    INNER JOIN clienteszonas zo ON ez.idzona = zo.idzona AND ez.idempresa = zo.idempresa  "
					+ "                    INNER JOIN clientesexpresos ex ON ez.idexpreso = ex.idexpreso  AND ez.idempresa = ex.idempresa  "
					+ "                    INNER JOIN pedidosprioridades pr ON pc.idprioridad = pr.idprioridad "
					+ "                    INNER JOIN ( "
					+ "                           SELECT rpc.idpedido_cabe, cr.idremitocliente, cr.nrosucursal, cr.nroremitocliente, "
					+ "                                       res.idestado AS idestadoremito, res.estado AS estadoremito, rpc.idempresa "
					+ "                             FROM pedidos_cabe rpc "
					+ "                                       INNER JOIN pedidos_deta rpd ON rpc.idpedido_cabe = rpd.idpedido_cabe AND rpc.idempresa = rpd.idempresa "
					+ "                                        LEFT JOIN clientesremitos cr ON rpd.idremitocliente = cr.idremitocliente AND rpd.idempresa = cr.idempresa "
					+ "                                        LEFT JOIN clientesremitosestados res ON cr.idestado = res.idestado AND cr.idempresa = res.idempresa "
					+ "                           WHERE rpc.idprioridad =  "
					+ this.idprioridad.toString()
					+ "                               AND (cr.idestado IS NULL OR cr.idestado = 1 ) "
					+ "                               AND rpc.idempresa =  "
					+ this.idempresa.toString()
					+ "                           GROUP BY rpc.idpedido_cabe, cr.idremitocliente, cr.nrosucursal, cr.nroremitocliente, res.idestado, res.estado, rpc.idempresa "
					+ "                          ) cre ON pc.idpedido_cabe = cre.idpedido_cabe AND pc.idempresa = cre.idempresa "
					+ "         ) pedidos_pendientes  ";
		} else {
			entidad = " ( "
					+ "        SELECT pc.idpedido_regalos_entrega_cabe, cre.idremitocliente, cre.nrosucursal, cre.nroremitocliente, "
					+ "                    es.idestado AS idestadopedido, es.estado AS estadopedido, cre.idestadoremito, cre.estadoremito,  "
					+ "                    cl.idcliente, cl.razon, pc.fechapedido, pr.idprioridad, pr.prioridad, "
					+ "                    zo.idzona, zo.zona, lo.localidad, ex.idexpreso, ex.expreso, cd.iddomicilio, an.idexpresozona, pc.idempresa  "
					+ "          FROM pedidos_regalos_entregas_cabe pc   "
					+ "                   INNER JOIN pedidosdomiciliosentrega cd ON pc.idsucuclie = cd.iddomicilio AND  pc.idempresa = cd.idempresa  "
					+ "                   INNER JOIN clientesclientes cl ON pc.idcliente = cl.idcliente AND pc.idempresa = cl.idempresa  "
					+ "                   INNER JOIN pedidosestados es ON pc.idestado = es.idestado AND pc.idempresa = es.idempresa   "
					+ "                   INNER JOIN clientesanexolocalidades an ON cd.idanexolocalidad = an.idanexolocalidad  AND  cd.idempresa = an.idempresa  "
					+ "                   INNER JOIN globallocalidades lo ON an.idlocalidad = lo.idlocalidad    "
					+ "                   INNER JOIN clientesexpresoszonas ez ON  an.idexpresozona = ez.codigo AND an.idempresa = ez.idempresa  "
					+ "                   INNER JOIN clienteszonas zo ON ez.idzona = zo.idzona AND ez.idempresa = zo.idempresa   "
					+ "                   INNER JOIN clientesexpresos ex ON ez.idexpreso = ex.idexpreso  AND ez.idempresa = ex.idempresa  "
					+ "                   INNER JOIN pedidosprioridades pr ON pc.idprioridad = pr.idprioridad  "
					+ "                   INNER JOIN (  "
					+ "                            SELECT rpc.idpedido_regalos_entrega_cabe, cr.idremitocliente, cr.nrosucursal, cr.nroremitocliente, "
					+ "                                        res.idestado AS idestadoremito, res.estado AS estadoremito, rpc.idempresa  "
					+ "                              FROM pedidos_regalos_entregas_cabe rpc  "
					+ "                                        INNER JOIN pedidos_regalos_entregas_deta rpd ON rpc.idpedido_regalos_entrega_cabe = rpd.idpedido_regalos_entrega_cabe AND rpc.idempresa = rpd.idempresa  "
					+ "                                          LEFT JOIN clientesremitos cr ON rpd.idremitocliente = cr.idremitocliente AND rpd.idempresa = cr.idempresa  "
					+ "                                          LEFT JOIN clientesremitosestados res ON cr.idestado = res.idestado AND cr.idempresa = res.idempresa  "
					+ "                             WHERE rpc.idprioridad =   "
					+ this.idprioridad.toString()
					+ "                               AND (cr.idestado IS NULL OR cr.idestado = 1 )  "
					+ "                               AND rpc.idempresa = "
					+ this.idempresa.toString()
					+ "                             GROUP BY rpc.idpedido_regalos_entrega_cabe, cr.idremitocliente, cr.nrosucursal, cr.nroremitocliente, res.idestado, res.estado, rpc.idempresa  "
					+ "                           ) cre ON pc.idpedido_regalos_entrega_cabe = cre.idpedido_regalos_entrega_cabe AND pc.idempresa = cre.idempresa  "
					+ "         ) pedidos_pendientes      ";
		}

		String filtro = " WHERE idestadopedido IN (1,2,3)  ";
		String auxFiltro = "";
		try {

			// EJV - 20100705 - MANTIS 540 .-->
			this.getDatosContador();
			// <--

			this.listEstadosPedido = clie.getPedidosEstadosAll(50, 0);
			this.listEstadosRemito = clie.getClientesRemitosEstadosAll(50, 0,
					this.idempresa);
			this.listZonas = clie.getClienteszonasAll(100, 0, this.idempresa);
			// EJV - Mantis 496 - 20100204
			this.listExpresosZonas = clie.getClientesExpresosZonasAll(500, 0,
					this.idempresa);

			if (!this.filtroIdpedido.trim().equals("")) {
				auxFiltro += " AND idpedido_cabe = " + this.filtroIdpedido;
			} else {

				if (!this.filtroEstadoPedido.trim().equals("")) {
					filtro = " WHERE TRUE ";
					auxFiltro = " AND idestadopedido = "
							+ this.filtroEstadoPedido;
				}

				if (!this.filtroIdclie.trim().equals("")) {
					auxFiltro += " AND idcliente = " + this.filtroIdclie;
				}

				if (!this.filtroCliente.trim().equals("")) {
					auxFiltro += " AND UPPER(razon) LIKE '%"
							+ this.filtroCliente.toUpperCase() + "%'  ";
				}

				if (!this.filtroEstadoRemito.trim().equals("")) {
					auxFiltro += !filtroEstadoRemito.equals(" NULL ") ? " AND idestadoremito =  "
							+ this.filtroEstadoRemito
							: " AND idestadoremito IS  "
									+ this.filtroEstadoRemito;
				}

				// Filtros Fecha --->
				if (!this.fechaDesde.equals("") || !this.fechaHasta.equals("")) {

					if (!Common.isFormatoFecha(this.fechaDesde)
							|| !Common.isFechaValida(this.fechaDesde)) {
						this.mensaje = "Ingrese fecha desde valida.";
					} else if (!Common.isFormatoFecha(this.fechaHasta)
							|| !Common.isFechaValida(this.fechaHasta)) {
						this.mensaje = "Ingrese fecha hasta valida.";
					} else if ((((java.util.Date) Common.setObjectToStrOrTime(
							this.fechaDesde, "StrToJUDate"))
							.after((java.util.Date) Common
									.setObjectToStrOrTime(this.fechaHasta,
											"StrToJUDate")))) {
						this.mensaje = "Fecha desde debe ser menor o igual a fecha hasta.";
					} else {

						auxFiltro += " AND fechapedido::DATE BETWEEN TO_DATE('"
								+ this.fechaDesde
								+ "',  'dd/mm/yyyy')  AND  TO_DATE('"
								+ this.fechaHasta + "',  'dd/mm/yyyy')  ";

					}

				} else if (!this.filtroFecha.trim().equals("")) {

					if (this.radioFechaPeriodo.equalsIgnoreCase("F")) {
						auxFiltro += " AND fechapedido::DATE = TO_DATE('"
								+ this.filtroFecha + "',  'dd/mm/yyyy')  ";
					} else if (this.radioFechaPeriodo.equalsIgnoreCase("P")) {

						auxFiltro += " AND DATE_PART('MONTH', fechapedido) = DATE_PART( 'MONTH',  TO_DATE('01/"
								+ this.filtroFecha + "',  'dd/mm/yyyy'))   ";
						auxFiltro += " AND DATE_PART('YEAR', fechapedido) = DATE_PART( 'YEAR', TO_DATE('01/"
								+ this.filtroFecha + "',  'dd/mm/yyyy'))   ";
					}

				}
				// <--- Filtros Fecha

				if (!this.filtroZona.trim().equals("")) {
					auxFiltro += " AND idzona = " + this.filtroZona;
				}

				// EJV - Mantis 496 - 20100204
				if (!this.filtroExpresoZona.trim().equals("")) {
					auxFiltro += " AND idexpresozona = "
							+ this.filtroExpresoZona + " ";
				}
				// --

				if (!this.filtroLocalidad.trim().equals("")) {
					auxFiltro += " AND UPPER(localidad) LIKE '"
							+ this.filtroLocalidad.replaceAll("'", "%")
									.toUpperCase() + "%'  ";
				}

			}

			auxFiltro = auxFiltro.trim();

			if (!auxFiltro.equals("")) {

				filtro += auxFiltro;

				this.totalRegistros = clie.getTotalEntidadFiltro(entidad,
						filtro, this.idempresa);

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

				this.pedidos_cabeList = clie.getPedidosProgramadosOcu(
						this.limit, this.offset, this.idprioridad,
						this.tipopedido, filtro, this.sort, this.idempresa);

			} else {
				this.totalRegistros = clie.getTotalEntidadFiltro(entidad,
						filtro, this.idempresa);

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.pedidos_cabeList = clie.getPedidosProgramadosAll(
						this.limit, this.offset, this.idprioridad,
						this.tipopedido, this.sort, this.idempresa);

			}

			if (this.totalRegistros < 1 && this.mensaje.equals(""))
				this.mensaje = "No existen registros.";

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	private void getDatosContador() {

		try {

			// EJV - 20100705 - MANTIS 540 - Harcode por requerimiento.-->
			this.sucursalTipoPedido = (this.tipopedido.equalsIgnoreCase("N")) ? "1"
					: "3";
			this.contador += this.sucursalTipoPedido;
			List listContador = Common
					.getGeneral()
					.getGlobalContadoresXContador(this.contador, this.idempresa);
			if (listContador != null && !listContador.isEmpty()) {

				String[] datosContador = (String[]) listContador.get(0);
				this.idcontadorcomprobante = new BigDecimal(datosContador[0]);
				this.valorContador = datosContador[2];
				if (!Common.setNotNull(datosContador[4]).trim().equals(
						this.sucursalTipoPedido)) {

					this.mensaje = "La sucursal definida en el comprobante ("
							+ Common.strZero(Common
									.setNotNull(datosContador[4]), 4)
							+ "),  difiere de la designada al tipo de pedido: ("
							+ Common.strZero(this.sucursalTipoPedido, 4) + ")";

				}

			} else {
				this.mensaje = "No está definido el contador para la sucursal:  "
						+ Common.strZero(this.sucursalTipoPedido, 4);
			}

			// <--

		} catch (Exception e) {
			log.error("getDatosContador(): " + e);
		}

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

	public List getPedidos_cabeList() {
		return pedidos_cabeList;
	}

	public void setPedidos_cabeList(List pedidos_cabeList) {
		this.pedidos_cabeList = pedidos_cabeList;
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

	public String[] getIdpedido_cabe() {
		return idpedido_cabe;
	}

	public void setIdpedido_cabe(String[] idpedido_cabe) {
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
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

	public String getFiltroCliente() {
		return filtroCliente;
	}

	public void setFiltroCliente(String filtroCliente) {
		this.filtroCliente = filtroCliente;
	}

	public String getRadioFechaPeriodo() {
		return radioFechaPeriodo;
	}

	public void setRadioFechaPeriodo(String radioFechaPeriodo) {
		this.radioFechaPeriodo = radioFechaPeriodo;
	}

	public String getFiltroFecha() {
		return filtroFecha;
	}

	public void setFiltroFecha(String filtroFecha) {
		this.filtroFecha = filtroFecha;
	}

	public String getFiltroEstadoPedido() {
		return filtroEstadoPedido;
	}

	public void setFiltroEstadoPedido(String filtroEstadoPedido) {
		this.filtroEstadoPedido = filtroEstadoPedido;
	}

	public String getFiltroEstadoRemito() {
		return filtroEstadoRemito;
	}

	public void setFiltroEstadoRemito(String filtroEstadoRemito) {
		this.filtroEstadoRemito = filtroEstadoRemito;
	}

	public String getFiltroZona() {
		return filtroZona;
	}

	public void setFiltroZona(String filtroZona) {
		this.filtroZona = filtroZona;
	}

	public String getFiltroLocalidad() {
		return filtroLocalidad;
	}

	public void setFiltroLocalidad(String filtroLocalidad) {
		this.filtroLocalidad = filtroLocalidad;
	}

	public String getFiltroExpresoZona() {
		return filtroExpresoZona;
	}

	public void setFiltroExpresoZona(String filtroExpresoZona) {
		this.filtroExpresoZona = filtroExpresoZona;
	}

	public BigDecimal getIdpuesto() {
		return idpuesto;
	}

	public void setIdpuesto(BigDecimal idpuesto) {
		this.idpuesto = idpuesto;
	}

	public BigDecimal getIdcontadorcomprobante() {
		return idcontadorcomprobante;
	}

	public void setIdcontadorcomprobante(BigDecimal idcontadorcomprobante) {
		this.idcontadorcomprobante = idcontadorcomprobante;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public List getListEstadosPedido() {
		return listEstadosPedido;
	}

	public void setListEstadosPedido(List listEstadosPedido) {
		this.listEstadosPedido = listEstadosPedido;
	}

	public List getListEstadosRemito() {
		return listEstadosRemito;
	}

	public void setListEstadosRemito(List listEstadosRemito) {
		this.listEstadosRemito = listEstadosRemito;
	}

	public List getListZonas() {
		return listZonas;
	}

	public void setListZonas(List listZonas) {
		this.listZonas = listZonas;
	}

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

	public List getListExpresosZonas() {
		return listExpresosZonas;
	}

	public void setListExpresosZonas(List listExpresosZonas) {
		this.listExpresosZonas = listExpresosZonas;
	}

	// EJV - Mantis 540 - 20100705 -->

	public String getSucursalTipoPedido() {
		return sucursalTipoPedido;
	}

	public void setSucursalTipoPedido(String sucursalTipoPedido) {
		this.sucursalTipoPedido = sucursalTipoPedido;
	}

	public String getContador() {
		return contador;
	}

	public void setContador(String contador) {
		this.contador = contador;
	}

	public String getValorContador() {
		return valorContador;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	// <--

}
