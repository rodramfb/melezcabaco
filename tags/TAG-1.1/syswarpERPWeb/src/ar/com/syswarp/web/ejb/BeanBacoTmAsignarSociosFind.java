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

public class BeanBacoTmAsignarSociosFind implements SessionBean, Serializable {

	static Logger log = Logger.getLogger(BeanBacoTmAsignarSociosFind.class);

	private SessionContext context;

	private String usuarioalt = "";

	private long totalRegistros = 0l;

	private String accion = "";

	private BigDecimal idempresa;

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private BigDecimal idtelemark = new BigDecimal(-1);

	private BigDecimal idcampacabe = new BigDecimal(-1);

	private BigDecimal idcampacabeAnterior = new BigDecimal(-1);

	private List listProvincias = new ArrayList();

	private List listOrigen = new ArrayList();

	private List listSubOrigen = new ArrayList();

	private List listEstado = new ArrayList();

	private List listMotivos = new ArrayList();

	private List listTipoCliente = new ArrayList();

	private List listCategoria = new ArrayList();

	private List listPreferencias = new ArrayList();

	private List listCampaniasActivas = new ArrayList();

	private List listCampaniasAnteriores = new ArrayList();

	private List listTeleMarketer = new ArrayList();

	//

	private String idclientedesde = "";

	private String idclientehasta = "";

	private String fechaingresodesde = "";

	private String fechaingresohasta = "";

	private BigDecimal idprovincia = new BigDecimal(-1);

	private BigDecimal idlocalidad = new BigDecimal(-1);

	private String localidad = "";

	private BigDecimal idestado = new BigDecimal(-1);

	private BigDecimal idmotivo = new BigDecimal(-1);

	private BigDecimal idorigen = new BigDecimal(-1);

	private BigDecimal idsuborigen = new BigDecimal(-1);

	private BigDecimal idtipoclie = new BigDecimal(-1);

	private BigDecimal idcategoria = new BigDecimal(-1);

	private BigDecimal idpreferencia = new BigDecimal(-1);

	private String limiteasignacion = "";

	private String filtros = "";

