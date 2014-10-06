/* 
   javabean para la entidad: vcajaAnuladas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 10 10:04:09 GYT 2009 
   
   Para manejar la pagina: vcajaAnuladasAbm.jsp
      
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

public class BeanVcajaAnuladasAbm implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanVcajaAnuladasAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vcajaAnuladasList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String tipomov_an;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanVcajaAnuladasAbm() {
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
		Tesoreria vcajaAnuladas = Common.getTesoreria();
		try {

			this.tipomov_an = Common.setNotNull(this.tipomov_an);

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			String entidad = "vcajaanuladas";
			String filtro = " WHERE TRUE "
					+ (this.tipomov_an.equals("") ? "" : " AND tipomov_an = '"
							+ this.tipomov_an.toUpperCase() + "'") ;

			if (buscar) {

				filtro += " AND (UPPER(comprobante) LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' OR UPPER(razon) LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' OR TO_CHAR(fechaan_an, 'DD/MM/YYYY') LIKE '%"
						+ ocurrencia.toUpperCase().trim() + "%')";
				this.totalRegistros = vcajaAnuladas.getTotalEntidadFiltro(
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
				this.vcajaAnuladasList = vcajaAnuladas.getVcajaAnuladasOcu(
						this.limit, this.offset, this.tipomov_an,
						this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = vcajaAnuladas.getTotalEntidadFiltro(
						entidad, filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.vcajaAnuladasList = vcajaAnuladas.getVcajaAnuladasAll(
						this.limit, this.offset, this.tipomov_an,
						this.idempresa);
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

	public List getVcajaAnuladasList() {
		return vcajaAnuladasList;
	}

	public void setVcajaAnuladasList(List vcajaAnuladasList) {
		this.vcajaAnuladasList = vcajaAnuladasList;
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

	public String getTipomov_an() {
		return tipomov_an;
	}

	public void setTipomov_an(String tipomov_an) {
		this.tipomov_an = tipomov_an;
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
