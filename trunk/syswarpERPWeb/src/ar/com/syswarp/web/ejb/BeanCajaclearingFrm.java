/* 
 javabean para la entidad (Formulario): Cajaclearing
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:36:49 GMT-03:00 2006 
 
 Para manejar la pagina: CajaclearingFrm.jsp
 
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

public class BeanCajaclearingFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);
	
	static Logger log = Logger.getLogger(BeanCajaclearingFrm.class);

	private String validar = "";

	private BigDecimal idclearing = BigDecimal.valueOf(-1);

	private String horas_cl = "";

	private BigDecimal dias_cl = BigDecimal.valueOf(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanCajaclearingFrm() {
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
			Tesoreria Cajaclearing = Common.getTesoreria();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = Cajaclearing.CajaclearingCreate(this.horas_cl,
						this.dias_cl, this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = Cajaclearing.CajaclearingUpdate(this.idclearing,
						this.horas_cl, this.dias_cl, this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = Cajaclearing.CajaclearingDelete(this.idclearing, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCajaclearing() {
		try {
			Tesoreria Cajaclearing = Common.getTesoreria();
			List listCajaclearing = Cajaclearing
					.getCajaclearingPK(this.idclearing, this.idempresa);
			Iterator iterCajaclearing = listCajaclearing.iterator();
			if (iterCajaclearing.hasNext()) {
				String[] uCampos = (String[]) iterCajaclearing.next();
				// TODO: Constructores para cada tipo de datos
				this.idclearing = BigDecimal
						.valueOf(Long.parseLong(uCampos[0]));
				this.horas_cl = uCampos[1];
				this.dias_cl = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosCajaclearing()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("CajaclearingAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosCajaclearing();
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
	public BigDecimal getIdclearing() {
		return idclearing;
	}

	public void setIdclearing(BigDecimal idclearing) {
		this.idclearing = idclearing;
	}

	public String getHoras_cl() {
		return horas_cl;
	}

	public void setHoras_cl(String horas_cl) {
		this.horas_cl = horas_cl;
	}

	public BigDecimal getDias_cl() {
		return dias_cl;
	}

	public void setDias_cl(BigDecimal dias_cl) {
		this.dias_cl = dias_cl;
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
