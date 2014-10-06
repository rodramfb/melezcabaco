/* 
   javabean para la entidad: vClientesRemitosHojaRutaFinalReimprime
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Sep 21 15:10:10 ART 2010 
   
   Para manejar la pagina: vClientesRemitosHojaRutaFinalReimprimeAbm.jsp
      
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

public class BeanClientesRemitosGeneraArchivoAndreani implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanClientesRemitosGeneraArchivoAndreani.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List clientesRemitosArchivoAndreaniList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	// private BigDecimal nrohojarutafinal;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String[] nrohojarutafinal = null;

	private String usuarioact = "";

	private String archivoAndreaniZip = "";

	public BeanClientesRemitosGeneraArchivoAndreani() {
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
		String[] resultado = new String[] { "", "" };
		String filtro = " WHERE idzona = 28";
		try {

			if (Common.setNotNull(this.accion).equalsIgnoreCase("generar")) {

				if (this.nrohojarutafinal != null
						&& this.nrohojarutafinal.length > 0) {

					resultado = clientes.generaArchivoAndreani(
							this.nrohojarutafinal, this.usuarioact,
							this.idempresa);

					if (Common.setNotNull(resultado[0]).equalsIgnoreCase("OK")) {
						this.mensaje = "Archivo generado correctamente.";
						this.archivoAndreaniZip = resultado[1];
					} else {
						this.mensaje = resultado[0];

					}

				} else {

					this.mensaje = "Es necesario seleccionar al menos un registro.";

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
				filtro += " AND ( (nrohojarutafinal::VARCHAR) LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' OR UPPER(descrip_dt_des) LIKE '%"
						+ ocurrencia.toUpperCase().trim()
						+ "%' )  " ;
				//String[] campos = { "nrohojarutafinal", "descrip_dt_des" };
				this.totalRegistros = clientes.getTotalEntidadFiltro(
						"vClientesRemitosHojaRutaFinalReimprime", 
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
				this.clientesRemitosArchivoAndreaniList = clientes
						.getVClientesRemitosHojaRutaFinalArchivoAndreaniOcu(
								this.limit, this.offset, this.ocurrencia,
								this.idempresa);
			} else {
				this.totalRegistros = clientes.getTotalEntidadFiltro(
						"vClientesRemitosHojaRutaFinalReimprime", 
						filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.clientesRemitosArchivoAndreaniList = clientes
						.getVClientesRemitosHojaRutaFinalArchivoAndreaniAll(
								this.limit, this.offset, this.idempresa);
			}

			if (this.totalRegistros < 1
					&& Common.setNotNull(this.mensaje).equals(""))
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

	public List getClientesRemitosArchivoAndreaniList() {
		return clientesRemitosArchivoAndreaniList;
	}

	public void setClientesRemitosArchivoAndreaniList(
			List clientesRemitosArchivoAndreaniList) {
		this.clientesRemitosArchivoAndreaniList = clientesRemitosArchivoAndreaniList;
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

	public String[] getNrohojarutafinal() {
		return nrohojarutafinal;
	}

	public void setNrohojarutafinal(String[] nrohojarutafinal) {
		this.nrohojarutafinal = nrohojarutafinal;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public String getArchivoAndreaniZip() {
		return archivoAndreaniZip;
	}

	public void setArchivoAndreaniZip(String archivoAndreaniZip) {
		this.archivoAndreaniZip = archivoAndreaniZip;
	}

}
