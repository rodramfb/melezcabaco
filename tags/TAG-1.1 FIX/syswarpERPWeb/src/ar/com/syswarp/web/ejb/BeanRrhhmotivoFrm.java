/* 
   javabean para la entidad (Formulario): rrhhmotivobaja
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Jul 16 10:03:59 ART 2012 
   
   Para manejar la pagina: rrhhmotivobajaFrm.jsp
      
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

public class BeanRrhhmotivoFrm implements SessionBean, Serializable {
	private SessionContext context;
	
	static Logger log = Logger.getLogger(BeanRrhhmotivoFrm.class);
	
	private String validar = "";
	
	private BigDecimal idmotivo = new BigDecimal(-1);
	
	private String motivo ="";
	
	private String usuarioalt ="";
	
	private String usuarioact ="";
	
	private String mensaje = "";
	
	private String volver = "";
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String accion = "";
	
	private boolean esempleado;
	
	private String empleado ="";
	private BigDecimal idempresa = new BigDecimal(-1);

	public BeanRrhhmotivoFrm() {
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
			RRHH rrhh = Common.getRrhh();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = rrhh.rrhhmotivoCreate(this.motivo,this.esempleado,
						this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = rrhh.rrhhmotivoUpdate(this.idmotivo,
						this.motivo,this.esempleado, this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhh.rrhhmotivoDelete(this.idmotivo,
						this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhmotivobaja() {
		try {
			RRHH rrhh = Common.getRrhh();
			List listRrhhmotivobaja = rrhh.getRrhhmotivoPK(
					this.idmotivo, this.idempresa);
			Iterator iterRrhhmotivobaja = listRrhhmotivobaja.iterator();
			if (iterRrhhmotivobaja.hasNext()) {
				String[] uCampos = (String[]) iterRrhhmotivobaja.next();
				// TODO: Constructores para cada tipo de datos
				this.idmotivo = new BigDecimal(uCampos[0]);
				this.motivo= uCampos[1];
				this.empleado = uCampos[2];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhmotivobaja()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			log.info("empleado: " + empleado);
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhmotivoAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					if (this.motivo.equalsIgnoreCase(""))
					{
						this.mensaje = "No puede dejar vacio el campo motivo.";
						return false;
					}
					
					if (this.empleado.equalsIgnoreCase("true"))
					{
						esempleado =true;
					}else{
						esempleado =false;
					}
					log.info("esempleado: " + esempleado);
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhmotivobaja();
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

	public BigDecimal getIdmotivo() {
		return idmotivo;
	}

	public void setIdmotivo(BigDecimal idmotivo) {
		this.idmotivo = idmotivo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
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

	public boolean getEsempleado() {
		return esempleado;
	}

	public void setEsempleado(boolean esempleado) {
		this.esempleado = esempleado;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	
}
