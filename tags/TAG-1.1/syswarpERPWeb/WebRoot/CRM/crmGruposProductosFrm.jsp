<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: crmGruposProductos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Aug 03 11:04:09 ART 2007 
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
<jsp:useBean id="BCGPF"  class="ar.com.syswarp.web.ejb.BeanCrmGruposProductosFrm"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="mypage">
<link href="<%=pathskin%>" rel="stylesheet" type="text/css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCGPF" property="*" />
 <% 
 String titulo = BCGPF.getAccion().toUpperCase() + " DE  GRUPOS DE PRODUCTOS" ;
 BCGPF.setResponse(response);
 BCGPF.setRequest(request);
 BCGPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCGPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCGPF.setIdempresa(new BigDecimal( session.getAttribute("empresa").toString() ));   
 BCGPF.ejecutarValidacion();
 %>
<form action="crmGruposProductosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCGPF.getAccion()%>" >
<input name="idgrupoproducto" type="hidden" value="<%=BCGPF.getIdgrupoproducto()%>" >
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
                <td height="26" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCGPF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="18%" height="39" class="fila-det-border">&nbsp;Grupo Producto: (*) </td>
                <td width="82%" class="fila-det-border">&nbsp;
                <input name="grupoproducto" type="text" value="<%=BCGPF.getGrupoproducto()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td height="32" class="fila-det-border">&nbsp;</td>
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

