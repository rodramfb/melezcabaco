<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoRefSubEstados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Mar 13 15:00:55 ART 2012 
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
<jsp:useBean id="BBRSEF"  class="ar.com.syswarp.web.ejb.BeanBacoRefSubEstadosFrm"   scope="page"/>
<head>
 <title>FRMBacoRefSubEstados</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBRSEF" property="*" />
 <% 
 String titulo = BBRSEF.getAccion().toUpperCase() + " DE SUBESTADO PREPROSPECTOS PARA " +  BBRSEF.getRefestado()  ;
 BBRSEF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRSEF.setResponse(response);
 BBRSEF.setRequest(request);
 BBRSEF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBRSEF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBRSEF.ejecutarValidacion();
 %>
<form action="bacoRefSubEstadosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBRSEF.getAccion()%>" >
<input name="idrefsubestado" type="hidden" value="<%=BBRSEF.getIdrefsubestado()%>" > 
<input name="refestado" type="hidden" value="<%=BBRSEF.getRefestado()%>" >
   <span class="fila-det-border">
   <input name="idrefestado" type="hidden" value="<%=BBRSEF.getIdrefestado()%>" class="campo" size="100" maxlength="100"  >
   </span>
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
                <td height="30" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BBRSEF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" height="34" class="fila-det-border">&nbsp;Sub Estado(*) :  </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="refsubestado" type="text" value="<%=BBRSEF.getRefsubestado()%>" class="campo" size="50" maxlength="50"  ></td>
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

