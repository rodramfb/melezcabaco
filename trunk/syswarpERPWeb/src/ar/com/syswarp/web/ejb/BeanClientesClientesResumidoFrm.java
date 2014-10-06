/* 
 javabean para la entidad (Formulario): clientesClientes
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Feb 12 11:19:57 ART 2008 
 
 Para manejar la pagina: clientesClientesFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;
import ar.com.syswarp.bc.TarjetaCreditoValidacion;

public class BeanClientesClientesResumidoFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesClientesResumidoFrm.class);

	private String validar = "";

	private String agregarDomicilio = "";

	private String idprecarga = "";

	private boolean primeraCarga = true;

	private String idcliente = "";

	private BigDecimal idtipodomicilio = new BigDecimal(-1);

	private String tipodomicilio = "";

	private String razon = "";

	private BigDecimal idtipodocumento = new BigDecimal(-1);

	private String tipodocumento = "";

	private String nrodocumento = "";

	private BigDecimal idcategoria = new BigDecimal(0);

	private String categoria = "Categoria X";

	private String brutos = "";

	private BigDecimal idtipoiva = new BigDecimal(-1);

	private String tipoiva = "";

	private BigDecimal idvendedor = new BigDecimal(0);

	private String vendedor = "";

	private BigDecimal idcondicion = new BigDecimal(-1);

	private String condicion = "";

	private Timestamp fven = null;

	private String anioVenTarj = "";

	private String mesVenTarj = "";

	private String descuento1 = "0";

	private String descuento2 = "0";

	private String descuento3 = "0";

	private String idctaneto = "";

	private BigDecimal idmoneda = new BigDecimal(-1);

	private String moneda = "";

	private BigDecimal idlista = new BigDecimal(-1);

	private String lista = "";

	private BigDecimal idtipoclie = new BigDecimal(-1);

	private String tipoclie = "";

	private String observacion = "";

	private String lcredito = "0";

	private BigDecimal idtipocomp = new BigDecimal(-1);

	private String tipocomp = "";

	private String autorizado = "";

	private BigDecimal idcredcate = new BigDecimal(-1);

	private String credcate = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal prospecto = new BigDecimal(0);
	private String d_prospecto = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	private String accion = "";

	private String subAccion = "";

	private String idSubAccion = "";

	private BigDecimal reducido = new BigDecimal(1);

	// DATOS DOMICILIOS CLIENTE ..

	private BigDecimal iddomicilio = new BigDecimal(0);

	private String esdefault = "";

	private String calle = "";

	private String nro = "";

	private String piso = "";

	private String depto = "";

	private BigDecimal idlocalidad = new BigDecimal(0);

	private String localidad = "";

	private String cpa = "";

	private String postal = "";

	private String contacto = "";

	private String cargocontacto = "";

	private String telefonos = "";

	private String celular = "";

	private String fax = "";

	private String web = "";

	private BigDecimal idanexolocalidad = new BigDecimal(-1);

	private BigDecimal idzona = new BigDecimal(-1);

	private String zona = "";

	private BigDecimal idexpreso = new BigDecimal(-1);

	/*
	 * nota: se crea la variable resumido la inicializo en 0 esto es para poder
	 * capturarla en el metodo y ejecutar lo que corresponda para el valor 0
	 */

	private BigDecimal resumido = new BigDecimal(0);

	private String expreso = "";

	private BigDecimal idcobrador = new BigDecimal(1);

	private String cobrador = "";

	private String[] email = null;

	private String obsentrega = "";

	private Hashtable htDomiciliosCliente = new Hashtable();

	private String statusItem = "";

	// DATOS TARJETAS CLIENTE

	private BigDecimal idtarjeta = new BigDecimal(0);

	private BigDecimal idtarjetacredito = new BigDecimal(-1);

	private String tarjetacredito = "";

	private BigDecimal idtipotarjeta = new BigDecimal(-1);

	private String tipotarjeta = "";

	private String nrotarjeta = "";

	private String nrocontrol = "0";

	private String fecha_emision = "";

	private String fecha_vencimiento = "";

	private String titular = "";

	private String orden = "1";

	private String activa = "S";

	private Hashtable htTarjetasCliente = new Hashtable();

	//

	private List monedaList = new ArrayList();

	private List listasPreciosList = new ArrayList();

	private List tiposComprobList = new ArrayList();

	/* private List listPreferencias = new ArrayList(); */

	/* private List listEstados = new ArrayList(); */

	private List listIva = new ArrayList();

	private List listCondicion = new ArrayList();

	private List listCategoriaCred = new ArrayList();

	private List listTiposDoc = new ArrayList();

	private List listTiposTarjeta = new ArrayList();

	private List listTiposDomicilio = new ArrayList();

	//

	private TarjetaCreditoValidacion validaTCredito = new TarjetaCreditoValidacion();

	public BeanClientesClientesResumidoFrm() {
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

	// --
	public void ejecutarSentenciaDML() {
		try {
			Clientes clientesClientes = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta")) {
				this.mensaje = clientesClientes.clientesClientesCreate(
						new BigDecimal(this.idcliente), this.razon,
						this.idtipodocumento,
						new BigDecimal(this.nrodocumento), this.idcategoria,
						this.brutos, this.idtipoiva, this.idcondicion,
						new Double(this.descuento1),
						new Double(this.descuento2),
						new Double(this.descuento3), this.idctaneto.equals("")
								|| this.idctaneto.equals("null") ? null
								: new BigDecimal(this.idctaneto),
						this.idmoneda, this.idlista, GeneralBean.setNull(
								this.idtipoclie, 1), this.observacion,
						new Double(this.lcredito), GeneralBean.setNull(
								this.idtipocomp, 1), this.autorizado,
						this.idcredcate, this.htDomiciliosCliente,
						this.htTarjetasCliente, (String[]) this.request
								.getSession().getAttribute("idmes"),
						this.idempresa, this.usuarioalt, this.prospecto,
						this.resumido,
						// TODO: 20110624 - EJV - Factuaracion FE-CF-MA -->
						null
				// <--
						);

				if (this.mensaje.equalsIgnoreCase("OK")) {
					this.mensaje = "Cliente generado correctamente.";
					this.accion = "modificacion";
					this.primeraCarga = true;
				}

			} else if (this.accion.equalsIgnoreCase("modificacion")) {
				this.mensaje = clientesClientes.clientesClientesUpdate(
						new BigDecimal(this.idcliente), this.razon,
						this.idtipodocumento,
						new BigDecimal(this.nrodocumento), this.brutos,
						this.idtipoiva, this.idcondicion, new Double(
								this.descuento1), new Double(this.descuento2),
						new Double(this.descuento3), this.idctaneto.equals("")
								|| this.idctaneto.equals("null") ? null
								: new BigDecimal(this.idctaneto),
						this.idmoneda, this.idlista, GeneralBean.setNull(
								this.idtipoclie, 1), this.observacion,
						new Double(this.lcredito), GeneralBean.setNull(
								this.idtipocomp, 1), this.autorizado,
						this.idcredcate, this.htDomiciliosCliente,
						this.htTarjetasCliente, this.idempresa,
						this.usuarioact, this.resumido,
						// TODO: 20110624 - EJV - Factuaracion FE-CF-MA -->
						null
				// <--
						);

				if (this.mensaje.equalsIgnoreCase("OK")) {
					this.mensaje = "Cliente actualizado correctamente.";
					this.primeraCarga = true;
				}
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	// --
	public void getDatosClientesClientes() {
		try {

			// log.info("INICIA getDatosClientesClientes ....");
			// log.info("primeraCarga: " + this.primeraCarga);

			if (this.isPrimeraCarga()) {

				this.resetHtSession();

				// log.info("RESETEO Session ....");

				Clientes clientesClientes = Common.getClientes();
				List listClientesClientes = clientesClientes
						.getClientesClientesPK(new BigDecimal(this.idcliente),
								this.idempresa);

				Iterator iterClientesClientes = listClientesClientes.iterator();
				if (iterClientesClientes.hasNext()) {
					String[] uCampos = (String[]) iterClientesClientes.next();
					// TODO: Constructores para cada tipo de datos

					/*
					 * 0 cl.idcliente - 1 cl.razon - 2 cl.idtipodocumento - 3
					 * td.tipodocumento - 4 cl.nrodocumento - 5 cl.brutos - 6
					 * cl.idtipoiva - 7 ti.tipoiva, - 8 cl.idcondicion - 9
					 * co.condicion - 10 cl.descuento1 - 11 cl.descuento2 - 12
					 * cl.descuento3 - 13 cl.idctaneto - 14 cc.credcate - 15
					 * cl.idmoneda - 16 mo.moneda - 17 cl.idlista - 18
					 * li.descri_lis - 19 cl.idtipoclie - 20 tcl.tipoclie - 21
					 * cl.observacion - 22 cl.lcredito - 23 cl.idtipocomp - 24
					 * tc.descri_tc - 25 cl.autorizado - 26 cl.idcredcate - 27
					 * cl.idempresa - 28 cl.usuarioalt - 29 cl.usuarioact - 30
					 * cl.fechaalt - 31 cl.fechaact -
					 */

					// for (int f = 0; f < uCampos.length; f++) {
					// log.info("indice " + f + " :" + uCampos[f]);
					// }
					this.idcliente = uCampos[0];
					this.razon = uCampos[1];
					this.idtipodocumento = new BigDecimal(uCampos[2]);
					this.tipodocumento = uCampos[3];
					this.nrodocumento = uCampos[4];
					this.brutos = uCampos[5];
					this.idtipoiva = new BigDecimal(uCampos[6]);
					this.tipoiva = uCampos[7];
					this.idcondicion = new BigDecimal(uCampos[8]);
					this.condicion = uCampos[9];
					this.descuento1 = uCampos[10];
					this.descuento2 = uCampos[11];
					this.descuento3 = uCampos[12];
					this.idctaneto = uCampos[13];
					this.idmoneda = new BigDecimal(uCampos[14]);
					this.moneda = uCampos[15];
					this.idlista = new BigDecimal(uCampos[16] == null ? "-1"
							: uCampos[16]);
					this.lista = uCampos[17];
					this.idtipoclie = new BigDecimal(uCampos[18] == null ? "-1"
							: uCampos[18]);
					this.tipoclie = uCampos[19];
					this.observacion = uCampos[20];
					this.lcredito = uCampos[21];
					this.idtipocomp = new BigDecimal(uCampos[22] == null ? "-1"
							: uCampos[22]);
					this.tipocomp = uCampos[23];
					this.autorizado = uCampos[24];
					this.idcredcate = new BigDecimal(uCampos[25] == null ? "-1"
							: uCampos[25]);
					this.credcate = uCampos[26];
					// this.idvendedor = new BigDecimal(uCampos[27]);
					// this.vendedor = uCampos[28];
					// this.idempresa = new BigDecimal(uCampos[28]);

					Hashtable htMails = new Hashtable();
					Iterator iter;
					listClientesClientes = clientesClientes
							.getClientesEmailXCliente(new BigDecimal(
									this.idcliente), this.idempresa);

					iter = listClientesClientes.iterator();
					String domi = "";
					String token = "";

					while (iter.hasNext()) {

						String[] mails = (String[]) iter.next();
						if (listClientesClientes.size() == 1) {
							htMails.put(domi, mails[3]);
							token = "";
						} else if ((!domi.equalsIgnoreCase(mails[1]) && !domi
								.equals(""))) {
							htMails.put(domi, mails[3]);
							htMails.put(domi, token.split("#"));
							token = "";
						}

						token += mails[3] + "#";
						domi = mails[1];
					}

					if (!token.equals("") && !htMails.containsKey(domi)) {
						htMails.put(domi, token.split("#"));
					}

					// TODO:
					listClientesClientes = clientesClientes
							.getClienteTarjetasCliente(100, 0, new BigDecimal(
									this.idcliente), this.idempresa);

					iter = listClientesClientes.iterator();

					while (iter.hasNext()) {

						String[] tarjetas = (String[]) iter.next();

						tarjetas = new String[] {
								tarjetas[0],
								tarjetas[1],
								tarjetas[2],
								tarjetas[4],
								tarjetas[5],
								tarjetas[6],
								tarjetas[7],
								tarjetas[8] != null ? Common
										.setObjectToStrOrTime(java.sql.Date
												.valueOf(tarjetas[8]),
												"JSDateToStr")
										+ "" : "",
								Common.setObjectToStrOrTime(
										java.sql.Date.valueOf(tarjetas[9]),
										"JSDateToStr").toString(),
								tarjetas[10], tarjetas[11], tarjetas[12], "M" };

						// log.info("llena tarjetas ...." + tarjetas[0]);
						this.htTarjetasCliente.put(tarjetas[0], tarjetas);

					}

					session.setAttribute("htTarjetasCliente",
							this.htTarjetasCliente);

					// TODO:
					listClientesClientes = clientesClientes
							.getClientesDomiciliosClienteResumido(100, 0,
									new BigDecimal(this.idcliente),
									this.idempresa);

					/*
					 * iddomicilio - idcliente - idtipodomicilio - tipodomicilio
					 * - esdefault - calle - nro - piso - depto - idlocalidad -
					 * localidad - cpa - postal - contacto - cargocontacto -
					 * telefonos - celular - fax - web - idzona - zona -
					 * idexpreso - expreso - idcobrador - cobrador - idvendedor
					 * - vendedor idempresa - usuarioalt - usuarioact - fechaalt
					 * - fechaact - diasauditados -
					 */
					iter = listClientesClientes.iterator();
					while (iter.hasNext()) {

						Object[] domicilios = (Object[]) iter.next();

						domicilios = new Object[] { domicilios[0],
								domicilios[2], domicilios[3], domicilios[4],
								domicilios[5], domicilios[6], domicilios[7],
								domicilios[8], domicilios[9], domicilios[10],
								domicilios[11], domicilios[12], domicilios[13],
								domicilios[14], domicilios[15], domicilios[16],
								domicilios[17], domicilios[18],
								domicilios[19] == null ? "-1" : domicilios[19],
								domicilios[20] == null ? "" : domicilios[20],
								domicilios[21] == null ? "-1" : domicilios[21],
								domicilios[22] == null ? "" : domicilios[22],
								domicilios[23] == null ? "-1" : domicilios[23],
								domicilios[24] == null ? "" : domicilios[24],
								domicilios[25] == null ? "-1" : domicilios[25],
								domicilios[26] == null ? "" : domicilios[26],
								htMails.get(domicilios[0]), "M",
								domicilios[36] == null ? "-1" : domicilios[36],
								domicilios[39], domicilios[40] };

						// log.info("llena domicilios ...." + domicilios[0]);
						this.htDomiciliosCliente.put(domicilios[0].toString(),
								domicilios);

					}

					session.setAttribute("htDomiciliosCliente",
							this.htDomiciliosCliente);

					// ---------------------------------
					// Categoria actual del socio.

					listClientesClientes = clientesClientes
							.getBacoTmCategoriaActualCliente(new BigDecimal(
									idcliente), idempresa);

					iter = listClientesClientes.iterator();

					if (iter.hasNext()) {

						String[] datos = (String[]) iter.next();

						if (!Common.setNotNull(datos[3]).equals("")) {
							this.idcategoria = new BigDecimal(datos[3]);
							this.categoria = datos[4];
						} else {

							this.idcategoria = new BigDecimal(0);
							this.categoria = "";
							// this.mensaje = "Cliente no categorizado.";
						}

					} else {
						this.idcategoria = new BigDecimal(0);
						this.categoria = "";
						// this.mensaje =
						// "Imposible recuperar categoria actual del cliente. / Cliente no categorizado.";
					}

				} else {
					this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
				}

			}

		} catch (Exception e) {
			log.error("getDatosClientesClientes():" + e);
		}
	}

	// --
	private void sugiereIdCliente() {
		try {
			Clientes clientesClientes = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta")) {
				if (this.idcliente.trim().equals("") || this.primeraCarga)
					if (this.validar.equalsIgnoreCase(""))
						this.idcliente = clientesClientes.getMaximoCliente(
								this.idempresa).toString();
			}

		} catch (Exception e) {
			log.error("sugiereIdCliente: " + e);
		}

	}

	// --
	private boolean isValidMail() {

		try {
			if (this.email != null) {
				for (int r = 0; this.email != null && r < this.email.length; r++) {
					if (!Common.isValidEmailAddress(this.email[r])) {
						this.mensaje = "Mail invalido: " + this.email[r];
						return false;
					}
				}
			}
		} catch (Exception e) {
			log.error("callValidMail: " + e);
			return false;
		}

		return true;
	}

	// --

	private boolean isRangoFechas(String desde, String hasta) {

		java.sql.Date fDesde = (java.sql.Date) Common.setObjectToStrOrTime(
				desde, "StrToJSDate");
		java.sql.Date fHasta = (java.sql.Date) Common.setObjectToStrOrTime(
				hasta, "StrToJSDate");

		try {

			if (fDesde.after(fHasta))
				return false;

		} catch (Exception e) {
			log.error("isRangoFechas: " + e);
		}

		return true;
	}

	// --

	private void resetHtSession() {

		this.htDomiciliosCliente = new Hashtable();
		this.htTarjetasCliente = new Hashtable();

		session.removeAttribute("htDomiciliosCliente");
		session.removeAttribute("htTarjetasCliente");
		session.removeAttribute("idmes");

		session.setAttribute("htDomiciliosCliente", this.htDomiciliosCliente);
		session.setAttribute("htTarjetasCliente", this.htTarjetasCliente);

	}

	// --
	public boolean ejecutarValidacion() {
		try {

			ArrayList aceptados = new ArrayList();
			aceptados.add(0, "All");

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesClientesResumidoAbm.jsp");
				return true;
			}

			//

			this.monedaList = Common.getGeneral().getGlobalmonedasAll(50, 0);
			this.tiposComprobList = Common.getClientes()
					.getClientesTipoCompAll(100, 0, this.idempresa);
			this.listasPreciosList = Common.getClientes().getClienteslistasAll(
					100, 0, this.idempresa);

			this.listIva = Common.getClientes().getClientestablaivaAll(100, 0,
					this.idempresa);
			this.listCondicion = Common.getClientes().getClientescondicioAll(
					100, 0, this.idempresa);
			this.listCategoriaCred = Common.getClientes()
					.getClientescredcateAll(100, 0, this.idempresa);
			this.listTiposDoc = Common.getGeneral()
					.getGlobaltiposdocumentosAll(100, 0, this.idempresa);
			this.listTiposTarjeta = Common.getClientes()
					.getClientesTipoTarjetasLovAll(100, 0, this.idempresa);
			this.listTiposDomicilio = Common.getClientes()
					.getClientesTiposDomiciliosAll(100, 0, this.idempresa);

			//

			this.sugiereIdCliente();

			if (isPrimeraCarga()) {
				resetHtSession();
			}

			this.htDomiciliosCliente = (Hashtable) session
					.getAttribute("htDomiciliosCliente");
			this.htTarjetasCliente = (Hashtable) session
					.getAttribute("htTarjetasCliente");

			/**
			 * TARJETAS -->
			 */

			// log.info("subAccion: " + subAccion);
			if (this.subAccion.equalsIgnoreCase("agregarTarjeta")) {

				/*
				 * AGREGAR TARJETA
				 */

				/*
				 * idtarjeta,idtarjetacredito,
				 * tarjetacredito,idtipotarjeta,tipotarjeta,
				 * nrotarjeta,nrocontrol
				 * ,fecha_emision,fecha_vencimiento,titular,
				 * orden,activa,idempresa,
				 * usuarioalt,usuarioact,fechaalt,fechaact"
				 */

				String[] tarjetasCliente = null;

				/*
				 * VALIDAR INGRESO DE DATOS TARJETA
				 */
				General general = Common.getGeneral();

				/*
				 * 
				 * 
				 * this.mensaje =
				 * clientesClientes.getValidacionTarjetaDeCredito(
				 * this.idempresa, this.nrotarjeta, this.idtarjetacredito); if
				 * (mensaje .equalsIgnoreCase("La longitud de la tarjeta es
				 * incorrecta")) { return false; }
				 * 
				 * if (this.accion.equalsIgnoreCase("alta")) { this.mensaje =
				 * clientesClientes
				 * .getValidacionTarjetaDeCreditoExistenciaaAlta(
				 * this.idempresa, this.nrotarjeta, this.idtarjetacredito); if
				 * (mensaje.equalsIgnoreCase("La tarjeta ya existe!")) {
				 * this.mensaje = "La tarjeta ya existe, por favor verifique!";
				 * return false; } }
				 * 
				 * if (this.accion.equalsIgnoreCase("Modificacion")) {
				 * this.mensaje = clientesClientes
				 * .getValidacionTarjetaDeCreditoExistenciaaModificacion(
				 * this.idempresa, this.nrotarjeta, this.idtarjetacredito,
				 * this.idtarjeta); if (mensaje.equalsIgnoreCase("La tarjeta ya
				 * existe!")) { this.mensaje = "La tarjeta ya existe, por favor
				 * verifique!"; return false; } }
				 * 
				 * this.mensaje = clientesClientes
				 * .validarTarjetaDeCredito(this.nrotarjeta); if
				 * (mensaje.equalsIgnoreCase("NOOK")) { this.mensaje = "El
				 * numero de tarjeta es invalido, por favor verifique!"; return
				 * false; }
				 */

				this.fven = (Timestamp) Common.setObjectToStrOrTime(
						this.fecha_vencimiento, "StrToJSTs");

				if (this.fven != null) {
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(fven.getTime());
					this.mesVenTarj = (cal.get(Calendar.MONTH) + 1) + "";
					this.anioVenTarj = cal.get(Calendar.YEAR) + "";
				}

				if (this.idtarjetacredito.longValue() <= 0) {
					this.mensaje = "Seleccione tarjeta de credito.";
				} else if (this.idtipotarjeta.longValue() <= 0) {
					this.mensaje = "Seleccione tipo tarjeta";
				} else if (this.nrotarjeta.trim().equals("")) {
					this.mensaje = "Ingrese nro. tarjeta valido.";

					//	

				} else if (!validaTCredito.validateCreditCard(this.nrotarjeta,
						"_es", aceptados, "N", this.mesVenTarj,
						this.anioVenTarj)) {

					this.mensaje = validaTCredito.CCVSError;
					log.info("CCVSType: " + validaTCredito.CCVSType);

					// } else if
					// (general.validarTarjetaDeCredito(this.nrotarjeta)
					// .equalsIgnoreCase("NOOK")) {
					// this.mensaje =
					// "El numero de tarjeta es invalido, por favor verifique!";
					//
					// } else if (general.getValidacionTarjetaDeCredito(
					// this.idempresa, this.nrotarjeta, this.idtarjetacredito)
					// .equalsIgnoreCase(
					// "La longitud de la tarjeta es incorrecta")) {
					// this.mensaje =
					// "La longitud de la tarjeta es incorrecta, por favor verifique!";

				} else if (this.accion.equalsIgnoreCase("alta")
						&& general
								.getValidacionTarjetaDeCreditoExistenciaaAlta(
										this.idempresa, this.nrotarjeta,
										this.idtarjetacredito)
								.equalsIgnoreCase("La tarjeta ya existe!")) {
					this.mensaje = "La tarjeta ya se encuentra asociada al cliente: "
							+ general.getClienteTarjeta(this.idempresa,
									this.nrotarjeta, this.idtarjetacredito);

				} else if (this.accion.equalsIgnoreCase("modificacion")
						&& general
								.getValidacionTarjetaDeCreditoExistenciaaModificacion(
										this.idempresa, this.nrotarjeta,
										this.idtarjetacredito, this.idtarjeta)
								.equalsIgnoreCase("La tarjeta ya existe!")) {
					this.mensaje = "La tarjeta ya se encuentra asociada al cliente: "
							+ general.getClienteTarjeta(this.idempresa,
									this.nrotarjeta, this.idtarjetacredito);
					//

				} else if (!Common.esEntero(this.nrocontrol)) {
					this.mensaje = "Ingrese nro. control tarjeta valido.";
				} else if (this.titular.trim().equals("")) {
					this.mensaje = "Ingrese titular de tarjeta.";
				} else if (!Common.esEntero(this.orden)) {
					this.mensaje = "Ingrese orden de tarjeta valido.";

				} else if (!this.fecha_emision.equals("")
						&& (!Common.isFormatoFecha(this.fecha_emision) || !Common
								.isFechaValida(this.fecha_emision))) {
					this.mensaje = "Ingrese fecha de emision tarjeta.";

				} else if (!Common.isFormatoFecha(this.fecha_vencimiento)
						|| !Common.isFechaValida(this.fecha_vencimiento)) {
					this.mensaje = "Ingrese fecha de validez tarjeta.";

				} else if (this.fven == null
						|| !this.fven.after(Calendar.getInstance().getTime())) {
					this.mensaje = "Fecha de Vencimiento no puede ser menor a la fecha Actual";

				} else if (!this.fecha_emision.equals("")
						&& !isRangoFechas(this.fecha_emision,
								this.fecha_vencimiento)) {
					this.mensaje = "Fecha de emision no puede ser superior a fecha de validez";

				} else if (this.activa.trim().equals("")) {
					this.mensaje = "Seleccionar tarjeta activa.";
				} else {

					if (this.idtarjeta.longValue() <= 0) {
						// fgp arreglo 25/09/09
						if (this.idtarjeta.signum() == -1)
							this.idtarjeta = new BigDecimal(Calendar
									.getInstance().getTimeInMillis()).negate();
						if (!this.htTarjetasCliente.containsKey(this.idtarjeta
								.toString())) {
							this.idtarjeta = new BigDecimal(
									this.htTarjetasCliente.size() + 1);
							this.idtarjeta = this.idtarjeta.negate();
						}

						this.statusItem = "A";
					} else
						this.statusItem = "U";

					tarjetasCliente = new String[] { this.idtarjeta.toString(),
							this.idtarjetacredito.toString(),
							this.tarjetacredito, this.idtipotarjeta.toString(),
							this.tipotarjeta, this.nrotarjeta, this.nrocontrol,
							this.fecha_emision, this.fecha_vencimiento,
							this.titular, this.orden, this.activa,
							this.statusItem };

					this.htTarjetasCliente.put(this.idtarjeta.toString(),
							tarjetasCliente);
					session.setAttribute("htTarjetasCliente",
							this.htTarjetasCliente);
				}

			} else if (this.subAccion.equalsIgnoreCase("modificaTarjeta")) {

				/*
				 * MODIFICAR TARJETA
				 */

				String[] tarjetasCliente = null;
				// this.htTarjetasCliente = (Hashtable) session
				// .getAttribute("htTarjetasCliente");
				tarjetasCliente = (String[]) this.htTarjetasCliente
						.get(this.idSubAccion);

				this.idtarjeta = new BigDecimal(tarjetasCliente[0]);
				this.idtarjetacredito = new BigDecimal(tarjetasCliente[1]);
				this.tarjetacredito = tarjetasCliente[2];
				this.idtipotarjeta = new BigDecimal(tarjetasCliente[3]);
				this.tipotarjeta = tarjetasCliente[4];
				this.nrotarjeta = tarjetasCliente[5];
				this.nrocontrol = tarjetasCliente[6];
				this.fecha_emision = tarjetasCliente[7];
				this.fecha_vencimiento = tarjetasCliente[8];
				this.titular = tarjetasCliente[9];
				this.orden = tarjetasCliente[10];
				this.activa = tarjetasCliente[11];

			} else if (this.subAccion.equalsIgnoreCase("bajaTarjeta")) {
				/*
				 * ELIMINAR TARJETA
				 */
				String[] tarjetasCliente = null;
				// this.htTarjetasCliente = (Hashtable) session
				// .getAttribute("htTarjetasCliente");
				tarjetasCliente = (String[]) this.htTarjetasCliente
						.get(this.idSubAccion);

				tarjetasCliente[12] = "B";

				session.setAttribute("htTarjetasCliente",
						this.htTarjetasCliente);

				this.idtarjeta = new BigDecimal(0);
				this.idtarjetacredito = new BigDecimal(-1);
				this.tarjetacredito = "";
				this.idtipotarjeta = new BigDecimal(-1);
				this.tipotarjeta = "";
				this.nrotarjeta = "";
				this.nrocontrol = "";
				this.fecha_emision = "";
				this.fecha_vencimiento = "";
				this.titular = "";
				this.orden = "";
				this.activa = "";

			} else if (this.subAccion.equalsIgnoreCase("undoBajaTarjeta")) {

				/*
				 * DESHACER ELIMINAR TARJETA
				 */
				String[] tarjetasCliente = null;
				// this.htTarjetasCliente = (Hashtable) session
				// .getAttribute("htTarjetasCliente");

				tarjetasCliente = (String[]) this.htTarjetasCliente
						.get(this.idSubAccion);
				tarjetasCliente[12] = Long.parseLong(this.idSubAccion) < 1 ? "A"
						: "M";

				session.setAttribute("htTarjetasCliente",
						this.htTarjetasCliente);

				/**
				 * <-- TARJETAS
				 */
			} else if (this.subAccion.equalsIgnoreCase("modificaDomicilio")) {

				/**
				 * DOMICILIOS -->
				 */

				/*
				 * MODIFICAR DOMICILIO
				 */
				// if (!this.modificaDomicilio.equalsIgnoreCase("")) {
				Object[] domicilioCliente = null;
				// this.htDomiciliosCliente = (Hashtable) session
				// .getAttribute("htDomiciliosCliente");

				domicilioCliente = (Object[]) this.htDomiciliosCliente
						.get(this.idSubAccion);

				this.iddomicilio = new BigDecimal(domicilioCliente[0]
						.toString());
				this.idtipodomicilio = new BigDecimal(domicilioCliente[1]
						.toString());
				this.tipodomicilio = domicilioCliente[2].toString();
				this.esdefault = domicilioCliente[3].toString();
				this.calle = domicilioCliente[4].toString();
				this.nro = domicilioCliente[5].toString();
				this.piso = domicilioCliente[6].toString();
				this.depto = domicilioCliente[7].toString();
				this.idlocalidad = new BigDecimal(domicilioCliente[8]
						.toString());
				this.localidad = domicilioCliente[9].toString();
				this.cpa = domicilioCliente[10].toString();
				this.postal = domicilioCliente[11].toString();
				this.contacto = domicilioCliente[12].toString();
				this.cargocontacto = domicilioCliente[13].toString();
				this.telefonos = domicilioCliente[14].toString();
				this.celular = domicilioCliente[15].toString();
				this.fax = domicilioCliente[16].toString();
				this.web = domicilioCliente[17].toString();
				this.idzona = new BigDecimal(domicilioCliente[18].toString());
				this.zona = domicilioCliente[19].toString();
				this.idexpreso = new BigDecimal(domicilioCliente[20].toString());
				this.expreso = domicilioCliente[21].toString();
				this.idcobrador = new BigDecimal(domicilioCliente[22]
						.toString());
				this.cobrador = domicilioCliente[23].toString();
				this.idvendedor = new BigDecimal(domicilioCliente[24]
						.toString());
				this.vendedor = domicilioCliente[25].toString();
				this.email = (String[]) domicilioCliente[26];
				this.idanexolocalidad = new BigDecimal(domicilioCliente[29]
						.toString());
				this.obsentrega = domicilioCliente[30].toString();

			} else if (this.subAccion.equalsIgnoreCase("bajaDomicilio")) {

				/*
				 * ELIMINAR DOMICILIO
				 */

				// if (!this.bajaDomicilio.equalsIgnoreCase("")) {
				Object[] domicilioCliente = null;
				// this.htDomiciliosCliente = (Hashtable) session
				// .getAttribute("htDomiciliosCliente");

				domicilioCliente = (Object[]) this.htDomiciliosCliente
						.get(this.idSubAccion);
				domicilioCliente[27] = "B";

				session.setAttribute("htDomiciliosCliente",
						this.htDomiciliosCliente);

				this.iddomicilio = new BigDecimal(0);
				this.idtipodomicilio = new BigDecimal(-1);
				this.tipodomicilio = "";
				this.esdefault = "";
				this.calle = "";
				this.nro = "";
				this.piso = "";
				this.depto = "";
				this.idlocalidad = new BigDecimal(-1);
				this.localidad = "";
				this.cpa = "";
				this.postal = "";
				this.contacto = "";
				this.cargocontacto = "";
				this.telefonos = "";
				this.celular = "";
				this.fax = "";
				this.web = "";
				this.idzona = new BigDecimal(-1);
				this.zona = "";
				this.idexpreso = new BigDecimal(-1);
				this.expreso = "";
				this.idcobrador = new BigDecimal(-1);
				this.cobrador = "";
				this.idvendedor = new BigDecimal(-1);
				this.vendedor = "";
				this.email = null;
				this.idanexolocalidad = new BigDecimal(-1);
				this.obsentrega = "";

			} else if (this.subAccion.equalsIgnoreCase("undoBajaDomicilio")) {

				/*
				 * DESHACER ELIMINAR DOMICILIO
				 */
				// if (!this.undoBajaDomicilio.equalsIgnoreCase("")) {
				Object[] domicilioCliente = null;
				// this.htDomiciliosCliente = (Hashtable) session
				// .getAttribute("htDomiciliosCliente");

				domicilioCliente = (Object[]) this.htDomiciliosCliente
						.get(this.idSubAccion);
				domicilioCliente[27] = Long.parseLong(this.idSubAccion) < 1 ? "A"
						: "M";

				session.setAttribute("htDomiciliosCliente",
						this.htDomiciliosCliente);

			} else if (this.subAccion.equalsIgnoreCase("agregarDomicilio")) {
				// if (!this.agregarDomicilio.equalsIgnoreCase("")) {
				/*
				 * AGREGAR UN NUEVO DOMICILIO
				 */
				Object[] domicilioCliente = null;
				// this.htDomiciliosCliente = (Hashtable) session
				// .getAttribute("htDomiciliosCliente");

				/*
				 * iddomicilio - idcliente - idtipodomicilio - tipodomicilio -
				 * esdefault - calle - nro - piso - depto - idlocalidad -
				 * localidad - cpa - postal - contacto - cargocontacto -
				 * telefonos - celular - fax - web - idzona - zona - idexpreso -
				 * expreso - idcobrador - cobrador - idvendedor - vendedor -
				 * idempresa - usuarioalt - usuarioact - fechaalt - fechaact -
				 */

				/*
				 * VALIDAR INGRESO DE DATOS DEL DOMICILIO
				 */

				if (this.calle.trim().equals("")) {
					this.mensaje = "Ingrese calle valida para domicilio.";
				} else if (this.idtipodomicilio.longValue() < 1) {
					this.mensaje = "Ingrese tipo de domicilio.";

					// } else if (this.idcobrador.longValue() < 0) {
					// this.mensaje = "Ingrese el Cobrador.";

				} else if (this.idlocalidad.longValue() < 1) {
					this.mensaje = "Ingrese localidad para domicilio.";
				} else if (!Common.esEntero(this.nro)) {
					this.mensaje = "Ingrese nro. valido para domicilio.";
				} else if (this.contacto.trim().equals("")) {
					this.mensaje = "Ingrese contacto para domicilio.";
				} else if (!isValidMail()) {
					this.mensaje = "Mail invalido.";
				} else if (this.idcondicion.longValue() < 1) {
					this.mensaje = "Seleccione condicion de pago.";
				} else if (this.idcondicion.longValue() == 1
						&& this.idcobrador.longValue() < 1) {
					this.mensaje = "Seleccion� condici�n de pago \"cobrador\", debe asignar uno.";
					/*-----------pedido amelia validacion que ingrese siempre un cobrador se hicieron
					 *           varias validaciones. 05/11/09 FGP 
					 */
					// } else if (this.idcondicion.longValue() == -1 &&
					// this.idcobrador.longValue() != -1) {
					// this.mensaje = "Debe seleccionar una Condicion de Pago.";
					// } else if (this.idcondicion.longValue() == 5 &&
					// this.idcobrador.longValue() == -1) {
					// this.mensaje =
					// "Debe seleccionar en el Cobrador Tarjeta de Credito.";
					// } else if (this.idcondicion.longValue() == 5 &&
					// this.idcobrador.longValue() != 1) {
					// this.mensaje =
					// "Debe seleccionar en el Cobrador Tarjeta de Credito.";
					// } else if (this.idcondicion.longValue() != 5 &&
					// this.idcobrador.longValue() == 1) {
					// this.mensaje =
					// "Si selecciona Condicion de Pago Tarjeta de Credito el Cobrador debe ser Tarjeta de Credito.";
					// } else if (this.idcobrador.longValue() == -1) {
					// this.mensaje = "Debe ingresar un Cobrador.";
					// ----------------------------------------------------------------------------------------
				} else {

					if (this.iddomicilio.longValue() <= 0) {
						// Siempre que se agrega un nuevo domicilio
						// fgp arreglo 25/09/09
						if (this.iddomicilio.signum() == -1)
							this.iddomicilio = new BigDecimal(Calendar
									.getInstance().getTimeInMillis()).negate();
						if (!this.htDomiciliosCliente
								.containsKey(this.iddomicilio.toString())) {
							this.iddomicilio = new BigDecimal(
									this.htDomiciliosCliente.size() + 1);
							this.iddomicilio = this.iddomicilio.negate();
						}

						this.statusItem = "A";
					} else
						this.statusItem = "U";
					domicilioCliente = new Object[] {
							this.iddomicilio.toString(),
							this.idtipodomicilio.toString(),
							this.tipodomicilio,
							this.esdefault.equalsIgnoreCase("") ? "N" : "S",
							this.calle, this.nro, this.piso, this.depto,
							this.idlocalidad.toString(), this.localidad,
							this.cpa, this.postal, this.contacto,
							this.cargocontacto, this.telefonos, this.celular,
							this.fax, this.web, this.idzona.toString(),
							this.zona, this.idexpreso.toString(), this.expreso,
							this.idcobrador.toString(), this.cobrador,
							this.idvendedor, this.vendedor, this.email,
							this.statusItem, "0", "-1", this.obsentrega };

					this.htDomiciliosCliente.put(this.iddomicilio.toString(),
							domicilioCliente);
					session.setAttribute("htDomiciliosCliente",
							this.htDomiciliosCliente);
				}
			}

			/**
			 * <-- DOMICILIOS
			 */

			// -- GENERAR / MODIFICAR
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (!Common.esEntero(this.idcliente)
							|| new BigDecimal(this.idcliente).longValue() < 0) {
						this.mensaje = "Ingrese un valor numerico valido para codigo.";
						return false;
					}

					if (this.razon == null || this.razon.trim().equals("")) {
						this.mensaje = "Ingrese razon social.";
						return false;
					}

					if (!Common.esEntero(this.nrodocumento)) {
						this.mensaje = "Ingrese nro. de documento valido.";
						return false;
					}

					if (this.idtipodocumento.longValue() < 0) {
						this.mensaje = "Seleccione tipo de documento.";
						return false;
					}

					if (this.idcategoria.longValue() < 0) {
						this.mensaje = "Ingrese categoria de cliente.";
						return false;
					}

					if (this.autorizado == null
							|| this.autorizado.trim().equalsIgnoreCase("")) {
						this.mensaje = "Seleccione autorizado.";
						return false;
					}

					if (idtipoiva.longValue() < 0) {
						this.mensaje = "Seleccione condicion fiscal(iva).";
						return false;
					}

					// if (!Common.esEntero(this.idctaneto)) {
					// this.mensaje = "Ingrese cuenta neto asociada.";
					// return false;
					// }

					if (this.idcondicion.longValue() < 0) {
						this.mensaje = "Seleccione condicion de pago. ";
						return false;
					}

					if (this.idmoneda.longValue() < 0) {
						this.mensaje = "Seleccione moneda asociada.";
						return false;
					}

					if (this.idcredcate.longValue() < 0) {
						this.mensaje = "Seleccione categoria de credito.";
						return false;
					}

					if (!Common.esPorcentaje(this.descuento1)) {
						this.mensaje = "Ingrese porcentaje valido para descuento uno.";
						return false;
					}

					if (!Common.esPorcentaje(this.descuento2)) {
						this.mensaje = "Ingrese porcentaje valido para descuento dos.";
						return false;
					}

					if (!Common.esPorcentaje(this.descuento3)) {
						this.mensaje = "Ingrese porcentaje valido para descuento tres.";
						return false;
					}

					if (!Common.esNumerico(this.lcredito)) {
						this.mensaje = "Ingrese valor numerico valido para limite de credito.";
						return false;
					}

					if (this.htDomiciliosCliente == null
							|| this.htDomiciliosCliente.isEmpty()) {
						this.mensaje = "Es necesario ingresar al menos un domicilio.";
						return false;
					}

				}

				this.ejecutarSentenciaDML();

				getDatosClientesClientes();

			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {

					getDatosClientesClientes();

				}
			}
		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
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

	// metodos para cada atributo de la entidad
	public String getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(String idcliente) {
		this.idcliente = idcliente;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public BigDecimal getIdtipodocumento() {
		return idtipodocumento;
	}

	public void setIdtipodocumento(BigDecimal idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public BigDecimal getIdcategoria() {
		return idcategoria;
	}

	public void setIdcategoria(BigDecimal idcategoria) {
		this.idcategoria = idcategoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getBrutos() {
		return brutos;
	}

	public void setBrutos(String brutos) {
		this.brutos = brutos;
	}

	public BigDecimal getIdtipoiva() {
		return idtipoiva;
	}

	public void setIdtipoiva(BigDecimal idtipoiva) {
		this.idtipoiva = idtipoiva;
	}

	public BigDecimal getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(BigDecimal idvendedor) {
		this.idvendedor = idvendedor;
	}

	public BigDecimal getIdcondicion() {
		return idcondicion;
	}

	public void setIdcondicion(BigDecimal idcondicion) {
		this.idcondicion = idcondicion;
	}

	public String getDescuento1() {
		return descuento1;
	}

	public void setDescuento1(String descuento1) {
		this.descuento1 = descuento1;
	}

	public String getDescuento2() {
		return descuento2;
	}

	public void setDescuento2(String descuento2) {
		this.descuento2 = descuento2;
	}

	public String getDescuento3() {
		return descuento3;
	}

	public void setDescuento3(String descuento3) {
		this.descuento3 = descuento3;
	}

	public String getIdctaneto() {
		return idctaneto;
	}

	public void setIdctaneto(String idctaneto) {
		this.idctaneto = idctaneto;
	}

	public BigDecimal getIdmoneda() {
		return idmoneda;
	}

	public void setIdmoneda(BigDecimal idmoneda) {
		this.idmoneda = idmoneda;
	}

	public BigDecimal getIdlista() {
		return idlista;
	}

	public void setIdlista(BigDecimal idlista) {
		this.idlista = idlista;
	}

	public BigDecimal getIdtipoclie() {
		return idtipoclie;
	}

	public void setIdtipoclie(BigDecimal idtipoclie) {
		this.idtipoclie = idtipoclie;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getLcredito() {
		return lcredito;
	}

	public void setLcredito(String lcredito) {
		this.lcredito = lcredito;
	}

	public BigDecimal getIdtipocomp() {
		return idtipocomp;
	}

	public void setIdtipocomp(BigDecimal idtipocomp) {
		this.idtipocomp = idtipocomp;
	}

	public String getAutorizado() {
		return autorizado;
	}

	public void setAutorizado(String autorizado) {
		this.autorizado = autorizado;
	}

	public BigDecimal getIdcredcate() {
		return idcredcate;
	}

	public void setIdcredcate(BigDecimal idcredcate) {
		this.idcredcate = idcredcate;
	}

	public String getCredcate() {
		return credcate;
	}

	public void setCredcate(String credcate) {
		this.credcate = credcate;
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

	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getTipoclie() {
		return tipoclie;
	}

	public void setTipoclie(String tipoclie) {
		this.tipoclie = tipoclie;
	}

	public String getTipocomp() {
		return tipocomp;
	}

	public void setTipocomp(String tipocomp) {
		this.tipocomp = tipocomp;
	}

	public String getTipoiva() {
		return tipoiva;
	}

	public void setTipoiva(String tipoiva) {
		this.tipoiva = tipoiva;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCargocontacto() {
		return cargocontacto;
	}

	public void setCargocontacto(String cargocontacto) {
		this.cargocontacto = cargocontacto;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCobrador() {
		return cobrador;
	}

	public void setCobrador(String cobrador) {
		this.cobrador = cobrador;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getCpa() {
		return cpa;
	}

	public void setCpa(String cpa) {
		this.cpa = cpa;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public String[] getEmail() {
		return email;
	}

	public void setEmail(String[] email) {
		this.email = email;
	}

	public String getEsdefault() {
		return esdefault;
	}

	public void setEsdefault(String esdefault) {
		this.esdefault = esdefault;
	}

	public String getExpreso() {
		return expreso;
	}

	public void setExpreso(String expreso) {
		this.expreso = expreso;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public BigDecimal getIdcobrador() {
		return idcobrador;
	}

	public void setIdcobrador(BigDecimal idcobrador) {
		this.idcobrador = idcobrador;
	}

	public BigDecimal getIddomicilio() {
		return iddomicilio;
	}

	public void setIddomicilio(BigDecimal iddomicilio) {
		this.iddomicilio = iddomicilio;
	}

	public BigDecimal getIdexpreso() {
		return idexpreso;
	}

	public void setIdexpreso(BigDecimal idexpreso) {
		this.idexpreso = idexpreso;
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

	public BigDecimal getIdzona() {
		return idzona;
	}

	public void setIdzona(BigDecimal idzona) {
		this.idzona = idzona;
	}

	public String getNro() {
		return nro;
	}

	public void setNro(String nro) {
		this.nro = nro;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getAgregarDomicilio() {
		return agregarDomicilio;
	}

	public void setAgregarDomicilio(String agregarDomicilio) {
		this.agregarDomicilio = agregarDomicilio;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	/*
	 * 
	 * public String getBajaDomicilio() { return bajaDomicilio; }
	 * 
	 * public void setBajaDomicilio(String bajaDomicilio) { this.bajaDomicilio =
	 * bajaDomicilio; }
	 * 
	 * public String getUndoBajaDomicilio() { return undoBajaDomicilio; }
	 * 
	 * public void setUndoBajaDomicilio(String undoBajaDomicilio) {
	 * this.undoBajaDomicilio = undoBajaDomicilio; }
	 * 
	 * public String getModificaDomicilio() { return modificaDomicilio; }
	 * 
	 * public void setModificaDomicilio(String modificaDomicilio) {
	 * this.modificaDomicilio = modificaDomicilio; }
	 */

	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public String getActiva() {
		return activa;
	}

	public void setActiva(String activa) {
		this.activa = activa;
	}

	public String getFecha_emision() {
		return fecha_emision;
	}

	public void setFecha_emision(String fecha_emision) {
		this.fecha_emision = fecha_emision;
	}

	public String getFecha_vencimiento() {
		return fecha_vencimiento;
	}

	public void setFecha_vencimiento(String fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}

	public BigDecimal getIdtarjeta() {
		return idtarjeta;
	}

	public void setIdtarjeta(BigDecimal idtarjeta) {
		this.idtarjeta = idtarjeta;
	}

	public BigDecimal getIdtarjetacredito() {
		return idtarjetacredito;
	}

	public void setIdtarjetacredito(BigDecimal idtarjetacredito) {
		this.idtarjetacredito = idtarjetacredito;
	}

	public BigDecimal getIdtipotarjeta() {
		return idtipotarjeta;
	}

	public void setIdtipotarjeta(BigDecimal idtipotarjeta) {
		this.idtipotarjeta = idtipotarjeta;
	}

	public String getNrocontrol() {
		return nrocontrol;
	}

	public void setNrocontrol(String nrocontrol) {
		this.nrocontrol = nrocontrol;
	}

	public String getNrotarjeta() {
		return nrotarjeta;
	}

	public void setNrotarjeta(String nrotarjeta) {
		this.nrotarjeta = nrotarjeta;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getTarjetacredito() {
		return tarjetacredito;
	}

	public void setTarjetacredito(String tarjetacredito) {
		this.tarjetacredito = tarjetacredito;
	}

	public String getTipotarjeta() {
		return tipotarjeta;
	}

	public void setTipotarjeta(String tipotarjeta) {
		this.tipotarjeta = tipotarjeta;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getSubAccion() {
		return subAccion;
	}

	public void setSubAccion(String subAccion) {
		this.subAccion = subAccion;
	}

	public String getIdSubAccion() {
		return idSubAccion;
	}

	public void setIdSubAccion(String idSubAccion) {
		this.idSubAccion = idSubAccion;
	}

	public BigDecimal getIdtipodomicilio() {
		return idtipodomicilio;
	}

	public void setIdtipodomicilio(BigDecimal idtipodomicilio) {
		this.idtipodomicilio = idtipodomicilio;
	}

	public String getTipodomicilio() {
		return tipodomicilio;
	}

	public void setTipodomicilio(String tipodomicilio) {
		this.tipodomicilio = tipodomicilio;
	}

	public BigDecimal getProspecto() {
		return prospecto;
	}

	public void setProspecto(BigDecimal prospecto) {
		this.prospecto = prospecto;
	}

	public String getD_prospecto() {
		return d_prospecto;
	}

	public void setD_prospecto(String d_prospecto) {
		this.d_prospecto = d_prospecto;
	}

	public BigDecimal getIdanexolocalidad() {
		return idanexolocalidad;
	}

	public void setIdanexolocalidad(BigDecimal idanexolocalidad) {
		this.idanexolocalidad = idanexolocalidad;
	}

	public String getObsentrega() {
		return obsentrega;
	}

	public void setObsentrega(String obsentrega) {
		this.obsentrega = obsentrega;
	}

	// --

	public List getMonedaList() {
		return monedaList;
	}

	public void setMonedaList(List monedaList) {
		this.monedaList = monedaList;
	}

	public List getListasPreciosList() {
		return listasPreciosList;
	}

	public void setListasPreciosList(List listasPreciosList) {
		this.listasPreciosList = listasPreciosList;
	}

	public List getTiposComprobList() {
		return tiposComprobList;
	}

	public void setTiposComprobList(List tiposComprobList) {
		this.tiposComprobList = tiposComprobList;
	}

	public List getListIva() {
		return listIva;
	}

	public void setListIva(List listIva) {
		this.listIva = listIva;
	}

	public List getListCondicion() {
		return listCondicion;
	}

	public void setListCondicion(List listCondicion) {
		this.listCondicion = listCondicion;
	}

	public List getListCategoriaCred() {
		return listCategoriaCred;
	}

	public void setListCategoriaCred(List listCategoriaCred) {
		this.listCategoriaCred = listCategoriaCred;
	}

	public List getListTiposDoc() {
		return listTiposDoc;
	}

	public void setListTiposDoc(List listTiposDoc) {
		this.listTiposDoc = listTiposDoc;
	}

	public List getListTiposTarjeta() {
		return listTiposTarjeta;
	}

	public void setListTiposTarjeta(List listTiposTarjeta) {
		this.listTiposTarjeta = listTiposTarjeta;
	}

	public List getListTiposDomicilio() {
		return listTiposDomicilio;
	}

	public void setListTiposDomicilio(List listTiposDomicilio) {
		this.listTiposDomicilio = listTiposDomicilio;
	}

}
