<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0); 
/* 
   Formulario de carga para la entidad: clientesclientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Dec 11 15:30:21 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/  
 
%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.*" %>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
String diasAuditoria = session.getAttribute("clientesDomiciliosDiasDeAuditoria").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientesClientesResumidoFrm"   scope="page"/>
<head>
 <title></title>
 <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" href="../imagenes/default/erp-style.css">
 <!--BORRAR-->
 <link rel="stylesheet" href="vs/calendar/calendar.css"> 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script >
 // --     
	function poblarLista(objTxt){
	  var tokens = objTxt.value.split('@');
		if(tokens.length == 2 && trim(tokens[0]).length > 0  && trim(tokens[1]).length > 0)
		  document.frm.email.options[document.frm.email.options.length] = new Option(objTxt.value, objTxt.value);
		else  
			alert("Ingrese un mail valido..");		
	}
 // --
	function desPoblarLista(objList){
		var i = objList.options.length
		for(;i>0;i--){
			if(document.frm.email.options[i-1].selected){
				document.frm.email.options[i-1] = null;
			}	
		}		
	}
 // --
	function limpiarDomicilios(){
		document.frm.iddomicilio.value =  document.frm.idtipodomicilio.value =  document.frm.tipodomicilio.value = document.frm.calle.value =   document.frm.nro.value =
		document.frm.piso.value =  document.frm.depto.value = document.frm.idlocalidad.value = document.frm.localidad.value = document.frm.cpa.value = document.frm.postal.value = 
		document.frm.contacto.value = document.frm.cargocontacto.value = document.frm.telefonos.value = document.frm.celular.value = document.frm.fax.value = document.frm.web.value = 
		document.frm.idzona.value = document.frm.zona.value = document.frm.idexpreso.value = document.frm.expreso.value = document.frm.idcobrador.value = document.frm.cobrador.value = "";
		document.frm.esdefault.checked = false; 
		for(var i=document.frm.email.options.length;i>0;i--){
				document.frm.email.options[i-1] = null;
		}		
	}
	
	 // --
	function limpiarTarjetas(){
				 document.frm.idtarjeta.value =  document.frm.tarjetacredito.value = document.frm.idtipotarjeta.value =  document.frm.tipotarjeta.value =
				  document.frm.idtarjetacredito.value = document.frm.nrotarjeta.value =  document.frm.nrocontrol.value =  document.frm.fecha_emision.value =  
				 document.frm.fecha_vencimiento.value =  document.frm.titular.value = document.frm.orden.value = "";
				 document.frm.activa.selectedIndex = 0; 
	}
 // --  
	function seleccionMail(){
		for(var f=0;f<document.frm.email.options.length;f++){
			document.frm.email.options[f].selected = true;
		}
	}  
 //--	
	function marcarBaja(iditem, tipoitem){ 
  	document.frm.idSubAccion.value = iditem;
  	document.frm.subAccion.value = 'baja' + tipoitem;
		setAction(tipoitem);
		document.frm.submit();
	}
 //--	
	function undoMarcarBaja(iditem, tipoitem){
  	document.frm.idSubAccion.value = iditem;		
  	document.frm.subAccion.value = 'undoBaja' + tipoitem;
		setAction(tipoitem);
		document.frm.submit();
	}	
 //--	
	function modificarItem(iditem, tipoitem){
  	document.frm.idSubAccion.value = iditem;
  	document.frm.subAccion.value = 'modifica' + tipoitem;		
		setAction(tipoitem);
		document.frm.submit();
	}	
	//--
	function setAction(identidad){
	  document.frm.action = 'clientesClientesResumidoFrm.jsp#' + identidad.toLowerCase();
	}
	//--
	function confirmarItem(tipoitem){
	   seleccionMail(); 
		 document.frm.subAccion.value = 'agregar' + tipoitem; 
		 setAction(tipoitem);
		 document.frm.submit();
	}	

  function replicaRazon(){
    document.frm.contacto.value = trim( document.frm.contacto.value ) == '' ? document.frm.razon.value : document.frm.contacto.value;
  }	

  function sugiereRazon(obj){
    var razon =  document.frm.razon.value
    obj.value  = trim( obj.value  ) == '' ? razon : '' ;
  }	

 </script>
 
 
 </head>

</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 Iterator iter;
 BCF.setResponse(response);
 BCF.setRequest(request); 
 BCF.setSession(session);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() ); 
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));     
 BCF.ejecutarValidacion();
 String titulo = BCF.getAccion().toUpperCase() + " DE CLIENTES" ;
 %>
<form action="clientesClientesResumidoFrm.jsp" method="post" name="frm"  >
  <input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
  <input name="idSubAccion" type="hidden" value="" >	
  <input name="subAccion" type="hidden" value="" >
  <!--input name="bajaDomicilio" type="hidden" id="bajaDomicilio" >
  <input name="undoBajaDomicilio" type="hidden" id="undoBajaDomicilio" >
  <input name="modificaDomicilio" type="hidden" id="modificaDomicilio" -->

  <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center"> 
     <tr>
       <td>
         <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<img src="../imagenes/default/gnome_tango/apps/config-users.png" width="22" height="22"> <%= titulo %></td>
            </tr>
         </table> 
            <table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
			  
			        <tr class="fila-det">
                <td width="20%" class="fila-det-border">Codigo:  (*)</td>
                <td width="31%" class="fila-det-border">
								   <table width="6%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="idcliente" type="text" class="campo" id="idcliente" value="<%=BCF.getIdcliente()%>" size="18" maxlength="18"  <%= !BCF.getAccion().equalsIgnoreCase("alta") ? "readonly" : "" %> ></td>
                    </tr>
                  </table>								</td>
                <td class="fila-det-border">Razon Social: (*) </td>
                <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0"> 
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="razon" type="text" value="<%=BCF.getRazon()%>" class="campo" size="35" maxlength="100"  onBlur="replicaRazon();" ></td>
                    </tr>
                </table
								></td>
			  </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Nro Documento: (*) </td>
                <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="nrodocumento" type="text" class="campo" id="nrodocumento" value="<%=BCF.getNrodocumento()%>" size="18" maxlength="18" <%= !BCF.getAccion().equalsIgnoreCase("alta") ? "readonly" : "" %>></td>
                  </tr>
                </table></td>
                <td width="22%" class="fila-det-border">Tipo Documento:(*)  
                  <%-- <input name="idtipodocumento" type="hidden" id="idtipodocumento" value="< %=BCF.getIdtipodocumento() %  >"> --%></td>
                <td width="27%" class="fila-det-border">
								<% if (BCF.getAccion().equalsIgnoreCase("alta")){%>
                  <select name="idtipodocumento" id="idtipodocumento" class="campo" style="width:200px" >
                    <option value="-1">Seleccionar</option>
                    <%
                   iter = BCF.getListTiposDoc().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                    <option value="<%= datos[0] %>" <%=  BCF.getIdtipodocumento().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                    <% 
                   } %>
                  </select>
                 <% }
                    else{  
                       out.write( BCF.getTipodocumento() +  " <input name=\"idtipodocumento\" type=\"hidden\" id=\"idtipodocumento\" value=\"" + BCF.getIdtipodocumento()+ "\">") ;
                   } %>
								<%--
                 <table width="75%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="tipodocumento" type="text" class="campo" id="tipodocumento" value="< %=BCF.getTipodocumento()% >" size="30" readonly></td>
                    <td width="39%">< % if (BCF.getAccion().equalsIgnoreCase("alta")){% >
                        <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lovGlobalTiposDocumentos.jsp', 'tdoc', 800, 450)" style="cursor:pointer">
                      < %}% ></td>
                  </tr>
                </table> 
								--%>              </td>
              </tr>

              <tr class="fila-det">
                <td class="fila-det-border">Tipo de cliente: 
                <input name="idtipoclie" type="hidden" id="idtipoclie" value="<%=BCF.getIdtipoclie()%>"></td>
                <td class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="tipoclie" type="text" class="campo" id="tipoclie" value="<%=str.esNulo(BCF.getTipoclie())%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_tipocliente.jsp', 'tclie', 800, 450)" style="cursor:pointer"></td>
                    </tr>
                </table></td>
                <td class="fila-det-border">Autorizado: (*) </td>
                <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
											<select name="autorizado" id="autorizado"  class="campo">
                          <option value="S" <%= BCF.getAutorizado().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                          <option value="N" <%= BCF.getAutorizado().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                      </select>											</td>
                    </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Observaciones:</td>
                <td colspan="3" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><textarea name="observacion" cols="80" rows="2" class="campo"><%=str.esNulo(BCF.getObservacion())%></textarea></td>
                  </tr>
                </table> </td>
              </tr>
			  
              <tr class="text-dos-bold">
                <td colspan="4"  ><img src="../imagenes/default/gnome_tango/apps/accessories-calculator.png" width="22" height="22"> GESTION COMERCIAL  </td>
              </tr>
              <tr class="fila-det"> 
                <td class="fila-det-border">Nro de Ingresos Brutos: </td>
                <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="brutos" type="text" value="<%=BCF.getBrutos()%>" class="campo" size="18" maxlength="18"  ></td>
                    </tr>
                </table></td>
                <td width="22%" class="fila-det-border">&nbsp;</td>
                <td width="27%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Tipo de I.V.A.: (*)                </td>
                <td class="fila-det-border">
                  <select name="idtipoiva" id="idtipoiva" class="campo" style="width:200px" >
                    <option value="-1">Seleccionar</option>
                    <%
                   iter = BCF.getListIva().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                    <option value="<%= datos[0] %>" <%=  BCF.getIdtipoiva().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                    <% 
                   } %>
                  </select>
                   <%-- <table width="23%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="tipoiva" type="text" class="campo" id="tipoiva" value="< %=BCF.getTipoiva()% >" size="30" readonly></td>
                      <td width="39%"><input name="idtipoiva" type="hidden" id="idtipoiva" value="< %=BCF.getIdtipoiva()% >">
                     <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_Tipoiva.jsp', 'tiva', 800, 450)" style="cursor:pointer"></td>
                    </tr>
                  </table> --%>                </td>
                <td class="fila-det-border">Condicion de Pago: (*)                </td>
                <td class="fila-det-border"><select name="idcondicion" id="idcondicion" class="campo" style="width:200px" >
                    <option value="-1">Seleccionar</option>
                    <%
                   iter = BCF.getListCondicion().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                    <option value="<%= datos[0] %>" <%=  BCF.getIdcondicion().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                    <% 
                   } %>
                  </select>
                  <%-- <table width="23%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="idcondicion" type="hidden" id="idcondicion" value="< %=BCF.getIdcondicion()% >"><input name="condicion" type="text" class="campo" id="condicion" value="< %=BCF.getCondicion()% >" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_condventa.jsp', 'venta', 800, 450)" style="cursor:pointer"></td>
                    </tr>
                </table> --%>               </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Cuenta Contable Neto:</td>
                <td class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="idctaneto" type="text" class="campo" id="idctaneto" value="<%=BCF.getIdctaneto()%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_cuentascontables.jsp', 'cuentas', 800, 450)" style="cursor:pointer"></td>
                    </tr>
                </table></td>
                <td class="fila-det-border">Moneda de Facturacion: (*)                </td>
                <td class="fila-det-border"><select name="idmoneda" id="idmoneda" class="campo" style="width:200px" >
                   <%
                   iter = BCF.getMonedaList().iterator();   
                   while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                     <option value="<%= datos[0] %>" <%= ( BCF.getIdmoneda().longValue() < 0 && datos[1].trim().equalsIgnoreCase("PESOS") )|| BCF.getIdmoneda().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                   <% 
                   } %>
                 </select>
                  <%-- <table width="23%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="idmoneda" type="hidden" id="idmoneda" value="< %=BCF.getIdmoneda()% >"><input name="moneda" type="text" class="campo" id="moneda" value="< %=BCF.getMoneda()% >" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_moneda.jsp', 'moneda', 800, 450)" style="cursor:pointer"></td>
                    </tr>
                  </table> --%>                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Categoria de Credito: (*)                  </td>
                <td class="fila-det-border"><select name="idcredcate" id="idcredcate" class="campo" style="width:200px" >
                    <option value="-1">Seleccionar</option>
                    <%
                   iter = BCF.getListCategoriaCred().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                    <option value="<%= datos[0] %>" <%=  BCF.getIdcredcate().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                    <% 
                   } %>
                  </select>
                  <%-- <table width="23%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="idcredcate" type="hidden" id="idcredcate" value="< %=BCF.getIdcredcate()%> "><input name="credcate" type="text" class="campo" id="credcate" value="< %=BCF.getCredcate()% >" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_catcliente.jsp', 'cate', 800, 450)" style="cursor:pointer"></td>
                    </tr>
                  </table> --%>                </td>
                <td class="fila-det-border">Limite de credito: </td>
                <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="lcredito" type="text" value="<%=BCF.getLcredito()%>" class="campo" size="18" maxlength="18"  ></td>
                    </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Lista de Precios:                </td>
                <td class="fila-det-border"><select name="idlista" id="idlista" class="campo" style="width:200px" >
                  <%
                   iter = BCF.getListasPreciosList().iterator();   
                   int x = 0; 
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                  <option value="<%= datos[0] %>" <%= ( BCF.getIdlista().longValue() < 0 && x==0  )|| BCF.getIdlista().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                  <% 
                     x++;
                   } %>
                </select>
                  <%-- <table width="23%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="idlista" type="hidden" id="idlista" value="< %=BCF.getIdlista()% >" /><input name="lista" type="text" class="campo" id="lista" value="< %=str.esNulo(BCF.getLista())% >" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_lista.jsp', 'lista', 800, 450)" style="cursor:pointer"></td>
                    </tr>
                  </table> --%>                </td>
                <td class="fila-det-border">Tipo de Comprobante :                </td>
                <td class="fila-det-border"><select name="idtipocomp" id="idtipocomp" class="campo" style="width:200px" >
                  <%
                   iter = BCF.getTiposComprobList().iterator();   
                   while(iter.hasNext()){
                     String[] datos = (String[]) iter.next(); 
                     %>
                  <option value="<%= datos[0] %>" <%= ( BCF.getIdtipocomp().longValue() < 0 && datos[2].trim().equalsIgnoreCase("FA") )|| BCF.getIdtipocomp().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[2] %></option>
                  <% 
                   } %>
                </select>
                  <%-- <table width="23%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="idtipocomp" type="hidden" id="idtipocomp" value="< %=BCF.getIdtipocomp()% >"><input name="tipocomp" type="text" class="campo" id="tipocomp" value="< %=str.esNulo(BCF.getTipocomp())% >" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_tipocomp.jsp', 'tcomp', 800, 450)" style="cursor:pointer"></td>
                    </tr>
                  </table> --%>                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Descuento 1: </td>
                <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="descuento1" type="text" value="<%=BCF.getDescuento1()%>" class="campo" size="18" maxlength="18"  ></td>
                    </tr>
                </table> </td>
                <td class="fila-det-border">Descuento 3: </td>
                <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="descuento3" type="text" value="<%=BCF.getDescuento3()%>" class="campo" size="18" maxlength="18"  ></td>
                    </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Descuento 2: </td>
                <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="descuento2" type="text" value="<%=BCF.getDescuento2()%>" class="campo" size="18" maxlength="18"  ></td>
                    </tr>
                </table></td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td colspan="4" class="fila-det-border">
								 <table width="100%" border="1px" cellspacing="0" cellpadding="0" align="center" id="domicilio">
                  <tr class="text-globales">
                    <td colspan="4" class="text-diez" ><img src="../imagenes/default/gnome_tango/actions/go-home.png" width="22" height="22"> DOMICILIOS DEL CLIENTE </td>
                  </tr>
                  <tr class="text-globales">
                    <td colspan="4" class="fila-det-bold-rojo" ><%=BCF.getSubAccion().equalsIgnoreCase("agregarDomicilio") ?  BCF.getMensaje() : ""   %></td>
                  </tr>
                  <tr class="text-globales">
                    <td colspan="4" class="fila-det" >
										<% 
										Enumeration enu;
										Hashtable htDomicilios = (Hashtable)session.getAttribute("htDomiciliosCliente");
										enu = htDomicilios.keys();
										if(!htDomicilios.isEmpty()){
										%>
					<table width="100%" border="1" cellspacing="0" cellpadding="0">
	                    <tr class="fila-det-bold">
                        <td>Calle</td>
                        <td>Nro</td>
                        <td>Localidad</td>
                        <td>Contacto</td>
                        <td>Tipo Domicilio </td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
						<td>&nbsp;</td>
						<td width="2%">&nbsp;</td>
	                    </tr>
										<%
											while(enu.hasMoreElements()){
												Object [] domicilio = (Object[])htDomicilios.get(enu.nextElement());
											 
										 %>
                        <tr class="fila-det">
                        <td width="10%">&nbsp;<%= domicilio[4] %></td>
                        <td width="4%">&nbsp;<%= domicilio[5] %></td>
                        <td width="17%">&nbsp;<%= domicilio[9] %></td>
                        <td width="22%">&nbsp;<%= domicilio[12] %></td> 
						<td width="39%">&nbsp;<%= domicilio[2] %></td> 
                        <td width="2%">
												<% if(!domicilio[27].toString().equalsIgnoreCase("B")) {%>
										    <img src="../imagenes/default/gnome_tango/actions/stock_paste.png" title="Modificar este domicilio ..." width="18" height="18" style="cursor:pointer" onClick="modificarItem(<%= domicilio[0] %>, 'Domicilio')">
												<% }
												   else{ %>
												   <img src="../imagenes/default/gnome_tango/status/image-missing.png" title="Imposible modificar este domicilio, el mismo esta marcado para eliminar ..." width="18" height="18" style="cursor:pointer">
						  <% } %>												</td>
                        <td width="2%">
												<% if(!domicilio[27].toString().equalsIgnoreCase("B")) {%>
												  <img src="../imagenes/default/gnome_tango/status/edittrash.png" title="Marcar para eliminar este domicilio ..." width="18" height="18" style="cursor:pointer" onClick="marcarBaja(<%= domicilio[0] %>, 'Domicilio')">
												<% }
												   else{ %>
												  <img src="../imagenes/default/gnome_tango/actions/gtk-undo-ltr.png" title="Desahacer marcar para eliminar ..." width="18" height="18" style="cursor:pointer" onClick="undoMarcarBaja(<%= domicilio[0] %>, 'Domicilio')">
						  <% } %>												</td>
                        <td width="2%">                        
						<%if( new BigDecimal(domicilio[28].toString()).compareTo(new BigDecimal(diasAuditoria))>= 0 ){ %>
					 	    <img src="../imagenes/default/gnome_tango/status/important.png" title="Atencion el domicilio necesita ser auditado nuevamente! (Total de dias de la ultima auditoria: <%=domicilio[28].toString()%>)"  width="18" height="18" style="cursor:pointer">
						  <% }else {%>
						     <img src="../imagenes/default/gnome_tango/status/dialog-information.png" title="(Total de dias de la ultima auditoria: <%=domicilio[28].toString()%>)"  width="18" height="18" style="cursor:pointer">
					    <% }%>						</td>
						<td>						
                        <img src="../imagenes/default/gnome_tango/actions/system-search.png" title="Consulta de Mapa" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('mapaprueba.jsp?iddomicilio=<%=domicilio[0]%>&idempresa=<%=session.getAttribute("empresa").toString()%>', 'gmap', 560, 420)">						</td>
                      </tr>
										<% 
											}										
										 %>											
                    </table>		
										<% 
										}										
										 %>									  </td>
                  </tr>
                  <tr class="text-globales">
                    <td colspan="3" class="text-diez" ><img src="../imagenes/default/gnome_tango/places/start-here.png" width="22" height="22"> Ubicacion &nbsp;
                    <input name="iddomicilio" type="hidden" id="iddomicilio" value="<%= BCF.getIddomicilio()%>">                    </td>
                    <td class="text-diez" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr class="text-diez">
                        <td><!--input name="agregarDomicilio" type="submit" class="boton" id="agregarDomicilio" value="Confirmar" onClick="seleccionMail(); document.frm.subAccion.value = 'agregarDomicilio'"-->
                          <div align="center"><img src="../imagenes/default/nuevo.gif" width="16" height="16" border="0"  onClick="confirmarItem('Domicilio');"  style="cursor:pointer"> Confirmar</div></td>
                        <td><!--input name="Limpiar" type="button" class="boton" id="Limpiar" value="Limpiar" onClick="limpiarDomicilios()" -->
                          <div align="center"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="18" height="18" border="0"  onClick="limpiarDomicilios()"  style="cursor:pointer"> Limpiar</div></td>
                      </tr>
                    </table>                    </td>
                  </tr>
                  <tr class="text-globales">
                    <td colspan="4" class="text-diez" >Atenci&oacute;n: Solo puede existir un domicilio del tipo Facturaci&oacute;n y Cobranza. De los del tipo Entrega solo uno puede ser marcado como default. </td>
                   </tr>
                  <tr class="fila-det">  
                    <td width="20%" class="fila-det-border">Calle: (*) </td>
                    <td width="31%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="calle" type="text" value="<%=BCF.getCalle()%>" class="campo" size="35" maxlength="70"  ></td>
                        </tr>
                    </table></td>
                    <td width="22%" class="fila-det-border">Nro:(*)</td>
                    <td width="27%" class="fila-det-border"><table width="6%" border="0"> 
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="nro" type="text" class="campo" id="nro" value="<%=BCF.getNro()%>" size="10" maxlength="10"  ></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">Tipo Domicilio:(*)                    </td>
                    <td class="fila-det-border">
                    <select name="idtipodomicilio" id="idtipodomicilio" class="campo" style="width:200px" >
                        <option value="-1">Seleccionar</option>
                        <%
                   iter = BCF.getListTiposDomicilio().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                        <option value="<%= datos[0] %>" <%=  BCF.getIdtipodomicilio().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                        <% 
                   } %>
                   </select>
                      <%-- <table width="23%" border="0" cellpadding="0" cellspacing="0">
                      <tr class="fila-det-border">
                        <td width="61%" ><input name="idtipodomicilio" type="hidden" id="idtipodomicilio" value="< %=BCF.getIdtipodomicilio()% >"><input name="tipodomicilio" type="text" class="campo" id="tipodomicilio" value="< %=BCF.getTipodomicilio()% >" size="30" readonly></td>
                        <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('clientesTiposDomiciliosLov.jsp', 'tdomi', 800, 450)" style="cursor:pointer"></td>
                      </tr>
                      </table> --%>                    </td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border">&nbsp;</td>
                  </tr>
                  <tr class="fila-det">
                    <td width="20%" class="fila-det-border">Localidad: (*) 
                      <input name="idlocalidad" type="hidden" id="idlocalidad" value="<%=BCF.getIdlocalidad()%>">
                    <input name="idprovincia" type="hidden" id="idprovincia">
                    <input name="provincia" type="hidden" id="provincia"></td>
                    <td width="31%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="localidad" type="text" class="campo" id="localidad" value="<%=BCF.getLocalidad()%>" size="30" readonly></td>
                          <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_localidades.jsp', 'localidad', 800, 450)" style="cursor:pointer"><!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_localidades.jsp')" style="cursor:pointer"--></td>
                        </tr>
                    </table></td>
                    <td width="22%" class="fila-det-border">Depto:</td>
                    <td width="27%" class="fila-det-border"><table width="6%" border="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="depto" type="text" class="campo" id="depto" value="<%=BCF.getDepto()%>" size="10" maxlength="10"  ></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">Codigo postal: </td>
                    <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="postal" type="text" value="<%=BCF.getPostal()%>" class="campo" size="10" maxlength="10" readonly=""  ></td>
                        </tr>
                    </table></td>
                    <td class="fila-det-border">Piso: </td>
                    <td class="fila-det-border"><table width="6%" border="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="piso" type="text" class="campo" id="piso" value="<%=BCF.getPiso()%>" size="10" maxlength="10"  ></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td width="20%" class="fila-det-border">CPA: </td>
                    <td width="31%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="cpa" type="text" class="campo" id="cpa" value="<%=BCF.getCpa()%>" size="10" maxlength="10"  ></td>
                        </tr>
                    </table></td>
                    <td width="22%" class="fila-det-border">Domicilio Entrega Default: </td>
                    <td width="27%" class="fila-det-border"><input name="esdefault" type="checkbox" id="esdefault" value="S" <%=BCF.getEsdefault().equalsIgnoreCase("S") ? "checked" : ""%>></td>
                  </tr>
                  <tr class="text-globales">
                    <td colspan="4" class="text-diez" ><img src="../imagenes/default/gnome_tango/actions/address-book-new.png" width="22" height="22"> Contacto &nbsp;</td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">Nombre Contacto: (*) </td>
                    <td class="fila-det-border"><table width="63%" border="0" cellpadding="0" cellspacing="0">
                      <tr class="fila-det-border">
                        <td width="61%" ><input name="contacto" type="text" value="<%=BCF.getContacto()%>" class="campo" size="35" maxlength="50"  ></td>
                        <td width="61%" ><img src="../imagenes/default/gnome_tango/actions/gtk-goto-first-ltr.png" width="15" height="15" onClick="sugiereRazon(document.frm.contacto)" style="cursor:pointer" title="Copiar Raz&oacute;n"></td>
                      </tr>
                    </table></td>
                    <td class="fila-det-border">Cargo  contacto en la empresa:</td>
                    <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="cargocontacto" type="text" value="<%=BCF.getCargocontacto()%>" class="campo" size="35" maxlength="50"  ></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td width="20%" class="fila-det-border">Telefono:</td>
                    <td width="31%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="telefonos" type="text" value="<%=BCF.getTelefonos()%>" class="campo" size="35" maxlength="50"  ></td>
                        </tr>
                    </table></td>
                    <td width="22%" class="fila-det-border">Celular: </td>
                    <td width="27%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="celular" type="text" value="<%=BCF.getCelular()%>" class="campo" size="35" maxlength="50"  ></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td width="20%" class="fila-det-border">Fax: </td>
                    <td width="31%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="fax" type="text" value="<%=BCF.getFax()%>" class="campo" size="35" maxlength="50"  ></td>
                        </tr>
                    </table></td>
                    <td width="22%" class="fila-det-border">Web: </td>
                    <td width="27%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="web" type="text" value="<%=BCF.getWeb()%>" class="campo" size="35" maxlength="50"  ></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">E-mail: </td>
                    <td class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="mail" type="text" class="campo" id="mail" size="30"  /></td>
                          <td width="39%"><img src="../imagenes/default/gnome_tango/actions/forward.png" title="Agregar e-mail" width="18" height="18" style="cursor:pointer" onClick="poblarLista(document.frm.mail)"></td>
                        </tr>
                    </table></td>
                    <td class="fila-det-border">
										  <select name="email" size="2" multiple id="email" style="font-size:10px;width:150px">
											  <%for(int m=0;BCF.getEmail()!=null&&m<BCF.getEmail().length;m++) { %>
                        <option value="<%= BCF.getEmail()[m] %>"><%= BCF.getEmail()[m] %></option>
												<%} %>
                      </select>											</td>
                    <td class="fila-det-border"><div style="vertical-align:middle"><img src="../imagenes/default/gnome_tango/status/gtk-missing-image.png" title="Eliminar e-mail" width="18" height="18" style="cursor:pointer" onClick="desPoblarLista(document.frm.email)"> Eliminar e-mails seleccionados.</div></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">&nbsp;</td>
                <td width="31%" class="fila-det-border">&nbsp;</td>
                <td width="22%" class="fila-det-border">&nbsp;</td>
                <td width="27%" class="fila-det-border"><input type="hidden" name="idempresa" value="<%= BCF.getIdempresa()%>">&nbsp;
                <input name="primeraCarga" type="hidden" id="primeraCarga" value="false"></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">&nbsp;</td>
                <td width="31%" class="fila-det-border"><input name="validar" type="submit" value="Confirmar" class="boton">
                  <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
                <td width="22%" class="fila-det-border">&nbsp;</td>
                <td width="27%" class="fila-det-border">&nbsp;</td>
              </tr>
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

