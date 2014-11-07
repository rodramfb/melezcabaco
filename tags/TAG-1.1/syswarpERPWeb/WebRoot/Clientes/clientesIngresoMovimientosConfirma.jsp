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
 <jsp:useBean id="BCIMC"  class="ar.com.syswarp.web.ejb.BeanClientesIngresoMovimientosConfirma"   scope="page"/>

<head>
 <title> <%=titulo%> </title>
 <meta http-equiv="description" content="mypage">
  
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script >
  function setValoresTipocliente(codigo, cliente, cuit, tipoiva, domicilio, cp, provincia, localidad ){
	
	  var CODIGO = document.getElementById('TDO').rows[0].cells[1] ;
	  var CLIENTE = document.getElementById('TDO').rows[0].cells[3] ;
	  var CUIT = document.getElementById('TDO').rows[1].cells[1] ;
	  var TIPOIVA = document.getElementById('TDO').rows[1].cells[3] ;		
	  var DOMICILIO = document.getElementById('TDO').rows[2].cells[1] ;
	  var CP = document.getElementById('TDO').rows[2].cells[3] ;
	  var PROVINCIA = document.getElementById('TDO').rows[3].cells[1] ;
	  var LOCALIDAD = document.getElementById('TDO').rows[3].cells[3] ;
		
		CODIGO.innerHTML = '&nbsp;' + codigo;
		CLIENTE.innerHTML = '&nbsp;' +cliente;
		CUIT.innerHTML = '&nbsp;' + cuit;
		TIPOIVA.innerHTML = '&nbsp;' + tipoiva; // 
		DOMICILIO.innerHTML = '&nbsp;' + domicilio;
		CP.innerHTML = '&nbsp;' + cp;
		PROVINCIA.innerHTML = '&nbsp;' + provincia;
		LOCALIDAD.innerHTML = '&nbsp;' + localidad;
		
	}
	
	
	
  function cancelaDatos(){
  
    if(confirm('Esta acción eliminará todos los datos cargados. Continua de todos modos?')){
	  document.location = 'clientesIngresoMovimientos.jsp'; 
	}
  
  }	
 </script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCIMC" property="*" />
<%
 BCIMC.setResponse(response);
 BCIMC.setRequest(request);
 BCIMC.setSession(session);
 BCIMC.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCIMC.setEjercicioactivo( Integer.parseInt( session.getAttribute("ejercicioActivo").toString() ) );
 BCIMC.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCIMC.ejecutarValidacion();
