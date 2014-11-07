package ar.com.syswarp.ejb;

import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import javax.ejb.Local;

import bsh.EvalError;

import sun.misc.REException;
import sun.org.mozilla.javascript.EvaluatorException;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.math.*;
import javax.ejb.Local;
@Local
public interface RRHH {

	// getTotalEntidad
	public long getTotalEntidad(String entidad, BigDecimal idempresa)
			throws RemoteException;

	// getTotalEntidadOcu
	public long getTotalEntidadOcu(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public long getTotalEntidadFiltro(String entidad, String filtro,
			BigDecimal idempresa) throws RemoteException;

	// getTotalEntidadRelacion
	public long getTotalEntidadRelacion(String entidad, String[] campos,
			String[] ocurrencia, BigDecimal idempresa) throws RemoteException;

	// RRHH
	// ACTIVIDADES
	public List getRrhhactividadAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhactividadOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhactividadPK(BigDecimal idactividad, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhactividadDelete(BigDecimal idactividad,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhactividadCreate(String actividad, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhactividadCreateOrUpdate(BigDecimal idactividad,
			String actividad, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhactividadUpdate(BigDecimal idactividad, String actividad,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// AFJP
	public List getRrhhafjpAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhafjpOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhafjpPK(BigDecimal idafjp, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhafjpDelete(BigDecimal idafjp, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhafjpCreate(String afjp, String expediente,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String rrhhafjpCreateOrUpdate(BigDecimal idafjp, String afjp,
			String expediente, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhafjpUpdate(BigDecimal idafjp, String afjp,
			String expediente, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// CATEGORIAS
	public List getRrhhcategoriasAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhcategoriasOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrhhcategoriasPK(BigDecimal idcategoria, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhcategoriasDelete(BigDecimal idcategoria,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhcategoriasCreate(String categoria, Double hs, Double hs1,
			Double hs2, Double hs3, Double hs4, String quinmens,BigDecimal basico,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String rrhhcategoriasCreateOrUpdate(BigDecimal idcategoria,
			String categoria, Double hs, Double hs1, Double hs2, Double hs3,
			Double hs4, String quinmens,BigDecimal basico, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhcategoriasUpdate(BigDecimal idcategoria,
			String categoria, Double hs, Double hs1, Double hs2, Double hs3,
			Double hs4, String quinmens,BigDecimal basico, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// CONCEPTOS
	public List getRrhhconceptosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhconceptosOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhconceptosPK(BigDecimal idconcepto, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhconceptosDelete(BigDecimal idconcepto,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhconceptosCreate(String concepto, String imprime,
			String formula, BigDecimal idctacont, BigDecimal idtipoconcepto,
			BigDecimal idtipocantidadconcepto, Double valor, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhconceptosUpdate(BigDecimal idconcepto, String concepto,
			String imprime, String formula, BigDecimal idctacont,
			BigDecimal idtipoconcepto, BigDecimal idtipocantidadconcepto,
			Double valor, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// SALARIOS FAMILIARES
	public List getRrhhcodsfAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhcodsfOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhcodsfPK(BigDecimal idcodsf, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhcodsfDelete(BigDecimal idcodsf, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhcodsfCreate(String codsf, String variable,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String rrhhcodsfCreateOrUpdate(BigDecimal idcodsf, String codsf,
			String variable, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhcodsfUpdate(BigDecimal idcodsf, String codsf,
			String variable, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// tipo documento
	public List getRrhhtipodocumentoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhtipodocumentoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrhhtipodocumentoPK(BigDecimal idtipodocumento,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhtipodocumentoDelete(BigDecimal idtipodocumento,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhtipodocumentoCreate(String tipodocumento,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String rrhhtipodocumentoCreateOrUpdate(BigDecimal idtipodocumento,
			String tipodocumento, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhtipodocumentoUpdate(BigDecimal idtipodocumento,
			String tipodocumento, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// estado civil
	public List getRrhhestadocivilAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhestadocivilOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrhhestadocivilPK(BigDecimal idestadocivil,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhestadocivilDelete(BigDecimal idestadocivil,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhestadocivilCreate(String estadocivil, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhestadocivilCreateOrUpdate(BigDecimal idestadocivil,
			String estadocivil, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhestadocivilUpdate(BigDecimal idestadocivil,
			String estadocivil, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// titulo
	public List getRrhhtituloAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhtituloOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhtituloPK(BigDecimal idtitulo, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhtituloDelete(BigDecimal idtitulo, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhtituloCreate(String titulo, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhtituloCreateOrUpdate(BigDecimal idtitulo, String titulo,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String rrhhtituloUpdate(BigDecimal idtitulo, String titulo,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// obra social
	public List getRrhhobrasocialAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhobrasocialOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrhhobrasocialPK(BigDecimal idobrasocial,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhobrasocialDelete(BigDecimal idobrasocial,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhobrasocialCreate(String obrasocial, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhobrasocialCreateOrUpdate(BigDecimal idobrasocial,
			String obrasocial, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhobrasocialUpdate(BigDecimal idobrasocial,
			String obrasocial, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// total All RRHHACTIVIDAD
	public long getTotalrrhhactividadAll(BigDecimal idempresa)
			throws RemoteException;

	// total Ocu RRHHACTIVIDAD
	public long getTotalrrhhactividadOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws RemoteException;

	// total All rrhhafjp
	public long getTotalrrhhafjpAll(BigDecimal idempresa)
			throws RemoteException;

	// total Ocu rrhhafjp
	public long getTotalrrhhafjpOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws RemoteException;

	// total All rrhhcategorias
	public long getTotalcategoriasAll(BigDecimal idempresa)
			throws RemoteException;

	// total Ocu rrhhcategorias
	public long getTotalcategoriasOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws RemoteException;

	// total All rrhhcodsf
	public long getTotalcodsfAll(BigDecimal idempresa) throws RemoteException;

	// total Ocu rrhhcodsf
	public long getTotalcodsfOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws RemoteException;

	// total All rrhhconceptos
	public long getTotalconceptosAll(BigDecimal idempresa)
			throws RemoteException;

	// total Ocu rrhhconceptos
	public long getTotalconceptosfOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws RemoteException;

	// total All rrhhestadocivil
	public long getTotalestadocivilAll(BigDecimal idempresa)
			throws RemoteException;

	// total Ocu rrhhestadocivil
	public long getTotalestadocivilOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws RemoteException;

	// total All rrhhobrasocial
	public long getTotalobrasocialAll(BigDecimal idempresa)
			throws RemoteException;

	// total Ocu rrhhobrasocial
	public long getTotalobrasocialOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws RemoteException;

	// total All rrhhtipodocumento
	public long getTotaltipodocumentoAll(BigDecimal idempresa)
			throws RemoteException;

	// total Ocu rrhhtipodocumento
	public long getTotaltipodocumentoOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws RemoteException;

	// total All rrhhtitulo
	public long getTotaltituloAll(BigDecimal idempresa) throws RemoteException;

	// total Ocu rrhhtitulo
	public long getTotaltituloOcu(BigDecimal idempresa, String[] campos,
			String ocurrencia) throws RemoteException;

	// busqueda laborales
	public List getRRHHbusquedasLaboralesAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRRHHbusquedasLaboralesActivas(BigDecimal idempresa)
			throws RemoteException;

	public List getRRHHbusquedasLaboralesActivasOcu(String filtro,
			BigDecimal idempresa) throws RemoteException;

	public List getRRHHbusquedasLaboralesOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRRHHbusquedasLaboralesPK(BigDecimal idbusquedalaboral,
			BigDecimal idempresa) throws RemoteException;

	public String RRHHbusquedasLaboralesDelete(BigDecimal idbusquedalaboral,
			BigDecimal idempresa) throws RemoteException;

	public String RRHHbusquedasLaboralesCreate(String referencia,
			Timestamp fechabusquedadesde, Timestamp fechabusquedahasta,
			String seniority, String lugartrabajo, String descripcionproyecto,
			String descripciontarea, String skillexcluyente,
			String skilldeseable, String idioma,
			String posibilidadderenovacion, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String RRHHbusquedasLaboralesCreateOrUpdate(
			BigDecimal idbusquedalaboral, String referencia,
			Timestamp fechabusquedadesde, Timestamp fechabusquedahasta,
			String seniority, String lugartrabajo, String descripcionproyecto,
			String descripciontarea, String skillexcluyente,
			String skilldeseable, String idioma,
			String posibilidadderenovacion, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String RRHHbusquedasLaboralesUpdate(BigDecimal idbusquedalaboral,
			String referencia, Timestamp fechabusquedadesde,
			Timestamp fechabusquedahasta, String seniority,
			String lugartrabajo, String descripcionproyecto,
			String descripciontarea, String skillexcluyente,
			String skilldeseable, String idioma,
			String posibilidadderenovacion, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: rrhhUserPostulante Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Oct 12 14:20:27 ART 2007
	 * 
	 */

	public List getRrhhUserPostulanteAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhUserPostulanteOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrhhUserPostulantePK(BigDecimal iduserpostulante,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhUserPostulanteDatosCvPK(BigDecimal iduserpostulante,
			BigDecimal idempresa) throws RemoteException;

	public List validarRrhhUserPostulante(String userpostulante, String clave,
			BigDecimal idempresa) throws RemoteException;

	public boolean isExisteRrhhUserPostulante(BigDecimal iduserpostulante,
			String userpostulante, BigDecimal idempresa) throws RemoteException;

	public String rrhhUserPostulanteDelete(BigDecimal iduserpostulante)
			throws RemoteException;

	public String rrhhUserPostulanteCreate(String userpostulante, String clave,
			String apellido, String nombre, String email, String pregunta,
			String respuesta, String direccion, String codigo_postal,
			BigDecimal idpais, BigDecimal idprovincia, String idlocalidad,
			BigDecimal nrodni, java.sql.Date fechanac, String telparticular,
			String tellaboral, String telcelular, String emailperfil,
			BigDecimal idpuesto, BigDecimal idlenguaje, BigDecimal idhw,
			BigDecimal idso, BigDecimal iddb, BigDecimal idapp,
			BigDecimal idred, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String rrhhUserPostulanteCreateOrUpdate(BigDecimal iduserpostulante,
			String userpostulante, String clave, String apellido,
			String nombre, String email, String pregunta, String respuesta,
			String direccion, String codigo_postal, BigDecimal idpais,
			BigDecimal idprovincia, String idlocalidad, BigDecimal nrodni,
			java.sql.Date fechanac, String telparticular, String tellaboral,
			String telcelular, String emailperfil, BigDecimal idpuesto,
			BigDecimal idlenguaje, BigDecimal idhw, BigDecimal idso,
			BigDecimal iddb, BigDecimal idapp, BigDecimal idred,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String rrhhUserPostulanteUpdate(BigDecimal iduserpostulante,
			String userpostulante, String clave, String apellido,
			String nombre, String email, String pregunta, String respuesta,
			String direccion, String codigo_postal, BigDecimal idpais,
			BigDecimal idprovincia, String idlocalidad, BigDecimal nrodni,
			java.sql.Date fechanac, String telparticular, String tellaboral,
			String telcelular, String emailperfil, BigDecimal idpuesto,
			BigDecimal idlenguaje, BigDecimal idhw, BigDecimal idso,
			BigDecimal iddb, BigDecimal idapp, BigDecimal idred,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String rrhhUserPostulanteUpdateCV(BigDecimal iduserpostulante,
			String archivocv, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	/**
	 * Metodos para la entidad: RrhhBbLlPuestos Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Oct 12 14:20:27 ART 2007
	 */

	public List getRrhhBbLlPuestosAll(BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: RrhhBbLlApp Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Fri Oct 12 14:20:27 ART 2007
	 */
	public List getRrhhBbLlAppAll(BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: RrhhBbLlDb Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Fri Oct 12 14:20:27 ART 2007
	 */
	public List getRrhhBbLlDbAll(BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: RrhhBbLlLenguaje Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Fri Oct 12 14:20:27 ART 2007
	 */
	public List getRrhhBbLlLenguajeAll(BigDecimal idempresa)
			throws RemoteException;

	/**
	 * Metodos para la entidad: RrhhBbLlHw Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Fri Oct 12 14:20:27 ART 2007
	 */
	public List getRrhhBbLlHwAll(BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: RrhhBbLlRed Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Fri Oct 12 14:20:27 ART 2007
	 */
	public List getRrhhBbLlRedAll(BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: RrhhBbLlSo Copyrigth(r) sysWarp S.R.L. Fecha de
	 * creacion: Fri Oct 12 14:20:27 ART 2007
	 */
	public List getRrhhBbLlSoAll(BigDecimal idempresa) throws RemoteException;

	/**
	 * Metodos para la entidad: rrhhPostulaciones Copyrigth(r) sysWarp S.R.L.
	 * Fecha de creacion: Wed Oct 17 11:16:21 ART 2007
	 */

	public boolean isExisteRrhhPostulaciones(BigDecimal iduserpostulante,
			BigDecimal idbusquedalaboral, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhPostulacionesCreate(BigDecimal iduserpostulante,
			BigDecimal idbusquedalaboral, BigDecimal idstatus,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	// estados sot
	public List getRrhhestadosotAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhestadosotOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhestadosotPK(BigDecimal idestadoot, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhestadosotDelete(BigDecimal idestadoot,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhestadosotCreate(String estadoot, BigDecimal idempresa,
			String usuarioalt) throws RemoteException;

	public String rrhhestadosotCreateOrUpdate(BigDecimal idestadoot,
			String estadoot, BigDecimal idempresa, String usuarioact)
			throws RemoteException;

	public String rrhhestadosotUpdate(BigDecimal idestadoot, String estadoot,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// ordenes de trabajo
	public List getRrhhordenesdetrabajoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhordenesdetrabajoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrhhordenesdetrabajoPK(BigDecimal idordendetrabajo,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhordenesdetrabajoDelete(BigDecimal idordendetrabajo,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhordenesdetrabajoCreate(BigDecimal idcliente,
			String descripcion, Timestamp fechainicio,
			Timestamp fechaprometida, Timestamp fechafinal,
			BigDecimal horasestimadas, Double valorhoracliente,
			Double valorhorarecurso, BigDecimal idestadoot,
			BigDecimal idempresa, String usuarioalt) throws RemoteException;

	public String rrhhordenesdetrabajoCreateOrUpdate(
			BigDecimal idordendetrabajo, BigDecimal idcliente,
			String descripcion, Timestamp fechainicio,
			Timestamp fechaprometida, Timestamp fechafinal,
			BigDecimal horasestimadas, Double valorhoracliente,
			Double valorhorarecurso, BigDecimal idestadoot,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String rrhhordenesdetrabajoUpdate(BigDecimal idordendetrabajo,
			BigDecimal idcliente, String descripcion, Timestamp fechainicio,
			Timestamp fechaprometida, Timestamp fechafinal,
			BigDecimal horasestimadas, Double valorhoracliente,
			Double valorhorarecurso, BigDecimal idestadoot,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// lov estado
	// para el lov de tipo de comprobante
	public List getRrhhestadosotLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhestadosotLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// ot horas
	public List getRrhhothorasAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhothorasOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhothorasPK(BigDecimal idothoras, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhothorasDelete(BigDecimal idothoras, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhothorasCreate(BigDecimal idordendetrabajo,
			String detalle, Timestamp fecha, Timestamp fechaentrada1,
			Timestamp fechasalida1, Timestamp fechaentrada2,
			Timestamp fechasalida2, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	// public String rrhhothorasCreateOrUpdate(BigDecimal idothoras, BigDecimal
	// idordendetrabajo,String detalle, Timestamp fecha, Timestamp
	// fechaentrada1, Timestamp fechasalida1, Timestamp fechaentrada2, Timestamp
	// fechasalida2, BigDecimal idempresa, String usuarioact) throws
	// RemoteException;
	public String rrhhothorasUpdate(BigDecimal idothoras,
			BigDecimal idordendetrabajo, String detalle, Timestamp fecha,
			Timestamp fechaentrada1, Timestamp fechasalida1,
			Timestamp fechaentrada2, Timestamp fechasalida2,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	// lov Orden trabajo
	// para el lov Orden trabajo
	public List getRrhhordentrabajoLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhordentrabajoLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	// ***************************
	public List getRrhhothorasXUsusarioAll(long limit, long offset,
			BigDecimal idempresa, String usuario, BigDecimal idordendetrabajo)
			throws RemoteException;

	public List getRrhhothorasXUsusarioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String usuario,
			BigDecimal idordendetrabajo) throws RemoteException;

	// getTotalEntidad x usuario
	public long getTotalEntidadXUsuario(String entidad, BigDecimal idempresa,
			String usuario, BigDecimal idordendetrabajo) throws RemoteException;

	// getTotalEntidadOcu x usuario
	public long getTotalEntidadOcuXUsuario(String entidad, String[] campos,
			String ocurrencia, BigDecimal idempresa, String usuario,
			BigDecimal idordendetrabajo) throws RemoteException;

	public List getTotalGeneralAll(BigDecimal idempresa, String usuario,
			BigDecimal idordendetrabajo) throws RemoteException;

	public List getTotalGeneralOcu(String ocurrencia, BigDecimal idempresa,
			String usuario, BigDecimal idordendetrabajo) throws RemoteException;

	// *****************************

	// 

	// ot por usuario
	public List getRrhhotxusuarioAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhotxusuarioOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrhhotxusuarioPK(BigDecimal idcodigo, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhotxusuarioDelete(BigDecimal idcodigo, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhotxusuarioCreate(BigDecimal idordendetrabajo,
			String idusuario, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String rrhhotxusuarioCreateOrUpdate(BigDecimal idcodigo,
			BigDecimal idordendetrabajo, String idusuario,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String rrhhotxusuarioUpdate(BigDecimal idcodigo,
			BigDecimal idordendetrabajo, String idusuario,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public BigDecimal getRrhhotxfiltroxOrdenTrabajo(String usuario)
			throws RemoteException;

	public List getRrhhOrdenXusuarioLovAll(long limit, long offset,
			BigDecimal idempresa, String usuario) throws RemoteException;

	public List getRrhhOrdenXusuarioLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa, String usuario)
			throws RemoteException;

	public long getTotalEntidadOrdenxusuario(String entidad,
			BigDecimal idempresa, String usuario) throws RemoteException;

	public long getTotalEntidadOrdenOcuxusuario(String entidad,
			String[] campos, String ocurrencia, BigDecimal idempresa,
			String usuario) throws RemoteException;

	// zona trabajo
	public List getRrhhzonatrabajoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhzonatrabajoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrhhzonatrabajoPK(BigDecimal idzonatrabajo,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhzonatrabajoDelete(BigDecimal idzonatrabajo,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhzonatrabajoCreate(String zonatrabajo, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhzonatrabajoCreateOrUpdate(BigDecimal idzonatrabajo,
			String zonatrabajo, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhzonatrabajoUpdate(BigDecimal idzonatrabajo,
			String zonatrabajo, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	// art
	public List getRrhhartAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhartOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhartPK(BigDecimal idart, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhartDelete(BigDecimal idart, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhartCreate(String art, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhartCreateOrUpdate(BigDecimal idart, String art,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String rrhhartUpdate(BigDecimal idart, String art,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// personal
	public List getRrhhpersonalAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhpersonalOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhpersonalPK(BigDecimal legajo, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhpersonalDelete(BigDecimal legajo, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhpersonalCreate(BigDecimal legajo, String apellido,
			String domicilio, BigDecimal puerta, String piso,
			String departamento, String idlocalidad, String idprovincia,
			String postal, String telefono, String idestadocivil,
			Timestamp fechanac, String sexo, String idtipodocumento,
			BigDecimal nrodocumento, String cuil, String idpais,
			Timestamp fbaja, String idcategoria, String tarea,
			Timestamp fingreso, String idtitulo, String idafjp, String nroafjp,
			String idart, String nroart, Double valor01, Double valor02,
			Double valor03, Double valor04, Double valor05,
			String mensualoquin, String idctacont, String idctacont2,
			BigDecimal aniosrecon, BigDecimal mesrecon, String idobrasocial,
			String jubilado, String email, String idlocalidadpago,
			String idmodalidadcontrato, String idbancodeposito,
			String usuarioalt, BigDecimal idempresa, BigDecimal idlista) throws RemoteException;

	public String rrhhpersonalUpdate(BigDecimal legajo, String apellido,
			String domicilio, BigDecimal puerta, String piso,
			String departamento, String idlocalidad, String idprovincia,
			String postal, String telefono, String idestadocivil,
			Timestamp fechanac, String sexo, String idtipodocumento,
			BigDecimal nrodocumento, String cuil, String idpais,
			Timestamp fbaja, String idcategoria, String tarea,
			Timestamp fingreso, String idtitulo, String idafjp, String nroafjp,
			String idart, String nroart, Double valor01, Double valor02,
			Double valor03, Double valor04, Double valor05,
			String mensualoquin, String idctacont, String idctacont2,
			BigDecimal aniosrecon, BigDecimal mesrecon, String idobrasocial,
			String jubilado, String email, String idlocalidadpago,
			String idmodalidadcontrato, String idbancodeposito,
			String usuarioact, BigDecimal idempresa,BigDecimal idlista) throws RemoteException;

	public List getRrrhhtokenkizerAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrrhhtokenkizerOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrrhhtokenkizerPK(BigDecimal idtokenkizer,
			BigDecimal idempresa) throws RemoteException;

	public String rrrhhtokenkizerDelete(BigDecimal idtokenkizer,
			BigDecimal idempresa) throws RemoteException;

	public String rrrhhtokenkizerCreate(String tokenkizer, String parametro,
			String descripcion, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String rrrhhtokenkizerUpdate(BigDecimal idtokenkizer,
			String tokenkizer, String parametro, String descripcion,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// lov tipo concepto
	public List getRrhhtipoconceptoLovAll(long limit, long offset)
			throws RemoteException;

	public List getRrhhtipoconceptoLovOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	// getTotalEntidadSinEmpresa sin empresa
	public long getTotalEntidadSinEmpresa(String entidad)
			throws RemoteException;

	// getTotalEntidadSinEmpresaOcu sin empresa
	public long getTotalEntidadSinEmpresaOcu(String entidad, String[] campos,
			String ocurrencia) throws RemoteException;

	// tipo cantidad concepto
	public List getRrhhtipocantidadconceptoLovAll(long limit, long offset)
			throws RemoteException;

	public List getRrhhtipocantidadconceptoLovOcu(long limit, long offset,
			String ocurrencia) throws RemoteException;

	/**
	 * Metodos para la entidad: rrhhConceptosXPersonal Copyrigth(r) sysWarp
	 * S.R.L. Fecha de creacion: Fri Aug 21 13:51:25 ART 2009
	 */

	public String callRrhhConceptosXPersonalCreate(String[] idconcepto,
			BigDecimal legajo, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	public String callRrhhConceptosXPersonalDelete(String[] idconcepto,
			BigDecimal legajo, BigDecimal idempresa) throws RemoteException,
			SQLException;

	public List getRrhhConceptosXPersonalPK(BigDecimal legajo,
			String asociados, BigDecimal idempresa) throws RemoteException;

	public String rrhhConceptosXPersonalReplicar(BigDecimal legajo,
			BigDecimal legajoReplicar, BigDecimal idempresa, String usuarioalt)
			throws RemoteException, SQLException;

	// modalidad de contrato
	public List getRrhhmodalidaddecontratoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhmodalidaddecontratoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrhhmodalidaddecontratoPK(BigDecimal idmodalidadcontrato,
			BigDecimal idempresa) throws RemoteException;

	public String getRrhhmodalidaddecontratoDelete(
			BigDecimal idmodalidadcontrato, BigDecimal idempresa)
			throws RemoteException;

	public String getRrhhmodalidaddecontratoCreate(String modalidadcontrato,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String getRrhhmodalidaddecontratoUpdate(
			BigDecimal idmodalidadcontrato, String modalidadcontrato,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	// lov caja identificadores
	public List getRrhhcajaidentificadoresLovAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhcajaidentificadoresLovOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrhhformulasAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhformulasOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhformulasPK(BigDecimal idformula, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhformulasDelete(BigDecimal idformula, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhformulasCreate(String formula, String descripcion,
			String sql, BigDecimal idempresa, String usuarioalt)
			throws RemoteException;

	public String rrhhformulasCreateOrUpdate(BigDecimal idformula,
			String formula, String descripcion, String sql,
			BigDecimal idempresa, String usuarioact) throws RemoteException;

	public String rrhhformulasUpdate(BigDecimal idformula, String formula,
			String descripcion, String sql, BigDecimal idempresa,
			String usuarioact) throws RemoteException;

	public List getRrhhmotivoAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhmotivoAll(boolean tipo, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhmotivoOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhmotivoPK(BigDecimal idmotivo, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhmotivoDelete(BigDecimal idmotivo, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhmotivoCreate(String motivo, boolean esempleado,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String rrhhmotivoCreateOrUpdate(BigDecimal idmotivo, String motivo,
			boolean esempleado, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhmotivoUpdate(BigDecimal idmotivo, String motivo,
			boolean esempleado, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhrazonesAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhrazonesAll(BigDecimal idmotivo, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhrazonesOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhrazonesPK(BigDecimal idrazon, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhrazonesDelete(BigDecimal idrazon, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhrazonesCreate(String razon, BigDecimal idmotivo,
			String usuarioalt, BigDecimal idempresa) throws RemoteException;

	public String rrhhrazonesCreateOrUpdate(BigDecimal idrazon, String razon,
			BigDecimal idmotivo, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhrazonesUpdate(BigDecimal idrazon, String razon,
			BigDecimal idmotivo, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhestadosempleadoAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhestadosempleadoOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrhhestadosempleadoPK(BigDecimal idestadoempleado,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhestadosempleadoDelete(BigDecimal idestadoempleado,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhestadosempleadoCreate(BigDecimal legajo, String apellido,
			boolean esempleado, BigDecimal idmotivo, BigDecimal idrazon,
			String observacion, Timestamp fecha, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhestadosempleadoCreateOrUpdate(
			BigDecimal idestadoempleado, BigDecimal legajo, String apellido,
			boolean esempleado, BigDecimal idmotivo, BigDecimal idrazon,
			String observacion, Timestamp fecha, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhestadosempleadoUpdate(BigDecimal idestadoempleado,
			BigDecimal legajo, String apellido, boolean esempleado,
			BigDecimal idmotivo, BigDecimal idrazon, String observacion,
			Timestamp fecha, String usuarioact, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhListNoEmpleadosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhListNoEmpleadosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public boolean esEmpleadoActual(BigDecimal legajo, BigDecimal idempresa)
			throws RemoteException;

	public List empleadosAFecha(BigDecimal idempresa) throws RemoteException;

	public List getRrhhliq_cabeAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhliq_cabeOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhliq_cabePK(BigDecimal idliqcabe, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhliq_cabeDelete(BigDecimal idliqcabe, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhliq_cabeCreate(BigDecimal nrorecibo, BigDecimal legajo,
			java.sql.Date fecha, int anioliq, int mesliq,
			BigDecimal nroquincena, BigDecimal idcategoria,
			BigDecimal idlocalidadpago, BigDecimal idbancodeposito,
			BigDecimal idmodalidadcontrato, java.sql.Date fechadeposito,
			double importesueldo, double totalremunerativo,
			double totalnoremunerativo, double totaldescuentos,
			double netoacobrar, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhliq_cabeCreateOrUpdate(BigDecimal idliqcabe,
			BigDecimal nrorecibo, BigDecimal legajo, java.sql.Date fecha,
			BigDecimal anioliq, BigDecimal mesliq, BigDecimal nroquincena,
			BigDecimal idcategoria, BigDecimal idlocalidadpago,
			BigDecimal idbancodeposito, BigDecimal idmodalidadcontrato,
			java.sql.Date fechadeposito, double importesueldo,
			double totalremunerativo, double totalnoremunerativo,
			double totaldescuentos, double netoacobrar, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhliq_cabeUpdate(BigDecimal idliqcabe,
			BigDecimal nrorecibo, BigDecimal legajo, java.sql.Date fecha,
			BigDecimal anioliq, BigDecimal mesliq, BigDecimal nroquincena,
			BigDecimal idcategoria, BigDecimal idlocalidadpago,
			BigDecimal idbancodeposito, BigDecimal idmodalidadcontrato,
			java.sql.Date fechadeposito, double importesueldo,
			double totalremunerativo, double totalnoremunerativo,
			double totaldescuentos, double netoacobrar, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public List getPersonalLovList(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getAniosLiquidacion() throws RemoteException;

	public String LiquidacionSueldoXLegajo(BigDecimal desdeLegajo,
			BigDecimal hastaLegajo, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;
	
	public List ObtenerPersonalXPeriodoAll(BigDecimal anioliq, BigDecimal mesliq,
			BigDecimal idempresa,long limit, long offset) throws RemoteException;
	
	public List ObtenerPersonalXPeriodoOcu(BigDecimal anioliq,
			BigDecimal mesliq, long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws  RemoteException;
	
	public boolean liquidacionSueldoEmpleados(String[] args,
			BigDecimal idempresa) throws RemoteException;

	public String conceptoExecuteToString(String concepto, BigDecimal idempresa)
			throws RemoteException;

	public boolean pruebaDeEvaluacion(String formulaConcepto,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhlistaAll(long limit, long offset, BigDecimal idempresa)
			throws RemoteException;

	public List getRrhhlistaOcu(long limit, long offset, String ocurrencia,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhlistaPK(BigDecimal idlista, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhlistaDelete(BigDecimal idlista, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhlistaCreate(String lista, String usuarioalt,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhlistaCreateOrUpdate(BigDecimal idlista, String lista,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public String rrhhlistaUpdate(BigDecimal idlista, String lista,
			String usuarioact, BigDecimal idempresa) throws RemoteException;

	public List getRrhhlistaconceptosAll(long limit, long offset,
			BigDecimal idempresa) throws RemoteException;

	public List getRrhhlistaconceptosOcu(long limit, long offset,
			String ocurrencia, BigDecimal idempresa) throws RemoteException;

	public List getRrhhlistaconceptosPK(BigDecimal idlistaconcepto,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhlistaconceptosDelete(BigDecimal idlistaconcepto,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhlistaconceptosCreate(BigDecimal idlista,
			BigDecimal idconcepto, String usuarioalt, BigDecimal idempresa)
			throws RemoteException;

	public String rrhhlistaconceptosCreateOrUpdate(BigDecimal idlistaconcepto,
			BigDecimal idlista, BigDecimal idconcepto, String usuarioact,
			BigDecimal idempresa) throws RemoteException;

	public String rrhhlistaconceptosUpdate(BigDecimal idlistaconcepto,
			BigDecimal idlista, BigDecimal idconcepto, String usuarioact,
			BigDecimal idempresa) throws RemoteException;
}
