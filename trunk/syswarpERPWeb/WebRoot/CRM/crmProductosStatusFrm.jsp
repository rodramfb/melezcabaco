<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: crmProductosStatus
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Aug 03 10:09:42 ART 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
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
<jsp:useBean id="BCPSF"  class="ar.com.syswarp.web.ejb.BeanCrmProductosStatusFrm"   scope="page"/>
<head>
 <title>FRMCrmProductosStatus.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCPSF" property="*" />
 <% 
 String titulo = BCPSF.getAccion().toUpperCase() + " DE ESTADOS DE PRODUCTOS" ;
 BCPSF.setResponse(response);
 BCPSF.setRequest(request);
 BCPSF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCPSF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCPSF.setIdempresa(new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCPSF.ejecutarValidacion();
 %>
<form action="crmProductosStatusFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCPSF.getAccion()%>" >
<input name="idproductostatus" type="hidden" value="<%=BCPSF.getIdproductostatus()%>" >
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
                <td height="23" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCPSF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="23%" height="33" class="fila-det-border">Estado de Producto : (*) </td>
                <td width="77%" class="fila-det-border">&nbsp;
                <input name="productostatus" type="text" value="<%=BCPSF.getProductostatus()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td height="34" class="fila-det-border">&nbsp;</td>
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

