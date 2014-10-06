/* 
   javabean para la entidad: bacoRefCatalogo
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jun 15 15:54:26 ART 2010 
   
   Para manejar la pagina: bacoRefCatalogoAbm.jsp
      
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

public class BeanBacoRefCatalogoAbm implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanBacoRefCatalogoAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List bacoRefCatalogoList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idcatalogo;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String usuarioact = "";

	// 201203015 - EJV - Mantis 702 -->

	private String filtroIdcatalogocategoria = "";

	private String filtroCodigoSt = "";

	private List listCatalogoCategoria = new ArrayList();

	// <--

	public BeanBacoRefCatalogoAbm() {
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
		Clientes clientes = Common.getClientes();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idcatalogo.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = clientes.bacoRefCatalogoDeleteLogico(
							idcatalogo, this.idempresa, this.usuarioact);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idcatalogo.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("bacoRefCatalogoFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("bacoRefCatalogoFrm.jsp");
				dispatcher.forward(request, response);
				return true;
			}

			this.listCatalogoCategoria = Common.getClientes()
					.getBacoRefCatalogoCategoriaAll(500, 0, this.idempresa);

			String filtro = " WHERE true ";
			String auxFiltro = " ";

			if (!Common.setNotNull(this.filtroCodigoSt).equals("")) {
				auxFiltro += " AND UPPER(codigo_st) LIKE '"
						+ this.filtroCodigoSt.toUpperCase() + "%'";
			}

			// Harcode -2 hasta que se asignen las categorias a todos los
			// registros del catalogo.
			if (Common.esEntero(this.filtroIdcatalogocategoria)
					&& (Integer.parseInt(this.filtroIdcatalogocategoria) > 0 || Integer
							.parseInt(this.filtroIdcatalogocategoria) == -2)) {
				auxFiltro += " AND idcatalogocategoria = "
						+ this.filtroIdcatalogocategoria;
			}

			filtro += auxFiltro;

			this.totalRegistros = clientes.getTotalEntidadFiltro(
					"bacoRefCatalogo", filtro, this.idempresa);
			this.totalPaginas = (this.totalRegistros / this.limit) + 1;
			if (this.totalPaginas < this.paginaSeleccion)
				this.paginaSeleccion = this.totalPaginas;
			this.offset = (this.paginaSeleccion - 1) * this.limit;

			if (this.totalRegistros == this.limit) {
				this.offset = 0;
				this.totalPaginas = 1;
			}

			this.bacoRefCatalogoList = clientes.getBacoRefCatalogoAll(
					this.limit, this.offset, auxFiltro, this.idempresa);

			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
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

	public List getBacoRefCatalogoList() {
		return bacoRefCatalogoList;
	}

	public void setBacoRefCatalogoList(List bacoRefCatalogoList) {
		this.bacoRefCatalogoList = bacoRefCatalogoList;
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

	public BigDecimal getIdcatalogo() {
		return idcatalogo;
	}

	public void setIdcatalogo(BigDecimal idcatalogo) {
		this.idcatalogo = idcatalogo;
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

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	// 201203015 - EJV - Mantis 702 -->

	public String getFiltroIdcatalogocategoria() {
		return filtroIdcatalogocategoria;
	}

	public void setFiltroIdcatalogocategoria(String filtroIdcatalogocategoria) {
		this.filtroIdcatalogocategoria = filtroIdcatalogocategoria;
	}

	public String getFiltroCodigoSt() {
		return filtroCodigoSt;
	}

	public void setFiltroCodigoSt(String filtroCodigoSt) {
		this.filtroCodigoSt = filtroCodigoSt;
	}

	public List getListCatalogoCategoria() {
		return listCatalogoCategoria;
	}

	// <--

}
