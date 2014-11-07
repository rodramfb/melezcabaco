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

public class BeanProveedoConsultaSubdiarioCompras implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger
			.getLogger(BeanProveedoConsultaSubdiarioCompras.class);

	private String validar = "";

	private BigDecimal idproveedordesde = new BigDecimal(-1);
	private BigDecimal idproveedorhasta = new BigDecimal(-1);
	private String dproveedordesde = "";
	private String dproveedorhasta = "";
	private String fechadesde = "";
	private String fechahasta = "";

	private java.sql.Date fecha_fer = new java.sql.Date(Common.initObjectTime());

	private String fecha_ferStr = Common.initObjectTimeStr();

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List SaldoAnteriorList = new ArrayList();
	private List MovimientosList = new ArrayList();
	private List SaldoFinalList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	// 20110818 - CAMI - Informes de compras --------------->

	private boolean flag = true;

	private int totCol = 0;

	// <-----------------------------------------------------

	// 20111109 - CAMI - Arreglos generales

	
	private List anio = new ArrayList();
	
	private GregorianCalendar calen = new GregorianCalendar();
	private String anio_sel  = calen.get(Calendar.YEAR) + "";
	private String mes = calen.get(Calendar.MONTH)+1 + "";
	
	// <-----------------------------------

	public BeanProveedoConsultaSubdiarioCompras() {
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
			Proveedores Consulta = Common.getProveedores();

			// this.SaldoAnteriorList =
			// Consulta.getMovArticuloDepositoSaldoAnterior(this.codigo_st,
			// this.iddeposito, this.fechadesde, this.fechahasta, idempresa);
			// this.MovimientosList =
			// Consulta.getProveedoresSubdiarioCompras(this.idproveedordesde,
			// this.idproveedorhasta, this.fechadesde, this.fechahasta,
			// idempresa);
			this.MovimientosList = Consulta.getProveedoresSubdiarioCompras(
					(java.sql.Date) Common.setObjectToStrOrTime(
							this.fechadesde, "STRToJSDate"),
					(java.sql.Date) Common.setObjectToStrOrTime(
							this.fechahasta, "STRToJSDate"), idempresa);
			// this.SaldoFinalList =
			// Consulta.getMovArticuloDepositoSaldoFinal(this.codigo_st,
			// this.iddeposito, this.fechadesde, this.fechahasta, idempresa);
			// 20110818 - CAMI - Informes de compras --------------->

			if (this.MovimientosList.size() < 1) {
				this.flag = false;

			}
			if (this.flag) {

				General general = Common.getGeneral();

				general.setArchivo(this.MovimientosList,
						new BigDecimal(totCol), "SubDiarioCompras");
			}
			// <-------------------------------------------------------

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			String anioList = "20";
			boolean first=false;
			Calendar cal = new GregorianCalendar();
			for (int i = 0 ; i<=9; i++)
			{
				if (i<9)
				{
					anioList = anioList +"0" + i;
					if (first)
					{
						anioList = "201";
						anioList = anioList + i;
					}
				}
				if (i==9)
				{
					anioList = anioList +"0" + i;
					
					i=-1;
					if (first == false)
					{
						first = true;
					}else{
						break;
					}
				}
				this.anio.add(anioList);
				anioList = "20";
			}
			this.anio.add("2019");
			this.anio.add("2020");
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("#"); // no tiene volver
				return true;
			}
			
			if (!this.accion.equalsIgnoreCase("")) {
				
				
				boolean esBisiesto = ((GregorianCalendar) cal).isLeapYear(Integer.parseInt(this.anio_sel));
				switch (Integer.parseInt(mes)) {
				case 1:
					this.fechadesde = "01/01/" + this.anio_sel;
					this.fechahasta = "31/01/" + this.anio_sel;
					break;
				case 2:
					this.fechadesde = "01/02/" + this.anio_sel;
					if (esBisiesto)
					{
						this.fechahasta = "29/02/" + this.anio_sel;
					}else{
						this.fechahasta ="28/02/" + this.anio_sel;
					}
					break;
				case 3:
					this.fechadesde = "01/03/" + this.anio_sel;
					this.fechahasta = "31/03/" + this.anio_sel;
					break;
				case 4:
					this.fechadesde = "01/04/" + this.anio_sel;
					this.fechahasta = "30/04/" + this.anio_sel;
					break;
				case 5:
					this.fechadesde = "01/05/" + this.anio_sel;
					this.fechahasta = "31/05/" + this.anio_sel;
					break;
				case 6:
					this.fechadesde = "01/06/" + this.anio_sel;
					this.fechahasta = "30/06/" + this.anio_sel;
					break;
				case 7:
					this.fechadesde = "01/07/" + this.anio_sel;
					this.fechahasta = "31/07/" + this.anio_sel;
					break;
				case 8:
					this.fechadesde = "01/08/" + this.anio_sel;
					this.fechahasta = "31/08/" + this.anio_sel;
					break;
				case 9:
					this.fechadesde = "01/09/" + this.anio_sel;
					this.fechahasta = "30/09/" + this.anio_sel;
					break;
				case 10:
					this.fechadesde = "01/10/" + this.anio_sel;
					this.fechahasta = "31/10/" + this.anio_sel;
					break;
				case 11:
					this.fechadesde = "01/11/" + this.anio_sel;
					this.fechahasta = "30/11/" + this.anio_sel;
					break;
				case 12:
					this.fechadesde = "01/12/" + this.anio_sel;
					this.fechahasta = "31/12/" + this.anio_sel;
					break;
				}
				this.ejecutarSentenciaDML();
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

	public List getSaldoAnteriorList() {
		return SaldoAnteriorList;
	}

	public void setSaldoAnteriorList(List saldoAnteriorList) {
		SaldoAnteriorList = saldoAnteriorList;
	}

	public List getMovimientosList() {
		return MovimientosList;
	}

	public void setMovimientosList(List movimientosList) {
		MovimientosList = movimientosList;
	}

	public List getSaldoFinalList() {
		return SaldoFinalList;
	}

	public void setSaldoFinalList(List saldoFinalList) {
		SaldoFinalList = saldoFinalList;
	}

	public BigDecimal getIdproveedordesde() {
		return idproveedordesde;
	}

	public void setIdproveedordesde(BigDecimal idproveedordesde) {
		this.idproveedordesde = idproveedordesde;
	}

	public BigDecimal getIdproveedorhasta() {
		return idproveedorhasta;
	}

	public void setIdproveedorhasta(BigDecimal idproveedorhasta) {
		this.idproveedorhasta = idproveedorhasta;
	}

	public String getDproveedordesde() {
		return dproveedordesde;
	}

	public void setDproveedordesde(String dproveedordesde) {
		this.dproveedordesde = dproveedordesde;
	}

	public String getDproveedorhasta() {
		return dproveedorhasta;
	}

	public void setDproveedorhasta(String dproveedorhasta) {
		this.dproveedorhasta = dproveedorhasta;
	}

	// 20110818- CAMI - Informes de compras ----------------->
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

	// <--------------------------------------------------
	// 20111109- CAMI - Arreglos generales

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List getAnio() {
		return anio;
	}

	public void setAnio(List anio) {
		this.anio = anio;
	}

	public String getAnio_sel() {
		return anio_sel;
	}

	public void setAnio_sel(String anio_sel) {
		this.anio_sel = anio_sel;
	}


	// <--------------------------------------------------

}
