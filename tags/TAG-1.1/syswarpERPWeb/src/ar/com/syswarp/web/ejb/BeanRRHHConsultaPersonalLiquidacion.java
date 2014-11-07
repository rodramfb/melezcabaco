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

public class BeanRRHHConsultaPersonalLiquidacion implements SessionBean,
		Serializable {
	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	static Logger log = Logger
			.getLogger(BeanRRHHConsultaPersonalLiquidacion.class);

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private String validar = "";

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private List personalList = new ArrayList();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private String anioliq = "";

	private BigDecimal mesliq = new BigDecimal(-1);

	private String ocurrencia = "";

	private boolean buscar = false;

	public BeanRRHHConsultaPersonalLiquidacion() {
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
		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public boolean ejecutarValidacion() {
		try {
			RRHH Consulta = Common.getRrhh();
			if (!this.ocurrencia.trim().equalsIgnoreCase("")) {
				if (ocurrencia.indexOf("'") >= 0) {
					this.mensaje = "Caracteres invalidos en campo busqueda.";
				} else {
					buscar = true;
				}
			}
			if (buscar) {
				if (!Common.esNumerico(anioliq)
						|| new BigDecimal(anioliq).longValue() <= 0) {
					this.mensaje = "Debe ingresar un año de liquidacion valido.";
					return false;
				}
				if (mesliq.longValue() <= 0) {
					this.mensaje = "Debe ingresar un mes de liquidacion valido";
					return false;
				}
				String entidad = "(Select liq.legajo, personal.apellido, liq.netoacobrar,liq.anioliq, liq.mesliq ,liq.idempresa  from rrhhliq_cabe liq"
						+ " inner join rrhhpersonal personal on (personal.legajo = liq.legajo and personal.idempresa = liq.idempresa)"
						+ " where liq.anioliq = "
						+ anioliq.toString()
						+ " and liq.mesliq = "
						+ mesliq.toString()
						+ " and liq.idempresa = "
						+ idempresa.toString()
						+ " )entidad";
				String filtro = " Where idempresa = " + idempresa.toString()
						+ " and anioliq =  " + anioliq.toString()
						+ " and mesliq = " + mesliq.toString()
						+ " and upper(apellido) like '%"
						+ ocurrencia.trim().toUpperCase() + "%'";
				this.totalRegistros = Consulta.getTotalEntidadFiltro(entidad,
						filtro, idempresa);
				this.totalPaginas = (this.totalRegistros / this.limit) + 1;
				if (this.totalPaginas < this.paginaSeleccion)
					this.paginaSeleccion = this.totalPaginas;
				if (this.totalRegistros == this.limit)
					this.offset = 0;
				this.offset = (this.paginaSeleccion - 1) * this.limit;
				if (this.totalRegistros == this.limit) {
					this.offset = 0;
					this.totalPaginas = 1;
				}
				this.personalList = Consulta.ObtenerPersonalXPeriodoOcu(
						new BigDecimal(anioliq), mesliq, limit, 0l, ocurrencia,
						idempresa);

			} else {
				if (!validar.equalsIgnoreCase("")) {

					if (!Common.esNumerico(anioliq)
							|| new BigDecimal(anioliq).longValue() <= 0) {
						this.mensaje = "Debe ingresar un año de liquidacion valido.";
						return false;
					}
					if (mesliq.longValue() <= 0) {
						this.mensaje = "Debe ingresar un mes de liquidacion valido";
						return false;
					}

					String entidad = "(Select liq.legajo, personal.apellido, liq.netoacobrar,liq.anioliq, liq.mesliq ,liq.idempresa from rrhhliq_cabe liq"
							+ " inner join rrhhpersonal personal on (personal.legajo = liq.legajo and personal.idempresa = liq.idempresa)"
							+ " where liq.anioliq = "
							+ anioliq.toString()
							+ " and liq.mesliq = "
							+ mesliq.toString()
							+ " and liq.idempresa = "
							+ idempresa.toString()
							+ ")entidad";
					this.totalRegistros = Consulta.getTotalEntidad(entidad,
							this.idempresa);
					this.totalPaginas = (this.totalRegistros / this.limit) + 1;
					if (this.totalPaginas < this.paginaSeleccion)
						this.paginaSeleccion = this.totalPaginas;
					this.offset = (this.paginaSeleccion - 1) * this.limit;
					if (this.totalRegistros == this.limit) {
						this.offset = 0;
						this.totalPaginas = 1;
					}

					personalList = Consulta.ObtenerPersonalXPeriodoAll(
							new BigDecimal(anioliq), mesliq, idempresa, limit,
							offset);

				}
			}
			if (this.totalRegistros < 1)
				this.mensaje = "No existen registros.";

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

	public List getPersonalList() {
		return personalList;
	}

	public void setPersonalList(List personalList) {
		this.personalList = personalList;
	}

	public String getAnioliq() {
		return anioliq;
	}

	public void setAnioliq(String anioliq) {
		this.anioliq = anioliq;
	}

	public BigDecimal getMesliq() {
		return mesliq;
	}

	public void setMesliq(BigDecimal mesliq) {
		this.mesliq = mesliq;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(long totalRegistros) {
		this.totalRegistros = totalRegistros;
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

	public String getOcurrencia() {
		return ocurrencia;
	}

	public void setOcurrencia(String ocurrencia) {
		this.ocurrencia = ocurrencia;
	}

}
