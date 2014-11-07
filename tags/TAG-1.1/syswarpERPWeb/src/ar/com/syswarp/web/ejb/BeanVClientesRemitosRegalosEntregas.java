/* 
   javabean para la entidad: vClientesRemitosRegalosEntregas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Dec 14 12:45:33 ART 2010 
   
   Para manejar la pagina: vClientesRemitosRegalosEntregasAbm.jsp
      
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

public class BeanVClientesRemitosRegalosEntregas implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanVClientesRemitosRegalosEntregas.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vClientesRemitosRegalosEntregasList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idpedido_regalos_cabe;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String filtroFRDesde = "";

	private String filtroFRHasta = "";

	private String filtroIdpedido = "";

	private String filtroIdclie = "";

	public BeanVClientesRemitosRegalosEntregas() {
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
		String filtro = "";
		try {

			if (this.filtroFRDesde.equals("") && this.filtroFRHasta.equals("")) {
				this.mensaje = "Es necesario filtrar por fecha de remito desde / hasta.";
			} else if (!this.filtroFRDesde.equals("")
					&& this.filtroFRHasta.equals("")) {
				this.mensaje = "Seleccione fecha hasta.";

			} else if (this.filtroFRDesde.equals("")
					&& !this.filtroFRHasta.equals("")) {
				this.mensaje = "Seleccione fecha desde.";

			} else {

				java.sql.Date fdesde = (java.sql.Date) Common
						.setObjectToStrOrTime(this.filtroFRDesde, "StrToJSDate");
				java.sql.Date fhasta = (java.sql.Date) Common
						.setObjectToStrOrTime(this.filtroFRHasta, "StrToJSDate");

				if (fdesde.after(fhasta)) {

					this.mensaje = "Fecha desde debe ser anterior a fecha hasta.";
				} else {

					filtro += " WHERE fecharemito BETWEEN  '" + fdesde
							+ "'::DATE AND '" + fhasta + "'::DATE";

					if (!Common.setNotNull(this.filtroIdpedido).equals("")) {
						if (Common.esEntero(this.filtroIdpedido))
							filtro += " AND idpedido_regalos_cabe = "
									+ this.filtroIdpedido;
						else
							this.mensaje = "Ingrese Nro. Orden de Pedido valido.";
					}

					if (!Common.setNotNull(this.filtroIdclie).equals("")) {
						if (Common.esEntero(this.filtroIdclie))
							filtro += " AND idcliente = " + this.filtroIdclie;
						else
							this.mensaje = "Ingrese Nro. Cliente valido.";
					}

					this.totalRegistros = clientes.getTotalEntidadFiltro(
							"vClientesRemitosRegalosEntregas", filtro,
							this.idempresa);

					this.totalPaginas = (this.totalRegistros / this.limit) + 1;
					if (this.totalPaginas < this.paginaSeleccion)
						this.paginaSeleccion = this.totalPaginas;
					this.offset = (this.paginaSeleccion - 1) * this.limit;
					if (this.totalRegistros == this.limit) {
						this.offset = 0;
						this.totalPaginas = 1;
					}

					
					
					filtro = filtro.replace("WHERE", " AND ");

					this.vClientesRemitosRegalosEntregasList = clientes
							.getVClientesRemitosRegalosEntregasAll(this.limit,
									this.offset, filtro, this.idempresa);

					if (this.totalRegistros < 1 && this.mensaje.equals(""))
						this.mensaje = "No existen registros.";

				}

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

	public List getVClientesRemitosRegalosEntregasList() {
		return vClientesRemitosRegalosEntregasList;
	}

	public void setVClientesRemitosRegalosEntregasList(
			List vClientesRemitosRegalosEntregasList) {
		this.vClientesRemitosRegalosEntregasList = vClientesRemitosRegalosEntregasList;
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

	public BigDecimal getIdpedido_regalos_cabe() {
		return idpedido_regalos_cabe;
	}

	public void setIdpedido_regalos_cabe(BigDecimal idpedido_regalos_cabe) {
		this.idpedido_regalos_cabe = idpedido_regalos_cabe;
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

	public String getFiltroFRDesde() {
		return filtroFRDesde;
	}

	public void setFiltroFRDesde(String filtroFRDesde) {
		this.filtroFRDesde = filtroFRDesde;
	}

	public String getFiltroFRHasta() {
		return filtroFRHasta;
	}

	public void setFiltroFRHasta(String filtroFRHasta) {
		this.filtroFRHasta = filtroFRHasta;
	}

	public String getFiltroIdpedido() {
		return filtroIdpedido;
	}

	public void setFiltroIdpedido(String filtroIdpedido) {
		this.filtroIdpedido = filtroIdpedido;
	}

	public String getFiltroIdclie() {
		return filtroIdclie;
	}

	public void setFiltroIdclie(String filtroIdclie) {
		this.filtroIdclie = filtroIdclie;
	}

}
