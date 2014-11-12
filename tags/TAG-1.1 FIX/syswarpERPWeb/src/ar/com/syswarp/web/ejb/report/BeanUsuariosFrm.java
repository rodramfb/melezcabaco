package ar.com.syswarp.web.ejb.report;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import java.util.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

/**
 * XDoclet-based session bean. The class must be declared public according to
 * the EJB specification.
 * 
 * To generate the EJB related files to this EJB: - Add Standard EJB module to
 * XDoclet project properties - Customize XDoclet configuration for your
 * appserver - Run XDoclet
 * 
 * Below are the xdoclet-related tags needed for this EJB.
 * 
 * @ejb.bean name="BeanUsuariosFrm" display-name="Name for BeanUsuariosFrm"
 *           description="Description for BeanUsuariosFrm"
 *           jndi-name="ejb/BeanUsuariosFrm" type="Stateful" view-type="remote"
 */

public class BeanUsuariosFrm implements SessionBean, Serializable {

	/** The session context */
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanUsuariosFrm.class);

	private String validar = "";

	private long idusuario = 0L;

	private String usuario = "";

	private String clave = "";

	private String claveContraste = "";

	private int administrador = 0;

	private String email = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String usuarioalt = "";

	private String usuarioact = "";

	private String accion = "";

	public BeanUsuariosFrm() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation.
	 * 
	 * The enterprise bean instance should store the reference to the context
	 * object in an instance variable.
	 * 
	 * This method is called with no transaction context.
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error.
	 */
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

	/*
	 * ====================================================================================
	 * VER COMMON
	 */
	public void ejecutarSentenciaDML() {

		try {
			Report reporting = Common.getReport();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = reporting.usuarioCreate(this.usuario,
						this.clave, this.administrador, this.email,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = reporting.usuarioUpdate(this.idusuario,
						this.usuario, this.clave, this.administrador,
						this.email, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = reporting.usuarioDelete(this.idusuario);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}

	}

	public void getDatosUsuario() {

		try {

			Report reporting = Common.getReport();
			List listUsuario = reporting.getUsuario(this.idusuario);
			Iterator iterUsuario = listUsuario.iterator();
			if (iterUsuario.hasNext()) {
				String[] uCampos = (String[]) iterUsuario.next();
				this.usuario = uCampos[1];
				this.clave = uCampos[2];
				this.administrador = Integer.parseInt(uCampos[3]);
				this.email = uCampos[4];
			} else {
				this.mensaje = "Imposible recuperar datos para el usuario seleccionado.";
			}

		} catch (Exception e) {
			log.error("getDatosUsuario()" + e);
		}

	}

	/*
	 * ====================================================================================
	 */

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("usuariosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					if (usuario.trim().length() == 0) {
						this.mensaje = "Longitud 0 usuario.";
						return false;
					}

					if (clave.trim().length() < 5) {
						this.mensaje = "Clave demasiado corta.";
						return false;
					}
					if (email.trim().length() == 0) {
						this.mensaje = "Longitud 0 mail.";
						return false;
					}

					if (usuario.indexOf("'") >= 0) {
						this.mensaje = "Caracteres Inv�lidos Campo Usuario.";
						return false;
					}
					if (clave.indexOf("'") >= 0) {
						this.mensaje = "Caracteres Inv�lidos Campo Clave.";
						return false;
					}

					if (!clave.equals(claveContraste)) {
						this.mensaje = "Ingrese Nuevamente clave, difiere con la reingresada.";
						this.clave = "";
						this.claveContraste = "";
						return false;
					}

					if (email.indexOf("'") >= 0) {
						this.mensaje = "Caracteres Inv�lidos Campo Email.";
						return false;
					}
					if (email.indexOf("@") <= 0) {
						this.mensaje = "Mail Inv�lido. (1)";
						return false;
					}
					if (email.indexOf(".") <= 0) {
						this.mensaje = "Mail Inv�lido. (2)";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosUsuario();
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

	public int getAdministrador() {
		return administrador;
	}

	public void setAdministrador(int administrador) {
		this.administrador = administrador;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(long idusuario) {
		this.idusuario = idusuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getclaveContraste() {
		return claveContraste;
	}

	public void setclaveContraste(String contrastarClave) {
		this.claveContraste = contrastarClave;
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

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

}
