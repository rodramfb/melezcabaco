/* 
   javabean para la entidad (Formulario): bacoRefTipoOperaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Jul 02 09:36:21 ART 2010 
   
   Para manejar la pagina: bacoRefTipoOperacionesFrm.jsp
      
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

public class BeanBacoRefTipoOperacionesFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoRefTipoOperacionesFrm.class);

	private String validar = "";

	private BigDecimal idtipooperacion;

	private String tipooperacion;

	private String observaciones;

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanBacoRefTipoOperacionesFrm() {
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
				this.mensaje = clientes.bacoRefTipoOperacionesCreate(
						this.tipooperacion, this.observaciones, this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.bacoRefTipoOperacionesUpdate(
						this.idtipooperacion, this.tipooperacion,
						this.observaciones, this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.bacoRefTipoOperacionesDelete(
						this.idtipooperacion, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoRefTipoOperaciones() {
		try {
			Clientes clientes = Common.getClientes();
			List listBacoRefTipoOperaciones = clientes
					.getBacoRefTipoOperacionesPK(this.idtipooperacion,
							this.idempresa);
			Iterator iterBacoRefTipoOperaciones = listBacoRefTipoOperaciones
					.iterator();
			if (iterBacoRefTipoOperaciones.hasNext()) {
				String[] uCampos = (String[]) iterBacoRefTipoOperaciones.next();
				// TODO: Constructores para cada tipo de datos
				this.idtipooperacion = new BigDecimal(uCampos[0]);
				this.tipooperacion = uCampos[1];
				this.observaciones = uCampos[2];
				this.idempresa = new BigDecimal(uCampos[3]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacoRefTipoOperaciones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacoRefTipoOperacionesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (tipooperacion == null) {
						this.mensaje = "No se puede dejar vacio el campo tipooperacion ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (tipooperacion.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo tipooperacion  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosBacoRefTipoOperaciones();
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
	public BigDecimal getIdtipooperacion() {
		return idtipooperacion;
	}

	public void setIdtipooperacion(BigDecimal idtipooperacion) {
		this.idtipooperacion = idtipooperacion;
	}

	public String getTipooperacion() {
		return tipooperacion;
	}

	public void setTipooperacion(String tipooperacion) {
		this.tipooperacion = tipooperacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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
