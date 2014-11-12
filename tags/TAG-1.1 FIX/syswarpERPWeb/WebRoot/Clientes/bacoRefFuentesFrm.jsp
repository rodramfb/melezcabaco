<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoRefFuentes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jun 15 17:55:58 ART 2010 
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
<jsp:useBean id="BBRFF"  class="ar.com.syswarp.web.ejb.BeanBacoRefFuentesFrm"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script> 
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBRFF" property="*" />
 <% 
 String titulo = BBRFF.getAccion().toUpperCase() + " DE FUENTES DE REFERIDOS" ;
 BBRFF.setResponse(response);
 BBRFF.setRequest(request);
 BBRFF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBRFF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBRFF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRFF.ejecutarValidacion();
 %>
<form action="bacoRefFuentesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBRFF.getAccion()%>" >
<input name="idfuente" type="hidden" value="<%=BBRFF.getIdfuente()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BBRFF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="15%" height="30" class="fila-det-border">Fuente:  </td>
                <td width="85%" class="fila-det-border"><input name="fuente" type="text" value="<%=BBRFF.getFuente()%>" class="campo" size="50" maxlength="45"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="15%" height="35" class="fila-det-border">Cliente Asociado :  
                <input name="cliente" type="hidden" class="campo" id="cliente" size="10" maxlength="10" readonly ></td>
                <td width="85%" class="fila-det-border"><table width="106" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="60"><input name="idcliente" type="text" value="<%=BBRFF.getIdcliente()%>" class="campo" size="10" maxlength="10" readonly ></td>
                    <td width="22"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_clientes.jsp', 'clie', 700, 400)" style="cursor:pointer"> </td>
                    <td width="24"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="22" height="22" title="Reestablecer sin cliente." onClick="document.frm.idcliente.value = 0;" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td height="42" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><input name="validar" type="submit" value="Enviar" class="boton">               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

