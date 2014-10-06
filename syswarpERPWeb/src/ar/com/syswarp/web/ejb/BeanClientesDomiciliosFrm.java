/* 
   javabean para la entidad (Formulario): clientesDomicilios
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jan 22 17:07:40 GMT-03:00 2009 
   
   Para manejar la pagina: clientesDomiciliosFrm.jsp
      
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

public class BeanClientesDomiciliosFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesDomiciliosFrm.class);

	private String validar = "";

	private BigDecimal iddomicilio = new BigDecimal(-1);

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private BigDecimal idtipodomicilio = new BigDecimal(-1);

	private String esdefault = "N";

	private String calle = "";

	private String nro = "";

	private String piso = "";

	private String depto = "";

	private BigDecimal idlocalidad = new BigDecimal(-1);

	private String localidad = "";

	private BigDecimal idzona = new BigDecimal(-1);

	private String zona = "";

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

	private BigDecimal idvendedor = new BigDecimal(-1);

	private BigDecimal idexpreso = new BigDecimal(-1);

	private String expreso = "";

	private String obsentrega = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal idcondicion = new BigDecimal(-1);// nando

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	// --

	private List clientesDomiciliosList = new ArrayList();

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	//

	private List listaTiposDomicilios = new ArrayList();

	public BeanClientesDomiciliosFrm() {
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
			Clientes clientesDomicilios = Common.getClientes();

			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clientesDomicilios.clientesDomiciliosCreate(
						this.idcliente, this.idtipodomicilio, this.esdefault,
						this.calle, this.nro, this.piso, this.depto,
						this.idlocalidad, this.cpa, this.postal, this.contacto,
						this.cargocontacto, this.telefonos, this.celular,
						this.fax, this.web, this.idanexolocalidad,
						this.idcobrador, this.idvendedor, this.obsentrega,
						this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientesDomicilios.clientesDomiciliosUpdate(
						this.iddomicilio, this.idcliente, this.idtipodomicilio,
						this.esdefault, this.calle, this.nro, this.piso,
						this.depto, this.idlocalidad, this.cpa, this.postal,
						this.contacto, this.cargocontacto, this.telefonos,
						this.celular, this.fax, this.web,
						this.idanexolocalidad, this.idcobrador,
						this.idvendedor, this.obsentrega, this.idempresa,
						this.usuarioact);

			if (this.mensaje.equalsIgnoreCase("OK")) {
				this.mensaje = "Domicilio generado correctamente.";
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}

		this.getDomicilioosCliente();

	}

	public void getDatosClientesDomicilios() {
		try {
			Clientes clientesDomicilios = Common.getClientes();
			List listClientesDomicilios = clientesDomicilios
					.getClientesDomiciliosPK(this.iddomicilio, this.idempresa);
			Iterator iterClientesDomicilios = listClientesDomicilios.iterator();
			if (iterClientesDomicilios.hasNext()) {
				String[] uCampos = (String[]) iterClientesDomicilios.next();
				// TODO: Constructores para cada tipo de datos
				this.iddomicilio = new BigDecimal(uCampos[0]);
				this.idcliente = new BigDecimal(uCampos[1]);
				this.idtipodomicilio = new BigDecimal(uCampos[2]);
				this.esdefault = uCampos[3];
				this.calle = uCampos[4];
				this.nro = uCampos[5];
				this.piso = uCampos[6];
				this.depto = uCampos[7];
				this.idlocalidad = new BigDecimal(uCampos[8]);
				this.cpa = uCampos[9];
				this.postal = uCampos[10];
				this.contacto = uCampos[11];
				this.cargocontacto = uCampos[12];
				this.telefonos = uCampos[13];
				this.celular = uCampos[14];
				this.fax = uCampos[15];
				this.web = uCampos[16];
				this.idanexolocalidad = new BigDecimal(uCampos[17]);
				this.idcobrador = new BigDecimal(uCampos[18]);
				this.idvendedor = new BigDecimal(uCampos[19]);
				this.idempresa = new BigDecimal(uCampos[20]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesDomicilios()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			Clientes clientesDomicilios = Common.getClientes();

			this.listaTiposDomicilios = clientesDomicilios
					.getClientesTiposDomiciliosAll(50, 0, this.idempresa);

			this.getDomicilioosCliente();

			if (!this.validar.equalsIgnoreCase("")) {

				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (idtipodomicilio == null
							|| idtipodomicilio.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo idtipodomicilio ";
						return false;
					}

					if (Common.setNotNull(calle).trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo calle ";
						return false;
					}
					if (idlocalidad == null || idlocalidad.longValue() < 1) {
						this.mensaje = "Seleccione Localidad. ";
						return false;
					}
					if (Common.setNotNull(contacto).trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo contacto ";
						return false;
					}

					/*-----------pedido amelia validacion que ingrese siempre un cobrador se hicieron
					 *           varias validaciones. 05/11/09 FGP 
					 */
					if (this.idcobrador.longValue() == -1) {
						this.mensaje = "Debe ingresar un Cobrador.";
						return false;
					}
					// ----------------------------------------------------------------------------------------

					if (Common.setNotNull(this.telefonos).trim().length() < 6
							&& Common.setNotNull(this.celular).trim().length() < 8) {

						this.mensaje = "Es necesario que ingrese telefono o celular.";
						return false;

					}

					// 2. len 0 para campos nulos

				}

				this.ejecutarSentenciaDML();

			} else {

			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private void getDomicilioosCliente() {

		try {

			this.totalRegistros = Common.getClientes().getTotalEntidadFiltro(
					"clientesdomicilios",
					" WHERE idcliente = " + this.idcliente, this.idempresa);
			this.totalPaginas = (this.totalRegistros / this.limit) + 1;
			if (this.totalPaginas < this.paginaSeleccion)
				this.paginaSeleccion = this.totalPaginas;
			this.offset = (this.paginaSeleccion - 1) * this.limit;

			if (this.totalRegistros == this.limit) {
				this.offset = 0;
				this.totalPaginas = 1;
			}

			this.clientesDomiciliosList = Common.getClientes()
					.getClientesDomiciliosCliente(this.limit, this.offset,
							this.idcliente, this.idempresa);

		} catch (Exception e) {

			log.info("getDomicilioosCliente(): " + e);

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
	public BigDecimal getIddomicilio() {
		return iddomicilio;
	}

	public void setIddomicilio(BigDecimal iddomicilio) {
		this.iddomicilio = iddomicilio;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public BigDecimal getIdtipodomicilio() {
		return idtipodomicilio;
	}

	public void setIdtipodomicilio(BigDecimal idtipodomicilio) {
		this.idtipodomicilio = idtipodomicilio;
	}

	public String getEsdefault() {
		return esdefault;
	}

	public void setEsdefault(String esdefault) {
		this.esdefault = esdefault;
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

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
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

	public BigDecimal getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(BigDecimal idvendedor) {
		this.idvendedor = idvendedor;
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

	public List getClientesDomiciliosList() {
		return clientesDomiciliosList;
	}

	public void setClientesDomiciliosList(List clientesDomiciliosList) {
		this.clientesDomiciliosList = clientesDomiciliosList;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public long getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(long totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public long getPaginaSeleccion() {
		return paginaSeleccion;
	}

	public void setPaginaSeleccion(long paginaSeleccion) {
		this.paginaSeleccion = paginaSeleccion;
	}

	public List getListaTiposDomicilios() {
		return listaTiposDomicilios;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
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

	public String getCobrador() {
		return cobrador;
	}

	public void setCobrador(String cobrador) {
		this.cobrador = cobrador;
	}

	public BigDecimal getIdexpreso() {
		return idexpreso;
	}

	public void setIdexpreso(BigDecimal idexpreso) {
		this.idexpreso = idexpreso;
	}

	public String getExpreso() {
		return expreso;
	}

	public void setExpreso(String expreso) {
		this.expreso = expreso;
	}

	public String getObsentrega() {
		return obsentrega;
	}

	public void setObsentrega(String obsentrega) {
		this.obsentrega = obsentrega;
	}

	public BigDecimal getIdcondicion() {
		return idcondicion;
	}

	public void setIdcondicion(BigDecimal idcondicion) {
		this.idcondicion = idcondicion;
	}
}
