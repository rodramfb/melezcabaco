/* 
 javabean para la entidad: clientesMovCli
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Dec 13 10:54:32 GMT-03:00 2006 
 
 Para manejar la pagina: clientesMovCliAbm.jsp
 
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
import javax.swing.text.TabExpander;

import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;
import ar.com.syswarp.api.Common;
import ar.com.syswarp.ejb.Tesoreria;

public class BeanLovClientesMovCliAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanLovClientesMovCliAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 10;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List clientesMovCliList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal cliente = new BigDecimal(-1);

	private String razon = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	boolean buscar = false;

	private String[] delKey = null;

	private String[] keyHashDatosComprobante = null;

	private String[] totalcobrar = null;

	private String comprobante = "";

	public BeanLovClientesMovCliAbm() {
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

		Tesoreria clientesMovCli = Common.getTesoreria();
		Hashtable htComprobantes = (Hashtable) session
				.getAttribute("htComprobantes") != null ? (Hashtable) session
				.getAttribute("htComprobantes") : (Hashtable) session
				.getAttribute("htComprobantesOK");

		try {
			String filtro = "WHERE cliente =  "
					+ cliente
					+ "   AND saldo > 0 AND tipomovs <> 'COB' AND (anulada = 'N' OR anulada IS NULL)";
			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			if (buscar) {

				filtro += " AND comprob::VARCHAR  LIKE '%" + ocurrencia + "%'";
				this.totalRegistros = clientesMovCli.getTotalEntidadFiltro(
						"clientesMovCli", filtro, this.idempresa);
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
				this.clientesMovCliList = clientesMovCli
						.getLovClientesMovCliOcu(this.limit, this.offset,
								this.ocurrencia, cliente, this.idempresa);
			} else {
				this.totalRegistros = clientesMovCli.getTotalEntidadFiltro(
						"clientesMovCli", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.clientesMovCliList = clientesMovCli
						.getLovClientesMovCliAll(this.limit, this.offset,
								cliente, this.idempresa);
			}

			if (this.accion.equalsIgnoreCase("eliminar")) {
				if (this.delKey == null) {
					this.mensaje = "Seleccione un registro a eliminar.";
				} else {
					for (int i = 0; i < this.delKey.length; i++) {
						if (htComprobantes.containsKey(this.delKey[i])) {
							htComprobantes.remove(this.delKey[i]);
						}
					}

				}
			}

			if (cliente == null || cliente.longValue() < 0) {
				this.mensaje = "Es Necesario Seleccionar un Cliente.";
			} else {

				if (this.accion.equalsIgnoreCase("agregar")) {

					if (!this.comprobante.equals("")) {

						String[] datosComp = this.comprobante.split("-");
						List lista = clientesMovCli.getLovClientesMovCliOne(
								cliente, new BigDecimal(datosComp[0]),
								new BigDecimal(datosComp[1]), datosComp[2],
								this.idempresa);
						if (lista.size() > 0) {
							String[] datos = (String[]) lista.get(0);

							// NC
							if (datos[7].equals("3")) {
								datos[1] = "-" + datos[1];
							}

							htComprobantes.put(this.comprobante, datos);

						} else {
							this.mensaje = "Imposible recuperar los datos del comprobante seleccionado.";
						}

					} else {
						this.mensaje = "Seleccione un comprobante para aplicar.";
					}

				}

				if (this.accion.equalsIgnoreCase("adelanto")) {
					String[] datos = new String[] { "99999999", "0.00", "0.00",
							"", "", "", "0.00", "-1", this.cliente.toString() };
					htComprobantes.put("ADELANTO", datos);
				}

				if (this.totalRegistros < 1)
					this.mensaje = "No existen registros.";

			}
			log.info("htComprobantes.size: " + htComprobantes.size());
			
			this.asignarValidar(htComprobantes);

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	private boolean asignarValidar(Hashtable ht) {
		boolean fOk = true;
		String mensaje = "";
		log.info("asignar validar?");
		try {
			if (this.keyHashDatosComprobante != null) {
				for (int i = 0; i < this.keyHashDatosComprobante.length; i++) {
					if (ht.containsKey(this.keyHashDatosComprobante[i])) {
						String[] datos = (String[]) ht
								.get(this.keyHashDatosComprobante[i]);

						// for(int z = 0; z<datos.length;z++){
						// log.info("datos["+z+"]: " + datos[z]);
						// }

						// TODO: esta pendiente discriminar entre tipos de
						// documentos NC - ND - FA
						if (!Common.esNumerico(this.totalcobrar[i])) {
							fOk = false;
							mensaje = "Total a cobrar solo admite valores numericos.";
						} else if (datos[7].equals("3")) {

							if (Float.parseFloat(this.totalcobrar[i]) > 0) {
								fOk = false;
								mensaje = "Total a cobrar debe ser un valor negativo, el documento es una NC.";
							} else if (Float.parseFloat(this.totalcobrar[i]) < Float
									.parseFloat("-" + datos[6])) {
								fOk = false;
								mensaje = "Total a cobrar no puede ser mayor al saldo del documento.";
							}

						} else if (Float.parseFloat(this.totalcobrar[i]) > Float
								.parseFloat(datos[6])
								&& !datos[0].equals("99999999")) {
							fOk = false;
							mensaje = "Total a cobrar no puede ser mayor al saldo del documento.";

						} else if (Float.parseFloat(this.totalcobrar[i]) < 0
								&& !datos[0].equals("99999999")) {
							fOk = false;
							mensaje = "Total a cobrar debe ser un valor positivo.";

						} else if (!(new BigDecimal(datos[8]))
								.equals(this.cliente)) {
							// TODO: validar que sea comprobante perteneciente
							// al cliente ... sino eliminarlo.
							fOk = false;
							mensaje = "Comprobante no perteneciente al cliente seleccionado.";

						} else {
							//
						}

						if (fOk) {
							datos[1] = Common.getGeneral().getNumeroFormateado(

							Float.parseFloat(this.totalcobrar[i]), 10, 2);

							// HEAD OF BALDE
							// Si no hay error en la carga de datos, cuando se
							// confirma, y el cliente no tiene comprobantes para
							// aplicar, limpia el mensaje de error del Bean que
							// deberia contener
							// el string "No existen registros.". En la pagina
							// se pregunta si este esta vacio para continuar
							// operando.
							if (this.accion.equalsIgnoreCase("confirmar")) {
								if (totalRegistros == 0)
									this.mensaje = "";
							}

						} else {
							datos[1] = this.totalcobrar[i];
						}

						ht.put(this.keyHashDatosComprobante[i], datos);

					}
				}

			}
			if ((this.accion.equalsIgnoreCase("confirmar") || (ht.isEmpty() && this.accion
					.equalsIgnoreCase("eliminar")))
					&& mensaje.equals("")) {
				session.setAttribute("htComprobantesOK", ht);
				session.removeAttribute("htComprobantes");
			} else {
				log.info("session.setAttribute(htComprobantes, ht)");
				if (!mensaje.equals(""))
					this.mensaje = mensaje;
				// he aqui
				session.setAttribute("htComprobantes", ht);
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

	public List getClientesMovCliList() {
		return clientesMovCliList;
	}

	public void setClientesMovCliList(List clientesMovCliList) {
		this.clientesMovCliList = clientesMovCliList;
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

	public BigDecimal getCliente() {
		return cliente;
	}

	public void setCliente(BigDecimal cliente) {
		this.cliente = cliente;
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

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
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

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public String[] getTotalcobrar() {
		return totalcobrar;
	}

	public void setTotalcobrar(String[] totalcobrar) {
		this.totalcobrar = totalcobrar;
	}

	public String[] getKeyHashDatosComprobante() {
		return keyHashDatosComprobante;
	}

	public void setKeyHashDatosComprobante(String[] keyHashDatosComprobante) {
		this.keyHashDatosComprobante = keyHashDatosComprobante;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
