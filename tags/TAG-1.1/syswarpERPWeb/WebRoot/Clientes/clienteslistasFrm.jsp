<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clienteslistas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Dec 11 15:17:53 GMT-03:00 2006 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClienteslistasFrm"   scope="page"/>
<head>
 <title>FRMClienteslistas.jsp</title>
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
 String titulo = BCF.getAccion().toUpperCase() + " DE Lista de Precios" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCF.ejecutarValidacion();
 %>
<form action="clienteslistasFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idlista" type="hidden" value="<%=BCF.getIdlista()%>" >
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
                <td width="12%" class="fila-det-border">Descipcion: (*) </td>
                <td width="88%" class="fila-det-border"><input name="descri_lis" type="text" value="<%=BCF.getDescri_lis()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Moneda:  (*)  </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                        <input name="moneda" type="text" class="campo" id="moneda" value="<%=BCF.getMoneda()%>" size="30" readonly></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_moneda.jsp')" style="cursor:pointer"></td>
                      <input name="idmoneda" type="hidden" id="idmoneda" value="<%=BCF.getIdmoneda()%>">
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Con iva :  </td>
                <td width="88%" class="fila-det-border">
                  <select name="coniva_lis" id="coniva_lis"  >
                    <option value="S" <%= BCF.getConiva_lis().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                    <option value="N" <%= BCF.getConiva_lis().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Con cuota :  </td>
                <td width="88%" class="fila-det-border">
                  <select name="concuo_lis" id="concuo_lis"  >
                    <option value="S" <%= BCF.getConcuo_lis().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                    <option value="N" <%= BCF.getConcuo_lis().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                  </select></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Decimales:  </td>
                <td width="88%" class="fila-det-border"><input name="decima_lis" type="text" value="<%=BCF.getDecima_lis()%>" class="campo" size="18" maxlength="18"  ></td>
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

