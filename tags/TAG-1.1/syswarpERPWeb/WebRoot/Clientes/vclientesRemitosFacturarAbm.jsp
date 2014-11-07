<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vclientesRemitosFacturar 
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Jul 04 14:04:32 ART 2011 
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
String titulo = "REMITOS PENDIENTES DE FACTURACIÓN";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVclientesRemitosFacturar   = null;
int totCol = 19; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = ""; 
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVRFA"  class="ar.com.syswarp.web.ejb.BeanVclientesRemitosFacturarAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVRFA" property="*" />
<%
 BVRFA.setUsuarioalt( session.getAttribute("usuario").toString() );
 BVRFA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVRFA.setResponse(response);
 BVRFA.setRequest(request);
 BVRFA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
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
 
 function emitirDocumentos(){

   document.frm.accion.value = 'emitir';
   document.getElementById("emitir").value = 'Procesando ...';
   document.getElementById("emitir").disabled = true;   
   document.frm.submit();
   
 }
 
 window.onload = function() { 

   document.getElementById('marca').onclick =  checkUnckeckAll;
   document.getElementById('emitir').onclick = emitirDocumentos;
  
 }
 
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Total";
tituCol[1] = "idremitocliente";
tituCol[2] = "nrosucursal";
tituCol[3] = "Remito";
tituCol[4] = "Suc.Factura";
tituCol[5] = "C.Clie";
tituCol[6] = "Cliente";
tituCol[7] = "idzona";
tituCol[8] = "Zona";
tituCol[9] = "idexpreso";
tituCol[10] = "Expreso";
tituCol[11] = "idexpresozona";
tituCol[12] = "idanexolocalidad";
tituCol[13] = "idtarjeta";
tituCol[14] = "Tjta.";
tituCol[15] = "IVA";
tituCol[16] = "";
tituCol[17] = "Condición";
tituCol[18] = "Fecha";
java.util.List VclientesRemitosFacturar = new java.util.ArrayList();
VclientesRemitosFacturar= BVRFA.getVclientesRemitosFacturarList();
iterVclientesRemitosFacturar = VclientesRemitosFacturar.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vclientesRemitosFacturarAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="26" colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="14%" height="38">
                    
                    <div align="center">
                      <input name="emitir" type="button" class="boton" id="emitir"  title="Orden de la b&uacute;squeda: Nro.Cliente, Direcci&oacute;n, Nro.Pedido" value="Emitir Documentos">
                    </div></td>
                   <td width="86%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="72%" height="26">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="23%" height="19"><div align="right">Total de registros:&nbsp;</div></td>
                                          <td width="17%" ><%=BVRFA.getTotalRegistros()+""%></td>
                                          <td width="17%" ><div align="right">Visualizar:</div></td>
                                          <td width="38%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BVRFA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BVRFA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                             </select>                                          </td>
                                          <td width="10%"><div align="right">P&aacute;gina:</div></td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BVRFA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BVRFA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select>                                          </td>
                                       </tr>
                                    </table>                                 </td>
                              </tr>
                           </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
                <tr>
                  <td height="38"><div align="center">
                    <input name="ir" type="submit" class="boton" id="ir" value="         Buscar         "  title="Orden de la b&uacute;squeda: Nro.Cliente, Direcci&oacute;n, Nro.Pedido">
                  </div></td>
                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr class="text-globales">
                        <td width="19%" height="19"><div align="right">Consultar Log :&nbsp;</div></td>
                        <td width="14%" ><span class="fila-det-border"><img src="../imagenes/default/gnome_tango/apps/arts.png" width="22" height="22" onClick="abrirVentana('clientesMovcliRemitosLogAbm.jsp' , 'Facturacion', 750, 450)" style="cursor:pointer"></span></td>
                        <td width="14%" ><div align="right">Pendiente:</div></td>
                        <td width="11%"><span class="fila-det-border"><img src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-x-kspread.png" width="22" height="22" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=clientes_pendientes_facturar_zona' , 'FacturacionPendiente', 750, 450)" style="cursor:pointer"></span></td>
                        <td width="15%"> <div align="right">Contadores:</div></td>
                        <td width="27%"><span class="fila-det-border"><img src="../imagenes/default/gnome_tango/apps/stock_help.png" width="22" height="22" onClick="abrirVentana('../Tesoreria/vCajaSucursalesContadoresAbm.jsp' , 'Contadores', 750, 450)" style="cursor:pointer"></span></td>
                      </tr>
                    </table></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVRFA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td ><div align="center"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 16 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 16 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 3 ASC, 4 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 3  DESC, 4 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 19 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 19 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 5 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 5 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 6 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 6 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 7 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 7 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 11 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 11 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 15 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 15 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 15 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 15 DESC ')"></div></td>
    <td ><div align="center"><img src="../imagenes/default/SortUp.gif" width="9" height="9" style="cursor:pointer" onClick="setSort(' 1 ASC ')"> <img src="../imagenes/default/SortDown.gif" width="9" height="9" style="cursor:pointer"  onClick="setSort(' 1 DESC ')"></div></td>
  </tr>
  <tr class="fila-encabezado">
    <td  ><span class="fila-det-border">
      <select name="idclub" id="idclub" class="campo" style="width:75px" onChange="document.frm.submit();" >
        <option value="-1" >Sel.</option>
        <% 
									  Iterator iter = BVRFA.getListClub().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
        <option value="<%= datos[0] %>" <%= datos[0].equals( BVRFA.getIdclub().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
        <%  
									  }%>
      </select>
    </span></td>
    <td  ><div align="center">
      <select name="filtroLetraIva" id="filtroLetraIva" class="campo" onChange="document.frm.submit();" style="width:50px">
        <option value="">Sel.</option>
        <%
						    iter = BVRFA.getListLetrasIva().iterator();   
							while(iter.hasNext()){
							 String[] datos = (String[]) iter.next();
							 %>
        <option value="<%= datos[0] %>" <%=  BVRFA.getFiltroLetraIva().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[0] %> </option>
        <% 
						   } %>
      </select>
    </div></td>
    <td >
