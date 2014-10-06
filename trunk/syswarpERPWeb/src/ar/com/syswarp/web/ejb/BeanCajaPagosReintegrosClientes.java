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

public class BeanCajaPagosReintegrosClientes implements SessionBean,
		Serializable {
	static Logger log = Logger.getLogger(BeanCajaPagosReintegrosClientes.class);

	private SessionContext context;

	private String nrointerno = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private String accion = "";

	private String ocurrencia = "";

	private boolean primerCarga = true;

	private BigDecimal idcliente = null;

	private String cliente = "";

	private BigDecimal idcobrador;

	private String cobrador = "";

	private String fechamov = "";

	private String observaciones = "";

	private String mensaje = "";

	private String usuarioalt = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	boolean buscar = false;

	private String confirmar = "";

	private BigDecimal totalComprobantes = new BigDecimal(0);

	private BigDecimal totalSalida = new BigDecimal(0);

	private BigDecimal totalPago = new BigDecimal(0);

	private Hashtable htComprobantesCobCli = null;

	private Hashtable htIdentificaSalidaPagos = null;

	private Hashtable htMovimientosCancelar = null;

	private BigDecimal ejercicio = new BigDecimal(-1);

	private String flagRetencion = "0";

	private String totalAdelanto = "";

	private BigDecimal nrointerno_cob = new BigDecimal(0);

	public BeanCajaPagosReintegrosClientes() {
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

			String[] resultado = new String[] { "", "" };
			RequestDispatcher dispatcher = null;
			Tesoreria pagos = Common.getTesoreria();

			/*
			 * Autor:EJV ................................................
			 * Fecha:20080422 ...........................................
			 * Motivo: Usar fecha de tesoreria ........................ definida
			 * en setupvariables (apertura y cierre de caja)...
			 */

			String fecha = Common.getGeneral().getValorSetupVariablesNoStatic(
					"tesoFechaCaja", this.idempresa);
			java.sql.Date fechamovimiento = (java.sql.Date) Common
					.setObjectToStrOrTime(fecha, "StrToJSDate");

			// resultado = pagos.pagosMovProveedorCreate(idcliente,
			// fechamovimiento, totalPago, this.htComprobantesCobCli,
			// this.htIdentificaSalidaPagos, this.htMovimientosCancelar,
			// observaciones, usuarioalt, this.idempresa);

			resultado = Common.getTesoreria().cajaClientesCobranzasReintegro(
					this.idcliente, this.nrointerno_cob, fechamovimiento,
					this.htIdentificaSalidaPagos, this.htMovimientosCancelar,
					this.usuarioalt, this.idempresa);

			if (Common.setNotNull(resultado[0]).equalsIgnoreCase("OK")) {

				// if (this.htComprobantesCobCli.containsKey("ADELANTO")) {
				// String[] datosAdelanto = (String[]) this.htComprobantesCobCli
				// .get("ADELANTO");
				// // for (int i = 0; i < datosAdelanto.length; i++)
				// // log.warn("datosAdelanto[" + i + "]: "
				// // + datosAdelanto[i]);
				//
				// this.totalAdelanto = datosAdelanto[1];
				// log.info("this.totalAdelanto:" + this.totalAdelanto);
				// }

				this.nrointerno = resultado[1];
				this.mensaje = "Pago / reintegro generado Nro.: " + resultado[2];

				this.totalComprobantes = new BigDecimal(0);
				this.totalSalida = new BigDecimal(0);
				this.totalPago = new BigDecimal(0);

				rmSessionHt();

			} else
				this.mensaje = resultado[0];

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void rmSessionHt() {
		try {

			

			session.removeAttribute("htComprobantesCobCliOK");
			session.removeAttribute("htIdentificaSalidaPagosOK");
			session.removeAttribute("htMovimientosCancelarOK");

			session.removeAttribute("htComprobantesCobCli");
			session.removeAttribute("htIdentificaSalidaPagos");
			session.removeAttribute("htMovimientosCancelar");

		} catch (Exception e) {
			log.error("rmSessionHt()" + e);
		}
	}

	public boolean ejecutarValidacion() {

		try {

			if (this.primerCarga) {

				rmSessionHt();

				session.setAttribute("htComprobantesCobCliOK", new Hashtable());
				session.setAttribute("htIdentificaSalidaPagosOK",
						new Hashtable());
				session
						.setAttribute("htMovimientosCancelarOK",
								new Hashtable());

				session.removeAttribute("flagRetencion");
				session.setAttribute("flagRetencion", "0");

			}

			this.flagRetencion = session.getAttribute("flagRetencion")
					.toString();

			// --->
			if (this.idcliente != null && this.flagRetencion.equals("0")) {
				List listRetencionesProv = Common.getTesoreria()
						.getRetencionesProveedor(idcliente, ejercicio,
								idempresa);

				if (!listRetencionesProv.isEmpty()) {

					Iterator iter = listRetencionesProv.iterator();
					Hashtable htAux = (Hashtable) session
							.getAttribute("htIdentificaSalidaPagosOK");

					while (iter.hasNext()) {
						String[] datos = (String[]) iter.next();
						htAux.put(datos[2], datos);

					}

					session.setAttribute("htIdentificaSalidaPagosOK", htAux);

				}

				session.setAttribute("flagRetencion", "1");

			}
			// <---

			this.htComprobantesCobCli = (Hashtable) session
					.getAttribute("htComprobantesCobCliOK");

			this.htIdentificaSalidaPagos = (Hashtable) session
					.getAttribute("htIdentificaSalidaPagosOK");

			this.htMovimientosCancelar = (Hashtable) session
					.getAttribute("htMovimientosCancelarOK");

			this.totalComprobantes = calculaTotalPosicion(htComprobantesCobCli,
					1);
			this.totalSalida = calculaTotalPosicion(htIdentificaSalidaPagos, 28);
			this.totalSalida = totalSalida.add(calculaTotalPosicion(
					htMovimientosCancelar, 28));
			this.totalPago = totalComprobantes;

			if (!this.confirmar.equals("")) {

				if (this.htComprobantesCobCli == null
						|| this.htComprobantesCobCli.isEmpty()) {
					this.mensaje = "Ingrese  adelanto o comprobante ha ser aplicado.";
					return false;
				}

				if ((this.htIdentificaSalidaPagos == null || this.htIdentificaSalidaPagos
						.isEmpty())
						&& (this.htMovimientosCancelar == null || this.htMovimientosCancelar
								.isEmpty())) {
					this.mensaje = "Es necesario cargar ingresos.";
					return false;
				}

				this.observaciones = this.observaciones.trim();

				if (this.observaciones.length() > 250) {
					this.mensaje = "El campo observaciones no puede superar los 250 caracteres.";
					return false;
				}

				if (totalComprobantes.subtract(totalSalida).compareTo(
						new BigDecimal(0)) != 0) {
					this.mensaje = "Total comprobantes debe ser igual a total salida.";
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
				// 20120718 - EJV - Mantis 859-->
				// total = total.add(new BigDecimal(Common.getGeneral()
				// .getNumeroFormateado(Float.parseFloat(datos[posicion]),
				// 10, 2)));

				total = total.add(new BigDecimal(Common.getNumeroFormateado(
						Double.parseDouble(datos[posicion]), 10, 2)));
				// <--

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

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public void setProveedor(String razon) {
		this.cliente = razon;
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

	public BigDecimal getTotalComprobantes() {
		return totalComprobantes;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public BigDecimal getTotalPago() {
		return totalPago;
	}

	public BigDecimal getTotalSalida() {
		return totalSalida;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BigDecimal getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(BigDecimal ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String getNrointerno() {
		return nrointerno;
	}

	public void setNrointerno(String nrointerno) {
		this.nrointerno = nrointerno;
	}

	public String getTotalAdelanto() {
		return totalAdelanto;
	}

	public BigDecimal getNrointerno_cob() {
		return nrointerno_cob;
	}

	public void setNrointerno_cob(BigDecimal nrointerno_cob) {
		this.nrointerno_cob = nrointerno_cob;
	}

}
