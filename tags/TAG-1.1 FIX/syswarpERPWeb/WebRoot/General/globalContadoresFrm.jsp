<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: globalContadores
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jan 24 11:42:37 GMT-03:00 2007 
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
<jsp:useBean id="BGCF"  class="ar.com.syswarp.web.ejb.BeanGlobalContadoresFrm"   scope="page"/>
<head>
 <title>FRMGlobalContadores.jsp</title>
  <title><MMString:LoadString id="insertbar/formsHidden" /></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script></head>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BGCF" property="*" />
 <% 
 String titulo = BGCF.getAccion().toUpperCase() + " DE Contadores" ;
 BGCF.setResponse(response);
 BGCF.setRequest(request);
 BGCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BGCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BGCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BGCF.ejecutarValidacion();
 %>
<form action="globalContadoresFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BGCF.getAccion()%>" >
<input name="idcontador" type="hidden" value="<%=BGCF.getIdcontador()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BGCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Contador: (*) </td>
                <td width="88%" class="fila-det-border"><input name="contador" type="text" value="<%=BGCF.getContador()%>" class="campo" size="30" maxlength="30"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Valor: (*) </td>
                <td width="88%" class="fila-det-border"><input name="valor" type="text" value="<%=BGCF.getValor()%>" class="campo" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Descripcion:  </td>
                <td width="88%" class="fila-det-border"><textarea name="descripcion" cols="70" rows="6" class="campo"><%=BGCF.getDescripcion()%></textarea></td>
              </tr>
			  <tr class="fila-det">
                <td width="12%" class="fila-det-border">Nro Sucursal </td>
                <td width="88%" class="fila-det-border"><input name="nrosucursal" type="text" class="campo" id="nrosucursal" value="<%=BGCF.getNrosucursal()%>" size="18" maxlength="18"  ></td>
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

