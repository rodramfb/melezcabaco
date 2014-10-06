<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesMovCliMotivosNc
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Oct 04 11:49:25 ART 2012 
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
<jsp:useBean id="BCMCMNF"  class="ar.com.syswarp.web.ejb.BeanClientesMovCliMotivosNcFrm"   scope="page"/>
<head>
 <title>FRMClientesMovCliMotivosNc</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCMCMNF" property="*" />
 <% 
 String titulo = BCMCMNF.getAccion().toUpperCase() + " DE MOTIVOS DE NOTA DE CREDITO" ;
 BCMCMNF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCMCMNF.setResponse(response);
 BCMCMNF.setRequest(request);
 BCMCMNF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCMCMNF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCMCMNF.ejecutarValidacion();
 %>
<form action="clientesMovCliMotivosNcFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCMCMNF.getAccion()%>" >
<input name="idmotivonc" type="hidden" value="<%=BCMCMNF.getIdmotivonc()%>" >
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
                <td height="27" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCMCMNF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="20%" height="34" class="fila-det-border">&nbsp;Motivo: (*) </td>
                <td width="80%" class="fila-det-border"><input name="motivonc" type="text" value="<%=BCMCMNF.getMotivonc()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" height="37" class="fila-det-border">&nbsp;Afecta Stock : (*) </td>
                <td width="80%" class="fila-det-border"><select name="afectastock" class="campo" id="afectastock" >
				    <option value="">Seleccionar</option>
				    <option value="S" <%= Common.setNotNull(BCMCMNF.getAfectastock()).equalsIgnoreCase("S") ? "selected" : ""%>>SI</option>
				    <option value="N" <%= Common.setNotNull(BCMCMNF.getAfectastock()).equalsIgnoreCase("N") ? "selected" : ""%>>NO</option>
                  </select>
                </td>
              </tr>
              <tr class="fila-det">
                <td height="46" class="fila-det-border">&nbsp;</td>
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

