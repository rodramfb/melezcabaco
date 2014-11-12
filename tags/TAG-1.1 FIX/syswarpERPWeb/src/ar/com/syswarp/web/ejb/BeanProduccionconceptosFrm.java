/* 
 javabean para la entidad (Formulario): Produccionconceptos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Sep 06 12:38:15 GMT-03:00 2006 
 
 Para manejar la pagina: ProduccionconceptosFrm.jsp
 
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

public class BeanProduccionconceptosFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProduccionconceptosFrm.class);

	private String validar = "";

	private BigDecimal idconcepto = BigDecimal.valueOf(-1);

	private String concepto = "";

	private String formula = "";

	private Double margen_error = Double.valueOf("0");
	
	private Double costo = Double.valueOf("0");

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanProduccionconceptosFrm() {
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
			Produccion Produccionconceptos = Common.getProduccion();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = Produccionconceptos.ProduccionconceptosCreate(
						this.concepto, this.formula, this.margen_error,this.costo,
						this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = Produccionconceptos.ProduccionconceptosUpdate(
						this.idconcepto, this.concepto, this.formula,
						this.margen_error,this.costo, this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = Produccionconceptos.ProduccionconceptosDelete(
						this.idconcepto, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProduccionconceptos() {
		try {
			Produccion Produccionconceptos = Common.getProduccion();
			List listProduccionconceptos = Produccionconceptos
					.getProduccionconceptosPK(this.idconcepto, this.idempresa);
			Iterator iterProduccionconceptos = listProduccionconceptos
					.iterator();
			if (iterProduccionconceptos.hasNext()) {
				String[] uCampos = (String[]) iterProduccionconceptos.next();
				// TODO: Constructores para cada tipo de datos
				this.idconcepto = BigDecimal
						.valueOf(Long.parseLong(uCampos[0]));
				this.concepto = uCampos[1];
				this.formula = uCampos[2];
				this.margen_error = Double.valueOf(uCampos[3]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProduccionconceptos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("ProduccionconceptosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (concepto == null) {
						this.mensaje = "No se puede dejar vacio el campo concepto ";
						return false;
					}
					// 2. len 0 para campos nulos
					// if(concepto.trim().length() == 0 ){
					// this.mensaje = "No se puede dejar con longitud 0 el campo
					// concepto ";
					// return false;
					// }
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosProduccionconceptos();
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
	public BigDecimal getIdconcepto() {
		return idconcepto;
	}

	public void setIdconcepto(BigDecimal idconcepto) {
		this.idconcepto = idconcepto;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Double getMargen_error() {
		return margen_error;
	}

	public void setMargen_error(Double margen_error) {
		this.margen_error = margen_error;
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

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}
}
