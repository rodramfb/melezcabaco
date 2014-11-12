<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: setupVariables
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jul 04 15:26:54 GMT-03:00 2006 
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
<jsp:useBean id="BSVF"  class="ar.com.syswarp.web.ejb.report.BeanSetupVariablesFrm"   scope="page"/>
<head>
 <title>FRMSetupVariables.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" type="text/css" href="imagenes/default/tnx.css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BSVF" property="*" />
 <% 
 String titulo = BSVF.getAccion().toUpperCase() + " DE VARIABLES DE SETUP" ;
 BSVF.setResponse(response);
 BSVF.setRequest(request);
 BSVF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BSVF.setUsuarioact( session.getAttribute("usuario").toString() );
 BSVF.ejecutarValidacion();
 %>
<form action="setupVariablesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BSVF.getAccion()%>" >
<input name="idvariable" type="hidden" value="<%=BSVF.getIdvariable()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BSVF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Variable: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="variable" type="text" value="<%=BSVF.getVariable()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Valor: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<textarea name="valor" cols="70" rows="6" class="campo"><%=BSVF.getValor()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Descripcion:  </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="descripcion" type="text" value="<%=BSVF.getDescripcion()%>" class="campo" size="100" maxlength="100"  ></td>
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

