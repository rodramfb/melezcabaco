<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: Stockgrupos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 04 09:20:45 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*"%>
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
<jsp:useBean id="BSF"  class="ar.com.syswarp.web.ejb.BeanStockgruposFrm"   scope="page"/>
<head>
 <title>FRMStockgrupos.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script></head>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BSF" property="*" />
 <% 
 String titulo = BSF.getAccion().toUpperCase() + " DE Grupos" ;
 BSF.setResponse(response);
 BSF.setRequest(request);
 BSF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BSF.setUsuarioact( session.getAttribute("usuario").toString() );
 String codigo_fm    = str.esNulo( request.getParameter("codigo_fm") );
 String d_codigo_fm    = str.esNulo( request.getParameter("d_codigo_fm") );
 String codigo_gr_pa   = str.esNulo( request.getParameter("codigo_gr_pa") );
 String d_codigo_gr_pa    = str.esNulo( request.getParameter("d_codigo_gr_pa") );
 BSF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BSF.ejecutarValidacion();
 %>

<form action="StockgruposFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BSF.getAccion()%>" >
<input name="codigo_gr" type="hidden" value="<%=BSF.getCodigo_gr()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BSF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Descripcion: (*) </td>
                <td width="88%" class="fila-det-border"><input name="descrip_gr" type="text" value="<%=BSF.getDescrip_gr()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Familia:  </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="d_codigo_fm" type="text" class="campo" id="d_codigo_fm" value="<%= str.esNulo(BSF.getD_codigo_fm())%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Stock/lov_familia.jsp')" style="cursor:pointer"></td>
                    <input name="codigo_fm" type="hidden" id="codigo_fm" value="<%=BSF.getCodigo_fm()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Codigo gr padre:  </td>
                <td width="88%" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="d_codigo_gr_pa" type="text" class="campo" id="d_codigo_gr_pa" value="<%= str.esNulo(BSF.getD_codigo_gr_pa())%>" size="30" readonly></td>
                    <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Stock/lov_grupo2.jsp')" style="cursor:pointer"></td>
                    <input name="codigo_gr_pa" type="hidden" id="codigo_gr_pa" value="<%=BSF.getCodigo_gr_pa()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton" >               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

