/* 
   javabean para la entidad (Formulario): sastiposcontactos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri May 27 09:12:29 ART 2011 
   
   Para manejar la pagina: sastiposcontactosFrm.jsp
      
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

public class BeanSastiposcontactosFrm implements SessionBean, Serializable {
	private SessionContext context;
	static Logger log = Logger.getLogger(BeanSastiposcontactosFrm.class);
	private String validar = "";
	private BigDecimal idtipocontacto = new BigDecimal(-1);
	private String tipocontacto = "";
	private String usuarioalt;
	private String usuarioact;
	private String mensaje = "";
	private String volver = "";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String accion = "";
	private BigDecimal idempresa = new BigDecimal(-1);

	public BeanSastiposcontactosFrm() {
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
			Clientes clientes = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientes.sastiposcontactosCreate(
						this.tipocontacto, this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.sastiposcontactosUpdate(
						this.idtipocontacto, this.tipocontacto,
						this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.sastiposcontactosDelete(
						this.idtipocontacto, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosSastiposcontactos() {
		try {
			Clientes clientes = Common.getClientes();
			List listSastiposcontactos = clientes.getSastiposcontactosPK(
					this.idtipocontacto, this.idempresa);
			Iterator iterSastiposcontactos = listSastiposcontactos.iterator();
			if (iterSastiposcontactos.hasNext()) {
				String[] uCampos = (String[]) iterSastiposcontactos.next();
				// TODO: Constructores para cada tipo de datos
				this.idtipocontacto = new BigDecimal(uCampos[0]);
				this.tipocontacto = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosSastiposcontactos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("sastiposcontactosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (tipocontacto == null) {
						this.mensaje = "No puede dejar vacio el campo Tipo de Contacto";
						return false;
					}
					if (tipocontacto.trim().equalsIgnoreCase("")) {
						this.mensaje = "No puede dejar vacio el campo Tipo de Contacto";
						return false;
					}
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosSastiposcontactos();
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
	public BigDecimal getIdtipocontacto() {
		return idtipocontacto;
	}

	public void setIdtipocontacto(BigDecimal idtipocontacto) {
		this.idtipocontacto = idtipocontacto;
	}

	public String getTipocontacto() {
		return tipocontacto;
	}

	public void setTipocontacto(String tipocontacto) {
		this.tipocontacto = tipocontacto;
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
