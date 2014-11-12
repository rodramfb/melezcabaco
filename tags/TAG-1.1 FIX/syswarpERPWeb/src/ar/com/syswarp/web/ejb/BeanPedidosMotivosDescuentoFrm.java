/* 
   javabean para la entidad (Formulario): pedidosMotivosDescuento
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 11 08:31:00 GYT 2009 
   
   Para manejar la pagina: pedidosMotivosDescuentoFrm.jsp
      
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

public class BeanPedidosMotivosDescuentoFrm implements SessionBean,
		Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanPedidosMotivosDescuentoFrm.class);

	private String validar = "";

	private BigDecimal idmotivodescuento = new BigDecimal(-1);

	private String motivodescuento = "";

	private String cuenta = "";

	private String idcuenta = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal ejercicio = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanPedidosMotivosDescuentoFrm() {
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
			Clientes pedidosMotivosDescuento = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = pedidosMotivosDescuento
						.pedidosMotivosDescuentoCreate(this.motivodescuento,
								new BigDecimal(this.idcuenta), this.idempresa,
								this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = pedidosMotivosDescuento
						.pedidosMotivosDescuentoUpdate(this.idmotivodescuento,
								this.motivodescuento, new BigDecimal(
										this.idcuenta), this.idempresa,
								this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = pedidosMotivosDescuento
						.pedidosMotivosDescuentoDelete(this.idmotivodescuento,
								this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosPedidosMotivosDescuento() {
		try {
			Clientes pedidosMotivosDescuento = Common.getClientes();
			List listPedidosMotivosDescuento = pedidosMotivosDescuento
					.getPedidosMotivosDescuentoPK(this.idmotivodescuento,
							this.ejercicio, this.idempresa);
			Iterator iterPedidosMotivosDescuento = listPedidosMotivosDescuento
					.iterator();
			if (iterPedidosMotivosDescuento.hasNext()) {
				String[] uCampos = (String[]) iterPedidosMotivosDescuento
						.next();
				// TODO: Constructores para cada tipo de datos

				this.idmotivodescuento = new BigDecimal(uCampos[0]);
				this.motivodescuento = uCampos[1];
				this.idcuenta = (uCampos[2]);
				this.cuenta = uCampos[3];

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosPedidosMotivosDescuento()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("pedidosMotivosDescuentoAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (motivodescuento == null) {
						this.mensaje = "No se puede dejar vacio el campo motivodescuento ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (motivodescuento.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo motivodescuento  ";
						return false;
					}

					if (!Common.esEntero(this.idcuenta)) {
						this.mensaje = "Es necesario asociar una cuenta contable.";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosPedidosMotivosDescuento();
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
	public BigDecimal getIdmotivodescuento() {
		return idmotivodescuento;
	}

	public void setIdmotivodescuento(BigDecimal idmotivodescuento) {
		this.idmotivodescuento = idmotivodescuento;
	}

	public String getMotivodescuento() {
		return motivodescuento;
	}

	public void setMotivodescuento(String motivodescuento) {
		this.motivodescuento = motivodescuento;
	}

	public String getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(String idcuenta) {
		this.idcuenta = idcuenta;
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

	public BigDecimal getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(BigDecimal ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
}
