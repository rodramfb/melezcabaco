/* 
   javabean para la entidad (Formulario): sascontactos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri May 27 12:30:35 ART 2011 
   
   Para manejar la pagina: sascontactosFrm.jsp
      
 */
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

public class BeanSascontactosFrm implements SessionBean, Serializable {

	private SessionContext context;

	static Logger log = Logger.getLogger(BeanSascontactosFrm.class);

	private String validar = "";

	private BigDecimal idcontacto = new BigDecimal(-1);

	private String descripcion = "";

	private BigDecimal idtipocontacto = new BigDecimal(-1);

	private String tipocontacto = "";

	private BigDecimal idcanalcontacto = new BigDecimal(-1);

	private String canalcontacto = "";

	private BigDecimal idmotivocontacto = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private List listtipo = new ArrayList();

	private List listcanal = new ArrayList();

	private List lismotivos = new ArrayList();

	private BigDecimal idcliente = new BigDecimal(-1);

	private boolean primeraCarga = true;

	private String motivocontacto = "";
	private BigDecimal idAccionContacto = new BigDecimal(-1);
	private String accionContacto = "";
	private BigDecimal idResultadoContacto = new BigDecimal(-1);
	private String resultadoContacto = "";
	private List<String[]> listAcciones = new ArrayList<String[]>();
	private List<String[]> listResultados = new ArrayList<String[]>();
	
