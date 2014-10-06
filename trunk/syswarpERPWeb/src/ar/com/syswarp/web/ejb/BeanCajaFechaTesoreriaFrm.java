/* 
 javabean para la entidad (Formulario): setupVariables 
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:36:49 GMT-03:00 2006 
 
 Para manejar la pagina: cajaFechaTesoreriaFrm.jsp
 
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

public class BeanCajaFechaTesoreriaFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanCajaFechaTesoreriaFrm.class);

	private String validar = "";

	private String variable = "tesoFechaCaja";

	private String sistema = "T";

	private String valor = "";

	private String fechaTesoValorActual = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "modificacion";

	public BeanCajaFechaTesoreriaFrm() {
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
			General general = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = general.setupVariablesSetValor(this.variable,
						this.valor, this.sistema, this.usuarioact,
						this.idempresa);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getTesoFechaCaja() {
		try {
			General general = Common.getGeneral();
			List listFechaTeso = general.getSetupvariablesPK(this.variable,
					this.idempresa);
			Iterator iterFechaTeso = listFechaTeso.iterator();
			if (iterFechaTeso.hasNext()) {
				String[] uCampos = (String[]) iterFechaTeso.next();
				// TODO: Constructores para cada tipo de datos

				this.fechaTesoValorActual = uCampos[1];

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getTesoFechaCaja()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			Date ftActual = (Date) Common.setObjectToStrOrTime(
					this.fechaTesoValorActual, "StrToJUDate");
			Date ftNueva = (Date) Common.setObjectToStrOrTime(this.valor,
					"StrToJUDate");

			if (!this.validar.equalsIgnoreCase("")) {
				
				if(ftNueva == null){
					this.mensaje = "Ingrese valor para fecha tesoreria.";
					return false;
				}
				
				if(ftNueva.before(ftActual)){
					this.mensaje = "Nueva fecha no puede ser anterior a la fecha actual de tesoreria.";
					return false;					
				}

				this.ejecutarSentenciaDML();
				
				
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getTesoFechaCaja();
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

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getFechaTesoValorActual() {
		return fechaTesoValorActual;
	}

	public void setFechaTesoValorActual(String fechaTesoValorActual) {
		this.fechaTesoValorActual = fechaTesoValorActual;
	}
}
