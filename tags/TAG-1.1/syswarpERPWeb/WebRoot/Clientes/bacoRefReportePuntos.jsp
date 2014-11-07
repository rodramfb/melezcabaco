<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: bacoRefCtaCte
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Jul 02 12:13:03 ART 2010 
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
String titulo = "REPORTE DE PUNTAJE ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterBacoRefCtaCte   = null;
int totCol = 9; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BBRCCA"  class="ar.com.syswarp.web.ejb.BeanBacoRefReportePuntos"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BBRCCA" property="*" />
<%
 BBRCCA.setResponse(response);
 BBRCCA.setRequest(request);
 BBRCCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRCCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 function imprimirSeleccion(){
 
  var isChecked = false;
  var clientesToCollection = '';
  var obj = document.frm.sel;
   if(obj){
     if(obj.length) {
       for(var i = 0;i<obj.length;i++)  {
         if(obj[i].checked){
		   isChecked = true;
		   clientesToCollection += obj[i].value + '-';
		 }
       }
     }
     else{  
       isCheked = obj.checked  ;
	   clientesToCollection = obj[i].value ;
	 }
   }    

   if(isChecked)
     abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=referidos_ctacte&clientesToCollection=' + clientesToCollection, 'ctactereferidos', 750, 500); 
   else 
     alert('Seleccione al menos un registro a imprimir.');
 }
 
  function checkUnckeckAll(){
   var objTextTipo = document.getElementById("marca").firstChild;
   var check = true;
   if(objTextTipo.nodeValue == 'Todo') objTextTipo.nodeValue = 'Nada';
   else{
     objTextTipo.nodeValue = 'Todo';
     check=false;
   }
   var obj = document.frm.sel;
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
 
 </script>

</head>
<%
// titulos para las columnas
tituCol[0] = "Cliente";
tituCol[1] = "Razon";
tituCol[2] = "Puntos";
tituCol[3] = "Provincia";
tituCol[4] = "Localidad";
tituCol[5] = "Telefonos";
tituCol[6] = "Celular";
tituCol[7] = "Correo";
tituCol[8] = "idempresa";
java.util.List BacoRefCtaCte = new java.util.ArrayList();
BacoRefCtaCte= BBRCCA.getBacoRefCtaCteList();
iterBacoRefCtaCte = BacoRefCtaCte.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="bacoRefReportePuntos.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="38"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td height="3"  class="text-globales"><hr color="#FFFFFF"></td>
                </tr>				
                <tr >
                  <td width="11%" height="38"><table width="100%" border="0" cellspacing="2" cellpadding="0">
                    <tr class="text-globales">
                      <td height="29">Fecha Desde(*) </td>
                      <td><span class="fila-det-border">
                        <input name="fechadesde" type="text" class="cal-TextBox" id="fechadesde" onFocus="this.blur()" value="<%=BBRCCA.getFechadesde()%>" size="10" maxlength="12" readonly>
                      <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a> </span></td>
                      <td>Fecha Hasta(*) </td>
                      <td><span class="fila-det-border">
                        <input name="fechahasta" type="text" class="cal-TextBox" id="fechahasta" onFocus="this.blur()" value="<%=BBRCCA.getFechahasta()%>" size="10" maxlength="12" readonly>
                      <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_0', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_0', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_0');return false;"> <img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a> </span></td>
                    </tr>
                    <tr class="text-globales">
                      <td width="11%" height="34">Vendedor
                      <input name="idvendedor" type="hidden" value="<%=BBRCCA.getIdvendedor()%>" class="campo" size="10" maxlength="10"  ></td>
                      <td width="33%"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                        <tr >
                          <td width="51%"><input name="vendedor" type="text" class="campo" id="vendedor" value="<%=BBRCCA.getVendedor()%>" size="30" maxlength="10" readonly ></td>
                          <td width="49%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="18" height="18" onClick="abrirVentana('../Clientes/lov_vendedor.jsp', 'vende', 700, 400)" style="cursor:pointer"></td>
                          <td width="49%"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="18" height="18"  style="cursor:pointer" onClick="document.frm.idvendedor.value = -1 ; document.frm.vendedor.value = '' "></td>
                        </tr>
                      </table></td>
                      <td height="34">Cliente
                        <input name="idcliente" type="hidden" class="campo" id="idcliente" value="<%=BBRCCA.getIdcliente()%>" size="10" maxlength="10"  ></td>
                      <td><table width="29%" border="0" cellspacing="0" cellpadding="0">
                          <tr >
                            <td width="51%"><input name="cliente" type="text" class="campo" id="cliente" value="<%=BBRCCA.getCliente()%>" size="30" maxlength="10" readonly ></td>
                            <td width="49%"><span class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
														width="18" height="18"
														onClick="abrirVentana('../Clientes/lov_clientes.jsp', 'clientes', 800, 400)"
														style="cursor:pointer" ></span></td>
                            <td width="49%"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="18" height="18"  style="cursor:pointer" onClick="document.frm.idcliente.value = -1 ; document.frm.cliente.value = '' "></td>
                          </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td height="31">
                        <input name="consulta" type="submit" class="boton" id="consulta" value="Consultar" onClick="document.frm.accion.value = 'consulta'">                      </td>
                      <td class="text-globales"><div style="cursor:pointer" onClick="imprimirSeleccion()"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" border="0" style="cursor:pointer" > Imprimir seleccionados. </div></td>
                      <td class="text-globales">Total Registros </td>
                      <td class="text-globales"><%=BBRCCA.getTotalRegistros()%></td>
                    </tr>
                  </table></td>
              </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BBRCCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td width="3%" ><a href="#" id="marca">Todo</a></td>
    <td width="2%" >&nbsp;</td>
     <td width="5%" ><%=tituCol[0]%></td>
     <td width="21%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[2]%></div></td>
     <td width="37%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="19%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
  </tr>
   <%int r = 0;
   while(iterBacoRefCtaCte.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterBacoRefCtaCte.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><label>
       <div align="center">
         <input name="sel" type="checkbox" id="sel" value="<%=sCampos[0]%>">
         </div>
     </label></td>
     <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="15" height="15" border="0" style="cursor:pointer" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=referidos_ctacte&clientesToCollection=<%=sCampos[0]%>', 'ctactereferidos', 750, 500);"></div></td> 
      <td class="fila-det-border" ><%=sCampos[0]%></td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="right"><%=sCampos[2]%>&nbsp;</div></td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;- <%=sCampos[4]%></td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;<%=sCampos[6]%></td>
      <td class="fila-det-border" ><div align="center"><%=!Common.setNotNull(sCampos[7]).equals("") ? "<a href=\"mailto:" +sCampos[7].substring(0, sCampos[7].lastIndexOf("-") - 1 ) + "\">" +  
	  "<img src=\"../imagenes/default/gnome_tango/actions/gnome-stock-mail-fwd.png\"  width=\"20\" height=\"20\" border=\"0\" title=\"" + sCampos[7].substring(0, sCampos[7].lastIndexOf("-") - 1 ) + "\" ></a>"  : "" %>&nbsp;</div>
	  </td> 
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
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

