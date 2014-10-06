/* 
 javabean para la entidad (Formulario): vproveedoOcEstado
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Apr 20 15:27:29 ART 2007 
 
 Para manejar la pagina: vproveedoOcEstadoFrm.jsp
 
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
import java.sql.Timestamp;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanVproveedoOcEstadoFrm implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanVproveedoOcEstadoFrm.class);

	private String validar = "";

	private BigDecimal id_oc_cabe = new BigDecimal(0);

	private BigDecimal idestadooc = new BigDecimal(0);

	private String estadooc = "";

	private BigDecimal idproveedor = new BigDecimal(0);

	private String razon_social = "";

	private Timestamp fechaoc;

	private BigDecimal idcondicionpago = new BigDecimal(0);

	private String condicion = "";

	private BigDecimal idmoneda = new BigDecimal(0);

	private String moneda = "";

	private BigDecimal idtipoiva = new BigDecimal(0);

	private String tipoiva;

	private BigDecimal idgrupooc = new BigDecimal(0);

	private String grupooc;

	private BigDecimal totaliva = new BigDecimal(0);

	private String observaciones = "";

	private BigDecimal codigo_dt = new BigDecimal(0);

	private String descrip_dt = "";

	private java.sql.Date fecha_entrega_prevista = null;

	private String fecha_entrega_previstaStr = Common.initObjectTimeStr();

	private List listaEstadosOc = new ArrayList();

	private String usuarioalt = "";

	private String usuarioact = "";

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listaOCAsociadas = new ArrayList();

	public BeanVproveedoOcEstadoFrm() {
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
			Proveedores vproveedoOcEstado = Common.getProveedores();
			if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = vproveedoOcEstado.proveedoOcCabeEstadoUpdate(
						this.id_oc_cabe, this.idestadooc, this.idgrupooc,
						this.usuarioact, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosVproveedoOcEstado() {
		try {
			Proveedores vproveedoOcEstado = Common.getProveedores();
			List listVproveedoOcEstado = vproveedoOcEstado
					.getVproveedoOcEstadoPK(this.id_oc_cabe, this.idempresa);
			Iterator iterVproveedoOcEstado = listVproveedoOcEstado.iterator();
			if (iterVproveedoOcEstado.hasNext()) {
				String[] uCampos = (String[]) iterVproveedoOcEstado.next();
				// TODO: Constructores para cada tipo de datos
				this.id_oc_cabe = new BigDecimal(uCampos[0]);
				if (this.validar.equals(""))
					this.idestadooc = new BigDecimal(uCampos[1]);
				this.estadooc = uCampos[2];
				this.idproveedor = new BigDecimal(uCampos[3]);
				this.razon_social = uCampos[4];
				this.fechaoc = Timestamp.valueOf(uCampos[5]);
				this.idcondicionpago = new BigDecimal(uCampos[6]);
				this.condicion = uCampos[7];
				this.idmoneda = new BigDecimal(uCampos[8]);
				this.moneda = uCampos[9];
				this.idtipoiva = new BigDecimal(uCampos[10]);
				this.tipoiva = uCampos[11];
				this.idgrupooc = new BigDecimal(uCampos[12] == null ? "0"
						: uCampos[12]);
				this.grupooc = uCampos[13].trim().equals("") ? "Sin Grupo Asociado."
						: uCampos[13];
				this.totaliva = new BigDecimal(uCampos[14]);
				this.observaciones = uCampos[15];
		
				this.fecha_entrega_prevista = (java.sql.Date) Common
						.setObjectToStrOrTime(uCampos[16], "StrYMDToJSDate");
				
				this.codigo_dt = new BigDecimal(uCampos[17] == null ? "0"
						: uCampos[17]);
				
				this.descrip_dt = uCampos[18] == null ? "Sin Deposito Asociado."
						: uCampos[18];
				
				this.listaEstadosOc = vproveedoOcEstado
						.getProveedo_oc_estadosAll(50, 0, this.idempresa);
				this.listaOCAsociadas = vproveedoOcEstado
						.getVproveedoOcEstadoPKGrupo(this.id_oc_cabe,
								this.idgrupooc, this.idempresa);

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosVproveedoOcEstado()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("vproveedoOcEstadoAbm.jsp");
				return true;
			}

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					if (this.idestadooc.compareTo(new BigDecimal(0)) == 0) {
						this.mensaje = "Debe seleccionar un nuevo estado.";
					} else
						this.ejecutarSentenciaDML();

				}
			}

			getDatosVproveedoOcEstado();

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
	public BigDecimal getId_oc_cabe() {
		return id_oc_cabe;
	}

	public void setId_oc_cabe(BigDecimal id_oc_cabe) {
		this.id_oc_cabe = id_oc_cabe;
	}

	public BigDecimal getIdestadooc() {
		return idestadooc;
	}

	public void setIdestadooc(BigDecimal idestadooc) {
		this.idestadooc = idestadooc;
	}

	public String getEstadooc() {
		return estadooc;
	}

	public void setEstadooc(String estadooc) {
		this.estadooc = estadooc;
	}

	public BigDecimal getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(BigDecimal idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public Timestamp getFechaoc() {
		return fechaoc;
	}

	public void setFechaoc(Timestamp fechaoc) {
		this.fechaoc = fechaoc;
	}

	public BigDecimal getIdcondicionpago() {
		return idcondicionpago;
	}

	public void setIdcondicionpago(BigDecimal idcondicionpago) {
		this.idcondicionpago = idcondicionpago;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public BigDecimal getIdmoneda() {
		return idmoneda;
	}

	public void setIdmoneda(BigDecimal idmoneda) {
		this.idmoneda = idmoneda;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getIdtipoiva() {
		return idtipoiva;
	}

	public void setIdtipoiva(BigDecimal idtipoiva) {
		this.idtipoiva = idtipoiva;
	}

	public String getTipoiva() {
		return tipoiva;
	}

	public void setTipoiva(String tipoiva) {
		this.tipoiva = tipoiva;
	}

	public BigDecimal getIdgrupooc() {
		return idgrupooc;
	}

	public void setIdgrupooc(BigDecimal idgrupooc) {
		this.idgrupooc = idgrupooc;
	}

	public String getGrupooc() {
		return grupooc;
	}

	public void setGrupooc(String grupooc) {
		this.grupooc = grupooc;
	}

	public BigDecimal getTotaliva() {
		return totaliva;
	}

	public void setTotaliva(BigDecimal totaliva) {
		this.totaliva = totaliva;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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

	public List getListaEstadosOc() {
		return listaEstadosOc;
	}

	public void setListaEstadosOc(List listaEstadosOc) {
		this.listaEstadosOc = listaEstadosOc;
	}

	public List getListaOCAsociadas() {
		return listaOCAsociadas;
	}

	public void setListaOCAsociadas(List listaOCAsociadas) {
		this.listaOCAsociadas = listaOCAsociadas;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public String getDescrip_dt() {
		return descrip_dt;
	}

	public void setDescrip_dt(String descrip_dt) {
		this.descrip_dt = descrip_dt;
	}

	public java.sql.Date getFecha_entrega_prevista() {
		return fecha_entrega_prevista;
	}

	public void setFecha_entrega_prevista(java.sql.Date fecha_entrega_prevista) {
		this.fecha_entrega_prevista = fecha_entrega_prevista;
	}

	public String getFecha_entrega_previstaStr() {
		return fecha_entrega_previstaStr;
	}

	public void setFecha_entrega_previstaStr(String fecha_entrega_previstaStr) {
		this.fecha_entrega_previstaStr = fecha_entrega_previstaStr;
		this.fecha_entrega_prevista = (java.sql.Date) Common
				.setObjectToStrOrTime(fecha_entrega_previstaStr, "strToJSDate");
	}

}
