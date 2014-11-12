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
String titulo = "PEDIDOS - ENTREGAS PENDIENTES";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterPedidos_cabe   = null;
int totCol = 31; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPA"  class="ar.com.syswarp.web.ejb.BeanPedidosPendientes"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPA" property="*" />
<%
 Iterator iter;
 BPA.setResponse(response);
 BPA.setRequest(request);
 BPA.setIdcontadorcomprobante( new BigDecimal( session.getAttribute("idcontadorremitos4").toString() ));  
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
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <!--script language="JavaScript" src="<%=pathscript%>/forms.js"></script-->
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css"> 
 <script>
 function checkUnckeckAll(){
   var objTextTipo = document.getElementById("marca").firstChild;
   var check = true;
   if(objTextTipo.nodeValue == 'Todos') objTextTipo.nodeValue = 'Ninguno';
   else{
     objTextTipo.nodeValue = 'Todos';
     check=false;
   }
   var obj = document.frm.idpedido_cabe;
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

 function validaCharsFecha(evento){
   var codigo = getCodigo(evento);
   var fecha = document.getElementById('filtroFecha');
   if((fecha.value.length == 2 || fecha.value.length == 5 ) &&  (codigo != 8)){
     fecha.value += '/';
     return false;
   }
   else{
     if( (codigo < 48 || codigo>57)  &&  (codigo != 8  )  ) return false;
   }
 }

 function ejecutarConsolidacion(){
   document.frm.accion.value = 'consolidar';
   document.getElementById("consolidar").value = 'Procesando ...';
   document.getElementById("consolidar").disabled = true;
   //document.getElementById("consolidar").style.visibility = 'hidden';
   //document.getElementById("bar").style.visibility = 'visible';
   document.frm.submit();
 }


function setTipoPedido(obj){
  var tipopedido = '<%=BPA.getTipopedido()%>';
  if(obj.value != tipopedido){
    document.frm.accion.value = '';
    document.getElementById("consolidar").disabled = true;
    document.frm.submit();
  } 

}


 window.onload = function() { 

 
  document.getElementById('marca').onclick =  checkUnckeckAll;
 //document.getElementById('filtroIdpedido').onkeypress = validaNumericos;
  document.getElementById('filtroFecha').onkeypress = validaCharsFecha;

  document.getElementById('consolidar').onclick = ejecutarConsolidacion;
  
 }

 </script>

</head>
<%
// titulos para las columnas
tituCol[0] = "Pedido";
tituCol[1] = "idestado";
tituCol[2] = "Estado";
tituCol[3] = "C.Clie.";
tituCol[4] = "Cliente";
tituCol[5] = "Fecha Entrega";
tituCol[6] = "idprioridad";
tituCol[7] = "Prioridad";
//tituCol[8] = "Zona";
tituCol[8] = "Expreso/Zona.";
tituCol[9] = "Localidad";
//tituCol[10] = "Expreso";
tituCol[10] = "Zona";

java.util.List Pedidos_cabe = new java.util.ArrayList();
Pedidos_cabe= BPA.getPedidos_cabeList();
iterPedidos_cabe = Pedidos_cabe.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="pedidosPendientes.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td height="35"><%=titulo%></td>
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
                                         <td width="12%" height="32"><div align="center" id="consolida">
                                           <input name="consolidar" type="button" class="boton" id="consolidar" value="Consolidar">
                                         <!--img src="../imagenes/default/gnome_tango/actions/bar-consolidando.gif" width="99" height="20" id="bar" style="visibility:hidden" --></div></td>
                                         <td>Total de registros:&nbsp;<%=BPA.getTotalRegistros()%></td>
                                         <td width="5%" >Visualizar:</td>
                                         <td width="24%">
                                            <select name="limit" >
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
                                            </select>                                          </td>
                                         <td width="6%">&nbsp;P&aacute;gina:</td>
                                         <td width="16%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BPA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BPA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                            </select>										</td>
                                      </tr>
                                       <tr class="text-globales">
                                         <td height="30">&nbsp;</td>
                                         <td height="30">Pedido Normal<span class="fila-det-border">
                                           <input name="tipopedido" type="radio" class="campo" value="N" <%= BPA.getTipopedido().equalsIgnoreCase("N") ? "checked" : "" %> onClick="setTipoPedido(this);" >
                                         </span></td>
                                         <td height="30" colspan="2"><span class="fila-det-border">R.Empresario
                                             <input name="tipopedido" type="radio" class="campo" value="R" <%= BPA.getTipopedido().equalsIgnoreCase("R") ? "checked" : "" %> onClick="setTipoPedido(this);">
                                         </span></td>
                                         <td height="30">&nbsp;</td>
                                         <td height="30">&nbsp;</td>
                                       </tr>
                                       <tr class="text-globales">
                                         <td height="30">&nbsp;</td>
                                         <td height="30">Fecha Remito:<span class="fila-det-border">
                                           <input class="cal-TextBox" onFocus="this.blur()" size="10" readonly type="text" name="fechaRemito" value="<%=BPA.getFechaRemito()%>" maxlength="10">
                                           <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechaRemito','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></span></td>
                                         <td height="30" >&nbsp;</td>
                                         <td height="30">&nbsp;</td>
                                         <td height="30">&nbsp;</td>
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
  <% 
   if(!BPA.getNameFile().equals("")){
   //if(BPA.getNameFile().equals("123456")){ %>  
  <tr class="text-globales"> 
    <td colspan="7" >Imprimir remitos generados <img src="../imagenes/default/gnome_tango/apps/pdf.jpg" border="0" style="cursor:pointer" onClick="abrirVentana('remitos/<%= BPA.getNameFile() %>', 'remitoscliente', 750, 500);">&nbsp;</td>
    </tr>
  <%
   }%>
  <tr class="text-globales">
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 1 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 1 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 4 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 4 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 5 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 5 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 6 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 6 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 7 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 7 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 9 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 9 DESC ')"></div></td>
<%--     <td ><div align="center">***<img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 10 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 10 DESC ')"></div></td> --%>
    <td >&nbsp;</td>
  </tr>
  <tr class="text-globales">
    <td ><input name="filtroIdpedido" type="text" value="<%=BPA.getFiltroIdpedido()%>" id="filtroIdpedido" size="4" maxlength="10" style="text-align:right" onKeyPress="if(!validaNumericosFF(event)) return false;"></td>
    <td ><div align="right">
      <input name="filtroIdclie" type="text" value="<%=BPA.getFiltroIdclie()%>" id="filtroIdclie" size="5" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;">
    </div></td>
    <td ><input name="filtroCliente" type="text" value="<%=BPA.getFiltroCliente()%>" id="filtroCliente" size="25" maxlength="30"></td>
    <td ><div align="center">
      <input name="filtroFecha" type="text" value="<%=BPA.getFiltroFecha()%>" id="filtroFecha" size="10" maxlength="10" style="text-align:right" onKeyPress="validaCharsFecha(event)">
    </div></td>
    <td ><span class="fila-det-border">
      <select name="filtroPrioridad" id="filtroPrioridad" class="campo" style="width:75px" >
        <option value="" selected>Sel.</option>
        <% 
									  iter = BPA.getListPrioridades().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
        <option value="<%= datos[0] %>" <%= datos[0].equals( BPA.getFiltroPrioridad()) ? "selected" : "" %>><%= datos[1] %></option>
        <%  
									  }%>
      </select>
      </span></td>
    <td >
      <span class="fila-det-border">
			<select name="filtroExpresoZona" id="filtroExpresoZona" class="campo" style="width:80%" >
				<option value="">Seleccionar</option>
				<%
      iter = BPA.getListExpresosZonas().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
				<option value="<%= datos[0] %>" <%= datos[0].equals( BPA.getFiltroExpresoZona().toString()) ? "selected" : "" %>><%= datos[2] +  " / " + datos[4]%></option>
				<%  
			}%>
			</select> 
       <%--
       <select name="filtroZona" id="filtroZona" class="campo" style="width:75px" >
        <option value="">Sel.</option>
        <% 
									  iter = BPA.getListZonas().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
        <option value="<%= datos[0] %>" <%= datos[0].equals( BPA.getFiltroZona()) ? "selected" : "" %>><%= datos[1] %></option>
        <%  
									  }%>
      </select> --%>
      </span>    </td>
