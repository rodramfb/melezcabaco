/* 
 javabean para la entidad: crmfamiliares
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jun 14 17:25:20 GMT-03:00 2007 
 
 Para manejar la pagina: crmfamiliaresAbm.jsp
 
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

public class BeanCrmfamiliaresLov implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanCrmfamiliaresLov.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List crmfamiliaresList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idfamiliar;

	private BigDecimal idempresa;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private BigDecimal idindividuos = new BigDecimal(-1);

	boolean buscar = false;

	public BeanCrmfamiliaresLov() {
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
		CRM crmfamiliares = Common.getCrm();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idfamiliar == null
						|| idfamiliar.compareTo(new BigDecimal(0)) == 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = crmfamiliares.crmfamiliaresDelete(
							idfamiliar, this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idfamiliar == null
						|| idfamiliar.compareTo(new BigDecimal(0)) == 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("crmfamiliaresFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("crmfamiliaresFrm.jsp");
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

				this.ocurrencia = this.ocurrencia.toUpperCase();

				String filtro = " WHERE idindividuos = " + idindividuos;
				filtro += " AND (    UPPER(razon_nombre) LIKE '%"
						+ this.ocurrencia + "%' ";
				filtro += "       OR UPPER(nexofamiliar) LIKE '%"
						+ this.ocurrencia + "%' ";
				filtro += "       OR UPPER(nombre) LIKE '%" + this.ocurrencia
						+ "%' ";
				filtro += "       OR UPPER(profesion) LIKE '%"
						+ this.ocurrencia + "%' ";
				filtro += "       OR UPPER(actividad) LIKE '%"
						+ this.ocurrencia + "%' ";
				filtro += "       OR UPPER(email) LIKE '%" + this.ocurrencia
						+ "%' ";
				filtro += "       OR UPPER(telefonos_part) LIKE '%"
						+ this.ocurrencia + "%' ";
				filtro += "       OR UPPER(celular) LIKE '%" + this.ocurrencia
						+ "%' ";
				filtro += "       OR UPPER(fechanacimiento) LIKE '%"
						+ this.ocurrencia + "%' ";
				filtro += "       OR UPPER(deportes) LIKE '%" + this.ocurrencia
						+ "%' ";
				filtro += "       OR UPPER(actividad_social) LIKE '%"
						+ this.ocurrencia + "%' ";
				filtro += "       OR UPPER(hobbies) LIKE '%" + this.ocurrencia
						+ "%' ";

				filtro += "       OR UPPER(observaciones) LIKE '%"
						+ this.ocurrencia + "%' ";
				filtro += "     ) ";

				this.totalRegistros = crmfamiliares.getTotalEntidadFiltro(
						"vcrmfamiliares", filtro, this.idempresa);
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
				this.crmfamiliaresList = crmfamiliares
						.getCrmfamiliaresIndividuoOcu(this.limit, this.offset,
								this.ocurrencia, this.idindividuos,
								this.idempresa);
			} else {
				String filtro = " WHERE idindividuos = "
						+ this.idindividuos.toString();
				this.totalRegistros = crmfamiliares.getTotalEntidadFiltro(
						"crmfamiliares", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.crmfamiliaresList = crmfamiliares
						.getCrmfamiliaresIndividuoAll(this.limit, this.offset,
								this.idindividuos, this.idempresa);
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

	public List getCrmfamiliaresList() {
		return crmfamiliaresList;
	}

	public void setCrmfamiliaresList(List crmfamiliaresList) {
		this.crmfamiliaresList = crmfamiliaresList;
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

	public BigDecimal getIdfamiliar() {
		return idfamiliar;
	}

	public void setIdfamiliar(BigDecimal idfamiliar) {
		this.idfamiliar = idfamiliar;
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

	public BigDecimal getIdindividuos() {
		return idindividuos;
	}

	public void setIdindividuos(BigDecimal idindividuos) {
		this.idindividuos = idindividuos;
	}
}
