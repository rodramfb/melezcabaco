/* 
 javabean para la entidad (Formulario): parametros
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Jul 03 17:43:40 GMT-03:00 2006 
 
 Para manejar la pagina: parametrosFrm.jsp
 
 */
package ar.com.syswarp.web.ejb.report;

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
import ar.com.syswarp.api.Strings;

public class BeanParametrosFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanParametrosFrm.class);

	private String validar = "";

	private BigDecimal idparametro = BigDecimal.valueOf(-1);

	private String parametro = "";

	private String descripcion = "";

	private BigDecimal idtipoparametro = BigDecimal.valueOf(-1);

	private String validacion_query = "";

	private BigDecimal iddatasource = BigDecimal.valueOf(-1);

	private String obligatorio = "";

	private BigDecimal orden = BigDecimal.valueOf(-1);

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List datasourceList = new ArrayList();

	private Hashtable htDT = new Hashtable();

	private Iterator iterDT = null;

	private List tipoParametroList = new ArrayList();

	private Hashtable htTP = new Hashtable();

	private Iterator iterTP = null;

	public BeanParametrosFrm() {
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

	public void ejecutarSentenciaDML() {
		try {
			Report parametros = Common.getReport();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = parametros.parametrosCreate(this.parametro,
						this.descripcion, this.idtipoparametro,
						this.validacion_query, this.iddatasource,
						this.obligatorio, this.orden, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = parametros.parametrosUpdate(this.idparametro,
						this.parametro, this.descripcion, this.idtipoparametro,
						this.validacion_query, this.iddatasource,
						this.obligatorio, this.orden, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = parametros.parametrosDelete(this.idparametro);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosParametros() {
		try {
			Report parametros = Common.getReport();
			List listParametros = parametros.getParametrosPK(this.idparametro);
			Iterator iterParametros = listParametros.iterator();
			if (iterParametros.hasNext()) {
				String[] uCampos = (String[]) iterParametros.next();
				// TODO: Constructores para cada tipo de datos
				this.idparametro = BigDecimal.valueOf(Long
						.parseLong(uCampos[0]));
				this.parametro = uCampos[1];
				this.descripcion = uCampos[2];
				this.idtipoparametro = BigDecimal.valueOf(Long
						.parseLong(uCampos[3]));
				this.validacion_query = uCampos[4];
				this.iddatasource = BigDecimal.valueOf(Long
						.parseLong(uCampos[5]));
				this.obligatorio = uCampos[6];
				this.orden = BigDecimal.valueOf(Long.parseLong(uCampos[7]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosParametros()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		Report reporting = Common.getReport();
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("parametrosAbm.jsp");
				return true;
			}

			this.datasourceList = reporting.getDatasourcesAll();
			this.iterDT = this.datasourceList.iterator();
			while (iterDT.hasNext()) {
				String[] dtCampos = (String[]) iterDT.next();
				this.htDT.put(dtCampos[0], dtCampos[1]);
			}

			this.tipoParametroList = reporting.getTipoParametrosAll();
			this.iterTP = this.tipoParametroList.iterator();
			while (iterTP.hasNext()) {
				String[] tpCampos = (String[]) iterTP.next();
				this.htTP.put(tpCampos[0], tpCampos[1]);
			}

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (parametro == null) {
						this.mensaje = "No se puede dejar vacio el campo parametro ";
						return false;
					}

					if (descripcion == null) {
						this.mensaje = "No se puede dejar vacio el campo descripcion ";
						return false;
					}
					if (idtipoparametro == null) {
						this.mensaje = "Es necesario seleccionar un Tipo de Parametro. ";
						return false;
					}
					if (iddatasource == null) {
						this.mensaje = "Es necesario seleccionar un Data Source. ";
						return false;
					}

					if (idtipoparametro.longValue() < 0) {
						this.mensaje = "Es necesario seleccionar un Tipo de Parametro. ";
						return false;
					}
					if (iddatasource.longValue() < 0) {
						this.mensaje = "Es necesario seleccionar un Data Source. ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (parametro.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo parametro.  ";
						return false;
					}

					if (parametro.length() < 3) {
						this.mensaje = "Longitud del campo parametro debe ser minimamente de tres caracteres: #p#. ";
						return false;
					}
					if (parametro.charAt(0) != '#'
							|| parametro.charAt(parametro.length() - 1) != '#') {
						this.mensaje = "El formato del campo parametro debe ser: #parametro#. ";
						return false;
					}

					if ((parametro.subSequence(1, parametro.length() - 1))
							.toString().indexOf('#') > -1) {
						this.mensaje = "El formato del campo parametro debe ser: #parametro#. "
								.toString();
						return false;
					}

					if (!Strings.esAlfabetico(parametro.substring(1, parametro
							.length() - 1))) {
						this.mensaje = "El formato del campo parametro debe ser: #parametro# y no puede contener caracteres numericos ni especiales, excepto #. ";
						return false;
					}

					if (descripcion.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo descripcion.  ";
						return false;
					}
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosParametros();
				}
			}
		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

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
	public BigDecimal getIdparametro() {
		return idparametro;
	}

	public void setIdparametro(BigDecimal idparametro) {
		this.idparametro = idparametro;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getIdtipoparametro() {
		return idtipoparametro;
	}

	public void setIdtipoparametro(BigDecimal idtipoparametro) {
		this.idtipoparametro = idtipoparametro;
	}

	public String getValidacion_query() {
		return validacion_query;
	}

	public void setValidacion_query(String validacion_query) {
		this.validacion_query = validacion_query;
	}

	public BigDecimal getIddatasource() {
		return iddatasource;
	}

	public void setIddatasource(BigDecimal iddatasource) {
		this.iddatasource = iddatasource;
	}

	public String getObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(String obligatorio) {
		this.obligatorio = obligatorio;
	}

	public BigDecimal getOrden() {
		return orden;
	}

	public void setOrden(BigDecimal orden) {
		this.orden = orden;
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

	public Hashtable getHtDT() {
		return htDT;
	}

	public void setHtDT(Hashtable htDT) {
		this.htDT = htDT;
	}

	public Hashtable getHtTP() {
		return htTP;
	}

	public void setHtTP(Hashtable htTP) {
		this.htTP = htTP;
	}
}