	public BeanBacoTmAsignarSociosFind() {
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
		String stmtLimit = "";

		try {

			// Llenar listas de seleccion.
			this.listProvincias = Common.getGeneral().getGlobalprovinciasAll(
					100, 0);
			this.listEstado = Common.getClientes().getClientesestadosAll(100,
					0, this.idempresa);
			this.listMotivos = Common.getClientes().getClientesmotivosAll(250,
					0, this.idempresa);
			this.listCategoria = Common.getClientes().getTMCategoriasSociosAll(
					100, 0, this.idempresa);
			this.listOrigen = Common.getGeneral().getOrigenprospectoAll(100, 0,
					this.idempresa);
			this.listSubOrigen = Common.getGeneral().getSuborigenprospectoAll(
					500, 0, this.idempresa);
			this.listPreferencias = Common.getGeneral()
					.getClientespreferenciasAll(100, 0, this.idempresa);
			this.listTipoCliente = Common.getClientes().getClientestipoclieAll(
					100, 0, this.idempresa);
			this.listCampaniasActivas = Common.getClientes()
					.getClientesCampaiaLovAll(100, 0, this.idempresa);

			this.listCampaniasAnteriores = Common.getClientes()
					.getClientesCampaiaAnterioresLovAll(100, 0, this.idempresa);

			this.listTeleMarketer = Common.getGeneral().getGlobalusuariosAll(
					250, 0, this.idempresa);
			//

			if (!this.accion.equals("")) {

				// --> EJV - 20100429
				// if (this.idcampacabe.longValue() < 0
				// && this.idcampacabeAnterior.longValue() < 0) {
				// this.mensaje =
				// "Es necesario seleccionar campa�a/campa�a anterior.";
				// return false;
				// }

				if (this.idcampacabe.longValue() < 0) {
					// --> EJV - 20100429
					// this.mensaje = "Es necesario seleccionar campa�a.";
					this.mensaje = "Es necesario seleccionar campaña.";
					// --
					return false;
				}

				// --> EJV - 20100429
				// if (this.idcampacabeAnterior.longValue() > 0){
				// this.idcampacabe = this.idcampacabeAnterior;
				// }

				if (this.idcampacabeAnterior.longValue() > 0) {

					this.filtros += "    AND cl.idcliente NOT IN ("
							+ "                      SELECT idcliente "
							+ "                        FROM bacotmseleccionsocio "
							+ "                       WHERE idcampacabe = "
							+ idcampacabeAnterior + " AND idempresa =   "
							+ this.idempresa + "                      )";

				}

				// <--

				if (this.idprovincia.longValue() > 0) {
					this.filtros += " AND pr.idprovincia = " + this.idprovincia;
					if (this.idlocalidad.longValue() > 0) {
						this.filtros += " AND lo.idlocalidad = "
								+ this.idlocalidad;
					}
				}

				if (!this.idclientedesde.trim().equals("")) {
					if (Common.esEntero(this.idclientedesde)) {
						this.filtros += " AND  cl.idcliente >= "
								+ this.idclientedesde;
					} else {
						this.mensaje = "Ingrese valores validos para socio desde.";
						return false;
					}
				}

				if (!this.idclientehasta.trim().equals("")) {
					if (Common.esEntero(this.idclientehasta)) {
						this.filtros += " AND  cl.idcliente <= "
								+ this.idclientehasta;
					} else {

						this.mensaje = "Ingrese valores validos para socio desde.";
						return false;
					}
				}

				if (this.idorigen.longValue() > 0) {
					this.filtros += " AND pcc.idorigen = " + this.idorigen;
				}

				if (this.idsuborigen.longValue() > 0) {
					this.filtros += " AND pcc.idsuborigen = "
							+ this.idsuborigen;
				}

				if (this.idestado.longValue() > 0) {
					this.filtros += " AND eh.idestado = " + this.idestado;
					if (this.idmotivo.longValue() > 0) {
						this.filtros += " AND eh.idmotivo = " + this.idmotivo;
					}
				}

				if (this.idmotivo.longValue() > 0) {
					this.filtros += " AND eh.idmotivo = " + this.idmotivo;
				}

				if (this.idpreferencia.longValue() > 0) {
					this.filtros += " AND pcc.idpreferencia = "
							+ this.idpreferencia;
				}

				if (this.idtipoclie.longValue() > 0) {
					this.filtros += " AND cl.idtipoclie = " + this.idtipoclie;
				}

				if (this.idcategoria.longValue() > 0) {
					this.filtros += " AND ca.idcategoria = " + this.idcategoria;
				}

				if (!this.fechaingresodesde.equals("")) {
					this.filtros += " AND pcc.fechadeingreso::date >= TO_DATE('"
							+ this.fechaingresodesde + "', 'dd/mm/yyyy')";
				}

				if (!this.fechaingresohasta.equals("")) {
					this.filtros += " AND pcc.fechadeingreso::date <= TO_DATE('"
							+ this.fechaingresohasta + "', 'dd/mm/yyyy')";
				}

				if (this.filtros.equals("")) {

					this.mensaje = "Es necesario seleccionar al menos un criterio de busqueda.";
					return false;

				} else if (this.accion.equalsIgnoreCase("asignar")) {

					if (this.idtelemark.longValue() < 0) {
						this.mensaje = "Es necesario seleccionar telemarketer a asignar.";
						return false;
					}

					if (!this.limiteasignacion.equals("")) {
						if (!Common.esEntero(this.limiteasignacion)) {
							this.mensaje = "Ingrese valores validos para socio desde.";
							return false;
						}

						stmtLimit = " LIMIT " + this.limiteasignacion;
					}

					// llamar asignacion

					this.mensaje = Common.getClientes()
							.bacoTmAsignacionSocioCreate(idcampacabe,
									idtelemark, filtros, stmtLimit, idempresa,
									usuarioalt);

					if (Common.esEntero(this.mensaje)) {
						this.mensaje = " Se asignaron un total de "
								+ this.mensaje + " registros.";

					}

				} else if (this.accion.equalsIgnoreCase("consultar")) {

					// llamar consulta

					this.totalRegistros = Common.getClientes()
							.getBacoTmAsignacionTotalSocios(this.idcampacabe,
									filtros, this.idempresa).longValue();

					if (this.totalRegistros < 0)
						this.mensaje = "No fue posible contabilizar socios que cumplan el criterio.";
					else if (this.totalRegistros == 0)
						this.mensaje = "No existen registros que cumplan el criterio de busqueda.";
					else
						this.mensaje = "Se contabilizo un total de "
								+ this.totalRegistros
								+ " registros que concuerdan con el criterio de busqueda.";

				}

			}

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	public long getTotalRegistros() {
		return totalRegistros;
	}

	public List getClienteszonasList() {
		return listProvincias;
	}

	public void setClienteszonasList(List clienteszonasList) {
		this.listProvincias = clienteszonasList;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public List getListProvincias() {
		return listProvincias;
	}

	public List getListOrigen() {
		return listOrigen;
	}

	public List getListSubOrigen() {
		return listSubOrigen;
	}

	public List getListEstado() {
		return listEstado;
	}

	public List getListMotivos() {
		return listMotivos;
	}

	public List getListTipoCliente() {
		return listTipoCliente;
	}

	public List getListCategoria() {
		return listCategoria;
	}

	public List getListPreferencias() {
		return listPreferencias;
	}

	public List getListCampaniasActivas() {
		return listCampaniasActivas;
	}

	public List getListCampaniasAnteriores() {
		return listCampaniasAnteriores;
	}

	public List getListTeleMarketer() {
		return listTeleMarketer;
	}

	public BigDecimal getIdtelemark() {
		return idtelemark;
	}

	public void setIdtelemark(BigDecimal idtelemark) {
		this.idtelemark = idtelemark;
	}

	public BigDecimal getIdcampacabe() {
		return idcampacabe;
	}

	public void setIdcampacabe(BigDecimal idcampacabe) {
		this.idcampacabe = idcampacabe;
	}

	public String getIdclientedesde() {
		return idclientedesde;
	}

	public void setIdclientedesde(String idclientedesde) {
		this.idclientedesde = idclientedesde;
	}

	public String getIdclientehasta() {
		return idclientehasta;
	}

	public void setIdclientehasta(String idclientehasta) {
		this.idclientehasta = idclientehasta;
	}

	public String getFechaingresodesde() {
		return fechaingresodesde;
	}

	public void setFechaingresodesde(String fechaingresodesde) {
		this.fechaingresodesde = fechaingresodesde;
	}

	public String getFechaingresohasta() {
		return fechaingresohasta;
	}

	public void setFechaingresohasta(String fechaingresohasta) {
		this.fechaingresohasta = fechaingresohasta;
	}

	public BigDecimal getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(BigDecimal idprovincia) {
		this.idprovincia = idprovincia;
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

	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
	}

	public BigDecimal getIdmotivo() {
		return idmotivo;
	}

	public void setIdmotivo(BigDecimal idmotivo) {
		this.idmotivo = idmotivo;
	}

	public BigDecimal getIdorigen() {
		return idorigen;
	}

	public void setIdorigen(BigDecimal idorigen) {
		this.idorigen = idorigen;
	}

	public BigDecimal getIdsuborigen() {
		return idsuborigen;
	}

	public void setIdsuborigen(BigDecimal idsuborigen) {
		this.idsuborigen = idsuborigen;
	}

	public BigDecimal getIdtipoclie() {
		return idtipoclie;
	}

	public void setIdtipoclie(BigDecimal idtipoclie) {
		this.idtipoclie = idtipoclie;
	}

	public BigDecimal getIdcategoria() {
		return idcategoria;
	}

	public void setIdcategoria(BigDecimal idcategoria) {
		this.idcategoria = idcategoria;
	}

	public BigDecimal getIdpreferencia() {
		return idpreferencia;
	}

	public void setIdpreferencia(BigDecimal idpreferencia) {
		this.idpreferencia = idpreferencia;
	}

	public String getLimiteasignacion() {
		return limiteasignacion;
	}

	public void setLimiteasignacion(String limiteasignacion) {
		this.limiteasignacion = limiteasignacion;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public BigDecimal getIdcampacabeAnterior() {
		return idcampacabeAnterior;
	}

	public void setIdcampacabeAnterior(BigDecimal idcampacabeAnterior) {
		this.idcampacabeAnterior = idcampacabeAnterior;
	}

}
