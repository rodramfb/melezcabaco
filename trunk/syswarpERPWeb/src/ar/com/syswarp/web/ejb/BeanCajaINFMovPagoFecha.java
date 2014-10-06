/* 
 javabean para la entidad: Cajaferiados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
 
 Para manejar la pagina: CajaferiadosAbm.jsp
 
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

public class BeanCajaINFMovPagoFecha implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanCajaINFMovPagoFecha.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List cajaMovPagoFecha = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String fechaDesde = "";

	private String fechaHasta = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String aceptar = "";

	public BeanCajaINFMovPagoFecha() {
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
		Tesoreria saldoFecha = Common.getTesoreria();
		try {

			if (!aceptar.equals("")) {
				if (!Common.isFechaValida(fechaDesde)
						|| !Common.isFormatoFecha(fechaDesde)) {
					this.mensaje = "Ingrese Fecha Desde.";
					return false;
				}

				if (!Common.isFechaValida(fechaHasta)
						|| !Common.isFormatoFecha(fechaHasta)) {
					this.mensaje = "Ingrese Fecha Hasta.";
					return false;
				}

				this.cajaMovPagoFecha = saldoFecha.getMovPagoFecha(
						(java.sql.Timestamp) Common.setObjectToStrOrTime(
								fechaDesde, "StrToJSTs"),
						(java.sql.Timestamp) Common.setObjectToStrOrTime(
								fechaHasta + " 23:59:59", "StrToJSTs"),
						idempresa);

				if (this.cajaMovPagoFecha.size() < 1)
					this.mensaje = "No existen movimientos.";
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

	public List getCajaMovPagoFecha() {
		return cajaMovPagoFecha;
	}

	public void setCajaMovPagoFecha(List cajaMovCobranzaFecha) {
		this.cajaMovPagoFecha = cajaMovCobranzaFecha;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public boolean isBuscar() {
		return buscar;
	}

	public void setBuscar(boolean buscar) {
		this.buscar = buscar;
	}

	public String getAceptar() {
		return aceptar;
	}

	public void setAceptar(String aceptar) {
		this.aceptar = aceptar;
	}
}
