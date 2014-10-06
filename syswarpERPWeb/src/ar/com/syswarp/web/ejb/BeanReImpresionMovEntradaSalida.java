/* 
 javabean para la entidad: vreImpresionRecibos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Sep 27 14:09:35 ART 2007 
 
 Para manejar la pagina: vreImpresionRecibosAbm.jsp
 
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

public class BeanReImpresionMovEntradaSalida implements SessionBean,
		Serializable {
	static Logger log = Logger.getLogger(BeanReImpresionMovEntradaSalida.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private long limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vreImpresionRecibosList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String comprobante;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String inOut = "ENTRADA";

	boolean buscar = false;

	private String usuario = "";

	public BeanReImpresionMovEntradaSalida() {
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
		Stock stock = Common.getStock();
		try {

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			String filtro = " WHERE tipo = '"
					+ this.inOut
					+ "' AND (tipoaux_ms IS NULL OR tipoaux_ms <> 'C')  "
					+ (stock.hasDepositosAsociados(usuario, idempresa) ? (" AND coddepo IN  " + stock
							.getQueryDepositosAsociados(usuario, idempresa))
							: "");
			String entidad = " ( SELECT DISTINCT remito_interno, sucu_ms, remito_interno_ms, fecha , tipo, coddepo , deposito, tipoaux_ms, idempresa FROM vremitointerno ) VRI ";
			if (buscar) {
                
				filtro += " AND remito_interno::VARCHAR LIKE '%" + this.ocurrencia + "%' ";
				//+ " or destino  LIKE '%" + this.ocurrencia.toUpperCase() + "%' ";
				
				this.totalRegistros = stock.getTotalEntidadFiltro(entidad,
						filtro, this.idempresa);
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
				this.vreImpresionRecibosList = stock
						.getReImprimeMovEntradaSalidaOcu(this.limit,
								this.offset, this.inOut, this.usuario,
								this.ocurrencia, this.idempresa);
			} else {
				// filtro absurdo por string esperado por metodo

				this.totalRegistros = stock.getTotalEntidadFiltro(entidad,
						filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.vreImpresionRecibosList = stock
						.getReImprimeMovEntradaSalidaAll(this.limit,
								this.offset, this.inOut, this.usuario,
								this.idempresa);

			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
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

	public List getVreImpresionRecibosList() {
		return vreImpresionRecibosList;
	}

	public void setVreImpresionRecibosList(List vreImpresionRecibosList) {
		this.vreImpresionRecibosList = vreImpresionRecibosList;
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

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
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

	public String getInOut() {
		return inOut;
	}

	public void setInOut(String inOut) {
		this.inOut = inOut;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
