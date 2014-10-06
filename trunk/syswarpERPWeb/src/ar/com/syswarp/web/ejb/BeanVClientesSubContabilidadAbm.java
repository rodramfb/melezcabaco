/* 
   javabean para la entidad: vClientesSubContabilidad
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu May 31 10:18:14 ART 2012 
   
   Para manejar la pagina: vClientesSubContabilidadAbm.jsp
      
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

public class BeanVClientesSubContabilidadAbm implements SessionBean,
		Serializable {

	static Logger log = Logger.getLogger(BeanVClientesSubContabilidadAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vClientesSubContabilidadList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private java.sql.Timestamp fechamov;

	private String fechadesde = "";

	private String fechahasta = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private int ejercicioActivo = 0;

	public BeanVClientesSubContabilidadAbm() {
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
		Contable contable = Common.getContable();

		try {

			this.ejercicioActivo = contable.getEjercicioActivo(this.idempresa);

			if (!Common.isFormatoFecha(this.fechadesde)
					|| !Common.isFechaValida(this.fechadesde)) {

				this.mensaje = "Ingrese fecha desde.";

			} else if (!Common.isFormatoFecha(this.fechahasta)
					|| !Common.isFechaValida(this.fechahasta)) {

				this.mensaje = "Ingrese fecha hasta.";

			} else if (((java.util.Date) Common.setObjectToStrOrTime(
					this.fechadesde, "StrToJUDate"))
					.after(((java.util.Date) Common.setObjectToStrOrTime(
							this.fechahasta, "StrToJUDate")))) {

				this.mensaje = "Fecha desde debe ser menor o igual a fecha hasta.";

			} else {

				// java.util.Date fdesde = (java.util.Date) Common
				// .setObjectToStrOrTime(this.fechadesde, "StrToJUDate") ;
				// java.util.Date fhasta = (java.util.Date) Common
				// .setObjectToStrOrTime(this.fechahasta, "StrToJUDate");

				String filtro = " WHERE ejercicio = " + this.ejercicioActivo
						+ " AND fechamov BETWEEN TO_DATE('" + this.fechadesde
						+ "', 'dd/mm/yyyy') AND TO_DATE('" + this.fechahasta
						+ "', 'dd/mm/yyyy') ";

				this.totalRegistros = contable.getTotalEntidadFiltro(
						"vClientesSubContabilidad", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;

				}

				this.vClientesSubContabilidadList = contable
						.getVClientesSubContabilidadAll(this.ejercicioActivo,
								this.fechadesde, this.fechahasta,
								this.idempresa);
			}

			if (this.totalRegistros < 1 && this.mensaje.equals(""))
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

	public List getVClientesSubContabilidadList() {
		return vClientesSubContabilidadList;
	}

	public void setVClientesSubContabilidadList(
			List vClientesSubContabilidadList) {
		this.vClientesSubContabilidadList = vClientesSubContabilidadList;
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

	public java.sql.Timestamp getFechamov() {
		return fechamov;
	}

	public void setFechamov(java.sql.Timestamp fechamov) {
		this.fechamov = fechamov;
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(String fechadesde) {
		this.fechadesde = fechadesde;
	}

	public String getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(String fechahasta) {
		this.fechahasta = fechahasta;
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
