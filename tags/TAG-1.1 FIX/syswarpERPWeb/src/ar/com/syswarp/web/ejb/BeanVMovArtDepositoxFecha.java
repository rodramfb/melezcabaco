/* 
 javabean para la entidad: vMovArtFecha
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Nov 14 09:20:44 GMT-03:00 2006 
 
 Para manejar la pagina: vMovArtFechaAbm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Date;

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

public class BeanVMovArtDepositoxFecha implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanVMovArtDepositoxFecha.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vMovArtFechaList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal nrointerno_ms;
	
	private BigDecimal codigo_dt= BigDecimal.valueOf(0);
	
	private String descrip_dt= "";

	private String fecha_ms = Common.initObjectTimeStr();

	private String mensaje = "";
	
	private String usuario = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanVMovArtDepositoxFecha() {
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
		Stock vMovArtFecha = Common.getStock();
		try {

			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("vMovArtxFechaFrm.jsp");
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
		if (this.accion.equalsIgnoreCase("consulta")){
				if (descrip_dt.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Deposito ";
					return false;
				}
			if (buscar) {
				this.totalRegistros = vMovArtFecha
						.getTotalStocDepositoFechaOcu(
								(java.sql.Date) Common.setObjectToStrOrTime(
										this.fecha_ms, "strToJSDate"),
								this.ocurrencia, this.idempresa, this.getUsuario(),this.codigo_dt);

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
				this.vMovArtFechaList = vMovArtFecha
						.getDepositoFechaOcu(this.limit, this.offset,
								(java.sql.Date) (Common.setObjectToStrOrTime(
										this.fecha_ms, "strToJSDate")),
								this.ocurrencia, this.idempresa, this.getUsuario(),this.codigo_dt);
			} else {
				this.totalRegistros = vMovArtFecha
						.getTotalStockDepositoFecha(
								(java.sql.Date) Common.setObjectToStrOrTime(
										this.fecha_ms, "strToJSDate"),
								this.idempresa, this.getUsuario(),this.codigo_dt);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.vMovArtFechaList = vMovArtFecha
						.getDepositoxFechaAll((java.sql.Date) (Common
								.setObjectToStrOrTime(this.fecha_ms,
										"strToJSDate")), this.limit,
								this.offset, this.idempresa, this.getUsuario(),this.codigo_dt);
			}
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

	public List getVMovArtFechaList() {
		return vMovArtFechaList;
	}

	public void setVMovArtFechaList(List vMovArtFechaList) {
		this.vMovArtFechaList = vMovArtFechaList;
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

	public BigDecimal getNrointerno_ms() {
		return nrointerno_ms;
	}

	public void setNrointerno_ms(BigDecimal nrointerno_ms) {
		this.nrointerno_ms = nrointerno_ms;
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

	public String getFecha_ms() {
		return fecha_ms;
	}

	public void setFecha_ms(String fecha_ms) {
		this.fecha_ms = fecha_ms;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}



	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getDescrip_dt() {
		return descrip_dt;
	}

	public void setDescrip_dt(String descrip_dt) {
		this.descrip_dt = descrip_dt;
	}
}
