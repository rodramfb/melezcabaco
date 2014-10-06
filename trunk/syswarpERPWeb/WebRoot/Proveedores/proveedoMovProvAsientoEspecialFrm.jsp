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
<%@ page import="java.math.*"%> 
<%@ page import="java.util.*" %>
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
<jsp:useBean id="BPMPAF"  class="ar.com.syswarp.web.ejb.BeanProveedoMovProvAsientoEspecialFrm"   scope="page"/>
<head>
 <title>CAPTURA COMPROBANTES </title>
 <meta http-equiv="description" content="mypage">
  <link rel="stylesheet" href="<%=pathskin%>">

 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>

</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPMPAF" property="*" />
 <% 
 String titulo = BPMPAF.getAccion().toUpperCase() + " - CAPTURA DOCUMENTOS" ;
 BPMPAF.setResponse(response);
 BPMPAF.setRequest(request);
 BPMPAF.setSession(session);
 BPMPAF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPMPAF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPMPAF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPMPAF.setNetoGravado(session.getAttribute("subTotalGravado").toString());
 BPMPAF.ejecutarValidacion();
 Hashtable htCP = BPMPAF.getHtCP();
 Enumeration enumCP = htCP.keys();
 
 Hashtable htStock = new Hashtable();
 %>
<form action="proveedoMovProvAsientoEspecialFrm.jsp" method="post" name="frm">
  <div align="right">
  <input name="accion" type="hidden" value="<%=BPMPAF.getAccion()%>" >
  <input name="nrointerno" type="hidden" value="<%=BPMPAF.getNrointerno()%>" >
     <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
       <tr>
         <td>
           <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="text-globales">
              <td>&nbsp;<%= titulo %> - &nbsp; ASIENTO </td>
              </tr>
           </table> 
              <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr class="fila-det-bold-rojo">
                  <td class="fila-det-border">&nbsp;</td>
                  <td colspan="3" class="fila-det-border"><jsp:getProperty name="BPMPAF" property="mensaje"/>&nbsp;</td>
                </tr>
                <tr  class="fila-det-bold">
                  <td width="16%" height="25" class="fila-det-border">Proveedor:</td>
                  <td colspan="3" class="fila-det-border"><%=BPMPAF.getIdproveedor()%> - <%=BPMPAF.getDproveedor()%><input name="idproveedor" type="hidden" value="<%=BPMPAF.getIdproveedor()%>"  >
