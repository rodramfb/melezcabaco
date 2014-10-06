/* 
 javabean para la entidad (Formulario):  
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Nov 14 14:50:18 GMT-03:00 2006 
 
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
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanBacoErGenerar implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoErGenerar.class);

	private String validar = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private int anio = Calendar.getInstance().get(Calendar.YEAR);

	private int anioactual = Calendar.getInstance().get(Calendar.YEAR);

	private int mes = -1;

	private int mesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List mesesList = new ArrayList();

	public BeanBacoErGenerar() {
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
			Clientes clie = Common.getClientes();

			this.mensaje = clie.setEntregasRegulares(this.anio, this.mes,
					this.idempresa, this.usuarioact);

			// Eventual, para realizar pruebas.
			if (this.mensaje.equalsIgnoreCase("OK"))
				this.mensaje = "Generación de Entregas Regulares Exitosa.";

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {


			this.mesesList = Common.getGeneral().getGlobalMeses();

			if (!this.validar.equalsIgnoreCase("")) {

				if (this.mes < 1) {
					log.info("mes erroneo.");
					this.mensaje = "Por favor seleccione mes válido.";
					return false;
				}


				if (this.anio < 2000) {
					log.info("anio erroneo.");
					this.mensaje = "Por favor seleccione un año válido.";
					return false;
				}


				
				// 20101229 - EJV Mantis 655 -->

				// if (this.mes < this.mesActual || this.anio < this.anioactual)
				// {
				// this.mensaje =
				// "(P2)Período debe ser igual o mayor al período actual: "
				// + (this.mesActual < 10 ? "0" + this.mesActual
				// : this.mesActual + "")
				// + "/"
				// + this.anioactual;
				// return false;
				// }				
				
				if (this.anio < this.anioactual) {
					this.mensaje = "(P1)Período debe ser igual o mayor al período actual: "
							+ (this.mesActual < 10 ? "0" + this.mesActual
									: this.mesActual + "")
							+ "/"
							+ this.anioactual;
					return false;
				}

				if (this.mes < this.mesActual && this.anio <= this.anioactual) {
					this.mensaje = "(P2)Período debe ser igual o mayor al período actual: "
							+ (this.mesActual < 10 ? "0" + this.mesActual
									: this.mesActual + "")
							+ "/"
							+ this.anioactual;
					return false;
				}

				//<--
				
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

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public List getMesesList() {
		return mesesList;
	}

	public void setMesesList(List mesesList) {
		this.mesesList = mesesList;
	}
}
