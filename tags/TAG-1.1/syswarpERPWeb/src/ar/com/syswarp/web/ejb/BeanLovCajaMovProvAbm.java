/* 
 javabean para la entidad: proveedoMovProv
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jul 27 09:30:45 GMT-03:00 2006 
 
 Para manejar la pagina: proveedoMovProvAbm.jsp
 
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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanLovCajaMovProvAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanLovCajaMovProvAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private long limit = 10; 

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List proveedoMovProvList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal nrointerno = BigDecimal.valueOf(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	boolean buscar = false;

	private BigDecimal idproveedor = new BigDecimal(-1);

	private String proveedor = "";

	private String[] delKey = null;

	private String[] keyHashDatosCompProv = null;

	private String[] importe = null;

	public BeanLovCajaMovProvAbm() {
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
		Tesoreria proveedoMovProv = Common.getTesoreria();
		try {

			Tesoreria caja = Common.getTesoreria();
			Hashtable htComprobantesProv = (Hashtable) session
					.getAttribute("htComprobantesProv") != null ? (Hashtable) session
					.getAttribute("htComprobantesProv")
					: (Hashtable) session.getAttribute("htComprobantesProvOK");

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			String filtro = " WHERE idproveedor = " + this.idproveedor
					+ " AND saldo > 0 AND tipomov < 3 ";
			if (buscar) {
				filtro += " AND comprob::VARCHAR like '" + this.ocurrencia + "%'";

				this.totalRegistros = proveedoMovProv.getTotalEntidadFiltro(
						"proveedoMovProv", filtro, this.idempresa);
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
				this.proveedoMovProvList = proveedoMovProv
						.getLovCajaMovProvOcu(this.limit, this.offset,
								this.idproveedor, this.ocurrencia,
								this.idempresa);
			} else {
				this.totalRegistros = proveedoMovProv.getTotalEntidadFiltro(
						"proveedoMovProv", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.proveedoMovProvList = proveedoMovProv
						.getLovCajaMovProvAll(this.limit, this.offset,
								this.idproveedor, this.idempresa);
			}
			if (this.idproveedor.compareTo(new BigDecimal(0)) <= 0)
				this.mensaje = "Es necesario seleccionar proveedor.";
			else if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";

			if (this.accion.equalsIgnoreCase("eliminar")) {
				if (this.delKey == null) {
					this.mensaje = "Seleccione un registro a eliminar.";
				} else {
					for (int i = 0; i < this.delKey.length; i++) {
						if (htComprobantesProv.containsKey(this.delKey[i])) {
							htComprobantesProv.remove(this.delKey[i]);
						}
					}
				}
			}

			if (this.accion.equalsIgnoreCase("agregar")) {

				if (this.nrointerno.compareTo(new BigDecimal(0)) <= 0) {
					this.mensaje = "Seleccione comprobante.";
				} else {
					List listaComp = caja.getLovCajaMovProvPK(this.nrointerno,
							this.idempresa);
					if (listaComp.size() > 0) {
						String[] datos = (String[]) listaComp.get(0);
						htComprobantesProv.put(this.nrointerno.toString(),
								datos);
					} else {
						this.mensaje = "Imposible recuperar los datos del comprobante seleccionado.";
					}
				}

			}

			if (this.accion.equalsIgnoreCase("adelanto")) {
				String[] datos = new String[] { "99999999", "0.00", "0.00", "",
						"", "", "0.00", "-1", this.idproveedor.toString(),
						"0.00" };
				htComprobantesProv.put("ADELANTO", datos);
			}

			this.asignarValidar(htComprobantesProv);

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	private boolean asignarValidar(Hashtable ht) {
		boolean fOk = true;
		String mensaje = "";
		try {

			if (keyHashDatosCompProv != null) {
				for (int i = 0; i < keyHashDatosCompProv.length; i++) {
					if (ht.containsKey(keyHashDatosCompProv[i])) {
						String[] datos = (String[]) ht
								.get(keyHashDatosCompProv[i]);
						/*
						 * // TODO: esta pendiente discriminar entre tipos de //
						 * //documentos NC - ND - FA
						 */
						this.importe[i] = this.importe[i].trim();

						if (!Common.esNumerico(this.importe[i])) {
							fOk = false;
							mensaje = "Importe a pagar solo admite valores numericos.";

						} else if (!datos[0].equals("99999999")
								&& new BigDecimal(this.importe[i])
										.compareTo(new BigDecimal(datos[6])) > 0) {
							fOk = false;
							mensaje = "Importe no puede ser mayor al saldo del comprobante.";
						} else if (new BigDecimal(this.importe[i])
								.compareTo(new BigDecimal(0)) < 1) {
							fOk = false;
							mensaje = "Importe no puede ser menor o igual a cero.";
						}

						if (fOk) {
							datos[1] = Common.getGeneral().getNumeroFormateado(
									Float.parseFloat(this.importe[i]), 10, 2);
						} else {
							datos[1] = this.importe[i];
						}

						ht.put(this.keyHashDatosCompProv[i], datos);

					}
				}
			}
			if ((this.accion.equalsIgnoreCase("confirmar") || (ht.isEmpty() && this.accion
					.equalsIgnoreCase("eliminar")))
					&& mensaje.equals("")) {

				session.setAttribute("htComprobantesProvOK", ht);
				session.removeAttribute("htComprobantesProv");

			} else {
				if (!mensaje.equals(""))
					this.mensaje = mensaje;
				session.setAttribute("htComprobantesProv", ht);
			}

		} catch (Exception e) {
			log.error("asignarValidar: " + e);
		}
		return fOk;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
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

	public List getProveedoMovProvList() {
		return proveedoMovProvList;
	}

	public void setProveedoMovProvList(List proveedoMovProvList) {
		this.proveedoMovProvList = proveedoMovProvList;
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

	public BigDecimal getNrointerno() {
		return nrointerno;
	}

	public void setNrointerno(BigDecimal nrointerno) {
		this.nrointerno = nrointerno;
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

	public BigDecimal getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(BigDecimal idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String[] getDelKey() {
		return delKey;
	}

	public void setDelKey(String[] delKey) {
		this.delKey = delKey;
	}

	public String[] getKeyHashDatosCompProv() {
		return keyHashDatosCompProv;
	}

	public void setKeyHashDatosCompProv(String[] keyHashDatosCompProv) {
		this.keyHashDatosCompProv = keyHashDatosCompProv;
	}

	public String[] getImporte() {
		return importe;
	}

	public void setImporte(String[] importe) {
		this.importe = importe;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}
}
