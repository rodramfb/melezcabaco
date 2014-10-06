<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoRefTipoOperaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Jul 02 09:36:21 ART 2010 
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
<jsp:useBean id="BBRTOF"  class="ar.com.syswarp.web.ejb.BeanBacoRefTipoOperacionesFrm"   scope="page"/>
<head>
 <title>FRMBacoRefTipoOperaciones</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBRTOF" property="*" />
 <% 
 String titulo = BBRTOF.getAccion().toUpperCase() + " DE TIPO OPERACIONES" ;
 BBRTOF.setResponse(response);
 BBRTOF.setRequest(request);
 BBRTOF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBRTOF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBRTOF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRTOF.ejecutarValidacion();
 %>
<form action="bacoRefTipoOperacionesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBRTOF.getAccion()%>" >
<input name="idtipooperacion" type="hidden" value="<%=BBRTOF.getIdtipooperacion()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BBRTOF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" height="33" class="fila-det-border">&nbsp;Tipo: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="tipooperacion" type="text" value="<%=BBRTOF.getTipooperacion()%>" class="campo" size="15" maxlength="15"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" height="40" class="fila-det-border">&nbsp;Observaciones:  </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="observaciones" type="text" value="<%=BBRTOF.getObservaciones()%>" class="campo" size="50" maxlength="50"  ></td>
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

