S<%@page language="java"%>
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
		class="ar.com.syswarp.web.ejb.BeanPedidos_cabeFrm" scope="page" />
	<head>
		<title>Pedidos Clientes</title>
		 <link rel = "stylesheet" href = "../imagenes/default/erp-style.css">

		 <link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
		<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
		

		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
	<BODY >
		<div id="popupcalendar" class="text"></div> 
		<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "Captura de Pedidos de Clientes";
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 BPF.setSession(session);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.ejecutarValidacion();
 Iterator iter = null; 




 %>
<script>

var existeDetalle  = false;
var valorActualLista = -1;
var ventana=null;


function validarCliente(){   
	if (document.frm.idcliente.value==0 || document.frm.idcliente.value==""){
		alert("Seleccione primero el Cliente");	 		
	}else{
		abreVentanaNombre("clientesclientesDetalleFrm.jsp?idcliente=" + document.frm.idcliente.value , 'Cliente',700, 400);  
	}	 
}


function selectDomicilio(obj){
  //EJV
  if(limpiarArticulos(obj)){
	  setDatosDomicilio(obj.options[obj.selectedIndex].value);
    document.frm.submit();
  }
}

function selectLista(obj){
  if(limpiarArticulos(obj)){
    document.frm.submit();
  }
}

function setDatosDomicilio(iddomicilio){
  var vendedores = new Array();
	var tieneDomicilios = false;
	<%=  BPF.getDatosJSDomi()  %>
	
<%
// Trasladar codigo a BEAN
iter = BPF.getListaVendedores().iterator();
String array = "";
while(iter.hasNext()){
 String [] datos = (String[])iter.next();
 //array += "vendedores["+   datos[0]  + "]='" + datos[1] + "'; \n";
}
out.write(array);
%>
	
	if(iddomicilio > 0){
		if(tieneDomicilios){
		
			var domicilio = eval ('datosDomicilio' + iddomicilio);
			
			document.frm.idexpreso.value = domicilio[0];
			document.frm.expreso.value = domicilio[1];
			document.frm.telefonos.value = domicilio[3] + ' / ' +  domicilio[4] ;
			document.frm.idzona.value = domicilio[5];
			document.frm.obsentrega.value = domicilio[7]; 
      document.frm.idtipoivaloca.value = domicilio[8]; 
	  document.frm.idanexolocalidad.value = domicilio[9]; 

		}
		else{
			alert("Cliente actual no tiene asociado ningún domicilio.");
		}
	}
	else{
	    document.frm.expreso.value = '';
		document.frm.idexpreso.value = '-1';
	}
}

function setIndiceTarjeta(obj){
	if(obj.options[obj.selectedIndex].value != 1){
		document.frm.idtarjeta.selectedIndex = 0;	  
	}
}

function getDetalleTarjeta(obj){
  var warn = obj.options[obj.selectedIndex].style.backgroundColor;
	if(obj.selectedIndex != 0){
		if( warn != ""){
			overlib(obj.options[obj.selectedIndex].id, STICKY, CAPTION, 'ATENCION: TARJETA VENCIDA - INHABILITADA !!',TIMEOUT,5000,HAUTO,VAUTO, WIDTH,350,BGCOLOR, warn, CAPCOLOR, '#FF0000');
		}
		else{
			overlib(obj.options[obj.selectedIndex].id, STICKY, CAPTION, 'DETALLE TARJETA ',TIMEOUT,5000,HAUTO,VAUTO,WIDTH,350);
		}
	}
}

function limpiarArticulos(obj){

  var indiceActualLista = -1;

  for(var x =0;x<obj.length;x++){
    if(obj.options[x].value == valorActualLista ){
      indiceActualLista = x;
      break;
    } 
  }

  if(ventana &&  !ventana.closed ) ventana.close();
	if(existeDetalle){
		if( confirm('Esta acción eliminará los artículos \ncargados hasta el momento. Confirma de todos modos?') ){
			document.frm.accion.value = 'limpiardetalle';
			return true; 
		}
		else{ 
			obj.options[indiceActualLista].selected = true;
      return false;
		}	
	}else	
	  return true; 		
}

function mostrarMensaje(mensaje){
	overlib( mensaje , STICKY, CAPTION, '[INFO]',TIMEOUT,25000,FIXX,0,FIXY,0,WIDTH,350,BGCOLOR,'#FF9900');  
}

function detalleDomicilio(detalle){
	overlib( detalle , STICKY, CAPTION, '[INFO]',TIMEOUT,25000,HAUTO,VAUTO, LEFT, OFFSETX , 50, WIDTH,350,BGCOLOR,'#FF9900');
}

function setPorcentajeAplicado(obj, indice){
  if(document.frm.porcdesc_apli.length){
		if(obj.selectedIndex == 0 )
			document.frm.porcdesc_apli[indice].value = 0;
		else
			document.frm.porcdesc_apli[indice].value = obj.options[obj.selectedIndex].text;	
	}
	else{
		if(obj.selectedIndex == 0 )
			document.frm.porcdesc_apli.value = 0;
		else
			document.frm.porcdesc_apli.value = obj.options[obj.selectedIndex].text;
	}			
}


