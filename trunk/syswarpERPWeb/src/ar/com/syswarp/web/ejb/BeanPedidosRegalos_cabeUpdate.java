/* 
 javabean para la entidad (Formulario): pedidos_cabe
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 02 09:51:29 GMT-03:00 2007 
 
 Para manejar la pagina: pedidos_cabeFrm.jsp
 
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

public class BeanPedidosRegalos_cabeUpdate implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanPedidosRegalos_cabeUpdate.class);

	private HttpSession session;

	private String validar = "";

	private BigDecimal idpedido_regalos_cabe = new BigDecimal(-1);

	private String idpedido_regalos_padre = "";

	private BigDecimal idestado = new BigDecimal(1);

	private BigDecimal idcliente = new BigDecimal(-1);

	private BigDecimal idempresa = new BigDecimal(-1);

	private String cliente = "";

	private BigDecimal idsucursal = new BigDecimal(-1);

	private String sucursal = "";

	private BigDecimal iddomicilio = new BigDecimal(-1);

	private String nombre_suc = "";

	private String fechapedido = Common.initObjectTimeStr();

	private BigDecimal idcondicion = new BigDecimal(-1);

	private String condicion = "";

	// EJV - 20091030
	// idvendedor Seteado a -1 siempre. No se asocia vendedor en Regalos
	// empresariales.
	// El valor es persistido en null (en la capa cliente se maneja con valor
	// -1)
	private BigDecimal idvendedor = new BigDecimal(-1);

	private String vendedor = "";

	private String credcate = "";

	private String preferencia = "";

	private BigDecimal idexpreso = new BigDecimal(-1);

	private String expreso = "";

	private BigDecimal comision = new BigDecimal(0);

	private BigDecimal ordencompra = new BigDecimal(0);

	private String obsarmado = "";

	private String obsentrega = "";

	private String recargo1 = "0";

	private String recargo2 = "0";

	private String recargo3 = "0";

	private String recargo4 = "0";

	private String bonific1 = "0";

	private String bonific2 = "0";

	private String bonific3 = "0";

	private BigDecimal totalFleteEstimado = new BigDecimal(0);

	private BigDecimal totalgeneral = new BigDecimal(0);

	private BigDecimal totalGeneralIva = new BigDecimal(0);

	private BigDecimal totalIva = new BigDecimal(0);

	private BigDecimal idlista = new BigDecimal(-1);

	private String lista = "";

	private BigDecimal idmoneda = new BigDecimal(-1);

	private String moneda = "";

	private BigDecimal idtipoiva = new BigDecimal(-1);

	private Hashtable htDepositos = null;

	private String tipoiva = "";

	private String cotizacion = "0";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	// 3 - Cuentas Articulos
	private BigDecimal totalDebe = new BigDecimal(0);

	// 3 - Cuentas Imputacion
	private BigDecimal totalHaber = new BigDecimal(0);

	private boolean primeraCarga = true;

	// private Hashtable htDepositos = Common
	// .getHashEntidad("getProveedoStockDepositosAll");

	private List listPrioridades = new ArrayList();

	private BigDecimal idprioridad = new BigDecimal(1);

	/**/

	private List listaExpresos = new ArrayList();

	private List listaCondicion = new ArrayList();

	private List listaMonedas = new ArrayList();

	private List listaTipoIva = new ArrayList();

	private List listaDomicilios = new ArrayList();

	private List listaVendedores = new ArrayList();

	private List listaListaPrecios = new ArrayList();

	private List listaTarjetasCredito = new ArrayList();

	private List listaPorcDescuento = new ArrayList();

	private List listaMotivosDescuento = new ArrayList();

	private List listaCuotas = new ArrayList();

	private String datosJSDomi = "";

	private String telefonos = "";

	private BigDecimal idtarjeta = new BigDecimal(-1);

	private BigDecimal idzona = new BigDecimal(-1);

	private String zona = "";

	private BigDecimal cuotas = new BigDecimal(0);

	private String[] keyHashArticulo = null;

	private String[] iddescuento_suge = null;

	private String[] porcdesc_suge = null;

	private String[] iddescuento_apli = null;

	private String[] porcdesc_apli = null;

	private String[] idmotivodescuento = null;

	// iddescuento_suge, iddescuento_apli, porcdesc_suge, porcdesc_apli

	private String origenpedido = "";

	private String tipopedido = "N";

	/**/

	private BigDecimal idcampacabe = new BigDecimal(-1);

	// Resultado correspondiente al llamado efectuado al socio, desde la
	// campa�a
	// activa
	private BigDecimal idresultado = new BigDecimal(-1);

	private String accionGTM = "";

	private String campacabe = "";

	private BigDecimal idcategoriasocio = new BigDecimal(-1);

	private String categoriasocio = "";

	private BigDecimal adidesc = new BigDecimal(0.00);

	private String transformacion = "";

	public BeanPedidosRegalos_cabeUpdate() {
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

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public void ejecutarSentenciaDML() {
		try {
			Clientes pedidos_cabe = Common.getClientes();

			Hashtable htArticulos = (Hashtable) session
					.getAttribute("htArticulosInOutOK");

			String[] resultado = new String[] { "", "" };

			BigDecimal idpedidoregalospadre = Common.setNotNull(
					this.idpedido_regalos_padre).equals("") ? null
					: new BigDecimal(this.idpedido_regalos_padre);

			resultado = pedidos_cabe.pedidosRegalosUpdate(
					this.idpedido_regalos_cabe, GeneralBean.setNull(
							this.idcampacabe, 1), idpedidoregalospadre,
					this.idestado, this.idcliente, this.idsucursal,
					this.iddomicilio, (Timestamp) Common.setObjectToStrOrTime(
							this.fechapedido, "StrToJSTs"), this.idcondicion,
					GeneralBean.setNull(this.idvendedor, 1), this.idexpreso,
					this.comision, this.ordencompra, this.obsarmado,
					this.obsentrega, new BigDecimal(this.recargo1),
					new BigDecimal(this.recargo2),
					new BigDecimal(this.recargo3),
					new BigDecimal(this.recargo4),
					new BigDecimal(this.bonific1),
					new BigDecimal(this.bonific2),
					new BigDecimal(this.bonific3), this.idlista, this.idmoneda,
					new BigDecimal(this.cotizacion), this.idtipoiva,
					this.totalGeneralIva, this.idprioridad, this.idzona,
					this.idtarjeta, this.cuotas, this.origenpedido,
					this.totalgeneral, this.tipopedido, htArticulos,
					this.transformacion, this.usuarioalt, this.idempresa, null);

			if (Common.esNumerico(resultado[0]) && resultado[1].equals("")) {
				this.mensaje = "Se genero el pedido nro.: " + resultado[0]
						+ ".";
				this.idpedido_regalos_cabe = new BigDecimal(resultado[0]);
			} else if (Common.esNumerico(resultado[0])
					&& !resultado[1].equals("")) {
				this.mensaje = "Se genero el pedido nro.: " + resultado[0]
						+ ".<br>ATENCION: hay faltantes de stock para: "
						+ resultado[1].replaceAll("#", "<br>");
				this.idpedido_regalos_cabe = new BigDecimal(resultado[0]);

			} else
				this.mensaje = resultado[0];

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			Clientes clientes = Common.getClientes();
			boolean calculoOk = true;

			// 20101111
			// this.llenarListas(clientes);

			if (isPrimeraCarga()) {

				session.removeAttribute("htArticulosInOutOK");

				this.getDatosClientePedido();

				this.getDatosDetallePedido();

				this.getTotalesAsiento((Hashtable) session
						.getAttribute("htArticulosInOutOK"), 11);

				// 20101125 - EJV
				// if (!Common.esEntero(this.idpedido_regalos_padre)) {
				// this.transformacion = "S";
				// } else {
				// this.transformacion = "N";
				// }

			} else {

				if (this.accion.equalsIgnoreCase("limpiardetalle")) {
					session.removeAttribute("htArticulosInOutOK");
					this.bonific1 = this.bonific2 = this.bonific3 = "0";
					this.recargo1 = this.recargo2 = this.recargo3 = this.recargo4 = "0";
				}

				if (session.getAttribute("htArticulosInOutOK") != null)
					this.asignarValidar((Hashtable) session
							.getAttribute("htArticulosInOutOK"));

				// 20101111- EJV - No deberia poder modificarse el cliente
				// asociado al pedido, en ese caso corresponderia anular el
				// pedido.
				// if (this.accion.equalsIgnoreCase("recarga")) {
				// // reiniciaIds();
				// getDatosCliente(clientes);
				// session.setAttribute("htArticulosInOutOK", null);
				//
				// }

				// 20090922 - EJV
				this.getTotalesAsiento((Hashtable) session
						.getAttribute("htArticulosInOutOK"), 11);

			}

			this.llenarListas(clientes);

			if (this.idcondicion.longValue() == 5) {

				if (this.listaTarjetasCredito != null
						&& !this.listaTarjetasCredito.isEmpty()
						&& this.listaTarjetasCredito.size() > 0) {

					java.sql.Date hoy = new java.sql.Date(Calendar
							.getInstance().getTimeInMillis());

					Iterator it = this.listaTarjetasCredito.iterator();
					while (it.hasNext()) {

						String datos[] = (String[]) it.next();

						if (Common.setNotNull(datos[12]).equalsIgnoreCase("S")
								&& java.sql.Date.valueOf(datos[9]).after(hoy)) {
							this.idtarjeta = new BigDecimal(datos[0]);
							break;
						}

					}

				}

				if (this.cuotas.intValue() == 0) {
					this.cuotas = new BigDecimal(1);
				}

			} else
				// log.info("entrooooooooooooooo");
				this.idtarjeta = new BigDecimal(-1);

			if (!this.validar.equalsIgnoreCase("")) {

				if (Common.setNotNull(this.transformacion).equals("")) {
					this.mensaje = "Seleccione si el producto final del pedido requiere transformacion. ";
					return false;
				}

				if (Common.setNotNull(this.tipopedido).equals("")) {
					this.mensaje = "Seleccione tipo de pedido. ";
					return false;
				}

				if (idcliente.longValue() < 1) {
					this.mensaje = "No se puede dejar vacio el campo Cliente. ";
					return false;
				}

				if (Common.setNotNull(this.fechapedido).equals("")) {
					this.mensaje = "No se puede dejar vacio el campo Fecha Pedido. ";
					return false;
				}

				if (idprioridad.longValue() < 1) {
					this.mensaje = "Es necesario seleccionar Prioridad del Pedido. ";
					return false;
				}

				if (this.iddomicilio.longValue() < 1) {
					this.mensaje = "No se puede dejar vacio el campo Domicilio. ";
					return false;
				}

				if (idcondicion.longValue() < 1) {
					this.mensaje = "No se puede dejar vacio el campo Condicion. ";
					return false;
				}

				// EJV - 20091030
				// Peiddos Regalos Empresarios no lleva vendedor asociado
				// if (idvendedor.longValue() < 1) {
				// this.mensaje = "No se puede dejar vacio el campo Vendedor. ";
				// return false;
				// }

				if (!Common.esNumerico(cotizacion)) {
					this.mensaje = "Cotizacion debe ser un valor numerico.";
					return false;
				}

				if (this.idcondicion.longValue() == 5) {

					if (this.idtarjeta.longValue() < 1) {
						this.mensaje = "Seleccione tarjeta de crédito.";
						return false;
					}

					if (cuotas.longValue() <= 0) {
						this.mensaje = "Seleccione cantidad de cuotas. ";
						return false;
					}

				}

				if (session.getAttribute("htArticulosInOutOK") == null
						|| ((Hashtable) session
								.getAttribute("htArticulosInOutOK")).isEmpty()) {
					this.mensaje = "Ingrese al menos un articulo que afecte al movimiento.";
					return false;
				}

				if (!calculoOk) {
					log.warn("FALLA CALCULO");
					return false;
				}

				if (!this.asignarValidar((Hashtable) session
						.getAttribute("htArticulosInOutOK"))) {

					return false;
				}

				if (this.obsarmado.length() > 999) {
					this.mensaje = "Observaciones armado no debe superar los 1000 caracteres.";
					return false;
				}

				if (this.obsentrega.length() > 999) {
					this.mensaje = "Observaciones entrega no debe superar los 1000 caracteres.";
					return false;
				}

				this.ejecutarSentenciaDML();

			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private void llenarListas(Clientes clientes) {

		/**/
		long cuantos = 100;
		long desde = 0;
		try {
			Clientes pedidos_cabe = Common.getClientes();
			if (this.accion.equalsIgnoreCase("recarga"))
				reiniciaIds();
			// Cambio agregado por CEP, validar.
			// getDatosCliente(pedidos_cabe);
			// Hashtable htArticulos = null;

			this.listaExpresos = clientes.getClientesexpresosAll(cuantos,
					desde, this.idempresa);

			this.listaCondicion = clientes.getClientescondicioAll(cuantos,
					desde, this.idempresa);

			this.listaMonedas = Common.getGeneral().getGlobalmonedasAll(
					cuantos, desde);

			this.listaTipoIva = clientes.getClientestablaivaAll(cuantos, desde,
					this.idempresa);

			getDomiciliosCliente(clientes);

			this.listaVendedores = clientes.getClientesvendedorAll(cuantos,
					desde, this.idempresa);

			this.listaListaPrecios = clientes.getClienteslistasAll(cuantos,
					desde, this.idempresa);

			this.listaTarjetasCredito = clientes
					.getClienteTarjetasClienteActivas(cuantos, desde,
							this.idcliente, this.idempresa);

			this.listaPorcDescuento = clientes
					.getClientesDescuentosAll(this.idempresa);

			this.listaMotivosDescuento = clientes
					.getPedidosMotivosDescuentosAll(cuantos, desde,
							this.idempresa);

			this.listaCuotas = clientes.getPedidosCuotasAll(cuantos, desde,
					this.idempresa);

			this.listPrioridades = clientes.getPedidosPrioridadesAll(50, 0);

			// No deberia estar aca ... pero ....
			this.htDepositos = Common.getHashEntidad(
					"getProveedoStockDepositosAll", new Class[] { long.class,
							long.class, BigDecimal.class }, new Object[] {
							new Long(250), new Long(0), this.idempresa });

		} catch (Exception e) {
			log.error("llenarListas(): " + e);
		}

		/**/

	}

	private void getDatosClientePedido() {
		try {

			List listaCliente = Common.getClientes()
					.getPedidosRegalosClienteXPedido(
							this.idpedido_regalos_cabe, this.idempresa);

			if (!listaCliente.isEmpty() && listaCliente.size() == 1) {
				
				String[] datos = (String[]) listaCliente.get(0);

				this.idcliente = new BigDecimal(datos[0]);
				this.cliente = datos[0] + " - " + datos[1];
				this.idpedido_regalos_padre = datos[2];
				this.idtipoiva = new BigDecimal(datos[3]);
				this.idcondicion = new BigDecimal(datos[4]);
				this.idmoneda = new BigDecimal(datos[5]);
				this.idlista = new BigDecimal(datos[6]);
				// this.vendedor = datos[28];
				this.credcate = datos[8];
				this.preferencia = datos[11];
				this.idcategoriasocio = new BigDecimal(datos[12]);
				this.categoriasocio = datos[13];
				this.adidesc = new BigDecimal(Common.setNotNull(datos[14])
						.equals("") ? "0" : datos[14]);
				this.iddomicilio = new BigDecimal(datos[15]);
				this.obsentrega = datos[16];
				this.obsarmado = datos[17];
				this.idexpreso = new BigDecimal(datos[18] != null ? datos[18]
						: "-1");
				this.expreso = datos[19];
				this.idzona = new BigDecimal(datos[20] != null ? datos[20]
						: "-1");
				this.zona = datos[21];
				this.telefonos = "Linea: " + datos[22] + " / CEL: " + datos[23];
				this.transformacion = datos[29];
				

			} else {
				log
						.warn("getDatosClientePedido: Imposible recuperar datos cliente:  "
								+ idcliente);
			}

		} catch (Exception e) {
			log.error("getDatosClientePedido(): " + e);
		}
	}

	// 20101111- EJV
	// private void getDatosCliente(Clientes clientes) {
	// try {
	//
	// List listaCliente = clientes.getClientesClientesPK(this.idcliente,
	// this.idempresa);
	// cliente = idcliente + "--" + cliente;
	// if (!listaCliente.isEmpty() && listaCliente.size() == 1) {
	//
	// String[] datos = (String[]) listaCliente.get(0);
	//
	// this.idtipoiva = new BigDecimal(datos[6]);
	// this.idcondicion = new BigDecimal(datos[8]);
	// this.idmoneda = new BigDecimal(datos[14]);
	// this.idlista = new BigDecimal(datos[16]);
	// this.vendedor = datos[28];
	// this.credcate = datos[26];
	// this.preferencia = datos[30];
	// this.idcategoriasocio = new BigDecimal(datos[31]);
	// this.categoriasocio = datos[32];
	// this.adidesc = new BigDecimal(Common.setNotNull(datos[33])
	// .equals("") ? "0" : datos[33]);
	// // TODO: PRUEBA EJV 20081002
	// // getDomiciliosCliente(clientes);
	//
	// } else {
	// log.warn("Imposible recuperar datos cliente:  " + idcliente);
	// }
	//
	// } catch (Exception e) {
	// log.error("getDatosCliente(): " + e);
	// }
	// }

	private void reiniciaIds() {

		this.idprioridad = new BigDecimal(1);
		this.idvendedor = new BigDecimal(-1);
		this.idtipoiva = new BigDecimal(-1);
		this.idcondicion = new BigDecimal(-1);
		this.idmoneda = new BigDecimal(-1);
		this.idlista = new BigDecimal(-1);
		this.iddomicilio = new BigDecimal(-1);
		this.idtarjeta = new BigDecimal(-1);
		this.idzona = new BigDecimal(-1);

	}

	private void getDomiciliosCliente(Clientes clientes) {
		try {
			this.listaDomicilios = clientes.getClientesDomiciliosCliente(2500, 0,
					this.idcliente, this.idempresa);

			if (!this.listaDomicilios.isEmpty()
					&& this.listaDomicilios.size() > 0) {
				Iterator iter = this.listaDomicilios.iterator();
				this.datosJSDomi += "\ttieneDomicilios = true;\n";
				while (iter.hasNext()) {
					String[] datos = (String[]) iter.next();

					// 20081210
					this.datosJSDomi += "\tvar datosDomicilio" + datos[0]
							+ " = new Array("
							+ (datos[21] == null ? "-1" : datos[21]) + ", '"
							+ Common.setNotNull(datos[22]) + "', "
							+ (datos[25] == null ? "-1" : datos[25])
							+ ", 'Linea:" + datos[15] + "', 'CEL:" + datos[16]
							+ "'," + (datos[19] == null ? "-1" : datos[19])
							+ ", '" + Common.setNotNull(datos[20]) + "', '"
							+ Common.setNotNull(datos[40]) + "' );\n";

					if (this.accion.equalsIgnoreCase("recarga")) {

						if (datos[4] == null || datos[21] == null
								|| datos[19] == null) {

							// ***** || datos[25] == null ***** Vendedor ahora
							// esta a nivel
							// precarga, el mismo no tiene validez en la entidad
							// clientesdomicilios

							this.mensaje = "Datos del domicilio mal definidos.";
							log
									.warn("getDomiciliosCliente(): Datos del domicilio mal definidos.");

							// 20081210
							this.idexpreso = new BigDecimal(
									datos[21] != null ? datos[21] : "-1");

							// this.idvendedor = new BigDecimal(
							// datos[25] != null ? datos[25] : "-1");

							this.idzona = new BigDecimal(
									datos[19] != null ? datos[19] : "-1");

							//

						} else if (!datos[4].equalsIgnoreCase("N")) {

							this.iddomicilio = new BigDecimal(datos[0]);
							this.idexpreso = new BigDecimal(
									datos[21] != null ? datos[21] : "-1");
							this.expreso = datos[22];
							// this.idvendedor = new BigDecimal(
							// datos[25] != null ? datos[25] : "-1");

							this.telefonos = "Linea: " + datos[15] + " / CEL: "
									+ datos[16];
							this.idzona = new BigDecimal(
									datos[19] != null ? datos[19] : "-1");
							this.zona = datos[20];
							this.obsentrega = datos[40];

						}
					}

				}
			} else {

				this.telefonos = "";
				this.expreso = "";
				this.idexpreso = new BigDecimal(-1);

			}

		} catch (Exception e) {
			log.error("getDomiciliosCliente(): " + e);
		}

	}

	private void getDatosDetallePedido() {

		try {

			Hashtable htDetalle = new Hashtable();
			List listaArt = Common.getClientes()
					.getPedidosRegalosDetalleXPedido(
							this.idpedido_regalos_cabe, this.idempresa);
			Iterator iterArt = listaArt.iterator();
			while (iterArt.hasNext()) {
				String[] datosArt = (String[]) iterArt.next();

				// 20101203 - EJV - familia 59 - mantis 631
				datosArt[33] = "false";

				htDetalle.put(datosArt[0] + "-" + datosArt[9], datosArt);
			}

			session.setAttribute("htArticulosInOutOK", htDetalle);

		} catch (Exception e) {
			log.error("getDatosDetallePedido(): " + e);
		}

	}

	private BigDecimal getTotalesAsiento(Hashtable ht, int indice) {
		BigDecimal total = new BigDecimal(0);
		Enumeration en;
		// 20091021 - Valor hasta la fecha = 2
		final int ESCALA = 3;
		// BigDecimal.ROUND_CEILING
		final int ROUND = BigDecimal.ROUND_CEILING;
		try {

			if (ht != null) {

				listaTipoIva = Common.getClientes().getClientestablaivaPK(
						this.idtipoiva, this.idempresa);
				if (this.idcliente.compareTo(new BigDecimal(0)) < 1) {

					this.mensaje = "Es necesario seleccionara un cliente para calcular importes.";

				} else if ((listaTipoIva == null || listaTipoIva.isEmpty())
						&& this.idcliente.compareTo(new BigDecimal(0)) != 0) {
					mensaje = "Imposible recuperar datos tipo iva.";
					// fOk = false;
				} else {
					String[] datosTipoIva = (String[]) listaTipoIva.get(0);
					BigDecimal porcIvaCliente = new BigDecimal(datosTipoIva[2]);

					// total por articulos solicitados
					total = total.add(this.totalDebe);

					en = ht.keys();
					while (en.hasMoreElements()) {
						Object clave = en.nextElement();
						String[] datos = (String[]) ht.get(clave);

						// log.info("<|||||||||||||||||||||||||||||>");
						// for (int i = 0; i < datos.length; i++)
						// log.info("DATO " + i + ":" + datos[i]);
						// log.info("<!!!!!!!!!!!!!!!!!!!!!!!!!!!!>");

						total = total.add(new BigDecimal(datos[indice]));

						// 20090825 - EJV
						String tipograv_exen = datos[25];
						// BigDecimal tipoiva_st = new BigDecimal(datos[23]);

						BigDecimal porciva_st = new BigDecimal(0)
								.setScale(ESCALA);
						BigDecimal ivaAplicado = (tipograv_exen
								.equalsIgnoreCase("G") ? porcIvaCliente
								: porciva_st).setScale(ESCALA, ROUND);

						// log.info("tipograv_exen: " + tipograv_exen);

						BigDecimal totalLinea = new BigDecimal(datos[indice])
								.setScale(ESCALA);
						BigDecimal totalLineaConIva = new BigDecimal(0)
								.setScale(ESCALA);
						BigDecimal totalIvaLinea = ((totalLinea
								.multiply(ivaAplicado)).divide(new BigDecimal(
								100))).setScale(ESCALA, ROUND);

						// log.info("totalLinea: " + totalLinea);
						// log.info("totalIvaLinea: " + totalIvaLinea);

						totalLineaConIva = totalLinea.add(totalIvaLinea);

						this.totalgeneral = this.totalgeneral.add(totalLinea);
						this.totalIva = this.totalIva.add(totalIvaLinea);
						this.totalGeneralIva = this.totalGeneralIva
								.add(totalLineaConIva);

						datos[26] = ivaAplicado.toString();
						datos[27] = totalIvaLinea.toString();

						// 20090827 - EJV
						ht.put(clave, datos);

						// log.info("<|-|-|-|-|-|-|-|-|-|-|-|-|-|-|>");
						// for (int i = 0; i < datos.length; i++)
						// log.info("Dato " + i + ":" + datos[i]);
						// log.info("<$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$>");

					}
				}
			}

		} catch (Exception e) {

			log.error("getTotalesAsiento(Hashtable ht, int indice):" + e);

		}
		return total;
	}

	//
	// public boolean calcularImportes() {
	//
	// // TODO: 20090827 - EJV. Ver de eliminar este metodo (calcularImportes),
	// // ya que el mismo quedo obsoleto desde la implementación de iva por
	// // producto.
	//
	// boolean fOk = true;
	// String mensajeCI = "";
	// BigDecimal total = new BigDecimal(0);
	// Clientes pedidos_cabe = Common.getClientes();
	// // List listaTipoIva = null;
	//
	// try {
	//
	// if (this.idcliente.compareTo(new BigDecimal(0)) == 0) {
	// mensajeCI = "Seleccione un cliente para calcular importes.";
	// fOk = false;
	// } else if (!Common.esNumerico(this.bonific1)) {
	// mensajeCI = "Bonificacion 1 incorrecta.";
	// fOk = false;
	// } else if (!Common.esNumerico(this.bonific2)) {
	// mensajeCI = "Bonificacion 2 incorrecta.";
	// fOk = false;
	// } else if (!Common.esNumerico(this.bonific3)) {
	// mensajeCI = "Bonificacion 3 incorrecta.";
	// fOk = false;
	// } else if (!Common.esNumerico(this.recargo1.toString())) {
	// mensajeCI = "Recargo 1 incorrecto.";
	// fOk = false;
	// } else if (!Common.esNumerico(this.recargo2.toString())) {
	// mensajeCI = "Recargo 2 incorrecto.";
	// fOk = false;
	// } else if (!Common.esNumerico(this.recargo3.toString())) {
	// mensajeCI = "Recargo 3 incorrecto.";
	// fOk = false;
	// } else if (!Common.esNumerico(this.recargo4.toString())) {
	// mensajeCI = "Recargo 4 incorrecto.";
	// fOk = false;
	// /*
	// * } else if (session.getAttribute("htArticulosInOutOK") == null
	// * || ((Hashtable) session.getAttribute("htArticulosInOutOK"))
	// * .isEmpty()) { mensajeCI = "Ingrese al menos un articulo que
	// * afecte al movimiento."; fOk = false;
	// */
	// } else {
	//
	// // listaTipoIva = pedidos_cabe.getClientestablaivaPK(
	// // this.idtipoiva, this.idempresa);
	//
	// // if ((listaTipoIva == null || listaTipoIva.isEmpty())
	// // && this.idcliente.compareTo(new BigDecimal(0)) != 0) {
	// // mensajeCI = "Imposible recuperar datos tipo iva.";
	// // fOk = false;
	// // } else {
	//
	// // String[] datosTipoIva = (String[]) listaTipoIva.get(0);
	// // BigDecimal porcentajeIva = new
	// // BigDecimal(datosTipoIva[2]);
	//
	// // total por articulos solicitados
	// total = total.add(this.totalDebe);
	//
	// // + recargos
	// total = total.add(new BigDecimal(this.recargo1));
	// total = total.add(new BigDecimal(this.recargo2));
	// total = total.add(new BigDecimal(this.recargo3));
	// total = total.add(new BigDecimal(this.recargo4));
	//
	// // - bonificaciones
	// total = total.subtract(new BigDecimal(this.bonific1));
	// total = total.subtract(new BigDecimal(this.bonific2));
	// total = total.subtract(new BigDecimal(this.bonific3));
	//
	// // this.totalgeneral = total;
	//
	// // 20090827 - EJV - IVA Por producto
	// // this.totalGeneralIva = this.totalgeneral
	// // .add(this.totalgeneral.multiply(porcentajeIva
	// // .divide(new BigDecimal(100), 0)));
	// //
	// // this.totalGeneralIva = new BigDecimal(Common
	// // .getNumeroFormateado(this.totalGeneralIva
	// // .floatValue(), 10, 2));
	//
	// }
	//
	// // }
	//
	// this.mensaje = mensajeCI;
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// this.mensaje = "Error calculando importes.";
	// log.error("calcularImportes():" + e);
	// }
	// return fOk;
	// }
	//
	private boolean asignarValidar(Hashtable ht) {
		boolean fOk = true;
		// Hashtable htDepArt = new Hashtable();
		String mensajeAV = "";

		// log.info("<-----:: -- asignarValidar: ");

		try {

			if (this.keyHashArticulo != null) {
				for (int i = 0; i < this.keyHashArticulo.length; i++) {
					if (ht.containsKey(this.keyHashArticulo[i])) {
						String[] datos = (String[]) ht
								.get(this.keyHashArticulo[i]);

						// log.info("################################");
						// for (int p = 0; p < datos.length; p++) {
						// log.info("datos[" + p + "]:" + datos[p]);
						// }
						// log.info("********************************");

						if (this.iddescuento_apli[i].equals("-1")) {
							fOk = false;
						} else {

							// 20090915-Modificado a pedido de Amelia.
							// Solo es necesaria la descripcion cuando el
							// porcentaje es igual a cien, hasta el momento se
							// velidaba aquellos > cero.
							if (Float.parseFloat(this.porcdesc_apli[i]) == 100) {

								if (this.idmotivodescuento[i].equals("-1")) {
									mensajeAV = "Seleccione motivo descuento de "
											+ this.porcdesc_apli[i]
											+ "%, para " + datos[0];
									fOk = false;
								}

							}

						}

						datos[6] = Common.setPorcentejeDescuento(
								new BigDecimal(datos[5]),
								new BigDecimal(porcdesc_apli[i]), 2).toString();

						datos[11] = Common.getNumeroFormateado((Float
								.parseFloat(datos[6]) * Float
								.parseFloat(datos[10])), 10, 2);

						// APLICA EL % DESCUENTO DEFINIDO EN stockstock
						datos[11] = Common.setPorcentejeDescuento(
								new BigDecimal(datos[11]),
								new BigDecimal(datos[4]), 2).toString();

						// datos[17] = iddescuento_suge[i];
						datos[18] = iddescuento_apli[i];
						// datos[19] = porcdesc_suge[i];
						datos[20] = porcdesc_apli[i];
						datos[21] = idmotivodescuento[i];

						ht.put(this.keyHashArticulo[i], datos);

					}
				}
				if (!fOk && mensajeAV.equals(""))
					this.mensaje = "Verifique depósitos seleccionados y cantidades ingresadas.";
				else
					mensaje = mensajeAV;
			}

		} catch (Exception e) {
			log.error("asignarValidar: " + e);
		}
		// htDepArt = null;
		// keyHashArticulo = null;
		return fOk;
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

	public BigDecimal getIdpedido_regalos_cabe() {
		return idpedido_regalos_cabe;
	}

	public void setIdpedido_regalos_cabe(BigDecimal idpedido_regalos_cabe) {
		this.idpedido_regalos_cabe = idpedido_regalos_cabe;
	}

	public String getIdpedido_regalos_padre() {
		return idpedido_regalos_padre;
	}

	public void setIdpedido_regalos_padre(String idpedido_regalos_padre) {
		this.idpedido_regalos_padre = idpedido_regalos_padre;
	}

	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public BigDecimal getIdsucursal() {
		return idsucursal;
	}

	public void setIdsucursal(BigDecimal idsucursal) {
		this.idsucursal = idsucursal;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public BigDecimal getIddomicilio() {
		return iddomicilio;
	}

	public void setIddomicilio(BigDecimal iddomicilio) {
		this.iddomicilio = iddomicilio;
	}

	public String getNombre_suc() {
		return nombre_suc;
	}

	public void setNombre_suc(String nombre_suc) {
		this.nombre_suc = nombre_suc;
	}

	public String getFechapedido() {
		return fechapedido;
	}

	public void setFechapedido(String fechapedido) {
		this.fechapedido = fechapedido;
	}

	public BigDecimal getIdcondicion() {
		return idcondicion;
	}

	public void setIdcondicion(BigDecimal idcondicion) {
		this.idcondicion = idcondicion;
	}

	public BigDecimal getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(BigDecimal idvendedor) {
		this.idvendedor = idvendedor;
	}

	public BigDecimal getIdexpreso() {
		return idexpreso;
	}

	public void setIdexpreso(BigDecimal idexpreso) {
		this.idexpreso = idexpreso;
	}

	public String getExpreso() {
		return expreso;
	}

	public void setExpreso(String expreso) {
		this.expreso = expreso;
	}

	public BigDecimal getComision() {
		return comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public BigDecimal getOrdencompra() {
		return ordencompra;
	}

	public void setOrdencompra(BigDecimal ordencompra) {
		this.ordencompra = ordencompra;
	}

	public String getObsarmado() {
		return obsarmado;
	}

	public void setObsarmado(String obsarmado) {
		this.obsarmado = obsarmado;
	}

	public String getObsentrega() {
		return obsentrega;
	}

	public void setObsentrega(String obsentrega) {
		this.obsentrega = obsentrega;
	}

	public String getRecargo1() {
		return recargo1;
	}

	public void setRecargo1(String recargo1) {
		this.recargo1 = recargo1;
	}

	public String getRecargo2() {
		return recargo2;
	}

	public void setRecargo2(String recargo2) {
		this.recargo2 = recargo2;
	}

	public String getRecargo3() {
		return recargo3;
	}

	public void setRecargo3(String recargo3) {
		this.recargo3 = recargo3;
	}

	public String getRecargo4() {
		return recargo4;
	}

	public void setRecargo4(String recargo4) {
		this.recargo4 = recargo4;
	}

	public String getBonific1() {
		return bonific1;
	}

	public void setBonific1(String bonific1) {
		this.bonific1 = bonific1;
	}

	public String getBonific2() {
		return bonific2;
	}

	public void setBonific2(String bonific2) {
		this.bonific2 = bonific2;
	}

	public String getBonific3() {
		return bonific3;
	}

	public void setBonific3(String bonific3) {
		this.bonific3 = bonific3;
	}

	public BigDecimal getIdlista() {
		return idlista;
	}

	public void setIdlista(BigDecimal idlista) {
		this.idlista = idlista;
	}

	public BigDecimal getIdmoneda() {
		return idmoneda;
	}

	public void setIdmoneda(BigDecimal idmoneda) {
		this.idmoneda = idmoneda;
	}

	public String getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(String cotizacion) {
		this.cotizacion = cotizacion;
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

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getCredcate() {
		return credcate;
	}

	public void setCredcate(String credcate) {
		this.credcate = credcate;
	}

	public String getPreferencia() {
		return preferencia;
	}

	public void setPreferencia(String preferencia) {
		this.preferencia = preferencia;
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

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public BigDecimal getTotalgeneral() {
		return totalgeneral;
	}

	public void setTotalgeneral(BigDecimal totalgeneral) {
		this.totalgeneral = totalgeneral;
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

	public BigDecimal getTotalGeneralIva() {
		return totalGeneralIva;
	}

	public void setTotalGeneralIva(BigDecimal totalGeneralIva) {
		this.totalGeneralIva = totalGeneralIva;
	}

	public String getDeposito(Object codigo_dt) {
		String deposito = "";
		try {
			deposito = this.htDepositos.get(codigo_dt).toString();

		} catch (Exception e) {
			log.error("getDeposito():" + e);
		}
		return deposito;
	}

	public List getListPrioridades() {
		return listPrioridades;
	}

	public void setListPrioridades(List listPrioridades) {
		this.listPrioridades = listPrioridades;
	}

	public BigDecimal getIdprioridad() {
		return idprioridad;
	}

	public void setIdprioridad(BigDecimal idprioridad) {
		this.idprioridad = idprioridad;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public List getListaExpresos() {
		return listaExpresos;
	}

	public List getListaCondicion() {
		return listaCondicion;
	}

	public List getListaMonedas() {
		return listaMonedas;
	}

	public List getListaTipoIva() {
		return listaTipoIva;
	}

	public List getListaDomicilios() {
		return listaDomicilios;
	}

	public List getListaVendedores() {
		return listaVendedores;
	}

	public List getListaListaPrecios() {
		return listaListaPrecios;
	}

	public String getDatosJSDomi() {
		return datosJSDomi;
	}

	public void setDatosJSDomi(String datosJSDomi) {
		this.datosJSDomi = datosJSDomi;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public BigDecimal getIdtarjeta() {
		return idtarjeta;
	}

	public void setIdtarjeta(BigDecimal idtarjeta) {
		this.idtarjeta = idtarjeta;
	}

	public List getListaTarjetasCredito() {
		return listaTarjetasCredito;
	}

	public List getListaPorcDescuento() {
		return listaPorcDescuento;
	}

	public List getListaMotivosDescuento() {
		return listaMotivosDescuento;
	}

	public List getListaCuotas() {
		return listaCuotas;
	}

	public void setListaCuotas(List listaCuotas) {
		this.listaCuotas = listaCuotas;
	}

	public BigDecimal getIdzona() {
		return idzona;
	}

	public void setIdzona(BigDecimal idzona) {
		this.idzona = idzona;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public BigDecimal getCuotas() {
		return cuotas;
	}

	public void setCuotas(BigDecimal cuotas) {
		this.cuotas = cuotas;
	}

	public String[] getKeyHashArticulo() {
		return keyHashArticulo;
	}

	public void setKeyHashArticulo(String[] keyHashArticulo) {
		this.keyHashArticulo = keyHashArticulo;
	}

	public String[] getIddescuento_suge() {
		return iddescuento_suge;
	}

	public void setIddescuento_suge(String[] iddescuento_suge) {
		this.iddescuento_suge = iddescuento_suge;
	}

	public String[] getPorcdesc_suge() {
		return porcdesc_suge;
	}

	public void setPorcdesc_suge(String[] porcdesc_suge) {
		this.porcdesc_suge = porcdesc_suge;
	}

	public String[] getIddescuento_apli() {
		return iddescuento_apli;
	}

	public void setIddescuento_apli(String[] iddescuento_apli) {
		this.iddescuento_apli = iddescuento_apli;
	}

	public String[] getPorcdesc_apli() {
		return porcdesc_apli;
	}

	public void setPorcdesc_apli(String[] porcdesc_apli) {
		this.porcdesc_apli = porcdesc_apli;
	}

	public String[] getIdmotivodescuento() {
		return idmotivodescuento;
	}

	public void setIdmotivodescuento(String[] idmotivodescuento) {
		this.idmotivodescuento = idmotivodescuento;
	}

	public String getOrigenpedido() {
		return origenpedido;
	}

	public void setOrigenpedido(String origenpedido) {
		this.origenpedido = origenpedido;
	}

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

	public BigDecimal getTotalFleteEstimado() {
		return totalFleteEstimado;
	}

	public void setTotalFleteEstimado(BigDecimal totalFleteEstimado) {
		this.totalFleteEstimado = totalFleteEstimado;
	}

	public BigDecimal getIdcampacabe() {
		return idcampacabe;
	}

	public void setIdcampacabe(BigDecimal idcampacabe) {
		this.idcampacabe = idcampacabe;
	}

	public BigDecimal getIdresultado() {
		return idresultado;
	}

	public void setIdresultado(BigDecimal idresultado) {
		this.idresultado = idresultado;
	}

	public String getAccionGTM() {
		return accionGTM;
	}

	public void setAccionGTM(String accionGTM) {
		this.accionGTM = accionGTM;
	}

	public String getCampacabe() {
		return campacabe;
	}

	public void setCampacabe(String campacabe) {
		this.campacabe = campacabe;
	}

	public BigDecimal getTotalIva() {
		return totalIva;
	}

	public BigDecimal getIdcategoriasocio() {
		return idcategoriasocio;
	}

	public void setIdcategoriasocio(BigDecimal idcategoriasocio) {
		this.idcategoriasocio = idcategoriasocio;
	}

	public String getCategoriasocio() {
		return categoriasocio;
	}

	public void setCategoriasocio(String categoriasocio) {
		this.categoriasocio = categoriasocio;

	}

	public BigDecimal getAdidesc() {
		return adidesc;
	}

	public void setAdidesc(BigDecimal adidesc) {
		this.adidesc = adidesc;
	}

	public String getTransformacion() {
		return transformacion;
	}

	public void setTransformacion(String transformacion) {
		this.transformacion = transformacion;
	}

}
