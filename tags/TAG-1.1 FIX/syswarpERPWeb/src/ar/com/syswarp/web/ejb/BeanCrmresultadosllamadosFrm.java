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

public class BeanCrmresultadosllamadosFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmresultadosllamadosFrm.class);

	private String validar = "";

	private BigDecimal idresultadollamada = BigDecimal.valueOf(-1);

	private String resultadollamada = "";


	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCrmresultadosllamadosFrm() {
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
			CRM crmresultadosllamados = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmresultadosllamados.crmresultadosllamadosCreate(
						this.resultadollamada,  this.idempresa,	this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmresultadosllamados.crmresultadosllamadosUpdate(
						this.idresultadollamada, this.resultadollamada, this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmresultadosllamados.crmresultadosllamadosDelete(
						this.idresultadollamada, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatoscrmresultadosllamados() {
		try {
			CRM crmresultadosllamados = Common.getCrm();
			List listcrmresultadosllamados = crmresultadosllamados
					.getcrmresultadosllamadosPK(this.idresultadollamada, this.idempresa);
			Iterator itercrmresultadosllamados = listcrmresultadosllamados.iterator();
			if (itercrmresultadosllamados.hasNext()) {
				String[] uCampos = (String[]) itercrmresultadosllamados.next();
				// TODO: Constructores para cada tipo de datos
				this.idresultadollamada = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.resultadollamada = uCampos[1];
				this.idempresa = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatoscrmresultadosllamados()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmresultadosllamadosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (resultadollamada == null) {
						this.mensaje = "No se puede dejar vacio el campo resultadollamada ";
						return false;
					}
					
					// 2. len 0 para campos nulos
					if (resultadollamada.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo resultadollamada  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatoscrmresultadosllamados();
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
	public BigDecimal getidresultadollamada() {
		return idresultadollamada;
	}

	public void setidresultadollamada(BigDecimal idresultadollamada) {
		this.idresultadollamada = idresultadollamada;
	}

	public String getresultadollamada() {
		return resultadollamada;
	}

	public void setresultadollamada(String resultadollamada) {
		this.resultadollamada = resultadollamada;
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
