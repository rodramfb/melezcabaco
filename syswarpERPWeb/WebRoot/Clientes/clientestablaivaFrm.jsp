<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientestablaiva
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Nov 14 15:07:13 GMT-03:00 2006 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientestablaivaFrm"   scope="page"/>
<head>
 <title>FRMClientestablaiva.jsp</title>
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
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE Tipos de Iva" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCF.ejecutarValidacion();
 %>
<form action="clientestablaivaFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idtipoiva" type="hidden" value="<%=BCF.getIdtipoiva()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Tipo iva: (*) </td>
                <td width="88%" class="fila-det-border"><input name="tipoiva" type="text" value="<%=BCF.getTipoiva()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Porcentaje 1: (*) </td>
                <td width="88%" class="fila-det-border"><input name="porcent1" type="text" value="<%=BCF.getPorcent1()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Descrimina: (*) </td>
                <td width="88%" class="fila-det-border">
                  <select name="descrimina" id="descrimina"  >
                    <option value="S" <%= BCF.getDescrimina().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                    <option value="N" <%= BCF.getDescrimina().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Desglosa: (*) </td>
                <td width="88%" class="fila-det-border"><select name="desglosa" id="desglosa"  >
                  <option value="S" <%= BCF.getDesglosa().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                  <option value="N" <%= BCF.getDesglosa().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Porcentaje 2: (*) </td>
                <td width="88%" class="fila-det-border"><input name="porcent2" type="text" value="<%=BCF.getPorcent2()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Letra: (*) </td>
                <td width="88%" class="fila-det-border"><input name="letra" type="text" value="<%=BCF.getLetra()%>" class="campo" size="3" maxlength="3"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Cta Promo:  </td>
                <td width="88%" class="fila-det-border"><input name="ctapromo" type="text" value="<%=BCF.getCtapromo()%>" class="campo" size="18" maxlength="18"  ></td>
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

