/* 
   javabean para la entidad: cajaMovTesoPagos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon May 18 13:11:15 GYT 2009 
   
   Para manejar la pagina: cajaMovTesoPagosAbm.jsp
      
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

public class BeanCajaMovTesoPagosAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanCajaMovTesoPagosAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List cajaMovTesoPagosList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String pago = "";

	private BigDecimal idproveedor = new BigDecimal(-1);

	private BigDecimal sucursal = new BigDecimal(-1);

	private BigDecimal comprobante = new BigDecimal(-1);

	private String mensaje = "";

	private String usuarioalt = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private java.sql.Timestamp fechatesoreria = null;

	private java.sql.Timestamp fechaultimocierreteso = null;

	private java.sql.Timestamp fechaultimocierreproveed = null;

	private String fechamo_mt = "";

	public BeanCajaMovTesoPagosAbm() {
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

		Tesoreria tesoreria = Common.getTesoreria();
		try {
			if (this.accion.equalsIgnoreCase("baja")) {

				java.sql.Timestamp fmov = java.sql.Timestamp
						.valueOf(this.fechamo_mt);
				String fUltCierreTeso = Common.getGeneral()
						.getValorSetupVariablesNoStatic(
								"tesoFechaUltimoCierreTesoreria",
								this.idempresa);

				String fUltCierreProv = Common.getGeneral()
						.getValorSetupVariablesNoStatic(
								"provFechaUltimoCierre", this.idempresa);

				this.fechatesoreria = Common.getTesoFechaCaja(idempresa);

				if (Common.isFormatoFecha(fUltCierreTeso)
						&& Common.isFechaValida(fUltCierreTeso))
					this.fechaultimocierreteso = (java.sql.Timestamp) Common
							.setObjectToStrOrTime(fUltCierreTeso, "StrToJSTs");

				if (Common.isFormatoFecha(fUltCierreProv)
						&& Common.isFechaValida(fUltCierreProv))
					this.fechaultimocierreproveed = (java.sql.Timestamp) Common
							.setObjectToStrOrTime(fUltCierreProv, "StrToJSTs");

				if (this.fechatesoreria == null) {

					this.mensaje = "Fecha de tesoreria no declarada o mal definida.";

				} else if (this.fechaultimocierreteso == null) {

					this.mensaje = "Fecha de ultimo cierre de tesoreria no declarada o mal definida.";

				} else if (this.fechaultimocierreproveed == null) {

					this.mensaje = "Fecha de ultimo cierre de proveedores no declarada o mal definida.";

				} else if (fmov.before(this.fechaultimocierreteso)) {

					this.mensaje = "No es posible eliminar el registro seleccionado, fecha del mismo es menor al ultimo cierre de tesoreria.";

				} else if (sucursal.longValue() < 0
						|| comprobante.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {

					this.mensaje = tesoreria.pagosCajaMovTesoAnular(
							this.sucursal, this.comprobante,
							this.fechatesoreria, this.fechaultimocierreproveed,
							this.idproveedor, this.usuarioalt, this.idempresa);

					if (this.mensaje.equalsIgnoreCase("OK")) {

						this.mensaje = "Comprobante de pagos Nro.: "
								+ Common.strZero(this.sucursal.toString(), 4)
								+ "-"
								+ Common
										.strZero(this.comprobante.toString(), 8)
								+ ", eliminado correctamente.";

					}
				}
			}

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			if (buscar) {
				String[] campos = { "pago", "razon_social" };
				this.totalRegistros = tesoreria.getTotalEntidadOcu(
						"vcajaMovTesoPagos", campos, this.ocurrencia,
						this.idempresa);
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
				this.cajaMovTesoPagosList = tesoreria.getCajaMovTesoPagosOcu(
						this.limit, this.offset, this.ocurrencia,
						this.idempresa);
			} else {
				this.totalRegistros = tesoreria.getTotalEntidad(
						"vcajaMovTesoPagos", this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.cajaMovTesoPagosList = tesoreria.getCajaMovTesoPagosAll(
						this.limit, this.offset, this.idempresa);
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

	public List getCajaMovTesoPagosList() {
		return cajaMovTesoPagosList;
	}

	public void setCajaMovTesoPagosList(List cajaMovTesoPagosList) {
		this.cajaMovTesoPagosList = cajaMovTesoPagosList;
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

	public BigDecimal getSucursal() {
		return sucursal;
	}

	public void setSucursal(BigDecimal sucursal) {
		this.sucursal = sucursal;
	}

	public BigDecimal getComprobante() {
		return comprobante;
	}

	public void setComprobante(BigDecimal comprobante) {
		this.comprobante = comprobante;
	}

	public String getPago() {
		return pago;
	}

	public void setPago(String pago) {
		this.pago = pago;
	}

	public BigDecimal getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(BigDecimal idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
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

	public String getFechamo_mt() {
		return fechamo_mt;
	}

	public void setFechamo_mt(String fechamo_mt) {
		this.fechamo_mt = fechamo_mt;
	}
}
