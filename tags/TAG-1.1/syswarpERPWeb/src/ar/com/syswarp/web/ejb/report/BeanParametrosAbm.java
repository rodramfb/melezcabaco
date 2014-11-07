/* 
 javabean para la entidad: parametros
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Jul 03 17:43:40 GMT-03:00 2006 
 
 Para manejar la pagina: parametrosAbm.jsp
 
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

public class BeanParametrosAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanParametrosAbm.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List parametrosList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idparametro = BigDecimal.valueOf(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private List datasourceList = new ArrayList();

	private Hashtable htDT = new Hashtable();

	private Iterator iterDT = null;

	private List tipoParametroList = new ArrayList();

	private Hashtable htTP = new Hashtable();

	private Iterator iterTP = null;

	public BeanParametrosAbm() {
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
		Report parametros = Common.getReport();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idparametro.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = parametros.parametrosDelete(idparametro);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idparametro.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("parametrosFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request.getRequestDispatcher("parametrosFrm.jsp");
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

			this.datasourceList = parametros.getDatasourcesAll();
			this.iterDT = this.datasourceList.iterator();
			while (iterDT.hasNext()) {
				String[] dtCampos = (String[]) iterDT.next();
				this.htDT.put(dtCampos[0], dtCampos[1]);
			}

			this.tipoParametroList = parametros.getTipoParametrosAll();
			this.iterTP = this.tipoParametroList.iterator();
			while (iterTP.hasNext()) {
				String[] tpCampos = (String[]) iterTP.next();
				this.htTP.put(tpCampos[0], tpCampos[1]);
			}

			if (buscar) {
				String[] campos = { "idparametro", "parametro" };
				this.totalRegistros = parametros.getTotalEntidadOcu(
						"parametros", campos, this.ocurrencia);
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
				this.parametrosList = parametros.getParametrosOcu(this.limit,
						this.offset, this.ocurrencia);
			} else {
				this.totalRegistros = parametros.getTotalEntidad("parametros");
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.parametrosList = parametros.getParametrosAll(this.limit,
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

	public List getParametrosList() {
		return parametrosList;
	}

	public void setParametrosList(List parametrosList) {
		this.parametrosList = parametrosList;
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

	public BigDecimal getIdparametro() {
		return idparametro;
	}

	public void setIdparametro(BigDecimal idparametro) {
		this.idparametro = idparametro;
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

	public Object recuperarValueHtDT(Object obj) {
		try {
			return this.htDT.get(obj);
		} catch (Exception e) {
			log.error("Object recuperarValueHtDT( Object obj ): " + e);
			return obj;
		}
	}

	public Object recuperarValueHtTP(Object obj) {
		try {
			return this.htTP.get(obj);
		} catch (Exception e) {
			log.error("Object recuperarValueHtTP( Object obj ): " + e);
			return obj;
		}
	}

}
