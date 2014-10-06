/* 
 javabean para la entidad (Formulario): pedidos_cabe
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 02 09:51:29 GMT-03:00 2007 
 
 Para manejar la pagina: pedidos_cabeFrm.jsp
 
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
import java.math.*;
import java.util.*;
import java.sql.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanPedidos_cabeSASFrm implements SessionBean, Serializable {
	private SessionContext context;

	GeneralBean gb = new GeneralBean();

	static Logger log = Logger.getLogger(BeanPedidos_cabeSASFrm.class);

	private HttpSession session;

	private String validar = "";

	private BigDecimal idpedido_cabe = BigDecimal.valueOf(-1);
	
	private BigDecimal idempresa = new BigDecimal(0);

	private BigDecimal idestado = new BigDecimal(0);

	private BigDecimal idcliente = new BigDecimal(0);

	private String cliente = "";

	private BigDecimal idsucursal = new BigDecimal(0);

	private String sucursal = "";

	private BigDecimal idsucuclie = new BigDecimal(0);

	private String nombre_suc = "";

	private String fechapedido = Common.initObjectTimeStr();

	private BigDecimal idcondicion = new BigDecimal(0);

	private String condicion = "";

	private BigDecimal idvendedor = new BigDecimal(0);

	private String vendedor = "";

	private BigDecimal idexpreso = new BigDecimal(0);

	private String expreso = "";

	private BigDecimal comision = new BigDecimal(0);

	private String ordencompra = "";

	private String observaciones = "";

	private String recargo1 = "0";

	private String recargo2 = "0";

	private String recargo3 = "0";

	private String recargo4 = "0";

	private String bonific1 = "0";

	private String bonific2 = "0";

	private String bonific3 = "0";

	private BigDecimal totalgeneral = new BigDecimal(0);

	private BigDecimal totalGeneralIva = new BigDecimal(0);

	private BigDecimal idlista = new BigDecimal(0);

	private String lista = "";

	private BigDecimal idmoneda = new BigDecimal(0);

	private String moneda = "";

	private BigDecimal idtipoiva = new BigDecimal(0);

	private String tipoiva = "";

	private String cotizacion = "0";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	// 3 - Cuentas Articulos
	private BigDecimal totalDebe = new BigDecimal(0);

	// 3 - Cuentas Imputacion
	private BigDecimal totalHaber = new BigDecimal(0);

	private boolean primeraCarga = true;

	private Hashtable htDepositos = null;

	private List listPrioridades = new ArrayList();

	private BigDecimal idprioridad = new BigDecimal(-1);

	public BeanPedidos_cabeSASFrm() {
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

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public void ejecutarSentenciaDML() {
		try {
			Clientes pedidos_cabe = Common.getClientes();

			Hashtable htArticulos = (Hashtable) session
					.getAttribute("htArticulosInOutOK");

			this.mensaje = pedidos_cabe.pedidosSASCreate(this.idestado,
					this.idcliente, this.idsucursal, this.idsucuclie,
					(Timestamp) Common.setObjectToStrOrTime(this.fechapedido,
							"StrToJSTs"), this.idcondicion, this.idvendedor,
					this.idexpreso, this.comision, this.ordencompra,
					this.observaciones, new BigDecimal(this.recargo1),
					new BigDecimal(this.recargo2),
					new BigDecimal(this.recargo3),
					new BigDecimal(this.recargo4),
					new BigDecimal(this.bonific1),
					new BigDecimal(this.bonific2),
					new BigDecimal(this.bonific3), this.idlista, this.idmoneda,
					new BigDecimal(this.cotizacion), this.idtipoiva,
					this.totalGeneralIva, this.idprioridad, htArticulos,
					this.usuarioalt,this.idempresa);

			if (gb.esNumerico(this.mensaje)) {
				mensaje = "Se genero el pedido nro.: " + this.mensaje;
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			Clientes pedidos_cabe = Common.getClientes();
			boolean calculoOk = true;
			this.listPrioridades = pedidos_cabe.getPedidosPrioridadesSASAll(50, 0);
			
			this.htDepositos = Common.getHashEntidad(
					"getProveedoStockDepositosAll", new Class[] { long.class,
							long.class, BigDecimal.class }, new Object[] { new Long(250), new Long(0),
							this.idempresa });

			if (isPrimeraCarga()) {
				session.removeAttribute("htArticulosInOutOK");
			} else {

				if (this.accion.equalsIgnoreCase("limpiardetalle")) {
					session.removeAttribute("htArticulosInOutOK");
					this.bonific1 = this.bonific2 = this.bonific3 = "0";
					this.recargo1 = this.recargo2 = this.recargo3 = this.recargo4 = "0";
				}

				this.totalDebe = getTotalesAsiento((Hashtable) session
						.getAttribute("htArticulosInOutOK"), 11);
				calculoOk = this.calcularImportes();
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (idestado == null) {
					this.mensaje = "No se puede dejar vacio el campo idestado ";
					return false;
				}
				if (idcliente == null) {
					this.mensaje = "No se puede dejar vacio el campo idcliente ";
					return false;
				}

				if (cliente.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Cliente  ";
					return false;
				}

				if (sucursal.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Sucursal  ";
					return false;
				}

				if (nombre_suc.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Suscursal.  ";
					return false;
				}

				if (condicion.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Condicion.  ";
					return false;
				}

				if (vendedor.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Vendedor.  ";
					return false;
				}

				if (expreso.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Expreso.  ";
					return false;
				}

				if (lista.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Lista.  ";
					return false;
				}

				if (moneda.trim().length() == 0) {
					this.mensaje = "No se puede dejar vacio el campo Moneda.  ";
					return false;
				}

				if (fechapedido == null || fechapedido.trim().equals("")) {
					this.mensaje = "No se puede dejar vacio el campo Fecha Pedido. ";
					return false;
				}

				if (idprioridad.compareTo(new BigDecimal(0)) < 0) {
					this.mensaje = "Es necesario seleccionar Prioridad del Pedido. ";
					return false;
				}

				if (idcondicion == null) {
					this.mensaje = "No se puede dejar vacio el campo condicion ";
					return false;
				}
				if (idvendedor == null) {
					this.mensaje = "No se puede dejar vacio el campo vendedor ";
					return false;
				}

				if (!gb.esNumerico(cotizacion)) {
					this.mensaje = "Cotizacion debe ser un valor numerico.";
					return false;
				}

				if (!calculoOk) {
					log.warn("FALLA CALCULO");
					return false;
				}

				this.ejecutarSentenciaDML();

			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private BigDecimal getTotalesAsiento(Hashtable ht, int indice) {
		BigDecimal total = new BigDecimal(0);
		Enumeration en;
		try {
			if (ht != null) {
				en = ht.keys();
				while (en.hasMoreElements()) {
					Object clave = en.nextElement();
					String[] datos = (String[]) ht.get(clave);
					/*
					 * for (int i = 0; i < datos.length; i++) log.info("DATO " +
					 * i + ":" + datos[i]);
					 */
					total = total.add(new BigDecimal(datos[indice]));
				}
			}
		} catch (Exception e) {

			log.error("getTotalesAsiento(Hashtable ht, int indice):");

		}
		return total;
	}

	public boolean calcularImportes() {

		boolean fOk = true;
		String mensajeCI = "";
		BigDecimal total = new BigDecimal(0);
		Clientes pedidos_cabe = Common.getClientes();
		List listaTipoIva = null;

		try {
			if (this.idcliente.compareTo(new BigDecimal(0)) == 0) {
				mensajeCI = "Seleccione un cliente para calcular importes.";
				fOk = false;
			} else if (!gb.esNumerico(this.bonific1)) {
				mensajeCI = "Bonificacion 1 incorrecta.";
				fOk = false;
			} else if (!gb.esNumerico(this.bonific2)) {
				mensajeCI = "Bonificacion 2 incorrecta.";
				fOk = false;
			} else if (!gb.esNumerico(this.bonific3)) {
				mensajeCI = "Bonificacion 3 incorrecta.";
				fOk = false;
			} else if (!gb.esNumerico(this.recargo1.toString())) {
				mensajeCI = "Recargo 1 incorrecto.";
				fOk = false;
			} else if (!gb.esNumerico(this.recargo2.toString())) {
				mensajeCI = "Recargo 2 incorrecto.";
				fOk = false;
			} else if (!gb.esNumerico(this.recargo3.toString())) {
				mensajeCI = "Recargo 3 incorrecto.";
				fOk = false;
			} else if (!gb.esNumerico(this.recargo4.toString())) {
				mensajeCI = "Recargo 4 incorrecto.";
				fOk = false;
			} else if (session.getAttribute("htArticulosInOutOK") == null
					|| ((Hashtable) session.getAttribute("htArticulosInOutOK"))
							.isEmpty()) {
				mensajeCI = "Ingrese al menos un articulo que afecte al movimiento.";
				fOk = false;
			} else {

				listaTipoIva = pedidos_cabe
						.getClientestablaivaPK(this.idtipoiva,this.idempresa);

				if ((listaTipoIva == null || listaTipoIva.isEmpty())
						&& this.idcliente.compareTo(new BigDecimal(0)) != 0) {
					mensajeCI = "Imposible recuperar datos tipo iva.";
					fOk = false;
				} else {

					String[] datosTipoIva = (String[]) listaTipoIva.get(0);
					BigDecimal porcentajeIva = new BigDecimal(datosTipoIva[2]);

					// total por articulos solicitados
					total = total.add(this.totalDebe);

					// + recargos
					total = total.add(new BigDecimal(this.recargo1));
					total = total.add(new BigDecimal(this.recargo2));
					total = total.add(new BigDecimal(this.recargo3));
					total = total.add(new BigDecimal(this.recargo4));

					// - bonificaciones
					total = total.subtract(new BigDecimal(this.bonific1));
					total = total.subtract(new BigDecimal(this.bonific2));
					total = total.subtract(new BigDecimal(this.bonific3));

					this.totalgeneral = total;
					this.totalGeneralIva = this.totalgeneral
							.add(this.totalgeneral.multiply(porcentajeIva
									.divide(new BigDecimal(100), 0)));
					this.totalGeneralIva = new BigDecimal(gb
							.getNumeroFormateado(this.totalGeneralIva
									.floatValue(), 10, 2));

				}

			}

			this.mensaje = mensajeCI;

		} catch (Exception e) {
			// TODO: handle exception
			this.mensaje = "Error calculando importes.";
			log.error("calcularImportes():" + e);
		}
		return fOk;
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
	public BigDecimal getIdpedido_cabe() {
		return idpedido_cabe;
	}

	public void setIdpedido_cabe(BigDecimal idpedido_cabe) {
		this.idpedido_cabe = idpedido_cabe;
	}

	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public BigDecimal getIdsucursal() {
		return idsucursal;
	}

	public void setIdsucursal(BigDecimal idsucursal) {
		this.idsucursal = idsucursal;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public BigDecimal getIdsucuclie() {
		return idsucuclie;
	}

	public void setIdsucuclie(BigDecimal idsucuclie) {
		this.idsucuclie = idsucuclie;
	}

	public String getNombre_suc() {
		return nombre_suc;
	}

	public void setNombre_suc(String nombre_suc) {
		this.nombre_suc = nombre_suc;
	}

	public String getFechapedido() {
		return fechapedido;
	}

	public void setFechapedido(String fechapedido) {
		this.fechapedido = fechapedido;
	}

	public BigDecimal getIdcondicion() {
		return idcondicion;
	}

	public void setIdcondicion(BigDecimal idcondicion) {
		this.idcondicion = idcondicion;
	}

	public BigDecimal getIdvendedor() {
		return idvendedor;
	}

	public void setIdvendedor(BigDecimal idvendedor) {
		this.idvendedor = idvendedor;
	}

	public BigDecimal getIdexpreso() {
		return idexpreso;
	}

	public void setIdexpreso(BigDecimal idexpreso) {
		this.idexpreso = idexpreso;
	}

	public String getExpreso() {
		return expreso;
	}

	public void setExpreso(String expreso) {
		this.expreso = expreso;
	}

	public BigDecimal getComision() {
		return comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public String getOrdencompra() {
		return ordencompra;
	}

	public void setOrdencompra(String ordencompra) {
		this.ordencompra = ordencompra;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getRecargo1() {
		return recargo1;
	}

	public void setRecargo1(String recargo1) {
		this.recargo1 = recargo1;
	}

	public String getRecargo2() {
		return recargo2;
	}

	public void setRecargo2(String recargo2) {
		this.recargo2 = recargo2;
	}

	public String getRecargo3() {
		return recargo3;
	}

	public void setRecargo3(String recargo3) {
		this.recargo3 = recargo3;
	}

	public String getRecargo4() {
		return recargo4;
	}

	public void setRecargo4(String recargo4) {
		this.recargo4 = recargo4;
	}

	public String getBonific1() {
		return bonific1;
	}

	public void setBonific1(String bonific1) {
		this.bonific1 = bonific1;
	}

	public String getBonific2() {
		return bonific2;
	}

	public void setBonific2(String bonific2) {
		this.bonific2 = bonific2;
	}

	public String getBonific3() {
		return bonific3;
	}

	public void setBonific3(String bonific3) {
		this.bonific3 = bonific3;
	}

	public BigDecimal getIdlista() {
		return idlista;
	}

	public void setIdlista(BigDecimal idlista) {
		this.idlista = idlista;
	}

	public BigDecimal getIdmoneda() {
		return idmoneda;
	}

	public void setIdmoneda(BigDecimal idmoneda) {
		this.idmoneda = idmoneda;
	}

	public String getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(String cotizacion) {
		this.cotizacion = cotizacion;
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

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getTotalDebe() {
		return totalDebe;
	}

	public void setTotalDebe(BigDecimal totalDebe) {
		this.totalDebe = totalDebe;
	}

	public BigDecimal getTotalHaber() {
		return totalHaber;
	}

	public void setTotalHaber(BigDecimal totalHaber) {
		this.totalHaber = totalHaber;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public BigDecimal getTotalgeneral() {
		return totalgeneral;
	}

	public void setTotalgeneral(BigDecimal totalgeneral) {
		this.totalgeneral = totalgeneral;
	}

	public BigDecimal getIdtipoiva() {
		return idtipoiva;
	}

	public void setIdtipoiva(BigDecimal idtipoiva) {
		this.idtipoiva = idtipoiva;
	}

	public String getTipoiva() {
		return tipoiva;
	}

	public void setTipoiva(String tipoiva) {
		this.tipoiva = tipoiva;
	}

	public BigDecimal getTotalGeneralIva() {
		return totalGeneralIva;
	}

	public void setTotalGeneralIva(BigDecimal totalGeneralIva) {
		this.totalGeneralIva = totalGeneralIva;
	}

	public String getDeposito(Object codigo_dt) {
		String deposito = "";
		try {
			deposito = this.htDepositos.get(codigo_dt).toString();

		} catch (Exception e) {
			log.error("getDeposito():" + e);
		}
		return deposito;
	}

	public List getListPrioridades() {
		return listPrioridades;
	}

	public void setListPrioridades(List listPrioridades) {
		this.listPrioridades = listPrioridades;
	}

	public BigDecimal getIdprioridad() {
		return idprioridad;
	}

	public void setIdprioridad(BigDecimal idprioridad) {
		this.idprioridad = idprioridad;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
