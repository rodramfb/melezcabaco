/* 
 javabean para la entidad (Formulario): globalentidadesasociables
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue May 06 14:29:26 CEST 2008 
 
 Para manejar la pagina: globalentidadesasociablesFrm.jsp
 
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

public class BeanGlobalentidadesasociablesFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanGlobalentidadesasociablesFrm.class);

	private String validar = "";

	private BigDecimal identidadesasociables = BigDecimal.valueOf(-1);

	private String entidadasociable = "";

	private String descripcion = "";

	private String campopk = "";

	private String querypk = "";

	private String querygrilla = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanGlobalentidadesasociablesFrm() {
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
			General globalentidadesasociables = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = globalentidadesasociables
						.globalentidadesasociablesCreate(this.entidadasociable,
								this.descripcion, this.campopk, this.querypk,
								this.querygrilla, this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = globalentidadesasociables
						.globalentidadesasociablesUpdate(
								this.identidadesasociables,
								this.entidadasociable, this.descripcion,
								this.campopk, this.querypk, this.querygrilla,
								this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = globalentidadesasociables
						.globalentidadesasociablesDelete(
								this.identidadesasociables, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGlobalentidadesasociables() {
		try {
			General globalentidadesasociables = Common.getGeneral();
			List listGlobalentidadesasociables = globalentidadesasociables
					.getGlobalentidadesasociablesPK(this.identidadesasociables,
							this.idempresa);
			Iterator iterGlobalentidadesasociables = listGlobalentidadesasociables
					.iterator();
			if (iterGlobalentidadesasociables.hasNext()) {
				String[] uCampos = (String[]) iterGlobalentidadesasociables
						.next();
				// TODO: Constructores para cada tipo de datos
				this.identidadesasociables = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.entidadasociable = uCampos[1];
				this.descripcion = uCampos[2];
				this.campopk = uCampos[3];
				this.querypk = uCampos[4];
				this.querygrilla = uCampos[5];
				this.idempresa = BigDecimal.valueOf(Long.parseLong(uCampos[6]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGlobalentidadesasociables()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("globalentidadesasociablesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (entidadasociable == null) {
						this.mensaje = "No se puede dejar vacio el campo entidadasociable ";
						return false;
					}
					if (descripcion == null) {
						this.mensaje = "No se puede dejar vacio el campo descripcion ";
						return false;
					}
					if (campopk == null) {
						this.mensaje = "No se puede dejar vacio el campo campopk ";
						return false;
					}

					// 2. len 0 para campos nulos
					if (entidadasociable.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Entidad asociable  ";
						return false;
					}
					if (descripcion.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Descripcion  ";
						return false;
					}
					if (campopk.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Campo pk  ";
						return false;
					}
					if (querypk.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Query pk  ";
						return false;
					}

					if (querygrilla.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Query Grilla  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGlobalentidadesasociables();
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
	public BigDecimal getIdentidadesasociables() {
		return identidadesasociables;
	}

	public void setIdentidadesasociables(BigDecimal identidadesasociables) {
		this.identidadesasociables = identidadesasociables;
	}

	public String getEntidadasociable() {
		return entidadasociable;
	}

	public void setEntidadasociable(String entidadasociable) {
		this.entidadasociable = entidadasociable;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCampopk() {
		return campopk;
	}

	public void setCampopk(String campopk) {
		this.campopk = campopk;
	}

	public String getQuerypk() {
		return querypk;
	}

	public void setQuerypk(String querypk) {
		this.querypk = querypk;
	}

	public String getQuerygrilla() {
		return querygrilla;
	}

	public void setQuerygrilla(String querylov) {
		this.querygrilla = querylov;
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
}