%>
<form action="clientesIngresoMovimientosConfirma.jsp" method="post" name="frm">
  <input name="accion" type="hidden" value="<%=BCIMC.getAccion()%>" >

		<!-- 5 -->
     <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
       <tr>
         <td colspan="5">
				 <!-- 0 -->
           <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="text-globales">
                <td height="23">&nbsp;<%= titulo %></td>
              </tr>
           </table>
					 <!-- 0 -->		</td>
       </tr>
		<tr class="fila-det">
			<td width="20%" height="25" class="fila-det-border-bold">Tipo de Comprobante: </td>
			<td width="20%" class="fila-det-border"><input type="hidden" value="<%=BCIMC.getTipocomp()%>" name="tipocomp">
			  <% 
			  Iterator iterClientesTipoComp = BCIMC.getListClientesTipoComp().iterator();
			  while(iterClientesTipoComp.hasNext()){  
				String [] datos = (String []) iterClientesTipoComp.next();
				if(BCIMC.getTipocomp().toString().equals(datos[0])) out.write(datos[2]);
			  } %></td>
	
			<td width="17%" class="fila-det-border-bold">Tipo Cliente:  </td>
			<td width="26%" class="fila-det-border"><%= BCIMC.getTipocliente().equalsIgnoreCase("C") ? "Con Cta. Cte.": BCIMC.getTipocliente().equalsIgnoreCase("A") ? "Casual (Anexo)": "" %><input type="hidden" value="<%=BCIMC.getTipocliente()%>" name="tipocliente">
			<input name="tipoclienteanterior" type="hidden" id="tipoclienteanterior" value="<%=BCIMC.getTipocliente()%>" >					</td>
			<td width="17%" class="fila-det-border">&nbsp;</td>
		</tr>
		<tr class="fila-det">
			<td width="20%" height="25" class="fila-det-border-bold">Fecha movimiento:</td>
		  <td width="20%" class="fila-det-border"><%=BCIMC.getFechamov()%><input type="hidden" name="fechamov" value="<%=BCIMC.getFechamov()%>" maxlength="12"></td>
			<td height="20" class="fila-det-border-bold">Sucursal:</td>
			<td class="fila-det-border"><%=Common.strZero( BCIMC.getSucursal(), 4 )%>
                <input name="sucursal" type="hidden" id="sucursal" value="<%=BCIMC.getSucursal()%>" ></td>
			<td width="17%" class="fila-det-border">&nbsp;</td>
		</tr>
		<tr class="fila-det">
		  <td height="25" class="fila-det-border-bold">Condici&oacute;n de Venta:</td>
		  <td class="fila-det-border"><%=BCIMC.getCondicion()%>&nbsp;
              <input name="condicion" type="hidden" id="condicion" value="<%=BCIMC.getCondicion()%>" >
              <input name="idcondicion" type="hidden" id="idcondicion" value="<%=BCIMC.getIdcondicion()%>"></td>
		  <td class="fila-det-border-bold">Cuotas:
	      <input name="cuotas" type="hidden" id="cuotas" value="<%=BCIMC.getCuotas()%>" size="2" ></td>
		  <td class="fila-det-border-bold"><%=BCIMC.getCuotas()%>&nbsp;</td>
		  <td class="fila-det-border">&nbsp;</td>
	   </tr>
		<tr class="fila-det">
		  <td height="25" class="fila-det-border-bold">Nro. Tarjeta:
		    <input name="idtarjeta" type="hidden" id="idtarjeta" value="<%=BCIMC.getIdtarjeta()%>" size="2" > 
	      <input name="nrotarjeta" type="hidden" id="nrotarjeta" value="<%=BCIMC.getNrotarjeta()%>" size="2" ></td>
		  <td class="fila-det-border"><%=BCIMC.getNrotarjeta()%>&nbsp;</td>
		  <td class="fila-det-border-bold">Tarjeta:
	      <input name="tarjetacredito" type="hidden" id="tarjetacredito" value="<%=BCIMC.getTarjetacredito()%>" size="2" ></td>  
		  <td class="fila-det-border"><%=BCIMC.getTarjetacredito()%>&nbsp;</td> 
		  <td class="fila-det-border">&nbsp;</td>
	   </tr>
	   
		<%  
		if(BCIMC.getTipocomp().intValue() == 3){
		
		%>	   
	   
		<tr class="fila-det"> 
		  <td height="25" class="fila-det-border-bold">Motivo NC: <span class="fila-det-border">
		    <input name="idmotivonc" type="hidden" id="idmotivonc" value="<%=BCIMC.getIdmotivonc()%>">
		  </span></td>
		  <td colspan="4" class="fila-det-border">&nbsp;<%=BCIMC.getMotivonc()%></td>
	   </tr>
		
		<% 
		} %>		
		
		
		<tr class="fila-det">
		  <td height="25" class="fila-det-border-bold"> Concepto / Obs.: </td>
		  <td colspan="4" class="fila-det-border">&nbsp;<input name="observaciones" type="hidden" id="observaciones" value="<%=BCIMC.getObservaciones()%>" ><%=BCIMC.getObservaciones()%></td>
	   </tr>
		<tr class="fila-det">
			<td colspan="5"  class="fila-det-border">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr >
					 <td class="fila-det-bold-rojo">     
					   <jsp:getProperty name="BCIMC" property="mensaje"/>					   </td>
					</tr>
				</table>			</td>
		</tr>
				<% 
		if(!BCIMC.getTipocliente().equalsIgnoreCase("")) {%>
		<tr class="fila-det">
		  <td colspan="5"  class="fila-det-border">
				<!-- 4 -->
				<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
				 <tr>
					 <td>
						<!-- 3 -->
						<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
							<tr class="text-globales">
								<td width="22%" height="20" ><%=BCIMC.getLabelTipocliente() %></td>
								<td width="88%" colspan="3" >
									<!-- 1 -->
									<table width="100%"  border="0" cellspacing="3" cellpadding="0" height="100%">
										 <tr class="text-globales">
										   <td width="22%" >&nbsp;</td>
										   <td width="78%">
												<input name="primeraCarga" type="hidden" value="false" >
												<input name="iniciaCobranza" type="hidden" value="false" >
												<input name="idprovincia" type="hidden" id="idprovincia" value="<%=BCIMC.getIdprovincia()%>" >
												<input name="idlocalidad" type="hidden" id="idlocalidad" value="<%=BCIMC.getIdlocalidad()%>" >
                                                <input name="idcliente" type="hidden" id="idcliente" value="<%=BCIMC.getIdcliente()%>" >
                                                <input name="iddomicilio" type="hidden" id="iddomicilio" value="<%=BCIMC.getIddomicilio()%>" >
											    <input name="idtipoiva" type="hidden" id="idtipoiva" value="<%=BCIMC.getIdtipoiva()%>" size="2" >
									       <input name="idclub" type="hidden" id="idclub" value="<%=BCIMC.getIdclub()%>" size="2" >										   </tr>
									</table>	 	
									 <!--1 -->								</td>
							</tr>
							
							<tr class="text-globales" height="3">
								<td width="12%" class="fila-det-border"></td>
								<td colspan="4" class="fila-det-border" ></td>
							</tr>											
							<tr class="fila-det">
								<td colspan="4" class="fila-det-border">
								<!-- 2 --> 
								<% 
								if(!BCIMC.getTipocliente().equalsIgnoreCase("A")) {%>
								<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="TDO">
									<tr class="fila-det">
									  <td height="25" class="fila-det-border-bold">C&oacute;digo</td>
									  <td class="fila-det-border"><%=BCIMC.getIdcliente().compareTo(new BigDecimal(0)) > 0 ? BCIMC.getIdcliente().toString() : "" %>&nbsp;</td>
									  <td class="fila-det-border-bold">Cliente</td>
									  <td class="fila-det-border">&nbsp;<%=BCIMC.getCliente()%></td>
								  </tr>
									<tr class="fila-det">
										<td width="9%" height="25" class="fila-det-border-bold">CUIT</td>
										<td width="39%" class="fila-det-border"><%=BCIMC.getNrodocumento()%></td>
										<td width="12%" class="fila-det-border-bold">Tipo de Iva  </td>
										<td width="40%" class="fila-det-border">&nbsp;<%=BCIMC.getTipoiva()%></td>
									</tr>
									<tr class="fila-det">
										<td height="25" class="fila-det-border-bold">Domicilio</td> 
										<td class="fila-det-border"><%=BCIMC.getDomicilio()%></td>
										<td class="fila-det-border-bold">CP</td>
										<td class="fila-det-border">&nbsp;<%=BCIMC.getPostal()%></td>
									</tr>
									<tr class="fila-det">
									  <td height="25" class="fila-det-border-bold">Provincia</td>
									  <td class="fila-det-border"><%=BCIMC.getProvincia()%></td>
									  <td class="fila-det-border-bold">Localidad</td>
									  <td class="fila-det-border">&nbsp;<%=BCIMC.getLocalidad()%></td>
								  </tr>
								</table>	
								<!-- ELSE-->
								<% 
								}
								else{ %>
								<table width="100%"  border="0" cellspacing="0" cellpadding="0">
									<tr class="fila-det">
									  <td height="25" class="fila-det-border-bold">Cliente</td>
									  <td colspan="3" class="fila-det-border"><%=BCIMC.getCliente()%><input name="cliente" type="hidden" id="cliente" value="<%=BCIMC.getCliente()%>"></td>
									</tr>
									<tr class="fila-det">
										<td width="14%" height="25" class="fila-det-border-bold">CUIT</td>
										<td width="34%" class="fila-det-border"><%=BCIMC.getNrodocumento()%><input name="nrodocumento" type="hidden" id="nrodocumento" value="<%=BCIMC.getNrodocumento()%>"></td>
										<td width="11%" class="fila-det-border-bold">Tipo de Iva  </td>
										<td width="41%" class="fila-det-border"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
											<tr class="fila-det">
											  <td width="71%" height="25" ><%=BCIMC.getTipoiva()%><input name="tipoiva" type="hidden" id="tipoiva" value="<%=BCIMC.getTipoiva()%>" ></td>
											  <td width="29%" >&nbsp;</td>
											</tr>
										  </table></td>
									</tr>
									<tr class="fila-det">
										<td height="25" class="fila-det-border-bold">Domicilio</td>
										<td class="fila-det-border"><%=BCIMC.getDomicilio()%><input name="domicilio" type="hidden" id="domicilio" value="<%=BCIMC.getDomicilio()%>" ></td>
										<td class="fila-det-border-bold">Localidad</td>
										<td >												  
										<table width="100%"  border="0" cellspacing="0" cellpadding="0">
											<tr class="fila-det">
											  <td width="71%" height="25" class="fila-det-border"><%=BCIMC.getLocalidad()%><input name="localidad" type="hidden" id="localidad" value="<%=BCIMC.getLocalidad()%>" ></td>
											  <td width="29%" class="fila-det-border">&nbsp;</td>
											</tr>
										</table>									  </td>
									</tr>
									<tr class="fila-det">
									  <td height="25" class="fila-det-border-bold">Provincia</td>
									  <td >											    
										<table width="100%"  border="0" cellspacing="0" cellpadding="0">
											<tr >
											  <td width="76%" height="25" class="fila-det"><%=BCIMC.getProvincia()%>
											  <input name="provincia" type="hidden" id="provincia" value="<%=BCIMC.getProvincia()%>" ></td>
											  <td width="24%" class="fila-det-border">&nbsp;</td>
											</tr>
										  </table>										 </td>
									  <td class="fila-det-border-bold">CP</td>
									  <td class="fila-det-border"><%=BCIMC.getPostal()%><input name="postal" type="hidden"  id="postal" value="<%=BCIMC.getPostal()%>" ></td>
								  </tr>
								</table>
								<% 
								}
								 %>																				
								<!-- 2 -->							</td>
						</tr>				
						<tr class="text-globales" height="3">
							<td width="12%" class="fila-det-border"></td>
							<td colspan="4" class="fila-det-border" ></td>
						</tr>		
					</table>
							<!-- 3 -->						</td>
			</tr>
				<%
				  Hashtable htArticulosInOutOK = (Hashtable)session.getAttribute("htArticulosInOutOK");
				  if(!BCIMC.getMueveStock().equalsIgnoreCase("N") && !BCIMC.getMueveStock().equals("") ) {%>
				 <tr>
					 <td>
						<!-- 3.1 -->
						<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
							<tr class="text-globales">
								<td width="22%" height="20" >&nbsp;STOCK  </td>
								<td width="78%" colspan="3" >
									<!-- 1.1 -->
									<table width="100%"  border="0" cellspacing="3" cellpadding="0" height="100%">
										 <tr class="text-globales">
											 <td width="24%" > Art&iacute;culos </td>
											 <td width="76%">&nbsp;</td>
										 </tr>
									</table>		
									 <!--1.1 -->										</td>
							</tr>
							<tr class="fila-det">
								<td colspan="4" valign="top" class="fila-det-border">
								<!-- 2.1 -->
								<table width="100%"  border="0" cellspacing="0" cellpadding="0">
									<tr class="fila-det-bold">
										<td width="48%" height="20" class="fila-det-border">Articulo</td>
										<td width="21%" class="fila-det-border">Cantidad</td>
										<td width="18%" class="fila-det-border">Precio Unidad </td>
										<td width="13%" class="fila-det-border"><div align="center">Total</div></td>
									</tr>
								<% 
										
										if(htArticulosInOutOK != null){
											 Enumeration en = htArticulosInOutOK.keys();
											 while(en.hasMoreElements()){
											   String key = (String)en.nextElement();
												 String art[] = (String[])htArticulosInOutOK.get(key);
												 //BCIMC.setTotalDebe(BCIMC.getTotalDebe().add(new BigDecimal( art[11] )));																
												 %>
									<tr class="fila-det">
										<td height="20" class="fila-det-border">&nbsp;<%= art[0] %></td>
										<td class="fila-det-border">&nbsp;<%= art[10] %></td>
										<td class="fila-det-border">&nbsp;<%= art[5] %></td>
										<td class="fila-det-border">
										  <div align="right">&nbsp;<%= art[11] %></div></td>
									</tr>
										<% 
											 }
										} 
										if(BCIMC.getTotalDebe().compareTo(new BigDecimal("0")) > 0){
										%>
									<tr class="fila-det-bold">
										<td class="fila-det-border" colspan="3"><div align="right">Total general &nbsp;</div></td>
										<td class="fila-det-border"><div align="right">&nbsp;<%= BCIMC.getTotalDebe() %></div></td>
									</tr>												
										<% 
										} 
										%>												
								</table>	
								<!-- 2.1 -->							</td>
						</tr>				
						<tr class="text-globales" height="3">
							<td width="22%" class="fila-det-border"></td>
							<td colspan="4" class="fila-det-border" ></td>
						</tr>		
					</table>
							<!-- 3.1 -->				</td>
			</tr>
			<% } %>
						<!-- 3.2 -->
						<tr>
						  <td>								 
							<!-- 1.2 -->
							 <table width="100%"  border="0" cellspacing="0" cellpadding="0" height="100%">
							   <tr class="text-globales">
								 <td width="22%" height="20" >COBRANZA</td>
								 <td width="78%" ><input type="button" name="actualiza2" value=">>" class="boton" onClick="abrirVentana('../Tesoreria/lov_cajaIdentificadoresPropioAbm.jsp?propio=N', 'ingresos', 800, 400)"></td>
							   </tr>
							   <tr >
								 <td height="20" colspan="2" >
								 <!-- -->
									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
										<tr >
										  <td width="10%" class="fila-det-bold">Ide.</td>
										  <td width="9%" class="fila-det-bold">CC1</td>
										  <td width="8%" class="fila-det-bold">CC2</td>
										  <td width="11%" class="fila-det-bold">N&uacute;mero</td>
										  <td width="37%" class="fila-det-bold">Detalle</td>
										  <td width="13%" class="fila-det-bold">Fecha</td>
										  <td width="12%" class="fila-det-bold"><div align="right">Importe</div></td>
										</tr>
										<tr height="3">
										  <td colspan="9" class="text-globales" height="3"> </td>
										</tr>
									<%	Hashtable htIdentificadoresIngresosOK = (Hashtable) session.getAttribute("htIdentificadoresIngresosOK");    
										if(htIdentificadoresIngresosOK != null && !htIdentificadoresIngresosOK.isEmpty()){
											Enumeration en = htIdentificadoresIngresosOK.keys();
											while (en.hasMoreElements()) {
												String key = (String) en.nextElement();
												String [] identificadores = (String []) htIdentificadoresIngresosOK.get(key);
															  %>
												<tr class="fila-det" >
												  <td width="10%" class="fila-det-border"> <%= key %> &nbsp;</td>
												  <td width="9%" class="fila-det-border"><%= identificadores[20] %>&nbsp; </td>
												  <td width="8%" class="fila-det-border"><%= identificadores[21] %>&nbsp;</td>
												  <td width="11%" class="fila-det-border"><%= identificadores[30] %>&nbsp;</td>
												  <td width="37%" class="fila-det-border"><%= identificadores[31] %>&nbsp;</td>
												  <td width="13%" class="fila-det-border">&nbsp;</td>
												  <td width="12%" class="fila-det-border">&nbsp;<div align="right"><%= identificadores[28] %></div></td>
												</tr>
												<% 
											}
										}
										%>
									</table>										 
								 
								 <!-- -->								 </td>
							   </tr>
							 </table>
						   <!--1.2 -->						  </td>
					  </tr>	
                              <tr class="text-globales">
                                 <td colspan="4" >&nbsp;								 </td>
                               </tr>
					  
					 <tr>
					 <td>
					 <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
					   <tr class="text-globales">
						 <td  colspan="4" ></td>
					   </tr>
					   
					  <tr class="fila-det">
						 <td colspan="4"   valign="top" class="fila-det-border">
						 <!-- 2.2 -->
						 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
							  <tr class="fila-det">
							 <td width="18%" height="20" class="fila-det-border-bold">Neto Gravado :</td>
							 <td width="42%"  class="fila-det-border">
							 <table width="70%" border="0" class="fila-det">
								 <tr>
								   <td width="40%">&nbsp;</td>
								   <td width="75%"><div align="right"><%=BCIMC.getNetogravado() + "" %>
								     <input name="netogravado" type="hidden" value="<%=BCIMC.getNetogravado()%>" class="campo" size="20" maxlength="20"  />
							         <input name="netogravadoaux" type="hidden" id="netogravadoaux" value="<%=BCIMC.getNetogravadoaux()%>" >
							       </div></td>
								 </tr>
							 </table>							 </td>
							 <td width="40%"  class="fila-det-border"><table width="100%" border="0" cellpadding="0" cellspacing="0"   >
							   <tr>
								 <td width="34%" ><div align="right">
								   <input name="cuentanetogravado" id="cuentanetogravado" type="text" value="<%=BCIMC.getCuentanetogravado()%>" class="campo" size="20" maxlength="20" style="text-align:right"/>
							     </div></td>
								 <td width="66%" ><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('lov_clientesInfiPlanAbm.jsp?elemento=cuentanetogravado', 'imputacion', 700, 250)" style="cursor:pointer" /></td>
							   </tr>
							 </table>							 </td>
						   </tr>
						   <tr class="fila-det">
							 <td width="18%" class="fila-det-border-bold">IVA : </td>
							 <td  class="fila-det-border"><table width="70%" border="0" class="fila-det">
								 <tr>
								   <td width="40%" class="fila-det"><div align="right"><%=BCIMC.getPorcentajeivauno() + "" %>
							         <input name="porcentajeivauno" type="hidden" id="porcentajeivauno" value="<%=BCIMC.getPorcentajeivauno()%>"  />
									 %</div></td>
								   <td width="75%"><div align="right"><%=BCIMC.getIvauno() + "" %>
								     <input name="ivauno" type="hidden" value="<%=BCIMC.getIvauno()%>" />
							       </div></td>
								 </tr>
							 </table></td>
							 <td  class="fila-det-border">
							 <% if(BCIMC.isNecesario(BCIMC.getIvauno().toString())) {%>
							 <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
							   <tr>
								 <td width="34%" ><div align="right">
								   <input name="cuentaiva" id="cuentaiva" type="text" value="<%=BCIMC.getCuentaiva()%>" class="campo" size="20" maxlength="20" style="text-align:right"/>                                         
							     </div></td>
								 <td width="66%" ><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('lov_clientesInfiPlanAbm.jsp?elemento=cuentaiva', 'imputacion', 700, 250)" style="cursor:pointer"></td>
							   </tr>
							 </table>
							 <% } else out.write("&nbsp;"); %>									 </td> 
						   </tr>
						   <tr class="fila-det">
							 <td width="18%" class="fila-det-border-bold">IVA : </td>
							 <td  class="fila-det-border"><table width="70%" border="0" class="fila-det">
								 <tr>
								   <td width="40%" class="fila-det"><div align="right">
								     <input name="porcentajeivados" type="hidden" id="porcentajeivados" value="<%=BCIMC.getPorcentajeivados()%>" />
								     <%=BCIMC.getPorcentajeivados() + "" %>
									 %</div></td>
								   <td width="75%"><div align="right"><%=BCIMC.getIvados() + "" %>
								     <input name="ivados" type="hidden" value="<%=BCIMC.getIvados()%>"/>
							       </div></td>
								 </tr>
							 </table></td>
							 <td  class="fila-det-border">
							 <% if(BCIMC.isNecesario(BCIMC.getIvados().toString())) {%>									 
							 <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
							   <tr >
								 <td width="34%" ><div align="right">
								   <input name="cuentaivanoinscripto" id="cuentaivanoinscripto" type="text" value="<%=BCIMC.getCuentaivanoinscripto()%>" class="campo" size="20" maxlength="20" style="text-align:right"/>                                         
							     </div></td>
								 <td width="66%" ><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('lov_clientesInfiPlanAbm.jsp?elemento=cuentaivanoinscripto', 'imputacion', 700, 250)" style="cursor:pointer"></td>
							   </tr>
							 </table>
							 <% } else out.write("&nbsp;");%>									 </td>
						   </tr>								   								   
						   <tr class="fila-det">
							 <td width="18%" height="20" class="fila-det-border-bold">Impuesto Int.: </td>
							 <td  class="fila-det-border"><table width="70%" border="0">
							   <tr class="fila-det">
								 <td width="40%"><div align="right">
								   <input name="imp_int_tc" type="hidden"  id="imp_int_tc" value="<%=BCIMC.getImp_int_tc() %>" />
								   <%=BCIMC.getImp_int_tc() + "" %>
						         %</div></td>
								 <td width="75%"><div align="right"><%=BCIMC.getImpuestosinternos() + "" %>
								   <input name="impuestosinternos" type="hidden" value="<%=BCIMC.getImpuestosinternos()%>">
							     </div></td>
							   </tr>
							 </table></td>
							 <td  class="fila-det-border"><table width="100%" border="0">
							   <tr class="fila-det">
								 <td width="35%" class="fila-det-bold">Recargo&nbsp;: </td>
								 <td width="65%"><%=BCIMC.getRecargo() + "" %><input name="recargo" type="hidden" value="<%=BCIMC.getRecargo()%>">                                         </td>
							   </tr>
							 </table></td>
						   </tr>
						   <tr class="fila-det">
						     <td height="20" class="fila-det-border-bold">Per. IIBB-BA  : </td>
						     <td  class="fila-det-border"><table width="70%" border="0" class="fila-det">
                               <tr>
                                 <td width="40%"><div align="right">
                                   <input name="porcentajeiibb" type="hidden" id="porcentajeiibb" value="<%=BCIMC.getPorcentajeiibb()%>" />
                                   <%=BCIMC.getPorcentajeiibb() + ""%> %</div></td>
                                 <td width="75%"><div align="right"><%=BCIMC.getPercepcioniibb() + "" %>
                                   <input name="percepcioniibb" type="hidden" id="percepcioniibb" value="<%=BCIMC.getPercepcioniibb()%>" >
                                 </div></td>
                               </tr>
                             </table></td>
						     <td  class="fila-det-border"><% if(BCIMC.isNecesario(BCIMC.getPercepcioniibb().toString())) {%>
                               <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
                                 <tr >
                                   <td width="34%" ><div align="right">
                                       <input name="cuentaiibb" id="cuentaiibb" type="text" value="<%=BCIMC.getCuentaiibb()%>" class="campo" size="20" maxlength="20" style="text-align:right"/>
                                   </div></td>
                                   <td width="66%" ><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('lov_clientesInfiPlanAbm.jsp?elemento=cuentaiibb', 'imputacion', 700, 250)" style="cursor:pointer"></td>
                                 </tr>
                               </table>
                               <% } else out.write("&nbsp;");%></td>
					       </tr>
						   <tr class="fila-det">
							 <td width="18%" height="20" class="fila-det-border-bold">Otros Imp.:  </td>
							 <td  class="fila-det-border"><table width="70%" border="0" class="fila-det">
							   <tr>
								 <td width="40%">&nbsp;</td>
								 <td width="75%"><div align="right"><%=BCIMC.getOtrosimpuestos() + "" %>
								   <input name="otrosimpuestos" type="hidden" value="<%=BCIMC.getOtrosimpuestos()%>" >                                         
							     </div></td>
							   </tr>
							 </table></td>
							 <td  class="fila-det-border"><table width="100%" border="0">
							   <tr class="fila-det">
								 <td width="35%" class="fila-det-bold">Bonificacion % &nbsp;: </td>
								 <td width="65%"><%=BCIMC.getBonificacion()%><input name="bonificacion" type="hidden" value="<%=BCIMC.getBonificacion()%>" >                                         </td>
							   </tr>
							 </table></td>
						   </tr>
						   <tr class="fila-det">
							 <td width="18%" height="20" class="fila-det-border-bold">Total:</td>
							 <td  class="fila-det-border"><table width="70%" border="0" class="fila-det-bold">
								 <tr>
								   <td width="40%">&nbsp;</td>
								   <td width="75%"><div align="right"><%=BCIMC.getTotal() + "" %>
								     <input name="total" type="hidden" value="<%=BCIMC.getTotal()%>">
							         <input name="totalaux" type="hidden" id="totalaux" value="<%=BCIMC.getTotalaux()%>">
							       </div></td>
								 </tr>
							 </table></td>
							 <td  class="fila-det-border">&nbsp;</td>
						   </tr>
						   <tr class="fila-det">
							 <td width="18%" height="20" class="fila-det-border-bold">No Gravado : </td>
							 <td  class="fila-det-border"><table width="70%" border="0" class="fila-det">
							   <tr>
								 <td width="40%" class="fila-det">&nbsp;</td>
								 <td width="75%"><div align="right"><%=BCIMC.getTotalnogravado() + "" %>
								   <input name="totalnogravado" type="hidden" value="<%=BCIMC.getTotalnogravado()%>">
							       <input name="totalnogravadoaux" type="hidden" id="totalnogravadoaux" value="<%=BCIMC.getTotalnogravadoaux()%>" >
							     </div></td>
							   </tr>
							 </table>							 </td>
							 <td  class="fila-det-border">
							 <% if(BCIMC.isNecesario(BCIMC.getTotalnogravado().toString())) {%>									 									 <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
							   <tr >
								 <td width="34%" ><div align="right">
								   <input name="cuentanetoexento" id="cuentanetoexento" type="text" value="<%=BCIMC.getCuentanetoexento()%>" class="campo" size="20" maxlength="20" style="text-align:right"/>
							     </div></td>
								 <td width="66%" ><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('lov_clientesInfiPlanAbm.jsp?elemento=cuentanetoexento', 'imputacion', 700, 250)" style="cursor:pointer" /></td>
							   </tr>
							 </table>
							 <% } else out.write("&nbsp;");%>							</td>
						   </tr>
						   <tr class="fila-det-bold">
							 <td width="18%" height="20" class="fila-det-border">Total Factura: </td>
							 <td  class="fila-det-border">
							 
							 <table width="70%" border="0" class="fila-det-bold">
								 <tr>
								   <td width="40%">&nbsp;</td>
								   <td width="75%"><div align="right"><%=BCIMC.getTotalfactura() + "" %>
								     <input name="totalfactura" type="hidden" value="<%=BCIMC.getTotalfactura()%>" >
							       </div></td>
								 </tr>
							 </table>							 </td>
							 <td  class="fila-det-border">
							 <% if(BCIMC.isNecesario(BCIMC.getTotalfactura().toString()) ) {%>										 									 <table width="100%" border="0" cellpadding="0" cellspacing="0"  >
							   <tr >
								 <td width="34%" ><div align="right">
								   <input name="cuentatotal" id="cuentatotal" type="text" value="<%=BCIMC.getCuentatotal()%>" class="campo" size="20" maxlength="20" style="text-align:right"/>                                         
							     </div></td>
								 <td width="66%" ><img src="../imagenes/default/gnome_tango/actions/edit-find-replace.png" width="18" height="18" onClick="abrirVentana('lov_clientesInfiPlanAbm.jsp?elemento=cuentatotal', 'imputacion', 700, 250)" style="cursor:pointer"></td>
							   </tr>
							 </table>
							 <% } else out.write("&nbsp;");%>							  </td>
						   </tr>
						   <tr class="text-globales" height="3">
							 <td height="20" class="fila-det-border"> </td>
							 <td class="fila-det-border" ></td>
							 <td class="fila-det-border" ></td>
						   </tr>
						   <tr class="fila-det">
							 <td height="20" class="fila-det-border">&nbsp;</td>
							 <td colspan="2"  class="fila-det-border"><table width="100%" border="0">
								 <tr>
								   <td width="13%">&nbsp;</td>
								   <td width="87%"><input name="confirmar" type="submit" class="boton" id="confirmar" value="Confirmar Movimiento">
									 <input name="volver" type="button" class="boton" id="volver" value="Cancelar" onClick="cancelaDatos();"/></td>
								 </tr>
							 </table></td>
						   </tr>
						 </table>
						 <!-- 2.2 -->						 </td>
					   </tr>
					   <tr class="text-globales" height="3">
						 <td class="fila-det-border"></td>
						 <td colspan="6" class="fila-det-border" ></td>
					   </tr>
					 </table>
					 <!-- 3.2 -->				</td>
			</tr>
		</table> 
		<!-- 4 -->
		<% 
		}%>		</td>
	 </tr>
	 <tr class="fila-det">
	   <td colspan="5"  class="fila-det-border">&nbsp;</td>
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