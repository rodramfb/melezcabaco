/* 
   javabean para la entidad (Formulario): bacoErEsquema
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 25 14:12:47 GYT 2009 
   
   Para manejar la pagina: bacoErEsquemaFrm.jsp
      
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

public class BeanBacoErEsquemaFrm implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanBacoErEsquemaFrm.class);

	private String validar = "";

	private BigDecimal idesquema = new BigDecimal(-1);

	private int anioactual = Calendar.getInstance().get(Calendar.YEAR);

	private BigDecimal anio = new BigDecimal(anioactual);

	private BigDecimal idmes = new BigDecimal(-1);

	private BigDecimal idpreferencia = new BigDecimal(-1);

	private String codigo_st = "";

	private String descrip_st = "";

	private String cantidad = "";

	private BigDecimal codigo_dt = new BigDecimal(-1);

	private String descrip_dt = "";

	private BigDecimal iddescuento = new BigDecimal(-1);

	private BigDecimal idmotivodescuento = new BigDecimal(-1);

	private BigDecimal idlista = new BigDecimal(-1);

	private String lista = "";

	private BigDecimal idempresa = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listMeses = new ArrayList();

	private List listMotivoDescuento = new ArrayList();

	private List listPreferencia = new ArrayList();

	private List listPorcDescuento = new ArrayList();

	public BeanBacoErEsquemaFrm() {
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
			Clientes bacoErEsquema = Common.getClientes();
			if (this.accion.equalsIgnoreCase("alta"))
				this.mensaje = bacoErEsquema.bacoErEsquemaCreate(this.anio,
						this.idmes, this.idpreferencia, this.codigo_st,
						new BigDecimal(this.cantidad), this.codigo_dt,
						GeneralBean
						.setNull(this.iddescuento, 1), GeneralBean
						.setNull(this.idmotivodescuento, 1), this.idlista,
						this.idempresa, this.usuarioalt);
			else if (this.accion.equalsIgnoreCase("modificacion"))
				this.mensaje = bacoErEsquema.bacoErEsquemaUpdate(
						this.idesquema, this.anio, this.idmes,
						this.idpreferencia, this.codigo_st, new BigDecimal(
								this.cantidad), this.codigo_dt, GeneralBean
								.setNull(this.iddescuento, 1), GeneralBean
								.setNull(this.idmotivodescuento, 1),
						this.idlista, this.idempresa, this.usuarioact);
			else if (this.accion.equalsIgnoreCase("baja"))
				this.mensaje = bacoErEsquema.bacoErEsquemaDelete(
						this.idesquema, this.idempresa);
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosBacoErEsquema() {
		try {
			Clientes bacoErEsquema = Common.getClientes();
			List listBacoErEsquema = bacoErEsquema.getBacoErEsquemaPK(
					this.idesquema, this.idempresa);
			Iterator iterBacoErEsquema = listBacoErEsquema.iterator();
			if (iterBacoErEsquema.hasNext()) {
				String[] uCampos = (String[]) iterBacoErEsquema.next();
				// TODO: Constructores para cada tipo de datos
				this.idesquema = new BigDecimal(uCampos[0]);
				this.anio = new BigDecimal(uCampos[1]);
				this.idmes = new BigDecimal(uCampos[2]);
				this.idpreferencia = new BigDecimal(uCampos[3]);
				this.codigo_st = uCampos[4];
				this.descrip_st = uCampos[5];
				this.cantidad = uCampos[6];
				this.codigo_dt = new BigDecimal(uCampos[7]);
				this.descrip_dt = uCampos[8];
				this.iddescuento = new BigDecimal(Common.setNotNull(uCampos[9])
						.equals("") ? "-1" : uCampos[9]);
				this.idmotivodescuento = new BigDecimal(Common.setNotNull(
						uCampos[10]).equals("") ? "-1" : uCampos[10]);
				this.idlista = new BigDecimal(uCampos[11]);
				this.lista = uCampos[12];

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}
		} catch (Exception e) {
			log.error("getDatosBacoErEsquema()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("bacoErEsquemaAbm.jsp");
				return true;
			}

			this.listMeses = Common.getGeneral().getGlobalMeses();
			this.listPreferencia = Common.getGeneral()
					.getClientespreferenciasAll(100, 0, idempresa);
			this.listMotivoDescuento = Common.getClientes()
					.getPedidosMotivosDescuentosAll(100, 0, idempresa);
			this.listPorcDescuento = Common.getClientes()
					.getClientesDescuentosAll(idempresa);

			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					// 1. nulidad de campos
					if (anio == null || anio.longValue() < 0) {
						this.mensaje = "No se puede dejar vacio el campo año. ";
						return false;
					}
					if (idmes == null || idmes.longValue() < 0) {
						this.mensaje = "No se puede dejar vacio el campo mes. ";
						return false;
					}
					if (idpreferencia == null || idpreferencia.longValue() < 0) {
						this.mensaje = "No se puede dejar vacio el campo preferencia. ";
						return false;
					}
					
					if (Common.setNotNull(lista).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo lista. ";
						return false;
					}					
					
					if (Common.setNotNull(codigo_st).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo articulo. ";
						return false;
					}
					if (!Common.esNumerico(cantidad)) {
						this.mensaje = "Ingrese valores validos para cantidad. ";
						return false;
					}
					if (Common.setNotNull(descrip_dt).equals("")) {
						this.mensaje = "No se puede dejar vacio el campo depósito. ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			} else {
				if (!this.accion.equalsIgnoreCase("alta")) {
					getDatosBacoErEsquema();
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
	public BigDecimal getIdesquema() {
		return idesquema;
	}

	public void setIdesquema(BigDecimal idesquema) {
		this.idesquema = idesquema;
	}

	public BigDecimal getAnio() {
		return anio;
	}

	public void setAnio(BigDecimal anio) {
		this.anio = anio;
	}

	public BigDecimal getIdmes() {
		return idmes;
	}

	public void setIdmes(BigDecimal idmes) {
		this.idmes = idmes;
	}

	public BigDecimal getIdpreferencia() {
		return idpreferencia;
	}

	public void setIdpreferencia(BigDecimal idpreferencia) {
		this.idpreferencia = idpreferencia;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getCodigo_dt() {
		return codigo_dt;
	}

	public void setCodigo_dt(BigDecimal codigo_dt) {
		this.codigo_dt = codigo_dt;
	}

	public BigDecimal getIddescuento() {
		return iddescuento;
	}

	public void setIddescuento(BigDecimal iddescuento) {
		this.iddescuento = iddescuento;
	}

	public BigDecimal getIdmotivodescuento() {
		return idmotivodescuento;
	}

	public void setIdmotivodescuento(BigDecimal idmotivodescuento) {
		this.idmotivodescuento = idmotivodescuento;
	}

	public BigDecimal getIdlista() {
		return idlista;
	}

	public void setIdlista(BigDecimal idlista) {
		this.idlista = idlista;
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

	public List getListMeses() {
		return listMeses;
	}

	public List getListMotivoDescuento() {
		return listMotivoDescuento;
	}

	public List getListPreferencia() {
		return listPreferencia;
	}

	public List getListPorcDescuento() {
		return listPorcDescuento;
	}

	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	public String getDescrip_st() {
		return descrip_st;
	}

	public void setDescrip_st(String descrip_st) {
		this.descrip_st = descrip_st;
	}

	public String getDescrip_dt() {
		return descrip_dt;
	}

	public void setDescrip_dt(String descrip_dt) {
		this.descrip_dt = descrip_dt;
	}

	public int getAnioactual() {
		return anioactual;
	}

}
