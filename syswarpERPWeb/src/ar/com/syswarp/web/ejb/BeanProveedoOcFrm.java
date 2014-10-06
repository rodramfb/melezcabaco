/* 
 javabean para la entidad (Formulario): proveedo_Oc_Cabe
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Mar 28 09:44:56 CEST 2007 
 
 Para manejar la pagina: proveedo_Oc_CabeFrm.jsp
 
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
import java.sql.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanProveedoOcFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(0);

	GeneralBean gb = new GeneralBean();

	static Logger log = Logger.getLogger(BeanProveedoOcFrm.class);

	private String validar = "";

	private BigDecimal id_oc_cabe = new BigDecimal(0);

	private BigDecimal idestadooc = new BigDecimal(0);

	private BigDecimal idproveedor = new BigDecimal(0);

	private String proveedor = "";

	private String fechaoc = Common.initObjectTimeStr();

	private BigDecimal idcondicion = new BigDecimal(0);

	private String condicion = "";

	private String comision = "0";

	private String observaciones = "";

	private String recargo1 = "0";

	private String recargo2 = "0";

	private String recargo3 = "0";

	private String recargo4 = "0";

	private String bonific1 = "0";

	private String bonific2 = "0";

	private String bonific3 = "0";

	private BigDecimal codigo_dt = new BigDecimal(0);

	private String descrip_dt = "";

	private java.sql.Date fecha_entrega_prevista = null;

	private String fecha_entrega_previstaStr = Common.initObjectTimeStr();

	private BigDecimal idmoneda = new BigDecimal(0);

	private String moneda = "";

	private String cotizacion = "0";

	private BigDecimal idtipoiva = new BigDecimal(0);

	private String tipoiva = "";

	private BigDecimal totaliva = new BigDecimal(0);

	private BigDecimal idgrupooc = new BigDecimal(0);

	private String grupooc = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	private String accion = "";

	private boolean imprimePdf = false;

	// 

	private BigDecimal totalDebe = new BigDecimal(0);

	private boolean primeraCarga = true;

	private String totalgeneral = "0";

	private String totalgeneralorigen = "";

	private BigDecimal totalbonificaorigen = new BigDecimal(0);

	private BigDecimal totalrecargoorigen = new BigDecimal(0);

	private String imprimir = "";

	private boolean fueradestock = false;

	public BeanProveedoOcFrm() {
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
			Proveedores proveedo_Oc_Cabe = Common.getProveedores();
			Hashtable htArticulos = (Hashtable) session
					.getAttribute("htArticulosInOutOK");

			this.mensaje = proveedo_Oc_Cabe.proveedoOcCreate(this.idestadooc,
					this.idproveedor, (Timestamp) Common.setObjectToStrOrTime(
							this.fechaoc, "StrToJSTs"), this.idcondicion,
					new BigDecimal(this.comision), this.observaciones,
					new BigDecimal(this.recargo1),
					new BigDecimal(this.recargo2),
					new BigDecimal(this.recargo3),
					new BigDecimal(this.recargo4),
					new BigDecimal(this.bonific1),
					new BigDecimal(this.bonific2),
					new BigDecimal(this.bonific3), this.idmoneda,
					new BigDecimal(this.cotizacion), this.idtipoiva,
					this.totaliva, this.idgrupooc.equals("0") ? null
							: this.idgrupooc, this.idempresa, htArticulos,
					this.usuarioalt, this.codigo_dt,
					this.fecha_entrega_prevista);
			if (gb.esNumerico(this.mensaje)) {
				this.imprimePdf = true;
				this.id_oc_cabe = new BigDecimal(this.mensaje);
				mensaje = "Se genero la orden de compra nro.: " + this.mensaje;
				if (this.idestadooc.toString().equals("1"))
					this.imprimir = "pr";
				else if (this.idestadooc.toString().equals("2"))
					this.imprimir = "oc";
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			boolean calculoOk = true;
			// log.info("isPrimeraCarga : " +isPrimeraCarga() );
			if (isPrimeraCarga()) {
				session.removeAttribute("htArticulosInOutOK");
			} else {
				this.totalDebe = getTotalesAsiento((Hashtable) session
						.getAttribute("htArticulosInOutOK"), 11);
				calculoOk = this.calcularImportes();
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos

					if (new BigDecimal(this.totalgeneral).signum() == -1) {
						this.mensaje = "Verifique valores ingresados, total debe ser un valor positivo.";
						return false;
					}

					if (idproveedor == null
							|| idproveedor.compareTo(new BigDecimal(0)) == 0) {
						this.mensaje = "Es necesario seleccionar proveedor.";
						return false;
					}

					if (idestadooc == null
							|| idestadooc.compareTo(new BigDecimal(0)) == 0) {
						this.mensaje = "Es necesario seleccionar estado.";
						return false;
					}

					if (idestadooc.compareTo(new BigDecimal(1)) == 0) {
						if (idgrupooc == null
								|| idgrupooc.compareTo(new BigDecimal(0)) == 0) {
							this.mensaje = "Es necesario seleccionar grupo de cotizacion.";
							return false;
						}
					}

					if (fechaoc == null || fechaoc.trim().equals("")) {
						this.mensaje = "No se puede dejar vacio el campo fecha. ";
						return false;
					}
					
					if (codigo_dt == null) {
						this.mensaje = "No se puede dejar vacio el campo deposito entrega previsto. ";
						return false;
					}
			
					if (idcondicion == null
							|| idcondicion.compareTo(new BigDecimal(0)) == 0) {
						this.mensaje = "No se puede dejar vacio el campo condicion de pago.";
						return false;
					}
					if (idmoneda == null
							|| idmoneda.compareTo(new BigDecimal(0)) == 0) {
						this.mensaje = "No se puede dejar vacio el campo moneda.";
						return false;
					}

					Calendar cal = new GregorianCalendar();
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);

					this.fecha_entrega_prevista = (java.sql.Date) Common
							.setObjectToStrOrTime(
									this.fecha_entrega_previstaStr,
									"StrToJSDate");
/*
					log.info("this.fecha_entrega_prevista: "
							+ this.fecha_entrega_prevista);
					log.info("new java.sql.Date(Common.initObjectTime()): "
							+ new java.sql.Date(Common.initObjectTime()));
					log.info("COMPARE: "
							+ this.fecha_entrega_prevista.compareTo(cal
									.getTime()));
					log
							.info("BEFORE: "
									+ this.fecha_entrega_prevista.before(cal
											.getTime()));

					log.info("cal.getTime().getTime(): "
							+ cal.getTime().getTime());
					log.info("this.fecha_entrega_prevista.getTime(): "
							+ this.fecha_entrega_prevista.getTime());

*/					if (this.fecha_entrega_prevista == null
							|| this.fecha_entrega_prevista.getTime() < (cal
									.getTime().getTime())) {
						this.mensaje = "Fecha prevista para la entrega debe ser mayor o igual a la fecha actual.";
						return false;
					}

					// 2. len 0 para campos nulos

					if (!calculoOk) {
						log.warn("FALLA CALCULO");
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

	private BigDecimal getTotalesAsiento(Hashtable ht, int indice) {
		BigDecimal total = new BigDecimal(0);
		Enumeration en;
		try {
			if (ht != null) {
				en = ht.keys();
				while (en.hasMoreElements()) {
					Object clave = en.nextElement();
					String[] datos = (String[]) ht.get(clave);
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
		BigDecimal totalbonifica = new BigDecimal(0);
		BigDecimal totalrecargo = new BigDecimal(0);
		Clientes pedidos_cabe = Common.getClientes();
		List listaTipoIva = null;

		try {

			if (this.idproveedor.compareTo(new BigDecimal(0)) == 0) {
				mensajeCI = "Seleccione un proveedor para calcular importes.";
				fOk = false;
			} else if (this.idtipoiva.compareTo(new BigDecimal(0)) == 0) {
				mensajeCI = "Seleccione tipo de iva para calcular importes.";
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
			} else if (!gb.esNumerico(this.totalgeneral.toString())) {
				mensajeCI = "Total General incorrecto.";
				fOk = false;
			} else if ((session.getAttribute("htArticulosInOutOK") == null || ((Hashtable) session
					.getAttribute("htArticulosInOutOK")).isEmpty())
					&& !isFueradestock()) {
				mensajeCI = "Ingrese al menos un articulo que afecte al movimiento.";
				fOk = false;
				// EJV 20070419
			} else if (session.getAttribute("htArticulosInOutOK") != null
					&& !(((Hashtable) session
							.getAttribute("htArticulosInOutOK")).isEmpty())
					&& isFueradestock()) {
				mensajeCI = "No se puede seleccionar Articulos Fuera de Stock e Ingresar Articulos.";
				fOk = false;
				// EJV 20070419
			} else if (isFueradestock()
					&& this.observaciones.trim().length() < 50) {
				mensajeCI = "Selecciono Artículos Fuera de Stock.\nObservaciones debe contener al menos 50 caracteres.";
				fOk = false;

			} else {

				listaTipoIva = pedidos_cabe.getClientestablaivaPK(
						this.idtipoiva, this.idempresa);

				if ((listaTipoIva == null || listaTipoIva.isEmpty())
						&& this.idtipoiva.compareTo(new BigDecimal(0)) != 0) {
					mensajeCI = "Imposible recuperar datos tipo iva.";
					fOk = false;
				} else {

					String[] datosTipoIva = (String[]) listaTipoIva.get(0);
					BigDecimal porcentajeIva = new BigDecimal(datosTipoIva[2]);

					// total por articulos solicitados
					total = total.add(this.totalDebe);

					// + recargos
					totalrecargo = totalrecargo.add(new BigDecimal(
							this.recargo1));
					totalrecargo = totalrecargo.add(new BigDecimal(
							this.recargo2));
					totalrecargo = totalrecargo.add(new BigDecimal(
							this.recargo3));
					totalrecargo = totalrecargo.add(new BigDecimal(
							this.recargo4));
					total = total.add(totalrecargo);

					// - bonificaciones
					totalbonifica = totalbonifica.add(new BigDecimal(
							this.bonific1));
					totalbonifica = totalbonifica.add(new BigDecimal(
							this.bonific2));
					totalbonifica = totalbonifica.add(new BigDecimal(
							this.bonific3));
					total = total.subtract(totalbonifica);
					// EJV 20070419
					// this.totalgeneral = total;
					if (isFueradestock()) {

						if (!this.totalgeneralorigen.equals(totalgeneral)) {

							this.totalgeneral = (new BigDecimal(
									this.totalgeneral).add(total)).toString();

						} else if (totalbonifica
								.compareTo(this.totalbonificaorigen) != 0
								|| totalrecargo
										.compareTo(this.totalrecargoorigen) != 0)

						{

							this.totalgeneral = (new BigDecimal(
									this.totalgeneral)
									.subtract(this.totalrecargoorigen))
									.toString();

							this.totalgeneral = (new BigDecimal(
									this.totalgeneral)
									.add(this.totalbonificaorigen)).toString();

							this.totalgeneral = (new BigDecimal(
									this.totalgeneral).add(total)).toString();

						}

					} else {
						this.totalgeneral = total.toString();
					}

					this.totaliva = new BigDecimal(this.totalgeneral)
							.add(new BigDecimal(this.totalgeneral)
									.multiply(porcentajeIva.divide(
											new BigDecimal(100), 0)));
					this.totaliva = new BigDecimal(gb.getNumeroFormateado(
							this.totaliva.floatValue(), 10, 2));

				}

			}

			this.totalrecargoorigen = totalrecargo;
			this.totalbonificaorigen = totalbonifica;
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
	public BigDecimal getId_oc_cabe() {
		return id_oc_cabe;
	}

	public void setId_oc_cabe(BigDecimal id_oc_cabe) {
		this.id_oc_cabe = id_oc_cabe;
	}

	public BigDecimal getIdestadooc() {
		return idestadooc;
	}

	public void setIdestadooc(BigDecimal idestadooc) {
		this.idestadooc = idestadooc;
	}

	public BigDecimal getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(BigDecimal idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getFechaoc() {
		return fechaoc;
	}

	public void setFechaoc(String fechaoc) {
		this.fechaoc = fechaoc;
	}

	public BigDecimal getIdcondicion() {
		return idcondicion;
	}

	public void setIdcondicion(BigDecimal idcondicion) {
		this.idcondicion = idcondicion;
	}

	public String getComision() {
		return comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
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

	public BigDecimal getIdtipoiva() {
		return idtipoiva;
	}

	public void setIdtipoiva(BigDecimal idtipoiva) {
		this.idtipoiva = idtipoiva;
	}

	public BigDecimal getTotaliva() {
		return totaliva;
	}

	public void setTotaliva(BigDecimal totaliva) {
		this.totaliva = totaliva;
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

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public void setTotalgeneral(String totalgeneral) {
		this.totalgeneral = totalgeneral;
	}

	public String getTotalgeneral() {
		return totalgeneral;
	}

	public BigDecimal getTotalDebe() {
		return totalDebe;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getTipoiva() {
		return tipoiva;
	}

	public void setTipoiva(String tipoiva) {
		this.tipoiva = tipoiva;
	}

	public String getGrupooc() {
		return grupooc;
	}

	public void setGrupooc(String grupooc) {
		this.grupooc = grupooc;
	}

	public BigDecimal getIdgrupooc() {
		return idgrupooc;
	}

	public void setIdgrupooc(BigDecimal idgrupooc) {
		this.idgrupooc = idgrupooc;
	}

	public String getImprimir() {
		return imprimir;
	}

	public boolean isFueradestock() {
		return fueradestock;
	}

	public void setFueradestock(boolean fueradestock) {
		this.fueradestock = fueradestock;
	}

	public String getTotalgeneralorigen() {
		return totalgeneralorigen;
	}

	public void setTotalgeneralorigen(String totalgeneralorigen) {
		this.totalgeneralorigen = totalgeneralorigen;
	}

	public BigDecimal getTotalbonificaorigen() {
		return totalbonificaorigen;
	}

	public void setTotalbonificaorigen(BigDecimal totalbonificaorigen) {
		this.totalbonificaorigen = totalbonificaorigen;
	}

	public BigDecimal getTotalrecargoorigen() {
		return totalrecargoorigen;
	}

	public void setTotalrecargoorigen(BigDecimal totalrecargoorigen) {
		this.totalrecargoorigen = totalrecargoorigen;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public boolean isImprimePdf() {
		return imprimePdf;
	}

	public void setImprimePdf(boolean imprimePdf) {
		this.imprimePdf = imprimePdf;
	}

	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getDescrip_dt() {
		return descrip_dt;
	}

	public void setDescrip_dt(String descrip_dt) {
		this.descrip_dt = descrip_dt;
	}

	public java.sql.Date getFecha_entrega_prevista() {
		return fecha_entrega_prevista;
	}

	public void setFecha_entrega_prevista(java.sql.Date fecha_entrega_prevista) {
		this.fecha_entrega_prevista = fecha_entrega_prevista;
	}

	public String getFecha_entrega_previstaStr() {
		return fecha_entrega_previstaStr;
	}

	public void setFecha_entrega_previstaStr(String fecha_entrega_previstaStr) {
		this.fecha_entrega_previstaStr = fecha_entrega_previstaStr;
		this.fecha_entrega_prevista = (java.sql.Date) Common
				.setObjectToStrOrTime(fecha_entrega_previstaStr, "strToJSDate");
	}

}
