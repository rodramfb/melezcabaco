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

public class BeanCrmindividuosxusuarioBuscar implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanCrmindividuosxusuarioBuscar.class);

	private String validar = "";

	private BigDecimal idindividuos = BigDecimal.valueOf(-1);

	private String razon_nombre = "";

	private String telefonos_part = "";

	private String celular = "";

	private String email = "";

	private String domicilio_part = "";

	private String fechanacimiento = "";

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

	private String seleccionados = "";

	private String fechacotizaciondesde = "";

	private String fechacotizacionhasta = "";

	private String fechallamadodesde = "";

	private String fechallamadohasta = "";

	private String fechavisitadesde = "";

	private String fechavisitahasta = "";

	private String nombrepariente = "";

	private String datosvehiculo = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCrmindividuosxusuarioBuscar() {
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

	public String getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(String fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public String getSeleccionados() {
		return seleccionados;
	}

	public void setSeleccionados(String seleccionados) {
		this.seleccionados = seleccionados;
	}

	public String getFechacotizaciondesde() {
		return fechacotizaciondesde;
	}

	public void setFechacotizaciondesde(String fechacotizaciondesde) {
		this.fechacotizaciondesde = fechacotizaciondesde;
	}

	public String getFechacotizacionhasta() {
		return fechacotizacionhasta;
	}

	public void setFechacotizacionhasta(String fechacotizacionhasta) {
		this.fechacotizacionhasta = fechacotizacionhasta;
	}

	public String getFechallamadodesde() {
		return fechallamadodesde;
	}

	public void setFechallamadodesde(String fechallamadodesde) {
		this.fechallamadodesde = fechallamadodesde;
	}

	public String getFechallamadohasta() {
		return fechallamadohasta;
	}

	public void setFechallamadohasta(String fechallamadohasta) {
		this.fechallamadohasta = fechallamadohasta;
	}

	public String getFechavisitadesde() {
		return fechavisitadesde;
	}

	public void setFechavisitadesde(String fechavisitadesde) {
		this.fechavisitadesde = fechavisitadesde;
	}

	public String getFechavisitahasta() {
		return fechavisitahasta;
	}

	public void setFechavisitahasta(String fechavisitahasta) {
		this.fechavisitahasta = fechavisitahasta;
	}

	public String getNombrepariente() {
		return nombrepariente;
	}

	public void setNombrepariente(String nombrepariente) {
		this.nombrepariente = nombrepariente;
	}

	public String getDatosvehiculo() {
		return datosvehiculo;
	}

	public void setDatosvehiculo(String datosvehiculo) {
		this.datosvehiculo = datosvehiculo;
	}
}
