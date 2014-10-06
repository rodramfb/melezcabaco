package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ar.com.syswarp.api.Common;
import ar.com.syswarp.api.IAcciones;
import ar.com.syswarp.ejb.Clientes;

public class BeanSasAccionesContactosFrm implements SessionBean, Serializable {

	private static final long serialVersionUID = -8352464200133707730L;

	static Logger log = Logger.getLogger(BeanSasAccionesContactosFrm.class);

	@SuppressWarnings("unused")
	private SessionContext context;

	private BigDecimal idTipoContacto = new BigDecimal(-1);
	private String tipoContacto = "";
	private BigDecimal idCanalContacto = new BigDecimal(-1);
	private String canalContacto = "";
	private BigDecimal idMotivoContacto = new BigDecimal(-1);
	private String motivoContacto = "";
	private BigDecimal idAccionContacto = new BigDecimal(-1);
	private String accionContacto = "";
	private BigDecimal idEmpresa = new BigDecimal(-1);

	private List listTipos = new ArrayList();
	private List listCanales = new ArrayList();
	private List listMotivos = new ArrayList();

	private String usuarioAlt;
	private String usuarioAct;
	private String validar = "";
	private String mensaje = "";
	private String volver = "";

	private HttpServletRequest request;
	private HttpServletResponse response;

	private String accion = "";

