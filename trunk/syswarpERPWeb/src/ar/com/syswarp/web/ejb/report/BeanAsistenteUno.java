package ar.com.syswarp.web.ejb.report;

import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import org.apache.log4j.Logger;
import java.util.*;
import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

/**
 * XDoclet-based session bean. The class must be declared public according to
 * the EJB specification.
 * 
 * To generate the EJB related files to this EJB: - Add Standard EJB module to
 * XDoclet project properties - Customize XDoclet configuration for your
 * appserver - Run XDoclet
 * 
 * Below are the xdoclet-related tags needed for this EJB.
 * 
 * @ejb.bean name="asistenteUno" display-name="Name for asistenteUno"
 *           description="Description for asistenteUno"
 *           jndi-name="ejb/asistenteUno" type="Stateful" view-type="remote"
 */
public class BeanAsistenteUno implements SessionBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	static Logger log = Logger.getLogger(BeanAsistenteUno.class);

	/** The session context */
	private SessionContext context;

	private long idusuario = 0l;

	private List listReportes = new ArrayList();

	public BeanAsistenteUno() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation.
	 * 
	 * The enterprise bean instance should store the reference to the context
	 * object in an instance variable.
	 * 
	 * This method is called with no transaction context.
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error.
	 */
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

	/**
	 * An example business method
	 * 
	 * @ejb.interface-method view-type = "remote"
	 * 
	 * @throws EJBException
	 *             Thrown if method fails due to system-level error.
	 */
	public boolean ejecutarValidacion() throws EJBException {
		try {
			Report reporting = Common.getReport();
			this.listReportes = reporting.getReportesUsuario(this.idusuario);

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public long getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(long idusuario) {
		this.idusuario = idusuario;
	}

	public List getListReportes() {
		return listReportes;
	}

	public void setListReportes(List listReportes) {
		this.listReportes = listReportes;
	}

}
