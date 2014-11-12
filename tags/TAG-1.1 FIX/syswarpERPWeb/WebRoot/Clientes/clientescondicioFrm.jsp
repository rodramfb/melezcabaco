<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientescondicio
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Nov 14 15:31:19 GMT-03:00 2006 
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanClientescondicioFrm"   scope="page"/>
<head>
 <title>FRMClientescondicio.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<script language="JavaScript" src="scripts/forms.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE Condiciones de Venta" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCF.ejecutarValidacion();
 %>
<form action="clientescondicioFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idcondicion" type="hidden" value="<%=BCF.getIdcondicion()%>" >
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
                <td height="30" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="23%" height="30" class="fila-det-border">Condicion: (*) </td>
                <td width="77%" class="fila-det-border"><input name="condicion" type="text" value="<%=BCF.getCondicion()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="23%" height="30" class="fila-det-border">Cantidad Dias(*) </td>
                <td width="77%" class="fila-det-border"><input name="cant_dias" type="text" value="<%=BCF.getCant_dias()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="23%" height="30" class="fila-det-border">Cuotas: (*) </td>
                <td width="77%" class="fila-det-border"><input name="cuotas" type="text" value="<%=BCF.getCuotas()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="23%" height="30" class="fila-det-border">Lapso: (*) </td>
                <td width="77%" class="fila-det-border"><input name="lapso" type="text" value="<%=BCF.getLapso()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="23%" height="30" class="fila-det-border">Fac cred: (*) </td>
                <td width="77%" class="fila-det-border"> <select name="fac_cred" id="fac_cred"  >
                  <option value="S" <%= BCF.getFac_cred().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                  <option value="N" <%= BCF.getFac_cred().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td height="30" class="fila-det-border">Cta. Neto Clientes </td>
                <td class="fila-det-border"><table width="33%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="7%"><input name="ctanetoclie" type="text" class="campo" id="ctanetoclie" value="<%=BCF. getCtanetoclie()%>" size="10" maxlength="18"  ></td>
    <td width="12%"><input name="ctanetoclieDescripcion" type="text" class="campo" id="ctanetoclieDescripcion" value="<%=BCF. getCtanetoclieDescripcion()%>" size="30" maxlength="18"  ></td>
    <td width="81%"><img src="../imagenes/default/audit.gif" width="21" height="17" onClick="abreVentana('lov_contableInfiPlan.jsp?campos=ctanetoclie|ctanetoclieDescripcion', 700, 450);" style="cursor:pointer"> </td>
  </tr>
</table></td>
              </tr>
              <tr class="fila-det">
                <td height="30" class="fila-det-border">&nbsp;</td>
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

