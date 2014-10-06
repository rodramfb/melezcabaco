/* 
 javabean para la entidad (Formulario): globalentidadesasociables
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue May 06 14:29:26 CEST 2008 
 
 Para manejar la pagina: globalentidadesasociablesFrm.jsp
 
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
import java.math.*;
import java.util.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanAsociarDocumentosLovGenerico implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanAsociarDocumentosLovGenerico.class);

	/**/

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List documentosGenricList = new ArrayList();

	private String ocurrencia = "";

	/**/

	private String validar = "";

	private BigDecimal identidadesasociables = BigDecimal.valueOf(-1);

	private String entidadasociable = "";

	private String descripcion = "";

	private String campopk = "";

	private String querypk = "";

	private String querygrilla = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String accion = "";

	private String[][] columnsMetaData = new String[][] { {} };

	private String cmpCodigo = "";

	private String cmpDescrip = "";

	public BeanAsociarDocumentosLovGenerico() {
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

	private void getDatosEntidadAsociable() {
		try {
			General gral = Common.getGeneral();
			List listGlobalentidadesasociables = gral
					.getGlobalentidadesasociablesPK(this.identidadesasociables,
							this.idempresa);
			Iterator iterGlobalentidadesasociables = listGlobalentidadesasociables
					.iterator();
			if (iterGlobalentidadesasociables.hasNext()) {
				String[] uCampos = (String[]) iterGlobalentidadesasociables
						.next();
				// TODO: Constructores para cada tipo de datos
				this.identidadesasociables = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.entidadasociable = uCampos[1];
				this.descripcion = uCampos[2];
				this.campopk = uCampos[3];
				this.querypk = uCampos[4];
				this.querygrilla = uCampos[5];
				this.idempresa = BigDecimal.valueOf(Long.parseLong(uCampos[6]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosEntidadAsociable()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			String entidad = "";
			General gral = Common.getGeneral();
			this.getDatosEntidadAsociable();

			entidad = "(" + this.querygrilla + ") AS entidad ";

			this.columnsMetaData = gral.getQueryEntidadMetadata(entidad,
					idempresa);

			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}

			if (buscar) {

				String filtro = " WHERE (UPPER(\"" + this.columnsMetaData[1][0]
						+ "\"::VARCHAR) LIKE '%";
				filtro += ocurrencia.toUpperCase() + "%' OR  UPPER(\""
						+ this.columnsMetaData[2][0] + "\"::VARCHAR) LIKE '%"
						+ ocurrencia.toUpperCase() + "%' ) ";

				this.totalRegistros = gral.getTotalEntidadFiltro(entidad,
						filtro, this.idempresa);

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
				this.documentosGenricList = gral.getDocumentosGenericosLovOcu(
						this.limit, this.offset, entidad, filtro.replaceFirst(
								"WHERE", "AND"), this.idempresa);
			} else {

				// TODO: revisar si es necesario sobrecargar getTotalEntidad con
				// idempresa

				this.totalRegistros = gral.getTotalEntidad(entidad);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.documentosGenricList = gral.getDocumentosGenericosLovAll(
						this.limit, this.offset, entidad, this.idempresa);
			}

			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	public List getDocumentosGenricList() {
		return documentosGenricList;
	}

	public void setDocumentosGenricList(List documentosGenricList) {
		this.documentosGenricList = documentosGenricList;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String ocurrencia) {
		this.ocurrencia = ocurrencia;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getPaginaSeleccion() {
		return paginaSeleccion;
	}

	public void setPaginaSeleccion(long paginaSeleccion) {
		this.paginaSeleccion = paginaSeleccion;
	}

	public long getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(long totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/*-----*/

	public String getValidar() {
		return validar;
	}

	public void setValidar(String validar) {
		this.validar = validar;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getVolver() {
		return volver;
	}

	public void setVolver(String volver) {
		this.volver = volver;
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

	// metodos para cada atributo de la entidad
	public BigDecimal getIdentidadesasociables() {
		return identidadesasociables;
	}

	public void setIdentidadesasociables(BigDecimal identidadesasociables) {
		this.identidadesasociables = identidadesasociables;
	}

	public String getEntidadasociable() {
		return entidadasociable;
	}

	public void setEntidadasociable(String entidadasociable) {
		this.entidadasociable = entidadasociable;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCampopk() {
		return campopk;
	}

	public void setCampopk(String campopk) {
		this.campopk = campopk;
	}

	public String getQuerypk() {
		return querypk;
	}

	public void setQuerypk(String querypk) {
		this.querypk = querypk;
	}

	public String getQuerygrilla() {
		return querygrilla;
	}

	public void setQuerygrilla(String querygrilla) {
		this.querygrilla = querygrilla;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public String[][] getColumnsMetaData() {
		return columnsMetaData;
	}

	public void setColumnsMetaData(String[][] columns) {
		this.columnsMetaData = columns;
	}

	public String getCmpCodigo() {
		return cmpCodigo;
	}

	public void setCmpCodigo(String cmpCodigo) {
		this.cmpCodigo = cmpCodigo;
	}

	public String getCmpDescrip() {
		return cmpDescrip;
	}

	public void setCmpDescrip(String cmpDescrip) {
		this.cmpDescrip = cmpDescrip;
	}

}