function validarCliente(idcliente){

  if(idcliente < 1)
    alert ("Es necesario seleccionar cliente.");
	else
    abrirVentana('clientesDomiciliosFrm.jsp?cliente=<%= BPF.getCliente() %>&idcliente=<%= BPF.getIdcliente() %>&accion=alta', 'dom', 800, 450);

}

function callMaps(){

  var objDomicilio = document.frm.iddomicilio;
  if(objDomicilio.selectedIndex < 1)
    alert ("Es necesario seleccionar domicilio.");
	else
    abrirVentana('mapaprueba.jsp?iddomicilio=<%= BPF.getIddomicilio() %>&idempresa=<%= BPF.getIdempresa() %>', 'maps', 800, 450);

}

 
function backGestionTM(){

   document.frm.accion.value =  document.frm.accionGTM.value;
   document.frm.action = 'bacoTmSeleccionSocio.jsp' ;
   document.frm.submit();

}

window.onload = function (){
 <% 
  if(!BPF.getMensaje().equals("")){ %>  
   mostrarMensaje('<%=BPF.getMensaje()%>');  
 <%
  }%>

    document.getElementById('confirmar').onclick = function(){
    document.getElementById('confirmar').disabled = true ;
    document.getElementById('validar').value = 'validar';
  }

}

function setConfirmar(){

  if(document.frm.confirmar.disabled ){
   document.frm.confirmar.value = 'Procesando ...';
  }

}

function getCtacteReferidos(){
  var idcliente = <%= BPF.getIdcliente() %>;
  if(idcliente < 1)
    alert ("Es necesario seleccionar cliente.");
  else
    abrirVentana('bacoRefCtaCteAbm.jsp?cliente=<%= BPF.getCliente() %>&idcliente=<%= BPF.getIdcliente() %>', 'refctacte', 800, 450);
}



