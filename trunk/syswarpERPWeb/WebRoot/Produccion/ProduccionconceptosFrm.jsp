<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: Produccionconceptos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Sep 06 12:38:15 GMT-03:00 2006 
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
<jsp:useBean id="BPF"  class="ar.com.syswarp.web.ejb.BeanProduccionconceptosFrm"   scope="page"/>
<head>
 <title>FRMProduccionconceptos.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script></head>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPF" property="*" />
 <% 
 String titulo = BPF.getAccion().toUpperCase() + " DE CONCEPTOS" ;
 BPF.setResponse(response);
 BPF.setRequest(request);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPF.ejecutarValidacion();
 %>
<form action="ProduccionconceptosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPF.getAccion()%>" >
<input name="idconcepto" type="hidden" value="<%=BPF.getIdconcepto()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BPF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="13%" class="fila-det-border">Concepto: (*) </td>
                <td width="87%" class="fila-det-border"><input name="concepto" type="text" value="<%=BPF.getConcepto()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="13%" class="fila-det-border">Formula: (*) </td>
                <td width="87%" class="fila-det-border"><textarea name="formula" cols="70" rows="6" class="campo"><%=BPF.getFormula()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="13%" class="fila-det-border">&nbsp;Margen error: (*) </td>
                <td width="87%" class="fila-det-border"><input name="margen_error" type="text" value="<%=BPF.getMargen_error()%>" class="campo" size="9" maxlength="9"  ></td>
              </tr>
			  
			  
			  
			  <tr class="fila-det">
                <td width="13%" class="fila-det-border">&nbsp;Costo: (*) </td>
                <td width="87%" class="fila-det-border"><input name="costo" type="text" value="<%=BPF.getCosto()%>" class="campo" size="9" maxlength="9"  ></td>
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

