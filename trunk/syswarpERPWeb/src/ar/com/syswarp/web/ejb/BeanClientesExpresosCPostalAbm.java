/* 
   javabean para la entidad: clientesExpresosCPostal
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Apr 19 14:40:36 GMT-03:00 2010 
   
   Para manejar la pagina: clientesExpresosCPostalAbm.jsp
      
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

public class BeanClientesExpresosCPostalAbm implements SessionBean,
		Serializable {
	static Logger log = Logger.getLogger(BeanClientesExpresosCPostalAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List clientesExpresosCPostalList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String cpostal = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	public BeanClientesExpresosCPostalAbm() {
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

			if (!Common.setNotNull(this.cpostal).trim().equals("")) {
				String filtro = " WHERE TRUE ";
				String entidad = "("
						+ "SELECT ex.idexpreso, ex.expreso, "
						+ "       cl.idcliente, cl.razon, es.idestado, es.estado,  "
						+ "       tc.idtipoclie, tc.tipoclie,  "
						+ "       gl.idlocalidad, gl.localidad, "
						+ "       gp.idprovincia, gp.provincia, "
						+ "       fu_periodicidad(cl.idcliente, cl.idempresa) AS periodicodad, "
						+ "       cl.idempresa, cl.usuarioalt, cl.usuarioact, cl.fechaalt, cl.fechaact "
						+ "  FROM clientesclientes cl "
						+ "       INNER JOIN clientesestadoshoy eh ON cl.idcliente = eh.idcliente AND cl.idempresa = eh.idempresa "
						+ "       INNER JOIN clientesestados es ON eh.idestado = es.idestado AND eh.idempresa = es.idempresa  "
						+ "       INNER JOIN clientestipoclie tc ON cl.idtipoclie = tc.idtipoclie AND cl.idempresa = tc.idempresa "
						+ "       INNER JOIN clientesdomicilios cd ON cl.idcliente = cd.idcliente AND cl.idempresa = cd.idempresa AND cd.esdefault = 'S' "
						+ "       INNER JOIN clientesanexolocalidades ax ON cd.idanexolocalidad = ax.idanexolocalidad AND cd.idempresa = ax.idempresa "
						+ "       INNER JOIN globallocalidades gl ON ax.idlocalidad = gl.idlocalidad  "
						+ "       INNER JOIN globalprovincias gp ON gl.idprovincia = gp.idprovincia "
						+ "       INNER JOIN clientesexpresoszonas ez ON ax.idexpresozona = ez.codigo AND ax.idempresa = ez.idempresa "
						+ "       INNER JOIN clientesexpresos ex ON ez.idexpreso = ex.idexpreso AND ex.idempresa = ex.idempresa	 "
						+ " WHERE TRIM(gl.cpostal) = '"
						+ this.cpostal.trim().toString() + "') entidad";

				this.totalRegistros = clientes.getTotalEntidadFiltro(entidad,
						filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.clientesExpresosCPostalList = clientes
						.getClientesExpresosCPostalAll(this.limit, this.offset,
								this.cpostal, this.idempresa);

				if (this.totalRegistros < 1)
					this.mensaje = "No existen registros.";

			} else {

				this.mensaje = "Ingrese CP a consultar.";
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

	public List getClientesExpresosCPostalList() {
		return clientesExpresosCPostalList;
	}

	public void setClientesExpresosCPostalList(List clientesExpresosCPostalList) {
		this.clientesExpresosCPostalList = clientesExpresosCPostalList;
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

	public String getCpostal() {
		return cpostal;
	}

	public void setCpostal(String cpostal) {
		this.cpostal = cpostal;
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
}
