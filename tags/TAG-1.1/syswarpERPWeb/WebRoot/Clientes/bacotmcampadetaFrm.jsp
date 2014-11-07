<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacotmcampadeta
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Apr 03 15:29:07 GMT-03:00 2007 
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
<jsp:useBean id="BBF"  class="ar.com.syswarp.web.ejb.BeanBacotmcampadetaFrm"   scope="page"/>
<head>
 <title>FRMBacotmcampadeta.jsp</title>
 <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBF" property="*" />
 <% 
 
  String idcampacabe =  request.getParameter("idcampacabe");
  String campacabe =  request.getParameter("campacabe");
 if(idcampacabe!=null){
   session.setAttribute("idcampacabe",idcampacabe);
   session.setAttribute("campacabe",campacabe);
 } 
idcampacabe =  session.getAttribute("idcampacabe").toString();
campacabe     =  session.getAttribute("campacabe").toString();
String titulo = BBF.getAccion().toUpperCase() + " DE Campaña Detalle" ;

 BBF.setResponse(response);
 BBF.setRequest(request);
 BBF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BBF.ejecutarValidacion();
 %>
<form action="bacotmcampadetaFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBF.getAccion()%>" >
<input name="idcampadeta" type="hidden" value="<%=BBF.getIdcampadeta()%>" >
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
			  <input name="idcampacabe" type="hidden" id="idcampacabe" value= "<%=BBF.getIdcampacabe()%>">
			  <input name="campacabe" type="hidden" id="campacabe" value= "<%=BBF.getCampacabe()%>">
			  
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">codigo_st: (*) </td>
                <td width="78%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="codigo_st" type="text" class="campo" id="codigo_st" value="<%=BBF.getCodigo_st()%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_articulo.jsp', 700, 400)" style="cursor:pointer"></td>
<input name="descrip_st" type="hidden" id="descrip_st" value="<%=BBF.getDescrip_st()%>">
</tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Observaciones: (*) </td>
                <td width="78%" class="fila-det-border"><textarea name="observaciones" cols="70" rows="6" class="campo"><%=BBF.getObservaciones()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Stock_estimado: (*) </td>
                <td width="78%" class="fila-det-border"><input name="stock_estimado" type="text" value="<%=BBF.getStock_estimado()%>" class="campo" size="18" maxlength="18"  ></td>
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

