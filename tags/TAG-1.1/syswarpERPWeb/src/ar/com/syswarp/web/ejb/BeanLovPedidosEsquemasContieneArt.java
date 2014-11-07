/* 
 javabean para la entidad: produccionEsquemas_Cabe
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Feb 13 09:18:39 GMT-03:00 2007 
 
 Para manejar la pagina: produccionEsquemasAbm.jsp
 
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

public class BeanLovPedidosEsquemasContieneArt implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanLovPedidosEsquemasContieneArt.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List produccionEsquemasList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idesquema;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private boolean replicar = false;

	boolean buscar = false;

	public BeanLovPedidosEsquemasContieneArt() {
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
		Clientes clientes = Common.getClientes();
		try {

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			if (buscar) {

				String entidad = " varticulosesquemadesarme ";
				
				
				// +
				// "(SELECT ec.idesquema, ec.esquema, replace(replace(ec.observaciones, chr(13), ''), chr(10), ''), ed.codigo_st, st.descrip_st, ed.cantidad::numeric(18) AS cantidad, ec.idempresa "
				// + "  FROM produccionesquemas_cabe ec  "
				// +
				// "       INNER JOIN  produccionesquemas_deta ed ON ec.idesquema = ed.idesquema AND ec.idempresa = ed.idempresa "
				// +
				// "       INNER JOIN stockstock st ON ed.codigo_st = st.codigo_st AND ed.idempresa = st.idempresa "
				// + " WHERE ec.idempresa =   "
				// + this.idempresa.toString()
				// + "   AND ed.entsal = 'C'  "
				// + "   AND ed.reutiliza = 'S') esquemas ";
				

				String filtro = "WHERE (UPPER(codigo_st) LIKE '%"
						+ ocurrencia.toUpperCase()
						+ "%'  OR UPPER(descrip_st) LIKE '%"
						+ ocurrencia.toUpperCase().trim() + "%')";

				this.totalRegistros = clientes.getTotalEntidadFiltro(entidad,
						filtro, this.idempresa);

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

				this.produccionEsquemasList = clientes
						.getEsquemasContienenArticulo(this.limit, this.offset,
								this.ocurrencia, this.idempresa);

				if (this.totalRegistros < 1)
					this.mensaje = "No existen registros.";

			}

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

	public List getProduccionEsquemasList() {
		return produccionEsquemasList;
	}

	public void setProduccionEsquemasList(List produccionEsquemasList) {
		this.produccionEsquemasList = produccionEsquemasList;
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

	public BigDecimal getIdesquema() {
		return idesquema;
	}

	public void setIdesquema(BigDecimal idesquema) {
		this.idesquema = idesquema;
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

	public boolean isReplicar() {
		return replicar;
	}

	public void setReplicar(boolean replicar) {
		this.replicar = replicar;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
