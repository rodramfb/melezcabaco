/* 
 javabean para la entidad (Formulario): clientesprecios
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Apr 11 13:21:59 GMT-03:00 2007 
 
 Para manejar la pagina: clientespreciosFrm.jsp
 
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

public class BeanClientespreciosFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientespreciosFrm.class);

	private String validar = "";

	private BigDecimal idprecio = BigDecimal.valueOf(-1);
	
	private BigDecimal idempresa;

	private String codigo_st = "";

	private String descrip_st = "";

	private BigDecimal idlista = BigDecimal.valueOf(0);

	private String lista = "";

	private Double precio_pre = Double.valueOf("0");

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private BigDecimal control = BigDecimal.valueOf(0);

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientespreciosFrm() {
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
			Clientes clientesprecios = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientesprecios.clientespreciosCreateOrUpdate(
						this.idprecio, this.codigo_st, this.idlista,
						this.precio_pre, this.usuarioalt,this.idempresa);

			if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientesprecios
						.clientespreciosDelete(this.idprecio,this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientespreciosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (codigo_st == null) {
						this.mensaje = "No se puede dejar vacio el campo codigo_st ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (codigo_st.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo codigo_st  ";
						return false;
					}

					if (idlista.compareTo(new BigDecimal(0)) == 0) {
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
	public BigDecimal getIdprecio() {
		return idprecio;
	}

	public void setIdprecio(BigDecimal idprecio) {
		this.idprecio = idprecio;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public BigDecimal getIdlista() {
		return idlista;
	}

	public void setIdlista(BigDecimal idlista) {
		this.idlista = idlista;
	}

	public Double getPrecio_pre() {
		return precio_pre;
	}

	public void setPrecio_pre(Double precio_pre) {
		this.precio_pre = precio_pre;
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

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public BigDecimal getControl() {
		return control;
	}

	public void setControl(BigDecimal control) {
		this.control = control;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
