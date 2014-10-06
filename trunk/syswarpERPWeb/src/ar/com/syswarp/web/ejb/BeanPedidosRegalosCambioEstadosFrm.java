/* 
 javabean para la entidad (Formulario): pedidosestados
 Copyrigth(r) sysWarp S.R.L. 
 Fecha de creacion: Tue Mar 27 11:09:46 GMT-03:00 2007 
 
 Para manejar la pagina: pedidosestadosFrm.jsp
 
 */
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

public class BeanPedidosRegalosCambioEstadosFrm implements SessionBean,
		Serializable {
	private SessionContext context;

	static Logger log = Logger
			.getLogger(BeanPedidosRegalosCambioEstadosFrm.class);

	private BigDecimal idempresa = new BigDecimal(-1);

	private String validar = "";

	private BigDecimal idpedido_regalos_cabe = new BigDecimal(-1);

	private BigDecimal idcliente = new BigDecimal(-1);

	private String cliente = "";

	private BigDecimal idestadoanterior = new BigDecimal(-1);

	private String estadoanterior = "";

	private BigDecimal idestadonuevo = new BigDecimal(-1);

	private String usuarioalt;

	private String usuarioact;

	private String mensaje = "";

	private String volver = "";

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String accion = "";

	private List listPedidosEstados = new ArrayList();

	private String tipopedido = "R";

	public BeanPedidosRegalosCambioEstadosFrm() {
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
			Clientes clientes = Common.getClientes();
			String[] resultado = new String[] { "", "" };
			boolean statusNotificacion = false;

			if (this.accion.equalsIgnoreCase("cambiarestado")) {

				resultado[0] = clientes.callPedidosRegalosCabeUpdateEstado(
						this.idpedido_regalos_cabe, this.idestadoanterior,
						this.idestadonuevo, this.idempresa, this.usuarioalt);

			}

			if (resultado[0].equalsIgnoreCase("OK")) {

				this.mensaje = "Cambio de estado correcto.";
				String asunto = "";
				String body = "";
				String sector = "";

				if (this.idestadonuevo.intValue() == 2) {
					asunto = "ARMADO DE ESQUEMA - Pedido Regalos Nº "
							+ idpedido_regalos_cabe;
					body = "\t\tPor la presente se notifica al sector compras que el pedido de regalos Nº "
							+ idpedido_regalos_cabe
							+ ", tiene asignado el estado ARMADO DE ESQUEMA.";
					sector = "compras";
				} else if (this.idestadonuevo.intValue() == 3) {
					asunto = "PENDIENTE - Pedido Regalos Nº "
							+ idpedido_regalos_cabe;
					body = "\t\tPor la presente se notifica al sector logistica que el pedido de regalos Nº "
							+ idpedido_regalos_cabe
							+ ", tiene asignado el estado ARMADO DE ESQUEMA.";
					sector = "logistica";
				} else if (this.idestadonuevo.intValue() == 4) {
					asunto = "REALIZADO - Pedido Regalos Nº "
							+ idpedido_regalos_cabe;
					body = "\t\tPor la presente se notifica al sector regalos que el pedido de regalos Nº "
							+ idpedido_regalos_cabe
							+ ", tiene asignado el estado REALIZADO.";
					sector = "regalos";
				} else if (this.idestadonuevo.intValue() == 99) {
					asunto = "ANULADO - Pedido Regalos Nº "
							+ idpedido_regalos_cabe;
					body = "\t\tPor la presente se notifica al sector regalos que el pedido de regalos Nº "
							+ idpedido_regalos_cabe
							+ ", tiene asignado el estado ANULADO.";
					sector = "regalos";
				}

				statusNotificacion = Common.sendMailRegalos(asunto, body,
						sector);

				if (!statusNotificacion) {

					this.mensaje += " No fue posible NOTFICAR sector " + sector
							+ " del cambio de estado efectuado.";

				}

				if (!resultado[1].equalsIgnoreCase("OK")
						&& !resultado[1].equalsIgnoreCase("")) {
					this.mensaje += " Se detectaron las siguientes alertas: "
							+ resultado[1];
				}

				this.getDatosPedido();

			} else
				this.mensaje = resultado[0];

		} catch (Exception ex) {
			log.error(" ejecutarSentenciaDML() : " + ex);
		}
	}

	public void getDatosPedido() {
		try {

			Iterator iterPedido = Common.getClientes()
					.getVPedidosRegalosEstadoPK(this.idpedido_regalos_cabe,
							this.tipopedido, this.idempresa).iterator();

			if (iterPedido != null && iterPedido.hasNext()) {

				String datos[] = (String[]) iterPedido.next();

				this.idestadoanterior = new BigDecimal(datos[1]);
				this.estadoanterior = datos[2];
				this.idcliente = new BigDecimal(datos[3]);
				this.cliente = datos[2];

			} else {

				this.mensaje = "Imposible recuperar estado actual del pedido seleccionado.";

			}

		} catch (Exception e) {
			log.error("getDatosPedidosestados()" + e);
		}
	}

	public boolean ejecutarValidacion() {
		try {

			if (!this.volver.equalsIgnoreCase("")) {
				response
						.sendRedirect("vPedidosRegalosEstadoAbm.jsp?tipopedido="
								+ this.tipopedido);
				return true;
			}

			this.getDatosPedido();

			this.listPedidosEstados = Common.getClientes()
					.getPedidosRegalosEstadosAll(100, 0, this.idempresa);

			if (!this.validar.equalsIgnoreCase("")) {

				if (!this.accion.equalsIgnoreCase("baja")) {
					// 1. nulidad de campos
					// 2. len 0 para campos nulos
					if (this.idcliente.longValue() < 1) {
						this.mensaje = "No se puede dejar vacio el campo Cliente  ";
						return false;
					}

					if (this.idestadonuevo.longValue() < 1) {
						this.mensaje = "Debe seleccionar un estado nuevo.";
						return false;
					}

					if (idestadoanterior.equals(this.idestadonuevo)) {
						this.mensaje = "El nuevo estado debe ser diferente del estado actual.";
						return false;
					}

					if (this.idestadonuevo.longValue() == 1) {
						this.mensaje = "No es posible asignar el estado seleccionado, el mismo solo se asigna automaticamente.";
						return false;
					}

					if (idestadoanterior.longValue() == 99) {
						this.mensaje = "El pedido ya fue anulado, no es posible cambiar su estado.";
						return false;
					}

					if (Common.setNotNull(this.tipopedido).equals("")) {
						this.mensaje = "Tipo de pedido no declarado.";
						return false;
					}

				}

				this.ejecutarSentenciaDML();
			}

		} catch (Exception e) {
			log.error(" ejecutarValidacion(): " + e);
		}
		return true;
	}

	public BigDecimal getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(BigDecimal idempresa) {
		this.idempresa = idempresa;
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

	public BigDecimal getIdpedido_regalos_cabe() {
		return idpedido_regalos_cabe;
	}

	public void setIdpedido_regalos_cabe(BigDecimal idpedido_regalos_cabe) {
		this.idpedido_regalos_cabe = idpedido_regalos_cabe;
	}

	public BigDecimal getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(BigDecimal idcliente) {
		this.idcliente = idcliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getIdestadoanterior() {
		return idestadoanterior;
	}

	public void setIdestadoanterior(BigDecimal idestadoanterior) {
		this.idestadoanterior = idestadoanterior;
	}

	public String getEstadoanterior() {
		return estadoanterior;
	}

	public void setEstadoanterior(String estadoanterior) {
		this.estadoanterior = estadoanterior;
	}

	public BigDecimal getIdestadonuevo() {
		return idestadonuevo;
	}

	public void setIdestadonuevo(BigDecimal idestadonuevo) {
		this.idestadonuevo = idestadonuevo;
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

	public List getListPedidosEstados() {
		return listPedidosEstados;
	}

	public void setListPedidosEstados(List listPedidosEstado) {
		this.listPedidosEstados = listPedidosEstado;
	}

	public String getTipopedido() {
		return tipopedido;
	}

	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}

}
