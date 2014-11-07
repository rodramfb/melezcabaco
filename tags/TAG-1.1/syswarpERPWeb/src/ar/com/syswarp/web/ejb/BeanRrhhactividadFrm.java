/* 
 javabean para la entidad (Formulario): rrhhactividad
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Dec 14 15:38:32 GMT-03:00 2006 
 
 Para manejar la pagina: rrhhactividadFrm.jsp
 
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

public class BeanRrhhactividadFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRrhhactividadFrm.class);

	private String validar = "";

	private BigDecimal idactividad = BigDecimal.valueOf(-1);
	
	private BigDecimal idempresa ;

	private String actividad = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanRrhhactividadFrm() {
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
			RRHH rrhhactividad = Common.getRrhh();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = rrhhactividad.rrhhactividadCreate(
						this.actividad, this.usuarioalt,this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = rrhhactividad.rrhhactividadUpdate(
						this.idactividad, this.actividad, this.usuarioact,this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhhactividad
						.rrhhactividadDelete(this.idactividad,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhactividad() {
		try {
			RRHH rrhhactividad = Common.getRrhh();
			List listRrhhactividad = rrhhactividad
					.getRrhhactividadPK(this.idactividad,this.idempresa);
			Iterator iterRrhhactividad = listRrhhactividad.iterator();
			if (iterRrhhactividad.hasNext()) {
				String[] uCampos = (String[]) iterRrhhactividad.next();
				// TODO: Constructores para cada tipo de datos
				this.idactividad = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.actividad = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhactividad()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhactividadAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhactividad();
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
	public BigDecimal getIdactividad() {
		return idactividad;
	}

	public void setIdactividad(BigDecimal idactividad) {
		this.idactividad = idactividad;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
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
