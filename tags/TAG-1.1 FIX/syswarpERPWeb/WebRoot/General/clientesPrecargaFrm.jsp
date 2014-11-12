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
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientesPrecargaFrm"   scope="page"/>
<head>
 <title></title>
 <title></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <!--BORRAR-->
 <link rel="stylesheet" href="../imagenes/default/erp-style.css">
 <link rel="stylesheet" href="vs/calendar/calendar.css"> 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script></head>
 
 
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
		document.frm.idzona.value = document.frm.zona.value = document.frm.idexpreso.value = document.frm.expreso.value = document.frm.idcobrador.value = document.frm.cobrador.value = document.frm.mail.value= "";
		document.frm.esdefault.checked = false; 
		//for(var i=document.frm.email.options.length;i>0;i--){
			//	document.frm.email.options[i-1] = null;
		//}		
	}
	
	 // --
	function limpiarTarjetas(){
				 document.frm.idtarjeta.value =  document.frm.tarjetacredito.value = document.frm.idtipotarjeta.value =  document.frm.tipotarjeta.value =
				  document.frm.idtarjetacredito.value = document.frm.nrotarjeta.value =  document.frm.nrocontrol.value =  document.frm.fecha_emision.value =  
				 document.frm.fecha_vencimiento.value =  document.frm.titular.value = document.frm.orden.value = "";
				 document.frm.activa.selectedIndex = 0; 
	}
 // --  
	//function seleccionMail(){
		
		//for(var f=0;f<document.frm.email.options.length;f++){
			//document.frm.email.options[f].selected = true;
		//}
	//}  
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
	  document.frm.action = 'clientesPrecargaFrm.jsp#' + identidad.toLowerCase();
	}
	//--
	function confirmarItem(tipoitem){
	   //seleccionMail(); 
		 document.frm.subAccion.value = 'agregar' + tipoitem; 
		 setAction(tipoitem);
		 document.frm.submit();
	}	
	
	
	
	function validar1(){  
	   if (document.frm.idorigen.value < 0 ) {
	       alert("Primero seleccione el Origen");
	   }else{
	     	abrirVentana('../General/lov_suborigen.jsp?idorigen=' + document.frm.idorigen.value, 'suborigen', 800, 450 )	
	   }
    }

  function replicaRazon(){
    document.frm.titular.value  = trim( document.frm.titular.value  ) == '' ? document.frm.razon.value : document.frm.titular.value;
    document.frm.contacto.value = trim( document.frm.contacto.value ) == '' ? document.frm.razon.value : document.frm.contacto.value;
  }	

  function sugiereRazon(obj){
    var razon =  document.frm.razon.value
    obj.value  = trim( obj.value  ) == '' ? razon : '' ;
  }	
	
	function getPromocionVigente(){  
	   var idtipoclie = document.frm.idtipoclie.value;
	   //alert(idtipoclie);
	   if (idtipoclie < 0 ) {
	       alert("Seleccione tipo de cliente para poder seleccionar promocion activa.");
	   }else{
	     	abrirVentana('../Clientes/lov_clientesPromocionesTipoCliente.jsp?idtipoclie=' + idtipoclie , 'promocion', 800, 450);	
	   }
    }	
	// 
	
	
	function asignarCuentaNeto(obj){
	  document.frm.idctaneto.value = obj.options[obj.selectedIndex].label;
	}
 </script>
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
 String titulo = BCF.getAccion().toUpperCase() + " DE PROSPECTO" ;
 %>
