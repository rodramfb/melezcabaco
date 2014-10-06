/* 
   javabean para la entidad (Formulario): tickets
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Sep 20 11:56:06 ART 2012 
   
   Para manejar la pagina: ticketsFrm.jsp
      
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

public class BeanTicketsFrm implements SessionBean, Serializable {
	private SessionContext context;
	static Logger log = Logger.getLogger(BeanTicketsFrm.class);

	private String validar = "";

	private BigDecimal idticket = new BigDecimal(-1);

	private BigDecimal idgrupo = new BigDecimal(-1);

	private String grupo = "";

	private List emailGrupo = new ArrayList();

	private BigDecimal idusuario = new BigDecimal(-1);

	private String usuario = "";

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private String resumen = "";

	private String descripcion = "";

	private BigDecimal idticketestado = new BigDecimal(-1);

	private List listEstadosTicket = new ArrayList();

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";
	
	//es pregunta.
	private boolean enviaCorreo = true;
	
	public BeanTicketsFrm() {
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
				this.mensaje = general.ticketsCreate(this.idgrupo,
						this.idusuario, this.idcliente, this.resumen,
						this.descripcion, this.idticketestado, this.idempresa,
						this.usuarioalt,enviaCorreo);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = general.ticketsUpdate(this.idticket,
						this.idgrupo, this.idusuario, this.idcliente,
						this.resumen, this.descripcion, this.idticketestado,
						this.idempresa, this.usuarioact,enviaCorreo);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = general.ticketsDelete(this.idticket,
						this.idempresa,enviaCorreo);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosTickets() {
		try {
			General general = Common.getGeneral();
			List listTickets = general.getTicketsPK(this.idticket,
					this.idempresa);
			Iterator iterTickets = listTickets.iterator();
			if (iterTickets.hasNext()) {
				String[] uCampos = (String[]) iterTickets.next();
				// TODO: Constructores para cada tipo de datos
				this.idticket = new BigDecimal(uCampos[0]);
				this.idgrupo = !Common.setNotNull(uCampos[1]).equalsIgnoreCase(
						"") ? new BigDecimal(uCampos[1]) : new BigDecimal(-1);
				this.grupo = !Common.setNotNull(uCampos[2])
						.equalsIgnoreCase("") ? uCampos[2] : "";
				this.idusuario = !Common.setNotNull(uCampos[3])
						.equalsIgnoreCase("") ? new BigDecimal(uCampos[3])
						: new BigDecimal(-1);
				this.usuario = !Common.setNotNull(uCampos[4]).equalsIgnoreCase(
						"") ? uCampos[4] : "";
				this.idcliente = !Common.setNotNull(uCampos[5])
						.equalsIgnoreCase("") ? new BigDecimal(uCampos[5])
						: new BigDecimal(-1);
				this.cliente = uCampos[6];
				this.resumen = uCampos[7];
				this.descripcion = uCampos[8];
				this.idticketestado = new BigDecimal(uCampos[9]);
				this.idempresa = new BigDecimal(uCampos[11]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosTickets()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			listEstadosTicket = Common.getGeneral().getTicketsestadosAll(250,
					0, this.idempresa);
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("ticketsAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					if (idgrupo.longValue() < 0) {
						if (idusuario.longValue() < 0) {
							this.mensaje = "Debe seleccionar grupo o usuario";
							return false;
						} else {
							idgrupo = new BigDecimal(-1);
							grupo = "";
						}
					}
					if (idusuario.longValue() < 0) {
						if (idgrupo.longValue() < 0) {
							this.mensaje = "Debe seleccionar grupo o usuario";
							return false;
						} else {
							idusuario = new BigDecimal(-1);
							usuario = "";
						}
					}
					if (idcliente.longValue() < 0) {
						this.mensaje = "No puede dejar vacio el campo cliente";
						return false;
					}
					if (resumen.equalsIgnoreCase(""))
					{
						this.mensaje = "No puede dejar vacio el resumen";
						return false;
					}
					if (descripcion.equalsIgnoreCase(""))
					{
						this.mensaje = "No puede dejar vacio el descripcion";
						return false;
					}
					if (idticketestado.longValue() < 0) {
						this.mensaje = "Debe seleccionar un estado";
						return false;
					}
				}
				// 2012-09-21 - CAMI - Gestion de cob
				/*
				 * Tengo que setear en null estos valores si son menores a cero,
				 * es un if ternario remember that,
				 */

				idusuario = GeneralBean.setNull(idusuario, 0);
				idgrupo = GeneralBean.setNull(idgrupo, 0);

				this.ejecutarSentenciaDML();
				// 2012-09-21 - CAMI - Gestion de cob
				/*
				 * Si los valores son nulos,los tengo que desanular (? porque si
				 * no se rompe el jsp
				 */

				if (idgrupo == null) {
					idgrupo = new BigDecimal(-1);
					grupo = "";
				}
				if (idusuario == null) {
					idusuario = new BigDecimal(-1);
					usuario = "";
				}
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosTickets();
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
	public BigDecimal getIdticket() {
		return idticket;
	}

	public void setIdticket(BigDecimal idticket) {
		this.idticket = idticket;
	}

	public BigDecimal getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(BigDecimal idgrupo) {
		this.idgrupo = idgrupo;
	}

	public BigDecimal getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(BigDecimal idusuario) {
		this.idusuario = idusuario;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getIdticketestado() {
		return idticketestado;
	}

	public void setIdticketestado(BigDecimal idticketestado) {
		this.idticketestado = idticketestado;
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

	public List getListEstadosTicket() {
		return listEstadosTicket;
	}

	public void setListEstadosTicket(List listEstadosTicket) {
		this.listEstadosTicket = listEstadosTicket;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public List getEmailGrupo() {
		return emailGrupo;
	}

	public void setEmailGrupo(List emailGrupo) {
		this.emailGrupo = emailGrupo;
	}

	public boolean isEnviaCorreo() {
		return enviaCorreo;
	}

	public void setEnviaCorreo(boolean enviaCorreo) {
		this.enviaCorreo = enviaCorreo;
	}

}
