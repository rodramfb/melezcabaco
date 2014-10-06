/* 
 javabean para la entidad: globalImagenes
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Mar 10 10:51:29 ART 2008 
 
 Para manejar la pagina: globalImagenesAbm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.File;
import java.io.InputStream;
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
import java.net.URI;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.servlet.upload.UploadFicheroNew;
import ar.com.syswarp.api.Common;

public class BeanGlobalImagenesAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanGlobalImagenesAbm.class);

	private BigDecimal idempresa = new BigDecimal(-1);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List globalImagenesList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idimagen;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanGlobalImagenesAbm() {
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
		General globalImagenes = Common.getGeneral();

		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idimagen.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {

					String[] image = (String[]) globalImagenes
							.getGlobalImagenesPK(idimagen, idempresa).get(0);
					InputStream is = UploadFicheroNew.class
							.getResourceAsStream("upload.properties");
					Properties props = new Properties();
					props.load(is);
					String path = props.getProperty("upload.path").replaceAll(
							"MODULO", "general");

					File file = new File(path
							+ image[2].substring(image[2].lastIndexOf("/")));
					if (file.exists())
						file.delete();

					this.mensaje = globalImagenes
							.globalImagenesDelete(this.idimagen, this.idempresa);

				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idimagen.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("globalImagenesFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("globalImagenesFrm.jsp");
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
				String[] campos = { "idimagen", "descripcion" };
				this.totalRegistros = globalImagenes.getTotalEntidadOcu(
						"globalImagenes", campos, this.ocurrencia);
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
				this.globalImagenesList = globalImagenes.getGlobalImagenesOcu(
						this.limit, this.offset, this.ocurrencia,
						this.idempresa);
			} else {
				this.totalRegistros = globalImagenes
						.getTotalEntidad("globalImagenes");
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.globalImagenesList = globalImagenes.getGlobalImagenesAll(
						this.limit, this.offset, this.idempresa);
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

	public List getGlobalImagenesList() {
		return globalImagenesList;
	}

	public void setGlobalImagenesList(List globalImagenesList) {
		this.globalImagenesList = globalImagenesList;
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

	public BigDecimal getIdimagen() {
		return idimagen;
	}

	public void setIdimagen(BigDecimal idimagen) {
		this.idimagen = idimagen;
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
}
