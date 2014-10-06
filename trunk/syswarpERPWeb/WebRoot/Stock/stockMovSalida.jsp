<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: 
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Oct 26 16:33:45 GMT-03:00 2006 
   Observaciones: 

*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 


try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
String destino = str.esNulo(request.getParameter("destino"));
String titulo = " MOVIMIENTOS DE SALIDA" ;

 Enumeration e = request.getParameterNames();
 while(e.hasMoreElements()){
   String key = e.nextElement().toString();
	 System.out.println("Parametro: " + key + "/ Valor: " + request.getParameter(key));
 
 }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
 <jsp:useBean id="BSMSF"  class="ar.com.syswarp.web.ejb.BeanStockMovSalidaFrm"   scope="page"/>

<head>
 <title> <%=titulo%> </title>
 <meta http-equiv="description" content="mypage">
  
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script >
 
  function setValoresOrigen(codigo, razonsocial, cuit, iibb, domicilio, cp, provincia, localidad, tipodocumento ){
	
	  var CODIGO = document.getElementById('TDO').rows[0].cells[1] ;
	  var RAZONSOCIAL = document.getElementById('TDO').rows[0].cells[3] ;
	  
	  var TIPODOC = document.getElementById('TDO').rows[1].cells[0] ;
	  
	  var CUIT = document.getElementById('TDO').rows[1].cells[1] ;
	  var IIBB = document.getElementById('TDO').rows[1].cells[3] ;		
	  var DOMICILIO = document.getElementById('TDO').rows[2].cells[1] ;
	  var CP = document.getElementById('TDO').rows[2].cells[3] ;
	  var PROVINCIA = document.getElementById('TDO').rows[3].cells[1] ;
	  var LOCALIDAD = document.getElementById('TDO').rows[3].cells[3] ;
		
		CODIGO.innerHTML = '&nbsp;' + codigo;
		RAZONSOCIAL.innerHTML = '&nbsp;' +razonsocial;
		
		TIPODOC.innerHTML  = tipodocumento + '&nbsp;';
				
		CUIT.innerHTML = '&nbsp;' + cuit;
		IIBB.innerHTML = '&nbsp;' + iibb;
		DOMICILIO.innerHTML = '&nbsp;' + domicilio;
		CP.innerHTML = '&nbsp;' + cp;
		PROVINCIA.innerHTML = '&nbsp;' + provincia;
		LOCALIDAD.innerHTML = '&nbsp;' + localidad;
		
	}
	
 </script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BSMSF" property="*" />
<%


 BSMSF.setResponse(response);
 BSMSF.setRequest(request);
 BSMSF.setSession(session);
 BSMSF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BSMSF.setEjercicioactivo( Integer.parseInt( session.getAttribute("ejercicioActivo").toString() ) );
 BSMSF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 /*
 Enumeration enu = session.getAttributeNames();
 while(enu.hasMoreElements()){
   String clave = enu.nextElement().toString(); 
	 System.out.println(" - "+ clave +": " + session.getAttribute(clave));
 }
 */
 BSMSF.setIdcontadorcomprobante( new BigDecimal( session.getAttribute("idcontadorremitos1").toString() ));   
 
 BSMSF.ejecutarValidacion();
