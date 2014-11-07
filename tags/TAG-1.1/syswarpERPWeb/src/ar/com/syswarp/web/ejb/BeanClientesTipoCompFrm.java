/* 
 javabean para la entidad (Formulario): clientesTipoComp
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jun 14 10:37:10 ART 2007 
 
 Para manejar la pagina: clientesTipoCompFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanClientesTipoCompFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesTipoCompFrm.class);

	private String validar = "";

	private BigDecimal idtipocomp = new BigDecimal(-1);

	// Validar que sea entero
	private String tipomov_tc = "";

	private String descri_tc = "";

	private String cuotas_tc = "";

	private String vtacont_tc = "";

	private String st_stok_tc = "";

	private String st_prec_tc = "";

	private String st_remi_tc = "";

	private String comi_tc = "";

	private String imprime_tc = "";

	// Validar que sea entero
	private String bonif_tc = "";

	private String mod_mon_tc = "";

	private String ingb_tc = "";

	// Validar que sea entero
	private String contad_tc = "";

	// Validar que sea entero
	private String ctaiva_tc = "";

	// Validar que sea entero
	private String ctivani_tc = "";

	// Validar que sea entero
	private String ctgrava_tc = "";

	// Validar que sea entero
	private String ctexent_tc = "";

	private String ranking_tc = "";

	private String transpo_tc = "";

	private String bon_x_art = "";

	private String remdesp_tc = "";

	private String mod_con_tc = "";

	private String dere1_tc = "";

	private String reca1_tc = "";

	// Validar que sea entero
	private String ctare1_tc = "";

	private String recai1_tc = "";

	private String recgr1_tc = "";

	private String dere2_tc = "";

	private String reca2_tc = "";

	// Validar que sea entero
	private String ctare2_tc = "";

	private String recai2_tc = "";

	private String recgr2_tc = "";

	private String dere3_tc = "";

	private String reca3_tc = "";

	// Validar que sea entero
	private String ctare3_tc = "";

	private String recai3_tc = "";

	private String recgr3_tc = "";

	private String dere4_tc = "";

	private String reca4_tc = "";

	// Validar que sea entero
	private String ctare4_tc = "";

	private String recai4_tc = "";

	private String recgr4_tc = "";

	// Validar que sea entero
	private String centr1_tc = "";

	// Validar que sea entero
	private String centr2_tc = "";

	private String iva_x_art = "";

	private String jasper_tc = "";

	private String imp_int_tc = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClientesTipoCompFrm() {
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

			Clientes clientesTipoComp = Common.getClientes();
			this.descri_tc = this.descri_tc.trim().toUpperCase();

			if (this.accion.equalsIgnoreCase("alta")) {
				this.mensaje = clientesTipoComp.clientesTipoCompCreate(
						strVacioToBigDecimal(this.tipomov_tc), this.descri_tc,
						this.cuotas_tc, this.vtacont_tc, this.st_stok_tc,
						this.st_prec_tc, this.st_remi_tc, this.comi_tc,
						this.imprime_tc, strVacioToBigDecimal(this.bonif_tc),
						this.mod_mon_tc, this.ingb_tc,
						strVacioToBigDecimal(this.contad_tc),
						strVacioToBigDecimal(this.ctaiva_tc),
						strVacioToBigDecimal(this.ctivani_tc),
						strVacioToBigDecimal(this.ctgrava_tc),
						strVacioToBigDecimal(this.ctexent_tc), this.ranking_tc,
						this.transpo_tc, this.bon_x_art, this.remdesp_tc,
						this.mod_con_tc, this.dere1_tc, this.reca1_tc,
						strVacioToBigDecimal(this.ctare1_tc), this.recai1_tc,
						this.recgr1_tc, this.dere2_tc, this.reca2_tc,
						strVacioToBigDecimal(this.ctare2_tc), this.recai2_tc,
						this.recgr2_tc, this.dere3_tc, this.reca3_tc,
						strVacioToBigDecimal(this.ctare3_tc), this.recai3_tc,
						this.recgr3_tc, this.dere4_tc, this.reca4_tc,
						strVacioToBigDecimal(this.ctare4_tc), this.recai4_tc,
						this.recgr4_tc, strVacioToBigDecimal(this.centr1_tc),
						strVacioToBigDecimal(this.centr2_tc), this.iva_x_art,
						strVacioToBigDecimal(this.imp_int_tc), this.idempresa,
						this.usuarioalt);

			} else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clientesTipoComp.clientesTipoCompUpdate(
						this.idtipocomp, strVacioToBigDecimal(this.tipomov_tc),
						this.descri_tc, this.cuotas_tc, this.vtacont_tc,
						this.st_stok_tc, this.st_prec_tc, this.st_remi_tc,
						this.comi_tc, this.imprime_tc,
						strVacioToBigDecimal(this.bonif_tc), this.mod_mon_tc,
						this.ingb_tc, strVacioToBigDecimal(this.contad_tc),
						strVacioToBigDecimal(this.ctaiva_tc),
						strVacioToBigDecimal(this.ctivani_tc),
						strVacioToBigDecimal(this.ctgrava_tc),
						strVacioToBigDecimal(this.ctexent_tc), this.ranking_tc,
						this.transpo_tc, this.bon_x_art, this.remdesp_tc,
						this.mod_con_tc, this.dere1_tc, this.reca1_tc,
						strVacioToBigDecimal(this.ctare1_tc), this.recai1_tc,
						this.recgr1_tc, this.dere2_tc, this.reca2_tc,
						strVacioToBigDecimal(this.ctare2_tc), this.recai2_tc,
						this.recgr2_tc, this.dere3_tc, this.reca3_tc,
						strVacioToBigDecimal(this.ctare3_tc), this.recai3_tc,
						this.recgr3_tc, this.dere4_tc, this.reca4_tc,
						strVacioToBigDecimal(this.ctare4_tc), this.recai4_tc,
						this.recgr4_tc, strVacioToBigDecimal(this.centr1_tc),
						strVacioToBigDecimal(this.centr2_tc), this.iva_x_art,
						strVacioToBigDecimal(this.imp_int_tc), this.idempresa,
						this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clientesTipoComp
						.clientesTipoCompDelete(this.idtipocomp);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClientesTipoComp() {
		try {
			Clientes clientesTipoComp = Common.getClientes();
			List listClientesTipoComp = clientesTipoComp.getClientesTipoCompPK(
					this.idtipocomp, this.idempresa);
			Iterator iterClientesTipoComp = listClientesTipoComp.iterator();
			if (iterClientesTipoComp.hasNext()) {
				String[] uCampos = (String[]) iterClientesTipoComp.next();
				// TODO: Constructores para cada tipo de datos
				this.idtipocomp = new BigDecimal(uCampos[0]);
				this.tipomov_tc = uCampos[1] == null ? "" : uCampos[1];
				this.descri_tc = uCampos[2] == null ? "" : uCampos[2];
				this.cuotas_tc = uCampos[3] == null ? "" : uCampos[3];
				this.vtacont_tc = uCampos[4] == null ? "" : uCampos[4];
				this.st_stok_tc = uCampos[5] == null ? "" : uCampos[5];
				this.st_prec_tc = uCampos[6] == null ? "" : uCampos[6];
				this.st_remi_tc = uCampos[7] == null ? "" : uCampos[7];
				this.comi_tc = uCampos[8] == null ? "" : uCampos[8];
				this.imprime_tc = uCampos[9] == null ? "" : uCampos[9];
				this.bonif_tc = uCampos[10] == null ? "" : uCampos[10];
				this.mod_mon_tc = uCampos[11] == null ? "" : uCampos[11];
				this.ingb_tc = uCampos[12] == null ? "" : uCampos[12];
				this.contad_tc = uCampos[13] == null ? "" : uCampos[13];
				this.ctaiva_tc = uCampos[14] == null ? "" : uCampos[14];
				this.ctivani_tc = uCampos[15] == null ? "" : uCampos[15];
				this.ctgrava_tc = uCampos[16] == null ? "" : uCampos[16];
				this.ctexent_tc = uCampos[17] == null ? "" : uCampos[17];
				this.ranking_tc = uCampos[18] == null ? "" : uCampos[18];
				this.transpo_tc = uCampos[19] == null ? "" : uCampos[19];
				this.bon_x_art = uCampos[20] == null ? "" : uCampos[20];
				this.remdesp_tc = uCampos[21] == null ? "" : uCampos[21];
				this.mod_con_tc = uCampos[22] == null ? "" : uCampos[22];
				this.dere1_tc = uCampos[23] == null ? "" : uCampos[23];
				this.reca1_tc = uCampos[24] == null ? "" : uCampos[24];
				this.ctare1_tc = uCampos[25] == null ? "" : uCampos[25];
				this.recai1_tc = uCampos[26] == null ? "" : uCampos[26];
				this.recgr1_tc = uCampos[27] == null ? "" : uCampos[27];
				this.dere2_tc = uCampos[28] == null ? "" : uCampos[28];
				this.reca2_tc = uCampos[29] == null ? "" : uCampos[29];
				this.ctare2_tc = uCampos[30] == null ? "" : uCampos[30];
				this.recai2_tc = uCampos[31] == null ? "" : uCampos[31];
				this.recgr2_tc = uCampos[32] == null ? "" : uCampos[32];
				this.dere3_tc = uCampos[33] == null ? "" : uCampos[33];
				this.reca3_tc = uCampos[34] == null ? "" : uCampos[34];
				this.ctare3_tc = uCampos[35] == null ? "" : uCampos[35];
				this.recai3_tc = uCampos[36] == null ? "" : uCampos[36];
				this.recgr3_tc = uCampos[37] == null ? "" : uCampos[37];
				this.dere4_tc = uCampos[38] == null ? "" : uCampos[38];
				this.reca4_tc = uCampos[39] == null ? "" : uCampos[39];
				this.ctare4_tc = uCampos[40] == null ? "" : uCampos[40];
				this.recai4_tc = uCampos[41] == null ? "" : uCampos[41];
				this.recgr4_tc = uCampos[42] == null ? "" : uCampos[42];
				this.centr1_tc = uCampos[43] == null ? "" : uCampos[43];
				this.centr2_tc = uCampos[44] == null ? "" : uCampos[44];
				this.iva_x_art = uCampos[45] == null ? "" : uCampos[45];
				this.jasper_tc = uCampos[46] == null ? "" : uCampos[46];
				this.idempresa = new BigDecimal(uCampos[47]);
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClientesTipoComp()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clientesTipoCompAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
					if (this.cuotas_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar permite cuotas.";
						return false;
					}

					if (this.vtacont_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar venta al contado.";
						return false;
					}

					if (this.st_stok_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar mueve stock.";
						return false;
					}

					if (this.st_prec_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar si permite modificar precios de venta.";
						return false;
					}

					if (this.st_remi_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar imprime remito.";
						return false;
					}

					if (this.comi_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar afecta comisiones.";
						return false;
					}

					if (this.imprime_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar si imprime el comprobante.";
						return false;
					}

					if (this.mod_mon_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar permite modificar moneda.";
						return false;
					}

					if (this.ingb_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar afecta ingresos brutos.";
						return false;
					}

					if (this.ranking_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar va al ranking de clientes.";
						return false;
					}

					if (this.transpo_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar permite modificar transportista.";
						return false;
					}

					if (this.bon_x_art.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar pide bonificacion en articulo.";
						return false;
					}

					if (this.remdesp_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar imprime remito despacho.";
						return false;
					}

					if (this.mod_con_tc.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar modifica condicion de venta.";
						return false;
					}

					if (!Common.esEntero(this.bonif_tc)) {
						this.mensaje = "Es necesario ingresar cantidad de bonificaciones.";
						return false;
					}

					// Cuentas contables
					// ...............................................

					// Recargo Uno
					// ...............................................

					if (!this.dere1_tc.trim().equals("")) {

						if (this.ctare1_tc.trim().equals("")) {
							this.mensaje = "Es necesario seleccionar c.contable para recargo uno.";
							return false;
						}

						if (this.recai1_tc.trim().equals("")) {
							this.mensaje = "Es necesario seleccionar afecta impuestos para recargo uno.";
							return false;
						}

						if (this.recgr1_tc.trim().equals("")) {
							this.mensaje = "Es necesario seleccionar va al neto gravado para recargo uno.";
							return false;
						}

					} else {
						this.dere1_tc = this.dere1_tc.trim();
						this.ctare1_tc = this.ctare1_tc.trim();
						this.recai1_tc = this.recai1_tc.trim();
						this.recgr1_tc = this.recgr1_tc.trim();
					}

					// Recargo Dos
					// ...............................................

					if (!this.dere1_tc.trim().equals("")) {

						if (this.ctare2_tc.trim().equals("")) {
							this.mensaje = "Es necesario seleccionar c.contable para recargo dos.";
							return false;
						}

						if (this.recai2_tc.trim().equals("")) {
							this.mensaje = "Es necesario seleccionar afecta impuestos para recargo uno.";
							return false;
						}

						if (this.recgr2_tc.trim().equals("")) {
							this.mensaje = "Es necesario seleccionar va al neto gravado para recargo dos.";
							return false;
						}

					} else {
						this.dere2_tc = this.dere2_tc.trim();
						this.ctare2_tc = this.ctare2_tc.trim();
						this.recai2_tc = this.recai2_tc.trim();
						this.recgr2_tc = this.recgr2_tc.trim();
					}

					// Recargo tres
					// ...............................................

					if (!this.dere3_tc.trim().equals("")) {

						if (this.ctare3_tc.trim().equals("")) {
							this.mensaje = "Es necesario seleccionar c.contable para recargo tres.";
							return false;
						}

						if (this.recai3_tc.trim().equals("")) {
							this.mensaje = "Es necesario seleccionar afecta impuestos para recargo tres.";
							return false;
						}

						if (this.recgr3_tc.trim().equals("")) {
							this.mensaje = "Es necesario seleccionar va al neto gravado para recargo tres.";
							return false;
						}

					} else {
						this.dere3_tc = this.dere3_tc.trim();
						this.ctare3_tc = this.ctare3_tc.trim();
						this.recai3_tc = this.recai3_tc.trim();
						this.recgr3_tc = this.recgr3_tc.trim();
					}

					// Recargo Cuatro
					// ...............................................

					if (!this.dere4_tc.trim().equals("")) {

						if (this.ctare4_tc.trim().equals("")) {
							this.mensaje = "Es necesario seleccionar c.contable para recargo cuatro.";
							return false;
						}

						if (this.recai4_tc.trim().equals("")) {
							this.mensaje = "Es necesario seleccionar afecta impuestos para recargo cuatro.";
							return false;
						}

						if (this.recgr4_tc.trim().equals("")) {
							this.mensaje = "Es necesario seleccionar va al neto gravado para recargo cuatro.";
							return false;
						}

					} else {
						this.dere4_tc = this.dere4_tc.trim();
						this.ctare4_tc = this.ctare4_tc.trim();
						this.recai4_tc = this.recai4_tc.trim();
						this.recgr4_tc = this.recgr4_tc.trim();
					}

					if (this.iva_x_art.trim().equals("")) {
						this.mensaje = "Es necesario seleccionar iva segun articulo.";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClientesTipoComp();
				}
			}
		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	private BigDecimal strVacioToBigDecimal(String valor) {

		if (valor == null || valor.trim().equals("")) {
			valor = "0";
		}
		return new BigDecimal(valor);
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
	public BigDecimal getIdtipocomp() {
		return idtipocomp;
	}

	public void setIdtipocomp(BigDecimal idtipocomp) {
		this.idtipocomp = idtipocomp;
	}

	public String getTipomov_tc() {
		return tipomov_tc;
	}

	public void setTipomov_tc(String tipomov_tc) {
		this.tipomov_tc = tipomov_tc;
	}

	public String getDescri_tc() {
		return descri_tc;
	}

	public void setDescri_tc(String descri_tc) {
		this.descri_tc = descri_tc;
	}

	public String getCuotas_tc() {
		return cuotas_tc;
	}

	public void setCuotas_tc(String cuotas_tc) {
		this.cuotas_tc = cuotas_tc;
	}

	public String getVtacont_tc() {
		return vtacont_tc;
	}

	public void setVtacont_tc(String vtacont_tc) {
		this.vtacont_tc = vtacont_tc;
	}

	public String getSt_stok_tc() {
		return st_stok_tc;
	}

	public void setSt_stok_tc(String st_stok_tc) {
		this.st_stok_tc = st_stok_tc;
	}

	public String getSt_prec_tc() {
		return st_prec_tc;
	}

	public void setSt_prec_tc(String st_prec_tc) {
		this.st_prec_tc = st_prec_tc;
	}

	public String getSt_remi_tc() {
		return st_remi_tc;
	}

	public void setSt_remi_tc(String st_remi_tc) {
		this.st_remi_tc = st_remi_tc;
	}

	public String getComi_tc() {
		return comi_tc;
	}

	public void setComi_tc(String comi_tc) {
		this.comi_tc = comi_tc;
	}

	public String getImprime_tc() {
		return imprime_tc;
	}

	public void setImprime_tc(String imprime_tc) {
		this.imprime_tc = imprime_tc;
	}

	public String getBonif_tc() {
		return bonif_tc;
	}

	public void setBonif_tc(String bonif_tc) {
		this.bonif_tc = bonif_tc;
	}

	public String getMod_mon_tc() {
		return mod_mon_tc;
	}

	public void setMod_mon_tc(String mod_mon_tc) {
		this.mod_mon_tc = mod_mon_tc;
	}

	public String getIngb_tc() {
		return ingb_tc;
	}

	public void setIngb_tc(String ingb_tc) {
		this.ingb_tc = ingb_tc;
	}

	public String getContad_tc() {
		return contad_tc;
	}

	public void setContad_tc(String contad_tc) {
		this.contad_tc = contad_tc;
	}

	public String getCtaiva_tc() {
		return ctaiva_tc;
	}

	public void setCtaiva_tc(String ctaiva_tc) {
		this.ctaiva_tc = ctaiva_tc;
	}

	public String getCtivani_tc() {
		return ctivani_tc;
	}

	public void setCtivani_tc(String ctivani_tc) {
		this.ctivani_tc = ctivani_tc;
	}

	public String getCtgrava_tc() {
		return ctgrava_tc;
	}

	public void setCtgrava_tc(String ctgrava_tc) {
		this.ctgrava_tc = ctgrava_tc;
	}

	public String getCtexent_tc() {
		return ctexent_tc;
	}

	public void setCtexent_tc(String ctexent_tc) {
		this.ctexent_tc = ctexent_tc;
	}

	public String getRanking_tc() {
		return ranking_tc;
	}

	public void setRanking_tc(String ranking_tc) {
		this.ranking_tc = ranking_tc;
	}

	public String getTranspo_tc() {
		return transpo_tc;
	}

	public void setTranspo_tc(String transpo_tc) {
		this.transpo_tc = transpo_tc;
	}

	public String getBon_x_art() {
		return bon_x_art;
	}

	public void setBon_x_art(String bon_x_art) {
		this.bon_x_art = bon_x_art;
	}

	public String getRemdesp_tc() {
		return remdesp_tc;
	}

	public void setRemdesp_tc(String remdesp_tc) {
		this.remdesp_tc = remdesp_tc;
	}

	public String getMod_con_tc() {
		return mod_con_tc;
	}

	public void setMod_con_tc(String mod_con_tc) {
		this.mod_con_tc = mod_con_tc;
	}

	public String getDere1_tc() {
		return dere1_tc;
	}

	public void setDere1_tc(String dere1_tc) {
		this.dere1_tc = dere1_tc;
	}

	public String getReca1_tc() {
		return reca1_tc;
	}

	public void setReca1_tc(String reca1_tc) {
		this.reca1_tc = reca1_tc;
	}

	public String getCtare1_tc() {
		return ctare1_tc;
	}

	public void setCtare1_tc(String ctare1_tc) {
		this.ctare1_tc = ctare1_tc;
	}

	public String getRecai1_tc() {
		return recai1_tc;
	}

	public void setRecai1_tc(String recai1_tc) {
		this.recai1_tc = recai1_tc;
	}

	public String getRecgr1_tc() {
		return recgr1_tc;
	}

	public void setRecgr1_tc(String recgr1_tc) {
		this.recgr1_tc = recgr1_tc;
	}

	public String getDere2_tc() {
		return dere2_tc;
	}

	public void setDere2_tc(String dere2_tc) {
		this.dere2_tc = dere2_tc;
	}

	public String getReca2_tc() {
		return reca2_tc;
	}

	public void setReca2_tc(String reca2_tc) {
		this.reca2_tc = reca2_tc;
	}

	public String getCtare2_tc() {
		return ctare2_tc;
	}

	public void setCtare2_tc(String ctare2_tc) {
		this.ctare2_tc = ctare2_tc;
	}

	public String getRecai2_tc() {
		return recai2_tc;
	}

	public void setRecai2_tc(String recai2_tc) {
		this.recai2_tc = recai2_tc;
	}

	public String getRecgr2_tc() {
		return recgr2_tc;
	}

	public void setRecgr2_tc(String recgr2_tc) {
		this.recgr2_tc = recgr2_tc;
	}

	public String getDere3_tc() {
		return dere3_tc;
	}

	public void setDere3_tc(String dere3_tc) {
		this.dere3_tc = dere3_tc;
	}

	public String getReca3_tc() {
		return reca3_tc;
	}

	public void setReca3_tc(String reca3_tc) {
		this.reca3_tc = reca3_tc;
	}

	public String getCtare3_tc() {
		return ctare3_tc;
	}

	public void setCtare3_tc(String ctare3_tc) {
		this.ctare3_tc = ctare3_tc;
	}

	public String getRecai3_tc() {
		return recai3_tc;
	}

	public void setRecai3_tc(String recai3_tc) {
		this.recai3_tc = recai3_tc;
	}

	public String getRecgr3_tc() {
		return recgr3_tc;
	}

	public void setRecgr3_tc(String recgr3_tc) {
		this.recgr3_tc = recgr3_tc;
	}

	public String getDere4_tc() {
		return dere4_tc;
	}

	public void setDere4_tc(String dere4_tc) {
		this.dere4_tc = dere4_tc;
	}

	public String getReca4_tc() {
		return reca4_tc;
	}

	public void setReca4_tc(String reca4_tc) {
		this.reca4_tc = reca4_tc;
	}

	public String getCtare4_tc() {
		return ctare4_tc;
	}

	public void setCtare4_tc(String ctare4_tc) {
		this.ctare4_tc = ctare4_tc;
	}

	public String getRecai4_tc() {
		return recai4_tc;
	}

	public void setRecai4_tc(String recai4_tc) {
		this.recai4_tc = recai4_tc;
	}

	public String getRecgr4_tc() {
		return recgr4_tc;
	}

	public void setRecgr4_tc(String recgr4_tc) {
		this.recgr4_tc = recgr4_tc;
	}

	public String getCentr1_tc() {
		return centr1_tc;
	}

	public void setCentr1_tc(String centr1_tc) {
		this.centr1_tc = centr1_tc;
	}

	public String getCentr2_tc() {
		return centr2_tc;
	}

	public void setCentr2_tc(String centr2_tc) {
		this.centr2_tc = centr2_tc;
	}

	public String getIva_x_art() {
		return iva_x_art;
	}

	public void setIva_x_art(String iva_x_art) {
		this.iva_x_art = iva_x_art;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
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

	public String getJasper_tc() {
		return jasper_tc;
	}

	public void setJasper_tc(String jasper_tc) {
		this.jasper_tc = jasper_tc;
	}

	public String getImp_int_tc() {
		return imp_int_tc;
	}

	public void setImp_int_tc(String imp_int_tc) {
		this.imp_int_tc = imp_int_tc;
	}
}
