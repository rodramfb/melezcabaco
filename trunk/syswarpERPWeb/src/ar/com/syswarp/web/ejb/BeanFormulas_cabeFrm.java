/* 
 javabean para la entidad (Formulario): formulas_cabe
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Mar 12 10:04:42 GMT-03:00 2007 
 
 Para manejar la pagina: formulas_cabeFrm.jsp
 
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

public class BeanFormulas_cabeFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanFormulas_cabeFrm.class);

	private String validar = "";

	private BigDecimal idformulacabe = BigDecimal.valueOf(-1);

	private String formula = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanFormulas_cabeFrm() {
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
			General formulas_cabe = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = formulas_cabe.formulas_cabeCreate(this.formula,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = formulas_cabe.formulas_cabeUpdate(
						this.idformulacabe, this.formula, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = formulas_cabe
						.formulas_cabeDelete(this.idformulacabe);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosFormulas_cabe() {
		try {
			General formulas_cabe = Common.getGeneral();
			List listFormulas_cabe = formulas_cabe
					.getFormulas_cabePK(this.idformulacabe);
			Iterator iterFormulas_cabe = listFormulas_cabe.iterator();
			if (iterFormulas_cabe.hasNext()) {
				String[] uCampos = (String[]) iterFormulas_cabe.next();
				// TODO: Constructores para cada tipo de datos
				this.idformulacabe = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.formula = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosFormulas_cabe()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("formulas_cabeAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
					if (formula.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Formula  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosFormulas_cabe();
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
	public BigDecimal getIdformulacabe() {
		return idformulacabe;
	}

	public void setIdformulacabe(BigDecimal idformulacabe) {
		this.idformulacabe = idformulacabe;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
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
