/* 
   javabean para la entidad: clientesRemitosLeyendas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Dec 10 09:15:59 GMT-03:00 2009 
   
   Para manejar la pagina: clientesRemitosLeyendasAbm.jsp
      
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

public class BeanClientesRemitosLeyendasAbm implements SessionBean,
		Serializable {

	static Logger log = Logger.getLogger(BeanClientesRemitosLeyendasAbm.class);

	private BigDecimal idempresa = new BigDecimal(-1);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List clientesRemitosLeyendasList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idleyenda = new BigDecimal(-1);

	private String[] idleyendaMultiple = null;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private List listProvincias = new ArrayList();

	private List mesesList = new ArrayList();

	boolean buscar = false;

	private int anioactual = Calendar.getInstance().get(Calendar.YEAR);

	private String filtroMes = "";

	private String filtroProvincia = "";

	private String filtroAnio = "";

	private String filtroLocalidad = "";

	private String filtroPostal = "";

	// EJV - Mantis 733 - 20110725 -->

	private List listClub = new ArrayList();

	private String filtroClub = "";

	// <--

	public BeanClientesRemitosLeyendasAbm() {
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
		Clientes clientesRemitosLeyendas = Common.getClientes();
		String auxFiltro = "";
		String entidad = "("
				+ "         SELECT ly.idleyenda, ly.anio, ly.idmes, me.mes, lo.idlocalidad, "
				+ "                COALESCE(lo.localidad, 'TODAS') AS localidad, lo.cpostal, pr.idprovincia, "
				+ "                COALESCE(pr.provincia, 'TODAS') AS provincia, ly.leyenda,"
				// EJV - Mantis 733 - 20110725 -->
				+ "                ly.idclub, "
				// <--
				+ "                ly.idempresa, ly.usuarioalt, ly.usuarioact, ly.fechaalt, ly.fechaact"
				+ "           FROM clientesremitosleyendas ly"
				+ "                INNER JOIN globalmeses me ON ly.idmes = me.idmes "
				+ "                 LEFT JOIN globallocalidades lo ON ly.idlocalidad = lo.idlocalidad "
				+ "                 LEFT JOIN globalprovincias pr ON lo.idprovincia = pr.idprovincia "
				+ "       ) AS entidad  ";

		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idleyenda.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = clientesRemitosLeyendas
							.clientesRemitosLeyendasDelete(idleyenda,
									this.idempresa);
				}
			}

			if (this.accion.equalsIgnoreCase("bajaMultiple")) {
				if (this.idleyendaMultiple == null
						|| this.idleyendaMultiple.length < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = clientesRemitosLeyendas
							.clientesRemitosLeyendasDeleteMultiple(
									this.idleyendaMultiple, this.idempresa);
				}
			}

			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idleyenda.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("clientesRemitosLeyendasFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("clientesRemitosLeyendasFrm.jsp");
				dispatcher.forward(request, response);
				return true;
			}

			this.listProvincias = Common.getGeneral().getGlobalprovinciasAll(
					50, 0);

			this.mesesList = Common.getGeneral().getGlobalMeses();

			// EJV - Mantis 733 - 20110725 -->
			this.listClub = Common.getClientes().getClientesClubAll(100, 0,
					this.idempresa);

			if (!this.filtroClub.trim().equals("")) {
				auxFiltro += " AND  idclub = " + this.filtroClub;
			}

			// <--

			if (!this.filtroAnio.trim().equals("")) {
				auxFiltro += " AND  anio = " + this.filtroAnio;
			}

			if (!this.filtroMes.trim().equals("")) {
				auxFiltro += " AND  idmes = " + this.filtroMes;
			}

			if (!this.filtroProvincia.trim().equals("")) {
				auxFiltro += " AND idprovincia = " + this.filtroProvincia;
			}

			if (!this.filtroLocalidad.trim().equals("")) {
				auxFiltro += " AND UPPER(localidad) LIKE '"
						+ this.filtroLocalidad.replaceAll("'", "%")
								.toUpperCase() + "%'  ";
			}

			if (!this.filtroPostal.trim().equals("")) {

				if (Common.esEntero(this.filtroPostal)) {
					auxFiltro += " AND cpostal = '" + this.filtroPostal + "'  ";
				} else
					this.filtroPostal = "";
			}

			if (!auxFiltro.equalsIgnoreCase("")) {
				String[] campos = { "idleyenda", "anio" };
				this.totalRegistros = clientesRemitosLeyendas
						.getTotalEntidadFiltro(entidad, " WHERE TRUE "
								+ auxFiltro, this.idempresa);
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
				this.clientesRemitosLeyendasList = clientesRemitosLeyendas
						.getClientesRemitosLeyendasOcu(this.limit, this.offset,
								auxFiltro, this.idempresa);
			} else {
				this.totalRegistros = clientesRemitosLeyendas.getTotalEntidad(
						"clientesRemitosLeyendas", this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.clientesRemitosLeyendasList = clientesRemitosLeyendas
						.getClientesRemitosLeyendasAll(this.limit, this.offset,
								this.idempresa);
			}
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

	public List getClientesRemitosLeyendasList() {
		return clientesRemitosLeyendasList;
	}

	public void setClientesRemitosLeyendasList(List clientesRemitosLeyendasList) {
		this.clientesRemitosLeyendasList = clientesRemitosLeyendasList;
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

	public BigDecimal getIdleyenda() {
		return idleyenda;
	}

	public void setIdleyenda(BigDecimal idleyenda) {
		this.idleyenda = idleyenda;
	}

	public String[] getIdleyendaMultiple() {
		return idleyendaMultiple;
	}

	public void setIdleyendaMultiple(String[] idleyendaMultiple) {
		this.idleyendaMultiple = idleyendaMultiple;
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

	public List getListProvincias() {
		return listProvincias;
	}

	public void setListProvincias(List listProvincias) {
		this.listProvincias = listProvincias;
	}

	public int getAnioactual() {
		return anioactual;
	}

	public void setAnioactual(int anioactual) {
		this.anioactual = anioactual;
	}

	public List getMesesList() {
		return mesesList;
	}

	public void setMesesList(List mesesList) {
		this.mesesList = mesesList;
	}

	public String getFiltroMes() {
		return filtroMes;
	}

	public void setFiltroMes(String filtroMes) {
		this.filtroMes = filtroMes;
	}

	public String getFiltroProvincia() {
		return filtroProvincia;
	}

	public void setFiltroProvincia(String filtroProvincia) {
		this.filtroProvincia = filtroProvincia;
	}

	public String getFiltroAnio() {
		return filtroAnio;
	}

	public void setFiltroAnio(String filtroAnio) {
		this.filtroAnio = filtroAnio;
	}

	public String getFiltroLocalidad() {
		return filtroLocalidad;
	}

	public void setFiltroLocalidad(String filtroLocalidad) {
		this.filtroLocalidad = filtroLocalidad;
	}

	public String getFiltroPostal() {
		return filtroPostal;
	}

	public void setFiltroPostal(String filtroPostal) {
		this.filtroPostal = filtroPostal;
	}

	// EJV - Mantis 733 - 20110725 -->

	public String getFiltroClub() {
		return filtroClub;
	}

	public void setFiltroClub(String filtroClub) {
		this.filtroClub = filtroClub;
	}

	public List getListClub() {
		return listClub;
	}

	// <--
}
