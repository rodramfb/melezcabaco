/* 
   javabean para la entidad: vCajaSubcontabilidad
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jul 07 08:34:18 GYT 2009 
   
   Para manejar la pagina: vCajaSubcontabilidadAbm.jsp
      
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanSubcontabilidadGetSetPasaje implements SessionBean,
		Serializable {

	private static final long serialVersionUID = 200907071L;

	static Logger log = Logger.getLogger(BeanSubcontabilidadGetSetPasaje.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal ejercicio = new BigDecimal(-1);

	private String usuarioalt = "";

	private String entidad = "";

	private String fdesde = "";

	private String fhasta = "";

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List vCajaSubcontabilidadList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private java.sql.Timestamp fechamov;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String fechaUltimoPasajeContable = "";

	boolean buscar = false;

	private String modulo = "";

	private java.sql.Timestamp fechaEjercicioActivoDesde = null;

	private java.sql.Timestamp fechaEjercicioActivoHasta = null;

	public BeanSubcontabilidadGetSetPasaje() {
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

		Contable contable = Common.getContable();

		try {

					
			this.fechaEjercicioActivoDesde = java.sql.Timestamp.valueOf(request
					.getSession().getAttribute("fechaEjercicioActivoDesde")
					.toString());
			this.fechaEjercicioActivoHasta = java.sql.Timestamp.valueOf(request
					.getSession().getAttribute("fechaEjercicioActivoHasta")
					.toString());

			this.ejercicio = new BigDecimal(request.getSession().getAttribute(
					"ejercicioActivo")
					+ "");
			this.usuarioalt = request.getSession().getAttribute("usuario") + "";

			java.sql.Date f_desde = (java.sql.Date) Common
					.setObjectToStrOrTime(this.fdesde, "StrToJSDate");
			java.sql.Date f_hasta = (java.sql.Date) Common
					.setObjectToStrOrTime(this.fhasta, "StrToJSDate");

			String variable = "";
			java.sql.Date fUPContable = null;

			if (entidad.equalsIgnoreCase("VCAJASUBCONTABILIDAD")) {
				variable = "tesoFechaUltimoCierreTesoreria";
				this.modulo = "Caja";
				
			} else if (entidad.equalsIgnoreCase("VPROVEEDORESSUBCONTABILIDAD")) {
				variable = "provFechaUltimoCierre";
				this.modulo = "Compras";

			} else if (entidad.equalsIgnoreCase("VCLIENTESSUBCONTABILIDAD")) {
				variable = "cliFechaUltimoCierre";
				this.modulo = "Ventas";
				
				
			} else if (entidad.equalsIgnoreCase("VSTOCKSUBCONTABILIDAD")) {
				variable = "stockFechaUltimoCierre";
				this.modulo = "Stock";
			}

			if (!this.modulo.equals(""))
				this.fechaUltimoPasajeContable = Common.getGeneral()
						.getValorSetupVariablesNoStatic(variable,
								this.idempresa);

			if (this.accion.equals("")) {
				this.mensaje = "Seleccionar criterio de operacion.";
			} else if (this.entidad.equals("")) {
				this.mensaje = "Seleccione tipo de Consulta - Pasaje.";
			} else if (f_desde == null) {
				this.mensaje = "Ingrese fecha desde.";
			} else if (f_hasta == null) {
				this.mensaje = "Ingrese fecha hasta.";
			} else if (f_desde.after(f_hasta)) {
				this.mensaje = "Fecha desde debe ser menor o igual a fecha hasta.";
			} else if (f_hasta.before(this.fechaEjercicioActivoDesde)
					|| f_hasta.after(this.fechaEjercicioActivoHasta)) {
				this.mensaje = "Fecha hasta debe estar comprendida entre el rango de fechas de inicio y fin de ejercicio: "
						+ Common.setObjectToStrOrTime(
								this.fechaEjercicioActivoDesde, "JSTsToStr")
						+ "  -  "
						+ Common.setObjectToStrOrTime(
								this.fechaEjercicioActivoHasta, "JSTsToStr");
			} else {

				fUPContable = (java.sql.Date) Common.setObjectToStrOrTime(
						this.fechaUltimoPasajeContable, "StrToJSDate");

				if (fUPContable == null) {

					this.mensaje = "Fecha de Ultimo pasaje de " + this.modulo
							+ ", no definida. ";

				} else if (fUPContable.after(f_desde)) {

					this.mensaje = "Fecha desde debe ser mayor o igual a fecha de Ultimo pasaje de "
							+ this.modulo + ".";

				} else if (this.accion.equalsIgnoreCase("consulta")) {

					String subQuery = "(SELECT * FROM " + this.entidad
							+ ") ent ";
					String filtro = " WHERE ent.fechamov::DATE BETWEEN TO_DATE('"
							+ this.fdesde
							+ "', 'dd/mm/yyyy') AND TO_DATE('"
							+ this.fhasta + "', 'dd/mm/yyyy') ";

					this.totalRegistros = contable.getTotalEntidadFiltro(
							subQuery, filtro, this.idempresa);
					this.totalPaginas = (this.totalRegistros / this.limit) + 1;
					if (this.totalPaginas < this.paginaSeleccion)
						this.paginaSeleccion = this.totalPaginas;
					this.offset = (this.paginaSeleccion - 1) * this.limit;
					if (this.totalRegistros == this.limit) {
						this.offset = 0;
						this.totalPaginas = 1;
					}

					this.vCajaSubcontabilidadList = contable
							.getSubcontabilidadAll(this.entidad,
									this.ejercicio, this.fdesde, this.fhasta,
									this.idempresa);

					if (this.totalRegistros < 1)
						this.mensaje = "No existen registros.";

				} else if (this.accion.equalsIgnoreCase("pasaje")) {

					this.mensaje = contable.setLibroDiarioSC(this.ejercicio
							.intValue(), this.fdesde, this.fhasta,
							this.entidad, this.usuarioalt, this.idempresa);

					if (this.mensaje.equalsIgnoreCase("OK")) {

						this.mensaje = "Pasaje realizado con Exito.";

					}

				}

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

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getFdesde() {
		return fdesde;
	}

	public void setFdesde(String desde) {
		fdesde = desde;
	}

	public String getFhasta() {
		return fhasta;
	}

	public void setFhasta(String hasta) {
		fhasta = hasta;
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

	public List getVCajaSubcontabilidadList() {
		return vCajaSubcontabilidadList;
	}

	public void setVCajaSubcontabilidadList(List vCajaSubcontabilidadList) {
		this.vCajaSubcontabilidadList = vCajaSubcontabilidadList;
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

	public java.sql.Timestamp getFechamov() {
		return fechamov;
	}

	public void setFechamov(java.sql.Timestamp fechamov) {
		this.fechamov = fechamov;
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

	public String getFechaUltimoPasajeContable() {
		return fechaUltimoPasajeContable;
	}

	public void setFechaUltimoPasajeContable(String fechaUltimoPasajeContable) {
		this.fechaUltimoPasajeContable = fechaUltimoPasajeContable;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

}
