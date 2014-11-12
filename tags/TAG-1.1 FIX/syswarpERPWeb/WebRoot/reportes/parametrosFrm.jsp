<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: parametros
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Jul 03 17:43:40 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="java.util.*" %>
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
<jsp:useBean id="BPF"  class="ar.com.syswarp.web.ejb.report.BeanParametrosFrm"   scope="page"/>
<head>
 <title>FRMParametros.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" type="text/css" href="imagenes/default/tnx.css">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPF" property="*" />
 <% 
 String titulo = BPF.getAccion().toUpperCase() + " DE PARAMETROS" ;
 BPF.setResponse(response);
 BPF.setRequest(request);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.ejecutarValidacion();
 Hashtable htDT = BPF.getHtDT();
 Enumeration enumDT = htDT.keys(); 
 Hashtable htTP = BPF.getHtTP();
 Enumeration enumTP = htTP.keys(); 
 
 %>
<form action="parametrosFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPF.getAccion()%>" >
<input name="idparametro" type="hidden" value="<%=BPF.getIdparametro()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BPF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Parametro: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="parametro" type="text" value="<%=BPF.getParametro()%>" class="campo" size="20" maxlength="20"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;Descripcion: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="descripcion" type="text" value="<%=BPF.getDescripcion()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Tipo Parametro: (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<select name="idtipoparametro" class="campo">
																																<option value="-1">Seleccionar</option>
																																&nbsp;
																																<% 
																															while(enumTP.hasMoreElements()){
																																String sel = "";
																																String key =  (String)enumTP.nextElement() ;
																																if( Long.parseLong(key)  == BPF.getIdtipoparametro().longValue() ) sel = "selected";
																																else sel = "";
																																
																															 %>
																																<option value="<%= key %>" <%= sel %> ><%= htTP.get(key) %></option>
																																<%  
																															}
																															%>
																															</select>
								</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Consulta:  </td>
                <td width="88%" class="fila-det-border">&nbsp;<textarea name="validacion_query" cols="70" rows="6" class="campo"><%=BPF.getValidacion_query()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Data Source : (*) </td>
                <td width="88%" class="fila-det-border">&nbsp;<select name="iddatasource" class="campo">
																																<option value="-1">Seleccionar</option>
																																&nbsp;
																																<% 
																															while(enumDT.hasMoreElements()){
																																String sel = "";
																																String key =  (String)enumDT.nextElement() ;
																																if( Long.parseLong(key)  == BPF.getIddatasource().longValue() ) sel = "selected";
																																else sel = "";
																																
																															 %>
																																<option value="<%= key %>" <%= sel %> ><%= htDT.get(key) %></option>
																																<%  
																															}
																															%>
																															</select>
								</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Obligatorio:  </td>
                <td width="88%" class="fila-det-border">&nbsp;<select name="obligatorio" class="campo">
																																 <option value="" >Seleccionar</option>
																																 <option value="s" <% if(BPF.getObligatorio().equalsIgnoreCase("t")) out.write("selected") ;%>>Si</option>
																																 <option value="n" <% if(BPF.getObligatorio().equalsIgnoreCase("f")) out.write("selected") ;%> >No</option>									 									 
																															 </select>
								</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Orden:  </td>
                <td width="88%" class="fila-det-border">&nbsp;<input name="orden" type="text" value="<%=BPF.getOrden()%>" class="campo" size="10" maxlength="2"  ></td>
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

