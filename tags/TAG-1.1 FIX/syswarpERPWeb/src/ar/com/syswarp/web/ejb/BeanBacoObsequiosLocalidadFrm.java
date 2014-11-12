/* 
   javabean para la entidad (Formulario): bacoObsequiosLocalidad
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Nov 04 15:51:58 GMT-03:00 2009 
   
   Para manejar la pagina: bacoObsequiosLocalidadFrm.jsp
      
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

public class BeanBacoObsequiosLocalidadFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoObsequiosLocalidadFrm.class);

	private String validar = "";

	private BigDecimal idobsequio = new BigDecimal(-1);

	private BigDecimal idlocalidad = new BigDecimal(-1);

	private String localidad = "";

	private String cartaoregalo = "";

	private String informecarta = "";

	private String restaurant = "";

	private String clusterlogistica = "";

	private BigDecimal idclusterlogistica = new BigDecimal(-1);

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private String referente = "";

	public BeanBacoObsequiosLocalidadFrm() {
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
			Clientes bacoObsequiosLocalidad = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = bacoObsequiosLocalidad
						.bacoObsequiosLocalidadCreate(this.idlocalidad,
								this.cartaoregalo, this.informecarta,
								this.restaurant, GeneralBean.setNull(
										this.idclusterlogistica, 1),
								this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = bacoObsequiosLocalidad
						.bacoObsequiosLocalidadUpdate(this.idobsequio,
								this.idlocalidad, this.cartaoregalo,
								this.informecarta, this.restaurant, GeneralBean
										.setNull(this.idclusterlogistica, 1),
								this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja")) {
				this.mensaje = bacoObsequiosLocalidad
						.bacoObsequiosLocalidadDelete(this.idobsequio,
								this.idempresa);

				if (this.mensaje.equalsIgnoreCase("baja correcta")) {

					this.accion = "alta";
					this.idobsequio = new BigDecimal(-1);

				}

			}
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoObsequiosLocalidad() {
		try {
			Clientes clie = Common.getClientes();
			List listBacoObsequiosLocalidad = clie.getBacoObsequiosLocalidadPK(
					this.idobsequio, this.idempresa);
			Iterator iterBacoObsequiosLocalidad = listBacoObsequiosLocalidad
					.iterator();
			if (iterBacoObsequiosLocalidad.hasNext()) {
				String[] uCampos = (String[]) iterBacoObsequiosLocalidad.next();
				// TODO: Constructores para cada tipo de datos
				this.idobsequio = new BigDecimal(uCampos[0]);
				this.idlocalidad = new BigDecimal(uCampos[1]);
				this.localidad = uCampos[2];
				this.cartaoregalo = uCampos[3];
				this.informecarta = uCampos[4];
				this.restaurant = uCampos[5];
				this.idclusterlogistica = new BigDecimal(!Common.setNotNull(
						uCampos[6]).equals("") ? uCampos[6] : "-1");
				this.clusterlogistica = Common.setNotNull(uCampos[7]);

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacoObsequiosLocalidad()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (this.referente.equals("")) {
				this.referente = request.getHeader("referer").substring(
						request.getHeader("referer").lastIndexOf("/") + 1);
			}

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect(referente);
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idlocalidad == null) {
						this.mensaje = "No se puede dejar vacio el campo idlocalidad ";
						return false;
					}
					if (cartaoregalo == null) {
						this.mensaje = "No se puede dejar vacio el campo cartaoregalo ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (cartaoregalo.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo cartaoregalo  ";
						return false;
					}

					if (this.cartaoregalo.equalsIgnoreCase("C")) {
						if (Common.setNotNull(this.informecarta).equals("")) {
							this.mensaje = "Es necesario ingresar Plantilla";
							return false;
						}
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosBacoObsequiosLocalidad();
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
	public BigDecimal getIdobsequio() {
		return idobsequio;
	}

	public void setIdobsequio(BigDecimal idobsequio) {
		this.idobsequio = idobsequio;
	}

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCartaoregalo() {
		return cartaoregalo;
	}

	public void setCartaoregalo(String cartaoregalo) {
		this.cartaoregalo = cartaoregalo;
	}

	public String getInformecarta() {
		return informecarta;
	}

	public void setInformecarta(String informecarta) {
		this.informecarta = informecarta;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
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

	public String getReferente() {
		return referente;
	}

	public void setReferente(String referente) {
		this.referente = referente;
	}

	// 20110331 - EJV - Mantis 685

	public String getClusterlogistica() {
		return clusterlogistica;
	}

	public void setClusterlogistica(String clusterlogistica) {
		this.clusterlogistica = clusterlogistica;
	}

	public BigDecimal getIdclusterlogistica() {
		return idclusterlogistica;
	}

	public void setIdclusterlogistica(BigDecimal idclusterlogistica) {
		this.idclusterlogistica = idclusterlogistica;
	}

	// <--

}