%>
<form action="stockMovSalida.jsp" method="post" name="frm">
  <input name="accion" type="hidden" value="<%=BSMSF.getAccion()%>" >

		<!-- 5 -->
     <table width="95%" height="88"  border="0" align="center" cellpadding="0" cellspacing="0">
       <tr>
         <td colspan="5">
				 <!-- 0 -->
           <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="text-globales">
                <td height="33">&nbsp;<%= titulo %></td>
              </tr>
           </table>
					 <!-- 0 --> 
         </td>
       </tr>
				<tr class="fila-det">
					<td width="21%" height="28" class="fila-det-border">&nbsp;Fecha movimiento: (* ) </td>
					<td width="17%" class="fila-det-border"><input class="campo" onFocus="this.blur()" size="12" readonly type="text" name="fechamov" value="<%=BSMSF.getFechamov()%>" maxlength="12">
								<a class="so-BtnLink" href="javascript:calClick();return false;"
												onmouseover="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
												onmouseout="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
												onclick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','fechamov','BTN_date_3');return false;"> 
												<img align="absmiddle" border="0" name="BTN_date_3" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a> 
				  </td>
					<td width="11%" class="fila-det-border">Destino </td>
					<td width="24%" class="fila-det-border">
						<select name="destino" id="destino" class="campo" onChange="document.frm.submit()">
							<option value="">Seleccionar</option>
							<option value="C" <%= BSMSF.getDestino().equalsIgnoreCase("C") ? "selected": "" %>>Cliente</option>
							<option value="P" <%= BSMSF.getDestino().equalsIgnoreCase("P") ? "selected": "" %>>Proveedor</option>
							<option value="O" <%= BSMSF.getDestino().equalsIgnoreCase("O") ? "selected": "" %>>Otro</option>
						</select>
            <input name="destinoanterior" type="hidden" id="destinoanterior" value="<%=BSMSF.getDestino()%>" >						
					</td>
				  <td width="27%" class="fila-det-border">&nbsp;<!--input type="submit" name="confirma_destino" value="Confirma" class="boton"--></td>
				</tr>
				<%if(!BSMSF.getDestino().equalsIgnoreCase("O")){%>
				<tr class="fila-det">
					<td width="21%" height="28" class="fila-det-border">&nbsp;Remito </td>
					<td width="17%" class="fila-det-border">
					  <input class="campo"  size="12" type="text" name="remito_ms" value="<%=BSMSF.getRemito_ms()%>" maxlength="6">
					</td>
					<td width="11%" class="fila-det-border">&nbsp;</td>
					<td width="24%" class="fila-det-border">&nbsp;</td>
				  <td width="27%" class="fila-det-border">&nbsp;</td>
				</tr>
				<%}%>		
				<tr class="fila-det">
					<td width="21%" height="28" class="fila-det-border">&nbsp;Observaciones</td>
					<td colspan="4" class="fila-det-border">
					  <textarea name="observaciones" cols="80" rows="2" class="campo"><%=BSMSF.getObservaciones()%></textarea>
					</td>
				</tr>						
				<tr class="fila-det">
					<td colspan="5"  class="fila-det-border">
						<table width="100%"  border="0" cellspacing="0" cellpadding="0">
							<tr >
							 <td class="fila-det-bold-rojo">     
							   <jsp:getProperty name="BSMSF" property="mensaje"/>     
							 </td>
							</tr>
						</table>					
					</td>
				</tr>
				<% 
				if(!BSMSF.getDestino().equalsIgnoreCase("")) {%>
				<tr class="fila-det">
				  <td colspan="5"  class="fila-det-border">
						<!-- 4 -->
						<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
						 <tr>
							 <td>
								<!-- 3 -->
								<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
									<tr class="text-globales">
										<td width="22%" ><%=BSMSF.getLabelDestino() %></td>
										<td width="88%" colspan="3" >
											<!-- 1 -->
											<table width="100%"  border="0" cellspacing="3" cellpadding="0" height="100%">
												 <tr class="text-globales">
													 <td width="22%" height="30" >
												   <%if(BSMSF.getDestino().equalsIgnoreCase("P")){%>
													   <input type="button" name="openlov" value=">>" class="boton" onClick="abrirVentana('lov_vanexosclieproveedoAbm.jsp?tipo=<%= BSMSF.getDestino() %>', 'ori', 800, 400)">
												   <%}else if(BSMSF.getDestino().equalsIgnoreCase("C")){%>
													   <input type="button" name="openlov" value=">>" class="boton" onClick="abrirVentana('lov_clientes_new.jsp?tipo=<%= BSMSF.getDestino() %>', 'ori', 800, 400)">													 
												   <%} %>
												   </td>
												   <td width="78%">
													   <input name="primeraCarga" type="hidden" value="false" >
													   <input name="idprovincia" type="hidden" id="idprovincia" value="<%=BSMSF.getIdprovincia()%>" >
													   <input name="idlocalidad" type="hidden" id="idlocalidad" value="<%=BSMSF.getIdlocalidad()%>" >
                             <input name="codigo_anexo" type="hidden" id="codigo_anexo" value="<%=BSMSF.getCodigo_anexo()%>" >
												     <input name="iddomicilio"  type="hidden" id="iddomicilio"  value="<%=BSMSF.getIddomicilio()%>" >
													 </td>
										      </tr>
											</table>		
											 <!--1 -->																
										</td>
									</tr>
									
									<tr class="text-globales" height="3">
										<td width="12%" class="fila-det-border"></td>
										<td colspan="4" class="fila-det-border" ></td>
									</tr>											
									<tr class="fila-det">
										<td colspan="4" class="fila-det-border">
										<!-- 2 -->
										<% 
										if(!BSMSF.getDestino().equalsIgnoreCase("O")) {%>
										<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="TDO">
											<tr class="fila-det">
											  <td height="25" class="fila-det-border-bold">C&oacute;digo</td>
											  <td class="fila-det-border"><%=BSMSF.getCodigo_anexo().compareTo(new BigDecimal(0)) > 0 ? BSMSF.getCodigo_anexo().toString() : "" %>&nbsp;</td>
											  <td class="fila-det-border-bold">Razon Social</td>
											  <td class="fila-det-border">&nbsp;<%=BSMSF.getRazon_social()%></td>
										  </tr>
											<tr class="fila-det">
												<td width="7%" height="30" class="fila-det-border-bold"><%= BSMSF.getTipodocumento() %></td>
												<td width="41%" class="fila-det-border">&nbsp;<%=BSMSF.getCuit()%></td>
												<td width="11%" class="fila-det-border-bold">Ing. Brutos </td>
												<td width="41%" class="fila-det-border">&nbsp;<%=BSMSF.getIibb()%></td>
											</tr>
											<tr class="fila-det">
												<td height="25" class="fila-det-border-bold">Domicilio</td>
												<td class="fila-det-border">&nbsp;<%=BSMSF.getDomicilio()%></td>
												<td class="fila-det-border-bold">CP</td>
												<td class="fila-det-border">&nbsp;<%=BSMSF.getCodigo_postal()%></td>
											</tr>
											<tr class="fila-det">
											  <td height="25" class="fila-det-border-bold">Provincia</td>
											  <td class="fila-det-border">&nbsp;<%=BSMSF.getProvincia()%></td>
											  <td class="fila-det-border-bold">Localidad</td>
											  <td class="fila-det-border">&nbsp;<%=BSMSF.getLocalidad()%></td>
										  </tr>
										</table>	
										<!-- ELSE-->
										<% 
										}
										else{ %>
										<table width="100%"  border="0" cellspacing="0" cellpadding="0">
											<tr class="fila-det">
											  <td height="25" class="fila-det-border-bold">Razon Social</td>
											  <td colspan="3" class="fila-det-border"><input name="razon_social" type="text" class="campo" id="razon_social4" value="<%=BSMSF.getRazon_social()%>" size="40" maxlength="50"  ></td>
											</tr>
											<tr class="fila-det">
												<td width="14%" height="30" class="fila-det-border-bold">CUIT</td>
												<td width="34%" class="fila-det-border"><input name="cuit" type="text" class="campo" id="cuit" value="<%=BSMSF.getCuit()%>" size="20" maxlength="14"  ></td>
												<td width="11%" class="fila-det-border-bold">Ing. Brutos </td>
												<td width="41%" class="fila-det-border"><input name="iibb" type="text" class="campo" id="iibb" value="<%=BSMSF.getIibb()%>" size="40" maxlength="50"  ></td>
											</tr>
											<tr class="fila-det">
												<td height="25" class="fila-det-border-bold">Domicilio</td>
												<td class="fila-det-border"><input name="domicilio" type="text" class="campo" id="razon_social3" value="<%=BSMSF.getDomicilio()%>" size="40" maxlength="50"  ></td>
												<td class="fila-det-border-bold">Localidad</td>
												<td >												  
												<table width="100%"  border="0" cellspacing="0" cellpadding="0">
                            <tr class="fila-det">
                              <td width="71%" height="25" class="fila-det-border"><input name="localidad" type="text" class="campo" id="localidad4" value="<%=BSMSF.getLocalidad()%>" size="40" maxlength="50"  readonly></td>
                              <td width="29%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('lov_localidades.jsp', 'loca', 800, 400)" style="cursor:pointer"></td>
                            </tr>
                          </table></td>
											</tr>
											<tr class="fila-det">
											  <td height="25" class="fila-det-border-bold">Provincia</td>
											  <td >											    <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                            <tr >
                              <td width="76%" height="25" class="fila-det-border-bold"><input name="provincia" type="text" class="campo" id="provincia" value="<%=BSMSF.getProvincia()%>" size="40" maxlength="50"  readonly></td>
                              <td width="24%" class="fila-det-border">&nbsp;</td>
                            </tr>
                          </table></td>
											  <td class="fila-det-border-bold">CP</td>
											  <td class="fila-det-border"><input name="codigo_postal" type="text" class="campo" id="codigo_postal" value="<%=BSMSF.getCodigo_postal()%>" size="5" maxlength="5"  readonly></td>
										  </tr>
										</table>
										<% 
										}
										 %>																				
										<!-- 2 -->								
									</td>
								</tr>				
								<tr class="text-globales" height="3">
									<td width="12%" class="fila-det-border"></td>
									<td colspan="4" class="fila-det-border" ></td>
								</tr>		
							</table>
									<!-- 3 -->
						</td>
					</tr>
						 <tr>
							 <td>
								<!-- 3.1 -->
								<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
									<tr class="text-globales">
										<td width="22%" >&nbsp;STOCK  </td>
										<td width="78%" colspan="3" >
											<!-- 1.1 -->
											<table width="100%"  border="0" cellspacing="3" cellpadding="0" height="100%">
												 <tr class="text-globales">
													 <td width="24%" height="30" >Ingresar Art&iacute;culos </td>
													 <td width="76%">
														 <input type="button" name="actualiza" value=">>" class="boton" onClick="abrirVentana('lov_articulos_stock_in_out.jsp?tipomov=S', 'art', 800, 400)">
													</td>
												 </tr>
											</table>		
											 <!--1.1 -->																
										</td>
									</tr>
									<tr class="fila-det">
										<td colspan="4" valign="top" class="fila-det-border">
										<!-- 2.1 -->
										<table width="100%"  border="0" cellspacing="0" cellpadding="0">
											<tr class="fila-det-bold">
												<td width="48%" height="30" class="fila-det-border">Articulo</td>
												<td width="21%" class="fila-det-border">Cantidad</td>
												<td width="18%" class="fila-det-border">Precio Unidad </td>
												<td width="13%" class="fila-det-border"><div align="center">Total</div></td>
											</tr>
										<% 
												Hashtable htArticulosInOutOK = (Hashtable)session.getAttribute("htArticulosInOutOK");
												if(htArticulosInOutOK != null){
													 Enumeration en = htArticulosInOutOK.keys();
													 while(en.hasMoreElements()){
													   String key = (String)en.nextElement();
														 String art[] = (String[])htArticulosInOutOK.get(key);
														 //BSMSF.setTotalDebe(BSMSF.getTotalDebe().add(new BigDecimal( art[11] )));																
														 %>
											<tr class="fila-det">
												<td height="25" class="fila-det-border">&nbsp;<%= art[0] %></td>
												<td class="fila-det-border">&nbsp;<%= art[10] %></td>
												<td class="fila-det-border">&nbsp;<%= art[5] %></td>
												<td class="fila-det-border">
												  <div align="right">&nbsp;<%= art[11] %></div></td>
											</tr>
												<% 
													 }
												} 
												if(BSMSF.getTotalDebe().compareTo(new BigDecimal("0")) > 0){
												%>
											<tr class="fila-det-bold">
												<td class="fila-det-border" colspan="3">
                        <div align="right">Total general &nbsp;</div></td>
												<td class="fila-det-border">
												  <div align="right">&nbsp;<%= BSMSF.getTotalDebe() %></div>
												</td>
											</tr>												
												<% 
												} 
												%>												
										</table>	
										<!-- 2.1 -->								
									</td>
								</tr>				
								<tr class="text-globales" height="3">
									<td width="22%" class="fila-det-border"></td>
									<td colspan="4" class="fila-det-border" ></td>
								</tr>		
							</table>
									<!-- 3.1 -->
						</td>
					</tr>
								<!-- 3.2 -->
										<% 
						if(htArticulosInOutOK != null){
								 %>								
						 <tr>
							 <td>								
								<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
									<tr class="text-globales">
										<td width="22%" >&nbsp;IMPUTACION CONTABLE</td>
										<td width="78%" colspan="3" >
											<!-- 1.2 -->
											<table width="100%"  border="0" cellspacing="3" cellpadding="0" height="100%">
												 <tr class="text-globales">
													 <td width="24%" height="30" >Cuentas a imputar </td>
													 <td width="17%">
														 <input type="button" name="actualiza" value=">>" class="boton" onClick="abrirVentana('lov_contableinfiplan.jsp', 'cuentas', 800, 400)">
													</td>
												   <td width="29%">Remito queda pendiente </td>
												   <td width="30%"> <input name="remitopendiente" type="checkbox" id="remitopendiente" value="true" <%=BSMSF.isRemitopendiente() ? "checked" : ""%>> </td>
												 </tr>
											</table>		
											 <!--1.2 -->																
										</td>
									</tr>
									<tr class="fila-det">
										<td colspan="4" valign="top" class="fila-det-border">
										<!-- 2.2 -->
										<table width="100%"  border="0" cellspacing="0" cellpadding="0">
											<tr class="fila-det-bold">
												<td width="17%" height="30" class="fila-det-border">Cuenta</td>
												<td width="70%" class="fila-det-border">Descripci&oacute;n</td>
											  <td width="13%" class="fila-det-border"><div align="center">Total</div></td>
											</tr>
										<% 
												
												Hashtable htCuentasOk = (Hashtable)session.getAttribute("htCuentasOk");
												if(htCuentasOk != null){
													 Enumeration en = htCuentasOk.keys();
													 while(en.hasMoreElements()){
																String key = (String)en.nextElement();
																String cuentas[] = (String[])htCuentasOk.get(key);
																//BSMSF.setTotalHaber(BSMSF.getTotalHaber().add(new BigDecimal( cuentas[2] )));
														 %>
											<tr class="fila-det">
												<td height="25" class="fila-det-border">&nbsp;<%= cuentas[0] %></td>
												<td class="fila-det-border">&nbsp;<%= cuentas[1] %></td>
											  <td class="fila-det-border"><div align="right"><%= cuentas[2] %></div></td>
											</tr>
												<% 
													}
												} 
												if(BSMSF.getTotalHaber().compareTo(new BigDecimal("0")) > 0){
												%>
											<tr class="fila-det-bold">
												<td class="fila-det-border" colspan="2">
											  <div align="right">Total General &nbsp;</div></td>
												<td class="fila-det-border"><div align="right"><%= BSMSF.getTotalHaber() %>&nbsp;</div>
												</td>  
											</tr>												
												<% 
												} %>
										</table>	
										<!-- 2.2 -->								
									</td>
								</tr>				
								<tr class="text-globales" height="3">
									<td width="22%" class="fila-det-border"></td>
									<td colspan="4" class="fila-det-border" ></td>
								</tr>		
							</table>
							<% 
							}
							 %>							
									<!-- 3.2 -->
						</td>
					</tr>

				</table> 
				<!-- 4 -->
				<% 
				}%>
			</td>
	 </tr>
				<tr class="fila-det">
				  <td colspan="5"  class="fila-det-border">
					<input name="validar" type="submit" class="boton" id="validar" value="Confirmar Movimiento">
				  </td>
		   </tr>				
</table>
	<!-- 5 -->    
  
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