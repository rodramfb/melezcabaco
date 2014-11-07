package ar.com.syswarp.web.ejb.report;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.com.syswarp.api.Common;
import ar.com.syswarp.ejb.Report;

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
 * @ejb.bean name="BeanGruposFrm" display-name="Name for BeanGruposFrm"
 *           description="Description for BeanGruposFrm"
 *           jndi-name="ejb/BeanGruposFrm" type="Stateful" view-type="remote"
 */
public class BeanGruposFrm implements SessionBean, Serializable {

	/** The session context */
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanGruposFrm.class);

	private String validar = "";

	private long idgrupo = 0L;

	private String grupo = "";

	private String descripcion = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String usuarioalt = "";

	private String usuarioact = "";

	private String accion = "";

	public BeanGruposFrm() {
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

	public void ejecutarSentenciaDML() {

		try {
			Report reporting = Common.getReport();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = reporting.grupoCreate(this.grupo,
						this.descripcion, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = reporting.grupoUpdate(this.idgrupo, this.grupo,
						this.descripcion, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = reporting.grupoDelete(this.idgrupo);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}

	}

	public void getDatosGrupo() {

		try {

			Report reporting = Common.getReport();
			List listGrupo = reporting.getGrupo(this.idgrupo);
			Iterator iterGrupo = listGrupo.iterator();
			if (iterGrupo.hasNext()) {
				String[] uCampos = (String[]) iterGrupo.next();
				this.grupo = uCampos[1];
				this.descripcion = uCampos[2];
			} else {
				this.mensaje = "Imposible recuperar datos para el grupo seleccionado.";
			}

		} catch (Exception e) {
			log.error("getDatosGrupo()" + e);
		}

	}

	/*
	 * ====================================================================================
	 */

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("gruposAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					if (grupo.trim().length() == 0) {
						this.mensaje = "Ingrese grupo.";
						return false;
					}

					if (descripcion.trim().length() == 0) {
						this.mensaje = "Ingrese descripci�n.";
						return false;
					}

					if (grupo.indexOf("'") >= 0) {
						this.mensaje = "Caracteres Inv�lidos Campo Grupo.";
						return false;
					}

					if (descripcion.indexOf("'") >= 0) {
						this.mensaje = "Caracteres Inv�lidos Campo Descripci�n.";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGrupo();
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(long idgrupo) {
		this.idgrupo = idgrupo;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
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