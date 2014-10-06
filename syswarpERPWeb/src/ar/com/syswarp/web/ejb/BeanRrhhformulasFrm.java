/* 
   javabean para la entidad (Formulario): rrhhformulas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 04 17:02:24 ART 2012 
   
   Para manejar la pagina: rrhhformulasFrm.jsp
      
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

public class BeanRrhhformulasFrm implements SessionBean, Serializable {
	private SessionContext context;
	static Logger log = Logger.getLogger(BeanRrhhformulasFrm.class);
	
	private String validar = "";
	
	private BigDecimal idformula = new BigDecimal(-1);
	
	private String formula ="";
	
	private String descripcion = "";
	
	private String sql = "";
	
	private BigDecimal idempresa = new BigDecimal (-1);
	
	private String usuarioalt ="";
	
	private String usuarioact = "";
	
	private String mensaje = "";
	
	private String volver = "";
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String accion = "";

	public BeanRrhhformulasFrm() {
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
			RRHH recursoshumanos = Common.getRrhh();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = recursoshumanos.rrhhformulasCreate(this.formula,
						this.descripcion, this.sql, this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = recursoshumanos.rrhhformulasUpdate(this.idformula,
						this.formula, this.descripcion, this.sql,
						this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = recursoshumanos.rrhhformulasDelete(this.idformula,
						this.idempresa);
			
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhformulas() {
		try {
			RRHH recursoshumanos = Common.getRrhh();
			List listRrhhformulas = recursoshumanos.getRrhhformulasPK(this.idformula,
					this.idempresa);
			Iterator iterRrhhformulas = listRrhhformulas.iterator();
			if (iterRrhhformulas.hasNext()) {
				String[] uCampos = (String[]) iterRrhhformulas.next();
				// TODO: Constructores para cada tipo de datos
				this.idformula = new BigDecimal(uCampos[0]);
				this.formula = uCampos[1];
				this.descripcion = uCampos[2];
				this.sql = uCampos[3];
				this.idempresa = new BigDecimal(uCampos[4]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhformulas()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhformulasAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (formula == null) {
						this.mensaje = "No se puede dejar vacio el campo formula ";
						return false;
					}
					if (descripcion == null) {
						this.mensaje = "No se puede dejar vacio el campo descripcion ";
						return false;
					}
					//20120706 - CAMI - Se agrego este replace ya que el nombre de la formula no debe contener espacios.
					formula = formula.replace(" ", "");
					// 2. len 0 para campos nulos
					if (formula.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo formula  ";
						return false;
					}
					if (descripcion.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo descripcion  ";
						return false;
					}
					if (sql.length()==0)
					{
						this.mensaje = "No se puede dejar vacio el campo Sql  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhformulas();
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

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
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
