/* 
 javabean para la entidad (Formulario): Cajatipoidentificadores
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:48:54 GMT-03:00 2006 
 
 Para manejar la pagina: CajatipoidentificadoresFrm.jsp
 
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

public class BeanCajatipoidentificadoresFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanCajatipoidentificadoresFrm.class);

	private String validar = "";

	private BigDecimal idtipoidentificador = BigDecimal.valueOf(-1);

	private String tipoidentificador = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCajatipoidentificadoresFrm() {
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
			Tesoreria Cajatipoidentificadores = Common.getTesoreria();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = Cajatipoidentificadores
						.CajatipoidentificadoresCreate(this.tipoidentificador,
								this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = Cajatipoidentificadores
						.CajatipoidentificadoresUpdate(
								this.idtipoidentificador,
								this.tipoidentificador, this.usuarioact,
								this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = Cajatipoidentificadores
						.CajatipoidentificadoresDelete(
								this.idtipoidentificador, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCajatipoidentificadores() {
		try {
			Tesoreria Cajatipoidentificadores = Common.getTesoreria();
			List listCajatipoidentificadores = Cajatipoidentificadores
					.getCajatipoidentificadoresPK(this.idtipoidentificador,
							this.idempresa);
			Iterator iterCajatipoidentificadores = listCajatipoidentificadores
					.iterator();
			if (iterCajatipoidentificadores.hasNext()) {
				String[] uCampos = (String[]) iterCajatipoidentificadores
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idtipoidentificador = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.tipoidentificador = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCajatipoidentificadores()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("CajatipoidentificadoresAbm.jsp");
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
					getDatosCajatipoidentificadores();
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
	public BigDecimal getIdtipoidentificador() {
		return idtipoidentificador;
	}

	public void setIdtipoidentificador(BigDecimal idtipoidentificador) {
		this.idtipoidentificador = idtipoidentificador;
	}

	public String getTipoidentificador() {
		return tipoidentificador;
	}

	public void setTipoidentificador(String tipoidentificador) {
		this.tipoidentificador = tipoidentificador;
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
