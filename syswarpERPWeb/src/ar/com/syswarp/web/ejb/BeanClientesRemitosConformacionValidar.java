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

public class BeanClientesRemitosConformacionValidar implements SessionBean,
		Serializable {

	static Logger log = Logger
			.getLogger(BeanClientesRemitosConformacionValidar.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private String accion = "";

	private String nrosucursal = "0000";

	private String nroremitocliente = "00000000";

	private String tipopedido = "N";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String idctrlremitoAnt = "";

	private String nrosucursalAnt = "0000";

	private String nroremitoclienteAnt = "00000000";

	private boolean buscar = false;

	private String usuarioalt = "";

	private String volver = "";

	private BigDecimal idestado = new BigDecimal(-1);

	private String tipoinput = "L";

	private List listEstadosRemito = new ArrayList();

	public BeanClientesRemitosConformacionValidar() {
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

		Clientes clie = Common.getClientes();
		try {

			this.listEstadosRemito = clie.getClientesRemitosEstadosAll(50, 0,
					this.idempresa);

			if (this.accion.equalsIgnoreCase("validarestado")) {

				if (this.idestado == null || this.idestado.longValue() < 1) {

					this.mensaje = "Seleccione estado.";

				} else if (!Common.esEntero(this.nrosucursal)) {

					this.mensaje = "Ingrese Nro. de Sucursal valida.";

				} else if (!Common.esEntero(this.nroremitocliente)) {

					this.mensaje = "Ingrese Nro. de Comprobante valido.";

				} else {

					this.mensaje = clie.clientesRemitosValidarEstado(
							new BigDecimal(this.nrosucursal), new BigDecimal(
									this.nroremitocliente), this.idestado,
							this.tipoinput, this.usuarioalt, this.idempresa);

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

	public String getNrosucursal() {
		return nrosucursal;
	}

	public void setNrosucursal(String nrosucursal) {
		this.nrosucursal = nrosucursal;
	}

	public String getNroremitocliente() {
		return nroremitocliente;
	}

	public void setNroremitocliente(String nroremitocliente) {
		this.nroremitocliente = nroremitocliente;
	}

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
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

	public String getIdctrlremitoAnt() {
		return idctrlremitoAnt;
	}

	public void setIdctrlremitoAnt(String idctrlremitoAnt) {
		this.idctrlremitoAnt = idctrlremitoAnt;
	}

	public String getNrosucursalAnt() {
		return nrosucursalAnt;
	}

	public void setNrosucursalAnt(String nrosucursalAnt) {
		this.nrosucursalAnt = nrosucursalAnt;
	}

	public String getNroremitoclienteAnt() {
		return nroremitoclienteAnt;
	}

	public void setNroremitoclienteAnt(String nroremitoclienteAnt) {
		this.nroremitoclienteAnt = nroremitoclienteAnt;
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

	public BigDecimal getIdestado() {
		return idestado;
	}

	public String getTipoinput() {
		return tipoinput;
	}

	public void setTipoinput(String tipoinput) {
		this.tipoinput = tipoinput;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
	}

	public List getListEstadosRemito() {
		return listEstadosRemito;
	}

	public void setListEstadosRemito(List listEstadosRemito) {
		this.listEstadosRemito = listEstadosRemito;
	}

}
