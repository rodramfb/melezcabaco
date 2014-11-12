<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vPedidosEstado
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Apr 16 08:42:23 GYT 2009 
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
String titulo = "PRECONFORMACION - CONSULTA DE PEDIDOS POR ESTADO";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVPedidosEstado   = null;
int totCol = 11; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVPEA"  class="ar.com.syswarp.web.ejb.BeanVPedidosEstadoPreconform"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVPEA" property="*" />
<%
 BVPEA.setResponse(response);
 BVPEA.setRequest(request);
 BVPEA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVPEA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script>
 
 
 
 
 
 
   function callDetalle(idpedido, idcliente, cliente, tipopedido){
     var pagina = 'pedidosHistoricoClienteDetalle.jsp?idpedido_cabe=' + idpedido + '&idcliente=' + idcliente + '&cliente=' + cliente + '&tipopedido=' + tipopedido;
     abrirVentana(pagina, 'detalle', 800, 450);
   }

   function cambiarEstado( idpedido ){

		 document.frm.accion.value = 'cambiarestado';  
		 document.frm.idpedido.value = idpedido;
     document.frm.submit();

   }

 </script>

</head>
<%
// titulos para las columnas
/*

				+ "SELECT idpedido_cabe,idestado,estado,idcliente,razon, tipopedido, "
				+ "       origenpedido, fechapedido, fechaalt, usuarioalt, idempresa "

*/
tituCol[0] = "Pedido";
tituCol[1] = "idestado";
tituCol[2] = "Estado";
tituCol[3] = "idcliente";
tituCol[4] = "Cliente";
tituCol[5] = "Tipo";
tituCol[6] = "Origen";
tituCol[7] = "Fecha";
tituCol[8] = "Prioridad";
tituCol[9] = "F.Carga";
tituCol[10] = "U.Carga";
java.util.List VPedidosEstado = new java.util.ArrayList();
VPedidosEstado= BVPEA.getVPedidosEstadoList();
iterVPedidosEstado = VPedidosEstado.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vPedidosEstadoPreconform.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="28" colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="2%" height="38">&nbsp;</td>
                   <td width="98%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="19%">
                           <input name="ocurrencia" type="text" value="<%=BVPEA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="75%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="33%" height="19">Total de registros:&nbsp;<%=BVPEA.getTotalRegistros()%></td>
                                          <td width="15%" >Visualizar:</td>
                                          <td width="14%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BVPEA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BVPEA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                             </select>                                          </td>
                                          <td width="10%">&nbsp;P&aacute;gina:</td>
                                          <td width="15%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BVPEA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BVPEA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select>                                          </td>
                                          <td width="13%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>                                 </td>
                              </tr>
                           </table>                        </td>
                     </tr>
                         <tr>
                           <td height="26" class="text-globales">Estado</td>
                           <td>
                           <select name="idestado" id="idestado"  class="campo">
                             <option value="-1" >Seleccionar</option>
                             <% 
                              Iterator iter = BVPEA.getEstadosList().iterator();
                              while(iter.hasNext()){
                                String[] datos = (String[]) iter.next();
                              %>
                             <option value="<%=datos[0]%>"  <%= datos[0].equals(BVPEA.getIdestado().toString() ) ?  "selected" : "" %> ><%=datos[1]%></option>
                             <%
                              }%>
                           </select>
                           </td>
                           <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                             <tr class="text-globales">
                               <td width="12%" height="19"><span class="fila-det-border">Fecha Desde </span></td>
                               <td width="21%"><table width="86%" border="0">
                                 <tr class="fila-det-border">
                                   <td width="32%"><input name="fdesde" type="text" class="cal-TextBox"
													id="fdesde" onFocus="this.blur()"
													value="<%=BVPEA.getFdesde() %>" size="12"
													maxlength="12" readonly>
                                   </td>
                                   <td width="68%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fdesde','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
                                 </tr>
                               </table></td>
                               <td width="13%" ><span class="fila-det-border">Fecha Hasta </span></td>
                               <td width="21%"><table width="51%" border="0">
                                 <tr class="fila-det-border">
                                   <td width="28%"><input name="fhasta" type="text" class="cal-TextBox"
													id="fhasta" onFocus="this.blur()"
													value="<%=BVPEA.getFhasta() %>" size="12"
													maxlength="12" readonly>
                                   </td>
                                   <td width="14%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fhasta','BTN_date_7');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_7" width="22" height="17" border="0"
														align="absmiddle"> </a> </td>
                                   <td width="58%">&nbsp;</td>
                                 </tr>
                               </table></td>
                               <td width="6%">&nbsp;</td>
                               <td width="13%">&nbsp;</td>
                               <td width="14%" class="text-globales">&nbsp;</td>
                             </tr>
                           </table></td>
                         </tr>
                  </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVPEA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[7]%></div></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[9]%></div></td>
  </tr>
   <%int r = 0;
   while(iterVPedidosEstado.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVPedidosEstado.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/gtk-refresh.png" width="22" height="22" style="cursor:pointer" title="Actualizar Estado del Pedido Número: <%=sCampos[0]%>" onClick="cambiarEstado(<%=sCampos[0]%>);"></td> 
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/mimetypes/x-office-spreadsheet.png" width="22" height="22" style="cursor:pointer" onClick="callDetalle('<%=sCampos[0]%>', '<%=sCampos[3]%>', '<%=sCampos[4]%>', '<%=sCampos[5]%>')" title="Consultar Detalle del Pedido"></div></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[7]), "JSTsToStr")%></div></td>
      <td class="fila-det-border" ><%=sCampos[8]%></td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp; </td>
      <td class="fila-det-border" ><%=sCampos[3]%>-<%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><%= Common.setNotNull(sCampos[6]).equals("") ? "SAS" : sCampos[6] %>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[10]%></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[9]), "JSTsToStrWithHM")%></div></td>
   </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="idpedido" value="" type="hidden">
   <input name="tipopedido" type="hidden" id="tipopedido" value="<%= BVPEA.getTipopedido() %>">
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

