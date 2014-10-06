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
import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanRrhhUserPostulanteFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRrhhUserPostulanteFrm.class);

	private String validar = "";

	private BigDecimal iduserpostulante = new BigDecimal(0);

	private String userpostulante = "";

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

	private String idlocalidad = "";

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

	private String accion = "alta";

	private Hashtable htDinamico = new Hashtable();

	List listProvincias = null;

	public List getListProvincias() {
		return listProvincias;
	}

	public void setListProvincias(List listProvincias) {
		this.listProvincias = listProvincias;
	}

	public BeanRrhhUserPostulanteFrm() {
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
						
			RRHH rrhhUserPostulante = Common.getRrhh();
			java.sql.Date fnacimiento = (java.sql.Date) Common
					.setObjectToStrOrTime(this.fechanac, "StrToJSDate");
			if (this.accion.equalsIgnoreCase("alta")) {
				this.mensaje = rrhhUserPostulante.rrhhUserPostulanteCreate(
						this.userpostulante.toLowerCase(), this.clave
								.toLowerCase(), this.apellido, this.nombre,
						this.email, this.pregunta, this.respuesta,
						this.direccion, this.codigo_postal, this.idpais,
						this.idprovincia, this.idlocalidad, new BigDecimal(
								this.nrodni), fnacimiento, this.telparticular,
						this.tellaboral, this.telcelular, this.emailperfil,
						this.idpuesto, setNull(this.idlenguaje),
						setNull(this.idhw), setNull(this.idso),
						setNull(this.iddb), setNull(this.idapp),
						setNull(this.idred), this.idempresa, this.usuarioalt);
				if (Common.esEntero(this.mensaje)) {
					this.accion = "modificacion";
					this.iduserpostulante = new BigDecimal(this.mensaje);
					this.mensaje = "Proceso de registro correcto.";
				} else {
					this.mensaje = "No es posible realizar la operación en estos momentos. ";
				}

			} else if (this.accion.equalsIgnoreCase("modificacion")) {
				this.mensaje = rrhhUserPostulante.rrhhUserPostulanteUpdate(
						this.iduserpostulante, this.userpostulante
								.toLowerCase(), this.clave, this.apellido,
						this.nombre, this.email, this.pregunta, this.respuesta,
						this.direccion, this.codigo_postal, this.idpais,
						this.idprovincia, this.idlocalidad, new BigDecimal(
								this.nrodni), fnacimiento, this.telparticular,
						this.tellaboral, this.telcelular, this.emailperfil,
						this.idpuesto, setNull(this.idlenguaje),
						setNull(this.idhw), setNull(this.idso),
						setNull(this.iddb), setNull(this.idapp),
						setNull(this.idred), this.idempresa, this.usuarioact);
			} else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhhUserPostulante
						.rrhhUserPostulanteDelete(this.iduserpostulante);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhUserPostulante() {
		try {
			RRHH rrhhUserPostulante = Common.getRrhh();
			List listRrhhUserPostulante = rrhhUserPostulante
					.getRrhhUserPostulantePK(this.iduserpostulante,
							this.idempresa);
			Iterator iterRrhhUserPostulante = listRrhhUserPostulante.iterator();
			if (iterRrhhUserPostulante.hasNext()) {
				String[] uCampos = (String[]) iterRrhhUserPostulante.next();
				// TODO: Constructores para cada tipo de datos
				this.iduserpostulante = new BigDecimal(uCampos[0]);
				this.userpostulante = uCampos[1];
				this.clave = uCampos[2];
				this.apellido = uCampos[3];
				this.nombre = uCampos[4];
				this.email = uCampos[5];
				this.pregunta = uCampos[6];
				this.respuesta = uCampos[7];
				this.direccion = uCampos[8];
				this.codigo_postal = uCampos[9];
				this.idpais = new BigDecimal(uCampos[10]);
				this.idprovincia = new BigDecimal(uCampos[11]);
				this.idlocalidad = uCampos[12];
				this.nrodni = uCampos[13];
				this.fechanac = uCampos[14] == null ? uCampos[14] : Common
						.setObjectToStrOrTime(
								java.sql.Date.valueOf(uCampos[14]),
								"JSDateToStr").toString();
				this.telparticular = uCampos[15];
				this.tellaboral = uCampos[16];
				this.telcelular = uCampos[17];
				this.emailperfil = uCampos[18];
				this.idpuesto = new BigDecimal(uCampos[19]);
				this.idlenguaje = new BigDecimal(uCampos[20]);
				this.idhw = new BigDecimal(uCampos[21]);
				this.idso = new BigDecimal(uCampos[22]);
				this.iddb = new BigDecimal(uCampos[23]);
				this.idapp = new BigDecimal(uCampos[24]);
				this.idred = new BigDecimal(uCampos[25]);
				this.idempresa = new BigDecimal(uCampos[26]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhUserPostulante()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhUserPostulanteAbm.jsp");
				return true;
			}

			RRHH rrhh = Common.getRrhh();
			General gral = Common.getGeneral();
			listProvincias = gral.getGlobalprovinciasAll(25, 0);

			this.htDinamico.put("puesto", rrhh
					.getRrhhBbLlPuestosAll(this.idempresa));
			this.htDinamico.put("lenguaje", rrhh
					.getRrhhBbLlLenguajeAll(this.idempresa));
			this.htDinamico.put("app", rrhh.getRrhhBbLlAppAll(this.idempresa));
			this.htDinamico.put("db", rrhh.getRrhhBbLlDbAll(this.idempresa));
			this.htDinamico.put("hw", rrhh.getRrhhBbLlHwAll(this.idempresa));
			this.htDinamico.put("so", rrhh.getRrhhBbLlSoAll(this.idempresa));
			this.htDinamico.put("red", rrhh.getRrhhBbLlRedAll(this.idempresa));

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (userpostulante == null
							|| userpostulante.trim().equals("")
							|| userpostulante.trim().length() < 6) {
						this.mensaje = "El campo identificación debe tener una longitud mínima de seis carácteres.";
						return false;
					}

					if (clave == null || userpostulante.trim().equals("")
							|| clave.trim().length() < 6) {
						this.mensaje = "El campo clave debe tener una longitud mínima de seis carácteres. ";
						return false;
					}

					if (claveconfirma == null
							|| claveconfirma.trim().equals("")) {
						this.mensaje = "Ingrese confirmación contraseña. ";
						return false;
					}

					if (!clave.equalsIgnoreCase(claveconfirma)) {
						this.mensaje = "Contraseña no coincide con su respectiva confirmación. ";
						return false;
					}

					if (apellido == null || apellido.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo apellido. ";
						return false;
					}
					if (nombre == null || nombre.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo nombre. ";
						return false;
					}
					if (email == null || email.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo email ";
						return false;
					}
					if (pregunta == null || pregunta.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo pregunta ";
						return false;
					}
					if (respuesta == null || respuesta.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo respuesta ";
						return false;
					}
					if (idpais == null || idpais.longValue() < 1) {
						this.mensaje = "Seleccione pais. ";
						return false;
					}
					if (idprovincia == null || idprovincia.longValue() < 1) {
						this.mensaje = "Seleccione provincia. ";
						return false;
					}

					if (nrodni != null && !nrodni.trim().equals("")) {
						if (!Common.esEntero(nrodni)
								|| Long.parseLong(nrodni) < 1) {
							this.mensaje = "DNI inválido. ";
							return false;
						}
					}

					if (fechanac != null && !fechanac.trim().equals("")) {
						if (!Common.isFormatoFecha(fechanac)
								|| !Common.isFechaValida(fechanac)) {
							this.mensaje = "Fecha nacimiento inválida. ";
							return false;
						}
					}

					if (emailperfil == null || emailperfil.trim().equals("")) {
						this.mensaje = "Ingrese e-mail avisos. ";
						return false;
					}
					if (idpuesto == null || idpuesto.longValue() < 1) {
						this.mensaje = "Seleccione puesto perfil. ";
						return false;
					}

					if (rrhh.isExisteRrhhUserPostulante(this.iduserpostulante,
							this.userpostulante, this.idempresa)) {
						this.mensaje = "La identificación ingresada ya se encuentra en uso. ";
						return false;
					}

				}
				
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					
					if (iduserpostulante == null
							|| iduserpostulante.longValue() < 1)
							 {
						this.mensaje = "Es necesario ser usuario registrado para modificar perfil.";
						return false;
					}
					
					getDatosRrhhUserPostulante();
				}
			}
		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private BigDecimal setNull(BigDecimal valor) {
		BigDecimal retorno = null;
		try {

			retorno = valor != null && valor.longValue() > 0 ? retorno = valor
					: null;

		} catch (Exception e) {
			log.error("setNull:" + e);
		}
		return retorno;
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

	public String getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(String idlocalidad) {
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

}
