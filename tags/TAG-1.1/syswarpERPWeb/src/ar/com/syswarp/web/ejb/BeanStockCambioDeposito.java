/* 
 javabean para la entidad: stockstock 
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Aug 02 14:39:14 GMT-03:00 2006 
 
 Para manejar la pagina: proveedoArticulosAbm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

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

public class BeanStockCambioDeposito implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanStockCambioDeposito.class);

	private GeneralBean gb = new GeneralBean();

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

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

	private String usuarioalt = "";

	private String fechamov = Common.initObjectTimeStr();

	private List listDepositos = new ArrayList();

	/***/

	private String[] keyHashDatosArticulo = null;

	private String[] origen = null;

	private String[] destino = null;

	private String[] cantidad = null;

	private String[] delKey = null;

	private String tipomov = "";

	private String eliminar = "";

	/**
	 * Campos previstos para recuperar el proximo numero de comprobante
	 * correspondiente
	 * 
	 */

	private BigDecimal num_cnt = new BigDecimal(-1);

	private BigDecimal sucursal = new BigDecimal(-1);

	private String tipo = "";

	/***/

	private String observaciones = "";

	private boolean mismorigdest = false;

	private BigDecimal idcontadorcomprobante = new BigDecimal(-1);

	private boolean primeraCarga = true;

	private String relacionHash = "";

	public BeanStockCambioDeposito() {
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

	public void ejecutarSentenciaDML() {
		try {
			String[] resultado = null;
			Stock stock = Common.getStock();
			Hashtable htArticulosMovimiento = (Hashtable) session
					.getAttribute("htArticulosMovimiento");

			Hashtable htSerieDespacho = (Hashtable) session
					.getAttribute("htArticulosSerieDespachoOK");

			if (this.accion.equalsIgnoreCase("confirmar"))
				resultado = stock.callStockMovInternoCreate((Timestamp) Common
						.setObjectToStrOrTime(this.fechamov, "strToJSTs"),
						this.tipomov, this.num_cnt, this.sucursal, this.tipo,
						this.observaciones, htArticulosMovimiento,
						this.idcontadorcomprobante, htSerieDespacho,
						this.usuarioalt, this.idempresa);
			if (resultado[0].equalsIgnoreCase("OK")) {

				/*
				 * response
				 * .sendRedirect("stockRemitoInternoFrame.jsp?remito_interno=" +
				 * this.mensaje + "&fechamov=" + this.fechamov);
				 */
				/*
				 * response
				 * .sendRedirect("../reportes/jasper/generaPDF.jsp?remito_interno="
				 * + this.mensaje +
				 * "&plantillaImpresionJRXML=cambio.deposito.frame&tipo=Cambio
				 * Deposito");
				 */
				htArticulosMovimiento = null;
				session.removeAttribute("htArticulosMovimiento");
				response
						.sendRedirect("impresionRemitosInternos.jsp?remitos="
								+ resultado[1]
								+ "&tipo=Cambio Deposito&plantillaImpresionJRXML=cambio_deposito_frame");
			} else {
				this.mensaje = resultado[0];
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		boolean valoresOk = false;
		Stock stock = Common.getStock();
		Proveedores proveedoArticulos = Common.getProveedores();

		if (this.primeraCarga) {
			session.removeAttribute("htArticulosMovimiento");
			session.removeAttribute("htArticulosSerieDespacho");
			session.removeAttribute("htArticulosSerieDespachoOK");
		}

		Hashtable htArticulosMovimiento = (Hashtable) session
				.getAttribute("htArticulosMovimiento") != null ? (Hashtable) session
				.getAttribute("htArticulosMovimiento")
				: new Hashtable();
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

			String filtro = " WHERE inventa_st = 'S' ";

			if (buscar && accion.equals("")) {
				// String[] campos = { "codigo_st", "alias_st" };

				filtro += " AND ( codigo_st LIKE '%"
						+ this.ocurrencia.toUpperCase()
						+ "%' OR alias_st LIKE '%"
						+ this.ocurrencia.toUpperCase() + "%') ";

				this.totalRegistros = proveedoArticulos.getTotalEntidadFiltro(
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
				this.proveedoArticulosList = proveedoArticulos
						.getProveedoArticulosOcu(this.limit, this.offset,
								this.ocurrencia, this.idempresa);
			} else {
				this.totalRegistros = proveedoArticulos.getTotalEntidadFiltro(
						"stockstock", filtro, this.idempresa);
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

					List listaArt = stock.getStockArticulosPK(this.codigo_st,
							this.idempresa);
					Iterator iterArt = listaArt.iterator();
					while (iterArt.hasNext()) {
						String[] datosArt = (String[]) iterArt.next();

						// 20100113-EJV: Para facilitar ingreso por nro de
						// serie,
						// de todos modos, la aplicacion original (+) no
						// permitia cargar el mismo articulo dos veces en un
						// cambio de deposito.
						// ----------------------------------------------
						// htArticulosMovimiento.put(Common.initObjectTime() +
						// "",
						// datosArt);
						// ----------------------------------------------

						htArticulosMovimiento.put(this.codigo_st, datosArt);

					}

					/**/

				}
			}

			if (this.accion.equals("eliminar")) {
				if (this.delKey == null) {
					this.mensaje = "Seleccione un artículo a eliminar.";
				} else {
					for (int i = 0; i < this.delKey.length; i++) {
						htArticulosMovimiento.remove(this.delKey[i]);
					}
				}
			}

			valoresOk = this.asignarValidar(htArticulosMovimiento);
			session
					.setAttribute("htArticulosMovimiento",
							htArticulosMovimiento);

			if (this.accion.equalsIgnoreCase("confirmar") && valoresOk) {

				if (this.fechamov.equals("")) {
					this.mensaje = "Ingrese fecha movimiento.";
					return false;
				}

				if (this.tipomov.equals("")) {
					this.mensaje = "Seleccione tipo comprobante.";
					return false;
				}

				this.ejecutarSentenciaDML();

			}

			if (this.accion.equals("cambiodeposito")) {
				Hashtable htArticulosSerieDespachoOK = (Hashtable) session
						.getAttribute("htArticulosSerieDespachoOK");

				if (htArticulosSerieDespachoOK != null
						&& !htArticulosSerieDespachoOK.isEmpty()) {

					htArticulosSerieDespachoOK.remove(this.relacionHash);
					session.setAttribute("htArticulosSerieDespachoOK",
							htArticulosSerieDespachoOK);
					// Cambio aplicado 20100121
					// htArticulosInOutOK.remove(this.relacionHash);
					// session.setAttribute("htArticulosInOutOK",
					// htArticulosInOutOK);

				}
			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public boolean asignarValidar(Hashtable ht) {
		boolean fOk = true;
		int j = 0;

		if (this.keyHashDatosArticulo != null) {
			for (int i = 0; i < this.keyHashDatosArticulo.length; i++) {

				if (ht.containsKey(this.keyHashDatosArticulo[i])) {
					String[] datos = (String[]) ht
							.get(this.keyHashDatosArticulo[i]);
					if (!gb.esNumerico(this.cantidad[i])
							|| Float.parseFloat(this.cantidad[i]) < 0)
						fOk = false;
					else if (this.origen[i].equals("-1"))
						fOk = false;
					else if (this.destino[i].equals("-1"))
						fOk = false;
					else if (this.origen[i].equals(this.destino[i]))
						fOk = false;

					// NO APLICA A ARTICULOS CON SERIE O DESPACHO
					// && !datos[8].equalsIgnoreCase("S")
					// && !datos[9].equalsIgnoreCase("S")
					if (!isMismorigdest() || !datos[8].equalsIgnoreCase("S")
							|| !datos[9].equalsIgnoreCase("S"))
						j = i;

					// datos[0] = codigo_st
					// datos[1] = alias_st
					// datos[2] = descrip_st
					// datos[3] = descri2_st
					datos[4] = this.cantidad[i];
					datos[5] = this.origen[j];
					datos[6] = this.destino[j];
					// datos[7] = cost_uc_st

					ht.put(this.keyHashDatosArticulo[i], datos);
				}

			}
			if (!fOk && accion.equalsIgnoreCase("confirmar"))
				this.mensaje = "Verifique origen-destino seleccionados y cantidades ingresados.";

		}
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

	public String[] getOrigen() {
		return origen;
	}

	public void setOrigen(String[] deposito) {
		this.origen = deposito;
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

	public String[] getDestino() {
		return destino;
	}

	public void setDestino(String[] destino) {
		this.destino = destino;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getFechamov() {
		return fechamov;
	}

	public void setFechamov(String fechamov) {
		this.fechamov = fechamov;
	}

	public String getTipomov() {
		return tipomov;
	}

	public void setTipomov(String tipomov) {
		this.tipomov = tipomov;
	}

	public BigDecimal getNum_cnt() {
		return num_cnt;
	}

	public void setNum_cnt(BigDecimal num_cnt) {
		this.num_cnt = num_cnt;
	}

	public BigDecimal getSucursal() {
		return sucursal;
	}

	public void setSucursal(BigDecimal sucursal) {
		this.sucursal = sucursal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isMismorigdest() {
		return mismorigdest;
	}

	public void setMismorigdest(boolean mismorigdest) {
		this.mismorigdest = mismorigdest;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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

	public BigDecimal getIdcontadorcomprobante() {
		return idcontadorcomprobante;
	}

	public void setIdcontadorcomprobante(BigDecimal idcontadorcomprobante) {
		this.idcontadorcomprobante = idcontadorcomprobante;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public String getRelacionHash() {
		return relacionHash;
	}

	public void setRelacionHash(String relacionHash) {
		this.relacionHash = relacionHash;
	}

}
