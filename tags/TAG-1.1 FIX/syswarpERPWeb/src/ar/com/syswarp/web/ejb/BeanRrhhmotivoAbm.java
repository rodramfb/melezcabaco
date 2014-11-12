/* 
   javabean para la entidad: rrhhmotivobaja
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Jul 16 10:03:59 ART 2012 
   
   Para manejar la pagina: rrhhmotivobajaAbm.jsp
      
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

public class BeanRrhhmotivoAbm implements SessionBean, Serializable {
	
	static Logger log = Logger.getLogger(BeanRrhhmotivoAbm.class);
	
	private SessionContext context;
	
	private BigDecimal idempresa = new BigDecimal(-1);
	
	private int limit = 15;
	
	private long offset = 0l;
	
	private long totalRegistros = 0l;
	
	private long totalPaginas = 0l;
	
	private long paginaSeleccion = 1l;
	
	private List rrhhmotivoList = new ArrayList();
	
	private String accion = "";
	
	private String ocurrencia = "";
	
	private BigDecimal idmotivo = new BigDecimal(-1);
	
	private String mensaje = "";
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	boolean buscar = false;

	public BeanRrhhmotivoAbm() {
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
		RRHH rrhh = Common.getRrhh();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idmotivo.longValue() < 0 || idmotivo == null ) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = rrhh.rrhhmotivoDelete(idmotivo,
							this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idmotivo.longValue() < 0 || idmotivo == null) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("rrhhmotivoFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("rrhhmotivoFrm.jsp");
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
				String entidad = "(SELECT  idmotivo,motivo,fechaalt,usuarioalt,usuarioact,fechaact,idempresa FROM RRHHMOTIVO WHERE (UPPER(MOTIVO) LIKE '%" + ocurrencia.toUpperCase().trim() + "%' or idmotivo::varchar like '%"+ocurrencia.toUpperCase().trim()+"%')  AND idempresa = " + idempresa.toString() +")entidad";
				String filtro = " Where idempresa = " + idempresa.toString()
							+ " and (UPPER(MOTIVO) LIKE '%" + ocurrencia.toUpperCase().trim() + "%' or idmotivo::varchar like '%"+ocurrencia.toUpperCase().trim()+"%')";
				this.totalRegistros = rrhh.getTotalEntidadFiltro(entidad, filtro, idempresa);
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
				this.rrhhmotivoList = rrhh.getRrhhmotivoOcu(this.limit,
						this.offset, this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = rrhh.getTotalEntidad("rrhhmotivo",
						this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.rrhhmotivoList = rrhh.getRrhhmotivoAll(this.limit,
						this.offset, this.idempresa);
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

	public List getRrhhmotivoList() {
		return rrhhmotivoList;
	}

	public void setRrhhmotivoList(List rrhhmotivoList) {
		this.rrhhmotivoList = rrhhmotivoList;
	}

	public BigDecimal getIdmotivo() {
		return idmotivo;
	}

	public void setIdmotivo(BigDecimal idmotivo) {
		this.idmotivo = idmotivo;
	}
	
	
}