<form action="clientesPrecargaFrm.jsp" method="post" name="frm"  >
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
				
				
				<% if (!BCF.getAccion().equalsIgnoreCase("alta")){%>	
                <td width="20%" class="fila-det-border">Codigo:  (*)</td>
                <td width="31%" class="fila-det-border">
								   <table width="6%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="idprecarga" type="text" class="campo" id="idprecarga" value="<%=BCF.getIdprecarga()%>" size="18" maxlength="18" readonly="" ></td>
                    </tr>
                  </table>				</td>
				 <% 
                   } %>
				
				
                <td class="fila-det-border">Razon Social: (*) </td>
                <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="razon" type="text" value="<%=BCF.getRazon()%>" class="campo" size="35" maxlength="100" onBlur="replicaRazon();"></td> 
                    </tr>
                </table
								></td>
			  </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Nro Documento: (*) </td>
                <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="nrodocumento" type="text" class="campo" id="nrodocumento" value="<%=BCF.getNrodocumento()%>" size="18" maxlength="15" <%= !BCF.getAccion().equalsIgnoreCase("alta") ? "readonly" : "" %>> </td>
                    </tr> 
                </table></td>
                <td width="22%" class="fila-det-border">Tipo Documento:(*)                  </td>
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
                      out.write( " <input name=\"tipodocumento\" type=\"hidden\" id=\"tipodocumento\" value=\"" + BCF.getTipodocumento() + "\">") ;
                   } %>                 </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Tipo de cliente: 
                <input name="idtipoclie" type="hidden" id="idtipoclie" value="<%=BCF.getIdtipoclie()%>"></td>
                <td class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="tipoclie" type="text" class="campo" id="tipoclie" value="<%=str.esNulo(BCF.getTipoclie())%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_tipocliente.jsp', 'tipoclie', 800, 450)" style="cursor:pointer"></td>
                    </tr>
                </table></td>
                <td class="fila-det-border">Autorizado: (*) </td>
                <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
											<select name="autorizado" id="autorizado"  class="campo" style="width:200px" >
                          <option value="S" <%= BCF.getAutorizado().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                          <option value="N" <%= BCF.getAutorizado().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                      </select>											</td>
                    </tr>
                </table></td>
              </tr>
			  <tr class="fila-det">
                <td class="fila-det-border">Fecha de Ingreso: (*)</td>
                <td class="fila-det-border"><table width="73%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly="readonly" type="text" name="fechadeingreso" value="<%=BCF.getFechadeingreso()%>" maxlength="12">
                      <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_5', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_5', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_5', 'img_Date_DOWN');showCalendar('frm','fechadeingreso','BTN_date_5');return false;"> <img src="vs/calendar/btn_date_up.gif" name="BTN_date_5" width="22" height="17" border="0" align="absmiddle" id="BTN_date_5"></a></td>
                  </tr>
                </table></td>
                <td class="fila-det-border">Sexo</td>
                <td class="fila-det-border"><select name="sexo" id="sexo"  class="campo" style="width:200px" >
                  <option value="M" <%= BCF.getSexo().equalsIgnoreCase("M") ? "selected" : "" %> >MASCULINO</option>
                  <option value="F" <%= BCF.getSexo().equalsIgnoreCase("F") ? "selected" : "" %> >FEMENINO</option>
                </select></td>
			  </tr>
			  <tr class="fila-det">
                <td class="fila-det-border">Fecha de Nacimiento: (*)</td>
                <td class="fila-det-border"><table width="73%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly="readonly" type="text" name="fechadenacimiento" value="<%=BCF.getFechadenacimiento()%>" maxlength="12">
                      <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechadenacimiento','BTN_date_5');return false;"> <img src="vs/calendar/btn_date_up.gif" name="BTN_date_6" width="22" height="17" border="0" align="absmiddle" id="BTN_date_6"></a></td>
                  </tr>
                </table></td>
                <td class="fila-det-border">Referencia <input name="referencia" type="hidden" id="referencia" value="<%=BCF.getReferencia()%>"></td>
                <td class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="d_referencia" type="text" class="campo" id="d_referencia" value="<%=Common.setNotNull(BCF.getD_referencia())%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../General/lov_referencia.jsp', 'referencia', 800, 450)" style="cursor:pointer"></td>
                  </tr>
                </table></td> 
			  </tr>
			  <tr class="fila-det">
                <td class="fila-det-border">Vendedor Asignado: (*) 
                  <input name="idvendedorasignado" type="hidden" id="idvendedorasignado" value="<%=BCF.getIdvendedorasignado()%>"></td>
                <td class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="vendedorasignado" type="text" class="campo" id="vendedorasignado" value="<%=str.esNulo(BCF.getVendedorasignado())%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_vendedorasignado.jsp', 'vendedor', 800, 450)" style="cursor:pointer"></td>
                  </tr>
                </table></td>
                <td class="fila-det-border">Clientes Estado Precarga:(*)                </td>
                <td class="fila-det-border">
                  <select name="idestadoprecarga" id="idestadoprecarga" class="campo" style="width:200px" >
                    <option value="-1">Seleccionar</option>
                    <%
                   iter = BCF.getListEstados().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                    <option value="<%= datos[0] %>" <%=  BCF.getIdestadoprecarga().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                    <% 
                   } %>
                  </select></td>
			  </tr>
			  <tr class="fila-det">
                <td class="fila-det-border">Nuevo o Reactivacion (*) </td>
            <td class="fila-det-border"><select name="nuevoreactivacion" id="nuevoreactivacion"  class="campo" style="width:200px" >
                <option value="N" <%= BCF.getNuevoreactivacion().equalsIgnoreCase("N") ? "selected" : "" %> >Nuevo</option>
              </select></td>
                <td class="fila-det-border">Porcentaje</td>
                <td class="fila-det-border"><select name="porcentaje" id="porcentaje"  class="campo"  style="width:200px" >
                  <option value="0"  >Seleccionar</option>
                  <option value="50" <%= BCF.getPorcentaje().intValue()== 50   ? "selected" : "" %> >50</option>
                  <option value="100" <%= BCF.getPorcentaje().intValue()== 100 ? "selected" : "" %> >100</option>
                </select></td>
			  </tr>
			  
			  <tr class="fila-det">
                <td class="fila-det-border">Preferencia 
                (*)                  </td>
                <td class="fila-det-border"><select name="idpreferencia" id="idpreferencia" class="campo" style="width:200px" >
                    <option value="-1">Seleccionar</option>
                    <%
                   iter = BCF.getListPreferencias().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                    <option value="<%= datos[0] %>" <%=  BCF.getIdpreferencia().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                    <% 
                   } %>
                  </select></td>
                <td class="fila-det-border">Origen 
                (*)
                  <input name="idorigen" type="hidden" id="idorigen" value="<%=BCF.getIdorigen()%>"></td>
                <td class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="origen" type="text" class="campo" id="origen" value="<%=str.esNulo(BCF.getOrigen())%>" size="30" readonly="readonly"></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../General/lov_origen2.jsp', 'origen', 800, 450)" style="cursor:pointer"></td>
                  </tr>
                </table></td>
			  </tr>
			  <tr class="fila-det">
                <td class="fila-det-border">Promociones:
                <input name="idpromocion" type="hidden" id="idpromocion" value="<%=BCF.getIdpromocion()%>"></td>
                <td class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="promocion" type="text" class="campo" id="promocion" value="<%=str.esNulo(BCF.getPromocion())%>" size="30" readonly="readonly"></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="getPromocionVigente();" style="cursor:pointer"></td>
                  </tr>
                </table></td>
                <td class="fila-det-border">Sub origen (*) 
                  <input name="idsuborigen" type="hidden" id="idsuborigen" value="<%=BCF.getIdsuborigen()%>"></td>
                <td class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="suborigen" type="text" class="campo" id="suborigen" value="<%=str.esNulo(BCF.getSuborigen())%>" size="30" readonly="readonly"></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22"  onClick= "validar1();" style="cursor:pointer"></td>
                  </tr>
                </table></td>
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
                <td class="fila-det-border">Sucursal Facturaci&oacute;n:(*)</td>
                <td class="fila-det-border"><select name="sucursalFactura" id="sucursalFactura" class="campo" >
                    <option value="">Sel.</option>
                    <%
						    iter = BCF.getListSucursales().iterator();   
							while(iter.hasNext()){
							 String[] datos = (String[]) iter.next();
							 %>
                    <option value="<%= datos[0] %>" <%=  BCF.getSucursalFactura().toString().equals(datos[0]) ? "selected" : "" %> label="<%= datos[1] %>"><%= Common.strZero(datos[0] , 4) %></option>
                    <% 
						   } %>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Tipo de I.V.A.: (*)                </td>
                <td class="fila-det-border"><select name="idtipoiva" id="idtipoiva" class="campo" style="width:200px" >
                    <option value="-1">Seleccionar</option>
                    <%
                   iter = BCF.getListIva().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                    <option value="<%= datos[0] %>" <%=  BCF.getIdtipoiva().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                    <% 
                   } %>
                  </select></td>
                <td class="fila-det-border">Condicion de Pago: (*) 
                <input name="" type="hidden" id="" value="<%=BCF.getIdcondicion()%>"></td>
                <td class="fila-det-border">
				 <select name="idcondicion" id="idcondicion" class="campo" style="width:200px"  onChange="asignarCuentaNeto(this)">
                    <option value="-1">Seleccionar</option>
                    <%
                   iter = BCF.getListCondicion().iterator();   
                    while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                    <option value="<%= datos[0] %>"  <%=  BCF.getIdcondicion().toString().equals(datos[0]) ? "selected" : "" %> label="<%= datos[6] %>" title="<%= datos[7] %>"><%= datos[1] %></option>
                    <% 
                   } %>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Cuenta Contable Neto:</td>
                <td class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="idctaneto" type="text" class="campo" id="idctaneto" value="<%=str.esNulo(BCF.getIdctaneto())%>" size="20" maxlength="20" readonly="readonly"></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/status/info.png" width="18" height="18" title='Para asignar cuenta es necesario seleccionar "Condición de Pago"' style="cursor:help" ><%-- <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_cuentascontables.jsp', 'cta', 800, 450)" style="cursor:pointer"> --%></td>
                    </tr>
                </table></td>
                <td class="fila-det-border">Moneda de Facturacion: (*)                </td>
                <td class="fila-det-border">
                 <select name="idmoneda" id="idmoneda" class="campo" style="width:200px" >
                   <%
                   iter = BCF.getMonedaList().iterator();   
                   while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                     <option value="<%= datos[0] %>" <%= ( BCF.getIdmoneda().longValue() < 0 && datos[1].trim().equalsIgnoreCase("PESOS") )|| BCF.getIdmoneda().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                   <% 
                   } %>
                 </select>                 </td>
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
                  </select></td>
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
                </select></td>
                <td class="fila-det-border">Tipo de Comprobante :                </td>
                <td class="fila-det-border">
                 <select name="idtipocomp" id="idtipocomp" class="campo" style="width:200px" >
                  <%
                   iter = BCF.getTiposComprobList().iterator();   
                   while(iter.hasNext()){
                     String[] datos = (String[]) iter.next(); 
                     %>
                  <option value="<%= datos[0] %>" <%= ( BCF.getIdtipocomp().longValue() < 0 && datos[2].trim().equalsIgnoreCase("FA") )|| BCF.getIdtipocomp().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[2] %></option>
                  <% 
                   } %>
                </select>                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Descuento 1: </td>
                <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><input name="descuento1" type="text" value="<%=BCF.getDescuento1()%>" class="campo" size="18" maxlength="18"  ></td>
                    </tr>
                </table></td>
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
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det" >
                <td colspan="4" class="fila-det-border">
								<table width="100%" border="1px" cellspacing="0" cellpadding="0" align="center" id="tarjeta">
                  <tr class="text-dos-bold" >
                    <td colspan="4" > <img src="../imagenes/default/gnome_tango/actions/contact-new.png" width="22" height="22"> TARJETAS DE CREDITO DEL PROSPECTO </td>
                  </tr>  
                  <tr class="text-dos-bold" >
                    <td colspan="4" class="fila-det-bold-rojo"><%=BCF.getSubAccion().equalsIgnoreCase("agregarTarjeta") ?  BCF.getMensaje() : ""   %></td>
                  </tr>
                  <tr class="text-globales">
                    <td colspan="4" class="fila-det" >
										<% 
										Enumeration enu;
										Hashtable htTarjetas = (Hashtable)session.getAttribute("htTarjetasCliente");
										enu = htTarjetas.keys();
										if(!htTarjetas.isEmpty()){
										%>
										<table width="100%" border="1" cellspacing="0" cellpadding="0" >
                        <tr class="fila-det-bold">
                          <td>Tarjeta</td>
                          <td>Activa</td>
                          <td>Titular</td>
                          <td>Nro. Tarjeta </td>
                          <td width="15%">Orden</td>
                          <td width="7%">&nbsp;</td>
                          <td width="8%">&nbsp;</td>
                        </tr>
										<%
											while(enu.hasMoreElements()){
												Object [] tarjeta = (Object[])htTarjetas.get(enu.nextElement());
										 %>
                        <tr class="fila-det">
                          <td width="13%"><%= tarjeta[2] %>&nbsp;</td>
                          <td width="6%"><%= tarjeta[11] %>&nbsp;</td>
                          <td width="24%"><%= tarjeta[9] %>&nbsp;</td>
                          <td width="27%"><%= tarjeta[5] %>&nbsp;</td>
						  <td width="27%"><%= tarjeta[10] %>&nbsp;</td>
                          <td>&nbsp;</td>
                          <td><% if(!tarjeta[12].toString().equalsIgnoreCase("B")) {%>
                            <img src="../imagenes/default/gnome_tango/actions/stock_paste.png" title="Modificar esta tarjeta ..." width="18" height="18" style="cursor:pointer" onClick="modificarItem(<%= tarjeta[0] %>, 'Tarjeta')">
                            <% }
												   else{ %>
                            <img src="../imagenes/default/gnome_tango/status/image-missing.png" title="Imposible modificar esta tarjeta, el mismo esta marcado para eliminar ..." width="18" height="18" style="cursor:pointer">
                          <% } %></td>
                          <td>
						  
						  <%
						   if(BCF.getIdcliente().intValue() < 1){
						     if(!tarjeta[12].toString().equalsIgnoreCase("B")) {%>
                            <img src="../imagenes/default/gnome_tango/status/edittrash.png" title="Marcar para eliminar esta tarjeta ..." width="18" height="18" style="cursor:pointer" onClick="marcarBaja(<%= tarjeta[0] %>, 'Tarjeta')">
                            <% }
												   else{ %>
                            <img src="../imagenes/default/gnome_tango/actions/gtk-undo-ltr.png" title="Desahacer marcar para eliminar ..." width="18" height="18" style="cursor:pointer" onClick="undoMarcarBaja(<%= tarjeta[0] %>, 'Tarjeta')">
                          <% } 
						   }else{  
						  %>
						  <img src="../imagenes/default/gnome_tango/actions/lock.png" width="18" height="18" title="No es posible eliminar tarjetas, prospecto ya es cliente." style="cursor:pointer">
						  <% 
						   } %>
						  </td>
                        </tr>
										<% 
											}										
										 %>											
                    </table>		
										<% 
										}										
										 %>										</td>
                  </tr>
                  <tr class="text-dos-bold" >
                    <td colspan="3" >
                      <input name="idtarjeta" type="hidden" id="idtarjeta" value="<%= BCF.getIdtarjeta()%>">                   </td>
                    <td ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr class="text-dos-bold">
                        <td><!--input name="agregarTarjeta" type="submit" class="boton" id="agregarTarjeta" value="Confirmar"-->
                          <div align="center"><img src="../imagenes/default/nuevo.gif" width="16" height="16" border="0"  onClick="confirmarItem('Tarjeta');"  style="cursor:pointer"> Confirmar </div></td>
                        <td><!--input name="Limpiar" type="button" class="boton" id="Limpiar" value="Limpiar"-->
                          <div align="center"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="18" height="18" border="0"  onClick="limpiarTarjetas()"  style="cursor:pointer"> Limpiar </div></td>
                      </tr>
                    </table></td>
                  </tr> 

                  <tr class="fila-det">
                    <td width="20%" class="fila-det-border">Tarjeta de Credito: (*) <input name="idtarjetacredito" type="hidden" id="idtarjetacredito" value="<%=BCF.getIdtarjetacredito()%>"></td>
                    <td width="31%" class="fila-det-border">
										<table width="23%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="tarjetacredito" type="text" class="campo" id="tarjetacredito" value="<%=BCF.getTarjetacredito()%>" size="30" readonly="readonly"></td>
                          <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_tarjetamarcas.jsp', 'tarjetamarca', 700, 400)" style="cursor:pointer"></td>
                        </tr>
                    </table></td>
                    <td width="22%" class="fila-det-border">N&ordm; Tarjeta: (*) </td>
                    <td width="27%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="nrotarjeta" type="text" value="<%=BCF.getNrotarjeta()%>" class="campo" size="20" maxlength="20" ></td>
                        </tr>
                      </table></td>
                  </tr>
                  <tr class="fila-det"> 
                    <td width="20%" class="fila-det-border">Tipo Tarjeta: (*)                      </td>
                    <td width="31%" class="fila-det-border">
                   <select name="idtipotarjeta" id="idtipotarjeta" class="campo" style="width:200px" >
                      <%
                   iter = BCF.getListTiposTarjeta().iterator();   
                   while(iter.hasNext()){
                     String[] datos = (String[]) iter.next();
                     %>
                      <option value="<%= datos[0] %>" <%= ( BCF.getIdtipotarjeta().longValue() < 0 && datos[1].trim().toUpperCase().indexOf("PROPIA") > -1 )|| BCF.getIdtipotarjeta().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                      <% 
                   } %>
                    </select>
										 <%--  <table width="64%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="88%" ><input name="tipotarjeta" type="text" class="campo" id="tipotarjeta" value="< %=BCF.getTipotarjeta()% >" size="30"  readonly="">
                          <input name="idtipotarjeta" type="hidden" id="idtipotarjeta" value="< %=BCF.getIdtipotarjeta()% >"></td> 
                          <td width="12%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22"  onClick="abrirVentana('../Clientes/lov_tipotarjeta.jsp', 'tipotarjeta', 700, 400)" style="cursor:pointer"  ></td>
                        </tr>
                    </table> --%>                    </td>
                    <td width="22%" class="fila-det-border">N&ordm; Control: (*)</td>
                    <td width="27%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="nrocontrol" type="text" value="<%=BCF.getNrocontrol()%>" class="campo" size="10" maxlength="10"  ></td>
                        </tr>
                      </table>										</td>
                  </tr> 
                  <tr class="fila-det">
                    <td class="fila-det-border">Titular: (*)</td>
                    <td class="fila-det-border"><table width="57%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="titular" type="text" value="<%=BCF.getTitular()%>" class="campo" size="30" maxlength="100"  ></td>
                          <td width="61%" ><img src="../imagenes/default/gnome_tango/actions/gtk-goto-first-ltr.png" width="15" height="15" onClick="sugiereRazon(document.frm.titular)" style="cursor:pointer" title="Copiar Razón"></td>
                        </tr>
                      </table></td>
                    <td class="fila-det-border">Orden: (*)</td>
                    <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="orden" type="text" value="<%=BCF.getOrden()%>" class="campo" size="10" maxlength="18"  ></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">Fecha Emision: (*)</td>
                    <td class="fila-det-border"><table width="73%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly="readonly" type="text" name="fecha_emision" value="<%=BCF.getFecha_emision()%>" maxlength="12">
                            <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fecha_emision','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                        </tr>
                      </table></td>
                    <td class="fila-det-border">Activa: (*)</td>
                    <td class="fila-det-border"><table width="47%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><select name="activa" id="activa" class="campo"  >
                              <option value=""  >Seleccionar</option>
                              <option value="S" <%= BCF.getActiva().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                              <option value="N" <%= BCF.getActiva().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                            </select>                          </td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">Fecha Vencimiento: (*)</td>
                    <td class="fila-det-border"><table width="73%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly="readonly" type="text" name="fecha_vencimiento" value="<%=BCF.getFecha_vencimiento()%>" maxlength="12">
                            <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_8', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_8', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_8', 'img_Date_DOWN');showCalendar('frm','fecha_vencimiento','BTN_date_8');return false;"> <img align="absmiddle" border="0" name="BTN_date_8" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                        </tr>
                    </table></td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border">&nbsp;</td>
                  </tr>
                  <tr class="text-dos-bold" >
                    <td colspan="4">&nbsp;</td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td colspan="4" class="fila-det-border">
								 <table width="100%" border="1px" cellspacing="0" cellpadding="0" align="center" id="domicilio">
                  <tr class="text-globales">
                    <td colspan="4" class="text-diez" ><img src="../imagenes/default/gnome_tango/actions/go-home.png" width="22" height="22"> DOMICILIOS DEL PROSPECTO </td>
                  </tr>
                  <tr class="text-globales">
                    <td colspan="4" class="fila-det-bold-rojo" ><%=BCF.getSubAccion().equalsIgnoreCase("agregarDomicilio") ?  BCF.getMensaje() : ""   %></td>
                  </tr>
                  <tr class="text-globales">
                    <td colspan="4" class="fila-det" >
										<% 
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
                      </tr>
										<%
											while(enu.hasMoreElements()){
												Object [] domicilio = (Object[])htDomicilios.get(enu.nextElement());
											 
										 %>
                      <tr class="fila-det">
                        <td width="12%">&nbsp;<%= domicilio[4] %></td>
                        <td width="5%">&nbsp;<%= domicilio[5] %></td>
                        <td width="18%">&nbsp;<%= domicilio[9] %></td>
                        <td width="23%">&nbsp;<%= domicilio[12] %></td>
						<td width="35%">&nbsp;<%= domicilio[2] %></td>  
                        <td width="2%">
												<% if(!domicilio[27].toString().equalsIgnoreCase("B")) {%>
										    <img src="../imagenes/default/gnome_tango/actions/stock_paste.png" title="Modificar este domicilio ..." width="18" height="18" style="cursor:pointer" onClick="modificarItem(<%= domicilio[0] %>, 'Domicilio')">
												<% }
												   else{ %>
												   <img src="../imagenes/default/gnome_tango/status/image-missing.png" title="Imposible modificar este domicilio, el mismo esta marcado para eliminar ..." width="18" height="18" style="cursor:pointer">
						<% } %>												</td>
                        <td width="5%">
												<% if(!domicilio[27].toString().equalsIgnoreCase("B")) {%>
												  <img src="../imagenes/default/gnome_tango/status/edittrash.png" title="Marcar para eliminar este domicilio ..." width="18" height="18" style="cursor:pointer" onClick="marcarBaja(<%= domicilio[0] %>, 'Domicilio')">
												<% }
												   else{ %>
												  <img src="../imagenes/default/gnome_tango/actions/gtk-undo-ltr.png" title="Desahacer marcar para eliminar ..." width="18" height="18" style="cursor:pointer" onClick="undoMarcarBaja(<%= domicilio[0] %>, 'Domicilio')">
						<% } %>												
						</td>
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
                    <input name="iddomicilio" type="hidden" id="iddomicilio" value="<%= BCF.getIddomicilio()%>"></td>
                    <td class="text-diez" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr class="text-diez">
                        <td><!--input name="agregarDomicilio" type="submit" class="boton" id="agregarDomicilio" value="Confirmar" onClick="seleccionMail(); document.frm.subAccion.value = 'agregarDomicilio'"-->
                          <div align="center"><img src="../imagenes/default/nuevo.gif" width="16" height="16" border="0"  onClick="confirmarItem('Domicilio');"  style="cursor:pointer"> Confirmar</div></td>
                        <td><!--input name="Limpiar" type="button" class="boton" id="Limpiar" value="Limpiar" onClick="limpiarDomicilios()" -->
                          <div align="center"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="18" height="18" border="0"  onClick="limpiarDomicilios()"  style="cursor:pointer"> Limpiar</div></td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr class="text-globales">
                    <td colspan="4" class="text-diez" ><p>Atenci&oacute;n: Solo puede existir un domicilio del tipo Facturaci&oacute;n y Cobranza. De los del tipo Entrega solo uno puede ser marcado como default. </p>                    </td>
                   </tr>
                  <tr class="fila-det">  
                    <td width="20%" class="fila-det-border">Calle: (*) </td>
                    <td width="30%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="calle" type="text" value="<%=BCF.getCalle()%>" class="campo" size="35" maxlength="70"  ></td>
                        </tr>
                    </table></td>
                    <td width="23%" class="fila-det-border">Nro:(*)</td>
                    <td width="27%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0"> 
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="nro" type="text" class="campo" id="nro" value="<%=BCF.getNro()%>" size="10" maxlength="10"  ></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">Tipo Domicilio:(*)                   </td>
                    <td class="fila-det-border"><select name="idtipodomicilio" id="idtipodomicilio" class="campo" style="width:200px" >
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
                        <td width="61%" > <input name="idtipodomicilio" type="hidden" id="idtipodomicilio" value="< %=BCF.getIdtipodomicilio()% >">
                                          <input name="tipodomicilio" type="text" class="campo" id="tipodomicilio" value="< %=BCF.getTipodomicilio()% >" size="30" readonly></td>
                        <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('clientesTiposDomiciliosLov.jsp')" style="cursor:pointer"></td>
                      </tr>
                    </table> --%></td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border">&nbsp;</td>
                  </tr>
                  <tr class="fila-det">
                    <td width="20%" class="fila-det-border">Localidad: (*) 
                      <input name="idlocalidad" type="hidden" id="idlocalidad" value="<%=BCF.getIdlocalidad()%>">
                    <input name="idprovincia" type="hidden" id="idprovincia">
                    <input name="provincia" type="hidden" id="provincia"></td>
                    <td width="30%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="localidad" type="text" class="campo" id="localidad" value="<%=BCF.getLocalidad()%>" size="30" readonly="readonly"></td>
                          <td width="39%">
<!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_localidades.jsp')" style="cursor:pointer"-->
<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_localidades_anexos.jsp', 'localidad', 800, 450)" style="cursor:pointer"></td>
                        </tr>
                    </table></td>
                    <td width="23%" class="fila-det-border">Depto:</td>
                    <td width="27%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
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
                    <td class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="piso" type="text" class="campo" id="piso" value="<%=BCF.getPiso()%>" size="10" maxlength="10"  ></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td width="20%" class="fila-det-border">CPA: </td>
                    <td width="30%" class="fila-det-border"><table width="6%" border="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="cpa" type="text" class="campo" id="cpa" value="<%=BCF.getCpa()%>" size="10" maxlength="10"  ></td>
                        </tr>
                    </table></td>
                    <td width="23%" class="fila-det-border">Domicilio Entrega Default: </td>
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
                    <td width="30%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="telefonos" type="text" value="<%=BCF.getTelefonos()%>" class="campo" size="35" maxlength="50"  ></td>
                        </tr>
                    </table></td>
                    <td width="23%" class="fila-det-border">Celular: </td>
                    <td width="27%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="celular" type="text" value="<%=BCF.getCelular()%>" class="campo" size="35" maxlength="50"  ></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td width="20%" class="fila-det-border">Fax: </td>
                    <td width="30%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="fax" type="text" value="<%=BCF.getFax()%>" class="campo" size="35" maxlength="50"  ></td>
                        </tr>
                    </table></td>
                    <td width="23%" class="fila-det-border">Web: </td>
                    <td width="27%" class="fila-det-border"><table width="6%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="web" type="text" value="<%=BCF.getWeb()%>" class="campo" size="35" maxlength="50"  ></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">E-mail (separar con punto y coma ';')</td>
                    <td class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                        <tr class="fila-det-border">
                        	<td><textarea rows="4" cols="70" name="mail" id="mail" style="font-size:10px;width:150px"><%=BCF.getMail() %></textarea></td>
                          <!--<td width="61%" ><input name="mail" type="text" class="campo" id="mail" size="30"  /></td>
                          <td width="39%"><img src="../imagenes/default/gnome_tango/actions/forward.png" title="Agregar e-mail" width="18" height="18" style="cursor:pointer" onClick="poblarLista(document.frm.mail)"></td>
                        --></tr>
                    </table></td>
                    <!--<td class="fila-det-border">
										  <select name="email" size="2" multiple id="email" style="font-size:10px;width:150px">
											  < %for(int m=0;BCF.getEmail()!=null&&m<BCF.getEmail().length;m++) { %>
                        <option value="< %= BCF.getEmail()[m] %>">< %= BCF.getEmail()[m] %></option>
												< %} %>
                      </select>											</td>
                    <td class="fila-det-border"><div style="vertical-align:middle"><img src="../imagenes/default/gnome_tango/status/gtk-missing-image.png" title="Eliminar e-mail" width="18" height="18" style="cursor:pointer" onClick="desPoblarLista(document.frm.email)"> Eliminar e-mails seleccionados.</div></td>-->
                  </tr>
                  <tr class="text-globales">
                    <td colspan="4" class="text-diez" ><img src="../imagenes/default/gnome_tango/apps/krfb.png" width="22" height="22"> Distribuci&oacute;n y Log&iacute;stica &nbsp;</td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">Distribuidor: 
                    <input name="idzona" type="hidden" id="idzona" value="<%=BCF.getIdzona()%>">
                    <input name="idanexolocalidad" type="hidden" id="idanexolocalidad" value="<%=BCF.getIdanexolocalidad()%>"></td>
                    <td class="fila-det-border"><table width="23%" border="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="zona" type="text" class="campo" id="zona" value="<%=str.esNulo(BCF.getZona())%>" size="30" readonly="readonly"></td>
                          <td width="39%"><%// <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_zona.jsp')" style="cursor:pointer"> %></td>
                        </tr>
                    </table></td>
                    <td class="fila-det-border">Zona: 
                    <input name="idexpreso" type="hidden" id="idexpreso" value="<%=BCF.getIdexpreso()%>"></td>
                    <td class="fila-det-border"><table width="23%" border="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="expreso" type="text" class="campo" id="expreso" value="<%=str.esNulo(BCF.getExpreso())%>" size="30" readonly="readonly" style="border-style:none"></td>
                          <td width="39%"><!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_expreso.jsp')" style="cursor:pointer"--></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">Cobrador: (*) 
                      <input name="idcobrador" type="hidden" id="idcobrador" value="<%=BCF.getIdcobrador()%>"></td>
                    <td class="fila-det-border"><table width="23%" border="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><input name="cobrador" type="text" class="campo" id="cobrador" value="<%=str.esNulo(BCF.getCobrador())%>" size="30" readonly="readonly"></td>
                          <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_cobrador.jsp', 'cobrador', 800, 450)" style="cursor:pointer"></td>
                        </tr>
                    </table></td>
                    <td width="23%" class="fila-det-border"><%//= Vendedor:  %>
                      <input name="idvendedor" type="hidden" id="idvendedor" value="<%//=BCF.getIdvendedor()%>"></td>
                    <td width="27%" class="fila-det-border"><table width="23%" border="0">
                        <tr class="fila-det-border">
                          <td width="61%" ><% //<input name="vendedor" type="text" class="campo" id="vendedor" value="< %=str.esNulo(BCF.getVendedor())% >" size="30" readonly> %></td>
                          <td width="39%"><% // <img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_vendedor.jsp')" style="cursor:pointer"> %></td>
                        </tr>
                    </table></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Obs. Entrega: </td>
                <td colspan="3" class="fila-det-border"><table width="6%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" ><textarea name="obsentrega" cols="80" rows="2" class="campo" id="obsentrega"><%=str.esNulo(BCF.getObsentrega())%></textarea></td>
                    </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">&nbsp;</td>
                <td width="31%" class="fila-det-border">&nbsp; </td>
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
<input name="referer" value="<%= BCF.getReferer()%>" type="hidden">
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

