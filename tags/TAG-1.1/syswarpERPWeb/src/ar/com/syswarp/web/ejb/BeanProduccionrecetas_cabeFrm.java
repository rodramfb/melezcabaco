/* 
 javabean para la entidad (Formulario): produccionRecetas_cabe
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Oct 20 11:54:16 GMT-03:00 2006 
 
 Para manejar la pagina: produccionRecetas_cabeFrm.jsp
 
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

public class BeanProduccionrecetas_cabeFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProduccionrecetas_cabeFrm.class);

	private String validar = "";

	private String codigo_st = "";

	private String d_codigo_st = "";

	private String receta = "";

	private BigDecimal codigo_md = new BigDecimal(0);

	private String d_codigo_md = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanProduccionrecetas_cabeFrm() {
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
			Produccion produccionRecetas_cabe = Common.getProduccion();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = produccionRecetas_cabe
						.produccionRecetas_cabeCreate(this.codigo_st,
								this.receta, this.codigo_md, this.usuarioalt,
								this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = produccionRecetas_cabe
						.produccionRecetas_cabeUpdate(this.codigo_st,
								this.receta, this.codigo_md, this.usuarioact,
								this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = produccionRecetas_cabe
						.produccionRecetas_cabeDelete(this.codigo_st,
								this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProduccionRecetas_cabe() {
		try {
			Produccion produccionRecetas_cabe = Common.getProduccion();
			List listProduccionRecetas_cabe = produccionRecetas_cabe
					.getProduccionRecetas_cabePK(this.codigo_st, this.idempresa);
			Iterator iterProduccionRecetas_cabe = listProduccionRecetas_cabe
					.iterator();
			if (iterProduccionRecetas_cabe.hasNext()) {
				String[] uCampos = (String[]) iterProduccionRecetas_cabe.next();
				// TODO: Constructores para cada tipo de datos
				this.d_codigo_st = uCampos[0];
				this.receta = uCampos[1];
				this.codigo_md = new BigDecimal(uCampos[2]);
				this.d_codigo_md = uCampos[3];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProduccionRecetas_cabe()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("Produccionrecetas_cabeAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					if (d_codigo_st == null) {
						this.mensaje = "No se puede dejar vacio el campo Articulo ";
						return false;
					}

					if (receta == null) {
						this.mensaje = "No se puede dejar vacio el campo Receta ";
						return false;
					}

					if (d_codigo_md == null) {
						this.mensaje = "No se puede dejar vacio el campo U.Medida ";
						return false;
					}

					if (d_codigo_st.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Articulo";
						return false;
					}

					if (receta.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Receta";
						return false;
					}

					if (d_codigo_md.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo U.Medida";
						return false;
					}

					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosProduccionRecetas_cabe();
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

	public String getD_codigo_md() {
		return d_codigo_md;
	}

	public void setD_codigo_md(String d_codigo_md) {
		this.d_codigo_md = d_codigo_md;
	}

	public String getReceta() {
		return receta;
	}

	public void setReceta(String receta) {
		this.receta = receta;
	}

	public BigDecimal getCodigo_md() {
		return codigo_md;
	}

	public void setCodigo_md(BigDecimal codigo_md) {
		this.codigo_md = codigo_md;
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
