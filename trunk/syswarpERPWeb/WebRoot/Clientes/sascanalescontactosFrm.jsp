<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: sascanalescontactos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri May 27 10:10:39 ART 2011 
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
<jsp:useBean id="BSF"  class="ar.com.syswarp.web.ejb.BeanSascanalescontactosFrm"   scope="page"/>
<head>
 <title>FRMSascanalescontactos</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BSF" property="*" />
 <% 
 String titulo = BSF.getAccion().toUpperCase() + " DE SAS - CANALES DE CONTACTOS" ;
 BSF.setResponse(response);
 BSF.setRequest(request);
 BSF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BSF.setUsuarioact( session.getAttribute("usuario").toString() );
 BSF.setIdempresa ( new BigDecimal( session.getAttribute("empresa").toString() )  );
 BSF.ejecutarValidacion();
 %>
<form action="sascanalescontactosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BSF.getAccion()%>" >
<input name="idcanalcontacto" type="hidden" value="<%=BSF.getIdcanalcontacto()%>" >
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
                <td width="12%" class="fila-det-border">&nbsp;Tipo de contacto : (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<select name="idtipocontacto" id="idtipocontacto">
					<option value="-1">Seleccionar</option>
				<%
					Iterator itertipo = BSF.getListatipos().iterator();
					while(itertipo.hasNext())
					{
						String[] datostipo = (String[]) itertipo.next();
						%><option value="<%=datostipo[0]%>" <%= Integer.parseInt(datostipo[0]) == BSF.
					getIdtipocontacto().intValue()   ? "selected" : "" %> ><%=datostipo[1]%></option><%
					}
				%>
				</select>
				</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Canal de contacto: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="canalcontacto" type="text" value="<%=BSF.getCanalcontacto()%>" class="campo" size="50" maxlength="100"  ></td>
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

