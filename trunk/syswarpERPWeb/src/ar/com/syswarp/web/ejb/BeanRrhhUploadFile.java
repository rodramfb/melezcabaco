/* 
 javabean para la entidad (Formulario): RRHHbusquedasLaborales
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Wed Oct 10 16:05:59 ART 2007 
 
 Para manejar la pagina: RRHHbusquedasLaboralesFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

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

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.*;

public class BeanRrhhUploadFile implements SessionBean, Serializable {
	private SessionContext context;

	static Logger log = Logger.getLogger(BeanRrhhUploadFile.class);

	private String validar = "";

	private BigDecimal idbusquedalaboral = BigDecimal.valueOf(-1);

	private BigDecimal iduserpostulante = new BigDecimal(0);

	private String referencia = "";

	private String usuarioalt = "";

	private String usuarioact = "";

	private BigDecimal idempresa;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private boolean upfile = false;

	private String filecv = "";

	private boolean error = false;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getFilecv() {
		return filecv;
	}

	public void setFilecv(String filecv) {
		this.filecv = filecv;
	}

	public boolean isUpfile() {
		return upfile;
	}

	public void setUpfile(boolean upfile) {
		this.upfile = upfile;
	}

	public BeanRrhhUploadFile() {
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
			String resultado = "";
			RRHH rrhh = Common.getRrhh();

			resultado = rrhh.rrhhUserPostulanteUpdateCV(this.iduserpostulante, this.filecv,
					this.idempresa, this.usuarioact);
			
			if(!resultado.equalsIgnoreCase("OK")){
				this.mensaje = resultado;
			}

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.volver.equalsIgnoreCase("")) {
				//response.sendRedirect("RRHHbusquedasLaboralesAbm.jsp");
				return true;
			}

			if (this.upfile) {

				if (!this.error) {
					// 1. nulidad de campos
					this.ejecutarSentenciaDML();

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
	public BigDecimal getIdbusquedalaboral() {
		return idbusquedalaboral;
	}

	public void setIdbusquedalaboral(BigDecimal idbusquedalaboral) {
		this.idbusquedalaboral = idbusquedalaboral;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public BigDecimal getIduserpostulante() {
		return iduserpostulante;
	}

	public void setIduserpostulante(BigDecimal iduserpostulante) {
		this.iduserpostulante = iduserpostulante;
	}

	public String getUsuarioact() {
		return usuarioact;
	}

	public void setUsuarioact(String usuarioact) {
		this.usuarioact = usuarioact;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

}
