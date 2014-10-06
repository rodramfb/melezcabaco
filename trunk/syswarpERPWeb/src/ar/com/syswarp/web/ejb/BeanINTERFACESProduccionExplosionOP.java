/* 
 javabean para la entidad: produccionMovProdu
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Feb 21 13:30:17 GMT-03:00 2007 
 
 Para manejar la pagina: produccionMovProduAbm.jsp
 
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

public class BeanINTERFACESProduccionExplosionOP implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanINTERFACESProduccionExplosionOP.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List produccionMovProduList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String[] idop = null;

	private String[] cantidadparcial = null;

	private String[] realizado = null;

	private String[] estimado = null;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String explosion = "";

	private String usuarioalt = "";

	public BeanINTERFACESProduccionExplosionOP() {
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
		BC bc = Common.getBc();
		String filtro = " "
				+ "WHERE cantre_op < cantest_op AND fbaja IS NULL  "
				+ "  AND idop IN ( SELECT idop FROM interfacesopregalos WHERE idempresa = "
				+ idempresa + " ) ";
		int i = 0;
		try {

			if (!this.explosion.equalsIgnoreCase("")) {

				if (this.idop == null) {
					this.mensaje = "Es necesario seleccionar al menos una orden pendiente.";
				} else

					for (i = 0; i < cantidadparcial.length; i++) {
						if (!Common.esNumerico(cantidadparcial[i])) {
							this.mensaje = "Cantidades solo permite valores numericos.";
							break;
						}

						if (Double.parseDouble(cantidadparcial[i]) < 0) {
							this.mensaje = "Cantidades deben ser positivas.";
							break;
						}

						if (Double.parseDouble(this.estimado[i]) < (Double
								.parseDouble(this.cantidadparcial[i]) + Double
								.parseDouble(this.realizado[i]))) {
							this.mensaje = "Estimado (" + estimado[i]
									+ ") no puede ser menor a realizado ("
									+ this.realizado[i] + ") + cantidad("
									+ this.cantidadparcial[i] + ")";
							break;
						}
					}

				if (mensaje.equals("")) {
					this.mensaje = bc
							.prodINTFGenerarExplosionOP(this.idop,
									this.cantidadparcial, this.usuarioalt,
									this.idempresa);
					if (this.mensaje.equalsIgnoreCase("OK")) {
						this.mensaje = "Cantidades de Ordenes y Existencias de Stock actualizadas correctamente.";
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

				filtro += " AND (idop::VARCHAR LIKE '%" + ocurrencia
						+ "%' OR codigo_st LIKE '%" + ocurrencia + "%')";
				this.totalRegistros = bc.getTotalEntidadFiltro(
						"produccionMovProdu", filtro, this.idempresa);
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
				this.produccionMovProduList = bc
						.getINTFMovProduPendientesOcu(this.limit,
								this.offset, this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = bc.getTotalEntidadFiltro(
						"produccionMovProdu", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.produccionMovProduList = bc
						.getINTFMovProduPendientesAll(this.limit,
								this.offset, this.idempresa);
			}
			if (this.totalRegistros < 1 && this.explosion.equalsIgnoreCase(""))
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

	public List getProduccionMovProduList() {
		return produccionMovProduList;
	}

	public void setProduccionMovProduList(List produccionMovProduList) {
		this.produccionMovProduList = produccionMovProduList;
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

	public String[] getIdop() {
		return idop;
	}

	public void setIdop(String[] idop) {
		this.idop = idop;
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

	public String getExplosion() {
		return explosion;
	}

	public void setExplosion(String explosion) {
		this.explosion = explosion;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String[] getCantidadparcial() {
		return cantidadparcial;
	}

	public void setCantidadparcial(String[] cantidadparcial) {
		this.cantidadparcial = cantidadparcial;
	}

	public String[] getRealizado() {
		return realizado;
	}

	public void setRealizado(String[] realizado) {
		this.realizado = realizado;
	}

	public String[] getEstimado() {
		return estimado;
	}

	public void setEstimado(String[] estimado) {
		this.estimado = estimado;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
