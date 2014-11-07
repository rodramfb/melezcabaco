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
String titulo = "CONSULTA - PEDIDOS PROGRAMADOS";
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
<jsp:useBean id="BCPP"  class="ar.com.syswarp.web.ejb.BeanConsultaPedidosProgramados"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCPP" property="*" />
<%
 Iterator iter;
 BCPP.setResponse(response);
 BCPP.setRequest(request);
 //BCPP.setIdcontadorcomprobante( new BigDecimal( session.getAttribute("idcontadorremitos4").toString() ));  
 BCPP.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCPP.setIdpuesto( new BigDecimal( session.getAttribute("idpuesto").toString() ));
 BCPP.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BCPP.ejecutarValidacion(); 

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
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
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

 function validaCharsFecha(evento){
 
   var tipo = 'F';
   
   for(var i=0;i<document.frm.radioFechaPeriodo.length;i++){
     if(document.frm.radioFechaPeriodo[i].checked){
	   tipo = document.frm.radioFechaPeriodo[i].value;
	   break;
	 }
   }
   
   var codigo = getCodigo(evento);
   var fecha = document.getElementById('filtroFecha');
   if(tipo == 'F'){
	   if((fecha.value.length == 2 || fecha.value.length == 5 ) &&  (codigo != 8)){
		 fecha.value += '/';
		 return false;
	   }
	   else{
		 if( (codigo < 48 || codigo>57)  &&  (codigo != 8  )  ) return false;
	   }
   } else if(tipo == 'P'){
   	   if((fecha.value.length == 2 ) &&  (codigo != 8)){
		 fecha.value += '/';
		 return false;
	   }
	   else{
		 if( (codigo < 48 || codigo>57)  &&  (codigo != 8  )  ) return false;
	   }
   }
 }
 
 function setAtributosFecha(tipo){
   var fecha = document.getElementById('filtroFecha');
   fecha.value = '';  
   if(tipo == 'F'){
     fecha.maxLength = 10;
   } else {
     fecha.maxLength = 7;
   }  
 }


function setTipoPedido(obj){
  var tipopedido = '<%=BCPP.getTipopedido()%>';
  if(obj.value != tipopedido){
    document.frm.accion.value = '';
    document.frm.submit();
  } 

}


function mostrarMensaje(tipo){
  var descripcion = 'Filtro por FECHA: DD/MM/YYYY ';
  if(tipo == 'P') descripcion = 'Filtro por PERIODO: MM/YYYY.';
  overlib('', STICKY, CAPTION, descripcion ,TIMEOUT,5000,HAUTO,VAUTO,WIDTH,220,CAPCOLOR, '#FF0000');
}


 window.onload = function() { 
  document.getElementById('filtroIdpedido').onkeypress = validaNumericos;
  document.getElementById('filtroFecha').onkeypress = validaCharsFecha;
 }

 </script>

</head>
<%
// titulos para las columnas
tituCol[0] = "Pedido";
tituCol[1] = "idremitocliente";
tituCol[2] = "Sucursal";
tituCol[3] = "Remito";
tituCol[4] = "idestadopedido";
tituCol[5] = "Est. Ped.";
tituCol[6] = "idestadoremito";
tituCol[7] = "Est. Rem.";
tituCol[8] = "C.Clie.";
tituCol[9] = "Cliente";
tituCol[10] = "Fecha Entrega";
tituCol[11] = "idprioridad";
tituCol[12] = "Prioridad";
//tituCol[8] = "Zona";
tituCol[13] = "Expreso/Zona.";
tituCol[11] = "Localidad";
//tituCol[10] = "Expreso";
tituCol[14] = "Zona";

