/* 
 javabean para la entidad (Formulario): globalmenues
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 23 13:27:52 GMT-03:00 2007 
 
 Para manejar la pagina: globalmenuesFrm.jsp
 
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

public class BeanGlobalmenuesFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanGlobalmenuesFrm.class);

	private String validar = "";

	private BigDecimal idmenu = BigDecimal.valueOf(-1);

	private String menu = "";

	private String link = "";

	private String target = "";

	private String image1 = "";

	private String image2 = "";

	private String idmenupadre = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanGlobalmenuesFrm() {
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
			
			this.link  = this.link.equalsIgnoreCase("") ? null : this.link;
			
			General globalmenues = Common.getGeneral();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = globalmenues.globalmenuesCreate(this.menu,
						this.link, this.target, this.image1, this.image2,
						this.idmenupadre, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = globalmenues.globalmenuesUpdate(this.idmenu,
						this.menu, this.link, this.target, this.image1,
						this.image2, this.idmenupadre, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = globalmenues.globalmenuesDelete(this.idmenu);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGlobalmenues() {
		try {
			General globalmenues = Common.getGeneral();
			List listGlobalmenues = globalmenues.getGlobalmenuesPK(this.idmenu);
			Iterator iterGlobalmenues = listGlobalmenues.iterator();
			if (iterGlobalmenues.hasNext()) {
				String[] uCampos = (String[]) iterGlobalmenues.next();
				// TODO: Constructores para cada tipo de datos
				this.idmenu = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.menu = uCampos[1];
				this.link = uCampos[2];
				this.target = uCampos[3];
				this.image1 = uCampos[4];
				this.image2 = uCampos[5];
				this.idmenupadre = uCampos[6];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGlobalmenues()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("globalmenuesAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (menu == null) {
						this.mensaje = "No se puede dejar vacio el campo menu ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (menu.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo Menu  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGlobalmenues();
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getIdmenupadre() {
		return idmenupadre;
	}

	public void setIdmenupadre(String idmenupadre) {
		this.idmenupadre = idmenupadre;
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
