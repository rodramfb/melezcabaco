<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: wkfprocesoseventos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Jul 24 08:30:58 ACT 2009 
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
<jsp:useBean id="BWF"  class="ar.com.syswarp.web.ejb.BeanWkfprocesoseventosFrm"   scope="page"/>
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
 String titulo = BWF.getAccion().toUpperCase() + " DE Procesos Eventos" ;
 BWF.setResponse(response);
 BWF.setRequest(request);
 BWF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BWF.setUsuarioact( session.getAttribute("usuario").toString() );
 BWF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BWF.ejecutarValidacion();
 %>
<form action="wkfprocesoseventosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BWF.getAccion()%>" >
<input name="idprocesoevento" type="hidden" value="<%=BWF.getIdprocesoevento()%>" >
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
                <td width="23%" class="fila-det-border">Proceso: (*)
			    <input name="idproceso" type="hidden" id="idproceso" value="<%=BWF.getIdproceso()%>"></td>
                <td width="77%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="proceso" type="text" class="campo" id="proceso" value="<%=str.esNulo(BWF.getProceso())%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../General/lov_proceso.jsp', 'tipoclie', 800, 450)" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="23%" class="fila-det-border">Evento: (*) 
				  <input name="idevento" type="hidden" id="idevento" value="<%=BWF.getIdevento()%>"></td>
                <td width="77%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="evento" type="text" class="campo" id="evento" value="<%=str.esNulo(BWF.getEvento())%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../General/lov_evento.jsp', 'tipoclie', 800, 450)" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="23%" class="fila-det-border">Descripcion: (*) </td>
                <td width="77%" class="fila-det-border"><input name="descripcion" type="text" value="<%=BWF.getDescripcion()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="23%" class="fila-det-border">Proceso negocio next:  
			      <input name="idprocesonegocionext" type="hidden" id="idprocesonegocionext" value="<%=BWF.getIdprocesonegocionext()%>"></td>
                <td width="77%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="procesonegocionext" type="text" class="campo" id="procesonegocionext" value="<%=str.esNulo(BWF.getProcesonegocionext())%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../General/lov_procesoevento.jsp', 'tipoclie', 800, 450)" style="cursor:pointer"></td>
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

