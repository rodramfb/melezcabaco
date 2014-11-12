/* 
   javabean para la entidad: pedidosConDescuento
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 15 09:28:14 GYT 2009 
   
   Para manejar la pagina: pedidosConDescuentoAbm.jsp
      
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

public class BeanPedidosEvaluacionAbm implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanPedidosEvaluacionAbm.class);

	private BigDecimal idempresa = new BigDecimal(-1);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List pedidosConDescuentoList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idtipoclie = new BigDecimal(-1);

	private BigDecimal anio = new BigDecimal(-1);

	private BigDecimal mes = new BigDecimal(-1);

	private BigDecimal idestado = new BigDecimal(-1);

	private String fechapedido = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private List estadosList = new ArrayList();

	private List listTipoClie = new ArrayList();

	public BeanPedidosEvaluacionAbm() {
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
		Clientes clie = Common.getClientes();

		try {

			this.estadosList = clie.getClientesestadosAll(100, 0,
					this.idempresa);

			this.listTipoClie = clie.getClientestipoclieAll(100, 0,
					this.idempresa);

			if (this.accion.equals("")) {

				this.mensaje = "Ingrese criterio de búsqueda.";

			} else if (this.fechapedido.equals("")) {

				this.mensaje = "Es necesario seleccionar Mes y Año.";

			} else if (this.idestado.longValue() == -1) {

				this.mensaje = "Es necesario seleccionar Estado.";

			} else if (this.idtipoclie.longValue() == -1) {

				this.mensaje = "Es necesario seleccionar Tipo de Cliente.";

			} else {

				java.util.Date fPedido = (java.util.Date) Common
						.setObjectToStrOrTime(this.fechapedido, "StrToJUDate");
				Calendar cal = Calendar.getInstance();
				cal.setTime(fPedido);
				this.anio = new BigDecimal(cal.get(Calendar.YEAR));
				this.mes = new BigDecimal(cal.get(Calendar.MONTH) + 1);

				if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
					if (ocurrencia.indexOf("'") >= 0) {
						this.mensaje = "Caracteres invalidos en campo busqueda.";
					} else {
						buscar = true;
					}
				}

				String entidad = "("
						+ "SELECT idpedido_cabe, idtipoclie, tipoclie, idcliente, razon, idexpreso, expreso, "
						+ "       idestado, estado, motivo, fechadesde, fechapedido, origenpedido, "
						+ "       idestadopedido, estadopedido, idempresa, usuarioalt, usuarioact, fechaalt, fechaact  "
						+ "  FROM ( "
						+ "	SELECT pc.idpedido_cabe, cl.idtipoclie, tc.tipoclie, cl.idcliente, cl.razon, ex.idexpreso, ex.expreso,  "
						+ "	       es.idestado, es.estado, es.motivo, es.fechadesde, pc.fechapedido, pc.origenpedido,  "
						+ "	       pc.idestado AS idestadopedido, pes.estado AS estadopedido,  "
						+ "        cl.idempresa, pc.usuarioalt, pc.usuarioact, pc.fechaalt, pc.fechaact  "
						+ "	  FROM clientesclientes cl  "
						+ "	       INNER JOIN clientestipoclie tc ON ( cl.idtipoclie = tc.idtipoclie AND cl.idempresa = tc.idempresa )  "
						+ "	       INNER JOIN vclientesestadoshoy es ON ( cl.idcliente = es.idcliente AND cl.idempresa = es.idempresa )  "
						+ "	       LEFT JOIN pedidos_cabe pc ON (pc.idcliente = cl.idcliente AND cl.idempresa = pc.idempresa  "
						+ "		     AND DATE_PART('YEAR',pc.fechapedido) = "
						+ this.anio.toString()
						+ "                     AND DATE_PART('MONTH',pc.fechapedido) = "
						+ this.mes.toString()
						+ ")"
						+ "	       LEFT JOIN clientesexpresos ex ON pc.idexpreso = ex.idexpreso AND pc.idempresa = ex.idempresa  "
						+ "               LEFT JOIN pedidosestados pes ON pc.idestado = pes.idestado AND pc.idempresa = pes.idestado "
						+ "	 WHERE cl.idempresa =  "
						+ this.idempresa.toString()
						+ "	   AND cl.idtipoclie =  "
						+ this.idtipoclie.toString()
						+ "	   AND es.idestado = "
						+ this.idestado.toString()
						+ "       )evaluacion "
						+ " WHERE idpedido_cabe IS NULL OR idestadopedido IN (4, 5)   ) entidad ";

				String filtro = " WHERE TRUE ";

				if (buscar) {
					filtro += " AND (UPPER(razon) LIKE '%"
							+ ocurrencia.toUpperCase().trim() + "%' OR idcliente::VARCHAR LIKE '%"
							+ ocurrencia.toUpperCase().trim() + "%') ";

					this.totalRegistros = clie.getTotalEntidadFiltro(entidad,
							filtro, this.idempresa);
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
					this.pedidosConDescuentoList = clie
							.getPedidosEvaluacionOcu(this.limit, this.offset,
									this.ocurrencia, this.idtipoclie, this.mes,
									this.anio, this.idestado, this.idempresa);
				} else {
					this.totalRegistros = clie.getTotalEntidadFiltro(entidad,
							filtro, this.idempresa);
					this.totalPaginas = (this.totalRegistros / this.limit) + 1;
					if (this.totalPaginas < this.paginaSeleccion)
						this.paginaSeleccion = this.totalPaginas;
					this.offset = (this.paginaSeleccion - 1) * this.limit;
					if (this.totalRegistros == this.limit) {
						this.offset = 0;
						this.totalPaginas = 1;
					}
					this.pedidosConDescuentoList = clie
							.getPedidosEvaluacionAll(this.limit, this.offset,
									this.idtipoclie, this.mes, this.anio,
									this.idestado, this.idempresa);
				}

				if (this.totalRegistros < 1)
					this.mensaje = "No existen registros.";

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

	public List getPedidosConDescuentoList() {
		return pedidosConDescuentoList;
	}

	public void setPedidosConDescuentoList(List pedidosConDescuentoList) {
		this.pedidosConDescuentoList = pedidosConDescuentoList;
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

	public BigDecimal getIdtipoclie() {
		return idtipoclie;
	}

	public void setIdtipoclie(BigDecimal idtipoclie) {
		this.idtipoclie = idtipoclie;
	}

	public BigDecimal getAnio() {
		return anio;
	}

	public void setAnio(BigDecimal anio) {
		this.anio = anio;
	}

	public BigDecimal getMes() {
		return mes;
	}

	public void setMes(BigDecimal mes) {
		this.mes = mes;
	}

	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
	}

	public String getFechapedido() {
		return fechapedido;
	}

	public void setFechapedido(String fechapedido) {
		this.fechapedido = fechapedido;
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

	public List getEstadosList() {
		return estadosList;
	}

	public void setEstadosList(List estadosList) {
		this.estadosList = estadosList;
	}

	public List getListTipoClie() {
		return listTipoClie;
	}

	public void setListTipoClie(List listTipoClie) {
		this.listTipoClie = listTipoClie;
	}

}
