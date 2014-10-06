/* 
 javabean para la entidad (Formulario): crmclasificacionclientes
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jun 14 17:20:08 GMT-03:00 2007 
 
 Para manejar la pagina: crmclasificacionclientesFrm.jsp
 
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

public class BeanGeneralTipoobsequiosFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanGeneralTipoobsequiosFrm.class);

	private String validar = "";

	private BigDecimal idtipoobsequio = BigDecimal.valueOf(-1);

	private String tipoobsequio = "";

	private BigDecimal idmotivodescuento = new BigDecimal(-1);

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listMotivosDescuento = new ArrayList();

	public BeanGeneralTipoobsequiosFrm() {
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
			General crmclasificacionclientes = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = crmclasificacionclientes
						.BacotipoobsequiosCreate(this.tipoobsequio,
								this.idmotivodescuento, this.usuarioalt,
								this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = crmclasificacionclientes
						.BacotipoobsequiosUpdate(this.idtipoobsequio,
								this.tipoobsequio, this.idmotivodescuento,
								this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = crmclasificacionclientes
						.BacotipoobsequiosDelete(this.idtipoobsequio,
								this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCrmclasificacionclientes() {
		try {
			General crmclasificacionclientes = Common.getGeneral();
			List listCrmclasificacionclientes = crmclasificacionclientes
					.getBacotipoobsequiosPK(this.idtipoobsequio, this.idempresa);
			Iterator iterCrmclasificacionclientes = listCrmclasificacionclientes
					.iterator();
			if (iterCrmclasificacionclientes.hasNext()) {
				String[] uCampos = (String[]) iterCrmclasificacionclientes
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idtipoobsequio = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.tipoobsequio = uCampos[1];
				this.idmotivodescuento = new BigDecimal(uCampos[2]);

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getBacotipoobsequiosPK()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("tipoobsequiosAbm.jsp");
				return true;
			}

			BigDecimal ejercicio = new BigDecimal(request.getSession()
					.getAttribute("ejercicioActivo")
					+ "");

			this.listMotivosDescuento = Common
					.getClientes()
					.getPedidosMotivosDescuentoAll(250, 0, ejercicio, idempresa);

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (tipoobsequio.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Tipo Obsequio";
						return false;
					}

					if (this.idmotivodescuento.longValue() < 1) {
						this.mensaje = "Seleccione motivo de descuento.";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCrmclasificacionclientes();
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

	public BigDecimal getIdtipoobsequio() {
		return idtipoobsequio;
	}

	public void setIdtipoobsequio(BigDecimal idtipoobsequio) {
		this.idtipoobsequio = idtipoobsequio;
	}

	public String getTipoobsequio() {
		return tipoobsequio;
	}

	public void setTipoobsequio(String tipoobsequio) {
		this.tipoobsequio = tipoobsequio;
	}

	public BigDecimal getIdmotivodescuento() {
		return idmotivodescuento;
	}

	public void setIdmotivodescuento(BigDecimal idmotivodescuento) {
		this.idmotivodescuento = idmotivodescuento;
	}

	public List getListMotivosDescuento() {
		return listMotivosDescuento;
	}

	public void setListMotivosDescuento(List listMotivosDescuento) {
		this.listMotivosDescuento = listMotivosDescuento;
	}

}
