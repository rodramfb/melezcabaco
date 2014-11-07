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
import javax.mail.Flags.Flag;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanStockSerieDespachoEntrada implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanStockSerieDespachoEntrada.class);

	private GeneralBean gb = new GeneralBean();

	private BigDecimal idempresa = new BigDecimal(-1);

	private SessionContext context;

	private int limit = 10;

	private int offset = 0;

	private int totalRegistros = 0;

	private int totalPaginas = 0;

	private int paginaSeleccion = 1;

	private String accion = "";

	private String codigo_st = "";

	private String descrip_st = "";

	private String relacionHash = "";

	private String id_indi_st = "";

	private String despa_st = "";

	private String nroserie = "";

	private String nrodespacho = "";

	private String cantdespacho = "0";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	boolean buscar = false;

	/***/

	private String serieDespacho[][] = null;

	private String[][] auxSerieDespacho = null;

	private String[] keyHashDatosArticulo = null;

	private String[] cantidad = null;

	private String[] delKey = null;

	private String eliminar = "";

	private String idcantidad = "";

	private long cantidadIngresada = 0l;

	/***/

	public BeanStockSerieDespachoEntrada() {
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
		// RequestDispatcher dispatcher = null;
		// Proveedores proveed = Common.getProveedores();
		int lenSerieDespacho = 0;
		boolean existeArtEnHash = false;
		boolean flagValidez = true;

		Hashtable htArticulosSerieDespachoOK = (Hashtable) session
				.getAttribute("htArticulosSerieDespachoOK") != null ? (Hashtable) session
				.getAttribute("htArticulosSerieDespachoOK")
				: new Hashtable();

		Hashtable htArticulosSerieDespacho = (Hashtable) session
				.getAttribute("htArticulosSerieDespacho") != null
				&& !((Hashtable) session
						.getAttribute("htArticulosSerieDespacho")).isEmpty() ? (Hashtable) session
				.getAttribute("htArticulosSerieDespacho")
				: htArticulosSerieDespachoOK;

		// log.info("htArticulosSerieDespachoOK:" + htArticulosSerieDespachoOK
		// );

		try {

			existeArtEnHash = (!htArticulosSerieDespacho.isEmpty() && htArticulosSerieDespacho
					.containsKey(this.relacionHash));

			if (this.accion.equalsIgnoreCase("agregar")) {

				// log.info("this.relacionHash: " + this.relacionHash);

				if (Common.setNotNull(this.relacionHash).equals("")) {

					this.mensaje = "Seleccione un articulo.";

				} else {

					// log.info("htArticulosSerieDespacho: "
					// + htArticulosSerieDespacho);
					// log.info("htArticulosSerieDespacho.isEmpty(): "
					// + htArticulosSerieDespacho.isEmpty());
					// log
					// .info("htArticulosSerieDespacho.containsKey(this.relacionHash): "
					// + htArticulosSerieDespacho
					// .containsKey(this.relacionHash));

					log.info("this.id_indi_st: "+this.id_indi_st);
					if (this.id_indi_st.equalsIgnoreCase("S"))
						this.cantdespacho = "1";

					if (!existeArtEnHash) {
							
						 log.info("A: Inicio");

						if ((flagValidez = validarAsignacion(0))) {

							this.serieDespacho = new String[1][3];
							this.serieDespacho[0][0] = Common.setNotNull(
									this.nroserie).toUpperCase();
							this.serieDespacho[0][1] = Common.setNotNull(
									this.nrodespacho).toUpperCase();
							this.serieDespacho[0][2] = Common.setNotNull(
									this.cantdespacho).toUpperCase();

						}

						// log.info("A: Final ");

					} else {

						log.info("B: Inicio");

						this.auxSerieDespacho = (String[][]) htArticulosSerieDespacho
								.get(this.relacionHash);
						this.serieDespacho = this.auxSerieDespacho;
						lenSerieDespacho = this.serieDespacho.length;

						this.serieDespacho = new String[lenSerieDespacho + 1][3];

						// for (int j = 0; j < lenSerieDespacho; j++) {
						// this.serieDespacho[j][0] = auxSerieDespacho[j][0];
						// this.serieDespacho[j][1] = auxSerieDespacho[j][1];
						// }

						for (int j = lenSerieDespacho - 1; j >= 0; j--) {
							this.serieDespacho[j + 1][0] = this.auxSerieDespacho[j][0];
							this.serieDespacho[j + 1][1] = this.auxSerieDespacho[j][1];
							this.serieDespacho[j + 1][2] = this.auxSerieDespacho[j][2];

							if (!(flagValidez = validarAsignacion(j)))
								break;

						}

						 log.info("flagValidez: " + flagValidez);

						if (flagValidez) {

							this.serieDespacho[0][0] = Common.setNotNull(
									this.nroserie).toUpperCase();
							this.serieDespacho[0][1] = Common.setNotNull(
									this.nrodespacho).toUpperCase();
							this.serieDespacho[0][2] = Common.setNotNull(
									this.cantdespacho).toUpperCase();

						} else {

							this.serieDespacho = this.auxSerieDespacho;

						}

						// log.info("B: final");

					}

					if (flagValidez) {
						this.cantidadIngresada = getTotalIngresado(this.serieDespacho);
						htArticulosSerieDespacho.put(this.relacionHash,
								this.serieDespacho);
						existeArtEnHash = true;
					}
				}

			}

			if (this.accion.equals("eliminar")) {

				if (existeArtEnHash) {

					if (this.delKey == null) {
						this.mensaje = "Seleccione al menos un registro a eliminar.";
					} else {

						Hashtable htAux = new Hashtable();
						String[][] auxSerieDespacho = (String[][]) htArticulosSerieDespacho
								.get(this.relacionHash);
						int lenAux = auxSerieDespacho.length;
						int lenDeleted = this.delKey.length;

						// ....................................................

						this.serieDespacho = new String[lenAux - lenDeleted][3];

						for (int i = 0; i < this.delKey.length; i++) {
							htAux.put(this.delKey[i], this.delKey[i]);
						}

						for (int i = 0, j = 0; i < auxSerieDespacho.length; i++) {
							if (htAux.contains(i + ""))
								continue;
							this.serieDespacho[j++] = auxSerieDespacho[i];
						}

						if (this.serieDespacho != null
								&& this.serieDespacho.length != 0) {

							htArticulosSerieDespacho.put(this.relacionHash,
									this.serieDespacho);

							this.cantidadIngresada = getTotalIngresado(this.serieDespacho);

						} else {
							htArticulosSerieDespacho.remove(this.relacionHash);
							existeArtEnHash = false;
						}

						this.mensaje = "Se eliminaron " + lenDeleted
								+ " registros.";

					}

				}

			}

			// log.info("Valida: 0");

			if (existeArtEnHash) {
				this.totalRegistros = ((String[][]) htArticulosSerieDespacho
						.get(this.relacionHash)).length;
			}

			// log.info("Valida: 1");

			this.totalPaginas = (this.totalRegistros / this.limit) + 1;
			if (this.totalPaginas < this.paginaSeleccion)
				this.paginaSeleccion = this.totalPaginas;
			this.offset = (this.paginaSeleccion - 1) * this.limit;
			if (this.totalRegistros == this.limit) {
				this.offset = 0;
				this.totalPaginas = 1;
			}

			// log.info("Valida: 2");

			// log.info("totalRegistros: " + totalRegistros);
			// log.info("totalPaginas: " + totalPaginas);
			// log.info("paginaSeleccion: " + paginaSeleccion);
			// log.info("offset: " + offset);
			// log.info("limit: " + limit);

			session.setAttribute("htArticulosSerieDespacho",
					htArticulosSerieDespacho);

			if (this.accion.equalsIgnoreCase("confirmar")) {

				if (existeArtEnHash) {

					String[][] matrizSerieDespacho = (String[][]) htArticulosSerieDespacho
							.get(this.relacionHash);

					this.cantidadIngresada = getTotalIngresado(matrizSerieDespacho);

					if (this.cantidadIngresada > 0) {

						session.setAttribute("htArticulosSerieDespachoOK",
								htArticulosSerieDespacho);
						session.removeAttribute("htArticulosSerieDespacho");

					} else {

						this.mensaje = "La cantidad total ingresada es incorrecta.";

					}

				} else {

					this.mensaje = "Imposible confirmar, no existen valores cargados.";

				}

			}

			// log.info("Valida: 3");

			if (this.totalRegistros < 1 && this.mensaje.equals(""))
				this.mensaje = "Aun no se ingresaron articulos serializados y/o con despacho.";

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	private boolean validarAsignacion(int index) {

		// log.info("this.id_indi_st: " + this.id_indi_st);
		// log.info("this.nroserie  : " + this.nroserie);
		// log.info("this.despa_st  : " + this.despa_st);
		// log.info("this.cantdespacho  : " + this.cantdespacho);
		log.info("validarAsignacion");
		if (this.id_indi_st.equalsIgnoreCase("S")) {

			if (Common.setNotNull(this.nroserie).equalsIgnoreCase("")) {
				this.mensaje = "Es necesario ingresar nro de serie";
				return false;
			}
			log.info("auxSerieDespacho: "+this.auxSerieDespacho);
			if (this.auxSerieDespacho != null
					&& Common.setNotNull(this.auxSerieDespacho[index][0])
							.equalsIgnoreCase(Common.setNotNull(this.nroserie))) {
				this.mensaje = "El nro. de serie ya se encuentra ingresado.";
				return false;
			}

		} else if (this.despa_st.equalsIgnoreCase("S")) {

			if (Common.setNotNull(this.nrodespacho).equals("")) {

				this.mensaje = "Ingrese despacho valido.";
				return false;

			}

			if (this.auxSerieDespacho != null
					&& Common.setNotNull(this.nrodespacho).equalsIgnoreCase(
							Common.setNotNull(this.auxSerieDespacho[index][1]))) {

				this.mensaje = "El despacho ya fue ingresado anteriormente.";
				return false;

			}

			if (!Common.esEntero(this.cantdespacho)
					|| Long.parseLong(this.cantdespacho) < 1) {

				this.mensaje = "Ingrese valores numericos validos, para cantidad de despacho.";
				return false;

			}

		}

		return true;
	}

	private long getTotalIngresado(String[][] matriz) {

		long cantidad = 0;

		// log.info("----------");
		for (int i = 0; matriz != null && i < matriz.length; i++) {

			// log.info("matriz[" + i + "][2]" + matriz[i][2]);
			cantidad += Long.parseLong(matriz[i][2]);

		}

		return cantidad;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public int getPaginaSeleccion() {
		return paginaSeleccion;
	}

	public void setPaginaSeleccion(int paginaSeleccion) {
		this.paginaSeleccion = paginaSeleccion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public String getRelacionHash() {
		return relacionHash;
	}

	public void setRelacionHash(String relacionHash) {
		this.relacionHash = relacionHash;
	}

	public String getId_indi_st() {
		return id_indi_st;
	}

	public void setId_indi_st(String id_indi_st) {
		this.id_indi_st = id_indi_st;
	}

	public String getDespa_st() {
		return despa_st;
	}

	public void setDespa_st(String despa_st) {
		this.despa_st = despa_st;
	}

	public String getNroserie() {
		return nroserie;
	}

	public void setNroserie(String nroserie) {
		this.nroserie = nroserie;
	}

	public String getNrodespacho() {
		return nrodespacho;
	}

	public void setNrodespacho(String nrodespacho) {
		this.nrodespacho = nrodespacho;
	}

	public String getCantdespacho() {
		return cantdespacho;
	}

	public void setCantdespacho(String cantdespacho) {
		this.cantdespacho = cantdespacho;
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

	public String getIdcantidad() {
		return idcantidad;
	}

	public void setIdcantidad(String idcantidad) {
		this.idcantidad = idcantidad;
	}

	public long getCantidadIngresada() {
		return cantidadIngresada;
	}

	public void setCantidadIngresada(long cantidadIngresada) {
		this.cantidadIngresada = cantidadIngresada;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
