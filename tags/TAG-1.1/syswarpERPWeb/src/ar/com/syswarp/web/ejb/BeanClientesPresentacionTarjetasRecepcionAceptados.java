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

public class BeanClientesPresentacionTarjetasRecepcionAceptados implements
		SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanClientesPresentacionTarjetasRecepcionAceptados.class);

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

	private long totalRegistrosAceptar = 0;

	private BigDecimal importeAceptar = new BigDecimal(0);

	private List listTarjetasCredito = new ArrayList();

	private String entidad = "";

	private String filtro = "";

	// 20121101 - EJV - Mantis 889 -->

	private BigDecimal idgeneracion = new BigDecimal(-1);

	private String club = "";

	private String tarjetacredito = "";

	// <--

	public BeanClientesPresentacionTarjetasRecepcionAceptados() {
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

			if (!this.accion.equalsIgnoreCase("")) {

				if (this.validaParametros()) {

					this.entidad = " vclientesmovcuorecepcion ";
					this.filtro = " WHERE fecha_envio_cuo = '"
							+ this.fpresentacion
							+ "'::date  AND estado_cuo = 'E' AND idtarjetacredito = "
							+ this.idtarjetacredito + "  AND idclub = "
							+ this.idclub;

					this.totalRegistrosAceptar = Common.getClientes()
							.getTotalEntidadFiltro(this.entidad, this.filtro,
									this.idempresa);

					this.importeAceptar = Common.getClientes()
							.getClientesTarjetasPresentacionTotalAceptar(
									this.idclub, this.idtarjetacredito,
									this.fpresentacion, this.idempresa);

					if (this.accion.equalsIgnoreCase("generaraceptacion")) {

						this.mensaje = Common.getClientes()
								.cobranzasMovClientePresentacionTarjeta(
										this.idclub, this.idtarjetacredito,
										this.fpresentacion, this.usuarioalt,
										this.idempresa);

						if (Common.setNotNull(this.mensaje).equalsIgnoreCase(
								"OK")) {
							this.mensaje = "Aceptacion procesada correctamente.";
						}

					}

				}
			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private boolean validaParametros() {

		try {

			if (this.idclub.intValue() < 1) {
				this.mensaje = "Seleccione club.";
				return false;
			} else if (this.idtarjetacredito.intValue() < 1) {
				this.mensaje = "Seleccione tarjeta.";
				return false;
			} else if (!Common.isFormatoFecha(this.fechaPresentacion)
					|| !Common.isFechaValida(this.fechaPresentacion)) {
				this.mensaje = "Fecha de presentacion invalida.";
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

	public long getTotalRegistrosAceptar() {
		return totalRegistrosAceptar;
	}

	public BigDecimal getImporteAceptar() {
		return importeAceptar;
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
