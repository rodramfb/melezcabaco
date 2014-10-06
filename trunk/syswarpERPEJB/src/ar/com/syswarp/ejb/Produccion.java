package ar.com.syswarp.ejb;

import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import javax.ejb.Local;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.math.*;
import javax.ejb.Local;
@Local
public interface Produccion  {

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

	// PRODUCCION CONCEPTO
	public List getProduccionconceptosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProduccionconceptosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProduccionconceptosPK(BigDecimal idconcepto,
			BigDecimal idempresa) throws RemoteException;

	public String ProduccionconceptosDelete(BigDecimal idconcepto,
			BigDecimal idempresa) throws RemoteException;

	public String ProduccionconceptosCreate(String concepto, String formula,
			Double margen_error, Double costo, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String ProduccionconceptosCreateOrUpdate(BigDecimal idconcepto,
			String concepto, String formula, Double margen_error, Double costo,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String ProduccionconceptosUpdate(BigDecimal idconcepto,
			String concepto, String formula, Double margen_error, Double costo,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: produccionEsquemas_Cabe -
	 * produccionEsquemas_Deta Copyrigth(r) sysWarp S.R.L. Fecha de creacion:
	 * Tue Feb 14 14:32:00 GMT-03:00 2007
	 */

	public String produccionEsquemasCreate(String esquema,
			BigDecimal codigo_md, String observaciones,
			Hashtable htDetalleEsquema, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public String produccionEsquemasUpdate(BigDecimal idesquema,
			String esquema, BigDecimal codigo_md, String observaciones,
			Hashtable htDetalleEsquema, Hashtable htDeleteDetalle,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	/**
	 * Metodos para la entidad: produccionEsquemas_Cabe Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Feb 13 09:18:39 GMT-03:00 2007
	 */

	public List getProduccionEsquemas_CabeAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProduccionEsquemas_CabeOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProduccionEsquemas_CabePK(BigDecimal idesquema,
			BigDecimal idempresa) throws RemoteException;

	public BigDecimal esquemaHaveMovProd(BigDecimal idesquema,
			BigDecimal idempresa) throws RemoteException;

	public String produccionEsquemas_CabeDelete(BigDecimal idesquema,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public String produccionEsquemas_CabeCreate(String esquema,
			BigDecimal codigo_md, String observaciones, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String produccionEsquemas_CabeCreateOrUpdate(BigDecimal idesquema,
			String esquema, BigDecimal codigo_md, String observaciones,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String produccionEsquemas_CabeUpdate(BigDecimal idesquema,
			String esquema, BigDecimal codigo_md, String observaciones,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// ESTO SE HIZO PARA TRAER EL LOV DE MEDIDAS
	public List getProduccionMedidasLOV(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// ESTO SE HIZO PARA TRAER EL LOV CONTADOR
	public List getProduccionContadorLOV(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// PRODUCCION ESQUEMAS DETALLE

	/**
	 * Metodos para la entidad: produccionEsquemas_Deta Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Feb 13 09:19:28 GMT-03:00 2007
	 */

	public List getProduccionEsquemas_DetaAll(long limit, long offset,
			BigDecimal idesquema, BigDecimal idempresa) throws RemoteException;

	public List getProduccionEsquemas_DetaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProduccionEsquemas_DetaPK(BigDecimal idesquema,
			BigDecimal idempresa) throws RemoteException;

	public String produccionEsquemas_DetaDelete(BigDecimal idesquema,
			BigDecimal renglon, BigDecimal idempresa) throws RemoteException;

	public String produccionEsquemas_DetaCreate(BigDecimal idesquema,
			BigDecimal renglon, String tipo, String codigo_st,
			BigDecimal cantidad, String entsal, BigDecimal codigo_dt,
			BigDecimal margen_error, String imprime, String edita,
			String formula, String reutiliza, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String produccionEsquemas_DetaCreateOrUpdate(BigDecimal idesquema,
			BigDecimal renglon, String tipo, String codigo_st,
			BigDecimal cantidad, String entsal, BigDecimal codigo_dt,
			BigDecimal margen_error, String imprime, String edita,
			String formula, String reutiliza, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String produccionEsquemas_DetaUpdate(BigDecimal idesquema,
			BigDecimal renglon, String tipo, String codigo_st, Double cantidad,
			String entsal, BigDecimal codigo_dt, Double margen_error,
			String imprime, String edita, String formula, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// ESTO SE HIZO PARA TRAER EL LOV DE RECETA
	public List getProduccionRecetaLOV(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// ESTO SE HIZO PARA TRAER EL LOV DE DEPOSITO
	public List getProduccionDepositoLOV(String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// PRODUCCION RECURSOS
	public List getProduccionrecursosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProduccionrecursosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProduccionrecursosPK(BigDecimal idrecurso,
			BigDecimal idempresa) throws RemoteException;

	public String ProduccionrecursosDelete(BigDecimal idrecurso,
			BigDecimal idempresa) throws RemoteException;

	public String ProduccionrecursosCreate(String recurso,
			BigDecimal codigo_md, Double costo, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String ProduccionrecursosCreateOrUpdate(BigDecimal idrecurso,
			String recurso, BigDecimal codigo_md, Double costo,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String ProduccionrecursosUpdate(BigDecimal idrecurso,
			String recurso, BigDecimal codigo_md, Double costo,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// RECETAS DE PRODUCCION:
	// cabecera
	public List getProduccionRecetas_cabeAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProduccionRecetas_cabeOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProduccionRecetas_cabePK(String codigo_st,
			BigDecimal idempresa) throws RemoteException;

	public String produccionRecetas_cabeDelete(String codigo_st,
			BigDecimal idempresa) throws RemoteException;

	public String produccionRecetas_cabeCreate(String codigo_st, String receta,
			BigDecimal codigo_md, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String produccionRecetas_cabeCreateOrUpdate(String codigo_st,
			String receta, BigDecimal codigo_md, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String produccionRecetas_cabeUpdate(String codigo_st, String receta,
			BigDecimal codigo_md, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// detalle
	public List getProduccionRecetas_detaAll(String codigo_st_cabe, long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getProduccionRecetas_detaOcu(String codigo_st_cabe, long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getProduccionRecetas_detaPK(String codigo_st_cabe,
			String codigo_st, BigDecimal idempresa) throws RemoteException;

	public String produccionRecetas_detaDelete(String codigo_st_cabe,
			String codigo_st, BigDecimal idempresa) throws RemoteException;

	public String produccionRecetas_detaCreate(String codigo_st_cabe,
			String codigo_st, String tipo_rd, Double cantidad_rd,
			String imprime, Double margen_error_rd, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String produccionRecetas_detaCreateOrUpdate(String codigo_st_cabe,
			String codigo_st, String tipo_rd, Double cantidad_rd,
			String imprime, Double margen_error_rd, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String produccionRecetas_detaUpdate(String codigo_st_cabe,
			String codigo_st, String tipo_rd, Double cantidad_rd,
			String imprime, Double margen_error_rd, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// ORDENES DE PRODUCCION

	/**
	 * Metodos para la entidad: produccionMovProdu Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Feb 21 13:30:16 GMT-03:00 2007
	 */

	public String produccionOrdenProdCreate(BigDecimal idesquema,
			BigDecimal idcliente, BigDecimal cantre_op, BigDecimal cantest_op,
			java.sql.Date fecha_prometida, java.sql.Date fecha_emision,
			String observaciones, String codigo_st, BigDecimal idcontador,
			BigDecimal nrointerno, BigDecimal idpedido_regalos_cabe,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public String produccionOrdenProdUpdate(BigDecimal idop,
			BigDecimal idesquema, BigDecimal idcliente, BigDecimal cantre_op,
			BigDecimal cantest_op, java.sql.Date fecha_prometida,
			java.sql.Date fecha_emision, String observaciones,
			String codigo_st, BigDecimal idcontador, BigDecimal nrointerno,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public List getProduccionMovProduAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProduccionMovProduOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProduccionMovProduFiltro(long limit, long offset,
			String filtro, BigDecimal idempresa) throws RemoteException;

	public List getProduccionMovProduPendientesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProduccionMovProduPendientesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// 20101117-EJV

	public List getProduccionMovProduRegalosPendientesAll(long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getProduccionMovProduRegalosPendientesOcu(long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// <--

	public List getProduccionMovProduPK(BigDecimal idop, BigDecimal idempresa)
			throws RemoteException;

	public List getProduccionMovProduParcialesPK(BigDecimal idop,
			BigDecimal idempresa) throws RemoteException;

	public String produccionMovProduDelete(BigDecimal idop, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public String produccionMovProduCreate(BigDecimal idesquema,
			BigDecimal idcliente, BigDecimal cantre_op, BigDecimal cantest_op,
			java.sql.Date fecha_prometida, java.sql.Date fecha_emision,
			String observaciones, String codigo_st, BigDecimal idcontador,
			BigDecimal nrointerno, BigDecimal idpedido_regalos_cabe,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String produccionMovProduCreateOrUpdate(BigDecimal idop,
			BigDecimal idesquema, BigDecimal idcliente, BigDecimal cantre_op,
			BigDecimal cantest_op, java.sql.Date fecha_prometida,
			java.sql.Date fecha_emision, String observaciones,
			String codigo_st, BigDecimal idcontador, BigDecimal nrointerno,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String produccionMovProduUpdate(BigDecimal idop,
			BigDecimal idesquema, BigDecimal idcliente, BigDecimal cantre_op,
			BigDecimal cantest_op, java.sql.Date fecha_prometida,
			java.sql.Date fecha_emision, String observaciones,
			String codigo_st, BigDecimal idcontador, BigDecimal nrointerno,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// ALTA DE ORDENES DE PRODUCCION
	public List getProduccionprogramaauxAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProduccionprogramaauxOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getProduccionprogramaauxPK(BigDecimal idopaux,
			BigDecimal idempresa) throws RemoteException;

	public String produccionprogramaauxDelete(BigDecimal idopaux,
			BigDecimal idempresa) throws RemoteException;

	public String produccionprogramaauxCreate(String codigo_st,
			Double cantidad, Timestamp fecha_prom, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String produccionprogramaauxCreateOrUpdate(BigDecimal idopaux,
			String codigo_st, Double cantidad, Timestamp fecha_prom,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String produccionprogramaauxUpdate(BigDecimal idopaux,
			String codigo_st, Double cantidad, Timestamp fecha_prom,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public long getTotalReceteDetalle(String codigo_st_cabe,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vArticulosEsquemas Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Feb 14 10:31:16 GMT-03:00 2007
	 */

	public List getVArticulosEsquemasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getVArticulosEsquemasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vLovArticulosProduccion Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Mon Feb 19 11:56:26 GMT-03:00 2007
	 */

	public List getVLovArticulosProduccionAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getVLovArticulosProduccionOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vCalculoNecesidadTotales Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Mon Feb 19 17:09:56 GMT-03:00 2007
	 * 
	 */

	public List getVCalculoNecesidadTotalesPK(BigDecimal idesquema,
			BigDecimal cantidad, BigDecimal nivel, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: vCalculoNecesidadTotales Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Mon Feb 19 17:09:56 GMT-03:00 2007.
	 * Descripcion: devuelve para un esquema todos los items que lo componen,
	 * recuperando de forma anidada los datos que correspondan a esquemas que
	 * compongan al esquema en cuestion.
	 * 20101117-EJV-Se.agrego.parametro-tipoPedido
	 * -con.el.fin.de.acceder.tambien.
	 * a.la.vista.VCALCULONECESIDADTOTALESREGALOS.
	 */

	public List getRecursividadEsquema(BigDecimal idesquema,
			BigDecimal cantidad, int nivel, String tipoPedido,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vEsquemaProducto Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Feb 21 16:58:59 GMT-03:00 2007
	 */

	public List getVEsquemaProductoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getVEsquemaProductoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: produccionMovProdu_Deta Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Feb 21 13:31:14 GMT-03:00 2007
	 */

	public List getProduccionMovProdu_DetaPK(BigDecimal idop,
			BigDecimal idempresa) throws RemoteException;

	public String produccionMovProdu_DetaDelete(BigDecimal idop,
			BigDecimal idempresa) throws RemoteException;

	public String produccionOrdenProdDetaCreate(BigDecimal idesquema,
			BigDecimal idop, BigDecimal cantidad_cal, Timestamp fechaaltaorden,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String produccionGenerarExplosionOP(String[] idop,
			String[] cantidadparcial, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	// EJV-20101118 -->
	public String produccionGenerarExplosionOPRegalos(String[] idop,
			String[] cantidadparcial, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	// <--

	/**
	 * AUTOR: EJV ............................................................
	 * FECHA: 20071213 .......................................................
	 * MOTIVO:DESARMADO DE PRODUCTOS ELABORADOS...............................
	 */

	public List getEsquemasProductoFinal(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	// --------------------------------------------------------------

	public List getEsquemasProductoFinalOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// --------------------------------------------------------------

	public List getOrdenProduccionAnular(BigDecimal idop, BigDecimal idempresa)
			throws RemoteException;

	// Consulta de Esquema
	public List getConsulataEsquema(String fechadesde, BigDecimal idempresa)
			throws RemoteException;

	// lov esquemas
	public List getEsquemasLovAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getEsquemasLovOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getOPs(String articulo, BigDecimal idempresa)
			throws RemoteException;

	// EJV - 20101115 - MANTIS 602

	public List getProduccionMovProduRegalosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getProduccionMovProduRegalosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;
}
