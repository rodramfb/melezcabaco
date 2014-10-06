/* 
   javabean para la entidad: bacotmliquidacionresumen
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 02 15:34:16 ART 2011 
   
   Para manejar la pagina: bacotmliquidacionresumenAbm.jsp
      
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Date;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.registry.infomodel.TelephoneNumber;

import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanBacotmliquidacionresumenAbm implements SessionBean,
		Serializable {
	static Logger log = Logger.getLogger(BeanBacotmliquidacionresumenAbm.class);
	private SessionContext context;
	private BigDecimal idempresa = new BigDecimal(-1);
	private int limit = 15;
	private long offset = 0l;
	private long totalRegistros = 0l;
	private long totalPaginas = 0l;
	private long paginaSeleccion = 1l;
	private List bacotmliquidacionresumenList = new ArrayList();
	private String accion = "";
	private String ocurrencia = "";
	private BigDecimal idliquidacion;
	private String mensaje = "";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private List listaVendedores = new ArrayList();
	private List listaCampanias = new ArrayList();
	private String vendedor = "";
	private String fecha = "";
	private BigDecimal idcampania =new BigDecimal(-1);
	boolean buscar = false;
	

	public BeanBacotmliquidacionresumenAbm() {
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
		General clientes = Common.getGeneral();
		try {
			//20110505 - CAMI - liquidacion a vendedores filtro grupo telemarketer 
			listaVendedores = clientes.getVendedoresTelemarketers();
			listaCampanias = clientes.getCampanias();
			if (this.accion.equalsIgnoreCase("consulta")) {
				if (idcampania.longValue()<0)
				{
					this.mensaje ="Debe seleccionar la campaña.";
					return false;
				}
				if (vendedor.equalsIgnoreCase("-1"))
				{
					this.mensaje="Debe seleccionar un telemarketer";
					return false;
				}
				
				this.bacotmliquidacionresumenList = clientes
						.getBacotmliquidacionresumenParametros(vendedor, idcampania,idempresa);
				request.getSession().setAttribute("idcampania", idcampania);
				request.getSession().setAttribute("usuario", vendedor);
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

	public List getBacotmliquidacionresumenList() {
		return bacotmliquidacionresumenList;
	}

	public void setBacotmliquidacionresumenList(
			List bacotmliquidacionresumenList) {
		this.bacotmliquidacionresumenList = bacotmliquidacionresumenList;
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

	public BigDecimal getIdliquidacion() {
		return idliquidacion;
	}

	public void setIdliquidacion(BigDecimal idliquidacion) {
		this.idliquidacion = idliquidacion;
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

	public List getListaVendedores() {
		return listaVendedores;
	}

	public void setListaVendedores(List listaVendedores) {
		this.listaVendedores = listaVendedores;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public BigDecimal getIdcampania() {
		return idcampania;
	}

	public void setIdcampania(BigDecimal idcampania) {
		this.idcampania = idcampania;
	}

	public List getListaCampanias() {
		return listaCampanias;
	}

	public void setListaCampanias(List listaCampanias) {
		this.listaCampanias = listaCampanias;
	}	
	
}
