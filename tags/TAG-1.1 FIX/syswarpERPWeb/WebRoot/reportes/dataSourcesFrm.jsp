<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: dataSources
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Jul 03 10:04:11 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BDSF"  class="ar.com.syswarp.web.ejb.report.BeanDataSourcesFrm"   scope="page"/>
<head>
 <title>FRMDataSources.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" type="text/css" href="imagenes/default/tnx.css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BDSF" property="*" />
 <% 
 String titulo = BDSF.getAccion().toUpperCase() + " DE DATASOURCES" ;
 BDSF.setResponse(response);
 BDSF.setRequest(request);
 BDSF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BDSF.setUsuarioact( session.getAttribute("usuario").toString() );
BDSF.ejecutarValidacion();
 %>
  <form action="dataSourcesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BDSF.getAccion()%>" >
<input name="iddatasource" type="hidden" value="<%=BDSF.getIddatasource()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
            </table> 
            <table width="90%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BDSF" property="mensaje"/>&nbsp;</td>
              </tr>
        <tr class="fila-det">
          <td width="21%" class="fila-det-border">&nbsp;Datasource: (*) </td>
          <td width="79%" class="fila-det-border">&nbsp;<input name="datasource" type="text" value="<%=BDSF.getDatasource()%>" class="campo" size="100" maxlength="100"  ></td>
        </tr>
        <tr class="fila-det">
          <td width="21%" class="fila-det-border">&nbsp;Driver: (*) </td>
          <td width="79%" class="fila-det-border">&nbsp;<input name="driver" type="text" value="<%=BDSF.getDriver()%>" class="campo" size="100" maxlength="100"  ></td>
        </tr>
        <tr class="fila-det">
          <td width="21%" class="fila-det-border">&nbsp;Usuario Base de Datos:  </td>
          <td width="79%" class="fila-det-border">&nbsp;<input name="db_user" type="text" value="<%=BDSF.getDb_user()%>" class="campo" size="100" maxlength="100"  ></td>
        </tr>
        <tr class="fila-det">
          <td width="21%" class="fila-det-border">&nbsp;Password Base de Dataos:  </td>
          <td width="79%" class="fila-det-border">&nbsp;<input name="db_pass" type="text" value="<%=BDSF.getDb_pass()%>" class="campo" size="100" maxlength="100"  ></td>
        </tr>
        <tr class="fila-det">
          <td width="21%" class="fila-det-border">&nbsp;url: (*) </td>
          <td width="79%" class="fila-det-border">&nbsp;<input name="url" type="text" value="<%=BDSF.getUrl()%>" class="campo" size="100" maxlength="200"  ></td>
        </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;
								<input name="validar" type="submit" value="Enviar" class="boton">               
								<input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>  
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

