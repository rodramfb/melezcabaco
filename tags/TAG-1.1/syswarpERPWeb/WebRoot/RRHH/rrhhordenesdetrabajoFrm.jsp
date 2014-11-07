<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: rrhhordenesdetrabajo
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Apr 04 12:30:21 ART 2008 
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
<jsp:useBean id="BRF"  class="ar.com.syswarp.web.ejb.BeanRrhhordenesdetrabajoFrm"   scope="page"/>
<head>
 <title>FRMRrhhordenesdetrabajo.jsp</title>
<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BRF" property="*" />
 <% 
 String titulo = BRF.getAccion().toUpperCase() + " DE Ordenes de Trabajo" ;
 BRF.setResponse(response);
 BRF.setRequest(request);
 BRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BRF.setUsuarioact( session.getAttribute("usuario").toString() );
 BRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BRF.ejecutarValidacion();
 %>
<form action="rrhhordenesdetrabajoFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BRF.getAccion()%>" >
<input name="idordendetrabajo" type="hidden" value="<%=BRF.getIdordendetrabajo()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BRF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Cliente: (*) </td>
                <td width="86%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="cliente" type="text" class="campo" id="cliente" value="<%=BRF.getCliente()%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_clientes.jsp')" style="cursor:pointer"></td>
<input name="idcliente" type="hidden" id="idcliente" value="<%=BRF.getIdcliente()%>">
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Descripcion: (*) </td>
                <td width="86%" class="fila-det-border"><input name="descripcion" type="text" value="<%=BRF.getDescripcion()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Fecha inicio: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input name="fechainicioStr" type="text" class="cal-TextBox" id="fechainicioStr" onFocus="this.blur()" value="<%=BRF.getFechainicioStr()%>" size="12" maxlength="12" readonly>
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fechainicioStr','BTN_date_4');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Fecha prometida: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input name="fechaprometidaStr" type="text" class="cal-TextBox" id="fechaprometidaStr" onFocus="this.blur()" value="<%=BRF.getFechaprometidaStr()%>" size="12" maxlength="12" readonly>
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_5', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_5', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_5', 'img_Date_DOWN');showCalendar('frm','fechaprometidaStr','BTN_date_5');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_5" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Fecha final: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input name="fechafinalStr" type="text" class="cal-TextBox" id="fechafinalStr" onFocus="this.blur()" value="<%=BRF.getFechafinalStr()%>" size="12" maxlength="12" readonly>
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechafinalStr','BTN_date_6');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_6" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Horas estimadas: (*) </td>
                <td width="86%" class="fila-det-border"><input name="horasestimadas" type="text" value="<%=BRF.getHorasestimadas()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Valor hora cliente: (*) </td>
                <td width="86%" class="fila-det-border"><input name="valorhoracliente" type="text" value="<%=BRF.getValorhoracliente()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Valor hora recurso: (*) </td>
                <td width="86%" class="fila-det-border"><input name="valorhorarecurso" type="text" value="<%=BRF.getValorhorarecurso()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Estado ot: (*) </td>
                <td width="86%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="estadoot" type="text" class="campo" id="estadoot" value="<%=BRF.getEstadoot()%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../RRHH/lov_estadosot.jsp')" style="cursor:pointer"></td>
<input name="idestadoot" type="hidden" id="idestadoot" value="<%=BRF.getIdestadoot()%>">
</tr>
</table></td>
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

