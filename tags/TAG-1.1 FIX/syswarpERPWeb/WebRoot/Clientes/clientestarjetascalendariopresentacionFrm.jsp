<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientestarjetascalendariopresentacion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 23 15:17:01 ART 2008 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientestarjetascalendariopresentacionFrm"   scope="page"/>
<head>
 <title>FRMClientestarjetascalendariopresentacion.jsp</title>
 <meta http-equiv="description" content="mypage">
  <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
<script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE CRONOGRAMA DE PRESENTACION DE TARJETAS" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCF.ejecutarValidacion();
 %>
<form action="clientestarjetascalendariopresentacionFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="codigo" type="hidden" value="<%=BCF.getCodigo()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Tarjeta de Credito: (*)  
                  <input name="idtarjetacredito" type="hidden" id="idtarjetacredito" value="<%=BCF.getIdtarjetacredito()%>"> </td>
                <td width="78%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">			  
                    <td width="61%" ><input name="tarjetacredito" type="text" class="campo" id="tarjetacredito" value="<%=BCF.getTarjetacredito()%>" size="30" readonly>
					
					</td>
					
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_tarjetamarcas.jsp', 'tarjetamarca', 700, 400)" style="cursor:pointer"></td>
					
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">A&ntilde;o: (*) </td>
                <td width="78%" class="fila-det-border">

                <select name="anio" class="campo" id="anio">
                  <option value="-1">Seleccionar</option> 
                <%// System.out.println("!!!!!!!!!\n!!!!!!!!!\nES LA NUEVA VERSION. !!!!!!!!!\n!!!!!!!!!\n");
                 for (int year = Calendar.getInstance().get(Calendar.YEAR);year<Calendar.getInstance().get(Calendar.YEAR)+3;year++){
                 %>
                  <option value="<%=  year %>" <%=  BCF.getAnio().intValue() == year ? "selected" : "" %>><%= year %></option>
                 <% 
                 } %>
                </select>          
                </td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Mes: (*)  
                  <input name="mes" type="hidden" id="mes" value="<%=BCF.getMes()%>">  </td>
                <td width="78%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="des_mes" type="text" class="campo" id="des_mes" value="<%=BCF.getDes_mes()%>" size="30" readonly>
                    </td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_meses.jsp', 'meses', 700, 400)" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Fecha Desde:  </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fpresentaciondesdeStr" value="<%=BCF.getFpresentaciondesdeStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_5', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_5', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_5', 'img_Date_DOWN');showCalendar('frm','fpresentaciondesdeStr','BTN_date_5');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_5" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Fecha Hasta </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fpresentacionhastaStr" value="<%=BCF.getFpresentacionhastaStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fpresentacionhastaStr','BTN_date_6');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_6" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

