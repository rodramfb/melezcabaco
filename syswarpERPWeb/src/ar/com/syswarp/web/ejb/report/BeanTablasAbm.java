/* 
 javabean para la entidad: tablas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Jun 28 14:08:23 GMT-03:00 2006 
 
 Para manejar la pagina: tablasAbm.jsp
 
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
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanTablasAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanTablasAbm.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List tablasList = new ArrayList();

	private List datasourceList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private long idtabla = -1l;

	private String mensaje = "";

	private Hashtable htDT = new Hashtable();

	private Iterator iterDT = null;

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanTablasAbm() {
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
				if (idtabla < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = reporting.tablasDelete(idtabla);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idtabla < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request.getRequestDispatcher("tablasFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}

			if (this.accion.equalsIgnoreCase("tablas-graficos")) {
				if (idtabla < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {

					dispatcher = request
							.getRequestDispatcher("tablasGraficosAbm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}

			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request.getRequestDispatcher("tablasFrm.jsp");
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

			this.datasourceList = reporting.getDatasourcesAll();
			this.iterDT = this.datasourceList.iterator();
			while (iterDT.hasNext()) {
				String[] dtCampos = (String[]) iterDT.next();
				this.htDT.put(dtCampos[0], dtCampos[1]);
			}

			if (buscar) {
				String[] campos = { "idtabla", "tabla" };
				this.totalRegistros = reporting.getTotalEntidadOcu("tablas",
						campos, this.ocurrencia);
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
				this.tablasList = reporting.getTablasOcu(this.limit,
						this.offset, this.ocurrencia);
			} else {
				this.totalRegistros = reporting.getTotalEntidad("tablas");
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.tablasList = reporting.getTablasAll(this.limit,
						this.offset);
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

	public List getTablasList() {
		return tablasList;
	}

	public void setTablasList(List tablasList) {
		this.tablasList = tablasList;
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

	public long getIdtabla() {
		return idtabla;
	}

	public void setIdtabla(long codigo) {
		this.idtabla = codigo;
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

	public List getDatasourceList() {
		return datasourceList;
	}

	public void setDatasourceList(List datasourceList) {
		this.datasourceList = datasourceList;
	}

	public Object recuperarValueHtDT(Object obj) {
		try {
			return this.htDT.get(obj);
		} catch (Exception e) {
			log.error("Object recuperarValueHtDT( Object obj ): " + e);
			return obj;
		}
	}
}
