/* 
 javabean para la entidad (Formulario): marketRegistroDireccion
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Mar 14 14:48:01 ART 2008 
 
 Para manejar la pagina: marketRegistroDireccionFrm.jsp
 
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

public class BeanMarketRegistroDireccionFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanMarketRegistroDireccionFrm.class);

	private BigDecimal idempresa = new BigDecimal(-1);

	boolean mismadireccion = false;

	private String email = "";

	private String pass = "";

	private BigDecimal total = new BigDecimal(0);

	private String validar = "";

	private BigDecimal idcliente = new BigDecimal(-1);

	private String nombreFact = "";

	private String apellidoFact = "";

	private String empresaFact = "";

	private String direccionFact = "";

	private String ciudadFact = "";

	private String provinciaestadoFact = "";

	private String codigopostalFact = "";

	private String paisFact = "";

	private String telefonoFact = "";

	private String faxFact = "";

	private String comentarios = "";

	private String obsentrega = "";

	private String nombreEnv = "";

	private String apellidoEnv = "";

	private String empresaEnv = "";

	private String direccionEnv = "";

	private String ciudadEnv = "";

	private String provinciaestadoEnv = "";

	private String codigopostalEnv = "";

	private String paisEnv = "";

	private String telefonoEnv = "";

	private String faxEnv = "";

	private String tipodireccion = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private Hashtable htCarrito = new Hashtable();

	public BeanMarketRegistroDireccionFrm() {
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
		// TODO: Eliminar, quedo obsoleta, ahora se llama desde la seleccion de
		// formas de pago.
		try {
			Stock marketRegistroDireccion = Common.getStock();

			Hashtable htDireccion = new Hashtable();

			BigDecimal idformadepago = new BigDecimal(-1);

			// Datos facturacion
			htDireccion.put("nombreFact", nombreFact);
			htDireccion.put("apellidoFact", apellidoFact);
			htDireccion.put("empresaFact", empresaFact);
			htDireccion.put("direccionFact", direccionFact);
			htDireccion.put("ciudadFact", ciudadFact);
			htDireccion.put("provinciaestadoFact", provinciaestadoFact);
			htDireccion.put("codigopostalFact", codigopostalFact);
			htDireccion.put("paisFact", paisFact);
			htDireccion.put("telefonoFact", telefonoFact);
			htDireccion.put("faxFact", faxFact);

			// Datos envio
			htDireccion.put("nombreEnv", nombreEnv);
			htDireccion.put("apellidoEnv", apellidoEnv);
			htDireccion.put("empresaEnv", empresaEnv);
			htDireccion.put("direccionEnv", direccionEnv);
			htDireccion.put("ciudadEnv", ciudadEnv);
			htDireccion.put("provinciaestadoEnv", provinciaestadoEnv);
			htDireccion.put("codigopostalEnv", codigopostalEnv);
			htDireccion.put("paisEnv", paisEnv);
			htDireccion.put("telefonoEnv", telefonoEnv);
			htDireccion.put("faxEnv", faxEnv);

			if (this.accion.equalsIgnoreCase("alta")) {

				this.mensaje = marketRegistroDireccion
						.marketCreateRegistroAndPedido(this.idcliente,
								this.email, this.pass, "S", this.total,
								idformadepago, this.comentarios,
								this.obsentrega, htDireccion, this.htCarrito,
								this.idempresa, usuarioalt);

				if (Common.esEntero(this.mensaje)) {
					request.getSession().setAttribute("accionCarrito",
							"modificacion");
					request.getSession().removeAttribute("htCarrito");
					request.getSession().removeAttribute("totalCarrito");

					this.mensaje = this.nombreFact + " " + this.apellidoFact
							+ ", su pedido # " + this.mensaje
							+ " ha sido recibido con �xito.";
					this.mensaje += "Pronto recibir� por e-mail una copia con el detalle de su compra. ";
					request.getSession().setAttribute("mensaje", this.mensaje);
					response.sendRedirect("marketAviso.jsp");

				}

			} else if (this.accion.equalsIgnoreCase("modificacion")) {
				this.mensaje = marketRegistroDireccion
						.marketCreateRegistroAndPedido(this.idcliente,
								this.email, this.pass, "S", this.total,
								idformadepago, this.comentarios,
								this.obsentrega, htDireccion, this.htCarrito,
								this.idempresa, usuarioalt);

				if (Common.esEntero(this.mensaje)) {
					request.getSession().setAttribute("accionCarrito",
							"modificacion");
					request.getSession().removeAttribute("htCarrito");
					request.getSession().removeAttribute("totalCarrito");

					this.mensaje = this.nombreFact + " " + this.apellidoFact
							+ ", su pedido # " + this.mensaje
							+ " ha sido recibido con �xito.";
					this.mensaje += "Pronto recibir� por e-mail una copia con el detalle de su compra. ";
					request.getSession().setAttribute("mensaje", this.mensaje);
					response.sendRedirect("marketAviso.jsp");
				}
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	private void saveDatosToSession() {
		try {

			Hashtable htDireccion = new Hashtable();
			Hashtable htVarios = new Hashtable();

			// Datos facturacion
			htDireccion.put("nombreFact", nombreFact);
			htDireccion.put("apellidoFact", apellidoFact);
			htDireccion.put("empresaFact", empresaFact);
			htDireccion.put("direccionFact", direccionFact);
			htDireccion.put("ciudadFact", ciudadFact);
			htDireccion.put("provinciaestadoFact", provinciaestadoFact);
			htDireccion.put("codigopostalFact", codigopostalFact);
			htDireccion.put("paisFact", paisFact);
			htDireccion.put("telefonoFact", telefonoFact);
			htDireccion.put("faxFact", faxFact);

			// Datos envio
			htDireccion.put("nombreEnv", nombreEnv);
			htDireccion.put("apellidoEnv", apellidoEnv);
			htDireccion.put("empresaEnv", empresaEnv);
			htDireccion.put("direccionEnv", direccionEnv);
			htDireccion.put("ciudadEnv", ciudadEnv);
			htDireccion.put("provinciaestadoEnv", provinciaestadoEnv);
			htDireccion.put("codigopostalEnv", codigopostalEnv);
			htDireccion.put("paisEnv", paisEnv);
			htDireccion.put("telefonoEnv", telefonoEnv);
			htDireccion.put("faxEnv", faxEnv);

			// Agrupar datos varios
			htVarios.put("idcliente", this.idcliente);
			htVarios.put("comentarios", this.comentarios);
			htVarios.put("obsentrega", this.obsentrega);

			// Guardar para listar
			request.getSession().setAttribute("htDireccion", htDireccion);
			request.getSession().setAttribute("htVarios", htVarios);

			response.sendRedirect("marketSelFPago.jsp");

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosMarketRegistroDireccion() {
		try {

			Stock marketRegistroDireccion = Common.getStock();
			this.idcliente = marketRegistroDireccion
					.getMarketRegistroIdCliente(this.email, this.pass,
							this.idempresa);
			List listMarketRegistroDireccion = marketRegistroDireccion
					.getMarketRegistroDireccionPK(this.idcliente,
							this.idempresa);
			Iterator iterMarketRegistroDireccion = listMarketRegistroDireccion
					.iterator();
			if (iterMarketRegistroDireccion.hasNext()) {
				do {
					String[] uCampos = (String[]) iterMarketRegistroDireccion
							.next();
					this.tipodireccion = uCampos[11];
					if (this.tipodireccion.equalsIgnoreCase("F")) {
						// TODO: Constructores para cada tipo de datos
						this.nombreFact = uCampos[1];
						this.apellidoFact = uCampos[2];
						this.empresaFact = uCampos[3];
						this.direccionFact = uCampos[4];
						this.ciudadFact = uCampos[5];
						this.provinciaestadoFact = uCampos[6];
						this.codigopostalFact = uCampos[7];
						this.paisFact = uCampos[8];
						this.telefonoFact = uCampos[9];
						this.faxFact = uCampos[10];
					} else if (this.tipodireccion.equalsIgnoreCase("E")) {
						this.nombreEnv = uCampos[1];
						this.apellidoEnv = uCampos[2];
						this.empresaEnv = uCampos[3];
						this.direccionEnv = uCampos[4];
						this.ciudadEnv = uCampos[5];
						this.provinciaestadoEnv = uCampos[6];
						this.codigopostalEnv = uCampos[7];
						this.paisEnv = uCampos[8];
						this.telefonoEnv = uCampos[9];
						this.faxEnv = uCampos[10];
					}

				} while (iterMarketRegistroDireccion.hasNext());
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosMarketRegistroDireccion()" + e);
		}
	}

	private boolean setAccion() {
		try {

			String accionCarrito = request.getSession().getAttribute(
					"accionCarrito") != null ? request.getSession()
					.getAttribute("accionCarrito").toString() : "";

			if (this.accion.equals("")) {
				if (accionCarrito.equals("")) {
					response
							.sendRedirect("marketRegistroFrm.jsp?mensaje=Por favor, valide nuevamente su usuario.");
					return false;
				} else
					this.accion = accionCarrito;

			}

		} catch (Exception e) {

			log.error("setAccion():" + e);
			return false;
		}

		return true;
	}

	public boolean ejecutarValidacion() {
		try {

			this.htCarrito = (Hashtable) request.getSession().getAttribute(
					"htCarrito");

			if (this.htCarrito == null || this.htCarrito.isEmpty()) {
				request
						.getSession()
						.setAttribute("mensaje",
								"Su carrito se encuentra vacio, por favor seleccione algún producto.");
				response.sendRedirect("marketAviso.jsp");
			}

			if (!this.setAccion())
				return false;

			this.total = (BigDecimal) request.getSession().getAttribute(
					"totalCarrito");
			this.email = (String) request.getSession().getAttribute(
					"emailCarrito");
			this.pass = (String) request.getSession().getAttribute(
					"passCarrito");

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (this.nombreFact == null
							|| this.nombreFact.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo nombre facturacion. ";
						return false;
					}
					if (this.apellidoFact == null
							|| this.apellidoFact.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo apellido facturacion. ";
						return false;
					}
					if (this.empresaFact == null
							|| this.empresaFact.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo empresa facturacion. ";
						return false;
					}
					if (this.direccionFact == null
							|| this.direccionFact.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo direccion facturacion. ";
						return false;
					}
					if (this.ciudadFact == null
							|| this.ciudadFact.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo ciudad facturacion. ";
						return false;
					}
					if (this.provinciaestadoFact == null
							|| this.provinciaestadoFact.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo provinciaestado facturacion. ";
						return false;
					}
					if (this.codigopostalFact == null
							|| this.codigopostalFact.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo codigopostal facturacion. ";
						return false;
					}
					if (this.paisFact == null
							|| this.paisFact.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo pais facturacion. ";
						return false;
					}
					if (this.telefonoFact == null
							|| this.telefonoFact.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo telefono facturacion. ";
						return false;
					}


					if (!this.mismadireccion) {
						if (this.nombreEnv == null
								|| this.nombreEnv.trim().equals("")) {
							this.mensaje = "No se puede dejar vacio el campo nombre envio. ";
							return false;
						}
						if (this.apellidoEnv == null
								|| this.apellidoEnv.trim().equals("")) {
							this.mensaje = "No se puede dejar vacio el campo apellido envio. ";
							return false;
						}
						if (this.empresaEnv == null
								|| this.empresaEnv.trim().equals("")) {
							this.mensaje = "No se puede dejar vacio el campo empresa envio. ";
							return false;
						}
						if (this.direccionEnv == null
								|| this.direccionEnv.trim().equals("")) {
							this.mensaje = "No se puede dejar vacio el campo direccion envio. ";
							return false;
						}
						if (this.ciudadEnv == null
								|| this.ciudadEnv.trim().equals("")) {
							this.mensaje = "No se puede dejar vacio el campo ciudad envio. ";
							return false;
						}
						if (this.provinciaestadoEnv == null
								|| this.provinciaestadoEnv.trim().equals("")) {
							this.mensaje = "No se puede dejar vacio el campo provinciaestado envio. ";
							return false;
						}
						if (this.codigopostalEnv == null
								|| this.codigopostalEnv.trim().equals("")) {
							this.mensaje = "No se puede dejar vacio el campo codigopostal envio. ";
							return false;
						}
						if (this.paisEnv == null
								|| this.paisEnv.trim().equals("")) {
							this.mensaje = "No se puede dejar vacio el campo pais envio. ";
							return false;
						}
						if (this.telefonoEnv == null
								|| this.telefonoEnv.trim().equals("")) {
							this.mensaje = "No se puede dejar vacio el campo telefono envio. ";
							return false;
						}

					} else {

						this.nombreEnv = this.nombreFact;
						this.apellidoEnv = this.apellidoFact;
						this.empresaEnv = this.empresaFact;
						this.direccionEnv = this.direccionFact;
						this.ciudadEnv = this.ciudadFact;
						this.provinciaestadoEnv = this.provinciaestadoFact;
						this.codigopostalEnv = this.codigopostalFact;
						this.paisEnv = this.paisFact;
						this.telefonoEnv = this.telefonoFact;
						this.faxEnv = this.faxFact;

					}

				}

				// this.ejecutarSentenciaDML();

				this.saveDatosToSession();

			} else {
				if (!this.accion.equalsIgnoreCase("alta")
						&& this.idcliente.longValue() < 1) {
					getDatosMarketRegistroDireccion();
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
	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getNombreFact() {
		return nombreFact;
	}

	public void setNombreFact(String nombre) {
		this.nombreFact = nombre;
	}

	public String getApellidoFact() {
		return apellidoFact;
	}

	public void setApellidoFact(String apellido) {
		this.apellidoFact = apellido;
	}

	public String getEmpresaFact() {
		return empresaFact;
	}

	public void setEmpresaFact(String empresa) {
		this.empresaFact = empresa;
	}

	public String getDireccionFact() {
		return direccionFact;
	}

	public void setDireccionFact(String direccion) {
		this.direccionFact = direccion;
	}

	public String getCiudadFact() {
		return ciudadFact;
	}

	public void setCiudadFact(String ciudad) {
		this.ciudadFact = ciudad;
	}

	public String getProvinciaestadoFact() {
		return provinciaestadoFact;
	}

	public void setProvinciaestadoFact(String provinciaestado) {
		this.provinciaestadoFact = provinciaestado;
	}

	public String getCodigopostalFact() {
		return codigopostalFact;
	}

	public void setCodigopostalFact(String codigopostal) {
		this.codigopostalFact = codigopostal;
	}

	public String getPaisFact() {
		return paisFact;
	}

	public void setPaisFact(String pais) {
		this.paisFact = pais;
	}

	public String getTelefonoFact() {
		return telefonoFact;
	}

	public void setTelefonoFact(String telefono) {
		this.telefonoFact = telefono;
	}

	public String getFaxFact() {
		return faxFact;
	}

	public void setFaxFact(String fax) {
		this.faxFact = fax;
	}

	public String getTipodireccion() {
		return tipodireccion;
	}

	public void setTipodireccion(String tipodireccion) {
		this.tipodireccion = tipodireccion;
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

	public String getApellidoEnv() {
		return apellidoEnv;
	}

	public void setApellidoEnv(String apellidoEnv) {
		this.apellidoEnv = apellidoEnv;
	}

	public String getCiudadEnv() {
		return ciudadEnv;
	}

	public void setCiudadEnv(String ciudadEnv) {
		this.ciudadEnv = ciudadEnv;
	}

	public String getCodigopostalEnv() {
		return codigopostalEnv;
	}

	public void setCodigopostalEnv(String codigopostalEnv) {
		this.codigopostalEnv = codigopostalEnv;
	}

	public String getDireccionEnv() {
		return direccionEnv;
	}

	public void setDireccionEnv(String direccionEnv) {
		this.direccionEnv = direccionEnv;
	}

	public String getEmpresaEnv() {
		return empresaEnv;
	}

	public void setEmpresaEnv(String empresaEnv) {
		this.empresaEnv = empresaEnv;
	}

	public String getFaxEnv() {
		return faxEnv;
	}

	public void setFaxEnv(String faxEnv) {
		this.faxEnv = faxEnv;
	}

	public String getNombreEnv() {
		return nombreEnv;
	}

	public void setNombreEnv(String nombreEnv) {
		this.nombreEnv = nombreEnv;
	}

	public String getPaisEnv() {
		return paisEnv;
	}

	public void setPaisEnv(String paisEnv) {
		this.paisEnv = paisEnv;
	}

	public String getProvinciaestadoEnv() {
		return provinciaestadoEnv;
	}

	public void setProvinciaestadoEnv(String provinciaestadoEnv) {
		this.provinciaestadoEnv = provinciaestadoEnv;
	}

	public String getTelefonoEnv() {
		return telefonoEnv;
	}

	public void setTelefonoEnv(String telefonoEnv) {
		this.telefonoEnv = telefonoEnv;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getObsentrega() {
		return obsentrega;
	}

	public void setObsentrega(String obsentrega) {
		this.obsentrega = obsentrega;
	}

	public boolean isMismadireccion() {
		return mismadireccion;
	}

	public void setMismadireccion(boolean mismadireccion) {
		this.mismadireccion = mismadireccion;
	}
}
