/* 
 javabean para la entidad (Formulario): clientesestados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Mar 02 11:31:57 GMT-03:00 2007 
 
 Para manejar la pagina: clientesestadosFrm.jsp
 
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

public class BeanClientesestadosFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesestadosFrm.class);

	private String validar = "";

	private BigDecimal idestado = BigDecimal.valueOf(-1);
	
	private BigDecimal idempresa;

	private String estado = "";

	private String fechasn = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientesestadosFrm() {
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
			Clientes clientesestados = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientesestados.clientesestadosCreate(
						this.estado, this.fechasn, this.usuarioalt,this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientesestados.clientesestadosUpdate(
						this.idestado, this.estado, this.fechasn,
						this.usuarioact,this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientesestados
						.clientesestadosDelete(this.idestado,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesestados() {
		try {
			Clientes clientesestados = Common.getClientes();
			List listClientesestados = clientesestados
					.getClientesestadosPK(this.idestado,this.idempresa);
			Iterator iterClientesestados = listClientesestados.iterator();
			if (iterClientesestados.hasNext()) {
				String[] uCampos = (String[]) iterClientesestados.next();
				// TODO: Constructores para cada tipo de datos
				this.idestado = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.estado = uCampos[1];
				this.fechasn = uCampos[2];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesestados()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesestadosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
					if (estado.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Estado  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesestados();
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
	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechasn() {
		return fechasn;
	}

	public void setFechasn(String fechasn) {
		this.fechasn = fechasn;
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
