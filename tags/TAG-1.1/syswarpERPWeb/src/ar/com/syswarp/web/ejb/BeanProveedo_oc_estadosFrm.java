/* 
 javabean para la entidad (Formulario): proveedo_oc_estados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Mar 27 11:28:28 GMT-03:00 2007 
 
 Para manejar la pagina: proveedo_oc_estadosFrm.jsp
 
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

public class BeanProveedo_oc_estadosFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProveedo_oc_estadosFrm.class);

	private String validar = "";

	private BigDecimal idestadooc = BigDecimal.valueOf(-1);

	private String estadooc = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanProveedo_oc_estadosFrm() {
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
			Proveedores proveedo_oc_estados = Common.getProveedores();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = proveedo_oc_estados.proveedo_oc_estadosCreate(
						this.estadooc, this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = proveedo_oc_estados.proveedo_oc_estadosUpdate(
						this.idestadooc, this.estadooc, this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = proveedo_oc_estados.proveedo_oc_estadosDelete(
						this.idestadooc, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProveedo_oc_estados() {
		try {
			Proveedores proveedo_oc_estados = Common.getProveedores();
			List listProveedo_oc_estados = proveedo_oc_estados
					.getProveedo_oc_estadosPK(this.idestadooc, this.idempresa);
			Iterator iterProveedo_oc_estados = listProveedo_oc_estados
					.iterator();
			if (iterProveedo_oc_estados.hasNext()) {
				String[] uCampos = (String[]) iterProveedo_oc_estados.next();
				// TODO: Constructores para cada tipo de datos
				this.idestadooc = BigDecimal
						.valueOf(Long.parseLong(uCampos[0]));
				this.estadooc = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProveedo_oc_estados()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("proveedo_oc_estadosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos

					if (estadooc.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Estado oc ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosProveedo_oc_estados();
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
	public BigDecimal getIdestadooc() {
		return idestadooc;
	}

	public void setIdestadooc(BigDecimal idestadooc) {
		this.idestadooc = idestadooc;
	}

	public String getEstadooc() {
		return estadooc;
	}

	public void setEstadooc(String estadooc) {
		this.estadooc = estadooc;
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
}
