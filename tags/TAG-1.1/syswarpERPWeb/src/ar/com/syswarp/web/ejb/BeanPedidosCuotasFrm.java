/* 
   javabean para la entidad (Formulario): pedidosCuotas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Sep 03 17:38:57 GMT-03:00 2008 
   
   Para manejar la pagina: pedidosCuotasFrm.jsp
      
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

public class BeanPedidosCuotasFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanPedidosCuotasFrm.class);

	private String validar = "";

	private BigDecimal idcuota = new BigDecimal(-1);

	private String nrocuotas = "";

	private String observaciones = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanPedidosCuotasFrm() {
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
			Clientes pedidosCuotas = Common.getClientes();
			
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = pedidosCuotas.pedidosCuotasCreate(
						new BigDecimal(this.nrocuotas), this.observaciones, this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = pedidosCuotas.pedidosCuotasUpdate(this.idcuota,
						new BigDecimal(this.nrocuotas), this.observaciones, this.idempresa,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = pedidosCuotas.pedidosCuotasDelete(this.idcuota,
						this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosPedidosCuotas() {
		try {
			Clientes pedidosCuotas = Common.getClientes();
			List listPedidosCuotas = pedidosCuotas.getPedidosCuotasPK(
					this.idcuota, this.idempresa);
			Iterator iterPedidosCuotas = listPedidosCuotas.iterator();
			if (iterPedidosCuotas.hasNext()) {
				String[] uCampos = (String[]) iterPedidosCuotas.next();
				// TODO: Constructores para cada tipo de datos
				this.idcuota = new BigDecimal(uCampos[0]);
				this.nrocuotas =  uCampos[1] ;
				this.observaciones = uCampos[2];

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosPedidosCuotas()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("pedidosCuotasAbm.jsp");
				return true;
			}
			
			
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					 
					if (!Common.esEntero(this.nrocuotas)) {
						this.mensaje = "Nro. cuotas debe ser numerico. ";
						return false;
					}
	
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosPedidosCuotas();
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
	public BigDecimal getIdcuota() {
		return idcuota;
	}

	public void setIdcuota(BigDecimal idcuota) {
		this.idcuota = idcuota;
	}

	public String getNrocuotas() {
		return nrocuotas;
	}

	public void setNrocuotas(String nrocuotas) {
		this.nrocuotas = nrocuotas;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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
