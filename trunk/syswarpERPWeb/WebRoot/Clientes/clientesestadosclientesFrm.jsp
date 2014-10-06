<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesestadosclientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Mar 02 14:15:18 GMT-03:00 2007 
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
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientesestadosclientesFrm"   scope="page"/>
<head>
 <title>FRMClientesestadosclientes.jsp</title>
 <meta http-equiv="description" content="mypage">
  
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
<script> 
function validarMotivo(){   
  if (document.frm.idestado.value==0 || document.frm.idestado.value==""){
     alert("Seleccione primero el Estado");	 		
  }else{
	   abreVentanaNombre("lov_motivos.jsp?idestado=" + document.frm.idestado.value , 'motivos',700, 400);  
  }	 
}


function validarEstado(){   
  if (document.frm.idmotivo.value!=0 || document.frm.idmotivo.value!=""){
	  abreVentanaNombre("lov_estados.jsp?idestado=" + document.frm.idestado.value , 'motivos',700, 400);
	  document.frm.idmotivo.value = "";
      document.frm.motivo.value = ""; 	
  }else{
	   abreVentanaNombre("lov_estados.jsp?idestado=" + document.frm.idestado.value , 'motivos',700, 400);  
  }	 
}

 </script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE Estados Clientes" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCF.ejecutarValidacion();
 %>
<form action="clientesestadosclientesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idestadocliente" type="hidden" value="<%=BCF.getIdestadocliente()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
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
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cliente: (*) </td>
                <td width="37%" class="fila-det-border"><input name="cliente" type="text" class="campo" id="cliente" value="<%=BCF.getCliente()%>" size="50" readonly></td>
                <td width="41%" class="fila-det-border"><% if(BCF.getAccion().equalsIgnoreCase("alta")){ %><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_clientes2.jsp')" style="cursor:pointer"><% } %>
                  <input name="idcliente" type="hidden" id="idcliente" value="<%=BCF.getIdcliente()%>"></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Estado: (*) </td>
                <td class="fila-det-border"><input name="estado" type="text" id="estado" size="50" maxlength="45" readonly="true" value="<%=BCF.getEstado()%>" class="campo"></td>
                <td class="fila-det-border"><a href="javascript:validarEstado()"><img src="../imagenes/default/gnome_tango/actions/filefind.png" title="Seleccion Estado" width="22" height="22" border="0">
                    <input name="idestado" type="hidden" id="idestado" value="<%=BCF.getIdestado()%>">
                </a></td>
              </tr>
			    
              <tr class="fila-det">
                <td width="22%" height="23" class="fila-det-border">Motivo:  </td>
              <td class="fila-det-border"><input name="motivo" type="text" id="motivo" size="50" maxlength="45" readonly="true" value="<%=str.esNulo(BCF.getMotivo())%>" class="campo"></td>
              <td class="fila-det-border"><a href="javascript:validarMotivo()"><img src="../imagenes/default/gnome_tango/actions/filefind.png" title="Seleccion Motivo" width="22" height="22" border="0">
                  <input name="idmotivo" type="hidden" id="idmotivo" value="<%=BCF.getIdmotivo()%>">
              </a></td>
              </tr>








              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Fecha desde: (*) </td>
                <td colspan="3" class="fila-det-border" >
				<input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechadesde" value="<%=BCF.getFechadesde()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a> </td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Fecha hasta: (*) </td>
                <td colspan="3" class="fila-det-border" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechahasta" value="<%=BCF.getFechahasta()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Fecha baja: (*) </td>
                <td colspan="3" class="fila-det-border" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fbaja" value="<%=BCF.getFbaja()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fbaja','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Observaciones:  </td>
                <td colspan="2" class="fila-det-border"><textarea name="observaciones" cols="80" rows="4" class="campo"><%= str.esNulo(BCF.getObservaciones())%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

