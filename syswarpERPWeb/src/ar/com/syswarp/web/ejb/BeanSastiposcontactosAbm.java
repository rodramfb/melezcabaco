/* 
   javabean para la entidad: sastiposcontactos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri May 27 09:12:29 ART 2011 
   
   Para manejar la pagina: sastiposcontactosAbm.jsp
      
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

public class BeanSastiposcontactosAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanSastiposcontactosAbm.class);
	private SessionContext context;
	private BigDecimal idempresa = new BigDecimal(-1);
	private int limit = 15;
	private long offset = 0l;
	private long totalRegistros = 0l;
	private long totalPaginas = 0l;
	private long paginaSeleccion = 1l;
	private List sastiposcontactosList = new ArrayList();
	private String accion = "";
	private String ocurrencia = "";
	private BigDecimal idtipocontacto= new BigDecimal(-1);
	private String mensaje = "";
	private HttpServletRequest request;
	private HttpServletResponse response;
	boolean buscar = false;

	public BeanSastiposcontactosAbm() {
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
				if (idtipocontacto == null || idtipocontacto.longValue() < 0  ) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = clientes.sastiposcontactosDelete(
							idtipocontacto, this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idtipocontacto == null || idtipocontacto.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("sastiposcontactosFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("sastiposcontactosFrm.jsp");
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
				String entidad = "(SELECT  idtipocontacto,tipocontacto,usuarioalt,usuarioact,fechaalt,fechaact,idempresa FROM SASTIPOSCONTACTOS WHERE (UPPER(TIPOCONTACTO) LIKE '%" + ocurrencia.toUpperCase().trim() + "%')  AND idempresa = " + idempresa.toString()  + ")entidad";
				String filtro = " Where idempresa = "  + idempresa;
				String[] campos = { "idtipocontacto", "tipocontacto" };
				filtro += " and UPPER(TIPOCONTACTO) LIKE '%" + ocurrencia.toUpperCase().trim() + "%')  ";
				this.totalRegistros = clientes.getTotalEntidadFiltro(entidad, filtro, idempresa);
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
				this.sastiposcontactosList = clientes.getSastiposcontactosOcu(
						this.limit, this.offset, this.ocurrencia,
						this.idempresa);
			} else {
				this.totalRegistros = clientes.getTotalEntidad(
						"sastiposcontactos", this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.sastiposcontactosList = clientes.getSastiposcontactosAll(
						this.limit, this.offset, this.idempresa);
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

	public List getSastiposcontactosList() {
		return sastiposcontactosList;
	}

	public void setSastiposcontactosList(List sastiposcontactosList) {
		this.sastiposcontactosList = sastiposcontactosList;
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

	public BigDecimal getIdtipocontacto() {
		return idtipocontacto;
	}

	public void setIdtipocontacto(BigDecimal idtipocontacto) {
		this.idtipocontacto = idtipocontacto;
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
