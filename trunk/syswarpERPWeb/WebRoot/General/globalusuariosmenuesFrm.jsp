<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globalusuariosmenues
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 23 13:32:41 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
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
<jsp:useBean id="BGF"  class="ar.com.syswarp.web.ejb.BeanGlobalusuariosmenuesFrm"   scope="page"/>
<head>
 <title>FRMGlobalusuariosmenues.jsp</title>
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
 String titulo = BGF.getAccion().toUpperCase() + " DE Usuarios Menues" ;
 BGF.setResponse(response);
 BGF.setRequest(request);
 BGF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BGF.setUsuarioact( session.getAttribute("usuario").toString() );
 BGF.ejecutarValidacion();
 %>
<form action="globalusuariosmenuesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BGF.getAccion()%>" >

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
                <td width="12%" class="fila-det-border">Usuario: (*) </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="usuario" type="text" class="campo" id="usuario" value="<%=BGF.getUsuario()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" <% if (BGF.getAccion().equalsIgnoreCase("Alta")) {%> onClick="mostrarLOV('../General/lov_usuario.jsp')" style="cursor:pointer"<% } %>></td>
                    <input name="idusuario" type="hidden" id="idusuario" value="<%=BGF.getIdusuario()%>">
                  </tr>
                </table></td>
			  </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Menu: (*) </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="menu" type="text" class="campo" id="menu" value="<%=BGF.getMenu()%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" <% if (BGF.getAccion().equalsIgnoreCase("Alta")) {%> onClick="mostrarLOV('../General/lov_menu.jsp')" style="cursor:pointer"<% } %>></td>
                    <input name="idmenu" type="hidden" id="idmenu" value="<%=BGF.getIdmenu()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Nivel: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="nivel" type="text" value="<%=BGF.getNivel()%>" class="campo" size="18" maxlength="18"  ></td>
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

