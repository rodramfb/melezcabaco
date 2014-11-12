/* 
   javabean para la entidad: globalBlobImagenes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 17 11:33:18 GYT 2009 
   
   Para manejar la pagina: globalBlobImagenesAbm.jsp
      
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
import ar.com.syswarp.servlet.upload.*;
import ar.com.syswarp.api.Common;

public class BeanGlobalBlobImagenesAbm implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanGlobalBlobImagenesAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List globalBlobImagenesList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal tupla = new BigDecimal(-1);

	private BigDecimal trama = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private boolean soloImagen = false;

	public BeanGlobalBlobImagenesAbm() {
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
		General globalBlobImagenes = Common.getGeneral();
		try {

			if (this.accion.equalsIgnoreCase("baja")) {
				if (tupla.longValue() < 0 || trama.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = globalBlobImagenes.globalBlobImagenesDelete(
							this.tupla, this.trama, this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (tupla.longValue() < 0 || trama.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("globalBlobImagenesFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request.getRequestDispatcher("uploadFileBlob.jsp");
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

			if (this.tupla == null || this.tupla.longValue() < 0)
				this.tupla = new BigDecimal(0);

			String filtro = "WHERE tupla = " + this.tupla;
			String entidad = "( SELECT * FROM globalBlobImagenes )BlobImagenes ";
			long principal = globalBlobImagenes.getTotalEntidadFiltro(entidad,
					filtro + " AND principal = 'S'", this.idempresa);

			if (buscar) {

				filtro += " AND UPPER(descripcion) LIKE '%"
						+ this.ocurrencia.toUpperCase() + "%'";
				this.totalRegistros = globalBlobImagenes.getTotalEntidadFiltro(
						entidad, filtro, this.idempresa);
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
				this.globalBlobImagenesList = globalBlobImagenes
						.getGlobalBlobImagenesOcu(this.limit, this.offset,
								this.tupla, this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = globalBlobImagenes.getTotalEntidadFiltro(
						entidad, filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.globalBlobImagenesList = globalBlobImagenes
						.getGlobalBlobImagenesAll(this.limit, this.offset,
								this.tupla, this.idempresa);
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
			else if (principal == 0)
				this.mensaje += " - ATENCION: Aun no se designo imagen principal.";

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

	public List getGlobalBlobImagenesList() {
		return globalBlobImagenesList;
	}

	public void setGlobalBlobImagenesList(List globalBlobImagenesList) {
		this.globalBlobImagenesList = globalBlobImagenesList;
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

	public BigDecimal getTupla() {
		return tupla;
	}

	public void setTupla(BigDecimal tupla) {
		this.tupla = tupla;
	}

	public BigDecimal getTrama() {
		return trama;
	}

	public void setTrama(BigDecimal trama) {
		this.trama = trama;
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

	public boolean isSoloImagen() {
		return soloImagen;
	}

	public void setSoloImagen(boolean soloImagen) {
		this.soloImagen = soloImagen;
	}
}
