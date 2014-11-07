/* 
 javabean para la entidad (Formulario): Cajaferiados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
 
 Para manejar la pagina: CajaferiadosFrm.jsp
 
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

public class BeanCajaferiadosFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanCajaferiadosFrm.class);

	private String validar = "";

	private BigDecimal idferiado = BigDecimal.valueOf(-1);

	private String feriado = "";

	private java.sql.Date fecha_fer = new java.sql.Date(Common.initObjectTime());

	private String fecha_ferStr = Common.initObjectTimeStr();

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCajaferiadosFrm() {
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
			Tesoreria Cajaferiados = Common.getTesoreria();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = Cajaferiados.CajaferiadosCreate(this.feriado,
						this.fecha_fer, this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = Cajaferiados.CajaferiadosUpdate(this.idferiado,
						this.feriado, this.fecha_fer, this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = Cajaferiados.CajaferiadosDelete(this.idferiado,
						this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCajaferiados() {
		try {
			Tesoreria Cajaferiados = Common.getTesoreria();
			List listCajaferiados = Cajaferiados.getCajaferiadosPK(
					this.idferiado, this.idempresa);
			Iterator iterCajaferiados = listCajaferiados.iterator();
			if (iterCajaferiados.hasNext()) {
				String[] uCampos = (String[]) iterCajaferiados.next();
				// TODO: Constructores para cada tipo de datos
				this.idferiado = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.feriado = uCampos[1];
				this.fecha_fer = java.sql.Date.valueOf(uCampos[2]);
				this.fecha_ferStr = Common.setObjectToStrOrTime(fecha_fer,
						"JSDateToStr").toString();
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCajaferiados()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("CajaferiadosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCajaferiados();
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
	public BigDecimal getIdferiado() {
		return idferiado;
	}

	public void setIdferiado(BigDecimal idferiado) {
		this.idferiado = idferiado;
	}

	public String getFeriado() {
		return feriado;
	}

	public void setFeriado(String feriado) {
		this.feriado = feriado;
	}

	public Date getFecha_fer() {
		return fecha_fer;
	}

	public void setFecha_fer(java.sql.Date fecha_fer) {
		this.fecha_fer = fecha_fer;
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

	public String getFecha_ferStr() {
		return fecha_ferStr;
	}

	public void setFecha_ferStr(String fecha_ferStr) {
		this.fecha_ferStr = fecha_ferStr;
		this.fecha_fer = (java.sql.Date) Common.setObjectToStrOrTime(
				fecha_ferStr, "strToJSDate");
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
