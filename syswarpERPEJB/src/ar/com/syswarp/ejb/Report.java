package ar.com.syswarp.ejb;

import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import javax.ejb.Local;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.math.*;
import java.util.*;
import javax.ejb.Local;
@Local
public interface Report{
	public List getReportesUsuario(long idusuario) throws RemoteException;

	public List getReportesPK(long idReporte) throws RemoteException;

	public String prueba() throws RemoteException;

	public ResultSet getConnectionDS(Integer idDatasource, String qry)
			throws RemoteException;

	public ResultSet getTablasReportes(Integer idReporte)
			throws RemoteException;

	public boolean hasTablasReportes(Integer idReporte) throws RemoteException;

	public Hashtable getParametros(String query) throws RemoteException;

	public String setQueryParametros(String query, Hashtable parametros)
			throws RemoteException;

	public ResultSet getGraficosTablas(Integer idTabla) throws RemoteException;

	public boolean hasGraficosTablas(Integer idTabla) throws RemoteException;

	public List getVariables() throws RemoteException;

	public String par2var(String parametro) throws RemoteException;

	public List getParametrosPK(String parametro) throws RemoteException;

	public List getParametros(Hashtable parametros) throws RemoteException;

	public String GenerarGraficosXY(String nombreArchivo,
			String tituloPrincipal, ResultSet rsDatos) throws RemoteException;

	public String GenerarGraficosBar3D(String nombreArchivo,
			String tituloPrincipal, ResultSet rsDatos) throws RemoteException;

	public String GenerarGraficosBar3D(String nombreArchivo,
			String tituloPrincipal, ResultSet rsDatos, int largo, int alto)
			throws RemoteException;

	public String GenerarGraficosPie3D(String nombreArchivo,
			String tituloPrincipal, ResultSet rsDatos) throws RemoteException;

	public String GenerarGraficosPie3D(String nombreArchivo,
			String tituloPrincipal, ResultSet rsDatos, int largo, int alto)
			throws RemoteException;

	public String GenerarGraficosTimes(String nombreArchivo,
			String tituloPrincipal, ResultSet rsDatos) throws RemoteException;

	public String usuarioCreate(String usuario, String clave,
			int administrador, String email, String usuarioalt)
			throws RemoteException;

	public String usuarioUpdate(long idusuario, String usuario, String clave,
			int administrador, String email, String usuarioact)
			throws RemoteException;

	public String usuarioDelete(long idusuario) throws RemoteException;

	public List getUsuario(long idUsuario) throws RemoteException;

	public List getValidarUsuario(String usuario, String password)
			throws RemoteException;

