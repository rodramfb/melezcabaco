/* 
   javabean para la entidad (Formulario): clientesMovCliMotivosNc
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Oct 04 11:49:25 ART 2012 
   
   Para manejar la pagina: clientesMovCliMotivosNcFrm.jsp
      
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

public class BeanClientesMovCliMotivosNcFrm implements SessionBean,
		Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesMovCliMotivosNcFrm.class);

	private String validar = "";

	private BigDecimal idmotivonc = new BigDecimal(-1);

	private String motivonc = "";

	private String afectastock = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientesMovCliMotivosNcFrm() {
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
			Clientes clientes = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientes.clientesMovCliMotivosNcCreate(
						this.motivonc, this.afectastock, this.idempresa,
						this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.clientesMovCliMotivosNcUpdate(
						this.idmotivonc, this.motivonc, this.afectastock,
						this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.clientesMovCliMotivosNcDelete(
						this.idmotivonc, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesMovCliMotivosNc() {
		try {
			Clientes clientes = Common.getClientes();
			List listClientesMovCliMotivosNc = clientes
					.getClientesMovCliMotivosNcPK(this.idmotivonc,
							this.idempresa);
			Iterator iterClientesMovCliMotivosNc = listClientesMovCliMotivosNc
					.iterator();
			if (iterClientesMovCliMotivosNc.hasNext()) {
				String[] uCampos = (String[]) iterClientesMovCliMotivosNc
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idmotivonc = new BigDecimal(uCampos[0]);
				this.motivonc = uCampos[1];
				this.afectastock = uCampos[2];
				this.idempresa = new BigDecimal(uCampos[3]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesMovCliMotivosNc()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesMovCliMotivosNcAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					
					if ( Common.setNotNull(this.motivonc).trim().equals("")) {
						this.mensaje = "Es necesario ingresar Motivo. ";
						return false;
					}

					// -- 
					if ( Common.setNotNull(this.afectastock).equals("")) {
						this.mensaje = "Es necesario seleccionar Afecta Stock. ";
						return false;
					}

					
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesMovCliMotivosNc();
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
	public BigDecimal getIdmotivonc() {
		return idmotivonc;
	}

	public void setIdmotivonc(BigDecimal idmotivonc) {
		this.idmotivonc = idmotivonc;
	}

	public String getMotivonc() {
		return motivonc;
	}

	public void setMotivonc(String motivonc) {
		this.motivonc = motivonc;
	}

	public String getAfectastock() {
		return afectastock;
	}

	public void setAfectastock(String afectastock) {
		this.afectastock = afectastock;
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
