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

public class BeanProduccionCalculoNecesidad implements SessionBean,
		Serializable {
	static Logger log = Logger.getLogger(BeanProduccionCalculoNecesidad.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private String accion = "";

	private String ocurrencia = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String validar = "";

	private List produccionEsquemasList = new ArrayList();

	private String codigo_st = "";

	private String descrip_st = "";

	private String cantidad = "0";

	private BigDecimal idesquema = new BigDecimal(0);

	public BeanProduccionCalculoNecesidad() {
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

		Produccion produccionEsquemas = Common.getProduccion();

		try {

			if (!validar.equals("")) {

				if (this.codigo_st.equalsIgnoreCase("")) {
					this.mensaje = "Ingrese un articulo para calcular necesidad.";
					return false;
				}

				this.cantidad = this.cantidad.trim();

				if (!Common.esNumerico(this.cantidad)) {
					this.mensaje = "Ingrese valores numericos validos para cantidad.";
					return false;
				}

				if (new BigDecimal(this.cantidad).compareTo(new BigDecimal(0)) <= 0) {
					this.mensaje = "Cantidad debe ser mayor a cero.";
					return false;
				}

				// 20101117-EJV-Mantis-602: Se agrego parametro "N"
				this.produccionEsquemasList = produccionEsquemas
						.getRecursividadEsquema(this.idesquema, new BigDecimal(
								this.cantidad), 1, "N", this.idempresa);

			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	/**
	 * private List recursividadEsquema(BigDecimal idesquema, BigDecimal
	 * cantidad, int nivel) {
	 * 
	 * List auxList = new ArrayList(); List retornoList = new ArrayList(); List
	 * recursivaList = new ArrayList(); Iterator auxIter;
	 * 
	 * try {
	 * 
	 * Produccion produccionEsquemas = Common.getProduccion();
	 * 
	 * auxList = produccionEsquemas.getVCalculoNecesidadTotalesPK( idesquema,
	 * cantidad , new BigDecimal( nivel));
	 * 
	 * auxIter = auxList.iterator();
	 * 
	 * while (auxIter.hasNext()) {
	 * 
	 * String[] datos = (String[]) auxIter.next(); retornoList.add(datos); if
	 * (datos[4].equalsIgnoreCase("E")) {
	 * 
	 * recursivaList = recursividadEsquema( new BigDecimal(datos[2]), cantidad,
	 * ++nivel); Iterator recursivaIter = recursivaList.iterator(); while
	 * (recursivaIter.hasNext()) { retornoList.add(recursivaIter.next()); } } }
	 * } catch (Exception e) { log.error("recursividadEsquema(): " + e); }
	 * return retornoList; }
	 */

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

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getValidar() {
		return validar;
	}

	public void setValidar(String validar) {
		this.validar = validar;
	}

	public BigDecimal getIdesquema() {
		return idesquema;
	}

	public void setIdesquema(BigDecimal idesquema) {
		this.idesquema = idesquema;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
