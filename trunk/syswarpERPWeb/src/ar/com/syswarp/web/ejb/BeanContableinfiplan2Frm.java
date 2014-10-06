/* 
   javabean para la entidad (Formulario): contableinfiplan
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 08 10:21:10 ART 2008 
   
   Para manejar la pagina: contableinfiplanFrm.jsp
      
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

public class BeanContableinfiplan2Frm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanContableinfiplan2Frm.class);

	private String validar = "";

	private BigDecimal ejercicio = BigDecimal.valueOf(0);

	private String idcuenta = "";

	private String cuenta = "";

	private String inputable = "";

	private BigDecimal nivel = BigDecimal.valueOf(0);

	private String ajustable = "";

	private String resultado = "";

	private BigDecimal cent_cost1 = BigDecimal.valueOf(0);

	private String d_cent_cost1 = "";

	private BigDecimal cent_cost2 = BigDecimal.valueOf(0);

	private String d_cent_cost2 = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanContableinfiplan2Frm() {
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
			Contable contableinfiplan = Common.getContable();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = contableinfiplan.contableinfiplanCreate(
						this.ejercicio, new BigDecimal(this.idcuenta),
						this.cuenta, this.inputable, this.nivel,
						this.ajustable, this.resultado, this.cent_cost1,
						this.cent_cost2, this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = contableinfiplan.contableinfiplanUpdate(
						this.ejercicio, new BigDecimal(this.idcuenta),
						this.cuenta, this.inputable, this.nivel,
						this.ajustable, this.resultado, this.cent_cost1,
						this.cent_cost2, this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = contableinfiplan.contableinfiplanDelete(
						this.ejercicio, new BigDecimal(this.idcuenta),
						this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosContableinfiplan() {
		try {
			Contable contableinfiplan = Common.getContable();
			List listContableinfiplan = contableinfiplan.getContableinfiplanpk(
					this.ejercicio, new BigDecimal(this.idcuenta),
					this.idempresa);
			Iterator iterContableinfiplan = listContableinfiplan.iterator();
			if (iterContableinfiplan.hasNext()) {
				String[] uCampos = (String[]) iterContableinfiplan.next();
				// TODO: Constructores para cada tipo de datos
				this.ejercicio = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.idcuenta = uCampos[1];
				this.cuenta = uCampos[2];
				this.inputable = uCampos[3];
				this.nivel = BigDecimal.valueOf(Long.parseLong(uCampos[4]));
				this.ajustable = uCampos[5];
				this.resultado = uCampos[6];
				this.cent_cost1 = BigDecimal
						.valueOf(Long.parseLong(uCampos[7]));
				this.d_cent_cost1 = uCampos[8];
				this.cent_cost2 = BigDecimal
						.valueOf(Long.parseLong(uCampos[9]));
				this.d_cent_cost2 = uCampos[10];
				this.idempresa = BigDecimal
						.valueOf(Long.parseLong(uCampos[11]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosContableinfiplan()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("contableinfiplan2Abm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (!Common.esEntero(idcuenta)) {
						this.mensaje = "Ingrese un número de cuenta valido. ";
						return false;
					}

					if (Common.setNotNull(cuenta).trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo Descripción ";
						return false;
					}

					if (inputable == null) {
						this.mensaje = "No se puede dejar vacio el campo inputable ";
						return false;
					}

					if (nivel == null || nivel.intValue() < 1) {
						this.mensaje = "Seleccione nivel. ";
						return false;
					}

					if (ajustable == null) {
						this.mensaje = "No se puede dejar vacio el campo ajustable ";
						return false;
					}
					if (resultado == null) {
						this.mensaje = "No se puede dejar vacio el campo resultado ";
						return false;
					}

					if (inputable.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo inputable  ";
						return false;
					}
					if (ajustable.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo ajustable  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosContableinfiplan();
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
	public BigDecimal getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(BigDecimal ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(String idcuenta) {
		this.idcuenta = idcuenta;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getInputable() {
		return inputable;
	}

	public void setInputable(String inputable) {
		this.inputable = inputable;
	}

	public BigDecimal getNivel() {
		return nivel;
	}

	public void setNivel(BigDecimal nivel) {
		this.nivel = nivel;
	}

	public String getAjustable() {
		return ajustable;
	}

	public void setAjustable(String ajustable) {
		this.ajustable = ajustable;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public BigDecimal getCent_cost1() {
		return cent_cost1;
	}

	public void setCent_cost1(BigDecimal cent_cost1) {
		this.cent_cost1 = cent_cost1;
	}

	public BigDecimal getCent_cost2() {
		return cent_cost2;
	}

	public void setCent_cost2(BigDecimal cent_cost2) {
		this.cent_cost2 = cent_cost2;
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

	public String getD_cent_cost1() {
		return d_cent_cost1;
	}

	public void setD_cent_cost1(String d_cent_cost1) {
		this.d_cent_cost1 = d_cent_cost1;
	}

	public String getD_cent_cost2() {
		return d_cent_cost2;
	}

	public void setD_cent_cost2(String d_cent_cost2) {
		this.d_cent_cost2 = d_cent_cost2;
	}
}
