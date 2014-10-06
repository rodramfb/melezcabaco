/* 
 javabean para la entidad: clienteszonas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Nov 14 14:50:18 GMT-03:00 2006 
 
 Para manejar la pagina: clienteszonasAbm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanBacoErNecesidad implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanBacoErNecesidad.class);

	private SessionContext context;

	private int anio = -1;

	private int idmes = -1;

	private BigDecimal idempresa;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private List necesidadList = new ArrayList();

	public BeanBacoErNecesidad() {
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
		Clientes clientes = Common.getClientes();
		try {
              
			this.necesidadList = clientes.getERegularesNecesidad(
					this.anio, this.idmes, this.idempresa);

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getIdmes() {
		return idmes;
	}

	public void setIdmes(int idmes) {
		this.idmes = idmes;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public List getNecesidadList() {
		return necesidadList;
	}

	public void setNecesidadList(List necesidadList) {
		this.necesidadList = necesidadList;
	}

}
