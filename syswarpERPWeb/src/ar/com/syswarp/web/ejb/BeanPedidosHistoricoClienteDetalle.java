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

public class BeanPedidosHistoricoClienteDetalle implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanPedidosHistoricoClienteDetalle.class);
	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List pedidos_DetaList = new ArrayList();

	private List pedidos_CabeList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idpedido_deta;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private BigDecimal idpedido_cabe = new BigDecimal(-1);

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	//

	private String estado = "";

	private String calle = "";

	private String nro = "";

	private String piso = "";

	private String depto = "";

	private String cpa = "";

	private String postal = "";

	private String contacto = "";

	private String localidad = "";

	private String provincia = "";

	private String fechapedido = "";

	private String condicion = "";

	private String vendedor = "";

	private String expreso = "";

	private String comision = "";

	private String ordencompra = "";

	private String obsarmado = "";

	private String obsentrega = "";

	private String recargo1 = "";

	private String recargo2 = "";

	private String recargo3 = "";

	private String recargo4 = "";

	private String bonific1 = "";

	private String bonific2 = "";

	private String bonific3 = "";

	private String descri_lis = "";

	private String moneda = "";

	private String cotizacion = "";

	private String tipoiva = "";

	private String totaliva = "";

	private String prioridad = "";

	private String zona = "";

	private String tarjeta = "";

	private String cuotas = "";

	private String origenpedido = "";

	private String tipopedido = "";

	public BeanPedidosHistoricoClienteDetalle() {
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
		String filtro = " WHERE idpedido_cabe = "
				+ this.idpedido_cabe.toString() + " AND tipo = '"
				+ this.tipopedido + "'";

		String entidad = ""
				+ "(SELECT idpedido_cabe,  'N' AS tipo, idempresa FROM pedidos_deta "
				+ " UNION ALL "
				+ "SELECT idpedido_regalos_cabe , 'R' AS tipo, idempresa FROM pedidos_regalos_cabe"
				+ " UNION ALL "
				// 20110120 - EJV - Mantis - 661 -->
				+ "SELECT idpedido_regalos_entrega_cabe , 'E' AS tipo, idempresa FROM pedidos_regalos_entregas_cabe )"
				// <--
				+ " pedidos";

		try {

			if (this.accion.equalsIgnoreCase("volver")) {
				dispatcher = request
						.getRequestDispatcher("pedidosHistoricoCliente.jsp");
				dispatcher.forward(request, response);
				return true;

			}

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			this.totalRegistros = pedidos_Deta.getTotalEntidadFiltro(entidad,
					filtro, this.idempresa);
			this.totalPaginas = (this.totalRegistros / this.limit) + 1;
			if (this.totalPaginas < this.paginaSeleccion)
				this.paginaSeleccion = this.totalPaginas;
			this.offset = (this.paginaSeleccion - 1) * this.limit;
			if (this.totalRegistros == this.limit) {
				this.offset = 0;
				this.totalPaginas = 1;
			}

			if (this.tipopedido.equalsIgnoreCase("N"))

				this.pedidos_CabeList = pedidos_Deta
						.getPedidosHistoricoClientePK(this.idpedido_cabe,
								this.idcliente, this.idempresa);

			else if (this.tipopedido.equalsIgnoreCase("R"))

				this.pedidos_CabeList = pedidos_Deta
						.getPedidosRegalosHistoricoClientePK(
								this.idpedido_cabe, this.idcliente,
								this.idempresa);
			// 20110120 - EJV - Mantis - 661 -->
			else if (this.tipopedido.equalsIgnoreCase("E"))

				this.pedidos_CabeList = pedidos_Deta
						.getPedidosEntregasHistoricoClientePK(
								this.idpedido_cabe, this.idcliente,
								this.idempresa);
			// <--

			if (this.pedidos_CabeList != null
					&& !this.pedidos_CabeList.isEmpty()) {

				String[] datosCabecera = (String[]) this.pedidos_CabeList
						.get(0);

				/*
				 * idpedido_cabe- estado- idcliente- idsucursal- calle-
				 * direccion- piso- depto- cpa- postal- contacto- localidad-
				 * provincia- fechapedido- condicion- vendedor- expreso-
				 * comision- ordencompra- obsarmado- obsentrega- recargo1-
				 * recargo2- recargo3- recargo4- bonific1- bonific2- bonific3-
				 * descri_lis- moneda- cotizacion- tipoiva- totaliva- prioridad-
				 * zona- tarjeta- cuotas- origenpedido- idempresa- usuarioalt-
				 * usuarioact- fechaalt- fechaact-
				 */

				// this.idpedido_cabe= datosCabecera[0];
				this.estado = datosCabecera[1];
				// this.idcliente= datosCabecera[2];
				// this.idsucursal= datosCabecera[3];
				this.calle = datosCabecera[4];
				this.nro = datosCabecera[5];
				this.piso = datosCabecera[6];
				this.depto = datosCabecera[7];
				this.cpa = datosCabecera[8];
				this.postal = datosCabecera[9];
				this.contacto = datosCabecera[10];
				this.localidad = datosCabecera[11];
				this.provincia = datosCabecera[12];
				this.fechapedido = datosCabecera[13];
				this.condicion = datosCabecera[14];
				this.vendedor = datosCabecera[15];
				this.expreso = datosCabecera[16];
				this.comision = datosCabecera[17];
				this.ordencompra = datosCabecera[18];
				this.obsarmado = datosCabecera[19];
				this.obsentrega = datosCabecera[20];
				this.recargo1 = datosCabecera[21];
				this.recargo2 = datosCabecera[22];
				this.recargo3 = datosCabecera[23];
				this.recargo4 = datosCabecera[24];
				this.bonific1 = datosCabecera[25];
				this.bonific2 = datosCabecera[26];
				this.bonific3 = datosCabecera[27];
				this.descri_lis = datosCabecera[28];
				this.moneda = datosCabecera[29];
				this.cotizacion = datosCabecera[30];
				this.tipoiva = datosCabecera[31];
				this.totaliva = datosCabecera[32];
				this.prioridad = datosCabecera[33];
				this.zona = datosCabecera[34];
				this.tarjeta = datosCabecera[35];
				this.cuotas = datosCabecera[36];
				this.origenpedido = datosCabecera[37];

			}

			if (this.tipopedido.equalsIgnoreCase("N"))

				this.pedidos_DetaList = pedidos_Deta
						.getPedidosHistoricoDetalle(this.idpedido_cabe,
								this.idempresa);

			else if (this.tipopedido.equalsIgnoreCase("R"))

				this.pedidos_DetaList = pedidos_Deta
						.getPedidosRegalosHistoricoDetalle(this.idpedido_cabe,
								this.idempresa);

			// 20110120 - EJV - Mantis - 661 -->
			else if (this.tipopedido.equalsIgnoreCase("E"))

				this.pedidos_DetaList = pedidos_Deta
						.getPedidosEntregasHistoricoDetalle(this.idpedido_cabe,
								this.idempresa);

			// <--

			if (this.totalRegistros < 1)
				this.mensaje = "ERROR: Detalle inexistente.";

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

	public List getPedidos_DetaList() {
		return pedidos_DetaList;
	}

	public void setPedidos_DetaList(List pedidos_DetaList) {
		this.pedidos_DetaList = pedidos_DetaList;
	}

	public List getPedidos_CabeList() {
		return pedidos_CabeList;
	}

	public void setPedidos_CabeList(List pedidos_CabeList) {
		this.pedidos_CabeList = pedidos_CabeList;
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

	public BigDecimal getIdpedido_deta() {
		return idpedido_deta;
	}

	public void setIdpedido_deta(BigDecimal idpedido_deta) {
		this.idpedido_deta = idpedido_deta;
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

	public BigDecimal getIdpedido_cabe() {
		return idpedido_cabe;
	}

	public void setIdpedido_cabe(BigDecimal idpedido_cabe) {
		this.idpedido_cabe = idpedido_cabe;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public String getFechapedido() {
		return fechapedido;
	}

	public void setFechapedido(String fechapedido) {
		this.fechapedido = fechapedido;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getExpreso() {
		return expreso;
	}

	public void setExpreso(String expreso) {
		this.expreso = expreso;
	}

	public String getComision() {
		return comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
	}

	public String getOrdencompra() {
		return ordencompra;
	}

	public void setOrdencompra(String ordencompra) {
		this.ordencompra = ordencompra;
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

	public String getRecargo1() {
		return recargo1;
	}

	public void setRecargo1(String recargo1) {
		this.recargo1 = recargo1;
	}

	public String getRecargo2() {
		return recargo2;
	}

	public void setRecargo2(String recargo2) {
		this.recargo2 = recargo2;
	}

	public String getRecargo3() {
		return recargo3;
	}

	public void setRecargo3(String recargo3) {
		this.recargo3 = recargo3;
	}

	public String getRecargo4() {
		return recargo4;
	}

	public void setRecargo4(String recargo4) {
		this.recargo4 = recargo4;
	}

	public String getBonific1() {
		return bonific1;
	}

	public void setBonific1(String bonific1) {
		this.bonific1 = bonific1;
	}

	public String getBonific2() {
		return bonific2;
	}

	public void setBonific2(String bonific2) {
		this.bonific2 = bonific2;
	}

	public String getBonific3() {
		return bonific3;
	}

	public void setBonific3(String bonific3) {
		this.bonific3 = bonific3;
	}

	public String getDescri_lis() {
		return descri_lis;
	}

	public void setDescri_lis(String descri_lis) {
		this.descri_lis = descri_lis;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(String cotizacion) {
		this.cotizacion = cotizacion;
	}

	public String getTipoiva() {
		return tipoiva;
	}

	public void setTipoiva(String tipoiva) {
		this.tipoiva = tipoiva;
	}

	public String getTotaliva() {
		return totaliva;
	}

	public void setTotaliva(String totaliva) {
		this.totaliva = totaliva;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}

	public String getCuotas() {
		return cuotas;
	}

	public void setCuotas(String cuotas) {
		this.cuotas = cuotas;
	}

	public String getOrigenpedido() {
		return origenpedido;
	}

	public void setOrigenpedido(String origenpedido) {
		this.origenpedido = origenpedido;
	}

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

}
