/* 
 javabean para la entidad (Formulario): Cajaferiados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
 
 Para manejar la pagina: CajaferiadosFrm.jsp
 
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
import java.math.*;
import java.util.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanReactivacionAbm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	// private String[] idcliente = new String[] {};
	private String[] idcliente = null;

	private String cliente = "";

	static Logger log = Logger.getLogger(BeanReactivacionAbm.class);

	private String validar = "";

	private int limit = 15;

	private long offset = 0l;

	private long paginaSeleccion = 1l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private String usuarioalt;

	private String ocurrencia = "";

	private String explosion = "";

	private String usuarioact;

	private BigDecimal idestado = new BigDecimal(0);
	private BigDecimal idmotivo = new BigDecimal(0);
	private String observaciones = "";
	private BigDecimal idvendedor = new BigDecimal(0);

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	boolean buscar = false;

	// 20110909 - EJV - Mantis 789 -->
	private String ejercicioContable = "";

	// <--

	public BeanReactivacionAbm() {
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
		General Consulta = Common.getGeneral();

		// TODO - EJV - 20110912: La logica en activacion - reactivacion no es
		// transaccional ... es necesario reveer todos los metodos relacionados.

		try {

			if (!this.explosion.equalsIgnoreCase("")) {

				if (this.idcliente == null) {
					this.mensaje = "Es necesario seleccionar al menos un Registro.";
				} else

					// 20110909 - EJV - Mantis 789 -->
					this.ejercicioContable = Common.setNotNull(request
							.getSession().getAttribute("ejercicioActivo")
							+ "");
				if (!Common.esEntero(this.ejercicioContable)) {

					this.mensaje = "No existe ejercicio activo.";

				} else {

					int ejercicioActivo = Integer
							.parseInt(this.ejercicioContable);

					if (mensaje.equals("")) {
						this.mensaje = Consulta.EstadoActualUpdate(
								this.idcliente, this.idempresa);
					}

					if (this.mensaje.equalsIgnoreCase("OK")) {
						this.mensaje = Consulta.EstadoActualInsert(
								this.idcliente, this.idestado, this.idmotivo,
								this.observaciones, ejercicioActivo,
								this.usuarioalt, this.idempresa);
						this.mensaje = "Cliente Impactado correctamente!";
					}

				}
				// <--

			}

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			if (buscar) {
				String[] campos = { "vceh.idcliente", "vceh.razon",
						"vceh.estado", "vceh.motivo", "cv.vendedor" };
				this.totalRegistros = Consulta.getTotalEntidadReactivacionOcu(
						campos, this.ocurrencia, this.idempresa);
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
				this.MovimientosList = Consulta.ClientesReactivacionOcu(
						this.limit, this.offset, this.idempresa,
						this.ocurrencia);
			} else {
				this.totalRegistros = Consulta
						.getTotalEntidadReactivacionAll(this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.MovimientosList = Consulta.ClientesReactivacionAll(
						this.limit, this.offset, this.idempresa);
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;

	}

	public String getValidar() {
		return validar;
	}

	public void setValidar(String validar) {
		this.validar = validar;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getVolver() {
		return volver;
	}

	public void setVolver(String volver) {
		this.volver = volver;
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

	// metodos para cada atributo de la entidad

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public List getMovimientosList() {
		return MovimientosList;
	}

	public void setMovimientosList(List movimientosList) {
		MovimientosList = movimientosList;
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

	public void setTotalRegistros(long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public long getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(long totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String ocurrencia) {
		this.ocurrencia = ocurrencia;
	}

	public long getPaginaSeleccion() {
		return paginaSeleccion;
	}

	public void setPaginaSeleccion(long paginaSeleccion) {
		this.paginaSeleccion = paginaSeleccion;
	}

	public String[] getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(String[] idcliente) {
		this.idcliente = idcliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getExplosion() {
		return explosion;
	}

	public void setExplosion(String explosion) {
		this.explosion = explosion;
	}

	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
	}

	public BigDecimal getIdmotivo() {
		return idmotivo;
	}

	public void setIdmotivo(BigDecimal idmotivo) {
		this.idmotivo = idmotivo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public BigDecimal getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(BigDecimal idvendedor) {
		this.idvendedor = idvendedor;
	}

}
