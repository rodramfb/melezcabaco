package ar.com.syswarp.ejb;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.sql.SQLException;
import javax.ejb.Local;

import ar.com.syswarp.entity.Empresa;
@Local
public interface General {
	public List getVariables(String idempresa) throws RemoteException;

	public int getPermisos(String usuario, String formulario)
			throws RemoteException;

	// contable GlobalMeses
	public List getGlobalMeses() throws RemoteException; // LISTAR TODO

	public Timestamp StrToTimestampDDMMYYYY(String dateEntered)
			throws RemoteException;

	public Timestamp StrToTimestampDDMMYYYYHHMISE(String dateEntered)
			throws RemoteException;

	public String TimestampToStrDDMMYYYY(Timestamp timestamp)
			throws RemoteException;

	public String getNumeroFormateado(float numero, int enteros, int decimales)
			throws RemoteException;

	public String colorSaldo(Object objeto, String clasePos, String claseNeg)
			throws RemoteException;

	// ayudas
	public ResultSet getAyudaPk(Long pk) throws RemoteException;

	public Long getIdayuda() throws RemoteException;

	public String getReferencia() throws RemoteException;

	public String getAyuda() throws RemoteException;

	public String getImagen() throws RemoteException;

	public int getAyudaMax() throws RemoteException;

	// -- fin ayudas.
	public boolean esNumerico(String num) throws RemoteException;

	public boolean esEntero(String num) throws RemoteException;

	// getTotalEntidad
	public long getTotalEntidad(String entidad) throws RemoteException;

	// getTotalEntidadOcu
	public long getTotalEntidadOcu(String entidad, String[] campos,
			String ocurrencia) throws RemoteException;

	// getTotalEntidadFiltro
	public long getTotalEntidadFiltro(String entidad, String filtro,
			BigDecimal idempresa) throws RemoteException;

	// getTotalEntidadRelacion
	public long getTotalEntidadRelacion(String entidad, String[] campos,
			String[] ocurrencia) throws RemoteException;

	public String getMenuTreeJS(BigDecimal idusuario) throws RemoteException;

	public String getMenuTreeJSScroll(BigDecimal idusuario)
			throws RemoteException;

	public BigDecimal getUsuarioValidacion(String usuario, String clave)
			throws RemoteException;

	// globalcontadores
	public List getGlobalContadoresAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getGlobalContadoresOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getGlobalContadoresPK(BigDecimal idcontador,
			BigDecimal idempresa) throws RemoteException;

	public List getGlobalContadoresXContador(String contador,
			BigDecimal idempresa) throws RemoteException;

	public String globalContadoresDelete(BigDecimal idcontador,
			BigDecimal idempresa) throws RemoteException;

