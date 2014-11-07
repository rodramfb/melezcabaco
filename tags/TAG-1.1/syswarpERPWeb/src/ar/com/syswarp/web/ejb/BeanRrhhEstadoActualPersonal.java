/* 
   javabean para la entidad: rrhhpersonal
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 22 09:44:28 ACT 2009 
   
   Para manejar la pagina: rrhhpersonalAbm.jsp
      
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

public class BeanRrhhEstadoActualPersonal implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanRrhhEstadoActualPersonal.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List rrhhNoEmpleadosList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal legajo;

	private BigDecimal idempresa;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanRrhhEstadoActualPersonal() {
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
		RRHH rrhhpersonal = Common.getRrhh();
		try {
			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			if (legajo.longValue()>0)
			{
				buscar = true;
				ocurrencia = legajo.toString();
			}
			if (buscar) {
				String entidad = "(Select estados.legajo,estados.apellido,estados.fecha,estados.idmotivo,mot.motivo,estados.idrazon,raz.razon ,estados.observacion, estados.esempleado,estados.idempresa "
						+ " from rrhhestadosempleado estados "
						+ " inner join rrhhmotivo mot on (estados.idmotivo = mot.idmotivo and estados.idempresa = mot.idempresa)"
						+ " inner join rrhhrazones raz on (estados.idrazon = raz.idrazon and estados.idempresa = raz.idempresa)"
						+ " Where estados.idempresa = "
						+ idempresa.toString()
						+ " and (legajo::varchar like '%"
						+ ocurrencia.trim()
						+ "%' or upper(apellido) like '%"
						+ ocurrencia.toUpperCase().trim() + "%' ))entidad";
				String filtro = "Where idempresa = " + idempresa.toString()
						+ " and (legajo::VARCHAR like '%" + ocurrencia
						+ "%' OR " + " UPPER(apellido) LIKE '%"
						+ ocurrencia.toUpperCase() + "%')";
				this.totalRegistros = rrhhpersonal.getTotalEntidadFiltro(
						entidad, filtro, idempresa);
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
				this.rrhhNoEmpleadosList = rrhhpersonal
						.getRrhhListNoEmpleadosOcu(limit,offset,ocurrencia, idempresa);
			} else {
				String entidad  ="(Select estados.legajo,estados.apellido,estados.fecha,estados.idmotivo,mot.motivo,estados.idrazon,raz.razon ,estados.observacion, estados.esempleado, estados.idempresa "
						+ " from rrhhestadosempleado estados "
						+ " inner join rrhhmotivo mot on (estados.idmotivo = mot.idmotivo and estados.idempresa = mot.idempresa)"
						+ " inner join rrhhrazones raz on (estados.idrazon = raz.idrazon and estados.idempresa = raz.idempresa) where estados.idempresa = "+idempresa.toString()+")entidad";
				this.totalRegistros = rrhhpersonal.getTotalEntidad(
						entidad , this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.rrhhNoEmpleadosList = rrhhpersonal
						.getRrhhListNoEmpleadosAll(limit, offset,this.idempresa);
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

	public List getRrhhNoEmpleadosList() {
		return rrhhNoEmpleadosList;
	}

	public void setRrhhNoEmpleadosList(List rrhhNoEmpleadosList) {
		this.rrhhNoEmpleadosList = rrhhNoEmpleadosList;
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

	public BigDecimal getLegajo() {
		return legajo;
	}

	public void setLegajo(BigDecimal legajo) {
		this.legajo = legajo;
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