<%--    <td >***
      <input name="filtroLocalidad" type="text" value="<%=BPA.getFiltroLocalidad()%>" id="filtroLocalidad" size="10" maxlength="30"></td> --%>
    <td ><div align="center">
      <input name="ir" type="submit" class="boton" id="ir" value="Buscar" onClick="setSort(' 4, 14 , 1 ')" title="Orden de la búsqueda: Nro.Cliente, Dirección, Nro.Pedido">
    </div></td>
  </tr>
  <tr class="fila-encabezado">
     <td width="5%" ><div align="center"><%=tituCol[0]%>&nbsp;</div></td>
     <td width="5%" ><div align="right"><%=tituCol[3]%></div></td>
     <td width="19%" ><%=tituCol[4]%></td>
     <td width="10%" ><div align="center"><%=tituCol[5]%></div></td>
     <td width="7%" ><%=tituCol[7]%></td>
     <td width="32%" ><%=tituCol[8]%></td>
<%-- <td width="17%" >***<%=tituCol[9]%></td>--%>
     <td width="5%" ><div align="center" ><a href="#" id="marca">Todos</a></div></td>
  </tr>
   <%int r = 0;
   String target = "pedidosHistoricoClienteDetalle.jsp";//;
   if(BPA.getTipopedido().equalsIgnoreCase("R")) target =  "pedidosRegalosDetalleEntrega.jsp" ;
   while(iterPedidos_cabe.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidos_cabe.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td rowspan="2" class="fila-det-border" ><div align="center" onClick="abrirVentana('<%= target %>?tipopedido=<%=BPA.getTipopedido()%>&idpedido_cabe=<%=sCampos[0]%>&idcliente=<%=sCampos[3]%>&cliente=<%=sCampos[4]%>','detalle', 750, 450)" style="cursor:pointer" ><a href="#"><%=sCampos[0]%>&nbsp;</a></div></td>
      <td rowspan="2" class="fila-det-border" ><div align="right"><%=sCampos[3]%></div></td>
      <td rowspan="2" class="fila-det-border" ><%=sCampos[4]%></td>
      <td rowspan="2" class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(Timestamp.valueOf(sCampos[5]), "JSTsToStr")%></div></td>
      <td rowspan="2" class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td  ><%=sCampos[10].length() > 20 ? sCampos[10].substring(0, 20) : sCampos[10] %></td>
      <%-- <td rowspan="2" class="fila-det-border" >***<%=sCampos[9]%>&nbsp;</td> --%>
      <td rowspan="2" class="fila-det-border" ><div align="center">
          <input name="idpedido_cabe" type="checkbox" id="idpedido_cabe" value="<%=sCampos[0]%>">
      </div></td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><%=sCampos[8].length() > 20 ? sCampos[8].substring(0, 20) : sCampos[8] %></td>
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

   System.out.println( " * FINALIZA EJECUCION * " );

 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : " + ex);   
}%>

