/* 
 javabean para la entidad (Formulario): crmfamiliares
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jun 14 17:25:20 GMT-03:00 2007 
 
 Para manejar la pagina: crmfamiliaresFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;
 
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

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

public class BeanCrmfamiliaresxusuarioFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmfamiliaresxusuarioFrm.class);

	private String validar = "";

	private BigDecimal idfamiliar = BigDecimal.valueOf(-1);

	private BigDecimal idindividuos = BigDecimal.valueOf(0);

	private String razon_nombre = "";

	private BigDecimal idnexofamiliar = BigDecimal.valueOf(0);

	private String nexofamiliar = "";

	private String nombre = "";

	private String profesion = "";

	private String actividad = "";

	private String email = "";

	private String telefonos_part = "";

	private String celular = "";

	private Timestamp fechanacimiento = new Timestamp(Common.initObjectTime());

	private String fechanacimientoStr = Common.initObjectTimeStr();

	private String deportes = "";

	private String hobbies = "";

	private String actividad_social = "";

	private String obseravaciones = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCrmfamiliaresxusuarioFrm() {
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
			CRM crmfamiliares = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmfamiliares.crmfamiliaresCreate(
						this.idindividuos, this.idnexofamiliar, this.nombre,
						this.profesion, this.actividad, this.email,
						this.telefonos_part, this.celular,
						this.fechanacimiento, this.deportes, this.hobbies,
						this.actividad_social, this.obseravaciones,
						this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmfamiliares.crmfamiliaresUpdate(
						this.idfamiliar, this.idindividuos,
						this.idnexofamiliar, this.nombre, this.profesion,
						this.actividad, this.email, this.telefonos_part,
						this.celular, this.fechanacimiento, this.deportes,
						this.hobbies, this.actividad_social,
						this.obseravaciones, this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmfamiliares.crmfamiliaresDelete(
						this.idfamiliar, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmfamiliares() {
		try {
			CRM crmfamiliares = Common.getCrm();
			List listCrmfamiliares = crmfamiliares.getCrmfamiliaresPK(
					this.idfamiliar, this.idempresa);
			Iterator iterCrmfamiliares = listCrmfamiliares.iterator();
			if (iterCrmfamiliares.hasNext()) {
				String[] uCampos = (String[]) iterCrmfamiliares.next();
				// TODO: Constructores para cada tipo de datos
				this.idfamiliar = BigDecimal
						.valueOf(Long.parseLong(uCampos[0]));
				this.idindividuos = BigDecimal.valueOf(Long
						.parseLong(uCampos[1]));
				this.razon_nombre = uCampos[2];
				this.idnexofamiliar = BigDecimal.valueOf(Long
						.parseLong(uCampos[3]));
				this.nexofamiliar = uCampos[4];
				this.nombre = uCampos[5];
				this.profesion = uCampos[6];
				this.actividad = uCampos[7];
				this.email = uCampos[8];
				this.telefonos_part = uCampos[9];
				this.celular = uCampos[10];
				this.fechanacimiento = Timestamp.valueOf(uCampos[11]);
				this.fechanacimientoStr = Common.setObjectToStrOrTime(
						fechanacimiento, "JSTsToStr").toString();
				this.deportes = uCampos[12];
				this.hobbies = uCampos[13];
				this.actividad_social = uCampos[14];
				this.obseravaciones = uCampos[15];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCrmfamiliares()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmfamiliaresxusuarioAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idindividuos == null) {
						this.mensaje = "No se puede dejar vacio el campo idindividuos ";
						return false;
					}
					if (idnexofamiliar == null) {
						this.mensaje = "No se puede dejar vacio el campo idnexofamiliar ";
						return false;
					}
					if (nombre == null) {
						this.mensaje = "No se puede dejar vacio el campo nombre ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (razon_nombre.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Individuos  ";
						return false;
					}
					if (nexofamiliar.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Nexo Familiar  ";
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
					getDatosCrmfamiliares();
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
	public BigDecimal getIdfamiliar() {
		return idfamiliar;
	}

	public void setIdfamiliar(BigDecimal idfamiliar) {
		this.idfamiliar = idfamiliar;
	}

	public BigDecimal getIdindividuos() {
		return idindividuos;
	}

	public void setIdindividuos(BigDecimal idindividuos) {
		this.idindividuos = idindividuos;
	}

	public BigDecimal getIdnexofamiliar() {
		return idnexofamiliar;
	}

	public void setIdnexofamiliar(BigDecimal idnexofamiliar) {
		this.idnexofamiliar = idnexofamiliar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefonos_part() {
		return telefonos_part;
	}

	public void setTelefonos_part(String telefonos_part) {
		this.telefonos_part = telefonos_part;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Timestamp getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(Timestamp fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public String getDeportes() {
		return deportes;
	}

	public void setDeportes(String deportes) {
		this.deportes = deportes;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getActividad_social() {
		return actividad_social;
	}

	public void setActividad_social(String actividad_social) {
		this.actividad_social = actividad_social;
	}

	public String getObseravaciones() {
		return obseravaciones;
	}

	public void setObseravaciones(String obseravaciones) {
		this.obseravaciones = obseravaciones;
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

	public String getFechanacimientoStr() {
		return fechanacimientoStr;
	}

	public void setFechanacimientoStr(String fechanacimientoStr) {
		this.fechanacimientoStr = fechanacimientoStr;
		this.fechanacimiento = (java.sql.Timestamp) Common
				.setObjectToStrOrTime(fechanacimientoStr, "StrToJSTs");
	}

	public String getNexofamiliar() {
		return nexofamiliar;
	}

	public void setNexofamiliar(String nexofamiliar) {
		this.nexofamiliar = nexofamiliar;
	}

	public String getRazon_nombre() {
		return razon_nombre;
	}

	public void setRazon_nombre(String razon_nombre) {
		this.razon_nombre = razon_nombre;
	}

}
