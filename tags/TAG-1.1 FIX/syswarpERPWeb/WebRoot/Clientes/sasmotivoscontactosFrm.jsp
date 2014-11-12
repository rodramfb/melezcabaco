<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: sasmotivoscontactos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri May 27 10:58:35 ART 2011 
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
<jsp:useBean id="BSF"  class="ar.com.syswarp.web.ejb.BeanSasmotivoscontactosFrm"   scope="page"/>
<head>
 <title>FRMSasmotivoscontactos</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<script>
function enviaTipo()
{
	var id= document.frm.idtipocontacto.options[document.frm.idtipocontacto.selectedIndex].value;
	var tipo = document.frm.idtipocontacto.options[document.frm.idtipocontacto.selectedIndex].text;
	document.frm.tipocontacto.value = tipo;
	document.frm.idtipocontacto.value = id;
	document.frm.submit();
}
</script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BSF" property="*" />
 <% 
 String titulo = BSF.getAccion().toUpperCase() + " DE SAS - MOTIVOS DE CONTACTOS" ;
 BSF.setResponse(response);
 BSF.setRequest(request);
 BSF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BSF.setUsuarioact( session.getAttribute("usuario").toString() );
 BSF.setIdempresa ( new BigDecimal( session.getAttribute("empresa").toString() )  ); 
 BSF.ejecutarValidacion();
 %>
<form action="sasmotivoscontactosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BSF.getAccion()%>" >
<input name="idmotivocontacto" type="hidden" value="<%=BSF.getIdmotivocontacto()%>" >
<input name="tipocontacto" type="hidden"  value="<%=BSF.getTipocontacto()%>">

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
                <td width="12%" class="fila-det-border">&nbsp;Tipo de contacto: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;
				<select name="idtipocontacto" id="idtipocontacto" onChange="enviaTipo()">
					<option value="-1">Seleccionar</option>
					<%
						Iterator itertipo = BSF.getListtipo().iterator();
						while(itertipo.hasNext())
						{
							String[] datostipo = (String[]) itertipo.next();
							%><option value="<%=datostipo[0]%>"<%=Integer.parseInt(datostipo[0]) == BSF.getIdtipocontacto().intValue()   ? "selected" : "" %>><%=datostipo[1]%></option><%
						}
					%>
				</select>
				</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Canal del contacto: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;
				<select name="idcanalcontacto" id="idcanalcontacto">
					<option value="-1">Seleccionar</option>
					<%
						Iterator itercanal = BSF.getListcanal().iterator();
						while(itercanal.hasNext())
						{
							String[] datoscanal = (String[]) itercanal.next();
							%><option value="<%=datoscanal[0]%>"<%=Integer.parseInt(datoscanal[0]) == BSF.getIdcanalcontacto().intValue()   ? "selected" : "" %>><%=datoscanal[1]%></option><%
						}
					%>
				</select>
				</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Motivo del contacto: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp; <input name="motivocontacto" type="text" value="<%=BSF.getMotivocontacto()%>" class="campo" size="50" maxlength="100"  ></td>
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

