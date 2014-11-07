<%@page language="java" %>
<%@ page contentType="application/vnd.ms-excel" %>
<%response.setHeader("Content-Disposition","attachment;filename=Test.xls"); %>
<%//@ page contentType="application/pdf" %>
<%//response.setHeader("Content-Disposition","attachment;filename=Test.pdf"); %>
<%
 System.out.println("....... PDF .......");
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
<!--meta http-equiv="Content-Type" content="application/pdf;"-->
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BPMPIF"  class="ar.com.syswarp.web.ejb.BeanProveedoMovProvImpresionFrm"   scope="page"/>
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
 <jsp:setProperty name="BPMPIF" property="*" />
 <% 
 String titulo = BPMPIF.getAccion().toUpperCase() + " - CAPTURA DOCUMENTOS" ;
 BPMPIF.setResponse(response);
 BPMPIF.setRequest(request);
 BPMPIF.setSession(session);
 BPMPIF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPMPIF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPMPIF.ejecutarValidacion();
 Hashtable htCP = BPMPIF.getHtCP();
 Enumeration enumCP = htCP.keys();
 
 Hashtable htStock = new Hashtable();
 %>
<form action="proveedoMovProvImpresion.jsp" method="post" name="frm">
  <div align="right">
  <input name="accion" type="hidden" value="<%=BPMPIF.getAccion()%>" >
  <input name="nrointerno" type="hidden" value="<%=BPMPIF.getNrointerno()%>" >
     <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
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
                  <td colspan="3" class="fila-det-border"><jsp:getProperty name="BPMPIF" property="mensaje"/>&nbsp;</td>
                </tr>
                <tr  class="fila-det-bold">
                  <td width="16%" height="25" class="fila-det-border">Proveedor:</td>
                  <td colspan="3" class="fila-det-border"><%=BPMPIF.getIdproveedor()%> - <%=BPMPIF.getDproveedor()%><input name="idproveedor" type="hidden" value="<%=BPMPIF.getIdproveedor()%>"  >
