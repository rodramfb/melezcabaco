/* 
   javabean para la entidad (Formulario): rrhhrazonesmotivobaja
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Jul 16 11:20:20 ART 2012 
   
   Para manejar la pagina: rrhhrazonesmotivobajaFrm.jsp
      
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

public class BeanRrhhrazonesFrm implements SessionBean, Serializable {
	private SessionContext context;
	
	static Logger log = Logger.getLogger(BeanRrhhrazonesFrm.class);
	
	private String validar = "";
	
	private BigDecimal idrazon= new BigDecimal (-1);
	
	private String razon ="";
	
	private BigDecimal idmotivo = new BigDecimal (-1);
	
	private String usuarioalt ="";
	
	private String usuarioact ="";
	
	private String mensaje = "";
	
	private String volver = "";
	
	private BigDecimal idempresa = new BigDecimal(-1);
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String accion = "";
	
	public List listMotivos = new ArrayList();

	public BeanRrhhrazonesFrm() {
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
				this.mensaje = rrhh.rrhhrazonesCreate(this.razon,
						this.idmotivo, this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = rrhh.rrhhrazonesUpdate(
						this.idrazon, this.razon, this.idmotivo,
						this.usuarioact,this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhh.rrhhrazonesDelete(
						this.idrazon, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhrazonesmotivobaja() {
		try {
			RRHH rrhh = Common.getRrhh();
			List listRrhhrazones = rrhh.getRrhhrazonesPK(
					this.idrazon, this.idempresa);
			Iterator iterRrhhrazones = listRrhhrazones
					.iterator();
			if (iterRrhhrazones.hasNext()) {
				String[] uCampos = (String[]) iterRrhhrazones.next();
				// TODO: Constructores para cada tipo de datos
				this.idrazon= new BigDecimal(uCampos[0]);
				this.razon = uCampos[1];
				this.idmotivo = new BigDecimal(uCampos[2]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhrazonesmotivobaja()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			listMotivos= Common.getRrhh().getRrhhmotivoAll(250, 0, this.idempresa);
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhrazonesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					if(this.idmotivo.longValue()<0)
					{
						this.mensaje = "No seleccionó motivo de baja";
						return false;
					}
					if (this.razon == null)
					{
						this.mensaje = "No puede dejar vacio el campo razon";
						return false;
					}
					if (this.razon.equalsIgnoreCase(""))
					{
						this.mensaje = "No puede dejar vacio el campo razon";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhrazonesmotivobaja();
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

	public BigDecimal getIdrazon() {
		return idrazon;
	}

	public void setIdrazon(BigDecimal idrazon) {
		this.idrazon = idrazon;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public BigDecimal getIdmotivo() {
		return idmotivo;
	}

	public void setIdmotivo(BigDecimal idmotivo) {
		this.idmotivo = idmotivo;
	}

	public List getListMotivos() {
		return listMotivos;
	}

	public void setListMotivos(List listMotivos) {
		this.listMotivos = listMotivos;
	}


}
