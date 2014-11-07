package ar.com.syswarp.web.ejb.report;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.com.syswarp.api.Common;
import ar.com.syswarp.ejb.Report;

/**
 * XDoclet-based session bean. The class must be declared public according to
 * the EJB specification.
 * 
 * To generate the EJB related files to this EJB: - Add Standard EJB module to
 * XDoclet project properties - Customize XDoclet configuration for your
 * appserver - Run XDoclet
 * 
 * Below are the xdoclet-related tags needed for this EJB.
 * 
 * @ejb.bean name="BeanUsuariosGruposAbm" display-name="Name for
 *           BeanUsuariosGruposAbm" description="Description for
 *           BeanUsuariosGruposAbm" jndi-name="ejb/BeanUsuariosGruposAbm"
 *           type="Stateful" view-type="remote"
 */
public class BeanUsuariosGruposAbm implements SessionBean, Serializable {

	/** The session context */
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanUsuariosGruposAbm.class);

	private long idusuario = 0;

	private List listUsuario = new ArrayList();

	private String usuario = "";

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List gruposList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private long idgrupo = -1l;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String volver_usuarios = "";

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation.
	 * 
	 * The enterprise bean instance should store the reference to the context
	 * object in an instance variable.
	 * 
	 * This method is called with no transaction context.
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error.
	 */

	public BeanUsuariosGruposAbm() {
		super();
		// TODO Auto-generated constructor stub
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

	/**
	 * An example business method
	 * 
	 * @ejb.interface-method view-type = "remote"
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error.
	 */

	public boolean ejecutarValidacion() {
		RequestDispatcher dispatcher = null;
		Report reporting = Common.getReport();
		try {

			if (!this.volver_usuarios.equalsIgnoreCase("")) {
				dispatcher = request.getRequestDispatcher("usuariosAbm.jsp");
				dispatcher.forward(request, response);
				return true;
			}

			if (this.accion.equalsIgnoreCase("baja")) {
				if (idgrupo < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = reporting.usuarioGrupoDelete(this.idusuario,
							this.idgrupo);
				}

			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idgrupo < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {

					dispatcher = request
							.getRequestDispatcher("usuariosGruposFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("usuariosGruposFrm.jsp");
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

			listUsuario = reporting.getUsuario(this.idusuario);
			Iterator iterUsuario = listUsuario.iterator();
			if (iterUsuario.hasNext()) {
				String[] uCampos = (String[]) iterUsuario.next();
				this.usuario = uCampos[1];
			}
			/*
			 * POR EL MOMENTO NO SE REALIZAN BUSQUEDAS SIEMPRE ENTRA POR ELSE.
			 */
			buscar = false;
			if (buscar) {

				String[] campos = { " idgrupo ", " grupo " };
				this.totalRegistros = reporting.getTotalEntidadOcu("idgrupos",
						campos, this.ocurrencia);
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
				this.gruposList = reporting.getGruposOcu(this.limit,
						this.offset, this.ocurrencia);
			} else {
				String[] campos = { "idusuario" };
				String[] valores = { this.idusuario + "" };
				this.totalRegistros = reporting.getTotalEntidadRelacion(
						"grupo_usuarios", campos, valores);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.gruposList = reporting.getUsuarioGruposAll(this.limit,
						this.offset, this.idusuario);
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

	public List getGruposList() {
		return gruposList;
	}

	public void setGruposList(List gruposList) {
		this.gruposList = gruposList;
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

	public long getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(long codigo) {
		this.idgrupo = codigo;
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

	public long getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(long idusuario) {
		this.idusuario = idusuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getVolver_usuarios() {
		return volver_usuarios;
	}

	public void setVolver_usuarios(String volver_usuarios) {
		this.volver_usuarios = volver_usuarios;
	}

}
