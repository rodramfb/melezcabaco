<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: proveedoCondicio
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 05 11:18:15 GMT-03:00 2006 
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
<jsp:useBean id="BPCF"  class="ar.com.syswarp.web.ejb.BeanProveedoCondicioFrm"   scope="page"/>
<head>
 <title>FRMProveedoCondicio.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPCF" property="*" />
 <% 
 String titulo = BPCF.getAccion().toUpperCase() + " DE Condicion de Pago" ;
 BPCF.setResponse(response);
 BPCF.setRequest(request);
 BPCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ) ); 
 BPCF.ejecutarValidacion();
 %>
<form action="proveedoCondicioFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPCF.getAccion()%>" >
<input name="idcondicionpago" type="hidden" value="<%=BPCF.getIdcondicionpago()%>" >
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
                
            <td width="17%" class="fila-det-border">&nbsp;Condicion de pago: (*) 
            </td>
                <td width="83%" class="fila-det-border">&nbsp;<input name="condicionpago" type="text" value="<%=BPCF.getCondicionpago()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                
            <td width="17%" class="fila-det-border">&nbsp;Cantidad de dias: (*) 
            </td>
                <td width="83%" class="fila-det-border">&nbsp;<input name="cantidaddias" type="text" value="<%=BPCF.getCantidaddias()%>" class="campo" size="18" maxlength="18"  ></td>
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

