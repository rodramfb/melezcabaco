/* 
 javabean para la entidad (Formulario): clientescredcate
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Nov 14 14:36:19 GMT-03:00 2006 
 
 Para manejar la pagina: clientescredcateFrm.jsp
 
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

public class BeanClientescredcateFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientescredcateFrm.class);

	private String validar = "";

	private BigDecimal idcredcate = BigDecimal.valueOf(-1);
	
	private BigDecimal idempresa ;

	private String credcate = "";

	private BigDecimal dias_cre = BigDecimal.valueOf(0);

	private Double porcen_cre = Double.valueOf("0");

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientescredcateFrm() {
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
			Clientes clientescredcate = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientescredcate.clientescredcateCreate(
						this.credcate, this.dias_cre, this.porcen_cre,
						this.usuarioalt,this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientescredcate.clientescredcateUpdate(
						this.idcredcate, this.credcate, this.dias_cre,
						this.porcen_cre, this.usuarioact,this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientescredcate
						.clientescredcateDelete(this.idcredcate,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientescredcate() {
		try {
			Clientes clientescredcate = Common.getClientes();
			List listClientescredcate = clientescredcate
					.getClientescredcatePK(this.idcredcate,this.idempresa);
			Iterator iterClientescredcate = listClientescredcate.iterator();
			if (iterClientescredcate.hasNext()) {
				String[] uCampos = (String[]) iterClientescredcate.next();
				// TODO: Constructores para cada tipo de datos
				this.idcredcate = BigDecimal
						.valueOf(Long.parseLong(uCampos[0]));
				this.credcate = uCampos[1];
				this.dias_cre = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
				this.porcen_cre = Double.valueOf(uCampos[3]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientescredcate()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientescredcateAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (credcate == null) {
						this.mensaje = "No se puede dejar vacio el campo credcate ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (credcate.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Credito Cuenta Corriente  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientescredcate();
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
	public BigDecimal getIdcredcate() {
		return idcredcate;
	}

	public void setIdcredcate(BigDecimal idcredcate) {
		this.idcredcate = idcredcate;
	}

	public String getCredcate() {
		return credcate;
	}

	public void setCredcate(String credcate) {
		this.credcate = credcate;
	}

	public BigDecimal getDias_cre() {
		return dias_cre;
	}

	public void setDias_cre(BigDecimal dias_cre) {
		this.dias_cre = dias_cre;
	}

	public Double getPorcen_cre() {
		return porcen_cre;
	}

	public void setPorcen_cre(Double porcen_cre) {
		this.porcen_cre = porcen_cre;
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
