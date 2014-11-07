/* 
   javabean para la entidad: pedidos_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Feb 12 08:43:04 GYT 2009 
   
   Para manejar la pagina: pedidos_cabeAbm.jsp
      
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanClientesRemitosControlDespachos implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanClientesRemitosControlDespachos.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private String sort = " 1 ASC ";

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List pedidos_cabeList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String[] nrohojaarmado = new String[] {};

	private BigDecimal idpuesto = new BigDecimal(-1);

	private BigDecimal idcontadorcomprobante = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String filtroHojarutafinal = "";

	private String filtroExpreso = "";

	private String filtroDepositoDestino = "";

	private String filtroFHADesde = "";

	private String filtroFHAHasta = "";

	private String filtroCtaCte = "";

	private String usuarioalt;

	private String tipo = "";

	private String tipopedido = "N";

	private List listExpresos = new ArrayList();

	public BeanClientesRemitosControlDespachos() {
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
		// RequestDispatcher dispatcher = null;
		Clientes remitoscliente = Common.getClientes();
		String entidad = "vclientesremitoscontroldespachos";
		String filtro = " WHERE tipo =  '" + this.tipopedido.toUpperCase()
				+ "'";
		String auxFiltro = "";
		try {

			this.listExpresos = remitoscliente.getClientesexpresosAll(500, 0,
					this.idempresa);

			if (!this.filtroHojarutafinal.trim().equals("")) {
				auxFiltro += " AND nrohojaarmado = " + this.filtroHojarutafinal;
			}

			if (!this.filtroExpreso.trim().equals("")) {
				auxFiltro += " AND idexpreso =  " + this.filtroExpreso;
			}

			if (!this.filtroCtaCte.trim().equals("")) {
				auxFiltro += " AND nroctacte =  '" + this.filtroCtaCte + "' ";
			}

			if (!this.filtroFHADesde.equals("")
					|| !this.filtroFHAHasta.equals("")) {

				if (!this.filtroFHADesde.equals("")
						&& this.filtroFHAHasta.equals("")) {
					this.mensaje = "Seleccione fecha hasta.";
				} else if (this.filtroFHADesde.equals("")
						&& !this.filtroFHAHasta.equals("")) {
					this.mensaje = "Seleccione fecha desde.";
				} else {

					java.sql.Date fdesde = (java.sql.Date) Common
							.setObjectToStrOrTime(this.filtroFHADesde,
									"StrToJSDate");
					java.sql.Date fhasta = (java.sql.Date) Common
							.setObjectToStrOrTime(this.filtroFHAHasta,
									"StrToJSDate");

					if (fdesde.after(fhasta)) {
						this.mensaje = "Fecha desde debe ser anterior a fecha hasta.";
					} else {

						auxFiltro += " AND fechahojaarmado BETWEEN  '" + fdesde
								+ "'::DATE AND '" + fhasta + "'::DATE";

					}

				}

			}

			auxFiltro = auxFiltro.trim();

			if (!auxFiltro.equals("")) {

				filtro += auxFiltro;

				this.totalRegistros = remitoscliente.getTotalEntidadFiltro(
						entidad, filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				if (this.totalRegistros == this.limit)
					this.offset = 0;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.pedidos_cabeList = remitoscliente
						.getClientesRemitosControlDespachosOcu(this.limit,
								this.offset, filtro, this.sort, this.idempresa);
			} else {
				this.totalRegistros = remitoscliente.getTotalEntidadFiltro(
						entidad, filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.pedidos_cabeList = remitoscliente
						.getClientesRemitosControlDespachosAll(this.limit,
								this.offset, this.sort, this.tipopedido,
								this.idempresa);
			}

			if (this.totalRegistros < 1 && this.mensaje.equals(""))
				this.mensaje = "No existen registros.";

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

	public List getPedidos_cabeList() {
		return pedidos_cabeList;
	}

	public void setPedidos_cabeList(List pedidos_cabeList) {
		this.pedidos_cabeList = pedidos_cabeList;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getFiltroHojarutafinal() {
		return filtroHojarutafinal;
	}

	public void setFiltroHojarutafinal(String filtroHojarutafinal) {
		this.filtroHojarutafinal = filtroHojarutafinal;
	}

	public BigDecimal getIdpuesto() {
		return idpuesto;
	}

	public void setIdpuesto(BigDecimal idpuesto) {
		this.idpuesto = idpuesto;
	}

	public BigDecimal getIdcontadorcomprobante() {
		return idcontadorcomprobante;
	}

	public void setIdcontadorcomprobante(BigDecimal idcontadorcomprobante) {
		this.idcontadorcomprobante = idcontadorcomprobante;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getFiltroExpreso() {
		return filtroExpreso;
	}

	public void setFiltroExpreso(String filtroExpreso) {
		this.filtroExpreso = filtroExpreso;
	}

	public String getFiltroDepositoDestino() {
		return filtroDepositoDestino;
	}

	public void setFiltroDepositoDestino(String filtroDepositoDestino) {
		this.filtroDepositoDestino = filtroDepositoDestino;
	}

	public String getFiltroFHADesde() {
		return filtroFHADesde;
	}

	public void setFiltroFHADesde(String filtroFHADesde) {
		this.filtroFHADesde = filtroFHADesde;
	}

	public String getFiltroFHAHasta() {
		return filtroFHAHasta;
	}

	public void setFiltroFHAHasta(String filtroFHAHasta) {
		this.filtroFHAHasta = filtroFHAHasta;
	}

	public String[] getNrohojaarmado() {
		return nrohojaarmado;
	}

	public void setNrohojaarmado(String[] nrohojaarmado) {
		this.nrohojaarmado = nrohojaarmado;
	}

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

	public List getListExpresos() {
		return listExpresos;
	}

	public void setListExpresos(List listExpresos) {
		this.listExpresos = listExpresos;
	}

}
