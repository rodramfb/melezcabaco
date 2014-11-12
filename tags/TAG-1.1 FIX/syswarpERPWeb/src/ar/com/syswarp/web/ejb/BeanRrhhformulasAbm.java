/* 
   javabean para la entidad: rrhhformulas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 04 17:02:24 ART 2012 
   
   Para manejar la pagina: rrhhformulasAbm.jsp
      
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

public class BeanRrhhformulasAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanRrhhformulasAbm.class);
	private SessionContext context;
	private BigDecimal idempresa = new BigDecimal(-1);
	
	private int limit = 15;
	
	private long offset = 0l;
	
	private long totalRegistros = 0l;
	
	private long totalPaginas = 0l;
	
	private long paginaSeleccion = 1l;
	
	private List rrhhformulasList = new ArrayList();
	
	private String accion = "";
	
	private String ocurrencia = "";
	
	private BigDecimal idformula = new BigDecimal(-1);
	
	private String mensaje = "";
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	boolean buscar = false;

	
	public BeanRrhhformulasAbm() {
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
		RRHH recursoshumanos = Common.getRrhh();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idformula == null ||idformula.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = recursoshumanos.rrhhformulasDelete(idformula,
							this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idformula == null  || idformula.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("rrhhformulasFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("rrhhformulasFrm.jsp");
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
				String entidad = "(SELECT  idformula,formula,descripcion,sql,idempresa,usuarioalt,usuarioact,fechaalt,fechaact"
								+" FROM RRHHFORMULAS WHERE (UPPER(FORMULA) LIKE '%" + ocurrencia.toUpperCase().trim() + "%')  or (idformula::varchar) like '%"+ocurrencia.toUpperCase().trim()+"%' AND idempresa = " + idempresa.toString()  + " )entidad";
				String filtro = "Where idempresa  = " + this.idempresa.toString();
				
				this.totalRegistros = recursoshumanos.getTotalEntidadFiltro(
						entidad, filtro, this.idempresa);
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
				this.rrhhformulasList = recursoshumanos.getRrhhformulasOcu(this.limit,
						this.offset, this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = recursoshumanos.getTotalEntidad(
						"rrhhformulas", this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.rrhhformulasList = recursoshumanos.getRrhhformulasAll(this.limit,
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

	public List getRrhhformulasList() {
		return rrhhformulasList;
	}

	public void setRrhhformulasList(List rrhhformulasList) {
		this.rrhhformulasList = rrhhformulasList;
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

	public BigDecimal getIdformula() {
		return idformula;
	}

	public void setIdformula(BigDecimal idformula) {
		this.idformula = idformula;
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
