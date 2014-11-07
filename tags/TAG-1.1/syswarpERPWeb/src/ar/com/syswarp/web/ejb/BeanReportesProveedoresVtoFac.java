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

public class BeanReportesProveedoresVtoFac implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanReportesProveedoresVtoFac.class);

	private String validar = "";

	// private java.sql.Date fechavtodesde = new java.sql.Date(Common
	// .initObjectTime());

	// private String fechavtodesdeStr = Common.initObjectTimeStr();
	private String fechavtodesdeStr = "";

	// private java.sql.Date fechavtohasta = new java.sql.Date(Common
	// .initObjectTime());

	// private String fechavtohastaStr = Common.initObjectTimeStr();
	private String fechavtohastaStr = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanReportesProveedoresVtoFac() {
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

	public boolean ejecutarValidacion() {
		try {
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (fechavtodesdeStr.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Fecha Desde ";
						return false;
					}
					if (fechavtohastaStr.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Fecha Hasta ";
						return false;
					}

				}
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					// getDatosRrhhconceptos();
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

	/*
	 * public Date getFechavtodesde() { return fechavtodesde; }
	 * 
	 * public void setFechavtodesde(java.sql.Date fechavtodesde) {
	 * this.fechavtodesde = fechavtodesde; }
	 * 
	 * public String getFechavtodesdeStr() { return fechavtodesdeStr; }
	 * 
	 * public void setFechavtodesdeStr(String fechavtodesdeStr) {
	 * this.fechavtodesdeStr = fechavtodesdeStr; this.fechavtodesde =
	 * (java.sql.Date) Common.setObjectToStrOrTime( fechavtodesdeStr,
	 * "strToJSDate"); }
	 * 
	 * public Date getFechavtohasta() { return fechavtohasta; }
	 * 
	 * public void setFechavtohasta(java.sql.Date fechavtohasta) {
	 * this.fechavtohasta = fechavtohasta; }
	 * 
	 * public String getFechavtohastaStr() { return fechavtohastaStr; }
	 * 
	 * public void setFechavtohastaStr(String fechavtohastaStr) {
	 * this.fechavtohastaStr = fechavtohastaStr; this.fechavtohasta =
	 * (java.sql.Date) Common.setObjectToStrOrTime( fechavtohastaStr,
	 * "strToJSDate"); }
	 */

	public String getFechavtodesdeStr() {
		return fechavtodesdeStr;
	}

	public void setFechavtodesdeStr(String fechavtodesdeStr) {
		this.fechavtodesdeStr = fechavtodesdeStr;
	}

	public String getFechavtohastaStr() {
		return fechavtohastaStr;
	}

	public void setFechavtohastaStr(String fechavtohastaStr) {
		this.fechavtohastaStr = fechavtohastaStr;
	}
}
