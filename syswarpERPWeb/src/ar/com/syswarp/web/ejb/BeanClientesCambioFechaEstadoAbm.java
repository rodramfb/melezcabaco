/* 
   javabean para la entidad: clientesCambioFechaEstado
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 06 10:34:57 ART 2011 
   
   Para manejar la pagina: clientesCambioFechaEstadoAbm.jsp
      
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

public class BeanClientesCambioFechaEstadoAbm implements SessionBean,
		Serializable {

	static Logger log = Logger
			.getLogger(BeanClientesCambioFechaEstadoAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List clientesCambioFechaEstadoList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	// HARCODE - Por definicion solo es posible modificar aquellos registros
	// cuyo estado es 1 - Activo.
	private BigDecimal idestadocliente = new BigDecimal(1);

	private String[] idestadoclienteVector = null;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String fechadesde = "";

	private java.sql.Date fDesde = null;

	private String fechadesdeNew = "";

	private java.sql.Date fDesdeNew = null;

	private String actualizar = "";

	private String usuarioact = "";

	public BeanClientesCambioFechaEstadoAbm() {
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

	private void ejecutarSentenciaDML() {

		try {

			this.mensaje = Common.getClientes()
					.clientesCambioFechaEstadoUpdate(
							this.idestadoclienteVector, this.fDesdeNew,
							this.usuarioact, this.idempresa);

			if (Common.setNotNull(this.mensaje).equalsIgnoreCase("OK")) {

				this.mensaje = "Fecha actualizada correctamente.";

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public boolean ejecutarValidacion() {
		Clientes clientes = Common.getClientes();
		String filtro = "";
		String entidad = "("

				+ "SELECT ce.idestadocliente, ce.idcliente, ce.idestado, ce.fechadesde, ce.fbaja, "
				+ "            ce.idempresa, ce.fechaalt, ce.fechaact, ce.usuarioalt, ce.usuarioact "
				+ "  FROM clientesestadosclientes ce " + ") entidad ";

		try {

			Calendar cal = new GregorianCalendar();
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.HOUR_OF_DAY, 0);

			java.sql.Date actual = new java.sql.Date(cal.getTimeInMillis());

			if (Common.isFormatoFecha(this.fechadesde)
					&& Common.isFechaValida(this.fechadesde)) {

				this.fDesde = (java.sql.Date) Common.setObjectToStrOrTime(
						fechadesde, "StrToJSDate");

				if (!actual.before(this.fDesde)) {

					this.mensaje = "Fecha desde debe ser mayor a fecha actual: "
							+ this.fechadesde;

				} else {

					filtro = " WHERE idestado =  " + this.idestadocliente
							+ "   AND  fechadesde = '" + this.fDesde
							+ "'::DATE  AND fbaja IS NULL ";

					if (!Common.setNotNull(this.actualizar).equals("")) {

						if (!Common.isFormatoFecha(this.fechadesdeNew)
								|| !Common.isFechaValida(this.fechadesdeNew)) {

							this.mensaje = "Ingrese nueva fecha valida.";

						} else if (this.idestadoclienteVector == null
								|| this.idestadoclienteVector.length == 0) {

							this.mensaje = "Seleccione al menos un registro para actualizar.";

						} else {

							this.fDesdeNew = (java.sql.Date) Common
									.setObjectToStrOrTime(fechadesdeNew,
											"StrToJSDate");

							if (this.fDesdeNew.compareTo(actual) < 0
									|| this.fDesdeNew.compareTo(this.fDesde) >= 0) {

								this.mensaje = "Nueva Fecha debe ser mayor o igual a fecha actual: "
										+ Common.setObjectToStrOrTime(actual,
												"JSDateToStr")
										+ " y menor a fecha desde: "
										+ this.fechadesde;

							} else {

								this.ejecutarSentenciaDML();

							}

						}

					}

					this.totalRegistros = clientes.getTotalEntidadFiltro(
							entidad, filtro, this.idempresa);
					this.totalPaginas = (this.totalRegistros / this.limit) + 1;
					if (this.totalPaginas < this.paginaSeleccion)
						this.paginaSeleccion = this.totalPaginas;
					this.offset = (this.paginaSeleccion - 1) * this.limit;
					if (this.totalRegistros == this.limit) {
						this.offset = 0;
						this.totalPaginas = 1;
					}

					this.clientesCambioFechaEstadoList = clientes
							.getClientesCambioFechaEstadoAll(this.limit,
									this.offset, this.idestadocliente,
									this.fDesde, this.idempresa);

					if (this.totalRegistros < 1
							&& Common.setNotNull(this.mensaje).equals(""))
						this.mensaje = "No existen registros.";

				}

			} else {
				this.mensaje = "Es necesario ingresar una fecha valida.";
			}

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

	public List getClientesCambioFechaEstadoList() {
		return clientesCambioFechaEstadoList;
	}

	public void setClientesCambioFechaEstadoList(
			List clientesCambioFechaEstadoList) {
		this.clientesCambioFechaEstadoList = clientesCambioFechaEstadoList;
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

	public BigDecimal getIdestadocliente() {
		return idestadocliente;
	}

	public void setIdestadocliente(BigDecimal idestadocliente) {
		this.idestadocliente = idestadocliente;
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

	public void setFechadesde(String fechadesde) {
		this.fechadesde = fechadesde;
	}

	public String getFechadesdeNew() {
		return fechadesdeNew;
	}

	public void setFechadesdeNew(String fechadesdeNew) {
		this.fechadesdeNew = fechadesdeNew;
	}

	public String[] getIdestadoclienteVector() {
		return idestadoclienteVector;
	}

	public void setIdestadoclienteVector(String[] idestadoclienteVector) {
		this.idestadoclienteVector = idestadoclienteVector;
	}

	public String getActualizar() {
		return actualizar;
	}

	public void setActualizar(String actualizar) {
		this.actualizar = actualizar;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

}
