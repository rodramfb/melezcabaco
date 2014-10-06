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

public class BeanMovInOutArticulosLov implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanMovInOutArticulosLov.class);

	private GeneralBean gb = new GeneralBean();

	private BigDecimal idempresa = new BigDecimal(-1);

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

	private String[] cantidad = null;

	private String[] delKey = null;

	private String eliminar = "";

	private BigDecimal total = new BigDecimal(0);

	/***/

	private String tipomov = "";

	private String relacionHash = "";

	public BeanMovInOutArticulosLov() {
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
		Proveedores proveed = Common.getProveedores();

		// Hashtable htArticulosInOutOK = (Hashtable) session
		// .getAttribute("htArticulosInOutOK") != null ? (Hashtable) session
		// .getAttribute("htArticulosInOutOK")
		// : new Hashtable();
		//
		// Hashtable htArticulosInOut = (Hashtable) session
		// .getAttribute("htArticulosInOut") != null
		// && !((Hashtable) session.getAttribute("htArticulosInOut"))
		// .isEmpty() ? (Hashtable) session
		// .getAttribute("htArticulosInOut") : htArticulosInOutOK;

		Hashtable htArticulosInOutOK = (Hashtable) session
				.getAttribute("htArticulosInOutOK") != null ? (Hashtable) session
				.getAttribute("htArticulosInOutOK")
				: new Hashtable();

		Hashtable htArticulosInOut = new Hashtable();

		if ((Hashtable) session.getAttribute("htArticulosInOut") != null
		// && !((Hashtable) session.getAttribute("htArticulosInOut")).isEmpty()
		) {

			htArticulosInOut = (Hashtable) session
					.getAttribute("htArticulosInOut");

		} else {

			Common.htRellenar(htArticulosInOutOK, htArticulosInOut);
		}

		try {

			listDepositos = proveed.getProveedoStockDepositosAll(250, 0,
					this.idempresa);

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			String filtro = " WHERE inventa_st = 'S' ";

			if (buscar && accion.equals("")) {
				// String[] campos = { "codigo_st", "alias_st" };

				filtro += " AND ( codigo_st LIKE '%"
						+ this.ocurrencia.toUpperCase()
						+ "%' OR alias_st LIKE '%"
						+ this.ocurrencia.toUpperCase() + "%') ";

				this.totalRegistros = proveed.getTotalEntidadFiltro(
						"stockStock", filtro, this.idempresa);

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
				this.proveedoArticulosList = proveed.getProveedoArticulosOcu(
						this.limit, this.offset, this.ocurrencia,
						this.idempresa);
			} else {

				this.totalRegistros = proveed.getTotalEntidadFiltro(
						"stockStock", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.proveedoArticulosList = proveed.getProveedoArticulosAll(
						this.limit, this.offset, this.idempresa);
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";

			if (this.accion.equalsIgnoreCase("agregar")) {

				if (this.codigo_st == null || this.codigo_st.equals("")) {
					this.mensaje = "Seleccione un articulo.";

				} else {

					List listaArt = proveed.getProveedoArticulosPK(
							this.codigo_st, this.idempresa);
					Iterator iterArt = listaArt.iterator();
					while (iterArt.hasNext()) {
						String[] datosArt = (String[]) iterArt.next();
						htArticulosInOut.put(Common.initObjectTime() + "",
								datosArt);
					}

					/**/

				}
			} else if (this.accion.equals("eliminar")) {
				if (this.delKey == null) {
					this.mensaje = "Seleccione un articulo a eliminar.";
				} else {
					for (int i = 0; i < this.delKey.length; i++) {
						htArticulosInOut.remove(this.delKey[i]);
					}
				}
			} else if (this.accion.equals("cambiodeposito")) {
				Hashtable htArticulosSerieDespachoOK = (Hashtable) session
						.getAttribute("htArticulosSerieDespachoOK");

				if (htArticulosSerieDespachoOK != null
						&& !htArticulosSerieDespachoOK.isEmpty()) {

					htArticulosSerieDespachoOK.remove(this.relacionHash);
					session.setAttribute("htArticulosSerieDespachoOK",
							htArticulosSerieDespachoOK);
					// Cambio aplicado 20100121
					htArticulosInOutOK.remove(this.relacionHash);
					session.setAttribute("htArticulosInOutOK",
							htArticulosInOutOK);

				}
			}

			session.setAttribute("htArticulosInOut", htArticulosInOut);

			if (this.accion.equalsIgnoreCase("confirmar")
					&& this.asignarValidar(htArticulosInOut)) {
				session.setAttribute("htArticulosInOutOK", htArticulosInOut);
				session.removeAttribute("htArticulosInOut");
			}// 20100118 -EJV
			else
				this.asignarValidar(htArticulosInOut);

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
							&& Float.parseFloat(this.cantidad[i]) > 0) {
						datos[11] = (gb.getNumeroFormateado(Float
								.parseFloat(datos[5])
								* Float.parseFloat(this.cantidad[i]), 100, 2))
								+ "";
						total = total.add(new BigDecimal(datos[11]));
					} else
						fOk = false;

					datos[9] = this.deposito[i];
					datos[10] = this.cantidad[i];

					if (htDepArt.containsKey(datos[0] + datos[9])) {
						mensajeAV = "Imposible seleccionar mismo articulo: "
								+ datos[0] + " - mismo destino: " + datos[9];
						fOk = false;
					}
					// Verificar seleccion de depostito
					if (this.deposito[i].equals("-1")) {
						fOk = false;
					} else {
						htDepArt.put(datos[0] + datos[9], "");
					}

					ht.put(this.keyHashDatosArticulo[i], datos);

				}

			}
			if (!fOk && mensajeAV.equals("")) {
				if (this.accion.equalsIgnoreCase("confirmar"))
					this.mensaje = "Verifique depositos seleccionados y cantidades ingresadas.";
			} else
				this.mensaje = mensajeAV;

			// 20100118 - EJV

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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getTipomov() {
		return tipomov;
	}

	public void setTipomov(String tipomov) {
		this.tipomov = tipomov;
	}

	public String getRelacionHash() {
		return relacionHash;
	}

	public void setRelacionHash(String relacionHash) {
		this.relacionHash = relacionHash;
	}

}