</script>
 
 
		<form action="pedidos_cabeFrm.jsp" method="post" name="frm" onSubmit="setConfirmar()">
			<input name="accion" type="hidden" value="">
            <input name="accionGTM" type="hidden" id="accionGTM" value="<%= BPF.getAccionGTM() %>">
			<input name="validar" id="validar" type="hidden" value="">
			<input name="idcampacabe" type="hidden" value="<%=BPF.getIdcampacabe()%>">
            <input name="idresultado" type="hidden" value="<%=BPF.getIdresultado()%>">
			<input name="primeraCarga" type="hidden" value="false" >
	        <input name="recargar" type="hidden" id="recargar" value="1" >
			<input name="idzona" type="hidden" value="<%=BPF.getIdzona()%>">						
			<input name="origenpedido" type="hidden" id="origenpedido" value="<%=BPF.getOrigenpedido()%>">
			<input name="adidesc" type="hidden" id="adidesc" value="<%=BPF.getAdidesc()%>">
			<input name="idtipoivaloca" type="hidden" id="idtipoivaloca" value="<%=BPF.getIdtipoivaloca()%>">
			<input name="idtipoivacliente" type="hidden" id="idtipoivacliente" value="<%=BPF.getIdtipoivacliente()%>">
			<input name="idlistaReferidos" type="hidden" id="idlistaReferidos" value="17"> 
			<input name="idanexolocalidad" type="hidden" id="idanexolocalidad" value="<%=BPF.getIdanexolocalidad()%>">
			<input name="idtipoclie" type="hidden" id="idtipoclie" value="<%=BPF.getIdtipoclie()%>">
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
											<td width="542"><div align="right"><%= titulo %>&nbsp;</div></td> 
											<td width="753"><table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <tr>
                          <td width="15%"><div align="right">
                            <% if(BPF.getIdcampacabe().longValue()>0) {%> 
                            <input type="button" name="Submit" value="&lt;&lt; Gesti&oacute;n TM" onClick="backGestionTM();" class="boton" >
                            <% } %>
                          </div></td>
                          <td width="14%"><div align="right">
                            <% if(BPF.getIdpedido_cabe().longValue() > 0){
													   		String plantillaImpresionJRXML= BPF.getTipopedido().equalsIgnoreCase("N") ? "notas_pedido" : "notas_pedido_regalos";%>
                            <img src="../imagenes/default/gnome_tango/apps/pdf.jpg" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=<%= plantillaImpresionJRXML %>&idpedido_cabe=<%=BPF.getIdpedido_cabe()%>','pedido',750, 400)" style="cursor:pointer" title="Impresión Nota de Pedido">
                            <%}%>
                          </div></td>
                          <td width="12%"><div align="right"><img src="../imagenes/default/gnome_tango/actions/stock_new-bcard.png" width="22" height="22" onClick="getCtacteReferidos()" style="cursor:pointer" title="Cuenta corriente referidos." ></div></td>
                          <td width="11%"><div align="right"><img src="../imagenes/default/gnome_tango/status/gtk-missing-image.png" title="Suspensión de Entregas Regulares" width="22" height="22" onClick="ventana=abrirVentana('Suspensionentregaregular.jsp?cliente=<%= BPF.getCliente() %>&idcliente=<%= BPF.getIdcliente() %>', 'hist', 800, 400)" style="cursor:pointer"></div></td>
                          <td width="10%"><div align="right"><span class="fila-det-border"><img src="../imagenes/default/gnome_tango/apps/date.png" width="22" height="22" onClick="abrirVentana('../Clientes/clientesPeriodicidadEntregaFrm.jsp?idcliente=<%=BPF.getIdcliente()%>&accion=modificacion', 'periodicidad', 800, 450)" style="cursor:pointer" title="Periodicidad"></span></div></td>
                          <td width="10%"><div align="right"><img src="../imagenes/default/gnome_tango/actions/phone.png" width="22" height="22"  onClick="ventana=abrirVentana('bacoTmLlamadosFrm.jsp?cliente=<%= BPF.getCliente() %>&idcliente=<%= BPF.getIdcliente() %>&idcampacabe=<%= BPF.getIdcampacabe() %>&campacabe=<%= BPF.getCampacabe() %>&accion=alta', 'llamados', 800, 400)" style="cursor:pointer" title="Registrar llamado."></div></td>
                          <td width="9%"><div align="right"><img src="../imagenes/default/gnome_tango/actions/fileopen.png" title="Consulta Cuenta Corriente" width="22" height="22" onClick="ventana=abrirVentana('vclientesCtaCtesAbm.jsp?cliente=<%= BPF.getCliente() %>&idcliente=<%= BPF.getIdcliente() %>', 'ctacte', 800, 400)" style="cursor:pointer"></div></td> 
                          <td width="9%"><div align="right"><img src="../imagenes/default/gnome_tango/apps/gnome-session.png" title="Hist&oacute;rico de pedidos" width="22" height="22" onClick="ventana=abrirVentana('pedidosHistoricoCliente.jsp?cliente=<%= BPF.getCliente() %>&idcliente=<%= BPF.getIdcliente() %>', 'hist', 800, 400)" style="cursor:pointer"></div></td>
                          <td width="10%"><div align="right"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="22" height="22" title="Limpiar datos" onClick="if(confirm('Confirma limpiar datos ?')) window.location = 'pedidos_cabeFrm.jsp'" style="cursor:pointer"></div></td>
                        </tr>
                      </table>											
											</td>
										</tr>
								  </table>
									
								</td>
							</tr>
					  </table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold-rojo">
								<td class="fila-det-border">&nbsp;								</td>
								<td colspan="4" class="fila-det-border">&nbsp;</td>
							</tr>
							<input name="idestado" type="hidden" id="idestado" value="1">
							<tr class="fila-det">
							  <td height="27" class="fila-det-border">Pedido Normal:(*) </td>
							  <td class="fila-det-border"><input name="tipopedido" type="radio" class="campo" value="N" <%= BPF.getTipopedido().equalsIgnoreCase("N") ? "checked" : "" %> ></td>
							  <td valign="bottom" class="fila-det-border">&nbsp;</td>
							  <td class="fila-det-border">Ventas Especiales:(*) </td> 
							  <td class="fila-det-border"><select name="idventaespecial" id="idventaespecial" class="campo" style="width:200px" >
                                <option value="-1">Distribución Normal</option>
                                <% 
									  iter = BPF.getListVentasEspeciales().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                                <option value="<%= datos[0] %>" <%= datos[0].equals( BPF.getIdventaespecial().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                                <%  
									  }%>
                              </select></td>
							</tr>
							<tr class="fila-det">
								<td width="18%" height="27" class="fila-det-border">Cliente: (*)</td>
								<td width="28%" class="fila-det-border">
								   <input name="idcliente" type="hidden" id="idcliente" 
													value="<%=BPF.getIdcliente()%>" size="7"  readonly maxlength="7">
					      <input name="cliente" type="text" class="campo" id="cliente"
														value="<%=BPF.getCliente()%>" size="60" readonly >						      </td>
							  <td width="13%" valign="bottom" class="fila-det-border"><% if(BPF.getIdcampacabe().longValue()<0) {%><img src="../imagenes/default/gnome_tango/actions/filefind.png"
														width="18" height="18"
														onClick="abrirVentana('../Clientes/lov_clientesPedidos.jsp', 'clientes', 800, 400)"
														style="cursor:pointer" ><% } %>&nbsp;</td>
								<td width="18%" class="fila-det-border">Fecha Pedido(*)</td>
							  <td width="23%" class="fila-det-border"> 
							  <input name="fechapedido" type="text" class="campo"
													id="fechapedido" 
													value="<%=BPF.getFechapedido()%>" size="12"
													maxlength="12" readonly>
							  <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onMouseOver="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onMouseOut="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onClick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechapedido','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a></td>
							</tr>
							<tr class="fila-det">
							  <td height="27" class="fila-det-border"> Condicion: (*) </td>
							  <td colspan="2" class="fila-det-border">
                    <select name="idcondicion" id="idcondicion" class="campo" style="width:200px" onChange="setIndiceTarjeta(this);">
                      <option value="-1">Seleccionar</option>
                      <% 
									  iter = BPF.getListaCondicion().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                      <option value="<%= datos[0] %>" <%= datos[0].equals( BPF.getIdcondicion().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                      <%  
									  }%>
                    </select>                </td>
							  <td width="18%" class="fila-det-border">Prioridad(*)</td>
							    <td width="23%" class="fila-det-border">
							      <select name="idprioridad" id="idprioridad" class="campo" style="width:200px" >
                                    <option value="-1">Seleccionar</option>
                                    <% 
									  iter = BPF.getListPrioridades().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                                    <option value="<%= datos[0] %>" <%= datos[0].equals( BPF.getIdprioridad().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                                    <%  
									  }%>
                </select>							  </td>
							</tr>
							<tr class="fila-det">
							  <td width="18%" height="27" class="fila-det-border">Cant. Cuotas(*) </td>
							  <td colspan="2" class="fila-det-border">
								<select name="cuotas" id="cuotas" class="campo" style="width:200px" >
                  <option value="0">Seleccionar</option>
                                      <% 
									  iter = BPF.getListaCuotas().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                                      <option value="<%= datos[1] %>" <%= datos[1].equals(  BPF.getCuotas().toString()  )  ? "selected" : "" %> ><%= datos[1] %></option>
                                      <%  
									  }%>
                </select></td>
								<td width="18%" class="fila-det-border"> Domicilio: (*) </td>
								<td width="23%" class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="66%">
                     <select name="iddomicilio" id="iddomicilio" class="campo" style="width:200px" onChange="selectDomicilio(this);"  onFocus="valorActualLista = <%=BPF.getIddomicilio()%>"  >
                        <option value="-1">Seleccionar</option>
                        <% 
                    String useragent = request.getHeader("User-Agent");
									  iter = BPF.getListaDomicilios().iterator();
									  while(iter.hasNext()){
   										String [] datos = (String[])iter.next();
										  String detalleDom = "Calle:" +  Common.setNotNull(datos[5]).trim() ;
											detalleDom += "<br>Nro.:" +  Common.setNotNull(datos[6]).trim() ;
											detalleDom += "<br>Piso:" +  Common.setNotNull(datos[7]).trim() ;
											detalleDom += "<br>Depto.:" +  Common.setNotNull(datos[8]).trim() ;
											detalleDom += "<br>Contacto:" +  Common.setNotNull(datos[13]).trim() ;
											detalleDom += "<br>Cargo:" +  Common.setNotNull(datos[14]).trim() ;
                      detalleDom += "<br>T.Dom.:" +  Common.setNotNull(datos[3]).trim() ;
                      detalleDom += "<br>Localidad.:" +  Common.setNotNull(datos[10]).trim() ;
                      detalleDom += "<br>Provincia.:" +  Common.setNotNull(datos[38]).trim() ;
                      
%>
                        <option value="<%= datos[0] %>" <%= datos[0].equals(  BPF.getIddomicilio().toString()  )  ? "selected" : "" %> <%if(useragent.toLowerCase().indexOf("msie") > -1) {%> title="<%= detalleDom.replaceAll("<br>", "\n") %>" <% }else{ %> onMouseOver="detalleDomicilio('<%= detalleDom %>')" <% } %>><%= datos[5] %></option>
                        <%  
									  }%>
                      </select></td>
                      <td width="7%"><img src="../imagenes/default/gnome_tango/actions/gtk-add.png" width="22" height="22" style="cursor:pointer" onClick="validarCliente(<%= BPF.getIdcliente() %>)" title="Nuevo Domicilio"> </td>
                      <td width="27%"><img src="../imagenes/default/gnome_tango/apps/system-software-update.png" width="22" height="22"  style="cursor:pointer" onClick="callMaps()" title="Visualizar / Validar Dirección"></td>
                    </tr>
                </table></td>
							</tr>
							<tr class="fila-det">
							  <td width="18%" height="27" class="fila-det-border">Tarjetas:</td>
							  <td colspan="2" class="fila-det-border"><table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                      <td width="36%"><select name="idtarjeta" id="idtarjeta" class="campo" style="width:200px"  onChange="getDetalleTarjeta(this);">
                        <option value="-1">Seleccionar</option>
                        <% 
									  iter = BPF.getListaTarjetasCredito().iterator();
										java.sql.Date hoy = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

									  while(iter.hasNext()){
										  String [] datos = (String[])iter.next();
										  String warn = !str.esNulo(datos[12]).equalsIgnoreCase("S") || java.sql.Date.valueOf(datos[9]).before(hoy) ? "style=\"background-color:#FF9900\"" : ""  ;
											String data = "TIPO: "+datos[5] + "<br>NRO.: " + datos[6] +  "<br>MARCA: " +  datos[2];
										  data += "<br>EMITIDA: " + Common.setObjectToStrOrTime( java.sql.Date.valueOf(datos[8] ), "JSDateToStr") ;
											data += "<br>VENCE: " + Common.setObjectToStrOrTime(java.sql.Date.valueOf(datos[9]), "JSDateToStr") +  "<br>TITULAR: " +  datos[10];
											data += "<br>ACTIVA: " + datos[12] +  "<br>CONTROL: " +  datos[7] +  "<br>ORDEN: " +  datos[11];
											
                      //for(int l=0;l<datos.length;l++) System.out.println("datos[" + l + "]: " + datos[l]);

										%>
                        <option  value="<%= datos[0] %>" <%= datos[0].equals( BPF.getIdtarjeta().toString()) ? "selected" : "" %>  <%= warn %>  id="<%= data %>" ><%= datos[6] + " / " + datos[2] %></option>
                        <%  
									  }%>
                      </select></td>
                      <td width="64%"><img src="../imagenes/default/gnome_tango/actions/stock_undo.png" width="18" height="18" onClick="getDetalleTarjeta(document.frm.idtarjeta)" style="cursor:pointer"></td>
                    </tr>
                  </table></td>
							  <td width="18%" class="fila-det-border"> Vendedor: (*) </td>
								<td width="23%" class="fila-det-border">
								  <input name="vendedor" type="text" readonly value="<%= BPF.getVendedor() %>"  class="campo" style="width:150px;border-width:0px">
							  <input type="hidden" name="idvendedor" value="<%=  BPF.getIdvendedor() %>" >							  </td>
							</tr>
							<tr class="fila-det">
							  <td height="27" class="fila-det-border">Lista: (*) </td>
							  <td colspan="2" class="fila-det-border">
								<select name="idlista" id="idlista" class="campo" style="width:200px" onChange="selectLista(this);" onFocus="valorActualLista =  <%=BPF.getIdlista()%> " >
                    <option value="-1">Seleccionar</option>
                    <% 
									  iter = BPF.getListaListaPrecios().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                    <option value="<%= datos[0] %>" <%= datos[0].equals( BPF.getIdlista().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                    <%  
									  }%>
                </select></td>
							  <td width="18%" class="fila-det-border"> Expreso: (*) </td>
							  <td width="23%" class="fila-det-border">
											<input name="expreso" type="text" size="10" value="<%= BPF.getExpreso() %>" class="campo" style="width:150px;border-width:0px" readonly>
							  <input name="idexpreso" type="hidden" size="10" value="<%= BPF.getIdexpreso() %>">										  </td>
							</tr>
							<tr class="fila-det">
							  <td width="18%" height="27" class="fila-det-border"> Moneda: (*) </td>
							  <td colspan="2" class="fila-det-border"><select name="idmoneda" id="idmoneda" class="campo" style="width:200px" >
                    <option value="-1">Seleccionar</option>
                    <% 
									  iter = BPF.getListaMonedas().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                    <option value="<%= datos[0] %>" <%= datos[0].equals( BPF.getIdmoneda().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                    <%  
									  }%>
                  </select>                </td>
							  <td width="18%" class="fila-det-border">Telefonos:</td>
							  <td width="23%" class="fila-det-border"><input name="telefonos" type="text" class="campo" id="telefonos" style="border-width:0px" value="<%= BPF.getTelefonos() %>" size="40" readonly ></td>
							</tr>

							<tr class="fila-det">
							  <td height="27" class="fila-det-border">Tipo de Iva:  (*) </td>
							  <td colspan="2" class="fila-det-border"><select name="idtipoiva" id="idtipoiva" class="campo" style="width:200px" >
                    <option value="-1">Seleccionar</option>
                    <% 
									  iter = BPF.getListaTipoIva().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                    <option value="<%= datos[0] %>" <%= datos[0].equals( BPF.getIdtipoiva().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                    <%  
									  }%>
                  </select>                </td>
							  <td class="fila-det-border">Cotizaci&oacute;n:</td>
							  <td class="fila-det-border"><input name="cotizacion" type="text"
										value="<%=BPF.getCotizacion()%>" class="campo" size="10"
										maxlength="10" align="right"></td>
							</tr>
							<tr class="fila-det">
							  <td height="27" class="fila-det-border">Categor&iacute;a:
						    <input type="hidden" name="categoriasocio"  id="categoriasocio" value="<%=  BPF.getCategoriasocio() %>"></td>
							  <td colspan="2" class="fila-det-border"><%=  BPF.getCategoriasocio() %>&nbsp;</td>
							  <td class="fila-det-border">Preferencia:
						    <input type="hidden" name="preferencia"  id="preferencia" value="<%=  BPF.getPreferencia() %>"></td>
							  <td class="fila-det-border"><%= Common.setNotNull(BPF.getPreferencia()) %>&nbsp;</td>
						  </tr>
							<tr class="fila-det">
							  <td height="27" class="fila-det-border">Fecha Factura(*)</td>
							  <td colspan="2" class="fila-det-border"><input name="fechafactura" type="text" class="campo"
													id="fechafactura" 
													value="<%=BPF.getFechafactura()%>" size="12"
													maxlength="12" readonly>
                                <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onMouseOver="calSwapImg('BTN_date_0', 'img_Date_OVER',true); "
													onMouseOut="calSwapImg('BTN_date_0', 'img_Date_UP',true);"
													onClick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fechafactura','BTN_date_0');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_0" width="22" height="17" border="0"
														align="absmiddle"></a> <a href="#" onClick="document.frm.fechafactura.value = document.frm.fechapedido.value ">  Igual a Fecha Pedido</a></td>
							  <td class="fila-det-border">&nbsp;</td>
							  <td class="fila-det-border">&nbsp;</td>
						  </tr>
							<tr class="fila-det">
							  <td height="27" class="fila-det-border">&nbsp;</td>
							  <td colspan="2" class="fila-det-border">&nbsp;</td>
							  <td class="fila-det-border">&nbsp;</td>
							  <td class="fila-det-border">&nbsp;</td>
						  </tr>
							<tr class="fila-det">
							  <td class="fila-det-border">Obs. Armado  </td>
							  <td colspan="4" class="fila-det-border"><textarea name="obsarmado" cols="80" rows="2" class="campo" id="obsarmado"><%=Common.setNotNull(BPF.getObsarmado())%></textarea></td>
						  </tr>
							<tr class="fila-det">
							  <td class="fila-det-border">Obs. Entrega </td>
							  <td colspan="4" class="fila-det-border"><textarea name="obsentrega" cols="80" rows="2" class="campo" id="obsentrega"><%=Common.setNotNull(BPF.getObsentrega())%></textarea></td>
						  </tr>
							<tr class="fila-det">
							  <td class="fila-det-border">&nbsp;</td>
							  <td colspan="2" class="fila-det-border">&nbsp;</td>
							  <td class="fila-det-border">&nbsp;</td>
							  <td class="fila-det-border">&nbsp;</td>
							</tr>							
					  </table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
                       <%if( BPF.getIdpromocion().longValue() > 0 ){%>
						  <tr class="text-globales">
							  <td colspan="4" class="text-nueve">Promocion: <%= BPF.getPromocion() %> ||  Periodo validez: <%= Common.setObjectToStrOrTime(BPF.getFDesdePromo(), "JSDateToStr") %> - <%= !Common.setNotNull(BPF.getFHastaPromo() + "" ).equals("") ?  Common.setObjectToStrOrTime(BPF.getFHastaPromo(), "JSDateToStr") : "ABIERTA" %> || Porcentaje CI: <%= BPF.getPorc_desc_ci() %> </td>
						  </tr>
                       <%}%>
							<tr class="text-globales">
								<td width="22%">
									&nbsp;STOCK								</td>
								<td width="78%" colspan="3">
									<!-- 1.1 -->
									<table width="100%" border="0" cellspacing="3" cellpadding="0"
										height="100%">
										<tr class="text-globales">
											<td width="21%" height="24">
												Cargar Art&iacute;culos											</td>
											<td width="54%">
											<input type="button" name="actualiza" value=">>"
													class="boton"
													onClick="ventana=abrirVentana('lovArticulosListaPreciosPedidos.jsp?idlista=<%= BPF.getIdlista() %>&idzona='+document.frm.idzona.value, 'art', 800, 400)">											</td>
										    <td width="6%"><div align="right"></div></td>
										    <td width="4%"><div align="right"></div></td>
										    <td width="6%"><div align="right"><img src="../imagenes/default/gnome_tango/actions/gtk-jump-to-rtl.png" title="Cargar Articulos Campa&ntilde;a" width="22" height="22" style="cursor:pointer" onClick="ventana=abrirVentana('lovArticulosListaPreciosPedidos.jsp?idlista=<%= BPF.getIdlista() %>&idzona='+document.frm.idzona.value+'&idcampacabe=<%= BPF.getIdcampacabe() %>&soloCampania=S', 'art', 800, 400)"></div></td>
										    <td width="9%"><div align="right"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="22" height="22" style="cursor:pointer" title="Limpiar detalle de pedido." onClick="; if(confirm('Confirma limpiar detalle ?')){ document.frm.accion.value = 'limpiardetalle'; document.frm.submit() ;}"></div></td>
										</tr>
									</table>
									<!--1.1 -->								</td>
							</tr>
							<tr class="fila-det">
								<td colspan="4" valign="top" class="fila-det-border">
									<!-- 2.1 -->
									<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#99cc66">
										<tr class="fila-det-bold">
											<td width="28%" class="fila-det-border">
										  Articulo												</td>
											<td width="19%" class="fila-det-border">Dep&oacute;sito</td>
											<td width="5%" class="fila-det-border">
												<div align="right">
										  Cant.												</div>											</td>
											<td width="8%" class="fila-det-border"><div align="right">% Sug. </div></td>
											<td width="10%" class="fila-det-border"><div align="center">% Aplic </div></td>
											<td width="13%" class="fila-det-border"><div align="center">Mot. Desc. </div></td>
											<td width="6%" class="fila-det-border">
												<div align="right">
										  P.U												</div>											</td>
											<td width="7%" class="fila-det-border"><div align="right"> P.U.Desc </div></td>
											<td width="4%" class="fila-det-border">
											<div align="right">Total												</div></td>
										</tr>
										<%										
										Hashtable htArticulosInOutOK = (Hashtable)session.getAttribute("htArticulosInOutOK");
										int indice = 0;
												if(htArticulosInOutOK != null && !htArticulosInOutOK.isEmpty()){													
													 Enumeration en = htArticulosInOutOK.keys();
													 
													   %>
														 <script>
														 existeDetalle = true;
														 </script>
														 <%
													 
													 while(en.hasMoreElements()){
													     String key = (String)en.nextElement();
														 String art[] = (String[])htArticulosInOutOK.get(key);
														 //System.out.println(" ************************************************************** ");
														 //for(int P =0;P<art.length;P++) System.out.println("art[" + P + "]" + art[P]); 
														 long idfamilia = Long.parseLong(art[31]);
														 /* 20100204 - EJV 
															Modificacion pedida en mantis 379. Bugs en calculo provisorio de descuento para familia 59.
															Agregar flagFirstLoad
														 */
														 // -->
														 boolean flagFirstLoad = Boolean.parseBoolean(art[33]);
														 art[33] = "false"; 
														 if(idfamilia == 59 && flagFirstLoad){
														   art[20] =  BPF.getAdidesc().toString();
														 }else if( BPF.getIdpromocion().longValue()>0 && flagFirstLoad && art[0].equalsIgnoreCase("C.I.")){
														   art[20] =  BPF.getPorc_desc_ci().toString();
														 }
							
														 // <--
														 
														 System.out.println("flagFirstLoad:  " + flagFirstLoad);
														 System.out.println("art[0]:  " + art[0]);	
														 System.out.println("BPF.getIdpromocion():  " + BPF.getIdpromocion());	
														 System.out.println("EVAL:  " + ( BPF.getIdpromocion().longValue()>0 && flagFirstLoad && art[0].trim().equalsIgnoreCase("C.I")));
														 System.out.println("EVAL-CI:  " + (art[0].trim().equalsIgnoreCase("C.I.")));
														 													 
														 %>
									    
										<tr class="fila-det">
											<td height="25" class="fila-det-border-rigth"><%= art[0] %>-<%= art[2].length() > 20? art[2].substring(0, 20) + "..."  : art[2] %>
											<input  name="keyHashArticulo" type="hidden" value="<%= key %>"></td>
											<td class="fila-det-border-rigth"><%=  BPF.getDeposito(art[9]) .length() > 20 ?  BPF.getDeposito(art[9]) .substring(0, 20) + "..."  : BPF.getDeposito(art[9]) %></td>
											<td class="fila-det-border-rigth">
												
												<div align="right">
													<%= art[10] %>												</div>                      </td>
											<td class="fila-det-border-rigth"><div align="right"><%= art[17] %>&nbsp;</div></td>
											<td class="fila-det-border-rigth">
										    
									      <div align="center">
									        <%--= <input name="porcdesc_apli" type="hidden" id="porcdesc_apli" value="<%= art[20] %>">
                                --------------------------------------------------- 
                                20100204 - EJV
                                porcdesc_apli fue restituido a su posicion original.    
                          --%>
                          <input name="porcdesc_apli" type="hidden" id="porcdesc_apli" value="<%= art[20] %>">
									        <select name="iddescuento_apli" id="iddescuento_apli" class="campo" onChange="setPorcentajeAplicado( this, <%= indice %> );">
									          <option value="0" >0.00</option>
									          <% 

									  iter = BPF.getListaPorcDescuento().iterator();
									  while(iter.hasNext()){
										  String selected = "";
															  String [] datos = (String[])iter.next();
										  // --------> INICIA
										  // TODO: EJV - 20090925.
											// HARCODE idfamilia = 59 - Para familias premium, 
										  // setear el porcentaje de descuento, 
										  // debe ir en la lógica de descuentos cuando esta se desarrolle.
										  /* 20100204 - EJV 
											 Modificacion pedida en mantis 379. Bugs en calculo provisorio de descuento para familia 59.
											 Agregar flagFirstLoad
										  */
										  // -->
										  if(idfamilia == 59 && flagFirstLoad){
											//System.out.println("-------------------------------------------------" );
											//System.out.println("idfamilia       :" + idfamilia );
											//System.out.println("datos[1]        :" + Double.parseDouble(datos[1]) );                        
											//System.out.println("BPF.getAdidesc():" + BPF.getAdidesc().doubleValue() );  
											if(Double.parseDouble(datos[1]) == BPF.getAdidesc().doubleValue()){
											  selected = "selected";
											  //art[20] =  BPF.getAdidesc().toString();
											}
										  }
										  else if( BPF.getIdpromocion().longValue()>0 && flagFirstLoad && art[0].trim().equalsIgnoreCase("C.I.")){
                                            if(Double.parseDouble(datos[1]) == BPF.getPorc_desc_ci().doubleValue()){
											  selected = "selected";
											}										  
										  }
										  else{
											selected = datos[0].equals(  art[18] ) ? "selected" : "" ;
										  }
										  
										  
										  // <-------- FIN
                    %>
									          <option value="<%= datos[0] %>" <%= selected %>><%= datos[1] %></option>
									          <%  
									  }%>
								          </select>
                          <% /* 20090925 - EJV 
                                porcdesc_apli fue cambiado de posicion par setear el porcentaje que corresponde según la categoría para la familia 59
                                ---------------------------------------------------
                                20100204 - EJV
                                porcdesc_apli fue restituido a su posicion original.
                             */
                          %>

                          <%-- <input name="porcdesc_apli" type="hidden" id="porcdesc_apli" value="<%= art[20] %>"> --%>
						            </div></td>
											<td class="fila-det-border-rigth"><div align="center">
											  <select name="idmotivodescuento" id="idmotivodescuento" class="campo" style="width:80px" >
											    <option value="-1">Seleccionar</option>
											    <% 
									  iter = BPF.getListaMotivosDescuento().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
											    <option value="<%= datos[0] %>" <%= datos[0].equals(  art[21] ) ? "selected" : "" %> title="<%= datos[1] %>"><%= datos[1] %></option>
											    <%  
									  }%>
										    </select>
										  </div></td>
											<td class="fila-det-border-rigth">
												
												<div align="right">
													<%= new BigDecimal(art[5]) %>												</div>											</td>
											<td class="fila-det-border-rigth"><div align="right"> <%= new BigDecimal(art[6]) %> </div></td>
											<td class="fila-det-border-rigth">
												
												<div align="right">
													<%= new BigDecimal(art[11]) %>												</div>											</td>
										</tr>
										<% 
															indice++;										       
													 }
                        
												}
												
												%>
										<tr class="fila-det-bold">
											<td colspan="8" class="fila-det-border">											  <div align="right">Total&nbsp;</div></td>
										    <td class="fila-det-border"><div align="right"><%= Common.getNumeroFormateado(BPF.getTotalgeneral().doubleValue(), 10, 2 ) %></div></td>
										</tr>
									</table>
									<!-- 2.1 -->								</td>
							</tr>
							<tr class="text-globales" height="3">
								<td width="22%" class="fila-det-border"></td>
								<td colspan="4" class="fila-det-border"></td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold">
								<td width="14%" class="fila-det-border">&nbsp;</td>
								<td width="49%" class="fila-det-border">&nbsp;</td>
								<td width="15%" class="fila-det-border">&nbsp;</td>
								<td width="22%" class="fila-det-border">&nbsp;</td>
							</tr>
							<tr class="fila-det-bold">
								<td class="fila-det-border">&nbsp;</td>
								<td class="fila-det-border">&nbsp;</td>
								<td class="fila-det-border">&nbsp;</td>
								<td class="fila-det-border">&nbsp;</td>
							</tr>
						<tr >
						  <td  height="3" colspan="4" class="permiso-cinco"></td>
						</tr>

						<tr class="fila-det-bold">
						  <td class="fila-det-border">&nbsp;</td>
						  <td class="fila-det-border">&nbsp;</td>
						  <td class="fila-det-border"> <div align="right">Total General: </div></td>
						  <td class="fila-det-border" ><div align="right"><%= Common.getNumeroFormateado(BPF.getTotalgeneral().doubleValue(), 10, 2) %> </div></td>
						</tr>
						<tr class="fila-det-bold">
						  <td class="fila-det-border">&nbsp;</td> 
						  <td class="fila-det-border">&nbsp;</td>
						  <td class="fila-det-border"><div align="right">Total IVA: </div></td>
						  <td class="fila-det-border" ><div align="right"><%= Common.getNumeroFormateado( (BPF.getTotalGeneralIva().subtract( BPF.getTotalgeneral() )).doubleValue(), 10, 2) %></div></td>
						  </tr>
						<tr class="fila-det-bold">
							<td class="fila-det-border">&nbsp;</td>
							<td class="fila-det-border">&nbsp;</td>
							<td class="fila-det-border"><div align="right">Total General IVA:</div></td>
							<td class="fila-det-border" ><div align="right"><%= Common.getNumeroFormateado( BPF.getTotalGeneralIva().doubleValue(), 10, 2) %></div></td>
						</tr>

						<tr class="fila-det-bold">
						  <td class="fila-det-border">&nbsp;</td>
						  <td class="fila-det-border">&nbsp;</td>
						  <td class="fila-det-border"><div align="right">Flete Estimado: </div></td>
						  <td class="fila-det-border" ><div align="right"><%= BPF.getTotalFleteEstimado() %></div></td>
						</tr>						
						
						
						<tr class="fila-det-bold">
						  <td class="fila-det-border">&nbsp;</td>
						  <td colspan="2" class="fila-det-border">&nbsp;</td>
						  <td class="fila-det-border" >&nbsp;</td>
						</tr>						
						
						
						<tr >
						  <td  height="3" colspan="4" class="permiso-cinco"></td>
						</tr>
					  </table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det">
								<td width="10%" height="42"><label> 
								  
								</label></td>
								<td width="33%">&nbsp;</td>
								<td width="20%">&nbsp;</td>
							  <td width="18%"><input name="calcular" type="submit" class="boton" id="calcular" value="Calcular Importes"></td>
									<td width="6%">&nbsp;</td>
									<td width="13%">
									<span class="fila-det-border"> <input name="confirmar" id="confirmar"  type="submit" value="Confirmar Pedido" class="boton" onClick="this.disabled = true;  ">
									</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
		  </table>

		</form>
	</body>
</html>
<% 

    //System.out.println("ACCION: " + BPF.getAccion() );

 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>