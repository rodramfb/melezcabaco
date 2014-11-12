/* 
 javabean para la entidad (Formulario): formulas_deta
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Mar 12 10:04:47 GMT-03:00 2007 
 
 Para manejar la pagina: formulas_detaFrm.jsp
 
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

public class BeanFormulas_detaFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanFormulas_detaFrm.class);

	private String validar = "";

	private BigDecimal idformula = BigDecimal.valueOf(-1);

	private BigDecimal idformulacabe = BigDecimal.valueOf(0);

	private BigDecimal precedencia = BigDecimal.valueOf(0);

	private String formulacabe = "";

	private String formuladesc = "";

	private String formula_logica = "";

	private String formula_calculo = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanFormulas_detaFrm() {
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
			General formulas_deta = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = formulas_deta.formulas_detaCreate(
						this.idformulacabe, this.precedencia, this.formuladesc,
						this.formula_logica, this.formula_calculo,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = formulas_deta.formulas_detaUpdate(
						this.idformula, this.idformulacabe, this.precedencia,
						this.formuladesc, this.formula_logica,
						this.formula_calculo, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = formulas_deta
						.formulas_detaDelete(this.idformula);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosFormulas_deta() {
		try {
			General formulas_deta = Common.getGeneral();
			List listFormulas_deta = formulas_deta
					.getFormulas_detaPK(this.idformula);
			Iterator iterFormulas_deta = listFormulas_deta.iterator();
			if (iterFormulas_deta.hasNext()) {
				String[] uCampos = (String[]) iterFormulas_deta.next();
				// TODO: Constructores para cada tipo de datos
				this.idformula = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.idformulacabe = BigDecimal.valueOf(Long
						.parseLong(uCampos[1]));
				this.formulacabe = uCampos[2];
				this.precedencia = BigDecimal.valueOf(Long
						.parseLong(uCampos[3]));
				this.formuladesc = uCampos[4];
				this.formula_logica = uCampos[5];
				this.formula_calculo = uCampos[6];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosFormulas_deta()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("formulas_detaAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idformulacabe == null) {
						this.mensaje = "No se puede dejar vacio el campo idformulacabe ";
						return false;
					}
					if (formuladesc == null) {
						this.mensaje = "No se puede dejar vacio el campo Formula Descuento ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (formuladesc.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo Formula Descuento  ";
						return false;
					}

					if (formuladesc.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Formula Descripcion";
						return false;
					}
					if (formula_logica.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Formula Logica";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosFormulas_deta();
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
	public BigDecimal getIdformula() {
		return idformula;
	}

	public void setIdformula(BigDecimal idformula) {
		this.idformula = idformula;
	}

	public BigDecimal getIdformulacabe() {
		return idformulacabe;
	}

	public void setIdformulacabe(BigDecimal idformulacabe) {
		this.idformulacabe = idformulacabe;
	}

	public String getFormuladesc() {
		return formuladesc;
	}

	public void setFormuladesc(String formuladesc) {
		this.formuladesc = formuladesc;
	}

	public String getFormula_logica() {
		return formula_logica;
	}

	public void setFormula_logica(String formula_logica) {
		this.formula_logica = formula_logica;
	}

	public String getFormula_calculo() {
		return formula_calculo;
	}

	public void setFormula_calculo(String formula_calculo) {
		this.formula_calculo = formula_calculo;
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

	public String getFormulacabe() {
		return formulacabe;
	}

	public void setFormulacabe(String formulacabe) {
		this.formulacabe = formulacabe;
	}

	public BigDecimal getPrecedencia() {
		return precedencia;
	}

	public void setPrecedencia(BigDecimal precedencia) {
		this.precedencia = precedencia;
	}
}
