/* 
   javabean para la entidad (Formulario): clientesIndicadoresTipos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Apr 29 15:16:19 GMT-03:00 2010 
   
   Para manejar la pagina: clientesIndicadoresTiposFrm.jsp
      
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

public class BeanClientesIndicadoresTiposFrm implements SessionBean,
		Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesIndicadoresTiposFrm.class);

	private String validar = "";

	private BigDecimal idtipoindicador = new BigDecimal(-1);

	private String tipoindicador = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientesIndicadoresTiposFrm() {
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
				this.mensaje = clientes.clientesIndicadoresTiposCreate(
						this.tipoindicador, this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.clientesIndicadoresTiposUpdate(
						this.idtipoindicador, this.tipoindicador,
						this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.clientesIndicadoresTiposDelete(
						this.idtipoindicador, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesIndicadoresTipos() {
		try {
			Clientes clientes = Common.getClientes();
			List listClientesIndicadoresTipos = clientes
					.getClientesIndicadoresTiposPK(this.idtipoindicador,
							this.idempresa);
			Iterator iterClientesIndicadoresTipos = listClientesIndicadoresTipos
					.iterator();
			if (iterClientesIndicadoresTipos.hasNext()) {
				String[] uCampos = (String[]) iterClientesIndicadoresTipos
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idtipoindicador = new BigDecimal(uCampos[0]);
				this.tipoindicador = uCampos[1];
				
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesIndicadoresTipos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesIndicadoresTiposAbm.jsp");
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
					getDatosClientesIndicadoresTipos();
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
	public BigDecimal getIdtipoindicador() {
		return idtipoindicador;
	}

	public void setIdtipoindicador(BigDecimal idtipoindicador) {
		this.idtipoindicador = idtipoindicador;
	}

	public String getTipoindicador() {
		return tipoindicador;
	}

	public void setTipoindicador(String tipoindicador) {
		this.tipoindicador = tipoindicador;
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
