/* 
 javabean para la entidad (Formulario): RRHHbusquedasLaborales
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Oct 10 16:05:59 ART 2007 
 
 Para manejar la pagina: RRHHbusquedasLaboralesFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Date;
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

public class BeanRRHHbusquedasLaboralesFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRRHHbusquedasLaboralesFrm.class);

	private String validar = "";

	private BigDecimal idbusquedalaboral = BigDecimal.valueOf(-1);

	private BigDecimal iduserpostulante = new BigDecimal(0);

	private String referencia = "";

	private Timestamp fechabusquedadesde = new Timestamp(Common
			.initObjectTime());

	private String fechabusquedadesdeStr = Common.initObjectTimeStr();

	private Timestamp fechabusquedahasta = new Timestamp(Common
			.initObjectTime());

	private String fechabusquedahastaStr = Common.initObjectTimeStr();

	private String seniority = "";

	private String lugartrabajo = "";

	private String descripcionproyecto = "";

	private String descripciontarea = "";

	private String skillexcluyente = "";

	private String skilldeseable = "";

	private String idioma = "";

	private String posibilidadderenovacion = "";

	private String usuarioalt = "";

	private String usuarioact = "";

	private BigDecimal idempresa;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanRRHHbusquedasLaboralesFrm() {
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
			RRHH RRHHbusquedasLaborales = Common.getRrhh();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = RRHHbusquedasLaborales
						.RRHHbusquedasLaboralesCreate(this.referencia,
								this.fechabusquedadesde,
								this.fechabusquedahasta, this.seniority,
								this.lugartrabajo, this.descripcionproyecto,
								this.descripciontarea, this.skillexcluyente,
								this.skilldeseable, this.idioma,
								this.posibilidadderenovacion, this.usuarioalt,
								this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = RRHHbusquedasLaborales
						.RRHHbusquedasLaboralesUpdate(this.idbusquedalaboral,
								this.referencia, this.fechabusquedadesde,
								this.fechabusquedahasta, this.seniority,
								this.lugartrabajo, this.descripcionproyecto,
								this.descripciontarea, this.skillexcluyente,
								this.skilldeseable, this.idioma,
								this.posibilidadderenovacion, this.usuarioact,
								this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = RRHHbusquedasLaborales
						.RRHHbusquedasLaboralesDelete(this.idbusquedalaboral,
								this.idempresa);
			else if (this.accion.equalsIgnoreCase("postular")) {
				this.mensaje = RRHHbusquedasLaborales.rrhhPostulacionesCreate(
						this.iduserpostulante, this.idbusquedalaboral,
						new BigDecimal(0), this.idempresa, this.usuarioalt);
				if(this.mensaje.equalsIgnoreCase("OK")){
					this.mensaje = "Su postulación ha sido registrada correctamente.";
				}
			}
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRRHHbusquedasLaborales() {
		try {
			RRHH RRHHbusquedasLaborales = Common.getRrhh();
			List listRRHHbusquedasLaborales = RRHHbusquedasLaborales
					.getRRHHbusquedasLaboralesPK(this.idbusquedalaboral,
							this.idempresa);
			Iterator iterRRHHbusquedasLaborales = listRRHHbusquedasLaborales
					.iterator();
			if (iterRRHHbusquedasLaborales.hasNext()) {
				String[] uCampos = (String[]) iterRRHHbusquedasLaborales.next();
				// TODO: Constructores para cada tipo de datos
				this.idbusquedalaboral = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.referencia = uCampos[1];
				this.fechabusquedadesde = Timestamp.valueOf(uCampos[2]);
				this.fechabusquedadesdeStr = Common.setObjectToStrOrTime(
						fechabusquedadesde, "JSTsToStr").toString();
				this.fechabusquedahasta = Timestamp.valueOf(uCampos[3]);
				this.fechabusquedahastaStr = Common.setObjectToStrOrTime(
						fechabusquedahasta, "JSTsToStr").toString();
				this.seniority = uCampos[4];
				this.lugartrabajo = uCampos[5];
				this.descripcionproyecto = uCampos[6];
				this.descripciontarea = uCampos[7];
				this.skillexcluyente = uCampos[8];
				this.skilldeseable = uCampos[9];
				this.idioma = uCampos[10];
				this.posibilidadderenovacion = uCampos[11];

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRRHHbusquedasLaborales()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("RRHHbusquedasLaboralesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")
						&& !this.accion.equalsIgnoreCase("postular")) {
					// 1. nulidad de campos
					if (referencia == null) {
						this.mensaje = "No se puede dejar vacio el campo referencia ";
						return false;
					}
					if (fechabusquedadesde == null) {
						this.mensaje = "No se puede dejar vacio el campo fechabusquedadesde ";
						return false;
					}
					if (fechabusquedahasta == null) {
						this.mensaje = "No se puede dejar vacio el campo fechabusquedahasta ";
						return false;
					}
					if (seniority == null) {
						this.mensaje = "No se puede dejar vacio el campo seniority ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (referencia.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo referencia  ";
						return false;
					}
					if (seniority.trim().length() == 0) {
						this.mensaje = "No se puede dejar seniority el campo seniority  ";
						return false;
					}

					if (lugartrabajo.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Lugar Trabajo  ";
						return false;
					}

				} else if (this.accion.equalsIgnoreCase("postular")) {

					RRHH rrhh = Common.getRrhh();
					String datos[] = null;

					getDatosRRHHbusquedasLaborales();

					
					if(iduserpostulante.longValue() < 1){
						this.mensaje = "Para poder postularse es necesario ingresar como usuario registrado.";
						return false;
					}
					
					if (rrhh.isExisteRrhhPostulaciones(iduserpostulante,
							idbusquedalaboral, idempresa)) {
						this.mensaje = "Postulación para la oferta seleccionada, ya se encuentra registrada.";
						return false;
					}
					
					datos = (String[])rrhh.getRrhhUserPostulanteDatosCvPK(iduserpostulante, idempresa).get(0);
					
					if(datos[0]== null || datos[0].trim().equals("")){
						this.mensaje = "Para poder postularse es necesario ingresar el Curriculum Vitae.";
						return false;						
					}
		
					if(datos[1]== null || datos[1].trim().equals("")){
						this.mensaje = "Para poder postularse es necesario ingresar el Curriculum Vitae.";
						return false;						
					}
					
					Calendar cal = new GregorianCalendar();
					cal.add(Calendar.DATE, -180);
															
					if( Date.valueOf(datos[1]).before( cal.getTime() ) ){
						this.mensaje = "Para poder postularse es necesario actualizar el Curriculum Vitae.";
						return false;						
					}
					
				}

				this.ejecutarSentenciaDML();

			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRRHHbusquedasLaborales();
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
	public BigDecimal getIdbusquedalaboral() {
		return idbusquedalaboral;
	}

	public void setIdbusquedalaboral(BigDecimal idbusquedalaboral) {
		this.idbusquedalaboral = idbusquedalaboral;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Timestamp getFechabusquedadesde() {
		return fechabusquedadesde;
	}

	public void setFechabusquedadesde(Timestamp fechabusquedadesde) {
		this.fechabusquedadesde = fechabusquedadesde;
	}

	public Timestamp getFechabusquedahasta() {
		return fechabusquedahasta;
	}

	public void setFechabusquedahasta(Timestamp fechabusquedahasta) {
		this.fechabusquedahasta = fechabusquedahasta;
	}

	public String getSeniority() {
		return seniority;
	}

	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}

	public String getLugartrabajo() {
		return lugartrabajo;
	}

	public void setLugartrabajo(String lugartrabajo) {
		this.lugartrabajo = lugartrabajo;
	}

	public String getDescripcionproyecto() {
		return descripcionproyecto;
	}

	public void setDescripcionproyecto(String descripcionproyecto) {
		this.descripcionproyecto = descripcionproyecto;
	}

	public String getDescripciontarea() {
		return descripciontarea;
	}

	public void setDescripciontarea(String descripciontarea) {
		this.descripciontarea = descripciontarea;
	}

	public String getSkillexcluyente() {
		return skillexcluyente;
	}

	public void setSkillexcluyente(String skillexcluyente) {
		this.skillexcluyente = skillexcluyente;
	}

	public String getSkilldeseable() {
		return skilldeseable;
	}

	public void setSkilldeseable(String skilldeseable) {
		this.skilldeseable = skilldeseable;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getPosibilidadderenovacion() {
		return posibilidadderenovacion;
	}

	public void setPosibilidadderenovacion(String posibilidadderenovacion) {
		this.posibilidadderenovacion = posibilidadderenovacion;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getFechabusquedadesdeStr() {
		return fechabusquedadesdeStr;
	}

	public void setFechabusquedadesdeStr(String fechabusquedadesdeStr) {
		this.fechabusquedadesdeStr = fechabusquedadesdeStr;
		this.fechabusquedadesde = (java.sql.Timestamp) Common
				.setObjectToStrOrTime(fechabusquedadesdeStr, "StrToJSTs");
	}

	public String getFechabusquedahastaStr() {
		return fechabusquedahastaStr;
	}

	public void setFechabusquedahastaStr(String fechabusquedahastaStr) {
		this.fechabusquedahastaStr = fechabusquedahastaStr;
		this.fechabusquedahasta = (java.sql.Timestamp) Common
				.setObjectToStrOrTime(fechabusquedahastaStr, "StrToJSTs");
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BigDecimal getIduserpostulante() {
		return iduserpostulante;
	}

	public void setIduserpostulante(BigDecimal iduserpostulante) {
		this.iduserpostulante = iduserpostulante;
	}

}
