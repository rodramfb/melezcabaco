/* 
 javabean para la entidad (Formulario): globalmonedas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 23 12:00:35 GMT-03:00 2007 
 
 Para manejar la pagina: globalmonedasFrm.jsp
 
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

public class BeanGlobalmonedasFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanGlobalmonedasFrm.class);

	private String validar = "";

	private BigDecimal idmoneda = BigDecimal.valueOf(-1);

	private String moneda = "";

	private BigDecimal idpais = BigDecimal.valueOf(0);

	private String pais = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanGlobalmonedasFrm() {
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
			General globalmonedas = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = globalmonedas.globalmonedasCreate(this.moneda,
						this.idpais, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = globalmonedas.globalmonedasUpdate(this.idmoneda,
						this.moneda, this.idpais, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = globalmonedas.globalmonedasDelete(this.idmoneda);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGlobalmonedas() {
		try {
			General globalmonedas = Common.getGeneral();
			List listGlobalmonedas = globalmonedas
					.getGlobalmonedasPK(this.idmoneda);
			Iterator iterGlobalmonedas = listGlobalmonedas.iterator();
			if (iterGlobalmonedas.hasNext()) {
				String[] uCampos = (String[]) iterGlobalmonedas.next();
				// TODO: Constructores para cada tipo de datos
				this.idmoneda = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.moneda = uCampos[1];
				this.idpais = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
				this.pais = uCampos[3];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGlobalmonedas()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("globalmonedasAbm.jsp");
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
					getDatosGlobalmonedas();
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
	public BigDecimal getIdmoneda() {
		return idmoneda;
	}

	public void setIdmoneda(BigDecimal idmoneda) {
		this.idmoneda = idmoneda;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getIdpais() {
		return idpais;
	}

	public void setIdpais(BigDecimal idpais) {
		this.idpais = idpais;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
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
