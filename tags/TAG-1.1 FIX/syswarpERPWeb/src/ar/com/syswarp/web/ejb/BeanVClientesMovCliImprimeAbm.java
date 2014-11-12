/* 
   javabean para la entidad: vClientesMovCliImprime
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Jan 13 09:57:36 ART 2012 
   
   Para manejar la pagina: vClientesMovCliImprimeAbm.jsp
      
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

public class BeanVClientesMovCliImprimeAbm implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanVClientesMovCliImprimeAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vClientesMovCliImprimeList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal nrointerno;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String sort = "";

	private String filtroIdclie = "";

	private String filtroLetraIva = "";

	private String filtroSucursal = "";

	private String filtroNroComprobante = "";

	private String filtroFechamov = "";

	private String filtroTipomov = "";

	private List listSucursales = new ArrayList();

	private List listLetrasIva = new ArrayList();

	private List listTipoMov = new ArrayList();

	public BeanVClientesMovCliImprimeAbm() {
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
		Clientes clientes = Common.getClientes();
		String filtro = " WHERE TRUE ";
		String auxFiltro = "";
		try {

			this.listSucursales = Common.getTesoreria().getCajaSucursalesAll(
					250, 0, this.idempresa);

			this.listLetrasIva = Common.getClientes()
					.getClientestablaivaLetrasAll(this.idempresa);

			this.listTipoMov = Common.getClientes().getClientesTipoMovAll(200,
					0, this.idempresa);

			if (!Common.setNotNull(this.filtroIdclie).equals("")
					&& Common.esEntero(this.filtroIdclie)) {
				auxFiltro += " AND cliente = " + this.filtroIdclie;
			}

			if (!Common.setNotNull(this.filtroLetraIva).equals("")) {
				auxFiltro += " AND letra = '" + this.filtroLetraIva + "'";
			}

			if (!Common.setNotNull(this.filtroSucursal).equals("")) {
				auxFiltro += " AND sucursal= " + this.filtroSucursal;
			}

			if (!Common.setNotNull(this.filtroNroComprobante).equals("")) {
				auxFiltro += " AND comprob= " + this.filtroNroComprobante;
			}

			if (!Common.setNotNull(this.filtroTipomov).equals("")) {
				auxFiltro += " AND tipomov= " + this.filtroTipomov;
			}

			if (!Common.setNotNull(this.filtroFechamov).equals("")
					&& Common.isFormatoFecha(this.filtroFechamov)
					&& Common.isFechaValida(this.filtroFechamov)) {
				auxFiltro += " AND fechamov = TO_DATE('" + this.filtroFechamov
						+ "', 'dd/mm/yyyy') ";
			}

			filtro += auxFiltro;

			this.totalRegistros = clientes.getTotalEntidadFiltro(
					"vClientesMovCliImprime", filtro, this.idempresa);
			this.totalPaginas = (this.totalRegistros / this.limit) + 1;
			if (this.totalPaginas < this.paginaSeleccion)
				this.paginaSeleccion = this.totalPaginas;
			this.offset = (this.paginaSeleccion - 1) * this.limit;
			if (this.totalRegistros == this.limit) {
				this.offset = 0;
				this.totalPaginas = 1;
			}

			if (Common.setNotNull(this.sort).equals(""))
				this.sort = "  fechamov DESC, sucursal, comprob  ";

			this.vClientesMovCliImprimeList = clientes
					.getVClientesMovCliImprimeAll(this.limit, this.offset,
							auxFiltro, " ORDER BY " + this.sort, this.idempresa);

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

	public List getVClientesMovCliImprimeList() {
		return vClientesMovCliImprimeList;
	}

	public void setVClientesMovCliImprimeList(List vClientesMovCliImprimeList) {
		this.vClientesMovCliImprimeList = vClientesMovCliImprimeList;
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

	public BigDecimal getNrointerno() {
		return nrointerno;
	}

	public void setNrointerno(BigDecimal nrointerno) {
		this.nrointerno = nrointerno;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getFiltroIdclie() {
		return filtroIdclie;
	}

	public void setFiltroIdclie(String filtroIdclie) {
		this.filtroIdclie = filtroIdclie;
	}

	public String getFiltroLetraIva() {
		return filtroLetraIva;
	}

	public void setFiltroLetraIva(String filtroLetraIva) {
		this.filtroLetraIva = filtroLetraIva;
	}

	public String getFiltroSucursal() {
		return filtroSucursal;
	}

	public void setFiltroSucursal(String filtroSucursal) {
		this.filtroSucursal = filtroSucursal;
	}

	public String getFiltroNroComprobante() {
		return filtroNroComprobante;
	}

	public void setFiltroNroComprobante(String filtroNroComprobante) {
		this.filtroNroComprobante = filtroNroComprobante;
	}

	public String getFiltroFechamov() {
		return filtroFechamov;
	}

	public void setFiltroFechamov(String filtroFechamov) {
		this.filtroFechamov = filtroFechamov;
	}

	public String getFiltroTipomov() {
		return filtroTipomov;
	}

	public void setFiltroTipomov(String filtroTipomov) {
		this.filtroTipomov = filtroTipomov;
	}

	public List getListSucursales() {
		return listSucursales;
	}

	public List getListLetrasIva() {
		return listLetrasIva;
	}

	public List getListTipoMov() {
		return listTipoMov;
	}

}
