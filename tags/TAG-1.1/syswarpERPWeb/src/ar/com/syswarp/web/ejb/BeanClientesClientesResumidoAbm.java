/* 
 javabean para la entidad: clientesClientes
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Feb 12 11:19:57 ART 2008 
 
 Para manejar la pagina: clientesClientesAbm.jsp
 
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

public class BeanClientesClientesResumidoAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanClientesClientesAbm.class);

	private BigDecimal idempresa = new BigDecimal(-1);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List clientesClientesList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idcliente;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanClientesClientesResumidoAbm() {
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
		Clientes clientesClientes = Common.getClientes();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idcliente.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = clientesClientes.clientesClientesDelete(
							idcliente, idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idcliente.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("clientesClientesResumidoFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("clientesClientesResumidoFrm.jsp");
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
			if (buscar) {

				String entidad = "("
						+ "SELECT cl.idcliente, cl.razon, cl.nrodocumento, cl.idempresa"
						+ "  FROM clientesclientes cl "
						+ "       INNER JOIN globaltiposdocumentos td ON cl.idtipodocumento = td.idtipodocumento and cl.idempresa = td.idempresa  "
						+ "       LEFT  JOIN vclientesestadoshoy ce ON cl.idcliente = ce.idcliente and  cl.idempresa = ce.idempresa "
						+ "  WHERE (UPPER(cl.razon) LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' OR cl.idcliente::VARCHAR LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' OR cl.idcliente  IN (SELECT idcliente FROM clientetarjetascredito WHERE nrotarjeta::VARCHAR LIKE '"
						+ ocurrencia.toUpperCase().trim()
						+ "%' AND idempresa = " + idempresa.toString()
						+ ")  OR cl.nrodocumento::VARCHAR LIKE '"
						+ ocurrencia.toUpperCase().trim()
						+ "%')  AND cl.idempresa = " + idempresa.toString() + " )cliente";
                String filtro = " WHERE TRUE ";
                
				this.totalRegistros = clientesClientes.getTotalEntidadFiltro(
						entidad, filtro,
						this.idempresa);
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
				this.clientesClientesList = clientesClientes
						.getClientesClientesOcu(this.limit, this.offset,
								this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = clientesClientes.getTotalEntidad(
						"clientesClientes", this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.clientesClientesList = clientesClientes
						.getClientesClientesAll(this.limit, this.offset,
								this.idempresa);
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
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

	public List getClientesClientesList() {
		return clientesClientesList;
	}

	public void setClientesClientesList(List clientesClientesList) {
		this.clientesClientesList = clientesClientesList;
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
}
