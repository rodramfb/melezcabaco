/* 
 javabean para la entidad (Formulario): Cajaferiados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Aug 01 11:33:07 GMT-03:00 2006 
 
 Para manejar la pagina: CajaferiadosFrm.jsp
 
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

public class BeanProveedoresConsultaArribosOCAprobadas implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProveedoresConsultaArribosOCAprobadas.class);

	private String validar = "";

	private String iddeposito="";
	private String deposito="";
	
	private String idestadooc="";
	private String estadooc="";
	
    private String fechadesde ="";
    private String fechahasta ="";

	private java.sql.Date fecha_fer = new java.sql.Date(Common.initObjectTime());

	private String fecha_ferStr = Common.initObjectTimeStr();

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List MovimientosList = new ArrayList();
	
	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";
	
	//20110818 - CAMI - Informes de compras ------------>
	
	private boolean flag = true;
	
	private int totCol = 0;
	
	//<--------------------------------------------------
	
	

	public BeanProveedoresConsultaArribosOCAprobadas() {
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
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}


	public boolean ejecutarValidacion() {
		try {
			Proveedores Consulta = Common.getProveedores();	
			this.MovimientosList   = Consulta.getArriboOCAprobadas(this.idempresa);
			//20110818 - CAMI - Informes de compras --------->
			
			if (this.MovimientosList.size()<1)
			{
				this.flag = false;
			}
			if (flag)
			{
				General general = Common.getGeneral();
				general.setArchivo(this.MovimientosList,new BigDecimal(totCol), "ArriboOCAprobadas");
			
			}
			//<--------------------------------------------------
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




	public Date getFecha_fer() {
		return fecha_fer;
	}

	public void setFecha_fer(java.sql.Date fecha_fer) {
		this.fecha_fer = fecha_fer;
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

	public String getFecha_ferStr() {
		return fecha_ferStr;
	}

	public void setFecha_ferStr(String fecha_ferStr) {
		this.fecha_ferStr = fecha_ferStr;
		this.fecha_fer = (java.sql.Date) Common.setObjectToStrOrTime(
				fecha_ferStr, "strToJSDate");
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getiddeposito() {
		return iddeposito;
	}

	public void setiddeposito(String iddeposito) {
		this.iddeposito = iddeposito;
	}


	

	public String getdeposito() {
		return deposito;
	}

	public void setdeposito(String deposito) {
		this.deposito = deposito;
	}

	public String getFechadesde() {
		return fechadesde;
	}

	public void setFechadesde(String fechadesde) {
		this.fechadesde = fechadesde;
	}

	public String getFechahasta() {
		return fechahasta;
	}

	public void setFechahasta(String fechahasta) {
		this.fechahasta = fechahasta;
	}

	
	

	
	public List getMovimientosList() {
		return MovimientosList;
	}

	public void setMovimientosList(List movimientosList) {
		MovimientosList = movimientosList;
	}

	public String getIdestadooc() {
		return idestadooc;
	}

	public void setIdestadooc(String idestadooc) {
		this.idestadooc = idestadooc;
	}

	public String getEstadooc() {
		return estadooc;
	}

	public void setEstadooc(String estadooc) {
		this.estadooc = estadooc;
	}
	//20110818 - CAMI - Informes de compras --------->
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getTotCol() {
		return totCol;
	}

	public void setTotCol(int totCol) {
		this.totCol = totCol;
	}
	//<---------------------------------------------
	
	
	}
