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

public class BeanBacoRefReportePuntos implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanBacoRefReportePuntos.class);

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

	private BigDecimal idvendedor = new BigDecimal(-1);

	private String vendedor = "";

	private String fechadesde = "";

	private String fechahasta = "";

	boolean buscar = false;

	// 20120410 - EJV - Mantis 702 -->

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	// <--

	public BeanBacoRefReportePuntos() {
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

			if (this.accion.equalsIgnoreCase("consulta")) {

				// 20120410 - EJV - Mantis 702 -->
				// if (this.idvendedor == null || this.idvendedor.longValue() <
				// 1) {
				// this.mensaje = "Es necesario seleccionar vendedor.";
				// } else
				// <--
				if (!Common.isFormatoFecha(this.fechadesde)
						|| !Common.isFechaValida(this.fechadesde)) {
					this.mensaje = "Fecha desde invalida.";
				} else if (!Common.isFormatoFecha(this.fechahasta)
						|| !Common.isFechaValida(this.fechahasta)) {
					this.mensaje = "Fecha hasta invalida.";
				} else {

					java.sql.Date fdesde = (java.sql.Date) Common
							.setObjectToStrOrTime(this.fechadesde,
									"StrToJSDate");
					java.sql.Date fhasta = (java.sql.Date) Common
							.setObjectToStrOrTime(this.fechahasta,
									"StrToJSDate");

					if (fhasta.before(fdesde)) {
						this.mensaje = "Fecha hasta debe ser mayor a fecha desde.";
					} else {

						String entidad = "("
								+ "SELECT DISTINCT cl.idcliente, cc.fecha,  pr.idvendedorasignado, cc.idempresa "
								+ "  FROM clientesclientes cl  "
								+ "       INNER JOIN clientesprecargaclientes pr ON cl.idcliente = pr.idcliente AND cl.idempresa = pr.idempresa "
								+ "       INNER JOIN clientesdomicilios cd ON cl.idcliente = cd.idcliente AND cl.idempresa = cd.idempresa AND cd.esdefault = 'S' "
								+ "       INNER JOIN clientesanexolocalidades ax ON cd.idanexolocalidad = ax.idanexolocalidad AND cd.idempresa = ax.idempresa "
								+ "       INNER JOIN globallocalidades lo ON ax.idlocalidad = lo.idlocalidad "
								+ "       INNER JOIN globalprovincias pv ON lo.idprovincia = pv.idprovincia "
								+ "       INNER JOIN bacorefctacte cc ON cl.idcliente = cc.idcliente AND cl.idempresa = cc.idempresa "

								+ " ) entidad";

						String filtro = "WHERE fecha BETWEEN '" + fdesde
								+ "'::DATE AND '" + fhasta + "'::DATE  ";
						// 20120410 - EJV - Mantis 702 -->
						if (this.idvendedor != null
								&& this.idvendedor.longValue() > 0) {
							filtro += "  AND idvendedorasignado="
									+ this.idvendedor.toString();
						}

						if (this.idcliente != null
								&& this.idcliente.longValue() > 0) {
							filtro += "  AND idcliente="
									+ this.idcliente.toString();
						}

						// <--
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

						this.bacoRefCtaCteList = clientes
								.getBacoRefCtaCteReportePuntos(this.idvendedor,
										this.idcliente, fdesde, fhasta,
										this.idempresa);

						if (this.totalRegistros < 1 && this.mensaje.equals(""))
							this.mensaje = "No existen registros.";

					}

				}

			} else
				this.mensaje = "Ingrese parametros de busqueda.";

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

	public BigDecimal getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(BigDecimal idvendedor) {
		this.idvendedor = idvendedor;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(String fechadesde) {
		this.fechadesde = fechadesde;
	}

	public String getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(String fechahasta) {
		this.fechahasta = fechahasta;
	}

	// 20120410 - EJV - Mantis 702 -->

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

	// <--

}
