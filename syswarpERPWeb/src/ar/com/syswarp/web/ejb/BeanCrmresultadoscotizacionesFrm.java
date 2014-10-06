/* 
 javabean para la entidad (Formulario): crmfuentecaptacion
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jun 14 17:21:14 GMT-03:00 2007 
 
 Para manejar la pagina: crmfuentecaptacionFrm.jsp
 
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

public class BeanCrmresultadoscotizacionesFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmresultadoscotizacionesFrm.class);

	private String validar = "";

	private BigDecimal idresultadocotizacion = BigDecimal.valueOf(-1);

	private String resultadocotizacion = "";


	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCrmresultadoscotizacionesFrm() {
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
			CRM crmresultadoscotizaciones = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmresultadoscotizaciones.crmresultadoscotizacionesCreate(
						this.resultadocotizacion,  this.idempresa,	this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmresultadoscotizaciones.crmresultadoscotizacionesUpdate(
						this.idresultadocotizacion, this.resultadocotizacion, this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmresultadoscotizaciones.crmresultadoscotizacionesDelete(
						this.idresultadocotizacion, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatoscrmresultadoscotizaciones() {
		try {
			CRM crmresultadoscotizaciones = Common.getCrm();
			List listcrmresultadoscotizaciones = crmresultadoscotizaciones
					.getcrmresultadoscotizacionesPK(this.idresultadocotizacion, this.idempresa);
			Iterator itercrmresultadoscotizaciones = listcrmresultadoscotizaciones.iterator();
			if (itercrmresultadoscotizaciones.hasNext()) {
				String[] uCampos = (String[]) itercrmresultadoscotizaciones.next();
				// TODO: Constructores para cada tipo de datos
				this.idresultadocotizacion = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.resultadocotizacion = uCampos[1];
				this.idempresa = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatoscrmresultadoscotizaciones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmresultadoscotizacionesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (resultadocotizacion == null) {
						this.mensaje = "No se puede dejar vacio el campo resultadocotizacion ";
						return false;
					}
					
					// 2. len 0 para campos nulos
					if (resultadocotizacion.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo resultadocotizacion  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatoscrmresultadoscotizaciones();
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
	public BigDecimal getidresultadocotizacion() {
		return idresultadocotizacion;
	}

	public void setidresultadocotizacion(BigDecimal idresultadocotizacion) {
		this.idresultadocotizacion = idresultadocotizacion;
	}

	public String getresultadocotizacion() {
		return resultadocotizacion;
	}

	public void setresultadocotizacion(String resultadocotizacion) {
		this.resultadocotizacion = resultadocotizacion;
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
