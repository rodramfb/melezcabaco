/* 
 javabean para la entidad (Formulario): produccionprogramaaux
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Dec 13 16:04:32 GMT-03:00 2006 
 
 Para manejar la pagina: produccionprogramaauxFrm.jsp
 
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
import java.sql.*;

public class BeanProduccionprogramaauxFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProduccionprogramaauxFrm.class);

	private String validar = "";

	private BigDecimal idopaux;

	private String codigo_st;

	private Double cantidad;

	private Timestamp fecha_prom;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanProduccionprogramaauxFrm() {
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
			Produccion produccionprogramaaux = Common.getProduccion();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = produccionprogramaaux
						.produccionprogramaauxCreate(this.codigo_st,
								this.cantidad, this.fecha_prom,
								this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = produccionprogramaaux
						.produccionprogramaauxUpdate(this.idopaux,
								this.codigo_st, this.cantidad, this.fecha_prom,
								this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = produccionprogramaaux
						.produccionprogramaauxDelete(this.idopaux,
								this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProduccionprogramaaux() {
		try {
			Produccion produccionprogramaaux = Common.getProduccion();
			List listProduccionprogramaaux = produccionprogramaaux
					.getProduccionprogramaauxPK(this.idopaux, this.idempresa);
			Iterator iterProduccionprogramaaux = listProduccionprogramaaux
					.iterator();
			if (iterProduccionprogramaaux.hasNext()) {
				String[] uCampos = (String[]) iterProduccionprogramaaux.next();
				// TODO: Constructores para cada tipo de datos
				this.idopaux = new BigDecimal(uCampos[0]);
				this.codigo_st = uCampos[1];
				this.cantidad = new Double(uCampos[2]);
				// this.fecha_prom= new Timestamp(uCampos[3]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProduccionprogramaaux()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("produccionprogramaauxAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (codigo_st == null) {
						this.mensaje = "No se puede dejar vacio el campo codigo_st ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (codigo_st.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo codigo_st  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosProduccionprogramaaux();
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
	public BigDecimal getIdopaux() {
		return idopaux;
	}

	public void setIdopaux(BigDecimal idopaux) {
		this.idopaux = idopaux;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Timestamp getFecha_prom() {
		return fecha_prom;
	}

	public void setFecha_prom(Timestamp fecha_prom) {
		this.fecha_prom = fecha_prom;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
