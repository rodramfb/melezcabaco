<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: wkftransacciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 23 08:41:21 ACT 2009 
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
<jsp:useBean id="BWF"  class="ar.com.syswarp.web.ejb.BeanWkftransaccionesFrm"   scope="page"/>
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
 String titulo = BWF.getAccion().toUpperCase() + " DE Transacciones" ;
 BWF.setResponse(response);
 BWF.setRequest(request);
 BWF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BWF.setUsuarioact( session.getAttribute("usuario").toString() );
 BWF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BWF.ejecutarValidacion();
 %>
<form action="wkftransaccionesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BWF.getAccion()%>" >
<input name="idtransaccion" type="hidden" value="<%=BWF.getIdtransaccion()%>" >
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
                <td width="20%" class="fila-det-border">Transaccion: (*) </td>
                <td width="80%" class="fila-det-border"><input name="transaccion" type="text" value="<%=BWF.getTransaccion()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">Tipo transaccion: (*)  
				<input name="idtipotransaccion" type="hidden" id="idtipotransaccion" value="<%=BWF.getIdtipotransaccion()%>"></td>
                <td width="80%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="tipotransaccion" type="text" class="campo" id="tipotransaccion" value="<%=str.esNulo(BWF.getTipotransaccion())%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../General/lov_tipotransaccion.jsp', 'tipoclie', 800, 450)" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">Proxima transaccion:  
				<input name="idproximatransaccion" type="hidden" id="idproximatransaccion" value="<%=BWF.getIdproximatransaccion()%>"></td>
                <td width="80%" class="fila-det-border"><table width="23%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det-border">
                    <td width="61%" ><input name="proximatransaccion" type="text" class="campo" id="proximatransaccion" value="<%=str.esNulo(BWF.getProximatransaccion())%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../General/lov_proximotransaccion.jsp', 'tipoclie', 800, 450)" style="cursor:pointer"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">Descripcion:  </td>
                <td width="80%" class="fila-det-border">&nbsp;
                <textarea name="descripcion" cols="70" rows="6" class="campo"><%=BWF.getDescripcion()%></textarea></td>
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

