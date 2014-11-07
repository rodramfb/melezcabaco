<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: pedidos_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Feb 12 08:43:04 GYT 2009 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*" %> 
<%@ page import="java.sql.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "CONTROL DE DESPACHOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterPedidos_cabe   = null;
int totCol = 21; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
Iterator iter;
%>
<html>
<jsp:useBean id="BPA"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosControlDespachos"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPA" property="*" />
<%
 BPA.setResponse(response);
 BPA.setRequest(request);
 BPA.setIdcontadorcomprobante( new BigDecimal( session.getAttribute("idcontadorremitos1").toString() ));  
 BPA.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPA.setIdpuesto( new BigDecimal( session.getAttribute("idpuesto").toString() ));
 BPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BPA.ejecutarValidacion(); 

/*
 Enumeration e = session.getAttributeNames();
 while(e.hasMoreElements()){
   String atr = e.nextElement().toString();
   System.out.println("  " + atr + " : " + session.getAttribute(atr));
 }
*/

%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script> 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 
 function setSort(orden){
   document.getElementById('sort').value = orden;
   document.frm.submit();
 }

 function getCodigo(evento) {
	var evento = evento || window.event;
	var codigo = evento.charCode || evento.keyCode;
	var caracter = String.fromCharCode(codigo);
    return codigo;
 }

 function validaNumericos(){
   var codigo = getCodigo(window.event);
   if( codigo<48 || codigo>57 ) return false;
 }

 function validaCharsFecha(){
   var codigo = getCodigo(window.event);
   var fecha = document.getElementById('filtroFecha');
   if(fecha.value.length == 2 || fecha.value.length == 5){
     fecha.value += '/';
     return false;
   }
   else{
     if( codigo < 48 || codigo>57 ) return false;
   }
 }

 
 function setTipoPedido(obj){
  var tipopedido = '<%=BPA.getTipopedido()%>';
  if(obj.value != tipopedido){
    document.frm.accion.value = '';
    document.frm.submit();
  } 

}


function mostrarMensaje(mensaje){
	overlib( mensaje , STICKY, CAPTION, '[INFO:Grupo Armado]',TIMEOUT,25000,HAUTO,VAUTO,WIDTH,200,BGCOLOR,'#000000');
}

 window.onload = function() { 
 
  }

 </script>

</head>
<%
// titulos para las columnas
tituCol[0] = "HA";
tituCol[1] = "Fecha HA";
tituCol[2] = "HRF";
tituCol[3] = "Fecha HRF";
tituCol[4] = "Cta.Cte";
tituCol[5] = "idExpreso";
tituCol[6] = "Expreso";
tituCol[7] = "Bultos";
tituCol[8] = "IdDeposito";
tituCol[9] = "Depósito Destino";




java.util.List Pedidos_cabe = new java.util.ArrayList();
Pedidos_cabe= BPA.getPedidos_cabeList();
iterPedidos_cabe = Pedidos_cabe.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div> 
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesRemitosControlDespachos.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td height="30"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="89%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <!--td width="6%" height="26" class="text-globales">Buscar</td>
                          <td width="22%">
                             <input name="ocurrencia" type="text" value="< %=BPA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td-->
                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td height="44"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                           <tr class="text-globales">
                                             <td width="28%">F.H.A Desde</td>
                                             <td width="72%"><span class="fila-det-border">
                                               <input name="filtroFHADesde" type="text" class="campo"
													id="filtroFHADesde" 
													value="<%=BPA.getFiltroFHADesde()%>" size="12"
													maxlength="12" readonly>
                                             <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onMouseOver="calSwapImg('BTN_date_0', 'img_Date_OVER',true); "
													onMouseOut="calSwapImg('BTN_date_0', 'img_Date_UP',true);"
													onClick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','filtroFHADesde','BTN_date_0');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_0" width="22" height="17" border="0"
														align="absmiddle"> </a></span></td>
                                           </tr>
                                         </table></td>
                                         <td>&nbsp;Total de registros:&nbsp;<%=BPA.getTotalRegistros() + ""%></td>
                                         <td >Visualizar:</td>
                                         <td><select name="limit" >
                                             <%for(i=15; i<= 150 ; i+=15){%>
                                             <%if(i==BPA.getLimit()){%>
                                             <option value="<%=i%>" selected><%=i%></option>
                                             <%}else{%>
                                             <option value="<%=i%>"><%=i%></option>
                                             <%}
                                                      if( i >= BPA.getTotalRegistros() ) break;
                                                    %>
                                             <%}%>
                                             <option value="<%= BPA.getTotalRegistros()%>">Todos</option>
                                           </select>                                         </td>
                                         <td>&nbsp;P&aacute;gina:</td>
                                         <td><select name="paginaSeleccion" >
                                             <%for(i=1; i<= BPA.getTotalPaginas(); i++){%>
                                             <%if ( i==BPA.getPaginaSeleccion() ){%>
                                             <option value="<%=i%>" selected><%=i%></option>
                                             <%}else{%>
                                             <option value="<%=i%>"><%=i%></option>
                                             <%}%>
                                             <%}%>
                                           </select>                                         </td>
                                       </tr>
                                       <tr class="text-globales">
                                         <td width="34%" height="44"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                           <tr class="text-globales">
                                             <td width="27%">F.H.A Hasta </td>
                                             <td width="73%"><span class="fila-det-border">
                                               <input name="filtroFHAHasta" type="text" class="campo"
													id="filtroFHAHasta" 
													value="<%=BPA.getFiltroFHAHasta()%>" size="12"
													maxlength="12" readonly>
                                             <a class="so-BtnLink"
													href="javascript:calClick();return false;"
													onMouseOver="calSwapImg('BTN_date_6', 'img_Date_OVER',true); "
													onMouseOut="calSwapImg('BTN_date_6', 'img_Date_UP',true);"
													onClick="calSwapImg('BTN_date_6', 'img_Date_DOWN');showCalendar('frm','filtroFHAHasta','BTN_date_6');return false;"><img
														src="vs/calendar/btn_date_up.gif" title="Ver Calendario..."
														name="BTN_date_6" width="22" height="17" border="0"
														align="absmiddle"> </a></span></td>
                                           </tr>
                                         </table></td>
                                         <td width="22%">Pedido Normal<span class="fila-det-border">
                                         <input name="tipopedido" type="radio" class="campo" value="N" <%= BPA.getTipopedido().equalsIgnoreCase("N") ? "checked" : "" %> onClick="setTipoPedido(this);" >
                                         </span></td>
                                         <td width="16%" ><span class="fila-det-border">R.Empresario
                                             <input name="tipopedido" type="radio" class="campo" value="R" <%= BPA.getTipopedido().equalsIgnoreCase("R") ? "checked" : "" %> onClick="setTipoPedido(this);">
                                         </span></td>
                                         <td width="11%">&nbsp;</td>
                                         <td width="6%">&nbsp;</td>
                                         <td width="11%"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                       <tr class="text-globales">
                                         <td height="3" colspan="6" bgcolor="#000000"></td>
                                       </tr>
                                       <tr class="text-globales">
                                         <td height="15" colspan="2">&nbsp;</td>
                                         <td >&nbsp;</td>
                                         <td>&nbsp;</td>
                                         <td>&nbsp;</td>
                                         <td>&nbsp;</td>
                                       </tr>
                                   </table>                                 </td>
                              </tr>
                          </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="text-globales">
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 1 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 1 DESC  ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 2 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 2 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 3 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 3 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 4 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 4 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 5 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 5 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 7 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 7 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 8 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 8 DESC ')"></div></td>
  </tr>
  <tr class="text-globales">
    <td ><div align="center">
      <input name="filtroHojarutafinal" type="text" value="<%=BPA.getFiltroHojarutafinal()%>" id="filtroHojarutafinal" size="6" maxlength="5" style="text-align:right"  >
    </div></td>
    <td >&nbsp;</td>
    <td ><div align="center"><%-- <input name="filtroExpreso_" type="text" value="< %=BPA.getFiltroExpreso()%>" id="filtroExpreso_" size="30" maxlength="30" style="text-align:right"> --%>
    </div></td>
    <td ><div align="center"></div></td>
    <td ><div align="center">
      <input name="filtroCtaCte" type="text" value="<%=BPA.getFiltroDepositoDestino()%>" id="filtroCtaCte" size="10" maxlength="10" style="text-align:right">
