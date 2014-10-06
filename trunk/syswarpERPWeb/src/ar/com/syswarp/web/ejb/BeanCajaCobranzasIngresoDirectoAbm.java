/* 
 javabean para la entidad: vlov_Clientes
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Dec 12 14:43:42 GMT-03:00 2006 
 
 Para manejar la pagina: vlov_ClientesAbm.jsp
 
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

public class BeanCajaCobranzasIngresoDirectoAbm implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanCajaCobranzasIngresoDirectoAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private String accion = "";

	private String ocurrencia = "";

	private boolean primerCarga = true;

	private BigDecimal idcliente;

	private String razon = "";

	private BigDecimal idcobrador;

	private String cobrador = "";

	private String fechamov = Common.initObjectTimeStr();

	private String observaciones = "";

	private String mensaje = "";

	private String usuarioalt = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	boolean buscar = false;

	private String confirmar = "";

	private BigDecimal totalCobranza = new BigDecimal(0);

	private BigDecimal totalIngresos = new BigDecimal(0);

	private BigDecimal totalContrapartida = new BigDecimal(0);

	private Hashtable htIdentificadoresContrapartida = null;

	private Hashtable htIdentificadoresIngresos = null;

	private Hashtable htMovimientosCancelar = null;

	public BeanCajaCobranzasIngresoDirectoAbm() {
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

	public void ejecutarSentenciaDML() {
		try {
			RequestDispatcher dispatcher = null;
			Tesoreria cobranzas = Common.getTesoreria();
			/*
			 * Autor:EJV ................................................
			 * Fecha:20080422 ...........................................
			 * Motivo: Usar fecha de tesoreria ........................
			 * definida en setupvariables (apertura y cierre de caja)...
			 */
			String fecha = Common.getGeneral().getValorSetupVariablesNoStatic(
					"tesoFechaCaja", this.idempresa);
			java.sql.Date fechamovimiento = (java.sql.Date) Common
					.setObjectToStrOrTime(fecha, "StrToJSDate");

			this.mensaje = cobranzas.cobranzasIngresosDirectosCreate(
					this.htIdentificadoresIngresos,
					this.htIdentificadoresContrapartida,
					this.htMovimientosCancelar, fechamovimiento, observaciones,
					usuarioalt, this.idempresa);

			if (Common.esNumerico(this.mensaje)) {
				dispatcher = request
						.getRequestDispatcher("CajaImprimeIngresosDirectosFrame.jsp?nrocomprobante="
								+ mensaje);
				dispatcher.forward(request, response);
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {

		try {

			if (this.primerCarga) {

				
				session.removeAttribute("htIdentificadoresIngresos");
				session.removeAttribute("htIdentificadoresContrapartida");
				session.removeAttribute("htMovimientosCancelar");
				
				session.removeAttribute("htIdentificadoresIngresosOK");
				session.removeAttribute("htIdentificadoresContrapartidaOK");
				session.removeAttribute("htMovimientosCancelarOK");

				session.setAttribute("htIdentificadoresIngresosOK",
						new Hashtable());
				session.setAttribute("htIdentificadoresContrapartidaOK",
						new Hashtable());
				session
						.setAttribute("htMovimientosCancelarOK",
								new Hashtable());
			}

			this.htIdentificadoresIngresos = (Hashtable) session
					.getAttribute("htIdentificadoresIngresosOK");

			this.htIdentificadoresContrapartida = (Hashtable) session
					.getAttribute("htIdentificadoresContrapartidaOK");

			this.htMovimientosCancelar = (Hashtable) session
					.getAttribute("htMovimientosCancelarOK");

			totalIngresos = calculaTotalPosicion(htIdentificadoresIngresos, 28);
			totalContrapartida = calculaTotalPosicion(
					htIdentificadoresContrapartida, 28).add(
					calculaTotalPosicion(htMovimientosCancelar, 28));
			totalCobranza = totalIngresos;

			if (!this.confirmar.equals("")) {

				if (this.htIdentificadoresIngresos == null
						|| this.htIdentificadoresIngresos.isEmpty()) {
					this.mensaje = "Es necesario cargar ingresos.";
					return false;
				}

				if ((this.htIdentificadoresContrapartida == null || this.htIdentificadoresContrapartida
						.isEmpty())
						&& (this.htMovimientosCancelar == null || this.htMovimientosCancelar
								.isEmpty())) {
					this.mensaje = "Es necesario cargar contrapartida.";
					return false;
				}

				if (totalIngresos.subtract(totalContrapartida).compareTo(
						new BigDecimal(0)) != 0) {
					this.mensaje = "Total ingresos y total contrapartida deben ser cifras iguales.";
					return false;
				}

				this.observaciones = this.observaciones.trim();

				if (this.observaciones.length() > 250) {
					this.mensaje = "El campo observaciones no puede superar los 250 caracteres.";
					return false;
				}

				this.ejecutarSentenciaDML();

			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	private BigDecimal calculaTotalPosicion(Hashtable ht, int posicion) {
		Enumeration enu = null;
		BigDecimal total = new BigDecimal(0);
		try {

			enu = ht.keys();
			while (enu.hasMoreElements()) {
				String clave = (String) enu.nextElement();
				String[] datos = (String[]) ht.get(clave);
				total = total.add(new BigDecimal(Common.getGeneral()
						.getNumeroFormateado(Float.parseFloat(datos[posicion]),
								10, 2)));
			}
		} catch (Exception e) {
			total = new BigDecimal(0);
			log.error("calculaTotalPosicion(Hashtable ht, int posicion):" + e);
		}
		return total;
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

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
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

	public String getCobrador() {
		return cobrador;
	}

	public void setCobrador(String cobrador) {
		this.cobrador = cobrador;
	}

	public BigDecimal getIdcobrador() {
		return idcobrador;
	}

	public void setIdcobrador(BigDecimal idcobrador) {
		this.idcobrador = idcobrador;
	}

	public boolean isPrimerCarga() {
		return primerCarga;
	}

	public void setPrimerCarga(boolean primerCarga) {
		this.primerCarga = primerCarga;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String getConfirmar() {
		return confirmar;
	}

	public void setConfirmar(String confirmar) {
		this.confirmar = confirmar;
	}

	public String getFechamov() {
		return fechamov;
	}

	public void setFechamov(String fechamov) {
		this.fechamov = fechamov;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public BigDecimal getTotalCobranza() {
		return totalCobranza;
	}

	public BigDecimal getTotalIngresos() {
		return totalIngresos;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public BigDecimal getTotalContrapartida() {
		return totalContrapartida;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
