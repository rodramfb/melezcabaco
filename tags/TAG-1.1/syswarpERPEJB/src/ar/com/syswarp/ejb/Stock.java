package ar.com.syswarp.ejb;

import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import javax.ejb.Local;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.io.InputStream;
import java.math.*;

import javax.ejb.Local;

@Local
public interface Stock {
	public String getMarketIdlista() throws RemoteException;

	// getTotalEntidad
	public long getTotalEntidad(String entidad, BigDecimal idempresa)
			throws RemoteException;

	// getTotalEntidadOcu
	public long getTotalEntidadOcu(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidadFiltro(String entidad, String filtro,
			BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidadFitroOcu(String entidad, String filtro,
			String[] campos, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// getTotalEntidadRelacion
	public long getTotalEntidadRelacion(String entidad, String[] campos,
			String[] ocurrencia, BigDecimal idempresa) throws RemoteException;

	// STOCK FAMILIAS
	public List getStockfamiliasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getStockfamiliasMarketAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getStockfamiliasOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getStockfamiliasPK(BigDecimal codigo_fm, BigDecimal idempresa)
			throws RemoteException;

	public String StockfamiliasDelete(BigDecimal codigo_fm, BigDecimal idempresa)
			throws RemoteException;

	public String StockfamiliasCreate(String descrip_fm, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String StockfamiliasCreateOrUpdate(BigDecimal codigo_fm,
			String descrip_fm, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String StockfamiliasUpdate(BigDecimal codigo_fm, String descrip_fm,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// STOCK GRUPOS
	public List getStockgruposAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getStockgruposOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getStockgruposPK(BigDecimal codigo_gr, BigDecimal idempresa)
			throws RemoteException;

	public List getStockGruposXFamilia(BigDecimal codigo_fm,
			BigDecimal idempresa) throws RemoteException;

	public String StockgruposDelete(BigDecimal codigo_gr, BigDecimal idempresa)
			throws RemoteException;

	public String StockgruposCreate(String descrip_gr, String codigo_fm,
			String codigo_gr_pa, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String StockgruposCreateOrUpdate(BigDecimal codigo_gr,
			String descrip_gr, String codigo_fm, String codigo_gr_pa,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String StockgruposUpdate(BigDecimal codigo_gr, String descrip_gr,
			String codigo_fm, String codigo_gr_pa, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// LOV FAMILIAS
	public List getFamiliasLOV(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// LOV GRUPOS
	public List getGruposLOV(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// STOCK UBICACION
	public List getStockubicacioAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getStockubicacioOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getStockubicacioPK(BigDecimal codigo_ubi, BigDecimal idempresa)
			throws RemoteException;

	public String StockubicacioDelete(BigDecimal codigo_ubi,
			BigDecimal idempresa) throws RemoteException;

	public String StockubicacioCreate(BigDecimal deposi_ubi, String ubicac_ubi,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String StockubicacioCreateOrUpdate(BigDecimal codigo_ubi,
			BigDecimal deposi_ubi, String ubicac_ubi, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String StockubicacioUpdate(BigDecimal codigo_ubi,
			BigDecimal deposi_ubi, String ubicac_ubi, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// FIN ID EMPRESA NO SPEC VIOLATION
	// LOV DEPOSITOS
	public List getDepositoLOV(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// STOCK MEDIDAS
	public List getStockmedidasAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getStockmedidasOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getStockmedidasPK(BigDecimal codigo_md, BigDecimal idempresa)
			throws RemoteException;

	public String StockmedidasDelete(BigDecimal codigo_md, BigDecimal idempresa)
			throws RemoteException;

	public String StockmedidasCreate(String descrip_md, Double cantidad_md,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String StockmedidasCreateOrUpdate(BigDecimal codigo_md,
			String descrip_md, Double cantidad_md, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String StockmedidasUpdate(BigDecimal codigo_md, String descrip_md,
			Double cantidad_md, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// STOCK DEPOSITOS
	public List getStockdepositosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getStockdepositosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getStockdepositosPK(BigDecimal codigo_dt, BigDecimal idempresa)
			throws RemoteException;

	public String StockdepositosDelete(BigDecimal codigo_dt,
			BigDecimal idempresa) throws RemoteException;

	public String StockdepositosCreate(String descrip_dt, String direc_dt,
			String factura_dt, BigDecimal idlocalidad, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String StockdepositosCreateOrUpdate(BigDecimal codigo_dt,
			String descrip_dt, String direc_dt, String factura_dt,
			BigDecimal idlocalidad, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String StockdepositosUpdate(BigDecimal codigo_dt, String descrip_dt,
			String direc_dt, String factura_dt, BigDecimal idlocalidad,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// STOCK STOCK
	public List getStockstockAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getStockstockOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getStockstockPK(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	public List getStockStockMarketPK(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	public List getStockArticulosPK(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	// Recupera articulos para un determinado grupo.
	public List getStockStockXGrupo(long limit, long offset,
			BigDecimal grupo_st, BigDecimal idempresa) throws RemoteException;

	// Recupera todos los articulos que cumplen un criterio de busqueda.
	public List getStockStockMarketOcu(long limit, long offset,
			String ocurrencia, BigDecimal grupo_st, BigDecimal idempresa)
			throws RemoteException;

	public String StockstockDelete(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	public String StockstockCreate(String codigo_st, String alias_st,
			String descrip_st, String descri2_st, Double cost_pp_st,
			Double precipp_st, Double cost_uc_st, Double ultcomp_st,
			Double cost_re_st, Double reposic_st, String nom_com_st,
			String grupo_st, Double cantmin_st, String unimed_st,
			Double bonific_st, Double impint_st, Double impcant_st,
			String cuencom_st, String cuenven_st, String cuenve2_st,
			String cuencos_st, String cuenaju_st, String inventa_st,
			String proveed_st, String provart_st, String id_indi_st,
			String despa_st, String marca_st, Double cafecga_st,
			String unialt1_st, String unialt2_st, String unialt3_st,
			String unialt4_st, Double factor1_st, Double factor2_st,
			Double factor3_st, Double factor4_st, String tipoiva_st,
			String venta_st, String compra_st, BigDecimal esquema_st,
			String usuarioalt, BigDecimal idempresa, Double kilaje)
			throws RemoteException;

	public String StockstockCreateOrUpdate(String codigo_st, String alias_st,
			String descrip_st, String descri2_st, Double cost_pp_st,
			Double precipp_st, Double cost_uc_st, Double ultcomp_st,
			Double cost_re_st, Double reposic_st, String nom_com_st,
			String grupo_st, Double cantmin_st, String unimed_st,
			Double bonific_st, Double impint_st, Double impcant_st,
			String cuencom_st, String cuenven_st, String cuenve2_st,
			String cuencos_st, String cuenaju_st, String inventa_st,
			String proveed_st, String provart_st, String id_indi_st,
			String despa_st, String marca_st, Double cafecga_st,
			String unialt1_st, String unialt2_st, String unialt3_st,
			String unialt4_st, Double factor1_st, Double factor2_st,
			Double factor3_st, Double factor4_st, String tipoiva_st,
			String venta_st, String compra_st, BigDecimal esquema_st,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String StockstockUpdate(String codigo_st, String alias_st,
			String descrip_st, String descri2_st, Double cost_pp_st,
			Double precipp_st, Double cost_uc_st, Double ultcomp_st,
			Double cost_re_st, Double reposic_st, String nom_com_st,
			String grupo_st, Double cantmin_st, String unimed_st,
			Double bonific_st, Double impint_st, Double impcant_st,
			String cuencom_st, String cuenven_st, String cuenve2_st,
			String cuencos_st, String cuenaju_st, String inventa_st,
			String proveed_st, String provart_st, String id_indi_st,
			String despa_st, String marca_st, Double cafecga_st,
			String unialt1_st, String unialt2_st, String unialt3_st,
			String unialt4_st, Double factor1_st, Double factor2_st,
			Double factor3_st, Double factor4_st, String tipoiva_st,
			String venta_st, String compra_st, BigDecimal esquema_st,
			String usuarioact, BigDecimal idempresa, Double kilaje)
			throws RemoteException;

	// ESTO SE HIZO PARA TRAER EL LOV DE MEDIDAS
	public List getMedidasLOV(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// ESTO SE HIZO PARA TRAER LAS MONEDAS
	public List getMonedasLOV(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// ESTO SE HIZO PARA TRAER LOS PROVEEDORES
	public List getProveedoresLOV(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: vstockTotalDeposito Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Oct 24 16:08:01 GMT-03:00 2006
	 */

	public List getVstockTotalDepositoAll(long limit, long offset,
			BigDecimal idempresa, String usuario) throws RemoteException;

	public List getVstockTotalDepositoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String usuario)
			throws RemoteException;

	public List getVstockTotalDepositoPK(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * 
	 */

	public String[] callStockMovInternoCreate(Timestamp fechamov,
			String tipomov, BigDecimal num_cnt, BigDecimal sucursal,
			String tipo, String observaciones, Hashtable htArticulos,
			BigDecimal idcontadorcomprobante, Hashtable htSerieDespacho,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public String[] stockMovInternoCreate(Timestamp fechamov, String tipomov,
			BigDecimal num_cnt, BigDecimal sucursal, String tipo,
			String observaciones, Hashtable htArticulos,
			BigDecimal idcontadorcomprobante, Hashtable htSerieDespacho,
			Connection conn, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	/**
	 * IMPLEMENTACION DE MOVIMIENTOS POR DEFINICION DE ESQUEMAS ...............
	 * FECHA: 20071214 ........................................................
	 * AUTOR: EJV .............................................................
	 * 
	 */

	public String[] callStockMovDesarmadoProductosEsquema(BigDecimal idesquema,
			String codigo_st, BigDecimal codigo_dt, BigDecimal cantidad,
			BigDecimal idmotivodesarma, String observaciones, int recursivo,
			int ejercicioactivo, BigDecimal idcontadorcomprobante,
			String sistema_ms, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	// -----------------------------------------------------------------------

	public String[] callAnulaOrdenProduccion(BigDecimal idop,
			BigDecimal idesquema, String codigo_st, BigDecimal codigo_dt,
			BigDecimal cantidad, BigDecimal idmotivodesarma,
			String observaciones, int recursivo, int ejercicioactivo,
			BigDecimal idcontadorcomprobante, String sistema_ms,
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	/**
	 * -- MOVIMIENTOS DE ENTRADA ............................................
	 * Metodo para la entidad: stockhis - stockstockbis - stockmovstock -
	 * stockcontstock - stockanexos - stockremitos; Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Aug 23 16:55:00 GMT-03:00 2006 Modificacion:
	 * handAutocomit :::::::::::::::::::::::::::::::::::::::::::: Fecha:
	 * 20071214 ........................................................ Autor:
	 * EJV ............................................................. Motivo:
	 * No replicar el codigo del metodo y poder manejar la transaccion .......
	 * desde afuera....................................................
	 */
	public String[] stockMovEntradaCreate(BigDecimal codigo_anexo,
			String razon_social, String domicilio, String codigo_postal,
			BigDecimal idlocalidad, BigDecimal idprovincia, String cuit,
			String iibb, String sistema_ms, Timestamp fechamov,
			BigDecimal remito_ms, String tipomov, BigDecimal sucursal,
			String tipo, boolean remitopendiente, int ejercicioactivo,
			String observaciones, Hashtable htArticulos, Hashtable htCuentas,
			BigDecimal idcontadorcomprobante, boolean handAutocommit,
			Hashtable htSerieDespacho, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	/**
	 * -- MOVIMIENTOS DE SALIDA ...........................................
	 * Metodo para la entidad: stockhis - stockstockbis - stockmovstock -
	 * stockcontstock - stockanexos - stockremitos; Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Aug 23 16:55:00 GMT-03:00 2006 Modificacion:
	 * handAutocomit :::::::::::::::::::::::::::::::::::::::::::: Fecha:
	 * 20071214 ........................................................ Autor:
	 * EJV ............................................................. Motivo:
	 * No replicar el codigo del metodo y poder manejar la transaccion .......
	 * desde afuera....................................................
	 */
	public String[] stockMovSalidaCreate(BigDecimal codigo_anexo,
			String razon_social, String domicilio, String codigo_postal,
			BigDecimal idlocalidad, BigDecimal idprovincia, String cuit,
			String iibb, String sistema_ms, Timestamp fechamov,
			BigDecimal remito_ms, String tipomov, BigDecimal sucursal,
			String tipo, boolean remitopendiente, int ejercicioactivo,
			String observaciones, Hashtable htArticulos, Hashtable htCuentas,
			BigDecimal idcontadorcomprobante, boolean handAutocommit,
			Hashtable htSerieDespacho, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	// Verifica la cantidad de un determinado articulo en un deposito en
	// particular.
	// TODO: UNIFICAR 20061020 EJV
	// ATENCION !!!!:
	// --------------------------------------------------------------------
	// Metodos:...........................................................................
	// getExisteArticuloDeposito -
	// .......................................................
	// getValorSecuencia
	// -----------------------------------------------------------------------------------
	// duplicados en ProveedoresBean, solucion provisoria, para poder
	// generar transaccion con una sola coneccion.
	/*
	 * public boolean getExisteArticuloDeposito(String articulo, BigDecimal
	 * deposito, BigDecimal idempresa) throws RemoteException;
	 */
	// Verifica la cantidad de un determinado articulo en un deposito en
	// particular.
	// TODO: UNIFICAR 20061020 EJV
	// ATENCION !!!!:
	// --------------------------------------------------------------------
	// Metodos:...........................................................................
	// getExisteArticuloDeposito -
	// .......................................................
	// getValorSecuencia
	// -----------------------------------------------------------------------------------
	// duplicado en ProveedoresBean, solucion provisoria, para poder
	// generar transaccion con una sola coneccion.
	/*
	 * public BigDecimal getValorSequencia(String sequencia) throws
	 * RemoteException;
	 */
	/**
	 * Metodos para la entidad: lov_vanexosclieproveedo Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Fri Oct 27 14:24:30 GMT-03:00 2006
	 * 
	 */

	public List getLov_vanexosclieproveedoAll(long limit, long offset,
			String tipo, BigDecimal idempresa) throws RemoteException;

	public List getLov_vanexosclieproveedoOcu(long limit, long offset,
			String tipo, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getLov_vanexosclieproveedoPK(BigDecimal codigo_anexo,
			String tipo, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: globalLocalidades Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Oct 27 16:35:02 GMT-03:00 2006
	 */

	public List getGlobalLocalidadesAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalLocalidadesOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	/**
	 * Metodos para la entidad: contableInfiPlanYYYY Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Oct 30 12:11:00 GMT-03:00 2006
	 */
	public List getContableInfiPlanAll(long limit, long offset,
			int ejercicioactivo, BigDecimal idempresa) throws RemoteException;

	public List getContableInfiPlanOcu(long limit, long offset,
			int ejercicioactivo, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getContableInfiPlanPK(BigDecimal idcuenta, int ejercicioactivo,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: stockAnexos Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Wed Nov 01 10:52:18 GMT-03:00 2006
	 */

	// 20100910 - EJV
	// public String stockAnexosCreate(BigDecimal nint_ms_an,
	// BigDecimal codigo_anexo, String razon_social, String domicilio,
	// String codigo_postal, BigDecimal idlocalidad,
	// BigDecimal idprovincia, String cuit, String iibb,
	// String usuarioalt, BigDecimal idempresa) throws RemoteException;
	public List getStockAnexosNint(BigDecimal nint_ms_an, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: stockContStock Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Nov 01 11:08:52 GMT-03:00 2006
	 */

	// 20100910 - EJV
	// public String stockContStockCreate(BigDecimal nint_ms_cs,
	// BigDecimal cuenta_cs, BigDecimal importe_cs, String tipo_cs,
	// String sistema_cs, BigDecimal centr1_cs, BigDecimal centr2_cs,
	// String usuarioalt, BigDecimal idempresa) throws RemoteException;
	// 20100910 - EJV
	// public String stockContStockImporteUpdate(BigDecimal nint_ms_cs,
	// BigDecimal cuenta_cs, BigDecimal importe_cs, BigDecimal idempresa)
	// throws RemoteException;
	/**
	 * Metodos para la entidad: stockRemitos Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Nov 01 11:58:18 GMT-03:00 2006
	 */
	// 20100910 - EJV
	// public String stockRemitosCreate(BigDecimal nint_ms_rm,
	// BigDecimal remito_rm, Timestamp fecha_rm, String tipo_rm,
	// String tipomov_rm, BigDecimal codigo_rm, String marcado_rm,
	// BigDecimal sucurs_rm, String usuarioalt, BigDecimal idempresa)
	// throws RemoteException;
	/**
	 * Metodos para la entidad: vStockValorizado Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Nov 13 12:17:43 GMT-03:00 2006
	 */

	public List getVStockValorizadoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getVStockValorizadoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vMovArtFecha Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Nov 14 09:20:44 GMT-03:00 2006
	 */
	public List getVMovArtFechaAll(java.sql.Date fecha_ms, long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public long getTotalStockAgrupadoFecha(java.sql.Date fecha_ms,
			BigDecimal idempresa) throws RemoteException;

	public List getVMovArtFechaOcu(long limit, long offset,
			java.sql.Date fecha_ms, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public long getTotalStockAgrupadoFechaOcu(java.sql.Date fecha_ms,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vTotalArticuloDepositoFecha Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Nov 14 09:20:44 GMT-03:00 2006
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVTotalArtDepositoFechaAll(java.sql.Date fecha_ms,
			long limit, long offset, BigDecimal idempresa, String usuario)
			throws RemoteException;

	public long getTotalStockDepositoAgrupadoFecha(java.sql.Date fecha_ms,
			BigDecimal idempresa, String usuario) throws RemoteException;

	public List getVTotalArtDepositoFechaOcu(long limit, long offset,
			java.sql.Date fecha_ms, String ocurrencia, BigDecimal idempresa,
			String usuario) throws RemoteException;

	public long getTotalStockAgrupadoDepositoFechaOcu(java.sql.Date fecha_ms,
			String ocurrencia, BigDecimal idempresa, String usuario)
			throws RemoteException;

	// ****
	public List getProveedoresAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getProveedoresOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getStockfamiliasLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getStockfamiliasLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getStockgruposLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getStockgruposLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getStockdepositosLovAll(long limit, long offset,
			BigDecimal idempresa, String usuario) throws RemoteException;

	public List getStockdepositosLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String usuario)
			throws RemoteException;

	/**
	 * Metodos para la entidad: vRemitoInterno Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Fri Feb 16 16:20:02 GMT-03:00 2007
	 */

	public List getVRemitoInternoPK(BigDecimal remito_interno,
			BigDecimal idempresa) throws RemoteException;

	public List getReImprimeCambioDepositoAll(long limit, long offset,
			String user, BigDecimal idempresa) throws RemoteException;

	public List getReImprimeCambioDepositoOcu(long limit, long offset,
			String user, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getReImprimeMovEntradaSalidaAll(long limit, long offset,
			String inout, String user, BigDecimal idempresa)
			throws RemoteException;

	public List getReImprimeMovEntradaSalidaOcu(long limit, long offset,
			String inout, String user, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// total All globallocalidades
	public long gettotalgloballocalidadesAll() throws RemoteException;

	// total Ocu globallocalidades
	public long gettotalgloballocalidadesOcu(String[] campos, String ocurrencia)
			throws RemoteException;

	// -- metodos para reportes.
	public List getMovArticuloDepositoSaldoAnterior(String codigo_st,
			BigDecimal iddeposito, String fechadesde, String fechahasta,
			BigDecimal idempresa, String usuario) throws RemoteException;

	public List getExistencias4Deposito(BigDecimal iddeposito,
			BigDecimal idempresa) throws RemoteException;

	public List getMovArticuloDepositoDetalle(String codigo_st,
			BigDecimal iddeposito, String fechadesde, String fechahasta,
			BigDecimal idempresa, String usuario) throws RemoteException;

	public List getMovArticuloDepositoSaldoFinal(String codigo_st,
			BigDecimal iddeposito, String fechadesde, String fechahasta,
			BigDecimal idempresa, String usuario) throws RemoteException;

	// reporte 2
	public List getMovDepositoDetalle(BigDecimal iddeposito, String fechadesde,
			String fechahasta, BigDecimal idempresa, String usuario)
			throws RemoteException;

	// reporte 3
	public List getMovArticuloSaldoAnterior(String codigo_st,
			String fechadesde, String fechahasta, BigDecimal idempresa)
			throws RemoteException;

	public List getMovArticuloDetalle(String codigo_st, String fechadesde,
			String fechahasta, BigDecimal idempresa) throws RemoteException;

	public List getMovArticuloSaldoFinal(String codigo_st, String fechadesde,
			String fechahasta, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: stockMotivosDesarma Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Dec 17 13:21:38 ART 2007
	 */

	public List getStockMotivosDesarmaAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getStockMotivosDesarmaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getStockMotivosDesarmaPK(BigDecimal idmotivodesarma,
			BigDecimal idempresa) throws RemoteException;

	public String stockMotivosDesarmaDelete(BigDecimal idmotivodesarma)
			throws RemoteException;

	public String stockMotivosDesarmaCreate(String motivodesarma,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String stockMotivosDesarmaCreateOrUpdate(BigDecimal idmotivodesarma,
			String motivodesarma, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String stockMotivosDesarmaUpdate(BigDecimal idmotivodesarma,
			String motivodesarma, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: stockDesarmadoLog Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Dec 26 14:49:56 ART 2007
	 */

	public List getStockDesarmadoLogAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getStockDesarmadoLogFecha(long limit, long offset,
			Timestamp fechadesde, Timestamp fechahasta, BigDecimal idempresa)
			throws RemoteException;

	// carrito de compras
	// formas de pago
	public List getMarketformasdepagoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getMarketformasdepagoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getMarketformasdepagoPK(BigDecimal idformapago,
			BigDecimal idempresa) throws RemoteException;

	public String marketformasdepagoDelete(BigDecimal idformapago,
			BigDecimal idempresa) throws RemoteException;

	public String marketformasdepagoCreate(String formapago, String leyenda,
			String enviodatos, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String marketformasdepagoCreateOrUpdate(BigDecimal idformapago,
			String formapago, String leyenda, String enviodatos,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String marketformasdepagoUpdate(BigDecimal idformapago,
			String formapago, String leyenda, String enviodatos,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// depositos
	public List getMarketmarketdepositosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getMarketmarketdepositosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getMarketmarketdepositosPK(BigDecimal idmarketdeposito,
			BigDecimal idempresa) throws RemoteException;

	public String MarketmarketdepositosDelete(BigDecimal idmarketdeposito,
			BigDecimal idempresa) throws RemoteException;

	public String MarketmarketdepositosCreate(BigDecimal codigo_dt,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String MarketmarketdepositosCreateOrUpdate(
			BigDecimal idmarketdeposito, BigDecimal codigo_dt,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String MarketmarketdepositosUpdate(BigDecimal idmarketdeposito,
			BigDecimal codigo_dt, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodo GENERA Registro: marketRegistro - marketRegistroDireccion
	 * Copyrigth(r) sysWarp S.R.L. Fecha de creacion: Fri Mar 14 11:34:38 ART
	 * 2008
	 */

	public String marketCreateRegistroAndPedido(BigDecimal idcliente,
			String email, String pass, String activo, BigDecimal total,
			BigDecimal idformadepago, String comentarios, String obsentrega,
			Hashtable htDireccion, Hashtable htCarrito, BigDecimal idempresa,
			String usuarioalt) throws RemoteException, SQLException;

	/**
	 * Metodo ACTUALIZA DIRECCION - GENERA PEDIDO: marketRegistro -
	 * marketRegistroDireccion Copyrigth(r) sysWarp S.R.L. Fecha de creacion:
	 * Fri Mar 14 11:34:38 ART 2008
	 */

	public String marketUpdRegistroMakePedido(BigDecimal idcliente,
			String email, String pass, String activo, BigDecimal total,
			String comentarios, String obsentrega, Hashtable htDireccion,
			Hashtable htCarrito, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	/**
	 * Metodos para la entidad: marketRegistro Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Fri Mar 14 11:34:38 ART 2008
	 * 
	 */

	public List getMarketRegistroAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getMarketRegistroOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getMarketRegistroPK(BigDecimal idcliente, BigDecimal idempresa)
			throws RemoteException;

	public boolean isMarketRegistroValido(String email, String pass,
			BigDecimal idempresa) throws RemoteException;

	// Verificar existencia de usuario
	public boolean isMarketRegistroExistente(String email, BigDecimal idempresa)
			throws RemoteException;

	// Recuperar idcliente.
	public BigDecimal getMarketRegistroIdCliente(String email, String pass,
			BigDecimal idempresa) throws RemoteException;

	public String marketRegistroDelete(BigDecimal idcliente)
			throws RemoteException;

	public String marketRegistroCreate(String email, String pass,
			String activo, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String marketRegistroCreateOrUpdate(BigDecimal idcliente,
			String email, String pass, String activo, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String marketRegistroUpdate(BigDecimal idcliente, String email,
			String pass, String activo, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: marketRegistroDireccion Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Fri Mar 14 14:48:00 ART 2008
	 */

	public List getMarketRegistroDireccionAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getMarketRegistroDireccionOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getMarketRegistroDireccionPK(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	public List getMarketRegistroLastDireccion(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	public List getMarketRegistroDireccionPedido(BigDecimal idcliente,
			BigDecimal idpedicabe, BigDecimal idempresa) throws RemoteException;

	public String marketRegistroDireccionDelete(BigDecimal idcliente)
			throws RemoteException;

	public String marketRegistroDireccionCreate(BigDecimal idcliente,
			String nombre, String apellido, String empresa, String direccion,
			String ciudad, String provinciaestado, String codigopostal,
			String pais, String telefono, String fax, String tipodireccion,
			BigDecimal idpedicabe, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String marketRegistroDireccionCreateOrUpdate(BigDecimal idcliente,
			String nombre, String apellido, String empresa, String direccion,
			String ciudad, String provinciaestado, String codigopostal,
			String pais, String telefono, String fax, String tipodireccion,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String marketRegistroDireccionUpdate(BigDecimal idregistrodireccion,
			BigDecimal idcliente, String nombre, String apellido,
			String empresa, String direccion, String ciudad,
			String provinciaestado, String codigopostal, String pais,
			String telefono, String fax, String tipodireccion,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: marketPedidosCabe Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Mar 17 10:58:30 ART 2008
	 */

	public List getMarketPedidosCabeAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getMarketPedidosCabeOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getMarketPedidosCabePK(BigDecimal idpedicabe,
			BigDecimal idempresa) throws RemoteException;

	public String marketPedidosCabeDelete(BigDecimal idpedicabe)
			throws RemoteException;

	public String marketPedidosCabeCreate(BigDecimal idcliente,
			BigDecimal total, BigDecimal idestado, BigDecimal idformapago,
			String comentarios, String obsentrega, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String marketPedidosCabeCreateOrUpdate(BigDecimal idpedicabe,
			BigDecimal idcliente, Double total, BigDecimal idestado,
			BigDecimal idformapago, String comentarios, String obsentrega,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String marketPedidosCabeUpdate(BigDecimal idpedicabe,
			BigDecimal idcliente, Double total, BigDecimal idestado,
			BigDecimal idformapago, String comentarios, String obsentrega,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// Cambia el estado de un pedido.
	public String marketPedidosCabeSetEstado(BigDecimal idpedicabe,
			BigDecimal idestado, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: marketPedidosDeta Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Mar 17 10:59:11 ART 2008
	 */

	public List getMarketPedidosDetaAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getMarketPedidosDetaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getMarketPedidosDetaPK(BigDecimal idpedideta,
			BigDecimal idempresa) throws RemoteException;

	// Detalle para un pedido

	public List getMarketPedidosDetaXPedido(BigDecimal idpedicabe,
			BigDecimal idempresa) throws RemoteException;

	public String marketPedidosDetaDelete(BigDecimal idpedideta)
			throws RemoteException;

	public String marketPedidosDetaCreate(BigDecimal idpedicabe,
			String codigo_st, BigDecimal cantidad, BigDecimal precio,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String marketPedidosDetaCreateOrUpdate(BigDecimal idpedideta,
			BigDecimal idpedicabe, String codigo_st, BigDecimal cantidad,
			Double precio, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String marketPedidosDetaUpdate(BigDecimal idpedideta,
			BigDecimal idpedicabe, String codigo_st, BigDecimal cantidad,
			Double precio, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public List getArticulosPuntoMinimo(
	// String codigo_desde_st,
			// String codigo_hasta_st,
			BigDecimal iddepositodesde,
			// BigDecimal iddepositohasta,
			BigDecimal idempresa) throws RemoteException;

	public String getQueryDepositosAsociados(String usuario,
			BigDecimal idempresa) throws RemoteException;

	public boolean hasDepositosAsociados(String usuario, BigDecimal idempresa)
			throws RemoteException;

	// consulta stock familia grupo deposito
	public List getCosnultaFamiliaGrupoDeposito(BigDecimal codigo_fm,
			BigDecimal grupo_st, BigDecimal iddeposito, BigDecimal idempresa)
			throws RemoteException;

	public List getStockFamiliasGruposAll(long limit, long offset,
			BigDecimal codigo_fm, BigDecimal idempresa) throws RemoteException;

	public List getStockFamiliasGruposOcu(long limit, long offset,
			String ocurrencia, BigDecimal codigo_fm, BigDecimal idempresa)
			throws RemoteException;

	// fecha por deposito all
	public List getDepositoxFechaAll(java.sql.Date fecha_ms, long limit,
			long offset, BigDecimal idempresa, String usuario,
			BigDecimal codigo_dt) throws RemoteException;

	// fecha por deposito ocurrencia
	public List getDepositoFechaOcu(long limit, long offset,
			java.sql.Date fecha_ms, String ocurrencia, BigDecimal idempresa,
			String usuario, BigDecimal codigo_dt) throws RemoteException;

	public long getTotalStockDepositoFecha(java.sql.Date fecha_ms,
			BigDecimal idempresa, String usuario, BigDecimal codigo_dt)
			throws RemoteException;

	public long getTotalStocDepositoFechaOcu(java.sql.Date fecha_ms,
			String ocurrencia, BigDecimal idempresa, String usuario,
			BigDecimal codigo_dt) throws RemoteException;

	// Stock Iva
	public List getStockivaAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getStockivaOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getStockivaPK(BigDecimal idstockiva, BigDecimal idempresa)
			throws RemoteException;

	public String stockivaDelete(BigDecimal idstockiva, BigDecimal idempresa)
			throws RemoteException;

	public String stockivaCreate(String descripcion, Double porcentaje,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String stockivaUpdate(BigDecimal idstockiva, String descripcion,
			Double porcentaje, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// Consulta de Control de Disponible
	public List getConsultaControlDisponible(BigDecimal idempresa)
			throws RemoteException;

	// Consulta de Orden de Compra por producto
	public List getConsultaOrdendeCompraporProducto(BigDecimal idempresa,
			String producto) throws RemoteException;

	// esquema
	public List getConsultaEsquema(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	// stock
	public List getConsultaStock(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	// orden produccion
	public List getConsultaOrdenProduccion(String codigo_st,
			BigDecimal idempresa) throws RemoteException;

	// stock depositos
	public List getConsultaStockDepositos(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	// BIENES DE CAMBIO - CONSULTA DE PEDIDOS INVOLUCRADOS EN EL STOCK RESERVA
	public List getConsultaPedidosStockReserva(String codigo_st,
			String codigo_dt, java.sql.Date fdesde, java.sql.Date fhasta,
			String tipopedido, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: stockSerieDespacho Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Jan 19 14:13:10 GMT-03:00 2010
	 */

	public List getStockSerieDespachoAll(long limit, long offset,
			String codigo_st, BigDecimal codigo_dt, String id_indi_st,
			String despa_st, BigDecimal idempresa) throws RemoteException;

	public List getStockSerieDespachoOcu(long limit, long offset,
			String codigo_st, BigDecimal codigo_dt, String id_indi_st,
			String despa_st, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getStockSerieDespachoPK(BigDecimal idseriedespacho,
			BigDecimal idempresa) throws RemoteException;

	// descompromiso
	public List getArticuloDescompromiso(String articulo, BigDecimal deposito,
			BigDecimal idempresa) throws RemoteException;

	public String descomprometerUpdate(String articulo, BigDecimal deposito,
			BigDecimal idempresa, BigDecimal disponible, BigDecimal reservado,
			String usuarioact) throws RemoteException;

	// stock sin agrupar

	public List getStockStockMarketSinAgruparOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// getStockStockSinAgrupar

	public List getStockStockSinAgrupar(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: stockDepositosLockRegalos Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Nov 24 14:30:23 ART 2010
	 */

	public List getStockDepositosLockRegalosAll(long limit, long offset,
			BigDecimal codigo_dt, BigDecimal idempresa) throws RemoteException;

	public List getStockDepositosLockRegalosOcu(long limit, long offset,
			BigDecimal codigo_dt, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getStockDepositosLockRegalosPK(BigDecimal idlock,
			BigDecimal idempresa) throws RemoteException;

	public String stockDepositosLockRegalosDelete(BigDecimal idlock,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String stockDepositosLockRegalosCreate(BigDecimal codigo_dt,
			java.sql.Date fechadesde, java.sql.Date fechahasta,
			String comentarios, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String stockDepositosLockRegalosCreateOrUpdate(BigDecimal idlock,
			BigDecimal codigo_dt, java.sql.Date fechadesde,
			java.sql.Date fechahasta, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String stockDepositosLockRegalosUpdate(BigDecimal idlock,
			BigDecimal codigo_dt, java.sql.Date fechadesde,
			java.sql.Date fechahasta, String comentarios, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

}
