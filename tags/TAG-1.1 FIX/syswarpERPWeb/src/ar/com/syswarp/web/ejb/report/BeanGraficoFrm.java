/* 
 javabean para la entidad (Formulario): grafico
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Mon Jul 03 14:29:38 GMT-03:00 2006 
 
 Para manejar la pagina: graficoFrm.jsp
 
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

public class BeanGraficoFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanGraficoFrm.class);

	private String validar = "";

	private BigDecimal idgrafico = BigDecimal.valueOf(-1);

	private String grafico = "";

	private String query_consulta = "";

	private BigDecimal idtipografico = BigDecimal.valueOf(-1);

	private BigDecimal iddatasource = BigDecimal.valueOf(-1);

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

	private List tipoGraficoList = new ArrayList();

	private Hashtable htTG = new Hashtable();

	private Iterator iterTG = null;

	public BeanGraficoFrm() {
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
			Report grafico = Common.getReport();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = grafico.graficoCreate(this.grafico,
						this.query_consulta, this.idtipografico,
						this.iddatasource, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = grafico.graficoUpdate(this.idgrafico,
						this.grafico, this.query_consulta, this.idtipografico,
						this.iddatasource, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = grafico.graficoDelete(this.idgrafico);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosGrafico() {
		try {
			Report grafico = Common.getReport();
			List listGrafico = grafico.getGraficoPK(this.idgrafico);
			Iterator iterGrafico = listGrafico.iterator();
			if (iterGrafico.hasNext()) {
				String[] uCampos = (String[]) iterGrafico.next();
				// TODO: Constructores para cada tipo de datos
				this.idgrafico = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.grafico = uCampos[1];
				this.query_consulta = uCampos[2];
				this.idtipografico = BigDecimal.valueOf(Long
						.parseLong(uCampos[3]));
				this.iddatasource = BigDecimal.valueOf(Long
						.parseLong(uCampos[4]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosGrafico()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			Report reporting = Common.getReport();
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("graficoAbm.jsp");
				return true;
			}

			this.datasourceList = reporting.getDatasourcesAll();
			this.iterDT = this.datasourceList.iterator();
			while (iterDT.hasNext()) {
				String[] dtCampos = (String[]) iterDT.next();
				this.htDT.put(dtCampos[0], dtCampos[1]);
			}

			this.tipoGraficoList = reporting.getTipoGraficosAll();
			this.iterTG = this.tipoGraficoList.iterator();
			while (iterTG.hasNext()) {
				String[] tgCampos = (String[]) iterTG.next();
				this.htTG.put(tgCampos[0], tgCampos[1]);
			}

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (grafico == null) {
						this.mensaje = "No se puede dejar vacio el campo grafico ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (grafico.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo grafico  ";
						return false;
					}
					if (this.iddatasource.longValue() <= 0) {
						this.mensaje = "Debe seleccionar un Data Source.  ";
						return false;
					}

					if (this.idtipografico.longValue() <= 0) {
						this.mensaje = "Debe seleccionar un Tipo de Gr�fico.  ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosGrafico();
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
	public BigDecimal getIdgrafico() {
		return idgrafico;
	}

	public void setIdgrafico(BigDecimal idgrafico) {
		this.idgrafico = idgrafico;
	}

	public String getGrafico() {
		return grafico;
	}

	public void setGrafico(String grafico) {
		this.grafico = grafico;
	}

	public String getQuery_consulta() {
		return query_consulta;
	}

	public void setQuery_consulta(String query_consulta) {
		this.query_consulta = query_consulta;
	}

	public BigDecimal getIdtipografico() {
		return idtipografico;
	}

	public void setIdtipografico(BigDecimal idtipografico) {
		this.idtipografico = idtipografico;
	}

	public BigDecimal getIddatasource() {
		return iddatasource;
	}

	public void setIddatasource(BigDecimal iddatasource) {
		this.iddatasource = iddatasource;
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

	public Hashtable getHtTG() {
		return htTG;
	}

	public void setHtTG(Hashtable htTG) {
		this.htTG = htTG;
	}
}
