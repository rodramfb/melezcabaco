package ar.com.syswarp.ejb;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.Local;

@Local
public interface Proveedores {
	// public List getVariables() throws RemoteException;
	// retenciones
	public List getProveedoRetencionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProveedoRetencionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProveedoRetencionesOcuLOV(String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getProveedoRetencionesPK(BigDecimal idretencion,
			BigDecimal idempresa) throws RemoteException;

	public String proveedoRetencionesDelete(BigDecimal idretencion,
			BigDecimal idempresa) throws RemoteException;

	public String proveedoRetencionesCreate(String retencion,
			Double impor1_ret, Double impor2_ret, Double impor3_ret,
			Double impor4_ret, Double impor5_ret, Double impor6_ret,
			Double impor7_ret, Double impor8_ret, Double impor9_ret,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String proveedoRetencionesCreateOrUpdate(BigDecimal idretencion,
			String retencion, Double impor1_ret, Double impor2_ret,
			Double impor3_ret, Double impor4_ret, Double impor5_ret,
			Double impor6_ret, Double impor7_ret, Double impor8_ret,
			Double impor9_ret, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String proveedoRetencionesUpdate(BigDecimal idretencion,
			String retencion, Double impor1_ret, Double impor2_ret,
			Double impor3_ret, Double impor4_ret, Double impor5_ret,
			Double impor6_ret, Double impor7_ret, Double impor8_ret,
			Double impor9_ret, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// Condiciones de Pago
	public List getProveedoCondicioAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProveedoCondicioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProveedoCondicioPK(BigDecimal idcondicionpago,
			BigDecimal idempresa) throws RemoteException;

	public String proveedoCondicioDelete(BigDecimal idcondicionpago,
			BigDecimal idempresa) throws RemoteException;

	public String proveedoCondicioCreate(String condicionpago,
			BigDecimal cantidaddias, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String proveedoCondicioCreateOrUpdate(BigDecimal idcondicionpago,
			String condicionpago, BigDecimal cantidaddias, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String proveedoCondicioUpdate(BigDecimal idcondicionpago,
			String condicionpago, BigDecimal cantidaddias, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// PROVEEDORES PROVEEDORES
	public List getProveedoProveedAll(long limit, long offset, String orden,
			BigDecimal idempresa) throws RemoteException;

	public List getProveedoProveedOcu(long limit, long offset,
			String ocurrencia, String orden, BigDecimal idempresa)
			throws RemoteException;

	public List getProveedoProveedPK(BigDecimal idproveedor,
			BigDecimal idempresa) throws RemoteException;

	public String proveedoProveedDelete(BigDecimal idproveedor,
			BigDecimal idempresa) throws RemoteException;

	public String proveedoProveedCreate(BigDecimal idproveedor,
			String razon_social, String domicilio, BigDecimal idlocalidad,
			BigDecimal idprovincia, String postal, String contacto,
			String telefono, String cuit, String brutos, BigDecimal ctapasivo,
			BigDecimal ctaactivo1, BigDecimal ctaactivo2,
			BigDecimal ctaactivo3, BigDecimal ctaactivo4, BigDecimal ctaiva,
			BigDecimal ctaretiva, String letra_iva, BigDecimal ctadocumen,
			String ret_gan, BigDecimal idretencion1, BigDecimal idretencion2,
			BigDecimal idretencion3, BigDecimal idretencion4,
			BigDecimal idretencion5, BigDecimal ctades, String stock_fact,
			BigDecimal idcondicionpago, BigDecimal cent1, BigDecimal cent2,
			BigDecimal cent3, BigDecimal cent4, BigDecimal cents1,
			BigDecimal cents2, BigDecimal cents3, BigDecimal cents4,
			String email, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String proveedoProveedCreateOrUpdate(BigDecimal idproveedor,
			String razon_social, String domicilio, BigDecimal idlocalidad,
			BigDecimal idprovincia, String postal, String contacto,
			String telefono, String cuit, String brutos, BigDecimal ctapasivo,
			String ctaactivo1, String ctaactivo2, String ctaactivo3,
			String ctaactivo4, String ctaiva, String ctaretiva,
			String letra_iva, BigDecimal ctadocumen, String ret_gan,
			String idretencion1, String idretencion2, String idretencion3,
			String idretencion4, String idretencion5, BigDecimal ctades,
			String stock_fact, BigDecimal idcondicionpago, BigDecimal cent1,
			BigDecimal cent2, BigDecimal cent3, BigDecimal cent4,
			BigDecimal cents1, BigDecimal cents2, BigDecimal cents3,
			BigDecimal cents4, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String proveedoProveedUpdate(BigDecimal idproveedor,
			String razon_social, String domicilio, BigDecimal idlocalidad,
			BigDecimal idprovincia, String postal, String contacto,
			String telefono, String cuit, String brutos, BigDecimal ctapasivo,
			BigDecimal ctaactivo1, BigDecimal ctaactivo2,
			BigDecimal ctaactivo3, BigDecimal ctaactivo4, BigDecimal ctaiva,
			BigDecimal ctaretiva, String letra_iva, BigDecimal ctadocumen,
			String ret_gan, BigDecimal idretencion1, BigDecimal idretencion2,
			BigDecimal idretencion3, BigDecimal idretencion4,
			BigDecimal idretencion5, BigDecimal ctades, String stock_fact,
			BigDecimal idcondicionpago, BigDecimal cent1, BigDecimal cent2,
			BigDecimal cent3, BigDecimal cent4, BigDecimal cents1,
			BigDecimal cents2, BigDecimal cents3, BigDecimal cents4,
			String email, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// PROVINCIAS LOV
	public List getProveedoProvinciasOcuLOV(String ocurrencia)
			throws RemoteException;

	// CONDICIONES DE PAGO LOV
	public List getProveedoConddePagoOcuLOV(String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: proveedoMovProv Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jul 27 09:30:44 GMT-03:00 2006
	 */

	public String anularComprobanteProveedor(BigDecimal nrointerno,
			BigDecimal idproveedor, BigDecimal idempresa, String usuarioact)
			throws RemoteException, SQLException;

	public String modificarComprobanteProveedor(BigDecimal nrointerno,
			BigDecimal idproveedor, Timestamp fechamov, BigDecimal sucursal,
			BigDecimal comprob, BigDecimal tipomov, String tipomovs,
			BigDecimal importe, BigDecimal saldo, BigDecimal idcondicionpago,
			Timestamp fecha_subd, BigDecimal retoque, java.sql.Date fechavto,
			Hashtable htAsiento, Hashtable htArticulos, String usuarioact,
			BigDecimal idempresa, String observacionesContables)
			throws RemoteException, SQLException;

	public List getProveedoContProvImportes(BigDecimal compr_con,
			BigDecimal idempresa) throws RemoteException;

	public List getProveedoContProvTotalImportes(BigDecimal compr_con,
			BigDecimal idempresa) throws RemoteException;

	public List getProveedoMovProvAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProveedoMovProvOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProveedoMovProvPK(BigDecimal nrointerno, BigDecimal idempresa)
			throws RemoteException;

	public String proveedoMovProvDelete(BigDecimal nrointerno,
			BigDecimal idempresa) throws RemoteException;

	public String proveedoMovProvCreate(BigDecimal idproveedor,
			Timestamp fechamov, BigDecimal sucursal, BigDecimal comprob,
			BigDecimal tipomov, String tipomovs, Double importe, Double saldo,
			BigDecimal idcondicionpago, Timestamp fecha_subd,
			BigDecimal retoque, java.sql.Date fechavto, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String proveedoMovProvCreateOrUpdate(BigDecimal nrointerno,
			BigDecimal idproveedor, Timestamp fechamov, BigDecimal sucursal,
			BigDecimal comprob, BigDecimal tipomov, String tipomovs,
			Double importe, Double saldo, BigDecimal idcondicionpago,
			Timestamp fecha_subd, BigDecimal retoque, java.sql.Date fechavto,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String proveedoMovProvUpdate(BigDecimal nrointerno,
			BigDecimal idproveedor, Timestamp fechamov, BigDecimal sucursal,
			BigDecimal comprob, BigDecimal tipomov, String tipomovs,
			BigDecimal importe, BigDecimal saldo, BigDecimal idcondicionpago,
			Timestamp fecha_subd, BigDecimal retoque, java.sql.Date fechavto,
			String usuarioact, BigDecimal idempresa,
			String observacionesContables) throws RemoteException;

	public List getComprobantesProveedorAll(long limit, long offset,
			BigDecimal idproveedor, BigDecimal idempresa)
			throws RemoteException;

	public List getComprobantesProveedorOcu(long limit, long offset,
			BigDecimal idproveedor, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// Retorna el saldo de comprobantes de proveedor y total de cuenta
	// corriente.
	public List getComprobantesProveedorSaldoImporte(BigDecimal idproveedor,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: stockstock - desde lov articulos Copyrigth(r)
	 * utilizada para registrar captura de documentos sysWarp S.R.L. Fecha de
	 * creacion: Wed Aug 02 14:39:13 GMT-03:00 2006
	 */

	public List getProveedoArticulosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProveedoArticulosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProveedoArticulosPK(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * COMBO STOCK-DEPOSITOS
	 */

	public List getProveedoStockDepositosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidad(String entidad, BigDecimal idempresa)
			throws RemoteException;

	// getTotalEntidadOcu
	public long getTotalEntidadOcu(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// getTotalEntidadRelacion
	public long getTotalEntidadRelacion(String entidad, String[] campos,
			String[] ocurrencia, BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidadFiltro(String entidad, String filtro,
			BigDecimal idempresa) throws RemoteException;

	// Seteo - Recupero de variables
	public void setSetupVariable(String variable, String sistema, String valor,
			BigDecimal idempresa) throws RemoteException;

	public String getSetupVariable(String variable, String sistema,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: contableInfiPlanYYYY Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Aug 17 11:09:27 GMT-03:00 2006
	 * 
	 */
	public List getCuentasInfiPlanAll(long limit, long offset, int ejercicio,
			BigDecimal idempresa) throws RemoteException;

	public List getCuentasInfiPlanOcu(long limit, long offset,
			String ocurrencia, int ejercicio, BigDecimal idempresa)
			throws RemoteException;

	public List getCuentasInfiPlanPK(BigDecimal idcuenta, BigDecimal idempresa,
			int idejercicio) throws RemoteException;

	/**
	 * Metodos para la entidad: stockStock Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Thu Aug 24 13:28:06 GMT-03:00 2006
	 * 
	 */

	public List getStockArticuloPK(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodo para la entidad: proveedomovprov - proveedocontprov - stockstock -
	 * stockhis - stockstockbis; Copyrigth(r) sysWarp S.R.L. Fecha de creacion:
	 * Thu Aug 23 16:55:00 GMT-03:00 2006
	 * 
	 */

	// Verifica la cantidad de un determinado articulo en un deposito en
	// particular.
	// TODO: UNIFICAR 20061020 EJV
	// ATENCION !!!!:
	// --------------------------------------------------------------------
	// Metodos:...........................................................................
	// getExisteArticuloDeposito - getCantidadArticuloDeposito -
	// getValorSecuencia
	// -----------------------------------------------------------------------------------
	// duplicados en StockBean, solucion provisoria, para poder
	// generar transaccion con una sola coneccion
	/*
	 * public boolean getExisteArticuloDeposito(String articulo, BigDecimal
	 * deposito, BigDecimal idempresa) throws RemoteException;
	 */
	public boolean getExisteDocumento(BigDecimal idproveedor,
			BigDecimal sucursal, BigDecimal comprob, BigDecimal tipomov,
			BigDecimal idempresa) throws RemoteException;

	public boolean getExisteDocumentoUpd(BigDecimal nrointerno,
			BigDecimal idproveedor, BigDecimal sucursal, BigDecimal comprob,
			BigDecimal tipomov, BigDecimal idempresa) throws RemoteException;

	/*
	 * public BigDecimal getCantidadArticuloDeposito(String articulo, BigDecimal
	 * deposito, BigDecimal idempresa) throws RemoteException;
	 */

	public String capturaComprobantesProvCreate(BigDecimal idproveedor,
			Timestamp fechamov, BigDecimal sucursal, BigDecimal comprob,
			BigDecimal tipomov, String tipomovs, BigDecimal importe,
			BigDecimal saldo, BigDecimal idcondicionpago, Timestamp fecha_subd,
			BigDecimal retoque, java.sql.Date fechavto, int ejercicioactivo,
			String usuarioalt, Hashtable htAsiento, Hashtable articulos,
			BigDecimal idempresa, String obscontable) throws RemoteException,
			SQLException;

	public String capturaComprobantesProvContableCreate(BigDecimal idproveedor,
			Timestamp fechamov, BigDecimal sucursal, BigDecimal comprob,
			BigDecimal tipomov, String tipomovs, BigDecimal importe,
			BigDecimal saldo, BigDecimal idcondicionpago, Timestamp fecha_subd,
			BigDecimal retoque, java.sql.Date fechavto, int ejercicioactivo,
			String usuarioalt, String[] idConcepto, String[] idCuenta,
			String[] valor, String[] tipo, BigDecimal idempresa,
			String obscontable) throws RemoteException, SQLException;

	// Maximo Numero de Proveedor
	public BigDecimal getMaximoProveedor(BigDecimal idempresa)
			throws RemoteException;

	// proveedores oc Estados
	public List getProveedo_oc_estadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProveedo_oc_estadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProveedo_oc_estadosPK(BigDecimal idestadooc,
			BigDecimal idempresa) throws RemoteException;

	public String proveedo_oc_estadosDelete(BigDecimal idestadooc,
			BigDecimal idempresa) throws RemoteException;

	public String proveedo_oc_estadosCreate(String estadooc, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String proveedo_oc_estadosCreateOrUpdate(BigDecimal idestadooc,
			String estadooc, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String proveedo_oc_estadosUpdate(BigDecimal idestadooc,
			String estadooc, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: proveedo_Oc_Cabe Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Mar 28 09:44:55 CEST 2007
	 */

	public String proveedoOcCreate(BigDecimal idestadooc,
			BigDecimal idproveedor, Timestamp fechaoc,
			BigDecimal idcondicionpago, BigDecimal comision,
			String observaciones, BigDecimal recargo1, BigDecimal recargo2,
			BigDecimal recargo3, BigDecimal recargo4, BigDecimal bonific1,
			BigDecimal bonific2, BigDecimal bonific3, BigDecimal idmoneda,
			BigDecimal cotizacion, BigDecimal idtipoiva, BigDecimal totaliva,
			BigDecimal idgrupooc, BigDecimal idempresa, Hashtable htArticulos,
			String usuarioalt, BigDecimal codigo_dt,
			java.sql.Date fecha_entrega_prevista) throws RemoteException,
			SQLException;

	public List getProveedo_Oc_CabePK(BigDecimal id_oc_cabe,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: proveedo_Oc_Deta Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Mar 28 16:27:50 CEST 2007
	 */

	public String proveedo_Oc_DetaCreate(BigDecimal id_oc_cabe,
			String codigo_st, Timestamp fecha, BigDecimal renglon,
			BigDecimal precio, BigDecimal saldo, BigDecimal cantidad,
			BigDecimal bonific, BigDecimal codigo_md, BigDecimal cantuni,
			String entrega, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public List getProveedo_Oc_DetaOc(BigDecimal id_oc_cabe,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: proveedo_Oc_Grupos_Cotizaciones Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Thu Mar 29 15:29:34 ART 2007
	 */

	public List getProveedo_Oc_Grupos_CotizacionesActivasAll(long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getProveedo_Oc_Grupos_CotizacionesActivasOcu(long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// proveedores grupos cotizaciones
	public List getProveedo_oc_grupos_cotizacionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProveedo_oc_grupos_cotizacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProveedo_oc_grupos_cotizacionesPK(BigDecimal idgrupooc,
			BigDecimal idempresa) throws RemoteException;

	public String proveedo_oc_grupos_cotizacionesDelete(BigDecimal idgrupooc,
			BigDecimal idempresa) throws RemoteException;

	public String proveedo_oc_grupos_cotizacionesCreate(String grupooc,
			java.sql.Date fechadesde, java.sql.Date fechahasta,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String proveedo_oc_grupos_cotizacionesCreateOrUpdate(
			BigDecimal idgrupooc, String grupooc, java.sql.Date fechadesde,
			java.sql.Date fechahasta, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String proveedo_oc_grupos_cotizacionesUpdate(BigDecimal idgrupooc,
			String grupooc, java.sql.Date fechadesde, java.sql.Date fechahasta,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vproveedoOcEstado Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Apr 20 15:02:01 ART 2007
	 * 
	 */

	public List getVproveedoOcEstadoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getVproveedoOcEstadoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getVproveedoOcEstadoPK(BigDecimal id_oc_cabe,
			BigDecimal idempresa) throws RemoteException;

	public List getVproveedoOcEstadoPKGrupo(BigDecimal id_oc_cabe,
			BigDecimal idgrupooc, BigDecimal idempresa) throws RemoteException;

	public String proveedoOcCabeEstadoUpdate(BigDecimal id_oc_cabe,
			BigDecimal idestadooc, BigDecimal idgrupooc, String usuarioact,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public List getProveedoresCtaCteDetalleDesdeHasta(
			BigDecimal idproveedorDesde, BigDecimal idproveedorHasta,
			String tipo, java.sql.Timestamp fechadesde,
			java.sql.Timestamp fechahasta, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public List getProveedoresCtaCteDetalle(BigDecimal idproveedorDesde,
			String tipo, java.sql.Timestamp fechadesde,
			java.sql.Timestamp fechahasta, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public List getProveedoresVencimientos(String fechadesde,
			String fechahasta, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public List getProveedoresSaldoaFecha(String fechahasta,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public List getProveedoresSubdiarioCompras(BigDecimal idproveedorDesde,
			BigDecimal idproveedorHasta, String fechadesde, String fechahasta,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public List getProveedoresSubdiarioCompras(java.sql.Date fechadesde,
			java.sql.Date fechahasta, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public List getProveedoresSubdiarioPagos(String fechadesde,
			String fechahasta, BigDecimal idempresa) throws RemoteException,
			SQLException;

	// informe de movimientos de proveedores
	public List getProveedoInformeMovimientos(long limit, long offset,
			String idproveedordesde, String idproveedorhasta,
			Timestamp fechadesde, Timestamp fechahasta, BigDecimal idempresa)
			throws RemoteException;

	// informe de Aplicaciones
	public List getInformeAplicaciones(long limit, long offset,
			String idproveedordesde, String idproveedorhasta,
			Timestamp fechadesde, BigDecimal idempresa) throws RemoteException;

	// informe de compras por proveedores
	public List getProveedoInformeComprasporProveedores(long limit,
			long offset, String idproveedordesde, String idproveedorhasta,
			Timestamp fechadesde, Timestamp fechahasta, BigDecimal idempresa)
			throws RemoteException;

	public List getArriboOCDeposito(String codigo_dt, String fechadesde,
			String fechahasta, BigDecimal idEstadoOC, BigDecimal idempresa)
			throws RemoteException;

	public List getProveedoEstadosOcuLOV(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getProveedoresEstadosOCLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String usuario)
			throws RemoteException;

	public List getProveedoresEstadosOCLovAll(long limit, long offset,
			BigDecimal idempresa, String usuario) throws RemoteException;

	public List getProveedoresAplicaciones(BigDecimal idproveedorDesde,
			String tipo, java.sql.Timestamp fechadesde,
			java.sql.Timestamp fechahasta, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: proveedoMovProv Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jan 24 14:30:44 GMT-03:00 2007
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getLovCajaMovProvAll(long limit, long offset,
			BigDecimal idproveedor, String tipomovIN, BigDecimal idempresa)
			throws RemoteException;

	public List getLovCajaMovProvOcu(long limit, long offset,
			BigDecimal idproveedor, String tipomovIN, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getArriboOCAprobadas(BigDecimal idempresa)
			throws RemoteException;

	public long GetDevuelvoCuentaContable(BigDecimal idempresa,
			BigDecimal Ejercicio, BigDecimal cuenta) throws RemoteException;

	public long GetDevuelvoCuentaContable2(BigDecimal idempresa,
			BigDecimal Ejercicio, String cuenta) throws RemoteException;

	public List getTipoCompCombo(BigDecimal idempresa) throws RemoteException;

	public List getConceptos4Tipocomp(String letra, BigDecimal idtipocomp,
			BigDecimal ejercicio, BigDecimal idempresa) throws RemoteException;

	// abm proveedoconceptoscontables fgp 21/12/09
	public List getProveedoConceptoscontablesAll(long limit, long offset,
			BigDecimal idempresa, BigDecimal ejercicio) throws RemoteException;

	public List getProveedoConceptoscontablesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, BigDecimal ejercicio)
			throws RemoteException;

	public List getProveedoConceptoscontablesPK(BigDecimal idconcepto,
			BigDecimal idempresa) throws RemoteException;

	public String ProveedoConceptoscontablesDelete(BigDecimal idconcepto,
			BigDecimal idempresa) throws RemoteException;

	public String ProveedoConceptoscontablesCreate(BigDecimal idcuenta,
			BigDecimal orden, String letra, BigDecimal idtipocomp, String tipo,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String ProveedoConceptoscontablesUpdate(BigDecimal idconcepto,
			BigDecimal idcuenta, BigDecimal orden, String letra,
			BigDecimal idtipocomp, String tipo, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public List getProveedoConsultaCtaCteLovValoresAll(long limit, long offset,
			BigDecimal nrocomprobante, BigDecimal idempresa)
			throws RemoteException;

	public List getProveedoConsultaCtaCteLovAplicacionesAll(long limit,
			long offset, BigDecimal nrocomprobante, BigDecimal idempresa)
			throws RemoteException;

	// 20111012 - CNI - EJV - Comprobantes especiales -->

	public List getContabletipificacion() throws RemoteException;

	public List getCuentasInfiPlan2All(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCuentasInfiPlan2Ocu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public String getTipificacionContable(String idtipo) throws RemoteException;

	public String capturaComprobantesProvContableCreate(BigDecimal idproveedor,
			Timestamp fechamov, BigDecimal sucursal, BigDecimal comprob,
			BigDecimal tipomov, String tipomovs, BigDecimal importe,
			BigDecimal saldo, BigDecimal idcondicionpago, Timestamp fecha_subd,
			BigDecimal retoque, java.sql.Date fechavto, int ejercicioactivo,
			String usuarioalt, Hashtable htPlanesContables,
			BigDecimal idempresa, String obscontable) throws RemoteException,
			SQLException;

	// <--

	/**
	 * Metodos para la entidad: proveedoPlantilladocEsp Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Aug 07 11:00:12 ART 2013
	 */

	public List getProveedoPlantilladocEspActivas(BigDecimal idempresa)
			throws RemoteException;

}
