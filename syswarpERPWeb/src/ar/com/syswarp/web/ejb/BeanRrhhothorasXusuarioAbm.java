/* 
 javabean para la entidad: rrhhothoras
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Apr 07 15:54:04 ART 2008 
 
 Para manejar la pagina: rrhhothorasAbm.jsp
 
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

public class BeanRrhhothorasXusuarioAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanRrhhothorasXusuarioAbm.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private List listHorasxusuario = new ArrayList();

	private String totalHoras = "0.00";

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List rrhhothorasList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idothoras;

	private BigDecimal idempresa;

	private BigDecimal filtro;

	private String ordendetrabajo = "";

	private BigDecimal idordendetrabajo = BigDecimal.valueOf(0);

	private String usuario;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanRrhhothorasXusuarioAbm() {
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
		RRHH rrhhothoras = Common.getRrhh();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idothoras == null || idothoras.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = rrhhothoras.rrhhothorasDelete(
							this.idothoras, this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idothoras == null || idothoras.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("rrhhothorasxusuarioFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("rrhhothorasxusuarioFrm.jsp");
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

			

			if (this.idordendetrabajo.longValue() == 0) {
				this.filtro = rrhhothoras.getRrhhotxfiltroxOrdenTrabajo(this.usuario);
				this.idordendetrabajo = this.filtro;
				String[] datoss = (String[]) rrhhothoras.getRrhhordenesdetrabajoPK(this.idordendetrabajo,this.idempresa).get(0);
         		this.ordendetrabajo = datoss[3];

			}

			if (buscar) {
				String[] campos = { "descripcion", "detalle" };
				this.totalRegistros = rrhhothoras.getTotalEntidadOcuXUsuario(
						"vhorasxusuarios", campos, this.ocurrencia,
						this.idempresa, this.usuario, this.idordendetrabajo);
				this.listHorasxusuario = rrhhothoras.getTotalGeneralOcu(
						this.ocurrencia, this.idempresa, this.usuario,
						this.idordendetrabajo);
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
				this.rrhhothorasList = rrhhothoras.getRrhhothorasXUsusarioOcu(
						this.limit, this.offset, this.ocurrencia,
						this.idempresa, this.usuario, this.idordendetrabajo);
			} else {
				this.totalRegistros = rrhhothoras.getTotalEntidadXUsuario(
						"vhorasxusuarios", this.idempresa, this.usuario,
						this.idordendetrabajo);
				this.listHorasxusuario = rrhhothoras.getTotalGeneralAll(
						this.idempresa, this.usuario, this.idordendetrabajo);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.rrhhothorasList = rrhhothoras.getRrhhothorasXUsusarioAll(
						this.limit, this.offset, this.idempresa, this.usuario,
						this.idordendetrabajo);
			}

			if (this.listHorasxusuario != null
					&& !this.listHorasxusuario.isEmpty()) {
				String[] datos = (String[]) this.listHorasxusuario.get(0);
				this.totalHoras = datos[0];
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

	public List getRrhhothorasList() {
		return rrhhothorasList;
	}

	public void setRrhhothorasList(List rrhhothorasList) {
		this.rrhhothorasList = rrhhothorasList;
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

	public BigDecimal getIdothoras() {
		return idothoras;
	}

	public void setIdothoras(BigDecimal idothoras) {
		this.idothoras = idothoras;
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

	public void setTotalHorasxusuario(List listHorasxusuario) {
		this.listHorasxusuario = listHorasxusuario;
	}

	public List getTotalHorasxusuario() {
		return listHorasxusuario;
	}

	public String getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(String totalHoras) {
		this.totalHoras = totalHoras;
	}

	public BigDecimal getIdordendetrabajo() {
		return idordendetrabajo;
	}

	public void setIdordendetrabajo(BigDecimal idordendetrabajo) {
		this.idordendetrabajo = idordendetrabajo;
	}

	public String getOrdendetrabajo() {
		return ordendetrabajo;
	}

	public void setOrdendetrabajo(String ordendetrabajo) {
		this.ordendetrabajo = ordendetrabajo;
	}

	public BigDecimal getFiltro() {
		return filtro;
	}

	public void setFiltro(BigDecimal filtro) {
		this.filtro = filtro;
	}

}
