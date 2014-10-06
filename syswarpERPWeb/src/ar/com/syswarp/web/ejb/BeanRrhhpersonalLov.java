/* 
   javabean para la entidad: rrhhpersonal
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 22 09:44:28 ACT 2009 
   
   Para manejar la pagina: rrhhpersonalAbm.jsp
      
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

public class BeanRrhhpersonalLov implements SessionBean, Serializable {
	
	static Logger log = Logger.getLogger(BeanRrhhpersonalAbm.class);
	
	private SessionContext context;
	
	private int limit = 15;
	
	private long offset = 0l;
	
	private long totalRegistros = 0l;
	
	private long totalPaginas = 0l;
	
	private long paginaSeleccion = 1l;
	
	private List rrhhpersonalList = new ArrayList();
	
	private String accion = "";
	
	private String ocurrencia = "";
	
	private BigDecimal legajo;
	
	private BigDecimal idempresa;
	
	private String mensaje = "";
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	boolean buscar = false;
	
	private boolean salida = false;

	public BeanRrhhpersonalLov() {
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
		RRHH rrhhpersonal = Common.getRrhh();
		try {
			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			
			if (buscar) {
				String entidad = "(SELECT"
				+ " pe.legajo,"
				+ " pe.apellido,"
				+ " pe.domicilio,"
				+ " pe.fbaja,"
				+ " pe.idempresa"
				+ " FROM "
				+ " RRHHPERSONAL pe "
				+ " LEFT JOIN globallocalidades lo ON pe.idlocalidad = lo.idlocalidad "
				+ " LEFT JOIN globalprovincias pr ON pe.idprovincia = pr.idprovincia "
				+ " LEFT JOIN rrhhestadocivil ec ON  pe.idestadocivil = ec.idestadocivil  and pe.idempresa = ec.idempresa"
				+ " LEFT JOIN globaltiposdocumentos gd ON pe.idtipodocumento = gd.idtipodocumento  and pe.idempresa = gd.idempresa"
				+ " LEFT JOIN globalpaises gp ON  pe.idpais = gp.idpais"
				+ " LEFT JOIN rrhhcategorias gc ON  pe.idcategoria = gc.idcategoria  and pe.idempresa = gc.idempresa"
				+ " LEFT JOIN rrhhtitulo gt ON pe.idtitulo = gt.idtitulo  and pe.idempresa = gt.idempresa"
				+ " LEFT JOIN rrhhafjp ga ON pe.idafjp = ga.idafjp  and pe.idempresa = ga.idempresa"
				+ " LEFT JOIN rrhhart gar ON  pe.idart = gar.idart  and pe.idempresa = gar.idempresa"
				+ " LEFT JOIN rrhhobrasocial gos ON  pe.idobrasocial = gos.idobrasocial  and pe.idempresa = gos.idempresa"
				+ " LEFT JOIN globallocalidades locpago ON pe.idlocalidadpago = locpago.idlocalidad "
				+ " LEFT JOIN rrhhmodalidadcontrato mod ON pe.idmodalidadcontrato = mod.idmodalidadcontrato and pe.idempresa = mod.idempresa "
				+ " LEFT JOIN cajaidentificadores cmt ON pe.idbancodeposito = cmt.ididentificador and pe.idempresa = cmt.idempresa "
				+ " where pe.idempresa = " + idempresa.toString() + " and pe.fbaja is null "
				+ " and (legajo::VARCHAR = " + ocurrencia + " OR "
				+ " UPPER(apellido) LIKE '%" + ocurrencia.toUpperCase()
				+ "%'  ) )entidad";
				log.info(legajo);
				String filtro = " Where idempresa = " + idempresa.toString()
								+ " and fbaja is null "
				+ " and (legajo::VARCHAR LIKE '%" + ocurrencia + "%' OR "
				+ " UPPER(apellido) LIKE '%" + ocurrencia.toUpperCase() +"%')";
				log.info(entidad);
				this.totalRegistros = rrhhpersonal
						.getTotalEntidadFiltro(entidad, filtro, idempresa);
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
				this.rrhhpersonalList = rrhhpersonal.getRrhhpersonalOcu(
						this.limit, this.offset, this.ocurrencia,
						this.idempresa);
			} else {
				this.totalRegistros = rrhhpersonal.getTotalEntidad(
						"rrhhpersonal", this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.rrhhpersonalList = rrhhpersonal.getPersonalLovList(this.limit, this.offset, this.idempresa);
				
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

	public List getRrhhpersonalList() {
		return rrhhpersonalList;
	}

	public void setRrhhpersonalList(List rrhhpersonalList) {
		this.rrhhpersonalList = rrhhpersonalList;
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

	public BigDecimal getLegajo() {
		return legajo;
	}

	public void setLegajo(BigDecimal legajo) {
		this.legajo = legajo;
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

	public boolean isSalida() {
		return salida;
	}

	public void setSalida(boolean salida) {
		this.salida = salida;
	}
	
}
