/* 
   javabean para la entidad (Formulario): contableplanajus
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 22 09:55:13 ART 2008 
   
   Para manejar la pagina: contableplanajusFrm.jsp
      
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

public class BeanContableplanajusFrm implements SessionBean, Serializable {
	private SessionContext context;
	static Logger log = Logger.getLogger(BeanContableplanajusFrm.class);
	private String validar = "";
	private String cuenta_pl = "";
	private String d_cuenta_pl = "";
	private String indice_pl = "";
	private String d_indice_pl = "";
	private BigDecimal idempresa = BigDecimal.valueOf(0);
	private BigDecimal idejercicio = BigDecimal.valueOf(0);
	private String ano = "";
	private String d_ano = "";
	private String usuarioalt;
	private String usuarioact;
	private String mensaje = "";
	private String volver = "";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String accion = "";

	public BeanContableplanajusFrm() {
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
			Contable contableplanajus = Common.getContable();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = contableplanajus.contableplanajusCreate(
						this.cuenta_pl, this.indice_pl, this.usuarioalt,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = contableplanajus.contableplanajusUpdate(
						this.cuenta_pl, this.indice_pl, this.usuarioact,
						this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = contableplanajus.contableplanajusDelete(
						this.cuenta_pl, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosContableplanajus() {
		try {
			Contable contableplanajus = Common.getContable();
			// List listContableplanajus =
			// contableplanajus.getContableplanajusPK(this.idejercicio,this.cuenta_pl,
			// this.idempresa );
			List listContableplanajus = new ArrayList();
			Iterator iterContableplanajus = listContableplanajus.iterator();
			if (iterContableplanajus.hasNext()) {
				String[] uCampos = (String[]) iterContableplanajus.next();
				// TODO: Constructores para cada tipo de datos
				this.cuenta_pl = uCampos[0];
				this.d_cuenta_pl = uCampos[1];
				this.indice_pl = uCampos[2];
				this.d_indice_pl = uCampos[3];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosContableplanajus()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("contableplanajusAbm.jsp");
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
					getDatosContableplanajus();
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

	public String getD_cuenta_pl() {
		return d_cuenta_pl;
	}

	public void setD_cuenta_pl(String d_cuenta_pl) {
		this.d_cuenta_pl = d_cuenta_pl;
	}

	public String getD_indice_pl() {
		return d_indice_pl;
	}

	public void setD_indice_pl(String d_indice_pl) {
		this.d_indice_pl = d_indice_pl;
	}

	public void setCuenta_pl(String cuenta_pl) {
		this.cuenta_pl = cuenta_pl;
	}

	public void setIndice_pl(String indice_pl) {
		this.indice_pl = indice_pl;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getCuenta_pl() {
		return cuenta_pl;
	}

	public String getIndice_pl() {
		return indice_pl;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getD_ano() {
		return d_ano;
	}

	public void setD_ano(String d_ano) {
		this.d_ano = d_ano;
	}

	public BigDecimal getIdejercicio() {
		return idejercicio;
	}

	public void setIdejercicio(BigDecimal idejercicio) {
		this.idejercicio = idejercicio;
	}
}
