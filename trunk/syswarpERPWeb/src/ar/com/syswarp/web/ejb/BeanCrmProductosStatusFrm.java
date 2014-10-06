/* 
 javabean para la entidad (Formulario): crmProductosStatus
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Aug 03 10:09:42 ART 2007 
 
 Para manejar la pagina: crmProductosStatusFrm.jsp
 
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

public class BeanCrmProductosStatusFrm implements SessionBean, Serializable {
	private SessionContext context;
	
	static Logger log = Logger.getLogger(BeanCrmProductosStatusFrm.class);

	private String validar = "";

	private BigDecimal idproductostatus = new BigDecimal(-1);

	private String productostatus = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCrmProductosStatusFrm() {
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
			CRM crmProductosStatus = Common.getCrm();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmProductosStatus.crmProductosStatusCreate(
						this.productostatus, this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmProductosStatus.crmProductosStatusUpdate(
						this.idproductostatus, this.productostatus,
						this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmProductosStatus
						.crmProductosStatusDelete(this.idproductostatus);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmProductosStatus() {
		try {
			CRM crmProductosStatus = Common.getCrm();
			List listCrmProductosStatus = crmProductosStatus
					.getCrmProductosStatusPK(this.idproductostatus,
							this.idempresa);
			Iterator iterCrmProductosStatus = listCrmProductosStatus.iterator();
			if (iterCrmProductosStatus.hasNext()) {
				String[] uCampos = (String[]) iterCrmProductosStatus.next();
				// TODO: Constructores para cada tipo de datos
				this.idproductostatus = new BigDecimal(uCampos[0]);
				this.productostatus = uCampos[1];
				this.idempresa = new BigDecimal(uCampos[2]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCrmProductosStatus()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("crmProductosStatusAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCrmProductosStatus();
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
	public BigDecimal getIdproductostatus() {
		return idproductostatus;
	}

	public void setIdproductostatus(BigDecimal idproductostatus) {
		this.idproductostatus = idproductostatus;
	}

	public String getProductostatus() {
		return productostatus;
	}

	public void setProductostatus(String productostatus) {
		this.productostatus = productostatus;
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
