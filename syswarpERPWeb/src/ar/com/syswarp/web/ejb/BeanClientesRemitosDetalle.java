/* 
   javabean para la entidad: pedidos_Deta
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Sep 04 16:29:20 GMT-03:00 2008 
   
   Para manejar la pagina: pedidos_DetaAbm.jsp
      
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

public class BeanClientesRemitosDetalle implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanClientesRemitosDetalle.class);
	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List listDetalleRemito = new ArrayList();

	private List listCabeceraRemito = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private BigDecimal idremitocliente = new BigDecimal(-1);

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private String sucursal = "";

	private String remitocliente = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	//

	private String calle = "";

	private String nro = "";

	private String piso = "";

	private String depto = "";

	private String cpa = "";

	private String postal = "";

	private String localidad = "";

	private String provincia = "";

	private String tipodocumento = "";

	private String nrodocumento = "";

	private String bultos = "0";

	private String telefonos = "";

	private String fecharemito = "";

	private String contacto = "";

	//

	private String tipopedido = "N";

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public BeanClientesRemitosDetalle() {
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
		Clientes pedidos_Deta = Common.getClientes();

		try {

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			// this.totalRegistros = pedidos_Deta.getTotalEntidadFiltro(entidad,
			// filtro, this.idempresa);
			this.totalPaginas = (this.totalRegistros / this.limit) + 1;
			if (this.totalPaginas < this.paginaSeleccion)
				this.paginaSeleccion = this.totalPaginas;
			this.offset = (this.paginaSeleccion - 1) * this.limit;
			if (this.totalRegistros == this.limit) {
				this.offset = 0;
				this.totalPaginas = 1;
			}

			/*
			 * cl.idcliente, cl.razon, td.tipodocumento, cl.nrodocumento,
			 * cd.calle, cd.nro, cd.piso, cd.depto, lo.cpostal, lo.localidad,
			 * pr.provincia, cd.telefonos, cr.fecharemito, bultos,"
			 * LPAD(cr.nrosucursal::varchar, 4, '0') AS nrosucursal,
			 * LPAD(cr.nroremitocliente::varchar, 8, '0') AS nroremitocliente
			 */

			this.listCabeceraRemito = pedidos_Deta.getClientesRemitosCabecera(
					this.idremitocliente, this.tipopedido, this.idempresa);

			if (this.listCabeceraRemito != null
					&& !this.listCabeceraRemito.isEmpty()) {

				String[] datosCabecera = (String[]) this.listCabeceraRemito
						.get(0);

				this.tipodocumento = datosCabecera[2];
				this.nrodocumento = datosCabecera[3];
				this.calle = datosCabecera[4];
				this.nro = datosCabecera[5];
				this.piso = datosCabecera[6];
				this.depto = datosCabecera[7];
				this.postal = datosCabecera[8];
				this.localidad = datosCabecera[9];
				this.provincia = datosCabecera[10];
				this.telefonos = datosCabecera[11];
				this.contacto = datosCabecera[12];
				this.fecharemito = datosCabecera[13];
				this.bultos = datosCabecera[14];

			}

			this.listDetalleRemito = pedidos_Deta.getClientesRemitosDetalle(
					this.idremitocliente, this.tipopedido, this.idempresa);

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}

		return true;

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

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BigDecimal getIdremitocliente() {
		return idremitocliente;
	}

	public void setIdremitocliente(BigDecimal idremitocliente) {
		this.idremitocliente = idremitocliente;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getRemitocliente() {
		return remitocliente;
	}

	public void setRemitocliente(String remitocliente) {
		this.remitocliente = remitocliente;
	}

	public List getListDetalleRemito() {
		return listDetalleRemito;
	}

	public void setListDetalleRemito(List listDetalleRemito) {
		this.listDetalleRemito = listDetalleRemito;
	}

	public List getListCabeceraRemito() {
		return listCabeceraRemito;
	}

	public void setListCabeceraRemito(List listCabeceraRemito) {
		this.listCabeceraRemito = listCabeceraRemito;
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

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public String getBultos() {
		return bultos;
	}

	public void setBultos(String bultos) {
		this.bultos = bultos;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public String getFecharemito() {
		return fecharemito;
	}

	public void setFecharemito(String fecharemito) {
		this.fecharemito = fecharemito;
	}

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

}
