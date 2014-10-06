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

public class BeanMarketStockDetalle implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanMarketStockDetalle.class);

	private String agregar = "";

	private String codigo_st = "";

	private String alias_st = "";

	private String descrip_st = "";

	private String descri2_st = "";

	private Double cost_pp_st = Double.valueOf("0");

	private Double precipp_st = Double.valueOf("0");

	private Double cost_uc_st = Double.valueOf("0");

	private Double ultcomp_st = Double.valueOf("0");

	private Double cost_re_st = Double.valueOf("0");

	private Double reposic_st = Double.valueOf("0");

	private String nom_com_st = "";

	private String d_nom_com_st = "";

	private String grupo_st = "";

	private String d_grupo_st = "";

	private Double cantmin_st = Double.valueOf("0");

	private String unimed_st = "";

	private String d_unimed_st = "";

	private Double bonific_st = Double.valueOf("0");

	private Double impint_st = Double.valueOf("0");

	private Double impcant_st = Double.valueOf("0");

	private String cuencom_st = "";

	private String cuenven_st = "";

	private String cuenve2_st = "";

	private String cuencos_st = "";

	private String cuenaju_st = "";

	private String inventa_st = "";

	private String proveed_st = "";

	private String d_proveed_st = "";

	private String provart_st = "";

	private String id_indi_st = "";

	private String despa_st = "";

	private String marca_st = "";

	private Double cafecga_st = Double.valueOf("0");

	private String unialt1_st = "";

	private String d_unialt1_st = "";

	private String unialt2_st = "";

	private String d_unialt2_st = "";

	private String unialt3_st = "";

	private String d_unialt3_st = "";

	private String unialt4_st = "";

	private String d_unialt4_st = "";

	private Double factor1_st = Double.valueOf("0");

	private Double factor2_st = Double.valueOf("0");

	private Double factor3_st = Double.valueOf("0");

	private Double factor4_st = Double.valueOf("0");

	private String tipoiva_st = "";

	private String d_tipoiva_st = "";

	private String venta_st = "";

	private String compra_st = "";

	private BigDecimal esquema_st = BigDecimal.valueOf(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private BigDecimal precio = new BigDecimal(0);

	public BeanMarketStockDetalle() {
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

	private void getDatosStockstock() {
		try {
			Stock Stockstock = Common.getStock();
			List listStockstock = Stockstock.getStockStockMarketPK(this.codigo_st,
					this.idempresa);
			Iterator iterStockstock = listStockstock.iterator();
			if (iterStockstock.hasNext()) {
				String[] uCampos = (String[]) iterStockstock.next();
				// TODO: Constructores para cada tipo de datos

				this.codigo_st = uCampos[0];
				this.alias_st = uCampos[1];
				this.descrip_st = uCampos[2];
				this.descri2_st = uCampos[3];
				this.cost_pp_st = Double.valueOf(uCampos[4] == null ? "0"
						: uCampos[4]);
				this.precipp_st = Double.valueOf(uCampos[5] == null ? "0"
						: uCampos[5]);
				this.cost_uc_st = Double.valueOf(uCampos[6] == null ? "0"
						: uCampos[6]);
				this.ultcomp_st = Double.valueOf(uCampos[7] == null ? "0"
						: uCampos[7]);
				this.cost_re_st = Double.valueOf(uCampos[8] == null ? "0"
						: uCampos[8]);
				this.reposic_st = Double.valueOf(uCampos[9] == null ? "0"
						: uCampos[9]);
				this.nom_com_st = uCampos[10];
				this.d_nom_com_st = uCampos[11];
				this.grupo_st = uCampos[12];
				this.d_grupo_st = uCampos[13];
				this.cantmin_st = Double.valueOf(uCampos[14] == null ? "0"
						: uCampos[14]);
				this.unimed_st = uCampos[15];
				this.d_unimed_st = uCampos[16];
				this.bonific_st = Double.valueOf(uCampos[17] == null ? "0"
						: uCampos[17]);
				this.impint_st = Double.valueOf(uCampos[18] == null ? "0"
						: uCampos[18]);
				this.impcant_st = Double.valueOf(uCampos[19] == null ? "0"
						: uCampos[19]);
				this.cuencom_st = uCampos[20];
				this.cuenven_st = uCampos[21];
				this.cuenve2_st = uCampos[22];
				this.cuencos_st = uCampos[23];
				this.cuenaju_st = uCampos[24];
				this.inventa_st = uCampos[25];
				this.proveed_st = uCampos[26];
				this.d_proveed_st = uCampos[27];
				this.provart_st = uCampos[28];
				this.id_indi_st = uCampos[29];
				this.despa_st = uCampos[30];
				this.marca_st = uCampos[31];
				this.cafecga_st = Double.valueOf(uCampos[32] == null ? "0"
						: uCampos[32]);
				this.unialt1_st = uCampos[33];
				this.d_unialt1_st = uCampos[34];
				this.unialt2_st = uCampos[35];
				this.d_unialt2_st = uCampos[36];
				this.unialt3_st = uCampos[37];
				this.d_unialt3_st = uCampos[38];
				this.unialt4_st = uCampos[39];
				this.d_unialt4_st = uCampos[40];
				this.factor1_st = Double.valueOf(uCampos[41] == null ? "0"
						: uCampos[41]);
				this.factor2_st = Double.valueOf(uCampos[42] == null ? "0"
						: uCampos[42]);
				this.factor3_st = Double.valueOf(uCampos[43] == null ? "0"
						: uCampos[43]);
				this.factor4_st = Double.valueOf(uCampos[44] == null ? "0"
						: uCampos[44]);
				this.tipoiva_st = uCampos[45];
				this.d_tipoiva_st = uCampos[46];
				this.venta_st = uCampos[47];
				this.compra_st = uCampos[48];
				this.esquema_st = BigDecimal.valueOf(Long
						.parseLong(uCampos[49] == null ? "0" : uCampos[49]));
				} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosStockstock()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			this.getDatosStockstock();

			if (!this.agregar.equals("")) {

				HttpSession session = request.getSession();

				Hashtable htCarrito = session.getAttribute("htCarrito") == null ? new Hashtable()
						: (Hashtable) session.getAttribute("htCarrito");

				if (!htCarrito.containsKey(this.codigo_st)) {

					precio = new BigDecimal(this.precipp_st.doubleValue());
					
					precio = precio.round(MathContext.DECIMAL32);
					precio = precio.setScale(2, BigDecimal.ROUND_UP);
										htCarrito.put(this.codigo_st, new String[] {
							this.codigo_st, this.alias_st, this.descrip_st,
							precio.toString(), "1", precio.toString() });
					 
					
				
				}
				session.setAttribute("htCarrito", htCarrito);
				response.sendRedirect("marketCarrito.jsp");
			}	

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
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

	public String getAlias_st() {
		return alias_st;
	}

	public void setAlias_st(String alias_st) {
		this.alias_st = alias_st;
	}

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public String getDescri2_st() {
		return descri2_st;
	}

	public void setDescri2_st(String descri2_st) {
		this.descri2_st = descri2_st;
	}

	public Double getCost_pp_st() {
		return cost_pp_st;
	}

	public void setCost_pp_st(Double cost_pp_st) {
		this.cost_pp_st = cost_pp_st;
	}

	public Double getPrecipp_st() {
		return precipp_st;
	}

	public void setPrecipp_st(Double precipp_st) {
		this.precipp_st = precipp_st;
	}

	public Double getCost_uc_st() {
		return cost_uc_st;
	}

	public void setCost_uc_st(Double cost_uc_st) {
		this.cost_uc_st = cost_uc_st;
	}

	public Double getUltcomp_st() {
		return ultcomp_st;
	}

	public void setUltcomp_st(Double ultcomp_st) {
		this.ultcomp_st = ultcomp_st;
	}

	public Double getCost_re_st() {
		return cost_re_st;
	}

	public void setCost_re_st(Double cost_re_st) {
		this.cost_re_st = cost_re_st;
	}

	public Double getReposic_st() {
		return reposic_st;
	}

	public void setReposic_st(Double reposic_st) {
		this.reposic_st = reposic_st;
	}

	public String getNom_com_st() {
		return nom_com_st;
	}

	public void setNom_com_st(String nom_com_st) {
		this.nom_com_st = nom_com_st;
	}

	public String getD_nom_com_st() {
		return d_nom_com_st;
	}

	public void setD_nom_com_st(String d_nom_com_st) {
		this.d_nom_com_st = d_nom_com_st;
	}

	public String getGrupo_st() {
		return grupo_st;
	}

	public void setGrupo_st(String grupo_st) {
		this.grupo_st = grupo_st;
	}

	public Double getCantmin_st() {
		return cantmin_st;
	}

	public void setCantmin_st(Double cantmin_st) {
		this.cantmin_st = cantmin_st;
	}

	public String getUnimed_st() {
		return unimed_st;
	}

	public void setUnimed_st(String unimed_st) {
		this.unimed_st = unimed_st;
	}

	public String getD_unimed_st() {
		return d_unimed_st;
	}

	public void setD_unimed_st(String d_unimed_st) {
		this.d_unimed_st = d_unimed_st;
	}

	public Double getBonific_st() {
		return bonific_st;
	}

	public void setBonific_st(Double bonific_st) {
		this.bonific_st = bonific_st;
	}

	public Double getImpint_st() {
		return impint_st;
	}

	public void setImpint_st(Double impint_st) {
		this.impint_st = impint_st;
	}

	public Double getImpcant_st() {
		return impcant_st;
	}

	public void setImpcant_st(Double impcant_st) {
		this.impcant_st = impcant_st;
	}

	public String getCuencom_st() {
		return cuencom_st;
	}

	public void setCuencom_st(String cuencom_st) {
		this.cuencom_st = cuencom_st;
	}

	public String getCuenven_st() {
		return cuenven_st;
	}

	public void setCuenven_st(String cuenven_st) {
		this.cuenven_st = cuenven_st;
	}

	public String getCuenve2_st() {
		return cuenve2_st;
	}

	public void setCuenve2_st(String cuenve2_st) {
		this.cuenve2_st = cuenve2_st;
	}

	public String getCuencos_st() {
		return cuencos_st;
	}

	public void setCuencos_st(String cuencos_st) {
		this.cuencos_st = cuencos_st;
	}

	public String getCuenaju_st() {
		return cuenaju_st;
	}

	public void setCuenaju_st(String cuenaju_st) {
		this.cuenaju_st = cuenaju_st;
	}

	public String getInventa_st() {
		return inventa_st;
	}

	public void setInventa_st(String inventa_st) {
		this.inventa_st = inventa_st;
	}

	public String getProveed_st() {
		return proveed_st;
	}

	public void setProveed_st(String proveed_st) {
		this.proveed_st = proveed_st;
	}

	public String getD_proveed_st() {
		return d_proveed_st;
	}

	public void setD_proveed_st(String d_proveed_st) {
		this.d_proveed_st = d_proveed_st;
	}

	public String getProvart_st() {
		return provart_st;
	}

	public void setProvart_st(String provart_st) {
		this.provart_st = provart_st;
	}

	public String getId_indi_st() {
		return id_indi_st;
	}

	public void setId_indi_st(String id_indi_st) {
		this.id_indi_st = id_indi_st;
	}

	public String getDespa_st() {
		return despa_st;
	}

	public void setDespa_st(String despa_st) {
		this.despa_st = despa_st;
	}

	public String getMarca_st() {
		return marca_st;
	}

	public void setMarca_st(String marca_st) {
		this.marca_st = marca_st;
	}

	public Double getCafecga_st() {
		return cafecga_st;
	}

	public void setCafecga_st(Double cafecga_st) {
		this.cafecga_st = cafecga_st;
	}

	public String getUnialt1_st() {
		return unialt1_st;
	}

	public void setUnialt1_st(String unialt1_st) {
		this.unialt1_st = unialt1_st;
	}

	public String getD_unialt1_st() {
		return d_unialt1_st;
	}

	public void setD_unialt1_st(String d_unialt1_st) {
		this.d_unialt1_st = d_unialt1_st;
	}

	public String getUnialt2_st() {
		return unialt2_st;
	}

	public void setUnialt2_st(String unialt2_st) {
		this.unialt2_st = unialt2_st;
	}

	public String getD_unialt2_st() {
		return d_unialt2_st;
	}

	public void setD_unialt2_st(String d_unialt2_st) {
		this.d_unialt2_st = d_unialt2_st;
	}

	public String getUnialt3_st() {
		return unialt3_st;
	}

	public void setUnialt3_st(String unialt3_st) {
		this.unialt3_st = unialt3_st;
	}

	public String getD_unialt3_st() {
		return d_unialt3_st;
	}

	public void setD_unialt3_st(String d_unialt3_st) {
		this.d_unialt3_st = d_unialt3_st;
	}

	public String getUnialt4_st() {
		return unialt4_st;
	}

	public void setUnialt4_st(String unialt4_st) {
		this.unialt4_st = unialt4_st;
	}

	public String getD_unialt4_st() {
		return d_unialt4_st;
	}

	public void setD_unialt4_st(String d_unialt4_st) {
		this.d_unialt4_st = d_unialt4_st;
	}

	public Double getFactor1_st() {
		return factor1_st;
	}

	public void setFactor1_st(Double factor1_st) {
		this.factor1_st = factor1_st;
	}

	public Double getFactor2_st() {
		return factor2_st;
	}

	public void setFactor2_st(Double factor2_st) {
		this.factor2_st = factor2_st;
	}

	public Double getFactor3_st() {
		return factor3_st;
	}

	public void setFactor3_st(Double factor3_st) {
		this.factor3_st = factor3_st;
	}

	public Double getFactor4_st() {
		return factor4_st;
	}

	public void setFactor4_st(Double factor4_st) {
		this.factor4_st = factor4_st;
	}

	public String getTipoiva_st() {
		return tipoiva_st;
	}

	public void setTipoiva_st(String tipoiva_st) {
		this.tipoiva_st = tipoiva_st;
	}

	public String getD_tipoiva_st() {
		return d_tipoiva_st;
	}

	public void setD_tipoiva_st(String d_tipoiva_st) {
		this.d_tipoiva_st = d_tipoiva_st;
	}

	public String getVenta_st() {
		return venta_st;
	}

	public void setVenta_st(String venta_st) {
		this.venta_st = venta_st;
	}

	public String getCompra_st() {
		return compra_st;
	}

	public void setCompra_st(String compra_st) {
		this.compra_st = compra_st;
	}

	public BigDecimal getEsquema_st() {
		return esquema_st;
	}

	public void setEsquema_st(BigDecimal esquema_st) {
		this.esquema_st = esquema_st;
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

	public String getD_grupo_st() {
		return d_grupo_st;
	}

	public void setD_grupo_st(String d_grupo_st) {
		this.d_grupo_st = d_grupo_st;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}


}
