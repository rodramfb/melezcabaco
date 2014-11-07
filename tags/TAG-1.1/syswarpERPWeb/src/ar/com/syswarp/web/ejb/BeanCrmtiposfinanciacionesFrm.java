/* 
 javabean para la entidad (Formulario): crmtiposfinanciaciones
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jun 19 12:24:16 GMT-03:00 2007 
 
 Para manejar la pagina: crmtiposfinanciacionesFrm.jsp
 
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

public class BeanCrmtiposfinanciacionesFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmtiposfinanciacionesFrm.class);

	private String validar = "";

	private BigDecimal idtipofinanciacion = BigDecimal.valueOf(-1);

	private String tipofinanciacion = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCrmtiposfinanciacionesFrm() {
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
			CRM crmtiposfinanciaciones = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmtiposfinanciaciones
						.crmtiposfinanciacionesCreate(this.tipofinanciacion,
								this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmtiposfinanciaciones
						.crmtiposfinanciacionesUpdate(this.idtipofinanciacion,
								this.tipofinanciacion, this.usuarioact,
								this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmtiposfinanciaciones
						.crmtiposfinanciacionesDelete(this.idtipofinanciacion,
								this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmtiposfinanciaciones() {
		try {
			CRM crmtiposfinanciaciones = Common.getCrm();
			List listCrmtiposfinanciaciones = crmtiposfinanciaciones
					.getCrmtiposfinanciacionesPK(this.idtipofinanciacion,
							this.idempresa);
			Iterator iterCrmtiposfinanciaciones = listCrmtiposfinanciaciones
					.iterator();
			if (iterCrmtiposfinanciaciones.hasNext()) {
				String[] uCampos = (String[]) iterCrmtiposfinanciaciones.next();
				// TODO: Constructores para cada tipo de datos
				this.idtipofinanciacion = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.tipofinanciacion = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCrmtiposfinanciaciones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmtiposfinanciacionesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
					if (tipofinanciacion.trim().length() == 0) {
						this.mensaje = "No se puede vacio el campo Tipo financiacion  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCrmtiposfinanciaciones();
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
	public BigDecimal getIdtipofinanciacion() {
		return idtipofinanciacion;
	}

	public void setIdtipofinanciacion(BigDecimal idtipofinanciacion) {
		this.idtipofinanciacion = idtipofinanciacion;
	}

	public String getTipofinanciacion() {
		return tipofinanciacion;
	}

	public void setTipofinanciacion(String tipofinanciacion) {
		this.tipofinanciacion = tipofinanciacion;
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
}
