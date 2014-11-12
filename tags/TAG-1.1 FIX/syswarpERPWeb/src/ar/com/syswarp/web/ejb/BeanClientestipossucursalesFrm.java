/* 
 javabean para la entidad (Formulario): clientestipossucursales
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jun 12 12:01:47 GMT-03:00 2007 
 
 Para manejar la pagina: clientestipossucursalesFrm.jsp
 
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

public class BeanClientestipossucursalesFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientestipossucursalesFrm.class);

	private String validar = "";

	private BigDecimal idtiposucursal = BigDecimal.valueOf(-1);

	private BigDecimal idempresa = BigDecimal.valueOf(0);

	private String tiposucursal = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientestipossucursalesFrm() {
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
			Clientes clientestipossucursales = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientestipossucursales
						.clientestipossucursalesCreate(this.tiposucursal,
								this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientestipossucursales
						.clientestipossucursalesUpdate(this.idtiposucursal,
								this.tiposucursal, this.usuarioact,
								this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientestipossucursales
						.clientestipossucursalesDelete(this.idtiposucursal,
								this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientestipossucursales() {
		try {
			Clientes clientestipossucursales = Common.getClientes();
			List listClientestipossucursales = clientestipossucursales
					.getClientestipossucursalesPK(this.idtiposucursal,
							this.idempresa);
			Iterator iterClientestipossucursales = listClientestipossucursales
					.iterator();
			if (iterClientestipossucursales.hasNext()) {
				String[] uCampos = (String[]) iterClientestipossucursales
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idtiposucursal = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.tiposucursal = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientestipossucursales()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientestipossucursalesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
					if (tiposucursal.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo Sucursal ";
						return false;
					}	
					
					
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientestipossucursales();
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
	public BigDecimal getIdtiposucursal() {
		return idtiposucursal;
	}

	public void setIdtiposucursal(BigDecimal idtiposucursal) {
		this.idtiposucursal = idtiposucursal;
	}

	public String getTiposucursal() {
		return tiposucursal;
	}

	public void setTiposucursal(String tiposucursal) {
		this.tiposucursal = tiposucursal;
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
