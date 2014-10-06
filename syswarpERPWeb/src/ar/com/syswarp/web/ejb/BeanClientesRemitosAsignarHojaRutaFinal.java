/* 
   javabean para la entidad: pedidos_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Feb 12 08:43:04 GYT 2009 
   
   Para manejar la pagina: pedidos_cabeAbm.jsp
      
 */
package ar.com.syswarp.web.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;

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

public class BeanClientesRemitosAsignarHojaRutaFinal implements SessionBean,
		Serializable {
	static Logger log = Logger
			.getLogger(BeanClientesRemitosAsignarHojaRutaFinal.class);

	private SessionContext context;

	private BigDecimal idempresa = new BigDecimal(-1);

	private String sort = " 1 ASC ";

	private int limit = 15;

	private long offset = 0l;

	private long totalRegistros = 0l;

	private long totalPaginas = 0l;

	private long paginaSeleccion = 1l;

	private List pedidos_cabeList = new ArrayList();

	private String accion = "";

	private String ocurrencia = "";

	private String[] nrohojaarmado = new String[] {};

	private BigDecimal idpuesto = new BigDecimal(-1);

	private BigDecimal idcontadorcomprobante = new BigDecimal(-1);

	private String mensaje = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	boolean buscar = false;

	private String filtroHojarutafinal = "";

	private String filtroExpreso = "";

	private String filtroDepositoDestino = "";

	private String nameFile = "";

	private String usuarioalt;

	//

	private String nropallets = "";

	private String tipomov = "CI";

	private BigDecimal num_cnt = new BigDecimal(-1);

	private BigDecimal sucursal = new BigDecimal(-1);

	private String tipo = "";

	private String remitosgenerados = "";

	private String observaciones = "";

	private String tipopedido = "N";

	private List listExpresos = new ArrayList();

	private String archivoAndreani = "";

	private String archivoAndreaniZip = "";

	// EJV - Mantis 736 - 20110721 -->

	private List listClub = new ArrayList();

	private BigDecimal idclub = new BigDecimal(-1);

	// <--

	public BeanClientesRemitosAsignarHojaRutaFinal() {
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
		String entidad = "vclientesremitoshojarutafinal";
		String filtro = " WHERE tipo =  '" + this.tipopedido.toUpperCase()
				+ "'";
		String auxFiltro = "";
		try {

			this.listExpresos = remitoscliente.getClientesexpresosAll(500, 0,
					this.idempresa);

			// EJV - Mantis 736 - 20110721 -->
			this.listClub = Common.getClientes().getClientesClubAll(100, 0,
					this.idempresa);
			// <--

			if (this.accion.equalsIgnoreCase("asignarhojarutafinal")) {
				String[] resultado = new String[] { "", "", "" };
				Map parameters = new HashMap();

				// TODO: 20090312 / EJV - Pendiente de asignar el valor para
				// plantillaImpresionJRXML desde la entidad clientesexpresos.
				// Para poder llevar a cabo dicha asignac�n es necesario que al
				// momento de generar la hoja de armado se este filtrando para
				// un solo EXPRESO.

				if (!Common.esEntero(this.nropallets)
						|| new BigDecimal(this.nropallets).longValue() < 0) {

					resultado[0] = "Es necesario ingresar total de Pallets.";

				} else if (Common.setNotNull(this.filtroExpreso).equals("")) {

					resultado[0] = "Es necesario filtrar por expreso para poder ejecutar asignacion.";

					// EJV - Mantis 736 - 20110721 -->
				} else if (this.idclub.intValue() < 1) {

					resultado[0] = "Es necesario seleccionar club para poder ejecutar asignacion.";
					// <--
				} else if (this.nrohojaarmado != null
						&& this.nrohojaarmado.length != 0) {

					String plantillaImpresionJRXML = remitoscliente
							.getClientesExpresosReport(new BigDecimal(
									this.filtroExpreso), this.tipopedido, "HR",
									this.idempresa);

					// this.tipopedido
					// .equalsIgnoreCase("N") ?
					// "hoja_ruta_final_remitos_clientes_frame"
					// : "hoja_ruta_final_remitos_regalos_frame";
					//						

					Calendar calendar = new GregorianCalendar();
					Timestamp fechamov = new Timestamp(calendar
							.getTimeInMillis());

					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
					String time = sdf.format(calendar.getTime());
					nameFile = time + "-HRF-[###].pdf";

					resultado = remitoscliente.generarHojaRutaFinal(fechamov,
							this.tipomov, this.num_cnt, this.sucursal,
							this.tipo, this.observaciones,
							this.idcontadorcomprobante, this.nrohojaarmado,
							new BigDecimal(this.nropallets), this.tipopedido,
							this.nameFile, new BigDecimal(this.filtroExpreso),
							this.usuarioalt, this.idempresa);

					if (resultado[0].equalsIgnoreCase("OK")) {

						// nameFile += "-HRF-[" + resultado[2] + "].pdf";

						nameFile = nameFile.replace("###", resultado[2]);

						parameters.put("nrohojarutafinal", new BigDecimal(
								resultado[2]));
						parameters.put("usuario", this.usuarioalt);
						parameters
								.put("fechahojarutafinal", calendar.getTime());
						parameters.put("idempresa", this.idempresa.toString());

						this.remitosgenerados = resultado[1];

						log.info("remitosgenerados: " + this.remitosgenerados);

						byte[] bytes = Common.getReport()
								.getOpenReportImpresiones(
										plantillaImpresionJRXML, parameters);

						Common.getReport().saveByteToFile(
								bytes,
								Common.getReport().getClientesRemitosPath()
										+ nameFile);

					}

				} else
					resultado[0] = "Es necesario seleccionar al menos una hoja de armado.";

				if (resultado[0].equalsIgnoreCase("OK")) {
					this.mensaje = "Se generó correctamente hoja de ruta final nro.:"
							+ resultado[2] + ".";

					if (resultado[3].equalsIgnoreCase("SI")) {

						if (resultado[4].equalsIgnoreCase("")) {
							this.mensaje = this.mensaje
									+ " Fallo al crear archivo Andreani. ";
						} else {
							this.archivoAndreani = resultado[4];
							this.archivoAndreaniZip = resultado[5];
						}

					}

				} else {
					this.nameFile = "";
					this.mensaje = resultado[0];
				}

			}

			if (!this.filtroHojarutafinal.trim().equals("")) {
				auxFiltro += " AND nrohojaarmado = " + this.filtroHojarutafinal;
			}

			// EJV - 20100901 - Mantis 567 e implementacion de HRF
			// if (!this.filtroExpreso.trim().equals("")) {
			// auxFiltro += " AND UPPER(expreso) LIKE '"
			// + this.filtroExpreso.replaceAll("'", "%").toUpperCase()
			// + "%'  ";
			// }

			if (!this.filtroExpreso.trim().equals("")) {
				auxFiltro += " AND idexpreso =  " + this.filtroExpreso;
			}

			if (!this.filtroDepositoDestino.trim().equals("")) {
				auxFiltro += " AND UPPER(descrip_dt) LIKE '"
						+ this.filtroDepositoDestino.replaceAll("'", "%")
								.toUpperCase() + "%'  ";
			}

			// EJV - Mantis 736 - 20110721 -->
			if (this.idclub.intValue() > 0) {
				auxFiltro += " AND idclub = " + this.idclub;
			}
			// <--

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
						.getClientesRemitosHRFinalOcu(this.limit, this.offset,
								filtro, this.sort, this.idempresa);
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
						.getClientesRemitosHRFinalAll(this.limit, this.offset,
								this.sort, this.tipopedido, this.idempresa);
			}

			if (this.totalRegistros < 1 && this.mensaje.equals(""))
				this.mensaje = "No existen registros.";

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

	public String getFiltroHojarutafinal() {
		return filtroHojarutafinal;
	}

	public void setFiltroHojarutafinal(String filtroHojarutafinal) {
		this.filtroHojarutafinal = filtroHojarutafinal;
	}

	public BigDecimal getIdpuesto() {
		return idpuesto;
	}

	public void setIdpuesto(BigDecimal idpuesto) {
		this.idpuesto = idpuesto;
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

	public String getFiltroDepositoDestino() {
		return filtroDepositoDestino;
	}

	public void setFiltroDepositoDestino(String filtroDepositoDestino) {
		this.filtroDepositoDestino = filtroDepositoDestino;
	}

	public String[] getNrohojaarmado() {
		return nrohojaarmado;
	}

	public void setNrohojaarmado(String[] nrohojaarmado) {
		this.nrohojaarmado = nrohojaarmado;
	}

	public String getNropallets() {
		return nropallets;
	}

	public void setNropallets(String nropallets) {
		this.nropallets = nropallets;
	}

	public String getRemitosgenerados() {
		return remitosgenerados;
	}

	public void setRemitosgenerados(String remitosgenerados) {
		this.remitosgenerados = remitosgenerados;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

	public List getListExpresos() {
		return listExpresos;
	}

	public void setListExpresos(List listExpresos) {
		this.listExpresos = listExpresos;
	}

	public String getArchivoAndreani() {
		return archivoAndreani;
	}

	public void setArchivoAndreani(String archivoAndreani) {
		this.archivoAndreani = archivoAndreani;
	}

	public String getArchivoAndreaniZip() {
		return archivoAndreaniZip;
	}

	public void setArchivoAndreaniZip(String archivoAndreaniZip) {
		this.archivoAndreaniZip = archivoAndreaniZip;
	}

	// EJV - Mantis 736 - 20110721 -->

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
