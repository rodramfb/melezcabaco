<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: pedidosCuotas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Sep 03 17:38:57 GMT-03:00 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
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
<jsp:useBean id="BPCF"  class="ar.com.syswarp.web.ejb.BeanPedidosCuotasFrm"   scope="page"/>
<head>
 <title>CUOTAS</title>
 <link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPCF" property="*" />
 <% 
 String titulo = BPCF.getAccion().toUpperCase() + " DE CUOTAS" ;
 BPCF.setResponse(response);
 BPCF.setRequest(request);
 BPCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPCF.ejecutarValidacion();
 %>
<form action="pedidosCuotasFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPCF.getAccion()%>" >
<input name="idcuota" type="hidden" value="<%=BPCF.getIdcuota()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BPCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="18%" class="fila-det-border">&nbsp;Cuotas: (*) </td>
                <td width="82%" class="fila-det-border"><input name="nrocuotas" type="text" value="<%=BPCF.getNrocuotas()%>" class="campo" size="10" maxlength="2"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="18%" height="54" class="fila-det-border">&nbsp;Observaciones: (*) </td>
                <td width="82%" class="fila-det-border"><textarea name="observaciones" cols="80" rows="2" class="campo"><%=BPCF.getObservaciones()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td height="37" class="fila-det-border">&nbsp;</td>
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

