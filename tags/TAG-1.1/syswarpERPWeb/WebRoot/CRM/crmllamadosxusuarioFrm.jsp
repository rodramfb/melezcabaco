<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/*  
   Formulario de carga para la entidad: crmllamados
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jun 19 11:15:59 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.math.*" %> 
<%@ page import="java.util.*" %>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();




%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanCrmllamadosxusuarioFrm"   scope="page"/>
<head>
 <title>FRMCrmllamados.jsp</title>
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
 <jsp:setProperty name="BCF" property="*" />
 <% 
   String idindividuos =  request.getParameter("idindividuos");
  String razon_nombre =  request.getParameter("razon_nombre");
 if(idindividuos!=null){
   session.setAttribute("idindividuos",idindividuos);
   session.setAttribute("razon_nombre",razon_nombre);
 } 
String titulo    =  "Alta de LLamadas de: " + razon_nombre ; 
idindividuos     =  session.getAttribute("idindividuos").toString();
razon_nombre     =  session.getAttribute("razon_nombre").toString();
BCF.setIdindividuos(idindividuos); 
 
 
 //String titulo = BCF.getAccion().toUpperCase() + " DE Gestion de llamados" ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCF.setIdusuario(new BigDecimal( session.getAttribute("idusuario").toString() )) ;
 BCF.ejecutarValidacion();
 Iterator iter;
 %>
<form action="crmllamadosxusuarioFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="idllamado" type="hidden" value="<%=BCF.getIdllamado()%>" >
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
                <td width="26%" class="fila-det-border">Tipo llamada: (*) </td>
                <td width="74%" class="fila-det-border"><select name="idtipollamada" class="campo">
                  <option value="-1" >Seleccionar</option>
                  <%  
				    iter = BCF.getListTipoLlamados().iterator();
				    while(iter.hasNext()){
					  String [] datos = (String []) iter.next();
					  String sel = "";
					  if(BCF.getIdtipollamada() != null && BCF.getIdtipollamada().toString().equals(datos[0]))
					    sel = "selected";
					%>
                  <option value="<%= datos[0] %>" <%= sel %>><%= datos[1] %></option>
                  <%
				    }  %>
                </select></td>
              </tr>
              <!--tr class="fila-det">
                <td width="26%" class="fila-det-border">Usuario: (*) </td>
              <td width="74%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="usuario" type="text" class="campo" id="usuario" value="<%=str.esNulo(BCF.getUsuario())%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_usuario.jsp', 'usuario', 700, 400)" style="cursor:pointer"></td>

</tr>
</table></td>
              </tr-->
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Familiar:  </td>
              <td width="74%" class="fila-det-border"><table width="23%" border="0">
<tr class="fila-det-border">
<td width="61%" ><input name="nombre" type="text" class="campo" id="nombre" value="<%=str.esNulo(BCF.getNombre())%>" size="30" readonly></td>
<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../CRM/lov_familiar.jsp?idindividuos=<%= BCF.getIdindividuos() %>', 'familiar', 700, 400)" style="cursor:pointer"></td>
<input name="idfamiliar" type="hidden" id="idfamiliar" value="<%=BCF.getIdfamiliar()%>">
</tr>
</table></td>
<input name="idindividuos" type="hidden" id="idindividuos" value="<%=BCF.getIdindividuos()%>" size="30" readonly>
<input name="razon_nombre" type="hidden" id="razon_nombre" value="<%=BCF.getRazon_nombre()%>">	
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Fecha llamada: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechallamadaStr" value="<%=BCF.getFechallamadaStr()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fechallamadaStr','BTN_date_6');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_6" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border"> Fecha rellamada </td>
                <td colspan="2" class="fila-det-border" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecharellamada" value="<%=BCF.getFecharellamada()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fecharellamada','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Resultado Llamado </td>
                <td colspan="2" class="fila-det-border" ><select name="idresultadollamada" class="campo">
                  <option value="-1" >Seleccionar</option>
                  <%  
				    iter = BCF.getListResultadoLlamada().iterator();
				    while(iter.hasNext()){
					  String [] datos = (String []) iter.next();
					  String sel = "";
					  if(BCF.getIdresultadollamada() != null && BCF.getIdresultadollamada().toString().equals(datos[0]))
					    sel = "selected";
					%>
                  <option value="<%= datos[0] %>" <%= sel %>><%= datos[1] %></option>
                  <%
				    }  %>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td width="26%" class="fila-det-border">Obseravaciones:  </td>
                <td width="74%" class="fila-det-border"><textarea name="obseravaciones" cols="70" rows="6" class="campo"><%=str.esNulo(BCF.getObseravaciones())%></textarea></td>
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

