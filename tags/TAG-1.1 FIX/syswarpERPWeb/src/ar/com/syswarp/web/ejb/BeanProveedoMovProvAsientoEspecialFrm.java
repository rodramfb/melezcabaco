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

public class BeanProveedoMovProvAsientoEspecialFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProveedoMovProvAsientoEspecialFrm.class);

	private String aceptar = "";

	private BigDecimal nrointerno = BigDecimal.valueOf(-1);

	private BigDecimal idproveedor = BigDecimal.valueOf(0);

	private String dproveedor = "";

	
	private Timestamp fechamov = new Timestamp(Common.initObjectTime());

	private String fechamovStr = Common.initObjectTimeStr();

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

	private java.sql.Date fechavto = new java.sql.Date(Common.initObjectTime());

	private String fechavtoStr = Common.initObjectTimeStr();

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private String calcular = "";

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

	private String observacionesContables = "";

	private int ejercicioActivo = 0;

	// -----------
	private List tipocomp = new ArrayList();

	private List lstConceptos = new ArrayList();

	private String[] conc = null; // idconcepto
	private String[] idcuenta = null;
	private String[] cuenta = null;
	private String[] valor = null;
	private String[] tipo = null;
	// -----------

	/* ----- */

	private Hashtable htAsiento = new Hashtable();
	
	//20110811 - CAMI - Condicion de pago -------->
	
	private BigDecimal idCondpago = new BigDecimal(-1);
	
	//<--------------------------------------------
	

	public BeanProveedoMovProvAsientoEspecialFrm() {
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
		try {

			Hashtable htPlanesContables = (Hashtable) session
					.getAttribute("htPlanesContablesConfirmados");
			Proveedores proveedores = Common.getProveedores();
			if (this.accion.equalsIgnoreCase("alta")) {
				// TODO: flagMovAplicado, el mismo se remueve en
				// BeanProveedoMovProvFrm.
				log.debug("ENTRE ->");
				log.debug("session flag:"
						+ session.getAttribute("flagMovAplicado"));
				log.info("flag: " + session.getAttribute("flagMovAplicado"));
				if (session.getAttribute("flagMovAplicado") == null) {

					/*if (!this.stock_fact.equalsIgnoreCase("C")) { // si no es
						// contable
						/*
						 * BigDecimal idproveedor,
			Timestamp fechamov, BigDecimal sucursal, BigDecimal comprob,
			BigDecimal tipomov, String tipomovs, BigDecimal importe,
			BigDecimal saldo, BigDecimal idcondicionpago, Timestamp fecha_subd,
			BigDecimal retoque, java.sql.Date fechavto, int ejercicioactivo,
			String usuarioalt, String[] idConcepto, String[] idCuenta,
			String[] valor, String[] tipo, BigDecimal idempresa,
			String obscontable
						 * */
					/*
					}
					if (this.stock_fact.equalsIgnoreCase("C")) { // si es
						// contable
						*/
						log.debug("conc" + this.conc);
						log.debug("idcuenta" + this.idcuenta);
						log.debug("valor   " + this.valor);
						log.debug("tipo    " + this.tipo);
						log.debug("--------------------------");

						resultado = proveedores.capturaComprobantesProvContableCreate(
								idproveedor, fechamov, sucursal, comprob,
								tipomov, tipomovs, new BigDecimal(importe
										.toString()), new BigDecimal(saldo
										.toString()), idcondicionpago,
								fecha_subd, retoque, fechavto,
								this.ejercicioActivo, usuarioalt,htPlanesContables, this.idempresa,
								this.observacionesContables);
					//}

				} else {
					resultado = session.getAttribute("flagMovAplicado")
							.toString();
					log.debug("VALOR DE MOV. DESDE SESION (F5): " + resultado);
				}

			}

			if (Common.esNumerico(resultado)) {

				dispatcher = request
						.getRequestDispatcher("proveedoMovProvImpresionEsp.jsp?nrointerno="
								+ resultado);
				dispatcher.forward(request, response);

				session.setAttribute("flagMovAplicado", resultado);

			} else {
				this.mensaje = resultado;
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
				this.fechamov = Timestamp.valueOf(uCampos[2]);
				this.fechamovStr = Common.setObjectToStrOrTime(fechamov,
						"JSTsToStr").toString();

				this.sucursal = BigDecimal.valueOf(Long.parseLong(uCampos[3]));
				this.comprob = BigDecimal.valueOf(Long.parseLong(uCampos[4]));
				this.tipomov = BigDecimal.valueOf(Long.parseLong(uCampos[5]));
				this.tipomovs = uCampos[6];
				this.importe = Double.valueOf(uCampos[7]);
				this.saldo = Double.valueOf(uCampos[8]);
				//20110811 - CAMI - Condicion de pago -------->
				this.idCondpago = new BigDecimal(request.getSession().getAttribute("idcondpago").toString());
				if (this.idCondpago.longValue()>0)
				{
					this.idcondicionpago = this.idCondpago;
				}
				//<--------------------------------------------
				this.fecha_subd = Timestamp.valueOf(uCampos[10]);
				this.observacionesContables = uCampos[17];
				this.fecha_subdStr = Common.setObjectToStrOrTime(fecha_subd,
						"JSTsToStr").toString();

				this.retoque = BigDecimal.valueOf(Long.parseLong(uCampos[11]));
				this.fechavto = (java.sql.Date) Common.setObjectToStrOrTime(
						uCampos[12], "StrYMDToJSDate");
				this.fechavtoStr = Common.setObjectToStrOrTime(fechavto,
						"JSDateToStr").toString();

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
			log.info("idproveedor: " + this.idproveedor);
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

					// log.info("datosProveedor[18] - ctaretiva: " + ctaretiva);

					if (this.ctaiva != null && !this.ctaiva.equals("0"))
						this.readonlyIva = "";

					if (this.ctaretiva != null && !this.ctaretiva.equals("0"))
						this.readonlyRetIva = "";

					this.dproveedor = datosProveedor[1];
					// Si accion es alta seteo la condicion de pago del
					// proveedor,
					// sino prevalece la del movimiento.
					log.info("" + session.getAttribute("idcondpago").toString());
					if (this.accion.equalsIgnoreCase("alta")) {
						this.idCondpago = new BigDecimal(request.getSession().getAttribute("idcondpago").toString());
						if (this.idCondpago.longValue()>0)
						{
							this.idcondicionpago = this.idCondpago;
						}
					}
					//this.stock_fact = datosProveedor[33];
					//20110811 - CAMI - Condicion de pago -------->
					this.idCondpago = new BigDecimal(request.getSession().getAttribute("idcondpago").toString());
					if (this.idCondpago.longValue()>0)
					{
						this.idcondicionpago = this.idCondpago;
					}
					//<------------------------------------
					List listCondPago = proveedor.getProveedoCondicioPK(
							this.idcondicionpago, this.idempresa);
					if (listCondPago.iterator().hasNext()) {
						String[] datosCondPago = (String[]) listCondPago
								.iterator().next();
						this.dcondicionpago = datosCondPago[1];
						this.cantidaddias = Integer.parseInt(datosCondPago[2]);
					}
				}

				// Asegura que si el proveedor no factura con stock, no exista
				// ningun articulo en session
				/*if (this.stock_fact.equalsIgnoreCase("N")) {
					session.removeAttribute("htPlanesContablesConfirmados");
					session.removeAttribute("subTotalGravado");
					this.readonlyNetoGravado = "";
				}*/

				// Si el proveedor permite elegir actualizar o no el stock y no
				// se cargo producto alguno, permite que se modifique neto
				// gravado.
				if (//this.stock_fact.equalsIgnoreCase("?")&& 
						this.session.getAttribute("htPlanesContablesConfirmados") == null) {
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

				this.provPorcNormalIva = "0.00";
				this.provPorcEspecialIvaPercep = "0.00";

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
					if (Float.parseFloat(porcentaje) <= 100) {
						session.setAttribute(nombre, Common
								.getNumeroFormateado(Float
										.parseFloat(porcentaje), 3, 2));
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("asignarPorcentajes(Object obj, String nombre)" + e);
		}

	}

	/*
	 * public static boolean esPorcentaje(String porcentaje) { boolean fOk =
	 * false; try { if (Float.valueOf(porcentaje).floatValue() >= 0 &&
	 * Float.valueOf(porcentaje).floatValue() <= 100) { fOk = true; } } catch
	 * (Exception e) { // TODO: handle exception log.error("esPorcentaje(String
	 * porcentaje)" + e); }
	 * 
	 * return fOk; }
	 */
	public boolean calcularImportes() {
		String mensajeCI = "";
		boolean fOk = false;
		try {
			/*if (this.stock_fact.equalsIgnoreCase("C")) { // si viene unicamente
				// por imputacion
				// contable
				BigDecimal totBD = new BigDecimal(0);
				for (int i = 0; i < this.valor.length; ++i) {
					String valorC = valor[i];
					BigDecimal valorBD = new BigDecimal(Double.valueOf(
							Common.getNumeroFormateado(Double
									.parseDouble(valorC), 10, 2)).toString());
					totBD = totBD.add(valorBD);
					fOk = true;
				}
				this.importe = new Double(totBD.toString());

			} else {*/
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

					log.debug("calcularImportes(): NUMERICOS");

					if (!Common.esPorcentaje(this.porcentajeBonificacion)) {
						mensajeCI = " % (2) Bonificación Incorrecto.";
					} else if (!Common.esPorcentaje(this.provPorcNormalIva)) {
						mensajeCI = " % (2) Iva Incorrecto.";
					} else if (!Common
							.esPorcentaje(this.provPorcEspecialIvaPercep)) {
						mensajeCI = " % (2) Percepción Incorrecto.";
					} else {
						log.debug("calcularImportes(): %'s OK");
						// Neto Gravado se calcula cuando se cargaron articulos
						// para actualizar stock
						// si no se toma el valor ingresado por pantalla

						if (this.session.getAttribute("htPlanesContablesConfirmados") != null) {

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

						/**if (this.readonlyIva.equals(""))
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
						 	*/
						/**if (this.readonlyRetIva.equals(""))
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
							this.percepcionIva = "0.00";*/

						// CALCULA EL TOTAL DEL COMPROBANTE
						this.importe = Double
								.valueOf(Common
										.getNumeroFormateado(
												Double
														.parseDouble(this.netoGravado)
														+ Double
																.parseDouble(this.netoExento)
														+ Double
																.parseDouble(this.iva)
														+ Double
																.parseDouble(this.percepcionIva),
												10, 2));

						fOk = true;

					}
				}
			//}
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

			Contable contable = Common.getContable();
			// INICIA INICIALIZAR CUENTAS
			// 1 - Cuentas Neto Gravado
			// Generando datos para asiento
			// TODO: es necesario identificar si las cuentas se
			// modifican durante la session siempre y cuando lo permita
			// la regla del negocio
			if (this.session.getAttribute("htPlanesContablesConfirmados") != null) {
				// TODO: MODIFICAR PORCENTAJE DE BONIFICACION, MODIFICAR VALORES
				// TODO: TOTALES CUENTA A LA NUEVA BONIFICACION
				if (session.getAttribute("htNetoGravado") == null
						|| ((Hashtable) session.getAttribute("htNetoGravado"))
								.isEmpty()) {

					Enumeration enu = ((Hashtable) this.session
							.getAttribute("htPlanesContablesConfirmados")).keys();
					// RECORRE LA LISTA DE ARTICULOS
					while (enu.hasMoreElements()) {
						String key = (String) enu.nextElement();
						String[] planCtas = (String[]) ((Hashtable) this.session
								.getAttribute("htPlanesContablesConfirmados"))
								.get(key);
						// Carga vector con los datos de la cuenta
						// asociada
						// al articulo.
						// log.info("art[12]: " + art[12]);
						String[] datosCuenta = this.getDatosCuentas(planCtas[0]);

						String precioPlanCuentas = ""
								+ (Double.parseDouble(planCtas[11]) - (Double
										.parseDouble(planCtas[11]) * (Double
										.parseDouble(this.porcentajeBonificacion) / 100)));
						precioPlanCuentas = Common.getNumeroFormateado(Double
								.parseDouble(precioPlanCuentas), 10, 2);

						String[] datosAsientoNG = null;

						if (datosCuenta != null) {
							datosAsientoNG = new String[] { datosCuenta[0],
									datosCuenta[1], precioPlanCuentas, "G",
									"noasignable" };
						} else {
							datosAsientoNG = new String[] { "0", "",
									precioPlanCuentas, "G", "asignable" };
							this.modficaCNG = true;
						}
						this.htNetoGravado.put(key, datosAsientoNG);
					}

				} else {
					this.htNetoGravado = (Hashtable) session
							.getAttribute("htNetoGravado");
				}

			} else {
				this.modficaCNG = true;
				if (session.getAttribute("htNetoGravado") == null
						|| ((Hashtable) session.getAttribute("htNetoGravado"))
								.isEmpty()) {

					// log.info("ctaactivo1: " +ctaactivo1);
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
							.getAttribute("htNetoGravado");
				}

			}
			session.setAttribute("htNetoGravado", this.htNetoGravado);

			// 2 - IVA y TOTAL

			// log.info("ctapasivo: " + ctapasivo);
			String[] datosCuenta = this.getDatosCuentas(this.ctapasivo);
			// log.info("datosCuenta: " + datosCuenta);

			if (datosCuenta != null) {
				String[] datosAsientoIVATotal = new String[] {
						datosCuenta[0],
						datosCuenta[1],
						Common.getNumeroFormateado(this.importe.doubleValue(),
								10, 2), "T", "noasignable" };

				htIvaTotal.put("Z" + Common.initObjectTime() + "1",
						datosAsientoIVATotal);

				log.info("ctaiva: " + ctaiva);
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

					//log.info("ctaretiva: " + ctaretiva);
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
				if (this.netoExento != null && !this.netoExento.equals("0.00")) {
					//log.info("ctapasivo: " + ctapasivo);
					datosCuenta = this.getDatosCuentas(this.ctapasivo);
					// Si esta asociado a la session
					if (datosCuenta != null) {

						if (session.getAttribute("htNetoExento") == null) {

							log.debug("**** ***** **** datosCuenta: "
									+ datosCuenta);

							String[] datosAsientoNetoExento = new String[] {
									datosCuenta[0], datosCuenta[1],
									this.importe.toString(), "E", "noasignable" };
							//log.info("ctaactivo1: " + ctaactivo1);
							datosCuenta = this.getDatosCuentas(this.ctaactivo1);

							if (datosCuenta != null) {

								datosAsientoNetoExento = new String[] {
										datosCuenta[0], datosCuenta[1],
										this.netoExento, "E", "noasignable" };

								this.htNetoExento.put("W"
										+ Common.initObjectTime() + "4",
										datosAsientoNetoExento);
								session.setAttribute("htNetoExento",
										this.htNetoExento);
							} else
								log
										.info("Falla setear valores de cuenta neto exento");

						} else {
							this.htNetoExento = (Hashtable) session
									.getAttribute("htNetoExento");
						}

					} else
						log.info("Imposible setear cuenta Exento, definir. ");

				}
			} else
				this.mensaje = "Cuenta pasivo sin definir o inexistente en ejercicio activo.";

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
				// TODO: quitar
				log.debug("    -------   ");
				for (int i = 0; i < datos.length; i++)
					log.debug("DATOS " + i + ": " + datos[i]);
				// ---
				acumula += Double.parseDouble(datos[2]);
				if (datos[0] == null || datos[0].equals("0")
						|| datos[0].trim().equals(""))
					fOk = false;

				if (!fOk) {
					this.mensaje = "Cuenta invólida.";
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
		log.debug("INICIA generarDatosTransaccion()--> ");
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
				log.debug("  KEY :  " + key);
				for (int i = 0; i < str.length; i++) {
					log.debug("POSICION " + i + " : " + str[i]);
				}
			}

		} catch (Exception e) {
			log.error("generarDatosTransaccion():" + e);
		}
		log.debug("FINALIZA generarDatosTransaccion()<-- ");
	}

	public boolean ejecutarValidacion() {

		try {

			ejercicioActivo = Integer.parseInt(this.session.getAttribute(
					"ejercicioActivo").toString());

			Calendar cal = new GregorianCalendar();

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("proveedoMovProvEspFrm.jsp");
				this.destruirHTSession();
				return true;
			}

			this.htCP = Common
					.getHashEntidad("getProveedoCondicioAll", new Class[] {
							long.class, long.class, BigDecimal.class },
							new Object[] { new Long(250), new Long(0),
									this.idempresa });

			// 20101012
			// this.calcularImportes();

			if (!this.aceptar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					// Recupera los datos del proveedor
					this.inicializarDatosProv();
					
					// 20101012 -->
					this.calcularImportes();
					// <--
					
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

				//	if (this.stock_fact.equalsIgnoreCase("S")) {
						if (this.session.getAttribute("htPlanesContablesConfirmados") == null) {
							this.mensaje = "Debe ingresar algún plan de cuenta. ";
							return false;
						}
				//	}

					if (this.fechamov.compareTo(this.fechavto) > 0) {
						this.mensaje = "Fecha de movimiento no puede ser mayor a fecha vencimiento.";
						return false;
					}

					cal.setTime(this.fechamov);
					cal.add(Calendar.DATE, cantidaddias);

					if (this.fechavto.after(cal.getTime())) {
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

					if (!this.netoExento.equals("0.00")) {
						if (!validarTotales(
								Double.parseDouble(this.netoExento),
								this.htNetoExento))
							return false;
					}

					// 20090428 - EJV
					// -->
					if (!Common.setNotNull(this.ctaiva).equals("")
							&& !this.ctaiva.equals("0")
							&& getDatosCuentas(this.ctaiva) == null) {

						this.mensaje = "Cuenta iva " + this.ctaiva
								+ ", no pertenece al ejercicio activo.";
						return false;

					}
					
					if (!Common.setNotNull(this.ctaretiva).equals("")
							&& !this.ctaretiva.equals("0")
							&& getDatosCuentas(this.ctaretiva) == null) {

						this.mensaje = "Cuenta percepción iva "
								+ this.ctaretiva
								+ ", no pertenece al ejercicio activo.";
						return false;

					}

					if (!Common.setNotNull(this.ctapasivo).equals("")
							&& !this.ctapasivo.equals("0")
							&& getDatosCuentas(this.ctapasivo) == null) {

						this.mensaje = "Cuenta pasivo " + this.ctapasivo
								+ ", no pertenece al ejercicio activo.";
						return false;

					}

					// <--

				}

				this.generarDatosTransaccion();
				this.ejecutarSentenciaDML();
			} else {

				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosProveedoMovProv();
				}
				this.inicializarDatosProv();
				// 20101012 -->
				this.calcularImportes();
				// <--
				this.inicializarDatosCuentas();

			}

			//this.calcularImportes();

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	public String[] getDatosCuentas(String cuenta) throws RemoteException {

		String[] datos = null;

		try {

			Contable contable = Common.getContable();

			if (!Common.setNotNull(cuenta).equals("")) {

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
				log.debug("getDatosCuentas: CUENTA NULA");

		} catch (Exception e) {
			log.error("getDatosCuentas(): " + e);
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

	public Timestamp getFechamov() {
		return fechamov;
	}

	public void setFechamov(Timestamp fechamov) {
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

	public java.sql.Date getFechavto() {
		return fechavto;
	}

	public void setFechavto(java.sql.Date fechavto) {
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

	public String getFechamovStr() {
		return fechamovStr;
	}

	public void setFechamovStr(String fechamovStr) {
		this.fechamovStr = fechamovStr;
		this.fechamov = (java.sql.Timestamp) Common.setObjectToStrOrTime(
				fechamovStr, "StrToJSTs");
	}

	public String getFechavtoStr() {
		return fechavtoStr;
	}

	public void setFechavtoStr(String FechavtoStr) {
		this.fechavtoStr = FechavtoStr;
		this.fechavto = (java.sql.Date) Common.setObjectToStrOrTime(
				FechavtoStr, "StrToJSDate");
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
		session.removeAttribute("htNetoGravado");
		session.removeAttribute("htNetoExento");
		session.removeAttribute("htIvaTotal");
		session.removeAttribute("htPlanesContablesConfirmados");
		session.removeAttribute("subTotalGravado");
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getObservacionesContables() {
		return observacionesContables;
	}

	public void setObservacionesContables(String observacionesContables) {
		this.observacionesContables = observacionesContables;
	}

	public String[] getConc() {
		return conc;
	}

	public void setConc(String[] conc) {
		this.conc = conc;
	}

	public String[] getValor() {
		return valor;
	}

	public void setValor(String[] valor) {
		this.valor = valor;
	}

	public String[] getTipo() {
		return tipo;
	}

	public void setTipo(String[] tipo) {
		this.tipo = tipo;
	}

	public List getTipocomp() {
		return tipocomp;
	}

	public void setTipocomp(List tipocomp) {
		this.tipocomp = tipocomp;
	}

	public List getLstConceptos() {
		return lstConceptos;
	}

	public void setLstConceptos(List lstConceptos) {
		this.lstConceptos = lstConceptos;
	}

	public String[] getCuenta() {
		return cuenta;
	}

	public void setCuenta(String[] cuenta) {
		this.cuenta = cuenta;
	}

	public String[] getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(String[] idcuenta) {
		this.idcuenta = idcuenta;
	}

	public BigDecimal getIdCondpago() {
		return idCondpago;
	}

	public void setIdCondpago(BigDecimal idCondpago) {
		this.idCondpago = idCondpago;
	}

}

