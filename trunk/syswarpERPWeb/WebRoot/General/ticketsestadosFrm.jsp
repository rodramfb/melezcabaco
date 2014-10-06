<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: ticketsestados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Sep 20 09:41:44 ART 2012 
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
<jsp:useBean id="BTF"  class="ar.com.syswarp.web.ejb.BeanTicketsestadosFrm"   scope="page"/>
<head>
 <title>FRMTicketsestados</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BTF" property="*" />
 <% 
 String titulo = BTF.getAccion().toUpperCase() + " DE TICKETSESTADOS" ;
 BTF.setResponse(response);
 BTF.setRequest(request);
 BTF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BTF.setUsuarioact( session.getAttribute("usuario").toString() );
 BTF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BTF.ejecutarValidacion();
 %>
<form action="ticketsestadosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BTF.getAccion()%>" >
<input name="idticketestado" type="hidden" value="<%=BTF.getIdticketestado()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BTF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Estado: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="ticketestado" type="text" value="<%=BTF.getTicketestado()%>" class="campo" size="25" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Color: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="color_fondo" type="text" value="<%=BTF.getColor_fondo()%>" class="campo" size="20" maxlength="7"  ></td>
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

