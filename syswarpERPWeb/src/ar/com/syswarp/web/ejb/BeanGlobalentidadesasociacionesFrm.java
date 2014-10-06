/* 
 javabean para la entidad (Formulario): globalentidadesasociaciones
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue May 06 15:43:41 CEST 2008 
 
 Para manejar la pagina: globalentidadesasociacionesFrm.jsp
 
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

public class BeanGlobalentidadesasociacionesFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanGlobalentidadesasociacionesFrm.class);

	private String validar = "";

	private BigDecimal identidadorigen = BigDecimal.valueOf(0);

	private String entidadorigen = "";

	private BigDecimal identidaddestino = BigDecimal.valueOf(0);

	private String entidaddestino = "";

	private String observaciones = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanGlobalentidadesasociacionesFrm() {
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
		try {
			General globalentidadesasociaciones = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = globalentidadesasociaciones
						.globalentidadesasociacionesCreate(
								this.identidadorigen, this.identidaddestino,
								this.observaciones, this.idempresa,
								this.usuarioalt);
			else
			// if (this.accion.equalsIgnoreCase("modificacion"))
			// this.mensaje =
			// globalentidadesasociaciones.globalentidadesasociacionesUpdate(
			// this.identidadorigen, this.identidaddestino, this.observaciones,
			// this.idempresa, this.usuarioact);
			if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = globalentidadesasociaciones
						.globalentidadesasociacionesDelete(
								this.identidadorigen, this.identidaddestino,
								this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
			this.log.error("el valor de identidadorigen es:" + identidadorigen);
			this.log.error("el valor de identidaddestino es:"
					+ identidaddestino);

		}
	}

	public void getDatosGlobalentidadesasociaciones() {
		try {
			General globalentidadesasociaciones = Common.getGeneral();
			List listGlobalentidadesasociaciones = globalentidadesasociaciones
					.getGlobalentidadesasociacionesPK(this.identidadorigen,
							this.idempresa);
			Iterator iterGlobalentidadesasociaciones = listGlobalentidadesasociaciones
					.iterator();
			if (iterGlobalentidadesasociaciones.hasNext()) {
				String[] uCampos = (String[]) iterGlobalentidadesasociaciones
						.next();
				// TODO: Constructores para cada tipo de datos
				this.identidadorigen = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.entidadorigen = uCampos[1];
				this.identidaddestino = BigDecimal.valueOf(Long
						.parseLong(uCampos[2]));
				this.entidaddestino = uCampos[3];
				this.observaciones = uCampos[4];
				this.idempresa = BigDecimal.valueOf(Long.parseLong(uCampos[5]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGlobalentidadesasociaciones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("globalentidadesasociacionesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (identidaddestino == null) {
						this.mensaje = "No se puede dejar vacio el campo identidaddestino ";
						return false;
					}

					if (entidadorigen.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Entidad Origen  ";
						return false;
					}

					if (entidaddestino.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Entidad Destino  ";
						return false;
					}

					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGlobalentidadesasociaciones();
				}
			}
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
	public BigDecimal getIdentidadorigen() {
		return identidadorigen;
	}

	public void setIdentidadorigen(BigDecimal identidadorigen) {
		this.identidadorigen = identidadorigen;
	}

	public BigDecimal getIdentidaddestino() {
		return identidaddestino;
	}

	public void setIdentidaddestino(BigDecimal identidaddestino) {
		this.identidaddestino = identidaddestino;
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

	public String getEntidadorigen() {
		return entidadorigen;
	}

	public void setEntidadorigen(String entidadorigen) {
		this.entidadorigen = entidadorigen;
	}

	public String getEntidaddestino() {
		return entidaddestino;
	}

	public void setEntidaddestino(String entidaddestino) {
		this.entidaddestino = entidaddestino;
	}
}
