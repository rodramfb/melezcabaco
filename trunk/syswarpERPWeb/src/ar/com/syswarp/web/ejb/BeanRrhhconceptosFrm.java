/* 
 javabean para la entidad (Formulario): rrhhconceptos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Dec 14 17:06:43 GMT-03:00 2006 
 
 Para manejar la pagina: rrhhconceptosFrm.jsp
 
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

public class BeanRrhhconceptosFrm implements SessionBean, Serializable {
	public String getIdctacont() {
		return idctacont;
	}

	public void setIdctacont(String idctacont) {
		this.idctacont = idctacont;
	}

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRrhhconceptosFrm.class);

	private String validar = "";

	private BigDecimal idconcepto = BigDecimal.valueOf(-1);

	private BigDecimal idempresa;
	
	private int ejercicio = 01;

	public int getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(int ejercicio) {
		this.ejercicio = ejercicio;
	}

	private String concepto = "";      

	private String imprime = "";

	private String formula = "";

	private String idctacont = "";
	
	private BigDecimal idtipoconcepto = BigDecimal.valueOf(0);
	private String tipoconcepto = "";
	
	private BigDecimal idtipocantidadconcepto = BigDecimal.valueOf(0);
	private String tipocantidadconcepto = "";
	
	private Double valor = Double.valueOf("0");

	private String usuarioalt;

	
	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanRrhhconceptosFrm() {
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
			RRHH rrhhconceptos = Common.getRrhh();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = rrhhconceptos.rrhhconceptosCreate(this.concepto,
						this.imprime, this.formula, this.idctacont.equals("")
								|| this.idctacont.equals("null") ? null
								: new BigDecimal(this.idctacont),this.idtipoconcepto,
								this.idtipocantidadconcepto,this.valor,
						this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = rrhhconceptos.rrhhconceptosUpdate(
						this.idconcepto, this.concepto, this.imprime,
						this.formula, this.idctacont.equals("")
								|| this.idctacont.equals("null") ? null
								: new BigDecimal(this.idctacont),this.idtipoconcepto,
								this.idtipocantidadconcepto,this.valor, this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhhconceptos.rrhhconceptosDelete(
						this.idconcepto, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhconceptos() {
		try {
			RRHH rrhhconceptos = Common.getRrhh();
			List listRrhhconceptos = rrhhconceptos.getRrhhconceptosPK(
					this.idconcepto, this.idempresa);
			Iterator iterRrhhconceptos = listRrhhconceptos.iterator();
			if (iterRrhhconceptos.hasNext()) {
				String[] uCampos = (String[]) iterRrhhconceptos.next();
				// TODO: Constructores para cada tipo de datos
				this.idconcepto = BigDecimal
						.valueOf(Long.parseLong(uCampos[0]));
				this.concepto = uCampos[1];
				this.imprime = uCampos[2];
				this.formula = uCampos[3];
				this.idctacont = uCampos[4];
				this.idtipoconcepto= BigDecimal.valueOf(Long.parseLong(uCampos[5]));
				this.tipoconcepto= uCampos[6];
				this.idtipocantidadconcepto= BigDecimal.valueOf(Long.parseLong(uCampos[7]));
				this.tipocantidadconcepto= uCampos[8];
				this.valor = Double.valueOf(uCampos[9]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhconceptos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhconceptosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (concepto == null) {
						this.mensaje = "No se puede dejar vacio el campo concepto ";
						return false;
					}
					if (imprime == null) {
						this.mensaje = "No se puede dejar vacio el campo imprime ";
						return false;
					}
					if (formula == null) {
						this.mensaje = "No se puede dejar vacio el campo formula ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (concepto.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Concepto  ";
						return false;
					}
					if (imprime.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Imprime  ";
						return false;
					}
					if (formula.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Formula  ";
						return false;
					}
					if (tipoconcepto.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo Concepto  ";
						return false;
					}
					
					if (tipocantidadconcepto.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo Cantidad Concepto  ";
						return false;
					}
					
					
					
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhconceptos();
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

	public String getImprime() {
		return imprime;
	}

	public void setImprime(String imprime) {
		this.imprime = imprime;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BigDecimal getIdtipoconcepto() {
		return idtipoconcepto;
	}

	public void setIdtipoconcepto(BigDecimal idtipoconcepto) {
		this.idtipoconcepto = idtipoconcepto;
	}

	public String getTipoconcepto() {
		return tipoconcepto;
	}

	public void setTipoconcepto(String tipoconcepto) {
		this.tipoconcepto = tipoconcepto;
	}

	public BigDecimal getIdtipocantidadconcepto() {
		return idtipocantidadconcepto;
	}

	public void setIdtipocantidadconcepto(BigDecimal idtipocantidadconcepto) {
		this.idtipocantidadconcepto = idtipocantidadconcepto;
	}

	public String getTipocantidadconcepto() {
		return tipocantidadconcepto;
	}

	public void setTipocantidadconcepto(String tipocantidadconcepto) {
		this.tipocantidadconcepto = tipocantidadconcepto;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}





}
