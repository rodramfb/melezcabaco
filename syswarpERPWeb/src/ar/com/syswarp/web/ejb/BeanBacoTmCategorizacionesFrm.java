/* 
 javabean para la entidad (Formulario): bacoTmCategorizaciones
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Apr 04 17:01:51 ART 2007 
 
 Para manejar la pagina: bacoTmCategorizacionesFrm.jsp
 
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

public class BeanBacoTmCategorizacionesFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoTmCategorizacionesFrm.class);

	private String validar = "";

	private BigDecimal idcategorizacion = BigDecimal.valueOf(-1);

	private BigDecimal idcliente = BigDecimal.valueOf(0);

	private String cliente = "";

	private BigDecimal idcategoria = BigDecimal.valueOf(-1);

	private BigDecimal idempresa;

	private String categoria = "";

	private java.sql.Date fhasta = null;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listBacoTmCategorizaciones = new ArrayList();

	public BeanBacoTmCategorizacionesFrm() {
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
			Clientes bacoTmCategorizaciones = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta")) {
				
				this.mensaje = "Cliente categorizado correctamente.";

			} else if (this.accion.equalsIgnoreCase("modificacion")) {
				this.mensaje = bacoTmCategorizaciones
						.bacoTmCategorizacionesCreate(this.idcliente,
								this.idcategoria, this.fhasta, this.usuarioalt,
								this.idempresa);
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoTmCategorizaciones() {
		try {

		} catch (Exception e) {
			log.error("getDatosBacoTmCategorizaciones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			Clientes bacoTmCategorizaciones = Common.getClientes();

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacoTmCategorizacionesAbm.jsp");
				return true;
			}

			if (!this.validar.equalsIgnoreCase("")) {

				// 1. nulidad de campos

				if ( idcliente == null || idcliente.longValue() < 1 ) {
					this.mensaje = "No se puede dejar vacio el campo Cliente ";
				} else if ( idcategoria == null || idcategoria.longValue() < 1 ) {
					this.mensaje = "No se puede dejar vacio el campo Categoria ";
				} else if ( categoria == null || categoria.equals("") ) {
					this.mensaje = "No se puede dejar vacio el campo Categoria ";
				} else
					this.ejecutarSentenciaDML();
			}

			this.listBacoTmCategorizaciones = bacoTmCategorizaciones
					.getBacoTmCategorizacionesClienteAll(this.idcliente,
							this.idempresa);

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
	public BigDecimal getIdcategorizacion() {
		return idcategorizacion;
	}

	public void setIdcategorizacion(BigDecimal idcategorizacion) {
		this.idcategorizacion = idcategorizacion;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public BigDecimal getIdcategoria() {
		return idcategoria;
	}

	public void setIdcategoria(BigDecimal idcategoria) {
		this.idcategoria = idcategoria;
	}

	public Date getFhasta() {
		return fhasta;
	}

	public void setFhasta(java.sql.Date fhasta) {
		this.fhasta = fhasta;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public List getListBacoTmCategorizaciones() {
		return listBacoTmCategorizaciones;
	}

}
