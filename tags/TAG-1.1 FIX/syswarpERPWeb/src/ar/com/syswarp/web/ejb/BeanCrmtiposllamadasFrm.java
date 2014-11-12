/* 
 javabean para la entidad (Formulario): crmtiposllamadas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jun 14 17:22:02 GMT-03:00 2007 
 
 Para manejar la pagina: crmtiposllamadasFrm.jsp
 
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

public class BeanCrmtiposllamadasFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmtiposllamadasFrm.class);

	private String validar = "";

	private BigDecimal idtipollamada = BigDecimal.valueOf(-1);

	private String tipollamada = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCrmtiposllamadasFrm() {
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
			CRM crmtiposllamadas = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmtiposllamadas.crmtiposllamadasCreate(
						this.tipollamada, this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmtiposllamadas.crmtiposllamadasUpdate(
						this.idtipollamada, this.tipollamada, this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmtiposllamadas.crmtiposllamadasDelete(
						this.idtipollamada, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmtiposllamadas() {
		try {
			CRM crmtiposllamadas = Common.getCrm();
			List listCrmtiposllamadas = crmtiposllamadas.getCrmtiposllamadasPK(
					this.idtipollamada, this.idempresa);
			Iterator iterCrmtiposllamadas = listCrmtiposllamadas.iterator();
			if (iterCrmtiposllamadas.hasNext()) {
				String[] uCampos = (String[]) iterCrmtiposllamadas.next();
				// TODO: Constructores para cada tipo de datos
				this.idtipollamada = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.tipollamada = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCrmtiposllamadas()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmtiposllamadasAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
					if (tipollamada.trim().length() == 0) {
						this.mensaje = "No se puede vacio el campo Tipo llamada  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCrmtiposllamadas();
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
	public BigDecimal getIdtipollamada() {
		return idtipollamada;
	}

	public void setIdtipollamada(BigDecimal idtipollamada) {
		this.idtipollamada = idtipollamada;
	}

	public String getTipollamada() {
		return tipollamada;
	}

	public void setTipollamada(String tipollamada) {
		this.tipollamada = tipollamada;
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
