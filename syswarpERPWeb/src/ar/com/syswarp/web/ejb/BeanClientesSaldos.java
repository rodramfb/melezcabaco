/* 
 javabean para la entidad: globalLocalidades
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Oct 27 16:35:02 GMT-03:00 2006 
 
 Para manejar la pagina: globalLocalidadesAbm.jsp
 
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

public class BeanClientesSaldos implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanClientesSaldos.class);

	private SessionContext context;

	private List clientesclientesList = new ArrayList();

	// ----------------------------------------------
	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private String tipo = "";

	// ----------------------------------------------

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	public BeanClientesSaldos() {
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
		RequestDispatcher dispatcher = null;
		Clientes clientesclientes = Common.getClientes();
		log.info("validando");
		try {
			// this.clientesclientesList = clientesclientes
			// .getClientesclientesLovAll(this.limit, this.offset);

			if (idcliente.intValue() != -1) {
				this.clientesclientesList = clientesclientes.getCtaCteCliente(
						this.idcliente, this.tipo);
			}
		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public List getClientesclientesList() {
		return clientesclientesList;
	}

	public void setclientesclientesList(List clientesclientesList) {
		this.clientesclientesList = clientesclientesList;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
}
