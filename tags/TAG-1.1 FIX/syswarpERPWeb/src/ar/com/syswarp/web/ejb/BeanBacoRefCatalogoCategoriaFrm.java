/* 
   javabean para la entidad (Formulario): bacoRefCatalogoCategoria
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Mar 15 09:25:38 ART 2012 
   
   Para manejar la pagina: bacoRefCatalogoCategoriaFrm.jsp
      
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

public class BeanBacoRefCatalogoCategoriaFrm implements SessionBean,
		Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoRefCatalogoCategoriaFrm.class);

	private String validar = "";

	private BigDecimal idcatalogocategoria = new BigDecimal(-1);

	private String catalogocategoria = "";

	private String observaciones = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanBacoRefCatalogoCategoriaFrm() {
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
				this.mensaje = clientes.bacoRefCatalogoCategoriaCreate(
						this.catalogocategoria, this.observaciones,
						this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientes.bacoRefCatalogoCategoriaUpdate(
						this.idcatalogocategoria, this.catalogocategoria,
						this.observaciones, this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientes.bacoRefCatalogoCategoriaDelete(
						this.idcatalogocategoria, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoRefCatalogoCategoria() {
		try {
			Clientes clientes = Common.getClientes();
			List listBacoRefCatalogoCategoria = clientes
					.getBacoRefCatalogoCategoriaPK(this.idcatalogocategoria,
							this.idempresa);
			Iterator iterBacoRefCatalogoCategoria = listBacoRefCatalogoCategoria
					.iterator();
			if (iterBacoRefCatalogoCategoria.hasNext()) {
				String[] uCampos = (String[]) iterBacoRefCatalogoCategoria
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idcatalogocategoria = new BigDecimal(uCampos[0]);
				this.catalogocategoria = uCampos[1];
				this.observaciones = uCampos[2];
				this.idempresa = new BigDecimal(uCampos[3]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacoRefCatalogoCategoria()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacoRefCatalogoCategoriaAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (catalogocategoria == null) {
						this.mensaje = "No se puede dejar vacio el campo catalogocategoria ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (catalogocategoria.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo catalogocategoria  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosBacoRefCatalogoCategoria();
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
	public BigDecimal getIdcatalogocategoria() {
		return idcatalogocategoria;
	}

	public void setIdcatalogocategoria(BigDecimal idcatalogocategoria) {
		this.idcatalogocategoria = idcatalogocategoria;
	}

	public String getCatalogocategoria() {
		return catalogocategoria;
	}

	public void setCatalogocategoria(String catalogocategoria) {
		this.catalogocategoria = catalogocategoria;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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
