/* 
 javabean para la entidad (Formulario): produccionMovProdu
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Feb 21 13:30:17 GMT-03:00 2007 
 
 Para manejar la pagina: produccionMovProduFrm.jsp
 
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

public class BeanProduccionMovProduRegalosFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger
			.getLogger(BeanProduccionMovProduRegalosFrm.class);

	private String validar = "";

	private BigDecimal idop = new BigDecimal(0);

	private BigDecimal idesquema = new BigDecimal(0);

	private String esquema = "";

	private BigDecimal idcliente = null;

	private String cantre_op = "0";

	private String cantest_op = "";

	private String fecha_prometida = Common.initObjectTimeStr();

	private String fecha_emision = Common.initObjectTimeStr();

	private String observaciones = "";

	private String codigo_st = "";

	private String descrip_st = "";

	private BigDecimal idcontador = new BigDecimal(0);

	private BigDecimal nrointerno = new BigDecimal(0);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listaDetalleOrden = new ArrayList();

	private String idpedido_regalos_cabe = "";

	public BeanProduccionMovProduRegalosFrm() {
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
			Produccion produccionMovProdu = Common.getProduccion();
			if (this.accion.equalsIgnoreCase("alta")) {
				this.mensaje = produccionMovProdu.produccionOrdenProdCreate(
						this.idesquema, this.idcliente, new BigDecimal(
								this.cantre_op),
						new BigDecimal(this.cantest_op), (java.sql.Date) Common
								.setObjectToStrOrTime(this.fecha_prometida,
										"strToJSDate"), (java.sql.Date) Common
								.setObjectToStrOrTime(this.fecha_emision,
										"strToJSDate"), this.observaciones,
						this.codigo_st, this.idcontador, this.nrointerno,
						new BigDecimal(this.idpedido_regalos_cabe),
						this.usuarioalt, this.idempresa);

				if (Common.esEntero(this.mensaje)) {
					this.accion = "modificacion";
					this.idop = new BigDecimal(this.mensaje);
					this.mensaje = "Se genero la orden de produccion Nro: "
							+ mensaje;
				}

			} else if (this.accion.equalsIgnoreCase("modificacion")) {
				this.mensaje = produccionMovProdu.produccionOrdenProdUpdate(
						this.idop, this.idesquema, this.idcliente,
						new BigDecimal(this.cantre_op), new BigDecimal(
								this.cantest_op), (java.sql.Date) Common
								.setObjectToStrOrTime(this.fecha_prometida,
										"strToJSDate"), (java.sql.Date) Common
								.setObjectToStrOrTime(this.fecha_emision,
										"strToJSDate"), this.observaciones,
						this.codigo_st, this.idcontador, this.nrointerno,
						this.usuarioact, this.idempresa);
				if (this.mensaje.equalsIgnoreCase("OK")) {
					this.mensaje = "Actualizacion correcta de orden de produccion.";
				}
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosProduccionMovProdu() {
		try {
			Produccion produccionMovProdu = Common.getProduccion();
			List listProduccionMovProdu = produccionMovProdu
					.getProduccionMovProduPK(this.idop, this.idempresa);
			Iterator iterProduccionMovProdu = listProduccionMovProdu.iterator();
			if (iterProduccionMovProdu.hasNext()) {
				String[] uCampos = (String[]) iterProduccionMovProdu.next();
				// TODO: Constructores para cada tipo de datos

				/*
				 * 0 mp.idop, 1 mp.idesquema, 2 ec.esquema, 3 mp.idcliente,4
				 * mp.cantre_op, 5 mp.cantest_op,6 mp.fecha_prometida, 7
				 * mp.fecha_emision, 8 mp.observaciones, 9 mp.codigo_st,10
				 * sk.descrip_st, 11 mp.idcontador,12 mp.nrointerno,
				 */

				this.idop = new BigDecimal(uCampos[0]);
				this.idesquema = new BigDecimal(uCampos[1]);
				this.esquema = uCampos[2];
				this.idcliente = null;// uCampos[3]
				this.cantre_op = uCampos[4];
				this.cantest_op = uCampos[5];
				this.fecha_prometida = Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[6]), "JSDateToStr")
						.toString();
				this.fecha_emision = Common.setObjectToStrOrTime(
						java.sql.Date.valueOf(uCampos[7]), "JSDateToStr")
						.toString();
				this.observaciones = uCampos[8];
				this.codigo_st = uCampos[9];
				this.descrip_st = uCampos[10];
				this.idcontador = new BigDecimal(uCampos[11]);
				this.nrointerno = new BigDecimal(uCampos[12]);
				this.idpedido_regalos_cabe = (uCampos[14]);
				

			} else {
				this.mensaje = "Imposible recuperar datos para el registro seleccionado.";
			}

			listaDetalleOrden = produccionMovProdu
					.getProduccionMovProdu_DetaPK(this.idop, this.idempresa);

		} catch (Exception e) {
			log.error("getDatosProduccionMovProdu()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			if (!this.volver.equalsIgnoreCase("")) {
				response.sendRedirect("produccionMovProduPedidosRegalos.jsp");
				return true;
			}
			if (!this.validar.equalsIgnoreCase("")) {
				if (!this.accion.equalsIgnoreCase("baja")) {

					if (!Common.esEntero(this.idpedido_regalos_cabe)) {
						this.mensaje = "Es necesario seleccionar un pedido de regalos.";
						return false;
					}

					// 1. nulidad de campos
					if (idesquema == null) {
						this.mensaje = "No se puede dejar vacio el campo idesquema ";
						return false;
					}

					if (cantest_op == null || !Common.esNumerico(cantest_op)) {
						this.mensaje = "Cantidad estimada solo admite valores numericos. ";
						return false;
					}

					if (new BigDecimal(cantest_op).compareTo(new BigDecimal(0)) <= 0) {
						this.mensaje = "Cantidad estimada debe ser mayor a cero. ";
						return false;
					}

					if (fecha_prometida == null
							|| fecha_prometida.trim().equals("")) {
						this.mensaje = "Ingrese Fecha Prometida.";
						return false;
					}
					if (fecha_emision == null
							|| fecha_emision.trim().equals("")) {
						this.mensaje = "Ingrese Fecha Emision. ";
						return false;
					}
					if (codigo_st == null || codigo_st.trim().length() == 0) {
						this.mensaje = "No se puede dejar vacio el campo articulo ";
						return false;
					}

				}
				this.ejecutarSentenciaDML();
			}

			if (this.idop.compareTo(new BigDecimal(0)) != 0) {
				getDatosProduccionMovProdu();
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
	public BigDecimal getIdop() {
		return idop;
	}

	public void setIdop(BigDecimal idop) {
		this.idop = idop;
	}

	public BigDecimal getIdesquema() {
		return idesquema;
	}

	public void setIdesquema(BigDecimal idesquema) {
		this.idesquema = idesquema;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getCantre_op() {
		return cantre_op;
	}

	public void setCantre_op(String cantre_op) {
		this.cantre_op = cantre_op;
	}

	public String getCantest_op() {
		return cantest_op;
	}

	public void setCantest_op(String cantest_op) {
		this.cantest_op = cantest_op;
	}

	public String getFecha_prometida() {
		return fecha_prometida;
	}

	public void setFecha_prometida(String fecha_prometida) {
		this.fecha_prometida = fecha_prometida;
	}

	public String getFecha_emision() {
		return fecha_emision;
	}

	public void setFecha_emision(String fecha_emision) {
		this.fecha_emision = fecha_emision;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getCodigo_st() {
		return codigo_st;
	}

	public void setCodigo_st(String codigo_st) {
		this.codigo_st = codigo_st;
	}

	public BigDecimal getIdcontador() {
		return idcontador;
	}

	public void setIdcontador(BigDecimal idcontador) {
		this.idcontador = idcontador;
	}

	public BigDecimal getNrointerno() {
		return nrointerno;
	}

	public void setNrointerno(BigDecimal nrointerno) {
		this.nrointerno = nrointerno;
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

	public String getEsquema() {
		return esquema;
	}

	public void setEsquema(String esquema) {
		this.esquema = esquema;
	}

	public List getListaDetalleOrden() {
		return listaDetalleOrden;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getIdpedido_regalos_cabe() {
		return idpedido_regalos_cabe;
	}

	public void setIdpedido_regalos_cabe(String idpedido_regalos_cabe) {
		this.idpedido_regalos_cabe = idpedido_regalos_cabe;
	}

}
