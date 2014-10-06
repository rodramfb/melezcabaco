/* 
 javabean para la entidad: RRHHbusquedasLaborales
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Oct 10 16:05:59 ART 2007 
 
 Para manejar la pagina: RRHHbusquedasLaboralesAbm.jsp
 
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

public class BeanRRHHbusquedasLaboralesAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanRRHHbusquedasLaboralesAbm.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List RRHHbusquedasLaboralesList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idbusquedalaboral;

	private String mensaje = "";

	private BigDecimal idempresa;

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanRRHHbusquedasLaboralesAbm() {
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
		RRHH RRHHbusquedasLaborales = Common.getRrhh();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idbusquedalaboral == null
						|| idbusquedalaboral.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = RRHHbusquedasLaborales
							.RRHHbusquedasLaboralesDelete(idbusquedalaboral,
									this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idbusquedalaboral == null
						|| idbusquedalaboral.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("RRHHbusquedasLaboralesFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("RRHHbusquedasLaboralesFrm.jsp");
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
				String[] campos = { "idbusquedalaboral", "referencia",
						"fechaBusquedaDesde", "fechaBusquedaHasta",
						"seniority", "lugarTrabajo", "descripcionProyecto",
						"descripcionTarea", "skillExcluyente", "skillDeseable",
						"idioma", "posibilidadDeRenovacion" };
				this.totalRegistros = RRHHbusquedasLaborales
						.getTotalEntidadOcu("RRHHbusquedasLaborales", campos,
								this.ocurrencia, this.idempresa);
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
				this.RRHHbusquedasLaboralesList = RRHHbusquedasLaborales
						.getRRHHbusquedasLaboralesOcu(this.limit, this.offset,
								this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = RRHHbusquedasLaborales
						.getTotalEntidad("RRHHbusquedasLaborales", this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.RRHHbusquedasLaboralesList = RRHHbusquedasLaborales
						.getRRHHbusquedasLaboralesAll(this.limit, this.offset,
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

	public List getRRHHbusquedasLaboralesList() {
		return RRHHbusquedasLaboralesList;
	}

	public void setRRHHbusquedasLaboralesList(List RRHHbusquedasLaboralesList) {
		this.RRHHbusquedasLaboralesList = RRHHbusquedasLaboralesList;
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

	public BigDecimal getIdbusquedalaboral() {
		return idbusquedalaboral;
	}

	public void setIdbusquedalaboral(BigDecimal idbusquedalaboral) {
		this.idbusquedalaboral = idbusquedalaboral;
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
