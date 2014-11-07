/* 
   javabean para la entidad (Formulario): globalBlobImagenes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 17 11:33:18 GYT 2009 
   
   Para manejar la pagina: globalBlobImagenesFrm.jsp
      
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

public class BeanGlobalBlobImagenesFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanGlobalBlobImagenesFrm.class);

	private String validar = "";

	private BigDecimal tupla = new BigDecimal(-1);

	private BigDecimal trama = new BigDecimal(-1);

	private String nombre = "";

	private String descripcion = "";

	private String principal = "N";

	private String tmppath = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private boolean soloImagen = false;

	public BeanGlobalBlobImagenesFrm() {
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
			General globalBlobImagenes = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				;
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = globalBlobImagenes.globalBlobImagenesUpdate(
						this.tupla, this.trama, this.nombre, this.descripcion,
						this.principal, this.tmppath, this.idempresa,
						this.usuarioact);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGlobalBlobImagenes() {
		try {
			General globalBlobImagenes = Common.getGeneral();
			List listGlobalBlobImagenes = globalBlobImagenes
					.getGlobalBlobImagenesPK(this.tupla, this.trama,
							this.idempresa);
			Iterator iterGlobalBlobImagenes = listGlobalBlobImagenes.iterator();
			if (iterGlobalBlobImagenes.hasNext()) {
				String[] uCampos = (String[]) iterGlobalBlobImagenes.next();
				// TODO: Constructores para cada tipo de datos
				this.tupla = new BigDecimal(uCampos[0]);
				this.trama = new BigDecimal(uCampos[1]);
				this.nombre = uCampos[2];
				this.descripcion = uCampos[3];
				this.principal = uCampos[4];
				this.tmppath = uCampos[5];

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGlobalBlobImagenes()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("globalBlobImagenesAbm.jsp?tupla="
						+ this.tupla + "&soloImagen=" + this.soloImagen);
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (trama == null) {
						this.mensaje = "No se puede dejar vacio el campo trama ";
						return false;
					}
					if (nombre == null) {
						this.mensaje = "No se puede dejar vacio el campo nombre ";
						return false;
					}
					if (principal == null) {
						this.mensaje = "No se puede dejar vacio el campo principal ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (nombre.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo nombre  ";
						return false;
					}
					if (principal.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo principal  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGlobalBlobImagenes();
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
	public BigDecimal getTupla() {
		return tupla;
	}

	public void setTupla(BigDecimal tupla) {
		this.tupla = tupla;
	}

	public BigDecimal getTrama() {
		return trama;
	}

	public void setTrama(BigDecimal trama) {
		this.trama = trama;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getTmppath() {
		return tmppath;
	}

	public void setTmppath(String tmppath) {
		this.tmppath = tmppath;
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

	public boolean isSoloImagen() {
		return soloImagen;
	}

	public void setSoloImagen(boolean soloImagen) {
		this.soloImagen = soloImagen;
	}
}
