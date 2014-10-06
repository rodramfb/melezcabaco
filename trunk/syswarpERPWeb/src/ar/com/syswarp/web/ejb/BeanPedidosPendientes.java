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

public class BeanPedidosPendientes implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanPedidosPendientes.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal idestado = new BigDecimal(1);

	private String sort = " 1 ASC ";

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

	private String ultimoFiltroFecha = "";

	private String auxFiltroFecha = "";

	private String filtroPrioridad = "";

	private String filtroZona = "";

	private String filtroLocalidad = "";

	private String filtroExpresoZona = "";

	private BigDecimal idremitoclientedesde = new BigDecimal(-1);

	private BigDecimal idremitoclientehasta = new BigDecimal(-1);

	private String nameFile = "";

	private String usuarioalt;

	private List listPrioridades = new ArrayList();

	private List listZonas = new ArrayList();

	private String tipopedido = "N";

	// EJV - Mantis 496 - 20100204
	private List listExpresosZonas = new ArrayList();

	// EJV - Mantis 540 - 20100623 -->

	private String fechaRemito = "";

	private String sucursalTipoPedido = "";

	private String contador = "nroremitoclientes-suc-";

	private String valorContador = "";

	// <--

	// EJV - Mantis 608 - 20101109 -->

	private String filtroCotizacion = "";

	// <--

	// EJV - Mantis 734 - 20110720 -->

	private List listClub = new ArrayList();

	private BigDecimal idclub = new BigDecimal(-1);

	// <--

	public BeanPedidosPendientes() {
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
		String entidad = " vpedidospendientes ";

		// if (this.tipopedido.equalsIgnoreCase("N")) {
		//
		// entidad = "(SELECT pc.idpedido_cabe, es.idestado, es.estado,  "
		// +
		// "        cl.idcliente, cl.razon, pc.fechapedido, pr.idprioridad, pr.prioridad,"
		// +
		// "        zo.idzona, zo.zona, lo.localidad, ex.idexpreso, ex.expreso,  an.idexpresozona, pc.idempresa "
		// + "   FROM pedidos_cabe pc "
		// +
		// "              INNER JOIN clientesdomicilios cd ON pc.idsucuclie = cd.iddomicilio AND  pc.idempresa = cd.idempresa "
		// +
		// "              INNER JOIN clientesclientes cl ON pc.idcliente = cl.idcliente AND pc.idempresa = cl.idempresa "
		// +
		// "              INNER JOIN pedidosestados es ON pc.idestado = es.idestado AND pc.idempresa = es.idempresa "
		// // 20100804 - EJV - Reasignacion -->
		// // +
		// //
		// "              INNER JOIN clientesanexolocalidades an ON cd.idanexolocalidad = an.idanexolocalidad  AND  cd.idempresa = an.idempresa "
		// +
		// "              INNER JOIN clientesanexolocalidades an ON pc.idanexolocalidad = an.idanexolocalidad  AND  pc.idempresa = an.idempresa "
		// // +
		// //
		// "              INNER JOIN globallocalidades lo ON an.idlocalidad = lo.idlocalidad  "
		// // <--
		//
		// +
		// "              INNER JOIN clientesexpresoszonas ez ON  an.idexpresozona = ez.codigo AND an.idempresa = ez.idempresa "
		// +
		// "              INNER JOIN clienteszonas zo ON ez.idzona = zo.idzona AND ez.idempresa = zo.idempresa "
		// +
		// "              INNER JOIN clientesexpresos ex ON ez.idexpreso = ex.idexpreso  AND ez.idempresa = ex.idempresa "
		//
		// // 20100804 - EJV - Reasignacion -->
		// +
		// "              INNER JOIN clientesanexolocalidades anlo ON cd.idanexolocalidad = anlo.idanexolocalidad  AND  cd.idempresa = anlo.idempresa "
		// +
		// "              INNER JOIN globallocalidades lo ON anlo.idlocalidad = lo.idlocalidad "
		// // <--
		//
		// +
		// "              INNER JOIN pedidosprioridades pr ON pc.idprioridad = pr.idprioridad "
		// + ") pedido_pendientes ";
		// } else {
		// entidad = " ( "
		// +
		// "       SELECT pc.idpedido_regalos_entrega_cabe AS idpedido_cabe, es.idestado, es.estado,   "
		// +
		// "              cl.idcliente, cl.razon || ' - ' || cd.contacto AS razon , pc.fechapedido, pr.idprioridad, pr.prioridad, "
		// +
		// "              zo.idzona, zo.zona, lo.localidad, ex.idexpreso, ex.expreso, cd.iddomicilio, an.idexpresozona, pc.idempresa "
		// + "         FROM pedidos_regalos_entregas_cabe pc  "
		// +
		// "              INNER JOIN pedidosdomiciliosentrega cd ON pc.idsucuclie = cd.iddomicilio AND  pc.idempresa = cd.idempresa "
		// +
		// "              INNER JOIN clientesclientes cl ON pc.idcliente = cl.idcliente AND pc.idempresa = cl.idempresa "
		// +
		// "              INNER JOIN pedidosestados es ON pc.idestado = es.idestado AND pc.idempresa = es.idempresa  "
		// // 20100804 - EJV - Reasignacion -->
		// +
		// "              INNER JOIN clientesanexolocalidades an ON pc.idanexolocalidad = an.idanexolocalidad  AND  pc.idempresa = an.idempresa "
		// // +
		// //
		// "              INNER JOIN clientesanexolocalidades an ON cd.idanexolocalidad = an.idanexolocalidad  AND  cd.idempresa = an.idempresa "
		// // +
		// //
		// "              INNER JOIN globallocalidades lo ON an.idlocalidad = lo.idlocalidad   "
		// // <--
		// +
		// "              INNER JOIN clientesexpresoszonas ez ON  an.idexpresozona = ez.codigo AND an.idempresa = ez.idempresa "
		// +
		// "              INNER JOIN clienteszonas zo ON ez.idzona = zo.idzona AND ez.idempresa = zo.idempresa  "
		// +
		// "              INNER JOIN clientesexpresos ex ON ez.idexpreso = ex.idexpreso  AND ez.idempresa = ex.idempresa "
		// // 20100804 - EJV - Reasignacion -->
		// +
		// "              INNER JOIN clientesanexolocalidades anlo ON cd.idanexolocalidad = anlo.idanexolocalidad  AND  cd.idempresa = anlo.idempresa "
		// +
		// "              INNER JOIN globallocalidades lo ON anlo.idlocalidad = lo.idlocalidad  "
		// // <--
		// +
		// "              INNER JOIN pedidosprioridades pr ON pc.idprioridad = pr.idprioridad "
		// + "        ) pedido_pendientes ";
		// }

		String filtro = " WHERE idestado = " + this.idestado
				+ " AND noinventariable IS NULL  AND tipo = '"
				+ this.tipopedido + "'  "
		// 20101109 - MANTIS 608
		// + " AND cotizacion = 0 "
		;
		String auxFiltro = "";
		try {

			// EJV - 20100705 - MANTIS 540 .-->
			this.getDatosContador();
			// <--

			this.listPrioridades = clie.getPedidosPrioridadesAll(50, 0);
			this.listZonas = clie.getClienteszonasAll(100, 0, this.idempresa);
			// EJV - Mantis 496 - 20100204
			this.listExpresosZonas = clie.getClientesExpresosZonasAll(500, 0,
					this.idempresa);

			this.listClub = Common.getClientes().getClientesClubAll(100, 0,
					this.idempresa);

			if (this.accion.equalsIgnoreCase("consolidar")) {
				String[] resultado = new String[] { "", "", "" };
				List listMinMaxRemito = null;
				Map parameters = new HashMap();
				String plantillaImpresionJRXML = "";
				Calendar calendar = new GregorianCalendar();
				Calendar cal = new GregorianCalendar();

				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);

				// log.info("cal.getTime(): " + cal.getTime());
				// log.info("(java.util.Date) Common.setObjectToStrOrTime(this.fechaRemito,  StrToJUDate ): "
				// +
				// (java.util.Date) Common.setObjectToStrOrTime(
				// this.fechaRemito, "StrToJUDate"));

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
				String time = sdf.format(calendar.getTime());
				nameFile = time;

				if (Common.setNotNull(this.ultimoFiltroFecha).length() == 7) {
					this.ultimoFiltroFecha = "01/" + this.ultimoFiltroFecha;
				}

				if (this.idpedido_cabe == null
						|| this.idpedido_cabe.length == 0) {
					resultado[0] = "Es necesario seleccionar al menos un pedido a consolidar.";

				} else if (this.idclub.intValue() < 1) {

					resultado[0] = "Es necesario seleccionar club.";

				} else if (!Common.isFormatoFecha(this.fechaRemito)
						|| !Common.isFechaValida(this.fechaRemito)) {
					resultado[0] = "Es necesario ingresar fecha remito.";

				} else if (cal.getTime().after(
						(java.util.Date) Common.setObjectToStrOrTime(
								this.fechaRemito, "StrToJUDate"))) {
					resultado[0] = "Fecha debe ser mayor o igual a fecha actual: "
							+ Common.setObjectToStrOrTime(cal.getTime(),
									"JUDateToStr");
					// EJV 20100817 - Mantis 559 -->
					// Verifico que haya realizado una busqueda y que la misma
					// haya sido filtrada por fecha de entrega, previamente a
					// ejecutar consolidacion.
				} else if (!Common.isFormatoFecha(this.ultimoFiltroFecha)
						|| !Common.isFechaValida(this.ultimoFiltroFecha)) {

					resultado[0] = "Es necesario realizar una busqueda filtrando por Fecha de Entrega y que la misma sea válida.";
					// <--

				} else {

					java.sql.Date fecharemito = (java.sql.Date) Common
							.setObjectToStrOrTime(this.fechaRemito,
									"StrToJSDate");

					if (this.tipopedido.equalsIgnoreCase("N")) {
						plantillaImpresionJRXML = "remitos_clientes_frame";
						resultado = clie.pedidosNormalesConsolidarRemitos(
								this.idpuesto, this.idcontadorcomprobante,
								this.idpedido_cabe, fecharemito,
								this.idempresa, this.usuarioalt);
					} else {

						plantillaImpresionJRXML = "remitos_clientes_regalos_frame";
						resultado = clie.pedidosRegalosConsolidarRemitos(
								this.idpuesto, this.idcontadorcomprobante,
								this.idpedido_cabe, fecharemito,
								this.idempresa, this.usuarioalt);

					}

					if (resultado[0].equalsIgnoreCase("OK")) {

						this.idremitoclientedesde = new BigDecimal(resultado[1]);
						this.idremitoclientehasta = new BigDecimal(resultado[2]);

						listMinMaxRemito = Common.getClientes()
								.getPedidosRemitosGenerados(
										this.idremitoclientedesde,
										this.idremitoclientehasta,
										this.idempresa);

						if (listMinMaxRemito != null
								&& !listMinMaxRemito.isEmpty()) {

							String[] minMaxRemito = (String[]) listMinMaxRemito
									.get(0);

							nameFile += "-rc-[" + minMaxRemito[0] + "]-["
									+ minMaxRemito[1] + "].pdf";

							parameters.put("idremitoclientedesde",
									this.idremitoclientedesde);
							parameters.put("idremitoclientehasta",
									this.idremitoclientehasta);
							parameters.put("idempresa", this.idempresa
									.toString());
							parameters.put("reconsulta", "N");

							byte[] bytes = Common
									.getReport()
									.getOpenReportImpresiones(
											plantillaImpresionJRXML, parameters);
							Common.getReport().saveByteToFile(
									bytes,
									Common.getReport().getClientesRemitosPath()
											+ nameFile);
							// resultado[0] = "RESULTADO VALE: " + resultado[0];

						} else
							resultado[0] = "Error al recuperar numeros de remitos generados (Min/Max).";

					}

				} // else
				// resultado[0] =
				// "Es necesario seleccionar al menos un pedido a consolidar.";

				if (resultado[0].equalsIgnoreCase("OK")) {
					// this.mensaje = "Consolidaci�n de remitos correcta.";
					this.mensaje = "Consolidacion de remitos correcta.";
				} else {
					this.nameFile = "";
					this.mensaje = resultado[0];
				}

			}

			if (!this.filtroIdpedido.trim().equals("")) {
				auxFiltro += " AND idpedido_cabe = " + this.filtroIdpedido;
			} else {

				if (!this.filtroIdclie.trim().equals("")) {
					auxFiltro += " AND idcliente = " + this.filtroIdclie;
				}

				if (!this.filtroCliente.trim().equals("")) {
					auxFiltro += " AND UPPER(razon) LIKE '%"
							+ this.filtroCliente.toUpperCase() + "%'  ";
				}

				if (!this.filtroFecha.trim().equals("")) {

					if (this.radioFechaPeriodo.equalsIgnoreCase("F")) {
						auxFiltro += " AND fechapedido::DATE = TO_DATE('"
								+ this.filtroFecha + "',  'dd/mm/yyyy')  ";

						this.auxFiltroFecha = this.filtroFecha;

					} else if (this.radioFechaPeriodo.equalsIgnoreCase("P")) {

						this.auxFiltroFecha = "01/" + this.filtroFecha;

						auxFiltro += " AND DATE_PART('MONTH', fechapedido) = DATE_PART( 'MONTH',  TO_DATE('01/"
								+ this.filtroFecha + "',  'dd/mm/yyyy'))   ";
						auxFiltro += " AND DATE_PART('YEAR', fechapedido) = DATE_PART( 'YEAR', TO_DATE('01/"
								+ this.filtroFecha + "',  'dd/mm/yyyy'))   ";
					}

				}

				if (!this.filtroPrioridad.trim().equals("")) {
					auxFiltro += " AND idprioridad  = " + this.filtroPrioridad;
				}

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

				// EJV - Mantis 608 - 20101109 -->
				if (!this.filtroCotizacion.trim().equals("")) {
					auxFiltro += " AND cotizacion = " + this.filtroCotizacion;
				}
				// <--
			}

			if (this.idclub.intValue() > 0) {
				auxFiltro += " AND idclub = " + this.idclub;
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
				this.pedidos_cabeList = clie.getPedidosPendientesOcu(
						this.limit, this.offset, this.idestado,
						this.tipopedido, filtro, this.sort, this.idempresa);

			}

			else {
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

				this.pedidos_cabeList = clie.getPedidosPendientesAll(
						this.limit, this.offset, this.idestado,
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


			// EJV - Mantis 734 - 20110720 -->
			// EJV - 20100705 - MANTIS 540 - Harcode por requerimiento.-->
			// this.sucursalTipoPedido = (this.tipopedido.equalsIgnoreCase("N"))
			// ? "1"
			// : "3";
			if (this.idclub.intValue() > 0) {

				if (this.tipopedido.equalsIgnoreCase("N")) {

					if (this.idclub.intValue() == 1) {
						this.sucursalTipoPedido = "1";
					} else if (this.idclub.intValue() == 2) {
						this.sucursalTipoPedido = "4";
					}

				} else {
					this.sucursalTipoPedido = "3";
				}
				// <--

				this.contador += this.sucursalTipoPedido;
				List listContador = Common.getGeneral()
						.getGlobalContadoresXContador(this.contador,
								this.idempresa);
				if (listContador != null && !listContador.isEmpty()) {

					String[] datosContador = (String[]) listContador.get(0);
					this.idcontadorcomprobante = new BigDecimal(
							datosContador[0]);
					this.valorContador = datosContador[2];
					if (!Common.setNotNull(datosContador[4]).trim().equals(
							this.sucursalTipoPedido)) {

						this.mensaje = "La sucursal definida en el comprobante ("
								+ Common.strZero(Common
										.setNotNull(datosContador[4]), 4)
								+ "),  difiere de la designada al tipo de pedido: ("
								+ Common.strZero(this.sucursalTipoPedido, 4)
								+ ")";

					}

				} else {
					this.mensaje = "No está definido el contador para la sucursal:  "
							+ Common.strZero(this.sucursalTipoPedido, 4);
				}

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

	public String getUltimoFiltroFecha() {
		return ultimoFiltroFecha;
	}

	public void setUltimoFiltroFecha(String ultimoFiltroFecha) {
		this.ultimoFiltroFecha = ultimoFiltroFecha;
	}

	public String getFiltroPrioridad() {
		return filtroPrioridad;
	}

	public void setFiltroPrioridad(String filtroPrioridad) {
		this.filtroPrioridad = filtroPrioridad;
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

	public BigDecimal getIdremitoclientedesde() {
		return idremitoclientedesde;
	}

	public void setIdremitoclientedesde(BigDecimal idremitoclientedesde) {
		this.idremitoclientedesde = idremitoclientedesde;
	}

	public BigDecimal getIdremitoclientehasta() {
		return idremitoclientehasta;
	}

	public void setIdremitoclientehasta(BigDecimal idremitoclientehasta) {
		this.idremitoclientehasta = idremitoclientehasta;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public List getListPrioridades() {
		return listPrioridades;
	}

	public void setListPrioridades(List listPrioridades) {
		this.listPrioridades = listPrioridades;
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

	public String getFechaRemito() {
		return fechaRemito;
	}

	public void setFechaRemito(String fechaRemito) {
		this.fechaRemito = fechaRemito;
	}

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

	// <--

	// EJV - Mantis 608 - 20101109 -->

	public String getFiltroCotizacion() {
		return filtroCotizacion;
	}

	public void setFiltroCotizacion(String filtroCotizacion) {
		this.filtroCotizacion = filtroCotizacion;
	}

	// <--

	// EJV - Mantis 734 - 20110720 -->

	public List getListClub() {
		return listClub;
	}

	public BigDecimal getIdclub() {
		return idclub;
	}

	public void setIdclub(BigDecimal idclub) {
		this.idclub = idclub;
	}

	// <--

}
