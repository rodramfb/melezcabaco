<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: proveedoMovProv
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 20 11:33:45 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias
 
*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*"%> 
<%@ page import="ar.com.syswarp.api.*" %>
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
<jsp:useBean id="BPMPF"  class="ar.com.syswarp.web.ejb.BeanProveedoMovProvFrmUpd"   scope="page"/>
<head>
 <title>MODIFICACION DE COMPROBANTES</title>
 <meta http-equiv="description" content="mypage">
  <link rel="stylesheet" href="<%=pathskin%>">

 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>

<script>
<!--

function buscarProveedor(){
 mostrarLOV("lov_proveedores.jsp");
}
//-->
</script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPMPF" property="*" />
 <% 
 String titulo = BPMPF.getAccion().toUpperCase() + " DE COMPROBANTES" ;
 BPMPF.setResponse(response);
 BPMPF.setRequest(request);
 BPMPF.setSession(session);
 BPMPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPMPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPMPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPMPF.ejecutarValidacion();
 Hashtable htCP = BPMPF.getHtCP();  
 Enumeration enumCP = htCP.keys();
 
 Hashtable htStock = new Hashtable();
 %>
 <form action="proveedoMovProvFrmUpd.jsp" method="post" name="frm">
  <div align="right">
  <input name="accion" type="hidden" value="<%=BPMPF.getAccion()%>" >
  <input name="primeraCarga" type="hidden" value="false" > 	
  <input name="nrointerno" type="hidden" value="<%=BPMPF.getNrointerno()%>" >
     <table width="97%"  border="0" cellspacing="0" cellpadding="0" align="center">
       <tr>
         <td>
           <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="text-globales">
                <td>&nbsp;<%= titulo %></td>
              </tr>
           </table> 
              <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr class="fila-det-bold-rojo">
                  <td class="fila-det-border">&nbsp;</td>
                  <td colspan="3" class="fila-det-border"><jsp:getProperty name="BPMPF" property="mensaje"/>&nbsp;                  </td>
                </tr>
                <tr  class="fila-det">
                  <td width="18%" class="fila-det-border">&nbsp;Proveedor: (*) </td>
                  <td colspan="3" class="fila-det-border">									
                    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                      <tr class="fila-det">
                        <td width="11%" ><%=BPMPF.getDproveedor()%>&nbsp;
                        <input name="dproveedor" type="hidden" id="dproveedor" value="<%=BPMPF.getDproveedor()%>" >
                        <input name="idproveedor" type="hidden" id="idproveedor" value="<%=BPMPF.getIdproveedor()%>"></td><td width="27%" ><div align="right"></div></td>
                      </tr>
                    </table>								  </td>
                </tr>
							  <% 
							// FUE SELECCIONADO UN PROVEEDOR
							if(BPMPF.getIdproveedor().longValue() !=0) {%> 
                <tr class="fila-det">
                  <td width="18%" height="24" class="fila-det-border">&nbsp;Condicion Pago:  </td>
                  <td colspan="2" class="fila-det-border"><%=  BPMPF.getDcondicionpago() %>
								  <input name="idcondicionpago" type="hidden" value="<%=BPMPF.getIdcondicionpago().longValue()%>"  >
								  <input name="dcondicionpago" type="hidden" id="dcondicionpago" value="<%=BPMPF.getDcondicionpago()%>"  ></td>
                  <td class="fila-det-border">&nbsp;Factura Stock: <%=  BPMPF.getStock_fact() %></td>
                </tr>
                <tr class="fila-det"> 
                  <td class="fila-det-border">&nbsp;Fecha Factura: (*
)								  </td>
                  <td colspan="3" class="fila-det-border"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr class="fila-det">
                      <td width="35%" ><input class="campo" onFocus="this.blur()" size="12" readonly type="text" name="fechamov" value="<%=BPMPF.getFechamov()%>" maxlength="12">
                        <a class="so-BtnLink" href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','fechamov','BTN_date_3');return false;"> <img align="absmiddle" border="0" name="BTN_date_3" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a> </td>
                      <td width="24%" >&nbsp;Fecha Vencimiento: </td>
                      <td width="41%">
											<input class="campo" onFocus="this.blur()" size="12" readonly type="text" name="fechavto" value="<%=BPMPF.getFechavto()%>" maxlength="12">
                          <a class="so-BtnLink" href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_13', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_13', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_13', 'img_Date_DOWN');showCalendar('frm','fechavto','BTN_date_13');return false;"> <img align="absmiddle" border="0" name="BTN_date_13" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a> </td>
                    </tr>
                  </table></td>
                  <td width="0%" class="fila-det-border">&nbsp;</td>
                </tr>
													  
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">&nbsp;Comprobante: (*)</td> 
                <td width="11%" class="fila-det-border">
							<input name="sucursal" type="text" value="<%= Common.strZero(  BPMPF.getSucursal().toString(), 4) %>" class="campo" size="10" maxlength="10"  >								</td>
                <td width="19%" class="fila-det-border">
							<input name="comprob" type="text" value="<%=Common.strZero(  BPMPF.getComprob().toString(), 8)  %>"  class="campo" size="20" maxlength="20"  >								</td>
                <td width="52%" class="fila-det-border">&nbsp;</td>
              </tr>
  
              <tr class="text-globales" height="3">
                <td width="18%" class="fila-det-border"></td>
                <td colspan="4" class="fila-det-border" ></td>
						  </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">&nbsp;Tipo Movimiento: (*) </td>
                <td colspan="3" class="fila-det-border">
							    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr class="fila-det">
                      <td width="13%" height="30">
												<select name="tipomov" class="campo" onChange="document.frm.submit();">
													<option value="">Seleccionar</option>
													<option value="1" <% if(BPMPF.getTipomov().intValue()== 1) out.write("selected");  %>>Factura</option>
													<option value="2" <% if(BPMPF.getTipomov().intValue()== 2) out.write("selected"); %>>Nota D�bito</option>
													<option value="3" <% if(BPMPF.getTipomov().intValue()== 3) out.write("selected"); %>>Nota Cr�dito</option>
													<!--option value="4" < % if(BPMPF.getTipomov().intValue() == 4)out.write("selected"); %>>Pago</option-->
												</select>											</td>
                      <td width="41%" >&nbsp;Letra(*) 
												<select name="tipomovs" class="campo">
													<option value="">Seleccionar</option>
													<option value="A" <% if(BPMPF.getTipomovs().equalsIgnoreCase("A")) out.write("selected"); %>>A</option>
													<option value="B" <% if(BPMPF.getTipomovs().equalsIgnoreCase("B")) out.write("selected"); %>>B</option>
													<option value="C" <% if(BPMPF.getTipomovs().equalsIgnoreCase("C")) out.write("selected"); %>>C</option>
													<option value="M" <% if(BPMPF.getTipomovs().equalsIgnoreCase("M")) out.write("selected"); %>>M</option>
												</select>											</td>
                      <td width="19%">&nbsp;</td>
                      <td width="27%">&nbsp;</td>
										</tr>
                  </table>								</td>
              </tr>
							  <% 
								// FUE SELECCIONADO TIPO MOVIMIENTO
								if(BPMPF.getTipomov().longValue() > 0) {%>
  
              <tr class="text-globales" height="3">
                <td width="18%" class="fila-det-border"></td>
                <td colspan="4" class="fila-det-border" ></td>
              </tr>	
                 
							  <% 
									// SI LA FACTURA DEL PROVEEDOR AFECTA STOCK O LO PERMITE
									//if( !str.esNulo(BPMPF.getStock_fact()).equalsIgnoreCase("N") ||  BPMPF.getTipomov().intValue()== 3) {
                  // Por el momento no se modifican comprobantes con stock !!!!!!!!!!!
									// ABSURDO
									if( 1 != 1) {%>								 
                <tr >
									<td width="18%" class="text-globales">&nbsp;STOCK								  </td>
									<td colspan="3" class="text-globales">
									<table width="100%"  border="0" cellspacing="3" cellpadding="0" height="100%">
										 <tr>
											 <td width="23%" class="text-globales">Seleccionar  Art&iacute;culos </td>
											 <td width="77%">
									   <input type="button" name="actualiza" value=">>" class="boton" onClick="abreVentana('lov_articulos_stock.jsp', 800, 400)"></td>
<!--												 
											<% 
												// SI LA FACTURA DEL PROVEEDOR NO AFECTA STOCK OBLIGATORIAMENTE
												//if( str.esNulo(BPMPF.getStock_fact()).equalsIgnoreCase("?") ||  BPMPF.getTipomov().intValue()== 3 ) {%>												 
											 <td width="10%" class="text-globales">Omitir</td>
											 <td width="51%"><input type="submit" name="omite" value=">>" class="boton"></td>
											 <% 
												//}											 
											  %>
												-->
										 </tr>
								  </table>									</td>
                </tr>
									<tr class="fila-det">
										<td colspan="4" valign="top" class="fila-det-border"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
											<tr class="fila-det-bold">
												<td width="22%" height="30" class="fila-det-border">Articulo</td>
												<td width="25%" class="fila-det-border">Cantidad</td>
												<td width="36%" class="fila-det-border">Precio Unidad </td>
												<td width="12%" class="fila-det-border">Total</td>
												<td width="5%"  class="fila-det-border">&nbsp;</td>
											</tr>
										<% 
												Hashtable htArticulosConfirmados = (Hashtable)session.getAttribute("htArticulosConfirmados");
												if(htArticulosConfirmados != null){
													 Enumeration en = htArticulosConfirmados.keys();
															while(en.hasMoreElements()){
																String key = (String)en.nextElement();
																	String art[] = (String[])htArticulosConfirmados.get(key);
														 %>
											<tr class="fila-det">
												<td height="25" class="fila-det-border">&nbsp;<%= art[0] %></td>
												<td class="fila-det-border">&nbsp;<%= art[10] %></td>
												<td class="fila-det-border">&nbsp;<%= art[5] %></td>
												<td class="fila-det-border">&nbsp;<%= art[11] %></td>
												<td class="fila-det-border">&nbsp;</td>
											</tr>
												<% 
														}
												} %>
										</table>									</td>
								</tr>				
								<tr class="text-globales" height="3">
									<td width="18%" class="fila-det-border"></td>
									<td colspan="4" class="fila-det-border" ></td>
                </tr>		
							  <% 
									// FIN SI LA FACTURA DEL PROVEEDOR AFECTA STOCK O LO PERMITE
									}%>								 
							  <% 
									//  SI EL PROVEEDOR AFECTA STOCK O LO PERMITE Y LO AFECTO.
									if(BPMPF.getStock_fact().equalsIgnoreCase("S") || session.getAttribute("htArticulosConfirmados") != null){%>
                <tr class="fila-det">
									<td width="18%" class="fila-det-border">&nbsp;Subtotal Gravado:  </td>
								  <td colspan="3" class="fila-det-border">
								    <table width="100%" border="0">
											<tr>
												<td width="15%">&nbsp;</td>
												<td width="85%">
													<input name="subTotalGravado" type="text" value="<%=BPMPF.getSubTotalGravado()%>" class="campo" size="20" maxlength="20" readonly="yes" ></td>
											</tr>
										</table>									</td>
                </tr>
                <tr class="fila-det">
                  <td width="18%" class="fila-det-border">&nbsp;Bonificaci&oacute;n:  </td>
									<td colspan="3" class="fila-det-border">
										<table width="100%" border="0">
											<tr>
												<td width="15%" class="fila-det">
												  <input name="porcentajeBonificacion" type="text" class="campo" id="porcentajeBonificacion" value="<%=BPMPF.getPorcentajeBonificacion()%>" size="7" maxlength="6"  >
													%</td>
												<td width="85%"><input name="bonificacion" type="text" value="<%=BPMPF.getBonificacion()%>" class="campo" size="20" maxlength="20"  readonly ></td>
											</tr>
										</table>									</td>
                </tr>
								<% 
									// 
									}								
								 %>
                <tr class="fila-det">
									<td width="18%" class="fila-det-border">&nbsp;Neto Gravado :  </td>
								<td colspan="3" class="fila-det-border">
									<table width="100%" border="0">
										<tr>
											<td width="15%">&nbsp;</td>
											<td width="85%">
											  <input name="netoGravado" type="text" value="<%=BPMPF.getNetoGravado()%>" class="campo" size="20" maxlength="20" <%= BPMPF.getReadonlyNetoGravado() %>>											</td>
										</tr>
									</table>									</td>
                </tr>
                <tr class="fila-det">
                  <td width="18%" class="fila-det-border">&nbsp;IVA :  </td>
                <td colspan="3" class="fila-det-border"><table width="100%" border="0">
<tr>
<td width="15%" class="fila-det"><input name="provPorcNormalIva" type="text" class="campo" id="provPorcNormalIva" value="<%=BPMPF.getProvPorcNormalIva()%>" size="7" maxlength="6"  <%= BPMPF.getReadonlyIva() %>>
%</td>
<td width="85%"><input name="iva" type="text" value="<%=BPMPF.getIva()%>" class="campo" size="20" maxlength="20" readonly></td>
</tr>
</table></td>
                </tr>
                <tr class="fila-det">
									<td width="18%" class="fila-det-border">Percepcion IVA &nbsp;:  </td>
								<td colspan="3" class="fila-det-border">
									<table width="100%" border="0">
										<tr>
											<td width="15%" class="fila-det">
											 <input name="provPorcEspecialIvaPercep" type="text" class="campo" id="provPorcEspecialIvaPercep" value="<%=BPMPF.getProvPorcEspecialIvaPercep()%>" size="7" maxlength="6"  <%= BPMPF.getReadonlyRetIva() %>>
											%</td>
											<td width="85%">
											 <input name="percepcionIva" type="text" value="<%=BPMPF.getPercepcionIva()%>" class="campo" size="20" maxlength="20"  readonly>											</td>
										</tr>
									</table>								</td>
              </tr>
              <tr class="fila-det">
								<td width="18%" class="fila-det-border">Neto Exento &nbsp;:  </td>
								<td colspan="3" class="fila-det-border">
										<table width="100%" border="0">
											<tr class="fila-det">
												<td width="15%">&nbsp;</td>
												<td width="23%">
												  <input name="netoExento" type="text" value="<%=BPMPF.getNetoExento()%>" class="campo" size="20" maxlength="20"  >												</td>
											    <td width="62%">Obs Contables &nbsp;:
                                                <input name="observacionesContables" type="text" value="<%=BPMPF.getObservacionesContables() %>" class="campo" size="40" maxlength="75"  title="Ingrese observacion aclaratoria para los asientos contables, recuerde que los datos correspondiente al nro de documento, tipo y sucursal se generaran automaticamente." ></td>
											</tr>
										</table>									</td>
                </tr>
								
								
								
              <tr class="fila-det">
								<td width="18%" class="fila-det-border">&nbsp;</td>
								<td colspan="3" class="fila-det-border">
										<table width="100%" border="0">
											<tr>
												<td width="15%">&nbsp;</td>
												<td width="85%"><input name="calcular" type="submit" class="boton" id="calcular" value="Calcular Importes"></td>
											</tr>
										</table>									</td>
              </tr>								
								
								
								
                <tr class="fila-det-bold">
                  <td width="18%" class="fila-det-border">&nbsp;Total:  </td>
                <td colspan="3" class="fila-det-border">
									<table width="100%" border="0">
										<tr>
											<td width="15%">&nbsp;</td>
											<td width="85%">
											  <input name="importe" type="text" value="<%=Common.getNumeroFormateado(BPMPF.getImporte().doubleValue(), 10, 2)%>" class="campo" size="20" maxlength="20" readonly="yes" ></td>
</tr>
									</table>								</td>
                </tr>
								<tr class="text-globales" height="3">
									<td width="18%" class="fila-det-border"></td>
									<td colspan="4" class="fila-det-border" ></td>
                </tr>								
                <tr class="fila-det">
                  <td height="35" class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border"><table width="100%" border="0">
<tr>
<td width="15%">&nbsp;</td>
<td width="85%"><input name="validar" type="submit" value="Modificar Comprobante" class="boton">
<input name="volver" type="submit" class="boton" id="volver" value="Cancelar"></td>
</tr>
</table></td>
               </tr>
							   <%
							 //FIN TIPO-MOV. SELECCIONADO.
							 }
						 //FIN PROVEEDOR SELECCIONADO.
						 }%>
            </table>
         </td>
       </tr>
    </table>     
  </div>
	
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