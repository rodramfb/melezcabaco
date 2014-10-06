/* 
 javabean para la entidad (Formulario): produccionEsquemas_Cabe
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Feb 13 09:18:39 GMT-03:00 2007 
 
 Para manejar la pagina: produccionEsquemas_CabeFrm.jsp
 
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

public class BeanProduccionEsquemasFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProduccionEsquemasFrm.class);

	private boolean replicar = false;

	boolean primerCarga = true;

	private String validar = "";

	private BigDecimal idesquema = new BigDecimal(0);

	private String esquema = "";

	private BigDecimal codigo_md = new BigDecimal(0);

	private String d_codigo_md = "";

	private String observaciones = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	private String accion = "";

	private String agregar = "";

	/**
	 * DATOS DETALLE
	 */

	private String renglon = "1";

	private String tipo = "";

	private String codigo_st = "";

	private String descrip_st = "";

	private String cantidad = "";

	private String entsal = "";

	private String codigo_dt = "";

	private String margen_error = "0";

	private String imprime = "";

	private String edita = "";

	private String formula = "";

	private String reutiliza = "";

	List produccionEsquemas_DetaList = null;

	Hashtable htDetalleEsquema = null;

	private String rengloneliminar = "";

	private List listDepositos = new ArrayList();

	private Hashtable htDeleteDetalle = null;

	private String habilitado = "";

	/**
	 * */

	public BeanProduccionEsquemasFrm() {
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
			this.htDetalleEsquema = (Hashtable) session
					.getAttribute("htDetalleEsquema");

			this.htDeleteDetalle = (Hashtable) session
					.getAttribute("htDeleteDetalle");

			Produccion produccionEsquemas = Common.getProduccion();
			if (this.accion.equalsIgnoreCase("alta")) {
				this.mensaje = produccionEsquemas.produccionEsquemasCreate(
						this.esquema, this.codigo_md, this.observaciones,
						this.htDetalleEsquema, this.usuarioalt, this.idempresa);

				if (Common.esEntero(this.mensaje)) {
					idesquema = new BigDecimal(this.mensaje);
					this.accion = "MODIFICACION";
					this.mensaje = "Esquema generado correctamente con identificador nro: "
							+ this.mensaje;

				}

			} else if (this.accion.equalsIgnoreCase("modificacion")) {
				this.mensaje = produccionEsquemas.produccionEsquemasUpdate(
						idesquema, this.esquema, this.codigo_md,
						this.observaciones, this.htDetalleEsquema,
						this.htDeleteDetalle, this.usuarioalt, this.idempresa);
			}

			
			this.htDeleteDetalle.clear();
			
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProduccionEsquemas_Cabe() {
		try {
			Produccion produccionEsquemas = Common.getProduccion();
			List listProduccionEsquemas = produccionEsquemas
					.getProduccionEsquemas_CabePK(this.idesquema,
							this.idempresa);
			Iterator iterProduccionEsquemas = listProduccionEsquemas.iterator();
			if (iterProduccionEsquemas.hasNext()) {
				String[] uCampos = (String[]) iterProduccionEsquemas.next();
				// TODO: Constructores para cada tipo de datos
				this.idesquema = new BigDecimal(uCampos[0]);
				this.esquema = isReplicar() ? "REPLICA DE: " + uCampos[1]
						: uCampos[1];
				this.codigo_md = new BigDecimal(uCampos[2]);
				this.d_codigo_md = uCampos[3];
				this.observaciones = isReplicar() ? uCampos[4]
						+ " Replica de esquema:  " + this.idesquema + " - "
						+ uCampos[1] : uCampos[4];
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosProduccionEsquemas()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			Produccion produccionEsquemas = Common.getProduccion();
			Proveedores proveedoArticulos = Common.getProveedores();

			listDepositos = proveedoArticulos.getProveedoStockDepositosAll(250,
					0, this.idempresa);

			if (this.accion.equalsIgnoreCase("modificacion")) {

				BigDecimal totalMovimientos = produccionEsquemas
						.esquemaHaveMovProd(idesquema, idempresa);
				if (totalMovimientos.longValue() > 0) {
					this.habilitado = "disabled";
					this.mensaje = "El esquema no puede ser modificado, posee movimientos de produccion asociados.";
				} else if (totalMovimientos.longValue() < 0) {
					this.mensaje = "No se pudo contabilizar si el esquema posee movimientos de produccion.";
					this.habilitado = "disabled";
				}
			}

			if (this.primerCarga) {

				this.htDetalleEsquema = new Hashtable();
				// MODIFICACION
				if (accion.equalsIgnoreCase("modificacion") || isReplicar()) {
					getDatosProduccionEsquemas_Cabe();

					this.produccionEsquemas_DetaList = produccionEsquemas
							.getProduccionEsquemas_DetaAll(100, 0,
									this.idesquema, this.idempresa);
					Iterator iterEsquemaDetalle = this.produccionEsquemas_DetaList
							.iterator();

					while (iterEsquemaDetalle.hasNext()) {
						String[] datos = (String[]) iterEsquemaDetalle.next();
						this.htDetalleEsquema.put(Common.strZero(datos[1], 3),
								datos);
					}
				}

				session.setAttribute("htDetalleEsquema", this.htDetalleEsquema);
			}

			if (!this.rengloneliminar.equals("")) {
				this.htDetalleEsquema = (Hashtable) session
						.getAttribute("htDetalleEsquema");
				this.htDeleteDetalle = (Hashtable) session
						.getAttribute("htDeleteDetalle") != null ? (Hashtable) session
						.getAttribute("htDeleteDetalle")
						: new Hashtable();

				if (this.htDetalleEsquema.containsKey(this.rengloneliminar)) {

					String[] datos = (String[]) this.htDetalleEsquema
							.get(this.rengloneliminar);
					this.htDeleteDetalle.put(datos[1], datos[1]);
					session.setAttribute("htDeleteDetalle",
							this.htDeleteDetalle);
					this.htDetalleEsquema.remove(Common.strZero(datos[1], 3));

					this.renumeraRenglon(this.htDetalleEsquema);

					session.setAttribute("htDetalleEsquema",
							this.htDetalleEsquema);

				}

			}

			if (!this.agregar.equalsIgnoreCase("")) {

				if (!Common.esEntero(this.renglon)
						|| new BigDecimal(this.renglon.trim())
								.compareTo(new BigDecimal(0)) < 0) {
					this.mensaje = "Renglon solo permite valores enteros positivos.";
					return false;
				}

				if (this.codigo_st.trim().equals("")) {
					this.mensaje = "Es necesario seleccionar un item.";
					return false;
				}

				if (this.codigo_dt.trim().equals("")) {
					this.mensaje = "Es necesario seleccionar un deposito.";
					return false;
				}

				if (this.tipo.trim().equals("")) {
					this.mensaje = "Es necesario seleccionar tipo.";
					return false;
				}

				if (this.entsal.trim().equals("")) {
					this.mensaje = "Es necesario seleccionar si es producto de consumo o final.";
					return false;
				}

				if (this.margen_error.trim().equals("")) {
					this.mensaje = "Es necesario cargar margen de error.";
					return false;
				}

				if (!Common.esNumerico(this.margen_error)
						|| new BigDecimal(this.margen_error.trim())
								.compareTo(new BigDecimal(0)) < 0) {
					this.mensaje = "Margen de error permite valores enteros positivos.";
					return false;
				}

				if (this.cantidad.trim().equals("")) {
					this.mensaje = "Es necesario ingresar cantidad.";
					return false;
				}

				if (!Common.esNumerico(this.cantidad)) {
					this.mensaje = "Cantidad debe ser numerico.";
					return false;
				}

				if (this.entsal.equalsIgnoreCase("C")
						&& new BigDecimal(this.cantidad.trim())
								.compareTo(new BigDecimal(0.01)) <= 0) {
					this.mensaje = "Cantidad debe ser mayor a cero, selecciono Producto de Consumo.";
					return false;
				}

				if (this.entsal.equalsIgnoreCase("P")
						&& new BigDecimal(this.cantidad.trim())
								.compareTo(new BigDecimal(0)) != 0) {
					this.mensaje = "cantidad debe ser igual a cero, selecciono Producto Elaborado(Final).";
					return false;
				}

				if (this.imprime.trim().equals("")) {
					this.mensaje = "Es necesario seleccionar si imprime.";
					return false;
				}

				if (this.edita.trim().equals("")) {
					this.mensaje = "Es necesario seleccionar si edita.";
					return false;
				}

				if (this.reutiliza.trim().equals("")) {
					this.mensaje = "Es necesario seleccionar si es reutilizable en caso de desarme.";
					return false;
				}

				/*
				 * idesquema,renglon,tipo,codigo_st,descrip_st,cantidad,entsal,codigo_dt
				 * ,margen_error,
				 * imprime,edita,formula,reutiliza,usuarioalt,usuarioact
				 * ,fechaalt,fechaact
				 */

				String[] datos = new String[] { this.idesquema.toString(),
						this.renglon.trim(), this.tipo, this.codigo_st,
						this.descrip_st, this.cantidad.trim(), this.entsal,
						this.codigo_dt, this.margen_error.trim(), this.imprime,
						this.edita, this.formula, this.reutiliza, "", "", "",
						"" };

				this.htDetalleEsquema = (Hashtable) session
						.getAttribute("htDetalleEsquema");
				this.htDetalleEsquema.put(Common.strZero(datos[1], 3), datos);
				session.setAttribute("htDetalleEsquema", htDetalleEsquema);

			}

			if (this.htDetalleEsquema != null)
				this.renglon = (this.htDetalleEsquema.size() + 1) + "";

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("produccionEsquemasAbm.jsp");
				return true;
			}

			if (!this.validar.equalsIgnoreCase("")) {

				if (!this.accion.equalsIgnoreCase("baja")) {
					this.htDetalleEsquema = (Hashtable) session
							.getAttribute("htDetalleEsquema");
					// 1. nulidad de campos
					if (esquema == null) {
						this.mensaje = "No se puede dejar vacio el campo esquema ";
						return false;
					}
					if (codigo_md == null) {
						this.mensaje = "No se puede dejar vacio el campo codigo_md ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (esquema.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo esquema  ";
						return false;
					}

					if (this.htDetalleEsquema == null
							|| this.htDetalleEsquema.isEmpty()) {
						this.mensaje = "El esquema debe tener al menos un registro en su detalle. ";
						return false;
					}

				}

				this.ejecutarSentenciaDML();
			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private void renumeraRenglon(Hashtable ht) {

		Enumeration enumera = Common.getSetSorted(ht.keySet());
		Hashtable htAux = new Hashtable();
		int i = 0;

		while (enumera.hasMoreElements()) {
			i = htAux.size() + 1;
			String[] datos = (String[]) ht.get(enumera.nextElement());
			datos[1] = i + "";
			htAux.put(Common.strZero(datos[1], 3), datos);
		}

		Common.htRellenar(htAux, ht);

	}

	public String getValidar() {
		return validar;
	}

	public void setValidar(String validar) {
		this.validar = validar;
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
	public BigDecimal getIdesquema() {
		return idesquema;
	}

	public void setIdesquema(BigDecimal idesquema) {
		this.idesquema = idesquema;
	}

	public String getEsquema() {
		return esquema;
	}

	public void setEsquema(String esquema) {
		this.esquema = esquema;
	}

	public BigDecimal getCodigo_md() {
		return codigo_md;
	}

	public void setCodigo_md(BigDecimal codigo_md) {
		this.codigo_md = codigo_md;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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

	/**
	 * DATOS DETALLE
	 */

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(String codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public String getEdita() {
		return edita;
	}

	public void setEdita(String edita) {
		this.edita = edita;
	}

	public String getEntsal() {
		return entsal;
	}

	public void setEntsal(String entsal) {
		this.entsal = entsal;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getImprime() {
		return imprime;
	}

	public void setImprime(String imprime) {
		this.imprime = imprime;
	}

	public String getMargen_error() {
		return margen_error;
	}

	public void setMargen_error(String margen_error) {
		this.margen_error = margen_error;
	}

	public String getRenglon() {
		return renglon;
	}

	public void setRenglon(String renglon) {
		this.renglon = renglon;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List getProduccionEsquemas_DetaList() {
		return produccionEsquemas_DetaList;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpSession getSession() {
		return session;
	}

	public boolean isPrimerCarga() {
		return primerCarga;
	}

	public void setPrimerCarga(boolean primerCarga) {
		this.primerCarga = primerCarga;
	}

	public String getAgregar() {
		return agregar;
	}

	public void setAgregar(String agregar) {
		this.agregar = agregar;
	}

	public String getRengloneliminar() {
		return rengloneliminar;
	}

	public void setRengloneliminar(String rengloneliminar) {
		this.rengloneliminar = rengloneliminar;
	}

	public List getListDepositos() {
		return listDepositos;
	}

	public String getD_codigo_md() {
		return d_codigo_md;
	}

	public void setD_codigo_md(String d_codigo_md) {
		this.d_codigo_md = d_codigo_md;
	}

	public boolean isReplicar() {
		return replicar;
	}

	public void setReplicar(boolean replicar) {
		this.replicar = replicar;
	}

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getReutiliza() {
		return reutiliza;
	}

	public void setReutiliza(String reutiliza) {
		this.reutiliza = reutiliza;
	}

	public String getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}
}
