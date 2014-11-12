/* 
 javabean para la entidad: stockstock 
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Aug 02 14:39:14 GMT-03:00 2006 
 
 Para manejar la pagina: proveedoArticulosAbm.jsp
 
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

public class BeanLovArticulosOc implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanLovArticulosOc.class);

	private BigDecimal idempresa = new BigDecimal(-1);

	private GeneralBean gb = new GeneralBean();

	private SessionContext context;

	private int limit = 10;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List proveedoArticulosList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String codigo_st = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	boolean buscar = false;

	private List listDepositos = new ArrayList();

	/***/

	private String[] keyHashDatosArticulo = null;

	private String[] deposito = null;

	private String[] costosugerido = null;

	private String[] cantidad = null;

	private String[] delKey = null;

	private String eliminar = "";

	private BigDecimal total = new BigDecimal(0);

	/***/

	public BeanLovArticulosOc() {
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
		Proveedores proveedoArticulos = Common.getProveedores();

		Hashtable htArticulosInOutOk = (Hashtable) session
				.getAttribute("htArticulosInOutOK") != null ? (Hashtable) session
				.getAttribute("htArticulosInOutOK")
				: new Hashtable();

		Hashtable htArticulosInOut = (Hashtable) session
				.getAttribute("htArticulosInOut") != null
				&& !((Hashtable) session.getAttribute("htArticulosInOut"))
						.isEmpty() ? (Hashtable) session
				.getAttribute("htArticulosInOut") : htArticulosInOutOk;

		try {

			listDepositos = proveedoArticulos.getProveedoStockDepositosAll(250,
					0, this.idempresa);

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			if (buscar && accion.equals("")) {
				String[] campos = { "codigo_st", "alias_st" };
				this.totalRegistros = proveedoArticulos.getTotalEntidadOcu(
						"stockStock", campos, this.ocurrencia, this.idempresa);
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
				this.proveedoArticulosList = proveedoArticulos
						.getProveedoArticulosOcu(this.limit, this.offset,
								this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = proveedoArticulos.getTotalEntidad(
						"stockstock", this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.proveedoArticulosList = proveedoArticulos
						.getProveedoArticulosAll(this.limit, this.offset,
								this.idempresa);
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";

			if (this.accion.equalsIgnoreCase("agregar")) {

				if (this.codigo_st == null || this.codigo_st.equals("")) {
					this.mensaje = "Seleccione un articulo.";

				} else {

					List listaArt = proveedoArticulos.getProveedoArticulosPK(
							this.codigo_st, this.idempresa);
					Iterator iterArt = listaArt.iterator();
					while (iterArt.hasNext()) {
						String[] datosArt = (String[]) iterArt.next();
						htArticulosInOut.put(Common.initObjectTime() + "",
								datosArt);
					}

					/**/

				}
			}

			if (this.accion.equals("eliminar")) {
				if (this.delKey == null) {
					this.mensaje = "Seleccione un artículo a eliminar.";
				} else {
					for (int i = 0; i < this.delKey.length; i++) {
						htArticulosInOut.remove(this.delKey[i]);
					}
				}
			}

			session.setAttribute("htArticulosInOut", htArticulosInOut);

			if (this.accion.equalsIgnoreCase("confirmar")
					&& this.asignarValidar(htArticulosInOut)) {
				session.setAttribute("htArticulosInOutOK", htArticulosInOut);
				session.removeAttribute("htArticulosInOut");
			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public boolean asignarValidar(Hashtable ht) {
		boolean fOk = true;
		Hashtable htDepArt = new Hashtable();
		String mensajeAV = "";
		if (this.keyHashDatosArticulo != null) {
			for (int i = 0; i < this.keyHashDatosArticulo.length; i++) {
				if (ht.containsKey(this.keyHashDatosArticulo[i])) {
					String[] datos = (String[]) ht
							.get(this.keyHashDatosArticulo[i]);
					// Verificar tipo datos cantidad

					datos[5] = gb.getNumeroFormateado(Float
							.parseFloat(datos[5]), 10, 2)
							+ "";
					if (gb.esNumerico(this.cantidad[i])
							&& Float.parseFloat(this.cantidad[i]) > 0
							&& gb.esNumerico(this.costosugerido[i])) {
						datos[11] = (gb.getNumeroFormateado(Float
								.parseFloat(this.costosugerido[i])
								* Float.parseFloat(this.cantidad[i]), 10, 2))
								+ "";
						total = total.add(new BigDecimal(datos[11]));
					} else
						fOk = false;

					// datos[9] = "";//this.deposito[i];
					datos[5] = this.costosugerido[i];
					datos[10] = this.cantidad[i];

					ht.put(this.keyHashDatosArticulo[i], datos);

				}

			}
			if (!fOk && mensajeAV.equals(""))
				this.mensaje = "Verifique costos y cantidades ingresadas.";
			else
				mensaje = mensajeAV;
		}
		htDepArt = null;
		return fOk;
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

	public List getProveedoArticulosList() {
		return proveedoArticulosList;
	}

	public void setProveedoArticulosList(List proveedoArticulosList) {
		this.proveedoArticulosList = proveedoArticulosList;
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

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
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

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String[] getCantidad() {
		return cantidad;
	}

	public void setCantidad(String[] cantidad) {
		this.cantidad = cantidad;
	}

	public String[] getDeposito() {
		return deposito;
	}

	public void setDeposito(String[] deposito) {
		this.deposito = deposito;
	}

	public String[] getKeyHashDatosArticulo() {
		return keyHashDatosArticulo;
	}

	public void setKeyHashDatosArticulo(String[] keyHashDatosArticulo) {
		this.keyHashDatosArticulo = keyHashDatosArticulo;
	}

	public String[] getDelKey() {
		return delKey;
	}

	public void setDelKey(String[] delKey) {
		this.delKey = delKey;
	}

	public String getEliminar() {
		return eliminar;
	}

	public void setEliminar(String eliminar) {
		this.eliminar = eliminar;
	}

	public List getListDepositos() {
		return listDepositos;
	}

	public String[] getCostosugerido() {
		return costosugerido;
	}

	public void setCostosugerido(String[] costosugerido) {
		this.costosugerido = costosugerido;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
