<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoClustersLogistica
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Mar 31 16:05:57 ART 2011 
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
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BBCLF"  class="ar.com.syswarp.web.ejb.BeanBacoClustersLogisticaFrm"   scope="page"/>
<head>
 <title>FRMBacoClustersLogistica</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBCLF" property="*" />
 <% 
 String titulo = BBCLF.getAccion().toUpperCase() + " DE CLUSTERS DE LOGISTICA" ;
 BBCLF.setIdempresa ( new BigDecimal( session.getAttribute("empresa").toString() )  );
 BBCLF.setResponse(response);
 BBCLF.setRequest(request);
 BBCLF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBCLF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBCLF.ejecutarValidacion();
 %>
<form action="bacoClustersLogisticaFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBCLF.getAccion()%>" >
<input name="idclusterlogistica" type="hidden" value="<%=BBCLF.getIdclusterlogistica()%>" >
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
                <td height="25" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BBCLF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="14%" height="38" class="fila-det-border">&nbsp;Cluster: (*) </td>
                <td width="86%" class="fila-det-border">&nbsp;<input name="clusterlogistica" type="text" value="<%=BBCLF.getClusterlogistica()%>" class="campo" size="50" maxlength="50"  ></td>
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

