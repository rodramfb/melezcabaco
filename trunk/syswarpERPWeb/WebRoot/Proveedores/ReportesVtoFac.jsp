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
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.*"%>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanReportesProveedoresVtoFac"   scope="page"/>
<head>
 <title>FRMClientetarjetascredito.jsp</title>
 <meta http-equiv="description" content="mypage">
  <link rel="stylesheet" href="<%=pathskin%>">

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
 String titulo = "Reporte Vencimiento Factura" ; 
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.ejecutarValidacion();
 %>

<%
if (BCF.getFechavtodesdeStr()!="" && BCF.getFechavtohastaStr()!=""  ){ 
out.clear();
out = pageContext.pushBody(); 
ServletOutputStream ouputStream = null;
try {
    // definicion de variables (cambiar para cada reporte)

    String cReporteXML = "proveedoresvtofac";    
	
	javax.naming.Context context = new javax.naming.InitialContext();
	Object object = context.lookup("Report");
    ReportHome sHome = (ReportHome) javax.rmi.PortableRemoteObject.narrow(object, ReportHome.class);
	Report repo = sHome.create();
	
	Map parameters = new HashMap(); 	
    parameters.put("fechavtodesdeStr", BCF.getFechavtodesdeStr());
    parameters.put("fechavtohastaStr", BCF.getFechavtohastaStr());
	
	 
	byte[] bytes = repo.getOpenReport(cReporteXML,parameters);

    response.setContentType("application/pdf"); 
    response.setContentLength(bytes.length); 
    ouputStream = response.getOutputStream(); 
    ouputStream.write(bytes, 0, bytes.length); 
    ouputStream.flush(); 
    ouputStream.close(); 
   
 }
 catch (Exception e) { 
    e.printStackTrace(); 
	System.out.println("Error2:" +e.getMessage()); 
 }          
}
%>
<form action="ReportesVtoFac.jsp" method="post" name="frm">

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
                <td width="81%" class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">Fecha Vencimiento Desde: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechavtodesdeStr" value="<%=BCF.getFechavtodesdeStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechavtodesdeStr','BTN_date_7');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
              </tr>
              <tr class="fila-det">
                <td width="19%" class="fila-det-border">Fecha Vencimiento Hasta: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechavtohastaStr" value="<%=BCF.getFechavtohastaStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_8', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_8', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_8', 'img_Date_DOWN');showCalendar('frm','fechavtohastaStr','BTN_date_8');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_8" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>
                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               </td>
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

