<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacotmliquidacionresumen
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 02 15:34:16 ART 2011 
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
<jsp:useBean id="BBF"  class="ar.com.syswarp.web.ejb.BeanBacotmliquidacionresumenFrm"   scope="page"/>
<head>
 <title>FRMBacotmliquidacionresumen</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBF" property="*" />
 <% 
 String titulo = BBF.getAccion().toUpperCase() + " DE BACOTMLIQUIDACIONRESUMEN" ;
 BBF.setResponse(response);
 BBF.setRequest(request);
 BBF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBF.ejecutarValidacion();
 %>
<form action="bacotmliquidacionresumenFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBF.getAccion()%>" >
<input name="idliquidacion" type="hidden" value="<%=BBF.getIdliquidacion()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BBF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;anio: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="anio" type="text" value="<%=BBF.getAnio()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;mes: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="mes" type="text" value="<%=BBF.getMes()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;usuario: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="usuario" type="text" value="<%=BBF.getUsuario()%>" class="campo" size="20" maxlength="20"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;comision_vc: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="comision_vc" type="text" value="<%=BBF.getComision_vc()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;total_vc: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="total_vc" type="text" value="<%=BBF.getTotal_vc()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;comision_vv: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="comision_vv" type="text" value="<%=BBF.getComision_vv()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;total_vv: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="total_vv" type="text" value="<%=BBF.getTotal_vv()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;comision_ve: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="comision_ve" type="text" value="<%=BBF.getComision_ve()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;total_ve: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="total_ve" type="text" value="<%=BBF.getTotal_ve()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;comision_vc_jf: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="comision_vc_jf" type="text" value="<%=BBF.getComision_vc_jf()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;total_vc_jf: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="total_vc_jf" type="text" value="<%=BBF.getTotal_vc_jf()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;comision_vv_jf: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="comision_vv_jf" type="text" value="<%=BBF.getComision_vv_jf()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;total_vv_jf: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="total_vv_jf" type="text" value="<%=BBF.getTotal_vv_jf()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;idempresa: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="idempresa" type="text" value="<%=BBF.getIdempresa()%>" class="campo" size="100" maxlength="100"  ></td>
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

