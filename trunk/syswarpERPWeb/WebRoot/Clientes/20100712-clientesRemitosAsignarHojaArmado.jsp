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
String titulo = "ASIGNAR HOJA DE ARMADO";
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
%>
<html>
<jsp:useBean id="BPA"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosAsignarHojaArmado"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPA" property="*" />
<%
 BPA.setResponse(response);
 BPA.setRequest(request);
 //BPA.setIdcontadorcomprobante( new BigDecimal( session.getAttribute("idcontadorremitos4").toString() ));  
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
   var obj = document.frm.idremitocliente;
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

 function ejecutarConsolidacion(){
   document.frm.accion.value = 'asignarhojaarmado';
   document.getElementById("asignarhojaarmado").value = 'Procesando ...';
   document.getElementById("asignarhojaarmado").disabled = true;
   //document.getElementById("consolidar").style.visibility = 'hidden';
   //document.getElementById("bar").style.visibility = 'visible';
   document.frm.submit();
 }

function mostrarMensaje(mensaje){
	overlib( mensaje , STICKY, CAPTION, '[INFO:Grupo Armado]',TIMEOUT,25000,HAUTO,VAUTO,WIDTH,200,BGCOLOR,'#000000');
}


function setTipoPedido(obj){
  var tipopedido = '<%=BPA.getTipopedido()%>';
  if(obj.value != tipopedido){
    document.frm.accion.value = '';
    document.getElementById("asignarhojaarmado").disabled = true;
    document.frm.submit();
  } 

}

 window.onload = function() { 
  document.getElementById('marca').onclick =  checkUnckeckAll;
  document.getElementById('filtroSucursal').onkeypress = validaNumericos;
  document.getElementById('filtroRemito').onkeypress = validaNumericos;
  document.getElementById('filtroMes').onkeypress = validaNumericos;
  document.getElementById('filtroAno').onkeypress = validaNumericos;
  document.getElementById('filtroCliente').onkeypress = validaNumericos;
  document.getElementById('filtroNroctacte').onkeypress = validaNumericos;
  document.getElementById('asignarhojaarmado').onclick = ejecutarConsolidacion;
 }

 </script>

</head>
<%
// titulos para las columnas
tituCol[0] = "Id.Remito";
tituCol[1] = "Fecha";
tituCol[2] = "Sucursal";
tituCol[3] = "Remito";
tituCol[4] = "Id.Cliente";
tituCol[5] = "Cliente";
tituCol[6] = "Calle";
tituCol[7] = "Nro";
tituCol[8] = "Piso";
tituCol[9] = "Depto";
tituCol[10] = "idzona";
//tituCol[11] = "Zona";
tituCol[11] = "Dist.";
tituCol[12] = "idexpreso";
//tituCol[13] = "Expreso";
tituCol[13] = "Expreso/Zona";
tituCol[14] = "idlocalidad";
tituCol[15] = "Localidad";
tituCol[16] = "idProvincia";
tituCol[17] = "Provincia"; 
tituCol[18] = "codigo_dt";
tituCol[19] = "Depósito Origen"; 
tituCol[20] = "Cta.Cte"; 

