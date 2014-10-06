/* 
   javabean para la entidad (Formulario): rrhhestadosempleado
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 19 12:24:44 ART 2012 
   
   Para manejar la pagina: rrhhestadosempleadoFrm.jsp
      
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

public class BeanRrhhestadosempleadoFrm implements SessionBean, Serializable {
	private SessionContext context;
	
	static Logger log = Logger.getLogger(BeanRrhhestadosempleadoFrm.class);
	
	private String validar = "";
	
	private BigDecimal idestadoempleado = new BigDecimal (-1);
	
	private BigDecimal idempresa = new BigDecimal (-1);
	
	private BigDecimal legajo = new BigDecimal (-1);
	
	private String apellido  ="";
	
	private boolean esempleado;
	
	private BigDecimal idmotivo = new BigDecimal(-1);
	
	private BigDecimal idrazon = new BigDecimal(-1);
	
	private String observacion = "";
	
	private String fecha ="";
	
	private String fechaConversion  ="";
	
	private String usuarioalt;
	
	private String usuarioact;
	
	private String mensaje = "";
	
	private String volver = "";
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String accion = "";
	
	private String accionpersonal = "";

	private List listMotivos = new ArrayList();
	
	private List listRazones = new ArrayList();
	
	public BeanRrhhestadosempleadoFrm() {
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
				this.mensaje = rrhh.rrhhestadosempleadoCreate(this.legajo,
						this.apellido, this.esempleado, this.idmotivo,
						this.idrazon, this.observacion, (java.sql.Timestamp) Common.setObjectToStrOrTime(this.fechaConversion, "StrToJSTS"),
						this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = rrhh.rrhhestadosempleadoUpdate(
						this.idestadoempleado, this.legajo, this.apellido,
						this.esempleado, this.idmotivo, this.idrazon,
						this.observacion, (java.sql.Timestamp) Common.setObjectToStrOrTime(this.fechaConversion, "StrToJSTS"), this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhh.rrhhestadosempleadoDelete(
						this.idestadoempleado, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhestadosempleado() {
		try {
			RRHH rrhh = Common.getRrhh();
			List listrrhhpersonal = rrhh.getRrhhpersonalPK(legajo, idempresa);
			Iterator iterRrhhpersonal = listrrhhpersonal
					.iterator();
			if (iterRrhhpersonal.hasNext()) {
				String[] uCampos = (String[]) iterRrhhpersonal.next();
				// TODO: Constructores para cada tipo de datos
				this.apellido = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhestadosempleado()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			
			if (accionpersonal.equalsIgnoreCase("baja"))
			{
				esempleado = false;
			}else{
				esempleado = true;
			}
			this.listMotivos = Common.getRrhh().getRrhhmotivoAll(esempleado, this.idempresa);
			if (this.idmotivo.longValue()>0)
			{
				
				this.listRazones = Common.getRrhh().getRrhhrazonesAll(this.idmotivo, this.idempresa);
			}
			
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhpersonalAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (legajo == null) {
						this.mensaje = "No se puede dejar vacio el campo legajo ";
						return false;
					}
					if (legajo.longValue()<0)
					{
						this.mensaje = "No se puede dejar vacio el campo legajo ";
						return false;	
					}
					if (idmotivo == null) {
						this.mensaje = "No se puede dejar vacio el campo Motivo ";
						return false;
					}
					if (idmotivo.longValue()<0)
					{
						this.mensaje = "No se puede dejar vacio el campo Motivo ";
						return false;
					}
					if (idrazon == null) {
						this.mensaje = "No se puede dejar vacio el campo Razon ";
						return false;
					}
					if (idrazon.longValue()<0)
					{
						this.mensaje = "No se puede dejar vacio el campo Razon ";
						return false;
					}
					/**if (fecha == null)
					{
						this.mensaje = "No se puede dejar vacio el campo Fecha";
						return false;
					}
					if (fecha.equalsIgnoreCase(""))
					{
						this.mensaje = "No se puede dejar vacio el campo Fecha";
						return false;
					}
					
					
					log.info("FECHA conversion: " + (java.sql.Timestamp) Common.setObjectToStrOrTime(this.fechaConversion, "strToJSTs"));*/
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				//if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhestadosempleado();
				//}
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
	public BigDecimal getIdestadoempleado() {
		return idestadoempleado;
	}

	public void setIdestadoempleado(BigDecimal idestadoempleado) {
		this.idestadoempleado = idestadoempleado;
	}

	public BigDecimal getLegajo() {
		return legajo;
	}

	public void setLegajo(BigDecimal legajo) {
		this.legajo = legajo;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public BigDecimal getIdmotivo() {
		return idmotivo;
	}

	public void setIdmotivo(BigDecimal idmotivo) {
		this.idmotivo = idmotivo;
	}

	public BigDecimal getIdrazon() {
		return idrazon;
	}

	public void setIdrazon(BigDecimal idrazon) {
		this.idrazon = idrazon;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

	public boolean isEsempleado() {
		return esempleado;
	}

	public void setEsempleado(boolean esempleado) {
		this.esempleado = esempleado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public List getListMotivos() {
		return listMotivos;
	}

	public void setListMotivos(List listMotivos) {
		this.listMotivos = listMotivos;
	}

	public List getListRazones() {
		return listRazones;
	}

	public void setListRazones(List listRazones) {
		this.listRazones = listRazones;
	}

	public String getAccionpersonal() {
		return accionpersonal;
	}

	public void setAccionpersonal(String accionpersonal) {
		this.accionpersonal = accionpersonal;
	}

	public String getFechaConversion() {
		return fechaConversion;
	}

	public void setFechaConversion(String fechaConversion) {
		this.fechaConversion = fechaConversion;
	}
	
	
}