	public List getUsuariosOcu(int limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getUsuariosAll() throws RemoteException;

	public List getUsuariosAll(int limit, long offset) throws RemoteException;

	public List getGrupo(long idGrupo) throws RemoteException;

	public List getGruposOcu(int limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getGruposAll(int limit, long offset) throws RemoteException;

	public String grupoDelete(long idgrupo) throws RemoteException;

	public String grupoUpdate(long idgrupo, String grupo, String descripcion,
			String usuarioact) throws RemoteException;

	public String grupoCreate(String grupo, String descripcion,
			String usuarioalt) throws RemoteException;

	public List getUsuarioGruposAll(int limit, long offset, long idusuario)
			throws RemoteException;

	public List getGruposAsociar(int limit, long offset, long idusuario)
			throws RemoteException;

	public String usuarioGrupoDelete(long idusuario, long idgrupo)
			throws RemoteException;

	public String usuarioGrupoCreate(long idusuario, long[] idgrupos,
			String usuarioalt) throws RemoteException, SQLException;

	public String reportesDelete(long idreporte) throws RemoteException;

	public String reportesCreate(String reporte, String comentario,
			String skin, String usuarioalt) throws RemoteException;

	public String reportesUpdate(long idreporte, String reporte,
			String comentario, String skin, String usuarioact)
			throws RemoteException;

	public List getReportesAll(int limit, long offset) throws RemoteException;

	public List getReportesOcu(int limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getTablasPK(long idTabla) throws RemoteException;

	public String tablasCreate(String tabla, String query_carga,
			String query_consulta, long iddatasource, String usuarioalt)
			throws RemoteException;

	public String tablasUpdate(long idtabla, String tabla, String query_carga,
			String query_consulta, long iddatasource, String usuarioact)
			throws RemoteException;

	public String tablasDelete(long idtabla) throws RemoteException;

	// LLENA COMBO - Metodo Sobrecargado
	public List getTablasAll() throws RemoteException;

	public List getTablasAll(int limit, long offset) throws RemoteException;

	public List getTablasOcu(int limit, long offset, String ocurrencia)
			throws RemoteException;

	// DATASOURCES

	// LLENA COMBO - Metodo Sobrecargado
	public List getDatasourcesAll() throws RemoteException;

	public List getDataSourcesAll(long limit, long offset)
			throws RemoteException;

	public List getDataSourcesOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getDataSourcesPK(BigDecimal iddatasource)
			throws RemoteException;

	public String dataSourcesDelete(BigDecimal iddatasource)
			throws RemoteException;

	public String dataSourcesCreate(String datasource, String driver,
			String db_user, String db_pass, String url, String usuarioalt)
			throws RemoteException;

	public String dataSourcesCreateOrUpdate(BigDecimal iddatasource,
			String datasource, String driver, String db_user, String db_pass,
			String url, String usuarioact) throws RemoteException;

	public String dataSourcesUpdate(BigDecimal iddatasource, String datasource,
			String driver, String db_user, String db_pass, String url,
			String usuarioact) throws RemoteException;

	/**
	 * 
	 * Metodos para la entidad: campos Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Tue Jul 04 15:46:54 GMT-03:00 2006
	 */
	public List getCamposAll(long limit, long offset) throws RemoteException;

	public List getCamposOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getCamposPK(BigDecimal idcampo) throws RemoteException;

	public String camposDelete(BigDecimal idcampo) throws RemoteException;

	public String camposCreate(String campo, BigDecimal idtabla, String titulo,
			BigDecimal orden, String clase_css, String lenght_col,
			String comentario, String usuarioalt) throws RemoteException;

	public String camposCreateOrUpdate(BigDecimal idcampo, String campo,
			BigDecimal idtabla, String titulo, BigDecimal orden,
			String clase_css, String lenght_col, String comentario,
			String usuarioact) throws RemoteException;

	public String camposUpdate(BigDecimal idcampo, String campo,
			BigDecimal idtabla, String titulo, BigDecimal orden,
			String clase_css, String lenght_col, String comentario,
			String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: grafico Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Mon Jul 03 11:26:49 GMT-03:00 2006
	 */

	public List getGraficoAll(long limit, long offset) throws RemoteException;

	public List getGraficoOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getGraficoPK(BigDecimal idgrafico) throws RemoteException;

	public String graficoDelete(BigDecimal idgrafico) throws RemoteException;

	public String graficoCreate(String grafico, String query_consulta,
			BigDecimal idtipografico, BigDecimal iddatasource, String usuarioalt)
			throws RemoteException;

	public String graficoCreateOrUpdate(BigDecimal idgrafico, String grafico,
			String query_consulta, BigDecimal idtipografico,
			BigDecimal iddatasource, String usuarioact) throws RemoteException;

	public String graficoUpdate(BigDecimal idgrafico, String grafico,
			String query_consulta, BigDecimal idtipografico,
			BigDecimal iddatasource, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: parametros Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Mon Jul 03 17:43:39 GMT-03:00 2006
	 */

	public List getParametrosAll(long limit, long offset)
			throws RemoteException;

	public List getParametrosOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getParametrosPK(BigDecimal idparametro) throws RemoteException;

	public String parametrosDelete(BigDecimal idparametro)
			throws RemoteException;

	public String parametrosCreate(String parametro, String descripcion,
			BigDecimal idtipoparametro, String validacion_query,
			BigDecimal iddatasource, String obligatorio, BigDecimal orden,
			String usuarioalt) throws RemoteException;

	public String parametrosCreateOrUpdate(BigDecimal idparametro,
			String parametro, String descripcion, BigDecimal idtipoparametro,
			String validacion_query, BigDecimal iddatasource,
			String obligatorio, BigDecimal orden, String usuarioact)
			throws RemoteException;

	public String parametrosUpdate(BigDecimal idparametro, String parametro,
			String descripcion, BigDecimal idtipoparametro,
			String validacion_query, BigDecimal iddatasource,
			String obligatorio, BigDecimal orden, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: tipo_grafico Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Jul 04 14:21:40 GMT-03:00 2006
	 */
	// LLENA COMBO
	public List getTipoGraficosAll() throws RemoteException;

	public List getTipo_graficoAll(long limit, long offset)
			throws RemoteException;

	public List getTipo_graficoOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getTipo_graficoPK(BigDecimal idtipografico)
			throws RemoteException;

	public String tipo_graficoDelete(BigDecimal idtipografico)
			throws RemoteException;

	public String tipo_graficoCreate(String tipografico, String usuarioalt)
			throws RemoteException;

	public String tipo_graficoCreateOrUpdate(BigDecimal idtipografico,
			String tipografico, String usuarioact) throws RemoteException;

	public String tipo_graficoUpdate(BigDecimal idtipografico,
			String tipografico, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: tipo_parametro Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Jul 04 14:47:30 GMT-03:00 2006
	 * 
	 */

	// LLENA COMBO
	public List getTipoParametrosAll() throws RemoteException;

	public List getTipo_parametroAll(long limit, long offset)
			throws RemoteException;

	public List getTipo_parametroOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getTipo_parametroPK(BigDecimal idtipoparametro)
			throws RemoteException;

	public String tipo_parametroDelete(BigDecimal idtipoparametro)
			throws RemoteException;

	public String tipo_parametroCreate(String tipoparametro, String usuarioalt)
			throws RemoteException;

	public String tipo_parametroCreateOrUpdate(BigDecimal idtipoparametro,
			String tipoparametro, String usuarioact) throws RemoteException;

	public String tipo_parametroUpdate(BigDecimal idtipoparametro,
			String tipoparametro, String usuarioact) throws RemoteException;

	/**
	 * Metodos para la entidad: setupVariables Copyrigth(r) sysWarp S.R.L. Fecha
	 * de creacion: Tue Jul 04 15:26:53 GMT-03:00 2006
	 */
	public List getSetupVariablesAll(long limit, long offset)
			throws RemoteException;

	public List getSetupVariablesOcu(long limit, long offset, String ocurrencia)
			throws RemoteException;

	public List getSetupVariablesPK(BigDecimal idvariable)
			throws RemoteException;

	public String setupVariablesDelete(BigDecimal idvariable)
			throws RemoteException;

	public String setupVariablesCreate(String variable, String valor,
			String descripcion, String usuarioalt) throws RemoteException;

	public String setupVariablesCreateOrUpdate(BigDecimal idvariable,
			String variable, String valor, String descripcion, String usuarioact)
			throws RemoteException;

	public String setupVariablesUpdate(BigDecimal idvariable, String variable,
			String valor, String descripcion, String usuarioact)
			throws RemoteException;

	public List getReportesTablasAll(int limit, long offset,
			BigDecimal idreporte) throws RemoteException;

	public List getReportesTablasAsociar(int limit, long offset,
			BigDecimal idreporte) throws RemoteException;

	public String reporteTablaDelete(BigDecimal idusuario, BigDecimal idgrupo)
			throws RemoteException;

	public String reporteTablaCreate(BigDecimal idreporte, long[] idtablas,
			String usuarioalt) throws RemoteException, SQLException;

	// ///////

	public String grupoReporteDelete(BigDecimal idusuario, BigDecimal idgrupo)
			throws RemoteException;

	public String grupoReporteCreate(BigDecimal idreporte, long[] idtablas,
			String usuarioalt) throws RemoteException, SQLException;

	public List getGruposReportesAll(int limit, long offset,
			BigDecimal idreporte) throws RemoteException;

	public List getGruposReportesAsociar(int limit, long offset,
			BigDecimal idreporte) throws RemoteException;

	// ////

	// ///////

	public String tablaGraficoDelete(BigDecimal idtabla, BigDecimal idgrafico)
			throws RemoteException;

	public String tablaGraficoCreate(BigDecimal idtabla, long[] idgraficos,
			String usuarioalt) throws RemoteException, SQLException;

	public List getTablasGraficosAll(int limit, long offset, BigDecimal idtabla)
			throws RemoteException;

	public List getTablasGraficosAsociar(int limit, long offset,
			BigDecimal idtabla) throws RemoteException;

	// ////

	public long getTotalEntidad(String entidad) throws RemoteException;

	public long getTotalEntidadOcu(String entidad, String[] campos,
			String ocurrencia) throws RemoteException;

	public long getTotalEntidadRelacion(String entidad, String[] campos,
			String[] ocurrencia) throws RemoteException;

	public byte[] getOpenReport(String reportName, Map parameters)
			throws RemoteException;

	public byte[] getOpenReportImpresiones(String reportName, Map parameters)
			throws RemoteException;

	// Escribir archivo en disco

	public String saveByteToFile(byte[] byteToFile, String pathFile)
			throws RemoteException;

	// public String GenerarDualAxis(String nombreArchivo, String
	// tituloPrincipal, ResultSet rsDatos, ResultSet rsDatos2 )
	// throws RemoteException;

	public String getClientesRemitosPath() throws RemoteException;

	public String getRepositorioDocsPath() throws RemoteException;

}
