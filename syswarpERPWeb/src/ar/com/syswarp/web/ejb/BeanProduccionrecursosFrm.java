/* 
 javabean para la entidad (Formulario): Produccionrecursos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Sep 06 12:51:54 GMT-03:00 2006 
 
 Para manejar la pagina: ProduccionrecursosFrm.jsp
 
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

public class BeanProduccionrecursosFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProduccionrecursosFrm.class);

	private String validar = "";

	private BigDecimal idrecurso = BigDecimal.valueOf(-1);

	private String recurso = "";

	private BigDecimal codigo_md = BigDecimal.valueOf(0);

	private String d_codigo_md = "";

	private Double costo = Double.valueOf("0");

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanProduccionrecursosFrm() {
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
			Produccion Produccionrecursos = Common.getProduccion();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = Produccionrecursos.ProduccionrecursosCreate(
						this.recurso, this.codigo_md, this.costo,
						this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = Produccionrecursos.ProduccionrecursosUpdate(
						this.idrecurso, this.recurso, this.codigo_md,
						this.costo, this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = Produccionrecursos.ProduccionrecursosDelete(
						this.idrecurso, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProduccionrecursos() {
		try {
			Produccion Produccionrecursos = Common.getProduccion();
			List listProduccionrecursos = Produccionrecursos
					.getProduccionrecursosPK(this.idrecurso, this.idempresa);
			Iterator iterProduccionrecursos = listProduccionrecursos.iterator();
			if (iterProduccionrecursos.hasNext()) {
				String[] uCampos = (String[]) iterProduccionrecursos.next();
				// TODO: Constructores para cada tipo de datos
				this.idrecurso = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.recurso = uCampos[1];
				this.codigo_md = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
				this.d_codigo_md = uCampos[3];
				this.costo = Double.valueOf(uCampos[4]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProduccionrecursos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("ProduccionrecursosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (recurso == null) {
						this.mensaje = "No se puede dejar vacio el campo recurso ";
						return false;
					}
					// 2. len 0 para campos nulos
					// if(recurso.trim().length() == 0 ){
					// this.mensaje = "No se puede dejar con longitud 0 el campo
					// recurso ";
					// return false;
					// }
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosProduccionrecursos();
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
	public BigDecimal getIdrecurso() {
		return idrecurso;
	}

	public void setIdrecurso(BigDecimal idrecurso) {
		this.idrecurso = idrecurso;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public BigDecimal getCodigo_md() {
		return codigo_md;
	}

	public void setCodigo_md(BigDecimal codigo_md) {
		this.codigo_md = codigo_md;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
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

	public String getD_codigo_md() {
		return d_codigo_md;
	}

	public void setD_codigo_md(String d_codigo_md) {
		this.d_codigo_md = d_codigo_md;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
