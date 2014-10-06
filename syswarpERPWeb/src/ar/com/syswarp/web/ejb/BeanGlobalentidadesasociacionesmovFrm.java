/* 
 javabean para la entidad (Formulario): globalentidadesasociacionesmov
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed May 07 11:53:08 CEST 2008 
 
 Para manejar la pagina: globalentidadesasociacionesmovFrm.jsp
 
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

public class BeanGlobalentidadesasociacionesmovFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanGlobalentidadesasociacionesmovFrm.class);

	private String validar = "";

	private BigDecimal identidadesasociacionesmov = BigDecimal.valueOf(-1);

	private BigDecimal identidadorigen = BigDecimal.valueOf(0);

	private String entidadorigen = "";

	private BigDecimal identidaddestino = BigDecimal.valueOf(0);

	private String entidaddestino = "";

	private BigDecimal pkorigen = BigDecimal.valueOf(0);

	private BigDecimal pkdestino = BigDecimal.valueOf(0);

	private java.sql.Date fecha = new java.sql.Date(Common.initObjectTime());

	private String fechaStr = Common.initObjectTimeStr();

	private String observacion = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanGlobalentidadesasociacionesmovFrm() {
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
			General globalentidadesasociacionesmov = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = globalentidadesasociacionesmov
						.globalentidadesasociacionesmovCreate(
								this.identidadorigen, this.identidaddestino,
								this.pkorigen, this.pkdestino, this.fecha,
								this.observacion, this.idempresa,
								this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = globalentidadesasociacionesmov
						.globalentidadesasociacionesmovUpdate(
								this.identidadesasociacionesmov,
								this.identidadorigen, this.identidaddestino,
								this.pkorigen, this.pkdestino, this.fecha,
								this.observacion, this.idempresa,
								this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = globalentidadesasociacionesmov
						.globalentidadesasociacionesmovDelete(
								this.identidadesasociacionesmov, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGlobalentidadesasociacionesmov() {
		try {
			General globalentidadesasociacionesmov = Common.getGeneral();
			List listGlobalentidadesasociacionesmov = globalentidadesasociacionesmov
					.getGlobalentidadesasociacionesmovPK(
							this.identidadesasociacionesmov, this.idempresa);
			Iterator iterGlobalentidadesasociacionesmov = listGlobalentidadesasociacionesmov
					.iterator();
			if (iterGlobalentidadesasociacionesmov.hasNext()) {
				String[] uCampos = (String[]) iterGlobalentidadesasociacionesmov
						.next();
				// TODO: Constructores para cada tipo de datos
				this.identidadesasociacionesmov = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.identidadorigen = BigDecimal.valueOf(Long
						.parseLong(uCampos[1]));
				this.entidadorigen = uCampos[2];
				this.identidaddestino = BigDecimal.valueOf(Long
						.parseLong(uCampos[3]));
				this.entidaddestino = uCampos[4];
				this.pkorigen = BigDecimal.valueOf(Long.parseLong(uCampos[5]));
				this.pkdestino = BigDecimal.valueOf(Long.parseLong(uCampos[6]));
				this.fecha = java.sql.Date.valueOf(uCampos[7]);
				this.fechaStr = Common.setObjectToStrOrTime(fecha,
						"JSDateToStr").toString();
				this.observacion = uCampos[8];
				this.idempresa = BigDecimal.valueOf(Long.parseLong(uCampos[9]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGlobalentidadesasociacionesmov()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("globalentidadesasociacionesmovAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (identidadorigen == null) {
						this.mensaje = "No se puede dejar vacio el campo identidadorigen ";
						return false;
					}
					if (identidaddestino == null) {
						this.mensaje = "No se puede dejar vacio el campo identidaddestino ";
						return false;
					}
					if (pkorigen == null) {
						this.mensaje = "No se puede dejar vacio el campo pkorigen ";
						return false;
					}
					if (pkdestino == null) {
						this.mensaje = "No se puede dejar vacio el campo pkdestino ";
						return false;
					}
					if (fecha == null) {
						this.mensaje = "No se puede dejar vacio el campo fecha ";
						return false;
					}

					if (entidadorigen.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Entidad Origen  ";
						return false;
					}

					if (entidaddestino.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Entidad Destino  ";
						return false;
					}
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGlobalentidadesasociacionesmov();
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
	public BigDecimal getIdentidadesasociacionesmov() {
		return identidadesasociacionesmov;
	}

	public void setIdentidadesasociacionesmov(
			BigDecimal identidadesasociacionesmov) {
		this.identidadesasociacionesmov = identidadesasociacionesmov;
	}

	public BigDecimal getIdentidadorigen() {
		return identidadorigen;
	}

	public void setIdentidadorigen(BigDecimal identidadorigen) {
		this.identidadorigen = identidadorigen;
	}

	public BigDecimal getIdentidaddestino() {
		return identidaddestino;
	}

	public void setIdentidaddestino(BigDecimal identidaddestino) {
		this.identidaddestino = identidaddestino;
	}

	public BigDecimal getPkorigen() {
		return pkorigen;
	}

	public void setPkorigen(BigDecimal pkorigen) {
		this.pkorigen = pkorigen;
	}

	public BigDecimal getPkdestino() {
		return pkdestino;
	}

	public void setPkdestino(BigDecimal pkdestino) {
		this.pkdestino = pkdestino;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(java.sql.Date fecha) {
		this.fecha = fecha;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

	public String getFechaStr() {
		return fechaStr;
	}

	public void setFechaStr(String fechaStr) {
		this.fechaStr = fechaStr;
		this.fecha = (java.sql.Date) Common.setObjectToStrOrTime(fechaStr,
				"strToJSDate");
	}

	public String getEntidadorigen() {
		return entidadorigen;
	}

	public void setEntidadorigen(String entidadorigen) {
		this.entidadorigen = entidadorigen;
	}

	public String getEntidaddestino() {
		return entidaddestino;
	}

	public void setEntidaddestino(String entidaddestino) {
		this.entidaddestino = entidaddestino;
	}
}
