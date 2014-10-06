<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientetarjetascredito
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 23 19:21:13 GMT-03:00 2007 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientetarjetascreditoFrm"   scope="page"/>
<head>
 <title>FRMClientetarjetascredito.jsp</title>
 <meta http-equiv="description" content="mypage">
  
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE Tarjetas de Credito" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCF.ejecutarValidacion();
 %>
<form action="clientetarjetascreditoFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idtarjeta" type="hidden" value="<%=BCF.getIdtarjeta()%>" >
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
                <td width="17%" class="fila-det-border">Tarjeta de Credito: (*) </td>
                <td width="83%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="tarjetacredito" type="text" class="campo" id="tarjetacredito" value="<%=BCF.getTarjetacredito()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_tarjetamarcas.jsp')" style="cursor:pointer"></td>
                    <input name="idtarjetacredito" type="hidden" id="idtarjetacredito" value="<%=BCF.getIdtarjetacredito()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Cliente: (*) </td>
                <td width="83%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="cliente" type="text" class="campo" id="cliente" value="<%=BCF.getCliente()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_clientes2.jsp')" style="cursor:pointer"></td>
                    <input name="idcliente" type="hidden" id="idcliente" value="<%=BCF.getIdcliente()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Tip Tarjeta: (*) </td>
                <td width="83%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                        <input name="tipotarjeta" type="text" class="campo" id="tipotarjeta" value="<%=BCF.getTipotarjeta()%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_tipotarjeta.jsp')" style="cursor:pointer"></td>
                       <input name="idtipotarjeta" type="hidden" id="idtipotarjeta" value="<%=BCF.getIdtipotarjeta()%>">
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">N&ordm; Tarjeta: (*) </td>
                <td width="83%" class="fila-det-border"><input name="nrotarjeta" type="text" value="<%=BCF.getNrotarjeta()%>" class="campo" size="20" maxlength="20"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">N&ordm; Control: (*) </td>
                <td width="83%" class="fila-det-border"><input name="nrocontrol" type="text" value="<%=BCF.getNrocontrol()%>" class="campo" size="10" maxlength="10"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Fecha Emision: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_emisionStr" value="<%=BCF.getFecha_emisionStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fecha_emisionStr','BTN_date_7');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Fecha Vencimiento: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_vencimientoStr" value="<%=BCF.getFecha_vencimientoStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_8', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_8', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_8', 'img_Date_DOWN');showCalendar('frm','fecha_vencimientoStr','BTN_date_8');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_8" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Titular: (*) </td>
                <td width="83%" class="fila-det-border"><input name="titular" type="text" value="<%=BCF.getTitular()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Orden: (*) </td>
                <td width="83%" class="fila-det-border"><input name="orden" type="text" value="<%=BCF.getOrden()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Activa: (*) </td>
                <td width="83%" class="fila-det-border"><select name="activa" id="activa"  >
                  <option value="S" <%= BCF.getActiva().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                  <option value="N" <%= BCF.getActiva().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                </select></td>
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

