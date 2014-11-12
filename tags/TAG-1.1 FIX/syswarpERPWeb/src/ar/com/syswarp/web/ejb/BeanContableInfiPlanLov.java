/* 
 javabean para la entidad: contableInfiPlan2005
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Oct 30 12:11:01 GMT-03:00 2006 
 
 Para manejar la pagina: 
 
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

public class BeanContableInfiPlanLov implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanContableInfiPlanLov.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private GeneralBean gb = new GeneralBean();

	private int limit = 10;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List contableInfiPlanList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idcuenta = new BigDecimal(-1);

	private String mensaje = "";

	private int ejercicioactivo = 0;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	boolean buscar = false;

	private String[] keyHashCuenta = null;

	private String[] delKey = null;

	private String[] totalImputacionCuenta = null;

	private BigDecimal totalimputacion = new BigDecimal(0);

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BeanContableInfiPlanLov() {
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
		Stock contableInfiPlan = Common.getStock();
		Hashtable htCuentasOk = (Hashtable) session.getAttribute("htCuentasOk") != null ? (Hashtable) session
				.getAttribute("htCuentasOk")
				: new Hashtable();
		Hashtable htCuentas = (Hashtable) session.getAttribute("htCuentas") != null ? (Hashtable) session
				.getAttribute("htCuentas")
				: htCuentasOk;
		try {

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			String filtro = " WHERE ejercicio=" + this.ejercicioactivo;
			if (buscar) {

				filtro += " AND (idcuenta::VARCHAR LIKE '%" + this.ocurrencia
						+ "%' OR UPPER(cuenta) LIKE '%"
						+ this.ocurrencia.toUpperCase() + "%')";
				this.totalRegistros = contableInfiPlan.getTotalEntidadFiltro(
						"contableInfiPlan", filtro, this.idempresa);
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
				this.contableInfiPlanList = contableInfiPlan
						.getContableInfiPlanOcu(this.limit, this.offset,
								this.ejercicioactivo, this.ocurrencia,
								this.idempresa);
			} else {
				this.totalRegistros = contableInfiPlan.getTotalEntidadFiltro(
						"contableInfiPlan", filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.contableInfiPlanList = contableInfiPlan
						.getContableInfiPlanAll(this.limit, this.offset,
								this.ejercicioactivo, this.idempresa);
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";

			if (this.accion.equalsIgnoreCase("agregar")) {

				if (this.idcuenta == null || this.idcuenta.longValue() <= 0) {
					this.mensaje = "Seleccione una cuenta.";

				} else {

					List listaCuenta = contableInfiPlan
							.getContableInfiPlanPK(this.idcuenta,
									this.ejercicioactivo, this.idempresa);
					Iterator iterCuenta = listaCuenta.iterator();
					while (iterCuenta.hasNext()) {
						String[] datos = (String[]) iterCuenta.next();
						String[] datosCuenta = new String[] { datos[0],
								datos[1], "0" };
						htCuentas.put(datosCuenta[0], datosCuenta);
					}

					/**/

				}
			}

			if (this.accion.equals("eliminar")) {
				if (this.delKey == null) {
					this.mensaje = "Seleccione una cuenta a eliminar.";
				} else {
					for (int i = 0; i < this.delKey.length; i++) {
						htCuentas.remove(this.delKey[i]);
					}
				}
			}

			session.setAttribute("htCuentas", htCuentas);

			if (this.accion.equalsIgnoreCase("confirmar")
					&& this.asignarValidar(htCuentas)) {
				session.setAttribute("htCuentasOk", htCuentas);
				session.removeAttribute("htCuentas");
			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public boolean asignarValidar(Hashtable ht) {
		boolean fOk = true;

		if (this.keyHashCuenta != null) {
			for (int i = 0; i < this.keyHashCuenta.length; i++) {
				if (ht.containsKey(this.keyHashCuenta[i])) {
					String[] datos = (String[]) ht.get(this.keyHashCuenta[i]);
					// Verificar tipo datos cantidad

					if (gb.esNumerico(this.totalImputacionCuenta[i])
							&& Float.parseFloat(this.totalImputacionCuenta[i]) > 0) {
						datos[2] = gb.getNumeroFormateado(Float
								.parseFloat(totalImputacionCuenta[i]), 100, 2);
						totalimputacion = totalimputacion.add(new BigDecimal(
								datos[2]));
					} else {
						datos[2] = totalImputacionCuenta[i];
						fOk = false;
					}
					ht.put(this.keyHashCuenta[i], datos);
				}

			}
			if (!fOk)
				this.mensaje = "Verifique valores ingresadas.";

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

	public List getContableInfiPlanList() {
		return contableInfiPlanList;
	}

	public void setContableInfiPlanList(List contableInfiPlanList) {
		this.contableInfiPlanList = contableInfiPlanList;
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

	public int getEjercicioactivo() {
		return ejercicioactivo;
	}

	public void setEjercicioactivo(int ejercicioactivo) {
		this.ejercicioactivo = ejercicioactivo;
	}

	public void setDelKey(String[] delKey) {
		this.delKey = delKey;
	}

	public void setKeyHashCuenta(String[] keyHashCuenta) {
		this.keyHashCuenta = keyHashCuenta;
	}

	public String[] getDelKey() {
		return delKey;
	}

	public String[] getKeyHashCuenta() {
		return keyHashCuenta;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpSession getSession() {
		return session;
	}

	public String[] getTotalImputacionCuenta() {
		return totalImputacionCuenta;
	}

	public void setTotalImputacionCuenta(String[] totalImputacionCuenta) {
		this.totalImputacionCuenta = totalImputacionCuenta;
	}
}
