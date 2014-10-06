/* 
   javabean para la entidad: vclientesRemitosFacturar
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Jul 04 14:04:36 ART 2011 
   
   Para manejar la pagina: vclientesRemitosFacturarAbm.jsp
      
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

public class BeanVclientesRemitosFacturarAbm implements SessionBean,
		Serializable {
	static Logger log = Logger.getLogger(BeanVclientesRemitosFacturarAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List vclientesRemitosFacturarList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private Double totalfacturar;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String usuarioalt = "";

	boolean buscar = false;

	private List listExpresosZonas = new ArrayList();

	private List listTarjetasCredito = new ArrayList();

	private List listLetrasIva = new ArrayList();

	private String filtroExpresoZona = "";

	private String filtroIdclie = "";

	private String filtroSucursalRemito = "";

	private String filtroRemito = "";

	private String filtroSucursalFactura = "";

	private String filtroTarjetaCredito = "";

	private String filtroLetraIva = "";

	private String filtroValorComprobante = "";

	private String filtroCondicion = "";

	private String sort = "  9, 11  ";

	private List listSucursales = new ArrayList();

	private List listCondicion = new ArrayList();

	private String[] idremitocliente = null;

	private String ejercicioContable = "0";

	private List listClub = new ArrayList();

	private BigDecimal idclub = new BigDecimal(-1);

	// 20120307 - EJV - Mantis 819 -->

	private Calendar cal = new GregorianCalendar().getInstance();

	private int anioActual = cal.get(Calendar.YEAR);

	private String filtroFechaMes = "";

	private String filtroFechaAnio = "";

	// <--

	public BeanVclientesRemitosFacturarAbm() {
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

		try {

			String filtro = " WHERE TRUE ";
			String auxFiltro = "";

			this.listExpresosZonas = Common.getClientes()
					.getClientesExpresosZonasAll(500, 0, this.idempresa);

			this.listClub = Common.getClientes().getClientesClubAll(100, 0,
					this.idempresa);

			this.listSucursales = Common.getTesoreria().getCajaSucursalesAll(
					250, 0, this.idempresa);

			this.listTarjetasCredito = Common.getClientes()
					.getClientetarjetascreditomarcasAll(100, 0, this.idempresa);

			this.listLetrasIva = Common.getClientes()
					.getClientestablaivaLetrasAll(this.idempresa);

			this.listCondicion = Common.getClientes().getClientescondicioAll(
					250, 0, this.idempresa);

			this.ejercicioContable = Common.setNotNull(request.getSession()
					.getAttribute("ejercicioActivo")
					+ "");

			if (Common.setNotNull(this.filtroExpresoZona).equals("")) {
				this.mensaje = "Es necesario filtrar por Expreso/Zona ";
			} else {

				if (this.accion.equalsIgnoreCase("emitir")) {

					if (Common.setNotNull(this.filtroSucursalFactura)
							.equals("")) {
						this.mensaje = "Por favor seleccione sucursal de factura a emitir.";

					} else if (this.idclub.intValue() < 1) {

						this.mensaje = "Por favor seleccione club para emitir comprobantes.";

					} else if (Common.setNotNull(this.filtroLetraIva)
							.equals("")) {

						this.mensaje = "Por favor seleccione condicion de IVA para emitir comprobantes.";
						// 20120209 - EJV - Mantis 815 -->
						// Obliga a seleccionar hasta que se actualice
						// producción, con pasajes pendientes, luego debería
						// quitarse.
					} else if (Common.setNotNull(this.filtroValorComprobante)
							.equals("")) {

						this.mensaje = "Por favor seleccione si emite TF ( total > 0 ) o TNF ( total = 0 ) . ";
						// <--
					} else if (this.idremitocliente == null
							|| this.idremitocliente.length == 0) {

						this.mensaje = "Por favor seleccione al menos un documento.";

					} else if (!Common.esEntero(this.ejercicioContable)) {

						this.mensaje = "No existe ejercicio activo..";

					} else {

						int ejercicioactivo = Integer
								.parseInt(this.ejercicioContable);
						java.sql.Timestamp fechamov = new java.sql.Timestamp(
								Common.initObjectTime());
						BigDecimal tipomov = new BigDecimal(1);
						String tipomovs = "FA" + this.filtroLetraIva;

						String letraContador = this.filtroLetraIva;

						BigDecimal cambio = new BigDecimal(1);
						BigDecimal moneda = new BigDecimal(1);

						if (this.filtroValorComprobante.equals("0")) {
							tipomov = new BigDecimal(5);
							tipomovs = "TNF";
							letraContador = "@";
						}

						this.mensaje = Common.getClientes()
								.clientesMovCliDesdeRemitosCreate(
										this.idremitocliente,
										this.idclub,
										new BigDecimal(
												this.filtroSucursalFactura),
										fechamov, tipomov, tipomovs,
										this.filtroLetraIva, letraContador,
										cambio, moneda, ejercicioactivo,
										this.usuarioalt, this.idempresa);

					}

				}

				auxFiltro = " AND idexpresozona = " + this.filtroExpresoZona;

				if (!Common.setNotNull(this.filtroLetraIva).equals("")) {
					auxFiltro += " AND letra = '" + this.filtroLetraIva + "'";
				}

				if (!Common.setNotNull(this.filtroSucursalFactura).equals("")) {
					auxFiltro += " AND sucursalfactura = "
							+ this.filtroSucursalFactura;
				}

				if (Common.setNotNull(this.filtroSucursalRemito).equals("")
						&& Common.esEntero(this.filtroSucursalRemito)) {
					auxFiltro += " AND  nrosucursal = "
							+ this.filtroSucursalRemito;
				}

				if (!Common.setNotNull(this.filtroRemito).equals("")
						&& Common.esEntero(this.filtroRemito)) {
					auxFiltro += " AND  nroremitocliente = "
							+ this.filtroRemito;
				}

				if (!Common.setNotNull(this.filtroIdclie).equals("")
						&& Common.esEntero(this.filtroIdclie)) {
					auxFiltro += " AND  idcliente = " + this.filtroIdclie;
				}

				if (this.idclub.intValue() > 0) {
					auxFiltro += " AND idclub = " + idclub;
				}

				if (!Common.setNotNull(this.filtroTarjetaCredito).equals("")) {
					if (Common.setNotNull(this.filtroTarjetaCredito)
							.equalsIgnoreCase("ST"))
						auxFiltro += " AND  idtarjetacredito IS NULL ";
					else
						auxFiltro += " AND  idtarjetacredito = "
								+ this.filtroTarjetaCredito;
				}

				if (!Common.setNotNull(this.filtroValorComprobante).equals("")) {
					if (Common.setNotNull(this.filtroValorComprobante)
							.equalsIgnoreCase("0"))
						auxFiltro += " AND  totalfacturar = 0 ";
					else
						auxFiltro += " AND  totalfacturar > 0 ";

				}//

				if (!Common.setNotNull(this.filtroCondicion).equals("")
						&& Common.esEntero(this.filtroCondicion)) {
					auxFiltro += " AND  idcondicion = " + this.filtroCondicion;
				}

				// 20120307 - EJV - Mantis 819 -->

				if (Common.esEntero(this.filtroFechaMes)
						&& Common.esEntero(this.filtroFechaAnio)) {

					auxFiltro += " AND  DATE_PART('MONTH', fecharemito) = "
							+ this.filtroFechaMes;
					auxFiltro += " AND  DATE_PART('YEAR', fecharemito) = "
							+ this.filtroFechaAnio;

				}

				// <--

				this.totalRegistros = Common.getClientes()
						.getTotalEntidadFiltro("vclientesRemitosFacturar",
								filtro + auxFiltro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.vclientesRemitosFacturarList = Common.getClientes()
						.getVclientesRemitosFacturarAll(this.limit,
								this.offset, auxFiltro, this.sort,
								this.idempresa);

				if (this.totalRegistros < 1
						&& Common.setNotNull(this.mensaje).equals(""))
					this.mensaje = "No existen registros.";

			}

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

	public List getVclientesRemitosFacturarList() {
		return vclientesRemitosFacturarList;
	}

	public void setVclientesRemitosFacturarList(
			List vclientesRemitosFacturarList) {
		this.vclientesRemitosFacturarList = vclientesRemitosFacturarList;
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

	public Double getTotalfacturar() {
		return totalfacturar;
	}

	public void setTotalfacturar(Double totalfacturar) {
		this.totalfacturar = totalfacturar;
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

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getFiltroExpresoZona() {
		return filtroExpresoZona;
	}

	public void setFiltroExpresoZona(String filtroExpresoZona) {
		this.filtroExpresoZona = filtroExpresoZona;
	}

	public String getFiltroIdclie() {
		return filtroIdclie;
	}

	public void setFiltroIdclie(String filtroIdclie) {
		this.filtroIdclie = filtroIdclie;
	}

	public List getListExpresosZonas() {
		return listExpresosZonas;
	}

	public List getListTarjetasCredito() {
		return listTarjetasCredito;
	}

	public List getListLetrasIva() {
		return listLetrasIva;
	}

	public String getFiltroSucursalRemito() {
		return filtroSucursalRemito;
	}

	public void setFiltroSucursalRemito(String filtroSucursalRemito) {
		this.filtroSucursalRemito = filtroSucursalRemito;
	}

	public String getFiltroRemito() {
		return filtroRemito;
	}

	public void setFiltroRemito(String filtroRemito) {
		this.filtroRemito = filtroRemito;
	}

	public String getFiltroSucursalFactura() {
		return filtroSucursalFactura;
	}

	public String getFiltroTarjetaCredito() {
		return filtroTarjetaCredito;
	}

	public void setFiltroTarjetaCredito(String filtroTarjetaCredito) {
		this.filtroTarjetaCredito = filtroTarjetaCredito;
	}

	public void setFiltroSucursalFactura(String filtroSucursalFactura) {
		this.filtroSucursalFactura = filtroSucursalFactura;
	}

	public String getFiltroLetraIva() {
		return filtroLetraIva;
	}

	public void setFiltroLetraIva(String filtroLetraIva) {
		this.filtroLetraIva = filtroLetraIva;
	}

	public String getFiltroValorComprobante() {
		return filtroValorComprobante;
	}

	public void setFiltroValorComprobante(String filtroValorComprobante) {
		this.filtroValorComprobante = filtroValorComprobante;
	}

	public String getFiltroCondicion() {
		return filtroCondicion;
	}

	public void setFiltroCondicion(String filtroCondicion) {
		this.filtroCondicion = filtroCondicion;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List getListSucursales() {
		return listSucursales;
	}

	public List getListCondicion() {
		return listCondicion;
	}

	public String[] getIdremitocliente() {
		return idremitocliente;
	}

	public void setIdremitocliente(String[] idremitocliente) {
		this.idremitocliente = idremitocliente;
	}

	public BigDecimal getIdclub() {
		return idclub;
	}

	public void setIdclub(BigDecimal idclub) {
		this.idclub = idclub;
	}

	public List getListClub() {
		return listClub;
	}

	// 20120307 - EJV - Mantis 819 -->

	public int getAnioActual() {
		return anioActual;
	}

	public String getFiltroFechaMes() {
		return filtroFechaMes;
	}

	public void setFiltroFechaMes(String filtroFechaMes) {
		this.filtroFechaMes = filtroFechaMes;
	}

	public String getFiltroFechaAnio() {
		return filtroFechaAnio;
	}

	public void setFiltroFechaAnio(String filtroFechaAnio) {
		this.filtroFechaAnio = filtroFechaAnio;
	}

	// <--

}
