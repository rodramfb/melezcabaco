<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: pedidos_Regalos_Entregas_Cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Nov 16 09:07:57 GMT-03:00 2009 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


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
String color_fondo = "";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BPRECF"  class="ar.com.syswarp.web.ejb.BeanPedidos_Regalos_Entregas_CabeFrm"   scope="page"/>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPRECF" property="*" />
 <% 
 Iterator iter;
 String titulo = BPRECF.getAccion().toUpperCase() + " DE ENTREGAS DE REGALOS EMPRESARIOS" ;
 BPRECF.setResponse(response);
 BPRECF.setRequest(request);
 BPRECF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPRECF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPRECF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPRECF.ejecutarValidacion();
 %>
<head>
 <title><%= titulo %></title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">

<!--<link rel="stylesheet" type="text/css" href="../imagenes/default/erp-style.css"> -->


 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script>

 function setResetValue(obj, valor){
   obj.value = trim(obj.value) == ""  ?  valor  : "" ;
 }

function setConfirmar(){

   if(confirm('Confirma entrega con los los datos ingresados?')){
     document.frm.confirmar.disabled = true;
     document.frm.validar.value = 'validar';
     document.frm.confirmar.value = 'Procesando ...';
     setTimeout ("document.frm.submit();", 2000); 
   }
}

function callAbrirVentanaDomicilio(pagina){
  var mensaje = "Esta acción limpiará los campos de domicilio y los cambiará a modo 'solo lectura'";
      mensaje += "\npara reestablecer estos a modo 'lectura/escritura', presione el botón de limpieza.";
      mensaje += "\nContinua de todas formas?";

  if(confirm(mensaje)){  
    abrirVentana(pagina, 'domi', 800, 450);
    reinicializaDomicilio();
    habilitarDomicilio( true );
  }
}


function limpiarDomicilio(){
  var mensaje = "Confirma limpiar datos para ingresar nuevo domicilio?";

  if(confirm(mensaje)){  
    reinicializaDomicilio();
    habilitarDomicilio( false );
  }
}


function reinicializaDomicilio(){
	 document.frm.idsucuclie.value = -1;
	// --		
	 document.frm.idlocalidad.value = -1;
	 document.frm.localidad.value = '';
	 document.frm.idprovincia.value = -1;
	 document.frm.provincia.value = '';
	// --		
	 document.frm.calle.value = '';
	 document.frm.nro.value = '';
	 document.frm.piso.value = '';
	 document.frm.depto.value = '';
	 document.frm.cpa.value = '';
	 document.frm.postal.value = '';
	 document.frm.contacto.value = '';
	 document.frm.cargocontacto.value = '';
	 document.frm.telefonos.value = '';
	 document.frm.celular.value = '';
	 document.frm.fax.value = '';
	 document.frm.web.value = '';
	// --
	 document.frm.idexpreso.value = -1;
	 document.frm.idzona.value = -1;
	 document.frm.idanexolocalidad.value = -1;
}

function habilitarDomicilio(  readonly ){
   var colorRead = '#F0F0FF' ;
   var colorReadWrite = '#FFFFFF' ;
	 //document.frm.idsucuclie.value = -1;
	// --		
	 //document.frm.idlocalidad.value = -1;
   //document.frm.localidad.readOnly = readonly;
	 //document.frm.idlocalidad.readOnly = readonly;
	 //document.frm.idprovincia.value = -1;
	 //document.frm.provincia.readOnly = readonly;
	// --		
	 document.frm.calle.readOnly = readonly;
   document.frm.calle.style.backgroundColor = readonly ? colorRead : colorReadWrite;

	 document.frm.nro.readOnly = readonly;
   document.frm.nro.style.backgroundColor = readonly ? colorRead : colorReadWrite;

	 document.frm.piso.readOnly = readonly;
   document.frm.piso.style.backgroundColor = readonly ? colorRead : colorReadWrite;

	 document.frm.depto.readOnly = readonly;
   document.frm.depto.style.backgroundColor = readonly ? colorRead : colorReadWrite;

	 document.frm.cpa.readOnly = readonly;
   document.frm.cpa.style.backgroundColor = readonly ? colorRead : colorReadWrite;

	 document.frm.contacto.readOnly = readonly;
   document.frm.contacto.style.backgroundColor = readonly ? colorRead : colorReadWrite;

	 document.frm.cargocontacto.readOnly = readonly;
   document.frm.cargocontacto.style.backgroundColor = readonly ? colorRead : colorReadWrite;

	 document.frm.telefonos.readOnly = readonly;
   document.frm.telefonos.style.backgroundColor = readonly ? colorRead : colorReadWrite;

	 document.frm.celular.readOnly = readonly;
   document.frm.celular.style.backgroundColor = readonly ? colorRead : colorReadWrite;

	 document.frm.fax.readOnly = readonly;
   document.frm.fax.style.backgroundColor = readonly ? colorRead : colorReadWrite;

	 document.frm.web.readOnly = readonly;
   document.frm.web.style.backgroundColor = readonly ? colorRead : colorReadWrite;
	// --
	 //document.frm.idexpreso.value = -1;
	 //document.frm.idzona.value = -1;
	 //document.frm.idanexolocalidad.value = -1;
}


