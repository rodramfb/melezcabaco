/* 
   javabean para la entidad (Formulario): clientesRelaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Feb 15 15:02:21 GMT-03:00 2010 
   
   Para manejar la pagina: clientesRelacionesFrm.jsp
      
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

public class BeanClientesRelacionesFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesRelacionesFrm.class);

	private String validar = "";

	private BigDecimal idrelacion = new BigDecimal(-1);

	private BigDecimal idclienteroot = new BigDecimal(-1);

	private String razonroot = "";

	private String idclientebranch = "";

	private String razonbranch = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientesRelacionesFrm() {
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
			
			
			
			Clientes clientesRelaciones = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientesRelaciones.clientesRelacionesCreate(
						this.idclienteroot, new BigDecimal(this.idclientebranch),
						this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientesRelaciones.clientesRelacionesUpdate(
						this.idrelacion, this.idclienteroot,
						new BigDecimal(this.idclientebranch), this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientesRelaciones.clientesRelacionesDelete(
						this.idrelacion, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesRelaciones() {
		try {
			Clientes clientesRelaciones = Common.getClientes();
			List listClientesRelaciones = clientesRelaciones
					.getClientesRelacionesPK(this.idrelacion, this.idempresa);
			Iterator iterClientesRelaciones = listClientesRelaciones.iterator();
			if (iterClientesRelaciones.hasNext()) {
				String[] uCampos = (String[]) iterClientesRelaciones.next();
				// TODO: Constructores para cada tipo de datos
				this.idrelacion = new BigDecimal(uCampos[0]);
				this.idclienteroot = new BigDecimal(uCampos[1]);
				this.idclientebranch = uCampos[2];

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesRelaciones()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesRelacionesAbm.jsp?idcliente="
						+ this.idclienteroot + "&razon=" + this.razonroot);
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idclienteroot == null) {
						this.mensaje = "No se puede dejar vacio el campo idclienteroot ";
						return false;
					}
					
					if(!Common.esEntero(this.idclientebranch)){
						this.mensaje = "Es necesario ingresar Cliente Asociado. ";
						return false;
					}
					
					if(this.idclienteroot.toString().equals(this.idclientebranch)){
						this.mensaje = "Cliente principal y Cliente asociado deben ser distintos. ";
						return false;						
					}
					// 2. len 0 para campos nulos
				}
				
				this.ejecutarSentenciaDML();
				
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesRelaciones();
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
	public BigDecimal getIdrelacion() {
		return idrelacion;
	}

	public void setIdrelacion(BigDecimal idrelacion) {
		this.idrelacion = idrelacion;
	}

	public BigDecimal getIdclienteroot() {
		return idclienteroot;
	}

	public void setIdclienteroot(BigDecimal idclienteroot) {
		this.idclienteroot = idclienteroot;
	}

	public String getRazonroot() {
		return razonroot;
	}

	public void setRazonroot(String razonroot) {
		this.razonroot = razonroot;
	}

	public String getIdclientebranch() {
		return idclientebranch;
	}

	public void setIdclientebranch(String idclientebranch) {
		this.idclientebranch = idclientebranch;
	}

	public String getRazonbranch() {
		return razonbranch;
	}

	public void setRazonbranch(String razonbranch) {
		this.razonbranch = razonbranch;
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
