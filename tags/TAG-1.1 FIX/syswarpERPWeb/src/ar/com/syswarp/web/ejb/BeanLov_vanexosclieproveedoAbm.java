/* 
 javabean para la entidad: lov_vanexosclieproveedo
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Oct 27 14:42:47 GMT-03:00 2006 
 
 Para manejar la pagina: lov_vanexosclieproveedoAbm.jsp
 
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

public class BeanLov_vanexosclieproveedoAbm implements SessionBean,
		Serializable {
	static Logger log = Logger.getLogger(BeanLov_vanexosclieproveedoAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List lov_vanexosclieproveedoList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal codigo_anexo;

	private String tipo = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanLov_vanexosclieproveedoAbm() {
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
		Stock lov_vanexosclieproveedo = Common.getStock();
		try {

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			if (buscar) {

				if (tipo.equalsIgnoreCase("C")) {
					String[] campos = { "idcliente", "razon" };
					this.totalRegistros = lov_vanexosclieproveedo
							.getTotalEntidadOcu("clientesclientes", campos,
									this.ocurrencia, this.idempresa);
				} else if (tipo.equalsIgnoreCase("P")) {
					String[] campos = { "idproveedor", "razon_social" };
					this.totalRegistros = lov_vanexosclieproveedo
							.getTotalEntidadOcu("proveedoproveed", campos,
									this.ocurrencia, this.idempresa);
				}
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
				this.lov_vanexosclieproveedoList = lov_vanexosclieproveedo
						.getLov_vanexosclieproveedoOcu(this.limit, this.offset,
								tipo, this.ocurrencia, this.idempresa);
			} else {
				if (tipo.equalsIgnoreCase("C")) {
					this.totalRegistros = lov_vanexosclieproveedo
							.getTotalEntidad("clientesclientes", this.idempresa);
				} else if (tipo.equalsIgnoreCase("P")) {
					this.totalRegistros = lov_vanexosclieproveedo
							.getTotalEntidad("proveedoproveed", this.idempresa);
				}

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.lov_vanexosclieproveedoList = lov_vanexosclieproveedo
						.getLov_vanexosclieproveedoAll(this.limit, this.offset,
								tipo, this.idempresa);
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

	public List getLov_vanexosclieproveedoList() {
		return lov_vanexosclieproveedoList;
	}

	public void setLov_vanexosclieproveedoList(List lov_vanexosclieproveedoList) {
		this.lov_vanexosclieproveedoList = lov_vanexosclieproveedoList;
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

	public BigDecimal getCodigo_anexo() {
		return codigo_anexo;
	}

	public void setCodigo_anexo(BigDecimal codigo_anexo) {
		this.codigo_anexo = codigo_anexo;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
