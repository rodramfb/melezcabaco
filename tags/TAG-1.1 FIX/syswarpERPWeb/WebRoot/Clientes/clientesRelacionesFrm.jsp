<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesRelaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Feb 15 15:02:21 GMT-03:00 2010 
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
<jsp:useBean id="BCRF"  class="ar.com.syswarp.web.ejb.BeanClientesRelacionesFrm"   scope="page"/>
<head>
 <title>FRMClientesRelaciones</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCRF" property="*" />
 <% 
 String titulo = BCRF.getAccion().toUpperCase() + " DE CLIENTES RELACIONADOS " ;
 BCRF.setResponse(response);
 BCRF.setRequest(request);
 BCRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCRF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCRF.ejecutarValidacion();
 %>
<form action="clientesRelacionesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCRF.getAccion()%>" >
<input name="idrelacion" type="hidden" value="<%=BCRF.getIdrelacion()%>" >
<input name="idclienteroot" type="hidden" value="<%=BCRF.getIdclienteroot()%>" >
<input name="razonroot" type="hidden" value="<%=BCRF.getRazonroot()%>" > 
   <table width="90%" height="158"  border="0" align="center" cellpadding="0" cellspacing="0">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td height="34">&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" height="154" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCRF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="18%" height="45" class="fila-det-border">&nbsp;Cliente principal : (*) 
                <input name="idclienteroot" type="hidden" value="<%=BCRF.getIdclienteroot()%>" class="campo" size="20" maxlength="100"  ></td>
                <td width="82%" class="fila-det-border"><%=BCRF.getIdclienteroot()%> -<%=BCRF.getRazonroot()%></td>
              </tr>
              <tr class="fila-det">
                <td width="18%" height="48" class="fila-det-border">&nbsp;Cliente Asociado : (*) </td> 
                <td width="82%" class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="16%"><input name="idclientebranch" type="text" value="<%=BCRF.getIdclientebranch()%>" class="campo" size="20" maxlength="100" readonly ></td>
                      <td width="35%"><input name="razonbranch" type="text" class="campo" id="razonbranch" value="<%=BCRF.getRazonbranch()%>" size="50" maxlength="50" readonly ></td>
                      <td width="49%"><img src="../imagenes/default/gnome_tango/actions/gtk-find.png" width="22" height="22" onClick="abrirVentana('lov_clientes.jsp', 'branch', 750, 450);"></td>
                    </tr>
                  </table></td>
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

