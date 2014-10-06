<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: campos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jul 04 15:46:55 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
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
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.report.BeanCamposFrm"   scope="page"/>
<head>
 <title>FRMCampos.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" type="text/css" href="imagenes/default/tnx.css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo = BCF.getAccion().toUpperCase() + " DE CAMPOS" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.ejecutarValidacion();
 Hashtable htTablas = BCF.getHtTablas();
 Enumeration enumTablas = htTablas.keys();
 %>
<form action="camposFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idcampo" type="hidden" value="<%=BCF.getIdcampo()%>" >
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
                <td width="12%" class="fila-det-border">&nbsp;Campo: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="campo" type="text" value="<%=BCF.getCampo()%>" class="campo" size="20" maxlength="20"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Tabla: (*) </td>
                <td width="88%" class="fila-det-border"
								  >&nbsp;<select name="idtabla" class="campo">
								   <option value="0">Seleccionar</option>&nbsp;
									<% 
									String sel = "";
									while(enumTablas.hasMoreElements()){
										String key =  (String)enumTablas.nextElement() ;
										if( Long.parseLong(key) == BCF.getIdtabla().longValue() ) sel = "selected";
										else sel = "";
					
									 %>
									 <option value="<%= key %>" <%= sel %> ><%= htTablas.get(key) %></option>
									<%  
									}
									%>
									</select>
								</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Titulo: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="titulo" type="text" value="<%=BCF.getTitulo()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Orden: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="orden" type="text" value="<%=BCF.getOrden()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Clase:  </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="clase_css" type="text" value="<%=BCF.getClase_css()%>" class="campo" size="50" maxlength="50"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Longitud:  </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="lenght_col" type="text" value="<%=BCF.getLenght_col()%>" class="campo" size="20" maxlength="20"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Comentario:  </td>
                <td width="88%" class="fila-det-border">&nbsp;<textarea name="comentario" cols="70" rows="6" class="campo"><%=BCF.getComentario()%></textarea></td>
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

