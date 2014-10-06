/* 
 javabean para la entidad (Formulario): reportes
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Jun 28 09:50:53 GMT-03:00 2006 
 
 Para manejar la pagina: reportesFrm.jsp
 
 */
package ar.com.syswarp.web.ejb.report;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.util.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanReportesFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanReportesFrm.class);

	private String validar = "";

	private long idreporte = 0;

	private String reporte = "";

	private String comentario = "";

	private String skin = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String usuarioalt = "";

	private String usuarioact = "";

	private String accion = "";

	public BeanReportesFrm() {
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
			Report reportes = Common.getReport();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = reportes.reportesCreate(this.reporte,
						this.comentario, this.skin, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = reportes.reportesUpdate(this.idreporte,
						this.reporte, this.comentario, this.skin,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = reportes.reportesDelete(this.idreporte);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosReportes() {
		try {
			Report reportes = Common.getReport();
			List listReportes = reportes.getReportesPK(this.idreporte);
			Iterator iterReportes = listReportes.iterator();
			if (iterReportes.hasNext()) {
				String[] uCampos = (String[]) iterReportes.next();
				// TODO: Constructores para cada tipo de datos
				this.idreporte = Long.parseLong(uCampos[0]);
				this.reporte = uCampos[1];
				this.comentario = uCampos[2];
				this.skin = uCampos[3];
			} else {
				this.mensaje = "Imposible recuperar datos para el reporte seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosReportes()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("reportesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					if (this.reporte.trim().length() == 0) {
						this.mensaje = "Ingrese reporte";
						return false;
					}
					if (this.reporte.indexOf("'") >= 0) {
						this.mensaje = "Caracteres inv�lidos campo reporte.";
						return false;
					}
					if (this.comentario.indexOf("'") >= 0) {
						this.mensaje = "Caracteres inv�lidos campo comentario.";
						return false;
					}
					if (this.skin.indexOf("'") >= 0) {
						this.mensaje = "Caracteres inv�lidos campo skin.";
						return false;
					}
					// 2. nulidad de campos
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosReportes();
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

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public long getIdreporte() {
		return idreporte;
	}

	public void setIdreporte(long idreporte) {
		this.idreporte = idreporte;
	}

	public String getReporte() {
		return reporte;
	}

	public void setReporte(String reporte) {
		this.reporte = reporte;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

}
