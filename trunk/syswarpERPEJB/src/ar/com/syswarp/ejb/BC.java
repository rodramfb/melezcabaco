package ar.com.syswarp.ejb;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.ejb.Local;

@Local
public interface BC {
	public String holabean() throws RemoteException;

	// getTotalEntidad
	public long getTotalEntidad(String entidad, BigDecimal idempresa)
			throws RemoteException;

	// getTotalEntidadOcu
	public long getTotalEntidadOcu(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidadFiltro(String entidad, String filtro,
			BigDecimal idempresa) throws RemoteException;

	public ResultSet getTransaccion(String Sp, String Param)
			throws RemoteException, SQLException;

	public BigDecimal getIdDepositoDelta(BigDecimal idUsuario)
			throws RemoteException;

	public BigDecimal getIdDepositoDeltaXConjunto(BigDecimal cconjunto)
			throws RemoteException;

	public String setPedidoTM(String operacion, BigDecimal idcampania,
			BigDecimal idsocio, String fechapedi, String entregara,
			BigDecimal idmesentrega, String obsremito, String obscobranza,
			String obsproducto, String usuarioalt, String usuarioact,
			BigDecimal idprovincia, BigDecimal idlocalidad,
			BigDecimal totalbruto, BigDecimal adicdesc, BigDecimal totalpedido,
			String entregaren, String idproductoD, BigDecimal cantidad,
			BigDecimal descuento, BigDecimal mesentrega) throws RemoteException;

	public String[] InterfaseDbBacoDeltaMovSalida(String conjunto,
			String remito, String idestado, String confirma,
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	public String[] InterfaseDbBacoDeltaDespachoSocios(String conjuntoOrigen,
			String conjuntoDestino, Hashtable htSeleccion,
			String observaciones, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public String[] InterfaseDbBacoDeltaDespachoStock(Timestamp fechamov,
			String tipomov, BigDecimal num_cnt, BigDecimal sucursal,
			String tipo, String observaciones, Hashtable htArticulos,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public String[] InterfaseDbBacoDeltaDespachoAndreani(String conjuntoOrigen,
			String conjuntoDestino, String hrDesde, String hrHasta,
			Hashtable htSeleccion, String observaciones, String usuarioalt,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public String[] InterfaseDbBacoDeltaTransfDepAreas(BigDecimal idareaorigen,
			BigDecimal idareadestino, Timestamp fechamov, String tipomov,
			BigDecimal num_cnt, BigDecimal sucursal, String tipo,
			String observaciones, Hashtable htArticulos, String usuarioalt,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public List getStockArticulosPK(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	public ResultSet callSpInterfacesStockConjuntoTmp(BigDecimal cconjunto,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public String[] callStockMovDesarmadoProductosEsquema(BigDecimal idesquema,
			String codigo_st, BigDecimal codigo_dt, BigDecimal cantidad,
			BigDecimal idmotivodesarma, String observaciones, int recursivo,
			int ejercicioactivo, BigDecimal idcontadorcomprobante,
			String sistema_ms, BigDecimal idarea, BigDecimal idempresa,
			String usuarioalt) throws RemoteException, SQLException;

	public List getStockDepostiosTransf(String codigo_dt_IN,
			BigDecimal idempresa) throws RemoteException;

	/***************************************************************************
	 * 
	 * PRODUCCION
	 * 
	 **************************************************************************/

	public List getINTFMovProduAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getINTFMovProduOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public String intfMovProduDelete(BigDecimal idop, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public String intfOrdenProdCreate(BigDecimal idesquema,
			BigDecimal idcliente, BigDecimal cantre_op, BigDecimal cantest_op,
			java.sql.Date fecha_prometida, java.sql.Date fecha_emision,
			String observaciones, String codigo_st, BigDecimal idcontador,
			BigDecimal nrointerno, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public String intfOrdenProdUpdate(BigDecimal idop, BigDecimal idesquema,
			BigDecimal idcliente, BigDecimal cantre_op, BigDecimal cantest_op,
			java.sql.Date fecha_prometida, java.sql.Date fecha_emision,
			String observaciones, String codigo_st, BigDecimal idcontador,
			BigDecimal nrointerno, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public List getINTFMovProduPK(BigDecimal idop, BigDecimal idempresa)
			throws RemoteException;

	public List getINTFMovProduPendientesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getINTFMovProduPendientesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: produccionMovProdu_Deta Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Feb 21 13:31:14 GMT-03:00 2007
	 */

	public List getINTFMovProdu_DetaPK(BigDecimal idop, BigDecimal idempresa)
			throws RemoteException;

	public String prodINTFGenerarExplosionOP(String[] idop,
			String[] cantidadparcial, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	/**
	 * Metodos para la entidad: interfacesOpRegalos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Dec 04 11:20:14 GMT-03:00 2008
	 * 
	 */

	public String interfacesOpRegalosCreate(BigDecimal idop,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String[] callINTFAnulaOrdenProduccion(BigDecimal idop,
			BigDecimal idesquema, String codigo_st, BigDecimal codigo_dt,
			BigDecimal cantidad, BigDecimal idmotivodesarma,
			String observaciones, int recursivo, int ejercicioactivo,
			BigDecimal idcontadorcomprobante, String sistema_ms,
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	public List getClientesDomiciliosPK(BigDecimal iddomicilio,
			BigDecimal idempresa, Connection pgconn) throws RemoteException;

	// Metodos para indicadores de Marketing
	public List getClientesindicadoresmanualesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesindicadoresmanualesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesindicadoresmanualesPK(BigDecimal idindicador,
			BigDecimal idempresa) throws RemoteException;

	public String clientesindicadoresmanualesDelete(BigDecimal idindicador,
			BigDecimal idempresa) throws RemoteException;

	public String clientesindicadoresmanualesCreate(BigDecimal idtipoindicador,
			String indicador, String queryseleccion, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String clientesindicadoresmanualesUpdate(BigDecimal idindicador,
			BigDecimal idtipoindicador, String indicador,
			String queryseleccion, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public List getClientesindicadoresTiposAll(BigDecimal idempresa)
			throws RemoteException;

	/**
	 * FECHA:20100326..........................................................
	 * AUTOR:EJV...............................................................
	 * MOTIVO:MEJORAR GENERADOR................................................
	 * OBSERVACIONES:ESTOS METODOS PUEDEN SER BORRADOS, SOLO SON PRUEBAS PARA..
	 * .............. MEJORAR EL GENEADOR......................................
	 * 
	 * 
	 * */
	/*
	 * Metodos para la entidad: _borrar_clientesEstados Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Fri Mar 26 14:09:51 GMT-03:00 2010
	 */

	public List get_borrar_clientesEstadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List get_borrar_clientesEstadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List get_borrar_clientesEstadosPK(BigDecimal idestado,
			BigDecimal idempresa) throws RemoteException;

	public String _borrar_clientesEstadosDelete(BigDecimal idestado,
			BigDecimal idempresa) throws RemoteException;

	public String _borrar_clientesEstadosCreate(String estado, String fechasn,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String _borrar_clientesEstadosCreateOrUpdate(BigDecimal idestado,
			String estado, String fechasn, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String _borrar_clientesEstadosUpdate(BigDecimal idestado,
			String estado, String fechasn, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String InterFacesGenerarPedido(BigDecimal idpedidoDelta,
			BigDecimal idcampacabeDelta, BigDecimal idcliente,
			BigDecimal idsucuclie, Timestamp fechapedido,
			BigDecimal idcondicion, String obsarmado, String obsentrega,
			BigDecimal totaliva, BigDecimal idprioridad, BigDecimal idzona,
			BigDecimal idtarjeta, BigDecimal cuotas, String origenpedido,
			BigDecimal total, BigDecimal cotizacion, Hashtable htArticulos,
			String usuarioalt, BigDecimal idempresa, Properties props)
			throws SQLException, RemoteException;

	// ------------------------------------------------------------------------
	// ------------------------------------------------------------------------
	// ------------------------------------------------------------------------
}