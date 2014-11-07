/* 
 javabean para la entidad: rrhhactividad
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Dec 14 15:38:32 GMT-03:00 2006 
 
 Para manejar la pagina: rrhhactividadAbm.jsp
 
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

public class BeanRrhhactividadAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanRrhhactividadAbm.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List rrhhactividadList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idactividad;
	
	private BigDecimal idempresa;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanRrhhactividadAbm() {
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
		RRHH rrhhactividad = Common.getRrhh();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idactividad == null || idactividad.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = rrhhactividad
							.rrhhactividadDelete(idactividad,this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idactividad == null || idactividad.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("rrhhactividadFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("rrhhactividadFrm.jsp");
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
				String[] campos = { "idactividad", "actividad" };
				this.totalRegistros = rrhhactividad.getTotalrrhhactividadOcu(
						this.idempresa, campos, this.ocurrencia);
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
				this.rrhhactividadList = rrhhactividad.getRrhhactividadOcu(
						this.limit, this.offset, this.ocurrencia,this.idempresa);
			} else {
				this.totalRegistros = rrhhactividad
						.getTotalrrhhactividadAll(this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.rrhhactividadList = rrhhactividad.getRrhhactividadAll(
						this.limit, this.offset,this.idempresa);
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

	public List getRrhhactividadList() {
		return rrhhactividadList;
	}

	public void setRrhhactividadList(List rrhhactividadList) {
		this.rrhhactividadList = rrhhactividadList;
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

	public BigDecimal getIdactividad() {
		return idactividad;
	}

	public void setIdactividad(BigDecimal idactividad) {
		this.idactividad = idactividad;
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
