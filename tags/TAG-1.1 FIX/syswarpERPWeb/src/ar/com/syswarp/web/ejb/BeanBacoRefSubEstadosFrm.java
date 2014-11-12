/* 
   javabean para la entidad (Formulario): bacoRefSubEstados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Mar 13 15:00:55 ART 2012 
   
   Para manejar la pagina: bacoRefSubEstadosFrm.jsp
      
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

public class BeanBacoRefSubEstadosFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoRefSubEstadosFrm.class);

	private String validar = "";

	private BigDecimal idrefsubestado = new BigDecimal(-1);

	private String refsubestado = "";

	private BigDecimal idrefestado = new BigDecimal(-1);

	private String refestado = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanBacoRefSubEstadosFrm() {
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
				this.mensaje = clientes.bacoRefSubEstadosCreate(
						this.refsubestado, this.idrefestado, this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.bacoRefSubEstadosUpdate(
						this.idrefsubestado, this.refsubestado,
						this.idrefestado, this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.bacoRefSubEstadosDelete(
						this.idrefsubestado, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoRefSubEstados() {
		try {
			Clientes clientes = Common.getClientes();
			List listBacoRefSubEstados = clientes.getBacoRefSubEstadosPK(
					this.idrefsubestado, this.idempresa);
			Iterator iterBacoRefSubEstados = listBacoRefSubEstados.iterator();
			if (iterBacoRefSubEstados.hasNext()) {
				String[] uCampos = (String[]) iterBacoRefSubEstados.next();
				// TODO: Constructores para cada tipo de datos
				this.idrefsubestado = new BigDecimal(uCampos[0]);
				this.refsubestado = uCampos[1];
				this.idrefestado = new BigDecimal(uCampos[2]);
				this.idempresa = new BigDecimal(uCampos[3]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacoRefSubEstados()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacoRefSubEstadosAbm.jsp?idrefestado="
						+ this.idrefestado + "&refestado=" + this.refestado);
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
					getDatosBacoRefSubEstados();
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
	public BigDecimal getIdrefsubestado() {
		return idrefsubestado;
	}

	public void setIdrefsubestado(BigDecimal idrefsubestado) {
		this.idrefsubestado = idrefsubestado;
	}

	public String getRefsubestado() {
		return refsubestado;
	}

	public void setRefsubestado(String refsubestado) {
		this.refsubestado = refsubestado;
	}

	public BigDecimal getIdrefestado() {
		return idrefestado;
	}

	public void setIdrefestado(BigDecimal idrefestado) {
		this.idrefestado = idrefestado;
	}

	public String getRefestado() {
		return refestado;
	}

	public void setRefestado(String refestado) {
		this.refestado = refestado;
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
