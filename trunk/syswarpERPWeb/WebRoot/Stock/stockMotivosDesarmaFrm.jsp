<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: stockMotivosDesarma
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jan 03 14:18:59 ART 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*"%>
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
<jsp:useBean id="BSMDF"  class="ar.com.syswarp.web.ejb.BeanStockMotivosDesarmaFrm"   scope="page"/>
<head>
 <title>FRMStockMotivosDesarma.jsp</title>
<meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script></head>
 <script language="JavaScript" src="scripts/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BSMDF" property="*" />
 <% 
 String titulo = BSMDF.getAccion().toUpperCase() + " DE Motivos Desarmado" ;
 BSMDF.setResponse(response);
 BSMDF.setRequest(request);
 BSMDF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BSMDF.setUsuarioact( session.getAttribute("usuario").toString() );
 BSMDF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BSMDF.ejecutarValidacion();
 %>
<form action="stockMotivosDesarmaFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BSMDF.getAccion()%>" >
<input name="idmotivodesarma" type="hidden" value="<%=BSMDF.getIdmotivodesarma()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BSMDF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">Motivo desarmado:(*) </td>
                <td width="82%" class="fila-det-border"><input name="motivodesarma" type="text" value="<%=BSMDF.getMotivodesarma()%>" class="campo" size="50" maxlength="50"  ></td>
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

