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
String tipocliente = str.esNulo(request.getParameter("tipocliente"));
String titulo = " Emisión de Comprobantes para Clientes" ;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
 <jsp:useBean id="BPMPF"  class="ar.com.syswarp.web.ejb._MR_DEMOBeanClientesIngresoMovimientos"   scope="page"/>

<head>
 <title> <%=titulo%> </title>
 <meta http-equiv="description" content="mypage">
  
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script >
  function setValoresTipocliente(codigo, cliente, nrodocumento, tipoiva, domicilio, cp, provincia, localidad ){
	
	  alert("setValoresTipocliente");
	  return false;  
		
	}
 </script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPMPF" property="*" />
<%
 BPMPF.setResponse(response);
 BPMPF.setRequest(request);
 BPMPF.setSession(session);
 BPMPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPMPF.setEjercicioactivo( Integer.parseInt( session.getAttribute("ejercicioActivo").toString() ) );
 BPMPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPMPF.setTipocliente("C");
// BPMPF.setMueveStock("N");
 BPMPF.ejecutarValidacion();
// BPMPF.setMueveStock("N");


%>
<form action="MRdemoComprobantesClientes.jsp" method="post" name="frm">
  <input name="accion" type="hidden" value="<%=BPMPF.getAccion()%>" >

		<!-- 5 -->
        <span class="fila-det-border">
        <input name="tipoclienteanterior" type="hidden" id="tipoclienteanterior" value="<%=BPMPF.getTipocliente()%>" >
        </span>
        <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
       <tr>
         <td colspan="5">
				 <!-- 0 -->
           <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="text-globales">
                <td height="33">&nbsp;<%= titulo %></td>
              </tr>
           </table>
					 <!-- 0 -->         </td>
       </tr>
				<tr class="fila-det">
					<td width="20%" height="28" class="fila-det-border">Tipo de Comprobante:(*) </td>
					<td width="20%" class="fila-det-border"><select name="tipocomp" class="campo" onChange="document.frm.submit()">
					  <option value="-1" >Seleccionar</option>
					  <% 
					  Iterator iterClientesTipoComp = BPMPF.getListClientesTipoComp().iterator();
					  while(iterClientesTipoComp.hasNext()){  
					    String [] datos = (String []) iterClientesTipoComp.next(); 
					   %>
                      <option value="<%= datos[0] %>" <%=BPMPF.getTipocomp().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[2] %></option>
					  <% 
					  } %>
                  </select></td>
					<td width="17%" class="fila-det-border">&nbsp;Fecha movimiento: (*)</td>
				  <td width="26%" class="fila-det-border"><input class="campo" onFocus="this.blur()" size="12" readonly type="text" name="fechamov" value="<%=BPMPF.getFechamov()%>" maxlength="12">
                    <a class="so-BtnLink" href="javascript:calClick();return false;"
												onMouseOver="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
												onMouseOut="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
												onClick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','fechamov','BTN_date_3');return false;"> <img align="absmiddle" border="0" name="BTN_date_3" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
				  <td width="17%" class="fila-det-border">&nbsp;<!--input type="submit" name="confirma_tipocliente" value="Confirma" class="boton"--></td>
				</tr>
				<%//if(!BPMPF.getTipocliente().equalsIgnoreCase("A")){%>
				<tr class="fila-det">
					<td width="20%" height="28" class="fila-det-border">Comprobante:(*)</td>
					<td width="20%" class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="15%"><input name="sucursal" type="text" class="campo" id="sucursal" value="<%=BPMPF.getSucursal()%>" size="5" maxlength="4" readonly>                        </td>
                        <td width="85%"><input name="comprob" type="text" class="campo" id="comprob" value="<%=BPMPF.getComprob()%>" size="10" maxlength="8"  ></td>
                      </tr>
                    </table></td>
					<td width="17%" class="fila-det-border">Condici&oacute;n de Venta:(*)  </td>
					<td width="26%" class="fila-det-border"><input name="condicion" type="text" class="campo" id="condicion" value="<%=BPMPF.getCondicion()%>" size="30" maxlength="50"  >
				    <input name="idcondicion" type="hidden" class="campo" id="idcondicion" value="<%=BPMPF.getIdcondicion()%>"></td>
					<td width="17%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('lov_condventa.jsp', 'iva', 800, 400)" style="cursor:pointer"></td>
				</tr>
				<tr class="fila-det">
				  <td height="28" class="fila-det-border"> Concepto / Obs.: </td>
				  <td colspan="4" class="fila-det-border"><textarea name="observaciones" cols="80" rows="2" class="campo" id="observaciones"><%=BPMPF.getObservaciones()%></textarea></td>
	   </tr>
				<%//}%>						
				<tr class="fila-det">
					<td colspan="5"  class="fila-det-border">
						<table width="100%"  border="0" cellspacing="0" cellpadding="0">
							<tr >
							 <td class="fila-det-bold-rojo">     
							   <jsp:getProperty name="BPMPF" property="mensaje"/>							 </td>
							</tr>
						</table>					</td>
				</tr>
				<% 
				if(!BPMPF.getTipocliente().equalsIgnoreCase("")) {%>
				<tr class="fila-det">
				  <td colspan="5"  class="fila-det-border">
						<!-- 4 -->
						<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
						 <tr>
							 <td>
								<!-- 3 -->
								<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
									<tr class="text-globales">
										<td width="22%" ><%=BPMPF.getLabelTipocliente() %></td>
										<td width="88%" colspan="3" >
											<!-- 1 -->
											<table width="100%"  border="0" cellspacing="3" cellpadding="0" height="100%">
												 <tr class="text-globales">
													 <td width="22%" height="30" >
													   <input type="button" name="openlov" value=">>" class="boton" onClick="abrirVentana('<%= BPMPF.getLovClienteAnexo() %>', 'tipocliente', 800, 400)"></td>
													 <td width="78%">
													  <input name="primeraCarga" type="hidden" value="false" >
													  <input name="idprovincia" type="hidden" id="idprovincia" value="<%=BPMPF.getIdprovincia()%>" >
													  <input name="idlocalidad" type="hidden" id="idlocalidad" value="<%=BPMPF.getIdlocalidad()%>" >
                                                      <input name="idcliente" type="hidden" id="idcliente" value="<%=BPMPF.getIdcliente()%>" >
                                                      <input name="iddomicilio" type="hidden" id="iddomicilio" value="<%=BPMPF.getIddomicilio()%>" >
  							                          <input name="idtipoiva" type="hidden" id="idtipoiva" value="<%=BPMPF.getIdtipoiva()%>" size="2" >
                            <%//= BPMPF.getDiscrimina() %>                            </tr>
											</table>		
											 <!--1 -->										</td>
									</tr>
									
									<tr class="text-globales" height="3">
										<td width="12%" class="fila-det-border"></td>
										<td colspan="4" class="fila-det-border" ></td>
									</tr>											
									<tr class="fila-det">
										<td colspan="4" class="fila-det-border">
										<!-- 2 --> 
										<% 
										if(!BPMPF.getTipocliente().equalsIgnoreCase("A")) {%>
										<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="TDO">
											<tr class="fila-det">
											  <td height="25" class="fila-det-border-bold">C&oacute;digo</td>
											  <td class="fila-det-border"><%=BPMPF.getIdcliente().compareTo(new BigDecimal(0)) > 0 ? BPMPF.getIdcliente().toString() : "" %>&nbsp;</td>
											  <td class="fila-det-border-bold">Cliente</td>
											  <td class="fila-det-border">&nbsp;<%=BPMPF.getCliente()%></td>
										  </tr>
											<tr class="fila-det">
												<td width="9%" height="30" class="fila-det-border-bold"><%=BPMPF.getTipodocumento()%></td>
												<td width="39%" class="fila-det-border">&nbsp;<%=BPMPF.getNrodocumento()%></td>
												<td width="12%" class="fila-det-border-bold">Tipo de Iva  </td>
												<td width="40%" class="fila-det-border">&nbsp;<%=BPMPF.getTipoiva()%></td>
											</tr>
										</table>	
										<!-- ELSE-->
										<% 
										}%>																				
										<!-- 2 -->                                  </td>
								</tr>				
								<tr class="text-globales" height="3">
									<td width="12%" class="fila-det-border"></td>
									<td colspan="4" class="fila-det-border" ></td>
								</tr>		
							</table>
									<!-- 3 -->                       </td>
					</tr>
					
						 <tr>
							 <td>
							 <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
                               <tr class="text-globales">
                                 <td width="22%" >&nbsp;</td>
                                 <td width="78%" colspan="3" >
								 <!-- 1.2 --><!--1.2 -->								 </td>
                               </tr>
                               
                              <tr class="fila-det">
                                 <td colspan="4"   valign="top" class="fila-det-border">
								 <!-- 2.2 -->
								 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                                      <tr class="fila-det">
                                     <td width="18%" class="fila-det-border">&nbsp;Neto Gravado :(*) </td>
                                     <td width="42%"  class="fila-det-border"><table width="100%" border="0">
                                         <tr>
                                           <td width="25%">&nbsp;</td>
                                           <td width="75%"><input name="netogravado" type="text" value="<%=BPMPF.getNetogravado()%>" class="campo" size="20" maxlength="20" <%= BPMPF.getReadOnly("netogravado") %> onChange="document.frm.netogravadoaux.value = this.value" />
                                           <input name="netogravadoaux" type="hidden" id="netogravadoaux" value="<%=BPMPF.getNetogravadoaux()%>" ></td>
                                         </tr>
                                     </table></td>
                                     <td width="40%"  class="fila-det-border">&nbsp;</td>
                                   </tr>
                                   <tr class="fila-det">
                                     <td width="18%" class="fila-det-border">&nbsp;IVA : </td>
                                     <td  class="fila-det-border"><table width="100%" border="0">
                                         <tr>
                                           <td width="25%" class="fila-det"><input name="porcentajeivauno" type="text" class="campo" id="porcentajeivauno" value="<%=BPMPF.getPorcentajeivauno()%>" size="5" maxlength="5" readonly />
                                             %</td>
                                           <td width="75%"><input name="ivauno" type="text" value="<%=BPMPF.getIvauno()%>" class="campo" size="20" maxlength="20" readonly=/></td>
                                         </tr>
                                     </table></td>
                                     <td  class="fila-det-border">&nbsp;</td>
                                   </tr>
                                   <tr class="fila-det">
                                     <td width="18%" class="fila-det-border">&nbsp;IVA : </td>
                                     <td  class="fila-det-border"><table width="100%" border="0">
                                         <tr>
                                           <td width="25%" class="fila-det"><input name="porcentajeivados" type="text" class="campo" id="porcentajeivados" value="<%=BPMPF.getPorcentajeivados()%>" size="5" maxlength="5" readonly />
                                             %</td>
                                           <td width="75%"><input name="ivados" type="text" value="<%=BPMPF.getIvados()%>" class="campo" size="20" maxlength="20" readonly/></td>
                                         </tr>
                                     </table></td>
                                     <td  class="fila-det-border">&nbsp;</td>
                                   </tr>								   								   
                                   <tr class="fila-det">
                                     <td width="18%" class="fila-det-border">Impuesto Internos: </td>
                                     <td  class="fila-det-border"><table width="100%" border="0">
                                       <tr class="fila-det">
                                         <td width="25%"><input name="imp_int_tc" type="text" class="campo" id="imp_int_tc" value="<%=BPMPF.getImp_int_tc()%>" size="5" maxlength="5" readonly />
%</td>
                                         <td width="75%"><input name="impuestosinternos" type="text" value="<%=BPMPF.getImpuestosinternos()%>" class="campo" size="20" maxlength="20" readonly> </td>
                                       </tr>
                                     </table></td>
                                     <td  class="fila-det-border"><table width="100%" border="0">
                                       <tr class="fila-det">
                                         <td width="35%" class="fila-det">Recargo&nbsp;: </td>
                                         <td width="65%"><input name="recargo" type="text" value="<%=BPMPF.getRecargo()%>" class="campo" size="20" maxlength="20">                                         </td>
                                       </tr>
                                     </table></td>
                                   </tr>
                                   <tr class="fila-det">
                                     <td width="18%" class="fila-det-border">Otros Impuestos :  </td>
                                     <td  class="fila-det-border"><table width="100%" border="0">
                                       <tr>
                                         <td width="25%">&nbsp;</td>
                                         <td width="75%"><input name="otrosimpuestos" type="text" value="<%=BPMPF.getOtrosimpuestos()%>" class="campo" size="20" maxlength="20">                                         </td>
                                       </tr>
                                     </table></td>
                                     <td  class="fila-det-border"><table width="100%" border="0">
                                       <tr class="fila-det">
                                         <td width="35%">Bonificacion % &nbsp;: </td>
                                         <td width="65%"><input name="bonificacion" type="text" value="<%=BPMPF.getBonificacion()%>" class="campo" size="20" maxlength="20">                                         </td>
                                       </tr>
                                     </table></td>
                                   </tr>
                                   <tr class="fila-det">
                                     <td width="18%" class="fila-det-border">Total:(*)</td>
                                     <td  class="fila-det-border"><table width="100%" border="0">
                                         <tr>
                                           <td width="25%">&nbsp;</td>
                                           <td width="75%"><input name="total" type="text" value="<%=BPMPF.getTotal()%>" class="campo" size="20" maxlength="20" <%= BPMPF.getReadOnly("total") %> onChange="document.frm.totalaux.value = this.value">
                                           <input name="totalaux" type="hidden" id="totalaux" value="<%=BPMPF.getTotalaux()%>"></td>
                                         </tr>
                                     </table></td>
                                     <td  class="fila-det-border">&nbsp;</td>
                                   </tr>
                                   <tr class="fila-det">
                                     <td width="18%" class="fila-det-border">No Gravado : </td>
                                     <td  class="fila-det-border"><table width="100%" border="0">
                                       <tr>
                                         <td width="25%" class="fila-det">&nbsp;</td>
                                         <td width="75%"><input name="totalnogravado" type="text" value="<%=BPMPF.getTotalnogravado()%>" class="campo" size="20" maxlength="20" onChange="document.frm.totalnogravadoaux.value = this.value" >
                                         <input name="totalnogravadoaux" type="hidden" id="totalnogravadoaux" value="<%=BPMPF.getTotalnogravadoaux()%>" ></td>
                                       </tr>
                                     </table></td>
                                     <td  class="fila-det-border">&nbsp;</td>
                                   </tr>								   
                                   <tr class="fila-det">
                                     <td width="18%" class="fila-det-border">&nbsp;</td>
                                     <td  class="fila-det-border"><table width="100%" border="0">
                                         <tr>
                                           <td width="25%">&nbsp;</td>
                                           <td width="75%"><input name="calcular" type="submit" class="boton" id="calcular" value="Calcular Importes" /></td>
                                         </tr>
                                     </table></td>
                                     <td  class="fila-det-border">&nbsp;</td>
                                   </tr>
                                   <tr class="fila-det-bold">
                                     <td width="18%" class="fila-det-border">&nbsp;Total Comprobante: (*)</td>
                                     <td  class="fila-det-border"><table width="100%" border="0">
                                         <tr>
                                           <td width="25%">&nbsp;</td>
                                           <td width="75%"><input name="totalfactura" type="text" value="<%=BPMPF.getTotalfactura()%>" class="campo" size="20" maxlength="20" readonly></td>
                                         </tr>
                                     </table></td>
                                     <td  class="fila-det-border">&nbsp;</td>
                                   </tr>
                                   <tr class="text-globales" height="3">
                                     <td class="fila-det-border"> </td>
                                     <td class="fila-det-border" ></td>
                                     <td class="fila-det-border" ></td>
                                   </tr>
                                   <tr class="fila-det">
                                     <td height="35" class="fila-det-border">&nbsp;</td>
                                     <td colspan="2"  class="fila-det-border"><table width="100%" border="0">
                                         <tr>
                                           <td width="13%">&nbsp;</td>
                                           <td width="87%"><input name="validar" type="submit" class="boton" id="validar" value="Confirmar Movimiento">
                                             <input name="volver" type="submit" class="boton" id="volver" value="Cancelar" /></td>
                                         </tr>
                                     </table>									 </td>
                                   </tr>
                                 </table>
								 <!-- 2.2 -->								 </td>
                               </tr>
                               <tr class="text-globales" height="3">
                                 <td class="fila-det-border"></td>
                                 <td colspan="6" class="fila-det-border" ></td>
                               </tr>
                             </table>
						     <!-- 3.2 -->                          </td>
					</tr>
				</table> 
				<!-- 4 -->
				<% 
				}%>		</td>
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