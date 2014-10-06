/* 
 javabean para la entidad: clienteszonas
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Nov 14 14:50:18 GMT-03:00 2006 
 
 Para manejar la pagina: clienteszonasAbm.jsp
 
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
import ar.com.syswarp.api.Common;

public class BeanContableSaldoCuentasRPT implements SessionBean, Serializable {
	static Logger log = Logger.getLogger(BeanContableSaldoCuentasRPT.class);

	private SessionContext context;

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private String accion = "";

	private String ocurrencia = "";

	private BigDecimal idempresa;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String validar = "";

	boolean buscar = false;

	private List listEjercicios = new ArrayList();

	private List listMeses = new ArrayList();

	private List listSaldoCuentas = new ArrayList();

	private int anio = 0;

	private int mesDesde = 0;

	private int mesHasta = 0;

	public BeanContableSaldoCuentasRPT() {
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

		Contable contable = Common.getContable();

		try {

			this.listEjercicios = contable.getEjerciciosAll(this.idempresa);
			this.listMeses = Common.getGeneral().getGlobalMeses();

			if (!this.validar.equals("")) {
				if (this.anio < 1) {
					this.mensaje = "Debe seleccionar ejercicio.";

				} else if (this.mesDesde < 1) {
					this.mensaje = "Debe seleccionar Mes Desde.";
				} else if (this.mesHasta < 1) {
					this.mensaje = "Debe seleccionar Mes Hasta.";
				} else if (this.mesDesde > this.mesHasta) {
					this.mensaje = "Mes Desde debe ser Menor o Igual a Mes Hasta.";
				} else {
					buscar = true;
				}
			}
			if (buscar) {
				String entidad = "("
						+ "SELECT idcuenta, cuenta, idempresa  FROM contablesaldocuexmes  "
						+ " WHERE ejercicio = " + anio
						+ "   AND parmes BETWEEN  " + mesDesde + "   AND "
						+ mesHasta
						+ " GROUP BY idcuenta, cuenta, idempresa ) entidad";
				String filtro = " WHERE TRUE ";

				this.totalRegistros = contable.getTotalEntidadFiltro(entidad,
						filtro, this.idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}

				this.listSaldoCuentas = contable
						.getSaldoCuentasPeriodoAll(this.anio, this.mesDesde,
								this.mesHasta, this.idempresa);

				if (this.totalRegistros < 1)
					this.mensaje = "No existen registros.";

			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(long total) {
		this.totalRegistros = total;
	}

	public long getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(long totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public long getPaginaSeleccion() {
		return paginaSeleccion;
	}

	public void setPaginaSeleccion(long paginaSeleccion) {
		this.paginaSeleccion = paginaSeleccion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String buscar) {
		this.ocurrencia = buscar;
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

	public String getValidar() {
		return validar;
	}

	public void setValidar(String validar) {
		this.validar = validar;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getMesDesde() {
		return mesDesde;
	}

	public void setMesDesde(int mesDesde) {
		this.mesDesde = mesDesde;
	}

	public int getMesHasta() {
		return mesHasta;
	}

	public void setMesHasta(int mesHasta) {
		this.mesHasta = mesHasta;
	}

	public List getListEjercicios() {
		return listEjercicios;
	}

	public List getListMeses() {
		return listMeses;
	}

	public List getListSaldoCuentas() {
		return listSaldoCuentas;
	}
}
