/* 
   javabean para la entidad (Formulario): globaltiposdocumentos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Mar 28 09:45:19 ART 2008 
   
   Para manejar la pagina: globaltiposdocumentosFrm.jsp
      
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

public class BeanRecuperarClave implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRecuperarClave.class);

	private String email = "";

	private String user = "";

	private String pass = "";

	private String nombre = "";

	private String habilitado = "";

	private String validar = "";

	private BigDecimal idempresa = new BigDecimal(1);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanRecuperarClave() {
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

			String body = "";

			if (Common.setNotNull(this.user).equals("")) {
				this.mensaje = "No se reconoce usuario, por favor comuniquese con el área de sistemas.";
			} else if (Common.setNotNull(this.pass).equals("")) {
				this.mensaje = "No se reconoce password, por favor comuniquese con el área de sistemas.";
			} else if (Common.setNotNull(this.habilitado).equalsIgnoreCase("N")) {
				this.mensaje = "Usuario inhabilitado, por favor comuniquese con el área de sistemas.";
			} else if (Common.setNotNull(this.mensaje).equals("")) {

				body = ""
						+ "\nEstimada/o  : "
						+ this.nombre
						+ "\n\n\tEste mensaje fue generado por una solicitud de recuperación de información de ingreso para el sistema DELTA."
						+ "\n\tSi ud. no está intentando recuperar dichos datos, por favor ignore este mail."
						+ "\n\tSu usuario es:  "
						+ this.user
						+ "\n\tSu password es: "
						+ this.pass
						+ "\n\tSi su problema persiste, por favor comuniquese con el área de sistemas."
						+ "\n\nMuchas Gracias";

				if (Common.sendMail("SISTEMA DELTA - INGRESO", body, this.email))
					this.mensaje = "La solicitud fue enviada, en breve recibirá la notificación correspondiente.";
				else
					this.mensaje = "La solicitud no pudo ser enviada, por favor comuniquese con el area de sistemas.";
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGlobaltiposdocumentos() {
		try {

			this.email = Common.setNotNull(this.email).trim();
			List listGlobaltiposdocumentos = Common.getGeneral()
					.getGlobalusuariosXMail(this.email, this.idempresa);
			Iterator iterGlobaltiposdocumentos = listGlobaltiposdocumentos
					.iterator();
			if (iterGlobaltiposdocumentos.hasNext()) {
				String[] uCampos = (String[]) iterGlobaltiposdocumentos.next();
				// TODO: Constructores para cada tipo de datos

				this.user = uCampos[1];
				this.pass = uCampos[2];
				this.nombre = uCampos[4];
				this.habilitado = uCampos[5];

			} else {
				this.mensaje = "La cuenta de correo parece ser inexistente, por favor comuniquese con el área de sistemas.";
			}

		} catch (Exception e) {
			this.mensaje = "Se produjo un error al intentar recuperar datos de la cuenta ingresada, por favor comuniquese con el área de sistemas.";
			log.error("getDatosGlobaltiposdocumentos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos

					if (!Common.isValidEmailAddress(email)) {

						this.mensaje = "Ingrese una cuenta de correo valida.";
						return false;

					}
				}

				this.getDatosGlobaltiposdocumentos();

				this.ejecutarSentenciaDML();

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
