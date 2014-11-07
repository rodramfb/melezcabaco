/*
 BeanLovCajaMovimientosCancelarAbm 
 javabean para la entidad: clientesMovCli
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Dec 13 10:54:32 GMT-03:00 2006 
 
 Para manejar la pagina: clientesMovCliAbm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanLovCajaMovimientosCancelarAbm implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanLovCajaMovimientosCancelarAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List cajaIdentificadoresList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal ididentificador;

	private String mensaje = "";

	private String avisoTarjetas = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	boolean buscar = false;

	private String tipomov = "";

	private String propio = "";

	private String[] delKey = null;

	private String[] keyHashDatosIdentificador = null;

	private String[] importeingreso = null;

	private String[] detalle = null;

	private String[] chequenro = null;

	private String[] cc1 = null;

	private String[] cc2 = null;

	private String[] fecha = null;

	private String[] cuenta = null;

	private String[] clearing = null;

	// private String[] cuotas = null;

	private String identificador = "";

	private String cartera_mt = "";

	private String tipcart_mt = "";

	private String cartera = "";

	private BigDecimal idmovteso = new BigDecimal(0);

	private boolean reasigna = false;

	private Timestamp fecha_mt = new Timestamp(Calendar.getInstance()
			.getTimeInMillis());

	public BeanLovCajaMovimientosCancelarAbm() {
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

		Tesoreria cajaIdentificadores = Common.getTesoreria();
		Hashtable htMovimientosCancelar = (Hashtable) session
				.getAttribute("htMovimientosCancelar");

		if (htMovimientosCancelar == null) {
			htMovimientosCancelar = new Hashtable();
			Common.htRellenar((Hashtable) session
					.getAttribute("htMovimientosCancelarOK"),
					htMovimientosCancelar);
		}

		/**/
		BigDecimal totalCartera = new BigDecimal(-1);
		String[] tipoPropietario = null;
		/**/

		try {

			/**/
			this.getTesoFechaCaja();

			tipoPropietario = cajaIdentificadores
					.getIdentificadorTipoPropietario(this.cartera_mt,
							this.idempresa);

			if (tipoPropietario[0].equalsIgnoreCase("T")
					&& tipoPropietario[1].equalsIgnoreCase("N")) {

				totalCartera = cajaIdentificadores.getTotalCartera(
						this.cartera_mt, this.tipcart_mt, new java.sql.Date(
								this.fecha_mt.getTime()), this.idempresa);

				this.avisoTarjetas = "Monto maximo a cancelar: " + totalCartera;
				this.avisoTarjetas += ". Al cancelar cualquiera de estos movimientos, cancelara todos, ";
				this.avisoTarjetas += "generando uno nuevo con el arrastre correspondiente, si lo hubiere.";

			}
			/**/

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			String filtro = " WHERE usado_mt IS NULL  AND cartera_mt =  '"
					+ this.cartera_mt + "'   AND tipcart_mt = '"
					+ this.tipcart_mt + "'     AND fecha_mt::date <=  '"
					+ this.fecha_mt + "'::date ";
			if (buscar) {
				filtro += "   AND ((UPPER(detalle_mt) LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' OR (nrodoc_mt::VARCHAR) LIKE '%"
						+ ocurrencia.toUpperCase().trim() + "%') )";

				this.totalRegistros = cajaIdentificadores
						.getTotalEntidadFiltro("cajamovteso", filtro,
								this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				if (this.totalRegistros == this.limit)
					this.offset = 0;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.cajaIdentificadoresList = cajaIdentificadores
						.getLovCajaMovimientosCancelarOcu(this.limit,
								this.offset, this.ocurrencia, this.cartera_mt,
								this.tipcart_mt, this.fecha_mt, this.idempresa);
			} else {

				this.totalRegistros = cajaIdentificadores
						.getTotalEntidadFiltro("cajamovteso", filtro,
								this.idempresa);

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.cajaIdentificadoresList = cajaIdentificadores
						.getLovCajaMovimientosCancelarAll(this.limit,
								this.offset, this.cartera_mt, this.tipcart_mt,
								this.fecha_mt, this.idempresa);
			}

			if (this.accion.equalsIgnoreCase("agregar")) {

				if (!this.idmovteso.equals("0")) {
					
					List lista = cajaIdentificadores
							.getLovCajaMovimientosCancelarOne(this.idmovteso,
									this.idempresa);
					if (lista.size() > 0) {
						String[] datos = (String[]) lista.get(0);
						String clave = datos[2] + "#" + datos[6] + datos[7];

						if (datos[6].equalsIgnoreCase("T")
								&& datos[7].equalsIgnoreCase("N")) {

							datos[36] = totalCartera.toString();

							if (htMovimientosCancelar.containsKey(clave))
								this.reasigna = true;
							htMovimientosCancelar.put(clave, datos);

						} else {

							htMovimientosCancelar.put(
									this.idmovteso.toString(), datos);
						}

					} else {
						this.mensaje = "Imposible recuperar los datos del documento seleccionado.";
					}

				} else {
					this.mensaje = "Seleccione un documento para cancelar.";
				}

			}

			if (this.accion.equalsIgnoreCase("eliminar")) {
				if (this.delKey == null) {
					this.mensaje = "Seleccione un registro a eliminar.";
				} else {
					for (int i = 0; i < this.delKey.length; i++) {
						if (htMovimientosCancelar.containsKey(this.delKey[i])) {
							htMovimientosCancelar.remove(this.delKey[i]);
						}
					}
				}
			}

			this.asignarValidar(htMovimientosCancelar);

			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	private boolean asignarValidar(Hashtable ht) {
		boolean fOk = true;
		String mensaje = "";
		try {
			if (this.keyHashDatosIdentificador != null) {
				for (int i = 0; i < this.keyHashDatosIdentificador.length; i++) {
					if (ht.containsKey(this.keyHashDatosIdentificador[i])) {
						String[] datos = (String[]) ht
								.get(this.keyHashDatosIdentificador[i]);

						if (!Common.esNumerico(this.importeingreso[i])) {
							fOk = false;
							mensaje = "Importe solo admite valores numericos.";
							// valida - tarjeta
						} else if ((new BigDecimal(datos[36]))
								.compareTo(new BigDecimal(
										this.importeingreso[i])) < 0) {

							fOk = false;
							mensaje = "Importe no puede ser mayor a "
									+ datos[36] + " .";

						}

						if (!this.reasigna) {
							if (fOk) {
								datos[28] = Common
										.getGeneral()
										.getNumeroFormateado(

												Float
														.parseFloat(this.importeingreso[i]),
												10, 2);

								datos[4] = this.cuenta[i];
								datos[24] = this.cc1[i];
								datos[25] = this.cc2[i];
								datos[29] = this.detalle[i];
								//20090813 EJV
								//datos[30] = this.chequenro[i];
								//datos[37] = this.chequenro[i];
								datos[31] = this.fecha[i].replaceAll("-", "/");
								// TODO:CLEARING - quizas sea necesario
								// inicializar
								// con otro valor.
								datos[32] = this.clearing[i];
								// datos[33] = this.cuotas[i];

							} else {

								datos[28] = this.importeingreso[i];
								datos[4] = this.cuenta[i];
								datos[24] = this.cc1[i];
								datos[25] = this.cc2[i];
								datos[29] = this.detalle[i];
								//20090813 EJV
								//datos[30] = this.chequenro[i];
								//datos[37] = this.chequenro[i];
								datos[31] = this.fecha[i].replaceAll("-", "/");
								// TODO:CLEARING - quizas sea necesario
								// inicializar
								// con otro valor.
								datos[32] = this.clearing[i];
								// datos[33] = this.cuotas[i];

							}
						}

						ht.put(this.keyHashDatosIdentificador[i], datos);

					}
				}

			}
			if ((this.accion.equalsIgnoreCase("confirmar") || (ht.isEmpty() && this.accion
					.equalsIgnoreCase("eliminar")))
					&& mensaje.equals("")) {

				session.setAttribute("htMovimientosCancelarOK", ht);
				session.removeAttribute("htMovimientosCancelar");

			} else {
				if (!mensaje.equals(""))
					this.mensaje = mensaje;
				session.setAttribute("htMovimientosCancelar", ht);
			}

		} catch (Exception e) {
			log.error("asignarValidar: " + e);
		}
		return fOk;
	}

	private void getTesoFechaCaja() {
		try {
			General general = Common.getGeneral();
			List listFechaTeso = general.getSetupvariablesPK("tesoFechaCaja",
					this.idempresa);
			Iterator iterFechaTeso = listFechaTeso.iterator();
			if (iterFechaTeso.hasNext()) {
				String[] uCampos = (String[]) iterFechaTeso.next();
				// TODO: Constructores para cada tipo de datos

				this.fecha_mt = (Timestamp) Common.setObjectToStrOrTime(
						uCampos[1], "StrToJSTs");

			} else {
				this.mensaje = "Imposible recuperar fecha de tesoreria.";
			}
		} catch (Exception e) {
			log.error("getTesoFechaCaja()" + e);
		}
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

	public List getCajaIdentificadoresList() {
		return cajaIdentificadoresList;
	}

	public void setCajaIdentificadoresList(List cajaIdentificadoresList) {
		this.cajaIdentificadoresList = cajaIdentificadoresList;
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

	public BigDecimal getIdidentificador() {
		return ididentificador;
	}

	public void setIdidentificador(BigDecimal ididentificador) {
		this.ididentificador = ididentificador;
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

	public String getPropio() {
		return propio;
	}

	public void setPropio(String propio) {
		this.propio = propio;
	}

	public String getTipomov() {
		return tipomov;
	}

	public void setTipomov(String tipommov) {
		this.tipomov = tipommov;
	}

	public String[] getDelKey() {
		return delKey;
	}

	public void setDelKey(String[] delKey) {
		this.delKey = delKey;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String[] getKeyHashDatosIdentificador() {
		return keyHashDatosIdentificador;
	}

	public void setKeyHashDatosIdentificador(String[] keyHashDatosIdentificador) {
		this.keyHashDatosIdentificador = keyHashDatosIdentificador;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String[] getImporteingreso() {
		return importeingreso;
	}

	public void setImporteingreso(String[] totalcobrar) {
		this.importeingreso = totalcobrar;
	}

	public String[] getCc1() {
		return cc1;
	}

	public void setCc1(String[] cc1) {
		this.cc1 = cc1;
	}

	public String[] getCc2() {
		return cc2;
	}

	public void setCc2(String[] cc2) {
		this.cc2 = cc2;
	}

	public String[] getChequenro() {
		return chequenro;
	}

	public void setChequenro(String[] chequenro) {
		this.chequenro = chequenro;
	}

	public String[] getDetalle() {
		return detalle;
	}

	public void setDetalle(String[] detalle) {
		this.detalle = detalle;
	}

	public String[] getFecha() {
		return fecha;
	}

	public void setFecha(String[] fecha) {
		this.fecha = fecha;
	}

	public String[] getCuenta() {
		return cuenta;
	}

	public void setCuenta(String[] cuenta) {
		this.cuenta = cuenta;
	}

	public String[] getClearing() {
		return clearing;
	}

	public void setClearing(String[] clearing) {
		this.clearing = clearing;
	}

	/*
	 * public String[] getCuotas() { return cuotas; }
	 * 
	 * public void setCuotas(String[] cuotas) { this.cuotas = cuotas; }
	 */
	public String getCartera_mt() {
		return cartera_mt;
	}

	public void setCartera_mt(String cartera_mt) {
		this.cartera_mt = cartera_mt;
	}

	public String getTipcart_mt() {
		return tipcart_mt;
	}

	public void setTipcart_mt(String tipcart_mt) {
		this.tipcart_mt = tipcart_mt;
	}

	public String getCartera() {
		return cartera;
	}

	public void setCartera(String cartera) {
		this.cartera = cartera;
	}

	public BigDecimal getIdmovteso() {
		return idmovteso;
	}

	public void setIdmovteso(BigDecimal idmovteso) {
		this.idmovteso = idmovteso;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public boolean isReasigna() {
		return reasigna;
	}

	public void setReasigna(boolean reasigna) {
		this.reasigna = reasigna;
	}

	public String getAvisoTarjetas() {
		return avisoTarjetas;
	}

	public void setAvisoTarjetas(String avisoTarjetas) {
		this.avisoTarjetas = avisoTarjetas;
	}

}
