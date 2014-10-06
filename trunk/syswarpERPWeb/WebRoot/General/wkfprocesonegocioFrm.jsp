<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: wkfprocesonegocio
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 23 10:29:08 ACT 2009 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="ar.com.syswarp.api.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="java.util.Iterator" %> 
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
<jsp:useBean id="BWF"  class="ar.com.syswarp.web.ejb.BeanWkfprocesonegocioFrm"   scope="page"/>
<head>
   <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 		 <link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
		<script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BWF" property="*" />
 <% 
 String titulo = BWF.getAccion().toUpperCase() + " DE Procesos Negocios" ;
 BWF.setResponse(response);
 BWF.setRequest(request);
 BWF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BWF.setUsuarioact( session.getAttribute("usuario").toString() );
 BWF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BWF.ejecutarValidacion();
 %>
<form action="wkfprocesonegocioFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BWF.getAccion()%>" >
<input name="idprocesonegocio" type="hidden" value="<%=BWF.getIdprocesonegocio()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BWF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Proceso Negocio: (*) </td>
                <td width="78%" class="fila-det-border"><input name="procesonegocio" type="text" value="<%=BWF.getProcesonegocio()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Proceso negocio next:  
				  <input name="idprocesonegocionext" type="hidden" id="idprocesonegocionext" value="<%=BWF.getIdprocesonegocionext()%>"></td>
                <td width="78%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="procesonegocionext" type="text" class="campo" id="procesonegocionext" value="<%=str.esNulo(BWF.getProcesonegocionext())%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../General/lov_procesonegocionext.jsp', 'tipoclie', 800, 450)" style="cursor:pointer"></td>
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

