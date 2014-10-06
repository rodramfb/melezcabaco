/* 
 javabean para la entidad (Formulario): clientesestados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Fri Mar 02 11:31:57 GMT-03:00 2007 
 
 Para manejar la pagina: clientesestadosFrm.jsp
 
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

public class BeanClientesDocumentosUpdFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClientesDocumentosUpdFrm.class);

	private String validar = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List ListTiposDoc = new ArrayList();

	private BigDecimal idtipodocumento = new BigDecimal(-1);

	private String nrodocumento = "";

	private String nrodocumentoActualCliente = "";

	private String nrodocumentoActualPrecarga = "";

	private BigDecimal idtipodocumentoActualCliente = new BigDecimal(-1);

	private String tipodocumentoActualCliente = "";

	private String tipodocumentoActualPrecarga = "";

	private BigDecimal idtipodocumentoActualPrecarga = new BigDecimal(-1);

	private String idcliente = "";

	private String cliente = "";

	public BeanClientesDocumentosUpdFrm() {
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

			this.mensaje = Common.getClientes()
					.clientesClientesAndClientesPrecargaUpdateDocumento(
							new BigDecimal(this.idcliente),
							this.idtipodocumento,
							new BigDecimal(this.nrodocumento), idempresa,
							usuarioact);
			
			if(Common.setNotNull(this.mensaje).equalsIgnoreCase("OK")){
				this.mensaje = "Documento actualizdo correctamente.";
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosCliente() {
		try {

			List listClientes = Common.getClientes().getClientesClientesPK(
					new BigDecimal(this.idcliente), this.idempresa);
			Iterator iter = listClientes.iterator();
			if (iter.hasNext()) {
				String[] uCampos = (String[]) iter.next();
				// TODO: Constructores para cada tipo de datos

				// CLIENTES
				this.tipodocumentoActualCliente = uCampos[3];
				this.nrodocumentoActualCliente = uCampos[4];

				// PRECARGA

				listClientes = Common.getGeneral()
						.getClientesPrecargaXIdCliente(
								new BigDecimal(this.idcliente), this.idempresa);
				iter = listClientes.iterator();
				if (iter.hasNext()) {
					uCampos = (String[]) iter.next();
					this.tipodocumentoActualPrecarga = uCampos[3];
					this.nrodocumentoActualPrecarga = uCampos[4];
				} else {
					this.mensaje = "[PR] - Imposible recuperar datos para el registro seleccionado.";
				}

			} else {
				this.mensaje = "[CL] - Imposible recuperar datos para el registro seleccionado.";
			}

		} catch (Exception e) {
			log.error("getDatosClientesestados()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			this.ListTiposDoc = Common.getGeneral()
					.getGlobaltiposdocumentosAll(250, 0, this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {

				if (!Common.esEntero(this.idcliente)) {
					this.mensaje = "Es necesario seleccionar un cliente valido.";
					return false;
				}

				if (!Common.esEntero(this.nrodocumento)
						|| Long.parseLong(this.nrodocumento) < 1) {
					this.mensaje = "Es necesario ingresar un nro. de documento valido.";
					return false;
				}

				if (this.idtipodocumento.longValue() < 1) {
					this.mensaje = "Es seleccionar un tipo de documento valido.";
					return false;
				}

				this.ejecutarSentenciaDML();

			} else {

				if (this.accion.equalsIgnoreCase("recarga")) {
					getDatosCliente();
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

	public List getListTiposDoc() {
		return ListTiposDoc;
	}

	public void setListTiposDoc(List listTiposDoc) {
		ListTiposDoc = listTiposDoc;
	}

	public BigDecimal getIdtipodocumento() {
		return idtipodocumento;
	}

	public void setIdtipodocumento(BigDecimal idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public String getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(String idcliente) {
		this.idcliente = idcliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getNrodocumentoActualCliente() {
		return nrodocumentoActualCliente;
	}

	public void setNrodocumentoActualCliente(String nrodocumentoActualCliente) {
		this.nrodocumentoActualCliente = nrodocumentoActualCliente;
	}

	public String getNrodocumentoActualPrecarga() {
		return nrodocumentoActualPrecarga;
	}

	public void setNrodocumentoActualPrecarga(String nrodocumentoActualPrecarga) {
		this.nrodocumentoActualPrecarga = nrodocumentoActualPrecarga;
	}

	public BigDecimal getIdtipodocumentoActualCliente() {
		return idtipodocumentoActualCliente;
	}

	public void setIdtipodocumentoActualCliente(
			BigDecimal idtipodocumentoActualCliente) {
		this.idtipodocumentoActualCliente = idtipodocumentoActualCliente;
	}

	public String getTipodocumentoActualCliente() {
		return tipodocumentoActualCliente;
	}

	public void setTipodocumentoActualCliente(String tipodocumentoActualCliente) {
		this.tipodocumentoActualCliente = tipodocumentoActualCliente;
	}

	public String getTipodocumentoActualPrecarga() {
		return tipodocumentoActualPrecarga;
	}

	public void setTipodocumentoActualPrecarga(
			String tipodocumentoActualPrecarga) {
		this.tipodocumentoActualPrecarga = tipodocumentoActualPrecarga;
	}

	public BigDecimal getIdtipodocumentoActualPrecarga() {
		return idtipodocumentoActualPrecarga;
	}

	public void setIdtipodocumentoActualPrecarga(
			BigDecimal idtipodocumentoActualPrecarga) {
		this.idtipodocumentoActualPrecarga = idtipodocumentoActualPrecarga;
	}

}
