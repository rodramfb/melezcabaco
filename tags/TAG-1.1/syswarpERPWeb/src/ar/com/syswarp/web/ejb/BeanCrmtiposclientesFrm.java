/* 
 javabean para la entidad (Formulario): crmtiposclientes
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jun 14 17:19:12 GMT-03:00 2007 
 
 Para manejar la pagina: crmtiposclientesFrm.jsp
 
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

public class BeanCrmtiposclientesFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmtiposclientesFrm.class);

	private String validar = "";

	private BigDecimal idtipocliente = BigDecimal.valueOf(-1);

	private String tipocliente = "";

	private String observaciones = "";

	private BigDecimal idempresa = BigDecimal.valueOf(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCrmtiposclientesFrm() {
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
			CRM crmtiposclientes = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmtiposclientes.crmtiposclientesCreate(
						this.tipocliente, this.observaciones, this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmtiposclientes.crmtiposclientesUpdate(
						this.idtipocliente, this.tipocliente,
						this.observaciones, this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmtiposclientes.crmtiposclientesDelete(
						this.idtipocliente, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmtiposclientes() {
		try {
			CRM crmtiposclientes = Common.getCrm();
			List listCrmtiposclientes = crmtiposclientes.getCrmtiposclientesPK(
					this.idtipocliente, this.idempresa);
			Iterator iterCrmtiposclientes = listCrmtiposclientes.iterator();
			if (iterCrmtiposclientes.hasNext()) {
				String[] uCampos = (String[]) iterCrmtiposclientes.next();
				// TODO: Constructores para cada tipo de datos
				this.idtipocliente = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.tipocliente = uCampos[1];
				this.observaciones = uCampos[2];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCrmtiposclientes()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmtiposclientesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (tipocliente == null) {
						this.mensaje = "No se puede dejar vacio el campo tipocliente ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (tipocliente.trim().length() == 0) {
						this.mensaje = "No se puede vacio el campo Tipo cliente  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCrmtiposclientes();
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
	public BigDecimal getIdtipocliente() {
		return idtipocliente;
	}

	public void setIdtipocliente(BigDecimal idtipocliente) {
		this.idtipocliente = idtipocliente;
	}

	public String getTipocliente() {
		return tipocliente;
	}

	public void setTipocliente(String tipocliente) {
		this.tipocliente = tipocliente;
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
}
