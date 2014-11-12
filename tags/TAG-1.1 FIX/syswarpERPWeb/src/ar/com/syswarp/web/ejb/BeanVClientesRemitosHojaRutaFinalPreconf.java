/* 
   javabean para la entidad: vClientesRemitosHojaRutaFinalPreconf
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 20 10:43:23 ART 2010 
   
   Para manejar la pagina: vClientesRemitosHojaRutaFinalPreconfAbm.jsp
      
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

public class BeanVClientesRemitosHojaRutaFinalPreconf implements SessionBean,
		Serializable {

	static Logger log = Logger
			.getLogger(BeanVClientesRemitosHojaRutaFinalPreconf.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vClientesRemitosHojaRutaFinalPreconfList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String[] nrohojarutafinal = null;

	private String mensaje = "";

	private String usuarioact = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private boolean buscar = false;

	public BeanVClientesRemitosHojaRutaFinalPreconf() {
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

			if (this.accion.equalsIgnoreCase("asignarestadopreconformacion")) {

				if (this.nrohojarutafinal == null
						|| this.nrohojarutafinal.length == 0) {

					this.mensaje = "Seleccione al menos una Hoja de Ruta Final a preconformar.";

				} else {

					this.mensaje = clientes.clientesRemitosPreconformarXHRF(
							this.nrohojarutafinal, this.usuarioact,
							this.idempresa);

					if (this.mensaje.equalsIgnoreCase("OK")) {
						this.mensaje = "Remitos asociados a hojas de rutas seleccionadas, preconformados correctamente.";
					}

					log.info("EJECUTAR PREC ... ");
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
				String[] campos = { "nrohojarutafinal", "nropallets" };
				this.totalRegistros = clientes.getTotalEntidadOcu(
						"vClientesRemitosHojaRutaFinalPreconf", campos,
						this.ocurrencia, this.idempresa);
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
				this.vClientesRemitosHojaRutaFinalPreconfList = clientes
						.getVClientesRemitosHojaRutaFinalPreconfOcu(this.limit,
								this.offset, this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = clientes.getTotalEntidad(
						"vClientesRemitosHojaRutaFinalPreconf", this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.vClientesRemitosHojaRutaFinalPreconfList = clientes
						.getVClientesRemitosHojaRutaFinalPreconfAll(this.limit,
								this.offset, this.idempresa);
			}
			if (this.totalRegistros < 1)
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

	public List getVClientesRemitosHojaRutaFinalPreconfList() {
		return vClientesRemitosHojaRutaFinalPreconfList;
	}

	public void setVClientesRemitosHojaRutaFinalPreconfList(
			List vClientesRemitosHojaRutaFinalPreconfList) {
		this.vClientesRemitosHojaRutaFinalPreconfList = vClientesRemitosHojaRutaFinalPreconfList;
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

	public String[] getNrohojarutafinal() {
		return nrohojarutafinal;
	}

	public void setNrohojarutafinal(String[] nrohojarutafinal) {
		this.nrohojarutafinal = nrohojarutafinal;
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

}
