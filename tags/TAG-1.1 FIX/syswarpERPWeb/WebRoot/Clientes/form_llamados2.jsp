<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
 
 
 %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.util.Iterator" %> 
<% 
 
try{
String color_fondo ="";
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;



Iterator iterPedidosEstados   = null;
int totCol = 18; // cantidad de columnas
String[] tituCol = new String[totCol];
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCCSF"  class="ar.com.syswarp.web.ejb.BeanBacoTmLLamados"   scope="page"/>
<head>
 <title>consultadepedidos.jsp</title>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
  <script language="JavaScript">
function mostrarLOVDETA(pagina) {
	frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=800,height=450,status=yes');
	if (frmLOV.opener == null) 
		frmLOV.opener = self;
}	
</script>


<script language="JavaScript">
function validar(){  
  if (document.frm.idcampania.value == 0){
     alert('No se puede dejar vacio la campañia');
     document.frm.idcampania.focus();
     return false;
  }
  

document.frm.submit();

return true;
}
</script>




</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCCSF" property="*" />
<%
 String titulo = "Consulta de Pedidos por Estado" ;
 BCCSF.setResponse(response);
 BCCSF.setRequest(request);
 BCCSF.ejecutarValidacion();
%>
<%
// titulos para las columnas
/*
tituCol[0] = "Codigo";
tituCol[1] = "Razon";
tituCol[2] = "Sucursal";
tituCol[3] = "Condicion";
tituCol[4] = "Vendedor";
tituCol[5] = "Expreso";
tituCol[6] = "Comision";
tituCol[7] = "Observaciones";
tituCol[8] = "Recargo 1";
tituCol[9] = "Recargo 2";
tituCol[10] = "Recargo 3";
tituCol[11] = "Recargo 4";
tituCol[12] = "Bonificacion 1";
tituCol[13] = "Bonificacion 2";
tituCol[14] = "Bonificacion 3";
tituCol[15] = "Lista";
tituCol[16] = "Moneda";
tituCol[17] = "Cotizacion";
*/
//java.util.List PedidosEstados = new java.util.ArrayList();
//PedidosEstados= BCCSF.getPedidosEstadosList();
//iterPedidosEstados = PedidosEstados.iterator();
%>
<form action="form_llamados2.jsp" method="post" name="frm">
<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr class="text-globales">

<td width="81%"><table width="99%"  border="0" cellspacing="0" cellpadding="0">
<tr>
<td><table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr class="text-globales">
<td width="8%" height="19" class="text-globales">&nbsp;</td>
<td width="40%" class="text-globales">&nbsp;</td>
</tr>
</table></td>
</tr>
</table>
</td>
           
            </tr>
       </table> 
            <table width="104%" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
            <td class="fila-det-border">&nbsp;</td>
            </tr>
              
              <tr class="fila-det">
                <td width="10%" class="fila-det-border">Campa&ntilde;a </td>
              <td width="90%" class="fila-det-border"><table width="70%" border="0">
                  <tr class="fila-det-border">
                  <td width="24%" ><input name="campania" type="text" class="campo" id="campania" value="<%=BCCSF.getCampania()%>" size="30" readonly></td>
                    <td width="3%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_campania.jsp')" style="cursor:pointer"></td>
					<input name="idcampania" type="hidden" id="idcampania" value="<%=BCCSF.getIdcampania()%>">
                    <td width="7%" >F.Inicio </td>
<td width="11%"><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fdesdeStr" value="<%=BCCSF.getFdesdeStr()%>" maxlength="12">
<a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','fdesdeStr','BTN_date_3');return false;"></a></td>
<td width="3%"><a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_3', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_3', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_3', 'img_Date_DOWN');showCalendar('frm','fdesdeStr','BTN_date_3');return false;"></a></td>
<td width="5%"><font class=datasheetField>F.Fin</font></td>
<td width="10%"><a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fhastaStr','BTN_date_4');return false;">
<input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fhastaStr" value="<%=BCCSF.getFhastaStr()%>" maxlength="12">
</a></td>
<td width="3%"><a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fhastaStr','BTN_date_4');return false;"></a></td>
<td width="34%"><input name="button" type="button" onClick="validar();" value="Agenda del dia"></td>

                  </tr>
              </table>			  </td>
              </tr>
<tr class="fila-det">
<td class="fila-det-border">Cliente</td>
<td class="fila-det-border"><table width="86%" border="0">
<tr class="fila-det-border">
<td width="24%" ><input name="cliente" type="text" class="campo" id="cliente" value="<%=BCCSF.getCliente()%>" size="30" readonly></td>
<td width="3%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_clientes2.jsp')" style="cursor:pointer"></td>
<input name="idcliente" type="hidden" id="idcliente" value="<%=BCCSF.getIdcliente()%>">
<td width="73%"><input name="socio" type="button" id="socio" onClick="validarsocio();" value="Recuperar socio"></td>

</tr>
</table></td>
</tr>
<tr class="fila-det">
<td class="fila-det-border"><font class=datasheetField>Otros</font></td>
<td class="fila-det-border"><table width="86%" border="0">
<tr class="fila-det-border">
<td width="8%" ><font class=datasheetField>Resultado</font></td>
<td width="25%"><input name="resultado" type="text" class="campo" id="resultado" value="<%=BCCSF.getResultado()%>" size="30" readonly></td>
<td width="3%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_resultado.jsp')" style="cursor:pointer"></td>
<td width="64%"><input name="socio" type="button" id="socio" onClick="validarsocio();" value="Recuperar socio"></td>
<input name="idresultado" type="hidden" id="idresultado" value="<%=BCCSF.getIdresultado()%>">

</tr>
</table></td>
</tr>
       </table>
     </td>
</tr>
</table>
	<table width="100%" border="0" align="left" cellpadding="1" cellspacing="1" cols=<%=totCol+2-4%> id=rsTable name="rsTable"  >
  <tr class="fila-encabezado">
         <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[11]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[12]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[13]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[14]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[15]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[16]%></td>
		 <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[17]%></td>
         <td width="3%"  onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">Detalle</td>   
    </tr>
   <%int r = 0;
   while(iterPedidosEstados.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidosEstados.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[3]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[4]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[5]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[6]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[7]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[8]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[9]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[10]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[11]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[12]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[13]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[14]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[15]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[16]%></td>
	  <td class="fila-det-border" >&nbsp;<%=sCampos[17]%></td>
      <td class="fila-det-border" ><img src="../imagenes/doc.gif" width="22" height="22" border="0" title="Detalle de Cabecera" onClick="mostrarLOVDETA('pedidos_detaDetalleAbm.jsp?idpedido_cabe=<%=sCampos[0]%>')" style="cursor:pointer" ></td>
    </tr>
<%
   }%>
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

