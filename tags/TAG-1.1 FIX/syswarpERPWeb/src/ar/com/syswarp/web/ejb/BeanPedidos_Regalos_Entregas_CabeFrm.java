/* 
   javabean para la entidad (Formulario): pedidos_Regalos_Entregas_Cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Nov 16 09:07:57 GMT-03:00 2009 
   
   Para manejar la pagina: pedidos_Regalos_Entregas_CabeFrm.jsp
      
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

public class BeanPedidos_Regalos_Entregas_CabeFrm implements SessionBean,
		Serializable {

	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanPedidos_Regalos_Entregas_CabeFrm.class);

	private String validar = "";

	private BigDecimal idpedido_regalos_entrega_cabe = new BigDecimal(-1);

	private BigDecimal idpedido_regalos_cabe = new BigDecimal(-1);

	// Inicializar todas las entregas como pendientes.
	private BigDecimal idestado = new BigDecimal(1);

	private BigDecimal idcliente = new BigDecimal(-1);

	private String razon = "";

	private BigDecimal idestadopedido_ppal = new BigDecimal(-1);

	private String estadopedido_ppal = "";

	private BigDecimal idprioridadpedido_ppal = new BigDecimal(-1);

	private String prioridadpedido_ppal = "";

	private String obsarmadopedido_ppal = "";

	private String obsentregapedido_ppal = "";

	private String fechapedido_ppal = null;

	private BigDecimal idsucursal = new BigDecimal(-1);

	private BigDecimal idsucuclie = new BigDecimal(-1);

	private String fechapedido = "";

	private BigDecimal idexpreso = new BigDecimal(-1);

	private String expreso = "";

	private String obsarmado = "";

	private String obsentrega = "";

	private BigDecimal idprioridad = new BigDecimal(-1);

	private BigDecimal idzona = new BigDecimal(-1);

	private String zona = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listPrioridades = new ArrayList();

	private List listDisponiblePedido = new ArrayList();

	// DOMICILIO

	private String domicilioReadonly = "";

	private BigDecimal iddomicilio = new BigDecimal(-1);

	private String calle = "";

	private String nro = "";

	private String piso = "";

	private String depto = "";

	private String cpa = "";

	private String postal = "";

	private String contacto = "";

	private String cargocontacto = "";

	private String telefonos = "";

	private String celular = "";

	private String fax = "";

	private String web = "";

	private BigDecimal idanexolocalidad = new BigDecimal(-1);

	private BigDecimal idcobrador = new BigDecimal(-1);

	private String cobrador = "";

	private BigDecimal idlocalidad = new BigDecimal(-1);

	private String localidad = "";

	private BigDecimal idprovincia = new BigDecimal(-1);

	private String provincia = "";

	// < --

	private boolean primeraCarga = true;

	private String[] codigo_st = null;

	private String[] cant_entrega = null;

	private String[] cant_disponible = null;

	private String[] codigo_dt = null;

	public BeanPedidos_Regalos_Entregas_CabeFrm() {
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
			Clientes pedidos_Regalos_Entregas_Cabe = Common.getClientes();
			java.sql.Timestamp fpedido = (java.sql.Timestamp) Common
					.setObjectToStrOrTime(fechapedido, "StrToJSTs");

			String[] resultado = null;

			if (this.accion.equalsIgnoreCase("alta")) {

				resultado = pedidos_Regalos_Entregas_Cabe
						.pedidosRegalosEntregasCreate(
								this.idpedido_regalos_cabe, this.idestado,
								this.idcliente, this.idsucursal,
								this.idsucuclie, fpedido, this.idexpreso,
								this.obsarmado, this.obsentrega,
								this.idprioridad, this.idzona, this.codigo_st,
								this.cant_entrega, this.calle, this.nro,
								this.piso, this.depto, this.cpa, this.postal,
								this.contacto, this.cargocontacto,
								this.telefonos, this.celular, this.fax,
								this.web, this.idanexolocalidad,
								this.codigo_dt, this.idempresa, this.usuarioalt);

				if (Common.setNotNull(resultado[0]).equalsIgnoreCase("OK")) {
					this.idpedido_regalos_entrega_cabe = new BigDecimal(
							resultado[1]);
					this.idsucuclie = new BigDecimal(resultado[2]);
					this.mensaje = "Entrega Nro.: "
							+ this.idpedido_regalos_entrega_cabe
							+ " generada correctamente.";

				} else
					this.mensaje = resultado[0];

				// this.mensaje = "Ejecutando alta.";

				// } else if (this.accion.equalsIgnoreCase("modificacion")) {
				// this.mensaje = pedidos_Regalos_Entregas_Cabe
				// .pedidos_Regalos_Entregas_CabeUpdate(
				// this.idpedido_regalos_entrega_cabe,
				// this.idpedido_regalos_cabe, this.idestado,
				// this.idcliente, this.idsucursal,
				// this.idsucuclie, (java.sql.Timestamp) Common
				// .setObjectToStrOrTime(this.fechapedido,
				// "StrToJSTs"), this.idexpreso,
				// this.obsarmado, this.obsentrega,
				// this.idprioridad, this.idzona, this.idempresa,
				// this.usuarioact);
				// } else if (this.accion.equalsIgnoreCase("baja")) {
				// this.mensaje = pedidos_Regalos_Entregas_Cabe
				// .pedidos_Regalos_Entregas_CabeDelete(
				// this.idpedido_regalos_entrega_cabe,
				// this.idempresa);
			}

			this.listDisponiblePedido = Common.getClientes()
					.getPedidosRegalosEntregasDisponible(
							this.idpedido_regalos_cabe, this.idempresa);

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosPedidos_Regalos_Entregas_Cabe() {
		try {
			Clientes clie = Common.getClientes();
			List listPedidos_Regalos_Entregas_Cabe = clie
					.getPedidos_Regalos_Entregas_CabePK(
							this.idpedido_regalos_entrega_cabe, this.idempresa);
			Iterator iterPedidos_Regalos_Entregas_Cabe = listPedidos_Regalos_Entregas_Cabe
					.iterator();
			if (iterPedidos_Regalos_Entregas_Cabe.hasNext()) {
				String[] uCampos = (String[]) iterPedidos_Regalos_Entregas_Cabe
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idpedido_regalos_entrega_cabe = new BigDecimal(uCampos[0]);
				this.idpedido_regalos_cabe = new BigDecimal(uCampos[1]);
				this.idestado = new BigDecimal(uCampos[2]);
				this.idcliente = new BigDecimal(uCampos[3]);
				this.idsucursal = new BigDecimal(uCampos[4]);
				this.idsucuclie = new BigDecimal(uCampos[5]);
				this.fechapedido = uCampos[6];
				this.idexpreso = new BigDecimal(uCampos[7]);
				this.obsarmado = uCampos[8];
				this.obsentrega = uCampos[9];
				this.idprioridad = new BigDecimal(uCampos[10]);
				this.idzona = new BigDecimal(uCampos[11]);

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosPedidos_Regalos_Entregas_Cabe()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			this.getDatosPedidoRegalos();

			this.listDisponiblePedido = Common.getClientes()
					.getPedidosRegalosEntregasDisponible(
							this.idpedido_regalos_cabe, this.idempresa);

			this.listPrioridades = Common.getClientes()
					.getPedidosPrioridadesAll(50, 0);

			boolean existeEntrega = false;

			if (!this.volver.equalsIgnoreCase("")) {
				response
						.sendRedirect("pedidosRegalosEntregasAdministrar.jsp?idpedido_regalos_cabe="
								+ this.idpedido_regalos_cabe);
				return true;
			}

			if (!this.validar.equalsIgnoreCase("")) {

				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (this.codigo_st == null || this.codigo_st.length == 0) {

						this.mensaje = "No existen productos asociados al pedido.";
						return false;

					}

					if (idestado == null || idestado.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo estado ";
						return false;
					}
					if (idcliente == null || idestado.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo cliente ";
						return false;
					}

					if (!Common.isFormatoFecha(fechapedido)
							|| !Common.isFechaValida(fechapedido)) {
						this.mensaje = "No se puede dejar vacio el campo fecha pedido. ";
						return false;
					}
					if (idprioridad == null || this.idprioridad.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo prioridad. ";
						return false;
					}

					// Datos de domicilio

					if (Common.setNotNull(this.calle).equals("")) {
						this.mensaje = "Ingrese calle valida. ";
						return false;
					}

					if (Common.setNotNull(this.nro).equals("")
							|| !Common.esEntero(this.nro)) {
						this.mensaje = "Ingrese un nro. de calle valido. ";
						return false;
					}

					if (this.idanexolocalidad == null
							|| this.idanexolocalidad.longValue() < 1
							|| this.idlocalidad == null
							|| this.idlocalidad.longValue() < 1) {
						this.mensaje = "Ingrese localidad valida. ";
						return false;
					}

					if (Common.setNotNull(this.contacto).equals("")) {
						this.mensaje = "Ingrese contacto valido. ";
						return false;
					}

					// EJV - 20101129 - Mantis 623
					// if (Common.setNotNull(this.cargocontacto).equals("")) {
					// this.mensaje = "Ingrese cargo contacto valido. ";
					// return false;
					// }

					for (int k = 0; k < this.codigo_st.length; k++) {

						if (!Common.esEntero(this.cant_entrega[k])) {

							this.mensaje = "Ingrese valores numericos validos para cantidad entrega en producto: "
									+ this.codigo_st[k] + ".";
							return false;
						} else if (!existeEntrega) {

							if (Double.parseDouble(this.cant_entrega[k]) > 0)
								existeEntrega = true;

						}

						if (existeEntrega
								&& Double.parseDouble(this.cant_entrega[k]) > Double
										.parseDouble(this.cant_disponible[k])) {

							this.mensaje = "Cantidad ingresada para producto : "
									+ this.codigo_st[k]
									+ ", supera la cantidad disponible: "
									+ this.cant_disponible[k];
							return false;
						}

					}

					if (!existeEntrega) {
						this.mensaje = "Ingrese cantidad de entrega, al menos para alguno de los articulos.";
						return false;
					}

				}

				this.ejecutarSentenciaDML();

				if (this.idsucuclie != null && this.idsucuclie.longValue() > 0) {
					this.domicilioReadonly = "readonly";
				}

			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosPedidos_Regalos_Entregas_Cabe();
				}
			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}

		return true;
	}

	private void getDatosPedidoRegalos() {

		try {

			List listDatosPedidoPpal = Common.getClientes()
					.getPedidosRegalosCabeHeader(this.idpedido_regalos_cabe,
							this.idempresa);

			if (listDatosPedidoPpal != null && !listDatosPedidoPpal.isEmpty()) {

				String[] datos = (String[]) listDatosPedidoPpal.get(0);

				this.idcliente = new BigDecimal(datos[4]);
				this.razon = datos[5];
				this.idestadopedido_ppal = new BigDecimal(datos[2]);
				this.estadopedido_ppal = datos[3];
				this.fechapedido_ppal = (String) Common.setObjectToStrOrTime(
						java.sql.Timestamp.valueOf(datos[6]), "JSTsToStr");
				this.idprioridadpedido_ppal = new BigDecimal(datos[9]);
				this.obsarmadopedido_ppal = datos[7];
				this.obsentregapedido_ppal = datos[8];
				this.prioridadpedido_ppal = datos[10];

				if (isPrimeraCarga()) {

					this.idprioridad = this.idprioridadpedido_ppal;
					this.fechapedido = this.fechapedido_ppal;
					this.obsarmado = this.obsarmadopedido_ppal;
					this.obsentrega = this.obsentregapedido_ppal;
				}

			}

		} catch (Exception e) {
			log.error("getDatosPedidoRegalos(): " + e);
		}

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
	public BigDecimal getIdpedido_regalos_entrega_cabe() {
		return idpedido_regalos_entrega_cabe;
	}

	public void setIdpedido_regalos_entrega_cabe(
			BigDecimal idpedido_regalos_entrega_cabe) {
		this.idpedido_regalos_entrega_cabe = idpedido_regalos_entrega_cabe;
	}

	public BigDecimal getIdpedido_regalos_cabe() {
		return idpedido_regalos_cabe;
	}

	public void setIdpedido_regalos_cabe(BigDecimal idpedido_regalos_cabe) {
		this.idpedido_regalos_cabe = idpedido_regalos_cabe;
	}

	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public BigDecimal getIdestadopedido_ppal() {
		return idestadopedido_ppal;
	}

	public void setIdestadopedido_ppal(BigDecimal idestadopedido_ppal) {
		this.idestadopedido_ppal = idestadopedido_ppal;
	}

	public String getEstadopedido_ppal() {
		return estadopedido_ppal;
	}

	public void setEstadopedido_ppal(String estadopedido_ppal) {
		this.estadopedido_ppal = estadopedido_ppal;
	}

	public BigDecimal getIdprioridadpedido_ppal() {
		return idprioridadpedido_ppal;
	}

	public void setIdprioridadpedido_ppal(BigDecimal idprioridadpedido_ppal) {
		this.idprioridadpedido_ppal = idprioridadpedido_ppal;
	}

	public String getPrioridadpedido_ppal() {
		return prioridadpedido_ppal;
	}

	public void setPrioridadpedido_ppal(String prioridadpedido_ppal) {
		this.prioridadpedido_ppal = prioridadpedido_ppal;
	}

	public String getObsarmadopedido_ppal() {
		return obsarmadopedido_ppal;
	}

	public void setObsarmadopedido_ppal(String obsarmadopedido_ppal) {
		this.obsarmadopedido_ppal = obsarmadopedido_ppal;
	}

	public String getObsentregapedido_ppal() {
		return obsentregapedido_ppal;
	}

	public void setObsentregapedido_ppal(String obsentregapedido_ppal) {
		this.obsentregapedido_ppal = obsentregapedido_ppal;
	}

	public String getFechapedido_ppal() {
		return fechapedido_ppal;
	}

	public void setFechapedido_ppal(String fechapedido_ppal) {
		this.fechapedido_ppal = fechapedido_ppal;
	}

	public BigDecimal getIdsucursal() {
		return idsucursal;
	}

	public void setIdsucursal(BigDecimal idsucursal) {
		this.idsucursal = idsucursal;
	}

	public BigDecimal getIdsucuclie() {
		return idsucuclie;
	}

	public void setIdsucuclie(BigDecimal idsucuclie) {
		this.idsucuclie = idsucuclie;
	}

	public String getFechapedido() {
		return fechapedido;
	}

	public void setFechapedido(String fechapedido) {
		this.fechapedido = fechapedido;
	}

	public String getExpreso() {
		return expreso;
	}

	public void setExpreso(String expreso) {
		this.expreso = expreso;
	}

	public BigDecimal getIdexpreso() {
		return idexpreso;
	}

	public void setIdexpreso(BigDecimal idexpreso) {
		this.idexpreso = idexpreso;
	}

	public String getObsarmado() {
		return obsarmado;
	}

	public void setObsarmado(String obsarmado) {
		this.obsarmado = obsarmado;
	}

	public String getObsentrega() {
		return obsentrega;
	}

	public void setObsentrega(String obsentrega) {
		this.obsentrega = obsentrega;
	}

	public BigDecimal getIdprioridad() {
		return idprioridad;
	}

	public void setIdprioridad(BigDecimal idprioridad) {
		this.idprioridad = idprioridad;
	}

	public BigDecimal getIdzona() {
		return idzona;
	}

	public void setIdzona(BigDecimal idzona) {
		this.idzona = idzona;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
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

	public List getListPrioridades() {
		return listPrioridades;
	}

	public void setListPrioridades(List listPrioridades) {
		this.listPrioridades = listPrioridades;
	}

	public List getListDisponiblePedido() {
		return listDisponiblePedido;
	}

	public void setListDisponiblePedido(List listDisponiblePedido) {
		this.listDisponiblePedido = listDisponiblePedido;
	}

	// DOMICILIO --- >

	public BigDecimal getIddomicilio() {
		return iddomicilio;
	}

	public String getDomicilioReadonly() {
		return domicilioReadonly;
	}

	public void setDomicilioReadonly(String domicilioReadonly) {
		this.domicilioReadonly = domicilioReadonly;
	}

	public void setIddomicilio(BigDecimal iddomicilio) {
		this.iddomicilio = iddomicilio;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNro() {
		return nro;
	}

	public void setNro(String nro) {
		this.nro = nro;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public String getCpa() {
		return cpa;
	}

	public void setCpa(String cpa) {
		this.cpa = cpa;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getCargocontacto() {
		return cargocontacto;
	}

	public void setCargocontacto(String cargocontacto) {
		this.cargocontacto = cargocontacto;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public BigDecimal getIdanexolocalidad() {
		return idanexolocalidad;
	}

	public void setIdanexolocalidad(BigDecimal idanexolocalidad) {
		this.idanexolocalidad = idanexolocalidad;
	}

	public BigDecimal getIdcobrador() {
		return idcobrador;
	}

	public void setIdcobrador(BigDecimal idcobrador) {
		this.idcobrador = idcobrador;
	}

	public String getCobrador() {
		return cobrador;
	}

	public void setCobrador(String cobrador) {
		this.cobrador = cobrador;
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

	public BigDecimal getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(BigDecimal idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	// < ---

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public String[] getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String[] codigo_st) {
		this.codigo_st = codigo_st;
	}

	public String[] getCant_entrega() {
		return cant_entrega;
	}

	public void setCant_entrega(String[] cant_entrega) {
		this.cant_entrega = cant_entrega;
	}

	public String[] getCant_disponible() {
		return cant_disponible;
	}

	public void setCant_disponible(String[] cant_disponible) {
		this.cant_disponible = cant_disponible;
	}

	// 20100827 - EJV - add pedidos_regalos_entregas_deta.codigo_dt -->
	public String[] getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(String[] codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	// <--

}
