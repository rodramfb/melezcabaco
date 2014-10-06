/* 
 javabean para la entidad: globalLocalidades
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Oct 27 16:35:02 GMT-03:00 2006 
 
 Para manejar la pagina: globalLocalidadesAbm.jsp
 
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

public class BeanClientesLov implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanClientesLov.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List clientesclientesList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idcliente;

	private BigDecimal idempresa;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	// 20120213 - EJV - Mantis 816 -->

	private String filtroIdcliente = "";

	private String filtroIdclienteKosher = "";

	private String filtroCliente = "";

	private String filtroNrodocumento = "";

	private String filtroNrotarjeta = "";
	
	// <--
	// 20121025 - CAMI - Mantis 891 --->
	
	private String filtroEmail = "";
	
	//<---

	public BeanClientesLov() {
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
		Clientes clientesclientes = Common.getClientes();
		try {

			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("pedidos_cabeFrm.jsp");
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

			String filtro = " WHERE TRUE ";
			String auxFiltro = "";

			if (!Common.setNotNull(this.filtroIdcliente).equalsIgnoreCase("")
					&& Common.esEntero(this.filtroIdcliente)) {
				auxFiltro += "  AND idcliente = " + this.filtroIdcliente;
			}

			if (!Common.setNotNull(this.filtroIdclienteKosher)
					.equalsIgnoreCase("")
					&& Common.esEntero(this.filtroIdclienteKosher)) {
				auxFiltro += "  AND idclientekosher = "
						+ this.filtroIdclienteKosher;
			}

			if (!Common.setNotNull(this.filtroCliente).equalsIgnoreCase("")) {
				auxFiltro += "  AND razon ILIKE '%"
						+ this.filtroCliente.replaceAll("'", "_") + "%'";
			}

			if (!Common.setNotNull(this.filtroNrodocumento)
					.equalsIgnoreCase("")
					&& Common.esEntero(this.filtroNrodocumento)) {
				auxFiltro += "  AND nrodocumento = " + this.filtroNrodocumento;
			}

			if (!Common.setNotNull(this.filtroNrotarjeta).equalsIgnoreCase("")
					&& Common.esEntero(this.filtroNrotarjeta)) {
				auxFiltro += "  AND idcliente  IN ( SELECT idcliente FROM clientetarjetascredito WHERE  nrotarjeta = '"
						+ this.filtroNrotarjeta + "' ) ";

			}
			
			if (!Common.setNotNull(this.filtroEmail).equalsIgnoreCase(""))
			{
				//IN (SELECT  idcliente FROM clientesemail  WHERE email ILIKE '%danielgraziadio@fibertel.com.ar%') !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				auxFiltro += " and idcliente IN (SELECT  idcliente FROM clientesemail  WHERE email ILIKE '%" + this.filtroEmail + "%')";
			}
			filtro += auxFiltro;
//
			// if (buscar) {
			String entidad = "("
					+ "SELECT cl.idcliente, cl.idclientekosher, cl.razon, cl.nrodocumento, cl.idempresa, fu_mail_cliente (cl.idcliente, cl.idempresa) email "
					+ "  FROM clientesclientes cl "
					+ "       INNER JOIN globaltiposdocumentos td ON cl.idtipodocumento = td.idtipodocumento and cl.idempresa = td.idempresa  "
					// 20121025 - CAMI - Mantis 891 -->
					//+ "		  LEFT JOIN clientesemail clie on cl.idcliente = clie.idcliente and cl.idempresa = clie.idempresa "
					//<---
					+ "       LEFT  JOIN vclientesestadoshoy ce ON cl.idcliente = ce.idcliente and  cl.idempresa = ce.idempresa "
					// + "  WHERE (UPPER(cl.razon) LIKE '%"
					// + ocurrencia.toUpperCase().trim()
					// + "%' OR cl.idcliente::VARCHAR LIKE '%"
					// + ocurrencia.toUpperCase().trim()
					// + "%' OR cl.idclientekosher::VARCHAR LIKE '%"
					// + ocurrencia.toUpperCase().trim()
					// +
					// "%'  OR cl.idcliente  IN (SELECT idcliente FROM clientetarjetascredito WHERE nrotarjeta::VARCHAR LIKE '"
					// + ocurrencia.toUpperCase().trim()
					// + "%' AND idempresa = " + idempresa.toString()
					// + ")  OR cl.nrodocumento::VARCHAR LIKE '"
					// + ocurrencia.toUpperCase().trim()
					// + "%')  AND cl.idempresa = " + idempresa.toString()
					+ " ) cliente";

			this.totalRegistros = clientesclientes.getTotalEntidadFiltro(
					entidad, filtro, this.idempresa);
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
			this.clientesclientesList = clientesclientes
					.getClientesClientesOcu(this.limit, this.offset, auxFiltro,
							this.idempresa);
			// } else {
			// this.totalRegistros = clientesclientes.getTotalEntidad(
			// "clientesclientes", this.idempresa);
			// this.totalPaginas = (this.totalRegistros / this.limit) + 1;
			// if (this.totalPaginas < this.paginaSeleccion)
			// this.paginaSeleccion = this.totalPaginas;
			// this.offset = (this.paginaSeleccion - 1) * this.limit;
			// if (this.totalRegistros == this.limit) {
			// this.offset = 0;
			// this.totalPaginas = 1;
			// }
			// this.clientesclientesList = clientesclientes
			// .getClientesClientesAll(this.limit, this.offset,
			// this.idempresa);
			// }
			if (this.totalRegistros < 1)
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

	public List getClientesclientesList() {
		return clientesclientesList;
	}

	public void setclientesclientesList(List clientesclientesList) {
		this.clientesclientesList = clientesclientesList;
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

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
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

	// 20120213 - EJV - Mantis 816 -->

	public String getFiltroIdcliente() {
		return filtroIdcliente;
	}

	public void setFiltroIdcliente(String filtroIdcliente) {
		this.filtroIdcliente = filtroIdcliente;
	}

	public String getFiltroIdclienteKosher() {
		return filtroIdclienteKosher;
	}

	public void setFiltroIdclienteKosher(String filtroIdclienteKosher) {
		this.filtroIdclienteKosher = filtroIdclienteKosher;
	}

	public String getFiltroCliente() {
		return filtroCliente;
	}

	public void setFiltroCliente(String filtroCliente) {
		this.filtroCliente = filtroCliente;
	}

	public String getFiltroNrodocumento() {
		return filtroNrodocumento;
	}

	public void setFiltroNrodocumento(String filtroNrodocumento) {
		this.filtroNrodocumento = filtroNrodocumento;
	}

	public String getFiltroNrotarjeta() {
		return filtroNrotarjeta;
	}

	public void setFiltroNrotarjeta(String filtroNrotarjeta) {
		this.filtroNrotarjeta = filtroNrotarjeta;
	}

	// <--

	// 20121025 - CAMI - Mantis 891 -->
	
	public String getFiltroEmail() {
		return filtroEmail;
	}

	public void setFiltroEmail(String filtroEmail) {
		this.filtroEmail = filtroEmail;
	}
	
	
	// <--

}
