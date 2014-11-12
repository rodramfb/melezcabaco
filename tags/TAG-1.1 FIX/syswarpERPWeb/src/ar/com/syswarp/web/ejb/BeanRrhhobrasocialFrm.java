/* 
 javabean para la entidad (Formulario): rrhhobrasocial
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Apr 30 16:39:32 GMT-03:00 2007 
 
 Para manejar la pagina: rrhhobrasocialFrm.jsp
 
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

public class BeanRrhhobrasocialFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRrhhobrasocialFrm.class);

	private String validar = "";

	private BigDecimal idobrasocial= BigDecimal.valueOf(-1);
	
	private BigDecimal idempresa ;

	private String obrasocial = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanRrhhobrasocialFrm() {
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
			RRHH rrhhobrasocial = Common.getRrhh();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = rrhhobrasocial.rrhhobrasocialCreate(
						this.obrasocial, this.usuarioalt,this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = rrhhobrasocial.rrhhobrasocialUpdate(
						this.idobrasocial, this.obrasocial, this.usuarioact,this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhhobrasocial
						.rrhhobrasocialDelete(this.idobrasocial,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhobrasocial() {
		try {
			RRHH rrhhobrasocial = Common.getRrhh();
			List listRrhhobrasocial = rrhhobrasocial
					.getRrhhobrasocialPK(this.idobrasocial,this.idempresa);
			Iterator iterRrhhobrasocial = listRrhhobrasocial.iterator();
			if (iterRrhhobrasocial.hasNext()) {
				String[] uCampos = (String[]) iterRrhhobrasocial.next();
				// TODO: Constructores para cada tipo de datos
				this.idobrasocial = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.obrasocial = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhobrasocial()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhobrasocialAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
					if (obrasocial.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Obra Social  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhobrasocial();
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
	public BigDecimal getIdobrasocial() {
		return idobrasocial;
	}

	public void setIdobrasocial(BigDecimal idobrasocial) {
		this.idobrasocial = idobrasocial;
	}

	public String getObrasocial() {
		return obrasocial;
	}

	public void setObrasocial(String obrasocial) {
		this.obrasocial = obrasocial;
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
