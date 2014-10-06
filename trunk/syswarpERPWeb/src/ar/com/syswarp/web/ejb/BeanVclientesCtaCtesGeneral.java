/* 
   javabean para la entidad: vclientesCtaCtes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 25 08:50:26 GYT 2009 
   
   Para manejar la pagina: vclientesCtaCtesAbm.jsp
      
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

public class BeanVclientesCtaCtesGeneral implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanVclientesCtaCtesGeneral.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List vclientesCtaCtesList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String historico = "P";

	private BigDecimal saldo_total = new BigDecimal(0);

	boolean buscar = false;

	private String rangoRazonDesde = "";

	private String rangoRazonHasta = "";

	private BigDecimal idtipoclie = new BigDecimal(-1);

	private List listTipoCliente = new ArrayList();

	public BeanVclientesCtaCtesGeneral() {
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
		Clientes vclientesCtaCtes = Common.getClientes();

		try {

			this.listTipoCliente = Common.getClientes().getClientestipoclieAll(
					500, 0, this.idempresa);

			// this.saldo_total = vclientesCtaCtes.getVclientesCtaCtesSaldo(
			// this.idcliente, this.idempresa);

			String filtro = " WHERE TRUE  ";
			String auxFiltro = "";

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			if (Common.setNotNull(this.accion).equalsIgnoreCase("consulta")) {

				// DESDE AQUI
				if (!this.rangoRazonDesde.equalsIgnoreCase("")
						|| !this.rangoRazonHasta.equalsIgnoreCase("")) {

					if (!this.rangoRazonDesde.equalsIgnoreCase("")
							&& !this.rangoRazonHasta.equalsIgnoreCase("")) {

						auxFiltro += "  AND SUBSTRING(TRIM(REPLACE(razon, 'Ñ', 'N')), 1, 1) ~* '["
								+ this.rangoRazonDesde
								+ "-"
								+ this.rangoRazonHasta + "]'  ";

					} else if (!this.rangoRazonDesde.equalsIgnoreCase("")
							&& this.rangoRazonHasta.equalsIgnoreCase("")) {
						this.mensaje = "Es necesario seleccionar rango hasta";
					} else if (this.rangoRazonDesde.equalsIgnoreCase("")
							&& !this.rangoRazonHasta.equalsIgnoreCase("")) {
						this.mensaje = "Es necesario seleccionar rango desde";
					}

				}

				if (this.historico.equalsIgnoreCase("P")) {
					auxFiltro += " AND saldo_movimiento <> 0 ";
				}

				if (this.idtipoclie.intValue() > 0) {
					auxFiltro += " AND idtipoclie = " + this.idtipoclie;
				}

				filtro += auxFiltro;
				this.totalRegistros = vclientesCtaCtes.getTotalEntidadFiltro(
						"vclientesCtaCtes", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.vclientesCtaCtesList = vclientesCtaCtes
						.getVclientesCtaCtesGeneral(this.limit, this.offset,
								auxFiltro, this.idempresa);

				if (this.totalRegistros < 1 && this.mensaje.equals(""))
					this.mensaje = "No existen registros.";

			}

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

	public List getVclientesCtaCtesList() {
		return vclientesCtaCtesList;
	}

	public void setVclientesCtaCtesList(List vclientesCtaCtesList) {
		this.vclientesCtaCtesList = vclientesCtaCtesList;
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

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
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

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public BigDecimal getSaldo_total() {
		return saldo_total;
	}

	public void setSaldo_total(BigDecimal saldo_total) {
		this.saldo_total = saldo_total;
	}

	public String getRangoRazonDesde() {
		return rangoRazonDesde;
	}

	public void setRangoRazonDesde(String rangoRazonDesde) {
		this.rangoRazonDesde = rangoRazonDesde;
	}

	public String getRangoRazonHasta() {
		return rangoRazonHasta;
	}

	public void setRangoRazonHasta(String rangoRazonHasta) {
		this.rangoRazonHasta = rangoRazonHasta;
	}

	public BigDecimal getIdtipoclie() {
		return idtipoclie;
	}

	public void setIdtipoclie(BigDecimal idtipoclie) {
		this.idtipoclie = idtipoclie;
	}

	public List getListTipoCliente() {
		return listTipoCliente;
	}

}
