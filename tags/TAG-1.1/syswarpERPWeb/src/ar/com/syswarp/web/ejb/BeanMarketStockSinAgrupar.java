/* 
 javabean para la entidad: Stockstock
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Sep 04 09:21:34 GMT-03:00 2006 
 
 Para manejar la pagina: StockstockAbm.jsp
 
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

public class BeanMarketStockSinAgrupar implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanMarketStockXGrupo.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 20;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List StockstockList = new ArrayList();

	private String accion = "";

	private String cmpBuscar = "";

	private String codigo_st;

	private BigDecimal codigo_gr = new BigDecimal(-1);

	private String descrip_gr = "";

	private BigDecimal codigo_fm = new BigDecimal(-1);

	private String descrip_fm = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String filtro = "";

	boolean buscar = false;

	private String marketIdlista = "";

	public BeanMarketStockSinAgrupar() {
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
		Stock Stockstock = Common.getStock();
		try {

			this.marketIdlista = Stockstock.getMarketIdlista();

			
			String entidad = "("
					+ "SELECT st.codigo_st, st.alias_st, st.descrip_st, st.descri2_st, "
					+ "       l.precio::numeric(18, 2), l.precio::numeric(18, 2), "
					+ "       st.cost_uc_st::numeric(18, 2), st.ultcomp_st::numeric(18, 2), "
					+ "       st.cost_re_st::numeric(18, 2), st.reposic_st, st.nom_com_st, st.grupo_st, st.idempresa  "
					+ "  FROM STOCKSTOCK st"
					+ "       INNER JOIN clienteslistasdeprecios l ON st.codigo_st = l.codigo_st AND st.idempresa = l.idempresa"
					+ "   AND l.idlista =  " + this.marketIdlista + " where st.idempresa = " + idempresa.toString()+ "  order by 3) entidad ";



			if (this.codigo_gr.longValue() > 0) {
				// Stockstock.getStockfamiliasPK(this.codigo_fm,
				// this.idempresa);
				String[] datos = (String[]) Stockstock.getStockgruposPK(
						this.codigo_gr, this.idempresa).get(0);

				this.descrip_gr = datos[1];
				this.codigo_fm = new BigDecimal(datos[2]);
				this.descrip_fm = datos[3];

			}

			if (!this.cmpBuscar.trim().equalsIgnoreCase(""))
				buscar = true;

			if (buscar) {
				this.filtro = " WHERE UPPER(descrip_st) LIKE '%"
						+ cmpBuscar.replaceAll("'", "''").toUpperCase()
						+ "%') ";
				
				this.totalRegistros = Stockstock.getTotalEntidadFiltro(
						entidad, this.filtro, this.idempresa);
				
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
				this.StockstockList = Stockstock
						.getStockStockMarketSinAgruparOcu(this.limit, this.offset,
								this.cmpBuscar, this.idempresa);
			} else {
				this.filtro = " WHERE UPPER(descrip_st) LIKE '%"
					+ cmpBuscar.replaceAll("'", "''").toUpperCase()
					+ "%') ";
				this.totalRegistros = Stockstock.getTotalEntidadFiltro(
						entidad, filtro, this.idempresa);

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.StockstockList = Stockstock
						.getStockStockSinAgrupar(this.limit, this.offset
								,this.idempresa);
			}

			if (this.totalRegistros < 1)
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

	public List getStockstockList() {
		return StockstockList;
	}

	public void setStockstockList(List StockstockList) {
		this.StockstockList = StockstockList;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getCmpBuscar() {
		return cmpBuscar;
	}

	public void setCmpBuscar(String cmpBuscar) {
		this.cmpBuscar = cmpBuscar;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
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

	public BigDecimal getCodigo_gr() {
		return codigo_gr;
	}

	public void setCodigo_gr(BigDecimal codigo_gr) {
		this.codigo_gr = codigo_gr;
	}

	public BigDecimal getCodigo_fm() {
		return codigo_fm;
	}

	public void setCodigo_fm(BigDecimal codigo_fm) {
		this.codigo_fm = codigo_fm;
	}

	public String getDescrip_fm() {
		return descrip_fm;
	}

	public void setDescrip_fm(String descrip_fm) {
		this.descrip_fm = descrip_fm;
	}

	public String getDescrip_gr() {
		return descrip_gr;
	}

	public void setDescrip_gr(String descrip_gr) {
		this.descrip_gr = descrip_gr;
	}
}

