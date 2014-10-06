/*
 
 
 javabean para la entidad: cajaIdentificadores
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Dec 18 15:14:22 GMT-03:00 2006 
 
 Para manejar la pagina: cajaIdentificadoresAbm.jsp
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

import java.text.SimpleDateFormat;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanLovCajaIdentificadoresSalidaOtrosMov implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanLovCajaIdentificadoresSalidaOtrosMov.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal ejercicio = new BigDecimal(-1);

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

	private String[] cuotas = null;

	private String identificador = "";

	public BeanLovCajaIdentificadoresSalidaOtrosMov() {
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
		Hashtable htIdentificadoresContrapartida = (Hashtable) session
				.getAttribute("htIdentificadoresContrapartida") != null ? (Hashtable) session
				.getAttribute("htIdentificadoresContrapartida")
				: (Hashtable) session
						.getAttribute("htIdentificadoresContrapartidaOK");

		Hashtable htMovimientosCancelarOK = (Hashtable) session
				.getAttribute("htMovimientosCancelarOK") != null ? (Hashtable) session
				.getAttribute("htMovimientosCancelarOK")
				: new Hashtable();

		try {

			this.ejercicio = new BigDecimal(session.getAttribute(
					"ejercicioActivo").toString());

			if (session.getAttribute("htMovimientosCancelarOK") == null)
				session
						.setAttribute("htMovimientosCancelarOK",
								new Hashtable());

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			// TODO:20090807 eliminar filtro_TODO, momentaneamente para terminar
			// el desarrollo mas rapido, tiene que ver con documenteos
			// caucionados, los que actualmente tienen una inexistente
			// relevancia en el manejo de caja.
			
			String filtro_TODO = " AND (idtipoidentificador NOT IN (SELECT idtipoidentificador FROM cajatipoidentificadores WHERE tipomov = 'D' AND propio = 'N')) ";
			String filtro = " WHERE (tipomov <>  'T') " + filtro_TODO;
			if (buscar) {
				filtro += "   AND ((UPPER(descripcion) LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' OR UPPER(identificador) LIKE '%"
						+ ocurrencia.toUpperCase().trim() + "%') )";

				this.totalRegistros = cajaIdentificadores
						.getTotalEntidadFiltro("videntificadorestipo", filtro,
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
						.getLovCajaIdentOtrosMovOUTOcu(this.limit, this.offset,
								this.ocurrencia, this.idempresa);
			} else {

				this.totalRegistros = cajaIdentificadores
						.getTotalEntidadFiltro("videntificadorestipo", filtro,
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
						.getLovCajaIdentOtrosMovOUTAll(this.limit, this.offset,
								this.idempresa);
			}

			if (this.accion.equalsIgnoreCase("agregar")) {

				if (!this.identificador.equals("")) {

					List lista = cajaIdentificadores
							.getLovCajaContraPartidaOne(this.identificador,
									this.ejercicio, this.idempresa);
					if (lista.size() > 0) {
						String[] datos = (String[]) lista.get(0);

						// 20090609 - EJV:
						// Cargaba el mismo identificador solo UNA (1) vez.
						// htIdentificadoresContrapartida.put(this.identificador,
						// datos);
						// identificador N cantidad de veces.

						String key = Calendar.getInstance().getTimeInMillis()
								+ "";
						htIdentificadoresContrapartida.put(key, datos);

					} else {
						this.mensaje = "Imposible recuperar los datos del identificador seleccionado.";
					}

				} else {
					this.mensaje = "Seleccione un identificador para aplicar.";
				}

				// session.setAttribute("htIdentificadoresContrapartida",
				// htIdentificadoresContrapartida);
			}

			if (this.accion.equalsIgnoreCase("eliminar")) {
				if (this.delKey == null) {
					this.mensaje = "Seleccione un registro a eliminar.";
				} else {
					for (int i = 0; i < this.delKey.length; i++) {
						if (htIdentificadoresContrapartida
								.containsKey(this.delKey[i])) {
							htIdentificadoresContrapartida
									.remove(this.delKey[i]);
						}
						if (htMovimientosCancelarOK.containsKey(this.delKey[i])) {
							htMovimientosCancelarOK.remove(this.delKey[i]);
						}

					}
				}
			}

			this.asignarValidar(htIdentificadoresContrapartida);

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

						// TODO: esta pendiente discriminar entre tipos de
						// documentos NC - ND - FA
						if (!Common.esNumerico(this.importeingreso[i])) {
							fOk = false;
							mensaje = "Importe solo admite valores numericos.";
							// valida - cheque
						} else if (datos[6].equalsIgnoreCase("C")) {

							if (!this.chequenro[i].equals("")
									&& !Common.esEntero(this.chequenro[i])) {
								fOk = false;
								mensaje = "Número de cheque inválido.";
							} else if (!this.fecha[i].equals("")) {
								if (!Common.isFormatoFecha(this.fecha[i])
										|| !Common.isFechaValida(this.fecha[i])) {
									fOk = false;
									mensaje = "Fecha inválida: "
											+ this.fecha[i];
								}

							}
							// valida - tarjeta
						} else if (datos[4].equalsIgnoreCase("T")) {
							if (!Common.esEntero(this.cuotas[i])) {
								fOk = false;
								mensaje = "Número de cuotas inválido.";
							} else if (Long.parseLong(this.cuotas[i]) > Long
									.parseLong(datos[16])) {
								fOk = false;
								mensaje = "Número de cuotas no puede ser mayor a "
										+ datos[16] + ".";
							}
						}

						if (fOk) {
							datos[28] = Common.getGeneral()
									.getNumeroFormateado(

									Float.parseFloat(this.importeingreso[i]),
											10, 2);

							datos[4] = this.cuenta[i];
							datos[24] = this.cc1[i];
							datos[25] = this.cc2[i];
							datos[29] = this.detalle[i];
							datos[30] = Common.esEntero(this.chequenro[i])  ? this.chequenro[i] : "0";
							datos[31] = this.fecha[i].replaceAll("-", "/");
							// TODO:CLEARING - quizas sea necesario inicializar
							// con otro valor.
							datos[32] = this.clearing[i];
							datos[33] = this.cuotas[i];

						} else {

							datos[28] = this.importeingreso[i];
							datos[4] = this.cuenta[i];
							datos[24] = this.cc1[i];
							datos[25] = this.cc2[i];
							datos[29] = this.detalle[i];
							datos[30] = Common.esEntero(this.chequenro[i])  ? this.chequenro[i] : "0";
							datos[31] = this.fecha[i].replaceAll("-", "/");
							// TODO:CLEARING - quizas sea necesario inicializar
							// con otro valor.
							datos[32] = this.clearing[i];
							datos[33] = this.cuotas[i];

						}

						ht.put(this.keyHashDatosIdentificador[i], datos);

					}
				}

			}
			if ((this.accion.equalsIgnoreCase("confirmar") || (ht.isEmpty() && this.accion
					.equalsIgnoreCase("eliminar")))
					&& mensaje.equals("")) {

				session.setAttribute("htIdentificadoresContrapartidaOK", ht);
				session.removeAttribute("htIdentificadoresContrapartida");
				session.removeAttribute("tipodoc");
				session.setAttribute("htOMDocTercerosCancelarINOK", new Hashtable());
				

			} else {
				if (!mensaje.equals(""))
					this.mensaje = mensaje;
				session.setAttribute("htIdentificadoresContrapartida", ht);
			}

		} catch (Exception e) {
			log.error("asignarValidar: " + e);
		}
		return fOk;
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

	public String[] getCuotas() {
		return cuotas;
	}

	public void setCuotas(String[] cuotas) {
		this.cuotas = cuotas;
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

}
