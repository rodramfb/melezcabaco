/* 
 javabean para la entidad (Formulario): clientetarjetascreditomarcas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Jan 23 19:22:37 GMT-03:00 2007 
 
 Para manejar la pagina: clientetarjetascreditomarcasFrm.jsp
 
 */
package ar.com.syswarp.web.ejb;

import java.io.File;
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

public class BeanClientesPresentacionTarjetasArchivos implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanClientesPresentacionTarjetasArchivos.class);

	private String validar = "";

	private BigDecimal idtarjetacredito = BigDecimal.valueOf(-1);

	private String tarjetacredito = "";

	private BigDecimal idempresa;

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listTarjetasCredito = new ArrayList();

	private List listClub = new ArrayList();

	private BigDecimal idclub = new BigDecimal(-1);

	private Hashtable htPathTarjeta = new Hashtable();

	private Hashtable htClubes = new Hashtable();

	private Hashtable archivosHash = new Hashtable();

	private long totalRegistros = 0l;

	public BeanClientesPresentacionTarjetasArchivos() {
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

			this.listTarjetasCredito = Common.getClientes()
					.getClientetarjetascreditomarcasAll(250, 0, this.idempresa);

			this.listClub = Common.getClientes().getClientesClubAll(100, 0,
					this.idempresa);

			// 1. nulidad de campos
			// 2. len 0 para campos nulos

			if (this.idtarjetacredito.intValue() < 1) {
				this.mensaje = "Seleccione tarjeta.";
				return false;
			}

			this.setHtPathTarjeta(this.listTarjetasCredito);

			this.archivosHash = this.getHashArchivos();

			this.totalRegistros = this.archivosHash.size();

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}

		return true;
	}

	private void setHtPathTarjeta(List listTjtas) {

		try {

			Iterator it = listTjtas.iterator();
			while (it.hasNext()) {
				String[] datos = (String[]) it.next();
				this.htPathTarjeta.put(datos[0], Common.setNotNull(datos[4]));
			}

		} catch (Exception e) {
			log.error("setHtPathTarjeta: " + e);
		}

	}

	private Hashtable getHashArchivos() throws EJBException {
		String dirname = this.htPathTarjeta.get(this.idtarjetacredito
				.toString())
				+ "";
		Hashtable hash = new Hashtable();
		File file = new File(dirname);
		String relativo = "";

		try {

			relativo = dirname.substring(dirname.indexOf("tarjetas"));
			if (file.isDirectory()) {
				// log.debug("Directorio de " + dirname);
				String s[] = file.list();
				for (int i = 0; i < s.length; i++) {
					File fileToReadSave = new File(dirname + s[i]);
					if (fileToReadSave.isDirectory()) {
						// log.debug("DIRECTORIO:" + fileToReadSave);
					} else {
						if (fileToReadSave.getName().indexOf(".zip") < 0)
							continue;
						String sAtributos[] = new String[] {
								fileToReadSave.getName(),
								fileToReadSave.lastModified() + "",
								(fileToReadSave.length() / 1024) + "", relativo };
						hash.put(sAtributos[0], sAtributos);
						// log.debug("ARCHIVO: " + fileToReadSave);
					}
				}

			} else {
				// log.debug(dirname + " no es un directorio");
			}

		} catch (Exception e) {
			log.error("getListaArchivos(): " + e);
		}
		return hash;
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
	public BigDecimal getIdtarjetacredito() {
		return idtarjetacredito;
	}

	public void setIdtarjetacredito(BigDecimal idtarjetacredito) {
		this.idtarjetacredito = idtarjetacredito;
	}

	public String getTarjetacredito() {
		return tarjetacredito;
	}

	public void setTarjetacredito(String tarjetacredito) {
		this.tarjetacredito = tarjetacredito;
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

	public List getListTarjetasCredito() {
		return listTarjetasCredito;
	}

	public BigDecimal getIdclub() {
		return idclub;
	}

	public void setIdclub(BigDecimal idclub) {
		this.idclub = idclub;
	}

	public List getListClub() {
		return listClub;
	}

	public Hashtable getArchivosHash() {
		return archivosHash;
	}

	public long getTotalRegistros() {
		return totalRegistros;
	}

}
