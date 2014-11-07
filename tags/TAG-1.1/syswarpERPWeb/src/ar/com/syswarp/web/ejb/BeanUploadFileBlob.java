/* 
   javabean para la entidad: globalBlobImagenes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 17 11:33:18 GYT 2009 
   
   Para manejar la pagina: globalBlobImagenesAbm.jsp
      
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
import org.apache.log4j.Logger;
import java.util.*;
import java.math.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.servlet.upload.*;
import ar.com.syswarp.api.Common;

public class BeanUploadFileBlob implements SessionBean, Serializable {

	static final long serialVersionUID = 2009061811;
	
	static Logger log = Logger.getLogger(BeanUploadFileBlob.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private long totalRegistros = 0l;

	private List globalBlobImagenesList = new ArrayList();

	private BigDecimal tupla = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private boolean soloImagen = false;

	private int totalFiles = 0;

	boolean blobLimiteActivo = false;

	public BeanUploadFileBlob() {
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

		RequestDispatcher dispatcher = null;
		General globalBlobImagenes = Common.getGeneral();
		try {

			Properties props;
			props = new Properties();
			props
					.load(BeanUploadFileBlob.class
							.getResourceAsStream("/ar/com/syswarp/servlet/upload/upload.properties"));

			this.totalFiles = Integer.parseInt(props
					.getProperty("blob.totalFiles"));

			this.blobLimiteActivo = Boolean.parseBoolean(props
					.getProperty("blob.limiteActivo"));

			String filtro = "WHERE tupla = " + tupla;
			String entidad = "( SELECT * FROM globalBlobImagenes )BlobImagenes ";

			this.totalRegistros = globalBlobImagenes.getTotalEntidadFiltro(
					entidad, filtro, this.idempresa);

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(long total) {
		this.totalRegistros = total;
	}

	public List getGlobalBlobImagenesList() {
		return globalBlobImagenesList;
	}

	public void setGlobalBlobImagenesList(List globalBlobImagenesList) {
		this.globalBlobImagenesList = globalBlobImagenesList;
	}

	public BigDecimal getTupla() {
		return tupla;
	}

	public void setTupla(BigDecimal tupla) {
		this.tupla = tupla;
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

	public boolean isSoloImagen() {
		return soloImagen;
	}

	public void setSoloImagen(boolean soloImagen) {
		this.soloImagen = soloImagen;
	}

	public int getTotalFiles() {
		return totalFiles;
	}

	public void setTotalFiles(int totalFiles) {
		this.totalFiles = totalFiles;
	}

	public boolean isBlobLimiteActivo() {
		return blobLimiteActivo;
	}

	public void setBlobLimiteActivo(boolean blobLimiteActivo) {
		this.blobLimiteActivo = blobLimiteActivo;
	}
}
