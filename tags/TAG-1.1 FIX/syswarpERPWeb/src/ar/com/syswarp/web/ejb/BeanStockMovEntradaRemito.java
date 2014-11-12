/* 
 javabean para la entidad (Formulario): proveedoMovProv
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Thu Jul 27 09:30:45 GMT-03:00 2006 
 
 Para manejar la pagina: proveedoMovProvFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.math.*;
import java.util.*;
import java.sql.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanStockMovEntradaRemito implements SessionBean, Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger.getLogger(BeanStockMovEntradaRemito.class);

	GeneralBean gb = new GeneralBean();

	private String fechamov = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	// 1 - DATOS APLICACION

	private BigDecimal nint_ms_an = new BigDecimal(-1);

	private String origen = "";

	private String mensaje = "";

	private int ejercicioactivo = 0;

	private String usuarioact = "";

	private String usuarioalt = "";

	// 2 - DATOS ANEXOS

	private BigDecimal idanexo = new BigDecimal(0);

	private BigDecimal codigo_anexo = new BigDecimal(-1);

	private String razon_social = "";

	private String domicilio = "";

	private String codigo_postal = "";

	private BigDecimal idprovincia = new BigDecimal(0);

	private String provincia = "";

	private BigDecimal idlocalidad = new BigDecimal(0);

	private String localidad = "";

	private String cuit = "";

	private String iibb = "";

	private String remito_ms = "0";

	private String observaciones = "";

	// 3 - Cuentas Articulos

	public BeanStockMovEntradaRemito() {
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

	public boolean ejecutarValidacion() {
		try {
			Stock stock = Common.getStock();
			List lista = stock.getStockAnexosNint(nint_ms_an, this.idempresa);
			Iterator iter = lista.iterator();
			if (iter.hasNext()) {
				String[] datos = (String[]) iter.next();

				/*
				 * sa.idanexo, sa.nint_ms_an, sa.codigo_anexo, sa.razon_social,
				 * sa.domicilio,sa.codigo_postal, sa.idlocalidad, lo.localidad,
				 * sa.idprovincia, pr.provincia, sa.cuit, sa.iibb,
				 */

				this.codigo_anexo = new BigDecimal(datos[2]);
				this.razon_social = datos[3];
				this.domicilio = datos[4];
				this.codigo_postal = datos[5];
				this.localidad = datos[7];
				this.provincia = datos[9];
				this.cuit = datos[10];
				this.iibb = datos[11];

			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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

	public String getFechamov() {
		return fechamov;
	}

	public void setFechamov(String fechamov) {
		this.fechamov = fechamov;
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

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public BigDecimal getCodigo_anexo() {
		return codigo_anexo;
	}

	public void setCodigo_anexo(BigDecimal codigo_anexo) {
		this.codigo_anexo = codigo_anexo;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public BigDecimal getIdanexo() {
		return idanexo;
	}

	public void setIdanexo(BigDecimal idanexo) {
		this.idanexo = idanexo;
	}

	public BigDecimal getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(BigDecimal idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getIibb() {
		return iibb;
	}

	public void setIibb(String iibb) {
		this.iibb = iibb;
	}

	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public BigDecimal getIdlocalidad() {
		return idlocalidad;
	}

	public void setIdlocalidad(BigDecimal idlocalidad) {
		this.idlocalidad = idlocalidad;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getRemito_ms() {
		return remito_ms;
	}

	public void setRemito_ms(String remito_ms) {
		this.remito_ms = remito_ms;
	}

	public int getEjercicioactivo() {
		return ejercicioactivo;
	}

	public void setEjercicioactivo(int ejercicioactivo) {
		this.ejercicioactivo = ejercicioactivo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public BigDecimal getNint_ms_an() {
		return nint_ms_an;
	}

	public void setNint_ms_an(BigDecimal nint_ms_an) {
		this.nint_ms_an = nint_ms_an;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

}
