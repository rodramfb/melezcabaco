/* 
 javabean para la entidad (Formulario): tipo_parametro
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jul 04 14:47:30 GMT-03:00 2006 
 
 Para manejar la pagina: tipo_parametroFrm.jsp
 
 */
package ar.com.syswarp.web.ejb.report;

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

public class BeanTipo_parametroFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanTipo_parametroFrm.class);

	private String validar = "";

	private BigDecimal idtipoparametro = BigDecimal.valueOf(-1);

	private String tipoparametro = "";

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanTipo_parametroFrm() {
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
			Report reporting = Common.getReport();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = reporting.tipo_parametroCreate(
						this.tipoparametro, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = reporting.tipo_parametroUpdate(
						this.idtipoparametro, this.tipoparametro,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = reporting
						.tipo_parametroDelete(this.idtipoparametro);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosTipo_parametro() {
		try {
			Report reporting = Common.getReport();
			List listTipo_parametro = reporting
					.getTipo_parametroPK(this.idtipoparametro);
			Iterator iterTipo_parametro = listTipo_parametro.iterator();
			if (iterTipo_parametro.hasNext()) {
				String[] uCampos = (String[]) iterTipo_parametro.next();
				// TODO: Constructores para cada tipo de datos
				this.idtipoparametro = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.tipoparametro = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosTipo_parametro()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("tipo_parametroAbm.jsp");
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
					getDatosTipo_parametro();
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
	public BigDecimal getIdtipoparametro() {
		return idtipoparametro;
	}

	public void setIdtipoparametro(BigDecimal idtipoparametro) {
		this.idtipoparametro = idtipoparametro;
	}

	public String getTipoparametro() {
		return tipoparametro;
	}

	public void setTipoparametro(String tipoparametro) {
		this.tipoparametro = tipoparametro;
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
