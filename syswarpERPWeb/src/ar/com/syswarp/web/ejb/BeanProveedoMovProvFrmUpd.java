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

public class BeanProveedoMovProvFrmUpd implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProveedoMovProvFrmUpd.class);

	private String validar = "";

	private BigDecimal nrointerno = BigDecimal.valueOf(-1);

	private BigDecimal idproveedor = BigDecimal.valueOf(0);

	private String dproveedor = "";

	// HARCODE - POR AHORA SOLO SE MODIFICAN COMPROBANTES SIN STOCK
	private String stock_fact = "N";

	private String stock_fact_actual = "";

	private String fechamov = "";

	private String sucursal = "";

	private String comprob = "";

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

	// HARCODE
	private String accion = "modificacion";

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

	private boolean primeraCarga = true;

	/**/

	public BeanProveedoMovProvFrmUpd() {
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
			// Proveedores proveedoMovProv = Common.getProveedores();
			if (this.accion.equalsIgnoreCase("modificacion")) {
				// TODO: Flag de session (flagMovAplicadoUpd)para evitar
				// ejecutar
				// nuevamente la
				// ejecucion de un moviemiento, se le asigna un valor en el
				// momento que se genero correctamente el movimiento y
				// redirecciono a impresion de asientos.
				session.removeAttribute("flagMovAplicadoUpd");
				dispatcher = request
						.getRequestDispatcher("proveedoMovProvAsientoFrmUpd.jsp");
				dispatcher.forward(request, response);

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
				this.fechamov = Common.setObjectToStrOrTime(
						Timestamp.valueOf(uCampos[2]), "JSTsToStr").toString();

				this.sucursal = uCampos[3];
				this.comprob = uCampos[4];
				this.tipomov = BigDecimal.valueOf(Long.parseLong(uCampos[5]));
				this.tipomovs = uCampos[6].substring(uCampos[6].length() - 1);
				this.observacionesContables = uCampos[17];

				this.importe = Double.valueOf(uCampos[7]);
				this.saldo = Double.valueOf(uCampos[8]);
				this.idcondicionpago = BigDecimal.valueOf(Long
						.parseLong(uCampos[9]));
				this.fecha_subd = Timestamp.valueOf(uCampos[10]);
				this.fecha_subdStr = Common.setObjectToStrOrTime(fecha_subd,
						"JSTsToStr").toString();

				this.retoque = BigDecimal.valueOf(Long.parseLong(uCampos[11]));
				this.fechavto = Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[12]), "JSDateToStr")
						.toString();

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

	public void getImportesComprobante() {

		log.info("Inicia getImportesComprobante ");

		List listaImportes = new ArrayList();
		Iterator iterImportes;
		try {

			Proveedores proveedor = Common.getProveedores();
			listaImportes = proveedor.getProveedoContProvTotalImportes(
					this.nrointerno, this.idempresa);

			iterImportes = listaImportes.iterator();

			while (iterImportes.hasNext()) {

				String[] datosImporte = (String[]) iterImportes.next();

				switch (datosImporte[2].trim().charAt(0)) {
				case 'E':
					this.netoExento = datosImporte[1];
					break;
				case 'G':
					this.netoGravado = datosImporte[1];
					break;
				case 'I':
					this.iva = Common.getNumeroFormateado(Double
							.parseDouble(datosImporte[1]), 10, 2);
					break;
				case 'T':
					this.importe = new Double(datosImporte[1]);
					break;
				case 'R':
					/**
					 * @COMENTARIO: 20081030: TODO Controlar, por algun motivo
					 *              hasta / el dia de la fecha no asignaba valor
					 */
					this.percepcionIva = Common.getNumeroFormateado(Double
							.parseDouble(datosImporte[1]), 10, 2);
					break;

				default:
					break;
				}

			}

		} catch (Exception e) {
			log.error("getImportesComprobante():" + e);
		}

	}

	public void inicializarDatosProv() {
		try {

			Proveedores proveedor = Common.getProveedores();

			if (this.idproveedor != null && this.idproveedor.longValue() > 0) {
				log.info("idproveedor: " + this.idproveedor);
				List listProveedor = proveedor.getProveedoProveedPK(
						this.idproveedor, this.idempresa);
				if (listProveedor.iterator().hasNext()) {

					String[] datosProveedor = (String[]) listProveedor
							.iterator().next();

					if (datosProveedor[17] != null
							&& !datosProveedor[17].equals("0"))
						this.readonlyIva = "";

					if (datosProveedor[18] != null
							&& !datosProveedor[18].equals("0"))
						this.readonlyRetIva = "";

					this.dproveedor = datosProveedor[1];

					// this.stock_fact = datosProveedor[33];
					// SOLO PARA MOSTRAR LA CONDICION ACTUAL DEL PROVEEDOR
					this.stock_fact_actual = datosProveedor[33];

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
					if (Common.esPorcentaje(porcentaje)) {
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
		boolean fOk = false;
		String mensajeCI = "";
		try {

			if (!Common.esNumerico(this.porcentajeBonificacion)) {
				mensajeCI = "% Bonificaci�n Incorrecto.";
			} else if (!Common.esNumerico(this.bonificacion)) {
				mensajeCI = "Bonificaci�n Incorrecta.";
			} else if (!Common.esNumerico(this.netoGravado)) {
				mensajeCI = "Neto Gravado Incorrecto.";
			} else if (!Common.esNumerico(this.provPorcNormalIva)) {
				mensajeCI = "% Iva Incorrecto.";
			} else if (!Common.esNumerico(this.provPorcEspecialIvaPercep)) {
				mensajeCI = "% Percepci�n Iva Incorrecto.";
			} else if (!Common.esNumerico(this.iva)) {
				mensajeCI = "Iva Incorrecto.";
			} else if (!Common.esNumerico(this.percepcionIva)) {
				mensajeCI = "Percepci�n Iva Incorrecto.";
			} else if (!Common.esNumerico(this.netoExento)) {
				mensajeCI = "Neto Exento Incorrecto.";
			} else {

				log.info("calcularImportes(): NUMERICOS");

				if (!Common.esPorcentaje(this.porcentajeBonificacion)) {
					mensajeCI = " % (2) Bonificaci�n Incorrecto.";
				} else if (!Common.esPorcentaje(this.provPorcNormalIva)) {
					mensajeCI = " % (2) Iva Incorrecto.";
				} else if (!Common.esPorcentaje(this.provPorcEspecialIvaPercep)) {
					mensajeCI = " % (2) Percepci�n Incorrecto.";
				} else {
					log.info("calcularImportes(): %'s OK");
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

			// Solo muestra mensaje en el caso que el usuario requiere calculo o
			// envia el formulario.
			if (!this.validar.equals("") || !this.calcular.equals(""))
				this.mensaje = mensajeCI;

		} catch (Exception e) {
			// TODO: handle exception
			log.error("calcularImportes():" + e);
		}
		return fOk;
	}

	public boolean ejecutarValidacion() {

		try {

			this.inicializarDatosProv();

			Calendar cal = new GregorianCalendar();
			Proveedores proveedor = null;
			java.sql.Date fvto = (java.sql.Date) Common.setObjectToStrOrTime(
					this.fechavto, "StrToJSDate");
			Timestamp fmov = (Timestamp) Common.setObjectToStrOrTime(
					this.fechamov, "StrToJSTs");

			this.htCP = Common
					.getHashEntidad("getProveedoCondicioAll", new Class[] {
							long.class, long.class, BigDecimal.class },
							new Object[] { new Long(250), new Long(0),
									this.idempresa });

			if (!this.volver.equalsIgnoreCase("")) {
				session.removeAttribute("htArticulosConfirmados");
				session.removeAttribute("subTotalGravado");
				response.sendRedirect("proveedoMovProvBuscar.jsp?idproveedor="
						+ this.idproveedor + "&dproveedor=" + this.dproveedor);
				return true;
			}

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					proveedor = Common.getProveedores();

					// this.inicializarDatosProv();
					// 1. nulidad de campos
					if (idproveedor == null || idproveedor.longValue() <= 0) {
						this.mensaje = "Es necesario seleccionar un proveedor. ";
						return false;
					}

					if (fmov == null) {
						this.mensaje = "No se puede dejar vacio el campo fecha movimiento. ";
						return false;
					}
					if (sucursal == null) {
						this.mensaje = "No se puede dejar vacio el campo sucursal ";
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

					if (!Common.esEntero(sucursal)) {
						this.mensaje = "Ingrese valores numericos v�lidos para el campo sucursal. ";
						return false;
					}
					if (!Common.esEntero(comprob)) {
						this.mensaje = "Ingrese valores numericos v�lidos para el campo comprobante. ";
						return false;
					}

					if (tipomovs.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo letra comprobante.  ";
						return false;
					}

					if (this.stock_fact.equalsIgnoreCase("S")) {
						if (this.session.getAttribute("htArticulosConfirmados") == null) {
							this.mensaje = "Debe ingresar algun articulo en stock. ";
							return false;
						}
					}

					if (fmov.compareTo(fvto) > 0) {
						this.mensaje = "Fecha de movimiento no puede ser mayor a fecha vencimiento.";
						return false;
					}

					if (proveedor.getExisteDocumentoUpd(this.nrointerno,
							this.idproveedor, new BigDecimal(this.sucursal),
							new BigDecimal(this.comprob), this.tipomov,
							this.idempresa)) {
						this.mensaje = "El Nro. de comprobante ya fue ingresado para este proveedor.";
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
				}

				this.ejecutarSentenciaDML();

			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					// getDatosProveedoMovProv();
				}
				this.inicializarDatosProv();

			}

			if (this.primeraCarga) {
				this.getImportesComprobante();
				this.getDatosProveedoMovProv();
			} else
				this.calcularImportes();

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
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

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getComprob() {
		return comprob;
	}

	public void setComprob(String comprob) {
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

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public String getObservacionesContables() {
		return observacionesContables;
	}

	public void setObservacionesContables(String observacionesContables) {
		this.observacionesContables = observacionesContables;
	}

}
