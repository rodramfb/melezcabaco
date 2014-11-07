/* 
   javabean para la entidad (Formulario): ticketsestados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Sep 20 09:41:44 ART 2012 
   
   Para manejar la pagina: ticketsestadosFrm.jsp
      
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

public class BeanTicketsestadosFrm implements SessionBean, Serializable {
	private SessionContext context;
	static Logger log = Logger.getLogger(BeanTicketsestadosFrm.class);
	
	private String validar = "";
	
	private BigDecimal idticketestado = new BigDecimal(-1);
	
	private String ticketestado ="";
	
	private String color_fondo ="";
	
	private BigDecimal idempresa = new BigDecimal(-1);
	
	private String usuarioalt ="";
	
	private String usuarioact="";
	
	private String mensaje = "";
	
	private String volver = "";
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String accion = "";

	public BeanTicketsestadosFrm() {
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
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = general.ticketsestadosCreate(this.ticketestado,
						this.color_fondo, this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = general.ticketsestadosUpdate(
						this.idticketestado, this.ticketestado,
						this.color_fondo, this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = general.ticketsestadosDelete(
						this.idticketestado, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosTicketsestados() {
		try {
			General general = Common.getGeneral();
			List listTicketsestados = general.getTicketsestadosPK(
					this.idticketestado, this.idempresa);
			Iterator iterTicketsestados = listTicketsestados.iterator();
			if (iterTicketsestados.hasNext()) {
				String[] uCampos = (String[]) iterTicketsestados.next();
				// TODO: Constructores para cada tipo de datos
				this.idticketestado = new BigDecimal(uCampos[0]);
				this.ticketestado = uCampos[1];
				this.color_fondo = uCampos[2];
				this.idempresa = new BigDecimal(uCampos[3]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosTicketsestados()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("ticketsestadosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (ticketestado == null) {
						this.mensaje = "No se puede dejar vacio el campo ticketestado ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (ticketestado.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo ticketestado  ";
						return false;
					}
					if (ticketestado.equalsIgnoreCase(""))
					{
						this.mensaje ="No puede dejar vacio el campo estado";
						return false;
					}
					if (color_fondo.equalsIgnoreCase(""))
					{
						this.mensaje ="No puede dejar vacio el campo color";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosTicketsestados();
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
	public BigDecimal getIdticketestado() {
		return idticketestado;
	}

	public void setIdticketestado(BigDecimal idticketestado) {
		this.idticketestado = idticketestado;
	}

	public String getTicketestado() {
		return ticketestado;
	}

	public void setTicketestado(String ticketestado) {
		this.ticketestado = ticketestado;
	}

	public String getColor_fondo() {
		return color_fondo;
	}

	public void setColor_fondo(String color_fondo) {
		this.color_fondo = color_fondo;
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
