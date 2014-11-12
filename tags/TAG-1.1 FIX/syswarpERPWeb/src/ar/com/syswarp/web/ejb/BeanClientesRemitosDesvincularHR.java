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

public class BeanClientesRemitosDesvincularHR implements SessionBean,
		Serializable {

	static Logger log = Logger
			.getLogger(BeanClientesRemitosDesvincularHR.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List listRemitos = new ArrayList();

	private String accion = "";

	private String idctrlremito = "";

	private String nrosucursal = "0000";

	private String nroremitocliente = "00000000";

	private BigDecimal idesquema;

	private String tipopedido = "N";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private Hashtable htPedidosInvolucrados = new Hashtable();

	private Hashtable htRemitosInvolucrados = new Hashtable();

	private Hashtable htCtrlRemitos = new Hashtable();

	private String nrohojaarmado = "0";

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private String[] vecIdctrlremito = null;

	private String nrohojaarmadoAnt = "0";

	private String idctrlremitoAnt = "";

	private String nrosucursalAnt = "0000";

	private String nroremitoclienteAnt = "00000000";

	private boolean buscar = false;

	private boolean anulacionPosible = true;

	private String usuarioalt = "";

	private String volver = "";

	public BeanClientesRemitosDesvincularHR() {
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

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("pedidosAnulacionRemitosLogAbm.jsp");
				return true;
			}

			this.idctrlremito = this.idctrlremito.toUpperCase();

			if (!this.accion.equalsIgnoreCase("")) {

				this.idctrlremito = this.idctrlremito.replaceAll("'", "");

				if (!Common.esEntero(this.nrohojaarmado)
						|| Long.parseLong(this.nrohojaarmado) < 1) {

					this.mensaje = "Ingrese un valor valido para el campo hoja de armado.";

				} else if (this.idctrlremito.trim().length() != 16) {

					this.mensaje = "Longitud del campo identificador debe ser de 16 caracteres.";

				} else if (!Common.esEntero(this.nrosucursal)) {

					this.mensaje = "Ingrese un valor valido para el campo raiz.";

				} else if (!Common.esEntero(this.nroremitocliente)) {

					this.mensaje = "Ingrese un valor valido para el campo comprobante.";

				} else {

					String entidad = "( SELECT nrohojaarmado, idctrlremito, nrosucursal, nroremitocliente, idempresa FROM clientesremitos ) entidad ";
					String filtro = " WHERE idempresa = " + this.idempresa
							+ " AND nrohojaarmado = " + this.nrohojaarmado
							+ " AND nrosucursal = " + this.nrosucursal
							+ " AND nroremitocliente = "
							+ this.nroremitocliente + " AND idctrlremito = '"
							+ this.idctrlremito + "'";

					long totalCtrl = clie.getTotalEntidadFiltro(entidad,
							filtro, this.idempresa);

					if (totalCtrl > 0) {

						this.listRemitos = clie
								.getRemitoClienteAnularRecursivo(
										new BigDecimal(this.nrosucursal),
										new BigDecimal(this.nroremitocliente),
										this.idctrlremito, this.tipopedido, 1,
										new Hashtable(), this.idempresa);

						Iterator it = this.listRemitos.iterator();
						while (it.hasNext()) {

							String datos[] = (String[]) it.next();

							if (this.cliente.equals("")) {

								this.idcliente = new BigDecimal(datos[5]);
								this.cliente = datos[6];

							}

							this.htPedidosInvolucrados.put(datos[0], datos[0]);
							this.htRemitosInvolucrados.put(Common.strZero(
									datos[2], 4)
									+ " - " + Common.strZero(datos[3], 8),
									datos[1]);

							// log.info("htCtrlRemitos - datos[4]: " +
							// datos[4]);
							this.htCtrlRemitos.put(datos[4], datos[1]);

							if (!Common.setNotNull(datos[9]).equals("")
									&& Common
											.setNotNull(datos[4])
											.equalsIgnoreCase(this.idctrlremito)) {
								this.anulacionPosible = false;
							}

						}

						this.totalRegistros = this.listRemitos.size();

						if (this.totalRegistros < 1)
							this.mensaje = "Registro Inexistente.";

						if (this.accion.equalsIgnoreCase("anular")
								&& this.totalRegistros != 0) {

							if (!this.anulacionPosible) {
								this.mensaje = "Imposible anular remito, el mismo  fué afectado por otro proceso.";
							} else if (!this.nrohojaarmado
									.equals(this.nrohojaarmadoAnt)
									|| !this.nroremitocliente
											.equals(this.nroremitoclienteAnt)
									|| !this.nrosucursal
											.equals(this.nrosucursalAnt)
									|| !this.idctrlremito
											.equals(this.idctrlremitoAnt)) {

								this.mensaje = "Cambiaron los parametros de búsqueda, para anular debe ejecutar nuevamente esta con los parametros que corresponda.";
								for (int u = 0; u < this.vecIdctrlremito.length; u++) {
									this.vecIdctrlremito[u] = "";
								}

							} else

							// if (validarNroControl())

							{

								// if (this.tipopedido.equalsIgnoreCase("N"))

								this.mensaje = Common
										.getClientes()
										.clientesRemitosDesvincularHA(
												new BigDecimal(
														this.nrohojaarmado),
												this.idctrlremito,
												new BigDecimal(this.nrosucursal),
												new BigDecimal(
														this.nroremitocliente),
												this.tipopedido,
												this.idempresa, usuarioalt);

								if (this.mensaje.equalsIgnoreCase("OK")) {

									this.mensaje = "Desvinculación efectuada con éxito.";

								}

							}

						}

						//

					} else {

						this.mensaje = "No existen datos para el criterio de busqueda actual.";

					}

				}

			} else {

				this.mensaje = "Ingrese Identificador, Remito y HA.";

			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	private boolean validarNroControl() {

		boolean fSalida = true;

		if (this.vecIdctrlremito == null) {

			this.mensaje = "Es necesario ingresar Nro. Control";
			return false;

		}

		for (int v = 0; v < this.vecIdctrlremito.length; v++) {

			if (Common.setNotNull(this.vecIdctrlremito[v]).equals("")) {

				this.mensaje = "Debe ingresar Nro. Control.  ";
				return false;

			} else if (Common.setNotNull(this.vecIdctrlremito[v]).length() != 16) {

				this.mensaje = "Longitud Nro. Control debe ser de dieciseis (16) caracteres: "
						+ this.vecIdctrlremito[v];
				return false;

			} else if (Common.setNotNull(this.vecIdctrlremito[v]).indexOf(" ") > -1) {

				this.mensaje = "Nro. Control no puede conetener espacios: "
						+ this.vecIdctrlremito[v];
				return false;

			}

		}

		return fSalida;

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

	public List getListRemitos() {
		return listRemitos;
	}

	public void setListRemitos(List listRemitos) {
		this.listRemitos = listRemitos;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getIdctrlremito() {
		return idctrlremito;
	}

	public void setIdctrlremito(String idctrlremito) {
		this.idctrlremito = idctrlremito;
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

	public BigDecimal getIdesquema() {
		return idesquema;
	}

	public void setIdesquema(BigDecimal idesquema) {
		this.idesquema = idesquema;
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

	public Hashtable getHtPedidosInvolucrados() {
		return htPedidosInvolucrados;
	}

	public void setHtPedidosInvolucrados(Hashtable htPedidosInvolucrados) {
		this.htPedidosInvolucrados = htPedidosInvolucrados;
	}

	public Hashtable getHtRemitosInvolucrados() {
		return htRemitosInvolucrados;
	}

	public void setHtRemitosInvolucrados(Hashtable htRemitosInvolucrados) {
		this.htRemitosInvolucrados = htRemitosInvolucrados;
	}

	public String getNrohojaarmado() {
		return nrohojaarmado;
	}

	public void setNrohojaarmado(String nrohojaarmado) {
		this.nrohojaarmado = nrohojaarmado;
	}

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

	public String[] getVecIdctrlremito() {
		return vecIdctrlremito;
	}

	public void setVecIdctrlremito(String[] vecIdctrlremito) {
		this.vecIdctrlremito = vecIdctrlremito;
	}

	public String getNrohojaarmadoAnt() {
		return nrohojaarmadoAnt;
	}

	public void setNrohojaarmadoAnt(String nrohojaarmadoAnt) {
		this.nrohojaarmadoAnt = nrohojaarmadoAnt;
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

}
