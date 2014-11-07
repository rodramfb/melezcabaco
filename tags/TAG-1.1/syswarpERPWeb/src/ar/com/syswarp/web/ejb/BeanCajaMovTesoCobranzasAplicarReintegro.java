/* 
   javabean para la entidad: cajaMovTesoCobranzas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed May 13 11:50:20 GYT 2009 
   
   Para manejar la pagina: cajaMovTesoCobranzasAbm.jsp
      
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

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

public class BeanCajaMovTesoCobranzasAplicarReintegro implements SessionBean,
		Serializable {

	static Logger log = Logger
			.getLogger(BeanCajaMovTesoCobranzasAplicarReintegro.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List cajaMovTesoCobranzasList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal sucursal = new BigDecimal(-1);

	private BigDecimal comprobante = new BigDecimal(-1);

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private BigDecimal nrointerno_cob = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String usuarioact = "";

	private java.sql.Timestamp fechatesoreria = null;

	private java.sql.Timestamp fechaultimocierre = null;

	private String fechamo_mt = "";

	private String nrointernoReintegro = "0";

	public BeanCajaMovTesoCobranzasAplicarReintegro() {
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
		Tesoreria tesoreria = Common.getTesoreria();

		try {

			if (this.accion.equalsIgnoreCase("reintegro")) {

				java.sql.Timestamp fmov = java.sql.Timestamp
						.valueOf(this.fechamo_mt);
				String fUltCierreTeso = Common.getGeneral()
						.getValorSetupVariablesNoStatic(
								"tesoFechaUltimoCierreTesoreria",
								this.idempresa);

				this.fechatesoreria = Common.getTesoFechaCaja(idempresa);

				if (Common.isFormatoFecha(fUltCierreTeso)
						&& Common.isFechaValida(fUltCierreTeso))
					this.fechaultimocierre = (java.sql.Timestamp) Common
							.setObjectToStrOrTime(fUltCierreTeso, "StrToJSTs");

				if (this.fechatesoreria == null) {

					this.mensaje = "Fecha de tesoreria no declarada o mal definida.";

				} else if (this.fechaultimocierre == null) {

					this.mensaje = "Fecha de ultimo cierre de tesoreria no declarada o mal definida.";

				} else if (fmov.before(this.fechaultimocierre)) {

					this.mensaje = "No es posible aplicar reintegro sobre comprobante seleccionado, fecha del mismo es menor al ultimo cierre de tesoreria.";

				} else if (sucursal.longValue() < 0
						|| this.comprobante.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro al cual aplicarle reintegro.";
				} else {

					java.sql.Date fechamov = new java.sql.Date(
							this.fechatesoreria.getTime());

					String[] resultado = new String[] {};

					// String[] resultado = tesoreria
					// .cajaClientesCobranzasReintegro(this.idcliente,
					// this.nrointerno_cob, fechamov,
					// this.usuarioact, this.idempresa);

					this.mensaje = resultado[0];

					if (Common.setNotNull(resultado[0]).equalsIgnoreCase("OK")) {

						this.nrointernoReintegro = resultado[1];
						this.mensaje = "Comprobante de cobranzas Nro.: "
								+ Common.strZero(this.sucursal.toString(), 4)
								+ "-"
								+ Common
										.strZero(this.comprobante.toString(), 8)
								+ ", reintegrado correctamente.";

					}
				}
			}

			if (this.idcliente.longValue() > 0) {

				String filtro = " idcliente  = " + this.idcliente.toString();

				this.totalRegistros = tesoreria.getTotalEntidadFiltro(
						"vcajamovtesocobranzas", " WHERE " + filtro,
						this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.cajaMovTesoCobranzasList = tesoreria
						.getCajaMovTesoCobranzasFiltro(this.limit, this.offset,
								filtro, this.idempresa);

			} else
				this.mensaje = "Es necesario seleccionar cliente.";

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

	public List getCajaMovTesoCobranzasList() {
		return cajaMovTesoCobranzasList;
	}

	public void setCajaMovTesoCobranzasList(List cajaMovTesoCobranzasList) {
		this.cajaMovTesoCobranzasList = cajaMovTesoCobranzasList;
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

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getNrointerno_cob() {
		return nrointerno_cob;
	}

	public void setNrointerno_cob(BigDecimal nrointerno_cob) {
		this.nrointerno_cob = nrointerno_cob;
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

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public String getFechamo_mt() {
		return fechamo_mt;
	}

	public void setFechamo_mt(String fechamo_mt) {
		this.fechamo_mt = fechamo_mt;
	}

	public String getNrointernoReintegro() {
		return nrointernoReintegro;
	}

	public void setNrointernoReintegro(String nrointernoReintegro) {
		this.nrointernoReintegro = nrointernoReintegro;
	}

}
