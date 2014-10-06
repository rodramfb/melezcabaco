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
import javax.xml.rpc.holders.BigDecimalHolder;

import net.sf.jasperreports.crosstabs.fill.JRPercentageCalculatorFactory.BigDecimalPercentageCalculator;

import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;
import java.sql.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanClientesIngresoMovimientosConfirma implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger
			.getLogger(BeanClientesIngresoMovimientosConfirma.class);

	GeneralBean gb = new GeneralBean();

	private String fechamov = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	// 1 - DATOS APLICACION

	private String tipocliente = "";

	private String labelTipocliente = "";

	private String accion = "";

	private String mensaje = "";

	private String confirmar = "";

	private String calcular = "";

	private String volver = "";

	private boolean primeraCarga = true;

	private boolean iniciaCobranza = true;

	private String confirma_tipocliente = "";

	private String tipoclienteanterior = "";

	private int ejercicioactivo = 0;

	private BigDecimal tipocomp = new BigDecimal(-1);

	private String siglatipomovs = "";

	private String tipomovs = "";

	private BigDecimal tipomov_tc = new BigDecimal(-1);

	private String usuarioact = "";

	private String usuarioalt = "";

	private List listClientesTipoComp = null;

	private String lovClienteAnexo = "";

	// 2 - DATOS CLIENTE

	private BigDecimal idcliente = new BigDecimal(0);

	private String cliente = "";

	private String domicilio = "";

	private String postal = "";

	private BigDecimal idprovincia = new BigDecimal(0);

	private String provincia = "";

	private BigDecimal idlocalidad = new BigDecimal(0);

	private String localidad = "";

	private String tipodocumento = "CUIT";

	private String nrodocumento = "";

	private String sucursal = "";

	private String comprob = "";

	private String observaciones = "";

	private BigDecimal idtipoiva = new BigDecimal(-1);

	private String tipoiva = "";

	private String letra = "";

	private BigDecimal idcondicion = new BigDecimal(-1);

	private String condicion = "";

	private BigDecimal iddomicilio = new BigDecimal(-1);

	private String sucursalcliente = "";

	// DATOS TIPO COMPROBANTE

	private String mueveStock = "";

	// DATOS IVA CLIENTE

	private List listClientesTipoIva = new ArrayList();

	private String discrimina = "";

	// DATOS DOCUMENTO

	private String netogravado = "0.00";

	private String netogravadoaux = "0.00";

	private BigDecimal porcentajeivauno = new BigDecimal(0.00);

	private BigDecimal porcentajeivados = new BigDecimal(0.00);

	private BigDecimal ivauno = new BigDecimal(0.00);

	private BigDecimal ivados = new BigDecimal(0.00);

	private BigDecimal imp_int_tc = new BigDecimal(0.00);

	private BigDecimal impuestosinternos = new BigDecimal(0.00);

	private BigDecimal otrosimpuestos = new BigDecimal(0.00);

	private String total = "0.00";

	private String totalaux = "0.00";

	private String totalnogravado = "0.00";

	private String totalnogravadoaux = "0.00";

	private String recargo = "0.00";

	private String bonificacion = "0.00";

	private BigDecimal totalfactura = new BigDecimal(0);

	// 3 - Cuentas Articulos
	private BigDecimal totalDebe = new BigDecimal(0);

	// 3 - Cuentas Imputacion
	private BigDecimal totalHaber = new BigDecimal(0);

	private String cuentanetogravado = "";

	private String cuentaiva = "";

	private String cuentaivanoinscripto = "";

	private String cuentanetoexento = "";

	private String cuentatotal = "";

	// 20110628 - EJV - Factuaracion FE-CF-MA -->

	private String cuentaiibb = "";

	private BigDecimal porcentajeiibb = new BigDecimal(0);

	private BigDecimal percepcioniibb = new BigDecimal(0);

	// <--

	// 20110929 - EJV - mantis 771 -->

	private String nrotarjeta = "";

	private BigDecimal idtarjeta = new BigDecimal(-1);

	private String tarjetacredito = "";

	private int cuotas = 0;

	private BigDecimal idclub = new BigDecimal(-1);

	// <--

	// 20121005 - EJV - Mantis 882 -->

	private BigDecimal idmotivonc = new BigDecimal(-1);

	private String motivonc = "";

	// <--

	public BeanClientesIngresoMovimientosConfirma() {
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
		try {
			Clientes clientes = Common.getClientes();
			String[] resultado = null;
			Hashtable htArticulos = (Hashtable) session
					.getAttribute("htArticulosInOutOK");
			Hashtable htCuentas = (Hashtable) session
					.getAttribute("htCuentasOk");
			Hashtable htIdentificadoresIngresos = (Hashtable) session
					.getAttribute("htIdentificadoresIngresosOK");

			Hashtable htCuentasContCli = new Hashtable();

			htCuentasContCli.put("T", new String[] { this.cuentatotal,
					this.totalfactura.toString() });
			htCuentasContCli.put("G", new String[] { this.cuentanetogravado,
					this.netogravado });
			htCuentasContCli.put("I", new String[] { this.cuentaiva,
					this.ivauno.toString() });
			htCuentasContCli.put("N", new String[] { this.cuentaivanoinscripto,
					this.ivados.toString() });
			htCuentasContCli.put("E", new String[] { this.cuentanetoexento,
					this.totalnogravado });
			htCuentasContCli.put("PIB", new String[] { this.cuentaiibb,
					this.percepcioniibb.toString() });

			this.mensaje = clientes.clientesMovimientoClienteCreate(
					this.idcliente,
					this.nrodocumento,
					this.cliente,
					this.domicilio,
					this.idlocalidad,
					this.idprovincia,
					this.postal,
					this.idtipoiva,
					(Timestamp) Common.setObjectToStrOrTime(fechamov,
							"StrToJSTs"),
					new BigDecimal(this.sucursal),
					// 20110622 - EJV - Factuaracion FE-CF-MA -->
					// new BigDecimal(comprob),
					new BigDecimal(0),
					// <--
					null, this.tipomov_tc, this.tipomovs, this.letra,
					this.totalfactura, this.totalfactura, new BigDecimal(1),
					new BigDecimal(1), null, this.tipocomp.toString(),
					this.idcondicion, new BigDecimal(-1), null, null, null,
					null, null, null, new BigDecimal(0), null, null,
					this.ejercicioactivo,
					this.observaciones,
					// 20110628 - EJV - Factuaracion FE-CF-MA -->
					this.porcentajeiibb,
					// <--
					// 20110930 - EJV - mantis 771 -->
					this.idtarjeta, this.cuotas,
					this.idclub,
					// <--
					// 20121005 - EJV - Mantis 882 -->
					GeneralBean.setNull(this.idmotivonc, 1),
					// <--
					htCuentasContCli, htArticulos, htIdentificadoresIngresos,
					this.usuarioalt, this.idempresa);

			if (Common.esEntero(this.mensaje)) {
				String plantillaImpresionJRXML = "";

				switch (this.tipomovs.charAt(this.tipomovs.length() - 1)) {
				case 'A':
					plantillaImpresionJRXML = "modelo.factura.ri";
					break;
				case 'B':
					plantillaImpresionJRXML = "modelo.factura.rni";
					break;
				case 'E':
					plantillaImpresionJRXML = "modelo.factura.rni";
					break;
				default:
					plantillaImpresionJRXML = "modelo.factura.rni";
					break;

				}

				response
						.sendRedirect("impresionMovimientoCliente.jsp?nrointernomovcliente="
								+ this.mensaje
								+ "&plantillaImpresionJRXML="
								+ plantillaImpresionJRXML);
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		boolean isAnexoOk = false;
		try {

			Clientes clientes = Common.getClientes();
			this.listClientesTipoComp = clientes.getClientesTipoCompAll(50, 0,
					this.idempresa);

			// DATOS DEL TIPO DE COMPROBANTE ELEGIDO
			if (this.tipocomp.longValue() != -1) {
				try {
					String[] datos = (String[]) clientes.getClientesTipoCompPK(
							this.tipocomp, this.idempresa).get(0);
					this.tipomov_tc = new BigDecimal(datos[1]);
					this.mueveStock = datos[5];

					this.cuentaiva = datos[14] != null
							&& !datos[14].equals("0")
							&& this.cuentaiva.equals("") ? datos[14]
							: this.cuentaiva;
					this.cuentaivanoinscripto = datos[15] != null
							&& !datos[15].equals("0")
							&& this.cuentaivanoinscripto.equals("") ? datos[15]
							: this.cuentaivanoinscripto;
					this.cuentanetogravado = datos[16] != null
							&& !datos[16].equals("0")
							&& this.cuentanetogravado.equals("") ? datos[16]
							: this.cuentanetogravado;
					this.cuentanetoexento = datos[17] != null
							&& !datos[17].equals("0")
							&& this.cuentanetoexento.equals("") ? datos[17]
							: this.cuentanetoexento;

					this.imp_int_tc = new BigDecimal(datos[47]);

					this.siglatipomovs = clientes.getGTMSigla(this.tipomov_tc);

				} catch (Exception e) {
					log.error("DATOS TC: " + e);
				}

			}

			if (this.idtipoiva.longValue() != -1) {
				this.listClientesTipoIva = clientes.getClientestablaivaPK(
						this.idtipoiva, this.idempresa);
				try {

					String[] datos = (String[]) listClientesTipoIva.get(0);
					this.discrimina = datos[3];
					this.porcentajeivauno = new BigDecimal(datos[2])
							.setScale(2);
					this.porcentajeivados = new BigDecimal(datos[5])
							.setScale(2);
					this.letra = datos[6];

				} catch (Exception e) {
					log.error("DATOS TI: " + e);
				}
			}

			// TODO: FLAG PRIMERA CARGA !!!

			if (isIniciaCobranza()) {

				session.removeAttribute("htIdentificadoresIngresos");
				session.removeAttribute("htIdentificadoresIngresosOK");
				session.setAttribute("htIdentificadoresIngresosOK",
						new Hashtable());

			}

			this.totalDebe = getTotalesAsiento((Hashtable) session
					.getAttribute("htArticulosInOutOK"), 11);
			this.totalHaber = getTotalesAsiento((Hashtable) session
					.getAttribute("htCuentasOk"), 2);

			isAnexoOk = this.setDatosAnexo();

			if (!this.calcular.equals("")) {
				this.calculaTotalesDocumento();
			}

			if (!this.confirmar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					if (fechamov == null || fechamov.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo fecha movimiento. ";
						return false;
					}

					if (this.tipocomp.longValue() == -1) {
						this.mensaje = "Seleccione tipo de comprobante.";
						return false;
					}

					// 20110617 - EJV - Factuaracion FE-CF-MA -->
					// if (!Common.esEntero(this.comprob)
					// || Long.parseLong(this.comprob) < 1) {
					// this.mensaje = "Comprobante invalido.";
					// return false;
					// }
					if (!Common.esEntero(this.sucursal)
							|| Long.parseLong(this.sucursal) < 0) {
						this.mensaje = "Sucursal no definida o seleccionada.";
						return false;
					}
					// <--

					if (this.mueveStock.equalsIgnoreCase("N")
							&& this.observaciones.trim().length() < 30) {
						this.mensaje = "Ingrese concepto/obs. valido/as para el documento (minimo 30 caracteres).";
						return false;
					}

					if (this.tipocliente.equals("")) {
						this.mensaje = "Seleccione tipo de cliente.";
						return false;
					}

					if (!isAnexoOk) {
						return false;
					}

					// 20110622 - EJV - Factuaracion FE-CF-MA -->
					// if (!Common.esEntero(this.comprob)
					// || Long.parseLong(this.comprob) < 0) {
					// this.mensaje =
					// "Ingrese valores validos para comprobante.";
					// return false;
					// }
					// <--

					if (this.cliente.trim().equals("")) {
						this.mensaje = "Ingrese Razon.";
						return false;
					}

					if (this.idlocalidad.compareTo(new BigDecimal(0)) <= 0) {
						this.mensaje = "Ingrese Localidad.";
						return false;
					}

					// 20110930 - EJV - mantis 771 -->
					if (this.idcondicion == null
							|| this.idcondicion.intValue() < 1) {
						this.mensaje = "Ingrese Condicion de Venta.";
						return false;
					}

					if (this.idcondicion.intValue() == 5) {

						if (this.idtarjeta == null
								|| this.idtarjeta.intValue() < 1) {
							this.mensaje = "Condicion de venta es tarjeta de credito, es necesario seleccionar una.";
							return false;
						}

						if (this.cuotas < 1) {
							this.mensaje = "Ingrese Cantidad de Cuotas.";
							return false;
						}

					}
					// <--

					if (!this.mueveStock.equalsIgnoreCase("N")) {
						// TODO: es necesario aplicar logica ... esta pendiente
						if (session.getAttribute("htArticulosInOutOK") == null
								|| ((Hashtable) session
										.getAttribute("htArticulosInOutOK"))
										.isEmpty()) {
							this.mensaje = "Ingrese al menos un articulo que afecte al movimiento.";
							return false;
						}
					}

					this.tipomovs = this.siglatipomovs + this.letra;

					// 20110622 - EJV - Factuaracion FE-CF-MA -->
					// if (clientes.isExisteComprobanteMovCli(new BigDecimal(
					// this.sucursal), new BigDecimal(this.comprob),
					// this.tipomovs, this.idempresa)) {
					// this.mensaje = "El comprobante "
					// + Common.strZero(this.comprob, 8)
					// + " ya fue ingresado.";
					// return false;
					//
					// }
					// <--

					if (!this.calculaTotalesDocumento()) {
						return false;
					}

					if (isNecesario(this.netogravado)
							&& !Common.esEntero(this.cuentanetogravado)) {
						this.mensaje = "Cuenta neto gravado invalida.";
						return false;
					}

					if (isNecesario(this.ivauno.toString())
							&& !Common.esEntero(this.cuentaiva)) {
						this.mensaje = "Cuenta iva invalida.";
						return false;
					}

					if (isNecesario(this.ivados.toString())
							&& !Common.esEntero(this.cuentaivanoinscripto)) {
						this.mensaje = "Cuenta iva no inscripto invalida.";
						return false;
					}

					if (isNecesario(this.totalnogravado)
							&& !Common.esEntero(this.cuentanetoexento)) {
						this.mensaje = "Cuenta no gravado invalida.";
						return false;
					}

					if (isNecesario(this.percepcioniibb.toString())
							&& !Common.esEntero(this.cuentaiibb)) {
						this.mensaje = "Cuenta percepcion iibb invalida.";
						return false;
					}

					if (isNecesario(this.totalfactura.toString())
							&& !Common.esEntero(this.cuentatotal)) {
						this.mensaje = "Cuenta total invalida.";
						return false;
					}

					// 20121005 - EJV - Mantis 882 -->
					if (this.tipocomp.intValue() == 3) {
						if (this.idmotivonc.intValue() < 1) {
							this.mensaje = "Es necesario asignar un motivo para NC.";
							return false;
						}
					}
					// <--

				}

				this.ejecutarSentenciaDML();

			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {

				}
			}

			// log.info("Siglas: " + this.siglatipomovs);
			// log.info("Letra : " + this.letra);

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private boolean setDatosAnexo() {

		boolean fOk = true;
		try {

			Clientes clientes = Common.getClientes();
			if (!isPrimeraCarga()) {
				if (!this.tipocliente.equals(this.tipoclienteanterior)) {

					this.mensaje = "Cambio el tipo de cliente, es necesario cargar datos nuevamente.";
					this.idcliente = new BigDecimal(0);
					this.cliente = "";
					this.domicilio = "";
					this.idlocalidad = new BigDecimal(0);
					this.localidad = "";
					this.provincia = "";
					this.idprovincia = new BigDecimal(0);
					this.postal = "";
					this.nrodocumento = "";
					this.idtipoiva = new BigDecimal(-1);
					this.tipoiva = "";
					this.letra = "";
					this.ivauno = new BigDecimal(0);
					this.ivauno = new BigDecimal(0);
					this.porcentajeivauno = new BigDecimal(0);
					this.porcentajeivados = new BigDecimal(0);
					this.totalnogravado = "0.00";

					fOk = false;

				} else if (this.tipocliente.equalsIgnoreCase("C")
						&& !this.tipocliente.equals("")
						&& this.idcliente.compareTo(new BigDecimal(0)) > 0) {

					List lista = clientes.getClientesClientesDomiPK(
							this.idcliente, this.iddomicilio, this.idempresa);
					Iterator iter = lista.iterator();

					if (iter.hasNext()) {

						String[] datos = (String[]) iter.next();

						this.cliente = datos[1];
						this.tipodocumento = datos[3];
						this.nrodocumento = datos[4];
						this.idtipoiva = new BigDecimal(datos[6]);
						this.tipoiva = datos[7];
						this.idlocalidad = new BigDecimal(datos[27]);
						log.info("setDatosAnexo(): PASO 9");
						this.localidad = datos[28];
						this.idprovincia = new BigDecimal(datos[29]);
						// this.idprovincia = new BigDecimal(99999999);
						log.info("setDatosAnexo(): PASO 10");
						// this.provincia = "";
						this.provincia = datos[30];
						this.postal = datos[31];
						this.domicilio = datos[32];

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

	private boolean calculaTotalesDocumento() {
		boolean fOk = true;
		String salida = "";
		try {

			if (!Common.esPorcentaje(this.bonificacion)) {
				salida = "Porcentaje de bonificacion invalido.";
				fOk = false;
			} else if (!Common.esNumerico(this.netogravadoaux)) {
				salida = "Neto Gravado debe ser numerico.";
				fOk = false;
			} else if (!Common.esNumerico(this.totalaux)) {
				salida = "Total debe ser numerico.";
				fOk = false;
			} else if (!Common.esNumerico(this.totalnogravado)) {
				salida = "Total No Gravado debe ser numerico.";
				fOk = false;
			} else if (!Common.esNumerico(this.recargo)) {
				salida = "Recargo debe ser numerico.";
				fOk = false;
				// 20110627 - EJV - Factuaracion FE-CF-MA -->
			} else if (!Common.esPorcentaje(this.porcentajeiibb.toString())) {
				salida = "Porcentaje de percepcion de iibb invalido.";
				fOk = false;
				// <--
			} else {

				// 20110622 - EJV - Factuaracion FE-CF-MA -->

				int precision = 4;
				int auxPrecision = 4;
				int rMode = BigDecimal.ROUND_HALF_UP;
				BigDecimal bonificacionFuncion = new BigDecimal(0);
				BigDecimal recargoFuncion = new BigDecimal(this.recargo);
				BigDecimal divisor = new BigDecimal(1);
				BigDecimal tngaux = new BigDecimal(this.totalnogravadoaux);

				if (this.discrimina.equalsIgnoreCase("N")) {

					log.info("*NO* DISCRIMINA");

					// 20110627 - EJV - Factuaracion FE-CF-MA -->
					this.percepcioniibb = new BigDecimal(0);
					// <--

					if (this.mueveStock.equalsIgnoreCase("S")) {
						this.totalaux = this.totalDebe.toString();
					}

					BigDecimal totalFuncion = new BigDecimal(this.totalaux);
					totalFuncion = totalFuncion.add(recargoFuncion);

					BigDecimal factorUno = new BigDecimal(this.porcentajeivauno
							.floatValue() / 100).setScale(precision
							+ auxPrecision, rMode);
					BigDecimal factorDos = new BigDecimal(this.porcentajeivados
							.floatValue() / 100).setScale(precision
							+ auxPrecision, rMode);
					BigDecimal factorImpIntTc = new BigDecimal(this.imp_int_tc
							.floatValue() / 100).setScale(precision
							+ auxPrecision, rMode);

					BigDecimal factorBonificacion = new BigDecimal(
							new BigDecimal(this.bonificacion).divide(
									new BigDecimal(100),
									precision + auxPrecision, rMode).toString());

					bonificacionFuncion = totalFuncion.multiply(
							factorBonificacion).setScale(precision, rMode);

					totalFuncion = totalFuncion.subtract(bonificacionFuncion);

					divisor = new BigDecimal(factorUno.add(
							factorImpIntTc.add(new BigDecimal(1))).toString());
					divisor.setScale(precision + auxPrecision, rMode);// AQUI

					totalFuncion = totalFuncion.divide(divisor, precision
							+ auxPrecision, rMode);

					this.ivauno = (totalFuncion.multiply(factorUno));

					this.ivauno = this.ivauno.setScale(precision, rMode);

					if (this.imp_int_tc.floatValue() > 0) {
						this.impuestosinternos = new BigDecimal(this.totalaux);
						this.impuestosinternos = this.impuestosinternos
								.subtract(this.ivauno.add(totalFuncion));
					}

					// TODO: revisar iva2 (se calcula a partir del total ????)
					// this.ivados = totalFuncion.subtract(totalFuncion.divide(
					// factorDos, precision, BigDecimal.ROUND_UP));

					this.totalnogravado = (tngaux.subtract(tngaux
							.multiply(factorBonificacion)).setScale(precision,
							rMode)).toString();

					this.netogravado = totalFuncion.setScale(precision, rMode)
							.toString();
					this.totalfactura = new BigDecimal(this.netogravado)
							.setScale(precision, rMode);
					this.totalfactura = this.totalfactura.add(this.ivauno)
							.setScale(precision, rMode);
					this.totalfactura = this.totalfactura.add(this.ivados)
							.setScale(precision, rMode);
					this.total = this.totalfactura.toString();
					this.totalfactura = this.totalfactura.add(
							new BigDecimal(this.totalnogravado)).setScale(
							precision, rMode);

				} else if (this.discrimina.equalsIgnoreCase("S")) {

					log.info("DISCRIMINA");

					if (this.mueveStock.equalsIgnoreCase("S")) {
						this.netogravadoaux = this.totalDebe.toString();
					}

					BigDecimal totalFuncion = new BigDecimal(
							this.netogravadoaux).setScale(precision
							+ auxPrecision, rMode);
					BigDecimal factorUno = new BigDecimal(this.porcentajeivauno
							.floatValue() / 100).setScale(precision
							+ auxPrecision, rMode);
					BigDecimal factorDos = new BigDecimal(this.porcentajeivados
							.floatValue() / 100).setScale(precision
							+ auxPrecision, rMode);

					// 20110627 - EJV - Factuaracion FE-CF-MA -->
					BigDecimal factorIIBBBA = new BigDecimal(
							this.porcentajeiibb.floatValue() / 100).setScale(
							precision + auxPrecision, rMode);
					// <--

					BigDecimal factorBonificacion = new BigDecimal(
							this.bonificacion).divide(new BigDecimal(100),
							precision + auxPrecision, rMode);

					bonificacionFuncion = totalFuncion.multiply(
							factorBonificacion).setScale(
							precision + auxPrecision, rMode);

					totalFuncion = totalFuncion.subtract(bonificacionFuncion)
							.setScale(precision + auxPrecision, rMode);
					totalFuncion = totalFuncion.add(recargoFuncion).setScale(
							precision + auxPrecision, rMode);

					this.totalnogravado = (tngaux.subtract(tngaux
							.multiply(factorBonificacion)).setScale(precision,
							rMode)).toString();

					this.netogravado = totalFuncion.setScale(precision, rMode)
							.toString();

					this.ivauno = totalFuncion.multiply(factorUno).setScale(
							precision + auxPrecision, rMode);
					this.ivados = totalFuncion.multiply(factorDos).setScale(
							precision + auxPrecision, rMode);

					this.ivauno = this.ivauno.setScale(precision, rMode);
					this.ivados = this.ivados.setScale(precision, rMode);

					// this.total = ((totalFuncion.add(this.ivauno
					// .add(this.ivados))).setScale(precision, rMode))
					// .toString();
					// 20110627 - EJV - Factuaracion FE-CF-MA -->
					// TODO: Parametrizar umbral de aplicacion de percep.
					if (totalFuncion.doubleValue() < 50D) {

						this.total = (((totalFuncion.add(this.ivauno
								.add(this.ivados)))).setScale(precision, rMode))
								.toString();

						this.percepcioniibb = new BigDecimal(0);

					} else {

						this.percepcioniibb = totalFuncion.multiply(
								factorIIBBBA).setScale(
								precision + auxPrecision, rMode);
						this.percepcioniibb = this.percepcioniibb.setScale(
								precision, rMode);
						this.total = (((totalFuncion.add(this.ivauno
								.add(this.ivados))).add(this.percepcioniibb))
								.setScale(precision, rMode)).toString();
						// <--

					}

					this.totalfactura = new BigDecimal(this.totalnogravado)
							.add(new BigDecimal(this.total));
					this.totalfactura = this.totalfactura
							.add(this.impuestosinternos);
					this.totalfactura = this.totalfactura
							.add(this.otrosimpuestos);

					// int precision = 2;
					// BigDecimal bonificacionFuncion = new BigDecimal(0);
					// BigDecimal recargoFuncion = new BigDecimal(this.recargo);
					// BigDecimal divisor = new BigDecimal(1);
					// BigDecimal tngaux = new
					// BigDecimal(this.totalnogravadoaux);
					//
					// if (this.discrimina.equalsIgnoreCase("N")) {
					//
					// log.info("*NO* DISCRIMINA");
					//
					// if (this.mueveStock.equalsIgnoreCase("S")) {
					// this.totalaux = this.totalDebe.toString();
					// }
					//
					// BigDecimal totalFuncion = new BigDecimal(this.totalaux);
					// totalFuncion = totalFuncion.add(recargoFuncion);
					//
					// BigDecimal factorUno = new
					// BigDecimal(this.porcentajeivauno
					// .floatValue() / 100).setScale(precision,
					// BigDecimal.ROUND_UP);
					// BigDecimal factorDos = new
					// BigDecimal(this.porcentajeivados
					// .floatValue() / 100).setScale(precision,
					// BigDecimal.ROUND_UP);
					// BigDecimal factorImpIntTc = new
					// BigDecimal(this.imp_int_tc
					// .floatValue() / 100).setScale(precision,
					// BigDecimal.ROUND_UP);
					//
					// BigDecimal factorBonificacion = new BigDecimal(
					// new BigDecimal(this.bonificacion)
					// .divide(new BigDecimal(100), 4,
					// BigDecimal.ROUND_UP).toString());
					//
					// bonificacionFuncion = totalFuncion.multiply(
					// factorBonificacion).setScale(precision,
					// BigDecimal.ROUND_UP);
					//
					// totalFuncion =
					// totalFuncion.subtract(bonificacionFuncion);
					//
					// divisor = new BigDecimal(factorUno.add(
					// factorImpIntTc.add(new BigDecimal(1))).toString());
					// divisor.setScale(precision, BigDecimal.ROUND_UP);
					//
					// totalFuncion = totalFuncion.divide(divisor, precision,
					// BigDecimal.ROUND_UP);
					//
					// this.ivauno = (totalFuncion.multiply(factorUno));
					//
					// this.ivauno = this.ivauno.setScale(precision,
					// BigDecimal.ROUND_UP);
					//
					// if (this.imp_int_tc.floatValue() > 0) {
					// this.impuestosinternos = new BigDecimal(this.totalaux);
					// this.impuestosinternos = this.impuestosinternos
					// .subtract(this.ivauno.add(totalFuncion));
					// }
					//
					// // TODO: revisar iva2 (se calcula a partir del total
					// ????)
					// // this.ivados =
					// totalFuncion.subtract(totalFuncion.divide(
					// // factorDos, precision, BigDecimal.ROUND_UP));
					//
					// this.totalnogravado = (tngaux.subtract(tngaux
					// .multiply(factorBonificacion)).setScale(precision,
					// BigDecimal.ROUND_UP)).toString();
					//
					// this.netogravado = totalFuncion.setScale(precision,
					// BigDecimal.ROUND_UP).toString();
					// this.totalfactura = new BigDecimal(this.netogravado)
					// .setScale(precision, BigDecimal.ROUND_UP);
					// this.totalfactura = this.totalfactura.add(this.ivauno)
					// .setScale(precision, BigDecimal.ROUND_UP);
					// this.totalfactura = this.totalfactura.add(this.ivados)
					// .setScale(precision, BigDecimal.ROUND_UP);
					// this.total = this.totalfactura.toString();
					// this.totalfactura = this.totalfactura.add(
					// new BigDecimal(this.totalnogravado)).setScale(
					// precision, BigDecimal.ROUND_UP);
					//
					// } else if (this.discrimina.equalsIgnoreCase("S")) {
					//
					// log.info("DISCRIMINA");
					//
					// if (this.mueveStock.equalsIgnoreCase("S")) {
					// this.netogravadoaux = this.totalDebe.toString();
					// }
					//
					// BigDecimal totalFuncion = new BigDecimal(
					// this.netogravadoaux);
					// BigDecimal factorUno = new
					// BigDecimal(this.porcentajeivauno
					// .floatValue() / 100);
					// BigDecimal factorDos = new
					// BigDecimal(this.porcentajeivados
					// .floatValue() / 100);
					//
					// BigDecimal factorBonificacion = new BigDecimal(
					// this.bonificacion).divide(new BigDecimal(100),
					// precision, BigDecimal.ROUND_UP);
					//
					// bonificacionFuncion = totalFuncion.multiply(
					// factorBonificacion).setScale(precision,
					// BigDecimal.ROUND_UP);
					//
					// totalFuncion =
					// totalFuncion.subtract(bonificacionFuncion);
					// totalFuncion =
					// totalFuncion.add(recargoFuncion).setScale(2);
					//
					// this.totalnogravado = (tngaux.subtract(tngaux
					// .multiply(factorBonificacion)).setScale(precision,
					// BigDecimal.ROUND_UP)).toString();
					//
					// this.netogravado = totalFuncion.toString();
					//
					// this.ivauno = totalFuncion.multiply(factorUno).setScale(
					// precision, BigDecimal.ROUND_UP);
					// this.ivados = totalFuncion.multiply(factorDos).setScale(
					// precision, BigDecimal.ROUND_UP);
					//
					// this.total = (totalFuncion
					// .add(this.ivauno.add(this.ivados))).toString();
					// this.totalfactura = new BigDecimal(this.totalnogravado)
					// .add(new BigDecimal(this.total));
					// this.totalfactura = this.totalfactura
					// .add(this.impuestosinternos);
					// this.totalfactura = this.totalfactura
					// .add(this.otrosimpuestos);
					//

					// 20110622 - EJV - Factuaracion FE-CF-MA <--

				}
			}

		} catch (Exception e) {
			log.error("calculaTotalesDocumento(): " + e);
		}

		if (!this.confirmar.equals("") || !this.calcular.equals("")) {
			this.mensaje = salida;
		}

		return fOk;
	}

	public String getReadOnly(String campo) {
		String readonly = "readonly";
		if (this.mueveStock.equalsIgnoreCase("N")) {
			if (campo.equalsIgnoreCase("total")
					&& this.discrimina.equalsIgnoreCase("N"))
				readonly = "";
			else if (campo.equalsIgnoreCase("netogravado")
					&& this.discrimina.equalsIgnoreCase("S"))
				readonly = "";
		}
		return readonly;
	}

	public boolean isNecesario(String valor) {
		boolean isnecesario = false;

		try {

			if (Double.parseDouble(valor) > 0) {
				isnecesario = true;
			}

		} catch (Exception e) {
			log.error("isNecesario: " + e);
		}

		return isnecesario;
	}

	public String getConfirmar() {
		return confirmar;
	}

	public void setConfirmar(String confirmar) {
		this.confirmar = confirmar;
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

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public BigDecimal getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(BigDecimal idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getTipocliente() {
		return tipocliente;
	}

	public void setTipocliente(String tipocliente) {
		this.tipocliente = tipocliente;
	}

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
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

	public String getLabelTipocliente() {
		if (this.tipocliente.equalsIgnoreCase("C"))
			labelTipocliente = "CLIENTE CTA. CTE.";
		else if (this.tipocliente.equalsIgnoreCase("A"))
			labelTipocliente = "CLIENTE CASUAL (ANEXO) ";
		return labelTipocliente;
	}

	public void setLabelTipocliente(String labelTipocliente) {
		this.labelTipocliente = labelTipocliente;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public String getConfirma_tipocliente() {
		return confirma_tipocliente;
	}

	public void setConfirma_tipocliente(String confirma_tipocliente) {
		this.confirma_tipocliente = confirma_tipocliente;
	}

	public String getTipoclienteanterior() {
		return tipoclienteanterior;
	}

	public void setTipoclienteanterior(String tipoclienteanterior) {
		this.tipoclienteanterior = tipoclienteanterior;
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

	public String getComprob() {
		return comprob;
	}

	public void setComprob(String comprob) {
		this.comprob = comprob;
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

	public BigDecimal getTipocomp() {
		return tipocomp;
	}

	public void setTipocomp(BigDecimal tipocomp) {
		this.tipocomp = tipocomp;
	}

	public List getListClientesTipoComp() {
		return listClientesTipoComp;
	}

	public void setListClientesTipoComp(List listClientesTipoComp) {
		this.listClientesTipoComp = listClientesTipoComp;
	}

	public BigDecimal getIdtipoiva() {
		return idtipoiva;
	}

	public void setIdtipoiva(BigDecimal idtipoiva) {
		this.idtipoiva = idtipoiva;
	}

	public String getTipoiva() {
		return tipoiva;
	}

	public void setTipoiva(String tipoiva) {
		this.tipoiva = tipoiva;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public BigDecimal getIdcondicion() {
		return idcondicion;
	}

	public void setIdcondicion(BigDecimal idcondicion) {
		this.idcondicion = idcondicion;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public BigDecimal getIddomicilio() {
		return iddomicilio;
	}

	public void setIddomicilio(BigDecimal iddomicilio) {
		this.iddomicilio = iddomicilio;
	}

	public String getSucursalcliente() {
		return sucursalcliente;
	}

	public void setSucursalcliente(String sucursalcliente) {
		this.sucursalcliente = sucursalcliente;
	}

	public String getLovClienteAnexo() {

		if (this.tipocliente.equalsIgnoreCase("C"))
			lovClienteAnexo = "lov_clientes_ingmov.jsp";
		else
			lovClienteAnexo = "lov_clientesAnexov.jsp";

		return lovClienteAnexo;
	}

	public String getMueveStock() {
		return mueveStock;
	}

	public void setMueveStock(String mueveStock) {
		this.mueveStock = mueveStock;
	}

	public BigDecimal getImp_int_tc() {
		return imp_int_tc;
	}

	public void setImp_int_tc(BigDecimal imp_int_tc) {
		this.imp_int_tc = imp_int_tc;
	}

	public BigDecimal getIvados() {
		return ivados;
	}

	public void setIvados(BigDecimal ivados) {
		this.ivados = ivados;
	}

	public BigDecimal getIvauno() {
		return ivauno;
	}

	public void setIvauno(BigDecimal ivauno) {
		this.ivauno = ivauno;
	}

	public String getNetogravado() {
		return netogravado;
	}

	public void setNetogravado(String netogravado) {
		this.netogravado = netogravado;
	}

	public BigDecimal getOtrosimpuestos() {
		return otrosimpuestos;
	}

	public void setOtrosimpuestos(BigDecimal otrosimpuestos) {
		this.otrosimpuestos = otrosimpuestos;
	}

	public BigDecimal getPorcentajeivados() {
		return porcentajeivados;
	}

	public void setPorcentajeivados(BigDecimal porcentajeivados) {
		this.porcentajeivados = porcentajeivados;
	}

	public BigDecimal getPorcentajeivauno() {
		return porcentajeivauno;
	}

	public void setPorcentajeivauno(BigDecimal porcentajeivauno) {
		this.porcentajeivauno = porcentajeivauno;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public BigDecimal getTotalfactura() {
		return totalfactura;
	}

	public void setTotalfactura(BigDecimal totalfactura) {
		this.totalfactura = totalfactura;
	}

	public String getTotalnogravado() {
		return totalnogravado;
	}

	public void setTotalnogravado(String totalnogravado) {
		this.totalnogravado = totalnogravado;
	}

	public void setLovClienteAnexo(String lovClienteAnexo) {
		this.lovClienteAnexo = lovClienteAnexo;
	}

	public String getBonificacion() {
		return bonificacion;
	}

	public void setBonificacion(String bonificacion) {
		this.bonificacion = bonificacion;
	}

	public String getRecargo() {
		return recargo;
	}

	public void setRecargo(String recargo) {
		this.recargo = recargo;
	}

	public String getDiscrimina() {
		return discrimina;
	}

	public void setDiscrimina(String discrimina) {
		this.discrimina = discrimina;
	}

	public List getListClientesTipoIva() {
		return listClientesTipoIva;
	}

	public void setListClientesTipoIva(List listClientesTipoIva) {
		this.listClientesTipoIva = listClientesTipoIva;
	}

	public BigDecimal getImpuestosinternos() {
		return impuestosinternos;
	}

	public void setImpuestosinternos(BigDecimal impuestosinternos) {
		this.impuestosinternos = impuestosinternos;
	}

	public String getNetogravadoaux() {
		return netogravadoaux;
	}

	public void setNetogravadoaux(String netogravadoaux) {
		this.netogravadoaux = netogravadoaux;
	}

	public String getTotalaux() {
		return totalaux;
	}

	public void setTotalaux(String totalaux) {
		this.totalaux = totalaux;
	}

	public String getCalcular() {
		return calcular;
	}

	public void setCalcular(String calcular) {
		this.calcular = calcular;
	}

	public String getTotalnogravadoaux() {
		return totalnogravadoaux;
	}

	public void setTotalnogravadoaux(String totalnogravadoaux) {
		this.totalnogravadoaux = totalnogravadoaux;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getCuentaiva() {
		return cuentaiva;
	}

	public void setCuentaiva(String cuentaiva) {
		this.cuentaiva = cuentaiva;
	}

	public String getCuentaivanoinscripto() {
		return cuentaivanoinscripto;
	}

	public void setCuentaivanoinscripto(String cuentaivanoinscripto) {
		this.cuentaivanoinscripto = cuentaivanoinscripto;
	}

	public String getCuentanetoexento() {
		return cuentanetoexento;
	}

	public void setCuentanetoexento(String cuentanetoexento) {
		this.cuentanetoexento = cuentanetoexento;
	}

	public String getCuentanetogravado() {
		return cuentanetogravado;
	}

	public void setCuentanetogravado(String cuentanetogravado) {
		this.cuentanetogravado = cuentanetogravado;
	}

	public String getCuentatotal() {
		return cuentatotal;
	}

	public void setCuentatotal(String cuentatotal) {
		this.cuentatotal = cuentatotal;
	}

	public boolean isIniciaCobranza() {
		return iniciaCobranza;
	}

	public void setIniciaCobranza(boolean iniciaCobranza) {
		this.iniciaCobranza = iniciaCobranza;
	}

	// 20110628 - EJV - Factuaracion FE-CF-MA -->

	public String getCuentaiibb() {
		return cuentaiibb;
	}

	public void setCuentaiibb(String cuentaiibb) {
		this.cuentaiibb = cuentaiibb;
	}

	public BigDecimal getPorcentajeiibb() {
		return porcentajeiibb;
	}

	public void setPorcentajeiibb(BigDecimal porcentajeiibb) {
		this.porcentajeiibb = porcentajeiibb;
	}

	public BigDecimal getPercepcioniibb() {
		return percepcioniibb;
	}

	public void setPercepcioniibb(BigDecimal percepcioniibb) {
		this.percepcioniibb = percepcioniibb;
	}

	// <--

	// 20110929 - EJV - mantis 771 -->

	public String getNrotarjeta() {
		return nrotarjeta;
	}

	public void setNrotarjeta(String nrotarjeta) {
		this.nrotarjeta = nrotarjeta;
	}

	public BigDecimal getIdtarjeta() {
		return idtarjeta;
	}

	public void setIdtarjeta(BigDecimal idtarjeta) {
		this.idtarjeta = idtarjeta;
	}

	public String getTarjetacredito() {
		return tarjetacredito;
	}

	public void setTarjetacredito(String tarjetacredito) {
		this.tarjetacredito = tarjetacredito;
	}

	public int getCuotas() {
		return cuotas;
	}

	public void setCuotas(int cuotas) {
		this.cuotas = cuotas;
	}

	public BigDecimal getIdclub() {
		return idclub;
	}

	public void setIdclub(BigDecimal idclub) {
		this.idclub = idclub;
	}

	// <--

	// 20121005 - EJV - Mantis 882 -->

	public BigDecimal getIdmotivonc() {
		return idmotivonc;
	}

	public void setIdmotivonc(BigDecimal idmotivonc) {
		this.idmotivonc = idmotivonc;
	}

	public String getMotivonc() {
		return motivonc;
	}

	public void setMotivonc(String motivonc) {
		this.motivonc = motivonc;
	}

	// <--

}