<div align="center">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><div align="right">
            <input name="filtroSucursalRemito" type="text" value="<%=BVRFA.getFiltroSucursalRemito()%>" id="filtroSucursalRemito" size="3" maxlength="4" style="text-align:right"  >
          </div></td>
          <td><input name="filtroRemito" type="text" value="<%=BVRFA.getFiltroRemito()%>" id="filtroRemito" size="4" maxlength="8" style="text-align:right"  ></td>
        </tr>
      </table>
</div>	</td>
    <td ><div align="center">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><div align="center">
            <select name="filtroFechaMes" id="filtroFechaMes" class="campo" onChange="document.frm.submit();" >
              <option value="">Sel.</option>
              <%
						     
							for(int m=1;m<13;m++){
							  
							 %>
              <option value="<%= m %>" <%=  BVRFA.getFiltroFechaMes().equals(String.valueOf(m)) ? "selected" : "" %> label="<%= Common.strZero(String.valueOf(m), 2)%>"><%= Common.strZero(String.valueOf(m), 2) %></option>
              <% 
						   } %>
            </select>
          </div></td>
          <td><select name="filtroFechaAnio" id="filtroFechaAnio" class="campo" onChange="document.frm.submit();" >
            <option value="">Sel.</option>
              <%
						     
							for(int y=BVRFA.getAnioActual()-1 ;y<BVRFA.getAnioActual()+2;y++){
							  
							 %>
              <option value="<%= y %>" <%=  BVRFA.getFiltroFechaAnio().equals(String.valueOf(y)) ? "selected" : "" %> label="<%= y %>"><%= y %></option>
              <% 
						   } %>
          </select></td>
        </tr>
      </table>
    </div></td>
    <td ><div align="center">
      <select name="filtroSucursalFactura" id="filtroSucursalFactura" class="campo" onChange="document.frm.submit();">
        <option value="">Sel.</option>
        <%
						    iter = BVRFA.getListSucursales().iterator();   
							while(iter.hasNext()){
							 String[] datos = (String[]) iter.next();
							 %>
        <option value="<%= datos[0] %>" <%=  BVRFA.getFiltroSucursalFactura().toString().equals(datos[0]) ? "selected" : "" %> label="<%= datos[1] %>"><%= Common.strZero(datos[0] , 4) %></option>
        <% 
						   } %>
      </select>
    </div></td>
    <td ><div align="center">
      <input name="filtroIdclie" type="text" value="<%=BVRFA.getFiltroIdclie()%>" id="filtroIdclie" size="5" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;">
    </div></td>
    <td >&nbsp;</td>
    <td ><select name="filtroExpresoZona" id="filtroExpresoZona" class="campo" style="width:80%" onChange="document.frm.submit();">
				<option value="">Seleccionar</option>
				<%
            iter = BVRFA.getListExpresosZonas().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
				<option value="<%= datos[0] %>" <%= datos[0].equals( BVRFA.getFiltroExpresoZona().toString()) ? "selected" : "" %>><%= datos[2] +  " / " + datos[4]%></option>
				<%  
			}%>
			</select></td>
    <td ><div align="center">
      <select name="filtroCondicion" id="filtroCondicion" class="campo" style="width:80%" onChange="document.frm.submit();">
        <option value="">Seleccionar</option>
        <%
            iter = BVRFA.getListCondicion().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
        <option value="<%= datos[0] %>" <%= datos[0].equals( BVRFA.getFiltroCondicion().toString()) ? "selected" : "" %>><%= datos[1] %></option>
        <%  
			}%>
      </select>
    </div></td>
    <td ><div align="center">
      <select name="filtroTarjetaCredito" id="filtroTarjetaCredito" class="campo" style="width:80%" onChange="document.frm.submit();">
        <option value="">Seleccionar</option> 
        <%
            iter = BVRFA.getListTarjetasCredito().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
        <option value="<%= datos[0] %>" <%= datos[0].equals( BVRFA.getFiltroTarjetaCredito().toString()) ? "selected" : "" %>><%= datos[1] %></option>
        <%  
			}%>
		<option value="ST" <%=  BVRFA.getFiltroTarjetaCredito().equalsIgnoreCase("ST") ? "selected" : "" %>>SIN TARJETA</option>
      </select>
    </div></td>
    <td ><div align="center"><span class="fila-det-border">
      <select name="filtroValorComprobante" id="filtroValorComprobante" class="campo" style="width:75px" onChange="document.frm.submit();" >
        <option value="" >Sel.</option>
        <option value="0" <%=  BVRFA.getFiltroValorComprobante().equalsIgnoreCase("0") ? "selected" : "" %>>TNF (= 0)</option>
        <option value="1" <%=  BVRFA.getFiltroValorComprobante().equalsIgnoreCase("1") ? "selected" : "" %>>TF  (> 0)</option>				
      </select>
    </span></div></td>
  </tr>
  <tr class="fila-encabezado">
    <td width="7%" ><div align="center" ><a href="#" id="marca">Todos</a></div></td>
     <td width="4%" ><div align="center"><%=tituCol[15]%></div></td>
     <td width="14%" ><div align="center"><%=tituCol[3]%></div></td>
     <td width="6%" ><div align="center"><%=tituCol[18]%></div></td>
     <td width="6%" ><div align="center"><%=tituCol[4]%></div></td>
     <td width="3%" ><div align="right"><%=tituCol[5]%></div></td>
     <td width="24%" ><%=tituCol[6]%></td>
     <td width="27%" ><%=tituCol[10]%>- <%=tituCol[8]%></td>
     <td width="8%" ><%=tituCol[17]%></td>
     <td width="8%" ><div align="center"><%=tituCol[14]%></div></td>
     <td width="7%" ><div align="right"><%=tituCol[0]%></div></td>
  </tr>
   <%int r = 0;
   while(iterVclientesRemitosFacturar.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVclientesRemitosFacturar.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center">
        <input name="idremitocliente" type="checkbox" id="idremitocliente" value="<%=sCampos[1]%>" title="<%=sCampos[1]%>"> 
      </div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[15])%></div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.strZero(sCampos[2], 4) + "-" + Common.strZero(sCampos[3], 8)%></div></td>
      <td class="fila-det-border" ><div align="center"><%=!Common.setNotNull(sCampos[18]).equals("") ? Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[18]), "JSDateToStr" ): "" %></div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[4])%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[5]%></div></td>
      <td class="fila-det-border" ><%=sCampos[6]%></td>
      <td class="fila-det-border" ><%=sCampos[10]%> / <%=sCampos[8]%></td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[17])%></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[14]).length() > 10  ? sCampos[14].substring(0, 9) : Common.setNotNull(sCampos[14]) %> &nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=  Common.visualNumero(sCampos[0], 2)  %></div></td>
   </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="sort"  id="sort" value="<%= BVRFA.getSort() %>" type="hidden">
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

