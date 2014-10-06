/* 
 javabean para la entidad (Formulario): crmtiposcotizaciones
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jun 19 12:22:41 GMT-03:00 2007 
 
 Para manejar la pagina: crmtiposcotizacionesFrm.jsp
 
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

public class BeanCrmtiposcotizacionesFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmtiposcotizacionesFrm.class);

	private String validar = "";

	private BigDecimal idtipocotizacion = BigDecimal.valueOf(-1);

	private String tipocotizacion = "";

	private String valorunidadsup = "0.00";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCrmtiposcotizacionesFrm() {
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
			CRM crmtiposcotizaciones = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmtiposcotizaciones.crmtiposcotizacionesCreate(
						this.tipocotizacion, new BigDecimal(this.valorunidadsup),
						this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmtiposcotizaciones.crmtiposcotizacionesUpdate(
						this.idtipocotizacion, this.tipocotizacion,
						new BigDecimal(this.valorunidadsup), this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmtiposcotizaciones.crmtiposcotizacionesDelete(
						this.idtipocotizacion, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmtiposcotizaciones() {
		try {
			CRM crmtiposcotizaciones = Common.getCrm();
			List listCrmtiposcotizaciones = crmtiposcotizaciones
					.getCrmtiposcotizacionesPK(this.idtipocotizacion,
							this.idempresa);
			Iterator iterCrmtiposcotizaciones = listCrmtiposcotizaciones
					.iterator();
			if (iterCrmtiposcotizaciones.hasNext()) {
				String[] uCampos = (String[]) iterCrmtiposcotizaciones.next();
				// TODO: Constructores para cada tipo de datos
				this.idtipocotizacion = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.tipocotizacion = uCampos[1];
				this.valorunidadsup = uCampos[3] ;
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCrmtiposcotizaciones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmtiposcotizacionesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
					if (tipocotizacion.trim().length() == 0) {
						this.mensaje = "No se puede vacio el campo Tipo cotizacion.  ";
						return false;
					}
					if (!Common.esNumerico(this.valorunidadsup) ) {
						this.mensaje = "Valor unidad superficie debe ser un valor numérico.";
						return false;
					}
					
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCrmtiposcotizaciones();
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
	public BigDecimal getIdtipocotizacion() {
		return idtipocotizacion;
	}

	public void setIdtipocotizacion(BigDecimal idtipocotizacion) {
		this.idtipocotizacion = idtipocotizacion;
	}

	public String getTipocotizacion() {
		return tipocotizacion;
	}

	public void setTipocotizacion(String tipocotizacion) {
		this.tipocotizacion = tipocotizacion;
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

	public String getValorunidadsup() {
		return valorunidadsup;
	}

	public void setValorunidadsup(String valorunidadsup) {
		this.valorunidadsup = valorunidadsup;
	}
}
