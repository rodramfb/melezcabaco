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

public class BeanINTERFACESCambioDepositoArea implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanINTERFACESCambioDepositoArea.class);

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

	private BigDecimal idareaorigen = new BigDecimal(-1);

	private String areaorigen = "";

	private BigDecimal idareadestino = new BigDecimal(-1);

	private String areadestino = "";

	/***/

	private BigDecimal origengral = new BigDecimal(-1);

	private BigDecimal destinogral = new BigDecimal(-1);

	private String[] keyHashDatosArticulo = null;

	private String[] origen = null;

	private String[] destino = null;

	private String[] cantidad = null;

	private String[] delKey = null;

	private String tipomov = "";

	private String eliminar = "";

	private boolean primeraCarga = true;

	private String codigo_dt_IN = "";

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

	/***/

	public BeanINTERFACESCambioDepositoArea() {
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
			BC bc = Common.getBc();
			Hashtable htArtMovDepAreaIF = (Hashtable) session
					.getAttribute("htArtMovDepAreaIF");

			if (this.accion.equalsIgnoreCase("confirmar"))
				resultado = bc.InterfaseDbBacoDeltaTransfDepAreas(
						this.idareaorigen, this.idareadestino,
						(Timestamp) Common.setObjectToStrOrTime(this.fechamov,
								"strToJSTs"), this.tipomov, this.num_cnt,
						this.sucursal, this.tipo, this.observaciones,
						htArtMovDepAreaIF, this.usuarioalt, this.idempresa);

			if (resultado[3].equalsIgnoreCase("OK")) {

				htArtMovDepAreaIF = null;
				session.removeAttribute("htArtMovDepAreaIF");
				this.mensaje = "Transferencia exitosa.";

			} else {
				this.mensaje = "No se realizo la transferencia.";
			}

			this.mensaje += "<br>" + Common.setNotNull(resultado[0]) + "<br>"
					+ Common.setNotNull(resultado[1]) + "<br>"
					+ Common.setNotNull(resultado[2]); 

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		boolean valoresOk = false;
		Stock stock = Common.getStock();
		Proveedores proveedoArticulos = Common.getProveedores();
		Hashtable htArtMovDepAreaIF = (Hashtable) session
				.getAttribute("htArtMovDepAreaIF") != null ? (Hashtable) session
				.getAttribute("htArtMovDepAreaIF")
				: new Hashtable();
		try {

			if (isPrimeraCarga()) {
				htArtMovDepAreaIF = new Hashtable();
				session.setAttribute("htArtMovDepAreaIF", htArtMovDepAreaIF);
			}

			listDepositos = Common.getBc().getStockDepostiosTransf(
					codigo_dt_IN, idempresa);

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

					List listaArt = stock.getStockArticulosPK(this.codigo_st,
							this.idempresa);
					Iterator iterArt = listaArt.iterator();
					while (iterArt.hasNext()) {
						String[] datosArt = (String[]) iterArt.next();
						// htArtMovDepAreaIF.put(Common.initObjectTime() +
						// "", datosArt);
						htArtMovDepAreaIF.put(this.codigo_st, datosArt);
					}

					/**/

				}
			}

			if (this.accion.equals("eliminar")) {
				if (this.delKey == null) {
					this.mensaje = "Seleccione un art�culo a eliminar.";
				} else {
					for (int i = 0; i < this.delKey.length; i++) {
						htArtMovDepAreaIF.remove(this.delKey[i]);
					}
				}
			}

			valoresOk = this.asignarValidar(htArtMovDepAreaIF);
			session.setAttribute("htArtMovDepAreaIF", htArtMovDepAreaIF);

			if (this.accion.equalsIgnoreCase("confirmar") && valoresOk) {

				if (this.idareaorigen.intValue() < 1) {
					this.mensaje = "Seleccione area origen.";
					return false;
				}

				if (this.idareadestino.intValue() < 1) {
					this.mensaje = "Seleccione area destino.";
					return false;
				}

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
				if (!isMismorigdest())
					j = i;

				if (ht.containsKey(this.keyHashDatosArticulo[i])) {
					String[] datos = (String[]) ht
							.get(this.keyHashDatosArticulo[i]);
					if (!gb.esNumerico(this.cantidad[i])
							|| Float.parseFloat(this.cantidad[i]) <= 0)
						fOk = false;
					else if (this.origengral.equals("-1"))
						fOk = false;
					else if (this.destinogral.equals("-1"))
						fOk = false;
					else if (this.origengral.equals(this.destinogral))
						fOk = false;

					// datos[0] = codigo_st
					// datos[1] = alias_st
					// datos[2] = descrip_st
					// datos[3] = descri2_st
					datos[4] = this.cantidad[i];
					datos[5] = this.origengral.toString();
					datos[6] = this.destinogral.toString();
					// datos[7] = cost_uc_st

					ht.put(this.keyHashDatosArticulo[i], datos);
				}

			}
			if (!fOk && accion.equalsIgnoreCase("confirmar"))
				this.mensaje = "Verifique origen-destino seleccionados y cantidades ingresadas.";

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

	public BigDecimal getOrigengral() {
		return origengral;
	}

	public void setOrigengral(BigDecimal origengral) {
		this.origengral = origengral;
	}

	public BigDecimal getDestinogral() {
		return destinogral;
	}

	public void setDestinogral(BigDecimal destinogral) {
		this.destinogral = destinogral;
	}

	public BigDecimal getIdareaorigen() {
		return idareaorigen;
	}

	public void setIdareaorigen(BigDecimal idareaorigen) {
		this.idareaorigen = idareaorigen;
	}

	public String getAreaorigen() {
		return areaorigen;
	}

	public void setAreaorigen(String areaorigen) {
		this.areaorigen = areaorigen;
	}

	public BigDecimal getIdareadestino() {
		return idareadestino;
	}

	public void setIdareadestino(BigDecimal idareadestino) {
		this.idareadestino = idareadestino;
	}

	public String getAreadestino() {
		return areadestino;
	}

	public void setAreadestino(String areadestino) {
		this.areadestino = areadestino;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public String getCodigo_dt_IN() {
		return codigo_dt_IN;
	}

	public void setCodigo_dt_IN(String codigo_dt_IN) {
		this.codigo_dt_IN = codigo_dt_IN;
	}

}
