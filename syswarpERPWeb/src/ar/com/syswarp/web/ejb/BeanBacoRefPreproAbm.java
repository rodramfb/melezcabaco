/* 
   javabean para la entidad: bacoRefPrepro
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 16 10:12:03 ART 2010 
   
   Para manejar la pagina: bacoRefPreproAbm.jsp
      
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

public class BeanBacoRefPreproAbm implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanBacoRefPreproAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List bacoRefPreproList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idpreprospecto = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	// 20120301 - EJV - Mantis 702 -->

	private String fechadesde = "";

	private String fechahasta = "";

	private String filtroIdvendedor = "";

	private String filtroVendedor = "";

	private String filtroIdfuente = "";

	private String fuente = "";

	private List listFuente = new ArrayList();

	private String filtroIdrefestado = "";

	private List listRefEstados = new ArrayList();

	// <--

	public BeanBacoRefPreproAbm() {
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
				if (idpreprospecto.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = clientes.bacoRefPreproDelete(idpreprospecto,
							this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idpreprospecto.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("bacoRefPreproFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("bacoRefPreproFrm.jsp");
				dispatcher.forward(request, response);
				return true;
			}

			this.listFuente = Common.getClientes().getBacoRefFuentesAll(500, 0,
					this.idempresa);

			// 20120314 - EJV - Mantis 702 -->
			this.listRefEstados = Common.getClientes().getBacoRefEstadosAll(
					500, 0, this.idempresa);
			// <--

			if ((Common.isFormatoFecha(this.fechadesde) && Common
					.isFechaValida(this.fechadesde))
					&& (Common.isFormatoFecha(this.fechahasta) && Common
							.isFechaValida(this.fechahasta))

			) {

				String filtro = " WHERE TRUE ";

				String auxFiltro = " AND fecha BETWEEN  TO_DATE('"
						+ this.fechadesde + "', 'dd/mm/yyyy')";
				auxFiltro += "   AND  TO_DATE('" + this.fechahasta
						+ "', 'dd/mm/yyyy')";

				if (Common.esEntero(this.filtroIdvendedor)
						&& Integer.parseInt(this.filtroIdvendedor) > 0) {
					auxFiltro += " AND idvendedor = " + this.filtroIdvendedor;
				}

				if (Common.esEntero(this.filtroIdfuente)
						&& Integer.parseInt(this.filtroIdfuente) > 0) {
					auxFiltro += " AND idfuente = " + this.filtroIdfuente;
				}


				if (Common.esEntero(this.filtroIdrefestado)
						&& Integer.parseInt(this.filtroIdrefestado) > 0) {
					auxFiltro += " AND idrefestado = " + this.filtroIdrefestado;
				}
				
				filtro += auxFiltro;
				this.totalRegistros = clientes.getTotalEntidadFiltro(
						"bacoRefPrepro", filtro, this.idempresa);

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.bacoRefPreproList = clientes.getBacoRefPreproAll(
						this.limit, this.offset, auxFiltro, this.idempresa);

				if (this.totalRegistros < 1)
					this.mensaje = "No existen registros.";

			} else {

				this.mensaje = "Es necesario filtrar por periodo.";

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

	public List getBacoRefPreproList() {
		return bacoRefPreproList;
	}

	public void setBacoRefPreproList(List bacoRefPreproList) {
		this.bacoRefPreproList = bacoRefPreproList;
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

	public BigDecimal getIdpreprospecto() {
		return idpreprospecto;
	}

	public void setIdpreprospecto(BigDecimal idpreprospecto) {
		this.idpreprospecto = idpreprospecto;
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

	// 20120301 - EJV - Mantis 702 -->

	public String getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(String fechadesde) {
		this.fechadesde = fechadesde;
	}

	public String getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(String fechahasta) {
		this.fechahasta = fechahasta;
	}

	public String getFiltroIdvendedor() {
		return filtroIdvendedor;
	}

	public void setFiltroIdvendedor(String filtroIdvendedor) {
		this.filtroIdvendedor = filtroIdvendedor;
	}

	public String getFiltroVendedor() {
		return filtroVendedor;
	}

	public void setFiltroVendedor(String filtroVendedor) {
		this.filtroVendedor = filtroVendedor;
	}

	public String getFiltroIdfuente() {
		return filtroIdfuente;
	}

	public void setFiltroIdfuente(String filtroIdfuente) {
		this.filtroIdfuente = filtroIdfuente;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public List getListFuente() {
		return listFuente;
	}

	public String getFiltroIdrefestado() {
		return filtroIdrefestado;
	}

	public void setFiltroIdrefestado(String filtroIdrefestado) {
		this.filtroIdrefestado = filtroIdrefestado;
	}

	public List getListRefEstados() {
		return listRefEstados;
	}

	// <--

}
