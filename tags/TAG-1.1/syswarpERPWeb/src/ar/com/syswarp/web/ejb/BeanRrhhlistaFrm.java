/* 
   javabean para la entidad (Formulario): rrhhlista
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Nov 12 11:08:23 ART 2012 
   
   Para manejar la pagina: rrhhlistaFrm.jsp
      
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

public class BeanRrhhlistaFrm implements SessionBean, Serializable {
	private SessionContext context;
	
	static Logger log = Logger.getLogger(BeanRrhhlistaFrm.class);
	
	private String validar = "";
	
	private BigDecimal idlista = new BigDecimal(-1);
	
	private String lista = "";
	
	private String usuarioalt;
	
	private String usuarioact;
	
	private String mensaje = "";
	
	private String volver = "";
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String accion = "";
	
	private BigDecimal idempresa = new BigDecimal(-1);

	public BeanRrhhlistaFrm() {
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
				this.mensaje = rrhh.rrhhlistaCreate(this.lista,
						this.usuarioalt, this.idempresa);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = rrhh.rrhhlistaUpdate(this.idlista, this.lista,
						this.usuarioact,idempresa);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = rrhh.rrhhlistaDelete(this.idlista,
						this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosRrhhlista() {
		try {
			RRHH rrhh = Common.getRrhh();
			List listRrhhlista = rrhh.getRrhhlistaPK(this.idlista,
					this.idempresa);
			Iterator iterRrhhlista = listRrhhlista.iterator();
			if (iterRrhhlista.hasNext()) {
				String[] uCampos = (String[]) iterRrhhlista.next();
				// TODO: Constructores para cada tipo de datos
				this.idlista = new BigDecimal(uCampos[0]);
				this.lista = uCampos[1];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosRrhhlista()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("rrhhlistaAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					if (this.lista.equalsIgnoreCase(""))
					{
						this.mensaje ="No puede dejar vacio el campo Lista";
						return false;
					}
					if (this.lista.trim().length() == 0)
					{
						this.mensaje ="No puede dejar vacio el campo Lista";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosRrhhlista();
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
	public BigDecimal getIdlista() {
		return idlista;
	}

	public void setIdlista(BigDecimal idlista) {
		this.idlista = idlista;
	}

	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
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
	
	
}
