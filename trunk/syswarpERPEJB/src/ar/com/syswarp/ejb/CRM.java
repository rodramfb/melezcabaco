package ar.com.syswarp.ejb;

import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import javax.ejb.Local;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.math.*;
import javax.ejb.Local;
@Local

public interface CRM {


	public long getTotalEntidadRelacion(String entidad, String[] campos,
			String[] ocurrencia, BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidadOcu(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidad(String entidad, BigDecimal idempresa)
			throws RemoteException;

	public long getTotalEntidadFiltro(String entidad, String filtro,
			BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidadFiltroAlias(String entidad, String filtro,
			BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidadRelacionOcu(String entidad,
			String[] camposFiltro, String[] valorFiltro, String[] camposOcu,
			String[] valorOcu, BigDecimal idempresa) throws RemoteException;

	// Tipos de Clientes
	public List getCrmtiposclientesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmtiposclientesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCrmtiposclientesPK(BigDecimal idtipocliente,
			BigDecimal idempresa) throws RemoteException;

	public String crmtiposclientesDelete(BigDecimal idtipocliente,
			BigDecimal idempresa) throws RemoteException;

	public String crmtiposclientesCreate(String tipocliente,
			String observaciones, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String crmtiposclientesCreateOrUpdate(BigDecimal idtipocliente,
			String tipocliente, String observaciones, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String crmtiposclientesUpdate(BigDecimal idtipocliente,
			String tipocliente, String observaciones, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// Clasificacion de clientes
	public List getCrmclasificacionclientesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmclasificacionclientesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCrmclasificacionclientesPK(
			BigDecimal idclasificacioncliente, BigDecimal idempresa)
			throws RemoteException;

	public String crmclasificacionclientesDelete(
			BigDecimal idclasificacioncliente, BigDecimal idempresa)
			throws RemoteException;

	public String crmclasificacionclientesCreate(String clasificacioncliente,
			BigDecimal idtipocliente, String observaciones,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmclasificacionclientesCreateOrUpdate(
			BigDecimal idclasificacioncliente, String clasificacioncliente,
			BigDecimal idtipocliente, String observaciones, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String crmclasificacionclientesUpdate(
			BigDecimal idclasificacioncliente, String clasificacioncliente,
			BigDecimal idtipocliente, String observaciones, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// Fuente de Captacion
	public List getCrmfuentecaptacionAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmfuentecaptacionOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCrmfuentecaptacionPK(BigDecimal idfuente,
			BigDecimal idempresa) throws RemoteException;

	public String crmfuentecaptacionDelete(BigDecimal idfuente,
			BigDecimal idempresa) throws RemoteException;

	public String crmfuentecaptacionCreate(String fuente,
			BigDecimal valorpresupuesto, BigDecimal valorunitario,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmfuentecaptacionCreateOrUpdate(BigDecimal idfuente,
			String fuente, BigDecimal valorpresupuesto,
			BigDecimal valorunitario, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String crmfuentecaptacionUpdate(BigDecimal idfuente, String fuente,
			BigDecimal valorpresupuesto, BigDecimal valorunitario,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// Tipos de llamadas
	public List getCrmtiposllamadasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmtiposllamadasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCrmtiposllamadasPK(BigDecimal idtipollamada,
			BigDecimal idempresa) throws RemoteException;

	public String crmtiposllamadasDelete(BigDecimal idtipollamada,
			BigDecimal idempresa) throws RemoteException;

	public String crmtiposllamadasCreate(String tipollamada,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmtiposllamadasCreateOrUpdate(BigDecimal idtipollamada,
			String tipollamada, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String crmtiposllamadasUpdate(BigDecimal idtipollamada,
			String tipollamada, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// individuos
	public List getCrmindividuosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmindividuosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmindividuosPK(BigDecimal idindividuos, BigDecimal idempresa)
			throws RemoteException;

	public String crmindividuosDelete(BigDecimal idindividuos,
			BigDecimal idempresa) throws RemoteException;

	public String crmindividuosCreate(String razon_nombre,
			String telefonos_part, String celular, String email,
			String domicilio_part, Timestamp fechanacimiento, String empresa,
			String domicilio_laboral, String telefonos_empr, String profesion,
			String actividad, String deportes, String hobbies,
			String actividad_social, String diario_lectura,
			String revista_lectura, String lugar_veraneo,
			String obseravaciones, String idusuario, String idtipocliente,
			String idclasificacioncliente, String idfuente,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmindividuosCreateOrUpdate(BigDecimal idindividuos,
			String razon_nombre, String telefonos_part, String celular,
			String email, String domicilio_part, Timestamp fechanacimiento,
			String empresa, String domicilio_laboral, String telefonos_empr,
			String profesion, String actividad, String deportes,
			String hobbies, String actividad_social, String diario_lectura,
			String revista_lectura, String lugar_veraneo,
			String obseravaciones, String idusuario, String idtipocliente,
			String idclasificacioncliente, String idfuente, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String crmindividuosUpdate(BigDecimal idindividuos,
			String razon_nombre, String telefonos_part, String celular,
			String email, String domicilio_part, Timestamp fechanacimiento,
			String empresa, String domicilio_laboral, String telefonos_empr,
			String profesion, String actividad, String deportes,
			String hobbies, String actividad_social, String diario_lectura,
			String revista_lectura, String lugar_veraneo,
			String obseravaciones, String idusuario, String idtipocliente,
			String idclasificacioncliente, String idfuente, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// Nexos Familiares
	public List getCrmnexosfamiliaresAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmnexosfamiliaresOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCrmnexosfamiliaresPK(BigDecimal idnexofamiliar,
			BigDecimal idempresa) throws RemoteException;

	public String crmnexosfamiliaresDelete(BigDecimal idnexofamiliar,
			BigDecimal idempresa) throws RemoteException;

	public String crmnexosfamiliaresCreate(String nexofamiliar,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmnexosfamiliaresCreateOrUpdate(BigDecimal idnexofamiliar,
			String nexofamiliar, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String crmnexosfamiliaresUpdate(BigDecimal idnexofamiliar,
			String nexofamiliar, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// Familiares
	public List getCrmfamiliaresAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmfamiliaresOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmfamiliaresPK(BigDecimal idfamiliar, BigDecimal idempresa)
			throws RemoteException;

	public String crmfamiliaresDelete(BigDecimal idfamiliar,
			BigDecimal idempresa) throws RemoteException;

	public String crmfamiliaresCreate(BigDecimal idindividuos,
			BigDecimal idnexofamiliar, String nombre, String profesion,
			String actividad, String email, String telefonos_part,
			String celular, Timestamp fechanacimiento, String deportes,
			String hobbies, String actividad_social, String obseravaciones,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmfamiliaresCreateOrUpdate(BigDecimal idfamiliar,
			BigDecimal idindividuos, BigDecimal idnexofamiliar, String nombre,
			String profesion, String actividad, String email,
			String telefonos_part, String celular, Timestamp fechanacimiento,
			String deportes, String hobbies, String actividad_social,
			String obseravaciones, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String crmfamiliaresUpdate(BigDecimal idfamiliar,
			BigDecimal idindividuos, BigDecimal idnexofamiliar, String nombre,
			String profesion, String actividad, String email,
			String telefonos_part, String celular, Timestamp fechanacimiento,
			String deportes, String hobbies, String actividad_social,
			String obseravaciones, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public List getCrmfamiliaresIndividuoAll(long limit, long offset,
			BigDecimal idindividuos, BigDecimal idempresa)
			throws RemoteException;

	public List getCrmfamiliaresIndividuoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idindividuos, BigDecimal idempresa)
			throws RemoteException;

	// LLamados
	public List getCrmllamadosAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getCrmllamadosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmllamadosPK(BigDecimal idllamado, BigDecimal idempresa)
			throws RemoteException;

	public String crmllamadosDelete(BigDecimal idllamado, BigDecimal idempresa)
			throws RemoteException;

	public String crmllamadosCreate(BigDecimal idtipollamada,
			BigDecimal idusuario, String idindividuos, String idfamiliar,
			Timestamp fechallamada, String obseravaciones,
			BigDecimal idresultadollamada, Timestamp fecharellamada,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmllamadosCreateOrUpdate(BigDecimal idllamado,
			BigDecimal idtipollamada, BigDecimal idusuario,
			String idindividuos, String idfamiliar, Timestamp fechallamada,
			String obseravaciones, BigDecimal idresultadollamada,
			Timestamp fecharellamada, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String crmllamadosUpdate(BigDecimal idllamado,
			BigDecimal idtipollamada, BigDecimal idusuario,
			String idindividuos, String idfamiliar, Timestamp fechallamada,
			String obseravaciones, BigDecimal idresultadollamada,
			Timestamp fecharellamada, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// Tipo de Cotizaciones
	public List getCrmtiposcotizacionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmtiposcotizacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCrmtiposcotizacionesPK(BigDecimal idtipocotizacion,
			BigDecimal idempresa) throws RemoteException;

	public BigDecimal getCrmTCValorUnidaSup(BigDecimal idtipocotizacion,
			BigDecimal idempresa) throws RemoteException;

	public String crmtiposcotizacionesDelete(BigDecimal idtipocotizacion,
			BigDecimal idempresa) throws RemoteException;

	public String crmtiposcotizacionesCreate(String tipocotizacion,
			BigDecimal valorunidadsup, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String crmtiposcotizacionesCreateOrUpdate(
			BigDecimal idtipocotizacion, String tipocotizacion,
			BigDecimal valorunidadsup, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String crmtiposcotizacionesUpdate(BigDecimal idtipocotizacion,
			String tipocotizacion, BigDecimal valorunidadsup,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// Tipo financiaciones
	public List getCrmtiposfinanciacionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmtiposfinanciacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCrmtiposfinanciacionesPK(BigDecimal idtipofinanciacion,
			BigDecimal idempresa) throws RemoteException;

	public String crmtiposfinanciacionesDelete(BigDecimal idtipofinanciacion,
			BigDecimal idempresa) throws RemoteException;

	public String crmtiposfinanciacionesCreate(String tipofinanciacion,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmtiposfinanciacionesCreateOrUpdate(
			BigDecimal idtipofinanciacion, String tipofinanciacion,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String crmtiposfinanciacionesUpdate(BigDecimal idtipofinanciacion,
			String tipofinanciacion, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// Cotizaciones
	public List getCrmcotizacionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmcotizacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCrmcotizacionesPK(BigDecimal idcotizacion,
			BigDecimal idempresa) throws RemoteException;

	public String crmcotizacionesDelete(BigDecimal idcotizacion,
			BigDecimal idempresa) throws RemoteException;

	public String crmcotizacionesCreate(BigDecimal idusuario,
			BigDecimal nrolote, BigDecimal idtipocotizacion,
			String idtipofinanciacion, Double superficie, BigDecimal codigo_md,
			Double valor_unitario, Double valor_total, Double precio_contado,
			Double precio_financiado, BigDecimal idempresa, String usuarioalt,
			String idindividuos, BigDecimal idresultadocotizacion)
			throws RemoteException;

	public String crmcotizacionesCreateOrUpdate(BigDecimal idcotizacion,
			BigDecimal idusuario, BigDecimal nrolote,
			BigDecimal idtipocotizacion, String idtipofinanciacion,
			Double superficie, BigDecimal codigo_md, Double valor_unitario,
			Double valor_total, Double precio_contado,
			Double precio_financiado, String usuarioact, BigDecimal idempresa,
			BigDecimal idresultadocotizacion) throws RemoteException;

	public String crmcotizacionesUpdate(BigDecimal idcotizacion,
			BigDecimal idusuario, BigDecimal nrolote,
			BigDecimal idtipocotizacion, String idtipofinanciacion,
			Double superficie, BigDecimal codigo_md, Double valor_unitario,
			Double valor_total, Double precio_contado,
			Double precio_financiado, BigDecimal idempresa, String usuarioact,
			String idindividuos, BigDecimal idresultadocotizacion)
			throws RemoteException;

	// individuos x usuarios
	public List getCrmindividuosxusuarioAll(long limit, long offset,
			BigDecimal idempresa, String usu) throws RemoteException;

	public List getCrmindividuosxusuarioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String usu)
			throws RemoteException;

	public List getCrmindividuosxusuarioFiltro(long limit, long offset,
			String filtro) throws RemoteException;

	public List getCrmindividuosxusuarioPK(BigDecimal idindividuos,
			BigDecimal idempresa, String usu) throws RemoteException;

	public String crmindividuosxusuarioDelete(BigDecimal idindividuos,
			BigDecimal idempresa) throws RemoteException;

	public String crmindividuosxusuarioCreate(String razon_nombre,
			String telefonos_part, String celular, String email,
			String domicilio_part, Timestamp fechanacimiento, String empresa,
			String domicilio_laboral, String telefonos_empr, String profesion,
			String actividad, String deportes, String hobbies,
			String actividad_social, String diario_lectura,
			String revista_lectura, String lugar_veraneo,
			String obseravaciones, String idusuario, String idtipocliente,
			String idclasificacioncliente, String idfuente,
			String datosvehiculo, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String crmindividuosxusuarioCreateOrUpdate(BigDecimal idindividuos,
			String razon_nombre, String telefonos_part, String celular,
			String email, String domicilio_part, Timestamp fechanacimiento,
			String empresa, String domicilio_laboral, String telefonos_empr,
			String profesion, String actividad, String deportes,
			String hobbies, String actividad_social, String diario_lectura,
			String revista_lectura, String lugar_veraneo,
			String obseravaciones, String idusuario, String idtipocliente,
			String idclasificacioncliente, String idfuente,
			String datosvehiculo, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String crmindividuosxusuarioUpdate(BigDecimal idindividuos,
			String razon_nombre, String telefonos_part, String celular,
			String email, String domicilio_part, Timestamp fechanacimiento,
			String empresa, String domicilio_laboral, String telefonos_empr,
			String profesion, String actividad, String deportes,
			String hobbies, String actividad_social, String diario_lectura,
			String revista_lectura, String lugar_veraneo,
			String obseravaciones, String idusuario, String idtipocliente,
			String idclasificacioncliente, String idfuente,
			String datosvehiculo, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// Familiares x individuo
	public List getCrmfamiliaresxindividuoAll(long limit, long offset,
			BigDecimal idempresa, String idindividuos) throws RemoteException;

	public List getCrmfamiliaresxindividuoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String idindividuos)
			throws RemoteException;

	public long getTotalEntidadOcuxindividuo(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa, String idindividuos)
			throws RemoteException;

	public long getTotalEntidadxindividuo(String entidad, BigDecimal idempresa,
			String idindividuos) throws RemoteException;

	// LLamados x individuo
	public List getCrmllamadosxusuarioAll(long limit, long offset,
			BigDecimal idempresa, String idindividuos) throws RemoteException;

	public List getCrmllamadosxusuarioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String idindividuos)
			throws RemoteException;

	// Cotizaciones x individuo
	public List getCrmcotizacionesxusuarioAll(long limit, long offset,
			BigDecimal idempresa, String idindividuos) throws RemoteException;

	public List getCrmcotizacionesxusuarioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String idindividuos)
			throws RemoteException;

	public long getTotalEntidadxusuario(BigDecimal idempresa, String idusuario)
			throws RemoteException;

	public long getTotalEntidadxusuarioocu(String[] campos, String ocurrencia,
			BigDecimal idempresa, String idusuario) throws RemoteException;

	/**
	 * Metodos para la entidad: crmProductosStatus Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Aug 03 10:09:41 ART 2007
	 */

	public List getCrmProductosStatusAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmProductosStatusOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCrmProductosStatusPK(BigDecimal idproductostatus,
			BigDecimal idempresa) throws RemoteException;

	public String crmProductosStatusDelete(BigDecimal idproductostatus)
			throws RemoteException;

	public String crmProductosStatusCreate(String productostatus,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmProductosStatusCreateOrUpdate(BigDecimal idproductostatus,
			String productostatus, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String crmProductosStatusUpdate(BigDecimal idproductostatus,
			String productostatus, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: crmGruposProductos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Aug 03 11:04:08 ART 2007
	 */

	public List getCrmGruposProductosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmGruposProductosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCrmGruposProductosPK(BigDecimal idgrupoproducto,
			BigDecimal idempresa) throws RemoteException;

	public String crmGruposProductosDelete(BigDecimal idgrupoproducto)
			throws RemoteException;

	public String crmGruposProductosCreate(String grupoproducto,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmGruposProductosCreateOrUpdate(BigDecimal idgrupoproducto,
			String grupoproducto, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String crmGruposProductosUpdate(BigDecimal idgrupoproducto,
			String grupoproducto, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: crmProductos Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Fri Aug 03 11:44:52 ART 2007
	 */

	public List getCrmProductosAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getCrmProductosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmProductosPK(BigDecimal nrolote, BigDecimal idempresa)
			throws RemoteException;

	public BigDecimal getProximoNrolote(BigDecimal idempresa)
			throws RemoteException;

	public String crmProductosDelete(BigDecimal nrolote) throws RemoteException;

	public String crmProductosCreate(BigDecimal nrolote,
			BigDecimal idfamiliacotizacion, BigDecimal idgrupoproducto,
			BigDecimal idproductostatus, String calificacion,
			Double superficie, Double precioxmts, Double precio,
			Double valorcontado, Double boleto, Double cuotasx36,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmProductosCreateOrUpdate(BigDecimal nrolote,
			BigDecimal idfamiliacotizacion, BigDecimal idgrupoproducto,
			BigDecimal idproductostatus, String calificacion,
			Double superficie, Double precioxmts, Double precio,
			Double valorcontado, Double boleto, Double cuotasx36,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String crmProductosUpdate(BigDecimal nrolote,
			BigDecimal idfamiliacotizacion, BigDecimal idgrupoproducto,
			BigDecimal idproductostatus, String calificacion,
			Double superficie, Double precioxmts, Double precio,
			Double valorcontado, Double boleto, Double cuotasx36,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: crmFamiliaCotizacion Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Aug 03 14:04:30 ART 2007
	 */

	public List getCrmFamiliaCotizacionAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getCrmFamiliaCotizacionOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getCrmFamiliaCotizacionPK(BigDecimal idfamiliacotizacion,
			BigDecimal idempresa) throws RemoteException;

	public String crmFamiliaCotizacionDelete(BigDecimal idfamiliacotizacion)
			throws RemoteException;

	public String crmFamiliaCotizacionCreate(String familiacotizacion,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmFamiliaCotizacionCreateOrUpdate(
			BigDecimal idfamiliacotizacion, String familiacotizacion,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String crmFamiliaCotizacionUpdate(BigDecimal idfamiliacotizacion,
			String familiacotizacion, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// -- resultados de las llamadas
	public String crmresultadosllamadosDelete(BigDecimal idresultadollamada,
			BigDecimal idempresa) throws RemoteException;

	public List getcrmresultadosllamadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getcrmresultadosllamadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public String crmresultadosllamadosCreate(String resultadollamada,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmresultadosllamadosUpdate(BigDecimal idresultadollamada,
			String resultadollamada, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public List getcrmresultadosllamadosPK(BigDecimal idresultadollamada,
			BigDecimal idempresa) throws RemoteException;

	// resultado de cotizaciones
	public String crmresultadoscotizacionesDelete(
			BigDecimal idresultadocotizacion, BigDecimal idempresa)
			throws RemoteException;

	public List getcrmresultadoscotizacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getcrmresultadoscotizacionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public String crmresultadoscotizacionesCreate(String resultadocotizacion,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String crmresultadoscotizacionesUpdate(
			BigDecimal idresultadocotizacion, String resultadocotizacion,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public List getcrmresultadoscotizacionesPK(
			BigDecimal idresultadocotizacion, BigDecimal idempresa)
			throws RemoteException;

}