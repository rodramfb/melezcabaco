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

public class BeanMarketDetallePedido implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanMarketDetallePedido.class);

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal idpedicabe = new BigDecimal(1);

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

	private BigDecimal idformadepago = new BigDecimal(-1);

	private String formadepago = "";

	private Hashtable htCarrito = new Hashtable();

	private List listDetallePedido = new ArrayList();

	private String enviarPedido = "";

	private String body = "";

	public BeanMarketDetallePedido() {
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

	public void getDatosPedido() {
		try {
			
			Stock maket = Common.getStock();
			this.idcliente = maket.getMarketRegistroIdCliente(this.email,
					this.pass, this.idempresa);
			List listDatosPedido = maket.getMarketRegistroDireccionPedido(
					this.idcliente, this.idpedicabe, this.idempresa);
			Iterator iterDatosPedido = listDatosPedido.iterator();

			if (iterDatosPedido.hasNext()) {
				do {
					String[] uCampos = (String[]) iterDatosPedido.next();
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
						
						body = "Estimado/a " +  this.nombreEnv + " " + this.apellidoEnv + " este es el detalle de su pedido \n";
					}

				} while (iterDatosPedido.hasNext());

				listDatosPedido = maket.getMarketPedidosCabePK(this.idpedicabe,
						this.idempresa);

				if (!listDatosPedido.isEmpty()) {

					String[] uCampos = (String[]) listDatosPedido.get(0);
					this.idformadepago = new BigDecimal(uCampos[4]);

				}

				listDatosPedido = maket.getMarketformasdepagoPK(
						this.idformadepago, this.idempresa);

				if (!listDatosPedido.isEmpty()) {

					String[] uCampos = (String[]) listDatosPedido.get(0);
					this.formadepago = uCampos[1];

				}

				this.listDetallePedido = maket.getMarketPedidosDetaXPedido(
						this.idpedicabe, this.idempresa);

				Iterator iter = this.listDetallePedido.iterator();
				
				BigDecimal total = new BigDecimal(0); 
				if (iter != null) {
					while (iter.hasNext()) {
						String[] datos = (String[]) iter.next();
						body += "·" +datos[4] + "| Cantidad: " + datos [5] + "| Precio Unitario:" + datos[6] + "\n";
						total = total.add(new BigDecimal (datos[6])); 
						
					}
				}
				total = total.add(new BigDecimal(10));
				body += "								Total a abonar: " + total + "\n" ;
				body += "Método de pago: " + this.formadepago + "\n";
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosMarketRegistroDireccion()" + e);
		}
	}

	private boolean setEstadoPedido() {
		boolean retorno = false;
		try {

			Stock market = Common.getStock();
			String salida = market.marketPedidosCabeSetEstado(this.idpedicabe,
					new BigDecimal(1), this.idempresa, this.usuarioact);

			if (!(retorno = salida.equalsIgnoreCase("OK"))) {
				log.error("setEstadoPedido(): " + salida);
			}

		} catch (Exception e) {
			log.error(":" + e);
		}

		return retorno;
	}

	public boolean ejecutarValidacion() {
		try {

			BigDecimal orden = new BigDecimal(request.getSession()
					.getAttribute("ordenCarrito") == null ? "-1" : request
					.getSession().getAttribute("ordenCarrito").toString());

			this.email = (String) request.getSession().getAttribute(
					"emailCarrito");
			this.pass = (String) request.getSession().getAttribute(
					"passCarrito");

			if (orden == null || orden.compareTo(new BigDecimal(0)) < 1) {
				request
						.getSession()
						.setAttribute("mensaje",
								"Su carrito se encuentra vacio, por favor selecciones algun producto.");
				response.sendRedirect("marketAviso.jsp");
			}

			this.getDatosPedido();

			if (!this.enviarPedido.equalsIgnoreCase("")) {

				if (setEstadoPedido()) {
					this.mensaje = this.nombreFact + " " + this.apellidoFact
							+ ", su pedido # " + orden
							+ " ha sido recibido con exito.";
					this.mensaje += "Pronto recibira por e-mail una copia con el detalle de su compra. ";
					request.getSession().setAttribute("mensaje", this.mensaje);
					response.sendRedirect("marketAviso.jsp");
					String subject = "Detalle del pedido #" + orden
							+ " hecho a Syswarp-Market.";
					Common.sendMail(subject, body, this.email, "- SysWarp Market -");
				} else
					this.mensaje = "No se proceso su pedido  # " + orden
							+ ", por favor vuelva a intentarlo.";

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

	public BigDecimal getIdpedicabe() {
		return idpedicabe;
	}

	public void setIdpedicabe(BigDecimal idpedicabe) {
		this.idpedicabe = idpedicabe;
	}

	public List getListDetallePedido() {
		return listDetallePedido;
	}

	public void setListDetallePedido(List listDetallePedido) {
		this.listDetallePedido = listDetallePedido;
	}

	public String getFormadepago() {
		return formadepago;
	}

	public void setFormadepago(String formadepago) {
		this.formadepago = formadepago;
	}

	public BigDecimal getIdformadepago() {
		return idformadepago;
	}

	public void setIdformadepago(BigDecimal idformadepago) {
		this.idformadepago = idformadepago;
	}

	public String getEnviarPedido() {
		return enviarPedido;
	}

	public void setEnviarPedido(String enviarPedido) {
		this.enviarPedido = enviarPedido;
	}

}
