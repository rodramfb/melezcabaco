<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: TMCategoriasSocios
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 04 14:26:05 ART 2007 
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
<jsp:useBean id="BTMCSF"  class="ar.com.syswarp.web.ejb.BeanTMCategoriasSociosFrm"   scope="page"/>
<head>
 <title>FRMTMCategoriasSocios.jsp</title>
<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BTMCSF" property="*" />
 <% 
 String titulo = BTMCSF.getAccion().toUpperCase() + " DE Telemarketing Categorias Socio" ;
 BTMCSF.setResponse(response);
 BTMCSF.setRequest(request);
 BTMCSF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BTMCSF.setUsuarioact( session.getAttribute("usuario").toString() );
 BTMCSF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BTMCSF.ejecutarValidacion();
 %>
<form action="TMCategoriasSociosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BTMCSF.getAccion()%>" >
<input name="idcategoriasocio" type="hidden" value="<%=BTMCSF.getIdcategoriasocio()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BTMCSF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Categoria socio: (*) </td>
                <td width="86%" class="fila-det-border"><input name="categoriasocio" type="text" value="<%=BTMCSF.getCategoriasocio()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Observaciones:  </td>
                <td width="86%" class="fila-det-border"><textarea name="observaciones" cols="70" rows="6" class="campo"><%=BTMCSF.getObservaciones()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="14%" class="fila-det-border">Adicional o desc:  </td>
                <td width="86%" class="fila-det-border"><input name="adidesc" type="text" value="<%=BTMCSF.getAdidesc()%>" class="campo" size="18" maxlength="18"  ></td>
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

