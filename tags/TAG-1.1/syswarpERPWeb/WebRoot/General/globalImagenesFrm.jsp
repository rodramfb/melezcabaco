<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globalImagenes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Mar 10 10:51:29 ART 2008 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
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
<jsp:useBean id="BGIF"  class="ar.com.syswarp.web.ejb.BeanGlobalImagenesFrm"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" type="text/css" href="<%= pathskin %>">
 <script language="JavaScript" src="vs/forms/forms.js"></script>   
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BGIF" property="*" />
 <% 
 String titulo = BGIF.getAccion().toUpperCase() + " DE IMAGENES" ;
 BGIF.setResponse(response);
 BGIF.setRequest(request);
 BGIF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BGIF.setUsuarioact( session.getAttribute("usuario").toString() );
 BGIF.setIdempresa ( new BigDecimal( session.getAttribute("empresa").toString() )  );
 BGIF.ejecutarValidacion();
 %>
<form action="globalImagenesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BGIF.getAccion()%>" >
<input name="idimagen" type="hidden" value="<%=BGIF.getIdimagen()%>" >
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
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BGIF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="9%" height="30" class="fila-det-border">Descripci&oacute;n: (*) </td>
                <td colspan="2" class="fila-det-border"><input name="descripcion" type="text" value="<%=BGIF.getDescripcion()%>" class="campo" size="50" maxlength="50"  ></td> 
              </tr>
              <tr class="fila-det">
                <td width="9%" height="31" class="fila-det-border">Path: (*) </td>
                <td width="72%" class="fila-det-border"><input name="path" type="text" class="campo" value="<%=BGIF.getPath()%>" size="100" readonly></td>
                <td width="19%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abreVentana('uploadFile.jsp?soloImagen=true', 800, 450)" style="cursor:pointer"><img src="../imagenes/default/gnome_tango/devices/gnome-dev-wavelan.png" width="22" height="22" onClick="abreVentana('uploadFileBlob.jsp?soloImagen=true', 800, 450)" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

