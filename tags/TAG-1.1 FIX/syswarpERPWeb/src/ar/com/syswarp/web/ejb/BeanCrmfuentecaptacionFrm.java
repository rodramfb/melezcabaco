/* 
 javabean para la entidad (Formulario): crmfuentecaptacion
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jun 14 17:21:14 GMT-03:00 2007 
 
 Para manejar la pagina: crmfuentecaptacionFrm.jsp
 
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

public class BeanCrmfuentecaptacionFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmfuentecaptacionFrm.class);

	private String validar = "";

	private BigDecimal idfuente = BigDecimal.valueOf(-1);

	private String fuente = "";

	private String valorpresupuesto = "";

	private String valorunitario = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCrmfuentecaptacionFrm() {
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
			CRM crmfuentecaptacion = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmfuentecaptacion.crmfuentecaptacionCreate(
						this.fuente, new BigDecimal(this.valorpresupuesto),
						new BigDecimal(this.valorunitario), this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmfuentecaptacion.crmfuentecaptacionUpdate(
						this.idfuente, this.fuente, new BigDecimal(
								this.valorpresupuesto), new BigDecimal(
								this.valorunitario), this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmfuentecaptacion.crmfuentecaptacionDelete(
						this.idfuente, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmfuentecaptacion() {
		try {
			CRM crmfuentecaptacion = Common.getCrm();
			List listCrmfuentecaptacion = crmfuentecaptacion
					.getCrmfuentecaptacionPK(this.idfuente, this.idempresa);
			Iterator iterCrmfuentecaptacion = listCrmfuentecaptacion.iterator();
			if (iterCrmfuentecaptacion.hasNext()) {
				String[] uCampos = (String[]) iterCrmfuentecaptacion.next();
				// TODO: Constructores para cada tipo de datos
				this.idfuente = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.fuente = uCampos[1];
				this.valorpresupuesto = uCampos[2];
				this.valorunitario = uCampos[3];
				this.idempresa = BigDecimal.valueOf(Long.parseLong(uCampos[4]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCrmfuentecaptacion()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmfuentecaptacionAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (fuente == null) {
						this.mensaje = "No se puede dejar vacio el campo fuente ";
						return false;
					}
					if (valorpresupuesto == null) {
						this.mensaje = "No se puede dejar vacio el campo valorpresupuesto ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (fuente.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Fuente  ";
						return false;
					}
					if (!Common.esNumerico(valorpresupuesto)) {
						this.mensaje = "Ingrese valor numérico válido para el campo valor presupuesto ";
						return false;
					}
					if (!Common.esNumerico(valorunitario)) {
						this.mensaje = "Ingrese valor numérico válido para el campo valor unitario ";
						return false;
					}

					if (Double.parseDouble(valorunitario) > Double
							.parseDouble(valorpresupuesto)) {
						this.mensaje = "Valor unitario no puede superar a valor presupuesto.";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCrmfuentecaptacion();
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
	public BigDecimal getIdfuente() {
		return idfuente;
	}

	public void setIdfuente(BigDecimal idfuente) {
		this.idfuente = idfuente;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public String getValorpresupuesto() {
		return valorpresupuesto;
	}

	public void setValorpresupuesto(String valorpresupuesto) {
		this.valorpresupuesto = valorpresupuesto;
	}

	public String getValorunitario() {
		return valorunitario;
	}

	public void setValorunitario(String valorunitario) {
		this.valorunitario = valorunitario;
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
