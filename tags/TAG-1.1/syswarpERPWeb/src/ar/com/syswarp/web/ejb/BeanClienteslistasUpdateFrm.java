/* 
 javabean para la entidad (Formulario): clienteslistasdeprecios
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Mar 18 17:14:05 ART 2008 
 
 Para manejar la pagina: clienteslistasdepreciosFrm.jsp
 
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

public class BeanClienteslistasUpdateFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClienteslistasUpdateFrm.class);

	private String validar = "";

	private BigDecimal idlista = BigDecimal.valueOf(0);

	private String lista = "";


	private Double precio = Double.valueOf("0");

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClienteslistasUpdateFrm() {
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
			Clientes clienteslistasdeprecios = Common.getClientes();
			this.mensaje = clienteslistasdeprecios.clientesListadosUpdate(this.idlista,
			this.precio, this.idempresa,
			this.usuarioact);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}


	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("inicial.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (precio == null) {
						this.mensaje = "No se puede dejar vacio el campo precio ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (idlista == null) {
						this.mensaje = "No se puede dejar vacio el campo Codig ";
						return false;
					}
					
					
					if (lista.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Lista ";
						return false;
					}
					
					
				}
				this.ejecutarSentenciaDML();
			} else {
				
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
	public BigDecimal getIdlista() {
		return idlista;
	}

	public void setIdlista(BigDecimal idlista) {
		this.idlista = idlista;
	}


	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
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



	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}
}
