<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vPedidosRegalosValorizacion
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Dec 22 10:55:25 ART 2010 
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
String titulo = "CONSULTA DE ORDENES DE REGALOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVPedidosRegalosValorizacion   = null;
int totCol = 9; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVPRVA"  class="ar.com.syswarp.web.ejb.BeanVPedidosRegalosValorizacion"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVPRVA" property="*" />
<%
 BVPRVA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVPRVA.setResponse(response);
 BVPRVA.setRequest(request);
 BVPRVA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script> 
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Orden";
tituCol[1] = "idpedido_regalos_padre";
tituCol[2] = "idcliente";
tituCol[3] = "Cliente";
tituCol[4] = "Total";
tituCol[5] = "totaliva";
tituCol[6] = "Total IVA";
tituCol[7] = "idestado";
tituCol[8] = "Estado";
java.util.List VPedidosRegalosValorizacion = new java.util.ArrayList();
VPedidosRegalosValorizacion= BVPRVA.getVPedidosRegalosValorizacionList();
iterVPedidosRegalosValorizacion = VPedidosRegalosValorizacion.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div> 
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vPedidosRegalosValorizacionAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="25"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="89%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="6%" height="26" class="text-globales">Buscar</td>
                          <td width="22%">
                             <input name="ocurrencia" type="text" value="<%=BVPRVA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="1%" height="19">&nbsp; </td>
                                         <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVPRVA.getTotalRegistros()%></td>
                                         <td width="11%" >Visualizar:</td>
                                         <td width="11%">
                                            <select name="limit" >
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BVPRVA.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BVPRVA.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                               <option value="<%= BVPRVA.getTotalRegistros()%>">Todos</option>
                                            </select>                                          </td>
                                         <td width="7%">&nbsp;P&aacute;gina:</td>
                                         <td width="12%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BVPRVA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BVPRVA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                            </select>                                          </td>
                                         <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                      </tr>
                                   </table>                                 </td>
                              </tr>
                          </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
                <tr>
                  <td height="38"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="text-globales">
                      <td width="13%" height="25">F.R. Desde:(*) </td>
                      <td width="37%" ><span class="fila-det-border">
                        <input name="filtroFRDesde" type="text" class="campo"
													id="filtroFRDesde" 
													value="<%=BVPRVA.getFiltroFRDesde()%>" size="9"
													maxlength="10" readonly>
                      <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onMouseOver="calSwapImg('BTN_date_0', 'img_Date_OVER',true); "
													onMouseOut="calSwapImg('BTN_date_0', 'img_Date_UP',true);"
													onClick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','filtroFRDesde','BTN_date_0');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_0" width="22" height="17" border="0"
														align="absmiddle"> </a></span></td>
                      <td width="13%">F.R. Hasta:(*) </td>
                      <td width="37%"><span class="fila-det-border">
                        <input name="filtroFRHasta" type="text" class="campo" 
													id="filtroFRHasta" 
													value="<%=BVPRVA.getFiltroFRHasta()%>" size="9"
													maxlength="10" readonly>
                      <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onMouseOver="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onMouseOut="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onClick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','filtroFRHasta','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"></a></span></td>
                    </tr>
                    <tr class="text-globales">
                      <td height="25">Exportar PDF </td>
                      <td ><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="18" height="18" border="0" style="cursor:pointer" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=pedidos_regalos_padres_hijos_frame&fechadesde=<%=BVPRVA.getFiltroFRDesde()%>&fechahasta=<%=BVPRVA.getFiltroFRHasta()%>', 'ordenes', 750, 500);"></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>

                  </table></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVPRVA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"></div></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="20%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="44%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[4]%></div></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%></div></td>
    </tr>
   <%int r = 0;
   double totalParcialSinIva = 0.00;
   double totalParcialConIva = 0.00;
   
   while(iterVPedidosRegalosValorizacion.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVPedidosRegalosValorizacion.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  
	  totalParcialSinIva += Double.parseDouble(sCampos[4]);
      totalParcialConIva += Double.parseDouble(sCampos[6]);
	  
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-vnd.ms-excel.png" width="18" height="18" onClick="abrirVentana('vPedidosRegalosValorizacionHijos.jsp?idpedido_regalos_padre=<%=sCampos[0]%>&idcliente=<%=sCampos[2]%>&cliente=<%=sCampos[3]%>','detalle', 750, 250)"></td> 
      <td class="fila-det-border" ><div align="right"><%=sCampos[0]%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[8]%></td>
      <td class="fila-det-border" ><%=sCampos[2]%>-<%=sCampos[3]%></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[4]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[6]%></div></td> 
    </tr>
<%
   } 
   
   if(r>0){
 
   
   %>
   <tr class="text-dos" > 
      <td colspan="4"><div align="right"> Total Parcial</div></td>
      <td  ><div align="right"><%= Common.getNumeroFormateado(totalParcialSinIva, 10, 2)%></div></td>
      <td  ><div align="right"><%= Common.getNumeroFormateado(totalParcialConIva, 10, 2)  %></div></td>
    </tr>     
   <tr class="text-dos-bold" > 
      <td colspan="4" ><div align="right"> Total General</div></td>
      <td ><div align="right"><%= BVPRVA.getTotalsiniva()%></div></td>
      <td ><div align="right"><%= BVPRVA.getTotalconiva()%></div></td>
    </tr>   
<% 
   }
 %>   
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

