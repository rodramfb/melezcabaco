/* 
 javabean para la entidad (Formulario): clientetarjetascreditomarcas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 23 19:22:37 GMT-03:00 2007 
 
 Para manejar la pagina: clientetarjetascreditomarcasFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanClientesPresentacionTarjetasRecepcionRechazos implements
		SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanClientesPresentacionTarjetasRecepcionRechazos.class);

	private String validar = "";

	private BigDecimal idtarjetacredito = BigDecimal.valueOf(-1);

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String mensajePresntacion = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private boolean primeraCarga = true;

	private String fechaPresentacion = "";

	private java.sql.Date fpresentacion = null;

	private List listClub = new ArrayList();

	private BigDecimal idclub = new BigDecimal(-1);

	private String idcliente = "";

	private BigDecimal idmovcuo = new BigDecimal(-1);

	private BigDecimal importeRechazo = new BigDecimal(0);

	private String[] keyHashRechazos = null;

	private String[] idmotivorechazo = null;

	private List listTarjetasCredito = new ArrayList();

	private List listPresentacionCliente = new ArrayList();

	private List listMotivosRechazo = new ArrayList();

	private Hashtable htRechazosPresentacion = new Hashtable();

	// 20121101 - EJV - Mantis 889 -->

	private BigDecimal idgeneracion = new BigDecimal(-1);

	private String club = "";

	private String tarjetacredito = "";

	// <--

	public BeanClientesPresentacionTarjetasRecepcionRechazos() {
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
		try {

			this.listTarjetasCredito = Common.getClientes()
					.getClientetarjetascreditomarcasAll(250, 0, this.idempresa);

			this.listClub = Common.getClientes().getClientesClubAll(100, 0,
					this.idempresa);

			this.listMotivosRechazo = Common.getClientes()
					.getClientesTarjetasMotivosAll(250, 0, this.idempresa);

			this.setHashSession();

			if (this.accion.equalsIgnoreCase("presentacion")) {

				if (validaParametros())
					this.setListPresentacionCliente();
				else
					return false;

			} else if (this.accion.equalsIgnoreCase("eliminar")) {

				if (this.htRechazosPresentacion.containsKey(this.idmovcuo + "")) {
					this.htRechazosPresentacion.remove(this.idmovcuo + "");
					request.getSession().setAttribute("htRechazosPresentacion",
							this.htRechazosPresentacion);
					this.mensaje = "Se removio el registro seleccionado previamente para marcar como rechazado.";
				} else
					this.mensaje = "El registro seleccionado no existe, no es posible eliminar.";

			} else if (this.accion.equalsIgnoreCase("limpiar")) {

				this.htRechazosPresentacion.clear();
				this.htRechazosPresentacion = new Hashtable();
				request.getSession().setAttribute("htRechazosPresentacion",
						this.htRechazosPresentacion);
				this.mensaje = "Se removieron todos los registros seleccionados previamente para marcar como rechazados.";

			} else if (this.accion.equalsIgnoreCase("agregar")) {

				if (validaParametros()) {

					this.setListPresentacionCliente();

					if (this.idmovcuo.signum() < 1) {

						this.mensaje = "Es necesario seleccionar un registro de presentacion.";
						return false;

					} else {

						// 20121107 -->
						if (!this.htRechazosPresentacion
								.containsKey(this.idmovcuo.toString())) {

							// Agregar -->
							List listRecepcion = Common
									.getClientes()
									.getClientesTarjetasPresentacionRecepcionPK(
											this.idmovcuo, this.idempresa);
							Iterator iterRece = listRecepcion.iterator();

							if (iterRece.hasNext()) {
								String[] datosRece = (String[]) iterRece.next();
								this.htRechazosPresentacion.put(this.idmovcuo
										+ "", datosRece);
								//
								this.importeRechazo = this.importeRechazo
										.add(new BigDecimal(datosRece[2]));
								//
								request.getSession().setAttribute(
										"htRechazosPresentacion",
										this.htRechazosPresentacion);

							} else {
								this.mensaje = "No fue posible agregar el registro seleccionado, el mismo posiblemente haya sido modificado desde otra sesion.";
							}

							// <--

						} else {

							this.mensaje = "El registro seleccionado ya fue previamente marcado para rechazar.";
						}

					}

				}
			} else if (this.accion.equalsIgnoreCase("generarrechazo")) {

				if (validaParametros()) {

					if (this.htRechazosPresentacion != null
							&& !this.htRechazosPresentacion.isEmpty()) {

						if (this.asignacionMotivos(true)) {

							this.mensaje = Common.getClientes()
									.clientesPresentacionGenerarRechazos(
											this.htRechazosPresentacion,
											this.idempresa, this.usuarioact);
							if (this.mensaje.equalsIgnoreCase("OK")) {
								this.request.getSession().removeAttribute(
										"htRechazosPresentacion");
								this.htRechazosPresentacion = new Hashtable();
								this.request.getSession().setAttribute(
										"htRechazosPresentacion",
										this.htRechazosPresentacion);
								this.mensaje = "Registros procesados correctamente.";
							}

						}

					}
				} else {

					this.mensaje = "No existen registros seleccionados para rechazo";

				}

			}

			this.asignacionMotivos(false);

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private boolean asignacionMotivos(boolean validar) {
		boolean asigna = true;
		String msj = "";
		try {

			// log.info("VALIDANDO DATOS DE RECHAZO .... 0");

			if (this.keyHashRechazos != null && this.keyHashRechazos.length > 0) {

				// log.info("VALIDANDO DATOS DE RECHAZO .... 1");

				for (int i = 0; i < this.keyHashRechazos.length; i++) {

					// log.info("VALIDANDO DATOS DE RECHAZO .... 2");

					if (this.htRechazosPresentacion
							.containsKey(this.keyHashRechazos[i])) {

						// log.info("VALIDANDO DATOS DE RECHAZO .... 3");

						String[] datos = (String[]) this.htRechazosPresentacion
								.get(this.keyHashRechazos[i]);

						datos[9] = this.idmotivorechazo[i];

						this.importeRechazo = this.importeRechazo
								.add(new BigDecimal(datos[2]));

						if (!Common.esEntero(this.idmotivorechazo[i])
								|| Integer.parseInt(this.idmotivorechazo[i]) < 1) {

							msj = "Es necesario seleccionar motivo de rechazo para presentacion: "
									+ datos[6];
							asigna = false;

						}

						this.htRechazosPresentacion.put(
								this.keyHashRechazos[i], datos);

					}
				}

				request.getSession().setAttribute("htRechazosPresentacion",
						this.htRechazosPresentacion);

			}

		} catch (Exception e) {
			msj = "Se produjo un error al evaluar motivos asignados a rechazos";
			log.error("asignacionMotivos(): " + e);
		}

		if (validar)
			this.mensaje = msj;

		return asigna;

	}

	private void setHashSession() {

		try {

			if (this.isPrimeraCarga()) {
				request.getSession().setAttribute("htRechazosPresentacion",
						this.htRechazosPresentacion);
			} else {

				this.htRechazosPresentacion = (Hashtable) (request.getSession()
						.getAttribute("htRechazosPresentacion"));

			}

		} catch (Exception e) {
			log.error("setHashSession(): " + e);
		}

	}

	private void setListPresentacionCliente() {
		try {

			this.listPresentacionCliente = Common.getClientes()
					.getClientesTarjetasPresentacionRecepcionXClie(
							new BigDecimal(this.idcliente), this.idclub,
							this.idtarjetacredito, this.fpresentacion,
							this.idempresa);

			if (this.listPresentacionCliente.isEmpty()) {
				this.mensajePresntacion = "No existen registros para el criterio seleccionado.";
			}

		} catch (Exception e) {
			log.error("setListPresentacionCliente(): " + e);
		}

	}

	private boolean validaParametros() {

		try {

			if (this.idgeneracion.intValue() < 1) {
				this.mensaje = "Seleccione Presentacion.";
				return false;
			} else if (this.idclub.intValue() < 1) {
				this.mensaje = "Seleccione club.";
				return false;
			} else if (this.idtarjetacredito.intValue() < 1) {
				this.mensaje = "Seleccione tarjeta.";
				return false;
			} else if (!Common.isFormatoFecha(this.fechaPresentacion)
					|| !Common.isFechaValida(this.fechaPresentacion)) {
				this.mensaje = "Fecha de presentacion invalida.";
				return false;
			} else if (!Common.esEntero(this.idcliente)) {
				this.mensaje = "Ingrese Nro. Cliente valido.";
				return false;
			} else {
				this.fpresentacion = (java.sql.Date) Common
						.setObjectToStrOrTime(this.fechaPresentacion,
								"StrToJSDate");
				return true;
			}

		} catch (Exception e) {
			this.mensaje = "No fue posible validar parametros.";
			return false;
		}

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

	public String getMensajePresntacion() {
		return mensajePresntacion;
	}

	public void setMensajePresntacion(String mensajePresntacion) {
		this.mensajePresntacion = mensajePresntacion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
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
	public BigDecimal getIdtarjetacredito() {
		return idtarjetacredito;
	}

	public void setIdtarjetacredito(BigDecimal idtarjetacredito) {
		this.idtarjetacredito = idtarjetacredito;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String[] getKeyHashRechazos() {
		return keyHashRechazos;
	}

	public void setKeyHashRechazos(String[] keyHashRechazos) {
		this.keyHashRechazos = keyHashRechazos;
	}

	public String[] getIdmotivorechazo() {
		return idmotivorechazo;
	}

	public void setIdmotivorechazo(String[] idmotivorechazo) {
		this.idmotivorechazo = idmotivorechazo;
	}

	public List getListTarjetasCredito() {
		return listTarjetasCredito;
	}

	public String getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public BigDecimal getIdclub() {
		return idclub;
	}

	public void setIdclub(BigDecimal idclub) {
		this.idclub = idclub;
	}

	public List getListClub() {
		return listClub;
	}

	public String getIdcliente() {
		return idcliente;
	}

	public BigDecimal getIdmovcuo() {
		return idmovcuo;
	}

	public void setIdmovcuo(BigDecimal idmovcuo) {
		this.idmovcuo = idmovcuo;
	}

	public BigDecimal getImporteRechazo() {
		return importeRechazo;
	}

	public void setImporteRechazo(BigDecimal importeRechazo) {
		this.importeRechazo = importeRechazo;
	}

	public void setIdcliente(String idcliente) {
		this.idcliente = idcliente;
	}

	public List getListPresentacionCliente() {
		return listPresentacionCliente;
	}

	public List getListMotivosRechazo() {
		return listMotivosRechazo;
	}

	// 20121101 - EJV - Mantis 889 -->

	public BigDecimal getIdgeneracion() {
		return idgeneracion;
	}

	public void setIdgeneracion(BigDecimal idgeneracion) {
		this.idgeneracion = idgeneracion;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getTarjetacredito() {
		return tarjetacredito;
	}

	public void setTarjetacredito(String tarjetacredito) {
		this.tarjetacredito = tarjetacredito;
	}

	// <---

}
