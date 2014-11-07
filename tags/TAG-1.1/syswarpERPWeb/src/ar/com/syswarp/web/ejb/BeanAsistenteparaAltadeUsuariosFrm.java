/* 
 javabean para la entidad (Formulario): globalusuarios
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 23 13:30:47 GMT-03:00 2007 
 
 Para manejar la pagina: globalusuariosFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.com.syswarp.api.Common;
import ar.com.syswarp.ejb.General;

public class BeanAsistenteparaAltadeUsuariosFrm implements SessionBean,
		Serializable {
	private static final long serialVersionUID = -661562088971827628L;

	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanAsistenteparaAltadeUsuariosFrm.class);

	private String validar = "";

	private BigDecimal idusuario = BigDecimal.valueOf(-1);

	private BigDecimal idempresa;

	private String usuario = "";

	private String siguiente = "";

	private Connection dbconn;

	private String clave = "";

	private String email = "";

	private String nombre = "";

	private String enviar = "";

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

	public BeanAsistenteparaAltadeUsuariosFrm() {
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
		if (idusuario != null && idusuario.compareTo(BigDecimal.ZERO) > 0) {
			return;
		}
		try {
			General globalusuarios = Common.getGeneral();
			if (!this.validar.equalsIgnoreCase("")) {
				this.mensaje = globalusuarios.globalusuariosCreate(
						this.usuario, this.clave, this.email, this.nombre,
						this.habilitado, this.usuarioalt, this.idpuesto,
						this.idempresa);

				log.error(String.format("Returned message: %s", this.mensaje));
				System.out.println(String.format("Returned message: %s",
						this.mensaje));

				if (this.mensaje == null) {
					this.mensaje = "Mensaje es null";
					return;
				} else if (mensaje.trim().equalsIgnoreCase("null")) {
					this.mensaje = "Mensaje es el texto null";
					return;
				} else {
					this.idusuario = Common.getNumberFromString(this.mensaje);
					if (this.idusuario != null
							&& !idusuario.equals(BigDecimal.ZERO)) {
						this.mensaje = "Usuario generado correctamente.";
					} else {
						this.mensaje = "Usuario no se ha generado.";
					}
				}

				// if(Common.esEntero(this.mensaje)){
				// this.idusuario = new BigDecimal(this.mensaje);
				// this.mensaje = "Usuario generado correctamente.";
				// }

			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.siguiente.equalsIgnoreCase("")) {
				if (idusuario == null) {
					this.mensaje = "Id Usuario es null";
					return false;
				} else {
					System.out.println("IDUSUARIO: " + idusuario);
					int compare = idusuario.compareTo(BigDecimal.ZERO);
					if (compare < 0) {
						this.mensaje = "Por favor debe ingresar un usuario, grabar y despues hacer siguiente!";
						return false;
					} else if (compare == 0) {
						this.mensaje = "El usuario no fue creado o no se ha retornado el id desde la base de datos";
						return false;
					} else if (compare > 0) {
						this.mensaje = "El usuario fue creado exitosamente, será redirigido";
						response.sendRedirect("asistente2abm.jsp?idusuario="
								+ idusuario);
						return true;
					}
				}

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
					// getDatosGlobalusuarios();
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

	public String getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(String siguiente) {
		this.siguiente = siguiente;
	}

	public String getEnviar() {
		return enviar;
	}

	public void setEnviar(String enviar) {
		this.enviar = enviar;
	}
}
