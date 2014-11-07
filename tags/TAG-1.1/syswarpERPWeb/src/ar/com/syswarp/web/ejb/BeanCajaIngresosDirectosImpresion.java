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

public class BeanCajaIngresosDirectosImpresion implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanCajaIngresosDirectosImpresion.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal nrocomprobante = new BigDecimal(0);

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

	private Hashtable htDescripcionIdentificadores = null;

	public BeanCajaIngresosDirectosImpresion() {
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

		try {

			Tesoreria cobranzas = Common.getTesoreria();

			List listaIdent = cobranzas.getLovCajaIdentificadoresPropioAll(250,
					0, "N", this.idempresa);
			Iterator iterIdent = listaIdent.iterator();
			this.htDescripcionIdentificadores = new Hashtable();
			while (iterIdent.hasNext()) {
				String[] ident = (String[]) iterIdent.next();
				this.htDescripcionIdentificadores.put(ident[2], ident[3]);
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

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public void rmSessionHt() {
		try {
			session.removeAttribute("htIdentificadoresIngresosOK");
			session.removeAttribute("htIdentificadoresContrapartidaOK");
			session.removeAttribute("htMovimientosCancelarOK");
		} catch (Exception e) {
			log.error("rmSessionHt()" + e);
		}
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

	public BigDecimal getNrocomprobante() {
		return nrocomprobante;
	}

	public void setNrocomprobante(BigDecimal nrocomprobante) {
		this.nrocomprobante = nrocomprobante;
	}

	public String getDescripcionIdentificador(String clave) {
		String valor = "";
		try {

			valor = this.htDescripcionIdentificadores.get(clave).toString();

		} catch (Exception e) {
			log.error("getDescripcionIdentificador(String clave): " + e);
		}
		return valor;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
