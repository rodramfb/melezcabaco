/* 
 javabean para la entidad: globalLocalidades
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Oct 27 16:35:02 GMT-03:00 2006 
 
 Para manejar la pagina: globalLocalidadesAbm.jsp
 
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

public class BeanContableAjusteLov implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanContableAjusteLov.class);

	private SessionContext context;
	
	private BigDecimal idempresa = new BigDecimal(-1);
	
		
	private String ano ="";
	
	private String d_ano ="";
	
	private String indice_pl ="";
	
	private String d_indice_pl ="";
	
	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List AjusteList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

		
	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BeanContableAjusteLov() {
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
		Contable globalMeses = Common.getContable();
		try {

			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("lov_ajuste.jsp");
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
			if (buscar) {
				String[] campos = { "cod_ajuste", "anio" };
				this.totalRegistros = globalMeses.getTotalEntidadOcu(
						"contableajuste", campos, this.ocurrencia,this.idempresa);
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
				this.AjusteList = globalMeses.getContableAjusteLovOcu(
						this.limit, this.offset, this.ocurrencia,this.idempresa);
			} else {
				this.totalRegistros = globalMeses.getTotalEntidad(
						"contableajuste" ,this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.AjusteList = globalMeses.getContableAjusteLovAll(
						this.limit, this.offset,this.idempresa);
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

	public List getglobaAjusteList() {
		return AjusteList;
	}

	public void setglobaAjusteList(List globaAjusteList) {
		this.AjusteList = globaAjusteList;
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


	public List getGlobaAjusteList() {
		return AjusteList;
	}

	public void setGlobaAjusteList(List globaAjusteList) {
		this.AjusteList = globaAjusteList;
	}

	public List getAjusteList() {
		return AjusteList;
	}

	public void setAjusteList(List AjusteList) {
		AjusteList = AjusteList;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}



	public String getD_ano() {
		return d_ano;
	}

	public void setD_ano(String d_ano) {
		this.d_ano = d_ano;
	}


	public String getD_indice_pl() {
		return d_indice_pl;
	}

	public void setD_indice_pl(String d_indice_pl) {
		this.d_indice_pl = d_indice_pl;
	}

	public String getIndice_pl() {
		return indice_pl;
	}

	public void setIndice_pl(String indice_pl) {
		this.indice_pl = indice_pl;
	}



}
