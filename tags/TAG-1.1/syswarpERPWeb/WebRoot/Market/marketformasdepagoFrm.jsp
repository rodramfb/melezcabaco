<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: marketformasdepago
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Mar 11 13:29:00 ART 2008 
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
<jsp:useBean id="BMF"  class="ar.com.syswarp.web.ejb.BeanMarketformasdepagoFrm"   scope="page"/>
<head>
 <title>FRMMarketformasdepago.jsp</title>
  <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BMF" property="*" />
 <% 
 String titulo = BMF.getAccion().toUpperCase() + " DE market formas de pago" ;
 BMF.setResponse(response);
 BMF.setRequest(request);
 BMF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BMF.setUsuarioact( session.getAttribute("usuario").toString() );
 BMF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BMF.ejecutarValidacion();
 %>
<form action="marketformasdepagoFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BMF.getAccion()%>" >
<input name="idformapago" type="hidden" value="<%=BMF.getIdformapago()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BMF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Forma de pago: (*) </td>
                <td width="88%" class="fila-det-border"><input name="formapago" type="text" value="<%=BMF.getFormapago()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Leyenda: (*) </td>
                <td width="88%" class="fila-det-border"><textarea name="leyenda" cols="70" rows="6" class="campo"><%=BMF.getLeyenda()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Envio datos:  </td>
                <td width="88%" class="fila-det-border"><textarea name="enviodatos" cols="70" rows="6" class="campo"><%=BMF.getEnviodatos()%></textarea></td>
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

