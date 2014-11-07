<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacotmresultados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 11 11:16:36 GMT-03:00 2007 
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
<jsp:useBean id="BBF"  class="ar.com.syswarp.web.ejb.BeanBacotmresultadosFrm"   scope="page"/>
<head>
 <title> </title>
<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBF" property="*" />
 <% 
 String titulo = BBF.getAccion().toUpperCase() + " DE RESULTADOS DE LLAMADOS" ;
 BBF.setResponse(response);
 BBF.setRequest(request);
 BBF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BBF.ejecutarValidacion();
 %>
<form action="bacotmresultadosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBF.getAccion()%>" >
<input name="idresultado" type="hidden" value="<%=BBF.getIdresultado()%>" >
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
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BBF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="20%" height="42" class="fila-det-border">Resultado: (*)  </td>
                <td colspan="2" class="fila-det-border"><input name="resultado" type="text" value="<%=BBF.getResultado()%>" class="campo" size="50" maxlength="50"  >                  <div align="center"></div></td>
              </tr>
              
              <tr class="fila-det">
                <td height="43" class="fila-det-border">Motivos Asociados: </td>
                <td width="12%" class="fila-det-border"><select name="idmotivos" size="15" multiple id="idmotivos" class="campo">
                  <% 
                                   Iterator iter = BBF.getListMotivos().iterator();
                                   while(iter.hasNext()) {
                                     String[] datos = (String[])iter.next();
                                  %>
                  <option value="<%= datos[0] %>" <%= datos[2].equals("1") ? "selected" : "" %> ><%= datos[1] %> </option>
                  <%
                                   } 
                                  %>
                </select></td>
                <td width="68%" class="fila-det-border"> <div align="center">Para seleccionar multiples motivos presione [Ctrl + Click] o [Shift + Click] .</div></td>
              </tr>
              <tr class="fila-det">
                <td height="43" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton"></td>
                <td class="fila-det-border"><input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

