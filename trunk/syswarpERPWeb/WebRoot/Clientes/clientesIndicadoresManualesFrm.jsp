<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesIndicadoresManuales
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Apr 30 10:30:20 GMT-03:00 2010 
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
<jsp:useBean id="BCIMF"  class="ar.com.syswarp.web.ejb.BeanClientesIndicadoresManualesFrm"   scope="page"/>
<head>
 <title>Indicadores Manuales</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCIMF" property="*" />
 <% 
 String titulo = BCIMF.getAccion().toUpperCase() + " DE INDICADORES MANUALES" ; 
 Iterator it = null;
 BCIMF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCIMF.setResponse(response);
 BCIMF.setRequest(request);
 BCIMF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCIMF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCIMF.ejecutarValidacion();
 %>
<form action="clientesIndicadoresManualesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCIMF.getAccion()%>" >
<input name="idindicador" type="hidden" value="<%=BCIMF.getIdindicador()%>" >
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
                <td height="28" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCIMF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="19%" height="31" class="fila-det-border">&nbsp;Indicador: (*) </td>
                <td width="81%" class="fila-det-border"><input name="indicador" type="text" value="<%=BCIMF.getIndicador()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" height="33" class="fila-det-border">&nbsp;Tipo: (*) </td>
                <td width="81%" class="fila-det-border"><select name="idtipoindicador" class="campo" id="idtipoindicador">
                    <option value="-1" selected>Seleccionar</option>
                    <%  
																 it =  BCIMF.getListIndicadoresTipos().iterator();
																 while(it.hasNext()){
																	 String[] tipo = (String[])it.next(); %>
                    <option value="<%= tipo[0] %>" <%= BCIMF.getIdtipoindicador().intValue() == Integer.parseInt(tipo[0] )? "selected" : "" %>><%= tipo[1] %></option>
                    <%}  %>
                                    </select></td>
              </tr>
              <tr class="fila-det">
                <td width="19%" height="117" class="fila-det-border">&nbsp;Query Selecci&oacute;n :  </td>
                <td width="81%" class="fila-det-border"><textarea name="queryseleccion" cols="70" rows="6" class="campo"><%=BCIMF.getQueryseleccion()%></textarea></td>
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

