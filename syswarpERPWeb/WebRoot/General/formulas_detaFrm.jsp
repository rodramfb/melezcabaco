<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: formulas_deta
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Mar 12 10:04:47 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
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
<jsp:useBean id="BFF"  class="ar.com.syswarp.web.ejb.BeanFormulas_detaFrm"   scope="page"/>
<head>
 <title>FRMFormulas_deta.jsp</title>
 <meta http-equiv="description" content="mypage">
  
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>

</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BFF" property="*" />
 <% 
 String titulo = BFF.getAccion().toUpperCase() + " DE Formula Detalle" ;
 
 
 
  String idformulacabe =  request.getParameter("idformulacabe");
  //String cliente =  request.getParameter("cliente");
 if(idformulacabe!=null){
   session.setAttribute("idformulacabe",idformulacabe);
   //session.setAttribute("cliente",cliente);
 } 
idformulacabe =  session.getAttribute("idformulacabe").toString();
//cliente     =  session.getAttribute("cliente").toString();

 BFF.setResponse(response);
 BFF.setRequest(request);
 BFF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BFF.setUsuarioact( session.getAttribute("usuario").toString() );
 BFF.ejecutarValidacion();
 %>
<form action="formulas_detaFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BFF.getAccion()%>" >
<input name="idformula" type="hidden" value="<%=BFF.getIdformula()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BFF" property="mensaje"/>&nbsp;</td>
              </tr>
			  
			  <input name="formulacabe" type="hidden" id="formulacabe" value="<%=BFF.getFormulacabe()%>" size="30" readonly>
			  <input name="idformulacabe" type="hidden" id="idformulacabe" value="<%=BFF.getIdformulacabe()%>">
			  <tr class="fila-det">
                <td width="12%" class="fila-det-border">Precedencia: (*) </td>
                <td width="88%" class="fila-det-border"><input name="precedencia" type="text" class="campo" id="precedencia" value="<%=BFF.getPrecedencia()%>" size="18" maxlength="18"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Formula desc: (*) </td>
                <td width="88%" class="fila-det-border"><input name="formuladesc" type="text" class="campo" value="<%=BFF.getFormuladesc()%>" size="70" maxlength="500"></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Formula logica: (*) </td>
                <td width="88%" class="fila-det-border"><input name="formula_logica" type="text" value="<%=BFF.getFormula_logica()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Formula calculo:  </td>
                <td width="88%" class="fila-det-border"><input name="formula_calculo" type="text" value="<%=BFF.getFormula_calculo()%>" class="campo" size="100" maxlength="100"  ></td>
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

