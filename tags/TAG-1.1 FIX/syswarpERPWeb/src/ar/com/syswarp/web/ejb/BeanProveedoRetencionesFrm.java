/* 
 javabean para la entidad (Formulario): proveedoRetenciones
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jul 04 16:50:00 GMT-03:00 2006 
 
 Para manejar la pagina: proveedoRetencionesFrm.jsp
 
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
import java.lang.Double;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanProveedoRetencionesFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProveedoRetencionesFrm.class);

	private String validar = "";

	private BigDecimal idretencion = BigDecimal.valueOf(-1);

	private String retencion = "";

	private Double impor1_ret = Double.valueOf("0");

	private Double impor2_ret = Double.valueOf("0");

	private Double impor3_ret = Double.valueOf("0");

	private Double impor4_ret = Double.valueOf("0");

	private Double impor5_ret = Double.valueOf("0");

	private Double impor6_ret = Double.valueOf("0");

	private Double impor7_ret = Double.valueOf("0");

	private Double impor8_ret = Double.valueOf("0");

	private Double impor9_ret = Double.valueOf("0");

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanProveedoRetencionesFrm() {
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
			Proveedores proveedoRetenciones = Common.getProveedores();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = proveedoRetenciones.proveedoRetencionesCreate(
						this.retencion, this.impor1_ret, this.impor2_ret,
						this.impor3_ret, this.impor4_ret, this.impor5_ret,
						this.impor6_ret, this.impor7_ret, this.impor8_ret,
						this.impor9_ret, this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = proveedoRetenciones.proveedoRetencionesUpdate(
						this.idretencion, this.retencion, this.impor1_ret,
						this.impor2_ret, this.impor3_ret, this.impor4_ret,
						this.impor5_ret, this.impor6_ret, this.impor7_ret,
						this.impor8_ret, this.impor9_ret, this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = proveedoRetenciones.proveedoRetencionesDelete(
						this.idretencion, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProveedoRetenciones() {
		try {
			Proveedores proveedoRetenciones = Common.getProveedores();
			List listProveedoRetenciones = proveedoRetenciones
					.getProveedoRetencionesPK(this.idretencion, this.idempresa);
			Iterator iterProveedoRetenciones = listProveedoRetenciones
					.iterator();
			if (iterProveedoRetenciones.hasNext()) {
				String[] uCampos = (String[]) iterProveedoRetenciones.next();
				// TODO: Constructores para cada tipo de datos
				this.idretencion = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.retencion = uCampos[1];
				this.impor1_ret = Double.valueOf(uCampos[2]);
				this.impor2_ret = Double.valueOf(uCampos[3]);
				this.impor3_ret = Double.valueOf(uCampos[4]);
				this.impor4_ret = Double.valueOf(uCampos[5]);
				this.impor5_ret = Double.valueOf(uCampos[6]);
				this.impor6_ret = Double.valueOf(uCampos[7]);
				this.impor7_ret = Double.valueOf(uCampos[8]);
				this.impor8_ret = Double.valueOf(uCampos[9]);
				this.impor9_ret = Double.valueOf(uCampos[10]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProveedoRetenciones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("proveedoRetencionesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (retencion == null) {
						this.mensaje = "No se puede dejar vacio el campo retencion ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (retencion.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo retencion ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosProveedoRetenciones();
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
	public BigDecimal getIdretencion() {
		return idretencion;
	}

	public void setIdretencion(BigDecimal idretencion) {
		this.idretencion = idretencion;
	}

	public String getRetencion() {
		return retencion;
	}

	public void setRetencion(String retencion) {
		this.retencion = retencion;
	}

	public Double getImpor1_ret() {
		return impor1_ret;
	}

	public void setImpor1_ret(Double impor1_ret) {
		this.impor1_ret = impor1_ret;
	}

	public Double getImpor2_ret() {
		return impor2_ret;
	}

	public void setImpor2_ret(Double impor2_ret) {
		this.impor2_ret = impor2_ret;
	}

	public Double getImpor3_ret() {
		return impor3_ret;
	}

	public void setImpor3_ret(Double impor3_ret) {
		this.impor3_ret = impor3_ret;
	}

	public Double getImpor4_ret() {
		return impor4_ret;
	}

	public void setImpor4_ret(Double impor4_ret) {
		this.impor4_ret = impor4_ret;
	}

	public Double getImpor5_ret() {
		return impor5_ret;
	}

	public void setImpor5_ret(Double impor5_ret) {
		this.impor5_ret = impor5_ret;
	}

	public Double getImpor6_ret() {
		return impor6_ret;
	}

	public void setImpor6_ret(Double impor6_ret) {
		this.impor6_ret = impor6_ret;
	}

	public Double getImpor7_ret() {
		return impor7_ret;
	}

	public void setImpor7_ret(Double impor7_ret) {
		this.impor7_ret = impor7_ret;
	}

	public Double getImpor8_ret() {
		return impor8_ret;
	}

	public void setImpor8_ret(Double impor8_ret) {
		this.impor8_ret = impor8_ret;
	}

	public Double getImpor9_ret() {
		return impor9_ret;
	}

	public void setImpor9_ret(Double impor9_ret) {
		this.impor9_ret = impor9_ret;
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
