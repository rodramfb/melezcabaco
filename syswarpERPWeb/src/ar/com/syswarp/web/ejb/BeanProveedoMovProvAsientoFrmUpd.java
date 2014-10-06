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

public class BeanProveedoMovProvAsientoFrmUpd implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger
			.getLogger(BeanProveedoMovProvAsientoFrmUpd.class);

	private String aceptar = "";

	private BigDecimal nrointerno = BigDecimal.valueOf(-1);

	private BigDecimal idproveedor = BigDecimal.valueOf(0);

	private String dproveedor = "";

	// HARCODE - POR AHORA SOLO SE MODIFICAN COMPROBANTES SIN STOCK
	private String stock_fact = "N";

	private String stock_fact_actual = "";

	private String fechamov = "";

	private BigDecimal sucursal = BigDecimal.valueOf(0);

	private BigDecimal comprob = BigDecimal.valueOf(0);

	private BigDecimal tipomov = BigDecimal.valueOf(0);

	private String tipomovs = "";

	private Double importe = Double.valueOf("0");

	private Double saldo = Double.valueOf("0");

	private BigDecimal idcondicionpago = BigDecimal.valueOf(-1);

	private String dcondicionpago = "";

	private int cantidaddias = 0;

	private Timestamp fecha_subd = new Timestamp(Common.initObjectTime());

	private String fecha_subdStr = Common.initObjectTimeStr();

	private BigDecimal retoque = BigDecimal.valueOf(0);

	private String fechavto = "";

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private String calcular = "";
	
	private String observacionesContables = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	private String accion = "";

	private Hashtable htCP = null;

	/**/

	private String provPorcNormalIva = "";

	private String provPorcEspecialIvaPercep = "";

	private double subTotalGravado = 0;

	private String netoGravado = "0.00";

	private String porcentajeBonificacion = "0.00";

	private String bonificacion = "0.00";

	private String iva = "0.00";

	private String percepcionIva = "0.00";

	private String netoExento = "0.00";

	private String readonlyIva = "readonly";

	private String readonlyRetIva = "readonly";

	private String readonlyNetoGravado = "readonly";

	/*-----*/

	/* MANEJO DE CUENTAS */
	private Hashtable htNetoGravado = new Hashtable();

	private Hashtable htNetoExento = new Hashtable();

	private Hashtable htIvaTotal = new Hashtable();

	private boolean modficaCNG = false;

	// Cta's. proveedor
	private String ctapasivo = "";

	private String ctaactivo1 = "";

	private String ctaactivo2 = "";

	private String ctaactivo3 = "";

	private String ctaactivo4 = "";

	private String ctaiva = "";

	private String ctaretiva = "";

	/* ----- */

	private Hashtable htAsiento = new Hashtable();

	private boolean iniciarDatosAsiento = true;

	private int ejercicioActivo = 0;

	public BeanProveedoMovProvAsientoFrmUpd() {
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
		RequestDispatcher dispatcher;
		String resultado = "";
		BigDecimal importemov = new BigDecimal(importe.toString());
		try {

			Hashtable htArticulos = (Hashtable) session
					.getAttribute("htArticulosConfirmados");
			Proveedores proveedores = Common.getProveedores();
			if (this.accion.equalsIgnoreCase("modificacion")) {
				// TODO: flagMovAplicadoUpd, el mismo se remueve en
				// BeanProveedoMovProvFrm.
				if (session.getAttribute("flagMovAplicadoUpd") == null) {                    
					this.mensaje = proveedores.modificarComprobanteProveedor(
							this.nrointerno, this.idproveedor,
							(Timestamp) Common.setObjectToStrOrTime(
									this.fechamov, "StrToJSTs"), this.sucursal,
							this.comprob, this.tipomov, this.tipomovs,
							importemov, importemov, this.idcondicionpago,
							this.fecha_subd, this.retoque,
							(java.sql.Date) Common.setObjectToStrOrTime(
									this.fechavto, "StrToJSDate"),
							this.htAsiento, htArticulos, this.usuarioact,
							this.idempresa, this.observacionesContables );

				} else {
					resultado = session.getAttribute("flagMovAplicadoUpd")
							.toString();
					log.info("VALOR DE MOV. DESDE SESION (F5): " + resultado);
				}

			}

			if (this.mensaje.equalsIgnoreCase("OK")) {

				dispatcher = request
						.getRequestDispatcher("proveedoMovProvImpresionUpd.jsp?nrointerno="
								+ this.nrointerno);
				dispatcher.forward(request, response);

				session.setAttribute("flagMovAplicadoUpd", this.mensaje);

			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}

	}

	public void getDatosProveedoMovProv() {
		try {
			Proveedores proveedoMovProv = Common.getProveedores();
			List listProveedoMovProv = proveedoMovProv.getProveedoMovProvPK(
					this.nrointerno, this.idempresa);
			Iterator iterProveedoMovProv = listProveedoMovProv.iterator();
			if (iterProveedoMovProv.hasNext()) {
				String[] uCampos = (String[]) iterProveedoMovProv.next();
				// TODO: Constructores para cada tipo de datos
				this.nrointerno = BigDecimal
						.valueOf(Long.parseLong(uCampos[0]));
				this.idproveedor = BigDecimal.valueOf(Long
						.parseLong(uCampos[1]));
				// 20071020-this.fechamov = Common.setObjectToStrOrTime(
				// 20071020- Timestamp.valueOf(uCampos[2]),
				// "JSTsToStr").toString();

				// 20071020- this.sucursal =
				// BigDecimal.valueOf(Long.parseLong(uCampos[3]));
				// 20071020- this.comprob =
				// BigDecimal.valueOf(Long.parseLong(uCampos[4]));
				// 20071020- this.tipomov =
				// BigDecimal.valueOf(Long.parseLong(uCampos[5]));
				// 20071020- this.tipomovs = uCampos[6];
				// 20071020- this.importe = Double.valueOf(uCampos[7]);
				// 20071020- this.saldo = Double.valueOf(uCampos[8]);
				this.idcondicionpago = BigDecimal.valueOf(Long
						.parseLong(uCampos[9]));
				this.fecha_subd = Timestamp.valueOf(uCampos[10]);
				this.fecha_subdStr = Common.setObjectToStrOrTime(fecha_subd,
						"JSTsToStr").toString();

				this.retoque = BigDecimal.valueOf(Long.parseLong(uCampos[11]));
				//this.observacionesContables = uCampos[17];
				//20081103- this.fechavto = Common.setObjectToStrOrTime(
				// java.sql.Date.valueOf(uCampos[12]), "JSDateToStr")
				// .toString();

				/**/

				List listCondPago = proveedoMovProv.getProveedoCondicioPK(
						this.idcondicionpago, this.idempresa);
				if (listCondPago.iterator().hasNext()) {
					String[] datosCondPago = (String[]) listCondPago.iterator()
							.next();
					this.dcondicionpago = datosCondPago[1];
					this.cantidaddias = Integer.parseInt(datosCondPago[2]);
				}

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProveedoMovProv()" + e);
		}
	}

	public void inicializarDatosProv() {
		try {

			Proveedores proveedor = Common.getProveedores();

			if (this.idproveedor != null && this.idproveedor.longValue() > 0) {

				List listProveedor = proveedor.getProveedoProveedPK(
						this.idproveedor, this.idempresa);
				if (listProveedor.iterator().hasNext()) {

					String[] datosProveedor = (String[]) listProveedor
							.iterator().next();

					this.ctapasivo = datosProveedor[12];
					this.ctaactivo1 = datosProveedor[13];
					this.ctaactivo2 = datosProveedor[14];
					this.ctaactivo3 = datosProveedor[15];
					this.ctaactivo4 = datosProveedor[16];
					this.ctaiva = datosProveedor[17];
					this.ctaretiva = datosProveedor[18];

					if (this.ctaiva != null && !this.ctaiva.equals("0"))
						this.readonlyIva = "";

					if (this.ctaretiva != null && !this.ctaretiva.equals("0"))
						this.readonlyRetIva = "";

					this.dproveedor = datosProveedor[1];
					// Si accion es alta seteo la condicion de pago del
					// proveedor,
					// sino prevalece la del movimiento.
					if (this.accion.equalsIgnoreCase("alta")) {
						this.idcondicionpago = BigDecimal.valueOf(Long
								.parseLong(datosProveedor[34]));
					}
					// this.stock_fact = datosProveedor[33];
					// SOLO PARA MOSTRAR LA CONDICION ACTUAL DEL PROVEEDOR
					this.stock_fact_actual = datosProveedor[33];

					/*
					 * List listCondPago = proveedor.getProveedoCondicioPK(
					 * this.idcondicionpago, this.idempresa); if
					 * (listCondPago.iterator().hasNext()) { String[]
					 * datosCondPago = (String[]) listCondPago
					 * .iterator().next(); this.dcondicionpago =
					 * datosCondPago[1]; this.cantidaddias =
					 * Integer.parseInt(datosCondPago[2]); }
					 * 
					 */
				}

				// Asegura que si el proveedor no factura con stock, no exista
				// ningun articulo en session
				if (this.stock_fact.equalsIgnoreCase("N")) {
					session.removeAttribute("htArticulosConfirmados");
					session.removeAttribute("subTotalGravado");
					this.readonlyNetoGravado = "";
				}

				// Si el proveedor permite elegir actualizar o no el stock y no
				// se cargo producto alguno, permite que se modifique neto
				// gravado.
				if (this.stock_fact.equalsIgnoreCase("?")
						&& this.session.getAttribute("htArticulosConfirmados") == null) {
					this.readonlyNetoGravado = "";
				}

				// Si se cargaron articulos, levanta el SUBTOTAL GRAVADO
				if (session.getAttribute("subTotalGravado") != null) {
					subTotalGravado = Double.parseDouble(session.getAttribute(
							"subTotalGravado").toString());
				}
				// Si cambian los porcentajes de IVA, setea los de session por
				// los cargados en la pagina.
				asignarPorcentajes(this.provPorcNormalIva, "provPorcNormalIva");
				asignarPorcentajes(this.provPorcEspecialIvaPercep,
						"provPorcEspecialIvaPercep");

				this.provPorcNormalIva = (String) session
						.getAttribute("provPorcNormalIva");
				this.provPorcEspecialIvaPercep = (String) session
						.getAttribute("provPorcEspecialIvaPercep");

			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("inicializarDatosProv(); " + e);
		}
	}

	public void asignarPorcentajes(Object obj, String nombre) {
		// TODO: Mejorar el funcionamiento del metodo asignarPorcentajes.
		String porcentaje = obj.toString();
		try {
			if (!porcentaje.equals("")) {
				if (Common.esNumerico(porcentaje)) {
					if (Double.parseDouble(porcentaje) <= 100) {
						session.setAttribute(nombre, Common
								.getNumeroFormateado(Double
										.parseDouble(porcentaje), 3, 2));
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("asignarPorcentajes(Object obj, String nombre)" + e);
		}

	}

	public boolean calcularImportes() {
		String mensajeCI = "";
		boolean fOk = false;
		try {

			if (!Common.esNumerico(this.porcentajeBonificacion)) {
				mensajeCI = "% Bonificación Incorrecto.";
			} else if (!Common.esNumerico(this.bonificacion)) {
				mensajeCI = "Bonificación Incorrecta.";
			} else if (!Common.esNumerico(this.netoGravado)) {
				mensajeCI = "Neto Gravado Incorrecto.";
			} else if (!Common.esNumerico(this.provPorcNormalIva)) {
				mensajeCI = "% Iva Incorrecto.";
			} else if (!Common.esNumerico(this.provPorcEspecialIvaPercep)) {
				mensajeCI = "% Percepción Iva Incorrecto.";
			} else if (!Common.esNumerico(this.iva)) {
				mensajeCI = "Iva Incorrecto.";
			} else if (!Common.esNumerico(this.percepcionIva)) {
				mensajeCI = "Percepción Iva Incorrecto.";
			} else if (!Common.esNumerico(this.netoExento)) {
				mensajeCI = "Neto Exento Incorrecto.";
			} else {


				if (!Common.esPorcentaje(this.porcentajeBonificacion)) {
					mensajeCI = " % (2) Bonificación Incorrecto.";
				} else if (!Common.esPorcentaje(this.provPorcNormalIva)) {
					mensajeCI = " % (2) Iva Incorrecto.";
				} else if (!Common.esPorcentaje(this.provPorcEspecialIvaPercep)) {
					mensajeCI = " % (2) Percepción Incorrecto.";
				} else {
					// log.info("calcularImportes(): %'s OK");
					// Neto Gravado se calcula cuando se cargaron articulos
					// para actualizar stock
					// si no se toma el valor ingresado por pantalla

					if (this.session.getAttribute("htArticulosConfirmados") != null) {

						this.bonificacion = Common
								.getNumeroFormateado(
										(this.subTotalGravado * (Double
												.parseDouble(this.porcentajeBonificacion) / 100)),
										10, 2);
						// falta formatear
						this.netoGravado = (this.subTotalGravado - Double
								.parseDouble(this.bonificacion))
								+ "";
					}

					// TODO los calculos de IVA solo deben realizarse si el
					// proveedor tiene asignadas cuentas para cada tipo caso
					// contrario no debe aplicarse el porcentaje al total

					if (this.readonlyIva.equals(""))
						// Calcula el valor del IVA normal
						this.iva = ""
								+ Common
										.getNumeroFormateado(
												Double
														.parseDouble(this.netoGravado)
														* (Double
																.parseDouble(this.provPorcNormalIva) / 100),
												10, 2);
					else
						this.iva = "0.00";

					if (this.readonlyRetIva.equals(""))
						// Calcula el valor del IVA ESPECIAL por PERCEPCION
						this.percepcionIva = ""
								+ Common
										.getNumeroFormateado(
												Double
														.parseDouble(this.netoGravado)
														* (Double
																.parseDouble(this.provPorcEspecialIvaPercep) / 100),
												10, 2);
					else
						this.percepcionIva = "0.00";

					// CALCULA EL TOTAL DEL COMPROBANTE
					this.importe = Double.valueOf(Common.getNumeroFormateado(
							Double.parseDouble(this.netoGravado)
									+ Double.parseDouble(this.netoExento)
									+ Double.parseDouble(this.iva)
									+ Double.parseDouble(this.percepcionIva),
							10, 2));

					fOk = true;

				}

			}
			if (!this.aceptar.equals(""))
				this.mensaje = mensajeCI;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("calcularImportes():" + e);
		}
		return fOk;
	}

	public void inicializarDatosCuentas() {
		try {

			// Contable contable = Common.getContable();
			// INICIA INICIALIZAR CUENTAS
			// 1 - Cuentas Neto Gravado
			// Generando datos para asiento
			// TODO: es necesario identificar si las cuentas se
			// modifican durante la session siempre y cuando lo permita
			// la regla del negocio

			// Seteo de valores de Gravado y Exento para el asiento.
			if (this.iniciarDatosAsiento) {
				this.getValoresAsiento();
			}

			if (this.session.getAttribute("htArticulosConfirmados") != null) {
				// TODO: MODIFICAR PORCENTAJE DE BONIFICACION, MODIFICAR VALORES
				// TODO: TOTALES CUENTA A LA NUEVA BONIFICACION
				if (session.getAttribute("htNetoGravadoUpd") == null
						|| ((Hashtable) session
								.getAttribute("htNetoGravadoUpd")).isEmpty()) {

					Enumeration enu = ((Hashtable) this.session
							.getAttribute("htArticulosConfirmados")).keys();
					// RECORRE LA LISTA DE ARTICULOS
					while (enu.hasMoreElements()) {
						String key = (String) enu.nextElement();
						String[] art = (String[]) ((Hashtable) this.session
								.getAttribute("htArticulosConfirmados"))
								.get(key);
						// Carga vector con los datos de la cuenta
						// asociada
						// al articulo.

						String[] datosCuenta = this.getDatosCuentas(art[12]);

						String precioArticulo = ""
								+ (Double.parseDouble(art[11]) - (Double
										.parseDouble(art[11]) * (Double
										.parseDouble(this.porcentajeBonificacion) / 100)));
						precioArticulo = Common.getNumeroFormateado(Double
								.parseDouble(precioArticulo), 10, 2);

						String[] datosAsientoNG = null;

						if (datosCuenta != null) {
							datosAsientoNG = new String[] { datosCuenta[0],
									datosCuenta[1], precioArticulo, "G",
									"noasignable" };
						} else {
							datosAsientoNG = new String[] { "0", "",
									precioArticulo, "G", "asignable" };
							this.modficaCNG = true;
						}
						this.htNetoGravado.put(key, datosAsientoNG);
					}

				} else {
					this.htNetoGravado = (Hashtable) session
							.getAttribute("htNetoGravadoUpd");
				}

			} else {
				this.modficaCNG = true;
				if (session.getAttribute("htNetoGravadoUpd") == null
						|| ((Hashtable) session
								.getAttribute("htNetoGravadoUpd")).isEmpty()) {

					String[] datosCuenta = this
							.getDatosCuentas(this.ctaactivo1);
					if (datosCuenta != null) {
						String[] datosAsientoNG = new String[] {
								datosCuenta[0], datosCuenta[1],
								this.netoGravado, "G", "noasignable" };
						this.htNetoGravado.put("A" + Common.initObjectTime()
								+ "", datosAsientoNG);

					}
				} else {
					this.htNetoGravado = (Hashtable) session
							.getAttribute("htNetoGravadoUpd");
				}

			}

			session.setAttribute("htNetoGravadoUpd", this.htNetoGravado);

			// 2 - IVA y TOTAL
			// No es posible Modificarlos ...

			String[] datosCuenta = this.getDatosCuentas(this.ctapasivo);
			if (datosCuenta != null) {
				String[] datosAsientoIVATotal = new String[] {
						datosCuenta[0],
						datosCuenta[1],
						Common.getNumeroFormateado(this.importe.doubleValue(),
								10, 2), "T", "noasignable" };

				htIvaTotal.put("Z" + Common.initObjectTime() + "1",
						datosAsientoIVATotal);

				datosCuenta = this.getDatosCuentas(this.ctaiva);
				if (datosCuenta != null) {
					datosAsientoIVATotal = new String[] {
							datosCuenta[0],
							datosCuenta[1],
							Common.getNumeroFormateado(Double
									.parseDouble(this.iva), 10, 2), "I",
							"noasignable" };

					htIvaTotal.put("X" + Common.initObjectTime() + "2",
							datosAsientoIVATotal);

					datosCuenta = this.getDatosCuentas(this.ctaretiva);
					if (datosCuenta != null) {
						datosAsientoIVATotal = new String[] {
								datosCuenta[0],
								datosCuenta[1],
								Common
										.getNumeroFormateado(
												Double
														.parseDouble(this.percepcionIva),
												10, 2), "R", "noasignable" };

						htIvaTotal.put("X" + Common.initObjectTime() + "3",
								datosAsientoIVATotal);
					}
				}
				
				// 3 - NETO EXENTO
				// Verificar que se ingreso valor para NE
				if (this.netoExento != null
						&& Double.parseDouble(this.netoExento) > 0) {

					// Si esta asociado a la session
					if (session.getAttribute("htNetoExentoUpd") == null) {

						/*
						 * 20071120 - EJV - Inicialización Sin sentido??????
						 * String[] datosAsientoNetoExento = new String[] {
						 * datosCuenta[0], datosCuenta[1],
						 * this.importe.toString(), "E", "noasignable" };
						 */

						String[] datosAsientoNetoExento = null;

						datosCuenta = this.getDatosCuentas(this.ctaactivo1);

						if (datosCuenta != null) {
							datosAsientoNetoExento = new String[] {
									datosCuenta[0], datosCuenta[1],
									this.netoExento, "E", "noasignable" };

							this.htNetoExento.put("W" + Common.initObjectTime()
									+ "4", datosAsientoNetoExento);
							session.setAttribute("htNetoExentoUpd",
									this.htNetoExento);
						}
					} else {
						this.htNetoExento = (Hashtable) session
								.getAttribute("htNetoExentoUpd");
					}

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			log.error("inicializarDatosCuentas(): " + e);
		}

		// FINALIZA INICIALIZAR CUENTAS

	}

	public boolean validarTotales(double total, Hashtable ht) {
		boolean fOk = true;
		try {
			Enumeration e = ht.keys();
			double acumula = 0;
			while (e.hasMoreElements()) {
				String[] datos = (String[]) ht.get(e.nextElement());
				acumula += Double.parseDouble(datos[2]);
				if (datos[0] == null || datos[0].equals("0")
						|| datos[0].trim().equals(""))
					fOk = false;

				if (!fOk) {
					this.mensaje = "Cuenta inválida.";
					break;
				}
			}

			if (fOk && total != acumula) {
				this.mensaje = "Total no coincide con parciales.";
				fOk = false;
			}

		} catch (Exception e) {
			this.mensaje = "Error durante la validación de totales de asiento asociado al movimiento.";
			fOk = false;
			log.error("validarTotales(): " + e);
		}
		return fOk;
	}

	public void generarDatosTransaccion() {
		Enumeration en;
		log.info("INICIA generarDatosTransaccion()--> ");
		try {
			if (!this.htNetoExento.isEmpty()) {
				en = this.htNetoExento.keys();
				while (en.hasMoreElements()) {
					String key = (String) en.nextElement();
					this.htAsiento.put(key, this.htNetoExento.get(key));
				}
			}

			if (!this.htNetoGravado.isEmpty()) {
				en = this.htNetoGravado.keys();
				while (en.hasMoreElements()) {
					String key = (String) en.nextElement();
					this.htAsiento.put(key, this.htNetoGravado.get(key));
				}

			}

			if (!this.htIvaTotal.isEmpty()) {
				en = this.htIvaTotal.keys();
				while (en.hasMoreElements()) {
					String key = (String) en.nextElement();
					this.htAsiento.put(key, this.htIvaTotal.get(key));
				}
			}
			en = this.htAsiento.keys();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String[] str = (String[]) this.htAsiento.get(key);
				log.info("  KEY :  " + key);
				for (int i = 0; i < str.length; i++) {
					log.info("POSICION " + i + " : " + str[i]);
				}
			}

		} catch (Exception e) {
			log.error("generarDatosTransaccion():" + e);
		}
		log.info("FINALIZA generarDatosTransaccion()<-- ");
	}

	/**/

	public void getValoresAsiento() {

		List listaImportes = new ArrayList();
		Iterator iterImportes;
		try {

			Proveedores proveedor = Common.getProveedores();
			listaImportes = proveedor.getProveedoContProvImportes(
					this.nrointerno, this.idempresa);

			iterImportes = listaImportes.iterator();

			while (iterImportes.hasNext()) {

				String[] datosImporte = (String[]) iterImportes.next();

				List listaArt = proveedor.getCuentasInfiPlanPK(new BigDecimal(
						datosImporte[0]), this.idempresa, this.ejercicioActivo);
				Iterator iterArt = listaArt.iterator();

				while (iterArt.hasNext()) {

					String[] datos = (String[]) iterArt.next();
					String[] datosCuenta = null;

					switch (datosImporte[3].trim().charAt(0)) {
					case 'E':

						datosCuenta = new String[] { datos[0], datos[1],
								datosImporte[2], "E", "noasignable" };
						this.htNetoExento.put("W" + datosImporte[0] + "",
								datosCuenta);
						// 
						// this.netoExento = datosImporte[2];
						break;
					case 'G':
						datosCuenta = new String[] { datos[0], datos[1],
								datosImporte[2], "G", "noasignable" };
						this.htNetoGravado.put("A" + datosImporte[0] + "",
								datosCuenta);
						//
						// this.netoGravado = datosImporte[2];
						break;
					case 'I':
						this.iva = Common.getNumeroFormateado(Double
								.parseDouble(datosImporte[2]), 10, 2);
						break;
					case 'T':
						this.importe = new Double(datosImporte[2]);
						break;
					case 'R':
						/**
						 * @COMENTARIO: 20081030: TODO Controlar, por algun
						 *              motivo hasta / el dia de la fecha no
						 *              asignaba valor
						 */
						this.percepcionIva = Common.getNumeroFormateado(Double
								.parseDouble(datosImporte[2]), 10, 2);
						break;

					default:
						break;
					}
				}
			}

			session.setAttribute("htNetoExentoUpd", this.htNetoExento);
			session.setAttribute("htNetoGravadoUpd", this.htNetoGravado);

		} catch (Exception e) {
			log.error("getValoresAsiento():" + e);
		}

	}

	/**/

	public boolean ejecutarValidacion() {

		try {

			Calendar cal = new GregorianCalendar();

			Timestamp fmov = (Timestamp) Common.setObjectToStrOrTime(
					this.fechamov, "StrToJSTs");
			java.sql.Date fvto = (java.sql.Date) Common.setObjectToStrOrTime(
					this.fechavto, "StrToJSDate");

			if (!this.volver.equalsIgnoreCase("")) {
				
				response.sendRedirect("proveedoMovProvBuscar.jsp?idproveedor="
						+ this.idproveedor + "&dproveedor=" + this.dproveedor);
				this.destruirHTSession();
				return true;
			}
			
			this.htCP = Common
					.getHashEntidad("getProveedoCondicioAll", new Class[] {
							long.class, long.class, BigDecimal.class },
							new Object[] { new Long(250), new Long(0),
									this.idempresa });

			if (!this.aceptar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					// Recupera los datos del proveedor
					this.inicializarDatosProv();
					// Recupera datos de las cuentas asociadas al proveedor -
					// articulos

					this.inicializarDatosCuentas();

					// 1. nulidad de campos
					if (idproveedor == null || idproveedor.longValue() <= 0) {
						this.mensaje = "Es necesario seleccionar un proveedor. ";
						return false;
					}

					if (fechamov == null) {
						this.mensaje = "No se puede dejar vacio el campo fechamov. ";
						return false;
					}
					if (sucursal == null) {
						this.mensaje = "No se puede dejar vacio el campo sucursal. ";
						return false;
					}
					if (comprob == null) {
						this.mensaje = "No se puede dejar vacio el campo comprobante. ";
						return false;
					}
					if (tipomov == null) {
						this.mensaje = "No se puede dejar vacio el campo tipo comprobante. ";
						return false;
					}
					if (tipomovs == null) {
						this.mensaje = "No se puede dejar vacio el campo letra comprobante. ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (tipomovs.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo tipomovs  ";
						return false;
					}

					if (this.stock_fact.equalsIgnoreCase("S")) {
						if (this.session.getAttribute("htArticulosConfirmados") == null) {
							this.mensaje = "Debe ingresar algún artículo en stock. ";
							return false;
						}
					}

					if (fmov.compareTo(fvto) > 0) {
						this.mensaje = "Fecha de movimiento no puede ser mayor a fecha vencimiento.";
						return false;
					}

					cal.setTime(fmov);
					cal.add(Calendar.DATE, cantidaddias);

					if (fvto.after(cal.getTime())) {
						this.mensaje = "Fecha de vencimiento no puede ser mayor a: "
								+ Common.setObjectToStrOrTime(cal.getTime(),
										"JUDateToStr");
						return false;
					}

					if (!this.calcularImportes()) {
						return false;
					}

					if (!validarTotales(Double.parseDouble(this.netoGravado),
							this.htNetoGravado))
						return false;

					if (Double.parseDouble(this.netoExento) > 0) {
						if (!validarTotales(
								Double.parseDouble(this.netoExento),
								this.htNetoExento))
							return false;
					}

				}

				this.generarDatosTransaccion();

				this.ejecutarSentenciaDML();

			} else {

				if (!this.accion.equalsIgnoreCase("alta")) {

					getDatosProveedoMovProv();

				}

				this.inicializarDatosProv();

				// 20081030 - EJV
				// ver que pasa con comprobantes con stock
				// this.calcularImportes();

				this.inicializarDatosCuentas();

			}

			// 20081030 - EJV
			// ver que pasa con comprobantes con stock
			this.calcularImportes();

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}

		// IMPORTE OK
		return true;
	}

	public String[] getDatosCuentas(String cuenta) throws RemoteException {

		String[] datos = null;

		try {

			if (Common.esEntero(cuenta)) {

				Contable contable = Common.getContable();
				int ejercicioActivo = Integer.parseInt(this.session
						.getAttribute("ejercicioActivo").toString());

				List listaCuenta = contable.getCuentasPK(ejercicioActivo,
						new Long(cuenta), this.idempresa);
				/*
				 * List listaCuenta = contable.getCuentasPK(ejercicioActivo, new
				 * Long( cuenta));
				 */
				if (listaCuenta != null) {
					Iterator iterCuenta = listaCuenta.iterator();
					if (iterCuenta.hasNext()) {
						datos = (String[]) iterCuenta.next();
					}
				}
			} else
				log.warn("getDatosCuentas(): Cuenta Invalida  [" + cuenta
						+ "]:");

		} catch (Exception e) {
			log.error("getDatosCuentas() [" + cuenta + "]: " + e);
		}
		return datos;
	}

	public String getAceptar() {
		return aceptar;
	}

	public void setAceptar(String aceptar) {
		this.aceptar = aceptar;
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
	public BigDecimal getNrointerno() {
		return nrointerno;
	}

	public void setNrointerno(BigDecimal nrointerno) {
		this.nrointerno = nrointerno;
	}

	public BigDecimal getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(BigDecimal idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getFechamov() {
		return fechamov;
	}

	public void setFechamov(String fechamov) {
		this.fechamov = fechamov;
	}

	public BigDecimal getSucursal() {
		return sucursal;
	}

	public void setSucursal(BigDecimal sucursal) {
		this.sucursal = sucursal;
	}

	public BigDecimal getComprob() {
		return comprob;
	}

	public void setComprob(BigDecimal comprob) {
		this.comprob = comprob;
	}

	public BigDecimal getTipomov() {
		return tipomov;
	}

	public void setTipomov(BigDecimal tipomov) {
		this.tipomov = tipomov;
	}

	public String getTipomovs() {
		return tipomovs;
	}

	public void setTipomovs(String tipomovs) {
		this.tipomovs = tipomovs;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getIdcondicionpago() {
		return idcondicionpago;
	}

	public void setIdcondicionpago(BigDecimal idcondicionpago) {
		this.idcondicionpago = idcondicionpago;
	}

	public Timestamp getFecha_subd() {
		return fecha_subd;
	}

	public void setFecha_subd(Timestamp fecha_subd) {
		this.fecha_subd = fecha_subd;
	}

	public BigDecimal getRetoque() {
		return retoque;
	}

	public void setRetoque(BigDecimal retoque) {
		this.retoque = retoque;
	}

	public String getFechavto() {
		return fechavto;
	}

	public void setFechavto(String fechavto) {
		this.fechavto = fechavto;
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

	public String getFecha_subdStr() {
		return fecha_subdStr;
	}

	public void setFecha_subdStr(String fecha_subdStr) {
		this.fecha_subdStr = fecha_subdStr;
		this.fecha_subd = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fecha_subdStr, "StrToJSTs");
	}

	public Hashtable getHtCP() {
		return htCP;
	}

	public void setHtCP(Hashtable htCP) {
		this.htCP = htCP;
	}

	public String getDproveedor() {
		return dproveedor;
	}

	public void setDproveedor(String dproveedor) {
		this.dproveedor = dproveedor;
	}

	public String getDcondicionpago() {
		return dcondicionpago;
	}

	public void setDcondicionpago(String dcondicionpago) {
		this.dcondicionpago = dcondicionpago;
	}

	public String getStock_fact() {
		return stock_fact;
	}

	public void setStock_fact(String stock_fact) {
		this.stock_fact = stock_fact;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String getProvPorcEspecialIvaPercep() {
		return provPorcEspecialIvaPercep;
	}

	public void setProvPorcEspecialIvaPercep(String provPorcEspecialIvaPercep) {
		this.provPorcEspecialIvaPercep = provPorcEspecialIvaPercep;
	}

	public String getProvPorcNormalIva() {
		return provPorcNormalIva;
	}

	public void setProvPorcNormalIva(String provPorcNormalIva) {
		this.provPorcNormalIva = provPorcNormalIva;
	}

	public double getSubTotalGravado() {
		return subTotalGravado;
	}

	public void setSubTotalGravado(double subTotalGravado) {
		this.subTotalGravado = subTotalGravado;
	}

	public String getReadonlyIva() {
		return readonlyIva;
	}

	public void setReadonlyIva(String readonlyIva) {
		this.readonlyIva = readonlyIva;
	}

	public String getReadonlyRetIva() {
		return readonlyRetIva;
	}

	public void setReadonlyRetIva(String readonlyRetIva) {
		this.readonlyRetIva = readonlyRetIva;
	}

	public String getNetoGravado() {
		return netoGravado;
	}

	public void setNetoGravado(String netoGravado) {
		this.netoGravado = netoGravado;
	}

	public String getReadonlyNetoGravado() {
		return readonlyNetoGravado;
	}

	public void setReadonlyNetoGravado(String readonlyNetoGravado) {
		this.readonlyNetoGravado = readonlyNetoGravado;
	}

	public String getCalcular() {
		return calcular;
	}

	public void setCalcular(String calcular) {
		this.calcular = calcular;
	}

	public String getBonificacion() {
		return bonificacion;
	}

	public void setBonificacion(String bonificacion) {
		this.bonificacion = bonificacion;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getPercepcionIva() {
		return percepcionIva;
	}

	public void setPercepcionIva(String percepcionIva) {
		this.percepcionIva = percepcionIva;
	}

	public String getNetoExento() {
		return netoExento;
	}

	public void setNetoExento(String netoExento) {
		this.netoExento = netoExento;
	}

	public String getPorcentajeBonificacion() {
		return porcentajeBonificacion;
	}

	public void setPorcentajeBonificacion(String porcentajeBonificacion) {
		this.porcentajeBonificacion = porcentajeBonificacion;
	}

	public Hashtable getHtNetoGravado() {
		return htNetoGravado;
	}

	public void setHtNetoGravado(Hashtable htNetoGravado) {
		this.htNetoGravado = htNetoGravado;
	}

	public Hashtable getHtIvaTotal() {
		return htIvaTotal;
	}

	public void setHtIvaTotal(Hashtable htIvaTotal) {
		this.htIvaTotal = htIvaTotal;
	}

	public String getCtaactivo2() {
		return ctaactivo2;
	}

	public void setCtaactivo2(String ctaactivo0) {
		this.ctaactivo2 = ctaactivo0;
	}

	public String getCtaactivo1() {
		return ctaactivo1;
	}

	public void setCtaactivo1(String ctaactivo1) {
		this.ctaactivo1 = ctaactivo1;
	}

	public String getCtaactivo3() {
		return ctaactivo3;
	}

	public void setCtaactivo3(String ctaactivo3) {
		this.ctaactivo3 = ctaactivo3;
	}

	public String getCtaactivo4() {
		return ctaactivo4;
	}

	public void setCtaactivo4(String ctaactivo4) {
		this.ctaactivo4 = ctaactivo4;
	}

	public String getCtaiva() {
		return ctaiva;
	}

	public void setCtaiva(String ctaiva) {
		this.ctaiva = ctaiva;
	}

	public String getCtapasivo() {
		return ctapasivo;
	}

	public void setCtapasivo(String ctapasivo) {
		this.ctapasivo = ctapasivo;
	}

	public String getCtaretiva() {
		return ctaretiva;
	}

	public void setCtaretiva(String ctaretiva) {
		this.ctaretiva = ctaretiva;
	}

	public Hashtable getHtNetoExento() {
		return htNetoExento;
	}

	public void setHtNetoExento(Hashtable htNetoExento) {
		this.htNetoExento = htNetoExento;
	}

	public boolean isModficaCNG() {
		return modficaCNG;
	}

	public void setModficaCNG(boolean modficaCNG) {
		this.modficaCNG = modficaCNG;
	}

	// TODO: VER QUE HACER !!!!
	public void destruirHTSession() {
		session.removeAttribute("htNetoGravadoUpd");
		session.removeAttribute("htNetoExentoUpd");
		session.removeAttribute("htIvaTotal");
		session.removeAttribute("htArticulosConfirmados");
		session.removeAttribute("subTotalGravado");
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getStock_fact_actual() {
		return stock_fact_actual;
	}

	public void setStock_fact_actual(String stock_fact_actual) {
		this.stock_fact_actual = stock_fact_actual;
	}

	public boolean isIniciarDatosAsiento() {
		return iniciarDatosAsiento;
	}

	public void setIniciarDatosAsiento(boolean iniciarDatosAsiento) {
		this.iniciarDatosAsiento = iniciarDatosAsiento;
	}

	public int getEjercicioActivo() {
		return ejercicioActivo;
	}

	public void setEjercicioActivo(int ejercicioActivo) {
		this.ejercicioActivo = ejercicioActivo;
	}

	public String getObservacionesContables() {
		return observacionesContables;
	}

	public void setObservacionesContables(String observacionesContables) {
		this.observacionesContables = observacionesContables;
	}
}
