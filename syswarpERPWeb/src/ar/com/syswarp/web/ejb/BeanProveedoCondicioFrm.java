/* 
 javabean para la entidad (Formulario): proveedoCondicio
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Jul 05 11:18:15 GMT-03:00 2006 
 
 Para manejar la pagina: proveedoCondicioFrm.jsp
 
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

public class BeanProveedoCondicioFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProveedoCondicioFrm.class);

	private String validar = "";

	private BigDecimal idcondicionpago = BigDecimal.valueOf(-1);

	private String condicionpago = "";

	private BigDecimal cantidaddias = BigDecimal.valueOf(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanProveedoCondicioFrm() {
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
			Proveedores proveedoCondicio = Common.getProveedores();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = proveedoCondicio.proveedoCondicioCreate(
						this.condicionpago, this.cantidaddias, this.usuarioalt,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = proveedoCondicio.proveedoCondicioUpdate(
						this.idcondicionpago, this.condicionpago,
						this.cantidaddias, this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = proveedoCondicio.proveedoCondicioDelete(
						this.idcondicionpago, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProveedoCondicio() {
		try {
			Proveedores proveedoCondicio = Common.getProveedores();
			List listProveedoCondicio = proveedoCondicio.getProveedoCondicioPK(
					this.idcondicionpago, this.idempresa);
			Iterator iterProveedoCondicio = listProveedoCondicio.iterator();
			if (iterProveedoCondicio.hasNext()) {
				String[] uCampos = (String[]) iterProveedoCondicio.next();
				// TODO: Constructores para cada tipo de datos
				this.idcondicionpago = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.condicionpago = uCampos[1];
				this.cantidaddias = BigDecimal.valueOf(Long
						.parseLong(uCampos[2]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProveedoCondicio()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("proveedoCondicioAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (condicionpago == null) {
						this.mensaje = "No se puede dejar vacio el campo Condicion de Pago ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (condicionpago.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Condicion de Pago ";
						return false;
					}
					if (cantidaddias == null) {
						this.mensaje = "No se puede dejar vacio el campo Cantidad de Dias";
						return false;
					}
					// if (cantidaddias.abs().longValue()== 0) {
					// this.mensaje = "No se puede dejar vacio el campo Cantidad
					// de Dias";
					// return false;
					// }
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosProveedoCondicio();
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
	public BigDecimal getIdcondicionpago() {
		return idcondicionpago;
	}

	public void setIdcondicionpago(BigDecimal idcondicionpago) {
		this.idcondicionpago = idcondicionpago;
	}

	public String getCondicionpago() {
		return condicionpago;
	}

	public void setCondicionpago(String condicionpago) {
		this.condicionpago = condicionpago;
	}

	public BigDecimal getCantidaddias() {
		return cantidaddias;
	}

	public void setCantidaddias(BigDecimal cantidaddias) {
		this.cantidaddias = cantidaddias;
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
