/* 
 javabean para la entidad: proveedoMovProv
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jul 27 09:30:45 GMT-03:00 2006 
 
 Para manejar la pagina: proveedoMovProvBuscar.jsp
 
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

public class BeanProveedoMovProvBuscar implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanProveedoMovProvBuscar.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private BigDecimal idproveedor = new BigDecimal(0);

	private String dproveedor = "";

	private List proveedoMovProvList = new ArrayList();

	private List saldoImporteList = new ArrayList();

	private BigDecimal saldo = new BigDecimal(0);

	private BigDecimal importe = new BigDecimal(0);

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal nrointerno = BigDecimal.valueOf(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String usuarioact = "";

	private String usuarioalt = "";

	public BeanProveedoMovProvBuscar() {
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
		Proveedores proveedoMovProv = Common.getProveedores();
		try {

			// 20081103 - EJV aseegura limpiar datos de otros comprobantes
			this.request.getSession().removeAttribute("flagCuentas");
			this.request.getSession().removeAttribute("htArticulosConfirmados");
			this.request.getSession().removeAttribute("subTotalGravado");

			this.request.getSession().removeAttribute("htNetoGravado");
			this.request.getSession().removeAttribute("htNetoGravadoConfirma");
			this.request.getSession().removeAttribute("htNetoGravadoUpd");
			this.request.getSession().removeAttribute(
					"htNetoGravadoConfirmaUpd");

			this.request.getSession().removeAttribute("htNetoExento");
			this.request.getSession().removeAttribute("htNetoExentoConfirma");
			this.request.getSession().removeAttribute("htNetoExentoUpd");
			this.request.getSession()
					.removeAttribute("htNetoExentoConfirmaUpd");
			//

			if (this.accion.equalsIgnoreCase("baja")) {
				if (nrointerno.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {

					this.mensaje = proveedoMovProv.anularComprobanteProveedor(
							this.nrointerno, this.idproveedor, this.idempresa,
							this.usuarioact);

					if (mensaje.equalsIgnoreCase("OK")) {
						this.mensaje = "Comprobante Anulado Correctamente.";
					}

				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (nrointerno.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("proveedoMovProvFrmUpd.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("proveedoMovProvFrm.jsp");
				dispatcher.forward(request, response);
				return true;
			}
			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else if (!Common.esEntero(this.ocurrencia)) {
					this.mensaje = "Ingrese un valor numerico para buscar comprobante.";
				} else {
					buscar = true;
				}
			}

			String filtro = " WHERE idproveedor = " + this.idproveedor
					+ " AND tipomov <> 4 ";
			if (buscar) {
				filtro += " AND comprob::VARCHAR LIKE '" + this.ocurrencia + "%' ";
				this.totalRegistros = proveedoMovProv.getTotalEntidadFiltro(
						"proveedoMovProv", filtro, this.idempresa);
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
				this.proveedoMovProvList = proveedoMovProv
						.getComprobantesProveedorOcu(this.limit, this.offset,
								this.idproveedor, this.ocurrencia,
								this.idempresa);
			} else {
				this.totalRegistros = proveedoMovProv.getTotalEntidadFiltro(
						"proveedoMovProv", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.proveedoMovProvList = proveedoMovProv
						.getComprobantesProveedorAll(this.limit, this.offset,
								this.idproveedor, this.idempresa);

			}

			if (this.idproveedor.longValue() > 0) {
				String[] saldoImporte = null;
				saldoImporteList = proveedoMovProv
						.getComprobantesProveedorSaldoImporte(this.idproveedor,
								this.idempresa);
				if (!saldoImporteList.isEmpty()) {
					saldoImporte = (String[]) saldoImporteList.get(0);
					this.saldo = new BigDecimal(Common.getNumeroFormateado(
							Double.parseDouble(saldoImporte[0]), 10, 2));
					this.importe = new BigDecimal(Common.getNumeroFormateado(
							Double.parseDouble(saldoImporte[1]), 10, 2));
				}
			}

			if (this.dproveedor.equals(""))
				this.mensaje = "Seleccionar Proveedor.";
			else if (this.totalRegistros < 1 && this.accion.equals(""))
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

	public List getProveedoMovProvList() {
		return proveedoMovProvList;
	}

	public void setProveedoMovProvList(List proveedoMovProvList) {
		this.proveedoMovProvList = proveedoMovProvList;
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

	public BigDecimal getNrointerno() {
		return nrointerno;
	}

	public void setNrointerno(BigDecimal nrointerno) {
		this.nrointerno = nrointerno;
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

	public BigDecimal getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(BigDecimal idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getDproveedor() {
		return dproveedor;
	}

	public void setDproveedor(String dproveedor) {
		this.dproveedor = dproveedor;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}
}
