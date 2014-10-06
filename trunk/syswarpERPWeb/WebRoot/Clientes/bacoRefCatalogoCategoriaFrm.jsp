<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoRefCatalogoCategoria
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Mar 15 09:25:38 ART 2012 
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
<jsp:useBean id="BBRCCF"  class="ar.com.syswarp.web.ejb.BeanBacoRefCatalogoCategoriaFrm"   scope="page"/>
<head>
 <title>FRMBacoRefCatalogoCategoria</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBRCCF" property="*" />
 <% 
 String titulo = BBRCCF.getAccion().toUpperCase() + " DE BACOREFCATALOGOCATEGORIA" ;
 BBRCCF.setResponse(response);
 BBRCCF.setRequest(request);
 BBRCCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBRCCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBRCCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRCCF.ejecutarValidacion();
 %>
<form action="bacoRefCatalogoCategoriaFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBRCCF.getAccion()%>" >
<input name="idcatalogocategoria" type="hidden" value="<%=BBRCCF.getIdcatalogocategoria()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BBRCCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" height="34" class="fila-det-border">Categoria: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="catalogocategoria" type="text" value="<%=BBRCCF.getCatalogocategoria()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" height="107" class="fila-det-border">Observaciones:  </td>
                <td width="88%" class="fila-det-border">&nbsp;<textarea name="observaciones" cols="70" rows="6" class="campo"><%=BBRCCF.getObservaciones()%></textarea></td>
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

