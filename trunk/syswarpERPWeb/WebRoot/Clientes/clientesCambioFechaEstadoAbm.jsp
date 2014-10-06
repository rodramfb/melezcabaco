<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesCambioFechaEstado
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 06 10:44:16 ART 2011 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Cambiar fecha de estado Activo";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesCambioFechaEstado   = null;
int totCol = 5; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCCFEA"  class="ar.com.syswarp.web.ejb.BeanClientesCambioFechaEstadoAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCCFEA" property="*" />
<%
 BCCFEA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCCFEA.setUsuarioact( session.getAttribute("usuario").toString() );
 BCCFEA.setResponse(response);
 BCCFEA.setRequest(request);
 BCCFEA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 
 <script>
 function checkUnckeckAll(){
   var objTextTipo = document.getElementById("marca").firstChild;
   var check = true;
   if(objTextTipo.nodeValue == 'Todos') objTextTipo.nodeValue = 'Ninguno';
   else{
     objTextTipo.nodeValue = 'Todos';
     check=false;
   }
   var obj = document.frm.idestadoclienteVector; 
   if(obj){
     if(obj.length) {
       for(var i = 0;i<obj.length;i++)  {
         obj[i].checked = check;
       }
     }
     else  
       obj.checked = check;
   }    
 }
 
 window.onload = function() { 

  document.getElementById('marca').onclick =  checkUnckeckAll;
  
 }
 
 function enviarUpd(){
 
   if(confirm('Confirma fecha para los registros seleccionados?')) { 
     document.frm.actualizar.value = 'actualizar';
	 document.frm.submit();
   } 
 
 }
 
 </script>
 
</head>
<%
// titulos para las columnas
tituCol[0] = "idestadocliente";
tituCol[1] = "Cliente";
tituCol[2] = "Razón";
tituCol[3] = "Fecha";
tituCol[4] = "totalposterior";
java.util.List ClientesCambioFechaEstado = new java.util.ArrayList();
ClientesCambioFechaEstado= BCCFEA.getClientesCambioFechaEstadoList();
iterClientesCambioFechaEstado = ClientesCambioFechaEstado.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>

<form action="clientesCambioFechaEstadoAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="94%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="12%" height="26" class="text-globales">Fecha Desde(*) </td>
                          <td width="16%"><span class="fila-det-border">
                            <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechadesde" value="<%=BCCFEA.getFechadesde()%>" maxlength="12">
                          <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_4');return false;"> <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></span></td>
                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="11%" height="19"><input name="ir" type="submit" class="boton" id="ir" value="Buscar     "></td>
                                         <td width="42%">&nbsp;Total de registros:&nbsp;<%=BCCFEA.getTotalRegistros()%></td>
                                         <td width="9%" >Visualizar:</td>
                                         <td width="17%">
                                            <select name="limit" >
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BCCFEA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BCCFEA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BCCFEA.getTotalRegistros()%>">Todos</option>
                                            </select>                                          </td>
                                         <td width="6%">&nbsp;P&aacute;gina:</td>
                                         <td width="15%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BCCFEA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BCCFEA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                            </select>                                          </td>
                                      </tr>
                                  </table>                                 </td>
                              </tr>
                          </table>                        </td>
                     </tr>
                        <tr  height="3px" >
                          <td colspan="3" bgcolor="#FFFFFF"  height="3px" ></td>
                        </tr>
                        <tr>
                          <td height="26" class="text-globales">Nueva Fecha: </td>
                          <td><span class="fila-det-border">
                            <input name="fechadesdeNew" type="text" class="cal-TextBox" id="fechadesdeNew" onFocus="this.blur()" value="<%=BCCFEA.getFechadesdeNew()%>" size="12" maxlength="12" readonly>
                          <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_0', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_0', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fechadesdeNew','BTN_date_0');return false;"> <img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></span></td>
                          <td><input name="btnupd" type="button" class="boton" id="btnupd" value="Actualizar" onClick="enviarUpd();"></td>
                        </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCCFEA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td width="13%" ><div align="center" ><a href="#" id="marca">Todos</a></div></td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="62%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
    </tr>
   <%int r = 0;
   while(iterClientesCambioFechaEstado.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterClientesCambioFechaEstado.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center">
        <%
		 if(Common.setNotNull(sCampos[4]).equalsIgnoreCase("0")){ %>
        <input name="idestadoclienteVector" type="checkbox" id="idestadoclienteVector" value="<%=sCampos[0]%>">
        <%}else{%>
        <img src="../imagenes/default/gnome_tango/emblems/emblem-important.png" width="18" height="18" title="Existen  <%= sCampos[4] %> estados con fecha posterior a la ingresada, para este cliente.Informe a sistemas">
        <%}%>
      </div></td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[3]), "JSDateToStr")%>&nbsp;</td>
    </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="actualizar" value="" type="hidden">
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

