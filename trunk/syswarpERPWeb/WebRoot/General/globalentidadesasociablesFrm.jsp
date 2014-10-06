<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globalentidadesasociables
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue May 06 14:29:25 CEST 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.BigDecimal" %>
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
<jsp:useBean id="BGF"  class="ar.com.syswarp.web.ejb.BeanGlobalentidadesasociablesFrm"   scope="page"/>
<head>
 <title>FRMGlobalentidadesasociables.jsp</title>
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
 <jsp:setProperty name="BGF" property="*" />
 <% 
 String titulo = BGF.getAccion().toUpperCase() + " DE ENTIDADES ASOCIABLES" ;
 BGF.setResponse(response);
 BGF.setRequest(request);
 BGF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BGF.setUsuarioact( session.getAttribute("usuario").toString() );
 BGF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BGF.ejecutarValidacion();
 %>
<form action="globalentidadesasociablesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BGF.getAccion()%>" >
<input name="identidadesasociables" type="hidden" value="<%=BGF.getIdentidadesasociables()%>" >
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
                <td width="17%" class="fila-det-border">Entidad asociable: (*) </td>
                <td width="83%" class="fila-det-border"><input name="entidadasociable" type="text" value="<%=BGF.getEntidadasociable()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Descripcion: (*) </td>
                <td width="83%" class="fila-det-border"><input name="descripcion" type="text" value="<%=BGF.getDescripcion()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Campo pk: (*) </td>
                <td width="83%" class="fila-det-border"><input name="campopk" type="text" value="<%=BGF.getCampopk()%>" class="campo" size="30" maxlength="30"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Query pk: (*) </td>
                <td class="fila-det-border"><textarea name="querypk" cols="70" rows="6" class="campo"><%=BGF.getQuerypk()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Query Grilla:(*) </td>
                <td width="83%" class="fila-det-border"><textarea name="querygrilla" cols="70" rows="6" class="campo" id="querygrilla"><%=BGF.getQuerygrilla()%></textarea></td>
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

