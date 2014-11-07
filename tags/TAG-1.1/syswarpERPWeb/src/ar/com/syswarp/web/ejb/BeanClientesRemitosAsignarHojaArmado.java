/* 
   javabean para la entidad: pedidos_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Feb 12 08:43:04 GYT 2009 
   
   Para manejar la pagina: pedidos_cabeAbm.jsp
      
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

import java.text.SimpleDateFormat;
import java.util.*;
import java.math.*;

import ar.com.syswarp.ejb.*;
import ar.com.syswarp.api.Common;

public class BeanClientesRemitosAsignarHojaArmado implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanClientesRemitosAsignarHojaArmado.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private BigDecimal idestado = new BigDecimal(1);

	private String sort = " 23 ASC ";

	private int limit = 350;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 1l;

	private long paginaSeleccion = 1l;

	private List pedidos_cabeList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String[] idremitocliente = new String[] {};

	private BigDecimal idpuesto = new BigDecimal(-1);

	private BigDecimal idcontadorcomprobante = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String filtroSucursal = "";

	private String filtroRemito = "";

	private String filtroCliente = "";

	private String filtroMes = "";

	private String filtroAno = "";

	private String filtroExpreso = "";

	private String filtroZona = "";

	private String filtroNroctacte = "";

	private String filtroDepOrigen = "";

	private String filtroLocalidad = "";

	private String filtroExpresoZona = "";

	private String filtroGrupoArmado = "";

	private String nameFile = "";

	private String usuarioalt;

	private Hashtable htColorGrupoArmado = new Hashtable();

	private List depositosList = new ArrayList();

	private List expresosList = new ArrayList();
	// EJV - Mantis 487 - 20100107
	private List listExpresosZonas = new ArrayList();

	private String tipopedido = "N";

	// EJV - Mantis 735 - 20110721 -->

	private List listClub = new ArrayList();

	private BigDecimal idclub = new BigDecimal(-1);

	// <--

	public BeanClientesRemitosAsignarHojaArmado() {
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
		// RequestDispatcher dispatcher = null;
		Clientes remitoscliente = Common.getClientes();
		String entidad = "vclientesremitoshojaarmado";
		this.setHashColor(this.htColorGrupoArmado);

		String filtro = " WHERE tipopedido =  '" + this.tipopedido + "' ";
		String auxFiltro = "";
		try {

			this.depositosList = Common.getStock().getStockdepositosAll(500, 0,
					this.idempresa);
			this.expresosList = remitoscliente.getClientesexpresosAll(500, 0,
					this.idempresa);
			// EJV - Mantis 487 - 20100107
			this.listExpresosZonas = remitoscliente
					.getClientesExpresosZonasAll(500, 0, this.idempresa);

			// EJV - Mantis 735 - 20110721 -->
			this.listClub = Common.getClientes().getClientesClubAll(100, 0,
					this.idempresa);
			// <--

			if (this.filtroDepOrigen.equals("")
					|| this.filtroExpresoZona.equals("")
					|| !this.validarPeriodo()) {

				this.mensaje = "Es necesario filtrar por Fecha, Expreso/Zona y Depósito Origen validos.";

			} else {

				if (this.accion.equalsIgnoreCase("asignarhojaarmado")) {
					String[] resultado = new String[] { "", "", "" };
					Map parameters = new HashMap();
					// TODO: 20090312 / EJV - Pendiente de asignar el valor para
					// plantillaImpresionJRXML desde la entidad
					// clientesexpresos.
					// Para poder llevar a cabo dicha asignac�n es necesario que
					// al
					// momento de generar la hoja de armado se este filtrando
					// para
					// un solo EXPRESO.
					String plantillaImpresionJRXML = "";
					Calendar calendar = new GregorianCalendar();

					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
					String time = sdf.format(calendar.getTime());
					nameFile = time;

					// if (this.filtroDepOrigen.equals("")
					// || this.filtroExpresoZona.equals("")
					// || this.validarPeriodo()) {
					//
					// resultado[0] =
					// "Es necesario filtrar por Expreso/Zona, Depósito Origen y Fecha valida.";
					//
					// } else

					if (this.idremitocliente == null
							|| this.idremitocliente.length < 1) {

						resultado[0] = "Es necesario seleccionar al menos un remito para hoja de armado.";

					} else if (this.idclub.intValue() < 1) {

						resultado[0] = "Es necesario seleccionar club.";

					} else {

						resultado = remitoscliente
								.setHojaArmadoRemitosClientes(
										this.idremitocliente, this.idempresa,
										this.usuarioalt);

						if (this.tipopedido.equalsIgnoreCase("N")) {

							plantillaImpresionJRXML = "hoja_armado_frame";

						} else {

							plantillaImpresionJRXML = "hoja_armado_reg_frame";

						}

						if (resultado[0].equalsIgnoreCase("OK")) {

							nameFile += "-HA-[" + resultado[1] + "].pdf";

							parameters.put("nrohojaarmado", new BigDecimal(
									resultado[1]));
							parameters.put("usuario", this.usuarioalt);
							parameters.put("nroctacte", new BigDecimal(
									999999999));
							parameters.put("fechahojaarmado", calendar
									.getTime());
							parameters.put("idempresa", this.idempresa
									.toString());

							byte[] bytes = Common
									.getReport()
									.getOpenReportImpresiones(
											plantillaImpresionJRXML, parameters);

							Common.getReport().saveByteToFile(
									bytes,
									Common.getReport().getClientesRemitosPath()
											+ nameFile);

							Common.getReport()
									.saveByteToFile(
											bytes,
											Common.getReport()
													.getRepositorioDocsPath()
													.replaceAll("TIPODOC",
															"hojaarmado")
													+ nameFile);

						}

					}

					if (resultado[0].equalsIgnoreCase("OK")) {
						this.mensaje = "Se generó correctamente hoja de armado nro.:"
								+ resultado[1];
					} else {
						this.nameFile = "";
						this.mensaje = resultado[0];
					}

				}

				if (this.idclub.intValue() > 0) {
					auxFiltro = " AND idclub = " + this.idclub;
				}

				if (!this.filtroSucursal.trim().equals("")) {
					auxFiltro += " AND nrosucursal = " + this.filtroSucursal;
				}

				if (!this.filtroRemito.trim().equals("")) {
					auxFiltro += " AND nroremitocliente = " + this.filtroRemito;
				}

				if (!this.filtroCliente.trim().equals("")) {
					auxFiltro += " AND idcliente = " + this.filtroCliente;
				}

				if (!this.filtroMes.trim().equals("")
						&& !this.filtroAno.trim().equals("")) {

					auxFiltro += " AND DATE_PART ('MONTH' , fecharemito) = "
							+ Integer.parseInt(this.filtroMes)
							+ " AND DATE_PART ('YEAR' , fecharemito) = "
							+ Integer.parseInt(this.filtroAno);
				}

				if (!this.filtroExpreso.trim().equals("")) {
					auxFiltro += " AND idexpreso = " + this.filtroExpreso + " ";
				}

				// EJV - Mantis 487 - 20100107
				if (!this.filtroExpresoZona.trim().equals("")) {
					auxFiltro += " AND idexpresozona = "
							+ this.filtroExpresoZona + " ";
				}
				// --

				if (!this.filtroZona.trim().equals("")) {
					auxFiltro += " AND UPPER(zona) LIKE '"
							+ this.filtroZona.replaceAll("'", "%")
									.toUpperCase() + "%'  ";
				}

				if (!this.filtroNroctacte.trim().equals("")) {
					auxFiltro += " AND nroctacte = " + this.filtroNroctacte;
				}

				if (!this.filtroDepOrigen.trim().equals("")) {
					auxFiltro += " AND codigo_dt = " + this.filtroDepOrigen;
				}

				if (!this.filtroLocalidad.trim().equals("")) {
					auxFiltro += " AND UPPER(localidad) LIKE '"
							+ this.filtroLocalidad.replaceAll("'", "%")
									.toUpperCase() + "%'  ";
				}

				if (!this.filtroGrupoArmado.trim().equals("")) {
					auxFiltro += " AND grupoarmado = '"
							+ this.filtroGrupoArmado.toUpperCase() + "' ";
				}

				auxFiltro = auxFiltro.trim();

				if (!auxFiltro.equals("")) {

					filtro += auxFiltro;

					this.totalRegistros = remitoscliente.getTotalEntidadFiltro(
							entidad, filtro, this.idempresa);
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
					this.pedidos_cabeList = remitoscliente
							.getClientesRemitosPendientesHAOcu(this.limit,
									this.offset, this.idestado,
									this.tipopedido, filtro, this.sort,
									this.idempresa);
				} else {
					this.totalRegistros = remitoscliente.getTotalEntidadFiltro(
							entidad, filtro, this.idempresa);
					this.totalPaginas = (this.totalRegistros / this.limit) + 1;
					if (this.totalPaginas < this.paginaSeleccion)
						this.paginaSeleccion = this.totalPaginas;
					this.offset = (this.paginaSeleccion - 1) * this.limit;
					if (this.totalRegistros == this.limit) {
						this.offset = 0;
						this.totalPaginas = 1;
					}

					this.pedidos_cabeList = remitoscliente
							.getClientesRemitosPendientesHAAll(this.limit,
									this.offset, this.idestado,
									this.tipopedido, this.sort, this.idempresa);
				}

			} //

			if (this.totalRegistros < 1 && this.mensaje.equals(""))
				this.mensaje = "No existen registros.";

		} catch (Exception e) {
			log.error("ejecutarValidacion()" + e);
		}
		return true;
	}

	private boolean validarPeriodo() {

		boolean retorno = true;

		try {

			if (!Common.esEntero(this.filtroMes)) {
				retorno = false;
			} else if (Integer.parseInt(this.filtroMes) > 31
					|| Integer.parseInt(this.filtroMes) < 1) {
				retorno = false;
			} else if (!Common.esEntero(this.filtroAno)) {
				retorno = false;
			} else if (Integer.parseInt(this.filtroAno) < 0) {
				retorno = false;
			}

		} catch (Exception e) {
			retorno = false;
			log.error("validarPeriodo():  " + e);

		}

		return retorno;
	}

	private void setHashColor(Hashtable ht) {

		try {

			ht.put("#610b0b", "#610b0b");
			ht.put("#8a0808", "#8a0808");
			ht.put("#b40404", "#b40404");
			ht.put("#df0101", "#df0101");
			ht.put("#ff0000", "#ff0000");
			ht.put("#fe2e2e", "#fe2e2e");
			ht.put("#fa5858", "#fa5858");
			ht.put("#f78181", "#f78181");
			ht.put("#f5a9a9", "#f5a9a9");
			ht.put("#f6cece", "#f6cece");
			ht.put("#f8e0e0", "#f8e0e0");
			ht.put("#fbefef", "#fbefef");
			ht.put("#61380b", "#61380b");
			ht.put("#8a4b08", "#8a4b08");
			ht.put("#b45f04", "#b45f04");
			ht.put("#df7401", "#df7401");
			ht.put("#ff8000", "#ff8000");
			ht.put("#fe9a2e", "#fe9a2e");
			ht.put("#faac58", "#faac58");
			ht.put("#f7be81", "#f7be81");
			ht.put("#f5d0a9", "#f5d0a9");
			ht.put("#f6e3ce", "#f6e3ce");
			ht.put("#f8ece0", "#f8ece0");
			ht.put("#fbf5ef", "#fbf5ef");
			ht.put("#5e610b", "#5e610b");
			ht.put("#868A08", "#868A08");
			ht.put("#AEB404", "#AEB404");
			ht.put("#D7DF01", "#D7DF01");
			ht.put("#FFFF00", "#FFFF00");
			ht.put("#F7FE2E", "#F7FE2E");
			ht.put("#F4FA58", "#F4FA58");
			ht.put("#F3F781", "#F3F781");
			ht.put("#F2F5A9", "#F2F5A9");
			ht.put("#F5F6CE", "#F5F6CE");
			ht.put("#F7F8E0", "#F7F8E0");
			ht.put("#FBFBEF", "#FBFBEF");
			ht.put("#38610B", "#38610B");
			ht.put("#4B8A08", "#4B8A08");
			ht.put("#5FB404", "#5FB404");
			ht.put("#74DF00", "#74DF00");
			ht.put("#80FF00", "#80FF00");
			ht.put("#9AFE2E", "#9AFE2E");
			ht.put("#ACFA58", "#ACFA58");
			ht.put("#BEF781", "#BEF781");
			ht.put("#D0F5A9", "#D0F5A9");
			ht.put("#E3F6CE", "#E3F6CE");
			ht.put("#ECF8E0", "#ECF8E0");
			ht.put("#F5FBEF", "#F5FBEF");
			ht.put("#0B610B", "#0B610B");
			ht.put("#088A08", "#088A08");
			ht.put("#04B404", "#04B404");
			ht.put("#01DF01", "#01DF01");
			ht.put("#00FF00", "#00FF00");
			ht.put("#2EFE2E", "#2EFE2E");
			ht.put("#58FA58", "#58FA58");
			ht.put("#81F781", "#81F781");
			ht.put("#A9F5A9", "#A9F5A9");
			ht.put("#CEF6CE", "#CEF6CE");
			ht.put("#E0F8E0", "#E0F8E0");
			ht.put("#EFFBEF", "#EFFBEF");
			ht.put("#0B6138", "#0B6138");
			ht.put("#088A4B", "#088A4B");
			ht.put("#04B45F", "#04B45F");
			ht.put("#01DF74", "#01DF74");
			ht.put("#00FF80", "#00FF80");
			ht.put("#2EFE9A", "#2EFE9A");
			ht.put("#58FAAC", "#58FAAC");
			ht.put("#81F7BE", "#81F7BE");
			ht.put("#A9F5D0", "#A9F5D0");
			ht.put("#CEF6E3", "#CEF6E3");
			ht.put("#E0F8EC", "#E0F8EC");
			ht.put("#EFFBF5", "#EFFBF5");
			ht.put("#0B615E", "#0B615E");
			ht.put("#088A85", "#088A85");
			ht.put("#04B4AE", "#04B4AE");
			ht.put("#01DFD7", "#01DFD7");
			ht.put("#00FFFF", "#00FFFF");
			ht.put("#2EFEF7", "#2EFEF7");
			ht.put("#58FAF4", "#58FAF4");
			ht.put("#81F7F3", "#81F7F3");
			ht.put("#A9F5F2", "#A9F5F2");
			ht.put("#CEF6F5", "#CEF6F5");
			ht.put("#E0F8F7", "#E0F8F7");
			ht.put("#EFFBFB", "#EFFBFB");
			ht.put("#0B3861", "#0B3861");
			ht.put("#084B8A", "#084B8A");
			ht.put("#045FB4", "#045FB4");
			ht.put("#0174DF", "#0174DF");
			ht.put("#0080FF", "#0080FF");
			ht.put("#2E9AFE", "#2E9AFE");
			ht.put("#58ACFA", "#58ACFA");
			ht.put("#81BEF7", "#81BEF7");
			ht.put("#A9D0F5", "#A9D0F5");
			ht.put("#CEE3F6", "#CEE3F6");
			ht.put("#E0ECF8", "#E0ECF8");
			ht.put("#EFF5FB", "#EFF5FB");
			ht.put("#0B0B61", "#0B0B61");
			ht.put("#08088A", "#08088A");
			ht.put("#0404B4", "#0404B4");
			ht.put("#0101DF", "#0101DF");
			ht.put("#0000FF", "#0000FF");
			ht.put("#2E2EFE", "#2E2EFE");
			ht.put("#5858FA", "#5858FA");
			ht.put("#8181F7", "#8181F7");
			ht.put("#A9A9F5", "#A9A9F5");
			ht.put("#CECEF6", "#CECEF6");
			ht.put("#E0E0F8", "#E0E0F8");
			ht.put("#EFEFFB", "#EFEFFB");
			ht.put("#380B61", "#380B61");
			ht.put("#4B088A", "#4B088A");
			ht.put("#5F04B4", "#5F04B4");
			ht.put("#7401DF", "#7401DF");
			ht.put("#8000FF", "#8000FF");
			ht.put("#9A2EFE", "#9A2EFE");
			ht.put("#AC58FA", "#AC58FA");
			ht.put("#BE81F7", "#BE81F7");
			ht.put("#D0A9F5", "#D0A9F5");
			ht.put("#E3CEF6", "#E3CEF6");
			ht.put("#ECE0F8", "#ECE0F8");
			ht.put("#F5EFFB", "#F5EFFB");
			ht.put("#610B5E", "#610B5E");
			ht.put("#8A0886", "#8A0886");
			ht.put("#B404AE", "#B404AE");
			ht.put("#DF01D7", "#DF01D7");
			ht.put("#FF00FF", "#FF00FF");
			ht.put("#FE2EF7", "#FE2EF7");
			ht.put("#FA58F4", "#FA58F4");
			ht.put("#F781F3", "#F781F3");
			ht.put("#F5A9F2", "#F5A9F2");
			ht.put("#F6CEF5", "#F6CEF5");
			ht.put("#F8E0F7", "#F8E0F7");
			ht.put("#FBEFFB", "#FBEFFB");
			ht.put("#610B38", "#610B38");
			ht.put("#8A084B", "#8A084B");
			ht.put("#B4045F", "#B4045F");
			ht.put("#DF0174", "#DF0174");
			ht.put("#FF0080", "#FF0080");
			ht.put("#FE2E9A", "#FE2E9A");
			ht.put("#FA58AC", "#FA58AC");
			ht.put("#F781BE", "#F781BE");
			ht.put("#F5A9D0", "#F5A9D0");
			ht.put("#F6CEE3", "#F6CEE3");
			ht.put("#F8E0EC", "#F8E0EC");
			ht.put("#FBEFF5", "#FBEFF5");

		} catch (Exception e) {
			log.error("setHashColor(ht): " + e);
		}

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

	public List getPedidos_cabeList() {
		return pedidos_cabeList;
	}

	public void setPedidos_cabeList(List pedidos_cabeList) {
		this.pedidos_cabeList = pedidos_cabeList;
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

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public BigDecimal getIdestado() {
		return idestado;
	}

	public void setIdestado(BigDecimal idestado) {
		this.idestado = idestado;
	}

	public String getFiltroCliente() {
		return filtroCliente;
	}

	public void setFiltroCliente(String filtroCliente) {
		this.filtroCliente = filtroCliente;
	}

	public String getFiltroZona() {
		return filtroZona;
	}

	public void setFiltroZona(String filtroZona) {
		this.filtroZona = filtroZona;
	}

	public String getFiltroLocalidad() {
		return filtroLocalidad;
	}

	public void setFiltroLocalidad(String filtroLocalidad) {
		this.filtroLocalidad = filtroLocalidad;
	}

	// EJV - Mantis 487 - 20100107

	public String getFiltroExpresoZona() {
		return filtroExpresoZona;
	}

	public void setFiltroExpresoZona(String filtroExpresoZona) {
		this.filtroExpresoZona = filtroExpresoZona;
	}

	// --

	public void setIdpuesto(BigDecimal idpuesto) {
		this.idpuesto = idpuesto;
	}

	public BigDecimal getIdpuesto() {
		return idpuesto;
	}

	public BigDecimal getIdcontadorcomprobante() {
		return idcontadorcomprobante;
	}

	public void setIdcontadorcomprobante(BigDecimal idcontadorcomprobante) {
		this.idcontadorcomprobante = idcontadorcomprobante;
	}

	public String getUsuarioalt() {
		return usuarioalt;
	}

	public void setUsuarioalt(String usuarioalt) {
		this.usuarioalt = usuarioalt;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public String getFiltroExpreso() {
		return filtroExpreso;
	}

	public void setFiltroExpreso(String filtroExpreso) {
		this.filtroExpreso = filtroExpreso;
	}

	public String getFiltroSucursal() {
		return filtroSucursal;
	}

	public void setFiltroSucursal(String filtroSucursal) {
		this.filtroSucursal = filtroSucursal;
	}

	public String getFiltroRemito() {
		return filtroRemito;
	}

	public void setFiltroRemito(String filtroRemito) {
		this.filtroRemito = filtroRemito;
	}

	public String getFiltroMes() {
		return filtroMes;
	}

	public void setFiltroMes(String filtroMes) {
		this.filtroMes = filtroMes;
	}

	public String getFiltroAno() {
		return filtroAno;
	}

	public void setFiltroAno(String filtroAno) {
		this.filtroAno = filtroAno;
	}

	public String getFiltroDepOrigen() {
		return filtroDepOrigen;
	}

	public void setFiltroDepOrigen(String filtroDepOrigen) {
		this.filtroDepOrigen = filtroDepOrigen;
	}

	public String getFiltroNroctacte() {
		return filtroNroctacte;
	}

	public void setFiltroNroctacte(String filtroNroctacte) {
		this.filtroNroctacte = filtroNroctacte;
	}

	public String getFiltroGrupoArmado() {
		return filtroGrupoArmado;
	}

	public void setFiltroGrupoArmado(String filtroGrupoArmado) {
		this.filtroGrupoArmado = filtroGrupoArmado;
	}

	public Hashtable getHtColorGrupoArmado() {
		return htColorGrupoArmado;
	}

	public void setHtColorGrupoArmado(Hashtable htColorGrupoArmado) {
		this.htColorGrupoArmado = htColorGrupoArmado;
	}

	public String[] getIdremitocliente() {
		return idremitocliente;
	}

	public void setIdremitocliente(String[] idremitocliente) {
		this.idremitocliente = idremitocliente;
	}

	public List getDepositosList() {
		return depositosList;
	}

	public List getExpresosList() {
		return expresosList;
	}

	// EJV - Mantis 487 - 20100107
	public List getListExpresosZonas() {
		return listExpresosZonas;
	}

	public void setListExpresosZonas(List listExpresosZonas) {
		this.listExpresosZonas = listExpresosZonas;
	}

	// --

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

	// EJV - Mantis 735 - 20110721 -->

	public List getListClub() {
		return listClub;
	}

	public BigDecimal getIdclub() {
		return idclub;
	}

	public void setIdclub(BigDecimal idclub) {
		this.idclub = idclub;
	}

	// <--

}