	public BeanSascontactosFrm() {
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

				this.mensaje = clientes.sascontactosCreate(this.descripcion,
						this.idtipocontacto, this.idcanalcontacto,
						this.idmotivocontacto, this.idAccionContacto,
						this.idResultadoContacto, this.usuarioalt,
						this.idempresa, this.idcliente);

			} else if (this.accion.equalsIgnoreCase(IAcciones.MODIFICACION)) {

				this.mensaje = clientes.sascontactosUpdate(this.idcontacto,
						this.descripcion, this.idtipocontacto,
						this.idcanalcontacto, this.idmotivocontacto,
						this.idAccionContacto, this.idResultadoContacto,
						this.usuarioact, this.idempresa, this.idcliente);

			} else if (this.accion.equalsIgnoreCase(IAcciones.BAJA)) {

				this.mensaje = clientes.sascontactosDelete(this.idcontacto,
						this.idempresa);

			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	private void handleAccionResultadoIds(boolean toSave) {

		if (toSave) {
			this.idAccionContacto = (idAccionContacto != null && idAccionContacto.longValue() <= 0) ? null : idAccionContacto;
			this.idResultadoContacto = (idResultadoContacto != null && idResultadoContacto.longValue() <= 0) ? null : idResultadoContacto;
		} else {
			this.idAccionContacto = idAccionContacto != null ? idAccionContacto : new BigDecimal(-1);
			this.idResultadoContacto = idResultadoContacto != null ? idResultadoContacto : idResultadoContacto;			
		}
	}

	public void getDatosSascontactos() {
		try {
			Clientes clientes = Common.getClientes();
			List listSascontactos = clientes.getSascontactosPK(this.idcontacto,
					this.idempresa);
			Iterator iterSascontactos = listSascontactos.iterator();
			if (iterSascontactos.hasNext()) {

				String[] uCampos = (String[]) iterSascontactos.next();
				// TODO: Constructores para cada tipo de datos
				this.idcontacto = new BigDecimal(uCampos[0]);
				this.descripcion = uCampos[1];
				this.idtipocontacto = new BigDecimal(uCampos[2]);
				this.listcanal = clientes.getSascanalescontactosLista(
						idtipocontacto, idempresa);
				this.idcanalcontacto = new BigDecimal(uCampos[4]);
				this.idmotivocontacto = new BigDecimal(uCampos[6]);
				this.lismotivos = clientes.getSasmotivoscontactosLista(
						idcanalcontacto, idtipocontacto, idempresa);
				this.idAccionContacto = uCampos[15] != null ? new BigDecimal(uCampos[15]) : new BigDecimal(-1);
				this.listAcciones = clientes.getSasAccionesContactosLista(idtipocontacto, idcanalcontacto, idmotivocontacto, idempresa);
				this.idResultadoContacto = uCampos[17] != null ? new BigDecimal(uCampos[17]) : new BigDecimal(-1);
				this.listAcciones = clientes.getSasResultadosContactosLista(idtipocontacto, idcanalcontacto, idmotivocontacto, idAccionContacto, idempresa);

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}

		} catch (Exception e) {
			log.error("getDatosSascontactos()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			Clientes clientes = Common.getClientes();
			this.listtipo = clientes.getSastiposcontactosAll(250, 0, idempresa);
			if (this.idtipocontacto.longValue() > 0) {

				this.listcanal = clientes.getSascanalescontactosLista(
						idtipocontacto, idempresa);
			}

			if (this.idcanalcontacto.longValue() > 0
					&& idtipocontacto.longValue() > 0) {

				this.lismotivos = clientes.getSasmotivoscontactosLista(
						idcanalcontacto, idtipocontacto, idempresa);
			}

			if (this.idcanalcontacto.longValue() > 0
					&& idtipocontacto.longValue() > 0
					&& idmotivocontacto.longValue() > 0) {

				this.listAcciones = clientes.getSasAccionesContactosLista(
						idtipocontacto, idcanalcontacto, idmotivocontacto,
						idempresa);
				
				System.out.println("Cantidad de acciones: " + listAcciones.size());
			}
			
			if (this.idcanalcontacto.longValue() > 0
					&& idtipocontacto.longValue() > 0
					&& idmotivocontacto.longValue() > 0
					&& idAccionContacto != null 
					&& idAccionContacto.longValue() > 0) {

				this.listResultados = clientes.getSasResultadosContactosLista(
						idtipocontacto, idcanalcontacto, idmotivocontacto,
						idAccionContacto, idempresa);
			}

			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("sascontactosAbm.jsp?idcliente="
						+ this.idcliente);
				return true;
			}

			if (!this.validar.equalsIgnoreCase("")) {

				if (!this.accion.equalsIgnoreCase(IAcciones.BAJA)) {
					// 1. nulidad de campos
					if (descripcion == null) {
						this.mensaje = "No se puede dejar vacio el campo descripcion ";
						return false;
					}
					if (idtipocontacto == null) {
						this.mensaje = "No se puede dejar vacio el campo idtipocontacto ";
						return false;
					}
					if (idcanalcontacto == null) {
						this.mensaje = "No se puede dejar vacio el campo idcanalcontacto ";
						return false;
					}
					// 2. len 0 para campos nulos
					if (descripcion.trim().length() == 0) {
						this.mensaje = "No se puede dejar con longitud 0 el campo descripcion  ";
						return false;
					}

				}

				this.ejecutarSentenciaDML();

			} else {

				if (!this.accion.equalsIgnoreCase(IAcciones.ALTA) && isPrimeraCarga()) {

					getDatosSascontactos();

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
	public BigDecimal getIdcontacto() {
		return idcontacto;
	}

	public void setIdcontacto(BigDecimal idcontacto) {
		this.idcontacto = idcontacto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getIdtipocontacto() {
		return idtipocontacto;
	}

	public void setIdtipocontacto(BigDecimal idtipocontacto) {
		this.idtipocontacto = idtipocontacto;
	}

	public BigDecimal getIdcanalcontacto() {
		return idcanalcontacto;
	}

	public void setIdcanalcontacto(BigDecimal idcanalcontacto) {
		this.idcanalcontacto = idcanalcontacto;
	}

	public BigDecimal getIdmotivocontacto() {
		return idmotivocontacto;
	}

	public void setIdmotivocontacto(BigDecimal idmotivocontacto) {
		this.idmotivocontacto = idmotivocontacto;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getTipocontacto() {
		return tipocontacto;
	}

	public void setTipocontacto(String tipocontacto) {
		this.tipocontacto = tipocontacto;
	}

	public List getListtipo() {
		return listtipo;
	}

	public void setListtipo(List listtipo) {
		this.listtipo = listtipo;
	}

	public List getListcanal() {
		return listcanal;
	}

	public void setListcanal(List listcanal) {
		this.listcanal = listcanal;
	}

	public List getLismotivos() {
		return lismotivos;
	}

	public void setLismotivos(List lismotivos) {
		this.lismotivos = lismotivos;
	}

	public String getCanalcontacto() {
		return canalcontacto;
	}

	public void setCanalcontacto(String canalcontacto) {
		this.canalcontacto = canalcontacto;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public boolean isPrimeraCarga() {
		return primeraCarga;
	}

	public void setPrimeraCarga(boolean primeraCarga) {
		this.primeraCarga = primeraCarga;
	}



	public String getMotivocontacto() {
		return motivocontacto;
	}

	public void setMotivocontacto(String motivocontacto) {
		this.motivocontacto = motivocontacto;
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

	public BigDecimal getIdResultadoContacto() {
		return idResultadoContacto;
	}

	public void setIdResultadoContacto(BigDecimal idResultadoContacto) {
		this.idResultadoContacto = idResultadoContacto;
	}

	public String getResultadoContacto() {
		return resultadoContacto;
	}

	public void setResultadoContacto(String resultadoContacto) {
		this.resultadoContacto = resultadoContacto;
	}

	public List<String[]> getListAcciones() {
		return listAcciones;
	}

	public void setListAcciones(List<String[]> listAcciones) {
		this.listAcciones = listAcciones;
	}

	public List<String[]> getListResultados() {
		return listResultados;
	}

	public void setListResultados(List<String[]> listResultados) {
		this.listResultados = listResultados;
	}

}
