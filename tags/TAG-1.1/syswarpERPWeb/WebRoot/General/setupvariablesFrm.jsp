<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: setupvariables
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jan 25 11:06:26 GMT-03:00 2007 
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
<jsp:useBean id="BSF"  class="ar.com.syswarp.web.ejb.BeanSetupvariablesFrm"   scope="page"/>
<head>
 <title>FRMSetupvariables.jsp</title>
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
 <jsp:setProperty name="BSF" property="*" />
 <% 
 String titulo = BSF.getAccion().toUpperCase() + " DE Setup variables" ;
 BSF.setResponse(response);
 BSF.setRequest(request);
 BSF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BSF.setUsuarioact( session.getAttribute("usuario").toString() );
 BSF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BSF.ejecutarValidacion();
 %>
<form action="setupvariablesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BSF.getAccion()%>" >
<input name="variable" type="hidden" value="<%=BSF.getVariable()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BSF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Valor: (*) </td>
                <td width="88%" class="fila-det-border"><textarea name="valor" cols="50" rows="6" class="campo"><%=BSF.getValor()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Descripcion:  </td>
                <td width="88%" class="fila-det-border"><input name="descripcion" type="text" value="<%=BSF.getDescripcion()%>" class="campo" size="100" maxlength="100" readonly="si"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Validador:  </td>
                <td width="88%" class="fila-det-border"><input name="validador" type="text" value="<%=BSF.getValidador()%>" class="campo" size="-1" maxlength="-1"  readonly="si" ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Sistema:  </td>
                <td width="88%" class="fila-det-border"><input name="sistema" type="text" value="<%=BSF.getSistema()%>" class="campo" size="1" maxlength="1"  readonly="si"></td>
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

