<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: contablemonedas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 15 12:14:05 ART 2008 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanContablemonedasFrm"   scope="page"/>
<head>
 <title>FRMContablemonedas.jsp</title>
 <meta http-equiv="description" content="mypage">
  <link rel="stylesheet" href="<%=pathskin%>">
 
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
<script language="JavaScript" src="<%=pathscript%>/forms.js"></script> 
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE Monedas" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCF.ejecutarValidacion();
 %>
<form action="contablemonedasFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idmoneda" type="hidden" value="<%=BCF.getIdmoneda()%>" >
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
                <td width="12%" class="fila-det-border">Moneda:  </td>
                <td width="88%" class="fila-det-border"><input name="moneda" type="text" value="<%=BCF.getMoneda()%>" class="campo" size="30" maxlength="30"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Hasta:  </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="hasta_moStr" value="<%=BCF.getHasta_moStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','hasta_moStr','BTN_date_3');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_3" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Ceros:  </td>
                <td width="88%" class="fila-det-border"><input name="ceros_mo" type="text" value="<%=BCF.getCeros_mo()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Detalle:  </td>
                <td width="88%" class="fila-det-border"><input name="detalle" type="text" value="<%=BCF.getDetalle()%>" class="campo" size="30" maxlength="30"  ></td>
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