</div></td>
    <td ><div align="center">
      <select name="filtroExpreso" id="filtroExpreso" class="campo" style="width:80%" onChange="document.frm.submit();">
        <option value="">Seleccionar</option>
        <%
            iter = BPA.getListExpresos().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
        <option value="<%= datos[0] %>" <%= datos[0].equals( BPA.getFiltroExpreso().toString()) ? "selected" : "" %> ><%= datos[1] %></option>
        <%  
			}%>
      </select>
    </div></td>
    <td ><div align="center"></div></td>
  </tr>
  <tr class="fila-encabezado">
    <td width="5%" ><div align="center"><%=tituCol[0]%>&nbsp;</div></td>
     <td width="5%" ><div align="center"><%=tituCol[1]%></div></td>
     <td width="6%" ><div align="center"><%=tituCol[2]%></div></td>
     <td width="6%" ><div align="center"><%=tituCol[3]%></div></td>
     <td width="5%" ><div align="center"><%=tituCol[4]%></div></td>
     <td width="53%" ><div align="center"><%=tituCol[6]%></div></td>
     <td width="6%" ><div align="center"><%=tituCol[7]%></div></td>
  </tr>
   <%int r = 0;
   String grupoArmado = "";
   String colorGrupo = "#990000";
   String plantilla = BPA.getTipopedido().equalsIgnoreCase("N") ? "hoja_armado_frame" : "hoja_armado_reg_frame"; 
//   Enumeration en = Common.getSetSorted(BPA.getHtColorGrupoArmado().keySet());
   while(iterPedidos_cabe.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidos_cabe.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  
%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><div align="center" onClick="abrirVentana('consultaHojaArmado.jsp?nrohojaarmado=<%=sCampos[0]%>', 'hrarmado', 750, 400)" ><a href="#"><%=sCampos[0]%></a></div></td>
      <td class="fila-det-border" ><div align="center"><%=!Common.setNotNull(sCampos[1]).equals("") ? Common.setObjectToStrOrTime( java.sql.Date.valueOf(sCampos[1]), "JSDateToStr" ) : ""%>&nbsp; </div></td>
      <td class="fila-det-border" ><div >
        <div align="center"><%=Common.setNotNull(sCampos[2])%>&nbsp;</div>
      </div></td>
      <td class="fila-det-border" ><div title="">
          <div align="center"><%=!Common.setNotNull(sCampos[3]).equals("") ? Common.setObjectToStrOrTime( java.sql.Date.valueOf(sCampos[3]), "JSDateToStr" ) : ""%>&nbsp; </div>
      </div></td>
      <td class="fila-det-border" >  
        <div align="center"><%= Common.setNotNull(sCampos[4]) %>&nbsp;</div>
      </div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[6])%>&nbsp;</div></td>
      <td class="fila-det-border" ><div title="">
          <div align="center"><%=sCampos[7]%>&nbsp; </div>
      </div></td>
   </tr>
<% 
   }%>
   </table> 
   <input name="accion" value="" type="hidden">
   <input name="sort"  id="sort" value="<%= BPA.getSort() %>" type="hidden">
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

