/* 
 javabean para la entidad (Formulario): crmindividuos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jun 14 17:23:23 GMT-03:00 2007 
 
 Para manejar la pagina: crmindividuosFrm.jsp
 
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

public class BeanCrmindividuosxusuarioFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmindividuosxusuarioFrm.class);

	private String validar = "";

	private BigDecimal idindividuos = BigDecimal.valueOf(-1);

	private String razon_nombre = "";

	private String telefonos_part = "";

	private String celular = "";

	private String email = "";

	private String domicilio_part = "";

	private Timestamp fechanacimiento = new Timestamp(Common.initObjectTime());

	private String fechanacimientoStr = Common.initObjectTimeStr();

	private String empresa = "";

	private String domicilio_laboral = "";

	private String telefonos_empr = "";

	private String profesion = "";

	private String actividad = "";

	private String deportes = "";

	private String hobbies = "";

	private String actividad_social = "";

	private String diario_lectura = "";

	private String revista_lectura = "";

	private String lugar_veraneo = "";

	private String datosvehiculo = "";

	private String obseravaciones = "";

	private String idtipocliente = "";

	private String tipocliente = "";

	private String idclasificacioncliente = "";

	private String clasificacioncliente = "";

	private String idfuente = "";

	private String fuente = "";

	private BigDecimal idempresa;

	private String idusuario = "";

	private String usuario = "";

	private String usu = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "alta";

	public BeanCrmindividuosxusuarioFrm() {
		super();
	}

	public void setSessionContext(SessionContext newContext)
			throws EJBException {
		context = newContext;
	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
	}

	public String getClasificacioncliente() {
		return clasificacioncliente;
	}

	public void setClasificacioncliente(String clasificacioncliente) {
		this.clasificacioncliente = clasificacioncliente;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public String getTipocliente() {
		return tipocliente;
	}

	public void setTipocliente(String tipocliente) {
		this.tipocliente = tipocliente;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
	}

	public void ejecutarSentenciaDML() {
		try {
			CRM crmindividuos = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta")) {
				this.mensaje = crmindividuos.crmindividuosxusuarioCreate(
						this.razon_nombre, this.telefonos_part, this.celular,
						this.email, this.domicilio_part, this.fechanacimiento,
						this.empresa, this.domicilio_laboral,
						this.telefonos_empr, this.profesion, this.actividad,
						this.deportes, this.hobbies, this.actividad_social,
						this.diario_lectura, this.revista_lectura,
						this.lugar_veraneo, this.obseravaciones,
						this.idusuario, this.idtipocliente,
						this.idclasificacioncliente, this.idfuente,
						this.datosvehiculo, this.idempresa, this.usuarioalt);

				this.idindividuos = new BigDecimal(this.mensaje);

				if (this.idindividuos.longValue() > -1) {
					this.mensaje = "Alta Correcta";
					this.accion = "modificacion";
				} else
					this.mensaje = "Error al dar de alta el registro";

			} else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmindividuos.crmindividuosxusuarioUpdate(
						this.idindividuos, this.razon_nombre,
						this.telefonos_part, this.celular, this.email,
						this.domicilio_part, this.fechanacimiento,
						this.empresa, this.domicilio_laboral,
						this.telefonos_empr, this.profesion, this.actividad,
						this.deportes, this.hobbies, this.actividad_social,
						this.diario_lectura, this.revista_lectura,
						this.lugar_veraneo, this.obseravaciones,
						this.idusuario, this.idtipocliente,
						this.idclasificacioncliente, this.idfuente,
						this.datosvehiculo, this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmindividuos.crmindividuosxusuarioDelete(
						this.idindividuos, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmindividuos() {
		try {
			CRM crmindividuos = Common.getCrm();
			List listCrmindividuos = crmindividuos.getCrmindividuosxusuarioPK(
					this.idindividuos, this.idempresa, this.usu);
			Iterator iterCrmindividuos = listCrmindividuos.iterator();
			if (iterCrmindividuos.hasNext()) {
				String[] uCampos = (String[]) iterCrmindividuos.next();
				// TODO: Constructores para cada tipo de datos
				this.idindividuos = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.razon_nombre = uCampos[1];
				this.telefonos_part = uCampos[2];
				this.celular = uCampos[3];
				this.email = uCampos[4];
				this.domicilio_part = uCampos[5];
				this.fechanacimiento = Timestamp.valueOf(uCampos[6]);
				this.fechanacimientoStr = Common.setObjectToStrOrTime(
						fechanacimiento, "JSTsToStr").toString();
				this.empresa = uCampos[7];
				this.domicilio_laboral = uCampos[8];
				this.telefonos_empr = uCampos[9];
				this.profesion = uCampos[10];
				this.actividad = uCampos[11];
				this.deportes = uCampos[12];
				this.hobbies = uCampos[13];
				this.actividad_social = uCampos[14];
				this.diario_lectura = uCampos[15];
				this.revista_lectura = uCampos[16];
				this.lugar_veraneo = uCampos[17];
				this.obseravaciones = uCampos[18];
				this.idusuario = uCampos[19];
				this.usuario = uCampos[20];
				this.idtipocliente = uCampos[21];
				this.tipocliente = uCampos[22];
				this.idclasificacioncliente = uCampos[23];
				this.clasificacioncliente = uCampos[24];
				this.idfuente = uCampos[25];
				this.fuente = uCampos[26];
				this.datosvehiculo=uCampos[27];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCrmindividuos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmindividuosxusuarioAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (razon_nombre == null) {
						this.mensaje = "No se puede dejar vacio el campo razon_nombre ";
						return false;
					}
					if (telefonos_part == null) {
						this.mensaje = "No se puede dejar vacio el campo telefonos_part ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (razon_nombre.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Razon nombre  ";
						return false;
					}
					if (telefonos_part.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Telefonos particular  ";
						return false;
					}

					if (idusuario.equals("") || !Common.esEntero(idusuario)) {
						this.mensaje = "Es necesario asignar un usuario al individuo.";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCrmindividuos();
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
	public BigDecimal getIdindividuos() {
		return idindividuos;
	}

	public void setIdindividuos(BigDecimal idindividuos) {
		this.idindividuos = idindividuos;
	}

	public String getRazon_nombre() {
		return razon_nombre;
	}

	public void setRazon_nombre(String razon_nombre) {
		this.razon_nombre = razon_nombre;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDomicilio_part() {
		return domicilio_part;
	}

	public void setDomicilio_part(String domicilio_part) {
		this.domicilio_part = domicilio_part;
	}

	public Timestamp getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(Timestamp fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getDomicilio_laboral() {
		return domicilio_laboral;
	}

	public void setDomicilio_laboral(String domicilio_laboral) {
		this.domicilio_laboral = domicilio_laboral;
	}

	public String getTelefonos_empr() {
		return telefonos_empr;
	}

	public void setTelefonos_empr(String telefonos_empr) {
		this.telefonos_empr = telefonos_empr;
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

	public String getDiario_lectura() {
		return diario_lectura;
	}

	public void setDiario_lectura(String diario_lectura) {
		this.diario_lectura = diario_lectura;
	}

	public String getRevista_lectura() {
		return revista_lectura;
	}

	public void setRevista_lectura(String revista_lectura) {
		this.revista_lectura = revista_lectura;
	}

	public String getLugar_veraneo() {
		return lugar_veraneo;
	}

	public void setLugar_veraneo(String lugar_veraneo) {
		this.lugar_veraneo = lugar_veraneo;
	}

	public String getObseravaciones() {
		return obseravaciones;
	}

	public void setObseravaciones(String obseravaciones) {
		this.obseravaciones = obseravaciones;
	}

	public String getIdtipocliente() {
		return idtipocliente;
	}

	public void setIdtipocliente(String idtipocliente) {
		this.idtipocliente = idtipocliente;
	}

	public String getIdclasificacioncliente() {
		return idclasificacioncliente;
	}

	public void setIdclasificacioncliente(String idclasificacioncliente) {
		this.idclasificacioncliente = idclasificacioncliente;
	}

	public String getIdfuente() {
		return idfuente;
	}

	public void setIdfuente(String idfuente) {
		this.idfuente = idfuente;
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

	public String getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}

	public String getUsu() {
		return usu;
	}

	public void setUsu(String usu) {
		this.usu = usu;
	}

	public String getDatosvehiculo() {
		return datosvehiculo;
	}

	public void setDatosvehiculo(String datosvehiculo) {
		this.datosvehiculo = datosvehiculo;
	}
}
