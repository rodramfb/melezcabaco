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

public class BeanRrhhmodalidadcontratoAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanRrhhmodalidadcontratoAbm.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List rrhhmodalidadcontratoList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idmodalidadcontrato;
	
	private BigDecimal idempresa;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanRrhhmodalidadcontratoAbm() {
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
		RRHH rrhhmodalidadcontrato = Common.getRrhh();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idmodalidadcontrato == null || idmodalidadcontrato.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = rrhhmodalidadcontrato
							.getRrhhmodalidaddecontratoDelete(idmodalidadcontrato,this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idmodalidadcontrato == null || idmodalidadcontrato.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("rrhhmodalidadcontratoFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("rrhhmodalidadcontratoFrm.jsp");
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
				String[] campos = { "idmodalidadcontrato", "modalidadcontrato" };
				this.totalRegistros = rrhhmodalidadcontrato.getTotalEntidadOcu("rrhhmodalidadcontrato",
						campos,this.ocurrencia,this.idempresa);
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
				this.rrhhmodalidadcontratoList = rrhhmodalidadcontrato.getRrhhmodalidaddecontratoOcu(
						this.limit, this.offset, this.ocurrencia,this.idempresa);
			} else {
				this.totalRegistros = rrhhmodalidadcontrato
						.getTotalEntidad("rrhhmodalidadcontrato",this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.rrhhmodalidadcontratoList = rrhhmodalidadcontrato.getRrhhmodalidaddecontratoAll(
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

	public List getrrhhmodalidadcontratoList() {
		return rrhhmodalidadcontratoList;
	}

	public void setrrhhmodalidadcontratoList(List rrhhmodalidadcontratoList) {
		this.rrhhmodalidadcontratoList = rrhhmodalidadcontratoList;
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


	public List getRrhhmodalidadcontratoList() {
		return rrhhmodalidadcontratoList;
	}

	public void setRrhhmodalidadcontratoList(List rrhhmodalidadcontratoList) {
		this.rrhhmodalidadcontratoList = rrhhmodalidadcontratoList;
	}

	public BigDecimal getIdmodalidadcontrato() {
		return idmodalidadcontrato;
	}

	public void setIdmodalidadcontrato(BigDecimal idmodalidadcontrato) {
		this.idmodalidadcontrato = idmodalidadcontrato;
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
