/* 
   javabean para la entidad: vClientesRemitosDistribucion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Aug 23 13:37:10 ART 2010 
   
   Para manejar la pagina: vClientesRemitosDistribucionAbm.jsp
      
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

public class BeanVClientesRemitosDistribucionAbm implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanVClientesRemitosDistribucionAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vClientesRemitosDistribucionList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idremitocliente;

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private String sucursal = "";

	private String remitocliente = "";

	private String tipopedido = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanVClientesRemitosDistribucionAbm() {
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

			this.vClientesRemitosDistribucionList = clientes
					.getVClientesRemitosDistribucionPK(this.idremitocliente,
							this.idempresa);

			this.totalRegistros = vClientesRemitosDistribucionList.size();
			
			this.totalPaginas = (this.totalRegistros / this.limit) + 1;

			if (this.totalPaginas < this.paginaSeleccion) {
				this.paginaSeleccion = this.totalPaginas;
			}

			this.offset = (this.paginaSeleccion - 1) * this.limit;

			if (this.totalRegistros == this.limit) {
				this.offset = 0;
				this.totalPaginas = 1;
			}

			if (this.totalRegistros < 1) {
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

	public List getVClientesRemitosDistribucionList() {
		return vClientesRemitosDistribucionList;
	}

	public void setVClientesRemitosDistribucionList(
			List vClientesRemitosDistribucionList) {
		this.vClientesRemitosDistribucionList = vClientesRemitosDistribucionList;
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

	public BigDecimal getIdremitocliente() {
		return idremitocliente;
	}

	public void setIdremitocliente(BigDecimal idremitocliente) {
		this.idremitocliente = idremitocliente;
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

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getRemitocliente() {
		return remitocliente;
	}

	public void setRemitocliente(String remitocliente) {
		this.remitocliente = remitocliente;
	}

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

}
