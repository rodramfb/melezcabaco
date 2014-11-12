/* 
 javabean para la entidad (Formulario): marketRegistro
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Mar 14 11:34:39 ART 2008 
 
 Para manejar la pagina: marketRegistroFrm.jsp
 
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

public class BeanMarketRegistroFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanMarketRegistroFrm.class);

	private String validar = "";

	private BigDecimal idcliente = new BigDecimal(-1);

	private String email = "";

	private String pass = "";

	private String passverifica = "";

	private String activo = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanMarketRegistroFrm() {
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

	public void determinarAccion() {
		try {
			Stock marketRegistro = Common.getStock();
			boolean isflag = false;
			if (this.accion.equalsIgnoreCase("ingresar")) {
				isflag = marketRegistro.isMarketRegistroValido(this.email,
						this.pass, this.idempresa);
				if (isflag) {
					// redirigir a carga de datos de facturacion / entrega
					request.getSession().setAttribute("accionCarrito",
							"modificacion");
					request.getSession().setAttribute("emailCarrito",
							this.email);
					request.getSession().setAttribute("passCarrito", this.pass);
					response.sendRedirect("marketRegistroDireccionFrm.jsp");
				} else
					this.mensaje = ""
							+ "Ha ingresado mal su nombre de usuario y/o contraseña. "
							+ "Por favor, corrija el error e intente nuevamente.";

			} else if (this.accion.equalsIgnoreCase("registrar")) {
				// redirigir a carga de datos de facturacion / entrega

				isflag = marketRegistro.isMarketRegistroExistente(this.email,
						this.idempresa);

				if (isflag) {
					this.mensaje = ""
							+ "Ya existe un usuario registrado con ese nombre."
							+ "Por favor, intente nuevamente.   ";
				} else {
					request.getSession().setAttribute("accionCarrito", "alta");
					request.getSession().setAttribute("emailCarrito",
							this.email);
					request.getSession().setAttribute("passCarrito", this.pass);
					response.sendRedirect("marketRegistroDireccionFrm.jsp");
				}

			} else if (this.accion.equalsIgnoreCase("recuperar")) {
				isflag = marketRegistro.isMarketRegistroExistente(this.email,
						this.idempresa);
				if (isflag)
				{
					this.mensaje ="Su password ha sido enviada a su casilla de correo electrónico.";
				}else{
					this.mensaje= "Lo sentimos,pero no encontramos el correo electrónico ingresado en nuestros registros.";
				}
					
				
			}
		} catch (Exception ex) {
			log.error(" determinarAccion() : " + ex);
		}
	}

	public void getDatosMarketRegistro() {
		try {
			Stock marketRegistro = Common.getStock();
			List listMarketRegistro = marketRegistro.getMarketRegistroPK(
					this.idcliente, this.idempresa);
			Iterator iterMarketRegistro = listMarketRegistro.iterator();
			if (iterMarketRegistro.hasNext()) {
				String[] uCampos = (String[]) iterMarketRegistro.next();
				// TODO: Constructores para cada tipo de datos
				this.idcliente = new BigDecimal(uCampos[0]);
				this.email = uCampos[1];
				this.pass = uCampos[2];
				this.activo = uCampos[3];

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosMarketRegistro()" + e);
		}
	}

	public boolean ejecutarValidacion() {

		try {
			if (this.accion.equalsIgnoreCase("ingresar")) {
				if (Common.isValidEmailAddress(this.email)) {
					if (!this.pass.equals("")) {
						determinarAccion();
					} else {
						// si el email es invalido,entonces error.
						this.mensaje = "No puede dejar la password vacía.";
						return false;
					}
				} else {
					// si la password esta vacia entonces,error.
					this.mensaje = "Ha ingresado un email inválido, verifíquelo.";
					return false;
				}

			}
			if (this.accion.equalsIgnoreCase("registrar")) {

				if (this.passverifica != null
						|| !this.passverifica.trim().equals("")) {
					
					if (this.pass.trim().length() >= 4) {
						if (this.pass.equalsIgnoreCase(this.passverifica)) {
								determinarAccion();
						} else {
							this.mensaje = "Pass y Confirmación no coinciden.";
							return false;
						}

					} else {
						this.mensaje = "El campo Pass debe tener al menos 4 caracteres.";
						return false;
					}
				} else {
					this.mensaje = "No se puede dejar vacío el campo Confirmar Pass. ";
					return false;
				}

			}

			if (this.accion.equalsIgnoreCase("recuperar")) {
				if (!this.email.equals(""))
				{
					determinarAccion();
				}else{
					this.mensaje="No puede dejar vacío el campo e-mail.";
					return false;
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
	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
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

	public String getPassverifica() {
		return passverifica;
	}

	public void setPassverifica(String passverifica) {
		this.passverifica = passverifica;
	}
}
