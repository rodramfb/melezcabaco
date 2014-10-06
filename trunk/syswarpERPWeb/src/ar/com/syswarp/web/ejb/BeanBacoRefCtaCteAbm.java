/* 
   javabean para la entidad: bacoRefCtaCte
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 17 12:34:05 ART 2010 
   
   Para manejar la pagina: bacoRefCtaCteAbm.jsp
      
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

public class BeanBacoRefCtaCteAbm implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanBacoRefCtaCteAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List bacoRefCtaCteList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idctacte = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String idcliente = "";

	private String cliente = "";

	private String estado = "";

	private String tipodocumento = "";

	private String nrodocumento = "";

	private String tipocliente = "";

	boolean buscar = false;
	
	private BigDecimal totalDisponible = new BigDecimal(0);

	public BeanBacoRefCtaCteAbm() {
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
				if (idctacte.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = clientes.bacoRefCtaCteDelete(idctacte,
							this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idctacte.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("bacoRefCtaCteFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("bacoRefCtaCteFrm.jsp");
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

			if (Common.esEntero(this.idcliente)) {

				this.getDatosCliente();

				String filtro = "WHERE idcliente =" + this.idcliente;
				if (buscar) {

					this.totalRegistros = clientes.getTotalEntidadFiltro(
							"bacoRefCtaCte", filtro, this.idempresa);
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
					this.bacoRefCtaCteList = clientes.getBacoRefCtaCteOcu(
							this.limit, this.offset, this.ocurrencia,
							new BigDecimal(this.idcliente), this.idempresa);
				} else {
					this.totalRegistros = clientes.getTotalEntidadFiltro(
							"bacoRefCtaCte", filtro, this.idempresa);
					this.totalPaginas = (this.totalRegistros / this.limit) + 1;
					if (this.totalPaginas < this.paginaSeleccion)
						this.paginaSeleccion = this.totalPaginas;
					this.offset = (this.paginaSeleccion - 1) * this.limit;
					if (this.totalRegistros == this.limit) {
						this.offset = 0;
						this.totalPaginas = 1;
					}
					this.bacoRefCtaCteList = clientes.getBacoRefCtaCteAll(
							this.limit, this.offset, new BigDecimal(
									this.idcliente), this.idempresa);
				}

				if (this.totalRegistros < 1 && this.mensaje.equals(""))
					this.mensaje = "No existen registros.";

			} else {

				this.mensaje = "Ingrese un cliente válido para consultar la cuenta corriente.";

			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	private void getDatosCliente() {

		try {

			List listClientes = Common.getClientes().getClientesClientesONE(
					new BigDecimal(this.idcliente), this.idempresa);

			if (listClientes != null && !listClientes.isEmpty()) {

				String[] datos = (String[]) listClientes.get(0);
				this.estado = datos[3];
				this.tipodocumento = datos[5];
				this.nrodocumento = datos[6];
				this.tipocliente = datos[21];
				this.totalDisponible = Common.getClientes().getBacoRefCtaCtePuntosCliente(new BigDecimal(this.idcliente), this.idempresa);

			} else
				this.mensaje = "No fue posible recuperar datos del cliente.";

		} catch (Exception e) {
			log.error("getDatosCliente(): " + e);
		}

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

	public List getBacoRefCtaCteList() {
		return bacoRefCtaCteList;
	}

	public void setBacoRefCtaCteList(List bacoRefCtaCteList) {
		this.bacoRefCtaCteList = bacoRefCtaCteList;
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

	public BigDecimal getIdctacte() {
		return idctacte;
	}

	public void setIdctacte(BigDecimal idctacte) {
		this.idctacte = idctacte;
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

	public String getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(String idcliente) {
		this.idcliente = idcliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public String getTipocliente() {
		return tipocliente;
	}

	public void setTipocliente(String tipocliente) {
		this.tipocliente = tipocliente;
	}

	public BigDecimal getTotalDisponible() {
		return totalDisponible;
	}
	
	

}
