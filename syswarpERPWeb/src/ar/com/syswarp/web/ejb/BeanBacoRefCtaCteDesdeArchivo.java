/* 
   javabean para la entidad: bacoRefCtaCte
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jun 17 12:34:05 ART 2010 
   
   Para manejar la pagina: bacoRefCtaCteAbm.jsp
      
 */
package ar.com.syswarp.web.ejb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanBacoRefCtaCteDesdeArchivo implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanBacoRefCtaCteDesdeArchivo.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List bacoRefCtaCteList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idctacte = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String idcliente = "";

	boolean buscar = false;

	private String archivo = "";

	private String patharchivo = "";

	private boolean impactaTmp = false;

	private String tablename = "";

	private String retorno = "";

	private String usuarioalt = "";

	private long totalRegInvalidos = 0;

	public BeanBacoRefCtaCteDesdeArchivo() {
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
		RequestDispatcher dispatcher = null;
		Clientes clientes = Common.getClientes();
		this.tablename = "tmp_"
				+ this.request.getSession().getId().toString().toLowerCase();

		try {

			if (this.accion.equalsIgnoreCase("baja")) {
				if (idctacte.longValue() < 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = clientes.bacoRefCtaCteDelete(idctacte,
							this.idempresa);
				}
			}

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			if (this.isImpactaTmp()
					&& Common.setNotNull(this.accion).equalsIgnoreCase(
							"procesar")) {

				Properties props = new Properties();
				props
						.load(BeanBacoRefCtaCteDesdeArchivo.class
								.getResourceAsStream("/ar/com/syswarp/servlet/upload/upload.properties"));
				String pathFile = props.getProperty("upload.path.generico");
				pathFile = pathFile.replace("MODULO", "Clientes");
				// log.info("pathFile: " + pathFile);
				File file = new File(pathFile + this.archivo);
				/**/
				BufferedReader input = new BufferedReader(new FileReader(file));
				// log.info("SALIDA: " + salida);

				this.retorno = clientes.bacoRefCtaCtePuntosFromFile(input,
						tablename, idempresa);

				if (this.retorno.equalsIgnoreCase("OK")) {

					this.impactaTmp = false;
				} else
					this.mensaje = this.retorno;

				log.info("MENSAJE: " + this.mensaje);
			} else if (!this.isImpactaTmp()
					&& !Common.setNotNull(this.archivo).equals("")
					&& Common.setNotNull(this.accion).equalsIgnoreCase("carga")) {

				this.retorno = clientes.bacoRefCtaCteCargarPuntosFromFile(
						this.tablename, this.archivo, this.usuarioalt,
						this.idempresa);

				if (Common.setNotNull(this.retorno).equalsIgnoreCase("OK")) {
					this.mensaje = "Puntos asignados correctamente.";
				} else
					this.mensaje = this.retorno;

			}

			if (!this.isImpactaTmp()
					&& !Common.setNotNull(this.archivo).equals("")) {

				String filtro = " WHERE TRUE ";
				String filtroTotales = " WHERE idoperacion = -1 OR idcliente = -1 ";

				this.totalRegInvalidos = clientes.getTotalEntidadFiltro(
						this.tablename, filtroTotales, this.idempresa);

				filtroTotales = " WHERE idcliente = -1 AND idoperacion -1 ";

				if (buscar) {

					filtro += " AND ( razon ILIKE  '%" + ocurrencia
							+ "%' OR  idcliente::varchar ILIKE  '%"
							+ ocurrencia
							+ "%'  OR  idcliefile::varchar  ILIKE  '%"
							+ ocurrencia + "%' ) ";

					this.totalRegistros = clientes.getTotalEntidadFiltro(
							this.tablename, filtro, this.idempresa);
					this.totalPaginas = (this.totalRegistros / this.limit) + 1;
					if (this.totalPaginas < this.paginaSeleccion)
						this.paginaSeleccion = this.totalPaginas;
					if (this.totalRegistros == this.limit)
						this.offset = 0;
					this.offset = (this.paginaSeleccion - 1) * this.limit;
					if (this.totalRegistros == this.limit) {
						this.offset = 0;
						this.totalPaginas = 1;
					}
					this.bacoRefCtaCteList = clientes
							.getBacoRefCtaCteFromFileOcu(this.limit,
									this.offset, this.tablename,
									this.ocurrencia, this.idempresa);
				} else {
					this.totalRegistros = clientes.getTotalEntidadFiltro(
							tablename, filtro, this.idempresa);
					this.totalPaginas = (this.totalRegistros / this.limit) + 1;
					if (this.totalPaginas < this.paginaSeleccion)
						this.paginaSeleccion = this.totalPaginas;
					this.offset = (this.paginaSeleccion - 1) * this.limit;
					if (this.totalRegistros == this.limit) {
						this.offset = 0;
						this.totalPaginas = 1;
					}
					this.bacoRefCtaCteList = clientes
							.getBacoRefCtaCteFromFileAll(this.limit,
									this.offset, this.tablename, this.idempresa);
				}

				if (this.totalRegistros < 1 && this.mensaje.equals(""))
					this.mensaje = "No existen registros.";

			} else {

				if (Common.setNotNull(this.mensaje).equals(""))
					this.mensaje = "Seleccionar archivo.";

			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
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

	public List getBacoRefCtaCteList() {
		return bacoRefCtaCteList;
	}

	public void setBacoRefCtaCteList(List bacoRefCtaCteList) {
		this.bacoRefCtaCteList = bacoRefCtaCteList;
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

	public BigDecimal getIdctacte() {
		return idctacte;
	}

	public void setIdctacte(BigDecimal idctacte) {
		this.idctacte = idctacte;
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

	public String getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(String idcliente) {
		this.idcliente = idcliente;
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

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public long getTotalRegInvalidos() {
		return totalRegInvalidos;
	}

}
