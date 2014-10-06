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
	BigDecimal sum = new BigDecimal(0);
	Strings str = new Strings();
	String pathskin = session.getAttribute("pathskin").toString();
	String pathscript = session.getAttribute("pathscript").toString();
%>
	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
	<html>
		<%-- INSTANCIAR BEAN --%>  
		<jsp:useBean id="BPMPF"  class="ar.com.syswarp.web.ejb.BeanProveedoMovProvEspFrm"   scope="page"/>
		<head>
			<title>CAPTURA COMPROBANTES </title>
			<meta http-equiv="description" content="mypage">
			<link rel="stylesheet" href="<%=pathskin%>">
			<link rel="stylesheet" href="<%=pathskin%>">
			<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
			<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
			<script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
			<script language="JavaScript" src="vs/overlib/overlib.js"></script>
			<script>
			//-->
			function buscarProveedor(){
			 mostrarLOV("lov_proveedores.jsp");
			}
			//-->
			</script>
			<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
			<style type="text/css">
			<!--
			body {
				margin-left: 0px;
				margin-top: 0px;
				margin-right: 0px;
				margin-bottom: 0px;
			}
			-->
			</style>
		</head>
		<body>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
			<jsp:setProperty name="BPMPF" property="*" />
			<div id="popupcalendar" class="text"></div> 
			<% 
			String titulo = BPMPF.getAccion().toUpperCase() + " - CAPTURA DOCUMENTOS ESPECIALES" ;
			BPMPF.setResponse(response);
			BPMPF.setRequest(request);
			BPMPF.setSession(session);
			BPMPF.setUsuarioalt( session.getAttribute("usuario").toString() );
			BPMPF.setUsuarioact( session.getAttribute("usuario").toString() );
			BPMPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
			String ejercicio  = session.getAttribute("ejercicioActivo").toString();
			BPMPF.ejecutarValidacion();
			Hashtable htCP = BPMPF.getHtCP();  
			Enumeration enumCP = htCP.keys();
			Hashtable htStock = new Hashtable();
			%>
			<form action="proveedoMovProvEspFrm.jsp" method="post" name="frm">
				<input name="primeraCarga" type="hidden" value="false" >
				<input name="accion" type="hidden" value="<%=BPMPF.getAccion()%>" >
				<input name="nrointerno" type="hidden" value="<%=BPMPF.getNrointerno()%>" >
				<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
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
									<td colspan="3" class="fila-det-border"><jsp:getProperty name="BPMPF" property="mensaje"/>           
									&nbsp;
									</td>
								</tr>
								<tr class="fila-det">
									<td width="17%" class="fila-det-border">&nbsp;Proveedor: (*) </td>
									<td colspan="3" class="fila-det-border">
										<table width="100%"  border="0" cellspacing="0" cellpadding="0">
											<tr class="fila-det">
												<td width="58%" >
													<table width="23%" border="0" align="left" cellpadding="0" cellspacing="0">
														<tr class="fila-det-border">
															<td width="61%" >
																<input name="dproveedor" type="text" class="campo" id="dproveedor" value="<%=BPMPF.getDproveedor()%>" readonly="yes">
															</td>
															<td width="39%">
																<img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="22" height="22" onClick="abrirVentana('../Proveedores/lov_proveedores.jsp', 'prov', 800, 450)" style="cursor:pointer">
															</td>
														   <input name="idproveedor" type="hidden" id="idproveedor" value="<%=BPMPF.getIdproveedor()%>">
														</tr>
													</table>
											  </td>
												<td width="42%" >
													<div align="left">
													   <!--<input type="submit" name="confirma_proveedor" value="Confirma" class="boton"> -->
												   </div>
											  </td>
											</tr>
										</table>
									</td>
								</tr>
								<% 
								// FUE SELECCIONADO UN PROVEEDOR
								if(BPMPF.getIdproveedor().longValue() !=0) 
								{
								// cargo la lista con los tipos de comprobantes.
									List lstTipoComp = BPMPF.getTipocomp();
									Iterator iterTipoComp = lstTipoComp.iterator();
								%>
									<tr class="fila-det">
										<td width="17%" height="24" class="fila-det-border">&nbsp;Condicion Pago: </td>
										<td colspan="2" class="fila-det-border"><%=  BPMPF.getDcondicionpago() %>
										   <input name="idcondicionpago" type="hidden" value="<%=BPMPF.getIdcondicionpago().longValue()%>"  >
										</td>
										<td class="fila-det-border">&nbsp;</td>
									</tr>
									<tr class="fila-det">
										<td class="fila-det-border">&nbsp;Fecha Factura: (*) </td>
										<td colspan="3" class="fila-det-border">
											<table width="100%"  border="0" cellspacing="0" cellpadding="0">
												<tr class="fila-det">
													<td width="41%" >
													<input class="campo" onFocus="this.blur()" size="12" readonly="readonly"type="text" name="fechamovStr" value="<%=BPMPF.getFechamovStr()%>" maxlength="12">
													<a class="so-BtnLink" href="javascript:calClick();return false;"
														onMouseOver="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
														onMouseOut="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
														onClick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','fechamovStr','BTN_date_3');return false;"> 
														<img align="absmiddle" border="0" name="BTN_date_3" src="vs/calendar/btn_date_up.gif" width="22" height="17">
													  </a> 
													</td>
													<td width="17%" >&nbsp;F. Venc.: </td>
													<td width="42%">
														<input class="campo" onFocus="this.blur()" size="12" readonly="readonly" type="text" name="fechavtoStr" value="<%=BPMPF.getFechavtoStr()%>" maxlength="12">
														<a class="so-BtnLink" href="javascript:calClick();return false;"
														onMouseOver="calSwapImg('BTN_date_13', 'img_Date_OVER',true); "
														onMouseOut="calSwapImg('BTN_date_13', 'img_Date_UP',true);"
														onClick="calSwapImg('BTN_date_13', 'img_Date_DOWN');showCalendar('frm','fechavtoStr','BTN_date_13');return false;"> 
														<img align="absmiddle" border="0" name="BTN_date_13" src="vs/calendar/btn_date_up.gif" width="22" height="17">
														</a> 
													</td>
											   </tr>
										  </table>
										</td>
									</tr>
									<tr class="fila-det">
										<td width="17%" class="fila-det-border">&nbsp;Comprobante: (*)</td>
										<td width="21%" class="fila-det-border">
											<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
												<tr>
													<td>
													<input name="sucursal" type="text" value="<%=Common.strZero(BPMPF.getSucursal(), 4)%>" class="campo" size="4" maxlength="4" style="text-align:right" >
													</td>
													<td>
													<input name="comprob" type="text" value="<%=Common.strZero(BPMPF.getComprob(), 8)%>" class="campo" size="8" maxlength="8" style="text-align:right">
													</td>
												</tr>
											</table>
										</td>
										<td width="27%" class="fila-det-border">&nbsp;</td>
										<td width="35%" class="fila-det-border">&nbsp;</td>
									</tr>
									<tr class="fila-det">
										<td width="17%" class="fila-det-border">&nbsp;Tipo Mov.: (*) </td>
										<td colspan="3" class="fila-det-border">
											<table width="100%"  border="0" cellspacing="0" cellpadding="0">
												<tr class="fila-det">
													<td width="42%" height="30">
														<select name="tipomov" class="campo">
															<option value="">Seleccionar</option>
															<%
															while(iterTipoComp.hasNext())
															{ 
																String[] datosTM = (String[]) iterTipoComp.next();
															%>
																<option value="<%=datosTM[0]%>" <% if(BPMPF.getTipomov().toString().equalsIgnoreCase(datosTM[0]) ) out.write("selected");  %>><%=datosTM[1]%></option>
															<%}%>
														</select>                     
													</td>
													<td width="17%" >&nbsp;Letra(*)</td>
													<td width="14%">
														<select name="tipomovs" class="campo"  onChange="document.frm.submit();">
														   <option value="">Seleccionar</option>
														   <option value="A" <% if(BPMPF.getTipomovs().equalsIgnoreCase("A")) out.write("selected"); %>>A</option>
														   <option value="B" <% if(BPMPF.getTipomovs().equalsIgnoreCase("B")) out.write("selected"); %>>B</option>
														   <option value="C" <% if(BPMPF.getTipomovs().equalsIgnoreCase("C")) out.write("selected"); %>>C</option>
														   <option value="M" <% if(BPMPF.getTipomovs().equalsIgnoreCase("M")) out.write("selected"); %>>M</option>
														</select>
													</td>
													<td width="27%">&nbsp;</td>
												</tr>
											</table>
										</td>
									</tr>
								<%								
								// FUE SELECCIONADO TIPO MOVIMIENTO
								    System.out.println("------>");
									if(BPMPF.getTipomov().longValue() > 0) 
									{
									%>
										<tr class="fila-det"> 
														<td class="text-globales" colspan="4">
															<table width="100%"  border="0" cellspacing="3" cellpadding="0" height="100%">
																<tr>
																	<td width="32%" class="text-globales">&nbsp;Seleccionar C.Contables </td>
																	<td width="68%">
																		<input type="button" name="actualiza" value=">>" class="boton" onClick="abrirVentana('lov_cuentas_contables.jsp', 'infiplan', 800, 400)">
																	</td>
																</tr>
															</table>
														</td>
							  </tr>
										<%
										Hashtable htPlanesContablesConfirmados = (Hashtable)session.getAttribute("htPlanesContablesConfirmados");
										System.out.println("A");
										
										/*
										Enumeration cami = session.getAttributeNames();
										
										while(cami.hasMoreElements()){
										  String clave  = cami.nextElement( ).toString();
										  System.out.println( " A " +   clave + " :"  + session.getAttribute( clave  )   );
										  
										}
										*/
										
										if(htPlanesContablesConfirmados != null)
										{
										System.out.println("B");
										%>
											<tr class="fila-det">
													<td colspan="4" valign="top" class="fila-det-border">
														<table width="100%"  border="0" cellspacing="0" cellpadding="0">
															<tr class="fila-det-bold">
																<td width="17%" height="30" class="fila-det-border">&nbsp;Cód.</td>
																<td width="33%" class="fila-det-border">&nbsp;C.Contable</td>
																<td width="25%" class="fila-det-border">&nbsp;Tipificaci&oacute;n</td>
																<td width="25%" class="fila-det-border">&nbsp;Total</td>
															</tr>
										<%
											Enumeration en = htPlanesContablesConfirmados.keys();
											
											while(en.hasMoreElements())
											{
											System.out.println("C");
												String key = (String)en.nextElement();
												String art[] = (String[])htPlanesContablesConfirmados.get(key);
											 %>
												
															<tr class="fila-det">	
																<td height="25" class="fila-det-border">&nbsp;<%= art[0] %></td>
																<td class="fila-det-border">&nbsp;<%= art[1] %></td>
																<td class="fila-det-border">&nbsp;<%= art[10] %></td>
																<td class="fila-det-border">&nbsp;<%= art[11] %></td>
															</tr>
														
											<%
											}
											 
											%>
														</table>
													</td>
												</tr>
											<%
										}%> 
										<tr class="fila-det-bold">
											<td width="17%" height="28" class="fila-det-border">&nbsp;Total: </td>
											<td colspan="3" class="fila-det-border">
												<table width="100%" border="0">
													<tr>
														<td width="85%">
															<input name="importe" type="text" value="<%=BPMPF.getImporte()%>" class="campo" size="20" maxlength="20" readonly="readonly">
														</td>
												   </tr>
												</table>			   
											</td>
										</tr>
										<%--<tr class="fila-det" height="3">
											<td width="17%" class="fila-det-border"></td>
											<td colspan="4" class="fila-det-border">
												<input name="calcular" type="submit" class="boton" id="calcular" value="Calcular Importes">
											</td>
										</tr>--%>
										<tr class="fila-det">
											<td height="35" class="fila-det-border">&nbsp;</td>
											<td colspan="3" class="fila-det-border">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="29%">
															<input name="validar" type="submit" value="Aceptar Comprobante" class="boton">
														</td>
														<td width="71%">
															<input name="volver" type="submit" class="boton" id="volver" value="Cancelar">
														</td>
													</tr>
												</table>
											</td>
										</tr>
							   			<%
										}
									}
								%>
							</table>
						</td>
					</tr>
				</table>
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