function reutilizarDatos( ){
  document.frm.idsucuclie.value = -1;
  habilitarDomicilio( false );
}


window.onload = function (){

  var rw =   '<%= BPRECF.getDomicilioReadonly() %>';
  if(rw == 'readonly'){
    habilitarDomicilio(true);
  }

}

 </script>

</head>
<BODY >
<div id="popupcalendar" class="text"></div>

<form action="pedidos_Regalos_Entregas_CabeFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPRECF.getAccion()%>" >
<input name="idpedido_regalos_entrega_cabe" type="hidden" value="<%=BPRECF.getIdpedido_regalos_entrega_cabe()%>" >
<table width="100%"  border="1" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td><table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
      <tr class="text-globales">
        <td height="35"><%= titulo %></td>
      </tr>
    </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
          <tr class="fila-det-bold-rojo">
            <td class="fila-det-border"><input name="idpedido_regalos_cabe" type="hidden" value="<%=BPRECF.getIdpedido_regalos_cabe()%>" >
                <input name="idestado" type="hidden" value="<%=BPRECF.getIdestado()%>" >
                <input name="idcliente" type="hidden" value="<%=BPRECF.getIdcliente()%>" >
                <input name="idsucursal" type="hidden" value="<%=BPRECF.getIdsucursal()%>" >
                <input name="idsucuclie" type="hidden" value="<%=BPRECF.getIdsucuclie()%>" >
                <input name="idexpreso" type="hidden" value="<%=BPRECF.getIdexpreso()%>" >
                <input name="idzona" type="hidden" value="<%=BPRECF.getIdzona()%>" >
                <input name="zona" type="hidden" value="<%=BPRECF.getZona()%>" >
                <input name="validar" type="hidden" value="" >
              &nbsp;
              <input name="expreso" type="hidden" id="expreso" value="<%=BPRECF.getExpreso()%>" > <input name="primeraCarga" type="hidden" id="primeraCarga" value="false" ></td>
            <td colspan="3" class="fila-det-border"><jsp:getProperty name="BPRECF" property="mensaje"/>  
              &nbsp;</td>
          </tr>
          <tr class="fila-det-bold"> 
            <td width="20%" class="fila-det-border">Nro.&nbsp;Pedido: </td>
            <td width="33%" class="fila-det-border"><%=BPRECF.getIdpedido_regalos_cabe()%>&nbsp;</td>
            <td width="20%" class="fila-det-border">Estado:</td>
            <td width="27%" class="fila-det-border"><%=BPRECF.getEstadopedido_ppal()%></td>
          </tr>
          <tr class="fila-det-bold">
            <td class="fila-det-border">Cliente:</td>
            <td class="fila-det-border" ><%=BPRECF.getIdcliente() + " - " + BPRECF.getRazon()%></td>
            <td class="fila-det-border" >&nbsp;</td>
            <td class="fila-det-border" >&nbsp;</td>
          </tr>
          <tr class="fila-det-bold">
            <td class="fila-det-border">Fecha:</td>
            <td class="fila-det-border" ><%=BPRECF.getFechapedido_ppal()%></td>
            <td class="fila-det-border" >Prioridad:</td>
            <td class="fila-det-border" ><%=BPRECF.getPrioridadpedido_ppal()%>&nbsp;</td>
          </tr>
          <tr class="fila-det">
            <td class="fila-det-border">&nbsp;</td>
            <td class="fila-det-border" >&nbsp;</td>
            <td class="fila-det-border" >&nbsp;</td>
            <td class="fila-det-border" >&nbsp;</td>
          </tr>
          <tr class="text-globales">
            <td height="27" class="fila-det-border">Datos Entrega </td>
            <td class="fila-det-border" >&nbsp;</td>
            <td class="fila-det-border" >&nbsp;</td>
            <td class="fila-det-border" >&nbsp;</td>
          </tr>
          <tr class="fila-det"> 
            <td width="20%" height="34" class="fila-det-border">Fecha: (*) </td>
            <td class="fila-det-border" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechapedido" value="<%=BPRECF.getFechapedido()%>" maxlength="12">
              <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechapedido','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
            <td class="fila-det-border" >Prioridad: (*) </td> 
            <td class="fila-det-border" ><select name="idprioridad" id="idprioridad" class="campo" style="width:200px" >
                <option value="-1">Seleccionar</option>
                <% 
									  iter = BPRECF.getListPrioridades().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                <option value="<%= datos[0] %>" <%= datos[0].equals( BPRECF.getIdprioridad().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                <%  
									  }%>
            </select></td>
          </tr>
          <tr class="fila-det">
            <td width="20%" class="fila-det-border">Obs. Armado: </td>
            <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="51%"><textarea name="obsarmado" style="width:200px" rows="2" class="campo"><%=BPRECF.getObsarmado()%></textarea></td>
                  <td width="49%"><img src="../imagenes/default/gnome_tango/actions/gtk-goto-first-ltr.png" width="18" height="18" style="cursor:pointer" onClick="setResetValue( document.frm.obsarmado, '<%= BPRECF.getObsarmadopedido_ppal().replaceAll("\n", "") %>')" title="Copiar observaciones del pedido principal."></td>
                </tr>
              </table></td>
            <td class="fila-det-border">Obs. Entrega: </td>
            <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="63%"><textarea name="obsentrega" style="width:200px" rows="2" class="campo"><%=BPRECF.getObsentrega()%></textarea></td>
                  <td width="37%"><img src="../imagenes/default/gnome_tango/actions/gtk-goto-first-ltr.png" width="18" height="18" style="cursor:pointer" onClick="setResetValue( document.frm.obsentrega, '<%= BPRECF.getObsentregapedido_ppal().replaceAll("\n", "") %>')" title="Copiar observaciones del pedido principal."></td>
                </tr>
              </table></td>
          </tr>
          <tr class="fila-det">
            <td width="20%" class="fila-det-border">&nbsp;</td>
            <td class="fila-det-border">&nbsp;</td>
            <td class="fila-det-border">&nbsp;</td>
            <td class="fila-det-border">&nbsp;</td> 
          </tr>
      </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="1" cellpadding="0" align="center">
      <tr class="text-globales" >
        <td width="2%" rowspan="2" ><img src="../imagenes/default/gnome_tango/actions/delivery.gif" width="22" height="22"></td>
        <td width="45%" rowspan="2" >Art&iacute;culo</td>
        <td width="28%" rowspan="2" >Dep&oacute;sito</td>
         <td colspan="4" ><div align="center">Cantidades</div></td>
         </tr>
      <tr class="text-globales">
        <td width="7%" ><div align="right">Pedido</div></td>
        <td width="7%" ><div align="right">Insumido</div></td>
        <td width="6%" ><div align="right">Diponible</div></td>
        <td width="5%" ><div align="right">Entrega</div></td>
      </tr>
      <%
       iter = BPRECF.getListDisponiblePedido().iterator();
       int r = 0;
			 while(iter.hasNext()){
					
					String[] sCampos = (String[]) iter.next(); 
					// estos campos hay que setearlos segun la grilla 
					if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
					else color_fondo = "fila-det-verde";
          String estilo_readonly = "";
          if(  Double.parseDouble ( sCampos[8] ) > 0 ) {
            estilo_readonly =   "style=\"text-align:right\"" ; 
          } else {

            estilo_readonly =  "style=\"text-align:right;background:#CCCCCC\" readonly "; 
            if(BPRECF.getCant_entrega() != null) BPRECF.getCant_entrega()[r] = "0";
            
          }
          %>
      <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
        <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/devices/camera_unmount.png" width="18" height="18" onClick="abrirVentana('../General/globalBlobImagenesAbm.jsp?soloImagen=true&tupla=<%=sCampos[0]%>', 'BLOB', 800, 450)" style="cursor:pointer" title="<%= sCampos[2] %>"></div></td>
        <td class="fila-det-border" ><%=sCampos[1]%> - <%=sCampos[2].length() > 25 ? sCampos[2].substring(0, 25) : sCampos[2] %>&nbsp;</td>
        <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;
          <input  type="hidden" name="codigo_st" id="codigo_st" value="<%=sCampos[1]%>">
          <input  type="hidden" name="cant_disponible" id="cant_disponible" value="<%=sCampos[8]%>">
		  <input  type="hidden" name="codigo_dt" id="codigo_dt" value="<%=sCampos[9]%>">
		</td>
        <td class="fila-det-border" ><div align="right"><%=sCampos[6]%></div></td>
        <td class="fila-det-border" ><div align="right"><%=sCampos[7]%></div></td>
        <td class="fila-det-border" ><div align="right"><%=sCampos[8]%></div></td>
        <td class="fila-det-border" ><div align="right">
          <input name="cant_entrega" type="text" class="campo" id="cant_entrega" <%=  estilo_readonly %> value="<%= BPRECF.getCant_entrega() != null  ? BPRECF.getCant_entrega()[r] : "0" %>" size="5" maxlength="5" <%=sCampos[8]%> onKeyPress="if(!validaNumericosFF(event)) return false;">
        </div></td>
      </tr>
      <%
         r++;
			 }%>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="2" cellpadding="0" align="center">
      <tr class="text-globales">
        <td width="3%" ><img src="../imagenes/default/gnome_tango/actions/stock_spam.png" width="22" height="22"></td>
        </tr>
      <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
        <td class="fila-det-border" ><div align="center">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

            <tr class="fila-det">
              <td class="fila-det-border">Calle: (*) </td>
              <td width="33%" class="fila-det-border"><table width="70%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="70%"><input name="calle" type="text" value="<%=BPRECF.getCalle()%>" class="campo" size="40" maxlength="100" <%//= BPRECF.getDomicilioReadonly() %> ></td>
                    <td width="10%"><img src="../imagenes/default/gnome_tango/actions/stock_home.png" width="18" height="18" title="Buscar un domicilio existente." onClick="callAbrirVentanaDomicilio('../Clientes/lov_pedidosDomiciliosEntrega.jsp?idcliente=<%=BPRECF.getIdcliente()%>')" style="cursor:pointer"></td>
                    <td width="10%"><img src="../imagenes/default/gnome_tango/actions/stock_refresh.png" width="18" height="18" title="Reutilizar datos actuales y generar un nuevo domicilio, en el caso que solo cambie el piso o depto." onClick="reutilizarDatos();"></td>
                    <td width="10%"><img src="../imagenes/default/gnome_tango/actions/editclear.png" width="18" height="18" title="Limpiar datos actuales." onClick="limpiarDomicilio();"></td>
                  </tr>
                </table></td>
              <td width="14%" class="fila-det-border">Nro.: </td>
              <td width="10%" class="fila-det-border"><input name="nro" type="text" value="<%=BPRECF.getNro()%>" class="campo" size="4" maxlength="5" <%//= BPRECF.getDomicilioReadonly() %>></td>
              <td width="4%" class="fila-det-border">&nbsp;Piso: </td>
              <td width="5%" class="fila-det-border"><input name="piso" type="text" value="<%=BPRECF.getPiso()%>" class="campo" size="4" maxlength="3" <%//= BPRECF.getDomicilioReadonly() %>></td>
              <td width="5%" class="fila-det-border">Depto: </td>
              <td width="15%" class="fila-det-border"><input name="depto" type="text" value="<%=BPRECF.getDepto()%>" class="campo" size="4" maxlength="3" <%//= BPRECF.getDomicilioReadonly() %>></td>
            </tr>

            <tr class="fila-det">
              <td class="fila-det-border">Localidad:
                <input name="idanexolocalidad" type="hidden" value="<%=BPRECF.getIdanexolocalidad()%>" class="campo" size="10" maxlength="100"></td>
              <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="58%"><input name="localidad" type="text" class="campo" id="localidad" value="<%=BPRECF.getLocalidad()%>" size="40" maxlength="100" readonly ></td>
                    <td width="42%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_localidades_anexos.jsp', 'localidad', 800, 450)" style="cursor:pointer"> </td>
                  </tr>
                </table></td>
              <td class="fila-det-border">Provincia:
                <input name="idprovincia" type="hidden" class="campo" id="idprovincia" value="<%=BPRECF.getIdprovincia()%>" size="10" maxlength="100"></td>
              <td colspan="5" class="fila-det-border"><input name="provincia" type="text" class="campo" id="provincia" value="<%=BPRECF.getProvincia()%>" size="40" maxlength="100" readonly></td>
            </tr>
            <tr class="fila-det">
              <td width="14%" class="fila-det-border">CP: 
                <input name="idlocalidad" type="hidden" class="campo" id="idlocalidad" value="<%=BPRECF.getIdlocalidad()%>" size="10" maxlength="100"></td>
              <td width="33%" class="fila-det-border"><input name="postal" type="text" value="<%=BPRECF.getPostal()%>" class="campo" size="10" maxlength="10" readonly></td>
              <td class="fila-det-border">Cpa: </td>
              <td colspan="5" class="fila-det-border"><input name="cpa" type="text" value="<%=BPRECF.getCpa()%>" class="campo" size="15" maxlength="15" <%//= BPRECF.getDomicilioReadonly() %>></td>
            </tr>

            <tr class="fila-det">
              <td width="14%" class="fila-det-border">Contacto: (*) </td>
              <td width="33%" class="fila-det-border"><input name="contacto" type="text" value="<%=BPRECF.getContacto()%>" class="campo" size="40" maxlength="50" <%//= BPRECF.getDomicilioReadonly() %>></td>
              <td class="fila-det-border">Cargo Contacto: </td>
              <td colspan="5" class="fila-det-border"><input name="cargocontacto" type="text" value="<%=BPRECF.getCargocontacto()%>" class="campo" size="40" maxlength="50" <%//= BPRECF.getDomicilioReadonly() %>></td>
            </tr>

            <tr class="fila-det">
              <td width="14%" class="fila-det-border">Tel&eacute;fonos: </td>
              <td width="33%" class="fila-det-border"><input name="telefonos" type="text" class="campo" value="<%=BPRECF.getTelefonos()%>" size="40" <%//= BPRECF.getDomicilioReadonly() %>/></td>
              <td class="fila-det-border">Celular: </td>
              <td colspan="5" class="fila-det-border"><input name="celular" type="text" value="<%=BPRECF.getCelular()%>" class="campo" size="40" maxlength="50" <%//= BPRECF.getDomicilioReadonly() %>></td>
            </tr>

            <tr class="fila-det">
              <td width="14%" class="fila-det-border">Fax: </td>
              <td width="33%" class="fila-det-border"><input name="fax" type="text" value="<%=BPRECF.getFax()%>" class="campo" size="40" maxlength="50" <%//= BPRECF.getDomicilioReadonly() %>></td>
              <td class="fila-det-border">Web: </td>
              <td colspan="5" class="fila-det-border"><input name="web" type="text" value="<%=BPRECF.getWeb()%>" class="campo" size="40" maxlength="50" <%//= BPRECF.getDomicilioReadonly() %>></td>
            </tr>
          </table>
           </div></td>
        </tr>
    </table>
  </td>
  </tr>
  <tr>
    <td height="56">
      <input name="confirmar" type="button" value="Confirmar Entrega" class="boton" onClick="setConfirmar();">
      <input name="volver" type="submit" class="boton" id="volver" value="Volver">
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

