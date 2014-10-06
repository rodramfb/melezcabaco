/* 
   javabean para la entidad: rrhhliq_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 26 14:23:25 ART 2012 
   
   Para manejar la pagina: rrhhliq_cabeAbm.jsp
      
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

public class BeanRrhhliq_cabeAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanRrhhliq_cabeAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List rrhhliq_cabeList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idliqcabe = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanRrhhliq_cabeAbm() {
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
		RRHH rrhh = Common.getRrhh();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {
				if (idliqcabe == null || idliqcabe.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = rrhh.rrhhliq_cabeDelete(idliqcabe,
							this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idliqcabe == null || idliqcabe.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("rrhhliq_cabeFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("rrhhliq_cabeFrm.jsp");
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
				
				
				String entidad = "(SELECT  liq.idliqcabe,liq.nrorecibo,liq.legajo, per.apellido, liq.fecha, liq.anioliq,liq.mesliq,liq.nroquincena, "
						+ "liq.idcategoria,cat.categoria,liq.idlocalidadpago, loc.localidad,liq.idbancodeposito, caja.descripcion,liq.idmodalidadcontrato,mod.modalidadcontrato, "
						+ "liq.fechadeposito,liq.importesueldo,liq.totalremunerativo,liq.totalnoremunerativo, "
						+ "liq.totaldescuentos,liq.netoacobrar,liq.usuarioalt,liq.usuarioact,liq.fechaalt,liq.fechaact,liq.idempresa "
						+ "FROM RRHHLIQ_CABE liq "
						+ "inner join rrhhpersonal per on (liq.legajo = per.legajo and liq.idempresa = per.idempresa) "
						+ "inner join rrhhcategorias cat on (liq.idcategoria = cat.idcategoria and liq.idempresa= cat.idempresa) "
						+ "inner join globallocalidades loc on (liq.idlocalidadpago = loc.idlocalidad) "
						+ "inner join cajaidentificadores caja on (liq.idbancodeposito = caja.ididentificador and liq.idempresa = caja.idempresa) "
						+ "inner join rrhhmodalidadcontrato mod on (liq.idmodalidadcontrato = mod.idmodalidadcontrato and liq.idempresa = mod.idempresa) "
						+ " WHERE (liq.NRORECIBO::varchar like'%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' or liq.legajo::varchar like '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' or  liq.idliqcabe::varchar like '%"+ocurrencia.toUpperCase().trim()+"%')  AND liq.idempresa = "
						+ idempresa.toString()
						+ ")entidad";
				String filtro = " where idempresa = "
						+ this.idempresa.toString() 
						+ " and (NRORECIBO::varchar like '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' or legajo::varchar like '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' or  idliqcabe::varchar like '%"+ocurrencia.toUpperCase().trim()+"%')";
				
				this.totalRegistros = rrhh.getTotalEntidadFiltro(entidad,
						filtro, this.idempresa);
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
				this.rrhhliq_cabeList = rrhh.getRrhhliq_cabeOcu(this.limit,
						this.offset, this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = rrhh.getTotalEntidad("rrhhliq_cabe",
						this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.rrhhliq_cabeList = rrhh.getRrhhliq_cabeAll(this.limit,
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

	public List getRrhhliq_cabeList() {
		return rrhhliq_cabeList;
	}

	public void setRrhhliq_cabeList(List rrhhliq_cabeList) {
		this.rrhhliq_cabeList = rrhhliq_cabeList;
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

	public BigDecimal getIdliqcabe() {
		return idliqcabe;
	}

	public void setIdliqcabe(BigDecimal idliqcabe) {
		this.idliqcabe = idliqcabe;
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
