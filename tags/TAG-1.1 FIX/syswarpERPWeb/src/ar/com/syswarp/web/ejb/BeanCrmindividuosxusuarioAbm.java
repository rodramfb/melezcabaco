/* 
 javabean para la entidad: crmindividuos
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jun 14 17:23:23 GMT-03:00 2007 
 
 Para manejar la pagina: crmindividuosAbm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Date;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanCrmindividuosxusuarioAbm implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanCrmindividuosxusuarioAbm.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List crmindividuosList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idindividuos = BigDecimal.valueOf(0);

	private BigDecimal idempresa = BigDecimal.valueOf(0);

	private String usu = "";

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String filtro = "";

	private String subfiltro = "";

	private List listaTipoClientes = null;

	private List listaTipoLLamados = null;

	private List listaTipoCotizacion = null;

	private String fechacotizaciondesde = "";

	private String fechacotizacionhasta = "";

	private String fechallamadodesde = "";

	private String fechallamadohasta = "";

	private String fechavisitadesde = "";

	private String fechavisitahasta = "";

	private String nombrepariente = "";

	private String paramUrl = "";

	public BeanCrmindividuosxusuarioAbm() {
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
		CRM crmindividuos = Common.getCrm();
		String filtrofuncion = this.filtro;

		try {

			if (this.accion.equalsIgnoreCase("baja")) {
				if (idindividuos == null
						|| idindividuos.compareTo(new BigDecimal(0)) == 0) {
					this.mensaje = "Debe seleccionar un registro a eliminar.";
				} else {
					this.mensaje = crmindividuos.crmindividuosDelete(
							idindividuos, this.idempresa);
				}
			}
			if (this.accion.equalsIgnoreCase("modificacion")) {
				if (idindividuos == null
						|| idindividuos.compareTo(new BigDecimal(0)) == 0) {
					this.mensaje = "Debe seleccionar un registro a modificar.";
				} else {
					dispatcher = request
							.getRequestDispatcher("crmindividuosxusuarioFrm.jsp");
					dispatcher.forward(request, response);
					return true;
				}
			}
			if (this.accion.equalsIgnoreCase("alta")) {
				dispatcher = request
						.getRequestDispatcher("crmindividuosxusuarioFrm.jsp");
				dispatcher.forward(request, response);
				return true;
			}

			this.listaTipoClientes = crmindividuos.getCrmtiposclientesAll(100,
					0, this.idempresa);
			this.listaTipoCotizacion = crmindividuos
					.getCrmtiposcotizacionesAll(100, 0, this.idempresa);
			this.listaTipoLLamados = crmindividuos.getCrmtiposllamadasAll(100,
					0, this.idempresa);

			if (!this.ocurrencia.trim().equalsIgnoreCase("")
					|| !this.filtro.equals("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					if (this.filtro.equals("")
							&& !this.ocurrencia.equalsIgnoreCase("OK"))
						this.mensaje = "Seleccione filtro de busqueda.";
					else
						buscar = true;
				}
			}

			if (buscar) {
				java.sql.Date fdesde = null;
				java.sql.Date fhasta = null;
				if (!filtrofuncion.trim().equals("")) {
					filtrofuncion = filtrofuncion.replaceAll("!", " LIKE ");
					filtrofuncion = filtrofuncion.replaceAll("&", " = ");
					filtrofuncion = filtrofuncion.replaceAll("#", " \n AND ");
					filtrofuncion = filtrofuncion.replaceAll("ENTITY",
							"vcrmindividuos");
					filtrofuncion = filtrofuncion.substring(0, filtrofuncion
							.lastIndexOf("AND"));
				}

				if (Common.isFechaValida(this.fechacotizaciondesde)
						&& Common.isFechaValida(this.fechacotizacionhasta)) {

					fdesde = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechacotizaciondesde, "StrToJSDate");
					fhasta = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechacotizacionhasta, "StrToJSDate");

					filtrofuncion += !filtrofuncion.trim().equals("") ? " AND "
							: "";
					filtrofuncion += " vcrmindividuos.idindividuos  IN (  "
							+ "SELECT a.idindividuos "
							+ "  FROM vcrmcotizacionesfecha a "
							+ " WHERE a.idindividuos = vcrmindividuos.idindividuos "
							+ "   AND a.idusuario = vcrmindividuos.idusuario "
							+ "   AND a.idempresa = vcrmindividuos.idempresa "
							+ "   AND a.fechacotizacion BETWEEN  '" + fdesde
							+ "'::DATE AND '" + fhasta + "'::DATE)";
				}

				if (Common.isFechaValida(this.fechallamadodesde)
						&& Common.isFechaValida(this.fechallamadohasta)) {

					fdesde = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechallamadodesde, "StrToJSDate");
					fhasta = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechallamadohasta, "StrToJSDate");

					filtrofuncion += !filtrofuncion.trim().equals("") ? " AND "
							: "";
					filtrofuncion += "  vcrmindividuos.idindividuos  IN (  "
							+ "SELECT b.idindividuos "
							+ "  FROM vcrmllamadosfecha b "
							+ " WHERE b.idindividuos = vcrmindividuos.idindividuos "
							+ "   AND b.idusuario = vcrmindividuos.idusuario "
							+ "   AND b.idempresa = vcrmindividuos.idempresa "
							+ "   AND b.fechallamada BETWEEN  '" + fdesde
							+ "'::DATE AND '" + fhasta + "'::DATE)";
				}

				if (Common.isFechaValida(this.fechavisitadesde)
						&& Common.isFechaValida(this.fechavisitahasta)) {

					fdesde = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechavisitadesde, "StrToJSDate");
					fhasta = (java.sql.Date) Common.setObjectToStrOrTime(
							this.fechavisitahasta, "StrToJSDate");

					filtrofuncion += !filtrofuncion.trim().equals("") ? " AND "
							: "";

					filtrofuncion += " vcrmindividuos.fechaalt::DATE BETWEEN '"
							+ fdesde + "'::DATE AND '" + fhasta + "'::DATE ";

				}

				if (!this.nombrepariente.equals("")) {
					filtrofuncion += !filtrofuncion.trim().equals("") ? " AND "
							: "";
					filtrofuncion += " vcrmindividuos.idindividuos "
							+ "     IN ("
							+ "         SELECT idindividuos "
							+ "           FROM crmfamiliares WHERE UPPER(nombre) LIKE UPPER('"
							+ this.nombrepariente + "%')) ";
				}

				filtrofuncion = " WHERE " + filtrofuncion;
				filtrofuncion += "  AND UPPER(vcrmindividuos.usuario) = '"
						+ this.usu.toUpperCase() + "'";

				log.info("filtrofuncion: " + filtrofuncion);

				this.totalRegistros = crmindividuos.getTotalEntidadFiltroAlias(
						"vcrmindividuos", filtrofuncion, this.idempresa);
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

				this.crmindividuosList = crmindividuos
						.getCrmindividuosxusuarioFiltro(this.limit,
								this.offset, filtrofuncion);
			} else {
				// 

				filtrofuncion = " WHERE UPPER(vcrmindividuos.usuario) = '"
						+ this.usu.toUpperCase()
						+ "'  AND vcrmindividuos.llamadosfecha > 0";

				this.totalRegistros = crmindividuos.getTotalEntidadFiltroAlias(
						"vcrmindividuos", filtrofuncion, this.idempresa);
				/*
				 * this.totalRegistros = crmindividuos.getTotalEntidadFiltro(
				 * "vcrmindividuos", filtrofuncion, this.idempresa);
				 */

				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.crmindividuosList = crmindividuos
						.getCrmindividuosxusuarioFiltro(this.limit,
								this.offset, filtrofuncion);

				/*
				 * this.crmindividuosList = crmindividuos
				 * .getCrmindividuosxusuarioAll(this.limit, this.offset,
				 * this.idempresa, this.usu);
				 */
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";
		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	/*
	 * public String setArrayJavaScript(String tipo) { String array = ""; int j =
	 * 0; try {
	 * 
	 * if (tipo.equalsIgnoreCase("idtipocliente")) {
	 * 
	 * for (j = 0; j < this.listaTipoClientes.size(); j++) { String[] datos =
	 * (String[]) this.listaTipoClientes.get(j); array += "'" + datos[0] + "#" +
	 * datos[1] + "',"; }
	 * 
	 * array = array.substring(0, array.lastIndexOf(",")); } else if
	 * (tipo.equalsIgnoreCase("actividad_programada")) { String[] datos = null;
	 * for (j = 0; j < this.listaTipoCotizacion.size(); j++) { datos =
	 * (String[]) this.listaTipoCotizacion.get(j); array += "'idtipocotizacion#" +
	 * datos[0] + "#" + datos[1] + "',"; } for (j = 0; j <
	 * this.listaTipoLLamados.size(); j++) { datos = (String[])
	 * this.listaTipoLLamados.get(j); array += "'idtipollamada#" + datos[0] +
	 * "#" + datos[1] + "',"; }
	 * 
	 * array = array.substring(0, array.lastIndexOf(",")); } } catch (Exception
	 * e) { log.error("setArrayJavaScript(tipo):" + e); } return array; }
	 */
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

	public List getCrmindividuosList() {
		return crmindividuosList;
	}

	public void setCrmindividuosList(List crmindividuosList) {
		this.crmindividuosList = crmindividuosList;
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

	public BigDecimal getIdindividuos() {
		return idindividuos;
	}

	public void setIdindividuos(BigDecimal idindividuos) {
		this.idindividuos = idindividuos;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getUsu() {
		return usu;
	}

	public void setUsu(String usu) {
		this.usu = usu;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List getListaTipoClientes() {
		return listaTipoClientes;
	}

	public void setListaTipoClientes(List listaTipoClientes) {
		this.listaTipoClientes = listaTipoClientes;
	}

	public List getListaTipoCotizacion() {
		return listaTipoCotizacion;
	}

	public void setListaTipoCotizacion(List listaTipoCotizacion) {
		this.listaTipoCotizacion = listaTipoCotizacion;
	}

	public List getListaTipoLLamados() {
		return listaTipoLLamados;
	}

	public void setListaTipoLLamados(List listaTipoLLamados) {
		this.listaTipoLLamados = listaTipoLLamados;
	}

	public String getSubfiltro() {
		return subfiltro;
	}

	public void setSubfiltro(String subfiltro) {
		this.subfiltro = subfiltro;
	}

	public String getFechacotizaciondesde() {
		return fechacotizaciondesde;
	}

	public void setFechacotizaciondesde(String fechacotizaciondesde) {
		this.fechacotizaciondesde = fechacotizaciondesde;
	}

	public String getFechacotizacionhasta() {
		return fechacotizacionhasta;
	}

	public void setFechacotizacionhasta(String fechacotizacionhasta) {
		this.fechacotizacionhasta = fechacotizacionhasta;
	}

	public String getFechallamadodesde() {
		return fechallamadodesde;
	}

	public void setFechallamadodesde(String fechallamadodesde) {
		this.fechallamadodesde = fechallamadodesde;
	}

	public String getFechallamadohasta() {
		return fechallamadohasta;
	}

	public void setFechallamadohasta(String fechallamadohasta) {
		this.fechallamadohasta = fechallamadohasta;
	}

	public String getParamUrl() {
		return paramUrl;
	}

	public void setParamUrl(String paramUrl) {
		this.paramUrl = paramUrl;
	}

	public String getFechavisitadesde() {
		return fechavisitadesde;
	}

	public void setFechavisitadesde(String fechavisitadesde) {
		this.fechavisitadesde = fechavisitadesde;
	}

	public String getFechavisitahasta() {
		return fechavisitahasta;
	}

	public void setFechavisitahasta(String fechavisitahasta) {
		this.fechavisitahasta = fechavisitahasta;
	}

	public String getNombrepariente() {
		return nombrepariente;
	}

	public void setNombrepariente(String nombrepariente) {
		this.nombrepariente = nombrepariente;
	}

}
