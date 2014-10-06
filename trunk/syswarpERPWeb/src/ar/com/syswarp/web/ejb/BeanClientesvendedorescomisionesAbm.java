/* 
   javabean para la entidad: clientesvendedorescomisiones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Oct 26 10:25:12 ART 2012 
   
   Para manejar la pagina: clientesvendedorescomisionesAbm.jsp
      
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

public class BeanClientesvendedorescomisionesAbm implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanClientesvendedorescomisionesAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List clientesvendedorescomisionesList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private int idcomision = -1;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanClientesvendedorescomisionesAbm() {
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
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idcomision < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = clientes
							.clientesvendedorescomisionesDelete(idcomision);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idcomision < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("clientesvendedorescomisionesFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("clientesvendedorescomisionesFrm.jsp");
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
			String entidad = "(SELECT idcomision,descripcion,cantsociodesde,cantsociohasta,porcdeserdesde,porcdeserhasta,valorasociacion,porccartera,usuarioalt,usuarioact,fechaalt,fechaact,tipo FROM CLIENTESVENDEDORESCOMISIONES)entidad";
			String filtro =" where upper(descripcion) like '%"+ ocurrencia.trim().toUpperCase()+"%' or idcomision::varchar like '%"+ocurrencia.trim()+"%'";
			if (buscar) {
				this.totalRegistros = clientes
						.getTotalEntidadFiltro(entidad, filtro);
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
				this.clientesvendedorescomisionesList = clientes
						.getClientesvendedorescomisionesOcu(this.limit,
								this.offset, this.ocurrencia);
			} else {
				this.totalRegistros = clientes
						.getTotalEntidadGlobal("clientesvendedorescomisiones");
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.clientesvendedorescomisionesList = clientes
						.getClientesvendedorescomisionesAll(this.limit,
								this.offset);
			}
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

	public List getClientesvendedorescomisionesList() {
		return clientesvendedorescomisionesList;
	}

	public void setClientesvendedorescomisionesList(
			List clientesvendedorescomisionesList) {
		this.clientesvendedorescomisionesList = clientesvendedorescomisionesList;
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

	public int getIdcomision() {
		return idcomision;
	}

	public void setIdcomision(int idcomision) {
		this.idcomision = idcomision;
	}

}