	// idcontador,contador,valor,descripcion,nrosucursal,idempresa,usuarioalt
	public String globalContadoresCreate(String contador, BigDecimal valor,
			String descripcion, BigDecimal nrosucursal, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String globalContadoresCreateOrUpdate(BigDecimal idcontador,
			String contador, BigDecimal valor, String descripcion,
			BigDecimal nrosucursal, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String globalContadoresUpdate(BigDecimal idcontador,
			String contador, BigDecimal valor, String descripcion,
			BigDecimal nrosucursal, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// globalmonedas
	public List getGlobalmonedasAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalmonedasOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getGlobalmonedasPK(BigDecimal idmoneda) throws RemoteException;

	public String globalmonedasDelete(BigDecimal idmoneda)
			throws RemoteException;

	public String globalmonedasCreate(String moneda, BigDecimal idpais,
			String usuarioalt) throws RemoteException;

	public String globalmonedasCreateOrUpdate(BigDecimal idmoneda,
			String moneda, BigDecimal idpais, String usuarioact)
			throws RemoteException;

	public String globalmonedasUpdate(BigDecimal idmoneda, String moneda,
			BigDecimal idpais, String usuarioact) throws RemoteException;

	// lov pais
	public List getPaisLovAll(long limit, long offset) throws RemoteException;

	public List getPaisLovOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	// pais
	public List getGlobalpaisesAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalpaisesOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getGlobalpaisesPK(BigDecimal idpais) throws RemoteException;

	public String globalpaisesDelete(BigDecimal idpais) throws RemoteException;

	public String globalpaisesCreate(String pais, String usuarioalt)
			throws RemoteException;

	public String globalpaisesCreateOrUpdate(BigDecimal idpais, String pais,
			String usuarioact) throws RemoteException;

	public String globalpaisesUpdate(BigDecimal idpais, String pais,
			String usuarioact) throws RemoteException;

	// provincias
	public List getGlobalprovinciasAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalprovinciasOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	public List getGlobalprovinciasPK(BigDecimal idprovincia)
			throws RemoteException;

	public String globalprovinciasDelete(BigDecimal idprovincia)
			throws RemoteException;

	public String globalprovinciasCreate(String provincia, BigDecimal idpais,
			String usuarioalt) throws RemoteException;

	public String globalprovinciasCreateOrUpdate(BigDecimal idprovincia,
			String provincia, BigDecimal idpais, String usuarioact)
			throws RemoteException;

	public String globalprovinciasUpdate(BigDecimal idprovincia,
			String provincia, BigDecimal idpais, String usuarioact)
			throws RemoteException;

	// setup variables
	public List getSetupvariablesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getSetupvariablesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getSetupvariablesPK(String variable, BigDecimal idempresa)
			throws RemoteException;

	// Replicado para poder llamar desde los beans de pagina.
	public String getValorSetupVariablesNoStatic(String variable,
			BigDecimal idempresa) throws RemoteException;

	public String setupvariablesDelete(String variable, BigDecimal idempresa)
			throws RemoteException;

	public String setupvariablesCreate(String valor, String descripcion,
			String validador, String sistema, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String setupvariablesCreateOrUpdate(String variable, String valor,
			String descripcion, String validador, String sistema,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String setupvariablesUpdate(String variable, String valor,
			String descripcion, String validador, String sistema,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String setupVariablesSetValor(String variable, String valor,
			String sistema, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// globalgrupos
	public List getGlobalgruposAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalgruposOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getGlobalgruposPK(BigDecimal idgrupo) throws RemoteException;

	public String globalgruposDelete(BigDecimal idgrupo) throws RemoteException;

	public String globalgruposCreate(String grupo, String habilitado,
			String usuarioalt) throws RemoteException;

	public String globalgruposCreateOrUpdate(BigDecimal idgrupo, String grupo,
			String habilitado, String usuarioact) throws RemoteException;

	public String globalgruposUpdate(BigDecimal idgrupo, String grupo,
			String habilitado, String usuarioact) throws RemoteException;

	// grupos menues
	public List getGlobalgruposmenuesAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalgruposmenuesOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	public List getGlobalgruposmenuesPK(BigDecimal idgrupo, BigDecimal idmenu)
			throws RemoteException;

	public String globalgruposmenuesDelete(BigDecimal idgrupo, BigDecimal idmenu)
			throws RemoteException;

	public String globalgruposmenuesCreate(BigDecimal idgrupo,
			BigDecimal idmenu, BigDecimal nivel, String usuarioalt)
			throws RemoteException;

	public String globalgruposmenuesCreateOrUpdate(BigDecimal idgrupo,
			BigDecimal idmenu, BigDecimal nivel, String usuarioact)
			throws RemoteException;

	public String globalgruposmenuesUpdate(BigDecimal idgrupo,
			BigDecimal idmenu, BigDecimal nivel, String usuarioact)
			throws RemoteException;

	// lov globalmenues
	public List getGlobalMenuesLovAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalMenuesOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	// lov globalgrupos
	public List getGlobalgrupoLovAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalgrupoOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	// menues
	public List getGlobalmenuesAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalmenuesOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getGlobalmenuesPK(BigDecimal idmenu) throws RemoteException;

	public String globalmenuesDelete(BigDecimal idmenu) throws RemoteException;

	public String globalmenuesCreate(String menu, String link, String target,
			String image1, String image2, String idmenupadre, String usuarioalt)
			throws RemoteException;

	public String globalmenuesCreateOrUpdate(BigDecimal idmenu, String menu,
			String link, String target, String image1, String image2,
			String idmenupadre, String usuarioact) throws RemoteException;

	public String globalmenuesUpdate(BigDecimal idmenu, String menu,
			String link, String target, String image1, String image2,
			String idmenupadre, String usuarioact) throws RemoteException;

	// usuarios
	public List getGlobalusuariosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getGlobalusuariosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getGlobalusuariosPK(BigDecimal idusuario, BigDecimal idempresa)
			throws RemoteException;

	public List getGlobalusuariosXMail(String email, BigDecimal idempresa)
			throws RemoteException;

	public String globalusuariosDelete(BigDecimal idusuario,
			BigDecimal idempresa) throws RemoteException;

	public String globalusuariosCreate(String usuario, String clave,
			String email, String nombre, String habilitado, String usuarioalt,
			String idpuesto, BigDecimal idempresa) throws RemoteException;

	public String globalusuariosCreateOrUpdate(BigDecimal idusuario,
			String usuario, String clave, String email, String nombre,
			String habilitado, String usuarioact, String idpuesto,
			BigDecimal idempresa) throws RemoteException;

	public String globalusuariosUpdate(BigDecimal idusuario, String usuario,
			String clave, String email, String nombre, String habilitado,
			String usuarioact, String idpuesto, BigDecimal idempresa)
			throws RemoteException;

	// usuarios grupos
	public List getGlobalusuariosgruposAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalusuariosgruposOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	public List getGlobalusuariosgruposPK(BigDecimal idusuario,
			BigDecimal idgrupo) throws RemoteException;

	public String globalusuariosgruposDelete(BigDecimal idusuario,
			BigDecimal idgrupo) throws RemoteException;

	public String globalusuariosgruposCreate(BigDecimal idusuario,
			BigDecimal idgrupo, String usuarioalt) throws RemoteException;

	public String globalusuariosgruposCreateOrUpdate(BigDecimal idusuario,
			BigDecimal idgrupo, String usuarioact) throws RemoteException;

	public String globalusuariosgruposUpdate(BigDecimal idusuario,
			BigDecimal idgrupo, String usuarioact) throws RemoteException;

	// lov usuarios
	public List getUsuariosLovAll(long limit, long offset)
			throws RemoteException;

	public List getUsuariosLovOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	// usuarios menues
	public List getGlobalusuariosmenuesAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalusuariosmenuesOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	public List getGlobalusuariosmenuesPK(BigDecimal idusuario,
			BigDecimal idmenu) throws RemoteException;

	public String globalusuariosmenuesDelete(BigDecimal idusuario,
			BigDecimal idmenu) throws RemoteException;

	public String globalusuariosmenuesCreate(BigDecimal idusuario,
			BigDecimal idmenu, BigDecimal nivel, String usuarioalt)
			throws RemoteException;

	public String globalusuariosmenuesCreateOrUpdate(BigDecimal idusuario,
			BigDecimal idmenu, BigDecimal nivel, String usuarioact)
			throws RemoteException;

	public String globalusuariosmenuesUpdate(BigDecimal idusuario,
			BigDecimal idmenu, BigDecimal nivel, String usuarioact)
			throws RemoteException;

	// global plantas
	public List getGlobalplantasAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalplantasOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getGlobalplantasPK(BigDecimal idplanta) throws RemoteException;

	public String globalplantasDelete(BigDecimal idplanta)
			throws RemoteException;

	public String globalplantasCreate(String planta, String domicilio,
			BigDecimal idlocalidad, String codpostal, String telefonos,
			String fax, String tareaquedesa, String email,
			String esheadquarter, String usuarioalt) throws RemoteException;

	public String globalplantasCreateOrUpdate(BigDecimal idplanta,
			String planta, String domicilio, BigDecimal idlocalidad,
			String codpostal, String telefonos, String fax,
			String tareaquedesa, String email, String esheadquarter,
			String usuarioact) throws RemoteException;

	public String globalplantasUpdate(BigDecimal idplanta, String planta,
			String domicilio, BigDecimal idlocalidad, String codpostal,
			String telefonos, String fax, String tareaquedesa, String email,
			String esheadquarter, String usuarioact) throws RemoteException;

	// global puestos
	public List getGlobalpuestosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getGlobalpuestosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getGlobalpuestosPK(BigDecimal idpuesto, BigDecimal idempresa)
			throws RemoteException;

	public String globalpuestosDelete(BigDecimal idpuesto, BigDecimal idempresa)
			throws RemoteException;

	public String globalpuestosCreate(String puesto, BigDecimal idplanta,
			String idconta_facturasa, String idconta_facturasb,
			String idconta_facturasc, String idconta_recibos,
			String idconta_remitos1, String idconta_remitos2,
			String idconta_remitos3, String idconta_remitos4,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String globalpuestosCreateOrUpdate(BigDecimal idpuesto,
			String puesto, BigDecimal idplanta, String idconta_facturasa,
			String idconta_facturasb, String idconta_facturasc,
			String idconta_recibos, String idconta_remitos1,
			String idconta_remitos2, String idconta_remitos3,
			String idconta_remitos4, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String globalpuestosUpdate(BigDecimal idpuesto, String puesto,
			BigDecimal idplanta, String idconta_facturasa,
			String idconta_facturasb, String idconta_facturasc,
			String idconta_recibos, String idconta_remitos1,
			String idconta_remitos2, String idconta_remitos3,
			String idconta_remitos4, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// lov plantas
	// lov pais
	public List getPlantasAll(long limit, long offset) throws RemoteException;

	public List getPlantasOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	// lov contadores
	public List getContadoresAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getContadoresOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	// lov puesto
	public List getPuestosLovAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getPuestosLovOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public BigDecimal getGlobalUsuariosPuesto(BigDecimal idusuario)
			throws RemoteException;

	public BigDecimal getContadorDocumentos(BigDecimal idpuesto,
			String documento) throws RemoteException;

	public String getProximoNumeroDocumento(BigDecimal idcontador,
			BigDecimal idempresa) throws RemoteException;

	public String convertirNumerosALetras(int num) throws RemoteException;

	// formula Cabecera
	public List getFormulas_cabeAll(long limit, long offset)
			throws RemoteException;

	public List getFormulas_cabeOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getFormulas_cabePK(BigDecimal idformulacabe)
			throws RemoteException;

	public String formulas_cabeDelete(BigDecimal idformulacabe)
			throws RemoteException;

	public String formulas_cabeCreate(String formula, String usuarioalt)
			throws RemoteException;

	public String formulas_cabeCreateOrUpdate(BigDecimal idformulacabe,
			String formula, String usuarioact) throws RemoteException;

	public String formulas_cabeUpdate(BigDecimal idformulacabe, String formula,
			String usuarioact) throws RemoteException;

	// formula detalle
	public List getFormulas_detaAll(String idformulacabe, long limit,
			long offset) throws RemoteException;

	public long getTotalFormulas_detaAll(String idformulacabe)
			throws RemoteException;

	public List getFormulas_detaOcu(String idformulacabe, long limit,
			long offset, String ocurrencia) throws RemoteException;

	public long getTotalFormulas_detaOcu(String idformulacabe, String[] campos,
			String ocurrencia) throws RemoteException;

	public List getFormulas_detaPK(BigDecimal idformula) throws RemoteException;

	public String formulas_detaDelete(BigDecimal idformula)
			throws RemoteException;

	public String formulas_detaCreate(BigDecimal idformulacabe,
			BigDecimal precedencia, String formuladesc, String formula_logica,
			String formula_calculo, String usuarioalt) throws RemoteException;

	public String formulas_detaCreateOrUpdate(BigDecimal idformula,
			BigDecimal idformulacabe, BigDecimal precedencia,
			String formuladesc, String formula_logica, String formula_calculo,
			String usuarioact) throws RemoteException;

	public String formulas_detaUpdate(BigDecimal idformula,
			BigDecimal idformulacabe, BigDecimal precedencia,
			String formuladesc, String formula_logica, String formula_calculo,
			String usuarioact) throws RemoteException;

	// empresa
	public List getGlobalempresasAll(long limit, long offset)
			throws RemoteException;

	public List getGlobalempresasAll() throws RemoteException;

	public List<Empresa> getEmpresas() throws RemoteException;
	
	public List getGlobalempresasOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getGlobalempresasPK(BigDecimal idempresa)
			throws RemoteException;

	public String globalempresasDelete(BigDecimal idempresa)
			throws RemoteException;

	public String globalempresasCreate(String empresa, String usuarioalt)
			throws RemoteException;

	public String globalempresasCreateOrUpdate(BigDecimal idempresa,
			String empresa, String usuarioact) throws RemoteException;

	public String globalempresasUpdate(BigDecimal idempresa, String empresa,
			String usuarioact) throws RemoteException;

	// total All GLOBALCONTADORES
	public long getTotalglobalcontadoresAll(BigDecimal idempresa)
			throws RemoteException;

	// total Ocu GLOBALCONTADORES
	public long getTotalglobalcontadoresOcu(BigDecimal idempresa,
			String[] campos, String ocurrencia) throws RemoteException;

	// total All GLOBALPUESTOS
	public long getTotalglobalpuestosAll(BigDecimal idempresa)
			throws RemoteException;

	// total Ocu GLOBALPUESTOS
	public long getTotalglobalpuestosOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws RemoteException;

	// total All SETUPVARIABLES
	public long getTotalsetupvariablesAll(BigDecimal idempresa)
			throws RemoteException;

	// total Ocu SETUPVARIABLES
	public long getTotalsetupvariablesOcu(BigDecimal idempresa,
			String[] campos, String ocurrencia) throws RemoteException;

	// total All GLOBALUSUARIOS
	public long getTotalglobalusuariosAll(BigDecimal idempresa)
			throws RemoteException;

	// total Ocu GLOBALUSUARIOS
	public long getTotalglobalusuariosOcu(BigDecimal idempresa,
			String[] campos, String ocurrencia) throws RemoteException;

	public String getImagencustomintro() throws RemoteException;

	public String getImagenescustompath() throws RemoteException;

	public String getImagencustommenu() throws RemoteException;

	public String getImagenescustomapppath() throws RemoteException;

	public String getImagenescustomapprelativepath() throws RemoteException;

	// tickets proyectos categorias
	public List getTickets_proyectos_categoriasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getTickets_proyectos_categoriasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getTickets_proyectos_categoriasPK(BigDecimal idproyectocat,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_proyectos_categoriasDelete(BigDecimal idproyectocat,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_proyectos_categoriasCreate(String proyectocat,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String tickets_proyectos_categoriasCreateOrUpdate(
			BigDecimal idproyectocat, String proyectocat, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String tickets_proyectos_categoriasUpdate(BigDecimal idproyectocat,
			String proyectocat, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// tickets estados
	public List getTickets_estadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getTickets_estadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getTickets_estadosPK(BigDecimal idestado, BigDecimal idempresa)
			throws RemoteException;

	public String tickets_estadosDelete(BigDecimal idestado,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_estadosCreate(String estado, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String tickets_estadosCreateOrUpdate(BigDecimal idestado,
			String estado, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String tickets_estadosUpdate(BigDecimal idestado, String estado,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// tickets Proyectos
	public List getTickets_proyectosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getTickets_proyectosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getTickets_proyectosPK(BigDecimal idproyecto,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_proyectosDelete(BigDecimal idproyecto,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_proyectosCreate(String nombre, String description,
			BigDecimal idestado, String activo, BigDecimal idestadovista,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String tickets_proyectosCreateOrUpdate(BigDecimal idproyecto,
			String nombre, String description, BigDecimal idestado,
			String activo, BigDecimal idestadovista, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String tickets_proyectosUpdate(BigDecimal idproyecto, String nombre,
			String description, BigDecimal idestado, String activo,
			BigDecimal idestadovista, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// total all por empresa
	public long getTotalEntidadporempresa(String entidad, BigDecimal idempresa)
			throws RemoteException;

	// total ocu por empresa
	public long getTotalEntidadOcuporempresa(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// lov Estado
	public List getEstadoLovAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getEstadoLovOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	// tickets categorias
	public List getTickets_categoriasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getTickets_categoriasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getTickets_categoriasPK(BigDecimal idcategoria,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_categoriasDelete(BigDecimal idcategoria,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_categoriasCreate(String categoria,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String tickets_categoriasCreateOrUpdate(BigDecimal idcategoria,
			String categoria, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String tickets_categoriasUpdate(BigDecimal idcategoria,
			String categoria, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// tickets reproductividad
	public List getTickets_reproductividadAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getTickets_reproductividadOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getTickets_reproductividadPK(BigDecimal idreproductividad,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_reproductividadDelete(BigDecimal idreproductividad,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_reproductividadCreate(String reproductividad,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String tickets_reproductividadCreateOrUpdate(
			BigDecimal idreproductividad, String reproductividad,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String tickets_reproductividadUpdate(BigDecimal idreproductividad,
			String reproductividad, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// tickets severidad
	public List getTickets_severidadAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getTickets_severidadOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getTickets_severidadPK(BigDecimal idseveridad,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_severidadDelete(BigDecimal idseveridad,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_severidadCreate(String severidad,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String tickets_severidadCreateOrUpdate(BigDecimal idseveridad,
			String severidad, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String tickets_severidadUpdate(BigDecimal idseveridad,
			String severidad, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// tickets prioridad
	public List getTickets_prioridadAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getTickets_prioridadOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getTickets_prioridadPK(BigDecimal idprioridad,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_prioridadDelete(BigDecimal idprioridad,
			BigDecimal idempresa) throws RemoteException;

	public String tickets_prioridadCreate(String prioridad,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String tickets_prioridadCreateOrUpdate(BigDecimal idprioridad,
			String prioridad, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String tickets_prioridadUpdate(BigDecimal idprioridad,
			String prioridad, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: globalImagenes Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Mon Mar 10 10:51:28 ART 2008
	 */

	public List getGlobalImagenesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getGlobalImagenesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getGlobalImagenesPK(BigDecimal idimagen, BigDecimal idempresa)
			throws RemoteException;

	public String globalImagenesDelete(BigDecimal idimagen, BigDecimal idempresa)
			throws RemoteException;

	public String globalImagenesCreate(String descripcion, String path,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String globalImagenesCreateOrUpdate(BigDecimal idimagen,
			String descripcion, String path, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String globalImagenesUpdate(BigDecimal idimagen, String descripcion,
			String path, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// tipo documento
	public List getGlobaltiposdocumentosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getGlobaltiposdocumentosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getGlobaltiposdocumentosPK(BigDecimal idtipodocumento,
			BigDecimal idempresa) throws RemoteException;

	public String globaltiposdocumentosDelete(BigDecimal idtipodocumento,
			BigDecimal idempresa) throws RemoteException;

	public String globaltiposdocumentosCreate(String tipodocumento,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String globaltiposdocumentosCreateOrUpdate(
			BigDecimal idtipodocumento, String tipodocumento,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String globaltiposdocumentosUpdate(BigDecimal idtipodocumento,
			String tipodocumento, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// global entidad sociables
	public List getGlobalentidadesasociablesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getGlobalentidadesasociablesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getGlobalentidadesasociablesPK(
			BigDecimal identidadesasociables, BigDecimal idempresa)
			throws RemoteException;

	public String globalentidadesasociablesDelete(
			BigDecimal identidadesasociables, BigDecimal idempresa)
			throws RemoteException;

	public String globalentidadesasociablesCreate(String entidadasociable,
			String descripcion, String campopk, String querypk,
			String querygrilla, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String globalentidadesasociablesCreateOrUpdate(
			BigDecimal identidadesasociables, String entidadasociable,
			String descripcion, String campopk, String querypk,
			String querygrilla, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String globalentidadesasociablesUpdate(
			BigDecimal identidadesasociables, String entidadasociable,
			String descripcion, String campopk, String querypk,
			String querygrilla, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// global entidad asociaciones
	public List getGlobalentidadesasociacionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getGlobalentidadesasociacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getGlobalentidadesasociacionesPK(BigDecimal identidadorigen,
			BigDecimal idempresa) throws RemoteException;

	public String globalentidadesasociacionesDelete(BigDecimal identidadorigen,
			BigDecimal identidaddestino, BigDecimal idempresa)
			throws RemoteException;

	public String globalentidadesasociacionesCreate(BigDecimal identidadorigen,
			BigDecimal identidaddestino, String observaciones,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	// public String globalentidadesasociacionesCreateOrUpdate(BigDecimal
	// identidadorigen, BigDecimal identidaddestino, String observaciones,
	// BigDecimal idempresa, String usuarioact) throws RemoteException;
	// public String globalentidadesasociacionesUpdate(BigDecimal
	// identidadorigen, BigDecimal identidaddestino, String observaciones,
	// BigDecimal idempresa, String usuarioact) throws RemoteException;

	// para los lov
	public List getEntidAsociablesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getEntidAsociablesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// global entidad asociaciones mov
	public List getGlobalentidadesasociacionesmovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getGlobalentidadesasociacionesmovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public boolean isExisteAsociacion(BigDecimal pkorigen,
			BigDecimal pkdestino, BigDecimal idempresa) throws RemoteException;

	public List getGlobalentidadesasociacionesmovPK(
			BigDecimal identidadesasociacionesmov, BigDecimal idempresa)
			throws RemoteException;

	public String globalentidadesasociacionesmovDelete(
			BigDecimal identidadesasociacionesmov, BigDecimal idempresa)
			throws RemoteException;

	public String globalentidadesasociacionesmovCreate(
			BigDecimal identidadorigen, BigDecimal identidaddestino,
			BigDecimal pkorigen, BigDecimal pkdestino, java.sql.Date fecha,
			String observacion, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String globalentidadesasociacionesmovCreateOrUpdate(
			BigDecimal identidadesasociacionesmov, BigDecimal identidadorigen,
			BigDecimal identidaddestino, BigDecimal pkorigen,
			BigDecimal pkdestino, java.sql.Date fecha, String observacion,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String globalentidadesasociacionesmovUpdate(
			BigDecimal identidadesasociacionesmov, BigDecimal identidadorigen,
			BigDecimal identidaddestino, BigDecimal pkorigen,
			BigDecimal pkdestino, java.sql.Date fecha, String observacion,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// ///////////////////////////////////////////////////////////////////

	// 
	public List getDocumentosGenericosLovAll(long limit, long offset,
			String queryEntidad, BigDecimal idempresa) throws RemoteException;

	//

	public List getDocumentosGenericosLovOcu(long limit, long offset,
			String queryEntidad, String filtro, BigDecimal idempresa)
			throws RemoteException;

	// 
	public List getDocumentosGenericosAll(long limit, long offset,
			String queryEntidad, BigDecimal idempresa) throws RemoteException;

	//

	public List getDocumentosGenericosOcu(long limit, long offset,
			String queryEntidad, String filtro, BigDecimal idempresa)
			throws RemoteException;

	//
	public String[][] getQueryEntidadMetadata(String queryEntidad,
			BigDecimal idempresa) throws RemoteException;

	public List getAsociacionesXOrigen(BigDecimal pkorigen, BigDecimal idempresa)
			throws RemoteException;

	// global usuarios depositos
	public List getGlobalusuariosdepositosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getGlobalusuariosdepositosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getGlobalusuariosdepositosPK(BigDecimal idusuariosdepositos,
			BigDecimal idempresa) throws RemoteException;

	public String globalusuariosdepositosDelete(BigDecimal idusuariosdepositos,
			BigDecimal idempresa) throws RemoteException;

	public String globalusuariosdepositosCreate(BigDecimal idusuario,
			BigDecimal codigo_dt, String observaciones, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String globalusuariosdepositosCreateOrUpdate(
			BigDecimal idusuariosdepositos, BigDecimal idusuario,
			BigDecimal codigo_dt, String observaciones, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String globalusuariosdepositosUpdate(BigDecimal idusuariosdepositos,
			BigDecimal idusuario, BigDecimal codigo_dt, String observaciones,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// pedidos canones
	public List getPedidoscanonesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getPedidoscanonesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getPedidoscanonesPK(BigDecimal idcanon, BigDecimal idempresa)
			throws RemoteException;

	public String pedidoscanonesDelete(BigDecimal idcanon, BigDecimal idempresa)
			throws RemoteException;

	public String pedidoscanonesCreate(String canon, Double por_desc,
			String formula, BigDecimal precedencia, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String pedidoscanonesCreateOrUpdate(BigDecimal idcanon,
			String canon, Double por_desc, String formula,
			BigDecimal precedencia, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String pedidoscanonesUpdate(BigDecimal idcanon, String canon,
			Double por_desc, String formula, BigDecimal precedencia,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// esto hay que pasarlo a
	// clientes**********************************************************************
	// clientes expreso zonas
	public List getClientesexpresoszonasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesexpresoszonasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesexpresoszonasPK(BigDecimal codigo,
			BigDecimal idempresa) throws RemoteException;

	public String clientesexpresoszonasDelete(BigDecimal codigo,
			BigDecimal idempresa) throws RemoteException;

	public String clientesexpresoszonasCreate(BigDecimal idexpreso,
			BigDecimal idzona, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String clientesexpresoszonasCreateOrUpdate(BigDecimal codigo,
			BigDecimal idexpreso, BigDecimal idzona, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String clientesexpresoszonasUpdate(BigDecimal codigo,
			BigDecimal idexpreso, BigDecimal idzona, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	// clientes descuentos
	public List getClientesdescuentosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesdescuentosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesdescuentosPK(BigDecimal iddescuento,
			BigDecimal idempresa) throws RemoteException;

	public String clientesdescuentosDelete(BigDecimal iddescuento,
			BigDecimal idempresa) throws RemoteException;

	public String clientesdescuentosCreate(Double por_desc,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String clientesdescuentosCreateOrUpdate(BigDecimal iddescuento,
			Double por_desc, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String clientesdescuentosUpdate(BigDecimal iddescuento,
			Double por_desc, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// clientes anexo localidades
	public List getClientesanexolocalidadesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesanexolocalidadesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesanexolocalidadesPK(BigDecimal idanexolocalidad,
			BigDecimal idempresa) throws RemoteException;

	public String clientesanexolocalidadesDelete(BigDecimal idanexolocalidad,
			BigDecimal idempresa) throws RemoteException;

	public String clientesanexolocalidadesCreate(BigDecimal idexpreso,
			BigDecimal idprovincia, BigDecimal idlocalidad, BigDecimal cpostal,
			Double tax1bulto, Double taxexcedente, Double tax1bulto2,
			Double taxexcedente2, Double valor_seguro, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	// public String clientesanexolocalidadesCreateOrUpdate(BigDecimal
	// idanexolocalidad, BigDecimal idexpreso, BigDecimal idprovincia,
	// BigDecimal idlocalidad, BigDecimal cpostal, Double tax1bulto, Double
	// taxexcedente, Double tax1bulto2, Double taxexcedente2, Double
	// valor_seguro, String usuarioact) throws RemoteException;
	public String clientesanexolocalidadesUpdate(BigDecimal idanexolocalidad,
			BigDecimal idexpreso, BigDecimal idprovincia,
			BigDecimal idlocalidad, BigDecimal cpostal, Double tax1bulto,
			Double taxexcedente, Double tax1bulto2, Double taxexcedente2,
			Double valor_seguro, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// precarga clientes
	public List getClientesPrecargaAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesPrecargaOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientesPrecargaPK(BigDecimal idprecarga,
			BigDecimal idempresa) throws RemoteException;

	// 20110331 - EJV - Mantis 684 -->
	public List getClientesPrecargaXIdCliente(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	// <--
	public String getClientesPrecargaDelete(BigDecimal idprecarga,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public String clientesPrecargaCreate(BigDecimal idprecarga, String razon,
			BigDecimal idtipodocumento, BigDecimal nrodocumento, String brutos,
			BigDecimal idtipoiva, BigDecimal idcondicion, Double descuento1,
			Double descuento2, Double descuento3, BigDecimal idctaneto,
			BigDecimal idmoneda, BigDecimal idlista, BigDecimal idtipoclie,
			String observacion, Double lcredito, BigDecimal idtipocomp,
			String autorizado, BigDecimal idcredcate, Hashtable htDomicilios,
			Hashtable htTarjetas, Timestamp fechadeingreso,
			Timestamp fechadenacimiento, String sexo, BigDecimal referencia,
			BigDecimal idcliente, BigDecimal idempresa, String usuarioalt,
			String nuevoreactivacion, BigDecimal porcentaje,
			BigDecimal idvendedorasignado, BigDecimal idestadoprecarga,
			BigDecimal condomicilio, BigDecimal idpreferencia,
			BigDecimal idorigen, BigDecimal idsuborigen,
			BigDecimal idpromocion, BigDecimal sucursalfactura)
			throws RemoteException, SQLException;

	public String clientesPrecargaCreateOrUpdate(BigDecimal idcliente,
			String razon, BigDecimal idtipodocumento, BigDecimal nrodocumento,
			String brutos, BigDecimal idtipoiva, BigDecimal idcondicion,
			Double descuento1, Double descuento2, Double descuento3,
			BigDecimal idctaneto, BigDecimal idmoneda, BigDecimal idlista,
			BigDecimal idtipoclie, String observacion, Double lcredito,
			BigDecimal idtipocomp, String autorizado, BigDecimal idcredcate,
			Timestamp fechadeingreso, Timestamp fechadenacimiento, String sexo,
			BigDecimal referencia, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String clientesPrecargaUpdate(BigDecimal idprecarga, String razon,
			BigDecimal idtipodocumento, BigDecimal nrodocumento, String brutos,
			BigDecimal idtipoiva, BigDecimal idcondicion, Double descuento1,
			Double descuento2, Double descuento3, BigDecimal idctaneto,
			BigDecimal idmoneda, BigDecimal idlista, BigDecimal idtipoclie,
			String observacion, Double lcredito, BigDecimal idtipocomp,
			String autorizado, BigDecimal idcredcate, Hashtable htDomicilios,
			Hashtable htTarjetas, Timestamp fechadeingreso,
			Timestamp fechadenacimiento, String sexo, BigDecimal referencia,
			BigDecimal idempresa, String usuarioact, String nuevoreactivacion,
			BigDecimal porcentaje, BigDecimal idvendedorasignado,
			BigDecimal idestadoprecarga, BigDecimal condomicilio,
			BigDecimal idpreferencia, BigDecimal idorigen,
			BigDecimal idsuborigen, BigDecimal idpromocion,
			BigDecimal sucursalfactura) throws RemoteException, SQLException;

	public String clientesEmailCreate(BigDecimal iddomicilio,
			BigDecimal idcliente, BigDecimal idprecarga, String email,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String clienteTarjetasCreditoCreate(BigDecimal idtarjetacredito,
			BigDecimal idcliente, BigDecimal idtipotarjeta, String nrotarjeta,
			String nrocontrol, Timestamp fecha_emision,
			Timestamp fecha_vencimiento, String titular, BigDecimal orden,
			String activa, BigDecimal idempresa, String usuarioalt,
			BigDecimal idprecarga) throws RemoteException;

	public String clientesDomiciliosCreate(BigDecimal idcliente,
			BigDecimal idtipodomicilio, String esdefault, String calle,
			String nro, String piso, String depto, BigDecimal idlocalidad,
			String cpa, String postal, String contacto, String cargocontacto,
			String telefonos, String celular, String fax, String web,
			BigDecimal idzona, BigDecimal idcobrador, BigDecimal idvendedor,
			BigDecimal idempresa, String usuarioalt, BigDecimal idprecarga,
			String obsentrega) throws RemoteException;

	public String clientesEmailXDomicilioDelete(BigDecimal iddomicilio,
			BigDecimal idempresa) throws RemoteException;

	public String clientesDomiciliosUpdate(BigDecimal iddomicilio,
			BigDecimal cliente, BigDecimal idtipodomicilio, String esdefault,
			String calle, String nro, String piso, String depto,
			BigDecimal idlocalidad, String cpa, String postal, String contacto,
			String cargocontacto, String telefonos, String celular, String fax,
			String web, BigDecimal idanexolocalidad, BigDecimal idcobrador,
			BigDecimal idvendedor, BigDecimal idempresa, String usuarioact,
			BigDecimal idprecarga, String obsentrega) throws RemoteException;

	public String clientesDomiciliosDelete(BigDecimal iddomicilio,
			BigDecimal idempresa) throws RemoteException;

	public String clienteTarjetasCreditoUpdate(BigDecimal idtarjeta,
			BigDecimal idtarjetacredito, BigDecimal idcliente,
			BigDecimal idtipotarjeta, String nrotarjeta, String nrocontrol,
			Timestamp fecha_emision, Timestamp fecha_vencimiento,
			String titular, BigDecimal orden, String activa,
			BigDecimal idempresa, String usuarioact, BigDecimal idprecarga)
			throws RemoteException;

	public String clienteTarjetasCreditoDelete(BigDecimal idtarjeta,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesEmailXCliente(BigDecimal idprecarga,
			BigDecimal idempresa) throws RemoteException;

	public List getClienteTarjetasCliente(long limit, long offset,
			BigDecimal idprecarga, BigDecimal idempresa) throws RemoteException;

	public List getClientesDomiciliosCliente(long limit, long offset,
			BigDecimal idprecarga, BigDecimal idempresa) throws RemoteException;

	// *****************************************************************************************************

	public String usuarioUpdate(BigDecimal idusuario, String usuario,
			String clave, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public boolean isClaveAnterior(BigDecimal idusuario, String clave,
			BigDecimal idempresa) throws RemoteException;

	// Maximo Numero de Cliente precarga
	public BigDecimal getMaximoClientePrecarga(BigDecimal idempresa)
			throws RemoteException;

	public String encrypt(String plaintext) throws RemoteException;

	// levanto el lov de precarga dentro de clientes
	public List getClientesPrecargaLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesPrecargaLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// getTotalEntidad prospecto lov
	public long getTotalEntidadProspectoLov(String entidad)
			throws RemoteException;

	// getTotalEntidad prospecto lov ocurrencia
	public long getTotalEntidadProspectoLovOcu(String entidad, String[] campos,
			String ocurrencia) throws RemoteException;

	// lov estado precarg
	public List getEstadoPrecargaLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getEstadoPrecargaLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public long getTotalEstadoPrecargaAll(BigDecimal idempresa)
			throws RemoteException;

	// total estado precarg
	public long getTotalEstadoPrecargaOcu(BigDecimal idempresa,
			String[] campos, String ocurrencia) throws RemoteException;

	// consulta de precarga filtrando por fechaalt
	public List getClientesConsultaPrecargaAll(long limit, long offset,
			String fdesde, String fhasta, BigDecimal idempresa)
			throws RemoteException;

	public BigDecimal getMaximoCliente(BigDecimal idempresa)
			throws RemoteException;

	public String UpdateClientesPrecarga(BigDecimal idprecarga,
			int ejercicioactivo, String usuarioalt, BigDecimal idempresa)
			throws RemoteException, SQLException;

	public String insertClientestclientes(BigDecimal idcliente,
			BigDecimal idprecarga, int verificarEsKosher, BigDecimal idempresa,
			Connection conn) throws RemoteException, SQLException;

	public String updateClientestarjetas(BigDecimal idcliente,
			BigDecimal idprecarga, BigDecimal idempresa, Connection conn)
			throws RemoteException, SQLException;

	public String updateClientesmail(BigDecimal idcliente,
			BigDecimal idprecarga, BigDecimal idempresa, Connection conn)
			throws RemoteException, SQLException;

	public String updateClientesdomicilios(BigDecimal idcliente,
			BigDecimal idprecarga, BigDecimal idempresa, Connection conn)
			throws RemoteException, SQLException;

	public String ClientesInsertoEstadoInicial(BigDecimal idcliente,
			BigDecimal idestado, BigDecimal idmotivo, String observaciones,
			String usuarioalt, BigDecimal idempresa, Connection conn)
			throws RemoteException;

	// clientes preferencias
	public List getClientespreferenciasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientespreferenciasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getClientespreferenciasPK(BigDecimal idpreferencia,
			BigDecimal idempresa) throws RemoteException;

	public String clientespreferenciasDelete(BigDecimal idpreferencia,
			BigDecimal idempresa) throws RemoteException;

	public String clientespreferenciasCreate(String preferencia,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String clientespreferenciasCreateOrUpdate(BigDecimal idpreferencia,
			String preferencia, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String clientespreferenciasUpdate(BigDecimal idpreferencia,
			String preferencia, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// origen del prospecto
	public List getOrigenprospectoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getOrigenprospectoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getOrigenprospectoPK(BigDecimal idorigen, BigDecimal idempresa)
			throws RemoteException;

	public String origenprospectoDelete(BigDecimal idorigen,
			BigDecimal idempresa) throws RemoteException;

	public String origenprospectoCreate(String origen, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String origenprospectoCreateOrUpdate(BigDecimal idorigen,
			String origen, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String origenprospectoUpdate(BigDecimal idorigen, String origen,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// suborigen
	public List getSuborigenprospectoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getSuborigenprospectoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getSuborigenprospectoPK(BigDecimal idsuborigen,
			BigDecimal idempresa) throws RemoteException;

	public String suborigenprospectoDelete(BigDecimal idsuborigen,
			BigDecimal idempresa) throws RemoteException;

	public String suborigenprospectoCreate(String suborigen,
			BigDecimal idorigen, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String suborigenprospectoCreateOrUpdate(BigDecimal idsuborigen,
			String suborigen, BigDecimal idorigen, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String suborigenprospectoUpdate(BigDecimal idsuborigen,
			String suborigen, BigDecimal idorigen, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	// lov origen
	public List getOrigenLovAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getOrigenLovOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	// lov origen
	public List getPreferenciaLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getPreferenciaLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getSuborigenLovAll(long limit, long offset,
			BigDecimal idorigen, BigDecimal idempresa) throws RemoteException;

	public List getSuborigenLovOcu(long limit, long offset, String ocurrencia,
			BigDecimal idorigen, BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidadporempresayOrigen(String entidad,
			BigDecimal idorigen, BigDecimal idempresa) throws RemoteException;

	// total ocu por empresa
	public long getTotalEntidadOcuporempresauOrigen(String entidad,
			String[] campos, String ocurrencia, BigDecimal idorigen,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesPrecargaActivarAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesPrecargaActivarOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public long getTotalActivarEntidad(String entidad) throws RemoteException;

	public long getTotalActivarOcu(String entidad, String[] campos,
			String ocurrencia) throws RemoteException;

	public long getControlNumeroDocumentoPrecargaCreate(BigDecimal idempresa,
			BigDecimal tipodoc, String numerodoc) throws RemoteException;

	public long getControlNumeroDocumentoClientesCreate(BigDecimal idempresa,
			BigDecimal tipodoc, String numerodoc) throws RemoteException;

	public List getClientesConsultadePrecarga(String fdesde, String fhasta,
			BigDecimal idtipoclie, BigDecimal idempresa) throws RemoteException;

	public List getClientesConsultadePrecargaXFechaingreso(String fdesde,
			String fhasta, BigDecimal idtipoclie, BigDecimal idempresa)
			throws RemoteException;

	public String ClientesInsertoCategoriaInicial(BigDecimal idcliente,
			BigDecimal idempresa, Connection conn) throws RemoteException;

	public String getValidacionTarjetaDeCredito(BigDecimal idempresa,
			String nrotarjeta, BigDecimal idmarcatarjeta)
			throws RemoteException;

	public String getValidacionTarjetaDeCreditoExistenciaaAlta(
			BigDecimal idempresa, String nrotarjeta, BigDecimal idmarcatarjeta)
			throws RemoteException;

	public String getValidacionTarjetaDeCreditoExistenciaaModificacion(
			BigDecimal idempresa, String nrotarjeta, BigDecimal idmarcatarjeta,
			BigDecimal idtarjeta) throws RemoteException;

	public String validarTarjetaDeCredito(String nrotarjeta)
			throws RemoteException;

	public String getClienteTarjeta(BigDecimal idempresa, String nrotarjeta,
			BigDecimal idmarcatarjeta) throws RemoteException;

	public List Clientesesstadosbajasuspensionlarga(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List ClientesesstadosbajasuspensionlargaOcu(long limit, long offset,
			BigDecimal idempresa, String ocurrencia) throws RemoteException;

	public String EstadoActualUpdate(String[] idcliente, BigDecimal idempresa)
			throws RemoteException;

	public String EstadoActualInsert(String[] idcliente, BigDecimal idestado,
			BigDecimal idmotivo, String observaciones, int ejercicio,
			String usuarioalt, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public long getTotalEntidadporempresaEstado(BigDecimal idempresa)
			throws RemoteException;

	public long getTotalEntidadOcuporempresaEstado(String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public String EstadoActualinserto(BigDecimal idcliente,
			BigDecimal porcreact, String usuarioalt, BigDecimal idempresa,
			Timestamp fechareactivacion, BigDecimal idvendedor,
			BigDecimal idestadoreactivaciones, BigDecimal idpromocion)
			throws RemoteException;

	public String DescripcionClientexNumero(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	public List getClientesConsultaReactEntreFechas(String fdesde,
			String fhasta, BigDecimal idempresa) throws RemoteException;

	public List getClientesConsultaReactEntreFechasxVendedor(String fdesde,
			String fhasta, BigDecimal idempresa) throws RemoteException;

	public List getClientesConsultaPrecargaVendedor(String fdesde,
			String fhasta, BigDecimal idvendedor, BigDecimal idempresa)
			throws RemoteException;

	public List getGlobalLocalidadesXProvAll(long limit, long offset,
			BigDecimal idprovincia) throws RemoteException;

	public List getGlobalLocalidadesXProvOcu(long limit, long offset,
			String ocurrencia, BigDecimal idprovincia) throws RemoteException;

	// ------------------------------------------------------------------------
	// LOCALIDADES POR PROVINCIA - TEST EJV 20090129 - AJAX

	public List getGlobalLocalidadesProvAll(BigDecimal idprovincia)
			throws RemoteException;

	// update la tabla clientesprecargaclientes desde la reactivacion
	public String globalUpdateVendedor(String[] idcliente,
			BigDecimal idvendedor, BigDecimal idempresa) throws RemoteException;

	public List globalUpdatePK(BigDecimal idcliente, BigDecimal idempresa)
			throws RemoteException;

	// LOV estados reactivaciones
	public List getEstadosReactivacionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getEstadosReactivacionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// segunda etapa de reactivaciones donde norma cudos recibe los datos
	public List ClientesReactivacionAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List ClientesReactivacionOcu(long limit, long offset,
			BigDecimal idempresa, String ocurrencia) throws RemoteException;

	public long getTotalEntidadReactivacionAll(BigDecimal idempresa)
			throws RemoteException;

	public long getTotalEntidadReactivacionOcu(String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	/**
	 * 
	 * Metodos para la entidad: globalBlobImagenes Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Tue Jun 16 10:05:43 GYT 2009
	 * 
	 */

	public String callGlobalBlobImagenesCreate(String tupla, File file[],
			String nombre[], String descripcion[], String principal[],
			BigDecimal idempresa, String usuarioalt) throws RemoteException,
			SQLException;

	public String globalBlobImagenesCreate(String tupla, String file,
			String nombre, String descripcion, String principal,
			String tmppath, BigDecimal size, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public List getGlobalBlobImagenesAll(long limit, long offset,
			BigDecimal tupla, BigDecimal idempresa) throws RemoteException;

	public List getGlobalBlobImagenesOcu(long limit, long offset,
			BigDecimal tupla, String ocurrencia, BigDecimal idempresa)
			throws RemoteException;

	public List getGlobalBlobImagenesPK(BigDecimal tupla, BigDecimal trama,
			BigDecimal idempresa) throws RemoteException;

	public String globalBlobImagenesDelete(BigDecimal tupla, BigDecimal trama,
			BigDecimal idempresa) throws RemoteException, SQLException;

	public String globalBlobImagenesUpdate(BigDecimal tupla, BigDecimal trama,
			String nombre, String descripcion, String principal,
			String tmppath, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	// inserto la periodicidad del cliente
	public String ClientesInsertoPeriodicidaddelSocio(BigDecimal idcliente,
			int verificarEsKosher, BigDecimal idempresa, Connection conn)
			throws RemoteException;

	// SAS--ingreso de fichas
	public List getConsultaIngresoFichas(String fdesde, String fhasta,
			BigDecimal idempresa) throws RemoteException;

	// TIPOS EVENTOS
	public List getWkftipoeventosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getWkftipoeventosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getWkftipoeventosPK(BigDecimal idtipoevento,
			BigDecimal idempresa) throws RemoteException;

	public String wkftipoeventosDelete(BigDecimal idtipoevento,
			BigDecimal idempresa) throws RemoteException;

	public String wkftipoeventosCreate(String tipoevento, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String wkftipoeventosUpdate(BigDecimal idtipoevento,
			String tipoevento, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// TIPO TRANSACCION
	public List getWkftipotransaccionAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getWkftipotransaccionOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getWkftipotransaccionPK(BigDecimal idtipotransaccion,
			BigDecimal idempresa) throws RemoteException;

	public String wkftipotransaccionDelete(BigDecimal idtipotransaccion,
			BigDecimal idempresa) throws RemoteException;

	public String wkftipotransaccionCreate(String tipotransaccion,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String wkftipotransaccionUpdate(BigDecimal idtipotransaccion,
			String tipotransaccion, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// EVENTOS
	public List getWkfeventosAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getWkfeventosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getWkfeventosPK(BigDecimal idevento, BigDecimal idempresa)
			throws RemoteException;

	public String wkfeventosDelete(BigDecimal idevento, BigDecimal idempresa)
			throws RemoteException;

	public String wkfeventosCreate(String evento, BigDecimal idtipoevento,
			BigDecimal idproximoevento, String descripcion, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String wkfeventosUpdate(BigDecimal idevento, String evento,
			BigDecimal idtipoevento, BigDecimal idproximoevento,
			String descripcion, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// TRANSACCIONES

	public List getWkftransaccionesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getWkftransaccionesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getWkftransaccionesPK(BigDecimal idtransaccion,
			BigDecimal idempresa) throws RemoteException;

	public String wkftransaccionesDelete(BigDecimal idtransaccion,
			BigDecimal idempresa) throws RemoteException;

	public String wkftransaccionesCreate(String transaccion,
			BigDecimal idtipotransaccion, BigDecimal idproximatransaccion,
			String descripcion, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String wkftransaccionesUpdate(BigDecimal idtransaccion,
			String transaccion, BigDecimal idtipotransaccion,
			BigDecimal idproximatransaccion, String descripcion,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// PROCESO NEGOCIO
	public List getWkfprocesonegocioAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getWkfprocesonegocioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getWkfprocesonegocioPK(BigDecimal idprocesonegocio,
			BigDecimal idempresa) throws RemoteException;

	public String wkfprocesonegocioDelete(BigDecimal idprocesonegocio,
			BigDecimal idempresa) throws RemoteException;

	public String wkfprocesonegocioCreate(String procesonegocio,
			BigDecimal idprocesonegocionext, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String wkfprocesonegocioUpdate(BigDecimal idprocesonegocio,
			String procesonegocio, BigDecimal idprocesonegocionext,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// PROCESOS EVENTOS
	public List getWkfprocesoseventosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getWkfprocesoseventosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getWkfprocesoseventosPK(BigDecimal idprocesoevento,
			BigDecimal idempresa) throws RemoteException;

	public String wkfprocesoseventosDelete(BigDecimal idprocesoevento,
			BigDecimal idempresa) throws RemoteException;

	public String wkfprocesoseventosCreate(BigDecimal idproceso,
			BigDecimal idevento, String descripcion,
			BigDecimal idprocesonegocionext, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String wkfprocesoseventosUpdate(BigDecimal idprocesoevento,
			BigDecimal idproceso, BigDecimal idevento, String descripcion,
			BigDecimal idprocesonegocionext, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	// EVENTOS RESULTADOS
	public List getWkfeventosresultadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getWkfeventosresultadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getWkfeventosresultadosPK(BigDecimal ideventoresultado,
			BigDecimal idempresa) throws RemoteException;

	public String wkfeventosresultadosDelete(BigDecimal ideventoresultado,
			BigDecimal idempresa) throws RemoteException;

	public String wkfeventosresultadosCreate(String eventoresultado,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String wkfeventosresultadosUpdate(BigDecimal ideventoresultado,
			String eventoresultado, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// EVENTOS ACTUALES
	public List getWkfeventosactualesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getWkfeventosactualesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getWkfeventosactualesPK(BigDecimal idventoactual,
			BigDecimal idempresa) throws RemoteException;

	public String WkfeventosactualesDelete(BigDecimal idventoactual,
			BigDecimal idempresa) throws RemoteException;

	public String WkfeventosactualesCreate(BigDecimal ideventoresultado,
			BigDecimal idprocesoevento, BigDecimal idtransaccion,
			Timestamp fecha, String transaccion, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String WkfeventosactualesUpdate(BigDecimal idventoactual,
			BigDecimal ideventoresultado, BigDecimal idprocesoevento,
			BigDecimal idtransaccion, Timestamp fecha, String transaccion,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// PROCESO DE CUMPLEAÑOS
	public List getConsultaProcesodeCumpleanios(String anio, String mes,
			BigDecimal idempresa) throws RemoteException;

	// CARTA DE BIENVENIDA
	public List getCartaBienvenida(String fechaactivacion, String idtipoclie,
			BigDecimal idempresa) throws RemoteException;

	public List getGestionTmCampania(BigDecimal idtelemark,
			BigDecimal idcampacabe, BigDecimal idempresa)
			throws RemoteException;

	public List getGestionTmCampaniaResultado(BigDecimal idtelemark,
			BigDecimal idcampacabe, BigDecimal idresultado,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public List getGestionTmCampaniaResultado2(BigDecimal idtelemark,
			BigDecimal idcampacabe, BigDecimal idresultado,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public List getZonaLovAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getZonaLovOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	// BAJAS POR VENDEDOR
	public List getBajasporVendedor(String anio, BigDecimal idempresa)
			throws RemoteException;

	// consulta de estados por fechas
	public List getClientesConsultadeEstados(String fdesde, String fhasta,
			BigDecimal idestado, String criterio, BigDecimal idempresa)
			throws RemoteException;

	// consulta diferencia en pedidos
	public List getClientesDiferenciaenPedidos(String anio, String mes,
			BigDecimal codigo_fm, BigDecimal idempresa) throws RemoteException;

	// levanto por cliente todas las suspensiones de entregas regulares
	public List getClientesSuspEntregaRegularporCliente(BigDecimal idcliente,
			BigDecimal idempresa) throws RemoteException;

	public String clientesSuspEntregasRegularesFBajaUpd(BigDecimal idcodigo,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// BACO TIPO OBSEQUIOS
	public List getBacotipoobsequiosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacotipoobsequiosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacotipoobsequiosPK(BigDecimal idtipoobsequio,
			BigDecimal idempresa) throws RemoteException;

	public String BacotipoobsequiosDelete(BigDecimal idtipoobsequio,
			BigDecimal idempresa) throws RemoteException;

	public String BacotipoobsequiosCreate(String tipoobsequio,
			BigDecimal idmotivodescuento, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String BacotipoobsequiosUpdate(BigDecimal idtipoobsequio,
			String tipoobsequio, BigDecimal idmotivodescuento,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// public List getGestionTmBusquedaporCliente(String buscarsocio,
	// BigDecimal idempresa, String usuarioalt,BigDecimal idcampacabe) throws
	// RemoteException;

	public List getGestionTmBusquedaporCliente(String buscarsocio,
			BigDecimal idtelemark, BigDecimal idcampacabe, BigDecimal idempresa)
			throws RemoteException;

	// pedido amelia para modificar solicitud de Reactivacion FGP 03/02/2010
	public List ClientesesstadosbajasuspensionlargaMod(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List ClientesesstadosbajasuspensionlargaOcuMod(long limit,
			long offset, BigDecimal idempresa, String ocurrencia)
			throws RemoteException;

	public long getTotalEntidadporempresaEstadoMod(BigDecimal idempresa)
			throws RemoteException;

	public long getTotalEntidadOcuporempresaEstadoMod(String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List globalUpdatePKMod(BigDecimal idreactivacion,
			BigDecimal idempresa) throws RemoteException;

	public String isManager(String usuario) throws RemoteException;

	/*
	 * Metodos para la entidad: bacotmliquidacionresumen Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Wed Mar 02 15:34:16 ART 2011
	 */

	public List getBacotmliquidacionresumenAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getBacotmliquidacionresumenOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getBacotmliquidacionresumenPK(BigDecimal idliquidacion,
			BigDecimal idempresa) throws RemoteException;

	public String bacotmliquidacionresumenDelete(BigDecimal idliquidacion,
			BigDecimal idempresa) throws RemoteException;

	public String bacotmliquidacionresumenCreate(BigDecimal anio,
			BigDecimal mes, String usuario, BigDecimal comision_vc,
			BigDecimal total_vc, BigDecimal comision_vv, BigDecimal total_vv,
			BigDecimal comision_ve, BigDecimal total_ve,
			BigDecimal comision_vc_jf, BigDecimal total_vc_jf,
			BigDecimal comision_vv_jf, BigDecimal total_vv_jf,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String bacotmliquidacionresumenCreateOrUpdate(
			BigDecimal idliquidacion, BigDecimal anio, BigDecimal mes,
			String usuario, BigDecimal comision_vc, BigDecimal total_vc,
			BigDecimal comision_vv, BigDecimal total_vv,
			BigDecimal comision_ve, BigDecimal total_ve,
			BigDecimal comision_vc_jf, BigDecimal total_vc_jf,
			BigDecimal comision_vv_jf, BigDecimal total_vv_jf,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String bacotmliquidacionresumenUpdate(BigDecimal idliquidacion,
			BigDecimal anio, BigDecimal mes, String usuario,
			BigDecimal comision_vc, BigDecimal total_vc,
			BigDecimal comision_vv, BigDecimal total_vv,
			BigDecimal comision_ve, BigDecimal total_ve,
			BigDecimal comision_vc_jf, BigDecimal total_vc_jf,
			BigDecimal comision_vv_jf, BigDecimal total_vv_jf,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public List getVendedoresAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getVentasPorVendedor(String vendedor, BigDecimal idcampania,
			BigDecimal idempresa) throws RemoteException;

	public List getBacotmliquidacionresumenParametros(String usuario,
			BigDecimal idcampania, BigDecimal idempresa) throws RemoteException;

	public List getCampanias() throws RemoteException;

	public List getVendedoresTelemarketers() throws RemoteException;

	public void setArchivo(List lista, BigDecimal columnas, String nombre)
			throws RemoteException;

	public void setArchivo(List lista, BigDecimal columnas, String nombre,
			String[] tituCol) throws RemoteException;

	/*
	 * Metodos para la entidad: ticketsestados Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Thu Sep 20 09:41:44 ART 2012
	 * 
	 * //Para poner en el remoto:
	 */
	public List getTicketsestadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getTicketsestadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getTicketsestadosPK(BigDecimal idticketestado,
			BigDecimal idempresa) throws RemoteException;

	public String ticketsestadosDelete(BigDecimal idticketestado,
			BigDecimal idempresa) throws RemoteException;

	public String ticketsestadosCreate(String ticketestado, String color_fondo,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String ticketsestadosCreateOrUpdate(BigDecimal idticketestado,
			String ticketestado, String color_fondo, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public String ticketsestadosUpdate(BigDecimal idticketestado,
			String ticketestado, String color_fondo, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	/*
	 * Metodos para la entidad: tickets Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Thu Sep 20 11:56:06 ART 2012
	 * 
	 * //Para poner en el remoto:
	 */

	public List getTicketsAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getTicketsOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getTicketsPK(BigDecimal idticket, BigDecimal idempresa)
			throws RemoteException;

	public String ticketsDelete(BigDecimal idticket, BigDecimal idempresa, boolean enviaCorreo)
			throws RemoteException;

	public String ticketsCreate(BigDecimal idgrupo, BigDecimal idusuario,
			BigDecimal idcliente, String resumen, String descripcion,
			BigDecimal idticketestado, BigDecimal idempresa, String usuarioalt, boolean enviaCorreo )
			throws RemoteException;

	public String ticketsCreateOrUpdate(BigDecimal idticket,
			BigDecimal idgrupo, BigDecimal idusuario, BigDecimal idcliente,
			String resumen, String descripcion, BigDecimal idticketestado,
			BigDecimal idempresa, String usuarioact, boolean enviaCorreo ) throws RemoteException;

	public String ticketsUpdate(BigDecimal idticket, BigDecimal idgrupo,
			BigDecimal idusuario, BigDecimal idcliente, String resumen,
			String descripcion, BigDecimal idticketestado,
			BigDecimal idempresa, String usuarioact, boolean enviaCorreo ) throws RemoteException;

	public BigDecimal getUsuario(String nombre, BigDecimal idempresa)
			throws RemoteException;

	public List getUsuarioGrupo(BigDecimal idusuario) throws RemoteException;
	
	public   boolean validarClaveIdentificacionUnica(String clave) throws RemoteException;

//	public List ObtenerMailGrupo(BigDecimal idgrupo) throws RemoteException;
//
//	public String ObtenerMailUsuario(BigDecimal idusuario)
//			throws RemoteException;
}