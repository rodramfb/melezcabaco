/* 
   javabean para la entidad: vClientesRemitosTotalCondicion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 04 15:02:55 ART 2011 
   
   Para manejar la pagina: vClientesRemitosTotalCondicionAbm.jsp
      
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

public class BeanVClientesRemitosTotalCondicion implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanVClientesRemitosTotalCondicion.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vClientesRemitosTotalCondicionList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idzona;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private int mes = 0;

	private int ano = 0;

	private int idestado = 1;

	private String fecha = "";

	public BeanVClientesRemitosTotalCondicion() {
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

			if (Common.setNotNull(this.fecha).equals("")) {
				this.mensaje = "Ese necesario elegir periodo.";

			} else if (!Common.isFormatoFecha(this.fecha)
					|| !Common.isFechaValida(this.fecha)) {

				this.mensaje = "Ese necesario elegir periodo valido.";

			} else {

				String filtro = " WHERE idestado = " + this.idestado;
				Calendar cal = new GregorianCalendar();
				cal.setTime((java.util.Date) Common.setObjectToStrOrTime(
						this.fecha, "StrToJUDate"));

				this.mes = cal.get(Calendar.MONTH) + 1;
				this.ano = cal.get(Calendar.YEAR);

				filtro += " AND mes = " + this.mes + " AND ano = " + this.ano;

				this.totalRegistros = clientes.getTotalEntidadFiltro(
						"vClientesRemitosTotalCondicion", filtro,
						this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.vClientesRemitosTotalCondicionList = clientes
						.getVClientesRemitosTotalCondicionFleteAll(this.limit,
								this.offset, this.mes, this.ano, this.idestado,
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

	public List getVClientesRemitosTotalCondicionList() {
		return vClientesRemitosTotalCondicionList;
	}

	public void setVClientesRemitosTotalCondicionList(
			List vClientesRemitosTotalCondicionList) {
		this.vClientesRemitosTotalCondicionList = vClientesRemitosTotalCondicionList;
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

	public BigDecimal getIdzona() {
		return idzona;
	}

	public void setIdzona(BigDecimal idzona) {
		this.idzona = idzona;
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

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getIdestado() {
		return idestado;
	}

	public void setIdestado(int idestado) {
		this.idestado = idestado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
