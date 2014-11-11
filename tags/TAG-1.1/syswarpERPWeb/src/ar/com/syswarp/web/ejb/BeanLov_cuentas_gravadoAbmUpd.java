/* 
 javabean para la entidad: lov_cuentas_exento
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Aug 17 11:09:28 GMT-03:00 2006 
 
 Para manejar la pagina: lov_cuentas_gravadoAbm.jsp
 
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

public class BeanLov_cuentas_gravadoAbmUpd implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanLov_cuentas_gravadoAbmUpd.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 10;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List lov_cuentas_gravadoList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idcuenta = BigDecimal.valueOf(-1);

	private String mensaje = "";
	
	private int idejercicio;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	private int ejercicioActivo = 0;

	boolean buscar = false;

	private double TNGravado = 0;

	private double auxTNGravado = 0;

	private String flagCuentas = "";

	/***/

	private Hashtable htNetoGravadoConfirma = new Hashtable();

	private String[] keyHashDatosCuenta = null;

	private String[] totalAsiento = null;

	private String[] delKey = null;

	private String eliminar = "";

	private String cuentaAsociar = "";

	private String readonlyTA = "";

	/***/

	public BeanLov_cuentas_gravadoAbmUpd() {
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

	public boolean asignarValidar(Hashtable ht) {
		boolean fOk = true;
		if (this.keyHashDatosCuenta != null) {
			for (int i = 0; i < this.keyHashDatosCuenta.length; i++) {
				if (ht.containsKey(this.keyHashDatosCuenta[i])) {
					String[] datos = (String[]) ht
							.get(this.keyHashDatosCuenta[i]);
					datos[2] = this.totalAsiento[i];
					if (Common.esNumerico(datos[2])) {
						if (Double.parseDouble(datos[2]) >= 0) {
							auxTNGravado += Double.parseDouble(datos[2]);
						} else
							fOk = false;

					} else
						fOk = false;
				}
			}
			if (!fOk)
				this.mensaje = "Verifique totales ingresados.";

			auxTNGravado = Double.parseDouble(Common.getNumeroFormateado(
					auxTNGravado, 10, 2));
		}
		return fOk;
	}

	public boolean ejecutarValidacion() {

		Proveedores lov_cuentas_gravado = Common.getProveedores();
		try {
			if (this.session.getAttribute("htArticulosConfirmados") != null)
				this.readonlyTA = "readonly";
			if (((Hashtable) session.getAttribute("htNetoGravadoConfirmaUpd")) != null
					&& !((Hashtable) session
							.getAttribute("htNetoGravadoConfirmaUpd")).isEmpty()) {

				this.htNetoGravadoConfirma = (Hashtable) session
						.getAttribute("htNetoGravadoConfirmaUpd");

			} else {
				// Flag indica que es la primera carga de la pagina o no, si es
				// la primera setea el valor del hash con las cuentas asociadas
				// al proveedor para el neto gravado.
				if (this.flagCuentas.equals("")) {
					Hashtable htAux = ((Hashtable) session
							.getAttribute("htNetoGravadoUpd"));
					Enumeration e = htAux.keys();
					while (e.hasMoreElements()) {
						Object key = (Object) e.nextElement();
						this.htNetoGravadoConfirma.put(key, htAux.get(key));
					}
				}
			}

			if (this.accion.equalsIgnoreCase("asociar")) {

				if (this.idcuenta == null || this.idcuenta.longValue() < 0) {
					this.mensaje = "Seleccione una cuenta.";
				} else {
					try {

						List listaArt = lov_cuentas_gravado
								.getCuentasInfiPlanPK(this.idcuenta,
										this.idempresa, this.idejercicio);
						Iterator iterArt = listaArt.iterator();
						if (iterArt.hasNext()) {

							String[] datos = (String[]) iterArt.next();

							String[] datosCuenta = (String[]) this.htNetoGravadoConfirma
									.get(this.cuentaAsociar);
							datosCuenta[0] = datos[0];
							datosCuenta[1] = datos[1];

							this.htNetoGravadoConfirma.put(this.cuentaAsociar,
									datosCuenta);

						} else {
							this.mensaje = "Imposible recuperara datos asociados a la cuenta seleccionada: "
									+ this.idcuenta + ".";
						}

					} catch (Exception e) {
						log.error("Error asociando: " + e);

					}

				}
			}

			if (this.accion.equals("eliminar")) {
				if (this.delKey == null) {
					this.mensaje = "Seleccione un art�culo a eliminar.";
				} else {
					for (int i = 0; i < this.delKey.length; i++) {
						this.htNetoGravadoConfirma.remove(this.delKey[i]);
					}
				}
			}

			if (this.accion.equalsIgnoreCase("agregar")) {

				if (this.idcuenta == null || this.idcuenta.longValue() < 0) {
					this.mensaje = "Seleccione una cuenta.";

				} else {

					List listaArt = lov_cuentas_gravado.getCuentasInfiPlanPK(
							this.idcuenta, this.idempresa, this.idejercicio);
					Iterator iterArt = listaArt.iterator();
					while (iterArt.hasNext()) {
						String[] datos = (String[]) iterArt.next();
						String[] datosCuenta = new String[] { datos[0],
								datos[1], "0.00", "G", "noasignable" };
						htNetoGravadoConfirma.put("A" + Common.initObjectTime()
								+ "", datosCuenta);
					}
					/**/
				}
			}

			session.setAttribute("htNetoGravadoConfirmaUpd",
					this.htNetoGravadoConfirma);

			if (this.accion.equalsIgnoreCase("confirmar")) {

				if (this.asignarValidar(this.htNetoGravadoConfirma)) {

					if (this.auxTNGravado == this.TNGravado) {

						Hashtable htAux = new Hashtable();
						Enumeration e = this.htNetoGravadoConfirma.keys();
						while (e.hasMoreElements()) {
							Object key = (Object) e.nextElement();
							htAux.put(key, this.htNetoGravadoConfirma.get(key));
						}
						session.removeAttribute("htNetoGravadoUpd");
						session.removeAttribute("htNetoGravadoConfirmaUpd");
						session.setAttribute("htNetoGravadoUpd", htAux);
					} else
						this.mensaje = "Total de neto gravado incorrecto.";

				}

			}

			this.ejercicioActivo = Integer.parseInt(session.getAttribute(
					"ejercicioActivo").toString());

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			
			String filtro = " WHERE ejercicio = " + this.ejercicioActivo;
			if (buscar) {
				
				filtro += " AND (idcuenta::VARCHAR LIKE '" + this.ocurrencia
				+ "' OR UPPER(cuenta) LIKE '"
				+ this.ocurrencia.toUpperCase() + "')";
				
				this.totalRegistros = lov_cuentas_gravado.getTotalEntidadFiltro(
						"contableinfiplan", filtro, this.idempresa);
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
				this.lov_cuentas_gravadoList = lov_cuentas_gravado
						.getCuentasInfiPlanOcu(this.limit, this.offset,
								this.ocurrencia, this.ejercicioActivo,
								this.idempresa);
			} else {
				this.totalRegistros = lov_cuentas_gravado.getTotalEntidadFiltro(
						"contableinfiplan", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.lov_cuentas_gravadoList = lov_cuentas_gravado
						.getCuentasInfiPlanAll(this.limit, this.offset,
								this.ejercicioActivo, this.idempresa);
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
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

	public List getLov_cuentas_gravadoList() {
		return lov_cuentas_gravadoList;
	}

	public void setLov_cuentas_gravadoList(List lov_cuentas_gravadoList) {
		this.lov_cuentas_gravadoList = lov_cuentas_gravadoList;
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

	public BigDecimal getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(BigDecimal idcuenta) {
		this.idcuenta = idcuenta;
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

	public double getTNGravado() {
		return TNGravado;
	}

	public void setTNGravado(double gravado) {
		TNGravado = gravado;
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

	public String[] getKeyHashDatosCuenta() {
		return keyHashDatosCuenta;
	}

	public void setKeyHashDatosCuenta(String[] keyHashDatosCuenta) {
		this.keyHashDatosCuenta = keyHashDatosCuenta;
	}

	public Hashtable getHtNetoGravadoConfirma() {
		return htNetoGravadoConfirma;
	}

	public void setHtNetoGravadoConfirma(Hashtable htNetoGravadoConfirma) {
		this.htNetoGravadoConfirma = htNetoGravadoConfirma;
	}

	public String getFlagCuentas() {
		return flagCuentas;
	}

	public void setFlagCuentas(String flagCuentas) {
		this.flagCuentas = flagCuentas;
	}

	public String[] getTotalAsiento() {
		return totalAsiento;
	}

	public void setTotalAsiento(String[] totalAsiento) {
		this.totalAsiento = totalAsiento;
	}

	public String getCuentaAsociar() {
		return cuentaAsociar;
	}

	public void setCuentaAsociar(String cuentaAsociar) {
		this.cuentaAsociar = cuentaAsociar;
	}

	public String getReadonlyTA() {
		return readonlyTA;
	}

	public void setReadonlyTA(String readonlyTA) {
		this.readonlyTA = readonlyTA;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public int getIdejercicio() {
		return idejercicio;
	}

	public void setIdejercicio(int idejercicio) {
		this.idejercicio = idejercicio;
	}
}