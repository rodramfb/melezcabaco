<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesclientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Dec 11 15:30:21 GMT-03:00 2006 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanReportesProveedoresCtaCte"   scope="page"/>
<head>
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
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.ejecutarValidacion();
 %>
<form action="ReportesCtaCte.jsp" method="post" name="frm">
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
			  <tr class="fila-det">
                <td width="11%" class="fila-det-border">Proveedor: (*)  </td>
                <td width="89%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="proveedor" type="text" class="campo" id="tipo" value="<%=BCF.getProveedor()%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Proveedores/lov_proveedores.jsp')" style="cursor:pointer"></td>
                      <input name="idproveedor" type="hidden" id="idproveedor" value="<%=BCF.getIdproveedor()%>">            
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="11%" class="fila-det-border">Tipo: (*) </td>
                <td width="89%" class="fila-det-border"> <select name="idtipo" id="idtipo"  >
                  <option value="P" <%= BCF.getIdtipo().equalsIgnoreCase("P") ? "selected" : "" %> >P</option>
                  <option value="H" <%= BCF.getIdtipo().equalsIgnoreCase("H") ? "selected" : "" %> >H</option>
                </select> </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton">               </td>
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

