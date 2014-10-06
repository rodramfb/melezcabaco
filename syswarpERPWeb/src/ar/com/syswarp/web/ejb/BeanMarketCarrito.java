/* 
 javabean para la entidad (Formulario): Stockstock
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Sep 04 09:21:36 GMT-03:00 2006 
 
 Para manejar la pagina: StockstockFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanMarketCarrito implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanMarketCarrito.class);

	private String agregar = "";

	private String codigo_st = "";

	private String[] seleccion = null;

	private String[] keyRecalcular = null;

	private String[] cantidad = null;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private BigDecimal total = new BigDecimal(0);

	


	public BeanMarketCarrito() {
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

	private boolean recalcular(Hashtable htCarritoValidar) {
		boolean retorno = true;

		try {
			if (this.keyRecalcular != null) {
				for (int i = 0; i < this.keyRecalcular.length; i++) {

					BigDecimal totalParcial = new BigDecimal(0);

					// codigo_st, alias_st, descrip_st,
					// precipp_st, "1", precipp_st

					if (!Common.esEntero(this.cantidad[i])) {
						this.mensaje = "Cantidades ingresadas incorrectas.";
						retorno = false;
						break;
					} else if (new BigDecimal(this.cantidad[i])
							.compareTo(new BigDecimal(0)) < 1) {
						this.mensaje = "Cantidades ingresadas incorrectas.";
						retorno = false;
						break;
					} else {
						this.total = new BigDecimal(0);
						String[] datos = (String[]) htCarritoValidar
								.get(this.keyRecalcular[i]);
						totalParcial = ((new BigDecimal(this.cantidad[i])
								.multiply(new BigDecimal(datos[3]))));

						this.total = this.total.add(totalParcial);

						datos[4] = this.cantidad[i];
						datos[5] = totalParcial.toString();
						htCarritoValidar.put(this.keyRecalcular[i], datos);
					}

				}

			}
		} catch (Exception e) {
			log.error("recalculAr(): " + e);
			retorno = false;
		}

		return retorno;

	}

	public boolean ejecutarValidacion() {
		try {

			HttpSession session = request.getSession();

			Hashtable htCarrito = session.getAttribute("htCarrito") == null ? new Hashtable()
					: (Hashtable) session.getAttribute("htCarrito");

			if (this.accion.equalsIgnoreCase("eliminar")) {
				if (this.seleccion == null ) {
					this.mensaje = "Seleccione al menos un ítem a eliminar.";
				}else{
					for (int i = 0; i < this.seleccion.length; i++) {
						if (htCarrito.containsKey(this.seleccion[i])) {
							htCarrito.remove(this.seleccion[i]);
						}
					}
				}

			} else if (this.accion.equalsIgnoreCase("limpiar")) {

				htCarrito.clear();

			} else if (this.accion.equalsIgnoreCase("recalcular")) {

				this.recalcular(htCarrito);

			} else if (this.accion.equalsIgnoreCase("caja")) {

				if (htCarrito.isEmpty())
					this.mensaje = "No hay articulos en el carrito.";
				else if (recalcular(htCarrito)) {
					if (request.getSession().getAttribute("emailCarrito") != null)
						response.sendRedirect("marketRegistroDireccionFrm.jsp");
					else
						response.sendRedirect("marketRegistroFrm.jsp");
				}
			}
			
			session.setAttribute("totalCarrito", this.total);
			session.setAttribute("htCarrito", htCarrito);
			

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	public String getAgregar() {
		return agregar;
	}

	public void setAgregar(String agregar) {
		this.agregar = agregar;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getVolver() {
		return volver;
	}

	public void setVolver(String volver) {
		this.volver = volver;
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

	// metodos para cada atributo de la entidad
	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String[] getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(String[] seleccion) {
		this.seleccion = seleccion;
	}

	public String[] getKeyRecalcular() {
		return keyRecalcular;
	}

	public void setKeyRecalcular(String[] keyRecalcular) {
		this.keyRecalcular = keyRecalcular;
	}

	public String[] getCantidad() {
		return cantidad;
	}

	public void setCantidad(String[] cantidad) {
		this.cantidad = cantidad;
	}
	
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
