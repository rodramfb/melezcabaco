/* 
   javabean para la entidad: pedidos_Regalos_Entregas_Deta
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Nov 13 10:01:11 GMT-03:00 2009 
   
   Para manejar la pagina: pedidos_Regalos_Entregas_DetaAbm.jsp
      
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanPedidos_Regalos_Entregas_Deta implements SessionBean,
		Serializable {

	static Logger log = Logger
			.getLogger(BeanPedidos_Regalos_Entregas_Deta.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal idpedido_regalos_entrega_cabe = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List pedidos_Regalos_Entregas_DetaList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idpedido_regalos_entrega_deta;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String estado = "";

	// DOMICILIO

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

	public BeanPedidos_Regalos_Entregas_Deta() {
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

	public boolean ejecutarValidacion() {
		RequestDispatcher dispatcher = null;
		Clientes clie = Common.getClientes();
		try {

			List listaHeader = clie.getPedidos_Regalos_Entregas_CabePK(
					this.idpedido_regalos_entrega_cabe, this.idempresa);

			if (listaHeader != null && !listaHeader.isEmpty()) {

				String[] datos = (String[]) listaHeader.get(0);

				/*
				 * 
				 * 0.idpedido_regalos_entrega_cabe, 1.idpedido_regalos_cabe, "
				 * 2.idestado, 3.estado, 4.idsucursal, 5.fechapedido,
				 * 6.idsucuclie 7.idexpreso, 8.obsarmado, 9.obsentrega,
				 * 10.idprioridad 11.idzona, 12.calle, 13.nro, 14.piso,
				 * 15.depto, 16.cpa, 17.postal, 18.contacto, 19.cargocontacto,
				 * 20.localidad, 21.provincia, 22.telefonos, 23.celular, 24.fax,
				 * 25.web,
				 */

				this.estado = datos[3];
				this.calle = datos[12];
				this.nro = datos[13];
				this.depto = datos[14];
				this.piso = datos[15];
				this.cpa = datos[16];
				this.postal = datos[17];
				this.contacto = datos[18];
				this.cargocontacto = datos[19];
				this.localidad = datos[20];
				this.provincia = datos[21];
				this.telefonos = datos[22];
				this.celular = datos[23];
				this.fax = datos[24];
				this.web = datos[25];

				this.pedidos_Regalos_Entregas_DetaList = clie
						.getPedidos_Regalos_Entregas_DetaOne(
								this.idpedido_regalos_entrega_cabe,
								this.idempresa);

			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BigDecimal getIdpedido_regalos_entrega_cabe() {
		return idpedido_regalos_entrega_cabe;
	}

	public void setIdpedido_regalos_entrega_cabe(
			BigDecimal idpedido_regalos_entrega_cabe) {
		this.idpedido_regalos_entrega_cabe = idpedido_regalos_entrega_cabe;
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

	public void setTotalRegistros(long total) {
		this.totalRegistros = total;
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

	public List getPedidos_Regalos_Entregas_DetaList() {
		return pedidos_Regalos_Entregas_DetaList;
	}

	public void setPedidos_Regalos_Entregas_DetaList(
			List pedidos_Regalos_Entregas_DetaList) {
		this.pedidos_Regalos_Entregas_DetaList = pedidos_Regalos_Entregas_DetaList;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String buscar) {
		this.ocurrencia = buscar;
	}

	public BigDecimal getIdpedido_regalos_entrega_deta() {
		return idpedido_regalos_entrega_deta;
	}

	public void setIdpedido_regalos_entrega_deta(
			BigDecimal idpedido_regalos_entrega_deta) {
		this.idpedido_regalos_entrega_deta = idpedido_regalos_entrega_deta;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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

	public static Logger getLog() {
		return log;
	}

	public SessionContext getContext() {
		return context;
	}

	public boolean isBuscar() {
		return buscar;
	}

	public String getEstado() {
		return estado;
	}

	public BigDecimal getIddomicilio() {
		return iddomicilio;
	}

	public String getCalle() {
		return calle;
	}

	public String getNro() {
		return nro;
	}

	public String getPiso() {
		return piso;
	}

	public String getDepto() {
		return depto;
	}

	public String getCpa() {
		return cpa;
	}

	public String getPostal() {
		return postal;
	}

	public String getContacto() {
		return contacto;
	}

	public String getCargocontacto() {
		return cargocontacto;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public String getCelular() {
		return celular;
	}

	public String getFax() {
		return fax;
	}

	public String getWeb() {
		return web;
	}

	public BigDecimal getIdanexolocalidad() {
		return idanexolocalidad;
	}

	public BigDecimal getIdcobrador() {
		return idcobrador;
	}

	public String getCobrador() {
		return cobrador;
	}

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public String getLocalidad() {
		return localidad;
	}

	public BigDecimal getIdprovincia() {
		return idprovincia;
	}

	public String getProvincia() {
		return provincia;
	}

}
