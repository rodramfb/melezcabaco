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

public class BeanProveedoCtasContablesAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanProveedoCtasContablesAbm.class);

	// private GeneralBean gb = new GeneralBean();

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int ejercicio = -1;

	private int limit = 10;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List proveedoArticulosList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String idcuenta = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	boolean buscar = false;

	private float subTotalGravado = 0;

	private Hashtable htDepositos = null;

	private List listTipificacion = new ArrayList();

	/***/

	private String[] keyHashDatosPlanesContables = null;

	private String[] tipo = null;

	private String[] depositoNombre = new String[20];

	private String[] cantidad = null;

	private String[] precio = null;

	private String[] total = null;

	private String[] delKey = null;

	private String eliminar = "";

	/***/

	private List listPlantilla = new ArrayList();

	private BigDecimal idplantilla = new BigDecimal(-1);

	public BeanProveedoCtasContablesAbm() {
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
		Proveedores proveedo = Common.getProveedores();
		Contable contable = Common.getContable();
		// Si la sesion es distinto de nulo quiere decir que fueron cargadas
		// cuentas contables.
		Hashtable htPlanesContablesConfirmados = (Hashtable) session
				.getAttribute("htPlanesContablesConfirmados") != null ? (Hashtable) session
				.getAttribute("htPlanesContablesConfirmados")
				: new Hashtable();

		Hashtable htPlanesContables = new Hashtable();

		if ((Hashtable) session.getAttribute("htPlanesContables") != null
		// && !((Hashtable) session.getAttribute("htArticulosInOut")).isEmpty()
		) {

			htPlanesContables = (Hashtable) session
					.getAttribute("htPlanesContables");

		} else {

			Common.htRellenar(htPlanesContablesConfirmados, htPlanesContables);
		}

		try {

			this.listPlantilla = proveedo
					.getProveedoPlantilladocEspActivas(this.idempresa);

			// tipificacion contable
			// Llenamos el listt de tipos contables [combobox]
			listTipificacion = proveedo.getContabletipificacion();
			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			// ocurrencias
			if (buscar && accion.equals("")) {

				String entidad = "(Select idcuenta,cuenta,inputable,nivel,ajustable,resultado,ejercicio,idempresa from contableinfiplan "
						+ "where (idcuenta::varchar like ('%"
						+ ocurrencia.toUpperCase().toString()
						+ "%') or upper(cuenta) like ('%"
						+ ocurrencia.toUpperCase().toString() + "%'))" +
								" and ejercicio= (select ejercicio from contableejercicios where activo = 'S') )entidad";
				String filtro = " where idempresa = "
						+ this.idempresa.toString() + " and "
						+"(idcuenta::varchar like ('%"
						+ ocurrencia.toUpperCase().toString()
						+ "%') or upper(cuenta) like ('%"
						+ ocurrencia.toUpperCase().toString()
						+ "%') and ejercicio = (select ejercicio from contableejercicios where activo = 'S'))";

				this.totalRegistros = proveedo.getTotalEntidadFiltro(entidad,
						filtro, this.idempresa);
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
				this.proveedoArticulosList = proveedo.getCuentasInfiPlan2Ocu(
						this.limit, this.offset, this.ocurrencia,
						this.idempresa);
			} else {
				// ejercicio activo
				this.totalRegistros = proveedo
						.getTotalEntidadFiltro(
								"contableinfiplan",
								"where ejercicio = (select ejercicio from contableejercicios where activo = 'S')",
								this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				// todos los planes de cuenta...
				// habrá que traer los planes de cuenta que tienen ejercicios
				// activos: Si!!!!
				// Entonces getCuentasInfiPlan2All trae todos los planes de
				// cuenta con ejercicio activo.
				this.proveedoArticulosList = proveedo.getCuentasInfiPlan2All(
						this.limit, this.offset, this.idempresa);
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";

			if (this.accion.equalsIgnoreCase("agregar")) {
				// Cuando le damos agregar al plan de cuentas
				if (this.idcuenta == null || this.idcuenta.equals("")) {
					this.mensaje = "Seleccione una cuenta contable.";
				} else {
					// Obtiene los datos para llenar el hashtable segun
					// ejercicio, codigo de plan y la empresa
					List listaArt = contable.getMiniPlanCuentasPK(
							this.ejercicio, new Long(this.idcuenta),
							this.idempresa);
					Iterator iterArt = listaArt.iterator();
					while (iterArt.hasNext()) {
						String[] datosArt = (String[]) iterArt.next();
						// llena el hash;
						htPlanesContables.put(Math.random() + "", datosArt);

					}
				}
			} else if (this.accion.equals("eliminar")) {
				if (this.delKey == null) {
					this.mensaje = "Seleccione un articulo a eliminar.";
				} else {
					for (int i = 0; i < this.delKey.length; i++) {
						// remueve por clave valor
						htPlanesContables.remove(this.delKey[i]);
					}
				}
			} else if (this.accion.equals("asignarplantilla")) {

				session.removeAttribute("htPlanesContables");
				htPlanesContables.clear();
				List listaArt = Common.getContable()
						.getMiniPlanCuentasPlantilla(this.ejercicio,
								this.idplantilla, this.idempresa);
				Iterator iterArt = listaArt.iterator();
				while (iterArt.hasNext()) {
					String[] datosArt = (String[]) iterArt.next();
					// llena el hash;
					htPlanesContables.put(Math.random() + "", datosArt);
				}

			}

			// Creamos la variable de sesion : htArticulos que tiene el HASH
			// TABLE htArticulos
			session.setAttribute("htPlanesContables", htPlanesContables);

			if (this.accion.equalsIgnoreCase("confirmar")
					&& this.asignarValidar(htPlanesContables)) {



				Common.htRellenar(htPlanesContables,
						htPlanesContablesConfirmados);

				session.setAttribute("htPlanesContablesConfirmados",
						htPlanesContablesConfirmados);



				session.removeAttribute("htPlanesContables");

			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public boolean asignarValidar(Hashtable ht) throws RemoteException {
		boolean fOk = true;
		// ver que hace esta funcion
		this.subTotalGravado = 0;
		Proveedores proveedoArticulos = Common.getProveedores();
		// este hashtable de datos x articulo (cuando decimos articulos hablamos
		// de plan de cuentas)
		if (this.keyHashDatosPlanesContables != null) {

			for (int i = 0; i < this.keyHashDatosPlanesContables.length; i++) {
				Object key = this.keyHashDatosPlanesContables[i];
				if (ht.containsKey(key)) {
					String[] datos = (String[]) ht.get(key);
					// tipificacion contable
					if (this.tipo[i].equals("-1")) {
						fOk = false;
					} else {
						datos[9] = this.tipo[i];
						datos[10] = proveedoArticulos
								.getTipificacionContable(datos[9]);
					}

					
					// 20130808 - EJV -->
					// if (!datos[9].equalsIgnoreCase("E")) {
					// if (!datos[9].equalsIgnoreCase("IEX")) {
					// <--

					if (Common.esNumerico(this.precio[i])
							&& new BigDecimal(this.precio[i]).longValue() > 0) {
						datos[11] = this.precio[i];
						this.subTotalGravado += Float.parseFloat(datos[11]);
					} else {
						fOk = false;
					}
					ht.put(this.keyHashDatosPlanesContables[i], datos);

					// 20130808 - EJV -->
					// }
					// }
					// <--

				}
			}

			if (!fOk) {
				this.mensaje = "Verifique tipos de iva seleccionados y/o precios ingresados.";

			}

			session.setAttribute("subTotalGravado", Common.getNumeroFormateado(
					this.subTotalGravado, 10, 2));

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

	public String getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(String idcuenta) {
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

	public Hashtable getHtDepositos() {
		return htDepositos;
	}

	public void setHtDepositos(Hashtable htDepositos) {
		this.htDepositos = htDepositos;

	}

	public String[] getCantidad() {
		return cantidad;
	}

	public void setCantidad(String[] cantidad) {
		this.cantidad = cantidad;
	}

	public String[] getTipo() {
		return tipo;
	}

	public void setTipo(String[] tipo) {
		this.tipo = tipo;
	}

	public String[] getDepositoNombre() {
		return depositoNombre;
	}

	public void setDepositoNombre(String[] depositoNombre) {
		this.depositoNombre = depositoNombre;
	}

	public String[] getKeyHashDatosPlanesContables() {
		return keyHashDatosPlanesContables;
	}

	public void setKeyHashDatosPlanesContables(
			String[] keyHashDatosPlanesContables) {
		this.keyHashDatosPlanesContables = keyHashDatosPlanesContables;
	}

	public String[] getPrecio() {
		return precio;
	}

	public void setPrecio(String[] precio) {
		this.precio = precio;
	}

	public String[] getTotal() {
		return total;
	}

	public void setTotal(String[] total) {
		this.total = total;
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

	public List getListTipificacion() {
		return listTipificacion;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public int getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(int ejercicio) {
		this.ejercicio = ejercicio;
	}

	public List getListPlantilla() {
		return listPlantilla;
	}

	public BigDecimal getIdplantilla() {
		return idplantilla;
	}

	public void setIdplantilla(BigDecimal idplantilla) {
		this.idplantilla = idplantilla;
	}

}
