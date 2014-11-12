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

public class BeanProveedoOcImprimeDocumento implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	public GeneralBean gb = new GeneralBean();

	static Logger log = Logger.getLogger(BeanProveedoOcImprimeDocumento.class);

	private String validar = "";

	private BigDecimal id_oc_cabe = new BigDecimal(0);

	private BigDecimal idestadooc = new BigDecimal(0);

	private String estadooc = "";

	private BigDecimal idproveedor = new BigDecimal(0);

	private String proveedor = "";

	private String proveedorDireccion = "";

	private String proveedorLocalidad = "";

	private String proveedorProvincia = "";

	private String proveedorCodPost = "";

	private String proveedorTelefono = "";

	private String proveedorContacto = "";

	private String proveedorCuit = "";

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

	// 

	private BigDecimal totalDebe = new BigDecimal(0);

	private boolean primeraCarga = true;

	private BigDecimal totalgeneral = new BigDecimal(0);

	private List listOCDeta = new ArrayList();

	private List listProveedo = new ArrayList();

	private boolean fueradestock = false;

	public BeanProveedoOcImprimeDocumento() {

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
		try {
			Proveedores prov = Common.getProveedores();
			boolean calculoOk = true;

			List listOCCabe = prov.getProveedo_Oc_CabePK(this.id_oc_cabe,
					this.idempresa);
			String[] datosOCCabe = null;

			if (listOCCabe == null || listOCCabe.isEmpty()) {
				this.mensaje = "Imposible recuperar datos de cabecera.";
				return false;
			}
			/*
			 * occ.id_oc_cabe, occ.idestadooc, oce.estadooc, occ.idproveedor,
			 * occ.fechaoc, cc.condicion, occ.idcondicionpago, occ.comision,
			 * occ.observaciones, occ.recargo1, occ.recargo2, occ.recargo3,
			 * occ.recargo4,
			 * occ.bonific1,occ.bonific2,occ.bonific3,occ.idmoneda, gm.moneda,
			 * occ.cotizacion, occ.idtipoiva, cti.tipoiva, occ.totaliva,
			 * occ.usuarioalt, occ.usuarioact, occ.fechaalt, occ.fechaact
			 * 
			 */

			datosOCCabe = (String[]) listOCCabe.get(0);
			this.idestadooc = new BigDecimal(datosOCCabe[1]);
			this.estadooc = datosOCCabe[2];
			this.idproveedor = new BigDecimal(datosOCCabe[3]);
			this.fechaoc = Common.setObjectToStrOrTime(
					Timestamp.valueOf(datosOCCabe[4]), "JSTsToStr").toString();
			this.idcondicion = new BigDecimal(datosOCCabe[5]);
			this.condicion = datosOCCabe[6];
			this.comision = datosOCCabe[7];
			this.observaciones = datosOCCabe[8];
			this.recargo1 = datosOCCabe[9];
			this.recargo2 = datosOCCabe[10];
			this.recargo3 = datosOCCabe[11];
			this.recargo4 = datosOCCabe[12];
			this.bonific1 = datosOCCabe[13];
			this.bonific2 = datosOCCabe[14];
			this.bonific3 = datosOCCabe[15];
			this.idmoneda = new BigDecimal(datosOCCabe[16]);
			this.moneda = datosOCCabe[17];
			this.cotizacion = datosOCCabe[18];
			this.idtipoiva = new BigDecimal(datosOCCabe[19]);
			this.tipoiva = datosOCCabe[20];
			this.totaliva = new BigDecimal(datosOCCabe[21]);
			this.listProveedo = prov.getProveedoProveedPK(this.idproveedor,
					this.idempresa);

			if (this.listProveedo == null || this.listProveedo.isEmpty()) {
				this.mensaje = "Imposible recuperar datos proveedor.";
				return false;
			}

			String[] datosProveedo = (String[]) listProveedo.get(0);

			/*
			 * pr.idproveedor, pr.razon_social, pr.domicilio, pr.idlocalidad,
			 * gl.localidad,pr.idprovincia, gp.provincia, pr.postal,
			 * pr.contacto, pr.telefono, pr.cuit, pr.brutos,pr.ctapasivo,
			 * pr.ctaactivo1, pr.ctaactivo2, pr.ctaactivo3, pr.ctaactivo4,
			 * pr.ctaiva, pr.ctaretiva,pr.letra_iva, pr.ctadocumen, pr.ret_gan,
			 * pr.idretencion1,r1.retencion, pr.idretencion2, r2.retencion,
			 * pr.idretencion3, r3.retencion, pr.idretencion4,r4.retencion,
			 * pr.idretencion5, r5.retencion, pr.ctades, pr.stock_fact,
			 * pr.idcondicionpago, pc.condicionpago,pr.cent1,pr.cent2, pr.cent3,
			 * pr.cent4, pr.cents1,pr.cents2, pr.cents3, pr.cents4,
			 * pr.usuarioalt,pr.usuarioact, pr.fechaalt, pr.fechaact
			 * 
			 */

			this.proveedor = datosProveedo[1];
			this.proveedorDireccion = datosProveedo[2];
			this.proveedorLocalidad = datosProveedo[4];
			this.proveedorProvincia = datosProveedo[6];
			this.proveedorCodPost = datosProveedo[7];
			this.proveedorContacto = datosProveedo[8];
			this.proveedorTelefono = datosProveedo[9];
			this.proveedorCuit = datosProveedo[10];

			this.listOCDeta = prov.getProveedo_Oc_DetaOc(this.id_oc_cabe,
					this.idempresa);

			this.totalDebe = getTotalSinIva(this.listOCDeta);

			calculoOk = this.calcularImportes();

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private BigDecimal getTotalSinIva(List listaDetalle) {
		BigDecimal total = new BigDecimal(0);
		try {
			/*
			 * ocd.id_oc_deta, ocd.id_oc_cabe, ocd.codigo_st, st.descrip_st,
			 * st.proveed_st, st.provart_st, ocd.fecha, ocd.renglon, ocd.precio,
			 * ocd.saldo,ocd.cantidad, ocd.bonific, sm.descrip_md,
			 * ocd.codigo_md, ocd.cantuni, ocd.entrega, ((ocd.precio *
			 * ocd.cantidad)::numeric(18,2)) as totalproducto, ocd.usuarioalt,
			 * ocd.usuarioact, ocd.fechaalt, ocd.fechaact "
			 */
			if (listaDetalle != null) {
				Iterator iterDetalle = listaDetalle.iterator();
				while (iterDetalle.hasNext()) {
					String[] datos = (String[]) iterDetalle.next();

					total = total.add(new BigDecimal(datos[16]));
				}
			}
		} catch (Exception e) {

			log.error("getTotalSinIva(List listDetalle):");

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
				/*
				 * } else if (session.getAttribute("htArticulosInOutOK") == null ||
				 * ((Hashtable) session.getAttribute("htArticulosInOutOK"))
				 * .isEmpty()) { mensajeCI = "Ingrese al menos un articulo que
				 * afecte al movimiento."; fOk = false;
				 */
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
					total = total.add(new BigDecimal(this.recargo1));
					total = total.add(new BigDecimal(this.recargo2));
					total = total.add(new BigDecimal(this.recargo3));
					total = total.add(new BigDecimal(this.recargo4));

					// - bonificaciones
					total = total.subtract(new BigDecimal(this.bonific1));
					total = total.subtract(new BigDecimal(this.bonific2));
					total = total.subtract(new BigDecimal(this.bonific3));

					if (!isFueradestock()) {
						this.totalgeneral = total;
						this.totaliva = this.totalgeneral.add(this.totalgeneral
								.multiply(porcentajeIva.divide(new BigDecimal(
										100), 0)));
					} else {

						/*
						 * log.info("ESTA FUERA DE STOCK"); this.totalgeneral =
						 * (porcentajeIva .multiply(this.totaliva)).divide( new
						 * BigDecimal(100), 2);
						 */
					}
					this.totalgeneral = new BigDecimal(gb.getNumeroFormateado(
							this.totalgeneral.floatValue(), 10, 2));
					this.totaliva = new BigDecimal(gb.getNumeroFormateado(
							this.totaliva.floatValue(), 10, 2));

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

	public BigDecimal getTotalgeneral() {
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

	public List getListOCDeta() {
		return listOCDeta;
	}

	public List getListProveedo() {
		return listProveedo;
	}

	public String getProveedorCodPost() {
		return proveedorCodPost;
	}

	public String getProveedorContacto() {
		return proveedorContacto;
	}

	public String getProveedorCuit() {
		return proveedorCuit;
	}

	public String getProveedorDireccion() {
		return proveedorDireccion;
	}

	public String getProveedorLocalidad() {
		return proveedorLocalidad;
	}

	public String getProveedorTelefono() {
		return proveedorTelefono;
	}

	public String getProveedorProvincia() {
		return proveedorProvincia;
	}

	public String getEstadooc() {
		return estadooc;
	}

	public boolean isFueradestock() {
		return fueradestock;
	}

	public void setFueradestock(boolean fueradestock) {
		this.fueradestock = fueradestock;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
