/* 
 javabean para la entidad (Formulario): clienteslistasdeprecios
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Mar 18 17:14:05 ART 2008 
 
 Para manejar la pagina: clienteslistasdepreciosFrm.jsp
 
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

public class BeanClienteslistasdepreciosFrm implements SessionBean,
		Serializable {
	
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanClienteslistasdepreciosFrm.class);

	private String validar = "";

	private BigDecimal idlista = new BigDecimal (-1);

	private String lista = "";

	private String codigo_st = "";

	private String descrip_st = "";

	private String precio =  "" ;

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	public BeanClienteslistasdepreciosFrm() {
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
			Clientes clienteslistasdeprecios = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = clienteslistasdeprecios
						.clienteslistasdepreciosCreate(this.idlista,
								this.codigo_st, new Double(this.precio), this.idempresa,
								this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = clienteslistasdeprecios
						.clienteslistasdepreciosUpdate(this.idlista,
								this.codigo_st, new Double(this.precio), this.idempresa,
								this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = clienteslistasdeprecios
						.clienteslistasdepreciosDelete(this.idlista,
								this.codigo_st, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosClienteslistasdeprecios() {
		try {
			Clientes clienteslistasdeprecios = Common.getClientes();
			List listClienteslistasdeprecios = clienteslistasdeprecios
					.getClienteslistasdepreciosPK(this.idlista, this.codigo_st,
							this.idempresa);
			Iterator iterClienteslistasdeprecios = listClienteslistasdeprecios
					.iterator();
			if (iterClienteslistasdeprecios.hasNext()) {
				String[] uCampos = (String[]) iterClienteslistasdeprecios
						.next();
				// TODO: Constructores para cada tipo de datos
				this.idlista = BigDecimal.valueOf(Long.parseLong(uCampos[0]));
				this.lista = uCampos[1];
				this.codigo_st = uCampos[2];
				this.descrip_st = uCampos[3];
				this.precio =  uCampos[4];
				this.idempresa = BigDecimal.valueOf(Long.parseLong(uCampos[5]));
			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosClienteslistasdeprecios()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("clienteslistasdepreciosAbm.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {
 
					// 2. len 0 para campos nulos
					if (Common.setNotNull(codigo_st).equals("")) {
						this.mensaje = "Seleccione un Articulo.  ";
						return false;
					}
					if (idlista == null || idlista.longValue()<1) {
						this.mensaje = "Seleccione una lista de precios valida. ";
						return false;
					}
					
					if (!Common.esNumerico(this.precio)) {
						this.mensaje = "Ingrese un valor numérico para precio. ";
						return false;
					}
					
				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosClienteslistasdeprecios();
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
	public BigDecimal getIdlista() {
		return idlista;
	}

	public void setIdlista(BigDecimal idlista) {
		this.idlista = idlista;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
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

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}
}
