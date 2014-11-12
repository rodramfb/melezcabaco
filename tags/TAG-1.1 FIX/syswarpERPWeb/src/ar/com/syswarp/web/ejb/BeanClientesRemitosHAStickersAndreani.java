/* 
   javabean para la entidad: bacoObsequiosEsquema
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Nov 06 11:36:23 GMT-03:00 2009 
   
   Para manejar la pagina: bacoObsequiosEsquemaAbm.jsp
      
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

public class BeanClientesRemitosHAStickersAndreani implements SessionBean,
		Serializable {

	static Logger log = Logger
			.getLogger(BeanClientesRemitosHAStickersAndreani.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private String accion = "";

	private String nrohojaarmado = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private boolean buscar = false;

	private String usuarioalt = "";

	private String volver = "";

	public BeanClientesRemitosHAStickersAndreani() {
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

		try {

			if (this.accion.equalsIgnoreCase("generarstickers")) {

				if (!Common.esEntero(this.nrohojaarmado)) {

					this.mensaje = "Ingrese Nro. Hoja Armado valido.";

				} else {

					this.mensaje = Common.getClientes()
							.clientesRemitosGeneraStickersAndreani(
									new BigDecimal(this.nrohojaarmado),
									this.idempresa, this.usuarioalt);
					
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

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
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

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getVolver() {
		return volver;
	}

	public void setVolver(String volver) {
		this.volver = volver;
	}

	public String getNrohojaarmado() {
		return nrohojaarmado;
	}

	public void setNrohojaarmado(String nrohojaarmado) {
		this.nrohojaarmado = nrohojaarmado;
	}

}
