/* 
 javabean para la entidad: globalprovincias
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 23 13:09:51 GMT-03:00 2007 
 
 Para manejar la pagina: globalprovinciasAbm.jsp
 
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

public class BeanClientesInformesFacturaArticulo implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanClientesInformesFacturaArticulo.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);
	
	private String codigo_st = ""; 
	
	private String descrip_st = "";
	
	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List facturasArticuloList = new ArrayList();
	
	private List facturasArticulosExportar =  new ArrayList();
	
	private List listArticulos = new ArrayList();
	
	private int totCol = 0;
	
	private String[] titulos = null;
	
	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idprovincia;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanClientesInformesFacturaArticulo() {
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
		Clientes clientes = Common.getClientes();
		try {
			if (!accion.equalsIgnoreCase(""))
			{
				
				if (codigo_st.equalsIgnoreCase(""))
				{
					this.mensaje ="No ingreso codigo";
					return false;
				}
				if (accion.equalsIgnoreCase("ir"))
				{
					this.facturasArticuloList = clientes.ObtenerFacturasXArticulo(codigo_st, this.idempresa);
					this.totalRegistros = clientes.ObtenerFacturasXArticuloTotal(codigo_st, this.idempresa);
				}
				if (accion.equalsIgnoreCase("exportar"))
				{		
					General general = Common.getGeneral();
					this.facturasArticulosExportar = clientes.ObtenerFacturasXArticulo(codigo_st, this.idempresa);
					general.setArchivo(facturasArticulosExportar,new BigDecimal(totCol) ,"FacturasPorArticulo" , titulos);
					this.facturasArticuloList = clientes.ObtenerFacturasXArticulo(codigo_st, this.idempresa);
					this.totalRegistros = clientes.ObtenerFacturasXArticuloTotal(codigo_st, this.idempresa);
					this.mensaje = "Exportado correctamente";
				
				}
				
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

	public List getGlobalprovinciasList() {
		return facturasArticuloList;
	}

	public void setGlobalprovinciasList(List globalprovinciasList) {
		this.facturasArticuloList = globalprovinciasList;
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

	public BigDecimal getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(BigDecimal idprovincia) {
		this.idprovincia = idprovincia;
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

	public List getListArticulos() {
		return listArticulos;
	}

	public void setListArticulos(List listArticulos) {
		this.listArticulos = listArticulos;
	}

	public List getFacturasArticuloList() {
		return facturasArticuloList;
	}

	public void setFacturasArticuloList(List facturasArticuloList) {
		this.facturasArticuloList = facturasArticuloList;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public List getFacturasArticulosExportar() {
		return facturasArticulosExportar;
	}

	public void setFacturasArticulosExportar(List facturasArticulosExportar) {
		this.facturasArticulosExportar = facturasArticulosExportar;
	}

	
	public int getTotCol() {
		return totCol;
	}

	public void setTotCol(int totCol) {
		this.totCol = totCol;
	}

	public String[] getTitulos() {
		return titulos;
	}

	public void setTitulos(String[] titulos) {
		this.titulos = titulos;
	}

}
