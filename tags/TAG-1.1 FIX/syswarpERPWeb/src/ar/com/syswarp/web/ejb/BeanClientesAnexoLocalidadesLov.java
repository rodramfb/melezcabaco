/* 
   javabean para la entidad: clientesAnexoLocalidades
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Dec 23 14:59:21 GMT-03:00 2008 
   
   Para manejar la pagina: clientesAnexoLocalidadesAbm.jsp
      
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

public class BeanClientesAnexoLocalidadesLov implements SessionBean,
		Serializable {
	static Logger log = Logger.getLogger(BeanClientesAnexoLocalidadesLov.class);
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List clientesAnexoLocalidadesList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idanexolocalidad = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private BigDecimal idlocalidad = new BigDecimal(-1);

	private String localidad = "";

	private String postal = "";

	private BigDecimal idprovincia = new BigDecimal(-1);

	private String provincia = "";

	boolean buscar = false;

	public BeanClientesAnexoLocalidadesLov() {
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
		Clientes clientesAnexoLocalidades = Common.getClientes();

		String entidad = "("
				+ "SELECT al.idanexolocalidad,al.idexpresozona, e.expreso, z.zona,al.idlocalidad, l.localidad, al.codtfbasica, al.codtfctdo,"
				+ "       al.tarand1bulto,al.tarandexc,al.tarsoc1bulto,al.tarsocexc,al.cabeoinflu,al.norteosur,"
				+ "       al.idempresa,al.usuarioalt,al.usuarioact,al.fechaalt,al.fechaact"
				+ "  FROM clientesanexolocalidades al"
				+ "       INNER JOIN clientesexpresoszonas ez ON al.idexpresozona = ez.codigo AND al.idempresa = ez.idempresa "
				+ "       INNER JOIN clientesexpresos e ON ez.idexpreso = e.idexpreso AND ez.idempresa = e.idempresa "
				+ "       INNER JOIN clienteszonas z ON ez.idzona = z.idzona AND ez.idempresa = z.idempresa "
				+ "       INNER JOIN globallocalidades l ON al.idlocalidad = l.idlocalidad) entidad ";

		String filtro = " WHERE idlocalidad =  " + this.idlocalidad;

		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idanexolocalidad.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = clientesAnexoLocalidades
							.clientesAnexoLocalidadesDelete(idanexolocalidad,
									this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idanexolocalidad.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("clientesAnexoLocalidadesFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("clientesAnexoLocalidadesFrm.jsp");
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

				filtro += " AND (UPPER(expreso) LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' OR UPPER(zona) LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' OR UPPER(localidad) LIKE '%"
						+ ocurrencia.toUpperCase().trim() + "%') ";

				this.totalRegistros = clientesAnexoLocalidades
						.getTotalEntidadFiltro(entidad, filtro, this.idempresa);
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
				this.clientesAnexoLocalidadesList = clientesAnexoLocalidades
						.getClientesAnexoLocalidadesXLocaOcu(this.limit,
								this.offset, this.ocurrencia, this.idlocalidad,
								this.idempresa);
			} else {

				this.totalRegistros = clientesAnexoLocalidades
						.getTotalEntidadFiltro(entidad, filtro, this.idempresa);

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.clientesAnexoLocalidadesList = clientesAnexoLocalidades
						.getClientesAnexoLocalidadesXLocaAll(this.limit,
								this.offset, this.idlocalidad, this.idempresa);
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

	public List getClientesAnexoLocalidadesList() {
		return clientesAnexoLocalidadesList;
	}

	public void setClientesAnexoLocalidadesList(
			List clientesAnexoLocalidadesList) {
		this.clientesAnexoLocalidadesList = clientesAnexoLocalidadesList;
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

	public BigDecimal getIdanexolocalidad() {
		return idanexolocalidad;
	}

	public void setIdanexolocalidad(BigDecimal idanexolocalidad) {
		this.idanexolocalidad = idanexolocalidad;
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

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public BigDecimal getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(BigDecimal idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
}
