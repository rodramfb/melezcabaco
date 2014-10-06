package ar.com.syswarp.ejb;


import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.io.BufferedReader;
import java.math.*;

import javax.ejb.Local;

@Local
public interface Clientes  {
	// getTotalEntidad
	public long getTotalEntidad(String entidad, BigDecimal idempresa)
			throws RemoteException;

	// getTotalEntidadOcu
	public long getTotalEntidadOcu(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// getTotalEntidadRelacion
	public long getTotalEntidadRelacion(String entidad, String[] campos,
			String[] ocurrencia) throws RemoteException;

	public long getTotalEntidadFiltro(String entidad, String filtro,
			BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidadFiltro(String entidad, String filtro) throws  RemoteException;
	// getTotalEntidad
	public long getTotalEntidadGlobal(String entidad) throws RemoteException;

	// getTotalEntidadOcu
	public long getTotalEntidadGlobalOcu(String entidad, String[] campos,
			String ocurrencia) throws RemoteException;

	// CATEGORIAS DE CLIENTES
	public List getClientescredcateAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientescredcateOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientescredcatePK(BigDecimal idcredcate,
			BigDecimal idempresa) throws RemoteException;

	public String clientescredcateDelete(BigDecimal idcredcate,
			BigDecimal idempresa) throws RemoteException;

	public String clientescredcateCreate(String credcate, BigDecimal dias_cre,
			Double porcen_cre, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String clientescredcateCreateOrUpdate(BigDecimal idcredcate,
			String credcate, BigDecimal dias_cre, Double porcen_cre,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String clientescredcateUpdate(BigDecimal idcredcate,
			String credcate, BigDecimal dias_cre, Double porcen_cre,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// TIPOS DE CLIENTES
	public List getClientestipoclieAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientestipoclieOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// 20110907 - EJV - Mantis 777 -->
	// para todos los correspondienes a un club (ordena por el segundo campo por
	// defecto)
	public List getClientestipoclieXClub(long limit, long offset,
			BigDecimal idclub, BigDecimal idempresa) throws RemoteException;

	public List getClientestipocliePK(BigDecimal idtipoclie,
			BigDecimal idempresa) throws RemoteException;

	public String clientestipoclieDelete(BigDecimal idtipoclie,
			BigDecimal idempresa) throws RemoteException;

	public String clientestipoclieCreate(String tipoclie, BigDecimal idclub,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String clientestipoclieCreateOrUpdate(BigDecimal idtipoclie,
			String tipoclie, BigDecimal idclub, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String clientestipoclieUpdate(BigDecimal idtipoclie,
			String tipoclie, BigDecimal idclub, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// --> 20110711 - EJV - Mantis 727
	public List getClientesClubAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	// CONDICIONES DE VENTA
	public List getClientescondicioAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientescondicioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientescondicioPK(BigDecimal idcondicion,
			BigDecimal idempresa) throws RemoteException;

	public String clientescondicioDelete(BigDecimal idcondicion,
			BigDecimal idempresa) throws RemoteException;

	public String clientescondicioCreate(String condicion,
			BigDecimal cant_dias, BigDecimal cuotas, BigDecimal lapso,
			String fac_cred, BigDecimal ctanetoclie, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String clientescondicioCreateOrUpdate(BigDecimal idcondicion,
			String condicion, BigDecimal cant_dias, BigDecimal cuotas,
			BigDecimal lapso, String fac_cred, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String clientescondicioUpdate(BigDecimal idcondicion,
			String condicion, BigDecimal cant_dias, BigDecimal cuotas,
			BigDecimal lapso, String fac_cred, BigDecimal ctanetoclie,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// EXPRESOS
	public List getClientesexpresosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesexpresosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesexpresosPK(BigDecimal idexpreso, BigDecimal idempresa)
			throws RemoteException;

	public String getClientesExpresosReport(BigDecimal idexpreso,
			String tipopedido, String tiporeport, BigDecimal idempresa)
			throws RemoteException;

	public String clientesexpresosDelete(BigDecimal idexpreso,
			BigDecimal idempresa) throws RemoteException;

	public String clientesexpresosCreate(String expreso, String cuit,
			String direccion, BigDecimal idlocalidad, BigDecimal idtipoiva,
			Double valor_seguro, String report_hojaarmado,
			String report_hojarutafinal, BigDecimal codigo_dt,
			String report_hojarutafinal_reg,

			BigDecimal idmedidabulto, String calculaflete,
			String discriminaflete, BigDecimal valorapagar1bulto,
			BigDecimal valorapagarexcedente, String calculaiva,

			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String clientesexpresosCreateOrUpdate(BigDecimal idexpreso,
			String expreso, String cuit, String direccion,
			BigDecimal idlocalidad, BigDecimal idtipoiva, Double valor_seguro,
			String report_hojaarmado, String report_hojarutafinal,
			BigDecimal codigo_dt, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String clientesexpresosUpdate(BigDecimal idexpreso, String expreso,
			String cuit, String direccion, BigDecimal idlocalidad,
			BigDecimal idtipoiva, Double valor_seguro,
			String report_hojaarmado, String report_hojarutafinal,
			BigDecimal codigo_dt, String report_hojarutafinal_reg,

			BigDecimal idmedidabulto, String calculaflete,
			String discriminaflete, BigDecimal valorapagar1bulto,
			BigDecimal valorapagarexcedente, String calculaiva,

			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// para leventar LOV
	// public List getClientestablaivaAll(long limit, long offset)throws
	// RemoteException;
	// public List getClientestablaivaOcu(long limit, long offset,String
	// ocurrencia) throws RemoteException;

	// TIPOS DE IVA
	public List getClientestablaivaAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientestablaivaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientestablaivaPK(BigDecimal idtipoiva, BigDecimal idempresa)
			throws RemoteException;

	public List getClientestablaivaLetrasAll(BigDecimal idempresa)
			throws RemoteException;

	public String clientestablaivaDelete(BigDecimal idtipoiva,
			BigDecimal idempresa) throws RemoteException;

	public String clientestablaivaCreate(String tipoiva, Double porcent1,
			String descrimina, String desglosa, Double porcent2, String letra,
			BigDecimal ctapromo, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String clientestablaivaCreateOrUpdate(BigDecimal idtipoiva,
			String tipoiva, Double porcent1, String descrimina,
			String desglosa, Double porcent2, String letra,
			BigDecimal ctapromo, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String clientestablaivaUpdate(BigDecimal idtipoiva, String tipoiva,
			Double porcent1, String descrimina, String desglosa,
			Double porcent2, String letra, BigDecimal ctapromo,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// VENDEDORES
	public List getClientesvendedorAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesvendedorOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesvendedorPK(BigDecimal idvendedor,
			BigDecimal idempresa) throws RemoteException;

	public String clientesvendedorDelete(BigDecimal idvendedor,
			BigDecimal idempresa) throws RemoteException;

	public String clientesvendedorCreate(String vendedor, Double comision1,
			Double comision2, String domicilio, String usuarioalt,
			BigDecimal idempresa, BigDecimal sueldobasico,
			String tipoliquidacion, BigDecimal tasadesercion,
			BigDecimal valorasociacion) throws RemoteException;

	public String clientesvendedorCreateOrUpdate(BigDecimal idvendedor,
			String vendedor, Double comision1, Double comision2,
			String domicilio, String usuarioact, BigDecimal idempresa,
			BigDecimal sueldobasico, String tipoliquidacion,
			BigDecimal tasadesercion, BigDecimal valorasociacion)
			throws RemoteException;

	public String clientesvendedorUpdate(BigDecimal idvendedor,
			String vendedor, Double comision1, Double comision2,
			String domicilio, String usuarioact, BigDecimal idempresa,
			boolean reingresar, BigDecimal sueldobasico,
			String tipoliquidacion, BigDecimal tasadesercion,
			BigDecimal valorasociacion) throws RemoteException;

	// ZONAS
	public List getClienteszonasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClienteszonasOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesZonasLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesZonasLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClienteszonasPK(BigDecimal idzona, BigDecimal idempresa)
			throws RemoteException;

	public String clienteszonasDelete(BigDecimal idzona, BigDecimal idempresa)
			throws RemoteException;

	public String clienteszonasCreate(String zona, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String clienteszonasCreateOrUpdate(BigDecimal idzona, String zona,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String clienteszonasUpdate(BigDecimal idzona, String zona,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// LISTAS DE PRECION
	public List getClienteslistasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClienteslistasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClienteslistasPK(BigDecimal idlista, BigDecimal idempresa)
			throws RemoteException;

	public String clienteslistasDelete(BigDecimal idlista, BigDecimal idempresa)
			throws RemoteException;

	public String clienteslistasCreate(String descri_lis, BigDecimal idmoneda,
			String coniva_lis, String concuo_lis, BigDecimal decima_lis,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String clienteslistasCreateOrUpdate(BigDecimal idlista,
			String descri_lis, BigDecimal idmoneda, String coniva_lis,
			String concuo_lis, BigDecimal decima_lis, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String clienteslistasUpdate(BigDecimal idlista, String descri_lis,
			BigDecimal idmoneda, String coniva_lis, String concuo_lis,
			BigDecimal decima_lis, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// para el lov de la tabla global monedas *****
	public List getClientesglobalmonedasAll(long limit, long offset)
			throws RemoteException;

	public List getClientesglobalmonedasOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	// ***********
	/**
	 * Metodos para la entidad: clientesClientes Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Feb 12 11:19:56 ART 2008
	 * 
	 */

	public List getClientesClientesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesClientesOcu(long limit, long offset, String filtro,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesClientesPK(BigDecimal idcliente, BigDecimal idempresa)
			throws RemoteException;

	public List getClientesClientesONE(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	// Recuperar datos para un cliente, para un domicilio.

	public List getClientesClientesDomiPK(BigDecimal idcliente,
			BigDecimal iddomicilio, BigDecimal idempresa)
			throws RemoteException;

	public String clientesClientesDelete(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException, SQLException;

	/*
	 * Nota: se creo la variable resumido debido a que lo necesito para
	 * identificar de donde viene si de la base delta o la base delta_jaso en
	 * delta_jaso hay metodos que no los tengo que grabar por eso esta asi.
	 */
	public String clientesClientesCreate(BigDecimal idcliente, String razon,
			BigDecimal idtipodocumento, BigDecimal nrodocumento,
			BigDecimal idcategoria, String brutos, BigDecimal idtipoiva,
			BigDecimal idcondicion, Double descuento1, Double descuento2,
			Double descuento3, BigDecimal idctaneto, BigDecimal idmoneda,
			BigDecimal idlista, BigDecimal idtipoclie, String observacion,
			Double lcredito, BigDecimal idtipocomp, String autorizado,
			BigDecimal idcredcate, Hashtable htDomicilios,
			Hashtable htTarjetas, String[] idmes, BigDecimal idempresa,
			String usuarioalt, BigDecimal prospecto, BigDecimal resumido,
			BigDecimal sucursalfactura) throws RemoteException, SQLException;

	public String clientesClientesCreateOrUpdate(BigDecimal idcliente,
			String razon, BigDecimal idtipodocumento, BigDecimal nrodocumento,
			String brutos, BigDecimal idtipoiva, BigDecimal idcondicion,
			Double descuento1, Double descuento2, Double descuento3,
			BigDecimal idctaneto, BigDecimal idmoneda, BigDecimal idlista,
			BigDecimal idtipoclie, String observacion, Double lcredito,
			BigDecimal idtipocomp, String autorizado, BigDecimal idcredcate,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/*
	 * Nota: se creo la variable resumido debido a que lo necesito para
	 * identificar de donde viene si de la base delta o la base delta_jaso en
	 * delta_jaso hay metodos que no los tengo que grabar por eso esta asi.
	 */
	public String clientesClientesUpdate(BigDecimal idcliente, String razon,
			BigDecimal idtipodocumento, BigDecimal nrodocumento, String brutos,
			BigDecimal idtipoiva, BigDecimal idcondicion, Double descuento1,
			Double descuento2, Double descuento3, BigDecimal idctaneto,
			BigDecimal idmoneda, BigDecimal idlista, BigDecimal idtipoclie,
			String observacion, Double lcredito, BigDecimal idtipocomp,
			String autorizado, BigDecimal idcredcate, Hashtable htDomicilios,
			Hashtable htTarjetas, BigDecimal idempresa, String usuarioact,
			BigDecimal resumido, BigDecimal sucursalfactura)
			throws RemoteException, SQLException;

	// 20110331 - EJV - Mantis 684 -->
	public String clientesClientesAndClientesPrecargaUpdateDocumento(
			BigDecimal idcliente, BigDecimal idtipodocumento,
			BigDecimal nrodocumento, BigDecimal idempresa, String usuarioact)
			throws RemoteException, SQLException;

	// <--

	// para el lov de vendedores
	// para el lov de la tabla global monedas *****
	public List getClientescobradoresAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientescobradoresOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// para el lov de tipo de comprobante
	public List getClientestipocompAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientestipocompOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// pedidos

	// public String[] pedidosCreate(BigDecimal idcampacabe, BigDecimal
	// idestado,
	// BigDecimal idcliente, BigDecimal idsucursal, BigDecimal idsucuclie,
	// Timestamp fechapedido, BigDecimal idcondicion,
	// BigDecimal idvendedor, BigDecimal idexpreso, BigDecimal comision,
	// BigDecimal ordencompra, String obsarmado, String obsentrega,
	// BigDecimal recargo1, BigDecimal recargo2, BigDecimal recargo3,
	// BigDecimal recargo4, BigDecimal bonific1, BigDecimal bonific2,
	// BigDecimal bonific3, BigDecimal idlista, BigDecimal idmoneda,
	// BigDecimal cotizacion, BigDecimal idtipoiva, BigDecimal totaliva,
	// BigDecimal idprioridad, BigDecimal idzona, BigDecimal idtarjeta,
	// BigDecimal cuotas, String origenpedido, BigDecimal total,
	// String tipopedido, Hashtable htArticulos, String usuarioalt,
	// BigDecimal idempresa, Properties properties)
	// throws RemoteException, SQLException;

	public String[] pedidosNormalesCreate(BigDecimal idcampacabe,
			BigDecimal idestado, BigDecimal idcliente, BigDecimal idsucursal,
			BigDecimal idsucuclie, Timestamp fechapedido,
			BigDecimal idcondicion, BigDecimal idvendedor,
			BigDecimal idexpreso, BigDecimal comision, BigDecimal ordencompra,
			String obsarmado, String obsentrega, BigDecimal recargo1,
			BigDecimal recargo2, BigDecimal recargo3, BigDecimal recargo4,
			BigDecimal bonific1, BigDecimal bonific2, BigDecimal bonific3,
			BigDecimal idlista, BigDecimal idmoneda, BigDecimal cotizacion,
			BigDecimal idtipoiva, BigDecimal totaliva, BigDecimal idprioridad,
			BigDecimal idzona, BigDecimal idtarjeta, BigDecimal cuotas,
			String origenpedido, BigDecimal total, String tipopedido,
			Hashtable htArticulos, BigDecimal idlistaReferidos,
			BigDecimal idanexolocalidad, BigDecimal idpromocion,
			BigDecimal idventaespecial, java.sql.Date fechafactura,
			String usuarioalt, BigDecimal idempresa, Properties properties)
			throws RemoteException, SQLException;

	public String[] pedidosRegalosCreate(BigDecimal idcampacabe,
			BigDecimal idpedido_regalos_cabe, BigDecimal idestado,
			BigDecimal idcliente, BigDecimal idsucursal, BigDecimal idsucuclie,
			Timestamp fechapedido, BigDecimal idcondicion,
			BigDecimal idvendedor, BigDecimal idexpreso, BigDecimal comision,
			BigDecimal ordencompra, String obsarmado, String obsentrega,
			BigDecimal recargo1, BigDecimal recargo2, BigDecimal recargo3,
			BigDecimal recargo4, BigDecimal bonific1, BigDecimal bonific2,
			BigDecimal bonific3, BigDecimal idlista, BigDecimal idmoneda,
			BigDecimal cotizacion, BigDecimal idtipoiva, BigDecimal totaliva,
			BigDecimal idprioridad, BigDecimal idzona, BigDecimal idtarjeta,
			BigDecimal cuotas, String origenpedido, BigDecimal total,
			String tipopedido, Hashtable htArticulos, String transformacion,
			String usuarioalt, BigDecimal idempresa, Properties properties)
			throws RemoteException, SQLException;

	public String[] pedidosRegalosUpdate(BigDecimal idpedido_regalos_cabe,
			BigDecimal idcampacabe, BigDecimal idpedido_regalos_padre,
			BigDecimal idestado, BigDecimal idcliente, BigDecimal idsucursal,
			BigDecimal idsucuclie, Timestamp fechapedido,
			BigDecimal idcondicion, BigDecimal idvendedor,
			BigDecimal idexpreso, BigDecimal comision, BigDecimal ordencompra,
			String obsarmado, String obsentrega, BigDecimal recargo1,
			BigDecimal recargo2, BigDecimal recargo3, BigDecimal recargo4,
			BigDecimal bonific1, BigDecimal bonific2, BigDecimal bonific3,
			BigDecimal idlista, BigDecimal idmoneda, BigDecimal cotizacion,
			BigDecimal idtipoiva, BigDecimal totaliva, BigDecimal idprioridad,
			BigDecimal idzona, BigDecimal idtarjeta, BigDecimal cuotas,
			String origenpedido, BigDecimal total, String tipopedido,
			Hashtable htArticulos, String transformacion, String usuarioalt,
			BigDecimal idempresa, Properties properties)
			throws RemoteException, SQLException;

	/**
	 * Metodos para la entidad: pedidos_Cabe Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Sep 04 11:02:26 GMT-03:00 2008
	 * 
	 */

	public List getPedidosHistoricoClienteAll(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getPedidosHistoricoClienteOcu(long limit, long offset,
			String ocurrencia, BigDecimal idcliente, BigDecimal idempresa)
			throws RemoteException;

	public List getPedidosRegalosClienteAll(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getPedidosRegalosClienteOcu(long limit, long offset,
			String ocurrencia, BigDecimal idcliente, BigDecimal idempresa)
			throws RemoteException;

	public List getPedidosHistoricoClientePK(BigDecimal idpedido_cabe,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getPedidosRegalosHistoricoClientePK(BigDecimal idpedido_cabe,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	// 20110120 - EJV - Mantis - 661 -->

	public List getPedidosEntregasHistoricoClientePK(BigDecimal idpedido_cabe,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	// <--

	public List getPedidosHistoricoDetalle(BigDecimal idpedido_cabe,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidosRegalosHistoricoDetalle(BigDecimal idpedido_cabe,
			BigDecimal idempresa) throws RemoteException;

	// 20110120 - EJV - Mantis - 661 -->

	public List getPedidosEntregasHistoricoDetalle(BigDecimal idpedido_cabe,
			BigDecimal idempresa) throws RemoteException;

	// <--
	public List getPedidosRegalosDetalleEntrega(
			BigDecimal idpedido_regalos_entrega_cabe, BigDecimal idempresa)
			throws RemoteException;

	public List getPedidosRegalosDetalleEntregaPK(BigDecimal idpedido_cabe,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public boolean isClienteConPedidosPendientes(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	// lov clientes
	public List getClientesclientesLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesclientesLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// lov clientes
	public List getClientesclientesIngMovLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesclientesIngMovLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// lov sucursal
	public List getSucursalAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getSucursalOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	// lov sucucliente
	public List getSucuclieAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getSucuclieOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	// Consultas de cuentas corrientes
	public List getCtaCteCliente(BigDecimal idcliente, String tipo)
			throws RemoteException;

	public Double getCtaCteClienteSaldo(BigDecimal idcliente, String tipo)
			throws RemoteException;

	// Consultas de saldos de clientes
	public List getClientesConSaldo(String fechahasta, int orderby)
			throws RemoteException;

	public Double getClientesConSaldo(String fechahasta) throws RemoteException;

	// LOCALIDADES
	public List getGlobalLocalidadesAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalLocalidadesOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	// EJV -20100108 - Visualizacion con Obsequios - Mantis 478
	// ---->

	// para todo (ordena por el segundo campo por defecto)
	public List getGlobalLocalidadesObseqAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getGlobalLocalidadesObseqOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getGlobalLocalidadesObseqPK(BigDecimal idlocalidad,
			BigDecimal idempresa) throws RemoteException;

	// <----

	public List getGlobalLocalidadesPK(BigDecimal idlocalidad)
			throws RemoteException;

	public String globalLocalidadesDelete(BigDecimal idlocalidad)
			throws RemoteException;

	public String[] globalLocalidadesCreate(String localidad,
			BigDecimal idprovincia, String cpostal, BigDecimal idtipoiva,
			BigDecimal idlocalidadbaco, String usuarioalt)
			throws RemoteException;

	public String globalLocalidadesCreateOrUpdate(BigDecimal idlocalidad,
			String localidad, BigDecimal idprovincia, String cpostal,
			BigDecimal idtipoiva, BigDecimal idlocalidadbaco, String usuarioact)
			throws RemoteException;

	public String globalLocalidadesUpdate(BigDecimal idlocalidad,
			String localidad, BigDecimal idprovincia, String cpostal,
			BigDecimal idtipoiva, BigDecimal idlocalidadbaco, String usuarioact)
			throws RemoteException;

	// lov provincia
	public List getClientesprovinciaLovAll(long limit, long offset)
			throws RemoteException;

	public List getClientesprovinciaLovOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	// Tipos Tarjetas
	public List getClientetipostarjetasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientetipostarjetasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientetipostarjetasPK(BigDecimal idtipotarjeta,
			BigDecimal idempresa) throws RemoteException;

	public String clientetipostarjetasDelete(BigDecimal idtipotarjeta,
			BigDecimal idempresa) throws RemoteException;

	public String clientetipostarjetasCreate(String tipotarjeta,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String clientetipostarjetasCreateOrUpdate(BigDecimal idtipotarjeta,
			String tipotarjeta, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String clientetipostarjetasUpdate(BigDecimal idtipotarjeta,
			String tipotarjeta, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// tarjetas de credito marca
	public List getClientetarjetascreditomarcasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientetarjetascreditomarcasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientetarjetascreditomarcasPK(BigDecimal idtarjetacredito,
			BigDecimal idempresa) throws RemoteException;

	public String clientetarjetascreditomarcasDelete(
			BigDecimal idtarjetacredito, BigDecimal idempresa)
			throws RemoteException;

	public String clientetarjetascreditomarcasCreate(String tarjetacredito,
			String formato, String metodoasociado, String filtroarchivo,
			String formulavalidacion, String usuarioalt, BigDecimal idempresa,
			BigDecimal coddigitovermarca) throws RemoteException;

	public String clientetarjetascreditomarcasCreateOrUpdate(
			BigDecimal idtarjetacredito, String tarjetacredito, String formato,
			String metodoasociado, String filtroarchivo,
			String formulavalidacion, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String clientetarjetascreditomarcasUpdate(
			BigDecimal idtarjetacredito, String tarjetacredito, String formato,
			String metodoasociado, String filtroarchivo,
			String formulavalidacion, String usuarioact, BigDecimal idempresa,
			BigDecimal coddigitovermarca) throws RemoteException;

	/**
	 * Metodos para la entidad: clienteTarjetasCredito Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Feb 14 13:43:20 ART 2008
	 * 
	 */

	public List getClienteTarjetasCreditoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClienteTarjetasClienteActivas(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getClienteTarjetasCliente(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getClienteTarjetasCreditoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClienteTarjetasCreditoPK(BigDecimal idtarjeta,
			BigDecimal idempresa) throws RemoteException;

	public String clienteTarjetasCreditoDelete(BigDecimal idtarjeta,
			BigDecimal idempresa) throws RemoteException;

	public String clienteTarjetasCreditoCreate(BigDecimal idtarjetacredito,
			BigDecimal idcliente, BigDecimal idtipotarjeta, String nrotarjeta,
			String nrocontrol, Timestamp fecha_emision,
			Timestamp fecha_vencimiento, String titular, BigDecimal orden,
			String activa, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String clienteTarjetasCreditoCreateOrUpdate(BigDecimal idtarjeta,
			BigDecimal idtarjetacredito, BigDecimal idcliente,
			BigDecimal idtipotarjeta, String nrotarjeta, String nrocontrol,
			Timestamp fecha_emision, Timestamp fecha_vencimiento,
			String titular, BigDecimal orden, String activa,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String clienteTarjetasCreditoUpdate(BigDecimal idtarjeta,
			BigDecimal idtarjetacredito, BigDecimal idcliente,
			BigDecimal idtipotarjeta, String nrotarjeta, String nrocontrol,
			Timestamp fecha_emision, Timestamp fecha_vencimiento,
			String titular, BigDecimal orden, String activa,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/*
	 * // tarjetas de credito
	 * 
	 * public List getClientetarjetascreditoAll(long limit, long offset,
	 * BigDecimal idempresa) throws RemoteException;
	 * 
	 * public List getClientetarjetascreditoOcu(long limit, long offset, String
	 * ocurrencia, BigDecimal idempresa) throws RemoteException;
	 * 
	 * public List getClientetarjetascreditoPK(BigDecimal idtarjeta, BigDecimal
	 * idempresa) throws RemoteException;
	 * 
	 * public String clientetarjetascreditoDelete(BigDecimal idtarjeta,
	 * BigDecimal idempresa) throws RemoteException;
	 * 
	 * public String clientetarjetascreditoCreate(BigDecimal idtarjetacredito,
	 * BigDecimal idcliente, BigDecimal idtipotarjeta, String nrotarjeta, String
	 * nrocontrol, Timestamp fecha_emision, Timestamp fecha_vencimiento, String
	 * titular, BigDecimal orden, String activa, String usuarioalt, BigDecimal
	 * idempresa) throws RemoteException;
	 * 
	 * public String clientetarjetascreditoCreateOrUpdate(BigDecimal idtarjeta,
	 * BigDecimal idtarjetacredito, BigDecimal idcliente, BigDecimal
	 * idtipotarjeta, String nrotarjeta, String nrocontrol, Timestamp
	 * fecha_emision, Timestamp fecha_vencimiento, String titular, BigDecimal
	 * orden, String activa, String usuarioact, BigDecimal idempresa) throws
	 * RemoteException;
	 * 
	 * public String clientetarjetascreditoUpdate(BigDecimal idtarjeta,
	 * BigDecimal idtarjetacredito, BigDecimal idcliente, BigDecimal
	 * idtipotarjeta, String nrotarjeta, String nrocontrol, Timestamp
	 * fecha_emision, Timestamp fecha_vencimiento, String titular, BigDecimal
	 * orden, String activa, String usuarioact, BigDecimal idempresa) throws
	 * RemoteException;
	 */

	// tarjetas marcas lov
	public List getClientesTarjetaMarcasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesTarjetaMarcasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// tipo tarjetas
	public List getClientesTipoTarjetasLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesTipoTarjetasLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// detalle tarjeta del cliente
	// filtro por ocurrencia y por cliente
	public List getClientesDetalleTarjetaOcu(String idcliente, long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getClientesDetalleTarjetaAll(String idcliente, long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	// Maximo Numero de Cliente
	public BigDecimal getMaximoCliente(BigDecimal idempresa)
			throws RemoteException;

	// Clientes Sucursales
	public List getClientesClieSucAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesClieSucOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesClieSucPK(BigDecimal idsucursal, BigDecimal idempresa)
			throws RemoteException;

	public String clientesClieSucDelete(BigDecimal idsucursal,
			BigDecimal idempresa) throws RemoteException;

	public String clientesClieSucCreate(BigDecimal idcliente,
			BigDecimal idtiposucursal, String nombre_suc, String domici_suc,
			String telefo_suc, String idlocalidad, String idvendedor,
			String idcobrador, String idexpreso, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String clientesClieSucCreateOrUpdate(BigDecimal idsucursal,
			BigDecimal idcliente, BigDecimal idtiposucursal, String nombre_suc,
			String domici_suc, String telefo_suc, String idlocalidad,
			String idvendedor, String idcobrador, String idexpreso,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String clientesClieSucUpdate(BigDecimal idsucursal,
			BigDecimal idcliente, BigDecimal idtiposucursal, String nombre_suc,
			String domici_suc, String telefo_suc, String idlocalidad,
			String idvendedor, String idcobrador, String idexpreso,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// para el lov de sucursales
	public List getClientessucursalesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientessucursalesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// filtro por ocurrencia y por cliente
	public List getClientesDetalleDomicilioOcu(String idcliente, long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// traigo el total de registros por cliente
	public List getClientesDetalleDomicilioAll(String idcliente, long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	// clietes estados
	public List getClientesestadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesestadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesestadosPK(BigDecimal idestado, BigDecimal idempresa)
			throws RemoteException;

	public String clientesestadosDelete(BigDecimal idestado,
			BigDecimal idempresa) throws RemoteException;

	public String clientesestadosCreate(String estado, String fechasn,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String clientesestadosCreateOrUpdate(BigDecimal idestado,
			String estado, String fechasn, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String clientesestadosUpdate(BigDecimal idestado, String estado,
			String fechasn, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// motivos
	public List getClientesmotivosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesmotivosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesmotivosPK(BigDecimal idmotivo, BigDecimal idempresa)
			throws RemoteException;

	public String clientesmotivosDelete(BigDecimal idmotivo,
			BigDecimal idempresa) throws RemoteException;

	public String clientesmotivosCreate(String motivo, BigDecimal idestado,
			String observaciones, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String clientesmotivosCreateOrUpdate(BigDecimal idmotivo,
			String motivo, BigDecimal idestado, String observaciones,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String clientesmotivosUpdate(BigDecimal idmotivo, String motivo,
			BigDecimal idestado, String observaciones, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// lov estados
	public List getClientesEstadosLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesEstadosLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// estados clientes
	public List getClientesestadosclientesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesestadosclientesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesestadosclientesPK(BigDecimal idestadocliente,
			BigDecimal idempresa) throws RemoteException;

	public String clientesestadosclientesDelete(BigDecimal idestadocliente,
			BigDecimal idempresa) throws RemoteException;

	public String clientesEstadosClientesSetFBajaPK(BigDecimal idcliente,
			BigDecimal idestadocliente, Timestamp fbaja, String usuarioact,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public String clientesestadosclientesCreate(BigDecimal idcliente,
			BigDecimal idestado, BigDecimal idmotivo, java.sql.Date fechadesde,
			java.sql.Date fechahasta, Timestamp fbaja, String observaciones,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public String setEstadoClienteCreate(BigDecimal idestadocliente,
			BigDecimal idcliente, BigDecimal idestado, BigDecimal idmotivo,
			java.sql.Date fechadesde, java.sql.Date fhasta, Timestamp fbaja,
			String observaciones, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public String clientesestadosclientesCreateOrUpdate(
			BigDecimal idestadocliente, BigDecimal idcliente,
			BigDecimal idestado, String idmotivo, java.sql.Date fechadesde,
			java.sql.Date fechahasta, Timestamp fbaja, String observaciones,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String clientesestadosclientesUpdate(BigDecimal idestadocliente,
			BigDecimal idcliente, BigDecimal idestado, String idmotivo,
			java.sql.Date fechadesde, java.sql.Date fechahasta,
			Timestamp fbaja, String observaciones, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String setClientesEstadosHoyCliente(BigDecimal idcliente,
			BigDecimal idempresa, Connection conn) throws RemoteException;

	public BigDecimal getEstadoActualCliente(BigDecimal idcliente,
			BigDecimal idempresa, Connection conn) throws RemoteException;

	public BigDecimal getIdestadoclienteActualCliente(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	// lov motivos
	public List getClientesMotivosLovAll(BigDecimal idestado, long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getClientesMotivosLovOcu(BigDecimal idestado, long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// pedidos estados
	public List getPedidosestadosAll(long limit, long offset)
			throws RemoteException;

	public List getPedidosestadosOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getPedidosestadosPK(BigDecimal idestado) throws RemoteException;

	public String pedidosestadosDelete(BigDecimal idestado)
			throws RemoteException;

	public String pedidosestadosCreate(String estado, String usuarioalt)
			throws RemoteException;

	public String pedidosestadosCreateOrUpdate(BigDecimal idestado,
			String estado, String usuarioact) throws RemoteException;

	public String pedidosestadosUpdate(BigDecimal idestado, String estado,
			String usuarioact) throws RemoteException;

	// lov pedido estado
	public List getPedidosEstadosAll(long limit, long offset)
			throws RemoteException;

	public List getPedidosEstadosOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	/**
	 * Metodos para la entidad: vPedidosEstado Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Apr 16 08:42:22 GYT 2009
	 */

	public List getVPedidosEstadoAll(long limit, long offset, String filtro,
			BigDecimal idempresa) throws RemoteException;

	public List getVPedidosEstadoPreconfAll(long limit, long offset,
			BigDecimal idestado, String tipopedido, BigDecimal idempresa)
			throws RemoteException;

	public List getVPedidosEstadoOcu(long limit, long offset,
			BigDecimal idestado, String tipopedido, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidosRegalosAdministrarAll(long limit, long offset,
			BigDecimal idestado, BigDecimal idempresa) throws RemoteException;

	public List getPedidosRegalosAdministrarOcu(long limit, long offset,
			BigDecimal idestado, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getVPedidosEstadoPK(BigDecimal idpedido_cabe,
			String tipopedido, BigDecimal idempresa) throws RemoteException;

	public List getVPedidosRegalosEstadoPK(BigDecimal idpedido_cabe,
			String tipopedido, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: pedidosPrioridades Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Mar 29 11:25:48 ART 2007
	 * 
	 */

	public List getPedidosPrioridadesAll(long limit, long offset)
			throws RemoteException;

	// pedidos detalle
	public List getPedidos_detaDetalleAll(String idpedido_cabe, long limit,
			long offset) throws RemoteException;

	public List getPedidos_detaDetalleOcu(String idpedido_cabe, long limit,
			long offset, String ocurrencia) throws RemoteException;

	public List getPedidos_detaDetallePK(BigDecimal idpedido_deta)
			throws RemoteException;

	public String pedidos_detaDetalleDelete(BigDecimal idpedido_deta)
			throws RemoteException;

	public String pedidos_detaDetalleCreate(BigDecimal idpedido_cabe,
			String codigo_st, Timestamp fecha, BigDecimal renglon,
			Double precio, Double saldo, Double cantidad, Double bonific,
			BigDecimal codigo_md, Double cantuni, BigDecimal codigo_dt,
			String entrega, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String pedidos_detaDetalleCreateOrUpdate(BigDecimal idpedido_deta,
			BigDecimal idpedido_cabe, String codigo_st, Timestamp fecha,
			BigDecimal renglon, Double precio, Double saldo, Double cantidad,
			Double bonific, BigDecimal codigo_md, Double cantuni,
			BigDecimal codigo_dt, String entrega, String usuarioact)
			throws RemoteException;

	public String pedidos_detaDetalleUpdate(BigDecimal idpedido_deta,
			BigDecimal idpedido_cabe, String codigo_st, Timestamp fecha,
			BigDecimal renglon, Double precio, Double saldo, Double cantidad,
			Double bonific, BigDecimal codigo_md, Double cantuni,
			BigDecimal codigo_dt, String entrega, String usuarioact)
			throws RemoteException;

	// traigo el total por cliente
	public long getPedidos_detaDetalleAllTotal(String idpedido_cabe)
			throws RemoteException;

	// traigo el total de registros por ocurrencia y cliente
	public long getPedidos_detaDetalleOcuTotal(String idpedido_cabe,
			String[] campos, String ocurrencia) throws RemoteException;

	// replica sas
	public String pedidosSASCreate(BigDecimal idestado, BigDecimal idcliente,
			BigDecimal idsucursal, BigDecimal idsucuclie,
			Timestamp fechapedido, BigDecimal idcondicion,
			BigDecimal idvendedor, BigDecimal idexpreso, BigDecimal comision,
			String ordencompra, String obseraviones, BigDecimal recargo1,
			BigDecimal recargo2, BigDecimal recargo3, BigDecimal recargo4,
			BigDecimal bonific1, BigDecimal bonific2, BigDecimal bonific3,
			BigDecimal idlista, BigDecimal idmoneda, BigDecimal cotizacion,
			BigDecimal idtipoiva, BigDecimal totaliva, BigDecimal idprioridad,
			Hashtable htArticulos, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public List getPedidosPrioridadesSASAll(long limit, long offset)
			throws RemoteException;

	// replica TM

	/*
	 * 20090330 - EJV / public String pedidosTMCreate(BigDecimal idestado,
	 * BigDecimal idcliente, BigDecimal idsucursal, BigDecimal idsucuclie,
	 * Timestamp fechapedido, BigDecimal idcondicion, BigDecimal idvendedor,
	 * BigDecimal idexpreso, BigDecimal comision, String ordencompra, String
	 * obseraviones, BigDecimal recargo1, BigDecimal recargo2, BigDecimal
	 * recargo3, BigDecimal recargo4, BigDecimal bonific1, BigDecimal bonific2,
	 * BigDecimal bonific3, BigDecimal idlista, BigDecimal idmoneda, BigDecimal
	 * cotizacion, BigDecimal idtipoiva, BigDecimal totaliva, BigDecimal
	 * idprioridad, Hashtable htArticulos, String usuarioalt, BigDecimal
	 * idempresa) throws RemoteException, SQLException;
	 */

	public List getPedidosPrioridadesTMAll(long limit, long offset)
			throws RemoteException;

	// replica Distribuidores
	public String pedidosDistribuidoresCreate(BigDecimal idestado,
			BigDecimal idcliente, BigDecimal idsucursal, BigDecimal idsucuclie,
			Timestamp fechapedido, BigDecimal idcondicion,
			BigDecimal idvendedor, BigDecimal idexpreso, BigDecimal comision,
			String ordencompra, String obseraviones, BigDecimal recargo1,
			BigDecimal recargo2, BigDecimal recargo3, BigDecimal recargo4,
			BigDecimal bonific1, BigDecimal bonific2, BigDecimal bonific3,
			BigDecimal idlista, BigDecimal idmoneda, BigDecimal cotizacion,
			BigDecimal idtipoiva, BigDecimal totaliva, BigDecimal idprioridad,
			Hashtable htArticulos, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public List getPedidosPrioridadesDistribuidoresAll(long limit, long offset)
			throws RemoteException;

	// TM campa Cabecera
	public List getBacotmcampacabeAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacotmcampacabeOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacotmcampacabePK(BigDecimal idcampacabe,
			BigDecimal idempresa) throws RemoteException;

	public String bacotmcampacabeDelete(BigDecimal idcampacabe,
			BigDecimal idempresa) throws RemoteException;

	public String bacotmcampacabeCreate(String campacabe, Timestamp fdesde,
			Timestamp fhasta, BigDecimal descuento, BigDecimal total,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String bacotmcampacabeCreateOrUpdate(BigDecimal idcampacabe,
			String campacabe, Timestamp fdesde, Timestamp fhasta,
			BigDecimal descuento, BigDecimal total, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String bacotmcampacabeUpdate(BigDecimal idcampacabe,
			String campacabe, Timestamp fdesde, Timestamp fhasta,
			BigDecimal descuento, BigDecimal total, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// TM campa Detalle
	public List getBacotmcampadetaAll(String idcampacabe, long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getBacotmcampadetaOcu(String idcampacabe, long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getBacotmcampadetaPK(BigDecimal idcampadeta,
			BigDecimal idempresa) throws RemoteException;

	public String bacotmcampadetaDelete(BigDecimal idcampadeta,
			BigDecimal idempresa) throws RemoteException;

	public String bacotmcampadetaCreate(BigDecimal idcampacabe,
			String codigo_st, String observaciones, BigDecimal stock_estimado,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String bacotmcampadetaCreateOrUpdate(BigDecimal idcampadeta,
			BigDecimal idcampacabe, String codigo_st, String observaciones,
			BigDecimal stock_estimado, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String bacotmcampadetaUpdate(BigDecimal idcampadeta,
			BigDecimal idcampacabe, String codigo_st, String observaciones,
			BigDecimal stock_estimado, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public long getBacotmcampadetaAllTotal(String idcampacabe,
			BigDecimal idempresa) throws RemoteException;

	// traigo el total de registros por ocurrencia y cliente
	public long getBacotmcampadetaOcuTotal(String idcampacabe, String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// para el lov de articulos
	public List getClientesArticulosLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesArticulosLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// TM categorias Socios
	public List getTMCategoriasSociosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getTMCategoriasSociosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getTMCategoriasSociosPK(BigDecimal idcategoriasocio,
			BigDecimal idempresa) throws RemoteException;

	public String TMCategoriasSociosDelete(BigDecimal idcategoriasocio,
			BigDecimal idempresa) throws RemoteException;

	public String TMCategoriasSociosCreate(String categoriasocio,
			String observaciones, Double adidesc, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String TMCategoriasSociosCreateOrUpdate(BigDecimal idcategoriasocio,
			String categoriasocio, String observaciones, Double adidesc,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String TMCategoriasSociosUpdate(BigDecimal idcategoriasocio,
			String categoriasocio, String observaciones, Double adidesc,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// TM motivos
	public List getBacoTmMotivosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoTmMotivosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoTmMotivosPK(BigDecimal idmotivo, BigDecimal idempresa)
			throws RemoteException;

	public String bacoTmMotivosDelete(BigDecimal idmotivo, BigDecimal idempresa)
			throws RemoteException;

	public String bacoTmMotivosCreate(String motivo, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String bacoTmMotivosCreateOrUpdate(BigDecimal idmotivo,
			String motivo, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String bacoTmMotivosUpdate(BigDecimal idmotivo, String motivo,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// TM categorizaciones
	public List getBacoTmCategorizacionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoTmCategorizacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacoTmCategorizacionesClienteAll(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoTmCategorizacionesPK(BigDecimal idcategorizacion,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoTmCategoriaActualCliente(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	public String bacoTmCategorizacionesDelete(BigDecimal idcategorizacion,
			BigDecimal idempresa) throws RemoteException;

	public String bacoTmCategorizacionesCreate(BigDecimal idcliente,
			BigDecimal idcategoria, java.sql.Date fhasta, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String bacoTmCategorizacionesCreateOrUpdate(
			BigDecimal idcategorizacion, BigDecimal idcliente,
			BigDecimal idcategoria, java.sql.Date fhasta, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String bacoTmCategorizacionesUpdate(BigDecimal idcategorizacion,
			BigDecimal idcliente, BigDecimal idcategoria, java.sql.Date fhasta,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// lov tm categorizaciones
	public List getClientesCategorizacionesLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesCategorizacionesLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// TM resultados
	public List getBacotmresultadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacotmresultadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacotmresultadosPK(BigDecimal idresultado,
			BigDecimal idempresa) throws RemoteException;

	public String bacotmresultadosDelete(BigDecimal idresultado,
			BigDecimal idempresa) throws RemoteException;

	public String bacotmresultadosCreate(String resultado, String[] idmotivos,
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	public String bacotmresultadosCreateOrUpdate(BigDecimal idresultado,
			String resultado, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String bacotmresultadosUpdate(BigDecimal idresultado,
			String resultado, String[] idmotivos, BigDecimal idempresa,
			String usuarioact) throws RemoteException, SQLException;

	/**
	 * Motivos Resultados Asociar
	 */

	public List getResultadosMotivosAsociar(BigDecimal idresultado,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoTmResultadosMotivosAll(BigDecimal idempresa)
			throws RemoteException;

	// precios
	public List getClientespreciosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientespreciosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientespreciosPK(BigDecimal idprecio, BigDecimal idempresa)
			throws RemoteException;

	public String clientespreciosDelete(BigDecimal idprecio,
			BigDecimal idempresa) throws RemoteException;

	public String clientespreciosCreate(String codigo_st, BigDecimal idlista,
			Double precio_pre, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String clientespreciosCreateOrUpdate(BigDecimal idprecio,
			String codigo_st, BigDecimal idlista, Double precio_pre,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String clientespreciosUpdate(BigDecimal idprecio, String codigo_st,
			BigDecimal idlista, Double precio_pre, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// control si existe la combinacion Articulo Lista update sino insert
	public BigDecimal getClientesArtLista(String codigo_st, BigDecimal idlista)
			throws RemoteException;

	// lov campa�a
	public List getClientesCampanaLovAll(long limit, long offset)
			throws RemoteException;

	public List getClientesCampanaLovOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	// lov resultado
	public List getClientesResultadoLovAll(long limit, long offset)
			throws RemoteException;

	public List getClientesResultadoLovOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	// clientestipossucursales
	public List getClientestipossucursalesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientestipossucursalesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientestipossucursalesPK(BigDecimal idtiposucursal,
			BigDecimal idempresa) throws RemoteException;

	public String clientestipossucursalesDelete(BigDecimal idtiposucursal,
			BigDecimal idempresa) throws RemoteException;

	public String clientestipossucursalesCreate(String tiposucursal,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String clientestipossucursalesCreateOrUpdate(
			BigDecimal idtiposucursal, String tiposucursal, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String clientestipossucursalesUpdate(BigDecimal idtiposucursal,
			String tiposucursal, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// clientescobradores
	public List getClientescobradorAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientescobradorOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientescobradorPK(BigDecimal idcobrador,
			BigDecimal idempresa) throws RemoteException;

	public String clientescobradorDelete(BigDecimal idcobrador,
			BigDecimal idempresa) throws RemoteException;

	public String clientescobradorCreate(String cobrador, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String clientescobradorCreateOrUpdate(BigDecimal idcobrador,
			String cobrador, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String clientescobradorUpdate(BigDecimal idcobrador,
			String cobrador, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: clientesTipoComp Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Jun 14 10:37:09 ART 2007
	 */

	public List getClientesTipoCompAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesTipoCompOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesTipoCompPK(BigDecimal idtipocomp,
			BigDecimal idempresa) throws RemoteException;

	public String clientesTipoCompDelete(BigDecimal idtipocomp)
			throws RemoteException;

	public String clientesTipoCompCreate(BigDecimal tipomov_tc,
			String descri_tc, String cuotas_tc, String vtacont_tc,
			String st_stok_tc, String st_prec_tc, String st_remi_tc,
			String comi_tc, String imprime_tc, BigDecimal bonif_tc,
			String mod_mon_tc, String ingb_tc, BigDecimal contad_tc,
			BigDecimal ctaiva_tc, BigDecimal ctivani_tc, BigDecimal ctgrava_tc,
			BigDecimal ctexent_tc, String ranking_tc, String transpo_tc,
			String bon_x_art, String remdesp_tc, String mod_con_tc,
			String dere1_tc, String reca1_tc, BigDecimal ctare1_tc,
			String recai1_tc, String recgr1_tc, String dere2_tc,
			String reca2_tc, BigDecimal ctare2_tc, String recai2_tc,
			String recgr2_tc, String dere3_tc, String reca3_tc,
			BigDecimal ctare3_tc, String recai3_tc, String recgr3_tc,
			String dere4_tc, String reca4_tc, BigDecimal ctare4_tc,
			String recai4_tc, String recgr4_tc, BigDecimal centr1_tc,
			BigDecimal centr2_tc, String iva_x_art, BigDecimal imp_int_tc,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String clientesTipoCompCreateOrUpdate(BigDecimal idtipocomp,
			BigDecimal tipomov_tc, String descri_tc, String cuotas_tc,
			String vtacont_tc, String st_stok_tc, String st_prec_tc,
			String st_remi_tc, String comi_tc, String imprime_tc,
			BigDecimal bonif_tc, String mod_mon_tc, String ingb_tc,
			BigDecimal contad_tc, BigDecimal ctaiva_tc, BigDecimal ctivani_tc,
			BigDecimal ctgrava_tc, BigDecimal ctexent_tc, String ranking_tc,
			String transpo_tc, String bon_x_art, String remdesp_tc,
			String mod_con_tc, String dere1_tc, String reca1_tc,
			BigDecimal ctare1_tc, String recai1_tc, String recgr1_tc,
			String dere2_tc, String reca2_tc, BigDecimal ctare2_tc,
			String recai2_tc, String recgr2_tc, String dere3_tc,
			String reca3_tc, BigDecimal ctare3_tc, String recai3_tc,
			String recgr3_tc, String dere4_tc, String reca4_tc,
			BigDecimal ctare4_tc, String recai4_tc, String recgr4_tc,
			BigDecimal centr1_tc, BigDecimal centr2_tc, String iva_x_art,
			BigDecimal imp_int_tc, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String clientesTipoCompUpdate(BigDecimal idtipocomp,
			BigDecimal tipomov_tc, String descri_tc, String cuotas_tc,
			String vtacont_tc, String st_stok_tc, String st_prec_tc,
			String st_remi_tc, String comi_tc, String imprime_tc,
			BigDecimal bonif_tc, String mod_mon_tc, String ingb_tc,
			BigDecimal contad_tc, BigDecimal ctaiva_tc, BigDecimal ctivani_tc,
			BigDecimal ctgrava_tc, BigDecimal ctexent_tc, String ranking_tc,
			String transpo_tc, String bon_x_art, String remdesp_tc,
			String mod_con_tc, String dere1_tc, String reca1_tc,
			BigDecimal ctare1_tc, String recai1_tc, String recgr1_tc,
			String dere2_tc, String reca2_tc, BigDecimal ctare2_tc,
			String recai2_tc, String recgr2_tc, String dere3_tc,
			String reca3_tc, BigDecimal ctare3_tc, String recai3_tc,
			String recgr3_tc, String dere4_tc, String reca4_tc,
			BigDecimal ctare4_tc, String recai4_tc, String recgr4_tc,
			BigDecimal centr1_tc, BigDecimal centr2_tc, String iva_x_art,
			BigDecimal imp_int_tc, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: clientesAnexov Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Fri Jun 22 15:59:02 ART 2007
	 */

	public List getClientesAnexovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesAnexovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesAnexovPK(String cuit_an, BigDecimal idempresa)
			throws RemoteException;

	public String clientesAnexovDelete(String cuit_an) throws RemoteException;

	public String clientesAnexovCreate(String cuit_an, String razon_an,
			BigDecimal iva_an, BigDecimal sucur_an, BigDecimal compr_an,
			BigDecimal compro_has, String postal, BigDecimal loca_an,
			BigDecimal pcia_an, BigDecimal tipom_an, BigDecimal inter_an,
			Double impor_an, Timestamp fcha_an, Double com_venta,
			Double com_cobra, BigDecimal com_vende, BigDecimal moneda_an,
			Double cambio_an, String form_an, String tipmovs_an,
			String anulada, String domici_an, BigDecimal expreso_an,
			// 20110621 - EJV - Factuaracion FE-CF-MA -->
			String condicionletra,
			// <--
			BigDecimal idempresa, String usuarioalt, Connection conn)
			throws RemoteException;

	public String clientesAnexovCreateOrUpdate(String cuit_an, String razon_an,
			BigDecimal iva_an, BigDecimal sucur_an, BigDecimal compr_an,
			BigDecimal compro_has, String postal, BigDecimal loca_an,
			BigDecimal pcia_an, BigDecimal tipom_an, BigDecimal inter_an,
			Double impor_an, Timestamp fcha_an, Double com_venta,
			Double com_cobra, BigDecimal com_vende, BigDecimal moneda_an,
			Double cambio_an, String form_an, String tipmovs_an,
			String anulada, String domici_an, BigDecimal expreso_an,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String clientesAnexovUpdate(String cuit_an, String razon_an,
			BigDecimal iva_an, BigDecimal sucur_an, BigDecimal compr_an,
			BigDecimal compro_has, String postal, BigDecimal loca_an,
			BigDecimal pcia_an, BigDecimal tipom_an, BigDecimal inter_an,
			Double impor_an, Timestamp fcha_an, Double com_venta,
			Double com_cobra, BigDecimal com_vende, BigDecimal moneda_an,
			Double cambio_an, String form_an, String tipmovs_an,
			String anulada, String domici_an, BigDecimal expreso_an,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: clientesMovCli Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Dec 13 10:54:31 GMT-03:00 2006
	 */

	public boolean isExisteComprobanteMovCli(BigDecimal sucursal,
			BigDecimal comprob, String tipomovs, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: globalTipoMovimientos Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Aug 09 17:08:30 ART 2007
	 */

	public List getGlobalTipoMovimientosAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalTipoMovimientosOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	public List getGlobalTipoMovimientosPK(BigDecimal tipomov)
			throws RemoteException;

	public String getGTMSigla(BigDecimal tipomov) throws RemoteException;

	/**
	 * =======================================================================
	 * =======================================================================
	 * INICIO ................................................................
	 * EJV ...................................................................
	 * 20070822 ..............................................................
	 * INGRESO DE MOVIMIENTOS DE CLIENTES VERSION PREELIMINAR ................
	 * =======================================================================
	 * =======================================================================
	 * =======================================================================
	 * =======================================================================
	 */

	public String clientesMovimientoClienteCreate(
			BigDecimal idcliente,
			String cuit_an,
			String razon_an,
			String domici_an,
			BigDecimal loca_an,
			BigDecimal pcia_an,
			String postal,
			BigDecimal iva_an,
			Timestamp fechamov,
			BigDecimal sucursal,
			BigDecimal comprob,
			BigDecimal comprob_has,
			BigDecimal tipomov,
			String tipomovs,
			// 20110617 - EJV - Factuaracion FE-CF-MA -->
			String condicionletra,
			// <--
			BigDecimal saldo, BigDecimal importe, BigDecimal cambio,
			BigDecimal moneda, String unamode, String tipocomp,
			BigDecimal condicion, BigDecimal nrointerno, BigDecimal com_venta,
			BigDecimal com_cobra,
			BigDecimal com_vende,
			String anulada,
			BigDecimal retoque,
			BigDecimal expreso,
			BigDecimal sucucli,
			BigDecimal remito,
			BigDecimal credito,
			int ejercicioactivo,
			String observaciones,
			// 20110628 - EJV - Factuaracion FE-CF-MA -->
			BigDecimal por_per,
			// <--
			// 20110930 - EJV - mantis 771 -->
			BigDecimal idtarjeta,
			int cuotas,
			BigDecimal idclub,
			// <--
			// 20121005 - EJV - Mantis 882 -->
			BigDecimal idmotivonc,
			// <--

			Hashtable htCuentasContCli, Hashtable htArticulos,
			Hashtable htIdentificadoresIngresos, String usuarioalt,
			BigDecimal idempresa) throws RemoteException, SQLException;

	/**
	 * Metodos para la entidad: globalTiposDocumentos Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Feb 14 09:17:32 ART 2008
	 */

	public List getClientesDomiciliosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	// domicilio default para un cliente ()
	public List getClientesDomiciliosClienteDefault(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	// Todos los domicilios para un cliente
	public List getClientesDomiciliosCliente(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getClientesDomiciliosClienteOcu(long limit, long offset,
			BigDecimal idcliente, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getClientesDomiciliosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesDomiciliosPK(BigDecimal iddomicilio,
			BigDecimal idempresa) throws RemoteException;

	public String clientesDomiciliosDelete(BigDecimal iddomicilio,
			BigDecimal idempresa) throws RemoteException;

	public String clientesDomiciliosCreate(BigDecimal idcliente,
			BigDecimal idtipodomicilio, String esdefault, String calle,
			String nro, String piso, String depto, BigDecimal idlocalidad,
			String cpa, String postal, String contacto, String cargocontacto,
			String telefonos, String celular, String fax, String web,
			BigDecimal idzona, BigDecimal idcobrador, BigDecimal idvendedor,
			String obsentrega, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String clientesDomiciliosCreateOrUpdate(BigDecimal iddomicilio,
			BigDecimal idcliente, String esdefault, String calle, String nro,
			String piso, String depto, BigDecimal idlocalidad, String cpa,
			String postal, String contacto, String cargocontacto,
			String telefonos, String celular, String fax, String web,
			BigDecimal idzona, BigDecimal idexpreso, BigDecimal idcobrador,
			BigDecimal idvendedor, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String clientesDomiciliosUpdate(BigDecimal iddomicilio,
			BigDecimal idcliente, BigDecimal idtipodomicilio, String esdefault,
			String calle, String nro, String piso, String depto,
			BigDecimal idlocalidad, String cpa, String postal, String contacto,
			String cargocontacto, String telefonos, String celular, String fax,
			String web, BigDecimal idzona, BigDecimal idcobrador,
			BigDecimal idvendedor, String obsentrega, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: clientesEmail Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Fri Feb 15 12:39:01 ART 2008
	 * 
	 */

	public List getClientesEmailAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesEmailOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesEmailPK(BigDecimal idemail, BigDecimal idempresa)
			throws RemoteException;

	public List getClientesEmailXCliente(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	public String clientesEmailDelete(BigDecimal idemail, BigDecimal idempresa)
			throws RemoteException;

	public String clientesEmailXDomicilioDelete(BigDecimal iddomicilio,
			BigDecimal idempresa) throws RemoteException;

	public String clientesEmailCreate(BigDecimal iddomicilio,
			BigDecimal idcliente, String email, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String clientesEmailCreateOrUpdate(BigDecimal idemail,
			BigDecimal iddomicilio, BigDecimal idcliente, String email,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String clientesEmailUpdate(BigDecimal idemail,
			BigDecimal iddomicilio, BigDecimal idcliente, String email,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: clientesTiposDomicilios Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Feb 27 14:41:00 ART 2008
	 */

	public List getClientesTiposDomiciliosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesTiposDomiciliosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesTiposDomiciliosPK(BigDecimal idtipodomicilio,
			BigDecimal idempresa) throws RemoteException;

	public String clientesTiposDomiciliosDelete(BigDecimal idtipodomicilio)
			throws RemoteException;

	public String clientesTiposDomiciliosCreate(String tipodomicilio,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String clientesTiposDomiciliosCreateOrUpdate(
			BigDecimal idtipodomicilio, String tipodomicilio,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String clientesTiposDomiciliosUpdate(BigDecimal idtipodomicilio,
			String tipodomicilio, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// Listados Precios
	public List getClienteslistasdepreciosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClienteslistasdepreciosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClienteslistasdepreciosPK(BigDecimal idlista,
			String codigo_st, BigDecimal idempresa) throws RemoteException;

	public String clienteslistasdepreciosDelete(BigDecimal idlista,
			String codigo_st, BigDecimal idempresa) throws RemoteException;

	public String clienteslistasdepreciosCreate(BigDecimal idlista,
			String codigo_st, Double precio, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String clienteslistasdepreciosCreateOrUpdate(BigDecimal idlista,
			String codigo_st, Double precio, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String clienteslistasdepreciosUpdate(BigDecimal idlista,
			String codigo_st, Double precio, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	// Update Listados
	public String clientesListadosUpdate(BigDecimal idlista, Double precio,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// clientes tarjetas calendarios presentacion
	public List getClientestarjetascalendariopresentacionAll(long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getClientestarjetascalendariopresentacionOcu(long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getClientestarjetascalendariopresentacionPK(BigDecimal codigo,
			BigDecimal idempresa) throws RemoteException;

	public String clientestarjetascalendariopresentacionDelete(
			BigDecimal codigo, BigDecimal idempresa) throws RemoteException;

	public String clientestarjetascalendariopresentacionCreate(
			BigDecimal idtarjetacredito, BigDecimal anio, BigDecimal mes,
			Timestamp fpresentaciondesde, Timestamp fpresentacionhasta,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String clientestarjetascalendariopresentacionCreateOrUpdate(
			BigDecimal codigo, BigDecimal idtarjetacredito, BigDecimal anio,
			BigDecimal mes, Timestamp fpresentaciondesde,
			Timestamp fpresentacionhasta, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String clientestarjetascalendariopresentacionUpdate(
			BigDecimal codigo, BigDecimal idtarjetacredito, BigDecimal anio,
			BigDecimal mes, Timestamp fpresentaciondesde,
			Timestamp fpresentacionhasta, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: clientesPeriodicidadEntrega Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Jul 31 12:17:08 GMT-03:00 2008
	 */

	public List getClientesPeriodicidadEntregaAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesPeriodicidadEntregaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesPeriodicidadEntregaOne(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesPeriodicidadEntregaPK(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	public String clientesPeriodicidadEntregaDelete(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	public String clientesPeriodicidadEntregaGenerar(BigDecimal idcliente,
			String[] idmes, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: bacoTmLlamados Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Mar 12 08:58:18 GYT 2009
	 */

	public List getBacoTmLlamadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoTmLlamadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacoTmLlamadosPK(BigDecimal idllamado, BigDecimal idempresa)
			throws RemoteException;

	public String bacoTmLlamadosDelete(BigDecimal idllamado,
			BigDecimal idempresa) throws RemoteException;

	public String bacoTmLlamadosCreate(BigDecimal idcliente,
			BigDecimal idcampacabe, String sociotelefono,
			BigDecimal idpedidoasoc, BigDecimal idresultado,
			BigDecimal idmotivo, String observaciones,
			java.sql.Timestamp fecharellamada, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String bacoTmLlamadosCreateOrUpdate(BigDecimal idllamado,
			BigDecimal idcliente, BigDecimal idcampacabe, String sociotelefono,
			BigDecimal idpedidoasoc, BigDecimal idresultado,
			BigDecimal idmotivo, String observaciones,
			java.sql.Date fecharellamada, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String bacoTmLlamadosUpdate(BigDecimal idllamado,
			BigDecimal idcliente, BigDecimal idcampacabe, String sociotelefono,
			BigDecimal idpedidoasoc, BigDecimal idresultado,
			BigDecimal idmotivo, String observaciones,
			java.sql.Date fecharellamada, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	// lov bacotmcampacabe
	public List getCampaniaLovAll(long limit, long offset)
			throws RemoteException;

	public List getCampaniaLovOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	// consulta de fecha de cumplea�o de Prospecto
	public List getCosnultaCumpleanioProspecto(long limit, long offset,
			String fdesde, String fhasta, BigDecimal idempresa)
			throws RemoteException;

	// consulta de fecha de ingreso de Prospecto
	public List getCosnultaIngresoProspecto(long limit, long offset,
			String fdesde, BigDecimal idempresa) throws RemoteException;

	// consulta socio prospecto
	public List getCosnultaSocioProspecto(long limit, long offset,
			String sdesde, String shasta, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: stockstock - clienteslistasdeprecios desde
	 * lovArticulosListaPreciosPedidos.jsp Copyrigth(r) utilizada para registrar
	 * captura de documentos sysWarp S.R.L. Fecha de creacion: Wed Aug 27
	 * 11:20:13 GMT-03:00 2008
	 */

	public List getArtListaPreciosPedidosAll(long limit, long offset,
			BigDecimal idlista, BigDecimal idzona, String conExistencia,
			String soloCampania, BigDecimal idcampacabe, BigDecimal idempresa)
			throws RemoteException;

	public List getArtListaPreciosPedidosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idlista, BigDecimal idzona,
			String conExistencia, String soloCampania, BigDecimal idcampacabe,
			BigDecimal idempresa) throws RemoteException;

	// 20101124 - EJV - Mantis 602 -->

	public List getArtListaPreciosPedidosRegalosAll(long limit, long offset,
			BigDecimal idlista, BigDecimal idzona, String conExistencia,
			String soloCampania, BigDecimal idcampacabe, boolean exsiteLock,
			BigDecimal idempresa) throws RemoteException;

	public List getArtListaPreciosPedidosRegalosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idlista, BigDecimal idzona,
			String conExistencia, String soloCampania, BigDecimal idcampacabe,
			boolean exsiteLock, BigDecimal idempresa) throws RemoteException;

	public long getTotaLockRegalos(BigDecimal idempresa) throws RemoteException;

	// <--

	public List getArtListaPreciosPedidosPK(String codigo_st,
			BigDecimal idlista, BigDecimal codigo_dt, BigDecimal idempresa)
			throws RemoteException;

	public List getArtListaPreciosAll(long limit, long offset,
			BigDecimal idlista, BigDecimal idempresa) throws RemoteException;

	public List getArtListaPreciosOcu(long limit, long offset,
			BigDecimal idlista, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getClientesDescuentosAll(BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: stockStockBis Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Aug 28 17:41:57 GMT-03:00 2008
	 * 
	 */

	public List getStockDepositoArticulo(String articu_sb,
			BigDecimal deposi_sb, BigDecimal idempresa) throws RemoteException;

	public List getPedidosMotivosDescuentosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: clientesZonasDepositos Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Sep 02 17:40:20 GMT-03:00 2008
	 * 
	 */

	public String clientesZonasDepositosGenerar(BigDecimal idzona,
			String[] codigo_dt, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public List getClientesZonasDepositosOne(BigDecimal idzona,
			BigDecimal idempresa) throws RemoteException;

	public String clientesZonasDepositosDelete(BigDecimal idzona,
			BigDecimal idempresa) throws RemoteException;

	public String clientesZonasDepositosCreate(BigDecimal codigo_dt,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public List getStockDepositosXZona(BigDecimal idzona, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: pedidosCuotas Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Sep 03 17:38:57 GMT-03:00 2008
	 */

	public List getPedidosCuotasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidosCuotasOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidosCuotasPK(BigDecimal idcuota, BigDecimal idempresa)
			throws RemoteException;

	public String pedidosCuotasDelete(BigDecimal idcuota, BigDecimal idempresa)
			throws RemoteException;

	public String pedidosCuotasCreate(BigDecimal nrocuotas,
			String observaciones, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String pedidosCuotasCreateOrUpdate(BigDecimal idcuota,
			BigDecimal nrocuotas, String observaciones, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String pedidosCuotasUpdate(BigDecimal idcuota, BigDecimal nrocuotas,
			String observaciones, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// actualizo la tabla de precarga le ingreso el numero de socio
	public String ClientesActualizar(BigDecimal prospecto,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getClientesestadosclientesXClienteAll(BigDecimal idcliente,
			long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	/*
	 * 
	 * public long getTotalEntidadXCliente(BigDecimal idcliente, String entidad,
	 * BigDecimal idempresa) throws RemoteException;
	 */

	public List getClientesestadosclientesXClienteOcu(BigDecimal idcliente,
			long limit, long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public String ClientesInsertoEstadoInicial(BigDecimal idcliente,
			BigDecimal idestado, BigDecimal idmotivo, String observaciones,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public List getClientesCampaiaLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesCampaniaLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// distinto de resultado <> 4 resultado is not null
	public List getClientesTMllamados1(long limit, long offset,
			BigDecimal idempresa, BigDecimal idresultado,
			BigDecimal idcampania, long idusuario, String usuarioalt)
			throws RemoteException;

	// distinto de resultado <> 4 resultado is null
	public List getClientesTMllamados2(long limit, long offset,
			BigDecimal idempresa, BigDecimal idcampania, long idusuario,
			String usuarioalt) throws RemoteException;

	// resultado == 4
	public List getClientesTMllamados3(long limit, long offset,
			BigDecimal idempresa, BigDecimal idresultado,
			BigDecimal idcampania, long idusuario, String usuarioalt)
			throws RemoteException;

	public long getusuarioxdescripcion(BigDecimal idempresa, String usuario)
			throws RemoteException;

	public long getTotalEntidadXFecha(String entidad, BigDecimal idempresa)
			throws RemoteException;

	public long getTotalEntidadXFechaOcu(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/*
	 * EJV - 20090330
	 * 
	 * public List getClientesTMllamadosXSocio(long limit, long offset,
	 * BigDecimal idempresa, BigDecimal idcampania, long idusuario, BigDecimal
	 * idcliente) throws RemoteException;
	 */

	/*
	 * EJV - 20090330
	 * 
	 * public List getClientesTMllamadosAgendadelDia(long limit, long offset,
	 * BigDecimal idempresa, BigDecimal idcampania, long idusuario) throws
	 * RemoteException;
	 */

	public List getBacoTmAsignacionTelemarketAll(long limit, long offset,
			BigDecimal idtelemark, BigDecimal idempresa) throws RemoteException;

	public List getBacoTmAsignacionTelemarketOcu(long limit, long offset,
			BigDecimal idtelemark, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	/*
	 * public String getValidacionTarjetaDeCredito(BigDecimal idempresa, String
	 * nrotarjeta, BigDecimal idmarcatarjeta) throws RemoteException;
	 * 
	 * public String getValidacionTarjetaDeCreditoExistenciaaAlta( BigDecimal
	 * idempresa, String nrotarjeta, BigDecimal idmarcatarjeta) throws
	 * RemoteException;
	 * 
	 * public String getValidacionTarjetaDeCreditoExistenciaaModificacion(
	 * BigDecimal idempresa, String nrotarjeta, BigDecimal idmarcatarjeta,
	 * BigDecimal idtarjeta) throws RemoteException;
	 * 
	 * public String validarTarjetaDeCredito(String nrotarjeta) throws
	 * RemoteException;
	 * 
	 * public String getClienteTarjeta(BigDecimal idempresa, String nrotarjeta,
	 * BigDecimal idmarcatarjeta) throws RemoteException;
	 */

	/**
	 * Metodos para la entidad: clientesAnexoLocalidades Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Dec 23 14:59:21 GMT-03:00 2008
	 * 
	 */

	public List getClientesAnexoLocalidadesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesAnexoLocalidadesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesAnexoLocalidadesXLocaAll(long limit, long offset,
			BigDecimal idlocalidad, BigDecimal idempresa)
			throws RemoteException;

	public List getClientesAnexoLocalidadesXLocaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idlocalidad, BigDecimal idempresa)
			throws RemoteException;

	public List getClientesAnexoLocalidadesPK(BigDecimal idanexolocalidad,
			BigDecimal idempresa) throws RemoteException;

	public String clientesAnexoLocalidadesDelete(BigDecimal idanexolocalidad,
			BigDecimal idempresa) throws RemoteException;

	public String clientesAnexoLocalidadesCreate(BigDecimal idexpresozona,
			BigDecimal idlocalidad, Double codtfbasica, String codtfctdo,
			Double tarand1bulto, Double tarandexc, Double tarsoc1bulto,
			Double tarsocexc, String cabeoinflu, String norteosur,
			BigDecimal idexpresobaco, BigDecimal iddistribuidorbaco,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String clientesAnexoLocalidadesCreateOrUpdate(
			BigDecimal idanexolocalidad, BigDecimal idexpresozona,
			BigDecimal idlocalidad, Double codtfbasica, String codtfctdo,
			Double tarand1bulto, Double tarandexc, Double tarsoc1bulto,
			Double tarsocexc, String cabeoinflu, String norteosur,
			BigDecimal idexpresobaco, BigDecimal iddistribuidorbaco,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String clientesAnexoLocalidadesUpdate(BigDecimal idanexolocalidad,
			BigDecimal idexpresozona, BigDecimal idlocalidad,
			Double codtfbasica, String codtfctdo, Double tarand1bulto,
			Double tarandexc, Double tarsoc1bulto, Double tarsocexc,
			String cabeoinflu, String norteosur, BigDecimal idexpresobaco,
			BigDecimal iddistribuidorbaco, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	/**
	 * Metodos Lov Expresos / Zonas: Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Tue Dec 29 17:22:21 GMT-03:00 2008
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getClientesExpresosZonasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesExpresosZonasXZona(long limit, long offset,
			BigDecimal idzona, BigDecimal idempresa) throws RemoteException;

	public List getClientesExpresosZonasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public String getGoogleMapTitulo(BigDecimal idempresa,
			BigDecimal idDomicilio) throws RemoteException;

	public String getGoogleMapDomicilio(BigDecimal idempresa,
			BigDecimal idDomicilio) throws RemoteException;

	public String getGmapKeyID() throws RemoteException;

	/**
	 * Metodos para la entidad: stockstock - esquemascabe / deta desde
	 * lovPedidosEsquemasContieneArt.jsp Copyrigth(r) utilizada para consultar
	 * esquemas que contienen articulos sin stock, para desarme, para poder
	 * cumplir pedidos sysWarp S.R.L. Fecha de creacion: Wed Jan 22 12:06:13
	 * GMT-03:00 2009
	 */

	public List getEsquemasContienenArticulo(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: pedidos_cabe - clientesdomicilios -
	 * clientesclientes - pedidosestados - clientesanexolocalidades -
	 * globallocalidades - clientesexpresoszonas - clienteszonas -
	 * clientesexpresos Copyrigth(r) sysWarp S.R.L. Fecha de creacion: Thu Feb
	 * 12 08:43:04 GYT 2009
	 */

	public List getPedidosPendientesAll(long limit, long offset,
			BigDecimal idestado, String tipopedido, String orden,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidosPendientesOcu(long limit, long offset,
			BigDecimal idestado, String tipopedido, String filtro,
			String orden, BigDecimal idempresa) throws RemoteException;

	public List getPedidosProgramadosAll(long limit, long offset,
			BigDecimal idprioridad, String tipopedido, String orden,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidosProgramadosOcu(long limit, long offset,
			BigDecimal idprioridad, String tipopedido, String filtro,
			String orden, BigDecimal idempresa) throws RemoteException;

	/**
	 * ========================================================================
	 * ===== BEGIN - CONSOLIDACION DE PEDIDOS
	 * 
	 */

	/**
	 * Metodos para la entidad: clientesRemitos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Feb 17 14:10:19 GYT 2009
	 */

	public String clientesRemitosCreate(BigDecimal nrosucursal,
			BigDecimal nroremitocliente, BigDecimal idcliente,
			BigDecimal bultos, BigDecimal valorflete,
			BigDecimal valordeclaradoflete, BigDecimal seguro,
			BigDecimal impresion, BigDecimal idestado, String observaciones,
			java.sql.Date fecharemito, BigDecimal idprefactura,
			String tipopedido, String ctactezona, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String clientesRemitosUpdateBultos(BigDecimal nrosucursal,
			BigDecimal nroremitocliente, int bultos, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	// EJV - 20100804 -->
	// Se invoca desde regalos.

	public String clientesRemitosUpdateBultosCtaCte(BigDecimal nrosucursal,
			BigDecimal nroremitocliente, int bultos, String ctactezona,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// <--

	public List getClientesRemitosCabecera(BigDecimal idremitocliente,
			String tipopedido, BigDecimal idempresa) throws RemoteException;

	public List getClientesRemitosDetalle(BigDecimal idremitocliente,
			String tipopedido, BigDecimal idempresa) throws RemoteException;

	public List getPedidosRemitosXPedidos(BigDecimal idpedido,
			String tipopedido, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: pedidosMotivosDescuento Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Mar 11 08:31:00 GYT 2009
	 */

	public List getPedidosMotivosDescuentoAll(long limit, long offset,
			BigDecimal ejercicio, BigDecimal idempresa) throws RemoteException;

	public List getPedidosMotivosDescuentoOcu(long limit, long offset,
			String ocurrencia, BigDecimal ejercicio, BigDecimal idempresa)
			throws RemoteException;

	public List getPedidosMotivosDescuentoPK(BigDecimal idmotivodescuento,
			BigDecimal ejercicio, BigDecimal idempresa) throws RemoteException;

	public String pedidosMotivosDescuentoDelete(BigDecimal idmotivodescuento,
			BigDecimal idempresa) throws RemoteException;

	public String pedidosMotivosDescuentoCreate(String motivodescuento,
			BigDecimal idcuenta, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String pedidosMotivosDescuentoCreateOrUpdate(
			BigDecimal idmotivodescuento, String motivodescuento,
			BigDecimal idcuenta, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String pedidosMotivosDescuentoUpdate(BigDecimal idmotivodescuento,
			String motivodescuento, BigDecimal idcuenta, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	/**
	 * Generar consolidacion de pedidos
	 */

	public String[] pedidosNormalesConsolidarRemitos(BigDecimal idpuesto,
			BigDecimal idcontadorcomprobante, String[] idpedido_cabe,
			java.sql.Date fecharemito, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	public String[] pedidosRegalosConsolidarRemitos(BigDecimal idpuesto,
			BigDecimal idcontadorcomprobante, String[] idpedido_cabe,
			java.sql.Date fecharemito, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	/**
	 * Recuperar rango de remitos de clientes generados .
	 * 
	 */

	public List getPedidosRemitosGenerados(BigDecimal idremitoclientedesde,
			BigDecimal idremitoclientehasta, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * BEGIN HOJA ARMADO
	 */

	public String[] setHojaArmadoRemitosClientes(String[] idremitocliente,
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	// para todo (ordena por el segundo campo por defecto)
	public List getClientesRemitosPendientesHAAll(long limit, long offset,
			BigDecimal idestado, String tipopedido, String orden,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesRemitosPendientesHAXZonaAll(long limit, long offset,
			BigDecimal idzona, BigDecimal idestado, String tipopedido,
			String orden, BigDecimal idempresa) throws RemoteException;

	public List getClientesRemitosPendientesHAOcu(long limit, long offset,
			BigDecimal idestado, String tipopedido, String filtro,
			String orden, BigDecimal idempresa) throws RemoteException;

	public List getClientesRemitosHAAll(long limit, long offset,
			BigDecimal nrohojaarmado, BigDecimal idempresa)
			throws RemoteException;

	public List getClientesRemitosHAOcu(long limit, long offset,
			String ocurrencia, BigDecimal nrohojaarmado, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * END - HOJA ARMADO
	 */

	/**
	 * BEGIN - HOJA RUTA FINAL
	 */

	/**
	 * Metodos para la entidad: pedidosHojaRutaFinal Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Mar 17 08:12:59 GYT 2009
	 */

	public List getVClientesRemitosHojaRutaFinalPreconfAll(long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getVClientesRemitosHojaRutaFinalPreconfOcu(long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public String pedidosHojaRutaFinalCreate(BigDecimal nrohojarutafinal,
			BigDecimal nropallets, String filename, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	// para todo (ordena por el segundo campo por defecto)
	public List getClientesRemitosHRFinalAll(long limit, long offset,
			String orden, String tipopedido, BigDecimal idempresa)
			throws RemoteException;

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getClientesRemitosHRFinalOcu(long limit, long offset,
			String filtro, String orden, BigDecimal idempresa)
			throws RemoteException;

	public String[] generarHojaRutaFinal(Timestamp fechamov, String tipomov,
			BigDecimal num_cnt, BigDecimal sucursal, String tipo,
			String observaciones, BigDecimal idcontadorcomprobante,
			String[] nrohojaarmado, BigDecimal nropallets, String tipopedido,
			String nameFile, BigDecimal idexpreso, String usuarioalt,
			BigDecimal idempresa) throws RemoteException, SQLException;

	/**
	 * END - HOJA RUTA FINAL
	 */

	/**
	 * BEGIN - CALCULO DE BULTOS
	 * 
	 */

	// -- Metodos a testear
	// isKit: determina si un producto forma parte de un grupo de tipo KIT
	public boolean isKit(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	// isCaja: determina si un producto es una CAJA (si viene en una unidad de
	// medida mayor a 1
	public boolean isCaja(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	// trae del expreso el factor de la medida de su bulto mmmmmmmmmmmmmm!!
	public BigDecimal getMedida4Bulto(BigDecimal idExpreso, BigDecimal idempresa)
			throws RemoteException;

	// traer la cantidad de una unidad de medida que proviene de un producto
	public BigDecimal getMedidaFactor(String codigo_st,
			BigDecimal tipoDistribucion, BigDecimal idempresa)
			throws RemoteException;

	public int getCalculoBulto(BigDecimal nrosucursal, BigDecimal nroremito,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vclientesCtaCtes Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Mar 25 08:50:25 GYT 2009
	 */

	public List getVclientesCtaCtesAll(long limit, long offset,
			BigDecimal idcliente, String historico, BigDecimal idempresa)
			throws RemoteException;

	public List getVclientesCtaCtesOcu(long limit, long offset,
			BigDecimal idcliente, String historico, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public BigDecimal getVclientesCtaCtesSaldo(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vclientesAsientos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Mar 25 10:59:30 GYT 2009
	 */

	public List getVclientesAsientosPK(BigDecimal nrointerno,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: bacoErEsquema Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Mar 25 14:12:47 GYT 2009
	 */

	public List getBacoErEsquemaAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoErEsquemaOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoErEsquemaPK(BigDecimal idesquema, BigDecimal idempresa)
			throws RemoteException;

	public String bacoErEsquemaDelete(BigDecimal idesquema, BigDecimal idempresa)
			throws RemoteException;

	public String bacoErEsquemaCreate(BigDecimal anio, BigDecimal idmes,
			BigDecimal idpreferencia, String codigo_st, BigDecimal cantidad,
			BigDecimal codigo_dt, BigDecimal iddescuento,
			BigDecimal idmotivodescuento, BigDecimal idlista,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String bacoErEsquemaCreateOrUpdate(BigDecimal idesquema,
			BigDecimal anio, BigDecimal idmes, BigDecimal idpreferencia,
			String codigo_st, BigDecimal cantidad, BigDecimal codigo_dt,
			BigDecimal iddescuento, BigDecimal idmotivodescuento,
			BigDecimal idlista, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String bacoErEsquemaUpdate(BigDecimal idesquema, BigDecimal anio,
			BigDecimal idmes, BigDecimal idpreferencia, String codigo_st,
			BigDecimal cantidad, BigDecimal codigo_dt, BigDecimal iddescuento,
			BigDecimal idmotivodescuento, BigDecimal idlista,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	//

	public BigDecimal getBacoTmAsignacionTotalSocios(BigDecimal idcampacabe,
			String filtros, BigDecimal idempresa) throws RemoteException;

	public String bacoTmAsignacionSocioCreate(BigDecimal idcampacabe,
			BigDecimal idtelemark, String filtros, String limit,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	/**
	 * PROCESO DE ENTREGAS REGULARES. BEGIN 20090331 - EJV.
	 */

	public String setEntregasRegulares(int anio, int mes, BigDecimal idempresa,
			String usuarioalt) throws RemoteException, SQLException;

	/**
	 * Metodos para la entidad: pedidosEntregaRegularLog Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Apr 07 14:06:19 GYT 2009
	 */

	public List getPedidosEntregaRegularLogAll(long limit, long offset,
			int anio, int idmes, BigDecimal idempresa) throws RemoteException;

	public List getPedidosEntregaRegularLogOcu(long limit, long offset,
			String ocurrencia, int anio, int idmes, BigDecimal idempresa)
			throws RemoteException;

	// Consulta y totales de necesidad para Entregas Regulares.

	public List getERegularesNecesidad(int anio, int idmes, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * PROCESO DE ENTREGAS REGULARES. END
	 */

	public String getPivotContar(String tabla, String campoAlias)
			throws RemoteException;

	public java.sql.ResultSet getClientesSociosAsignados(BigDecimal idcampania,
			BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesSociosAsignadosTotales(
			BigDecimal idcampania, BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesSociosAsignadosTotalesDS(
			BigDecimal idcampania, BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesSociosAsignadosTotalesDS1(
			BigDecimal idcampania, BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesSociosAsignadosTotalesDS2(
			BigDecimal idcampania, BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesSociosAsignadosTotalesDS3(
			BigDecimal idcampania, BigDecimal idempresa) throws RemoteException;

	// total llamados
	public List getLlamados4Campania4Resultado(String fechaD, String fechaH,
			BigDecimal idcampania, BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesVentasporProductosyCampania(
			String fechaD, String fechaH, BigDecimal idempresa)
			throws RemoteException;

	public java.sql.ResultSet getClientesVentasporProductos(String fechaD,
			String fechaH, BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesVentasporCategorias(String fechaD,
			String fechaH, BigDecimal idempresa) throws RemoteException;

	public List getLClientesEstadnoCompro(BigDecimal idcampania,
			BigDecimal idempresa) throws RemoteException;

	public List getLClientesEstadTotal(BigDecimal idcampania,
			BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesCategoriaProvincia(
			BigDecimal idcampania, BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesCategoriaProvinciaTotal(
			BigDecimal idcampania, BigDecimal idempresa) throws RemoteException;

	public ResultSet getClientesCategoriaProvinciaDS(BigDecimal idcampania,
			BigDecimal idempresa) throws RemoteException;

	public ResultSet getClientesCategoriaProvinciaDS1(BigDecimal idcampania,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: pedidosCambioEstadosLog Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Jun 23 15:26:30 GYT 2009
	 * 
	 */

	public String[] callPedidosCabeUpdateEstado(BigDecimal idpedido,
			BigDecimal idestadoanterior, BigDecimal idestadonuevo,
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	public String callPedidosRegalosCabeUpdateEstado(
			BigDecimal idpedido_regalos_cabe, BigDecimal idestadoanterior,
			BigDecimal idestadonuevo, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	// 20101119 - EJV - Mantis 602
	public String[] callPedidosRegalosEntregasCabeUpdateEstado(
			BigDecimal idpedido_regalos_cabe,
			BigDecimal idpedido_regalos_entrega_cabe,
			BigDecimal idestadoanterior, BigDecimal idestadonuevo,
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	// --------------------------------------------------

	public String[] callPedidosCabeUpdateEstadoPreconf(BigDecimal idpedido,
			BigDecimal idestadoanterior, BigDecimal idestadonuevo,
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	public String callPedidosRegalosCabeUpdateEstadoPreconf(
			BigDecimal idpedido, BigDecimal idestadoanterior,
			BigDecimal idestadonuevo, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	public String callInterfacesPreconformacionTotalRemitosPedido(
			BigDecimal idpedidocabedelta) throws RemoteException;

	public String callInterfacesPreconformacionTotalPedidosRemito(
			BigDecimal idpedidocabedelta, int totalRemitos)
			throws RemoteException;

	public List getPedidosCambioEstadosLogPedido(BigDecimal idpedido,
			String tipopedido, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para cunsulta: pedidosConDescuento Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Jul 15 09:28:13 GYT 2009
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getPedidosConDescuentoAll(long limit, long offset,
			BigDecimal idtipoclie, BigDecimal mes, BigDecimal anio,
			BigDecimal idestado, BigDecimal idempresa) throws RemoteException;

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getPedidosConDescuentoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idtipoclie, BigDecimal mes,
			BigDecimal anio, BigDecimal idestado, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para cunsulta: pedidosEvaluacion Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Jul 15 09:28:13 GYT 2009
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getPedidosEvaluacionAll(long limit, long offset,
			BigDecimal idtipoclie, BigDecimal mes, BigDecimal anio,
			BigDecimal idestado, BigDecimal idempresa) throws RemoteException;

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getPedidosEvaluacionOcu(long limit, long offset,
			String ocurrencia, BigDecimal idtipoclie, BigDecimal mes,
			BigDecimal anio, BigDecimal idestado, BigDecimal idempresa)
			throws RemoteException;

	// Vendedores Resumen
	public List getCosnultaVendedoresResumenVendedores(String anio,
			BigDecimal mes, BigDecimal idvendedor, BigDecimal idempresa)
			throws RemoteException;

	// Detalle por Asociaciones
	public List getCosnultaVendedoresDetalleAsociaciones(String anio,
			BigDecimal mes, BigDecimal idvendedor, BigDecimal idempresa)
			throws RemoteException;

	// Notas de credito por ci
	public List getCosnultaVendedoresNotasdecreditoporCI(String anio,
			BigDecimal mes, BigDecimal idvendedor, BigDecimal idempresa)
			throws RemoteException;

	// Detalle de Movimiento en Cartera
	public List getCosnultaVendedoresDetalleMovimientoCartera(String anio,
			BigDecimal mes, BigDecimal idvendedor, BigDecimal idempresa)
			throws RemoteException;

	// Analisis de estado por a�o de ingreso.
	public List getCosnultaEstadoporanioIngreso(String fechadesde,
			String fechahasta, BigDecimal idestado, String reporte,
			BigDecimal idempresa) throws RemoteException;

	public List getCosnultaporVendedor(String fechadesde, String fechahasta,
			BigDecimal idestado, String reporte, BigDecimal idempresa)
			throws RemoteException;

	public ResultSet getCosnultaporVendedorMotivos(String fechadesde,
			String fechahasta, BigDecimal idestado, String reporte,
			BigDecimal idempresa) throws RemoteException;

	public String getPivotContar(String tabla, String campoAlias, String cWhere)
			throws RemoteException;

	public ResultSet getConsultaporporDistribuidoryMotivodeBaja(
			String fechadesde, String fechahasta, BigDecimal idestado,
			String reporte, BigDecimal idempresa) throws RemoteException;

	public ResultSet getConsultaporProvinciayMotivo(String fechadesde,
			String fechahasta, BigDecimal idestado, String reporte,
			BigDecimal idempresa) throws RemoteException;

	// pedidos pendientes para revisar
	public List getCosnultaPedidosPendientesRevisar(String fechadesde,
			String fechahasta, BigDecimal idempresa, BigDecimal idestado,
			BigDecimal idtipoclie) throws RemoteException;

	// seleccion de socios para llamadas sin socio
	public List getCosnultaSeleccionClientesLLamadas(BigDecimal idcampania,
			BigDecimal idresultado, BigDecimal idempresa)
			throws RemoteException;

	// seleccion de socios para llamadas con socio
	public List getCosnultaSeleccionClientesLLamadasconCliente(
			BigDecimal idcampania, BigDecimal idcliente,
			BigDecimal idresultado, BigDecimal idempresa)
			throws RemoteException;

	public List getClientesCampaiaAnterioresLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesCategoriaProvincia(String fdesde,
			String fhasta, BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesCategoriaProvinciaTotal(String fdesde,
			String fhasta, BigDecimal idempresa) throws RemoteException;

	public ResultSet getClientesCategoriaProvinciaDS(String fdesde,
			String fhasta, BigDecimal idempresa) throws RemoteException;

	public ResultSet getClientesCategoriaProvinciaDS1(String fdesde,
			String fhasta, BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesSociosAsignadosTotalesDS1(
			String fdesde, String fhasta, BigDecimal idempresa)
			throws RemoteException;

	// suspension de entregas regulares
	public String SuspensiondeEntregasRegularesCreate(BigDecimal idcliente,
			BigDecimal anio, BigDecimal mes, BigDecimal idempresa,
			String usuarioalt, BigDecimal idmotsusp) throws RemoteException;

	// reasignacion de socios
	// aca hago un count y levanto la cantidad de socios
	public BigDecimal getBacoTmReasignacionTotalSocios(BigDecimal idcampacabe,
			String filtros, BigDecimal idempresa, BigDecimal idtelemark)
			throws RemoteException;

	// aca tendria que borrar en la tabla bacotmseleccionsocio los datos de la
	// campaña,telemark origen,idempresa

	/*
	 * public String bacoTmReasignacionSocioDelete(BigDecimal idcampacabe,
	 * BigDecimal idempresa, BigDecimal idtelemark) throws RemoteException;
	 * 
	 * public String bacoTmReasignacionSocioIserto(BigDecimal idcampacabe,
	 * BigDecimal idtelemarkdestino, String filtros, String limit, BigDecimal
	 * idempresa, String usuarioalt) throws RemoteException;
	 */
	// REASIGNACION DE SOCIOS MODIFICADA 03/02/2010 FGP
	public String bacoTmReasignacionSocioUpdate(BigDecimal idcampacabe,
			BigDecimal idtelemarkorigen, BigDecimal idtelemarkdestino,
			String filtros, String limit, BigDecimal idempresa,
			String usuarioact, String idclientedesde, String idclientehasta)
			throws RemoteException;

	// bajas por vendedor
	public java.sql.ResultSet getClientesBajasporVendedor(String anio,
			BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesBajasporVendedorTotal(String anio,
			BigDecimal idempresa) throws RemoteException;

	public List getVPedidosEstadoconFechaAll(long limit, long offset,
			BigDecimal idestado, String tipopedido, BigDecimal idempresa,
			String fdesde, String fhasta) throws RemoteException;

	public List getVPedidosEstadoconFechaOcu(long limit, long offset,
			BigDecimal idestado, String tipopedido, String ocurrencia,
			BigDecimal idempresa, String fdesde, String fhasta)
			throws RemoteException;

	public List getPedidoStockNegativoAll(long limit, long offset,
			String codigo_st, BigDecimal idempresa) throws RemoteException;

	public List getPedidoStockNegativoOcu(long limit, long offset,
			String ocurrencia, String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	// lov suspension entregas regulares motivos
	public List getClientesSuspensionMotivosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public java.sql.ResultSet getClientesVentasporProductosyCampaniaporTelemark(
			String fechaD, String fechaH, BigDecimal idempresa,
			String idtelemark) throws RemoteException;

	/**
	 * Metodos para la entidad: bacoObsequiosLocalidad Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Nov 04 15:51:58 GMT-03:00 2009
	 */

	public List getBacoObsequiosLocalidadAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoObsequiosLocalidadOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacoObsequiosLocalidadPK(BigDecimal idobsequio,
			BigDecimal idempresa) throws RemoteException;

	public String bacoObsequiosLocalidadDelete(BigDecimal idobsequio,
			BigDecimal idempresa) throws RemoteException;

	public String bacoObsequiosLocalidadCreate(BigDecimal idlocalidad,
			String cartaoregalo, String informecarta, String restaurant,
			BigDecimal idclusterlogistica, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String bacoObsequiosLocalidadCreateOrUpdate(BigDecimal idobsequio,
			BigDecimal idlocalidad, String cartaoregalo, String informecarta,
			String restaurant, BigDecimal idclusterlogistica,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String bacoObsequiosLocalidadUpdate(BigDecimal idobsequio,
			BigDecimal idlocalidad, String cartaoregalo, String informecarta,
			String restaurant, BigDecimal idclusterlogistica,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: bacoProcesoObsequios Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Nov 05 11:24:51 GMT-03:00 2009
	 */

	public List getBacoProcesoObsequiosAll(long limit, long offset,
			BigDecimal idtipoobsequio, int mesCumpleanos, int anio, int mes,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoProcesoObsequiosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idtipoobsequio, int mesCumpleanos,
			int anio, int mes, BigDecimal idempresa) throws RemoteException;

	public List getBacoProcesObsequiosFromFileAll(long limit, long offset,
			String tablename, String[] idclienteTmp, boolean impactaTmp,
			BigDecimal idtipoobsequio, int anio, int mes, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: bacoTipoObsequios Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Thu Nov 05 14:51:35 GMT-03:00 2009
	 */

	public List getBacoTipoObsequiosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * @Comment: PROCESO DE OBSEQUIOS - EJV -- > BEGIN
	 * @author ejv
	 * @date: 20091106
	 * 
	 * */

	public String[] setProcesoObsequios(String[] vecIdcliente, int anio,
			int idmes, BigDecimal idtipoobsequio, boolean fromfile,
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	/**
	 * Metodos para la entidad: bacoObsequiosEsquema Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Nov 06 11:36:23 GMT-03:00 2009
	 */

	public List getBacoObsequiosEsquemaAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoObsequiosEsquemaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacoObsequiosEsquemaPK(BigDecimal idesquema,
			BigDecimal idempresa) throws RemoteException;

	public boolean isExisteBacoObsequiosEsquemaPeriodo(int anio, int idmes,
			BigDecimal idtipoobsequio, String cartaoregalo, BigDecimal idempresa)
			throws RemoteException;

	public String bacoObsequiosEsquemaDelete(BigDecimal idesquema,
			BigDecimal idempresa) throws RemoteException;

	public String bacoObsequiosEsquemaCreate(BigDecimal anio, BigDecimal idmes,
			BigDecimal idtipoobsequio, String codigo_st, BigDecimal codigo_dt,
			BigDecimal cantidad, String cartaoregalo, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String bacoObsequiosEsquemaCreateOrUpdate(BigDecimal idesquema,
			BigDecimal anio, BigDecimal idmes, BigDecimal idtipoobsequio,
			String codigo_st, BigDecimal codigo_dt, BigDecimal cantidad,
			String cartaoregalo, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String bacoObsequiosEsquemaUpdate(BigDecimal idesquema,
			BigDecimal anio, BigDecimal idmes, BigDecimal idtipoobsequio,
			String codigo_st, BigDecimal codigo_dt, BigDecimal cantidad,
			String cartaoregalo, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: pedidos_Regalos_Entregas_Cabe Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Fri Nov 13 10:00:59 GMT-03:00 2009
	 */

	public List getPedidos_Regalos_Entregas_CabeAll(long limit, long offset,
			BigDecimal idpedido_regalos_cabe, BigDecimal idempresa)
			throws RemoteException;

	public List getPedidos_Regalos_Entregas_CabeOcu(long limit, long offset,
			String ocurrencia, BigDecimal idpedido_regalos_cabe,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidos_Regalos_Entregas_CabePK(
			BigDecimal idpedido_regalos_entrega_cabe, BigDecimal idempresa)
			throws RemoteException;

	public String pedidos_Regalos_Entregas_CabeDelete(
			BigDecimal idpedido_regalos_entrega_cabe, BigDecimal idempresa)
			throws RemoteException;

	public String pedidosRegalosEntregasAnular(
			BigDecimal idpedido_regalos_entrega_cabe, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String pedidos_Regalos_Entregas_CabeCreate(
			BigDecimal idpedido_regalos_cabe, BigDecimal idestado,
			BigDecimal idcliente, BigDecimal idsucursal, BigDecimal idsucuclie,
			Timestamp fechapedido, BigDecimal idexpreso, String obsarmado,
			String obsentrega, BigDecimal idprioridad, BigDecimal idzona,
			BigDecimal idanexolocalidad, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String pedidos_Regalos_Entregas_CabeCreateOrUpdate(
			BigDecimal idpedido_regalos_entrega_cabe,
			BigDecimal idpedido_regalos_cabe, BigDecimal idestado,
			BigDecimal idcliente, BigDecimal idsucursal, BigDecimal idsucuclie,
			Timestamp fechapedido, BigDecimal idexpreso, String obsarmado,
			String obsentrega, BigDecimal idprioridad, BigDecimal idzona,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String pedidos_Regalos_Entregas_CabeUpdate(
			BigDecimal idpedido_regalos_entrega_cabe,
			BigDecimal idpedido_regalos_cabe, BigDecimal idestado,
			BigDecimal idcliente, BigDecimal idsucursal, BigDecimal idsucuclie,
			Timestamp fechapedido, BigDecimal idexpreso, String obsarmado,
			String obsentrega, BigDecimal idprioridad, BigDecimal idzona,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public List getPedidosRegalosEntregasDisponible(
			BigDecimal idpedido_regalos_entrega_cabe, BigDecimal idempresa)
			throws RemoteException;

	public List getPedidosRegalosCabeHeader(BigDecimal idpedido_regalos_cabe,
			BigDecimal idempresa) throws RemoteException;

	public String[] pedidosRegalosEntregasCreate(
			BigDecimal idpedido_regalos_cabe, BigDecimal idestado,
			BigDecimal idcliente, BigDecimal idsucursal, BigDecimal idsucuclie,
			Timestamp fechapedido, BigDecimal idexpreso, String obsarmado,
			String obsentrega, BigDecimal idprioridad,
			BigDecimal idzona,
			String[] codigo_st,
			String[] cant_entrega,

			//

			String calle, String nro, String piso, String depto, String cpa,
			String postal, String contacto, String cargocontacto,
			String telefonos, String celular, String fax, String web,
			BigDecimal idanexolocalidad,

			//
			String[] codigo_dt, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	public List getPedidos_Regalos_Entregas_DetaOne(
			BigDecimal idpedido_regalos_entrega_cabe, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: pedidosDomiciliosEntrega Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Fri Nov 13 10:00:09 GMT-03:00 2009
	 */

	public List getPedidosDomiciliosEntregaAll(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getPedidosDomiciliosEntregaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idcliente, BigDecimal idempresa)
			throws RemoteException;

	// Resumen de Ventas por Periodo TM
	// / cambiar CEP 30/11

	public List getClientesResumenVentasporPeriodoTMFINAL(String fechaD,
			String fechaH, BigDecimal idempresa) throws RemoteException;

	// Total Resumen de Ventas por Periodo TM
	public java.sql.ResultSet getClientesResumenVentasporPeriodoTMTotal(
			String fechaD, String fechaH, BigDecimal idempresa)
			throws RemoteException;

	// Resumen de Ventas por Periodo por otros sectores
	public java.sql.ResultSet getClientesResumenVentasporPeriodoOtrosSectores(
			String fechaD, String fechaH, BigDecimal idempresa)
			throws RemoteException;

	// Total Resumen de Ventas por Periodo TM
	public java.sql.ResultSet getClientesResumenVentasporPeriodoOtrosSectoresTotal(
			String fechaD, String fechaH, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: clientesRemitosLeyendas Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Dec 10 09:15:59 GMT-03:00 2009
	 */

	public List getClientesRemitosLeyendasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesRemitosLeyendasOcu(long limit, long offset,
			String auxFiltro, BigDecimal idempresa) throws RemoteException;

	public List getClientesRemitosLeyendasPK(BigDecimal idleyenda,
			BigDecimal idempresa) throws RemoteException;

	public String clientesRemitosLeyendasDelete(BigDecimal idleyenda,
			BigDecimal idempresa) throws RemoteException;

	public String clientesRemitosLeyendasDeleteMultiple(String[] idleyenda,
			BigDecimal idempresa) throws RemoteException;

	public String clientesRemitosLeyendasCreate(String criterio,
			BigDecimal anio, BigDecimal idmes, BigDecimal idlocalidad,
			BigDecimal idprovincia, String leyenda, BigDecimal idclub,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String clientesRemitosLeyendasUpdate(BigDecimal idleyenda,
			BigDecimal anio, BigDecimal idmes, BigDecimal idlocalidad,
			String leyenda, BigDecimal idclub, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public List getClientesRemitosByIdentificador(String idctrlremito,
			String tipopedido, BigDecimal idempresa) throws RemoteException;

	// CLIENTES DOMICILIOS RESUMIDOS
	public List getClientesDomiciliosClienteResumido(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: clientesRelaciones Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Feb 12 16:17:05 GMT-03:00 2010
	 */

	public List getClientesRelacionesAll(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getClientesRelacionesOcu(long limit, long offset,
			BigDecimal idcliente, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getClientesRelacionesPK(BigDecimal idrelacion,
			BigDecimal idempresa) throws RemoteException;

	public String clientesRelacionesDelete(BigDecimal idrelacion,
			BigDecimal idempresa) throws RemoteException;

	public String clientesRelacionesCreate(BigDecimal idclienteroot,
			BigDecimal idclientebranch, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String clientesRelacionesCreateOrUpdate(BigDecimal idrelacion,
			BigDecimal idclienteroot, BigDecimal idclientebranch,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String clientesRelacionesUpdate(BigDecimal idrelacion,
			BigDecimal idclienteroot, BigDecimal idclientebranch,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public List getRemitoClienteAnularRecursivo(BigDecimal nrosucursal,
			BigDecimal nroremitocliente, String idctrlremito,
			String tipopedido, int nivel, Hashtable htPedidoRemito,
			BigDecimal idempresa) throws RemoteException;

	public String clientesRemitosNormalesAnular(Iterator iter,
			Hashtable htPedidos, Hashtable htCtrlRemitos,
			String[] vecIdctrlremito, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	public String clientesRemitosRegalosAnular(Iterator iter,
			Hashtable htPedidos, Hashtable htCtrlRemitos,
			String[] vecIdctrlremito, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	/**
	 * Metodos para la entidad: pedidosAnulacionRemitosLog Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Feb 23 17:03:04 GMT-03:00 2010
	 */

	public List getPedidosAnulacionRemitosLogAll(long limit, long offset,
			String tipopedido, BigDecimal idempresa) throws RemoteException;

	public List getPedidosAnulacionRemitosLogOcu(long limit, long offset,
			String tipopedido, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getClientesExpresosCPostalAll(long limit, long offset,
			String cpostal, BigDecimal idempresa) throws RemoteException;

	public List getClientesXExpresoAll(long limit, long offset,
			BigDecimal idexpreso, BigDecimal idempresa) throws RemoteException;

	/**
	 * 20100902 - EJV MANTIS - 481 Desvincular remitos de hojas de armado BEGIN
	 * -->
	 */

	public String clientesRemitosDesvincularHA(BigDecimal nrohojaarmado,
			String idctrlremito, BigDecimal sucursal, BigDecimal comprobante,
			String tipopedido, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	/**
	 * Metodos para la entidad: clientesIndicadoresTipos Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Apr 29 15:16:19 GMT-03:00 2010
	 */

	public List getClientesIndicadoresTiposAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesIndicadoresTiposOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesIndicadoresTiposPK(BigDecimal idtipoindicador,
			BigDecimal idempresa) throws RemoteException;

	public String clientesIndicadoresTiposDelete(BigDecimal idtipoindicador,
			BigDecimal idempresa) throws RemoteException;

	public String clientesIndicadoresTiposCreate(String tipoindicador,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String clientesIndicadoresTiposCreateOrUpdate(
			BigDecimal idtipoindicador, String tipoindicador,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String clientesIndicadoresTiposUpdate(BigDecimal idtipoindicador,
			String tipoindicador, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: clientesIndicadoresManuales Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Fri Apr 30 10:30:20 GMT-03:00 2010
	 */

	public List getClientesIndicadoresManualesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesIndicadoresManualesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesIndicadoresManualesPK(BigDecimal idindicador,
			BigDecimal idempresa) throws RemoteException;

	public String clientesIndicadoresManualesDelete(BigDecimal idindicador,
			BigDecimal idempresa) throws RemoteException;

	public String clientesIndicadoresManualesCreate(String indicador,
			BigDecimal idtipoindicador, String queryseleccion,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String clientesIndicadoresManualesCreateOrUpdate(
			BigDecimal idindicador, String indicador,
			BigDecimal idtipoindicador, String queryseleccion,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String clientesIndicadoresManualesUpdate(BigDecimal idindicador,
			String indicador, BigDecimal idtipoindicador,
			String queryseleccion, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: clientesIndicadoresClientes Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Fri Apr 30 15:18:45 GMT-03:00 2010
	 */

	public List getClientesIndicadoresClientesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesIndicadoresClientesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesIndicadoresClientesPK(BigDecimal iddato,
			BigDecimal idempresa) throws RemoteException;

	// por cliente

	public List getClientesIndicadoresXClienteAll(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getClientesIndicadoresXClienteOcu(long limit, long offset,
			BigDecimal idcliente, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public String clientesIndicadoresClientesDelete(BigDecimal iddato,
			BigDecimal idempresa) throws RemoteException;

	public String clientesIndicadoresClientesCreate(BigDecimal idcliente,
			BigDecimal idindicador, BigDecimal valor, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String clientesIndicadoresClientesCreateOrUpdate(BigDecimal iddato,
			BigDecimal idcliente, BigDecimal idindicador, BigDecimal valor,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String clientesIndicadoresClientesUpdate(BigDecimal iddato,
			BigDecimal idcliente, BigDecimal idindicador, BigDecimal valor,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: bacoRefOperaciones Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Jun 15 11:08:58 ART 2010
	 */
	public List getBacoRefOperacionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoRefOperacionesXProceso(String proceso,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoRefOperacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacoRefOperacionesPK(BigDecimal idoperacion,
			BigDecimal idempresa) throws RemoteException;

	public String bacoRefOperacionesDelete(BigDecimal idoperacion,
			BigDecimal idempresa) throws RemoteException;

	public String bacoRefOperacionesDeleteLogico(BigDecimal idoperacion,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String bacoRefOperacionesCreate(String operacion,
			String descripcion, BigDecimal puntaje, String tipo, String signo,
			java.sql.Date fechadesde, java.sql.Date fechahasta,
			java.sql.Date fechabaja, String proceso, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String bacoRefOperacionesCreateOrUpdate(BigDecimal idoperacion,
			String operacion, String descripcion, BigDecimal puntaje,
			String tipo, String signo, java.sql.Date fechadesde,
			java.sql.Date fechahasta, java.sql.Date fechabaja, String proceso,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String bacoRefOperacionesUpdate(BigDecimal idoperacion,
			String operacion, String descripcion, BigDecimal puntaje,
			String tipo, String signo, java.sql.Date fechadesde,
			java.sql.Date fechahasta, java.sql.Date fechabaja, String proceso,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: bacoRefCatalogo Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Jun 15 15:54:26 ART 2010
	 */

	public List getBacoRefCatalogoAll(long limit, long offset, String filtro,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoRefCatalogoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacoRefCatalogoPK(BigDecimal idcatalogo, BigDecimal idempresa)
			throws RemoteException;

	public String bacoRefCatalogoDelete(BigDecimal idcatalogo,
			BigDecimal idempresa) throws RemoteException;

	public String bacoRefCatalogoDeleteLogico(BigDecimal idcatalogo,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String bacoRefCatalogoCreate(String codigo_st, String descrip_st,
			BigDecimal puntaje, BigDecimal comprometido, BigDecimal utilizado,
			java.sql.Date fechadesde, java.sql.Date fechahasta,
			java.sql.Date fechabaja,
			// 20120315 - EJV - Mantis 702 -->
			BigDecimal idcatalogocategoria,
			// <--
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String bacoRefCatalogoCreateOrUpdate(BigDecimal idcatalogo,
			String codigo_st, String descrip_st, BigDecimal puntaje,
			BigDecimal comprometido, BigDecimal utilizado,
			java.sql.Date fechadesde, java.sql.Date fechahasta,
			java.sql.Date fechabaja, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String bacoRefCatalogoUpdate(BigDecimal idcatalogo,
			String codigo_st, String descrip_st, BigDecimal puntaje,
			BigDecimal comprometido, BigDecimal utilizado,
			java.sql.Date fechadesde, java.sql.Date fechahasta,
			java.sql.Date fechabaja,
			// 20120315 - EJV - Mantis 702 -->
			BigDecimal idcatalogocategoria,
			// <--
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: bacoRefFuentes Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Jun 15 17:55:58 ART 2010
	 */

	public List getBacoRefFuentesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoRefFuentesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacoRefFuentesPK(BigDecimal idfuente, BigDecimal idempresa)
			throws RemoteException;

	public String bacoRefFuentesDelete(BigDecimal idfuente, BigDecimal idempresa)
			throws RemoteException;

	public String bacoRefFuentesCreate(String fuente, BigDecimal idcliente,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String bacoRefFuentesCreateOrUpdate(BigDecimal idfuente,
			String fuente, BigDecimal idcliente, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String bacoRefFuentesUpdate(BigDecimal idfuente, String fuente,
			BigDecimal idcliente, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: bacoRefPrepro Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Wed Jun 16 10:12:02 ART 2010
	 */

	public List getBacoRefPreproAll(long limit, long offset, String filtro,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoRefPreproOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoRefPreproPK(BigDecimal idpreprospecto,
			BigDecimal idempresa) throws RemoteException;

	public String bacoRefPreproDelete(BigDecimal idpreprospecto,
			BigDecimal idempresa) throws RemoteException;

	public String bacoRefPreproCreate(String nombre, String apellido,
			BigDecimal idreferente, BigDecimal idvendedor, BigDecimal idfuente,
			java.sql.Date fecha, String telefono, String celular, String email,
			BigDecimal idprovincia, BigDecimal idlocalidad,
			String observaciones, BigDecimal procesado,
			// 20120314 - EJV - Mantis 702 -->
			BigDecimal idrefestado, BigDecimal idrefsubestado,
			// <--
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String bacoRefPreproCreateOrUpdate(BigDecimal idpreprospecto,
			String nombre, String apellido, BigDecimal idreferente,
			BigDecimal idvendedor, BigDecimal idfuente, java.sql.Date fecha,
			String telefono, String celular, String email,
			BigDecimal idprovincia, BigDecimal idlocalidad,
			String observaciones, BigDecimal procesado, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String bacoRefPreproUpdate(BigDecimal idpreprospecto, String nombre,
			String apellido, BigDecimal idreferente, BigDecimal idvendedor,
			BigDecimal idfuente, java.sql.Date fecha, String telefono,
			String celular, String email, BigDecimal idprovincia,
			BigDecimal idlocalidad, String observaciones, BigDecimal procesado,
			// 20120314 - EJV - Mantis 702 -->
			BigDecimal idrefestado, BigDecimal idrefsubestado,
			// <--
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: bacoRefCtaCte Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Jun 17 12:34:05 ART 2010
	 */

	public List getBacoRefCtaCteAll(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getBacoRefCtaCteOcu(long limit, long offset, String ocurrencia,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getBacoRefCtaCteReportePuntos(BigDecimal idvendedor,
			BigDecimal idcliente, java.sql.Date fechadesde,
			java.sql.Date fechahasta, BigDecimal idempresa)
			throws RemoteException;

	public List getBacoRefCtaCtePK(BigDecimal idctacte, BigDecimal idempresa)
			throws RemoteException;

	public BigDecimal getBacoRefCtaCtePuntosCliente(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	public String bacoRefCtaCteDelete(BigDecimal idctacte, BigDecimal idempresa)
			throws RemoteException;

	public String bacoRefCtaCteCreate(BigDecimal idcliente,
			BigDecimal idoperacion, BigDecimal idreferido, BigDecimal puntos,
			java.sql.Date fecha, String observaciones, BigDecimal idpedido,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String bacoRefCtaCteCreateOrUpdate(BigDecimal idctacte,
			BigDecimal idcliente, BigDecimal idoperacion,
			BigDecimal idreferido, BigDecimal puntos, java.sql.Date fecha,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String bacoRefCtaCteUpdate(BigDecimal idctacte,
			BigDecimal idcliente, BigDecimal idoperacion,
			BigDecimal idreferido, BigDecimal puntos, java.sql.Date fecha,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: bacoRefTipoOperaciones Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Fri Jul 02 09:36:21 ART 2010
	 */

	public List getBacoRefTipoOperacionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoRefTipoOperacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacoRefTipoOperacionesPK(BigDecimal idtipooperacion,
			BigDecimal idempresa) throws RemoteException;

	public String bacoRefTipoOperacionesDelete(BigDecimal idtipooperacion,
			BigDecimal idempresa) throws RemoteException;

	public String bacoRefTipoOperacionesCreate(String tipooperacion,
			String observaciones, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String bacoRefTipoOperacionesCreateOrUpdate(
			BigDecimal idtipooperacion, String tipooperacion,
			String observaciones, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String bacoRefTipoOperacionesUpdate(BigDecimal idtipooperacion,
			String tipooperacion, String observaciones, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public List getClientesRemitosEstadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * EJV - 20100806 - REASIGNACION DE PEDIDOS BEGIN
	 * 
	 * */

	// para todo (ordena por el segundo campo por defecto)
	public List getClientesPedidosReasignacionAll(long limit, long offset,
			BigDecimal idestado, String tipopedido, String orden,
			BigDecimal idempresa) throws RemoteException;

	// para una ocurrencia (ordena por el segundo campo por defecto)

	public List getClientesPedidosReasignacionOcu(long limit, long offset,
			BigDecimal idestado, String tipopedido, String filtro,
			String orden, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesAnexosLocalidades Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Mon Aug 09 15:06:36 ART 2010
	 */

	public List getVClientesAnexosLocalidadesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getVClientesAnexosLocalidadesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesExpresosZonasReasignacion Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Mon Aug 09 15:06:36 ART 2010
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVClientesExpresosZonasReasignacionAll(long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getVClientesExpresosZonasReasignacionOcu(long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public String clientesPedidosNormalesReasignarDistribucion(
			String[] idpedidocabe, BigDecimal idanexolocalidaddestino,
			BigDecimal idexpresodestino, BigDecimal idzonadestino,
			String usuarioact, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public String clientesPedidosRegalosEntregasReasignarDistribucion(
			String[] idpedidocabe, BigDecimal idanexolocalidaddestino,
			BigDecimal idexpresodestino, BigDecimal idzonadestino,
			String usuarioact, BigDecimal idempresa) throws RemoteException,
			SQLException;

	/**
	 * END - REASIGNACION DE PEDIDOS
	 * */

	/**
	 * Metodos para la entidad: vClientesRemitosDistribucion Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Mon Aug 23 13:37:09 ART 2010
	 */

	public List getVClientesRemitosDistribucionPK(BigDecimal idremitocliente,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: clientesUsuarioZona Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Sep 07 16:01:29 ART 2010
	 */

	public List getClientesUsuarioZonaAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesUsuarioZonaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesUsuarioZonaPendientes(BigDecimal idempresa)
			throws RemoteException;

	public List getClientesZonaUsuarioPendientes(BigDecimal idempresa)
			throws RemoteException;

	public List getClientesUsuarioZonaPK(BigDecimal idusuario,
			BigDecimal idempresa) throws RemoteException;

	public BigDecimal getZonaXUsuario(BigDecimal idusuario, BigDecimal idempresa)
			throws RemoteException;

	public String clientesUsuarioZonaDelete(BigDecimal idusuario,
			BigDecimal idempresa) throws RemoteException;

	public String clientesUsuarioZonaCreate(BigDecimal idusuario,
			BigDecimal idzona, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	/**
	 * Metodos para la entidad: clientesRemitosConformacion Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Sep 07 10:05:56 ART 2010
	 */

	public List getClientesRemitosConformacionXRemitoAll(long limit,
			long offset, BigDecimal idremitocliente, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesRemitosConformacionStatus Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Tue Sep 28 15:33:05 ART 2010
	 */

	public List getClientesRemitosPendientesValidarAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesRemitosPendientesValidarOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getVClientesRemitosConformacionStatusPK(BigDecimal nrosucursal,
			BigDecimal nroremitocliente, BigDecimal idzona, BigDecimal idempresa)
			throws RemoteException;

	public String clientesRemitosConformacion(int idestadoNuevo,
			BigDecimal nrosucursal, BigDecimal nroremitocliente,
			BigDecimal idzona, String origentransaccion, String usuarioalt,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public String clientesRemitosPreconformarXHRF(String[] nrohojarutafinal,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public String clientesRemitosConformarPreconformados(int idestadoNuevo,
			BigDecimal nrosucursal, BigDecimal nroremitocliente,
			BigDecimal idzona, String origentransaccion, String tipoinput,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	/**
	 * Metodos para la entidad: vClientesRemitosHojaRutaFinalReimprime
	 * Copyrigth(r) sysWarp S.R.L. Fecha de creacion: Tue Sep 21 15:10:09 ART
	 * 2010
	 */

	public List getVClientesRemitosHojaRutaFinalReimprimeAll(long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getVClientesRemitosHojaRutaFinalReimprimeOcu(long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	// 20101210 - EJV - Mantis 637 -->

	public List getVClientesRemitosHojaRutaFinalArchivoAndreaniAll(long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getVClientesRemitosHojaRutaFinalArchivoAndreaniOcu(long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public String[] generaArchivoAndreani(String[] nrohojarutafinal,
			String usuarioact, BigDecimal idempresa) throws RemoteException,
			SQLException;

	// <--

	public String clientesRemitosValidarEstado(BigDecimal nrosucursal,
			BigDecimal nroremitocliente, BigDecimal idestadoesperado,
			String tipoinput, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public String clientesRemitosGeneraStickersAndreani(
			BigDecimal nrohojaarmado, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public BigDecimal getClientesRemitosBultos(BigDecimal nrosucursal,
			BigDecimal nroremitocliente, BigDecimal idempresa)
			throws RemoteException;

	public String clientesRemitosModificarBulto(BigDecimal nrosucursal,
			BigDecimal nroremitocliente, BigDecimal totalbultos,
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	public List getClientesRemitosHATotalesFamila(String idremitosIn,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesRemitosControlDespachosAll(long limit, long offset,
			String orden, String tipopedido, BigDecimal idempresa)
			throws RemoteException;

	public List getClientesRemitosControlDespachosOcu(long limit, long offset,
			String filtro, String orden, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesRemitosEstadoActual Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Wed Oct 20 14:22:33 ART 2010
	 */

	public List getVClientesRemitosEstadoActualAll(long limit, long offset,
			String filtro, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesRemitosHojaArmadoReimprime Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Thu Oct 21 11:29:27 ART 2010
	 */

	public List getVClientesRemitosHojaArmadoReimprimeAll(long limit,
			long offset, BigDecimal idempresa) throws RemoteException;

	public List getVClientesRemitosHojaArmadoReimprimeOcu(long limit,
			long offset, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public float getPorcentajeDescuentoIinBin(String iinBin)
			throws RemoteException;

	/**
	 * Metodos para la entidad: pedidosRegalosEstados Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Nov 10 15:14:42 ART 2010
	 */

	public List getPedidosRegalosEstadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidosRegalosEstadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getPedidosRegalosEstadosPK(BigDecimal idestado,
			BigDecimal idempresa) throws RemoteException;

	public String pedidosRegalosEstadosDelete(BigDecimal idestado,
			BigDecimal idempresa) throws RemoteException;

	public String pedidosRegalosEstadosCreate(String estado,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String pedidosRegalosEstadosCreateOrUpdate(BigDecimal idestado,
			String estado, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String pedidosRegalosEstadosUpdate(BigDecimal idestado,
			String estado, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: vPedidosRegalosEstado Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Nov 10 16:05:22 GYT 2010
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVPedidosRegalosEstadoAll(long limit, long offset,
			String filtro, BigDecimal idempresa) throws RemoteException;

	public List getPedidosRegalosDetalleXPedido(
			BigDecimal idpedido_regalos_cabe, BigDecimal idempresa)
			throws RemoteException;

	public List getPedidosRegalosClienteXPedido(
			BigDecimal idpedido_regalos_cabe, BigDecimal idempresa)
			throws RemoteException;

	// para todo (ordena por el segundo campo por defecto)
	public List getPedidosRegalosHijosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidosRegalosHijosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesRemitosRegalosEntregas Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Tue Dec 14 12:45:32 ART 2010
	 */
	public List getVClientesRemitosRegalosEntregasAll(long limit, long offset,
			String filtro, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vPedidosRegalosValorizacion Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Dec 22 10:55:24 ART 2010
	 */

	public List getVPedidosRegalosValorizacionAll(long limit, long offset,
			String filtro, BigDecimal idempresa) throws RemoteException;

	public BigDecimal getVPedidosRegalosValorizacionSumTotal(String filtro,
			String consiniva, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vPedidosRegalosValorizacionHijos Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Thu Dec 23 09:59:27 ART 2010
	 * 
	 */

	public List getVPedidosRegalosValorizacionHijosPK(
			BigDecimal idpedido_regalos_padre, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesRemitosTotalCondicionFlete Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Tue Jan 04 15:02:54 ART 2011
	 */

	public List getVClientesRemitosTotalCondicionFleteAll(long limit,
			long offset, int mes, int ano, int idestado, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: clientesPromociones Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Jan 18 09:58:24 ART 2011
	 */

	public List getClientesPromocionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesPromocionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesPromocionesPK(BigDecimal idpromocion,
			BigDecimal idempresa) throws RemoteException;

	public String clientesPromocionesDelete(BigDecimal idpromocion,
			BigDecimal idempresa) throws RemoteException;

	public String clientesPromocionesCreate(String promocion,
			BigDecimal idtipoclie, java.sql.Date fechadesde,
			java.sql.Date fechahasta, String convenio, BigDecimal porc_desc_ci,
			BigDecimal porc_desc_ci_react, BigDecimal porc_liq,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String clientesPromocionesCreateOrUpdate(BigDecimal idpromocion,
			String promocion, BigDecimal idtipoclie, java.sql.Date fechadesde,
			java.sql.Date fechahasta, String convenio, BigDecimal porc_desc_ci,
			BigDecimal porc_desc_ci_react, BigDecimal porc_liq,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String clientesPromocionesUpdate(BigDecimal idpromocion,
			String promocion, BigDecimal idtipoclie, java.sql.Date fechadesde,
			java.sql.Date fechahasta, String convenio, BigDecimal porc_desc_ci,
			BigDecimal porc_desc_ci_react, BigDecimal porc_liq,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// 20110124 - EJV - Mantis 597 -->
	public List getPromocionesActivasXTipoClie(BigDecimal idtipoclie,
			BigDecimal idempresa) throws RemoteException;

	// <--

	// 20110907 - EJV - Mantis 777 --
	// Metodo ya existente en ClientesBean, solo
	// se publico en interface.

	public int getExistePromocionActivaEnPeriodo(BigDecimal idpromocion,
			BigDecimal idtipoclie, java.sql.Date fechadesde,
			java.sql.Date fechahasta, BigDecimal idempresa)
			throws RemoteException;

	// <--

	/**
	 * Metodos para la entidad: pedidosVentasEspeciales Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Jan 27 11:07:27 ART 2011
	 */

	public List getPedidosVentasEspecialesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidosVentasEspecialesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getPedidosVentasEspecialesPK(BigDecimal idventaespecial,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidosVentasEspecialesActivas(BigDecimal idempresa)
			throws RemoteException;

	public String pedidosVentasEspecialesDelete(BigDecimal idventaespecial,
			BigDecimal idempresa) throws RemoteException;

	public String pedidosVentasEspecialesCreate(String ventaespecial,
			java.sql.Date fechadesde, java.sql.Date fechahasta,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String pedidosVentasEspecialesCreateOrUpdate(
			BigDecimal idventaespecial, String ventaespecial,
			java.sql.Date fechadesde, java.sql.Date fechahasta,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String pedidosVentasEspecialesUpdate(BigDecimal idventaespecial,
			String ventaespecial, java.sql.Date fechadesde,
			java.sql.Date fechahasta, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: pedidosReasignacionDespositos Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Fri Feb 04 13:46:54 ART 2011
	 */

	public List getPedidosReasignacionDespositosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidosReasignacionDespositosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getPedidosReasignacionDespositosPK(
			BigDecimal idreasignaciondeposito, BigDecimal idempresa)
			throws RemoteException;

	public String pedidosReasignacionDespositosDelete(
			BigDecimal idreasignaciondeposito, BigDecimal idempresa)
			throws RemoteException;

	public String pedidosReasignacionDespositosCreate(BigDecimal codigo_dt,
			String tipo, java.sql.Date fechadesde, java.sql.Date fechahasta,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String pedidosReasignacionDespositosCreateOrUpdate(
			BigDecimal idreasignaciondeposito, BigDecimal codigo_dt,
			String tipo, java.sql.Date fechadesde, java.sql.Date fechahasta,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String pedidosReasignacionDespositosUpdate(
			BigDecimal idreasignaciondeposito, BigDecimal codigo_dt,
			String tipo, java.sql.Date fechadesde, java.sql.Date fechahasta,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: bacoClustersLogistica Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Mar 31 16:05:57 ART 2011
	 */

	public List getBacoClustersLogisticaAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoClustersLogisticaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacoClustersLogisticaPK(BigDecimal idclusterlogistica,
			BigDecimal idempresa) throws RemoteException;

	public String bacoClustersLogisticaDelete(BigDecimal idclusterlogistica,
			BigDecimal idempresa) throws RemoteException;

	public String bacoClustersLogisticaCreate(String clusterlogistica,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String bacoClustersLogisticaCreateOrUpdate(
			BigDecimal idclusterlogistica, String clusterlogistica,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String bacoClustersLogisticaUpdate(BigDecimal idclusterlogistica,
			String clusterlogistica, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/* 20110406 - EJV Mantis 696 */

	public List getClientesCambioFechaEstadoAll(long limit, long offset,
			BigDecimal idestado, java.sql.Date fechadesde, BigDecimal idempresa)
			throws RemoteException;

	public String clientesCambioFechaEstadoUpdate(
			String[] idestadoclienteVector, java.sql.Date fechadesde, String usuarioact,
			BigDecimal idempresa) throws RemoteException, SQLException;

	/**/
	/**
	 * Metodos para la entidad: sastiposcontactos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri May 27 09:12:29 ART 2011
	 */
	public List getSastiposcontactosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getSastiposcontactosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getSastiposcontactosPK(BigDecimal idtipocontacto,
			BigDecimal idempresa) throws RemoteException;

	public String sastiposcontactosDelete(BigDecimal idtipocontacto,
			BigDecimal idempresa) throws RemoteException;

	public String sastiposcontactosCreate(String tipocontacto,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String sastiposcontactosCreateOrUpdate(BigDecimal idtipocontacto,
			String tipocontacto, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String sastiposcontactosUpdate(BigDecimal idtipocontacto,
			String tipocontacto, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: sascanalescontactos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri May 27 10:10:38 ART 2011
	 */

	public List getSascanalescontactosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getSascanalescontactosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getSascanalescontactosPK(BigDecimal idcanalcontacto,
			BigDecimal idempresa) throws RemoteException;

	public String sascanalescontactosDelete(BigDecimal idcanalcontacto,
			BigDecimal idempresa) throws RemoteException;

	public String sascanalescontactosCreate(String canalcontacto,
			BigDecimal idtipocontacto, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String sascanalescontactosCreateOrUpdate(BigDecimal idcanalcontacto,
			String canalcontacto, BigDecimal idtipocontacto, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String sascanalescontactosUpdate(BigDecimal idcanalcontacto,
			String canalcontacto, BigDecimal idtipocontacto, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: sasmotivoscontactos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri May 27 10:58:35 ART 2011
	 */

	public List getSasmotivoscontactosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getSasmotivoscontactosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getSasmotivoscontactosPK(BigDecimal idmotivocontacto,
			BigDecimal idempresa) throws RemoteException;

	public String sasmotivoscontactosDelete(BigDecimal idmotivocontacto,
			BigDecimal idempresa) throws RemoteException;

	public String sasmotivoscontactosCreate(String motivocontacto,
			BigDecimal idtipocontacto, BigDecimal idcanalcontacto,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String sasmotivoscontactosCreateOrUpdate(
			BigDecimal idmotivocontacto, String motivocontacto,
			BigDecimal idtipocontacto, BigDecimal idcanalcontacto,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String sasmotivoscontactosUpdate(BigDecimal idmotivocontacto,
			String motivocontacto, BigDecimal idtipocontacto,
			BigDecimal idcanalcontacto, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: sascontactos Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Fri May 27 12:30:35 ART 2011
	 */
	public List getSascontactosAll(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public List getSascontactosOcu(long limit, long offset,
			BigDecimal idcliente, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getSascontactosPK(BigDecimal idcontacto, BigDecimal idempresa)
			throws RemoteException;

	public String sascontactosDelete(BigDecimal idcontacto, BigDecimal idempresa)
			throws RemoteException;

	public String sascontactosCreate(String descripcion,
			BigDecimal idtipocontacto, BigDecimal idcanalcontacto,
			BigDecimal idmotivocontacto, BigDecimal idaccioncontacto,
			BigDecimal idresultadocontacto, String usuarioalt,
			BigDecimal idempresa, BigDecimal idcliente) throws RemoteException;

	// TODO: AGREGAR ACCION Y RESULTADOS
	public String sascontactosCreateOrUpdate(BigDecimal idcontacto,
			String descripcion, BigDecimal idtipocontacto,
			BigDecimal idcanalcontacto, BigDecimal idmotivocontacto,
			BigDecimal idaccioncontacto, BigDecimal idresultadocontacto,
			String usuarioact, BigDecimal idempresa, BigDecimal idcliente)
			throws RemoteException;

	public String sascontactosUpdate(BigDecimal idcontacto, String descripcion,
			BigDecimal idtipocontacto, BigDecimal idcanalcontacto,
			BigDecimal idmotivocontacto, BigDecimal idaccioncontacto,
			BigDecimal idresultadocontacto, String usuarioact,
			BigDecimal idempresa, BigDecimal idcliente) throws RemoteException;

	/**
	 * Metodos para la entidad: sasaccionescontactos Copyrigth(r) Juan Manuel Furattini
	 * Fecha de creacion: Sat Mar 08 09:30:00 ART 2013
	 */
	public List<String[]> getSasAccionesContactosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List<String[]> getSasAccionesContactosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List<String[]> getSasAccionesContactosPK(BigDecimal idaccioncontacto,
			BigDecimal idempresa) throws RemoteException;

	public List<String[]> getSasAccionesContactosLista(BigDecimal idtipo,
			BigDecimal idcanal, BigDecimal idmotivo, BigDecimal idempresa)
			throws RemoteException;

	public String sasAccionesContactosDelete(BigDecimal idaccioncontacto,
			BigDecimal idempresa) throws RemoteException;

	public String sasAccionesContactosCreate(String accioncontacto,
			BigDecimal idtipocontacto, BigDecimal idcanalcontacto,
			BigDecimal idmotivocontacto, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	/*public String sasAccionesContactosCreateOrUpdate(
			BigDecimal idaccioncontacto, String accioncontacto,
			BigDecimal idtipocontacto, BigDecimal idcanalcontacto,
			BigDecimal idmotivocontacto, String usuario, BigDecimal idempresa)
			throws RemoteException;*/

	public String sasAccionesContactosUpdate(BigDecimal idaccioncontacto,
			String accioncontacto, BigDecimal idtipocontacto,
			BigDecimal idcanalcontacto, BigDecimal idmotivocontacto,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: sasresultadoscontactos Copyrigth(r) Juan Manuel Furattini
	 * Fecha de creacion: Sat Mar 08 09:30:00 ART 2013
	 */
	public List<String[]> getSasResultadosContactosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List<String[]> getSasResultadosContactosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List<String[]> getSasResultadosContactosPK(BigDecimal idresultadocontacto,
			BigDecimal idempresa) throws RemoteException;

	public List<String[]> getSasResultadosContactosLista(BigDecimal idtipo,
			BigDecimal idcanal, BigDecimal idmotivo, BigDecimal idaccion,
			BigDecimal idempresa) throws RemoteException;

	public String sasResultadosContactosDelete(BigDecimal idresultadocontacto,
			BigDecimal idempresa) throws RemoteException;

	public String sasResultadosContactosCreate(String resultadocontacto,
			BigDecimal idtipocontacto, BigDecimal idcanalcontacto,
			BigDecimal idmotivocontacto, BigDecimal idaccioncontacto,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	/*public String sasResultadosContactosCreateOrUpdate(
			BigDecimal idresultadocontacto, String resultadocontacto,
			BigDecimal idtipocontacto, BigDecimal idcanalcontacto,
			BigDecimal idmotivocontacto, BigDecimal idaccioncontacto,
			String usuario, BigDecimal idempresa) throws RemoteException;*/

	public String sasResultadosContactosUpdate(BigDecimal idresultadocontacto,
			String resultadocontacto, BigDecimal idtipocontacto,
			BigDecimal idcanalcontacto, BigDecimal idmotivocontacto,
			BigDecimal idaccioncontacto, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Métodos de Lista
	 */
	public List getSascanalescontactosLista(
			BigDecimal id, BigDecimal idempresa) throws RemoteException;

	public List getSasmotivoscontactosLista(BigDecimal idcanal,
			BigDecimal idtipo, BigDecimal idempresa) throws RemoteException;

	public List getSasHistorialcontactos(long limit, long offset,
			BigDecimal idempresa, Timestamp fechaDesde, Timestamp fechaHasta)
			throws RemoteException;

	public List getSasHistorialcontactosOcu(long limit, long offset,
			BigDecimal idempresa, Timestamp fechaDesde, Timestamp fechaHasta,
			String ocurrencia) throws RemoteException;

	/**
	 * Metodos para la entidad: vclientesRemitosFacturar Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Mon Jul 04 14:04:27 ART 2011
	 */
	public List getVclientesRemitosFacturarAll(long limit, long offset,
			String filtro, String orden, BigDecimal idempresa)
			throws RemoteException;

	public List getVclientesRemitosFacturarPK(BigDecimal totalfacturar,
			BigDecimal idempresa) throws RemoteException;

	public String clientesMovCliDesdeRemitosCreate(String[] vecIdremitocliente,
			BigDecimal idclub, BigDecimal sucursal,
			java.sql.Timestamp fechamov, BigDecimal tipomov, String tipomovs,
			String condicionletra, String letracontador, BigDecimal cambio,
			BigDecimal moneda, int ejercicioactivo, String usuarioalt,
			BigDecimal idempresa) throws RemoteException, SQLException;

	// ---------------------------------------------------------------------------------------------------------------

	/*
	 * VISA
	 */

	public String setPresentacionVISA(BigDecimal idclub,
			BigDecimal idtarjetacredito, java.sql.Date fechaPresentacion,
			String archivo, String path, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	/*
	 * AMEX
	 */

	public String setPresentacionAMEX(BigDecimal idclub,
			BigDecimal idtarjetacredito, java.sql.Date fechaPresentacion,
			String archivo, String path, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	/*
	 * AMEX
	 */

	public String setPresentacionMASTERCARD(BigDecimal idclub,
			BigDecimal idtarjetacredito, java.sql.Date fechaPresentacion,
			String archivo, String path, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	/*
	 * DINERS
	 */

	public String setPresentacionDINERS(BigDecimal idclub,
			BigDecimal idtarjetacredito, java.sql.Date fechaPresentacion,
			String archivo, String path, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	// -- mantis 923 esta es la cabecera anterior.
	public String setEXPresentacionDINERS(BigDecimal idclub,
			BigDecimal idtarjetacredito, java.sql.Date fechaPresentacion,
			String archivoCtrl, String archivConsumo, String path,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	/*
	 * CABAL
	 */

	public String setPresentacionCABAL(BigDecimal idclub,
			BigDecimal idtarjetacredito, java.sql.Date fechaPresentacion,
			String archivo, String path, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	/**
	 * Metodos para la entidad: clientesMovcliRemitosLog Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Tue Aug 23 14:12:37 ART 2011
	 */

	public List getClientesMovcliRemitosLogAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesMovcliRemitosLogOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// 20110920 - EJV - Mantis 776 -->

	public List getClientesTarjetasPresentacionRecepcionXClie(
			BigDecimal idcliente, BigDecimal idclub,
			BigDecimal idtarjetacredito, java.sql.Date fechaenvio,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesTarjetasPresentacionRecepcionPK(BigDecimal idmovcuo,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: clientesTarjetasMotivos Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Sep 15 09:13:55 ART 2011
	 */

	public List getClientesTarjetasMotivosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public String clientesPresentacionGenerarRechazos(Hashtable htRechazos,
			BigDecimal idempresa, String usuarioact) throws RemoteException,
			SQLException;

	public BigDecimal getClientesTarjetasPresentacionTotalAceptar(
			BigDecimal idclub, BigDecimal idtarjetacredito,
			java.sql.Date fechaenvio, BigDecimal idempresa)
			throws RemoteException;

	public String cobranzasMovClientePresentacionTarjeta(BigDecimal idclub,
			BigDecimal idtarjetacredito, java.sql.Date fechapresentacion,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	// 20110926 - EJV - Mantis 779 -->

	/**
	 * Metodos para la entidad: vClientesMovCliCancelar Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Mon Sep 26 14:21:57 ART 2011
	 */

	public List getVClientesMovCliCancelarAll(long limit, long offset,
			String filtro, BigDecimal idempresa) throws RemoteException;

	public String clientesMovimientoClienteNC(BigDecimal nrointerno_cancelar,
			int ejercicioactivo, BigDecimal idmotivonc, String usuarioalt,
			BigDecimal idempresa) throws RemoteException, SQLException;

	// <--

	// --> 20110928 - EJV - Mantis 782

	/**
	 * Metodos para la entidad: vClientesAplicaciones Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Sep 28 17:28:54 ART 2011
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getVClientesAplicacionesXCliente(long limit, long offset,
			BigDecimal idcliente, String filtro, BigDecimal idempresa)
			throws RemoteException;

	public List getVClientesMovCuotaXCliente(long limit, long offset,
			BigDecimal idcliente, String filtro, BigDecimal idempresa)
			throws RemoteException;

	// <--

	// --> 20110929 - EJV - Mantis 771
	public List getVClientesTarjetasXCliente(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	// <--

	/**
	 * Metodos para la entidad: vClientesMovCli Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Mar 09 09:42:36 ART 2012
	 */

	public List getVClientesMovCliEtiquetas(long limit, long offset,
			String filtro, BigDecimal idempresa) throws RemoteException;

	// 20120313 - EJV - Mantis 702 -->

	/**
	 * Metodos para la entidad: bacoRefEstados Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Mar 13 15:00:43 ART 2012
	 */

	public List getBacoRefEstadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoRefEstadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacoRefEstadosPK(BigDecimal idrefestado, BigDecimal idempresa)
			throws RemoteException;

	public String bacoRefEstadosDelete(BigDecimal idrefestado,
			BigDecimal idempresa) throws RemoteException;

	public String bacoRefEstadosCreate(String refestado, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String bacoRefEstadosUpdate(BigDecimal idrefestado,
			String refestado, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: bacoRefSubEstados Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Mar 13 15:00:55 ART 2012
	 */
	public List getBacoRefSubEstadosAll(long limit, long offset,
			BigDecimal idrefestado, BigDecimal idempresa)
			throws RemoteException;

	public List getBacoRefSubEstadosOcu(long limit, long offset,
			BigDecimal idrefestado, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getBacoRefSubEstadosPK(BigDecimal idrefsubestado,
			BigDecimal idempresa) throws RemoteException;

	public String bacoRefSubEstadosDelete(BigDecimal idrefsubestado,
			BigDecimal idempresa) throws RemoteException;

	public String bacoRefSubEstadosCreate(String refsubestado,
			BigDecimal idrefestado, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String bacoRefSubEstadosUpdate(BigDecimal idrefsubestado,
			String refsubestado, BigDecimal idrefestado, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: bacoRefCatalogoCategoria Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Mar 15 09:25:38 ART 2012
	 */

	public List getBacoRefCatalogoCategoriaAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacoRefCatalogoCategoriaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacoRefCatalogoCategoriaPK(BigDecimal idcatalogocategoria,
			BigDecimal idempresa) throws RemoteException;

	public String bacoRefCatalogoCategoriaDelete(
			BigDecimal idcatalogocategoria, BigDecimal idempresa)
			throws RemoteException;

	public String bacoRefCatalogoCategoriaCreate(String catalogocategoria,
			String observaciones, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String bacoRefCatalogoCategoriaUpdate(
			BigDecimal idcatalogocategoria, String catalogocategoria,
			String observaciones, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// <--

	public String bacoRefCtaCtePuntosFromFile(BufferedReader input,
			String table, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public List getBacoRefCtaCteFromFileAll(long limit, long offset,
			String tablename, BigDecimal idempresa) throws RemoteException;

	public String bacoRefCtaCteCargarPuntosFromFile(String table, String file,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public List getBacoRefCtaCteFromFileOcu(long limit, long offset,
			String tablename, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: vBacoReferidosEvolucion Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Fri Apr 13 14:42:13 ART 2012
	 */

	public List getVBacoReferidosEvolucionAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getVBacoReferidosEvolucionOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// 20120426 - EJV - Mantis 708 -->

	/**
	 * Metodos para la entidad: vClientesLCSASVentasRevisar Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Apr 26 12:07:59 ART 2012
	 */

	public List getVClientesLCSASVentasRevisarAll(long limit, long offset,
			int anio, int mes, BigDecimal idtipoclie, BigDecimal idestado,
			BigDecimal idempresa) throws RemoteException;

	public List getVClientesLCSASVentasRevisarXCliente(long limit, long offset,
			int anio, int mes, BigDecimal idtipoclie, BigDecimal idestado,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesLCSASVentasDescuentoCero Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Thu May 03 09:08:59 ART 2012
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getVClientesLCSASVentasDescuentoCeroAll(long limit,
			long offset, int anio, int mes, BigDecimal idtipoclie,
			BigDecimal idestado, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesLCSASVentasDescuentoAplicadoTotal
	 * Copyrigth(r) sysWarp S.R.L. Fecha de creacion: Thu May 03 14:13:51 ART
	 * 2012
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVClientesLCSASVentasDescuentoAplicadoTotalAll(long limit,
			long offset, int anio, int mes, BigDecimal idtipoclie,
			BigDecimal idestado, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesLCSASVentasDescuentoAplicadoDeta
	 * Copyrigth(r) sysWarp S.R.L. Fecha de creacion: Fri May 04 10:47:59 ART
	 * 2012
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVClientesLCSASVentasDescuentoAplicadoDetaAll(long limit,
			long offset, int anio, int mes, BigDecimal idtipoclie,
			BigDecimal idestado, BigDecimal porcdesc_apli, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesLCSASVentasSuspensionEr Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Tue May 08 10:04:48 ART 2012
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getVClientesLCSASVentasSuspensionErAll(long limit, long offset,
			int anio, int mes, BigDecimal idtipoclie, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesLCSASVentasSociosActivos Copyrigth(r)
	 * sysWarp S.R.L. Fecha de creacion: Tue May 08 16:28:58 ART 2012
	 */
	// para todo (ordena por el segundo campo por defecto)
	public List getVClientesLCSASVentasSociosActivosAll(long limit,
			long offset, int anio, int mes, BigDecimal idtipoclie,
			BigDecimal idestado, BigDecimal idempresa) throws RemoteException;

	// <--

	// 20120607 - EJV -->

	public List getVclientesCtaCtesGeneral(long limit, long offset,
			String filtro, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesMovCliImprime Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Jun 21 13:36:40 ART 2012
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVClientesMovCliImprimeAll(long limit, long offset,
			String filtro, String orden, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: ClientesMov Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Fri Jan Thu Jun 21 13:36:40 ART 2012
	 */

	public List getClientesTipoMovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List ObtenerFacturasXArticulo(String codigo_st, BigDecimal idempresa)
			throws RemoteException;

	public long ObtenerFacturasXArticuloTotal(String codigo_st,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesClientesObtenerUno(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	// 20120911 - EJV -->

	public List getLovClientesMovClieXCliente(long limit, long offset,
			BigDecimal idcliente, String tipomovIN, BigDecimal idempresa)
			throws RemoteException;

	public List getLovClientesMovClieXClienteOcu(long limit, long offset,
			BigDecimal idcliente, String tipomovIN, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public String clientesAplicarComprobante(BigDecimal comp_canc,
			BigDecimal comp_q_canc, BigDecimal importe, String usuarioalt,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public List getClientesMovCliPK(BigDecimal nrointerno, BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesAplicacionesFull Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Sep 19 10:35:58 ART 2012
	 */

	// para todo (ordena por el segundo campo por defecto)
	public List getVClientesAplicacionesFullXClie(long limit, long offset,
			BigDecimal idcliente, BigDecimal idempresa) throws RemoteException;

	public String clientesMovCliDesaplicar(BigDecimal nrointerno_canc,
			BigDecimal nrointerno_qcan, String usuarioact, BigDecimal idempresa)
			throws RemoteException, SQLException;

	// 20121004 - EJV - Mantis 882 -->

	/**
	 * Metodos para la entidad: clientesMovCliMotivosNc Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Oct 04 11:49:25 ART 2012
	 */

	public List getClientesMovCliMotivosNcAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesMovCliMotivosNcOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesMovCliMotivosNcPK(BigDecimal idmotivonc,
			BigDecimal idempresa) throws RemoteException;

	public String clientesMovCliMotivosNcDelete(BigDecimal idmotivonc,
			BigDecimal idempresa) throws RemoteException;

	public String clientesMovCliMotivosNcCreate(String motivonc,
			String afectastock, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String clientesMovCliMotivosNcCreateOrUpdate(BigDecimal idmotivonc,
			String motivonc, String afectastock, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String clientesMovCliMotivosNcUpdate(BigDecimal idmotivonc,
			String motivonc, String afectastock, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	// <--

	/**
	 * Metodos para la entidad: clientesTarjetasGeneracion Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Thu Nov 01 14:12:16 ART 2012
	 */

	public List getClientesTarjetasGeneracionAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesTarjetasGeneracionOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: vClientesMovCuoHist Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Nov 02 15:06:49 ART 2012
	 */

	// por primary key (primer campo por defecto)
	public List getVClientesMovCuoHistPK(BigDecimal idmovcuo,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesvendedorescomisionesAll(long limit, long offset) throws RemoteException;

	public List getClientesvendedorescomisionesOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	public List getClientesvendedorescomisionesPK(int idcomision) throws RemoteException;

	public String clientesvendedorescomisionesDelete(int idcomision) throws RemoteException;

	public String clientesvendedorescomisionesCreate(String descripcion,
			double cantsociodesde, double cantsociohasta,
			double porcdeserdesde, double porcdeserhasta,
			double valorasociacion, double porccartera, String usuarioalt,
			String tipo) throws RemoteException;

	public String clientesvendedorescomisionesCreateOrUpdate(
			int idcomision, String descripcion, double cantsociodesde,
			double cantsociohasta, double porcdeserdesde,
			double porcdeserhasta, double valorasociacion, double porccartera,
			String usuarioact,String tipo) throws RemoteException;

	public String clientesvendedorescomisionesUpdate(int idcomision,
			String descripcion, double cantsociodesde, double cantsociohasta,
			double porcdeserdesde, double porcdeserhasta,
			double valorasociacion, double porccartera, String usuarioact,String tipo) throws RemoteException;
	
	public String AplicacionAutomaticaCobranzas(String[] args)  throws RemoteException;
}