java.util.List Pedidos_cabe = new java.util.ArrayList();
Pedidos_cabe= BCPP.getPedidos_cabeList();
iterPedidos_cabe = Pedidos_cabe.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="consultaPedidosProgramados.jsp" method="POST" name="frm">
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
                             <input name="ocurrencia" type="text" value="< %=BCPP.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td-->
                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tr class="text-globales">
                                      <td width="12%" height="32"><div align="center" id="consolida">
                                          <!--img src="../imagenes/default/gnome_tango/actions/bar-consolidando.gif" width="99" height="20" id="bar" style="visibility:hidden" -->
                                          <input name="ir" type="submit" class="boton" id="ir" value="Buscar" onClick="setSort(' 11 DESC')" title="Orden de la búsqueda: Fech de Entrega.">
                                      </div></td>
                                      <td width="29%">Total de registros:&nbsp;<%=BCPP.getTotalRegistros() + ""%></td>
                                      <td width="12%" >Visualizar:</td>
                                      <td width="20%"><select name="limit" >
                                          <%for(i=15; i<= 150 ; i+=15){%>
                                          <%if(i==BCPP.getLimit()){%>
                                          <option value="<%=i%>" selected><%=i%></option>
                                          <%}else{%>
                                          <option value="<%=i%>"><%=i%></option>
                                          <%}
                                                      if( i >= BCPP.getTotalRegistros() ) break;
                                                    %>
                                          <%}%>
                                          <option value="<%= BCPP.getTotalRegistros()%>">Todos</option>
                                        </select>                                      </td>
                                      <td width="11%">&nbsp;P&aacute;gina:</td>
                                      <td width="16%"><select name="paginaSeleccion" >
                                          <%for(i=1; i<= BCPP.getTotalPaginas(); i++){%>
                                          <%if ( i==BCPP.getPaginaSeleccion() ){%>
                                          <option value="<%=i%>" selected><%=i%></option>
                                          <%}else{%>
                                          <option value="<%=i%>"><%=i%></option>
                                          <%}%>
                                          <%}%>
                                        </select>                                      </td>
                                    </tr>
                                    <tr class="text-globales">
                                      <td height="30">&nbsp;</td>
                                      <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="45%" class="text-globales">Pedido Normal </td>
                                            <td width="55%"><span class="fila-det-border">
                                              <input name="tipopedido" type="radio" class="campo" value="N" <%= BCPP.getTipopedido().equalsIgnoreCase("N") ? "checked" : "" %> onClick="setTipoPedido(this);" >
                                          </span></td>
                                        </tr>
                                        </table></td>
                                      <td height="30" colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                          <tr class="text-globales">
                                            <td width="41%"><span class="fila-det-border">R.Empresario </span></td>
                                            <td width="59%"><span class="fila-det-border">
                                              <input name="tipopedido" type="radio" class="campo" value="R" <%= BCPP.getTipopedido().equalsIgnoreCase("R") ? "checked" : "" %> onClick="setTipoPedido(this);">
                                            </span></td>
                                          </tr>
                                        </table></td>
                                      <td height="30">&nbsp;</td>
                                      <td height="30">&nbsp;</td>
                                    </tr>
                                    <tr class="text-globales"> 
                                      <td height="30">&nbsp;</td>
                                      <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                          <tr class="text-globales">
                                            <td width="45%" >F. Desde: </td>
                                            <td width="55%"><span class="fila-det-border">
                                              <input name="fechaDesde" type="text" class="cal-TextBox" id="fechaDesde" onFocus="this.blur()" value="<%=BCPP.getFechaDesde()%>" size="10" maxlength="10" readonly>
                                            <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechaDesde','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></span></td>
                                          </tr>
                                        </table></td>
                                      <td height="30" colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr class="text-globales">
                                          <td width="41%">F. Hasta: </td>
                                          <td width="59%" class="fila-det-border"><input name="fechaHasta" type="text" class="cal-TextBox" id="fechaHasta" onFocus="this.blur()" value="<%=BCPP.getFechaHasta()%>" size="10" maxlength="10" readonly>
                                          <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onMouseOver="calSwapImg('BTN_date_77', 'img_Date_OVER',true); "
                  onMouseOut="calSwapImg('BTN_date_77', 'img_Date_UP',true);"
                  onClick="calSwapImg('BTN_date_77', 'img_Date_DOWN');showCalendar('frm','fechaHasta','BTN_date_77');return false;"> <img align="absmiddle" border="0" name="BTN_date_77" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                                        </tr>
                                      </table></td>
                                      <td height="30">&nbsp;</td>
                                      <td height="30">&nbsp;</td>
                                    </tr>
                                    
                                  </table></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCPP" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="text-globales">
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 1 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 1 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 6 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 6 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 3 ASC, 4 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 3 DESC, 4 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 8 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 8 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 9 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 9 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 10 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 10 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 11 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 11 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 20 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 20 DESC ')"></div></td>
<%--     <td ><div align="center">***<img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 10 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 10 DESC ')"></div></td> --%>
    </tr>
 
  <tr class="text-globales">
    <td ><input name="filtroIdpedido" type="text" value="<%=BCPP.getFiltroIdpedido()%>" id="filtroIdpedido" size="4" maxlength="10" style="text-align:right" onKeyPress="if(!validaNumericosFF(event)) return false;"></td>
    <td ><select name="filtroEstadoPedido" id="filtroEstadoPedido"  class="campo" style="width:110px">
      <option value="" >Seleccionar</option>
      <% 
                              iter = BCPP.getListEstadosPedido().iterator();
                              while(iter.hasNext()){
                                String[] datos = (String[]) iter.next();
								if(Integer.parseInt(datos[0]) > 3 ) continue;
                              %>
      <option value="<%=datos[0]%>"  <%= datos[0].equals(BCPP.getFiltroEstadoPedido().toString() ) ?  "selected" : "" %> ><%=datos[1]%></option>
      <%
                              }%>
    </select></td>
    <td >&nbsp;</td>
    <td ><span class="fila-det-border">
      <select name="filtroEstadoRemito" id="filtroEstadoRemito" class="campo" style="width:75px" >
        <option value="" selected>Sel.</option>
		<option value=" NULL " <%= BCPP.getFiltroEstadoRemito().equals( " NULL " ) ? "selected" : "" %>>SIN REMITO</option>
        <% 
									  iter = BCPP.getListEstadosRemito().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();
										if(Integer.parseInt(datos[0]) != 1 ) continue;
										%>
        <option value="<%= datos[0] %>" <%= datos[0].equals( BCPP.getFiltroEstadoRemito()) ? "selected" : "" %>><%= datos[1] %></option>
        <%  
									  }%>
      </select>
    </span></td>
    <td ><div align="right">
      <input name="filtroIdclie" type="text" value="<%=BCPP.getFiltroIdclie()%>" id="filtroIdclie" size="5" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;">
    </div></td>
    <td ><input name="filtroCliente" type="text" value="<%=BCPP.getFiltroCliente()%>" id="filtroCliente" size="25" maxlength="30"></td>
    <td ><div align="center">
      <input name="filtroFecha" type="text" value="<%=BCPP.getFiltroFecha()%>" id="filtroFecha" size="8" maxlength="10" style="text-align:right" onKeyPress="validaCharsFecha(event)">
    </div></td>
    <td >
      <span class="fila-det-border">
			<select name="filtroExpresoZona" id="filtroExpresoZona" class="campo" style="width:80%" >
				<option value="">Seleccionar</option>
				<%
      iter = BCPP.getListExpresosZonas().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
				<option value="<%= datos[0] %>" <%= datos[0].equals( BCPP.getFiltroExpresoZona().toString()) ? "selected" : "" %>><%= datos[2] +  " / " + datos[4]%></option>
				<%  
			}%>
			</select> 
      </span>    </td>
    </tr>
  <tr class="fila-encabezado">
     <td width="3%" ><div align="center"><%=tituCol[0]%>&nbsp;</div></td>
     <td width="8%" ><%=tituCol[5]%></td>
     <td width="10%" ><%=tituCol[3]%></td>
     <td width="10%" ><%=tituCol[7]%></td>
     <td width="6%" ><div align="right"><%=tituCol[8]%></div></td>
     <td width="31%" ><%=tituCol[9]%></td>
     <td width="11%" ><div align="center"><%=tituCol[10]%></div></td>
     <td width="31%" ><%=tituCol[13]%></td>
    </tr>
   <%int r = 0;
   String target = "pedidosHistoricoClienteDetalle.jsp";//;
   if(BCPP.getTipopedido().equalsIgnoreCase("R")) target =  "pedidosRegalosDetalleEntrega.jsp" ;
   while(iterPedidos_cabe.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidos_cabe.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  String plantillaRemitos = "";
	  if(BCPP.getTipopedido().equalsIgnoreCase("N")){
	    plantillaRemitos= "remitos_clientes_frame";
	  } 
	  else{
	    plantillaRemitos= "remitos_clientes_regalos_frame";	  
	  }
	  
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td rowspan="2" class="fila-det-border" ><div align="center" onClick="abrirVentana('<%= target %>?tipopedido=<%=BCPP.getTipopedido()%>&idpedido_cabe=<%=sCampos[0]%>&idcliente=<%=sCampos[8]%>&cliente=<%=sCampos[9]%>','detalle', 750, 450)" style="cursor:pointer" ><a href="#"><%=sCampos[0]%>&nbsp;</a></div></td>
      <td rowspan="2" class="fila-det-border" ><%=sCampos[5]%></td>
      <td rowspan="2" class="fila-det-border" ><%=!Common.setNotNull(sCampos[2]).equals("") ? "<div onClick=\"abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML="+ plantillaRemitos +"&idremitoclientedesde=" + sCampos[1] + "', '', 750, 450)\"><a href=\"#\">" + Common.strZero(Common.setNotNull(sCampos[2]), 4)  + "-"  + Common.strZero(Common.setNotNull(sCampos[3]), 8) + "</a></div>" : "&nbsp;"%></td>
      <td rowspan="2" class="fila-det-border" ><%=sCampos[7]%></td>
      <td rowspan="2" class="fila-det-border" ><div align="right"><%=sCampos[8]%></div></td>
      <td rowspan="2" class="fila-det-border" ><%=sCampos[9]%></td>
      <td rowspan="2" class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(Timestamp.valueOf(sCampos[10]), "JSTsToStr")%></div></td>
      <td  ><%=sCampos[15].length() > 20 ? sCampos[15].substring(0, 20) : sCampos[15] %></td>
    </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><%=sCampos[13].length() > 20 ? sCampos[13].substring(0, 20) : sCampos[13] %></td>
   </tr>
<% 
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="sort"  id="sort" value="<%= BCPP.getSort() %>" type="hidden">
</form>
</body>
</html>
<% 

 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : " + ex);   
}%>

