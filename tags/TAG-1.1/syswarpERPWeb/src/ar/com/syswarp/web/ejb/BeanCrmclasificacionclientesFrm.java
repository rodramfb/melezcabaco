/* 
 javabean para la entidad (Formulario): crmclasificacionclientes
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jun 14 17:20:08 GMT-03:00 2007 
 
 Para manejar la pagina: crmclasificacionclientesFrm.jsp
 
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

public class BeanCrmclasificacionclientesFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmclasificacionclientesFrm.class);

	private String validar = "";

	private BigDecimal idclasificacioncliente = BigDecimal.valueOf(-1);

	private String clasificacioncliente = "";

	private BigDecimal idtipocliente = BigDecimal.valueOf(0);

	private String tipocliente = "";

	private String observaciones = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCrmclasificacionclientesFrm() {
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
			CRM crmclasificacionclientes = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmclasificacionclientes
						.crmclasificacionclientesCreate(
								this.clasificacioncliente, this.idtipocliente,
								this.observaciones, this.idempresa,
								this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmclasificacionclientes
						.crmclasificacionclientesUpdate(
								this.idclasificacioncliente,
								this.clasificacioncliente, this.idtipocliente,
								this.observaciones, this.usuarioact,
								this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmclasificacionclientes
						.crmclasificacionclientesDelete(
								this.idclasificacioncliente, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmclasificacionclientes() {
		try {
			CRM crmclasificacionclientes = Common.getCrm();
			List listCrmclasificacionclientes = crmclasificacionclientes
					.getCrmclasificacionclientesPK(this.idclasificacioncliente,
							this.idempresa);
			Iterator iterCrmclasificacionclientes = listCrmclasificacionclientes
					.iterator();
			if (iterCrmclasificacionclientes.hasNext()) {
				String[] uCampos = (String[]) iterCrmclasificacionclientes
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idclasificacioncliente = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.clasificacioncliente = uCampos[1];
				this.idtipocliente = BigDecimal.valueOf(Long
						.parseLong(uCampos[2]));
				this.tipocliente = uCampos[3];
				this.observaciones = uCampos[4];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCrmclasificacionclientes()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmclasificacionclientesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (clasificacioncliente == null) {
						this.mensaje = "No se puede dejar vacio el campo clasificacioncliente ";
						return false;
					}
					if (idtipocliente == null) {
						this.mensaje = "No se puede dejar vacio el campo idtipocliente ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (clasificacioncliente.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Clasificacion cliente  ";
						return false;
					}
					if (tipocliente.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo cliente  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCrmclasificacionclientes();
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
	public BigDecimal getIdclasificacioncliente() {
		return idclasificacioncliente;
	}

	public void setIdclasificacioncliente(BigDecimal idclasificacioncliente) {
		this.idclasificacioncliente = idclasificacioncliente;
	}

	public String getClasificacioncliente() {
		return clasificacioncliente;
	}

	public void setClasificacioncliente(String clasificacioncliente) {
		this.clasificacioncliente = clasificacioncliente;
	}

	public BigDecimal getIdtipocliente() {
		return idtipocliente;
	}

	public void setIdtipocliente(BigDecimal idtipocliente) {
		this.idtipocliente = idtipocliente;
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

	public String getTipocliente() {
		return tipocliente;
	}

	public void setTipocliente(String tipocliente) {
		this.tipocliente = tipocliente;
	}
}
