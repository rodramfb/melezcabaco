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

public class BeanCajaDepositosEfectivoFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanCajaDepositosEfectivoFrm.class);

	private String validar = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private String identificadorDeposito = "";

	private String descripcionDeposito = "";

	private String cc1Deposito = "";

	private String descCc1Deposito = "";

	private String cc2Deposito = "";

	private String descCc2Deposito = "";

	private String identificadorBanco = "";

	private String descripcionBanco = "";

	private String cc1Banco = "";

	private String descCc1Banco = "";

	private String cc2Banco = "";

	private String descCc2Banco = "";

	private String importeDeposito = "";

	private boolean modCCDeposito = false;

	private boolean modCCBanco = false;

	private java.sql.Timestamp fecha_teso = null;

	private String tesoFechaCaja = "";

	private String nrodeposito = "";

	public BeanCajaDepositosEfectivoFrm() {
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

			String salida = "";

			Tesoreria caja = Common.getTesoreria();

			salida = caja.generarDepositoEfectivo(identificadorDeposito,
					identificadorBanco, new BigDecimal(importeDeposito),
					this.fecha_teso, cc1Deposito, cc2Deposito, cc1Banco,
					cc2Banco, idempresa, usuarioalt);

			if (Common.esNumerico(salida)) {

				this.mensaje = "Deposito Nro.: " + salida;
				this.nrodeposito = salida;
				response
						.sendRedirect("cajaDepositosCallImpresion.jsp?depositos="
								+ nrodeposito + "&tipoDeposito=efectivo");

			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					if (this.identificadorDeposito.equals("")) {
						this.mensaje = "Ingrese Identificador a Depositar.";
						return false;
					}

					if (this.identificadorDeposito.equals("")) {
						this.mensaje = "Ingrese Banco a Depositar.";
						return false;
					}

					if (!Common.esNumerico(this.importeDeposito)) {
						this.mensaje = "Ingrese Importe a Depositar valido.";
						return false;
					}

					this.fecha_teso = (java.sql.Timestamp) Common
							.setObjectToStrOrTime(Common.getGeneral()
									.getValorSetupVariablesNoStatic(
											"tesoFechaCaja", idempresa),
									"StrToJSTs");

					if (this.fecha_teso.compareTo((java.sql.Timestamp) Common
							.setObjectToStrOrTime(this.tesoFechaCaja,
									"StrToJSTS")) != 0) {

						this.mensaje = "La fecha de caja se modifico desde otra sesion, por favor ingrese nuevamente al menu de depositos.";
						return false;
					}

				}

				this.ejecutarSentenciaDML();

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

	public String getCc1Banco() {
		return cc1Banco;
	}

	public void setCc1Banco(String cc1Banco) {
		this.cc1Banco = cc1Banco;
	}

	public String getCc1Deposito() {
		return cc1Deposito;
	}

	public void setCc1Deposito(String cc1Deposito) {
		this.cc1Deposito = cc1Deposito;
	}

	public String getCc2Banco() {
		return cc2Banco;
	}

	public void setCc2Banco(String cc2Banco) {
		this.cc2Banco = cc2Banco;
	}

	public String getCc2Deposito() {
		return cc2Deposito;
	}

	public void setCc2Deposito(String cc2Deposito) {
		this.cc2Deposito = cc2Deposito;
	}

	public String getDescripcionBanco() {
		return descripcionBanco;
	}

	public void setDescripcionBanco(String descripcionBanco) {
		this.descripcionBanco = descripcionBanco;
	}

	public String getDescripcionDeposito() {
		return descripcionDeposito;
	}

	public void setDescripcionDeposito(String descripcionDeposito) {
		this.descripcionDeposito = descripcionDeposito;
	}

	public String getIdentificadorBanco() {
		return identificadorBanco;
	}

	public void setIdentificadorBanco(String identificadorBanco) {
		this.identificadorBanco = identificadorBanco;
	}

	public String getIdentificadorDeposito() {
		return identificadorDeposito;
	}

	public void setIdentificadorDeposito(String identificadorDeposito) {
		this.identificadorDeposito = identificadorDeposito;
	}

	public String getImporteDeposito() {
		return importeDeposito;
	}

	public void setImporteDeposito(String importeDeposito) {
		this.importeDeposito = importeDeposito;
	}

	public String getDescCc1Deposito() {
		return descCc1Deposito;
	}

	public void setDescCc1Deposito(String descCc1Deposito) {
		this.descCc1Deposito = descCc1Deposito;
	}

	public String getDescCc2Deposito() {
		return descCc2Deposito;
	}

	public void setDescCc2Deposito(String descCc2Deposito) {
		this.descCc2Deposito = descCc2Deposito;
	}

	public String getDescCc1Banco() {
		return descCc1Banco;
	}

	public void setDescCc1Banco(String descCc1Banco) {
		this.descCc1Banco = descCc1Banco;
	}

	public String getDescCc2Banco() {
		return descCc2Banco;
	}

	public void setDescCc2Banco(String descCc2Banco) {
		this.descCc2Banco = descCc2Banco;
	}

	public boolean isModCCDeposito() {
		return modCCDeposito;
	}

	public void setModCCDeposito(boolean modCCDeposito) {
		this.modCCDeposito = modCCDeposito;
	}

	public boolean isModCCBanco() {
		return modCCBanco;
	}

	public void setModCCBanco(boolean modCCBanco) {
		this.modCCBanco = modCCBanco;
	}

	public String getTesoFechaCaja() {
		return tesoFechaCaja;
	}

	public void setTesoFechaCaja(String tesoFechaCaja) {
		this.tesoFechaCaja = tesoFechaCaja;
	}

}
