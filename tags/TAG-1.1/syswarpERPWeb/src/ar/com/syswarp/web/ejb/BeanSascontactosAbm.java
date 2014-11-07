/* 
   javabean para la entidad: sascontactos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri May 27 12:30:35 ART 2011 
   
   Para manejar la pagina: sascontactosAbm.jsp
      
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

public class BeanSascontactosAbm implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanSascontactosAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List sascontactosList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idcontacto = new BigDecimal(-1);

	// 20121108 - EJV - Mantis 894-->

	private String cliente = "";

	// <--

	private BigDecimal idcliente = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanSascontactosAbm() {
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
				if (idcontacto == null || idcontacto.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = clientes.sascontactosDelete(idcontacto,
							this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idcontacto == null || idcontacto.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("sascontactosFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("sascontactosFrm.jsp");
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
				String entidad = "(SELECT sc.idcontacto,sc.descripcion,sc.idtipocontacto,tc.tipocontacto,sc.idcanalcontacto,cc.canalcontacto,sc.idmotivocontacto,mc.motivocontacto,sc.idcliente,cl.razon,sc.usuarioalt,sc.usuarioact,sc.fechaalt,sc.fechaact,sc.idempresa FROM SASCONTACTOS sc inner join sastiposcontactos tc on (sc.idtipocontacto = tc.idtipocontacto) inner join sascanalescontactos cc on (sc.idcanalcontacto = cc.idcanalcontacto) inner join sasmotivoscontactos mc on (sc.idmotivocontacto = mc.idmotivocontacto) inner join clientesclientes cl on (sc.idcliente = cl.idcliente)  WHERE (UPPER(sc.DESCRIPCION) LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' or upper(tc.tipocontacto) like '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' or upper(cl.razon) like '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%')  AND sc.idempresa = "
						+ this.idempresa.toString()
						+ " )entidad";
				String filtro = " Where idempresa = " + this.idempresa;
				filtro += " and (UPPER(DESCRIPCION) LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' or upper(tipocontacto) like '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' or upper(razon) like '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%') AND idcliente = " + idcliente;
				this.totalRegistros = clientes.getTotalEntidadFiltro(entidad,
						filtro, idempresa);
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
				this.sascontactosList = clientes.getSascontactosOcu(this.limit,
						this.offset, this.idcliente, this.ocurrencia,
						this.idempresa);
			} else {

				this.totalRegistros = clientes.getTotalEntidadFiltro(
						"sascontactos", " WHERE idcliente = " + this. idcliente,
						this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.sascontactosList = clientes.getSascontactosAll(this.limit,
						this.offset, this.idcliente, this.idempresa);
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

	public List getSascontactosList() {
		return sascontactosList;
	}

	public void setSascontactosList(List sascontactosList) {
		this.sascontactosList = sascontactosList;
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

	public BigDecimal getIdcontacto() {
		return idcontacto;
	}

	public void setIdcontacto(BigDecimal idcontacto) {
		this.idcontacto = idcontacto;
	}

	// 20121108 - EJV - Mantis 894-->

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	// <--

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
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
}
