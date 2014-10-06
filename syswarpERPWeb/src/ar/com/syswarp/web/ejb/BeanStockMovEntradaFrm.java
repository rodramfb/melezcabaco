/* 
 javabean para la entidad (Formulario): proveedoMovProv
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jul 27 09:30:45 GMT-03:00 2006 
 
 Para manejar la pagina: proveedoMovProvFrm.jsp
 
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
import java.math.*;
import java.util.*;
import java.sql.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanStockMovEntradaFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanStockMovEntradaFrm.class);

	GeneralBean gb = new GeneralBean();

	private String fechamov = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	// 1 - DATOS APLICACION

	private String origen = "";

	private String labelOrigen = "";

	private String accion = "";

	private String mensaje = "";

	private String validar = "";

	private String volver = "";

	private boolean primeraCarga = true;

	private String confirma_origen = "";

	private String origenanterior = "";

	private int ejercicioactivo = 0;

	private String usuarioact = "";

	private String usuarioalt = "";

	// 2 - DATOS ANEXOS

	private BigDecimal idanexo = new BigDecimal(0);

	private BigDecimal codigo_anexo = new BigDecimal(-1);

	private String razon_social = "";

	private BigDecimal iddomicilio = new BigDecimal(-1);

	private String domicilio = "";

	private String codigo_postal = "";

	private BigDecimal idprovincia = new BigDecimal(0);

	private String provincia = "";

	private BigDecimal idlocalidad = new BigDecimal(0);

	private String localidad = "";

	private String tipodocumento = "CUIT";

	private String cuit = "";

	private String iibb = "";

	private String remito_ms = "0";

	private String observaciones = "";

	// 3 - Cuentas Articulos
	private BigDecimal totalDebe = new BigDecimal(0);

	// 3 - Cuentas Imputacion
	private BigDecimal totalHaber = new BigDecimal(0);

	private boolean remitopendiente = false;

	private BigDecimal idcontadorcomprobante = new BigDecimal(-1);

	private String sistema_ms = "S";

	public BeanStockMovEntradaFrm() {
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
		// RequestDispatcher dispatcher;
		try {
			Stock stock = Common.getStock();
			String[] resultado = null;
			Hashtable htArticulos = (Hashtable) session
					.getAttribute("htArticulosInOutOK");
			Hashtable htCuentas = (Hashtable) session
					.getAttribute("htCuentasOk");

			Hashtable htSerieDespacho = (Hashtable) session
					.getAttribute("htArticulosSerieDespachoOK");

			resultado = stock.stockMovEntradaCreate(this.codigo_anexo,
					this.razon_social, this.domicilio, this.codigo_postal,
					this.idlocalidad, this.idprovincia, this.cuit, this.iibb,
					this.sistema_ms, (Timestamp) Common.setObjectToStrOrTime(
							this.fechamov, "strToJSTs"), new BigDecimal(
							this.remito_ms), "E", new BigDecimal(1),
					this.origen, this.remitopendiente, this.ejercicioactivo,
					this.observaciones, htArticulos, htCuentas,
					this.idcontadorcomprobante, true, htSerieDespacho,
					this.usuarioalt, this.idempresa);
			log.info(resultado[0]);
			if (resultado[0].equalsIgnoreCase("OK")) {

				session.removeAttribute("htArticulosInOutOK");
				session.removeAttribute("htArticulosSerieDespachoOK");
				session.removeAttribute("htCuentasOk");
				
				/*
				 * response.sendRedirect("stockRemitoMovEntradaFrame.jsp?remito_interno="
				 * + this.mensaje + "&fechamov=" + this.fechamov);
				 */
				/**/
				/*
				 * response
				 * .sendRedirect("../reportes/jasper/generaPDF.jsp?remito_interno="
				 * + this.mensaje +
				 * "&plantillaImpresionJRXML=cambio.deposito.frame&tipo=Entrada"
				 * );
				 */
				log.info("resultado[1]: " + resultado[1]);
				response
						.sendRedirect("impresionRemitosInternos.jsp?remitos="
								+ resultado[1]
								+ "&tipo=Entrada&plantillaImpresionJRXML=entrada_salida_frame");
				
			} else {
				this.mensaje = resultado[0];
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		boolean isAnexoOk = false;
		try {

			if (isPrimeraCarga()) {
				log.info("primeracargaejecutarValidacion");
				session.removeAttribute("htArticulosInOut");
				session.removeAttribute("htArticulosInOutOK");
				session.removeAttribute("htCuentasOk");
			} else {
				this.totalDebe = getTotalesAsiento((Hashtable) session
						.getAttribute("htArticulosInOutOK"), 11);
				this.totalHaber = getTotalesAsiento((Hashtable) session
						.getAttribute("htCuentasOk"), 2);
			}

			isAnexoOk = this.setDatosAnexo();
				log.info("validar: " + this.validar);
			if (!this.validar.equalsIgnoreCase("")) {
				
				if (!this.accion.equalsIgnoreCase("baja")) {

					if (fechamov == null || fechamov.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo fecha movimiento. ";
						return false;
					}

					if (this.origen.equals("")) {
						this.mensaje = "Seleccione origen.";
						return false;
					}

					if (!isAnexoOk) {
						return false;
					}

					if (!Common.esEntero(this.remito_ms)
							|| Long.parseLong(this.remito_ms) < 0) {
						this.mensaje = "Ingrese valores validos para remito.";
						return false;
					}

					if (this.razon_social.trim().equals("")) {
						this.mensaje = "Ingrese Razon.";
						return false;
					}

					if (this.idlocalidad.compareTo(new BigDecimal(0)) <= 0) {
						this.mensaje = "Ingrese Localidad.";
						return false;
					}

					if (session.getAttribute("htArticulosInOutOK") == null
							|| ((Hashtable) session
									.getAttribute("htArticulosInOutOK"))
									.isEmpty()) {
						this.mensaje = "Ingrese al menos un articulo que afecte al movimiento.";
						return false;
					}

					if (isRemitopendiente()) {
						if (this.totalHaber.compareTo(new BigDecimal(0)) != 0) {
							this.mensaje = "Selecciono \"Remito Pendiente\", y cargo imputaciones.<br>Elimine las imputaciones  o bien quite la seleccion de \"Remito Pendiente\".";
							return false;
						}
					} else {
						if (this.totalHaber.compareTo(this.totalDebe) != 0) {
							this.mensaje = "No coincide el total de movimiento con el total de imputacion.";
							return false;
						}
					}
				}

				this.ejecutarSentenciaDML();

			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {

				}
			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private boolean setDatosAnexo() {

		boolean fOk = true;
		log.info("datosanexo");
		try {

			if (!isPrimeraCarga()) {
				if (!this.origen.equals(this.origenanterior)) {
					log.info("primera carga");
					this.mensaje = "Selecciono nuevo origen, es necesario cargar datos nuevamente.";
					this.codigo_anexo = new BigDecimal(0);
					this.razon_social = "";
					this.iddomicilio = new BigDecimal(0);
					this.domicilio = "";
					this.idlocalidad = new BigDecimal(0);
					this.localidad = "";
					this.provincia = "";
					this.idprovincia = new BigDecimal(0);
					this.codigo_postal = "";
					this.cuit = "";
					this.iibb = "";
					fOk = false;

				} else if (!this.origen.equalsIgnoreCase("O")
						&& !this.origen.equals("")
						&& this.codigo_anexo.compareTo(new BigDecimal(0)) > 0) {

					List lista = null;
					Iterator iter = null;

					if (this.origen.equalsIgnoreCase("P")) {
						Stock stock = Common.getStock();
						lista = stock.getLov_vanexosclieproveedoPK(
								this.codigo_anexo, this.origen, this.idempresa);

						iter = lista.iterator();

						if (iter.hasNext()) {

							String[] datos = (String[]) iter.next();
							this.razon_social = datos[1];
							this.localidad = datos[3];
							this.domicilio = datos[4];
							this.codigo_postal = datos[5];
							this.provincia = datos[7];
							this.cuit = datos[8];
							this.iibb = datos[9];
						} else {

							log
									.warn("setDatosAnexo(): Imposible recuperar datos proveedor: ( "
											+ this.codigo_anexo
											+ " - "
											+ this.origen + " )");
							fOk = false;

						}
					} else if (this.origen.equalsIgnoreCase("C")) {
						log.info("C");
						Clientes clientes = Common.getClientes();
						lista = clientes.getClientesClientesDomiPK(
								this.codigo_anexo, this.iddomicilio,
								this.idempresa);

						iter = lista.iterator();

						if (iter.hasNext()) {

							String[] datos = (String[]) iter.next();

							// this.razon_social = datos[1];
							// this.localidad = datos[3];
							// this.domicilio = datos[4];
							// this.codigo_postal = datos[5];
							// this.provincia = datos[7];
							// this.cuit = datos[8];
							// this.iibb = datos[9];

							// ---------------------------------------

							this.razon_social = datos[1];
							this.tipodocumento = datos[3];
							// this.nrodocumento = datos[4];
							this.cuit = datos[4];
							this.iibb = datos[5];
							// this.idtipoiva = new BigDecimal(datos[6]);
							// this.tipoiva = datos[7];
							this.idlocalidad = new BigDecimal(datos[27]);
							this.localidad = datos[28];
							this.idprovincia = new BigDecimal(datos[29]);
							this.provincia = datos[30];
							this.codigo_postal = datos[31];
							this.domicilio = datos[32];
						} else {

							log
									.warn("setDatosAnexo(): Imposible recuperar datos cliente: ( "
											+ this.codigo_anexo
											+ " - "
											+ this.origen + " )");
							fOk = false;

						}
					}
				}
			}

		} catch (Exception e) {
			fOk = false;
			log.error("setDatosAnexo():" + e);
		}

		return fOk;
	}

	private BigDecimal getTotalesAsiento(Hashtable ht, int indice) {
		BigDecimal total = new BigDecimal(0);
		Enumeration en;
		try {
			if (ht != null) {
				en = ht.keys();
				while (en.hasMoreElements()) {
					Object clave = en.nextElement();
					String[] datos = (String[]) ht.get(clave);
					total = total.add(new BigDecimal(datos[indice]));
				}
			}
		} catch (Exception e) {

			log.error("getTotalesAsiento(Hashtable ht, int indice):");

		}
		return total;
	}

	public String getValidar() {
		return validar;
	}

	public void setValidar(String validar) {
		this.validar = validar;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getVolver() {
		return volver;
	}

	public void setVolver(String volver) {
		this.volver = volver;
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

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public BigDecimal getCodigo_anexo() {
		return codigo_anexo;
	}

	public void setCodigo_anexo(BigDecimal codigo_anexo) {
		this.codigo_anexo = codigo_anexo;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public BigDecimal getIddomicilio() {
		return iddomicilio;
	}

	public void setIddomicilio(BigDecimal iddomicilio) {
		this.iddomicilio = iddomicilio;
	}

	public BigDecimal getIdanexo() {
		return idanexo;
	}

	public void setIdanexo(BigDecimal idanexo) {
		this.idanexo = idanexo;
	}

	public BigDecimal getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(BigDecimal idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getIibb() {
		return iibb;
	}

	public void setIibb(String iibb) {
		this.iibb = iibb;
	}

	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLabelOrigen() {
		if (this.origen.equalsIgnoreCase("C"))
			labelOrigen = "CLIENTE";
		else if (this.origen.equalsIgnoreCase("P"))
			labelOrigen = "PROVEEDOR";
		else if (this.origen.equalsIgnoreCase("O"))
			labelOrigen = "OTROS";
		return labelOrigen;
	}

	public void setLabelOrigen(String labelOrigen) {
		this.labelOrigen = labelOrigen;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public String getConfirma_origen() {
		return confirma_origen;
	}

	public void setConfirma_origen(String confirma_origen) {
		this.confirma_origen = confirma_origen;
	}

	public String getOrigenanterior() {
		return origenanterior;
	}

	public void setOrigenanterior(String origenanterior) {
		this.origenanterior = origenanterior;
	}

	public BigDecimal getTotalDebe() {
		return totalDebe;
	}

	public void setTotalDebe(BigDecimal totalDebe) {
		this.totalDebe = totalDebe;
	}

	public BigDecimal getTotalHaber() {
		return totalHaber;
	}

	public void setTotalHaber(BigDecimal totalHaber) {
		this.totalHaber = totalHaber;
	}

	public boolean isRemitopendiente() {
		return remitopendiente;
	}

	public void setRemitopendiente(boolean remitopendiente) {
		this.remitopendiente = remitopendiente;
	}

	public String getRemito_ms() {
		return remito_ms;
	}

	public void setRemito_ms(String remito_ms) {
		this.remito_ms = remito_ms;
	}

	public int getEjercicioactivo() {
		return ejercicioactivo;
	}

	public void setEjercicioactivo(int ejercicioactivo) {
		this.ejercicioactivo = ejercicioactivo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BigDecimal getIdcontadorcomprobante() {
		return idcontadorcomprobante;
	}

	public void setIdcontadorcomprobante(BigDecimal idcontadorcomprobante) {
		this.idcontadorcomprobante = idcontadorcomprobante;
	}

}