	public BeanSasAccionesContactosFrm() {
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

			Clientes clientes = Common.getClientes();
		
			if (this.accion.equalsIgnoreCase(IAcciones.ALTA)) {
	
				this.mensaje = clientes.sasAccionesContactosCreate(
						this.accionContacto, this.idTipoContacto,
						this.idCanalContacto, this.idMotivoContacto,
						this.usuarioAlt, this.idEmpresa);
			} else if (this.accion.equalsIgnoreCase(IAcciones.MODIFICACION)) {

				this.mensaje = clientes.sasAccionesContactosUpdate(
						this.idAccionContacto, this.accionContacto,
						this.idTipoContacto, this.idCanalContacto,
						this.idMotivoContacto, this.usuarioAct, this.idEmpresa);
			} else if (this.accion.equalsIgnoreCase(IAcciones.BAJA)) {

				this.mensaje = clientes.sasAccionesContactosDelete(
						this.idAccionContacto, this.idEmpresa);
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosSasAccionesContactos() {

		try {

			Clientes clientes = Common.getClientes();

			List listSasAccionesContactos = clientes.getSasAccionesContactosPK(
					this.idAccionContacto, this.idEmpresa);
			Iterator iterSasAccionesContactos = listSasAccionesContactos
					.iterator();

			if (iterSasAccionesContactos.hasNext()) {

				String[] uCampos = (String[]) iterSasAccionesContactos.next();

				// TODO: Constructores para cada tipo de datos
				this.idAccionContacto = new BigDecimal(uCampos[0]);
				this.accionContacto = uCampos[1];
				this.idTipoContacto = new BigDecimal(uCampos[2]);
				this.listCanales = clientes.getSascanalescontactosLista(
						idTipoContacto, idEmpresa);
				this.idCanalContacto = new BigDecimal(uCampos[4]);
				this.idMotivoContacto = new BigDecimal(uCampos[6]);
				this.listMotivos = clientes.getSasmotivoscontactosLista(
						idCanalContacto, idTipoContacto, idEmpresa);

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosSasAccionesContactos()" + e);
		}
	}

	public boolean ejecutarValidacion() {

		try {

			Clientes clientes = Common.getClientes();

			this.listTipos = clientes.getSastiposcontactosAll(250, 0, idEmpresa);
//			System.out.println("idEmpresa: " + idEmpresa);
//			System.out.println("listTipos: " + listTipos);
			if (this.idTipoContacto.longValue() > 0) {

				this.listCanales = clientes.getSascanalescontactosLista(
						idTipoContacto, idEmpresa);
//				System.out.println("idTipoContacto entrada: " + idTipoContacto);
//				System.out.println("listCanales entrada: " + listCanales);
			}

			if (this.idCanalContacto.longValue() > 0
					&& idTipoContacto.longValue() > 0) {

				this.listMotivos = clientes.getSasmotivoscontactosLista(
						idCanalContacto, idTipoContacto, idEmpresa);
//				System.out.println("idCanalContacto: " + idCanalContacto);
//				System.out.println("listMotivos: " + listMotivos);
			}

//			if (this.idCanalContacto.longValue() > 0
//					&& idTipoContacto.longValue() > 0 
//					&& idCanalContacto.longValue() > 0) {
//				System.out.println("idMotivoContacto: " + idMotivoContacto);
//			}
			
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("sasAccionesContactosAbm.jsp?");
				return true;
			}
			
			if (!this.validar.equalsIgnoreCase("")) {

				if (!this.accion.equalsIgnoreCase(IAcciones.BAJA)) {

					// 1. nulidad de campos
					if (accionContacto == null) {
						this.mensaje = "No se puede dejar vacio el campo accioncontacto ";
						return false;
					}
					if (idTipoContacto == null) {
						this.mensaje = "No se puede dejar vacio el campo idtipocontacto ";
						return false;
					}
					if (idCanalContacto == null) {
						this.mensaje = "No se puede dejar vacio el campo idcanalcontacto ";
						return false;
					}
					if (idMotivoContacto == null) {
						this.mensaje = "No se puede dejar vacio el campo idmotivocontacto ";
						return false;
					}
					
					// 2. len 0 para campos nulos
					if (accionContacto.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo accioncontacto  ";
						return false;
					}
					if (idTipoContacto.longValue() < 0) {
						this.mensaje = "Debe seleccionar el tipo de contacto";
						return false;
					}
					if (idCanalContacto.longValue() < 0) {
						this.mensaje = "Debe seleccionar el canal de contacto";
						return false;
					}
					if (idMotivoContacto.longValue() < 0) {
						this.mensaje = "Debe seleccionar el motivo del contacto";
						return false;
					}
					
					// 2. len 0 para campos nulos
				}

				this.ejecutarSentenciaDML();

			} else {

//				System.out.println("Entra por else");
				if (!this.accion.equalsIgnoreCase(IAcciones.ALTA)) {
//					System.out.println("Va a obtener los datos de las acciones");
					getDatosSasAccionesContactos();
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
	public BigDecimal getIdTipoContacto() {
		return idTipoContacto;
	}

	public void setIdTipoContacto(BigDecimal idTipoContacto) {
		this.idTipoContacto = idTipoContacto;
	}

	public String getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(String tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	public BigDecimal getIdCanalContacto() {
		return idCanalContacto;
	}

	public void setIdCanalContacto(BigDecimal idCanalContacto) {
		this.idCanalContacto = idCanalContacto;
	}

	public String getCanalContacto() {
		return canalContacto;
	}

	public void setCanalContacto(String canalContacto) {
		this.canalContacto = canalContacto;
	}

	public BigDecimal getIdMotivoContacto() {
		return idMotivoContacto;
	}

	public void setIdMotivoContacto(BigDecimal idMotivoContacto) {
		this.idMotivoContacto = idMotivoContacto;
	}

	public String getMotivoContacto() {
		return motivoContacto;
	}

	public void setMotivoContacto(String motivoContacto) {
		this.motivoContacto = motivoContacto;
	}

	public BigDecimal getIdAccionContacto() {
		return idAccionContacto;
	}

	public void setIdAccionContacto(BigDecimal idAccionContacto) {
		this.idAccionContacto = idAccionContacto;
	}

	public String getAccionContacto() {
		return accionContacto;
	}

	public void setAccionContacto(String accionContacto) {
		this.accionContacto = accionContacto;
	}

	public BigDecimal getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(BigDecimal idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public List getListTipos() {
		return listTipos;
	}

	public void setListTipos(List listTipos) {
		this.listTipos = listTipos;
	}

	public List getListCanales() {
		return listCanales;
	}

	public void setListCanales(List listCanales) {
		this.listCanales = listCanales;
	}

	public List getListMotivos() {
		return listMotivos;
	}

	public void setListMotivos(List listMotivos) {
		this.listMotivos = listMotivos;
	}

	public String getUsuarioAlt() {
		return usuarioAlt;
	}

	public void setUsuarioAlt(String usuarioAlt) {
		this.usuarioAlt = usuarioAlt;
	}

	public String getUsuarioAct() {
		return usuarioAct;
	}

	public void setUsuarioAct(String usuarioAct) {
		this.usuarioAct = usuarioAct;
	}

}
