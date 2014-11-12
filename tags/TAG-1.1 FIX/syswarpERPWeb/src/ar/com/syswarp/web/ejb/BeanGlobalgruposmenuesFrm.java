/* 
 javabean para la entidad (Formulario): globalgruposmenues
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 23 13:14:04 GMT-03:00 2007 
 
 Para manejar la pagina: globalgruposmenuesFrm.jsp
 
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

public class BeanGlobalgruposmenuesFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanGlobalgruposmenuesFrm.class);

	private String validar = "";

	private BigDecimal idgrupo = BigDecimal.valueOf(0);

	private String grupo = "";

	private BigDecimal idmenu = BigDecimal.valueOf(0);

	private String menu = "";

	private BigDecimal nivel = BigDecimal.valueOf(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanGlobalgruposmenuesFrm() {
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
			General globalgruposmenues = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = globalgruposmenues.globalgruposmenuesCreate(
						this.idgrupo, this.idmenu, this.nivel, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = globalgruposmenues.globalgruposmenuesUpdate(
						this.idgrupo, this.idmenu, this.nivel, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = globalgruposmenues.globalgruposmenuesDelete(
						this.idgrupo, this.idmenu);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGlobalgruposmenues() {
		try {
			General globalgruposmenues = Common.getGeneral();
			List listGlobalgruposmenues = globalgruposmenues
					.getGlobalgruposmenuesPK(this.idgrupo, this.idmenu);
			Iterator iterGlobalgruposmenues = listGlobalgruposmenues.iterator();
			if (iterGlobalgruposmenues.hasNext()) {
				String[] uCampos = (String[]) iterGlobalgruposmenues.next();
				// TODO: Constructores para cada tipo de datos
				this.idgrupo = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.grupo = uCampos[1];
				this.idmenu = BigDecimal.valueOf(Long.parseLong(uCampos[2]));
				this.menu = uCampos[3];
				this.nivel = BigDecimal.valueOf(Long.parseLong(uCampos[4]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGlobalgruposmenues()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("globalgruposmenuesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGlobalgruposmenues();
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
	public BigDecimal getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(BigDecimal idgrupo) {
		this.idgrupo = idgrupo;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public BigDecimal getIdmenu() {
		return idmenu;
	}

	public void setIdmenu(BigDecimal idmenu) {
		this.idmenu = idmenu;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public BigDecimal getNivel() {
		return nivel;
	}

	public void setNivel(BigDecimal nivel) {
		this.nivel = nivel;
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