java.util.List Pedidos_cabe = new java.util.ArrayList();
Pedidos_cabe= BPA.getPedidos_cabeList();
iterPedidos_cabe = Pedidos_cabe.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesRemitosAsignarHojaArmado.jsp" method="POST" name="frm">
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

                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="24%" height="44"><div align="center" id="consolida">
                                           
                                           <input name="asignarhojaarmado" type="button" class="boton" id="asignarhojaarmado" value="Asignar Hoja Armado">
                                           <!--img src="../imagenes/default/gnome_tango/actions/bar-consolidando.gif" width="99" height="20" id="bar" style="visibility:hidden" -->
                                         </div></td>
                                         <td width="30%">&nbsp;Total de registros:&nbsp;<%=BPA.getTotalRegistros() + ""%></td>
                                         <td width="15%" >Visualizar:</td>
                                         <td width="10%">
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
                                         <td width="10%">&nbsp;P&aacute;gina:</td>
                                         <td width="11%">
                                            <select name="paginaSeleccion" >
                                               <%for(i=1; i<= BPA.getTotalPaginas(); i++){%>
                                                   <%if ( i==BPA.getPaginaSeleccion() ){%>
                                                      <option value="<%=i%>" selected><%=i%></option> 
                                                   <%}else{%>
                                                      <option value="<%=i%>"><%=i%></option>
                                                   <%}%>
                                               <%}%>
                                         </select>                                           </td>
                                      </tr>
                                       <tr class="text-globales">
                                         <td height="44">&nbsp;</td>
                                         <td height="44">Pedido Normal <span class="fila-det-border">
                                           <input name="tipopedido" type="radio" class="campo" value="N" <%= BPA.getTipopedido().equalsIgnoreCase("N") ? "checked" : "" %> onClick="setTipoPedido(this);" >
                                         </span></td>
                                         <td height="44" colspan="2"><span class="fila-det-border">R.Empresario
                                           <input name="tipopedido" type="radio" class="campo" value="R" <%= BPA.getTipopedido().equalsIgnoreCase("R") ? "checked" : "" %> onClick="setTipoPedido(this);">
                                         </span></td>
                                         <td height="44">&nbsp;</td>
                                         <td height="44">&nbsp;</td>
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
    <td colspan="9" >Imprimir Hoja de Armado Generada: <img src="../imagenes/default/gnome_tango/apps/pdf.jpg" border="0" style="cursor:pointer" onClick="abrirVentana('remitos/<%= BPA.getNameFile() %>', 'remitoscliente', 750, 500);">&nbsp;</td>
  </tr>
  <%
   }%>
  <tr class="text-globales">
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 23 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 23 DESC ')"></div></td>
    <td >&nbsp;</td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 21 ASC, 22 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 21 DESC, 22 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 5 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 5 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 2 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 2 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 20 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 20 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 14 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 14 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 19 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 19 DESC ')"></div></td>
    <td >&nbsp;</td>
  </tr>
  <tr class="text-globales">
    <td >&nbsp;</td> 
    <td >&nbsp;</td>
    <td ><div align="center">
      <input name="filtroSucursal" type="text" value="<%=BPA.getFiltroSucursal()%>" id="filtroSucursal" size="3" maxlength="4" style="text-align:right"  >
      <input name="filtroRemito" type="text" value="<%=BPA.getFiltroRemito()%>" id="filtroRemito" size="4" maxlength="6" style="text-align:right"  >
    </div></td>
    <td ><div align="center">
      <input name="filtroCliente" type="text" value="<%=BPA.getFiltroCliente()%>" id="filtroCliente" size="7" maxlength="10" style="text-align:right">
    </div></td>
    <td ><div align="center">
      <input name="filtroMes" type="text" value="<%=BPA.getFiltroMes()%>" id="filtroMes" size="1" maxlength="2" style="text-align:right">
      /
      <input name="filtroAno" type="text" value="<%=BPA.getFiltroAno()%>" id="filtroAno" size="2" maxlength="4" style="text-align:right">
    </div></td>
    <td ><div align="center">
      <input name="filtroNroctacte" type="text" value="<%=BPA.getFiltroNroctacte()%>" id="filtroNroctacte" size="6" maxlength="15" style="text-align:right">
    </div></td>
    <td ><div align="center">

			<select name="filtroExpresoZona" id="filtroExpresoZona" class="campo" style="width:80%" >
				<option value="">Seleccionar</option>
				<%
      Iterator iter = BPA.getListExpresosZonas().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
				<option value="<%= datos[0] %>" <%= datos[0].equals( BPA.getFiltroExpresoZona().toString()) ? "selected" : "" %>><%= datos[2] +  " / " + datos[4]%></option>
				<%  
			}%>
			</select> 

    </div></td>
    <td ><div align="center">
			<select name="filtroDepOrigen" id="filtroDepOrigen" class="campo" style="width:80%" >
				<option value="">Seleccionar</option>
				<%
      iter = BPA.getDepositosList().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
				<option value="<%= datos[0] %>" <%= datos[0].equals( BPA.getFiltroDepOrigen().toString()) ? "selected" : "" %>><%= datos[1] %></option>
				<%  
			}%>
			</select> 
    </div></td>
    <td ><div align="center">
      <input name="ir" type="submit" class="boton" id="ir" value="Buscar">
    </div></td>
  </tr>
  <tr class="fila-encabezado">
    <td width="3%" >&nbsp;</td>
     <td width="2%" >&nbsp;</td>
     <td width="15%" ><div align="center"><%=tituCol[3]%>&nbsp;</div></td>
     <td width="10%" ><div align="center"><%=tituCol[5]%></div></td>
     <td width="13%" ><div align="center"><%=tituCol[1]%></div></td>
     <td width="12%" ><div align="center"><%=tituCol[20]%></div></td>
     <td width="17%" ><%=tituCol[13]%></td>
     <td width="22%" ><%=tituCol[19]%></td>
     <td width="6%" ><div align="center" ><a href="#" id="marca">Todos</a></div></td>
  </tr>
   <%int r = 0;
   String grupoArmado = "";
   String colorGrupo = "#990000";
