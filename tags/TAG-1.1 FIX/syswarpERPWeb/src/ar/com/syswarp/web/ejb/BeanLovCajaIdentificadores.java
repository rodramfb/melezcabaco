/* 
 javabean para la entidad: cajaIdentificadores
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Dec 18 15:14:22 GMT-03:00 2006 
 
 Para manejar la pagina: cajaIdentificadoresAbm.jsp
 
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

public class BeanLovCajaIdentificadores implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanLovCajaIdentificadores.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List cajaIdentificadoresList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal ididentificador;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	boolean buscar = false;

	private String tipomov = "";

	private String propio = "";

	private String[] delKey = null;

	private String[] keyHashDatosIdentificador = null;

	private String[] importedescuento = null;

	private String identificador = "";

	public BeanLovCajaIdentificadores() {
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

		Tesoreria cajaIdentificadores = Common.getTesoreria();
		Hashtable htIdentificadores = (Hashtable) session
				.getAttribute("htIdentificadores") != null ? (Hashtable) session
				.getAttribute("htIdentificadores")
				: (Hashtable) session.getAttribute("htIdentificadoresOK");
		try {

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			if (buscar) {
				String[] campos = { "ididentificador","tipoidentificador","identificador","descripcion"};
				this.totalRegistros = cajaIdentificadores
						.getTotalEntidadRelacionOcu("videntificadorestipo",
								new String[] { "tipomov", "propio" },
								new String[] { "'" + this.tipomov + "'",
										"'" + this.propio + "'" }, campos,
								new String[] { this.ocurrencia, this.ocurrencia, this.ocurrencia, this.ocurrencia },
								this.idempresa);
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
				this.cajaIdentificadoresList = cajaIdentificadores
						.getLovCajaIdentificadoresOcu(this.limit, this.offset,
								this.ocurrencia, this.tipomov, this.propio,
								this.idempresa);
			} else {

				String filtro = " WHERE tipomov = '" + this.tipomov
						+ "' AND propio = '" + this.propio + "'";
				this.totalRegistros = cajaIdentificadores
						.getTotalEntidadFiltro("videntificadorestipo", filtro,
								this.idempresa);

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.cajaIdentificadoresList = cajaIdentificadores
						.getLovCajaIdentificadoresAll(this.limit, this.offset,
								this.tipomov, this.propio, this.idempresa);
			}

			if (this.accion.equalsIgnoreCase("agregar")) {

				if (!this.identificador.equals("")) {

					List lista = cajaIdentificadores
							.getLovCajaIdentificadoresOne(this.identificador,
									this.idempresa);
					if (lista.size() > 0) {
						String[] datos = (String[]) lista.get(0);
						htIdentificadores.put(this.identificador, datos);

					} else {
						this.mensaje = "Imposible recuperar los datos del identificador seleccionado.";
					}

				} else {
					this.mensaje = "Seleccione un identificador para aplicar.";
				}

				// session.setAttribute("htIdentificadores", htIdentificadores);
			}

			if (this.accion.equalsIgnoreCase("eliminar")) {
				if (this.delKey == null) {
					this.mensaje = "Seleccione un registro a eliminar.";
				} else {
					for (int i = 0; i < this.delKey.length; i++) {
						if (htIdentificadores.containsKey(this.delKey[i])) {
							htIdentificadores.remove(this.delKey[i]);
						}
					}
				}
			}

			this.asignarValidar(htIdentificadores);

			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	private boolean asignarValidar(Hashtable ht) {
		boolean fOk = true;
		String mensaje = "";
		try {
			if (this.keyHashDatosIdentificador != null) {
				for (int i = 0; i < this.keyHashDatosIdentificador.length; i++) {
					if (ht.containsKey(this.keyHashDatosIdentificador[i])) {
						String[] datos = (String[]) ht
								.get(this.keyHashDatosIdentificador[i]);

						// TODO: esta pendiente discriminar entre tipos de
						// documentos NC - ND - FA
						if (!Common.esNumerico(this.importedescuento[i])) {
							fOk = false;
							mensaje = "Importe a descontar solo admite valores numericos.";

						} else {
							//
						}

						if (fOk) {
							datos[8] = Common.getGeneral().getNumeroFormateado(

							Float.parseFloat(this.importedescuento[i]), 10, 2);
						} else {
							datos[8] = this.importedescuento[i];
						}

						ht.put(this.keyHashDatosIdentificador[i], datos);

					}
				}

			}
			if ((this.accion.equalsIgnoreCase("confirmar") || (ht.isEmpty() && this.accion
					.equalsIgnoreCase("eliminar")))
					&& mensaje.equals("")) {

				session.setAttribute("htIdentificadoresOK", ht);
				session.removeAttribute("htIdentificadores");

			} else {
				if (!mensaje.equals(""))
					this.mensaje = mensaje;
				session.setAttribute("htIdentificadores", ht);
			}

		} catch (Exception e) {
			log.error("asignarValidar: " + e);
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

	public List getCajaIdentificadoresList() {
		return cajaIdentificadoresList;
	}

	public void setCajaIdentificadoresList(List cajaIdentificadoresList) {
		this.cajaIdentificadoresList = cajaIdentificadoresList;
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

	public BigDecimal getIdidentificador() {
		return ididentificador;
	}

	public void setIdidentificador(BigDecimal ididentificador) {
		this.ididentificador = ididentificador;
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

	public String getPropio() {
		return propio;
	}

	public void setPropio(String propio) {
		this.propio = propio;
	}

	public String getTipomov() {
		return tipomov;
	}

	public void setTipomov(String tipommov) {
		this.tipomov = tipommov;
	}

	public String[] getDelKey() {
		return delKey;
	}

	public void setDelKey(String[] delKey) {
		this.delKey = delKey;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String[] getKeyHashDatosIdentificador() {
		return keyHashDatosIdentificador;
	}

	public void setKeyHashDatosIdentificador(String[] keyHashDatosIdentificador) {
		this.keyHashDatosIdentificador = keyHashDatosIdentificador;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String[] getImportedescuento() {
		return importedescuento;
	}

	public void setImportedescuento(String[] totalcobrar) {
		this.importedescuento = totalcobrar;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
