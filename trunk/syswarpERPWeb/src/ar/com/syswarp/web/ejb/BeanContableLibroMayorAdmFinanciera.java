/* 
 javabean para la entidad (Formulario): Cajaferiados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
 
 Para manejar la pagina: 
 
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

/*
 * nuevos datos . cuenta contable . Anio o Ejercicio
 * 
 */

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanContableLibroMayorAdmFinanciera implements SessionBean, Serializable {

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanContableLibroMayorAdmFinanciera.class);

	private String validar = "";

	private BigDecimal idejercicio;

	private String desc_anio = "";

	private String idcuenta_desde = "";

	private String cuenta_desde = "";

	private String idcuenta_hasta = "";

	private String cuenta_hasta = "";

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private String fecha_desde = "";

	private String fecha_hasta = "";

	public BeanContableLibroMayorAdmFinanciera() {
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
			Contable Consulta = Common.getContable();

			java.sql.Date fdesde = (java.sql.Date) Common.setObjectToStrOrTime(
					this.fecha_desde, "StrToJSDate");
			java.sql.Date fhasta = (java.sql.Date) Common.setObjectToStrOrTime(
					this.fecha_hasta, "StrToJSDate");

			if (this.accion.equalsIgnoreCase("consulta"))
				// aca va la sentencia que me devuelva el listado

				this.mensaje = "";

			if (this.cuenta_desde == null
					|| this.cuenta_desde.equalsIgnoreCase(""))
				this.mensaje = "Ingrese Cuenta Desde";
			else if (this.cuenta_hasta == null
					|| this.cuenta_hasta.equalsIgnoreCase(""))
				this.mensaje = "Ingrese Cuenta Hasta";
			else if (Long.parseLong(this.idcuenta_hasta) < Long
					.parseLong(this.idcuenta_desde))
				this.mensaje = "Cuenta Hasta debe ser mayor o igual a cuenta desde.";
			else if (fdesde == null)
				this.mensaje = "Ingrese Fecha Desde.";
			else if (fhasta == null)
				this.mensaje = "Ingrese Fecha Hasta.";
			else if (fdesde.after(fhasta))
				this.mensaje = "Fecha Desde debe ser menor o igual a Fecha Hasta.";
			else if (this.mensaje.equalsIgnoreCase("")) {
				// cambiar el nombre y ponerlo en el contable.
				this.MovimientosList = Consulta.getLibroMayorContFinanciera(
						new BigDecimal(this.idcuenta_desde), new BigDecimal(
								this.idcuenta_hasta), fdesde, fhasta,
						this.idejercicio, this.idempresa);
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("#"); // no tiene volver
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				/*
				 * if (!this.accion.equalsIgnoreCase("alta")) {
				 * getDatosCajaferiados(); }
				 */
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

	public List getMovimientosList() {
		return MovimientosList;
	}

	public void setMovimientosList(List movimientosList) {
		MovimientosList = movimientosList;
	}

	public String getIdcuenta_desde() {
		return idcuenta_desde;
	}

	public void setIdcuenta_desde(String idcuenta_desde) {
		this.idcuenta_desde = idcuenta_desde;
	}

	public String getCuenta_desde() {
		return cuenta_desde;
	}

	public void setCuenta_desde(String cuenta_desde) {
		this.cuenta_desde = cuenta_desde;
	}

	public String getIdcuenta_hasta() {
		return idcuenta_hasta;
	}

	public void setIdcuenta_hasta(String idcuenta_hasta) {
		this.idcuenta_hasta = idcuenta_hasta;
	}

	public String getCuenta_hasta() {
		return cuenta_hasta;
	}

	public void setCuenta_hasta(String cuenta_hasta) {
		this.cuenta_hasta = cuenta_hasta;
	}

	public String getDesc_anio() {
		return desc_anio;
	}

	public void setDesc_anio(String desc_anio) {
		this.desc_anio = desc_anio;
	}

	public BigDecimal getIdejercicio() {
		return idejercicio;
	}

	public void setIdejercicio(BigDecimal idejercicio) {
		this.idejercicio = idejercicio;
	}

	public String getFecha_desde() {
		return fecha_desde;
	}

	public void setFecha_desde(String fecha_desde) {
		this.fecha_desde = fecha_desde;
	}

	public String getFecha_hasta() {
		return fecha_hasta;
	}

	public void setFecha_hasta(String fecha_hasta) {
		this.fecha_hasta = fecha_hasta;
	}

}
