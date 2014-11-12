/* 
 javabean para la entidad (Formulario): produccionRecetas_deta
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Oct 23 12:38:58 GMT-03:00 2006 
 
 Para manejar la pagina: produccionRecetas_detaFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanProduccionrecetas_detaFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProduccionrecetas_detaFrm.class);

	private String validar = "";

	private String codigo_st_cabe = "";

	private String codigo_st = "";

	private String d_codigo_st = "";

	private String tipo_rd = "";

	private Double cantidad_rd = new Double(0);

	private String imprime = "";

	private Double margen_error_rd = new Double(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private String descrip_st = "";

	public BeanProduccionrecetas_detaFrm() {
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
			Produccion produccionRecetas_deta = Common.getProduccion();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = produccionRecetas_deta
						.produccionRecetas_detaCreate(this.codigo_st_cabe,
								this.codigo_st, this.tipo_rd, this.cantidad_rd,
								this.imprime, this.margen_error_rd,
								this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = produccionRecetas_deta
						.produccionRecetas_detaUpdate(this.codigo_st_cabe,
								this.codigo_st, this.tipo_rd, this.cantidad_rd,
								this.imprime, this.margen_error_rd,
								this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = produccionRecetas_deta
						.produccionRecetas_detaDelete(this.codigo_st_cabe,
								this.codigo_st, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProduccionRecetas_deta() {
		try {
			Produccion produccionRecetas_deta = Common.getProduccion();
			List listProduccionRecetas_deta = produccionRecetas_deta
					.getProduccionRecetas_detaPK(this.codigo_st_cabe,
							this.codigo_st, this.idempresa);
			Iterator iterProduccionRecetas_deta = listProduccionRecetas_deta
					.iterator();
			if (iterProduccionRecetas_deta.hasNext()) {
				String[] uCampos = (String[]) iterProduccionRecetas_deta.next();
				// TODO: Constructores para cada tipo de datos
				this.codigo_st_cabe = uCampos[0];
				this.d_codigo_st = uCampos[1];
				this.tipo_rd = uCampos[2];
				this.cantidad_rd = new java.lang.Double(uCampos[3]);
				this.imprime = uCampos[4];
				this.margen_error_rd = new java.lang.Double(uCampos[5]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProduccionRecetas_deta()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			// RequestDispatcher dispatcher = null;
			if (!this.volver.equalsIgnoreCase("")) {
				/*
				 * dispatcher =
				 * request.getRequestDispatcher("Produccionrecetas_detaAbm.jsp");
				 * dispatcher.forward(request, response);
				 */
				response.sendRedirect("Produccionrecetas_detaAbm.jsp");

				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (codigo_st == null) {
						this.mensaje = "No se puede dejar vacio el campo codigo_st ";
						return false;
					}
					if (tipo_rd == null) {
						this.mensaje = "No se puede dejar vacio el campo tipo_rd ";
						return false;
					}
					if (cantidad_rd == null) {
						this.mensaje = "No se puede dejar vacio el campo cantidad_rd ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (codigo_st.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Articulo  ";
						return false;
					}
					if (tipo_rd.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosProduccionRecetas_deta();
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
	public String getCodigo_st_cabe() {
		return codigo_st_cabe;
	}

	public void setCodigo_st_cabe(String codigo_st_cabe) {
		this.codigo_st_cabe = codigo_st_cabe;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public String getD_codigo_st() {
		return d_codigo_st;
	}

	public void setD_codigo_st(String d_codigo_st) {
		this.d_codigo_st = d_codigo_st;
	}

	public String getTipo_rd() {
		return tipo_rd;
	}

	public void setTipo_rd(String tipo_rd) {
		this.tipo_rd = tipo_rd;
	}

	public Double getCantidad_rd() {
		return cantidad_rd;
	}

	public void setCantidad_rd(Double cantidad_rd) {
		this.cantidad_rd = cantidad_rd;
	}

	public String getImprime() {
		return imprime;
	}

	public void setImprime(String imprime) {
		this.imprime = imprime;
	}

	public Double getMargen_error_rd() {
		return margen_error_rd;
	}

	public void setMargen_error_rd(Double margen_error_rd) {
		this.margen_error_rd = margen_error_rd;
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

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
