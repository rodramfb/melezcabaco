/* 
 javabean para la entidad (Formulario): globalusuarios
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 23 13:30:47 GMT-03:00 2007 
 
 Para manejar la pagina: globalusuariosFrm.jsp
 
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

public class BeanGlobalusuariosFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanGlobalusuariosFrm.class);

	private String validar = "";

	private BigDecimal idusuario = BigDecimal.valueOf(-1);
	
	private BigDecimal idempresa;

	private String usuario = "";

	private String clave = "";

	private String email = "";

	private String nombre = "";

	private String habilitado = "";

	private String usuarioalt;

	private String usuarioact;

	private String idpuesto = "";

	private String puesto = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanGlobalusuariosFrm() {
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
			General globalusuarios = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = globalusuarios.globalusuariosCreate(
						this.usuario, this.clave, this.email, this.nombre,
						this.habilitado, this.usuarioalt, this.idpuesto,this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = globalusuarios.globalusuariosUpdate(
						this.idusuario, this.usuario, this.clave, this.email,
						this.nombre, this.habilitado, this.usuarioact,
						this.idpuesto,this.idempresa);
			if(Common.esEntero(this.mensaje))
				this.mensaje = "Usuario generado correctamente.";
			
			
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = globalusuarios
						.globalusuariosDelete(this.idusuario,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGlobalusuarios() {
		try {
			General globalusuarios = Common.getGeneral();
			List listGlobalusuarios = globalusuarios
					.getGlobalusuariosPK(this.idusuario,this.idempresa);
			Iterator iterGlobalusuarios = listGlobalusuarios.iterator();
			if (iterGlobalusuarios.hasNext()) {
				String[] uCampos = (String[]) iterGlobalusuarios.next();
				// TODO: Constructores para cada tipo de datos
				this.idusuario = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.usuario = uCampos[1];
				this.clave = uCampos[2];
				this.email = uCampos[3];
				this.nombre = uCampos[4];
				this.habilitado = uCampos[5];
				this.idpuesto = uCampos[6];
				this.puesto = uCampos[7];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGlobalusuarios()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("globalusuariosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (usuario == null) {
						this.mensaje = "No se puede dejar vacio el campo usuario ";
						return false;
					}
					if (clave == null) {
						this.mensaje = "No se puede dejar vacio el campo clave ";
						return false;
					}
					if (email == null) {
						this.mensaje = "No se puede dejar vacio el campo email ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (usuario.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Usuario  ";
						return false;
					}
					if (clave.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Clave  ";
						return false;
					}
					if (email.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo E-mail  ";
						return false;
					}
					if (nombre.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Nombre  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGlobalusuarios();
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
	public BigDecimal getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(BigDecimal idusuario) {
		this.idusuario = idusuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
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

	public String getIdpuesto() {
		return idpuesto;
	}

	public void setIdpuesto(String idpuesto) {
		this.idpuesto = idpuesto;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
