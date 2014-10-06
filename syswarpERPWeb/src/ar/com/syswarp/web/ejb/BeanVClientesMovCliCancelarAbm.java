/* 
   javabean para la entidad: vClientesMovCliCancelar
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 26 14:22:11 ART 2011 
   
   Para manejar la pagina: vClientesMovCliCancelarAbm.jsp
      
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

public class BeanVClientesMovCliCancelarAbm implements SessionBean,
		Serializable {
	static Logger log = Logger.getLogger(BeanVClientesMovCliCancelarAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List vClientesMovCliCancelarList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal nrointerno = new BigDecimal(0);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String usuarioalt = "";

	private List listSucursales = new ArrayList();

	private List listClub = new ArrayList();

	private List listTipoComp = new ArrayList();

	private BigDecimal idclub = new BigDecimal(-1);

	private String filtroIdcliente = "";

	private String filtroSucursalFactura = "";

	boolean buscar = false;

	int ejercicioactivo = 0;

	// 20121005 - EJV - Mantis 882 -->

	private List listMotivosNc = new ArrayList();

	private BigDecimal idmotivonc = new BigDecimal(-1);

	// <--

	public BeanVClientesMovCliCancelarAbm() {
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

		Clientes clientes = Common.getClientes();

		try {

			String filtro = " WHERE tipomov < 3   AND importe <> 0 ";

			this.listClub = Common.getClientes().getClientesClubAll(100, 0,
					this.idempresa);

			this.listSucursales = Common.getTesoreria().getCajaSucursalesAll(
					250, 0, this.idempresa);

			// 20121005 - EJV - Mantis 882 -->

			this.listMotivosNc = Common.getClientes()
					.getClientesMovCliMotivosNcAll(250, 0, this.idempresa);

			// <--

			if (this.accion.equalsIgnoreCase("emitirnc")) {
				if (nrointerno.longValue() == 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else if (this.idmotivonc.longValue() < 1) {
					this.mensaje = "Es necesario seleccionar motivo para NC.";
				} else {

					this.mensaje = clientes.clientesMovimientoClienteNC(
							this.nrointerno, this.ejercicioactivo,
							this.idmotivonc, this.usuarioalt, this.idempresa);
				}
			}

			// 

			if (!Common.setNotNull(this.filtroIdcliente).equals("")
					&& Common.esEntero(this.filtroIdcliente)) {
				filtro += " AND  idcliente = "
						+ Common.setNotNull(this.filtroIdcliente);

				this.totalRegistros = clientes.getTotalEntidadFiltro(
						"vclientesmovclicancelar", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.vClientesMovCliCancelarList = clientes
						.getVClientesMovCliCancelarAll(this.limit, this.offset,
								filtro, this.idempresa);

			} else
				this.mensaje = "Es necesario filtrar por Nro. Cliente.";
			//

			if (this.totalRegistros < 1
					&& Common.setNotNull(this.mensaje).equals(""))
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

	public List getVClientesMovCliCancelarList() {
		return vClientesMovCliCancelarList;
	}

	public void setVClientesMovCliCancelarList(List vClientesMovCliCancelarList) {
		this.vClientesMovCliCancelarList = vClientesMovCliCancelarList;
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

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public BigDecimal getIdclub() {
		return idclub;
	}

	public void setIdclub(BigDecimal idclub) {
		this.idclub = idclub;
	}

	public String getFiltroIdcliente() {
		return filtroIdcliente;
	}

	public void setFiltroIdcliente(String filtroIdclie) {
		this.filtroIdcliente = filtroIdclie;
	}

	public String getFiltroSucursalFactura() {
		return filtroSucursalFactura;
	}

	public void setFiltroSucursalFactura(String filtroSucursalFactura) {
		this.filtroSucursalFactura = filtroSucursalFactura;
	}

	public List getListSucursales() {
		return listSucursales;
	}

	public List getListClub() {
		return listClub;
	}

	public List getListTipoComp() {
		return listTipoComp;
	}

	public int getEjercicioactivo() {
		return ejercicioactivo;
	}

	public void setEjercicioactivo(int ejercicioactivo) {
		this.ejercicioactivo = ejercicioactivo;
	}

	// 20121005 - EJV - Mantis 882 -->

	public BigDecimal getIdmotivonc() {
		return idmotivonc;
	}

	public void setIdmotivonc(BigDecimal idmotivonc) {
		this.idmotivonc = idmotivonc;
	}

	public List getListMotivosNc() {
		return listMotivosNc;
	}

	// <--
}