//   Enumeration en = Common.getSetSorted(BPA.getHtColorGrupoArmado().keySet());
   Enumeration en =  BPA.getHtColorGrupoArmado().keys();
   String plantillaImpresionJRXML = BPA.getTipopedido().equalsIgnoreCase("N") ? "remitos_clientes_frame" : "remitos_clientes_regalos_frame"; 
   while(iterPedidos_cabe.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidos_cabe.next(); 
      String fecha = Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[1]), "JSDateToStr") + "" ;
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
       
      if(!grupoArmado.equals(sCampos[22])){
        if(!en.hasMoreElements()) en = Common.getSetSorted(BPA.getHtColorGrupoArmado().keySet());
        colorGrupo = BPA.getHtColorGrupoArmado().get(en.nextElement()) + "" ;
        grupoArmado = sCampos[22];  
      } 
%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td rowspan="2" bgcolor="<%= colorGrupo %>"  class="fila-det-border" ><div align="center" onMouseOver="mostrarMensaje('<%= grupoArmado %>')">&nbsp;</div></td> 
      <td rowspan="2"  class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="15" height="15" border="0" style="cursor:pointer" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=<%= plantillaImpresionJRXML %>&idremitoclientedesde=<%=sCampos[0]%>', 'remitocliente', 750, 500);"></td>
      <td rowspan="2" class="fila-det-border" ><div align="center" onClick="abrirVentana('clientesRemitoDetalle.jsp?sucursal=<%=sCampos[2]%>&remitocliente=<%=sCampos[3]%>&idremitocliente=<%=sCampos[0]%>&idcliente=<%=sCampos[4]%>&cliente=<%=sCampos[5]%>&tipopedido=<%= BPA.getTipopedido() %>','detalle', 750, 450)" style="cursor:pointer" ><a href="#"><%=sCampos[2]%>-<%=sCampos[3]%>&nbsp;</a></div></td>
      <td rowspan="2" class="fila-det-border" ><div title="<%=sCampos[4]%>-<%=sCampos[5]%>">
        <div align="center"><%=sCampos[4]%></div>
      </div></td>
      <td rowspan="2" class="fila-det-border" ><div title="<%=  fecha %>">
        <div align="center"><%= fecha.substring(3, 10) %></div>
      </div></td>
      <td rowspan="2" class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[19])%>&nbsp;</div></td>
      <td  ><%=sCampos[13].length() > 20 ? sCampos[13].substring(0, 20) : sCampos[13] %></td>
      <td rowspan="2" class="fila-det-border" ><%=sCampos[18].length() > 20 ? sCampos[18].substring(0, 20) : sCampos[18] %>&nbsp;</td>  
      <td rowspan="2" class="fila-det-border" ><div align="center">
          <input name="idremitocliente" type="checkbox" id="idremitocliente" value="<%=sCampos[0]%>">
      </div></td>
   </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><%=sCampos[11].length() > 20 ? sCampos[11].substring(0, 20) : sCampos[11] %></td>
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

