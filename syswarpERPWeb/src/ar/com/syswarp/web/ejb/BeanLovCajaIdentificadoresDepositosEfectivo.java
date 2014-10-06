/* 
 javabean para la entidad: cajaIdentificadores
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Dec 18 15:14:22 GMT-03:00 2006 
 
 Para manejar la pagina: cajaIdentificadoresAbm.jsp
 
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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanLovCajaIdentificadoresDepositosEfectivo implements
		SessionBean, Serializable {
	static Logger log = Logger
			.getLogger(BeanLovCajaIdentificadoresDepositosEfectivo.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List cajaIdentificadoresList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal ididentificador;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	boolean buscar = false;

	private String tipomov = "";

	private String propio = "";

	private String identificador = "";

	private String cmpCodigo = "";

	private String cmpDescripcion = "";

	private String cmpCC1 = "";

	private String cmpCC2 = "";

	private String cmpModCC = "";

	public BeanLovCajaIdentificadoresDepositosEfectivo() {
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

		Tesoreria cajaIdentificadores = Common.getTesoreria();

		try {

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			if (buscar) {
				String[] campos = { "ididentificador", "tipoidentificador",
						"identificador", "descripcion" };
				this.totalRegistros = cajaIdentificadores
						.getTotalEntidadRelacionOcu("videntificadorestipo",
								new String[] { "tipomov", "propio" },
								new String[] { "'" + this.tipomov + "'",
										"'" + this.propio + "'" }, campos,
								new String[] { this.ocurrencia,
										this.ocurrencia, this.ocurrencia,
										this.ocurrencia }, this.idempresa);
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
				this.cajaIdentificadoresList = cajaIdentificadores
						.getLovCajaIdentificadoresOcu(this.limit, this.offset,
								this.ocurrencia, this.tipomov, this.propio,
								this.idempresa);
			} else {

				String filtro = " WHERE tipomov = '" + this.tipomov
						+ "' AND propio = '" + this.propio + "'";
				this.totalRegistros = cajaIdentificadores
						.getTotalEntidadFiltro("videntificadorestipo", filtro,
								this.idempresa);

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.cajaIdentificadoresList = cajaIdentificadores
						.getLovCajaIdentificadoresAll(this.limit, this.offset,
								this.tipomov, this.propio, this.idempresa);
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

	public List getCajaIdentificadoresList() {
		return cajaIdentificadoresList;
	}

	public void setCajaIdentificadoresList(List cajaIdentificadoresList) {
		this.cajaIdentificadoresList = cajaIdentificadoresList;
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

	public BigDecimal getIdidentificador() {
		return ididentificador;
	}

	public void setIdidentificador(BigDecimal ididentificador) {
		this.ididentificador = ididentificador;
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

	public String getPropio() {
		return propio;
	}

	public void setPropio(String propio) {
		this.propio = propio;
	}

	public String getTipomov() {
		return tipomov;
	}

	public void setTipomov(String tipommov) {
		this.tipomov = tipommov;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getCmpCodigo() {
		return cmpCodigo;
	}

	public void setCmpCodigo(String cmpCodigo) {
		this.cmpCodigo = cmpCodigo;
	}

	public String getCmpDescripcion() {
		return cmpDescripcion;
	}

	public void setCmpDescripcion(String cmpDescripcion) {
		this.cmpDescripcion = cmpDescripcion;
	}

	public String getCmpModCC() {
		return cmpModCC;
	}

	public void setCmpModCC(String cmpModCC) {
		this.cmpModCC = cmpModCC;
	}

	public String getCmpCC1() {
		return cmpCC1;
	}

	public void setCmpCC1(String cmpCC1) {
		this.cmpCC1 = cmpCC1;
	}

	public String getCmpCC2() {
		return cmpCC2;
	}

	public void setCmpCC2(String cmpCC2) {
		this.cmpCC2 = cmpCC2;
	}

}
