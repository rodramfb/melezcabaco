/* 
 javabean para la entidad: stockDesarmadoLog
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Dec 26 14:49:56 ART 2007 
 
 Para manejar la pagina: stockDesarmadoLogAbm.jsp
 
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

public class BeanStockDesarmadoLogAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanStockDesarmadoLogAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List stockDesarmadoLogList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idtransacciondesarmado;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String fechadesde = "";

	private String fechahasta = "";

	boolean buscar = false;

	public BeanStockDesarmadoLogAbm() {
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
		Stock stockDesarmadoLog = Common.getStock();
		try {

			if (!this.fechadesde.trim().equals("")
					&& fechahasta.trim().equals("")) {
				this.mensaje = "Ingrese Fecha Hasta.";

			} else if (this.fechadesde.trim().equals("")
					&& !fechahasta.trim().equals("")) {
				this.mensaje = "Ingrese Fecha Desde.";

			}
			if (!this.fechadesde.trim().equals("")
					&& !fechahasta.trim().equals("")) {
				buscar = true;
			}

			if (buscar) {

				log.info("BUSCAR");
				java.sql.Timestamp fdesde = (java.sql.Timestamp) Common
						.setObjectToStrOrTime(this.fechadesde, "StrToJSTs");
				java.sql.Timestamp fhasta = (java.sql.Timestamp) Common
						.setObjectToStrOrTime(this.fechahasta + " 23:59",
								"strToJSTsWithHM");

				String filtro = " WHERE fechaalt BETWEEN '" + fdesde
						+ "' AND '" + fhasta + "'";
				
				this.totalRegistros = stockDesarmadoLog.getTotalEntidadFiltro(
						"stockDesarmadoLog", filtro, this.idempresa);
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

				this.stockDesarmadoLogList = stockDesarmadoLog
						.getStockDesarmadoLogFecha(this.limit, this.offset,
								fdesde, fhasta, this.idempresa);
			} else {
				this.totalRegistros = stockDesarmadoLog.getTotalEntidad(
						"stockDesarmadoLog", this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.stockDesarmadoLogList = stockDesarmadoLog
						.getStockDesarmadoLogAll(this.limit, this.offset,
								this.idempresa);
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

	public List getStockDesarmadoLogList() {
		return stockDesarmadoLogList;
	}

	public void setStockDesarmadoLogList(List stockDesarmadoLogList) {
		this.stockDesarmadoLogList = stockDesarmadoLogList;
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

	public BigDecimal getIdtransacciondesarmado() {
		return idtransacciondesarmado;
	}

	public void setIdtransacciondesarmado(BigDecimal idtransacciondesarmado) {
		this.idtransacciondesarmado = idtransacciondesarmado;
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

	public String getFechadesde() {
		return fechadesde;
	}

	public String getFechahasta() {
		return fechahasta;
	}

	public void setFechadesde(String fechadesde) {
		this.fechadesde = fechadesde;
	}

	public void setFechahasta(String fechahasta) {
		this.fechahasta = fechahasta;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
