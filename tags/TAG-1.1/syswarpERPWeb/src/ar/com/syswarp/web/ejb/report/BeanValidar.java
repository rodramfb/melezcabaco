package ar.com.syswarp.web.ejb.report;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import java.util.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.*;

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
 * @ejb.bean name="BeanValidar" display-name="Name for BeanValidar"
 *           description="Description for BeanValidar"
 *           jndi-name="ejb/BeanValidar" type="Stateful" view-type="remote"
 */
public class BeanValidar implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanValidar.class);

	/** The session context */
	private SessionContext context;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	private String usuario = "";

	private String password = "";

	private String ingreso = "";

	private String mensaje = "";

	public BeanValidar() {

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

	/**
	 * An example business method
	 * 
	 * @ejb.interface-method view-type = "remote"
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error.
	 */
	public void replaceWithRealBusinessMethod() throws EJBException {
		// rename and start putting your business logic here

	}

	public boolean ejecutarValidacion() throws IOException {
		Report reporting = null;
		List listUsuario = null;
		if (!ingreso.equalsIgnoreCase("")) {
			if (this.usuario.trim().equalsIgnoreCase("")) {
				this.mensaje = "Debe ingresar usuario";
				return false;
			}
			if (this.password.trim().equalsIgnoreCase("")) {
				this.mensaje = "Debe ingresar clave.";
				return false;
			}
			if (this.usuario.indexOf("'") > 0) {
				this.mensaje = "Caracteres inv�lidos en campo usuario";
				return false;
			}

			if (this.password.indexOf("'") > 0) {
				this.mensaje = "Caracteres inv�lidos en campo clave";
				return false;
			}

			reporting = Common.getReport();
			listUsuario = reporting.getValidarUsuario(this.usuario,
					this.password);
			Iterator iterUsuario = listUsuario.iterator();

			if (iterUsuario.hasNext()) {
				java.util.Enumeration enume = session.getAttributeNames();
				while (enume.hasMoreElements()) {
					String nom = (String) enume.nextElement();
					this.session.removeAttribute(nom);
				}
				String[] uCampos = (String[]) iterUsuario.next();
				this.session.setAttribute("idusuario", uCampos[0]);
				this.session.setAttribute("usuario", uCampos[1]);
				this.session.setAttribute("administrador", uCampos[3]);
				this.session.setAttribute("email", uCampos[4]);

			} else {
				this.mensaje = "Usuario Inv�lido";
				return false;
			}
			java.util.List variables = new java.util.ArrayList();
			variables = reporting.getVariables();
			Iterator iterVariables = null;
			iterVariables = variables.iterator();
			while (iterVariables.hasNext()) {
				String[] sCampos = (String[]) iterVariables.next();
				String cmp_variable = sCampos[0];
				String cmp_valor = sCampos[1];
				session.setAttribute(cmp_variable, cmp_valor);
			}
			this.response.sendRedirect("inicial.jsp");

		}
		return true;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getIngreso() {
		return ingreso;
	}

	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

}
