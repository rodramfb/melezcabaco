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

public class BeanLovArticulosListaPreciosPedidosRegalos implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanLovArticulosListaPreciosPedidosRegalos.class);

	private BigDecimal idempresa = new BigDecimal(-1);

	private SessionContext context;

	private boolean primeraCarga = true;

	private int limit = 5;

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

	private String[] existencia = null;

	private String[] reserva = null;

	private String eliminar = "";

	private BigDecimal total = new BigDecimal(0);

	private BigDecimal idlista = new BigDecimal(-1);

	private BigDecimal idzona = new BigDecimal(-1);

	private BigDecimal codigo_dt = new BigDecimal(-1);

	private String conExistencia = "";

	private String soloCampania = "";

	private BigDecimal idcampacabe = new BigDecimal(-1);

	private long totalOcurrenciaEnEsquema = 0l;

	/***/

	public BeanLovArticulosListaPreciosPedidosRegalos() {
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

			long totalLockRegalos = clientes.getTotaLockRegalos(idempresa);
			boolean existeLock = false;

			if (totalLockRegalos < 0) {

				this.mensaje = "No fue posible determinar si existen depositos bloqueados para regalos en la fecha actual.";

			} else {

				if (totalLockRegalos > 0)
					existeLock = true;

				this.conExistencia = Common.setNotNull(this.conExistencia);
				this.conExistencia = this.isPrimeraCarga() ? "S"
						: this.conExistencia;

				String entidad = ""
						+ "(SELECT st.codigo_st,  st.descrip_st, lp.idlista, COALESCE(sb.canti_sb::numeric(18), 0) AS existencia, de.codigo_dt, st.idempresa  "
						+ "  FROM stockstock st  "
						+ "       INNER JOIN clienteslistasdeprecios lp ON st.codigo_st = lp.codigo_st  AND st.idempresa = lp.idempresa  "
						+ "       INNER JOIN stockdepositos de ON de.idempresa =   "
						+ this.idempresa
						+ "       INNER JOIN clienteszonasdepositos dz ON de.codigo_dt = dz.codigo_dt AND  de.idempresa = dz.idempresa  "
						+ "              AND dz.idzona = "
						+ this.idzona
						// -->
						+ (soloCampania.equalsIgnoreCase("S") ? "       INNER JOIN bacotmcampadeta cd ON st.codigo_st = cd.codigo_st AND st.idempresa = cd.idempresa AND cd.idcampacabe = "
								+ idcampacabe.toString()
								: "")
						// <--
						+ "       LEFT JOIN stockstockbis sb ON st.codigo_st = sb.articu_sb AND de.codigo_dt = sb.deposi_sb  "
						+ "             AND st.idempresa = sb.idempresa) entidad ";

				String filtro = " WHERE idlista = "
						+ idlista.toString()
						+ (this.conExistencia.equals("S") ? " AND  existencia IS NOT NULL AND existencia > 0 "
								: "")
						+ (existeLock ? " AND codigo_dt  IN (SELECT codigo_dt FROM stockdepositoslockregalos WHERE current_date BETWEEN fechadesde AND fechahasta  AND fechabaja IS NULL  AND idempresa = "
								+ idempresa + ")  "
								: "");

				Hashtable htArticulosInOutOk = (Hashtable) session
						.getAttribute("htArticulosInOutOK") != null ? (Hashtable) session
						.getAttribute("htArticulosInOutOK")
						: new Hashtable();

				Hashtable htArticulosInOut = (Hashtable) session
						.getAttribute("htArticulosInOut") != null
						&& !((Hashtable) session
								.getAttribute("htArticulosInOut")).isEmpty() ? (Hashtable) session
						.getAttribute("htArticulosInOut")
						: htArticulosInOutOk;

				if (isPrimeraCarga() && htArticulosInOutOk.isEmpty()) {
					htArticulosInOut = new Hashtable();
				}

				listDepositos = clientes.getStockDepositosXZona(this.idzona,
						this.idempresa);

				if (listDepositos == null || listDepositos.isEmpty()) {
					this.mensaje = "No existen depositos asociados a la zona correspondiente.";
				}

				if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
					if (ocurrencia.indexOf("'") >= 0) {
						this.mensaje = "Caracteres invalidos en campo busqueda.";
					} else {
						buscar = true;
					}
				}
				if (buscar && accion.equals("")) {

					String entidadEsquemas = ""
							+ "(SELECT ec.idesquema, ec.esquema, ec.observaciones, ed.codigo_st, st.descrip_st, ed.cantidad::numeric(18) AS cantidad, ec.idempresa "
							+ "  FROM produccionesquemas_cabe ec  "
							+ "       INNER JOIN  produccionesquemas_deta ed ON ec.idesquema = ed.idesquema AND ec.idempresa = ed.idempresa "
							+ "       INNER JOIN stockstock st ON ed.codigo_st = st.codigo_st AND ed.idempresa = st.idempresa "
							+ " WHERE ec.idempresa =   "
							+ this.idempresa.toString()
							+ "   AND ed.entsal = 'C'  "
							+ "   AND ed.reutiliza = 'S') esquemas ";

					String filtroAuxiliar = "(UPPER(codigo_st) LIKE '%"
							+ ocurrencia.toUpperCase()
							+ "%'  OR UPPER(descrip_st) LIKE '%"
							+ ocurrencia.toUpperCase().trim() + "%')";

					filtro += " AND " + filtroAuxiliar;

					this.totalOcurrenciaEnEsquema = clientes
							.getTotalEntidadFiltro(entidadEsquemas, " WHERE "
									+ filtroAuxiliar, this.idempresa);
					this.totalRegistros = clientes.getTotalEntidadFiltro(
							entidad, filtro, this.idempresa);
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
					this.proveedoArticulosList = clientes
							.getArtListaPreciosPedidosRegalosOcu(this.limit,
									this.offset, this.ocurrencia, this.idlista,
									this.idzona, this.conExistencia,
									this.soloCampania, this.idcampacabe,
									existeLock, this.idempresa);
				} else {
					this.totalRegistros = clientes.getTotalEntidadFiltro(
							entidad, filtro, this.idempresa);
					this.totalPaginas = (this.totalRegistros / this.limit) + 1;
					if (this.totalPaginas < this.paginaSeleccion)
						this.paginaSeleccion = this.totalPaginas;
					this.offset = (this.paginaSeleccion - 1) * this.limit;
					if (this.totalRegistros == this.limit) {
						this.offset = 0;
						this.totalPaginas = 1;
					}
					this.proveedoArticulosList = clientes
							.getArtListaPreciosPedidosRegalosAll(this.limit,
									this.offset, this.idlista, this.idzona,
									this.conExistencia, this.soloCampania,
									this.idcampacabe, existeLock,
									this.idempresa);
				}
				if (this.totalRegistros < 1)
					this.mensaje = "No existen registros.";

				if (this.accion.equalsIgnoreCase("agregar")) {

					if (this.codigo_st == null || this.codigo_st.equals("")) {
						this.mensaje = "Seleccione un articulo.";

					} else {

						List listaArt = clientes.getArtListaPreciosPedidosPK(
								this.codigo_st, this.idlista, this.codigo_dt,
								this.idempresa);
						Iterator iterArt = listaArt.iterator();
						while (iterArt.hasNext()) {
							String[] datosArt = (String[]) iterArt.next();

							htArticulosInOut.put(this.codigo_st + "-"
									+ this.codigo_dt, datosArt);
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
					session
							.setAttribute("htArticulosInOutOK",
									htArticulosInOut);
					session.removeAttribute("htArticulosInOut");
				}

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

					datos[5] = Common.getNumeroFormateado(Float
							.parseFloat(datos[5]), 10, 2)
							+ "";
					datos[6] = datos[5];

					if (Common.esNumerico(this.cantidad[i])
							&& Float.parseFloat(this.cantidad[i]) > 0) {

						float porcdesc = Float.parseFloat(datos[4]);
						float factordesc = porcdesc / 100;
						float precio = Float.parseFloat(datos[5]);

						datos[11] = (Common.getNumeroFormateado(
								(precio - (precio * factordesc))
										* Float.parseFloat(this.cantidad[i]),
								100, 2))
								+ "";

						total = total.add(new BigDecimal(datos[11]));

					} else
						fOk = false;

					// datos[7] = this.existencia[i];
					// datos[8] = this.reserva[i];
					// datos[9] = this.deposito[i];
					datos[10] = this.cantidad[i];

					if (htDepArt.containsKey(datos[0] + datos[9])) {
						mensajeAV = "Imposible seleccionar mismo articulo: "
								+ datos[0] + " - mismo destino: " + datos[9];
						fOk = false;
					}

					// Verificar seleccion de depostito

					// if (this.deposito[i].equals("-1")) {
					// fOk = false;
					// } else {
					// htDepArt.put(datos[0] + datos[9], "");
					// }
					//					

					ht.put(this.keyHashDatosArticulo[i], datos);

				}

			}
			if (!fOk && mensajeAV.equals(""))
				this.mensaje = "Verifique depósitos seleccionados y cantidades ingresadas.";
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BigDecimal getIdlista() {
		return idlista;
	}

	public void setIdlista(BigDecimal idlista) {
		this.idlista = idlista;
	}

	public String[] getExistencia() {
		return existencia;
	}

	public void setExistencia(String[] existencia) {
		this.existencia = existencia;
	}

	public String[] getReserva() {
		return reserva;
	}

	public void setReserva(String[] reserva) {
		this.reserva = reserva;
	}

	public BigDecimal getIdzona() {
		return idzona;
	}

	public void setIdzona(BigDecimal idzona) {
		this.idzona = idzona;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getConExistencia() {
		return conExistencia;
	}

	public void setConExistencia(String conExistencia) {
		this.conExistencia = conExistencia;
	}

	public String getSoloCampania() {
		return soloCampania;
	}

	public void setSoloCampania(String soloCampania) {
		this.soloCampania = soloCampania;
	}

	public BigDecimal getIdcampacabe() {
		return idcampacabe;
	}

	public void setIdcampacabe(BigDecimal idcampacabe) {
		this.idcampacabe = idcampacabe;
	}

	public long getTotalOcurrenciaEnEsquema() {
		return totalOcurrenciaEnEsquema;
	}

	public void setTotalOcurrenciaEnEsquema(long totalOcurrenciaEnEsquema) {
		this.totalOcurrenciaEnEsquema = totalOcurrenciaEnEsquema;
	}

}
