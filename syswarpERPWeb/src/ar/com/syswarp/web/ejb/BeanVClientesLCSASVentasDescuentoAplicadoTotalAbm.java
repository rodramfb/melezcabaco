/* 
   javabean para la entidad: vClientesLCSASVentasDescuentoAplicadoTotal
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu May 03 14:13:54 ART 2012 
   
   Para manejar la pagina: vClientesLCSASVentasDescuentoAplicadoTotalAbm.jsp
      
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

public class BeanVClientesLCSASVentasDescuentoAplicadoTotalAbm implements
		SessionBean, Serializable {

	static Logger log = Logger
			.getLogger(BeanVClientesLCSASVentasDescuentoAplicadoTotalAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vClientesLCSASVentasDescuentoAplicadoTotalList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private Double porcdesc_apli;

	private String mensaje = "";

	private int anioActual = 0;

	private int anio = 0;

	private int mes = 0;

	private BigDecimal idtipoclie = new BigDecimal(-1);

	private BigDecimal idestado = new BigDecimal(-1);

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private List listMeses = new ArrayList();

	private List listTipoCliente = new ArrayList();

	private List listEstados = new ArrayList();

	public BeanVClientesLCSASVentasDescuentoAplicadoTotalAbm() {
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

			this.anioActual = Calendar.getInstance().get(Calendar.YEAR);

			this.listEstados = Common.getClientes().getClientesestadosAll(500,
					0, this.idempresa);

			this.listMeses = Common.getGeneral().getGlobalMeses();

			this.listTipoCliente = Common.getClientes().getClientestipoclieAll(
					500, 0, this.idempresa);

			if (this.mes == 0) {
				this.mensaje = "Es necesario seleccionar mes..";
			} else if (this.anio == 0) {
				this.mensaje = "Es necesario seleccionar ano.";
			} else if (this.idtipoclie.longValue() < 0) {
				this.mensaje = "Es necesario seleccionar tipo de cliente.";
			} else if (this.idestado.longValue() < 0) {
				this.mensaje = "Es necesario seleccionar estado.";
			} else {

				String filtro = " WHERE anio = " + this.anio + " AND  mes = "
						+ this.mes + " AND idtipoclie = "
						+ this.idtipoclie.toString() + "  AND  idestado = "
						+ this.idestado.toString();

				this.totalRegistros = clientes.getTotalEntidadFiltro(
						"vClientesLCSASVentasDescuentoAplicadoTotal", filtro,
						this.idempresa);

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.vClientesLCSASVentasDescuentoAplicadoTotalList = clientes
						.getVClientesLCSASVentasDescuentoAplicadoTotalAll(
								this.limit, this.offset, this.anio, this.mes,
								this.idtipoclie, this.idestado, this.idempresa);
			}

			if (this.totalRegistros < 1
					&& Common.setNotNull(this.mensaje).equals(""))
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

	public List getVClientesLCSASVentasDescuentoAplicadoTotalList() {
		return vClientesLCSASVentasDescuentoAplicadoTotalList;
	}

	public void setVClientesLCSASVentasDescuentoAplicadoTotalList(
			List vClientesLCSASVentasDescuentoAplicadoTotalList) {
		this.vClientesLCSASVentasDescuentoAplicadoTotalList = vClientesLCSASVentasDescuentoAplicadoTotalList;
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

	public Double getPorcdesc_apli() {
		return porcdesc_apli;
	}

	public void setPorcdesc_apli(Double porcdesc_apli) {
		this.porcdesc_apli = porcdesc_apli;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getAnioActual() {
		return anioActual;
	}

	public void setAnioActual(int anioActual) {
		this.anioActual = anioActual;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public BigDecimal getIdtipoclie() {
		return idtipoclie;
	}

	public void setIdtipoclie(BigDecimal idtipoclie) {
		this.idtipoclie = idtipoclie;
	}

	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
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

	public List getListMeses() {
		return listMeses;
	}

	public List getListTipoCliente() {
		return listTipoCliente;
	}

	public List getListEstados() {
		return listEstados;
	}

}
