/* 
 javabean para la entidad: proveedoMovProv
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jul 27 09:30:45 GMT-03:00 2006 
 
 Para manejar la pagina: proveedoMovProvAbm.jsp
 
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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanLovProveedoMovProvAplicar implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanLovProveedoMovProvAplicar.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private long limit = 10;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List proveedoMovProvList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal nrointerno = BigDecimal.valueOf(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	boolean buscar = false;

	private BigDecimal idproveedor = new BigDecimal(-1);

	private String proveedor = "";

	private String tipomovIN = "0";
	
	private String tipo = "";	

	public BeanLovProveedoMovProvAplicar() {
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
		Proveedores proveedoMovProv = Common.getProveedores();
		try {

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			String filtro = " WHERE idproveedor = " + this.idproveedor
					+ " AND saldo > 0 AND tipomov IN (" + this.tipomovIN +  ") ";
			if (buscar) {
				filtro += " AND comprob::VARCHAR like '" + this.ocurrencia + "%'";

				this.totalRegistros = proveedoMovProv.getTotalEntidadFiltro(
						"proveedoMovProv", filtro, this.idempresa);
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
				this.proveedoMovProvList = proveedoMovProv
						.getLovCajaMovProvOcu(this.limit, this.offset,
								this.idproveedor, this.tipomovIN,this.ocurrencia,
								this.idempresa);
			} else {
				this.totalRegistros = proveedoMovProv.getTotalEntidadFiltro(
						"proveedoMovProv", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.proveedoMovProvList = proveedoMovProv
						.getLovCajaMovProvAll(this.limit, this.offset,
								this.idproveedor, this.tipomovIN, this.idempresa);
			}
			if (this.idproveedor.compareTo(new BigDecimal(0)) <= 0)
				this.mensaje = "Es necesario seleccionar proveedor.";
			else if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
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

	public List getProveedoMovProvList() {
		return proveedoMovProvList;
	}

	public void setProveedoMovProvList(List proveedoMovProvList) {
		this.proveedoMovProvList = proveedoMovProvList;
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

	public BigDecimal getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(BigDecimal idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getTipomovIN() {
		return tipomovIN;
	}

	public void setTipomovIN(String tipomovIN) {
		this.tipomovIN = tipomovIN;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
