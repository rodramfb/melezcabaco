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

public class BeanProduccionMovProduParciales implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanProduccionMovProduParciales.class);

	private String validar = "";

	private String idop = "";

	private BigDecimal idesquema = new BigDecimal(0);

	private String esquema = "";

	private BigDecimal idcliente = null;

	private String cantre_op = "0";

	private String cantest_op = "";

	private String fecha_prometida = "";

	private String fecha_emision = "";

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

	private List listaParcialesOrden = new ArrayList();

	public BeanProduccionMovProduParciales() {
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

	public void getDatosProduccionMovProdu() {
		try {
			Produccion produccionMovProdu = Common.getProduccion();
			List listProduccionMovProdu = produccionMovProdu
					.getProduccionMovProduPK(new BigDecimal(this.idop),
							this.idempresa);
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

				this.idop = uCampos[0];
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

			} else {
				this.mensaje = "No existen datos para nro de orden ingresado.";
			}

			this.listaDetalleOrden = produccionMovProdu
					.getProduccionMovProdu_DetaPK(new BigDecimal(this.idop),
							this.idempresa);
			this.listaParcialesOrden = produccionMovProdu
					.getProduccionMovProduParcialesPK(
							new BigDecimal(this.idop), this.idempresa);

		} catch (Exception e) {
			log.error("getDatosProduccionMovProdu()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (this.accion.equalsIgnoreCase("consulta")) {

				if (!Common.esEntero(this.idop)) {
					this.mensaje = "Ingrese Nro. Orden de producción valido.";
					return false;
				}

				if (Long.parseLong(this.idop) < 1) {
					this.mensaje = "Ingrese Nro. Orden de producción valido.";
					return false;
				}

				this.getDatosProduccionMovProdu();
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
	public String getIdop() {
		return idop;
	}

	public void setIdop(String idop) {
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

	public List getListaParcialesOrden() {
		return listaParcialesOrden;
	}

	public void setListaParcialesOrden(List listaParcialesOrden) {
		this.listaParcialesOrden = listaParcialesOrden;
	}

}
