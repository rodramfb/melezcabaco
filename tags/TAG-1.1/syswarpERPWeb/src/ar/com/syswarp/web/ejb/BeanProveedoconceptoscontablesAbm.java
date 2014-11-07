/* 
 javabean para la entidad: proveedoProveed
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Jul 05 15:38:22 GMT-03:00 2006 
 
 Para manejar la pagina: lov_proveedores.jsp
 
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

public class BeanProveedoconceptoscontablesAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanProveedoconceptoscontablesAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List proveedoProveedList = new ArrayList();
	
	private BigDecimal ejercicio = new BigDecimal(-1);

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idconcepto = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String sort = " 2 ASC ";

	public BeanProveedoconceptoscontablesAbm() {
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
		Proveedores proveedoProveed = Common.getProveedores();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idconcepto.longValue() < 0 || idconcepto == null ) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = proveedoProveed.ProveedoConceptoscontablesDelete(
							idconcepto, this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idconcepto.longValue() < 0  || idconcepto == null) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("proveedoconceptoscontablesFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("proveedoconceptoscontablesFrm.jsp");
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
				String[] campos = { "idconcepto", "letra" };
				this.totalRegistros = proveedoProveed.getTotalEntidad("proveedoconceptoscontables", this.idempresa);
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
				this.proveedoProveedList = proveedoProveed
						.getProveedoConceptoscontablesOcu(this.limit, this.offset,
								this.ocurrencia,this.idempresa,this.ejercicio );
			} else {
				this.totalRegistros = proveedoProveed.getTotalEntidad(
						"proveedoconceptoscontables", this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.proveedoProveedList = proveedoProveed
						.getProveedoConceptoscontablesAll(this.limit, this.offset, this.idempresa,this.ejercicio );
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public BigDecimal getIdconcepto() {
		return idconcepto;
	}

	public void setIdconcepto(BigDecimal idconcepto) {
		this.idconcepto = idconcepto;
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

	public List getProveedoProveedList() {
		return proveedoProveedList;
	}

	public void setProveedoProveedList(List proveedoProveedList) {
		this.proveedoProveedList = proveedoProveedList;
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

	public BigDecimal getidconcepto() {
		return idconcepto;
	}

	public void setidconcepto(BigDecimal idconcepto) {
		this.idconcepto = idconcepto;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public BigDecimal getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(BigDecimal ejercicio) {
		this.ejercicio = ejercicio;
	}	
	
}
