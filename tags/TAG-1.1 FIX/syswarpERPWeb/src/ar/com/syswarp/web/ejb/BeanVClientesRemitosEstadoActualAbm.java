/* 
   javabean para la entidad: vClientesRemitosEstadoActual
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Oct 20 14:23:07 ART 2010 
   
   Para manejar la pagina: vClientesRemitosEstadoActualAbm.jsp
      
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

public class BeanVClientesRemitosEstadoActualAbm implements SessionBean,
		Serializable {

	static Logger log = Logger
			.getLogger(BeanVClientesRemitosEstadoActualAbm.class);
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List vClientesRemitosEstadoActualList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idconformacion = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	// --

	private List listZonas = new ArrayList();

	private List listEstados = new ArrayList();

	private List listExpresos = new ArrayList();

	private String filtroFRDesde = "";

	private String filtroFRHasta = "";

	private String filtroZona = "";

	private String filtroEstados = "";

	private String filtroExpreso = "";

	private String filtroIdclie = "";

	// EJV - Mantis 757 - 20110726 -->

	private List listClub = new ArrayList();

	private BigDecimal idclub = new BigDecimal(-1);

	// <--

	// --

	public BeanVClientesRemitosEstadoActualAbm() {
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
		String filtro = "";

		try {

			this.listEstados = Common.getClientes()
					.getClientesRemitosEstadosAll(1000, 0, this.idempresa);
			this.listExpresos = clientes.getClientesexpresosAll(500, 0,
					this.idempresa);
			this.listZonas = clientes.getClienteszonasAll(1000, 0,
					this.idempresa);

			// EJV - Mantis 757 - 20110726 -->
			this.listClub = Common.getClientes().getClientesClubAll(100, 0,
					this.idempresa);
			// <--

			if (this.filtroFRDesde.equals("") && this.filtroFRHasta.equals("")) {
				this.mensaje = "Es necesario filtrar por fecha de remito desde / hasta.";
			} else if (!this.filtroFRDesde.equals("")
					&& this.filtroFRHasta.equals("")) {
				this.mensaje = "Seleccione fecha hasta.";

			} else if (this.filtroFRDesde.equals("")
					&& !this.filtroFRHasta.equals("")) {
				this.mensaje = "Seleccione fecha desde.";

			} else {

				java.sql.Date fdesde = (java.sql.Date) Common
						.setObjectToStrOrTime(this.filtroFRDesde, "StrToJSDate");
				java.sql.Date fhasta = (java.sql.Date) Common
						.setObjectToStrOrTime(this.filtroFRHasta, "StrToJSDate");

				if (fdesde.after(fhasta)) {

					this.mensaje = "Fecha desde debe ser anterior a fecha hasta.";
				} else {

					filtro += " WHERE fecharemito BETWEEN  '" + fdesde
							+ "'::DATE AND '" + fhasta + "'::DATE";

					if (!this.filtroEstados.equals("")) {
						filtro += " AND idestado = " + this.filtroEstados;
					}

					if (!this.filtroZona.equals("")) {
						filtro += " AND idzona = " + this.filtroZona;
					}

					if (!this.filtroExpreso.equals("")) {
						filtro += " AND idexpreso = " + this.filtroExpreso;
					}

					if (!Common.setNotNull(this.filtroIdclie).equals("")) {
						if (Common.esEntero(this.filtroIdclie))
							filtro += " AND idcliente = " + this.filtroIdclie;
						else
							this.mensaje = "Ingrese Nro. Cliente valido.";
					}

					// EJV - Mantis 757 - 20110726 -->
					if (this.idclub.intValue() > 0) {
						filtro += " AND idclub = " + this.idclub;
					}
					// <--

					this.totalRegistros = clientes.getTotalEntidadFiltro(
							"vClientesRemitosEstadoActual", filtro,
							this.idempresa);
					this.totalPaginas = (this.totalRegistros / this.limit) + 1;
					if (this.totalPaginas < this.paginaSeleccion)
						this.paginaSeleccion = this.totalPaginas;
					this.offset = (this.paginaSeleccion - 1) * this.limit;
					if (this.totalRegistros == this.limit) {
						this.offset = 0;
						this.totalPaginas = 1;
					}

					filtro = filtro.replace("WHERE", " AND ");

					this.vClientesRemitosEstadoActualList = clientes
							.getVClientesRemitosEstadoActualAll(this.limit,
									this.offset, filtro, this.idempresa);

					if (this.totalRegistros < 1 && this.mensaje.equals(""))
						this.mensaje = "No existen registros.";

				}

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

	public List getVClientesRemitosEstadoActualList() {
		return vClientesRemitosEstadoActualList;
	}

	public void setVClientesRemitosEstadoActualList(
			List vClientesRemitosEstadoActualList) {
		this.vClientesRemitosEstadoActualList = vClientesRemitosEstadoActualList;
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

	public BigDecimal getIdconformacion() {
		return idconformacion;
	}

	public void setIdconformacion(BigDecimal idconformacion) {
		this.idconformacion = idconformacion;
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

	public List getListZonas() {
		return listZonas;
	}

	public void setListZonas(List listZonas) {
		this.listZonas = listZonas;
	}

	public List getListEstados() {
		return listEstados;
	}

	public void setListEstados(List listEstados) {
		this.listEstados = listEstados;
	}

	public List getListExpresos() {
		return listExpresos;
	}

	public void setListExpresos(List listExpresos) {
		this.listExpresos = listExpresos;
	}

	public String getFiltroFRDesde() {
		return filtroFRDesde;
	}

	public void setFiltroFRDesde(String filtroFRDesde) {
		this.filtroFRDesde = filtroFRDesde;
	}

	public String getFiltroFRHasta() {
		return filtroFRHasta;
	}

	public void setFiltroFRHasta(String filtroFRHasta) {
		this.filtroFRHasta = filtroFRHasta;
	}

	public String getFiltroZona() {
		return filtroZona;
	}

	public void setFiltroZona(String filtroZona) {
		this.filtroZona = filtroZona;
	}

	public String getFiltroEstados() {
		return filtroEstados;
	}

	public void setFiltroEstados(String filtroEstados) {
		this.filtroEstados = filtroEstados;
	}

	public String getFiltroExpreso() {
		return filtroExpreso;
	}

	public void setFiltroExpreso(String filtroExpreso) {
		this.filtroExpreso = filtroExpreso;
	}

	public String getFiltroIdclie() {
		return filtroIdclie;
	}

	public void setFiltroIdclie(String filtroIdclie) {
		this.filtroIdclie = filtroIdclie;
	}

	// EJV - Mantis 757 - 20110726 -->

	public BigDecimal getIdclub() {
		return idclub;
	}

	public void setIdclub(BigDecimal idclub) {
		this.idclub = idclub;
	}

	public List getListClub() {
		return listClub;
	}

	// <--

}
