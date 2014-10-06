<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoRefCatalogo
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jun 15 15:54:26 ART 2010 
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
<jsp:useBean id="BBRCF"  class="ar.com.syswarp.web.ejb.BeanBacoRefCatalogoFrm"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
  <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BBRCF" property="*" />
 <% 
 String titulo = BBRCF.getAccion().toUpperCase() + " DE CATALOGO - REFERIDOS" ;
 BBRCF.setResponse(response);
 BBRCF.setRequest(request);
 BBRCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBRCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBRCF.ejecutarValidacion();
 %>
<form action="bacoRefCatalogoFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BBRCF.getAccion()%>" >
<input name="idcatalogo" type="hidden" value="<%=BBRCF.getIdcatalogo()%>" >
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
                <td class="fila-det-border"><jsp:getProperty name="BBRCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Art&iacute;culo: </td>
                <td width="88%" class="fila-det-border"><table width="200" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><input name="codigo_st" type="text" class="campo" id="codigo_st" value="<%=BBRCF.getCodigo_st()%>" size="15" maxlength="15" readonly ></td>
                    <td><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_articulo.jsp', 'arti', 700, 400)" style="cursor:pointer"> </td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Descripci&oacute;n: </td>
                <td width="88%" class="fila-det-border"><input name="descrip_st" type="text" class="campo" id="descrip_st" value="<%=BBRCF.getDescrip_st()%>" size="100" maxlength="100" readonly ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Puntaje: (*) </td>
                <td width="88%" class="fila-det-border"><input name="puntaje" type="text" value="<%=BBRCF.getPuntaje()%>" class="campo" size="10" maxlength="10" style="text-align:right"  ></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Categor&iacute;a:(*)</td>
                <td class="fila-det-border">
				<select name="idcatalogocategoria" id="idcatalogocategoria" class="campo" style="width:200px" >
                  <option value="-1" >Seleccionar</option>
                  <% 
					  Iterator iter = BBRCF.getListCatalogoCategoria().iterator();
					  while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
                  <option value="<%= datos[0] %>" <%= datos[0].equals( BBRCF.getIdcatalogocategoria().toString()) ? "selected" : "" %> lang="<%= datos[2]%>"><%= datos[1] %></option>
                  <%  
					  }%>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Comprometido: (*) </td>
                <td width="88%" class="fila-det-border"><input name="comprometido" type="text" value="<%=BBRCF.getComprometido()%>" class="campo" size="10" maxlength="10" style="text-align:right"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Utilizado: (*) </td>
                <td width="88%" class="fila-det-border"><input name="utilizado" type="text" value="<%=BBRCF.getUtilizado()%>" class="campo" size="10" maxlength="10" style="text-align:right"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Fecha desde: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechadesde" value="<%=BBRCF.getFechadesde()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_7');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">Fecha hasta: (*) </td>
                <td colspan="2" class="fila-det-border" >
                  <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechahasta" value="<%=BBRCF.getFechahasta()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_8', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_8', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_8', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_8');return false;">
                  <img align="absmiddle" border="0" name="BTN_date_8" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>                </td>
              </tr>
              <tr class="fila-det">
                <td width="12%" class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border" >&nbsp;</td>
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

