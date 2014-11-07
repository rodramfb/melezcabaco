/* 
 javabean para la entidad (Formulario): rrhhUserPostulante
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Oct 12 14:20:28 ART 2007 
 
 Para manejar la pagina: rrhhUserPostulanteFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanRrhhUserPostulanteValidar implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRrhhUserPostulanteValidar.class);

	private String validar = "";

	private BigDecimal iduserpostulante = new BigDecimal(0);

	private String userpostulante = "";

	private String uservalidar = "";

	private String contrasenavalidar = "";

	private String clave = "";

	private String claveconfirma = "";

	private String apellido = "";

	private String nombre = "";

	private String email = "";

	private String pregunta = "";

	private String respuesta = "";

	private String direccion = "";

	private String codigo_postal = "";

	private BigDecimal idpais = new BigDecimal(0);

	private BigDecimal idprovincia = new BigDecimal(0);

	private BigDecimal idlocalidad = new BigDecimal(0);

	private String nrodni = "";

	private String fechanac = "";

	private String telparticular = "";

	private String tellaboral = "";

	private String telcelular = "";

	private String emailperfil = "";

	private BigDecimal idpuesto = new BigDecimal(0);

	private BigDecimal idlenguaje = new BigDecimal(0);

	private BigDecimal idhw = new BigDecimal(0);

	private BigDecimal idso = new BigDecimal(0);

	private BigDecimal iddb = new BigDecimal(0);

	private BigDecimal idapp = new BigDecimal(0);

	private BigDecimal idred = new BigDecimal(0);

	private BigDecimal idempresa = new BigDecimal(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;
	
	private HttpSession session;

	private String accion = "alta";

	private Hashtable htDinamico = new Hashtable();

	public BeanRrhhUserPostulanteValidar() {
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

	public boolean ejecutarValidacion() {
		try {

			RRHH rrhh = Common.getRrhh();

			
			List listUser = null;
					
			String[] datos = null;

			if (!this.validar.equalsIgnoreCase("")) {

				if (this.uservalidar.trim().length() < 6) {
					this.mensaje = "Usuario debe ser mayor a seis carácteres.";
					return false;
				}

				if (this.contrasenavalidar.trim().length() < 6) {
					this.mensaje = "Contraseña debe ser mayor a seis carácteres.";
					return false;
				}

				listUser = rrhh.validarRrhhUserPostulante(this.uservalidar,
						this.contrasenavalidar, this.idempresa);

				if(listUser == null || listUser.isEmpty()){
					this.mensaje = "Usuario o Contraseña inválido/a.";
					return false;
				}
				
				datos = (String[]) listUser.get(0);
				
				session.setAttribute("idpostulante", datos[0]);
				session.setAttribute("postulante", datos[1]);
				session.setAttribute("empresapostulante", this.idempresa);
				
				response.sendRedirect("index.jsp");
				return true;

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
	public BigDecimal getIduserpostulante() {
		return iduserpostulante;
	}

	public void setIduserpostulante(BigDecimal iduserpostulante) {
		this.iduserpostulante = iduserpostulante;
	}

	public String getUserpostulante() {
		return userpostulante;
	}

	public void setUserpostulante(String userpostulante) {
		this.userpostulante = userpostulante;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public BigDecimal getIdpais() {
		return idpais;
	}

	public void setIdpais(BigDecimal idpais) {
		this.idpais = idpais;
	}

	public BigDecimal getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(BigDecimal idprovincia) {
		this.idprovincia = idprovincia;
	}

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getNrodni() {
		return nrodni;
	}

	public void setNrodni(String nrodni) {
		this.nrodni = nrodni;
	}

	public String getFechanac() {
		return fechanac;
	}

	public void setFechanac(String fechanac) {
		this.fechanac = fechanac;
	}

	public String getTelparticular() {
		return telparticular;
	}

	public void setTelparticular(String telparticular) {
		this.telparticular = telparticular;
	}

	public String getTellaboral() {
		return tellaboral;
	}

	public void setTellaboral(String tellaboral) {
		this.tellaboral = tellaboral;
	}

	public String getTelcelular() {
		return telcelular;
	}

	public void setTelcelular(String telcelular) {
		this.telcelular = telcelular;
	}

	public String getEmailperfil() {
		return emailperfil;
	}

	public void setEmailperfil(String emailperfil) {
		this.emailperfil = emailperfil;
	}

	public BigDecimal getIdpuesto() {
		return idpuesto;
	}

	public void setIdpuesto(BigDecimal idpuesto) {
		this.idpuesto = idpuesto;
	}

	public BigDecimal getIdlenguaje() {
		return idlenguaje;
	}

	public void setIdlenguaje(BigDecimal idlenguaje) {
		this.idlenguaje = idlenguaje;
	}

	public BigDecimal getIdhw() {
		return idhw;
	}

	public void setIdhw(BigDecimal idhw) {
		this.idhw = idhw;
	}

	public BigDecimal getIdso() {
		return idso;
	}

	public void setIdso(BigDecimal idso) {
		this.idso = idso;
	}

	public BigDecimal getIddb() {
		return iddb;
	}

	public void setIddb(BigDecimal iddb) {
		this.iddb = iddb;
	}

	public BigDecimal getIdapp() {
		return idapp;
	}

	public void setIdapp(BigDecimal idapp) {
		this.idapp = idapp;
	}

	public BigDecimal getIdred() {
		return idred;
	}

	public void setIdred(BigDecimal idred) {
		this.idred = idred;
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

	public String getClaveconfirma() {
		return claveconfirma;
	}

	public void setClaveconfirma(String claveconfirma) {
		this.claveconfirma = claveconfirma;
	}

	public Hashtable getHtDinamico() {
		return htDinamico;
	}

	public void setHtDinamico(Hashtable htDinamico) {
		this.htDinamico = htDinamico;
	}

	public String getContrasenavalidar() {
		return contrasenavalidar;
	}

	public void setContrasenavalidar(String contrasenavalidar) {
		this.contrasenavalidar = contrasenavalidar;
	}

	public String getUservalidar() {
		return uservalidar;
	}

	public void setUservalidar(String uservalidar) {
		this.uservalidar = uservalidar;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

}
