<%@page language="java"%>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: pedidos_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 02 09:51:28 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias 


*/ 

%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="ar.com.syswarp.api.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ include file="session.jspf"%>
<% 
try{
Strings str = new Strings();

String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanPedidos_cabeSASFrm" scope="page" />
	<head>
		<title>Pedidos Clientes</title>
		 <link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
		<script language="JavaScript" src="vs/overlib/overlib.js"></script>
		
		<script>
		function mostrarLOVDETA(pagina) {
	     frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=800,height=450,status=yes');
	     if (frmLOV.opener == null) 
		 frmLOV.opener = self;
         }	
		 </script>
		 
		 <script>
		function validarCliente(){   
        if (document.frm.idcliente.value==0 || document.frm.idcliente.value==""){
           alert("Seleccione primero el Cliente");	 		
        }else{
	       abreVentanaNombre("clientesclientesDetalleFrm.jsp?idcliente=" + document.frm.idcliente.value , 'Cliente',700, 400);  
             }	 
        }
        </script>
		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
	<BODY>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Captura de Pedidos de Clientes SAS";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 BPF.setSession(session);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.ejecutarValidacion();


   String idcliente =  request.getParameter("idcliente");
if(idcliente!=null){
   session.setAttribute("idcliente",idcliente);
   idcliente =  session.getAttribute("idcliente").toString(); 
   BPF.setIdcliente(new BigDecimal(idcliente));
     
 } 

 %>
		<form action="pedidos_cabeSASFrm.jsp" method="post" name="frm">
			<input name="accion" type="hidden" value="">
			<input name="idpedido_cabe" type="hidden"
				value="<%=BPF.getIdpedido_cabe()%>">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
							<tr class="text-globales">
								<td>
									<table width="100%" border="0"  cellpadding="0"
										cellspacing="0" class="text-globales">
										<tr>
											<td width="367"><%= titulo %>&nbsp;</td>
											<td width="467">&nbsp;											</td>
											<td width="50">&nbsp;											</td>
											<td width="43">
												<div align="center"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="22" height="22" title="Limpiar datos" onClick="if(confirm('Confirma limpiar datos ?')) window.location = 'pedidos_cabeSASFrm.jsp'" style="cursor:pointer"></div>
										  </td>
											<td width="42">
												<div align="center">
													<a href="javascript:validarCliente()"><img
														src="../imagenes/default/gnome_tango/actions/contact-new.png"
														title="Datos del Cliente" width="22" height="22" border="0"></a>
												</div>
										  </td>
										</tr>
								  </table>
									
								</td>
							</tr>
					  </table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold-rojo">
								<td class="fila-det-border">&nbsp;								</td>
								<td colspan="3" class="fila-det-border">
									<jsp:getProperty name="BPF" property="mensaje" />
&nbsp;																								</td>
							</tr>
							<input name="idestado" type="hidden" id="idestado" value="1">
							<tr class="fila-det">
								<td width="11%" class="fila-det-border">
									Cliente: (*)								</td>
								<td width="37%" class="fila-det-border">
									<table width="79%" border="0">
										<tr class="fila-det-border">
											<td width="61%">
												<input name="cliente" type="text" class="campo" id="cliente"
													value="<%=BPF.getCliente()%>" size="30" readonly>											</td>
											<td width="39%">
												<img src="../imagenes/default/gnome_tango/actions/filefind.png"
													width="22" height="22"
													onClick="abrirVentana('../Clientes/lov_clientes.jsp', 'clientes', 800, 400)"
													style="cursor:pointer">											</td>
											<input name="idcliente" type="hidden" id="idcliente"
												value="<%=BPF.getIdcliente()%>">
										</tr>
							  </table>								</td>
								<td width="13%" class="fila-det-border">
									Fecha (*)								</td>
								<td width="39%" class="fila-det-border">
									<table width="35%" border="0">
										<tr class="fila-det-border">
											<td width="50%">
												<input name="fechapedido" type="text" class="cal-TextBox"
													id="fechapedido" onFocus="this.blur()"
													value="<%=BPF.getFechapedido()%>" size="12"
													maxlength="12" readonly>											</td>
											<td width="50%">
												<a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechapedido','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle">												</a>											</td>
											<input name="idsucuclie" type="hidden" id="idsucuclie"
												value="<%=BPF.getIdsucuclie()%>">
										</tr>
							  </table>								</td>
							</tr>
							<tr class="fila-det">
								<td width="11%" class="fila-det-border">
									Sucursal: (*)								</td>
								<td width="37%" class="fila-det-border">
									<table width="79%" border="0">
										<tr class="fila-det-border">
											<td width="61%">
												<input name="sucursal" type="text" class="campo"
													id="sucursal" value="<%=BPF.getSucursal()%>" size="30"
													readonly>											</td>
											<td width="39%">
												<img src="../imagenes/default/gnome_tango/actions/filefind.png"
													width="22" height="22"
													onClick="mostrarLOV('../Clientes/lov_sucursal.jsp')"
													style="cursor:pointer">											</td>
											<input name="idsucursal" type="hidden" id="idsucursal"
												value="<%=BPF.getIdsucursal()%>">
										</tr>
							  </table>								</td>
								<td width="13%" class="fila-det-border">Prioridad(*)</td>
								<td width="39%" class="fila-det-border"><table width="53%" border="0">
                                  <tr class="fila-det-border">
                                    <td width="61%"><select name="idprioridad" class="campo">
                                      <option value="-1">Seleccionar</option>
                                      <% 
								  Iterator iterprioridad = BPF.getListPrioridades().iterator();
								  while(iterprioridad.hasNext()){
								    String [] datosPrioridad = (String[])iterprioridad.next();
								  %>
                                      <option value="<%= datosPrioridad[0] %>" <%= datosPrioridad[0].equals( BPF.getIdprioridad().toString()) ? "selected" : "" %>><%= datosPrioridad[1] %></option>
                                      <%  
								  }%>
                                    </select></td>
                                    <td width="39%">&nbsp;</td>
                                    <input name="idsucuclie3" type="hidden" id="idsucuclie3"
												value="<%=BPF.getIdsucuclie()%>" />
                                  </tr>
                                </table>								</td>
							</tr>
							<tr class="fila-det">
								<td width="11%" height="28" class="fila-det-border">
									Zona: (*)								</td>
								<td width="37%" class="fila-det-border">
									<table width="78%" border="0">
										<tr class="fila-det-border">
											<td width="61%">
												<input name="expreso" type="text" class="campo" id="expreso"
													value="<%=BPF.getExpreso()%>" size="30" readonly>											</td>
											<td width="39%">
												<img src="../imagenes/default/gnome_tango/actions/filefind.png"
													title="Modificar zona ..." width="22" height="22" style="cursor:pointer"
													onClick="mostrarLOV('../Clientes/lov_expreso.jsp')">											</td>
											<input name="idexpreso" type="hidden" id="idexpreso"
												value="<%=BPF.getIdexpreso()%>">
										</tr>
							  </table>								</td>
								<td width="13%" class="fila-det-border"> Domicilio: (*) </td>
								<td width="39%" class="fila-det-border"><table width="53%" border="0">
                                    <tr class="fila-det-border">
                                      <td width="61%"><input name="nombre_suc" type="text" class="campo"
													id="nombre_suc" value="<%=BPF.getNombre_suc()%>" size="30"
													readonly>
                                      </td>
                                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
													title="Modificar sucursal cliente ..." width="22" height="22" style="cursor:pointer"
													onClick="mostrarLOV('../Clientes/lov_sucuclie.jsp')"> </td>
                                      <input name="idsucuclie" type="hidden" id="idsucuclie"
												value="<%=BPF.getIdsucuclie()%>">
                                    </tr>
                                </table></td>
							</tr>
							<tr class="fila-det">
								<td width="11%" class="fila-det-border">
									Condicion: (*)								</td>
								<td width="37%" class="fila-det-border">
									<table width="78%" border="0">
										<tr class="fila-det-border">
											<td width="61%">
												<input name="condicion" type="text" class="campo"
													id="condicion" value="<%=BPF.getCondicion()%>" size="30"
													readonly>											</td>
											<td width="39%">
												<img src="../imagenes/default/gnome_tango/actions/filefind.png"
													title="Modificar condición de venta ..." width="22" height="22" style="cursor:pointer"
													onClick="mostrarLOV('../Clientes/lov_condventa.jsp')">											</td>
											<input name="idcondicion" type="hidden" id="idcondicion"
												value="<%=BPF.getIdcondicion()%>">
										</tr>
							  </table>								</td>
								<td width="13%" class="fila-det-border"> Vendedor: (*) </td>
								<td width="39%" class="fila-det-border"><table width="53%" border="0">
                                    <tr class="fila-det-border">
                                      <td width="61%"><input name="vendedor" type="text" class="campo"
													id="vendedor" value="<%=BPF.getVendedor()%>" size="30"
													readonly>
                                      </td>
                                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
													title="Modificar vendedor ... " width="22" height="22" style="cursor:pointer"
													onClick="mostrarLOV('../Clientes/lov_vendedor.jsp')"> </td>
                                      <input name="idvendedor" type="hidden" id="idvendedor"
												value="<%=BPF.getIdvendedor()%>">
                                    </tr>
                                </table></td>
							</tr>
							<tr class="fila-det">
								<td width="11%" class="fila-det-border">
									Moneda: (*)								</td>
								<td width="37%" class="fila-det-border">
									<table width="78%" border="0">
										<tr class="fila-det-border">
											<td width="61%">
												<input name="moneda" type="text" class="campo" id="moneda"
													value="<%=BPF.getMoneda()%>" size="30" readonly>											</td>
											<td width="39%">
												<img src="../imagenes/default/gnome_tango/actions/filefind.png"
													title="Modificar moneda ..." width="22" height="22" style="cursor:pointer"
													onClick="mostrarLOV('../Clientes/lov_moneda.jsp')">											</td>
											<input name="idmoneda" type="hidden" id="idmoneda"
												value="<%=BPF.getIdmoneda()%>">
										</tr>
							  </table>								</td>
								<td width="13%" class="fila-det-border"> Lista: (*) </td>
								<td width="39%" class="fila-det-border"><table width="54%" border="0">
                                    <tr class="fila-det-border">
                                      <td width="61%"><input name="lista" type="text" class="campo" id="lista2"
													value="<%=BPF.getLista()%>" size="30" readonly>
                                      </td>
                                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
													title="Modificar lista ..." width="22" height="22" style="cursor:pointer"
													onClick="mostrarLOV('../Clientes/lov_lista.jsp')"> </td>
                                      <input name="idlista" type="hidden" id="idlista3"
												value="<%=BPF.getIdlista()%>">
                                    </tr>
                                </table></td>
							</tr>
							<tr class="fila-det">
							  <td class="fila-det-border">Tipo de Iva : (*) </td>
							  <td class="fila-det-border"><table width="78%" border="0">
                                <tr class="fila-det-border">
                                  <td width="61%"><input name="tipoiva" type="text" class="campo" id="tipoiva"
													value="<%=BPF.getTipoiva()%>" size="30" readonly>                                  </td>
                                  <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
													title="Modificar tipo de iva ..." width="22" height="22" style="cursor:pointer"
													onClick="abrirVentana('lov_Tipoiva.jsp','tipoiva', 700, 400)"> </td>
                                  <input name="idtipoiva" type="hidden" id="idtipoiva"
												value="<%=BPF.getIdtipoiva()%>">
                                </tr>
                              </table></td>
							  <td width="13%" class="fila-det-border"> Cotizacion: </td>
							  <td width="39%" class="fila-det-border"><table width="53%" border="0">
                                  <tr class="fila-det-border">
                                    <td width="61%"><input name="cotizacion" type="text"
										value="<%=BPF.getCotizacion()%>" class="campo" size="18"
										maxlength="18"></td>
                                    <td width="39%">&nbsp;</td>
                                    <input name="idsucuclie2" type="hidden" id="idsucuclie2"
												value="<%=BPF.getIdsucuclie()%>" />
                                  </tr>
                                </table></td>
						  </tr>
							<tr class="fila-det">
							  <td class="fila-det-border">Observaciones </td>
							  <td colspan="3" class="fila-det-border">&nbsp;<textarea name="observaciones" cols="80" rows="2" class="campo"><%=BPF.getObservaciones()%></textarea></td>
						  </tr>
					  </table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
							<tr class="text-globales">
								<td width="22%">
									&nbsp;STOCK
								</td>
								<td width="78%" colspan="3">
									<!-- 1.1 -->
									<table width="100%" border="0" cellspacing="3" cellpadding="0"
										height="100%">
										<tr class="text-globales">
											<td width="24%" height="24">
												Ingresar Art&iacute;culos											</td>
											<td width="69%">
											<input type="button" name="actualiza" value=">>"
													class="boton"
													onClick="abrirVentana('lov_articulos_stock_in_out.jsp', 'art', 800, 400)">											</td>
										    <td width="7%"><div align="center"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="22" height="22" title="Limpiar detalle de pedido." onClick="; if(confirm('Confirma limpiar detalle ?')){ document.frm.accion.value = 'limpiardetalle'; document.frm.submit() ;}" style="cursor:pointer"></div></td>
										</tr>
									</table>
									<!--1.1 -->
								</td>
							</tr>
							<tr class="fila-det">
								<td colspan="4" valign="top" class="fila-det-border">
									<!-- 2.1 -->
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr class="fila-det-bold">
											<td width="40%" class="fila-det-border">
										  Articulo												</td>
											<td width="30%" class="fila-det-border">Dep&oacute;sito</td>
											<td width="10%" class="fila-det-border">
												<div align="right">
										  Cantidad												</div>											</td>
											<td width="10%" class="fila-det-border">
												<div align="right">
										  P.U												</div>											</td>
											<td width="10%" class="fila-det-border">
												<div align="right">Total												</div></td>
										</tr>
										<%										
										Hashtable htArticulosInOutOK = (Hashtable)session.getAttribute("htArticulosInOutOK");
												if(htArticulosInOutOK != null){													
													 Enumeration en = htArticulosInOutOK.keys();
													 
													 while(en.hasMoreElements()){
													   String key = (String)en.nextElement();
														 String art[] = (String[])htArticulosInOutOK.get(key);
														 
														 %>
									    
										<tr class="fila-det">
											<td height="25" class="fila-det-border">
												<%= art[0] %> - <%= art[2] %></td>
											<td class="fila-det-border"><%= BPF.getDeposito(art[9]) %></td>
											<td class="fila-det-border">
												
												<div align="right">
													<%= art[10] %>												</div>											</td>
											<td class="fila-det-border">
												
												<div align="right">
													<%= art[5] %>												</div>											</td>
											<td class="fila-det-border">
												
												<div align="right">
													<%= art[11] %>												</div>											</td>
										</tr>
										<% 
													 }
												}
												
												%>
										<tr class="fila-det-bold">
											<td colspan="4" class="fila-det-border">											  <div align="right">Total&nbsp;</div></td>
										    <td class="fila-det-border"><div align="right"><%= BPF.getTotalDebe() %></div></td>
										</tr>
									</table>
									<!-- 2.1 -->
								</td>
							</tr>
							<tr class="text-globales" height="3">
								<td width="22%" class="fila-det-border"></td>
								<td colspan="4" class="fila-det-border"></td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold">
								<td width="48%" class="fila-det-border">
									<div align="right">
										Bonific 1:									</div>								</td>
								<td width="17%" class="fila-det-border">
									<input name="bonific1" type="text"
										value="<%=BPF.getBonific1()%>" class="campo" size="10"
										maxlength="18">								</td>
								<td width="16%" class="fila-det-border">
									<div align="right">
										Recargo 1:									</div>								</td>
								<td width="19%" class="fila-det-border">
									<input name="recargo1" type="text"
										value="<%=BPF.getRecargo1()%>" class="campo" size="10"
										maxlength="18">								</td>
							</tr>
							<tr class="fila-det-bold">
								<td class="fila-det-border">
									<div align="right">
										Bonific 2:									</div>								</td>
								<td class="fila-det-border">
									<input name="bonific2" type="text"
										value="<%=BPF.getBonific2()%>" class="campo" size="10"
										maxlength="18">								</td>
								<td class="fila-det-border">
									<div align="right">
										Recargo 2:									</div>								</td>
								<td class="fila-det-border">
									<input name="recargo2" type="text"
										value="<%=BPF.getRecargo2()%>" class="campo" size="10"
										maxlength="18">								</td>
							</tr>
							<tr class="fila-det-bold">
								<td class="fila-det-border">
									<div align="right">
										Bonific 3:									</div>								</td>
								<td class="fila-det-border">
									 <input name="bonific3" type="text"
										value="<%=BPF.getBonific3()%>" class="campo" size="10"
										maxlength="18">								</td>
								<td class="fila-det-border">
									<div align="right">
										Recargo 3:									</div>								</td>
								<td class="fila-det-border">
									<input name="recargo3" type="text"
										value="<%=BPF.getRecargo3()%>" class="campo" size="10"
										maxlength="18">								</td>
							</tr>
						<tr class="fila-det-bold">
							<td class="fila-det-border">&nbsp;</td>
							<td class="fila-det-border">&nbsp;</td>
							<td class="fila-det-border"><div align="right">Recargo 4:</div>								</td>
							<td class="fila-det-border"><input name="recargo4" type="text"	value="<%=BPF.getRecargo4()%>" class="campo" size="10" maxlength="18"></td>
						</tr>
						<tr >
						  <td  height="3" colspan="4" class="permiso-cinco"></td>
						</tr>
						<tr class="fila-det-bold">
						  <td class="fila-det-border">&nbsp;</td>
						  <td class="fila-det-border">&nbsp;</td>
						  <td class="fila-det-border"><div align="right"> Total General: </div></td>
						  <td class="fila-det-border" > &nbsp;<%= BPF.getTotalgeneral() %> </td>
						</tr>
						<tr class="fila-det-bold">
							<td class="fila-det-border">&nbsp;</td>
							<td class="fila-det-border">&nbsp;</td>
							<td class="fila-det-border"><div align="right">Total General IVA:</div></td>
							<td class="fila-det-border" > &nbsp;<%=  BPF.getTotalGeneralIva() %></td>
						</tr>
						<tr >
						  <td  height="3" colspan="4" class="permiso-cinco"></td>
						</tr>
					  </table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det">
								<td width="10%" height="42">&nbsp;</td>
								<td width="33%">&nbsp;</td>
								<td width="20%">&nbsp;</td>
							  <td width="18%"><input name="calcular" type="submit" class="boton" id="calcular" value="Calcular Importes"></td>
									<td width="6%">&nbsp;</td>
									<td width="13%">
									<span class="fila-det-border"> <input name="validar"
											type="submit" value="Confirmar Pedido" class="boton">
									</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
		  </table>
			<input name="primeraCarga" type="hidden" value="false" >
		</form>
	</body>
</html>
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>

