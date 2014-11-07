/* 
 javabean para la entidad: produccionRecetas_deta
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Oct 23 12:38:58 GMT-03:00 2006 
 
 Para manejar la pagina: produccionRecetas_detaAbm.jsp
 
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

public class BeanProduccionrecetas_detaAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanProduccionrecetas_detaAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List produccionRecetas_detaList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String codigo_st_cabe;

	private String codigo_st;

	private String mensaje = "";

	private String descrip_st = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanProduccionrecetas_detaAbm() {
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
		Produccion produccionRecetas_deta = Common.getProduccion();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (codigo_st_cabe.equalsIgnoreCase("")) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = produccionRecetas_deta
							.produccionRecetas_detaDelete(this.codigo_st_cabe,
									this.codigo_st, this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (codigo_st_cabe.equals("")) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("Produccionrecetas_detaFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("Produccionrecetas_detaFrm.jsp");
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
				String[] campos = { "codigo_st_cabe", "codigo_st" };
				this.totalRegistros = produccionRecetas_deta
						.getTotalEntidadOcu("produccionRecetas_deta", campos,
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
				this.produccionRecetas_detaList = produccionRecetas_deta
						.getProduccionRecetas_detaOcu(this.codigo_st_cabe,
								this.limit, this.offset, this.ocurrencia,
								this.idempresa);
			} else {
				this.totalRegistros = produccionRecetas_deta
						.getTotalReceteDetalle(this.codigo_st_cabe,
								this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.produccionRecetas_detaList = produccionRecetas_deta
						.getProduccionRecetas_detaAll(this.codigo_st_cabe,
								this.limit, this.offset, this.idempresa);
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

	public List getProduccionRecetas_detaList() {
		return produccionRecetas_detaList;
	}

	public void setProduccionRecetas_detaList(List produccionRecetas_detaList) {
		this.produccionRecetas_detaList = produccionRecetas_detaList;
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

	public String getCodigo_st_cabe() {
		return codigo_st_cabe;
	}

	public void setCodigo_st_cabe(String codigo_st_cabe) {
		this.codigo_st_cabe = codigo_st_cabe;
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

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
