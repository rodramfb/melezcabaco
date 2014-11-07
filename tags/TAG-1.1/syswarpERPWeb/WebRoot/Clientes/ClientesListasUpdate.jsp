<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clienteslistasdeprecios
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Mar 18 17:14:05 ART 2008 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClienteslistasUpdateFrm"   scope="page"/>
<head>
 <title>FRMClienteslistasdeprecios.jsp</title>
 <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE Lista de Precios" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCF.ejecutarValidacion();
 %>
<form action="ClientesListasUpdate.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >


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
                <td width="12%" class="fila-det-border">Lista: (*) </td>
              <td width="88%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="lista" type="text" class="campo" id="lista" value="<%=BCF.getLista()%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22"  onClick="mostrarLOV('../Clientes/lov_lista.jsp')" style="cursor:pointer" ></td>
</tr>
</table><input name="idlista" type="hidden" id="idlista" value="<%=BCF.getIdlista()%>">
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Precio:  </td>
                <td width="88%" class="fila-det-border"><input name="precio" type="text" value="<%=BCF.getPrecio()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton"></td>
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