<input name="dproveedor"  type="hidden" value="<%=BPMPIF.getDproveedor()%>" ></td>
                </tr>
							  <% 
							// FUE SELECCIONADO UN PROVEEDOR
							if(BPMPIF.getIdproveedor().longValue() !=0) {%>
                <tr class="fila-det-bold">
                  <td width="16%" height="25" class="fila-det-border">Condicion Pago:  </td>
                  <td class="fila-det-border"><%=  BPMPIF.getDcondicionpago() %>
<input name="idcondicionpago" type="hidden" value="<%=BPMPIF.getIdcondicionpago().longValue()%>"  ></td>
                  <td class="fila-det-border">Factura Stock:</td>
<td class="fila-det-border"><%=  BPMPIF.getStock_fact() %></td>
                </tr>
                <tr class="fila-det-bold"> 
                <td height="25" class="fila-det-border">Fecha movimiento:</td>
                  <td class="fila-det-border"><%=BPMPIF.getFechamovStr()%> <input  type="hidden" name="fechamovStr" value="<%=BPMPIF.getFechamovStr()%>" >&nbsp;</td>
                  <td class="fila-det-border">Fecha Vencimiento:</td>
<td class="fila-det-border"><%= BPMPIF.getFechavtoStr() %><input  type="hidden" name="fechavtoStr" value="<%=BPMPIF.getFechavtoStr()%>" >&nbsp;</td>
<td width="0%" class="fila-det-border">&nbsp;</td>
                </tr>
													  
              <tr class="fila-det-bold">
                <td width="16%" height="25" class="fila-det-border">Comprobante: </td>
                <td width="34%" class="fila-det-border"><%= BPMPIF.getSucursal() %> - <%=BPMPIF.getComprob()%>
<input name="sucursal" type="hidden" value="<%=BPMPIF.getSucursal()%>" >
<input name="comprob" type="hidden" value="<%=BPMPIF.getComprob()%>"  ></td>
                <td width="24%" class="fila-det-border"><input name="tipomov" type="hidden" value="<%= BPMPIF.getTipomov() %>">
<% if(BPMPIF.getTipomov().intValue()== 1) out.write("Factura"); 
   if(BPMPIF.getTipomov().intValue()== 2) out.write("Nota Crédito"); 
   if(BPMPIF.getTipomov().intValue()== 3) out.write("Nota Débito"); 
   if(BPMPIF.getTipomov().intValue() == 4)out.write("Pago"); %>
<input name="tipomovs" type="hidden" value="<%= BPMPIF.getTipomovs() %>">
<% if(BPMPIF.getTipomovs().equalsIgnoreCase("A")) out.write("A"); 
   if(BPMPIF.getTipomovs().equalsIgnoreCase("B")) out.write("B");  
   if(BPMPIF.getTipomovs().equalsIgnoreCase("C")) out.write("C"); %></td>
                <td width="26%" class="fila-det-border">&nbsp;</td>
              </tr>
  
							  <% 
								// FUE SELECCIONADO TIPO MOVIMIENTO
								if(BPMPIF.getTipomov().longValue() > 0) {%>
  
              <tr class="text-globales" height="3">
                <td width="16%" class="fila-det-border"></td>
                <td colspan="4" class="fila-det-border" ></td>
              </tr>	
                 
							  <% 
									// SI LA FACTURA DEL PROVEEDOR AFECTA STOCK O LO PERMITE
									if( !str.esNulo(BPMPIF.getStock_fact()).equalsIgnoreCase("N") ||  BPMPIF.getTipomov().intValue()== 3) {%>								 
                <tr >
									<td width="16%" class="text-globales">&nbsp;STOCK								  </td>
									<td colspan="3" class="text-globales">Detalle de Art&iacute;culos Ingresados</td>
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
									<td width="16%" class="fila-det-border"></td>
									<td colspan="4" class="fila-det-border" ></td>
                </tr>		
							  <% 
									// FIN SI LA FACTURA DEL PROVEEDOR AFECTA STOCK O LO PERMITE
									}%>								 
							  <% 
									//  SI EL PROVEEDOR AFECTA STOCK O LO PERMITE Y LO AFECTO.
									if(BPMPIF.getStock_fact().equalsIgnoreCase("S") || session.getAttribute("htArticulosConfirmados") != null){%>

              <tr  height="10" class="permiso-cinco">
		            <td height="3" colspan="4"  ></td>
              </tr>									
                <tr class="fila-det">
									<td width="16%" class="fila-det-border">Subtotal Gravado:  </td>
								  <td colspan="3" class="fila-det-border">
								    <table width="100%" border="0">
											<tr>
												<td width="15%">&nbsp;</td>
												<td width="85%"><span class="fila-det"><%=BPMPIF.getSubTotalGravado()%></span>
											<input name="subTotalGravado" type="hidden" value="<%=BPMPIF.getSubTotalGravado()%>" class="campo" size="20" maxlength="20" readonly="yes" ></td>
											</tr>
										</table>									</td>
                </tr>
                <tr class="fila-det">
                  <td width="16%" class="fila-det-border">Bonificaci&oacute;n:  </td>
									<td colspan="3" class="fila-det-border">
										<table width="100%" border="0">
											<tr>
												<td width="15%" class="fila-det"><%=BPMPIF.getPorcentajeBonificacion()%>
<input name="porcentajeBonificacion" type="hidden" class="campo" id="porcentajeBonificacion" value="<%=BPMPIF.getPorcentajeBonificacion()%>" size="7" maxlength="6"  >
													%</td>
												<td width="85%"><span class="fila-det"><%=BPMPIF.getBonificacion()%></span>
<input name="bonificacion" type="hidden" value="<%=BPMPIF.getBonificacion()%>" class="campo" size="20" maxlength="20"  readonly ></td>
											</tr>
										</table>									</td>
                </tr>
								<% 
									// 
									}								
								 %>
                <tr class="fila-det">
									<td width="16%" class="fila-det-border">Neto Gravado :  </td>
								<td colspan="3" class="fila-det-border">
									<table width="100%" border="0">
										<tr>
											<td width="15%">&nbsp;</td>
											<td width="85%"><span class="fila-det"><%=BPMPIF.getNetoGravado()%></span>
											  <input name="netoGravado" type="hidden" value="<%=BPMPIF.getNetoGravado()%>" >											</td>
										</tr>
										</table>									</td>
                </tr>


              <tr  height="10" class="permiso-cinco">
		            <td height="3" colspan="4"  ></td>
              </tr>

              <tr class="fila-det">
								<td width="16%" class="fila-det-border">Neto Exento &nbsp;:  </td>
								<td colspan="3" class="fila-det-border">
										<table width="100%" border="0">
											<tr>
												<td width="15%">&nbsp;</td>
												<td width="85%"><span class="fila-det"><%=BPMPIF.getNetoExento()%></span>
												  <input name="netoExento" type="hidden" value="<%=BPMPIF.getNetoExento()%>"  ></td>
											</tr>
										</table>									
							</td>
              </tr>

              <tr  height="10" class="permiso-cinco">
		            <td height="3" colspan="4"  ></td>
              </tr>
                <tr class="fila-det">
                  <td width="16%" class="fila-det-border">IVA :  </td>
									<td colspan="3" class="fila-det-border">
									<table width="100%" border="0">
										<tr>
											<td width="15%" class="fila-det"><%=BPMPIF.getProvPorcNormalIva()%><input name="provPorcNormalIva" type="hidden"  id="provPorcNormalIva" value="<%=BPMPIF.getProvPorcNormalIva()%>" >
											%</td>
											<td width="85%"><span class="fila-det"><%=BPMPIF.getIva()%></span>
											<input name="iva" type="hidden" value="<%=BPMPIF.getIva()%>" ></td>
										</tr>
									</table>
									</td>
                </tr>

                <tr class="fila-det">
									<td width="16%" class="fila-det-border">Percepcion IVA &nbsp;:  </td>
								<td colspan="3" class="fila-det-border">
									<table width="100%" border="0">
										<tr>
											<td width="15%" class="fila-det"><%=BPMPIF.getProvPorcEspecialIvaPercep()%>
											 <input name="provPorcEspecialIvaPercep" type="hidden" class="campo" id="provPorcEspecialIvaPercep" value="<%=BPMPIF.getProvPorcEspecialIvaPercep()%>" >
											%</td>
											<td width="85%"><span class="fila-det"><%=BPMPIF.getPercepcionIva()%></span>
											 <input name="percepcionIva" type="hidden" value="<%=BPMPIF.getPercepcionIva()%>" >											</td>
										</tr>
									</table>								</td>
              </tr>
								
								
                <tr class="fila-det-bold">
                  <td width="16%" class="fila-det-border">&nbsp;Total:  </td>
                <td colspan="3" class="fila-det-border">
									<table width="100%" border="0">
										<tr>
											<td width="15%">&nbsp;</td>
											<td width="85%"><span class="fila-det"><%=BPMPIF.getImporte()%></span>
											  <input name="importe" type="hidden" value="<%=BPMPIF.getImporte()%>"></td>
                    </tr>
									</table>								  
									</td>
                </tr>
<!--								<tr class="text-globales" height="3">
-->
								<tr >
									<td colspan="5" class="fila-det-border" >

									<!--INICIA MANEJO DE ASIENTOS TEST -->
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
									
										<tr >
											<td width="20%" class="text-globales">NETO GRAVADO 
											</td>
										  <td width="80%" class="text-globales">&nbsp;
										  </td>
										</tr>
									
										<tr>
											<td colspan="2">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr  class="fila-det">
														<td width="60%" class="fila-det-bold">Cuenta
														  
														</td>
														<td width="20%" class="fila-det-bold">Debe
														  
														</td>			
														<td width="20%" class="fila-det-bold">Haber
														  
														</td>																										
													</tr>
													<tr >
													  <td class="text-globales" colspan="3" height="3"></td>
												  </tr>													
                         <% 
												 Hashtable htNG = BPMPIF.getHtNetoGravado();
												 Enumeration enumNG = htNG.keys();
												 while(enumNG.hasMoreElements()){
												 	 String  keyAsiento = (String )enumNG.nextElement();
												   String [] datosAsiento = (String [])htNG.get(keyAsiento);
												 %>
													<tr class="fila-det">
														<td class="fila-det-border"><%= datosAsiento[0] + " - " + datosAsiento[1]%>&nbsp;</td>
														<td class="fila-det-border"><%= datosAsiento[2] %></td>
														<td class="fila-det-border">&nbsp;<%= 0 %></td>				
													</tr>
											<%
												 }  
											%>
											</table>
											</td>
										</tr>
										
										<% 
												 if( !BPMPIF.getNetoExento().equals("0.00") )	{									
										 %>
										<tr>
											<td class="text-globales" >NETO EXENTO &nbsp;											</td>
										  <td class="text-globales" >&nbsp;</td>
										</tr>
										<tr>
											<td colspan="2">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr  class="fila-det-bold">
														<td width="60%">Cuenta </td>
														<td width="20%">Debe </td>
														<td width="20%">Haber </td>
													</tr>
													<tr >
													  <td class="text-globales" colspan="3" height="3"></td>
												  </tr>
                         <% 
												 Hashtable htNE = BPMPIF.getHtNetoExento();
												 Enumeration enumNE = htNE.keys();
												 while(enumNE.hasMoreElements()){
												 	 String  keyAsiento = (String )enumNE.nextElement();
												   String [] datosAsiento = (String [])htNE.get(keyAsiento);
												 %>
													<tr class="fila-det">
														<td class="fila-det-border"><%= datosAsiento[0] + " - " + datosAsiento[1]%>&nbsp;</td>
														<td class="fila-det-border"><%= datosAsiento[2] %></td>
														<td class="fila-det-border">&nbsp;<%= 0 %></td>				
													</tr>
											<%
												 }  
											%>

											 </table>
											</td>
										</tr>
										<% 
												 }									
										 %>										
										<tr>
											<td colspan="2" class="text-globales">IVA Y TOTAL 
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr  class="fila-det-bold">
														<td width="60%">Cuenta </td>
														<td width="20%">Debe </td>
														<td width="20%">Haber </td>
													</tr>
													<tr >
														<td class="text-globales" colspan="3" height="3"></td>
													</tr>									
                         <% 
												 Hashtable htIvaTotal = BPMPIF.getHtIvaTotal();
												 Enumeration enumIVATotal = htIvaTotal.keys();
												 while(enumIVATotal.hasMoreElements()){
												 	 String  keyAsiento = (String )enumIVATotal.nextElement();
												   String [] datosAsiento = (String [])htIvaTotal.get(keyAsiento);
												 %>
													<tr class="fila-det">
														<td class="fila-det-border"><%= datosAsiento[0] + " - " + datosAsiento[1]%>&nbsp;</td>
														<td class="fila-det-border"><%= datosAsiento[2] %></td>
														<td class="fila-det-border">&nbsp;<%= 0 %></td>				
													</tr>
											<%
												 }  
											%>		

												</table>
											</td>
										</tr>
									</table>
									<!--FINALIZA MANEJO DE ASIENTOS TEST -->				
									
									
									
									
									</td>
                </tr>								
                <tr class="fila-det">
                  <td height="35" class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border"><table width="100%" border="0">
<tr>
<td width="15%">&nbsp;</td>
<td width="85%">&nbsp;   </td>
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