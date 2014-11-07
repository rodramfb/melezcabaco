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
String titulo = "ASIGNAR HOJA DE RUTA FINAL";
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
<jsp:useBean id="BPA"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosAsignarHojaRutaFinal"   scope="page"/>
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
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 function checkUnckeckAll(){
   var objTextTipo = document.getElementById("marca").firstChild;
   var check = true;
   if(objTextTipo.nodeValue == 'Todos') objTextTipo.nodeValue = 'Ninguno';
   else{
     objTextTipo.nodeValue = 'Todos';
     check=false;
   }
   var obj = document.frm.nrohojaarmado; 
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

 function ejecutarAsignacionHojaRuta(){
   document.frm.accion.value = 'asignarhojarutafinal';
   document.getElementById("asignarhojarutafinal").disabled = true;
   document.frm.submit();
 }
 
 function setTipoPedido(obj){
  var tipopedido = '<%=BPA.getTipopedido()%>';
  if(obj.value != tipopedido){
    document.frm.accion.value = '';
    document.getElementById("asignarhojarutafinal").disabled = true;
    document.frm.submit();
  } 

}


function setClub(obj){
  var idclub = '<%=BPA.getIdclub()%>';
  if(obj.value != idclub){
    document.frm.accion.value = '';
    document.getElementById("asignarhojarutafinal").disabled = true;
    document.frm.submit();
  } 
}

function mostrarMensaje(mensaje){
	overlib( mensaje , STICKY, CAPTION, '[INFO:Grupo Armado]',TIMEOUT,25000,HAUTO,VAUTO,WIDTH,200,BGCOLOR,'#000000');
}

 window.onload = function() { 
  document.getElementById('marca').onclick =  checkUnckeckAll;
  document.getElementById('asignarhojarutafinal').onclick = ejecutarAsignacionHojaRuta;
 }

 </script>

</head>
<%
// titulos para las columnas
tituCol[0] = "HA";
tituCol[1] = "IdExpreso";
//tituCol[2] = "Expreso";
tituCol[2] = "Zona";
tituCol[3] = "IdDeposito";
tituCol[4] = "Depósito Destino";
tituCol[5] = "Bultos";



java.util.List Pedidos_cabe = new java.util.ArrayList();
Pedidos_cabe= BPA.getPedidos_cabeList();
iterPedidos_cabe = Pedidos_cabe.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesRemitosAsignarHojaRutaFinal.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td><%=titulo%></td>
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
                                         <td height="44"><div id="consolida">
                                             <input name="asignarhojarutafinal" type="button" class="boton" id="asignarhojarutafinal" value="Ejecutar Asignaci&oacute;n ">
                                             <!--img src="../imagenes/default/gnome_tango/actions/bar-consolidando.gif" width="99" height="20" id="bar" style="visibility:hidden" -->
                                         </div></td>
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
                                         <td width="11%">&nbsp;P&aacute;gina:</td>
                                         <td width="18%"><select name="paginaSeleccion" >
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
                                         <td width="15%" height="44">&nbsp;</td>
                                         <td width="22%">Pedido Normal<span class="fila-det-border">
                                         <input name="tipopedido" type="radio" class="campo" value="N" <%= BPA.getTipopedido().equalsIgnoreCase("N") ? "checked" : "" %> onClick="setTipoPedido(this);" >
                                         </span></td>
                                         <td width="19%" ><span class="fila-det-border">R.Empresario
                                             <input name="tipopedido" type="radio" class="campo" value="R" <%= BPA.getTipopedido().equalsIgnoreCase("R") ? "checked" : "" %> onClick="setTipoPedido(this);">
                                         </span></td>
                                         <td width="15%">&nbsp;</td>
                                         <td height="30">Club(*)</td>
                                         <td height="30"><span class="fila-det-border">
                                           <select name="idclub" id="idclub" class="campo" style="width:75px" onChange="setClub(this)" >
                                             <option value="-1" >Sel.</option>
                                             <% 
									  iter = BPA.getListClub().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                                             <option value="<%= datos[0] %>" <%= datos[0].equals( BPA.getIdclub().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                                             <%  
									  }%>
                                           </select>
                                         </span></td>
                                       </tr>
                                       <tr class="text-globales">
                                         <td height="3" colspan="6" bgcolor="#000000"></td>
                                       </tr>
                                       <tr class="text-globales">
                                         <td height="15" colspan="2">&nbsp;</td>
                                         <td >Total de Pallets:</td>
                                         <td><input name="nropallets" type="text" value="<%=BPA.getNropallets()%>" id="nropallets" size="6" maxlength="5" style="text-align:right"  ></td>
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
  <% 
   if(!BPA.getNameFile().equals("")){ %>  
  <tr class="text-globales"> 
    <td colspan="7" >
    Imprimir Hoja de Ruta Final Generada: <img src="../imagenes/default/gnome_tango/apps/pdf.jpg" border="0" style="cursor:pointer" onClick="abrirVentana('remitos/<%= BPA.getNameFile() %>', 'remitoscliente', 750, 500);">&nbsp;  </tr> 
 <%
   }%>
   <%-- 
   //20101213 - EJV - Mantis 637 -->
   //if(!BPA.getArchivoAndreani().equals("")){
   if(BPA.getArchivoAndreani().equals("¡¡¡¡¡¡¡¡¡ESTO NO SE USA MAS!!!!!!")){ %>  
  <tr class="text-globales"> 
    <td colspan="6" >
      Archivo Andreani: <a href="clientesArchivosVer.jsp?file=< %= BPA.getArchivoAndreani() % >" target="_blank"><img src="../imagenes/default/gnome_tango/actions/centrejust.png" width="18" height="18" border="0" style="cursor:pointer" title="Vista Rápida" ></a>&nbsp;  <a href="< %= BPA.getArchivoAndreaniZip() % >" target="_blank"><img src="../imagenes/default/gnome_tango/apps/kfm.png" width="20" height="20" border="0" style="cursor:pointer" title="Archivo Compromido"></a></tr> 
  < %
   }
   // <--
   --%>
   <%
   if(!BPA.getRemitosgenerados().equals("")){ %>  
  <tr class="text-globales"> 
    <td colspan="7" >
      Imprimir Remitos Internos <img src="../imagenes/default/gnome_tango/devices/printer1.png" width="22" height="22" onClick="abrirVentana('../Stock/impresionRemitosInternos.jsp?remitos=<%=  BPA.getRemitosgenerados() %>  &tipo=Cambio Deposito&plantillaImpresionJRXML=cambio_deposito_frame', 'remitos', 750, 450)" style="cursor:pointer"></td>
  </tr> 
  <%
   }%>
  
  <tr class="text-globales">
    <td >&nbsp;</td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 1 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 1 DESC  ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 3 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 3 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 5 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 5 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 6 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 6 DESC ')"></div></td>
    <td colspan="2" >&nbsp;</td>
  </tr>
  <tr class="text-globales">
    <td >&nbsp;</td>
    <td ><div align="center">
      <input name="filtroHojarutafinal" type="text" value="<%=BPA.getFiltroHojarutafinal()%>" id="filtroHojarutafinal" size="6" maxlength="5" style="text-align:right"  >
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
	
      <%-- <input name="filtroExpreso_" type="text" value="< %=BPA.getFiltroExpreso()%>" id="filtroExpreso_" size="30" maxlength="30" style="text-align:right"> --%>
    </div></td>
    <td ><div align="center">
      <input name="filtroDepositoDestino" type="text" value="<%=BPA.getFiltroDepositoDestino()%>" id="filtroDepositoDestino" size="30" maxlength="30" style="text-align:right">
</div></td>
    <td ><div align="center"></div></td>
    <td colspan="2" ><div align="center">
      <input name="ir" type="submit" class="boton" id="ir" value="Buscar">
    </div></td>
  </tr>
  <tr class="fila-encabezado">
    <td width="3%" >&nbsp;</td>
     <td width="9%" ><div align="center"><%=tituCol[0]%>&nbsp;</div></td>
     <td width="38%" ><div align="center"><%=tituCol[2]%></div></td>
     <td width="36%" ><div align="center"><%=tituCol[4]%></div></td>
     <td width="8%" ><div align="center"><%=tituCol[5]%></div></td>
     <td colspan="2" ><div align="center" ><a href="#" id="marca">Todos</a></div></td>
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
     <td  class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="15" height="15" border="0" style="cursor:pointer" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=<%= plantilla %>&nrohojaarmado=<%=sCampos[0]%>', 'hojaarmado', 750, 500);"></div></td>
      <td class="fila-det-border" ><div align="center" onClick="abrirVentana('consultaHojaArmado.jsp?nrohojaarmado=<%=sCampos[0]%>', 'hrarmado', 750, 400)" ><a href="#"><%=sCampos[0]%></a></div></td>
      <td class="fila-det-border" ><div title="<%=sCampos[0]%> ">
        <div align="center"><%=sCampos[2]%></div>
      </div></td>
      <td class="fila-det-border" >  
        <div align="center"><%= sCampos[4] %></div>
      </div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[5])%>&nbsp;</div></td>
      <td width="2%" class="fila-det-border" ><div align="center">
          <input name="nrohojaarmado" type="checkbox" id="nrohojaarmado" value="<%=sCampos[0]%>">
      </div></td>
      <td width="4%" class="fila-det-border" ><img src="<%=Common.setNotNull(sCampos[8])%>" title="<%=Common.setNotNull(sCampos[7])%>"></td>
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

