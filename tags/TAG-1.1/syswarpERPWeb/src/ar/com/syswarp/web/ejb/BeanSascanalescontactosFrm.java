/* 
   javabean para la entidad (Formulario): sascanalescontactos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri May 27 10:10:39 ART 2011 
   
   Para manejar la pagina: sascanalescontactosFrm.jsp
      
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

public class BeanSascanalescontactosFrm implements SessionBean, Serializable {
	private SessionContext context;
	static Logger log = Logger.getLogger(BeanSascanalescontactosFrm.class);
	private String validar = "";
	private BigDecimal idcanalcontacto = new BigDecimal(-1);
	private String canalcontacto = "";
	private BigDecimal idtipocontacto = new BigDecimal(-1);
	private String usuarioalt;
	private String usuarioact;
	private String mensaje = "";
	private String volver = "";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String accion = "";
	private BigDecimal idempresa = new BigDecimal(-1);
	private List listatipos = new ArrayList();
	public BeanSascanalescontactosFrm() {
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
				this.mensaje = clientes.sascanalescontactosCreate(
						this.canalcontacto, this.idtipocontacto,
						this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.sascanalescontactosUpdate(
						this.idcanalcontacto, this.canalcontacto,
						this.idtipocontacto, this.usuarioact, this.idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.sascanalescontactosDelete(
						this.idcanalcontacto, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosSascanalescontactos() {
		try {
			Clientes clientes = Common.getClientes();
			List listSascanalescontactos = clientes.getSascanalescontactosPK(
					this.idcanalcontacto, this.idempresa);
			Iterator iterSascanalescontactos = listSascanalescontactos
					.iterator();
			if (iterSascanalescontactos.hasNext()) {
				String[] uCampos = (String[]) iterSascanalescontactos.next();
				// TODO: Constructores para cada tipo de datos
				this.idcanalcontacto = new BigDecimal(uCampos[0]);
				this.canalcontacto = uCampos[1];
				this.idtipocontacto = new BigDecimal(uCampos[2]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosSascanalescontactos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			Clientes clientes = Common.getClientes();
			this.listatipos = clientes.getSastiposcontactosAll(250, 0, idempresa);
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("sascanalescontactosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (canalcontacto == null) {
						this.mensaje = "No se puede dejar vacio el campo Canal de Contacto ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (canalcontacto.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo Canal de Contacto   ";
						return false;
					}
					if (idtipocontacto.longValue() < 0)
					{
						this.mensaje = "Debe seleccionar el tipo de contacto";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosSascanalescontactos();
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
	public BigDecimal getIdcanalcontacto() {
		return idcanalcontacto;
	}

	public void setIdcanalcontacto(BigDecimal idcanalcontacto) {
		this.idcanalcontacto = idcanalcontacto;
	}

	public String getCanalcontacto() {
		return canalcontacto;
	}

	public void setCanalcontacto(String canalcontacto) {
		this.canalcontacto = canalcontacto;
	}

	
	public BigDecimal getIdtipocontacto() {
		return idtipocontacto;
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

	public void setIdtipocontacto(BigDecimal idtipocontacto) {
		this.idtipocontacto = idtipocontacto;
	}

	public List getListatipos() {
		return listatipos;
	}

	public void setListatipos(List listatipos) {
		this.listatipos = listatipos;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
	
}
