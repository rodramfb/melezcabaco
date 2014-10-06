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
import java.math.*;
import javax.ejb.Local;
@Local
public interface Tesoreria  {
	// public List getVariables() throws RemoteException;
	// CREARING
	public List getCajaclearingAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getCajaclearingOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getCajaclearingPK(BigDecimal idclearing, BigDecimal idempresa)
			throws RemoteException;

	public String CajaclearingDelete(BigDecimal idclearing, BigDecimal idempresa)
			throws RemoteException;

	public String CajaclearingCreate(String horas_cl, BigDecimal dias_cl,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String CajaclearingCreateOrUpdate(BigDecimal idclearing,
			String horas_cl, BigDecimal dias_cl, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String CajaclearingUpdate(BigDecimal idclearing, String horas_cl,
			BigDecimal dias_cl, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public long getTotalEntidadRelacion(String entidad, String[] campos,
			String[] ocurrencia, BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidadOcu(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidad(String entidad, BigDecimal idempresa)
			throws RemoteException;

	public long getTotalEntidadFiltro(String entidad, String filtro,
			BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidadRelacionOcu(String entidad,
			String[] camposFiltro, String[] valorFiltro, String[] camposOcu,
			String[] valorOcu, BigDecimal idempresa) throws RemoteException;

	// FERIADOS
	public List getCajaferiadosAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getCajaferiadosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	// Retorna Hashtable conteniendo los feriados como claves.

	public Hashtable getCajaFeriadosFecha(BigDecimal idempresa)
			throws RemoteException;

	public List getCajaferiadosPK(BigDecimal idferiado, BigDecimal idempresa)
			throws RemoteException;

	public String CajaferiadosDelete(BigDecimal idferiado, BigDecimal idempresa)
			throws RemoteException;

	public String CajaferiadosCreate(String feriado, java.sql.Date fecha_fer,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String CajaferiadosCreateOrUpdate(BigDecimal idferiado,
			String feriado, java.sql.Date fecha_fer, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String CajaferiadosUpdate(BigDecimal idferiado, String feriado,
			java.sql.Date fecha_fer, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// TIPO IDENTIFICADORES
	public List getCajatipoidentificadoresAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCajatipoidentificadoresOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCajatipoidentificadoresPK(BigDecimal idtipoidentificador,
			BigDecimal idempresa) throws RemoteException;

	public String CajatipoidentificadoresDelete(BigDecimal idtipoidentificador,
			BigDecimal idempresa) throws RemoteException;

	public String CajatipoidentificadoresCreate(String tipoidentificador,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String CajatipoidentificadoresCreateOrUpdate(
			BigDecimal idtipoidentificador, String tipoidentificador,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String CajatipoidentificadoresUpdate(BigDecimal idtipoidentificador,
			String tipoidentificador, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// FLUJO DE FONDOS
	public List getCajaflujosAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getCajaflujosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getCajaflujosPK(BigDecimal codigo_fl, BigDecimal idempresa)
			throws RemoteException;

	public String CajaflujosDelete(BigDecimal codigo_fl, BigDecimal idempresa)
			throws RemoteException;

	public String CajaflujosCreate(String descri_fl, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String CajaflujosCreateOrUpdate(BigDecimal codigo_fl,
			String descri_fl, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String CajaflujosUpdate(BigDecimal codigo_fl, String descri_fl,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vlov_Clientes Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Dec 12 14:43:41 GMT-03:00 2006
	 * 
	 */
	public List getVlov_ClientesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getVlov_ClientesOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: clientesCobradores Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Dec 12 15:32:43 GMT-03:00 2006
	 * 
	 */

	public List getLovClientesCobradoresAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getLovClientesCobradoresOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: clientesMovCli Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Dec 13 10:54:31 GMT-03:00 2006
	 * 
	 * 
	 */
	public List getLovClientesMovCliAll(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getLovClientesMovCliOcu(long limit, long offset,
			String ocurrencia, BigDecimal idcliente, BigDecimal idempresa)
			throws RemoteException;

	public List getLovClientesMovCliOne(BigDecimal cliente,
			BigDecimal sucursal, BigDecimal comprob, String tipomovs,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: cajaIdentificadores Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Dec 18 15:14:20 GMT-03:00 2006
	 * 
	 */

	public List getLovCajaIdentificadoresAll(long limit, long offset,
			String tipomov, String propio, BigDecimal idempresa)
			throws RemoteException;

	public List getLovCajaIdentificadoresOcu(long limit, long offset,
			String ocurrencia, String tipomov, String propio,
			BigDecimal idempresa) throws RemoteException;

	public List getLovCajaIdentificadoresPropioAll(long limit, long offset,
			String propio, BigDecimal idempresa) throws RemoteException;

	public List getLovCajaIdentificadoresPropioOcu(long limit, long offset,
			String ocurrencia, String propio, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Recuperar todos los registros correspondientes a identificadores de
	 * ENTRADA de Otros Movimientos de Caja.
	 * 
	 * @DATE:20090731
	 * @AUTOR: ejv
	 * */

	public List getLovCajaIdentOtrosMovINAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getLovCajaIdentOtrosMovINOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getLovCajaIdentOtrosMovOUTAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getLovCajaIdentOtrosMovOUTOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getLovCajaIdentificadoresConParAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getLovCajaIdentificadoresConParOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getLovCajaMovimientosCancelarAll(long limit, long offset,
			String cartera_mt, String tipcart_mt, Timestamp fecha_mt,
			BigDecimal idempresa) throws RemoteException;

	public List getLovCajaMovimientosCancelarOcu(long limit, long offset,
			String ocurrencia, String cartera_mt, String tipcart_mt,
			Timestamp fecha_mt, BigDecimal idempresa) throws RemoteException;

	// 20090803 EJV

	public List getLovCajaDocTercerosCancAll(long limit, long offset,
			String usado_mt, String cartera_mt, String tipcart_mt,
			Timestamp fecha_mt, BigDecimal idempresa) throws RemoteException;

	public List getLovCajaDocTercerosCancOcu(long limit, long offset,
			String ocurrencia, String usado_mt, String cartera_mt,
			String tipcart_mt, Timestamp fecha_mt, BigDecimal idempresa)
			throws RemoteException;

	public List getLovCajaIdentificadoresOne(String ididentificador,
			BigDecimal idempresa) throws RemoteException;

	public List getLovCajaIdentificadoresPropioOne(String identificador,
			BigDecimal ejercicio, BigDecimal idempresa) throws RemoteException;

	public List getLovCajaContraPartidaOne(String identificador,
			BigDecimal ejercicio, BigDecimal idempresa) throws RemoteException;

	public List getLovCajaMovimientosCancelarOne(BigDecimal idmovteso,
			BigDecimal idempresa) throws RemoteException;

	public List getLovCajaDocIIICancelarOne(BigDecimal idmovteso,
			BigDecimal idempresa) throws RemoteException;

	public List getRetencionesProveedor(BigDecimal idproveedor,
			BigDecimal ejercicio, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: contableInfiPlan Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Dec 21 12:03:24 GMT-03:00 2006
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getLovContableInfiPlanAll(long limit, long offset,
			int ejercicioActivo, BigDecimal idempresa) throws RemoteException;

	public List getLovContableInfiPlanOcu(long limit, long offset,
			String ocurrencia, int ejercicioActivo, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: clientesClientes Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Dec 29 09:29:05 GMT-03:00 2006
	 */

	public List getClientesClientesPK(BigDecimal idcliente, BigDecimal idempresa)
			throws RemoteException;

	/*
	 * INICIA METODOS PARA GENERARAR COBRANZAS POR CLIENTES
	 */

	public String cobranzasMovClienteCreate(BigDecimal idcliente,
			BigDecimal idcobrador, java.sql.Date fechamov,
			BigDecimal totalCobranza, Hashtable htComprobantes,
			Hashtable htIdentificadores, Hashtable htIdentificadoresIngesos,
			String observaciones, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	/**
	 * INICIA METODOS PARA GENERARAR INGRESOS DIRECTOS NO ASOCIADOS A CLIENTES
	 * TODO: caja - auxteso : no existe en el modelo nuevo.
	 */

	public String cobranzasIngresosDirectosCreate(
			Hashtable htIdentificadoresIngresos,
			Hashtable htIdentificadoresContrapartida,
			Hashtable htMovimientosCancelar, java.sql.Date fechamov,
			String observaciones, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	/**
	 * 
	 */

	/**
	 * 20090513 INICIA METODOS PARA ELIMINAR - ANULAR COBRANZAS POR CLIENTES /
	 * ING. DIRECTOS TODO: caja - auxteso : no existe en el modelo nuevo.
	 */

	public String cobranzasCajaMovTesoAnular(BigDecimal sucursal,
			BigDecimal comprobante, java.sql.Timestamp fechateso,
			BigDecimal idcliente, String usuarioact, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public String[] getIdentificadorTipoPropietario(String identificador,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * INICIA METODOS PARA GENERAR OTROS MOVIMIENTOS TODO: caja - auxteso : no
	 * existe en el modelo nuevo.
	 * 
	 */

	public String cajaOtrosMovimientosCreate(BigDecimal idproveedor,
			BigDecimal totalMovimiento, Hashtable htIdentificadoresIngresos,
			Hashtable htOMDocTercerosCancelarIN,
			Hashtable htIdentificadoresContrapartida,
			Hashtable htMovimientosCancelar, String tipodoc,
			java.sql.Date fechamov, String observaciones, String usuarioalt,
			BigDecimal idempresa) throws RemoteException, SQLException;

	/**
	 * Metodos para la entidad: cajaMovTeso Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Thu Dec 28 16:28:32 GMT-03:00 2006
	 */

	public String cajaMovTesoCreate(String cartera_mt, BigDecimal comprob_mt,
			Timestamp fechamo_mt, String tipomov_mt, String tipcart_mt,
			BigDecimal nroint_mt, String detalle_mt, BigDecimal nrodoc_mt,
			Timestamp fecha_mt, BigDecimal idclearing, Timestamp fclear_mt,
			BigDecimal impori_mt, BigDecimal importe_mt, String deposi_mt,
			BigDecimal idmoneda, String unamone_mt, BigDecimal cambio_mt,
			String movsal_mt, BigDecimal sucsal_mt, BigDecimal nrosal_mt,
			BigDecimal cuenta_mt, BigDecimal idcliente, BigDecimal tipo_mt,
			String usado_mt, String anulada_mt, BigDecimal idcencosto,
			BigDecimal idcencosto1, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	// Recuperar registro por primary key

	public List getCajaMovTesoPK(BigDecimal idmovteso, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: clientesMovCli Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Dec 28 16:47:10 GMT-03:00 2006
	 * 
	 */
	// public String clientesMovCliCreate(BigDecimal cliente, Timestamp
	// fechamov,
	// BigDecimal sucursal, BigDecimal comprob, BigDecimal comprob_has,
	// BigDecimal tipomov, String tipomovs, BigDecimal saldo,
	// BigDecimal importe, BigDecimal cambio, BigDecimal moneda,
	// String unamode, String tipocomp, BigDecimal condicion,
	// BigDecimal nrointerno, BigDecimal com_venta, BigDecimal com_cobra,
	// BigDecimal com_vende, String anulada, BigDecimal retoque,
	// BigDecimal expreso, BigDecimal sucucli, BigDecimal remito,
	// BigDecimal credito, String observaciones,
	// // 20110622 - EJV - Factuaracion FE-CF-MA -->
	// String condicionletra,
	// // <--
	// String usuarioalt, BigDecimal idempresa) throws RemoteException;
	/**
	 * Metodos para la entidad: clientesCancClie Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Dec 28 17:02:06 GMT-03:00 2006
	 */

	// 20120921 - EJV -->
	// public String clientesCancClieCreate(BigDecimal comp_canc,
	// BigDecimal comp_q_can, BigDecimal importe, Timestamp fecha,
	// String usuarioalt, BigDecimal idempresa) throws RemoteException;
	/**
	 * Metodos para la entidad: cajaAplicaci Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Dec 28 17:08:03 GMT-03:00 2006
	 */

	// public String cajaAplicaciCreate(String tipomov_ap, BigDecimal
	// comprob_ap,
	// BigDecimal nrointe_ap, String anexo_ap, BigDecimal sucursa_ap,
	// String usuarioalt, BigDecimal idempresa) throws RemoteException;
	// <--
	/**
	 * Metodos para la entidad: cajaSaldoBco Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Mon Jan 15 17:36:17 GMT-03:00 2007
	 * 
	 */
	public String cajaSaldoBcoCreateOrUpdate(String banco, java.sql.Date fecha,
			BigDecimal saldo_cont, BigDecimal saldo_disp, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vCajaMovTeso Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Jan 16 09:31:12 GMT-03:00 2007
	 * 
	 */

	// public String cajaSaldaMovimientosTeso(BigDecimal idmovteso,
	// String movsal_mt, BigDecimal sucsal_mt, BigDecimal nrosal_mt,
	// String usado_mt, String deposi_mt, String usuarioact,
	// BigDecimal idempresa) throws RemoteException;
	
	/**
	 * Metodo para recuperar totales por cartera.
	 * 
	 */

	public BigDecimal getTotalCartera(String cartera_mt, String tipcart_mt,
			java.sql.Date fecha_mt, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: clientesContCli Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jan 18 10:29:30 GMT-03:00 2007
	 */
	public String clientesContCliCreate(BigDecimal cuenta_con,
			BigDecimal impor_con, String nroiva_con, BigDecimal nroint_con,
			String centr1_con, String centr2_con, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: clientesTablaIva Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jan 18 12:43:16 GMT-03:00 2007
	 * 
	 */

	public List getClientesTablaIvaPK(BigDecimal idtipoiva, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: clientesVendedor Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Jan 19 10:25:04 GMT-03:00 2007
	 */

	public List getClientesVendedorPK(BigDecimal idvendedor,
			BigDecimal idempresa) throws RemoteException;

	/*
	 * FINALIZA METODOS PARA GENERARAR COBRANZAS POR CLIENTES
	 */

	/**
	 * Metodos para la entidad: proveedoMovProv Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jan 24 14:30:44 GMT-03:00 2007
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getLovCajaMovProvAll(long limit, long offset,
			BigDecimal idproveedor, BigDecimal idempresa)
			throws RemoteException;

	public List getLovCajaMovProvOcu(long limit, long offset,
			BigDecimal idproveedor, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getLovCajaMovProvPK(BigDecimal nrointerno, BigDecimal idempresa)
			throws RemoteException;

	//

	public List getLovCajaIdentificaSalidaPagosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getLovCajaIdentificaSalidaPagosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	//

	public List getLovCajaIdentificaEntradaPagosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getLovCajaIdentificaEntradaPagosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: proveedoMovProv Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Jan 30 14:07:49 GMT-03:00 2007
	 * 
	 */

	public String proveedoMovProvCreate(BigDecimal nrointerno,
			BigDecimal idproveedor, Timestamp fechamov, BigDecimal sucursal,
			BigDecimal comprob, BigDecimal tipomov, String tipomovs,
			BigDecimal importe, BigDecimal saldo, BigDecimal idcondicionpago,
			Timestamp fecha_subd, BigDecimal retoque, java.sql.Date fechavto,
			String observaciones, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String proveedoMovProvUpdateSaldo(BigDecimal nrointerno,
			BigDecimal saldo, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: proveedoCancProv Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Jan 30 14:40:17 GMT-03:00 2007
	 */

	public String proveedoMovProvAplicacionesCreate(BigDecimal nrointerno_canc,
			BigDecimal nrointerno_q_can, BigDecimal importe, String usuarioalt,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public String proveedoCancProvCreateOrUpdate(BigDecimal nrointerno_canc,
			BigDecimal nrointerno_q_can, BigDecimal importe, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * INICIA METODOS PARA GENERARAR PAGOS POR PROVEEDOR TODO: caja - auxteso :
	 * 
	 */

	public String[] pagosMovProveedorCreate(BigDecimal idproveedor,
			java.sql.Date fechamov, BigDecimal importe,
			Hashtable htComprobantesProv, Hashtable htIdentificaSalidaPagos,
			Hashtable htMovimientosCancelar, String observaciones,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	/**
	 * @DATE: 20080429 INICIA METODOS PARA GENERARAR PAGOS DIRECTOS TODO: caja -
	 *        auxteso (Pendiente de realizar pagos directos + anexo + factura):
	 * 
	 */

	public String pagosEgresosDirectosCreate(BigDecimal idproveedor,
			java.sql.Date fechamov, BigDecimal importe,
			Hashtable htIdentificaSalidaPagos, Hashtable htMovimientosCancelar,
			Hashtable htIdentificaEntradaPagosDirect,
			Hashtable htMovimientosEntradaCancelar, String observaciones,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	/**
	 * 20090513 INICIA METODOS PARA ELIMINAR - ANULAR PAGOS A PROVEEDORES /
	 * EGRESOS. DIRECTOS TODO: caja - auxteso : no existe en el modelo nuevo.
	 * 
	 */

	public String pagosCajaMovTesoAnular(BigDecimal sucursal,
			BigDecimal comprobante, java.sql.Timestamp fechateso,
			java.sql.Timestamp fultcierreprov, BigDecimal idproveedor,
			String usuarioact, BigDecimal idempresa) throws RemoteException,
			SQLException;

	/**
	 * Metodos para la entidad: proveedoProveed Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Jan 31 12:35:05 GMT-03:00 2007
	 */
	public List getProveedoProveedPK(BigDecimal idproveedor,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vreImpresionRecibos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Sep 27 14:09:34 ART 2007
	 */

	public List getVreImpresionRecibosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getVreImpresionRecibosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * - INFORMES TESORERIA
	 */

	/*
	 * Informe de Saldos en Efectivo entre fechas
	 */

	public List getSaldosEfectivoFecha(Timestamp fecha, Timestamp fechaHasta,
			BigDecimal idempresa) throws RemoteException;

	/*
	 * Informe de Movimientos de Cobranzas entre fechas
	 */

	public List getMovCobranzaFecha(Timestamp fechaDesde, Timestamp fechaHasta,
			BigDecimal idempresa) throws RemoteException;

	/*
	 * Informe de Movimientos de Pagos entre fechas
	 */

	public List getMovPagoFecha(Timestamp fechaDesde, Timestamp fechaHasta,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * DEPOSITOS ... INICIA
	 * 
	 * 
	 */

	/*
	 * Recuperar movimientos de cheques pendientes de deposito.
	 */

	//
	public List getLovChequesPendientesAll(long limit, long offset,
			String cartera_mt, String tipcart_mt, Timestamp fecha_mt,
			String filtroExtra, String operador, BigDecimal idempresa)
			throws RemoteException;

	//

	public List getLovChequesPendientesOcu(long limit, long offset,
			String ocurrencia, String cartera_mt, String tipcart_mt,
			Timestamp fecha_mt, String filtroExtra, String operador,
			BigDecimal idempresa) throws RemoteException;

	// -----------------------------------------------------------------
	/*
	 * 1 - EFECTIVO
	 */

	public String generarDepositoEfectivo(String identificadorDeposito,
			String identificadorBanco, BigDecimal importe,
			java.sql.Timestamp fecha_teso, String cc1Dep, String cc2Dep,
			String cc1Banco, String cc2Banco, BigDecimal idempresa,
			String usuarioalt) throws RemoteException, SQLException;

	// -----------------------------------------------------------------
	/*
	 * 2- CHEQUES
	 */

	// 2.1 - CARTERA
	public String[] generarDepositosCHQCartera(String identificadorDeposito,
			String identificadorBanco, BigDecimal importe,
			java.sql.Timestamp fecha_teso, Hashtable htCheques, String cc1Dep,
			String cc2Dep, String cc1Banco, String cc2Banco,
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	// 2.2- CAUCIONADOS
	public String[] generarDepositosCHQCaucionados(
			String identificadorDeposito, String identificadorBanco,
			BigDecimal importe, java.sql.Timestamp fecha_teso,
			Hashtable htCheques, String cc1Dep, String cc2Dep, String cc1Banco,
			String cc2Banco, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	// 2.3- CAUCION

	public String[] generarDepositosCHQCaucion(String identificadorDeposito,
			String identificadorBanco, BigDecimal importe,
			java.sql.Timestamp fecha_teso, Hashtable htCheques, String cc1Dep,
			String cc2Dep, String cc1Banco, String cc2Banco,
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	/**
	 * Metodos para la entidad: cajaIdentificadores Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Oct 17 10:32:33 GMT-03:00 2008
	 * 
	 */

	public List getCajaIdentificadoresAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCajaIdentificadoresOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCajaIdentificadoresPK(BigDecimal ididentificador,
			BigDecimal idempresa) throws RemoteException;

	public String cajaIdentificadoresDelete(BigDecimal ididentificador,
			String identificador, BigDecimal idempresa) throws RemoteException;

	public String cajaIdentificadoresCreate(BigDecimal idtipoidentificador,
			String identificador, String descripcion, BigDecimal cuenta,
			BigDecimal idmoneda, String modcta, String factura,
			Double saldo_id, Double saldo_disp, BigDecimal renglones,
			BigDecimal ctacaucion, BigDecimal ctatodoc, String gerencia,
			String formula, BigDecimal cuotas, BigDecimal presentacion,
			BigDecimal ctacaudoc, Double porcentaje, BigDecimal ctadtotar,
			BigDecimal ctatarjeta, BigDecimal comhyper, BigDecimal contador,
			String afecomicob, String impri_id, String subdiventa,
			String idcencosto, String idcencosto1, String modicent,
			BigDecimal prox_cheq, BigDecimal prox_reserv, BigDecimal ulti_cheq,
			String modsubcent, BigDecimal res_nro, Hashtable htCuotas,
			Hashtable htPresentacion, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	public String cajaIdentificadoresCreateOrUpdate(BigDecimal ididentificador,
			BigDecimal idtipoidentificador, String identificador,
			String descripcion, BigDecimal cuenta, BigDecimal idmoneda,
			String modcta, String factura, Double saldo_id, Double saldo_disp,
			BigDecimal renglones, BigDecimal ctacaucion, BigDecimal ctatodoc,
			String gerencia, String formula, BigDecimal cuotas,
			BigDecimal presentacion, BigDecimal ctacaudoc, Double porcentaje,
			BigDecimal ctadtotar, BigDecimal ctatarjeta, BigDecimal comhyper,
			BigDecimal contador, String afecomicob, String impri_id,
			String subdiventa, String idcencosto, String idcencosto1,
			String modicent, BigDecimal prox_cheq, BigDecimal prox_reserv,
			BigDecimal ulti_cheq, String modsubcent, BigDecimal res_nro,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String cajaIdentificadoresUpdate(BigDecimal ididentificador,
			BigDecimal idtipoidentificador, String identificador,
			String descripcion, BigDecimal cuenta, BigDecimal idmoneda,
			String modcta, String factura, Double saldo_id, Double saldo_disp,
			BigDecimal renglones, BigDecimal ctacaucion, BigDecimal ctatodoc,
			String gerencia, String formula, BigDecimal cuotas,
			BigDecimal presentacion, BigDecimal ctacaudoc, Double porcentaje,
			BigDecimal ctadtotar, BigDecimal ctatarjeta, BigDecimal comhyper,
			BigDecimal contador, String afecomicob, String impri_id,
			String subdiventa, String idcencosto, String idcencosto1,
			String modicent, BigDecimal prox_cheq, BigDecimal prox_reserv,
			BigDecimal ulti_cheq, String modsubcent, BigDecimal res_nro,
			Hashtable htCuotas, Hashtable htPresentacion, BigDecimal idempresa,
			String usuarioact) throws RemoteException, SQLException;

	public List getCajaLovIdentifTipoAll(long limit, long offset, String tipo,
			BigDecimal idempresa) throws RemoteException;

	public List getCajaLovIdentifTipoOcu(long limit, long offset, String tipo,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: cajaValorTar Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Oct 21 10:01:01 GMT-03:00 2008
	 * 
	 */

	public List getCajaValorTarPK(String valor_id, String tipo,
			BigDecimal idempresa) throws RemoteException;

	public String cajaValorTarDelete(String valor_id, String tipo,
			BigDecimal idempresa) throws RemoteException;

	public String cajaValorTarCreate(String valor_id, String tipo,
			BigDecimal numero, BigDecimal dias, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	/**
	 * Metodos para la entidad: vcajaMovTeso - COBRANZAS Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed May 13 10:18:02 GYT 2009
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getCajaMovTesoCobranzasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getCajaMovTesoCobranzasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// 20120925 -- EJV -->

	public List getCajaMovTesoCobranzasFiltro(long limit, long offset,
			String filtro, BigDecimal idempresa) throws RemoteException;

	// <--

	/**
	 * Metodos para la entidad: vcajaAnuladas Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Jun 10 10:04:09 GYT 2009
	 * 
	 */

	public List getVcajaAnuladasAll(long limit, long offset, String tipomov_an,
			BigDecimal idempresa) throws RemoteException;

	public List getVcajaAnuladasOcu(long limit, long offset, String tipomov_an,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vcajaMovTesoPagos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon May 18 13:11:14 GYT 2009
	 */

	public List getCajaMovTesoPagosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCajaMovTesoPagosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// libro diario de caja
	public List getCosnultaLibroDiariodeCaja(String fechadesde,
			String fechahasta, BigDecimal idempresa) throws RemoteException;

	// 20110622 - EJV - Factuaracion FE-CF-MA -->

	/**
	 * Metodos para la entidad: cajaSucursales Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Jun 16 11:12:47 ART 2011
	 */

	public List getCajaSucursalesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public String getPorPerIIBB(String cuit) throws RemoteException;

	// <--

	/**
	 * Metodos para la entidad: vCajaSucursalesContadores Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Mar 06 10:48:55 ART 2012
	 */

	public List getVCajaSucursalesContadoresAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getVCajaSucursalesContadoresOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getLovClientesAplicacionMovimientosAll(BigDecimal cliente,
			BigDecimal idempresa) throws RemoteException;

	public List getLovClientesAplicacionMovimientosOne(BigDecimal cliente,
			BigDecimal sucursal, BigDecimal comprob, String tipomovs,
			BigDecimal idempresa) throws RemoteException;

	public String[] cajaClientesCobranzasReintegro(BigDecimal idcliente,
			BigDecimal nrointerno_cob, java.sql.Date fechamov,
			Hashtable htIdentificaSalidaPagos, Hashtable htMovimientosCancelar,
			String usuarioact, BigDecimal idempresa) throws RemoteException,
			SQLException;

	// 20120926 - EJV -->

	public List getLovClientesMovCliCobReintegroAll(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getLovClientesMovCliCobReintegroOne(BigDecimal cliente,
			BigDecimal sucursal, BigDecimal comprob, String tipomovs,
			BigDecimal idempresa) throws RemoteException;

	// <--

}