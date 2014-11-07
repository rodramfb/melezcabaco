<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: grafico
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Jul 03 15:35:37 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
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
<jsp:useBean id="BGF"  class="ar.com.syswarp.web.ejb.report.BeanGraficoFrm"   scope="page"/>
<head>
 <title>FRMGrafico.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" type="text/css" href="imagenes/default/tnx.css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BGF" property="*" />
 <% 
 String titulo = BGF.getAccion().toUpperCase() + " DE GRAFICO" ;
 BGF.setResponse(response);
 BGF.setRequest(request);
 BGF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BGF.setUsuarioact( session.getAttribute("usuario").toString() );
 BGF.ejecutarValidacion();
 Hashtable htDT = BGF.getHtDT();
 Enumeration enumDT = htDT.keys(); 
 Hashtable htTG = BGF.getHtTG();
 Enumeration enumTG = htTG.keys(); 
 
 %>
<form action="graficoFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BGF.getAccion()%>" >
<input name="idgrafico" type="hidden" value="<%=BGF.getIdgrafico()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BGF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Gr&aacute;fico: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="grafico" type="text" value="<%=BGF.getGrafico()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Consulta:  </td>
                <td width="88%" class="fila-det-border">&nbsp;<textarea name="query_consulta" cols="70" rows="6" class="campo"><%=BGF.getQuery_consulta()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Tipo grafico: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<select name="idtipografico" class="campo">
																																<option value="-1">Seleccionar</option>&nbsp;
																															<% 
																															while(enumTG.hasMoreElements()){
																																String sel = "";
																																String key =  (String)enumTG.nextElement() ;
																																if( Long.parseLong(key)  == BGF.getIdtipografico().longValue() ) sel = "selected";
																																else sel = "";
																																
																															 %>
																															 <option value="<%= key %>" <%= sel %> ><%= htTG.get(key) %></option>
																															<%  
																															}
																															%>
																															</select>							  
							  </td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Data source: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<select name="iddatasource" class="campo">
																																<option value="-1">Seleccionar</option>&nbsp;
																															<% 
																															while(enumDT.hasMoreElements()){
																																String sel = "";
																																String key =  (String)enumDT.nextElement() ;
																																if( Long.parseLong(key)  == BGF.getIddatasource().longValue() ) sel = "selected";
																																else sel = "";
																																
																															 %>
																															 <option value="<%= key %>" <%= sel %> ><%= htDT.get(key) %></option>
																															<%  
																															}
																															%>
																															</select>								
									</td>
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

