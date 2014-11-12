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

public class BeanConsultaGenericaDocsAsociadosDetalle implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanConsultaGenericaDocsAsociadosDetalle.class);

	private List documentosGenricList = new ArrayList();

	private String validar = "";

	private BigDecimal identidadesasociables = BigDecimal.valueOf(-1);

	private String entidadasociable = "";

	private String descripcion = "";

	private String campopk = "";

	private String querypk = "";

	private String querygrilla = "";

	private BigDecimal pkorigen = new BigDecimal(-1);

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private String[][] columnsMetaData = new String[][] { {} };

	private Hashtable htAsociaciones = new Hashtable();

	private Hashtable htMetaData = new Hashtable();

	private Hashtable htDocumentos = new Hashtable();

	private Hashtable htEntidades = new Hashtable();

	public BeanConsultaGenericaDocsAsociadosDetalle() {
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
		try {

			String entidad = "";
			General gral = Common.getGeneral();
			List listAsociaciones = gral.getAsociacionesXOrigen(this.pkorigen,
					this.idempresa);
			Iterator iterAsociaciones = listAsociaciones.iterator();
			String tipo = "";
			while (iterAsociaciones.hasNext()) {

				
				String[] datos = (String[]) iterAsociaciones.next();
				
				entidad = "(" + datos[4] + ") AS entidad ";
				this.documentosGenricList = gral.getDocumentosGenericosAll(100,
						0, entidad, this.idempresa);
				
				this.columnsMetaData = gral.getQueryEntidadMetadata(entidad,
						idempresa);
				if (tipo.equals(""))
					tipo = datos[2];
				else if (!tipo.equalsIgnoreCase(datos[2])) {

					this.htAsociaciones.put(tipo, this.htDocumentos);
					this.htDocumentos = new Hashtable();
					tipo = datos[2];
				}

				this.htEntidades.put(datos[2], datos[3]);
				this.htMetaData.put(datos[2], this.columnsMetaData);
				this.htDocumentos.put(datos[0], this.documentosGenricList);

			}

			if (!tipo.equals("")) {
					this.htMetaData.put(tipo, this.columnsMetaData);
					this.htAsociaciones.put(tipo, this.htDocumentos);
			} else {
				this.mensaje = "No existen documentos asociados para el registro seleccionado.";
			}

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

	public Hashtable getHtAsociaciones() {
		return htAsociaciones;
	}

	public void setHtAsociaciones(Hashtable htAsociaciones) {
		this.htAsociaciones = htAsociaciones;
	}

	public Hashtable getHtMetaData() {
		return htMetaData;
	}

	public void setHtMetaData(Hashtable htMetaData) {
		this.htMetaData = htMetaData;
	}

	public Hashtable getHtDocumentos() {
		return htDocumentos;
	}

	public void setHtDocumentos(Hashtable htDocumentos) {
		this.htDocumentos = htDocumentos;
	}

	public BigDecimal getPkorigen() {
		return pkorigen;
	}

	public void setPkorigen(BigDecimal pkorigen) {
		this.pkorigen = pkorigen;
	}

	public Hashtable getHtEntidades() {
		return htEntidades;
	}

	public void setHtEntidades(Hashtable htEntidades) {
		this.htEntidades = htEntidades;
	}

}
