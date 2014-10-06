/* 
 javabean para la entidad (Formulario): proveedo_oc_grupos_cotizaciones
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Apr 03 09:17:36 GMT-03:00 2007 
 
 Para manejar la pagina: proveedo_oc_grupos_cotizacionesFrm.jsp
 
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

public class BeanProveedo_oc_grupos_cotizacionesFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger
			.getLogger(BeanProveedo_oc_grupos_cotizacionesFrm.class);

	private String validar = "";

	private BigDecimal idgrupooc = BigDecimal.valueOf(-1);

	private String grupooc = "";

	private java.sql.Date fechadesde = new java.sql.Date(Common
			.initObjectTime());

	private String fechadesdeStr = Common.initObjectTimeStr();

	private java.sql.Date fechahasta = new java.sql.Date(Common
			.initObjectTime());

	private String fechahastaStr = Common.initObjectTimeStr();

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanProveedo_oc_grupos_cotizacionesFrm() {
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
			Proveedores proveedo_oc_grupos_cotizaciones = Common
					.getProveedores();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = proveedo_oc_grupos_cotizaciones
						.proveedo_oc_grupos_cotizacionesCreate(this.grupooc,
								this.fechadesde, this.fechahasta,
								this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = proveedo_oc_grupos_cotizaciones
						.proveedo_oc_grupos_cotizacionesUpdate(this.idgrupooc,
								this.grupooc, this.fechadesde, this.fechahasta,
								this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = proveedo_oc_grupos_cotizaciones
						.proveedo_oc_grupos_cotizacionesDelete(this.idgrupooc,
								this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProveedo_oc_grupos_cotizaciones() {
		try {
			Proveedores proveedo_oc_grupos_cotizaciones = Common
					.getProveedores();
			List listProveedo_oc_grupos_cotizaciones = proveedo_oc_grupos_cotizaciones
					.getProveedo_oc_grupos_cotizacionesPK(this.idgrupooc,
							this.idempresa);
			Iterator iterProveedo_oc_grupos_cotizaciones = listProveedo_oc_grupos_cotizaciones
					.iterator();
			if (iterProveedo_oc_grupos_cotizaciones.hasNext()) {
				String[] uCampos = (String[]) iterProveedo_oc_grupos_cotizaciones
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idgrupooc = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.grupooc = uCampos[1];
				this.fechadesde = java.sql.Date.valueOf(uCampos[2]);
				this.fechadesdeStr = Common.setObjectToStrOrTime(fechadesde,
						"JSDateToStr").toString();
				this.fechahasta = java.sql.Date.valueOf(uCampos[3]);
				this.fechahastaStr = Common.setObjectToStrOrTime(fechahasta,
						"JSDateToStr").toString();
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProveedo_oc_grupos_cotizaciones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("proveedo_oc_grupos_cotizacionesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (grupooc == null) {
						this.mensaje = "No se puede dejar vacio el campo Grupo oc ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (grupooc.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Grupo oc  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosProveedo_oc_grupos_cotizaciones();
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
	public BigDecimal getIdgrupooc() {
		return idgrupooc;
	}

	public void setIdgrupooc(BigDecimal idgrupooc) {
		this.idgrupooc = idgrupooc;
	}

	public String getGrupooc() {
		return grupooc;
	}

	public void setGrupooc(String grupooc) {
		this.grupooc = grupooc;
	}

	public Date getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(java.sql.Date fechadesde) {
		this.fechadesde = fechadesde;
	}

	public Date getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(java.sql.Date fechahasta) {
		this.fechahasta = fechahasta;
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

	public String getFechadesdeStr() {
		return fechadesdeStr;
	}

	public void setFechadesdeStr(String fechadesdeStr) {
		this.fechadesdeStr = fechadesdeStr;
		this.fechadesde = (java.sql.Date) Common.setObjectToStrOrTime(
				fechadesdeStr, "strToJSDate");
	}

	public String getFechahastaStr() {
		return fechahastaStr;
	}

	public void setFechahastaStr(String fechahastaStr) {
		this.fechahastaStr = fechahastaStr;
		this.fechahasta = (java.sql.Date) Common.setObjectToStrOrTime(
				fechahastaStr, "strToJSDate");
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
