<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesZonasDepositos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Sep 02 17:40:20 GMT-03:00 2008 
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
<jsp:useBean id="BCZDF"  class="ar.com.syswarp.web.ejb.BeanClientesZonasDepositosFrm"   scope="page"/>
<head>
 <title>DISTRIBUIDORES - DEPOSITOS</title>
 <link rel="stylesheet" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<script type="text/JavaScript">
<!--
function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}
//-->
</script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCZDF" property="*" />
 <% 
 String titulo =  "ASOCIACION DE DEPOSITOS POR DISTRIBUIDOR: "  + BCZDF.getZona();
 BCZDF.setResponse(response);
 BCZDF.setRequest(request);
 BCZDF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCZDF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCZDF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCZDF.ejecutarValidacion();
 %>
<form action="clientesZonasDepositosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCZDF.getAccion()%>" >
<input name="idzona" type="hidden" value="<%=BCZDF.getIdzona()%>" >
   <input name="zona" type="hidden" id="zona" value="<%=BCZDF.getZona()%>" > 
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
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BCZDF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="17%" class="fila-det-border">Dep&oacute;sitos: (*) </td>
                <td width="23%" class="fila-det-border"><select name="codigo_dt" size="15" multiple id="codigo_dt" class="campo">
									<%
										Iterator iter = BCZDF.getListDepositosZona().iterator();  
										while(iter.hasNext()){ 
										  String[] datos = (String[])iter.next();
									 %>
										<option value="<%= datos[0] %>" <%= !datos[2].equals("0") ?  "selected":  "" %>><%= datos[1] %></option> 
									<% 
									  } %>  									
                  </select>                </td>
                <td width="60%" class="fila-det-border"><table width="100%" height="0" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det">
                    <td height="24" >Para seleccionar multiples dep&oacute;sitos </td>
                  </tr>
                  <tr class="fila-det">
                    <td > presione [Ctrl + Click] o [Shift + Click] .</td>
                  </tr>
                  <tr class="fila-det">
                    <td >&nbsp;</td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td height="41" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Enviar" class="boton"></td>
                <td class="fila-det-border"><input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
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

