/* 
 javabean para la entidad: tipo_grafico
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jul 04 14:21:40 GMT-03:00 2006 
 
 Para manejar la pagina: tipo_graficoAbm.jsp
 
 */
package ar.com.syswarp.web.ejb.report;

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

public class BeanTipo_graficoAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanTipo_graficoAbm.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List tipo_graficoList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idtipografico;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanTipo_graficoAbm() {
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
		Report reporting = Common.getReport();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idtipografico == null || idtipografico.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = reporting.tipo_graficoDelete(idtipografico);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idtipografico == null || idtipografico.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("tipo_graficoFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("tipo_graficoFrm.jsp");
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
				String[] campos = { "idtipografico", "tipografico" };
				this.totalRegistros = reporting.getTotalEntidadOcu(
						"tipo_grafico", campos, this.ocurrencia);
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
				this.tipo_graficoList = reporting.getTipo_graficoOcu(
						this.limit, this.offset, this.ocurrencia);
			} else {
				this.totalRegistros = reporting.getTotalEntidad("tipo_grafico");
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.tipo_graficoList = reporting.getTipo_graficoAll(
						this.limit, this.offset);
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

	public List getTipo_graficoList() {
		return tipo_graficoList;
	}

	public void setTipo_graficoList(List tipo_graficoList) {
		this.tipo_graficoList = tipo_graficoList;
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

	public BigDecimal getIdtipografico() {
		return idtipografico;
	}

	public void setIdtipografico(BigDecimal idtipografico) {
		this.idtipografico = idtipografico;
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
