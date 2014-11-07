/* 
   javabean para la entidad: vClientesAplicaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Sep 28 17:31:41 ART 2011 
   
   Para manejar la pagina: vClientesAplicacionesAbm.jsp
      
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

public class BeanVClientesAplicacionesAbm implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanVClientesAplicacionesAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vClientesAplicacionesList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private BigDecimal nrointerno = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanVClientesAplicacionesAbm() {
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
		try {

			String filtro = "  WHERE idcliente  = " + idcliente;
			String auxFiltro = "";

			if (this.nrointerno != null && this.nrointerno.longValue() > 0)
				auxFiltro = " AND nrointerno = " + this.nrointerno;

			filtro += auxFiltro;

			this.totalRegistros = clientes.getTotalEntidadFiltro(
					"vClientesAplicaciones", filtro, this.idempresa);

			this.totalPaginas = (this.totalRegistros / this.limit) + 1;
			if (this.totalPaginas < this.paginaSeleccion)
				this.paginaSeleccion = this.totalPaginas;
			this.offset = (this.paginaSeleccion - 1) * this.limit;
			if (this.totalRegistros == this.limit) {
				this.offset = 0;
				this.totalPaginas = 1;
			}

			this.vClientesAplicacionesList = clientes
					.getVClientesAplicacionesXCliente(this.limit, this.offset,
							this.idcliente, auxFiltro, this.idempresa);

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

	public List getVClientesAplicacionesList() {
		return vClientesAplicacionesList;
	}

	public void setVClientesAplicacionesList(List vClientesAplicacionesList) {
		this.vClientesAplicacionesList = vClientesAplicacionesList;
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

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
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
}
