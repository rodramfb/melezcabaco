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
import javax.xml.registry.FederatedConnection;

import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanClientesPrecargaActivarFrmBORRAR implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanClientesPrecargaActivarFrmBORRAR.class);

	private String validar = "";

	private String agregarDomicilio = "";

	private boolean primeraCarga = true;

	private String idprecarga = "";

	private BigDecimal idtipodomicilio = new BigDecimal(-1);

	private BigDecimal idvendedorasignado = new BigDecimal(-1);

	private String vendedorasignado = "";

	private BigDecimal idestadoprecarga = new BigDecimal(-1);

	private BigDecimal condomicilio = new BigDecimal(0);

	private String estadoprecarga = "";

	private String tipodomicilio = "";

	private String razon = "";

	private BigDecimal idtipodocumento = new BigDecimal(-1);

	private String tipodocumento = "";

	private String nrodocumento = "";

	private String brutos = "";

	private BigDecimal idtipoiva = new BigDecimal(-1);

	private String tipoiva = "";

	private BigDecimal idvendedor = new BigDecimal(-1);

	private String vendedor = "";

	private BigDecimal idcondicion = new BigDecimal(-1);

	private String condicion = "";

	private String descuento1 = "0";

	private String descuento2 = "0";

	private String descuento3 = "0";

	private String idctaneto = "";

	private BigDecimal idmoneda = new BigDecimal(-1);

	private String moneda = "";

	private BigDecimal idlista = new BigDecimal(-1); // arreglo -1 * 0

	private Timestamp fven = null;

	private String lista = "";

	private BigDecimal idtipoclie = new BigDecimal(-1); // arreglo -1 * 0

	private String tipoclie = "";

	private String observacion = "";

	private String lcredito = "0";

	private BigDecimal idtipocomp = new BigDecimal(-1); // arreglo -1 * 0

	private String tipocomp = "";

	private String autorizado = "";

	private BigDecimal idcredcate = new BigDecimal(-1); // arreglo -1 * 0

	private String credcate = "";

	private String fechadeingreso = "";

	private Timestamp fing = null;

	// private String fechadeingresoStr = Common.initObjectTimeStr();
	private String fechadenacimiento = "";

	private Timestamp fnac = null;

	// private String fechadenacimientoStr = Common.initObjectTimeStr();

	private String sexo = "";

	private BigDecimal referencia = new BigDecimal(-1);
	private String d_referencia = "";

	private BigDecimal idcliente;

	private BigDecimal idempresa = new BigDecimal(-1);

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

	// DATOS DOMICILIOS CLIENTE ..

	private BigDecimal iddomicilio = new BigDecimal(0);

	private String esdefault = "";

	private String calle = "";

	private String nro = "";

	private String piso = "";

	private String depto = "";

	private BigDecimal idlocalidad = new BigDecimal(-1);

	private String localidad = "";

	private String cpa = "";

	private String postal = "";

	private String contacto = "";

	private String cargocontacto = "";

	private String telefonos = "";

	private String celular = "";

	private String fax = "";

	private String web = "";

	private BigDecimal idzona = new BigDecimal(-1);

	private String zona = "";

	private BigDecimal idexpreso = new BigDecimal(-1);

	private String expreso = "";

	private BigDecimal idcobrador = new BigDecimal(-1);

	private String cobrador = "";

	private String[] email = null;

	private String nuevoreactivacion = "";

	private BigDecimal porcentaje = new BigDecimal(0);

	private Hashtable htDomiciliosCliente = new Hashtable();

	private String statusItem = "";

	// DATOS TARJETAS CLIENTE

	private BigDecimal idtarjeta = new BigDecimal(0);

	private BigDecimal idtarjetacredito = new BigDecimal(-1);

	private String tarjetacredito = "";

	private BigDecimal idtipotarjeta = new BigDecimal(-1);

	private String tipotarjeta = "";

	private String nrotarjeta = "";

	private String nrocontrol = "";

	private String fecha_emision = "";

	private String fecha_vencimiento = "";

	private BigDecimal idpreferencia = new BigDecimal(-1);

	private String preferencia = "";

	private BigDecimal idorigen = new BigDecimal(-1);

	private String origen = "";

	private BigDecimal idsuborigen = new BigDecimal(-1);

	private String suborigen = "";

	private String titular = "";

	private String orden = "";

	private String activa = "";

	private Hashtable htTarjetasCliente = new Hashtable();

	//

	public BeanClientesPrecargaActivarFrmBORRAR() {
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
			General clientesClientes = Common.getGeneral();
			if (this.htDomiciliosCliente == null) {
				condomicilio = new BigDecimal(0);
			} else {
				condomicilio = new BigDecimal(1);
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				this.mensaje = clientesClientes.clientesPrecargaCreate(
						new BigDecimal(this.idprecarga), this.razon,
						this.idtipodocumento,
						new BigDecimal(this.nrodocumento), this.brutos,
						this.idtipoiva, this.idcondicion, new Double(
								this.descuento1), new Double(this.descuento2),
						new Double(this.descuento3),
						this.idctaneto.equals("") ? null : new BigDecimal(
								this.idctaneto), this.idmoneda, this.idlista,
						GeneralBean.setNull(this.idtipoclie, 1),
						this.observacion, new Double(this.lcredito),
						GeneralBean.setNull(this.idtipocomp, 1),
						this.autorizado, this.idcredcate,
						this.htDomiciliosCliente, this.htTarjetasCliente,
						this.fing, this.fnac, this.sexo, this.referencia,
						this.idcliente, this.idempresa, this.usuarioalt,
						this.nuevoreactivacion, this.porcentaje,
						this.idvendedorasignado, this.idestadoprecarga,
						this.condomicilio, this.idpreferencia, this.idorigen,
						this.idsuborigen, null, null);

				if (this.mensaje.equalsIgnoreCase("OK")) {
					this.mensaje = "Prospecto generado correctamente.";
					this.accion = "modificacion";
					this.primeraCarga = true;
				}

			} else if (this.accion.equalsIgnoreCase("modificacion")) {
				this.mensaje = clientesClientes.clientesPrecargaUpdate(
						new BigDecimal(this.idprecarga), this.razon,
						this.idtipodocumento,
						new BigDecimal(this.nrodocumento), this.brutos,
						this.idtipoiva, this.idcondicion, new Double(
								this.descuento1), new Double(this.descuento2),
						new Double(this.descuento3),
						this.idctaneto.equals("") ? null : new BigDecimal(
								this.idctaneto), this.idmoneda, this.idlista,
						GeneralBean.setNull(this.idtipoclie, 1),
						this.observacion, new Double(this.lcredito),
						GeneralBean.setNull(this.idtipocomp, 1),
						this.autorizado, this.idcredcate,
						this.htDomiciliosCliente, this.htTarjetasCliente,
						this.fing, this.fnac, this.sexo, this.referencia,
						this.idempresa, this.usuarioact,
						this.nuevoreactivacion, this.porcentaje,
						this.idvendedorasignado, this.idestadoprecarga,
						this.condomicilio, this.idpreferencia, this.idorigen,
						this.idsuborigen, null, null);
				if (this.mensaje.equalsIgnoreCase("OK")) {
					this.mensaje = "Prospecto actualizado correctamente.";
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

				General clientesClientes = Common.getGeneral();
				List listClientesClientes = clientesClientes
						.getClientesPrecargaPK(new BigDecimal(this.idprecarga),
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
					this.idprecarga = uCampos[0];
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
					/*
					 * private Timestamp fechadeingreso= new
					 * Timestamp(Common.initObjectTime()); private String
					 * fechadeingresoStr = Common.initObjectTimeStr(); private
					 * Timestamp fechadenacimiento= new
					 * Timestamp(Common.initObjectTime()); private String
					 * fechadenacimientoStr = Common.initObjectTimeStr();
					 * private String sexo = "";
					 */

					this.fechadeingreso = Common.setObjectToStrOrTime(
							Timestamp.valueOf(uCampos[27]), "JSTsToStr")
							.toString();

					this.fechadenacimiento = Common.setObjectToStrOrTime(
							Timestamp.valueOf(uCampos[28]), "JSTsToStr")
							.toString();

					this.sexo = uCampos[29];
					this.referencia = new BigDecimal(uCampos[30] == null ? "-1"
							: uCampos[30]);
					this.d_referencia = uCampos[31];
					this.nuevoreactivacion = uCampos[32];
					this.porcentaje = new BigDecimal(uCampos[33] == null ? "-1"
							: uCampos[33]);
					this.idvendedorasignado = new BigDecimal(
							uCampos[34] == null ? "-1" : uCampos[34]);
					this.vendedorasignado = uCampos[35];
					this.idestadoprecarga = new BigDecimal(
							uCampos[36] == null ? "-1" : uCampos[36]);
					this.estadoprecarga = uCampos[37];
					this.idpreferencia = new BigDecimal(
							uCampos[38] == null ? "-1" : uCampos[38]);
					this.preferencia = uCampos[39];
					this.idorigen = new BigDecimal(uCampos[40] == null ? "-1"
							: uCampos[40]);
					this.origen = uCampos[41];
					this.idsuborigen = new BigDecimal(
							uCampos[42] == null ? "-1" : uCampos[42]);
					this.suborigen = uCampos[43];
					Hashtable htMails = new Hashtable();
					Iterator iter;
					listClientesClientes = clientesClientes
							.getClientesEmailXCliente(new BigDecimal(
									this.idprecarga), this.idempresa);

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
									this.idprecarga), this.idempresa);

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
								Common.setObjectToStrOrTime(
										java.sql.Date.valueOf(tarjetas[8]),
										"JSDateToStr").toString(),
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
							.getClientesDomiciliosCliente(100, 0,
									new BigDecimal(this.idprecarga),
									this.idempresa);

					/*
					 * iddomicilio - idcliente - idtipodomicilio - tipodomicilio
					 * - esdefault - calle - nro - piso - depto - idlocalidad -
					 * localidad - cpa - postal - contacto - cargocontacto -
					 * telefonos - celular - fax - web - idzona - zona -
					 * idexpreso - expreso - idcobrador - cobrador - idvendedor
					 * - vendedor idempresa - usuarioalt - usuarioact - fechaalt
					 * - fechaact -
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
								domicilios[36] == null ? "-1" : domicilios[36],
								domicilios[37] == null ? "" : domicilios[37],
								htMails.get(domicilios[0]), "M" };

						// log.info("llena domicilios ...." + domicilios[0]);
						this.htDomiciliosCliente.put(domicilios[0].toString(),
								domicilios);

					}
					session.setAttribute("htDomiciliosCliente",
							this.htDomiciliosCliente);

				} else {
					this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
				}

			}

		} catch (Exception e) {
			log.error("getDatosClientesClientes()" + e);
		}
	}

	// --
	private void sugiereIdCliente() {
		try {
			General clientesClientes = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta")) {
				if (this.idprecarga.trim().equals("") || this.primeraCarga)
					if (this.validar.equalsIgnoreCase(""))
						this.idprecarga = clientesClientes
								.getMaximoClientePrecarga(this.idempresa)
								.toString();
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

		session.setAttribute("htDomiciliosCliente", this.htDomiciliosCliente);
		session.setAttribute("htTarjetasCliente", this.htTarjetasCliente);

	}

	// --
	public boolean ejecutarValidacion() {
		try {

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesPrecargaActivarAbm.jsp");
				return true;
			}

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
				General clientesClientes = Common.getGeneral();
				this.mensaje = clientesClientes.getValidacionTarjetaDeCredito(
						this.idempresa, this.nrotarjeta, this.idtarjetacredito);
				if (mensaje
						.equalsIgnoreCase("La longitud de la tarjeta es incorrecta")) {
					return false;
				}

				if (this.accion.equalsIgnoreCase("alta")) {
					this.mensaje = clientesClientes
							.getValidacionTarjetaDeCreditoExistenciaaAlta(
									this.idempresa, this.nrotarjeta,
									this.idtarjetacredito);
					if (mensaje.equalsIgnoreCase("La tarjeta ya existe!")) {
						this.mensaje = "La tarjeta ya existe, por favor verifique!";
						return false;
					}
				}

				if (this.accion.equalsIgnoreCase("Modificacion")) {
					this.mensaje = clientesClientes
							.getValidacionTarjetaDeCreditoExistenciaaModificacion(
									this.idempresa, this.nrotarjeta,
									this.idtarjetacredito, this.idtarjeta);
					if (mensaje.equalsIgnoreCase("La tarjeta ya existe!")) {
						this.mensaje = "La tarjeta ya existe, por favor verifique!";
						return false;
					}
				}

				this.mensaje = clientesClientes
						.validarTarjetaDeCredito(this.nrotarjeta);
				if (mensaje.equalsIgnoreCase("NOOK")) {
					this.mensaje = "El numero de tarjeta es invalido, por favor verifique!";
					return false;
				}

				this.fven = (Timestamp) Common.setObjectToStrOrTime(
						this.fecha_vencimiento, "StrToJSTs");

				if (!this.fven.after(Calendar.getInstance().getTime())) {
					this.mensaje = "Fecha de Vencimiento no puede ser menor a la fecha Actual";
					return false;
				}

				if (this.idtarjetacredito.longValue() <= 0) {
					this.mensaje = "Seleccione tarjeta de credito.";
				} else if (this.idtipotarjeta.longValue() <= 0) {
					this.mensaje = "Seleccione tipo tarjeta";
				} else if (this.nrotarjeta.trim().equals("")) {
					this.mensaje = "Ingrese nro. tarjeta valido.";
				} else if (!Common.esEntero(this.nrocontrol)) {
					this.mensaje = "Ingrese nro. control tarjeta valido.";
				} else if (this.titular.trim().equals("")) {
					this.mensaje = "Ingrese titular de tarjeta.";
				} else if (!Common.esEntero(this.orden)) {
					this.mensaje = "Ingrese orden de tarjeta valido.";
				} else if (!Common.isFormatoFecha(this.fecha_emision)
						|| !Common.isFechaValida(this.fecha_emision)) {
					this.mensaje = "Ingrese fecha de emision tarjeta.";

				} else if (!Common.isFormatoFecha(this.fecha_vencimiento)
						|| !Common.isFechaValida(this.fecha_vencimiento)) {
					this.mensaje = "Ingrese fecha de validez tarjeta.";
				} else if (!isRangoFechas(this.fecha_emision,
						this.fecha_vencimiento)) {
					this.mensaje = "Fecha de emision no puede ser superior a fecha de validez";
				} else if (this.activa.trim().equals("")) {
					this.mensaje = "Seleccionar tarjeta activa.";
				} else {

					if (this.idtarjeta.longValue() <= 0) {
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
				} else if (this.idlocalidad.longValue() < 1) {
					this.mensaje = "Ingrese localidad para domicilio.";
				} else if (!Common.esEntero(this.nro)) {
					this.mensaje = "Ingrese nro. valido para domicilio.";
				} else if (this.contacto.trim().equals("")) {
					this.mensaje = "Ingrese contacto para domicilio.";
				} else if (!isValidMail()) {
					this.mensaje = "Mail invalido.";
				} else {

					if (this.iddomicilio.longValue() <= 0) {
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
							this.statusItem };

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

					if (!Common.esEntero(this.idprecarga)
							|| new BigDecimal(this.idprecarga).longValue() < 0) {
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

					if (this.idvendedorasignado.longValue() < 0) {
						this.mensaje = "Seleccione un Vendedor Asignado.";
						return false;
					}

					if (this.idestadoprecarga.longValue() < 0) {
						this.mensaje = "Seleccione un Estado.";
						return false;
					}

					if (this.idpreferencia.longValue() < 0) {
						this.mensaje = "Seleccione una Preferencia.";
						return false;
					}

					if (this.idorigen.longValue() < 0) {
						this.mensaje = "Seleccione un Origen.";
						return false;
					}

					if (this.idsuborigen.longValue() < 0) {
						this.mensaje = "Seleccione un Suborigen.";
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

					// if (this.htDomiciliosCliente == null
					// || this.htDomiciliosCliente.isEmpty()) {
					// this.mensaje =
					// "Es necesario ingresar al menos un domicilio.";
					// return false;
					// }

					if (this.idcondicion.longValue() == 5)
						if (this.htTarjetasCliente == null
								|| this.htTarjetasCliente.isEmpty()) {
							this.mensaje = "Es necesario ingresar al menos una Tarjeta.";
							return false;
						}

					if (!Common.isFormatoFecha(this.fechadeingreso)
							|| !Common.isFechaValida(this.fechadeingreso)) {
						this.mensaje = "Fecha de ingreso invalida.";
						return false;

					}

					if (!Common.isFormatoFecha(this.fechadenacimiento)
							|| !Common.isFechaValida(this.fechadenacimiento)) {
						this.mensaje = "Fecha de nacimiento invalida.";
						return false;

					}

					this.fing = (Timestamp) Common.setObjectToStrOrTime(
							this.fechadeingreso, "StrToJSTs");
					this.fnac = (Timestamp) Common.setObjectToStrOrTime(
							this.fechadenacimiento, "StrToJSTs");

					if (this.fing.after(Calendar.getInstance().getTime())) {
						this.mensaje = "Fecha de ingreso no puede ser mayor a fecha del dia.";
						return false;
					}

					if (this.fnac.after(Calendar.getInstance().getTime())) {
						this.mensaje = "Fecha de nacimiento no puede ser mayor a fecha del dia.";
						return false;
					}

					if (Common.getAnios(null, new java.util.Date(this.fnac
							.getTime())) < 18) {
						this.mensaje = "El socio debe ser mayor de 18 a�os.";
						return false;
					}

					if (this.nuevoreactivacion.equals("")) {
						this.mensaje = "Seleccione si es nuevo o reactivaci�n.";
						return false;
					}

					if (this.nuevoreactivacion.equalsIgnoreCase("R")) {
						if (this.porcentaje.intValue() < 1) {
							this.mensaje = "Debe seleccionar un porcentaje de reactivacion.";
							return false;
						}
					} else
						this.porcentaje = new BigDecimal(0);

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

	public String getFechadeingreso() {
		return fechadeingreso;
	}

	public void setFechadeingreso(String fechadeingreso) {
		this.fechadeingreso = fechadeingreso;
	}

	public String getFechadenacimiento() {
		return fechadenacimiento;
	}

	public void setFechadenacimiento(String fechadenacimiento) {
		this.fechadenacimiento = fechadenacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public BigDecimal getReferencia() {
		return referencia;
	}

	public void setReferencia(BigDecimal referencia) {
		this.referencia = referencia;
	}

	public String getD_referencia() {
		return d_referencia;
	}

	public void setD_referencia(String d_referencia) {
		this.d_referencia = d_referencia;
	}

	public String getIdprecarga() {
		return idprecarga;
	}

	public void setIdprecarga(String idprecarga) {
		this.idprecarga = idprecarga;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getNuevoreactivacion() {
		return nuevoreactivacion;
	}

	public void setNuevoreactivacion(String nuevoreactivacion) {
		this.nuevoreactivacion = nuevoreactivacion;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public BigDecimal getIdvendedorasignado() {
		return idvendedorasignado;
	}

	public void setIdvendedorasignado(BigDecimal idvendedorasignado) {
		this.idvendedorasignado = idvendedorasignado;
	}

	public String getVendedorasignado() {
		return vendedorasignado;
	}

	public void setVendedorasignado(String vendedorasignado) {
		this.vendedorasignado = vendedorasignado;
	}

	public BigDecimal getIdestadoprecarga() {
		return idestadoprecarga;
	}

	public void setIdestadoprecarga(BigDecimal idestadoprecarga) {
		this.idestadoprecarga = idestadoprecarga;
	}

	public String getEstadoprecarga() {
		return estadoprecarga;
	}

	public void setEstadoprecarga(String estadoprecarga) {
		this.estadoprecarga = estadoprecarga;
	}

	public BigDecimal getIdpreferencia() {
		return idpreferencia;
	}

	public void setIdpreferencia(BigDecimal idpreferencia) {
		this.idpreferencia = idpreferencia;
	}

	public String getPreferencia() {
		return preferencia;
	}

	public void setPreferencia(String preferencia) {
		this.preferencia = preferencia;
	}

	public BigDecimal getIdorigen() {
		return idorigen;
	}

	public void setIdorigen(BigDecimal idorigen) {
		this.idorigen = idorigen;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public BigDecimal getIdsuborigen() {
		return idsuborigen;
	}

	public void setIdsuborigen(BigDecimal idsuborigen) {
		this.idsuborigen = idsuborigen;
	}

	public String getSuborigen() {
		return suborigen;
	}

	public void setSuborigen(String suborigen) {
		this.suborigen = suborigen;
	}

}
