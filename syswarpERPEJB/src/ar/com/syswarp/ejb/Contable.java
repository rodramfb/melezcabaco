package ar.com.syswarp.ejb;

import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import javax.ejb.Local;

import sun.misc.REException;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.math.*;

import javax.ejb.Local;

@Local
public interface Contable {

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

	// centros de costos
	public List getCenCosto(BigDecimal idempresa) throws RemoteException; // LISTAR

	// TODO

	// POR LA PK
	public List getCenCostoPK(String pk, BigDecimal idempresa)
			throws RemoteException; // LISTAR

	// POR UNA OCURRENCIA
	public List getCenCosto(String ocurrencia, BigDecimal idempresa)
			throws RemoteException; // LISTAR

	// BORRADO
	public String delCenCosto(Integer pk, BigDecimal idempresa)
			throws RemoteException; // BORRAR

	// INSERTAR UN REGISTRO
	public String cenCostoSave(String descripcion, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	// MODIFICACION
	public String cenCostoSaveOrUpdate(String descripcion, String usuarioalt,
			Integer idcencosto, BigDecimal idempresa) throws RemoteException; // UPDATE

	// DE
	// REGISTRO

	// cuentas contables

	/**
	 * Metodos para la entidad: contableInfiPlan Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jun 14 15:59:43 ART 2007. - Creo: EJV - Llamada
	 * desde Lov
	 * 
	 */

	public List getCuentasTod(int idEjercicio, BigDecimal idempresa)
			throws RemoteException; // LISTAR

	// POR PK
	public List getCuentasPK(int idEjercicio, Long pk, BigDecimal idempresa)
			throws RemoteException; // LISTAR

	// POR OCURRENCIA
	public List getCuentasOcu(int idEjercicio, String ocurrencia,
			BigDecimal idempresa) throws RemoteException; // LISTAR POR UNA

	// OCURRENCIA

	// BORRO
	public String delContable(int idEjercicio, Long pk, BigDecimal idempresa)
			throws RemoteException; // BORRAR

	public boolean isImputable(int idEjercicio, Long idCuenta,
			BigDecimal idempresa) throws RemoteException;

	public boolean isAjustable(int idEjercicio, Long idCuenta,
			BigDecimal idempresa) throws RemoteException;

	public boolean isResultado(int idEjercicio, Long idCuenta,
			BigDecimal idempresa) throws RemoteException;

	// contable ajuste
	public List getAjusteTod(BigDecimal idempresa) throws RemoteException; // LISTAR

	// TODO

	public List getAjustePK(Integer pk, BigDecimal idempresa)
			throws RemoteException; // LISTAR POR LA

	// PK

	public List getAjusteOcu(String ocurrencia, BigDecimal idempresa)
			throws RemoteException; // LISTAR

	// POR
	// UNA
	// OCURRENCIA

	public String delAjuste(Integer pk, BigDecimal idempresa)
			throws RemoteException; // BORRAR POR PK

	public String AjusteSave(int anio, int mes, Float indice,
			String usuarioalt, BigDecimal idempresa) throws RemoteException; // INSERTAR

	// UN
	// REGISTRO

	public String AjusteSaveOrUpdate(int anio, int mes, Float indice,
			String usuarioalt, Integer cod_ajuste, BigDecimal idempresa)
			throws RemoteException; // UPDATE

	// contable Plan Ajuste
	public List getPlanAjusTod(int idEjercicio, BigDecimal idempresa)
			throws RemoteException; // LISTAR

	// TOD

	public List getPlanAjusPK(Integer pk, BigDecimal idempresa)
			throws RemoteException; // LISTAR

	public List getPlanAjusOcu(int idEjercicio, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public String delPlanAjus(Long pk, Long pk2, BigDecimal idempresa)
			throws RemoteException; // BORRAR

	public String PlanAjusSave(Long cuenta_pl, Long indice_pl,
			String usuarioalt, BigDecimal idempresa) throws RemoteException; // INSERTAR

	// UN
	// REGISTRO

	// handler para ejercicios contables.
	public boolean hasEjercicio(int idEjercicio, BigDecimal idempresa)
			throws RemoteException;

	public boolean setEjercicio(int idEjercicio, Timestamp fdesde,
			Timestamp fhasta, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public void ejercicioCreate(int idEjercicio, Timestamp fdesde,
			Timestamp fhasta, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public Timestamp getFechaEjercicioActivoDesde(BigDecimal idempresa)
			throws RemoteException;

	public Timestamp getFechaEjercicioActivoHasta(BigDecimal idempresa)
			throws RemoteException;

	public String activarEjercicio(int ejerciciodesde, BigDecimal idempresa)
			throws RemoteException;

	public int getEjercicioActivo(BigDecimal idempresa) throws RemoteException;

	public List getEjerciciosAll(BigDecimal idempresa) throws RemoteException;

	public void dropEjercicioPostgres(int idEjercicio, BigDecimal idempresa)
			throws RemoteException;

	public Long getCC1Cuenta(int idEjercicio, Long idCuenta,
			BigDecimal idempresa) throws RemoteException;

	public Long getCC2Cuenta(int idEjercicio, Long idCuenta,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: contableAsietip1 Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Mon Sep 15 13:54:44 GMT-03:00 2008
	 */

	public String contableAsientosTipoCreate(BigDecimal codigo, String leyenda,
			Hashtable htDetalle, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	public List getContableAsietip1All(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getContableAsietip1Ocu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getContableAsietip1PK(BigDecimal codigo, BigDecimal idempresa)
			throws RemoteException;

	public String contableAsietip1Delete(BigDecimal codigo, BigDecimal idempresa)
			throws RemoteException;

	public String contableAsietip1Create(BigDecimal codigo, String leyenda,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String contableAsietip1CreateOrUpdate(BigDecimal codigo,
			String leyenda, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String contableAsietip1Update(BigDecimal codigo, String leyenda,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: contableAsietip2 Copyrigth(r) sysWarp S.R.L.
	 */
	public List getContableAsietip2PK(BigDecimal codigo, BigDecimal ejercicio,
			BigDecimal idempresa) throws RemoteException;

	public String contableAsietip2Delete(BigDecimal codigo, BigDecimal idempresa)
			throws RemoteException;

	public String contableAsietip2Create(BigDecimal codigo, BigDecimal cuenta,
			String detalle, String cent_cost, String cent_cost1,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	// Asientos Tipo

	public List getAsientosTipoAll(BigDecimal idempresa) throws RemoteException;

	public List getAsientosTipoOcu(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// Asientos - Libro Diario
	public List getAsientosOcu(int idEjercicio, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * OBJETIVO: MANEJAR ASIENTOS Metodos para la entidad: contableLeyendas
	 * Copyrigth(r) sysWarp S.R.L. Fecha de creacion: Wed Sep 10 11:42:24
	 * GMT-03:00 2008 RELEASE
	 */

	public List getContableAsientosAll(long limit, long offset,
			BigDecimal ejercicioactivo, BigDecimal idempresa)
			throws RemoteException;

	public List getContableAsientosOcu(long limit, long offset,
			String ocurrencia, BigDecimal ejercicioactivo, BigDecimal idempresa)
			throws RemoteException;

	public List getContableAsientosPK(BigDecimal idasiento,
			BigDecimal ejercicioactivo, BigDecimal idempresa)
			throws RemoteException;

	public String contableAsientosDelete(BigDecimal idasiento,
			BigDecimal ejercicioactivo, BigDecimal idempresa)
			throws RemoteException;

	public String contableAsientosCreate(BigDecimal idasiento,
			BigDecimal ejercicio, Timestamp fecha, String leyenda,
			BigDecimal nroasiento, String tipo_asiento,
			BigDecimal asiento_nuevo, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String contableAsientosCreateOrUpdate(BigDecimal idasiento,
			BigDecimal ejercicio, Timestamp fecha, String leyenda,
			BigDecimal nroasiento, String tipo_asiento,
			BigDecimal asiento_nuevo, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String contableAsientosUpdate(BigDecimal idasiento,
			BigDecimal ejercicio, Timestamp fecha, String leyenda,
			BigDecimal nroasiento, String tipo_asiento,
			BigDecimal asiento_nuevo, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public List getAsientosPK(int idEjercicio, Long idAsiento,
			BigDecimal idempresa) throws RemoteException;

	public String contableGenerarAsiento(BigDecimal idasiento,
			BigDecimal ejercicio, Timestamp fecha, String leyenda,
			String tipo_asiento, BigDecimal asiento_nuevo, Hashtable htAsiento,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public String contableNuevoAsiento(BigDecimal idasiento,
			BigDecimal nroasiento, BigDecimal ejercicio, Timestamp fecha,
			String leyenda, String tipo_asiento, BigDecimal asiento_nuevo,
			Hashtable htAsiento, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public String contableEliminarAsiento(BigDecimal idasiento,
			BigDecimal ejercicio, BigDecimal idempresa) throws SQLException,
			RemoteException;

	public String contableActualizarAsiento(BigDecimal idasiento,
			BigDecimal ejercicio, Timestamp fecha, String leyenda,
			String tipo_asiento, BigDecimal asiento_nuevo, Hashtable htAsiento,
			String usuarioalt, BigDecimal idempresa) throws SQLException,
			RemoteException;

	/**
	 * Metodos para la entidad: contableInfimov Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Sep 12 10:02:05 GMT-03:00 2008
	 * 
	 */

	public List getContableInfimovPK(BigDecimal idasiento,
			BigDecimal ejercicio, BigDecimal idempresa) throws RemoteException;

	public String contableInfimovCreate(BigDecimal idasiento,
			BigDecimal renglon, BigDecimal cuenta, BigDecimal tipomov,
			BigDecimal importe, String detalle, BigDecimal asie_nue,
			BigDecimal cent_cost, BigDecimal cent_cost1, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String contableInfimovDelete(BigDecimal idasiento,
			BigDecimal idempresa) throws RemoteException;

	// no necesita el idempresa
	public List getLibroMayor(int idEjercicio, Long cuenta, int anio, int mes,
			Long cc1, Long cc2, BigDecimal idempresa) throws RemoteException;

	public List getLibroMayorSinCC(BigDecimal idcuenta_desde,
			BigDecimal idcuenta_hasta, java.sql.Date fecha_desde,
			java.sql.Date fecha_hasta, BigDecimal anio, BigDecimal idempresa)
			throws RemoteException;

	public List getMonedas(BigDecimal idempresa) throws RemoteException;

	public List getMonedas(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getMonedasPK(Integer pk, BigDecimal idempresa)
			throws RemoteException;

	public String delMonedas(Integer pk, BigDecimal idempresa)
			throws RemoteException;

	public String monedasSave(String moneda, Timestamp duracion_hasta,
			int cantidad_ceros, String detalle, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String monedasSaveOrUpdate(long idmoneda, String moneda,
			Timestamp duracion_hasta, int cantidad_ceros, String detalle,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public List getSaldoCuentasPeriodoAll(int idEjercicio, int mesDesde,
			int mesHasta, BigDecimal idempresa) throws RemoteException;

	public String contableAsientosRenumeracion(BigDecimal ejercicio,
			String usuarioact, BigDecimal idempresa) throws RemoteException,
			SQLException;

	// getLibroDiario
	public List getLibroDiario(int idEjercicio, Long idAsientoDesde,
			Long idAsisentoHasta, BigDecimal idempresa) throws RemoteException;

	public List getLibroDiario(int idEjercicio, Timestamp fechaDesde,
			Timestamp fechaHasta, BigDecimal idempresa) throws RemoteException;

	//

	public void setUsuario(String usuario, BigDecimal idempresa)
			throws RemoteException;

	public String getUsuario(BigDecimal idempresa) throws RemoteException;

	// lov centro de costo traigo todos
	public List getCentrosdecostoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	// lov centro de costo traigo x ocurrencia
	public List getCentrosdecostoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// total All contablecencosto
	public long getTotalcencostoAll(BigDecimal idempresa)
			throws RemoteException;

	// total Ocu contablecencosto
	public long getTotalcencostoOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws RemoteException;

	// Centros de Costos autor:fgp 03/01/08
	public List getContablecencostoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getContablecencostoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getContablecencostoPK(BigDecimal idcencosto,
			BigDecimal idempresa) throws RemoteException;

	public String contablecencostoDelete(BigDecimal idcencosto,
			BigDecimal idempresa) throws RemoteException;

	public String contablecencostoCreate(String descripcion, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String contablecencostoCreateOrUpdate(BigDecimal idcencosto,
			String descripcion, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String contablecencostoUpdate(BigDecimal idcencosto,
			String descripcion, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// Indice de Ajuste autor:fgp 08/01/08
	public List getContableajusteAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getContableajusteOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getContableajustePK(BigDecimal cod_ajuste, BigDecimal idempresa)
			throws RemoteException;

	public String contableajusteDelete(BigDecimal cod_ajuste,
			BigDecimal idempresa) throws RemoteException;

	public String contableajusteCreate(Integer anio, String mes, Double indice,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String contableajusteCreateOrUpdate(BigDecimal cod_ajuste,
			Integer anio, String mes, Double indice, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String contableajusteUpdate(BigDecimal cod_ajuste, Integer anio,
			String mes, Double indice, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public List getGlobalMesesAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalMesesOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public long getTotalEntidadSinEmpresa(String entidad)
			throws RemoteException;

	public long getTotalEntidadOcuSinEmpresa(String entidad, String[] campos,
			String ocurrencia) throws RemoteException;

	// contable monedas
	public List getContablemonedasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getContablemonedasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getContablemonedasPK(BigDecimal idmoneda, BigDecimal idempresa)
			throws RemoteException;

	public String contablemonedasDelete(BigDecimal idmoneda,
			BigDecimal idempresa) throws RemoteException;

	public String contablemonedasCreate(String moneda, Timestamp hasta_mo,
			BigDecimal ceros_mo, String detalle, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String contablemonedasCreateOrUpdate(BigDecimal idmoneda,
			String moneda, Timestamp hasta_mo, BigDecimal ceros_mo,
			String detalle, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String contablemonedasUpdate(BigDecimal idmoneda, String moneda,
			Timestamp hasta_mo, BigDecimal ceros_mo, String detalle,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// cuentas contables
	public List getContableinfiplannAll(BigDecimal idejercicio, long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getContableinfiplannOcu(BigDecimal idejercicio, long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// contable plan ajuste
	public List getContableplanajusAll(BigDecimal idejercicio, long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getContableplanajusOcu(BigDecimal idejercicio, long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public String contableplanajusDelete(String cuenta_pl, BigDecimal idempresa)
			throws RemoteException;

	public String contableplanajusCreate(String cuenta_pl, String indice_pl,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String contableplanajusCreateOrUpdate(String cuenta_pl,
			String indice_pl, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String contableplanajusUpdate(String cuenta_pl, String indice_pl,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// lov cuentas contables
	public List getContableCuentasLovAll(BigDecimal idejercicio, long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getContableCuentasLovOcu(BigDecimal idejercicio, long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// lov contable ajuste
	public List getContableAjusteLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getContableAjusteLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// contable rubros
	// public List getContablerubrosAll(long limit, long offset,
	// BigDecimal idempresa) throws RemoteException;

	// public List getContablerubrosOcu(long limit, long offset,
	// String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// public List getContablerubrosPK(BigDecimal idrubro, BigDecimal idempresa)
	// throws RemoteException;

	// public String contablerubrosDelete(BigDecimal idrubro, BigDecimal
	// idempresa)
	// throws RemoteException;

	// public String contablerubrosCreate(String rubro, String usuarioalt,
	// BigDecimal idempresa) throws RemoteException;

	// public String contablerubrosCreateOrUpdate(BigDecimal idrubro,
	// String rubro, String usuarioact, BigDecimal idempresa)
	// throws RemoteException;

	// public String contablerubrosUpdate(BigDecimal idrubro, String rubro,
	// String usuarioact, BigDecimal idempresa) throws RemoteException;

	public List getCcontablesAll(BigDecimal idEjercicio, long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getStockgruposOcu_NOMBRECOMOELORTO(BigDecimal idEjercicio,
			long limit, long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getContableinfiplanAll(long limit, long offset,
			BigDecimal ejercicio, BigDecimal idempresa) throws RemoteException;

	public List getContableinfiplanOcu(long limit, long offset,
			BigDecimal ejercicio, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getContableinfiplanpk(BigDecimal ejercicio,
			BigDecimal idcuenta, BigDecimal idempresa) throws RemoteException;

	public String contableinfiplanDelete(BigDecimal ejercicio,
			BigDecimal idcuenta, BigDecimal idempresa) throws RemoteException;

	public String contableinfiplanCreate(BigDecimal ejercicio,
			BigDecimal idcuenta, String cuenta, String inputable,
			BigDecimal nivel, String ajustable, String resultado,
			BigDecimal cent_cost1, BigDecimal cent_cost2, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String contableinfiplanCreateOrUpdate(BigDecimal ejercicio,
			BigDecimal idcuenta, String cuenta, String inputable,
			BigDecimal nivel, String ajustable, String resultado,
			BigDecimal cent_cost1, BigDecimal cent_cost2, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String contableinfiplanUpdate(BigDecimal ejercicio,
			BigDecimal idcuenta, String cuenta, String inputable,
			BigDecimal nivel, String ajustable, String resultado,
			BigDecimal cent_cost1, BigDecimal cent_cost2, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	// lov ejercicios
	public List getEjercicioLovAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getEjercicioLovOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	// impacto en la tabla contableinfiplan
	public String contableCopiarPlanCuentas(BigDecimal ejercicioOrigen,
			BigDecimal ejercicioDestino, BigDecimal idempresa)
			throws RemoteException;

	// contable ejercicios
	public List getContableejerciciosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getContableejerciciosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getContableejerciciosPK(BigDecimal ejercicio,
			BigDecimal idempresa) throws RemoteException;

	public String contableejerciciosDelete(BigDecimal ejercicio,
			BigDecimal idempresa) throws RemoteException;

	public String contableejerciciosCreate(BigDecimal ejercicio,
			Timestamp fechadesde, Timestamp fechahasta, String activo,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String contableejerciciosUpdate(BigDecimal ejercicio,
			Timestamp fechadesde, Timestamp fechahasta, String activo,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public List getLibroDiarioSC(int idEjercicio, String fechaDesde,
			String fechaHasta, BigDecimal idempresa, String vista)
			throws RemoteException;

	public String setLibroDiarioSC(int idEjercicio, String fechaDesde,
			String fechaHasta, String vista, String usuarioalt,
			BigDecimal idempresa) throws RemoteException, SQLException;

	// public List getSumasSaldos(int idEjercicio, BigDecimal idempresa) throws
	// RemoteException;

	public List getSumasSaldos(int idEjercicio, int mesDesde, int mesHasta,
			BigDecimal idempresa) throws RemoteException, SQLException;

	// resumen de movimiento historico
	public List getResumenMovHistorico(BigDecimal idempresa,
			BigDecimal ejercicio) throws RemoteException;

	// contable rubros bienes de uso
	public List getContablerubieusoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getContablerubieusoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getContablerubieusoPK(BigDecimal idrubro, BigDecimal idempresa)
			throws RemoteException;

	public String contablerubieusoDelete(BigDecimal idrubro,
			BigDecimal idempresa) throws RemoteException;

	public String contablerubieusoCreate(String rubro, BigDecimal anios,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	// public String contablerubieusoCreateOrUpdate(BigDecimal idrubro, String
	// rubro, BigDecimal anios, String usuarioact, Timestamp fechaact) throws
	// RemoteException;
	public String contablerubieusoUpdate(BigDecimal idrubro, String rubro,
			BigDecimal anios, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// contable bienes de uso
	public List getContablebienusoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getContablebienusoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getContablebienusoPK(BigDecimal idbienuso, BigDecimal idempresa)
			throws RemoteException;

	public String contablebienusoDelete(BigDecimal idbienuso,
			BigDecimal idempresa) throws RemoteException;

	public String contablebienusoCreate(String bienuso, BigDecimal idrubro,
			Timestamp fechacompra, Double valorori, BigDecimal anios,
			BigDecimal meses, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	// public String contablebienusoCreateOrUpdate(BigDecimal idbienuso, String
	// bienuso, BigDecimal idrubro, Timestamp fechacompra, Double valorori,
	// Timestamp fechabaja, BigDecimal anios, BigDecimal meses, BigDecimal
	// idempresa, String usuarioact) throws RemoteException;
	public String contablebienusoUpdate(BigDecimal idbienuso, String bienuso,
			BigDecimal idrubro, Timestamp fechacompra, Double valorori,
			BigDecimal anios, BigDecimal meses, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public long getTotalEntidadBienesdeUso(String entidad, BigDecimal idempresa)
			throws RemoteException;

	public long getTotalEntidadBienesdeUsoOcu(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// contable rubros
	public List getContablerubrosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getContablerubrosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getContablerubrosPK(BigDecimal cod_rubro, BigDecimal idempresa)
			throws RemoteException;

	public String contablerubrosDelete(BigDecimal cod_rubro,
			BigDecimal idempresa) throws RemoteException;

	public String contablerubrosCreate(String desc_rubro, String ubicacion,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	// public String contablerubrosCreateOrUpdate(BigDecimal cod_rubro, String
	// desc_rubro, String ubicacion, BigDecimal idempresa, String usuarioact)
	// throws RemoteException;
	public String contablerubrosUpdate(BigDecimal cod_rubro, String desc_rubro,
			String ubicacion, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: VCAJASUBCONTABILIDAD - VSTOCKSUBCONTABILIDAD -
	 * VPROVEEDORESSUBCONTABILIDAD -Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Tue Jul 07 08:34:18 GYT 2009
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getSubcontabilidadAll(String entidad, BigDecimal ejercicio,
			String fDesde, String fHasta, BigDecimal idempresa)
			throws RemoteException;

	public List getLibroMayorContFinanciera(BigDecimal idcuenta_desde,
			BigDecimal idcuenta_hasta, java.sql.Date fecha_desde,
			java.sql.Date fecha_hasta, BigDecimal anio, BigDecimal idempresa)
			throws RemoteException;

	public List getLibroMayorContFinancieraProveedores(
			BigDecimal idcuenta_desde, BigDecimal idcuenta_hasta,
			java.sql.Date fecha_desde, java.sql.Date fecha_hasta,
			BigDecimal anio, BigDecimal idempresa) throws RemoteException;

	public List getLibroMayorContFinancieraBienesdecambio(
			BigDecimal idcuenta_desde, BigDecimal idcuenta_hasta,
			java.sql.Date fecha_desde, java.sql.Date fecha_hasta,
			BigDecimal anio, BigDecimal idempresa) throws RemoteException;

	public List getLibroMayorConDetalleSinCC(BigDecimal idcuenta_desde,
			BigDecimal idcuenta_hasta, java.sql.Date fecha_desde,
			java.sql.Date fecha_hasta, BigDecimal anio, BigDecimal idempresa)
			throws RemoteException;

	public List getMiniPlanCuentasPK(int idEjercicio, Long pk,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesSubContabilidad Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu May 31 10:18:14 ART 2012
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVClientesSubContabilidadAll(int ejercicio,
			String fechadesde, String fechahasta, BigDecimal idempresa)
			throws RemoteException;

	// 20130807 - EJV -->

	public List getMiniPlanCuentasPlantilla(int idEjercicio,
			BigDecimal idplantilla, BigDecimal idempresa)
			throws RemoteException;

	// <--

}
