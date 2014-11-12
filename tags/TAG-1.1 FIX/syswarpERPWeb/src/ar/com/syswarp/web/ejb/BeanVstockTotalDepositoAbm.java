/* 
 javabean para la entidad: vstockTotalDeposito
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Oct 24 16:08:04 GMT-03:00 2006 
 
 Para manejar la pagina: vstockTotalDepositoAbm.jsp
 
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

public class BeanVstockTotalDepositoAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanVstockTotalDepositoAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vstockTotalDepositoList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String codigo_st;

	private String mensaje = "";
	
	private String usuario = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanVstockTotalDepositoAbm() {
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
		Stock vstockTotalDeposito = Common.getStock();
		try {

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			if (buscar) {
				String[] campos = { "codigo_st", "descrip_st" };
				
				if (vstockTotalDeposito.hasDepositosAsociados(this.getUsuario(), this.getIdempresa())){
				   String filtroC = "where codigo_dt in " + vstockTotalDeposito.getQueryDepositosAsociados(this.getUsuario(), this.getIdempresa());
				   this.totalRegistros = vstockTotalDeposito.getTotalEntidadFitroOcu(
								"vstockTotalDeposito",filtroC , campos, this.ocurrencia,
								this.idempresa);
					
				} else {
				   this.totalRegistros = vstockTotalDeposito.getTotalEntidadOcu(
						"vstockTotalDeposito", campos, this.ocurrencia,
						this.idempresa);
				}				
				
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
				this.vstockTotalDepositoList = vstockTotalDeposito
						.getVstockTotalDepositoOcu(this.limit, this.offset,
								this.ocurrencia, this.idempresa, this.getUsuario());
			} else {
				
				if (vstockTotalDeposito.hasDepositosAsociados(this.getUsuario(), this.getIdempresa())){
					String filtroC = "where codigo_dt in " + vstockTotalDeposito.getQueryDepositosAsociados(this.getUsuario(), this.getIdempresa());
				    this.totalRegistros = vstockTotalDeposito.getTotalEntidadFiltro("vstockTotalDeposito", filtroC, this.idempresa);				    
				} else {					
					this.totalRegistros = vstockTotalDeposito.getTotalEntidad(
							"vstockTotalDeposito", this.idempresa);
				}
				
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.vstockTotalDepositoList = vstockTotalDeposito
						.getVstockTotalDepositoAll(this.limit, this.offset,
								this.idempresa, this.getUsuario() );
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

	public List getVstockTotalDepositoList() {
		return vstockTotalDepositoList;
	}

	public void setVstockTotalDepositoList(List vstockTotalDepositoList) {
		this.vstockTotalDepositoList = vstockTotalDepositoList;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