<input name="dproveedor"  type="hidden" value="<%=BPMPAF.getDproveedor()%>" ></td>
                </tr>
							  <% 
							// FUE SELECCIONADO UN PROVEEDOR
							if(BPMPAF.getIdproveedor().longValue() !=0) {%>
                <tr class="fila-det-bold">
                  <td width="16%" height="25" class="fila-det-border">Condicion Pago:  </td>
                  <td width="34%" class="fila-det-border"><%=  BPMPAF.getDcondicionpago() %>
<input name="idcondicionpago" type="hidden" value="<%=BPMPAF.getIdcondicionpago().longValue()%>"  ><!--/td--></td>
                  <td width="24%" class="fila-det-border">&nbsp;</td>
<td width="26%" class="fila-det-border">&nbsp;</td>
                </tr> 
                <tr class="fila-det-bold"> 
                <td height="25" class="fila-det-border">Fecha Factura:</td>
                  <td class="fila-det-border"><%=BPMPAF.getFechamovStr()%> <input  type="hidden" name="fechamovStr" value="<%=BPMPAF.getFechamovStr()%>" >&nbsp;</td>
                  <td class="fila-det-border">Fecha Vencimiento:</td>
<td class="fila-det-border"><%= BPMPAF.getFechavtoStr() %><input  type="hidden" name="fechavtoStr" value="<%=BPMPAF.getFechavtoStr()%>" >&nbsp;</td>
<td width="0%" class="fila-det-border">&nbsp;</td>
                </tr>
													  
                <tr class="fila-det-bold">
                  <td height="25" class="fila-det-border">Comprobante: </td>
                  <td class="fila-det-border"><%= Common.strZero(BPMPAF.getSucursal().toString(), 4) + " - " + Common.strZero(BPMPAF.getComprob().toString(), 8) %>
                      <input name="sucursal" type="hidden" value="<%=BPMPAF.getSucursal()%>" >
                      <input name="comprob" type="hidden" value="<%=BPMPAF.getComprob()%>"  ></td>
                  <td class="fila-det-border"><input name="tipomov" type="hidden" value="<%= BPMPAF.getTipomov() %>">
                      <% if(BPMPAF.getTipomov().intValue()== 1) out.write("Factura"); 
   if(BPMPAF.getTipomov().intValue()== 2) out.write("Nota D&eacute;bito "); 
   if(BPMPAF.getTipomov().intValue()== 3) out.write("Nota Cr&eacute;dito"); 
   if(BPMPAF.getTipomov().intValue() == 4)out.write("Pago"); %>
                      <input name="tipomovs" type="hidden" value="<%= BPMPAF.getTipomovs() %>">
                      <% if(BPMPAF.getTipomovs().equalsIgnoreCase("A")) out.write("A"); 
   if(BPMPAF.getTipomovs().equalsIgnoreCase("B")) out.write("B");  
   if(BPMPAF.getTipomovs().equalsIgnoreCase("C")) out.write("C"); %></td>
                  <td class="fila-det-border">&nbsp;</td>
                </tr>
                <tr class="fila-det-bold">
                <td width="16%" height="25" class="fila-det-border">Observaciones:
                  <input name="observacionesContables" type="hidden" value="<%=BPMPAF.getObservacionesContables()%>"  ></td>
                <td colspan="3" class="fila-det-border"><%=BPMPAF.getObservacionesContables()%>&nbsp;</td>
                </tr>
  
							  <% 
								// FUE SELECCIONADO TIPO MOVIMIENTO
								if(BPMPAF.getTipomov().longValue() > 0) {%>
  
                                  <tr class="text-globales" height="3">
                                     <td width="16%" class="fila-det-border"></td>
                                     <td colspan="4" class="fila-det-border" ></td>
                                  </tr>	
                 
							       <% // SI LA FACTURA DEL PROVEEDOR AFECTA STOCK O LO PERMITE
									if(BPMPAF.getTipomov().intValue()== 3) {%>								 
                <tr >
									<td width="16%" class="text-globales">&nbsp;Cuentas Contables</td>
									<td colspan="3" class="text-globales">Detalle de C.Contables Ingresadas</td>
                </tr>
									<tr class="fila-det">
										<td colspan="4" valign="top" class="fila-det-border"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
											<tr class="fila-det-bold">
												<td width="22%" height="30" class="fila-det-border">Cód.</td>
												<td width="25%" class="fila-det-border">Detalle</td>
												<td width="36%" class="fila-det-border">Tipificación</td>
												<td width="12%" class="fila-det-border">Total</td>
												<td width="5%"  class="fila-det-border">&nbsp;</td>
											</tr>
										<% 
												Hashtable htPlanesContablesConfirmados = (Hashtable)session.getAttribute("htPlanesContablesConfirmados");
												if(htPlanesContablesConfirmados != null){
													 Enumeration en = htPlanesContablesConfirmados.keys();
															while(en.hasMoreElements()){
																String key = (String)en.nextElement();
																	String art[] = (String[])htPlanesContablesConfirmados.get(key);
														 %>
											<tr class="fila-det">
												<td height="25" class="fila-det-border">&nbsp;<%= art[0] %></td>
												<td class="fila-det-border">&nbsp;<%= art[1] %></td>
												<td class="fila-det-border">&nbsp;<%= art[10] %></td>
												<td class="fila-det-border">&nbsp;<%= art[11] %></td>
												<td class="fila-det-border">&nbsp;</td>
											</tr>
												<% 
														}
												} %>
										</table>									</td>
								</tr>				
								<tr class="text-globales" height="3">
									<td width="16%" class="fila-det-border"></td>
									<td colspan="4" class="fila-det-border" ></td>
                </tr>		
							  <% 
									// FIN SI LA FACTURA DEL PROVEEDOR AFECTA STOCK O LO PERMITE
									}%>								 
							  <% 
									//  SI EL PROVEEDOR AFECTA STOCK O LO PERMITE Y LO AFECTO.
									if(session.getAttribute("htPlanesContablesConfirmados") != null){%>

              <tr  height="10" class="permiso-cinco">
		            <td height="3" colspan="4"  ></td>
              </tr>									
                <tr class="fila-det">
									<td width="16%" class="fila-det-border">Subtotal Gravado:  </td>
								  <td colspan="3" class="fila-det-border">
								    <table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="8%">&nbsp;</td>
												<td width="24%"><div align="right"><span class="fila-det"><%=BPMPAF.getSubTotalGravado()+""%></span>
									        <input name="subTotalGravado" type="hidden" value="<%=BPMPAF.getSubTotalGravado()%>" class="campo" size="20" maxlength="20" readonly="yes" >
											  </div></td>
											  <td width="68%">&nbsp;</td>
											</tr>
										</table>									</td>
                </tr>
                <tr class="fila-det">
                  <td width="16%" class="fila-det-border">Bonificaci&oacute;n:  </td>
									<td colspan="3" class="fila-det-border">
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="8%" class="fila-det"><%=BPMPAF.getPorcentajeBonificacion()+""%>
<input name="porcentajeBonificacion" type="hidden" class="campo" id="porcentajeBonificacion" value="<%=BPMPAF.getPorcentajeBonificacion()%>" size="7" maxlength="6"  >
													%</td>
												<td width="24%"><div align="right"><span class="fila-det"><%=BPMPAF.getBonificacion()+""%></span>
                          <input name="bonificacion" type="hidden" value="<%=BPMPAF.getBonificacion()%>" class="campo" size="20" maxlength="20"  readonly="readonly" >
											  </div></td>
											  <td width="68%">&nbsp;</td>
											</tr>
										</table>									</td>
                </tr>
								<% 
									// 
									}								
								 %>
								 
          <%--  < %// solo aplicaciones contables puras
            // en realidad tengo que poner unicamente los valores ya ingresados.
                 String[] idconcepto = BPMPAF.getConc();
                 String[] idcuenta   = BPMPAF.getIdcuenta();
                 String[] cuenta     = BPMPAF.getCuenta();
                 String[] tipo       = BPMPAF.getTipo();
                 String[] valor      = BPMPAF.getValor();
                  
                 BigDecimal total = new BigDecimal(0);
                 for(int i=0; i< valor.length; i++){
                    total = total.add(new BigDecimal(valor[i]));                 
                %>
                    
                <tr class="fila-det">
                  <td width="05%"></td>
                  <td width="20%">< %=idcuenta[i]%></td>    
                  <td width="20%">< %=cuenta[i]%></td>
                  <td width="73%">< %=valor[i]%>
                  
                    <input name="conc"     type="hidden"  value="< %=idconcepto[i]%>">
                    <input name="tipo"     type="hidden"  value="< %=tipo[i]%>">
                    <input name="idcuenta" type="hidden"  value="< %=idcuenta[i]%>">
                    <input name="cuenta"   type="hidden"  value="< %=cuenta[i]%>">               
                    <input name="valor"    type="hidden"  value="< %=valor[i]%>">
                    <input name="importe"  type="hidden"  value="< %=total%>">                    
                  </td>
                                    
                </tr>             
                
                
            
                <tr class="fila-det">
                  <td width="05%"></td>
                  <td width="20%"></td>    
                  <td width="20%"> TOTAL:</td>
                  <td width="73%">< %=total%>
                     < % BPMPAF.setImporte( new Double(total.doubleValue()) ); %>
                  </td>                  
                </tr>             
            
            < %}%>   --%>								 

                <tr class="fila-det">
									<td width="16%" class="fila-det-border">Neto Gravado :  </td>
								<td colspan="3" class="fila-det-border">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="8%">&nbsp;</td>
											<td width="24%"><div align="right"><span class="fila-det"><%=BPMPAF.getNetoGravado()+""%></span>
										    <input name="netoGravado" type="hidden" value="<%=BPMPAF.getNetoGravado()%>" >											
											  </div></td>
										  <td width="68%">&nbsp;</td>
										</tr>
									  </table>
							    </td>
                </tr>


              <tr  height="10" class="permiso-cinco">
		            <td height="3" colspan="4"  ></td>
              </tr>

              <tr class="fila-det">
								<td width="16%" class="fila-det-border">Neto Exento &nbsp;:  </td>
								<td colspan="3" class="fila-det-border">
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="8%">&nbsp;</td>
												<td width="24%"><div align="right"><span class="fila-det"><%=BPMPAF.getNetoExento()+""%></span>
											    <input name="netoExento" type="hidden" value="<%=BPMPAF.getNetoExento()%>"  >
												  </div></td>
											  <td width="68%">&nbsp;</td>
											</tr>
										</table>							</td>
              </tr>

              <tr  height="10" class="permiso-cinco">
		            <td height="3" colspan="4"  ></td>
              </tr>
                <tr class="fila-det">
                  <td width="16%" class="fila-det-border">IVA :  </td>
									<td colspan="3" class="fila-det-border">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="8%" class="fila-det"><%=BPMPAF.getProvPorcNormalIva()+""%><input name="provPorcNormalIva" type="hidden"  id="provPorcNormalIva" value="<%=BPMPAF.getProvPorcNormalIva()%>" >
											%</td>
											<td width="24%"><div align="right"><span class="fila-det"><%=BPMPAF.getIva()+""%></span>
									      <input name="iva" type="hidden" value="<%=BPMPAF.getIva()%>" >
										  </div></td>
										  <td width="68%">&nbsp;</td>
										</tr>
									</table>									</td>
                </tr>

                <tr class="fila-det">
									<td width="16%" class="fila-det-border">Percepcion IVA &nbsp;:  </td>
								<td colspan="3" class="fila-det-border">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="8%" class="fila-det"><%=BPMPAF.getProvPorcEspecialIvaPercep()+""%>
											 <input name="provPorcEspecialIvaPercep" type="hidden" class="campo" id="provPorcEspecialIvaPercep" value="<%=BPMPAF.getProvPorcEspecialIvaPercep()%>" >
											%</td>
											<td width="24%"><div align="right"><span class="fila-det"><%=BPMPAF.getPercepcionIva()+""%></span>
									      <input name="percepcionIva" type="hidden" value="<%=BPMPAF.getPercepcionIva()%>" >											
										   </div></td>
										  <td width="68%">&nbsp;</td>
										</tr>
									</table>								</td>
              </tr>
								
								
                <tr class="fila-det-bold">
                  <td width="16%" class="fila-det-border">&nbsp;Total:  </td>
                  <td colspan="3" class="fila-det-border">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="8%">&nbsp;</td>
											<td width="24%"><div align="right"><span class="fila-det"><%=Common.getNumeroFormateado(BPMPAF.getImporte().doubleValue(), 10, 2)%></span>
										    
											  </div></td>
                      <td width="68%">&nbsp;</td>
										</tr>
									</table>									</td>
                </tr>
                

<!--								<tr class="text-globales" height="3">
-->

								<tr >
									<td colspan="5" class="fila-det-border" >

									<!--INICIA MANEJO DE ASIENTOS TEST -->
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <%
									 int tipomov = BPMPAF.getTipomov().intValue();
                   if(Double.parseDouble(BPMPAF.getNetoGravado())>0){
                      %>									
										<tr >
											<td width="20%" class="text-globales">NETO GRAVADO											</td>
										  <td width="80%" class="text-globales">
											  <% 
												//if(BPMPAF.isModficaCNG()){ %>
											  <a href="javascript:abreVentana('lov_cuentas_gravado.jsp?TNGravado=<%= BPMPAF.getNetoGravado() %>&tipomov=<%= BPMPAF.getTipomov() %>', 800, 500)"><img src="../imagenes/default/gnome_tango/actions/gnome-searchtool.png" width="18" height="18" title="Cuentas" border="0"></a>
												<% 
												//} 
                    %>&nbsp;
                      </td>
										</tr>
										<tr>
											<td colspan="2">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr  class="fila-det">
														<td width="60%" class="fila-det-bold">Cuenta</td>
														<td width="20%" class="fila-det-bold"><div align="right">Debe
														  
													  </div></td>			
														<td width="20%" class="fila-det-bold"><div align="right">Haber
														  
													  </div></td>																										
													</tr>
													<tr >
													  <td class="text-globales" colspan="3" height="3"></td>
												  </tr>													
                         <% 
												 Hashtable htNG = BPMPAF.getHtNetoGravado();
												 Enumeration enumNG = htNG.keys();
												 while(enumNG.hasMoreElements()){
												 	 String  keyAsiento = (String )enumNG.nextElement();
												   String [] datosAsiento = (String [])htNG.get(keyAsiento);
												   
												 %>
													<tr class="fila-det">
														<td class="fila-det-border"><%= datosAsiento[0] + " - " + datosAsiento[1]%>&nbsp;</td>
												
														<td class="fila-det-border"><div align="right"><%= tipomov != 3 ? datosAsiento[2]  : ""%>&nbsp;</div></td>
														<td class="fila-det-border"><div align="right"><%= tipomov == 3 ? datosAsiento[2]  : "" %>&nbsp;</div></td>
																
													</tr>
											<%
												 }  
											%>
											</table>											
                     </td>
										</tr>
										<% 
                   }
                   if( Double.parseDouble(BPMPAF.getNetoExento()) > 0 )	{									
										 %>
										<tr>
											<td class="text-globales" >NETO EXENTO &nbsp;											</td>
										  <td class="text-globales" ><a href="javascript:abreVentana('lov_cuentas_exento.jsp?TNExento=<%= BPMPAF.getNetoExento() %>&tipomov=<%= BPMPAF.getTipomov() %>', 800, 500)"><img src="../imagenes/default/gnome_tango/actions/gnome-searchtool.png" width="18" height="18" title="Cuentas" border="0"></a></td>
										</tr>
										<tr>
											<td colspan="2">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr  class="fila-det-bold">
														<td width="60%">Cuenta </td>
														<td width="20%"><div align="right">Debe </div></td>
														<td width="20%"><div align="right">Haber </div></td>
													</tr>
													<tr >
													  <td class="text-globales" colspan="3" height="3"></td>
												  </tr>
                         <% 
												 Hashtable htNE = BPMPAF.getHtNetoExento();
												 Enumeration enumNE = htNE.keys();
												 
												 while(enumNE.hasMoreElements()){
												 	 String  keyAsiento = (String )enumNE.nextElement();
												   String [] datosAsiento = (String [])htNE.get(keyAsiento);
												   
												 %>
													<tr class="fila-det">
														<td class="fila-det-border"><%= datosAsiento[0] + " - " + datosAsiento[1]%>&nbsp;</td>
												
														<td class="fila-det-border"><div align="right"><%= tipomov != 3 ? datosAsiento[2]  : ""%>&nbsp;</div></td>
														<td class="fila-det-border"><div align="right"><%= tipomov == 3 ? datosAsiento[2]  : "" %>&nbsp;</div></td>
																
													</tr>
											<%
												
												 }  
											%>
											 </table>											</td>
										</tr>
										<% 
                   }
                   if(BPMPAF.getImporte().doubleValue()>0){%>										
										<tr>
											<td colspan="2" class="text-globales">IVA Y TOTAL</td>
										</tr>
										<tr>
											<td colspan="2">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr  class="fila-det-bold">
														<td width="60%">Cuenta </td>
														<td width="20%"><div align="right">Debe </div></td>
														<td width="20%"><div align="right">Haber </div></td>
													</tr>
													<tr >
														<td class="text-globales" colspan="3" height="3"></td>
													</tr>									
                         <% 
						                         Hashtable htIvaTotal = BPMPAF.getHtIvaTotal();
												 // Ordenamiento hashtable.
												 Vector vecSort = new Vector(htIvaTotal.keySet());
												 Collections.sort(vecSort);
												 Enumeration enumIVATotal = vecSort.elements();

												 while(enumIVATotal.hasMoreElements()){
												   String  keyAsiento = (String )enumIVATotal.nextElement();
												   String [] datosAsiento = (String [])htIvaTotal.get(keyAsiento); 
												   
                           if(Double.parseDouble(datosAsiento[2]) > 0){
												 %>
													<tr class="fila-det">
														<td class="fila-det-border"><%= datosAsiento[0] + " - " + datosAsiento[1]%>&nbsp;</td>
														<td class="fila-det-border"><div align="right"><%= ( datosAsiento[3].equalsIgnoreCase("T") ?  (tipomov == 3 ? datosAsiento[2]  : "" ) : (tipomov != 3 ? datosAsiento[2]  : "" ))%>&nbsp;</div></td>
														<td class="fila-det-border"><div align="right"><%= (!datosAsiento[3].equalsIgnoreCase("T") ? (tipomov == 3 ? datosAsiento[2]  : "" ) : (tipomov != 3 ? datosAsiento[2]  : "" ))%>&nbsp;</div></td>
													</tr>
											<%
                           }
												 }  
											%>		
												</table>											
                       </td>
										</tr>
										<% 
                   }%>
									</table>
									<!--FINALIZA MANEJO DE ASIENTOS TEST -->									
                </td>
                </tr>								
                <tr class="fila-det">
                  <td height="35" class="fila-det-border">&nbsp;</td>
                	<td colspan="3" class="fila-det-border">
										<table width="100%" border="0">
											<tr>
												<td width="15%">&nbsp;</td>
											  <td width="85%"><input name="aceptar" type="submit" value="Aceptar Movimiento" class="boton">
												 <input name="volver" type="submit" class="boton" id="volver" value="Cancelar"></td>
											</tr>
										</table>									</td>
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