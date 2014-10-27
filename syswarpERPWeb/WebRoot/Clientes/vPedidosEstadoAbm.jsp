<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
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
String titulo = "CONSULTA DE PEDIDOS POR ESTADO";
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
<jsp:useBean id="BVPEA"  class="ar.com.syswarp.web.ejb.BeanVPedidosEstadoAbm"   scope="page"/>
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


	function getAudit(idpedido, idestado){
    if(idestado == 4 || idestado == 5){
      var pagina = 'pedidosCambioEstadosLogAudit.jsp?idpedido=' + idpedido + '&tipopedido=N'; 
		  frmLOV = open(pagina, 'audit','toolbar=no,menubar=no,scrollbars=yes,resizable=no,width=600,height=200,status=no,location=no'); 
		  if (frmLOV.opener == null) frmLOV.opener = self;
			frmLOV.focus();
			return frmLOV;
    }
    else{
      alert('No existe auditoría para pedido: ' + idpedido); 
      return null; 
    }
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
<form action="vPedidosEstadoAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="28"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="98%" height="38">
                     <table width="100%" border="0" cellpadding="0" cellspacing="2">
                        <tr class="text-globales">
                          <td width="15%" height="26" >Total de registros:&nbsp;</td>
                          <td width="17%"  ><%=BVPEA.getTotalRegistros() + ""%></td>
                          <td width="22%">Visualizar:</td>
                          <td width="12%"><select name="limit" >
                            <%for(i=15; i<= 150 ; i+=15){%>
                            <%if(i==BVPEA.getLimit()){%>
                            <option value="<%=i%>" selected="selected"><%=i%></option>
                            <%}else{%>
                            <option value="<%=i%>"><%=i%></option>
                            <%}
                                                      if( i >= BVPEA.getTotalRegistros() ) break;
                                                    %>
                            <%}%>
                          </select></td>
                          <td width="17%">P&aacute;gina:</td>
                          <td width="12%"><select name="paginaSeleccion" >
                            <%for(i=1; i<= BVPEA.getTotalPaginas(); i++){%>
                            <%if ( i==BVPEA.getPaginaSeleccion() ){%>
                            <option value="<%=i%>" selected="selected"><%=i%></option>
                            <%}else{%>
                            <option value="<%=i%>"><%=i%></option>
                            <%}%>
                            <%}%>
                          </select></td>
                          <td width="5%"><input name="ir" type="submit" class="boton" id="ir" value="  &gt;&gt;  " /></td>
                       </tr>
                        <tr class="text-globales">
                          <td height="26" >Estado</td>
                          <td>
                           <select name="idestado" id="idestado"  class="campo" style="width:110px">
                             <option value="-1" >Seleccionar</option>
                             <% 
                              Iterator iter = BVPEA.getEstadosList().iterator();
                              while(iter.hasNext()){
                                String[] datos = (String[]) iter.next();
                              %>
                             <option value="<%=datos[0]%>"  <%= datos[0].equals(BVPEA.getIdestado().toString() ) ?  "selected" : "" %> ><%=datos[1]%></option>
                             <%
                              }%>
                          </select>                           </td>
                          <td> Fecha Desde: </td>
                          <td>
                          <table width="70%" border="0" cellpadding="0" cellspacing="0">
                            <tr  >
                              <td width="32%"><input name="fdesde" type="text" class="cal-TextBox" 
													id="fdesde" onFocus="this.blur()"
													value="<%=BVPEA.getFdesde() %>" size="12"
													maxlength="12" readonly="readonly" />                              </td>
                              <td width="68%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','fdesde','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle" id="BTN_date_6" /> </a> </td>
                            </tr>
                          </table></td>
                          <td> Fecha Hasta: </td>
                          <td><table width="51%" border="0" cellpadding="0" cellspacing="0">
                            <tr class="fila-det-border">
                              <td width="28%"><input name="fhasta" type="text" class="cal-TextBox"
													id="fhasta" onFocus="this.blur()"
													value="<%=BVPEA.getFhasta() %>" size="12"
													maxlength="12" readonly="readonly" />                              </td>
                              <td width="14%"><a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
													onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
													onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fhasta','BTN_date_7');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_7" width="22" height="17" border="0"
														align="absmiddle" id="BTN_date_7" /> </a> </td>
                              <td width="58%">&nbsp;</td>
                            </tr>
                          </table></td>
                          <td>&nbsp;</td>
                        </tr>
                        <tr class="text-globales">
                          <td height="26" >Remito:</td>
                          <td><table width="54%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="22%"><input name="filtroSucursal" type="text" value="<%=BVPEA.getFiltroSucursal()%>" id="filtroSucursal" size="4" maxlength="4" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;" class="campo" title="Sucursal"></td>
                              <td width="78%"><input name="filtroComprob" type="text" value="<%=BVPEA.getFiltroComprob()%>" id="filtroComprob" size="8" maxlength="8" style="text-align:right"  onKeyPress="if(!validaNumericosFFRemito(event)) return false;" class="campo" title="Comprobante"></td>
                            </tr>
                          </table></td>
                          <td>Pedido:</td>
                          <td><input name="filtroIdpedido" type="text" value="<%=BVPEA.getFiltroIdpedido()%>" id="filtroIdpedido" size="12" maxlength="10" style="text-align:right" onKeyPress="if(!validaNumericosFF(event)) return false;" class="campo" />
                          </td>
                          <td>Cliente:</td>
                          <td><input name="filtroIdclie" type="text" value="<%=BVPEA.getFiltroIdclie()%>" id="filtroIdclie" size="12" maxlength="10" style="text-align:right"  onkeypress="if(!validaNumericosFF(event)) return false;" class="campo" /></td>
                          <td>&nbsp;</td>
                        </tr>
                  </table>                
                </td>
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
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[7]%></div></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="38%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
     <td width="20%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[9]%></div></td>
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
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/config-users.png" width="22" height="22" onClick="getAudit(<%=sCampos[0]%>, <%=sCampos[1]%>);" title="Auditoría Cambio de Estado."></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/kwin.png" width="22" height="22"  onClick="abrirVentana('pedidosRemitosPedido.jsp?idpedido=<%=sCampos[0]%>&tipopedido=N', 'remitos', 500, 250 );" title="Ver remitos asociados"></div></td>
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

