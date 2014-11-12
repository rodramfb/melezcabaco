/* 
   javabean para la entidad: tickets
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Sep 20 11:56:06 ART 2012 
   
   Para manejar la pagina: ticketsAbm.jsp
      
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

public class BeanTicketsAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanTicketsAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal idgrupo = new BigDecimal(-1);

	private BigDecimal idusuario = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List ticketsList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idticket = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String usuarioalt = "";

	private boolean enviaCorreo = true;
	
	boolean buscar = false;

	public BeanTicketsAbm() {
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
		General general = Common.getGeneral();

		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idticket.longValue() < 0 || idticket == null) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = general.ticketsDelete(idticket,
							this.idempresa,this.enviaCorreo);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idticket.longValue() < 0 || idticket == null) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request.getRequestDispatcher("ticketsFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request.getRequestDispatcher("ticketsFrm.jsp");
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
				String entidad = "(SELECT ti.idticket,gp.grupo,gu.usuario,cli.razon,ti.resumen,ti.descripcion,"
						+ " te.ticketestado,ti.idempresa,te.color_fondo,ti.usuarioalt,ti.usuarioact,ti.fechaalt,ti.fechaact"
						+ " FROM TICKETS ti"
						+ " left join globalgrupos gp on (ti.idgrupo = gp.idgrupo)"
						+ " left join globalusuarios gu on (ti.idusuario = gu.idusuario and ti.idempresa = gu.idempresa)"
						+ " left join globalusuariosgrupos gup on (ti.idgrupo = gup.idgrupo and ti.idempresa = gu.idempresa)"
						+ " inner join clientesclientes cli on (ti.idcliente = cli.idcliente and ti.idempresa = cli.idempresa )"
						+ " inner join ticketsestados te on (ti.idticketestado = te.idticketestado  and ti.idempresa = te.idempresa)"
						+ " WHERE (ti.idticket::varchar LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%') or (upper (gp.grupo) like '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%') or (upper (gu.usuario) like '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%') AND ti.idempresa = "
						+ idempresa.toString()
						+ ")entidad";
				String filtro = "Where idempresa = " + idempresa.toString()
						+ " and (idticket::varchar LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%') or (upper (grupo) like '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%') or (upper (usuario) like '%"
						+ ocurrencia.toUpperCase().trim() + "%') ";
				this.totalRegistros = general.getTotalEntidadFiltro(entidad,
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
				this.ticketsList = general.getTicketsOcu(this.limit,
						this.offset, this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = general.getTotalEntidad("tickets");
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.ticketsList = general.getTicketsAll(this.limit,
						this.offset, this.idempresa);
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

	public List getTicketsList() {
		return ticketsList;
	}

	public void setTicketsList(List ticketsList) {
		this.ticketsList = ticketsList;
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

	public BigDecimal getIdticket() {
		return idticket;
	}

	public void setIdticket(BigDecimal idticket) {
		this.idticket = idticket;
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

	public BigDecimal getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(BigDecimal idgrupo) {
		this.idgrupo = idgrupo;
	}

	public BigDecimal getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(BigDecimal idusuario) {
		this.idusuario = idusuario;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public boolean isEnviaCorreo() {
		return enviaCorreo;
	}

	public void setEnviaCorreo(boolean enviaCorreo) {
		this.enviaCorreo = enviaCorreo;
	}

}
