/* 
   javabean para la entidad (Formulario): sasmotivoscontactos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri May 27 10:58:35 ART 2011 
   
   Para manejar la pagina: sasmotivoscontactosFrm.jsp
      
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

public class BeanSasmotivoscontactosFrm implements SessionBean, Serializable {
	private SessionContext context;
	static Logger log = Logger.getLogger(BeanSasmotivoscontactosFrm.class);
	private String validar = "";
	private BigDecimal idmotivocontacto = new BigDecimal(-1);
	private String motivocontacto = "";
	private BigDecimal idtipocontacto = new BigDecimal(-1);
	private String tipocontacto = "";
	private BigDecimal idcanalcontacto = new BigDecimal(-1);
	private String usuarioalt;
	private String usuarioact;
	private String mensaje = "";
	private String volver = "";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String accion = "";
	private BigDecimal idempresa = new BigDecimal(-1);
	private List listtipo = new ArrayList();
	private List listcanal = new ArrayList();

	public BeanSasmotivoscontactosFrm() {
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
				this.mensaje = clientes.sasmotivoscontactosCreate(
						this.motivocontacto, this.idtipocontacto,
						this.idcanalcontacto, this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.sasmotivoscontactosUpdate(
						this.idmotivocontacto, this.motivocontacto,
						this.idtipocontacto, this.idcanalcontacto,
						this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.sasmotivoscontactosDelete(
						this.idmotivocontacto, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosSasmotivoscontactos() {
		try {
			Clientes clientes = Common.getClientes();
			List listSasmotivoscontactos = clientes.getSasmotivoscontactosPK(
					this.idmotivocontacto, this.idempresa);
			Iterator iterSasmotivoscontactos = listSasmotivoscontactos
					.iterator();
			if (iterSasmotivoscontactos.hasNext()) {
				String[] uCampos = (String[]) iterSasmotivoscontactos.next();
				// TODO: Constructores para cada tipo de datos

				this.idmotivocontacto = new BigDecimal(uCampos[0]);
				this.motivocontacto = uCampos[1];
				this.idtipocontacto = new BigDecimal(uCampos[2]);
				this.tipocontacto = uCampos[3];
				this.idcanalcontacto = new BigDecimal(uCampos[4]);
				this.listcanal = clientes.getSascanalescontactosLista(
						//"",
						idtipocontacto, idempresa);
				
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosSasmotivoscontactos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			Clientes clientes = Common.getClientes();
			this.listtipo = clientes.getSastiposcontactosAll(250, 0, idempresa);
			if (this.idtipocontacto.longValue() > 0 && tipocontacto.equalsIgnoreCase("entrada")) {

				this.listcanal = clientes.getSascanalescontactosLista(
						//"entrada", 
						idtipocontacto, idempresa);
			}
			if (this.idtipocontacto.longValue() > 0
					&& tipocontacto.equalsIgnoreCase("salida")) {
				this.listcanal = clientes.getSascanalescontactosLista(
						//"salida",
						idtipocontacto, idempresa);
			}
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("sasmotivoscontactosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (motivocontacto == null) {
						this.mensaje = "No se puede dejar vacio el campo motivocontacto ";
						return false;
					}
					if (idtipocontacto == null) {
						this.mensaje = "No se puede dejar vacio el campo idtipocontacto ";
						return false;
					}

					// 2. len 0 para campos nulos
					if (motivocontacto.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo motivocontacto  ";
						return false;
					}
					if (idtipocontacto.longValue() < 0) {
						this.mensaje = "Debe seleccionar el tipo de contacto";
						return false;
					}
					if (idcanalcontacto.longValue() < 0) {
						this.mensaje = "Debe seleccionar el canal de contacto";
						return false;
					}

					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosSasmotivoscontactos();
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
	public BigDecimal getIdmotivocontacto() {
		return idmotivocontacto;
	}

	public void setIdmotivocontacto(BigDecimal idmotivocontacto) {
		this.idmotivocontacto = idmotivocontacto;
	}

	public String getMotivocontacto() {
		return motivocontacto;
	}

	public void setMotivocontacto(String motivocontacto) {
		this.motivocontacto = motivocontacto;
	}

	public BigDecimal getIdtipocontacto() {
		return idtipocontacto;
	}

	public void setIdtipocontacto(BigDecimal idtipocontacto) {
		this.idtipocontacto = idtipocontacto;
	}

	public BigDecimal getIdcanalcontacto() {
		return idcanalcontacto;
	}

	public void setIdcanalcontacto(BigDecimal idcanalcontacto) {
		this.idcanalcontacto = idcanalcontacto;
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

	public List getListtipo() {
		return listtipo;
	}

	public void setListtipo(List listtipo) {
		this.listtipo = listtipo;
	}

	public List getListcanal() {
		return listcanal;
	}

	public void setListcanal(List listcanal) {
		this.listcanal = listcanal;
	}

	public String getTipocontacto() {
		return tipocontacto;
	}

	public void setTipocontacto(String tipocontacto) {
		this.tipocontacto = tipocontacto;
	}
	
}
