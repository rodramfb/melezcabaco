/* 
   javabean para la entidad: bacoProcesoObsequios
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Nov 05 11:24:51 GMT-03:00 2009 
   
   Para manejar la pagina: bacoProcesoObsequiosAbm.jsp
      
 */
package ar.com.syswarp.web.ejb;

import java.io.*; /*
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
 */
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.util.*;
import java.math.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.servlet.upload.UploadFicheroProObsequios;
import ar.com.syswarp.api.Common;

public class BeanBacoProcesoObsequiosAbm implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanBacoProcesoObsequiosAbm.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List bacoProcesoObsequiosList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String[] idcliente = new String[] {};

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private int anio = -1;

	private int anioactual = Calendar.getInstance().get(Calendar.YEAR);

	private int mes = -1;

	private int mesActual = Calendar.getInstance().get(Calendar.MONTH) + 1;

	private int mesCumpleanos = -1;

	private List mesesList = new ArrayList();

	private List listTipoObsequios = new ArrayList();

	private BigDecimal idtipoobsequio = new BigDecimal(-1);

	private boolean buscar = false;

	private boolean flagValidate = true;

	private boolean existeEsqCarta = false;

	private boolean existeEsqRegalo = false;

	private String usuarioalt = "";

	private String[] resultado = new String[] { "", "" };

	private boolean desdeArchivo = false;

	private String archivo = "";

	private String patharchivo = "";

	private boolean impactaTmp = false;

	private long totalLineas = 0;

	public BeanBacoProcesoObsequiosAbm() {
		super();
	}

	public void setSessionContext(SessionContext newContext)
			throws EJBException {
		context = newContext;
	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
	}

	public boolean ejecutarValidacion() {
		// RequestDispatcher dispatcher = null;
		Clientes clie = Common.getClientes();
		try {

			this.mesesList = Common.getGeneral().getGlobalMeses();
			this.listTipoObsequios = clie.getBacoTipoObsequiosAll(2500, 0,
					this.idempresa);

			if (this.idtipoobsequio.longValue() > 0) {

				this.existeEsqCarta = Common.getClientes()
						.isExisteBacoObsequiosEsquemaPeriodo(this.anio,
								this.mes, this.idtipoobsequio, "C",
								this.idempresa);
				this.existeEsqRegalo = Common.getClientes()
						.isExisteBacoObsequiosEsquemaPeriodo(this.anio,
								this.mes, this.idtipoobsequio, "R",
								this.idempresa);

				String entidad = "";
				String filtro = " WHERE true ";

				if (isDesdeArchivo()) {
					int totalLineas = 0;
					String spliter = "";
					String tablename = "tmp_"
							+ request.getSession().getId().toString()
									.toLowerCase();

					boolean flagArchivoValido = true;

					if (Common.setNotNull(this.archivo).equals("")) {

						this.mensaje = "Es necesario seleccionar un archivo.";

					} else {

						//log.info("CA - isDesdeArchivo(): " + isDesdeArchivo());

						this.callGenerarProceso();

						String[] idclienteTemporal = new String[] {};

						if (this.impactaTmp) {

							Properties props = new Properties();
							props
									.load(BeanBacoProcesoObsequiosAbm.class
											.getResourceAsStream("/ar/com/syswarp/servlet/upload/upload.properties"));
							String pathFile = props
									.getProperty("upload.path.generico");
							pathFile = pathFile.replace("MODULO", "Clientes");
							// log.info("pathFile: " + pathFile);
							File file = new File(pathFile + this.archivo);

							/**/

							BufferedReader input = new BufferedReader(
									new FileReader(file));
							String line = null; // not declared within while
							// loop
							/*
							 * readLine is a bit quirky : it returns the content
							 * of a line MINUS the newline. it returns null only
							 * for the END of the stream. it returns an empty
							 * String if two newlines appear in a row.
							 */

							while ((line = input.readLine()) != null) {
								++totalLineas;
								if (!Common.esEntero(line.toString())) {

									this.mensaje = "Se detectaron valores incorrectos en la linea: "
											+ totalLineas
											+ " - ["
											+ line
											+ "].";

									flagArchivoValido = false;
									break;

								}

								spliter += line.toString() + "#";
							}

							idclienteTemporal = spliter.split("#");
							/*
							 * for(int r=0;r<idclienteTemporal.length;r++)
							 * log.info("idclienteTemporal[" + r + "]: " +
							 * idclienteTemporal[r]);
							 */

							input.close();

							/**/

						}

						if (flagArchivoValido) {

							this.bacoProcesoObsequiosList = clie
									.getBacoProcesObsequiosFromFileAll(
											this.limit, this.offset, tablename,
											idclienteTemporal, impactaTmp,
											idtipoobsequio, this.anio,
											this.mes, idempresa);

							this.totalLineas = clie.getTotalEntidad(tablename
									+ "_aux", this.idempresa);

							this.totalRegistros = clie.getTotalEntidad(
									tablename, this.idempresa);
							this.totalPaginas = (this.totalRegistros / this.limit) + 1;
							if (this.totalPaginas < this.paginaSeleccion)
								this.paginaSeleccion = this.totalPaginas;
							if (this.totalRegistros == this.limit)
								this.offset = 0;
							this.offset = (this.paginaSeleccion - 1)
									* this.limit;
							if (this.totalRegistros == this.limit) {
								this.offset = 0;
								this.totalPaginas = 1;
							}

							this.impactaTmp = false;

						} else
							this.impactaTmp = true;

					}

				} else {

					//log.info("SA - isDesdeArchivo(): " + isDesdeArchivo());

					// -----------------------
					// SIN ARCHIVO -- BEGIN

					if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
						if (ocurrencia.indexOf("'") >= 0) {
							this.mensaje = "Caracteres invalidos en campo busqueda.";
						} else {
							buscar = true;
						}
					}

					if (this.idtipoobsequio.longValue() == 1) {

						if (this.mesCumpleanos < 1) {
							this.mensaje = "Es necesario ingresar mes de cumpleanos";
							this.flagValidate = false;
						} else
							this.callGenerarProceso();

						filtro += " AND DATE_PART('MONTH', fechadenacimiento) = "
								+ this.mesCumpleanos;
						entidad = "(SELECT cl.idcliente, cl.razon, pc.fechadenacimiento, dm.calle, dm.idanexolocalidad, ax.idlocalidad, lo.localidad, "
								+ "       pv.idprovincia, pv.provincia, es.idestado, es.estado, "
								+ "       COALESCE(bo.cartaoregalo, 'N') AS cartaoregalo, "
								+ "       CASE bo.cartaoregalo "
								+ "           WHEN 'C' THEN 'CARTA' "
								+ "           WHEN 'R' THEN 'REGALO'"
								+ "       ELSE "
								+ "           'NO DEF.' END AS descobs,  "
								+ "       cl.idempresa, cl.usuarioalt, cl.usuarioact, cl.fechaalt, cl.fechaact "
								+ "  FROM clientesclientes cl "
								+ "       INNER JOIN clientesdomicilios dm ON cl.idcliente = dm.idcliente "
								+ "              AND cl.idempresa = dm.idempresa AND cl.idtipoclie IN (1, 10) AND dm.esdefault = 'S' "
								+ "       INNER JOIN clientesprecargaclientes pc ON cl.idcliente = pc.idcliente AND cl.idempresa = pc.idempresa "
								+ "       INNER JOIN clientesanexolocalidades ax ON dm.idanexolocalidad = ax.idanexolocalidad AND dm.idempresa = ax.idempresa "
								+ "       INNER JOIN globallocalidades lo ON ax.idlocalidad = lo.idlocalidad "
								+ "       INNER JOIN globalprovincias pv ON lo.idprovincia = pv.idprovincia "
								+ "       INNER JOIN clientesestadoshoy eh ON cl.idcliente = eh.idcliente AND cl.idempresa = eh.idempresa "
								+ "       INNER JOIN clientesestados es ON eh.idestado = es.idestado AND eh.idempresa = es.idempresa  AND es.idestado = 1 "
								+ "        LEFT JOIN bacoobsequioslocalidad bo ON lo.idlocalidad = bo.idlocalidad  AND bo.idempresa = "
				+ this.idempresa.toString()
								+ "        LEFT JOIN pedidos_cabe pe ON cl.idcliente = pe.idcliente AND cl.idempresa = pe.idempresa "
								+ "              AND pe.idestado <> 4 AND  DATE_PART('MONTH', pe.fechapedido) = "
								+ this.mes
								+ "              AND  DATE_PART('YEAR', pe.fechapedido) = "
								+ this.anio
								+ "              AND pe.origenpedido = 'PO' || "
								+ this.idtipoobsequio

								+ ") entidad ";

						if (buscar) {

							filtro += " AND (UPPER(razon) LIKE '%"
									+ ocurrencia.toUpperCase().trim()
									+ "%' OR UPPER(idcliente::VARCHAR) LIKE '%"
									+ ocurrencia.toUpperCase().trim() + "%') ";

							this.totalRegistros = clie.getTotalEntidadFiltro(
									entidad, filtro, this.idempresa);
							this.totalPaginas = (this.totalRegistros / this.limit) + 1;
							if (this.totalPaginas < this.paginaSeleccion)
								this.paginaSeleccion = this.totalPaginas;
							if (this.totalRegistros == this.limit)
								this.offset = 0;
							this.offset = (this.paginaSeleccion - 1)
									* this.limit;
							if (this.totalRegistros == this.limit) {
								this.offset = 0;
								this.totalPaginas = 1;
							}
							this.bacoProcesoObsequiosList = clie
									.getBacoProcesoObsequiosOcu(this.limit,
											this.offset, this.ocurrencia,
											this.idtipoobsequio,
											this.mesCumpleanos, this.anio,
											this.mes, this.idempresa);
						} else {
							this.totalRegistros = clie.getTotalEntidadFiltro(
									entidad, filtro, this.idempresa);
							this.totalPaginas = (this.totalRegistros / this.limit) + 1;
							if (this.totalPaginas < this.paginaSeleccion)
								this.paginaSeleccion = this.totalPaginas;
							this.offset = (this.paginaSeleccion - 1)
									* this.limit;
							if (this.totalRegistros == this.limit) {
								this.offset = 0;
								this.totalPaginas = 1;
							}
							this.bacoProcesoObsequiosList = clie
									.getBacoProcesoObsequiosAll(this.limit,
											this.offset, this.idtipoobsequio,
											this.mesCumpleanos, this.anio,
											this.mes, this.idempresa);
						}

						if (this.totalRegistros < 1
								&& this.mensaje.equalsIgnoreCase("")
								&& !this.accion.equalsIgnoreCase("generar"))
							this.mensaje = "No existen registros.";

					} else {

						this.mensaje = "El proceso de obsequios seleccionado no tiene lógica definida.";

					}

					// -----------------------
					// SIN ARCHIVO -- END
				}

			} else {

				this.mensaje = "Es necesario seleccionar tipo de proceso.";

			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	private void callGenerarProceso() {

		try {

			if (this.accion.equalsIgnoreCase("generar")) {

				if (this.idcliente == null || this.idcliente.length == 0) {

					this.mensaje = "Es necesario seleccionar al menos un registro.";

				} else if (this.mes < 1) {

					this.mensaje = "Es necesario seleccionar mes.";

				} else if (this.anio < 1) {

					this.mensaje = "Es necesario seleccionar anio.";

				} else {

					this.resultado = Common.getClientes().setProcesoObsequios(
							this.idcliente, this.anio, this.mes,
							this.idtipoobsequio, this.desdeArchivo,
							this.idempresa, this.usuarioalt);

					this.impactaTmp = true;

					this.mensaje = "Proceso Ejecutado.";

				}

			}

		} catch (Exception e) {

			log.error("callGenerarProceso(): " + e);

		}

		// log.info("callGenerarProceso - mensaje:" + mensaje);

	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(long total) {
		this.totalRegistros = total;
	}

	public long getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(long totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public long getPaginaSeleccion() {
		return paginaSeleccion;
	}

	public void setPaginaSeleccion(long paginaSeleccion) {
		this.paginaSeleccion = paginaSeleccion;
	}

	public List getBacoProcesoObsequiosList() {
		return bacoProcesoObsequiosList;
	}

	public void setBacoProcesoObsequiosList(List bacoProcesoObsequiosList) {
		this.bacoProcesoObsequiosList = bacoProcesoObsequiosList;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String buscar) {
		this.ocurrencia = buscar;
	}

	public String[] getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(String[] idcliente) {
		this.idcliente = idcliente;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getAnioactual() {
		return anioactual;
	}

	public void setAnioactual(int anioactual) {
		this.anioactual = anioactual;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getMesCumpleanos() {
		return mesCumpleanos;
	}

	public void setMesCumpleanos(int mesCumpleanos) {
		this.mesCumpleanos = mesCumpleanos;
	}

	public List getMesesList() {
		return mesesList;
	}

	public void setMesesList(List mesesList) {
		this.mesesList = mesesList;
	}

	public List getListTipoObsequios() {
		return listTipoObsequios;
	}

	public void setListTipoObsequios(List listTipoObsequios) {
		this.listTipoObsequios = listTipoObsequios;
	}

	public BigDecimal getIdtipoobsequio() {
		return idtipoobsequio;
	}

	public void setIdtipoobsequio(BigDecimal idtipoobsequio) {
		this.idtipoobsequio = idtipoobsequio;
	}

	public boolean isExisteEsqCarta() {
		return existeEsqCarta;
	}

	public boolean isExisteEsqRegalo() {
		return existeEsqRegalo;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String[] getResultado() {
		return resultado;
	}

	public void setResultado(String[] resultado) {
		this.resultado = resultado;
	}

	public boolean isDesdeArchivo() {
		return desdeArchivo;
	}

	public void setDesdeArchivo(boolean desdeArchivo) {
		this.desdeArchivo = desdeArchivo;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getPatharchivo() {
		return patharchivo;
	}

	public void setPatharchivo(String patharchivo) {
		this.patharchivo = patharchivo;
	}

	public boolean isImpactaTmp() {
		return impactaTmp;
	}

	public void setImpactaTmp(boolean impactaTmp) {
		this.impactaTmp = impactaTmp;
	}

	public long getTotalLineas() {
		return totalLineas;
	}

	public void setTotalLineas(long totalLineas) {
		this.totalLineas = totalLineas;
	}

}
