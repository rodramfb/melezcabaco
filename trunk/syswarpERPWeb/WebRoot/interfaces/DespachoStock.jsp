<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: proveedoArticulos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Aug 02 14:39:14 GMT-03:00 2006 
   Observaciones: 
      .

  
*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*"%>
<%@ page import="ar.com.syswarp.api.*" %> 
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "DESPACHO DE STOCK";
// variables de entorno
String pathskin = "../imagenes/default/";
String pathscript = "/scripts/";
String stockMostrarPrecios = "N";

// variables de paginacion
int i = 0;
Iterator iterProveedoArticulos   = null;
int totCol = 9; // cantidad de columnas
String resultado = "";
String[] tituCol = new String[totCol];
String usuarioalt = str.esNulo( request.getParameter("usuario")  != null ? request.getParameter("usuario")  + "" :   session.getAttribute("usuario") + "");
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario") + "";
String idempresa = str.esNulo( request.getParameter("idempresa")  != null ? request.getParameter("idempresa")  + "" :   session.getAttribute("empresa") + "");;
%>
<html>
<jsp:useBean id="BSCDA"  class="ar.com.syswarp.web.ejb.BeanINTERFACESStockCambioDeposito"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BSCDA" property="*" />
<%

  System.out.println("STOCK: -" + usuarioalt + "- ");
	if(idempresa.equals("")) idempresa = "1";
	//if(usuarioalt.equals("")) usuarioalt = "INTERFAZ"; 
	if(usuarioalt.equals("")) response.sendRedirect("caduco.jsp"); 
	else{
	 if(usuarioalt.length()>17) usuarioalt = usuarioalt.substring(0, 16);
	 if (usuarioalt.indexOf(":IF") == -1) usuarioalt += ":IF" ;
	}

 BSCDA.setResponse(response);  
 BSCDA.setRequest(request);
 BSCDA.setSession(session);
 BSCDA.setUsuarioalt( usuarioalt );
 BSCDA.setIdempresa( new BigDecimal( idempresa ) ) ; 
 BSCDA.ejecutarValidacion();
 String [] retorno = BSCDA.getResultado();
 for(int j=0;retorno!=null&&j<3;j++)
	  resultado += str.esNulo(retorno[j]).equals("") ?  "" : retorno[j] + "<br>";	  
 
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="../imagenes/default/erp-style.css">
 <link rel="stylesheet" type="text/css" href="vs/calendar/calendar.css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="scripts/forms.js"></script>
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <script >
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}


function eliminarRegistro(){
  if(confirm("Confirma eliminar selección ?")){
		  document.frm.accion.value = "eliminar";
		  document.frm.submit();
		}
}

function confirmarDetalle(){
  if(confirm("Confirma datos ingresados ?")){
		  document.frm.accion.value = "confirmar";
		  document.frm.submit();
		}
}
//-->
 </script>
 <style type="text/css">
<!--
.style1 {font-size: 9px}
-->
 </style>
</head>
<%

/*			<!-- codigo_st,alias_st,descrip_st,descri2_st, "
				+ "cost_re_st, cost_uc_st, cost_pp_st,precipp_st, ultcomp_st-->*/
// titulos para las columnas
tituCol[0] = "Cod.";
tituCol[1] = "Alias";
tituCol[2] = "Articulo";
tituCol[3] = "Desc. Alt.";
tituCol[4] = "Precio Real";
tituCol[5] = "Precio U.C.";
tituCol[6] = "Costo";
tituCol[7] = "Precio PP";
//tituCol[8] = "Ult. Comp.";
tituCol[8] = "Existencia";
java.util.List ProveedoArticulos = new java.util.ArrayList();
ProveedoArticulos= BSCDA.getProveedoArticulosList();
iterProveedoArticulos = ProveedoArticulos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div>
<form action="DespachoStock.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td class="text-globales"><%=titulo   %>  </td> 
                </tr>
                <tr>
                   <td width="89%" height="38">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BSCDA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BSCDA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=10; i<= 100 ; i+=10){%>
                                                    <%if(i==BSCDA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BSCDA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BSCDA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BSCDA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BSCDA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select> 
                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>
                                 </td>
                              </tr>
                           </table>
                        </td>
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
   <td class="fila-det-bold-rojo"><table width="100%" border="0" cellspacing="0" cellpadding="0">
       <tr>
         <td class="fila-det-bold-rojo"><jsp:getProperty name="BSCDA" property="mensaje"/></td>
         <td class="fila-det-bold-rojo"><%=resultado %></td>
       </tr>
     </table></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" ><span class="fila-det-bold-rojo">
       <input name="agregar" id="agregar" value="agregar" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('agregar','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">
     </span></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>		 
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="31%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
	 <%if(stockMostrarPrecios.equalsIgnoreCase("S")){%>		
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <%}%> 	
   </tr>
   <%int r = 0;
   while(iterProveedoArticulos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProveedoArticulos.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input type="radio" name="codigo_st" value="<%= sCampos[0]%>"></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/gnome-calculator.png" width="18" height="18" onClick="abrirVentana('stockTotalDepositoLov.jsp?codigo_st=<%=sCampos[0]%>&idempresa=<%=idempresa%>', 'totStock', 700, 200)"></div></td>		
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
	  <%if(stockMostrarPrecios.equalsIgnoreCase("S")){%>		
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>		
	  <%}%> 		
   </tr>
<%
   }%>
  </table>

	 <% 
			
  	
   Hashtable htArticulosMovimientoIF = (Hashtable) session.getAttribute("htArticulosMovimientoIF");
	 if(htArticulosMovimientoIF != null && !htArticulosMovimientoIF.isEmpty()){
	   %> 	 
		 &nbsp; 
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr  class="text-globales" height="3">
				<td colspan="10"> </td>
			</tr>	
			
			<tr  class="fila-det-bold">
			  <td colspan="7" class="fila-det-border"> 
				  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr class="fila-det-bold">
            <td width="12%" height="23" class="fila-det-border">&nbsp;Fecha:</td>
            <td width="17%" class="fila-det-border"><span >
              <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechamov" value="<%= BSCDA.getFechamov() %>" maxlength="12">
              <a class="so-BtnLink" href="javascript:calClick();return false;" 
					 onMouseOver="calSwapImg('BTN_date_1', 'img_Date_OVER',true);" 
					 onMouseOut="calSwapImg('BTN_date_1', 'img_Date_UP',true);" 
					 onClick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fechamov','BTN_date_1');return false;"> <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></span></td>
            <td width="19%" class="fila-det-border">Comprobante Interno: </td>
            <td colspan="2" class="fila-det-border"><span >
              <input name="tipomov" type="radio" value="CI" class="campo" checked>
            </span></td>
          </tr>
          <tr class="fila-det-bold">
            <td height="26" class="fila-det-border">&nbsp;</td>
            <td height="26" class="fila-det-border">&nbsp;</td>
            <td height="26" class="fila-det-border">Remito Oficial:</td>
            <td width="3%" height="26" class="fila-det-border"><input name="tipomov" type="radio" value="RO" class="campo" disabled></td>
            <td width="49%" class="fila-det-border style1"> ( opción no operativa ) </td>
          </tr>
          <tr class="fila-det-bold">
            <td height="26" colspan="3" class="fila-det-border">&nbsp;Mantener mismo origen y destino para todos los movimientos </td>
            <td width="3%" height="26" class="fila-det-border"><input name="mismorigdest" type="checkbox"  class="campo" value="true"  <%= BSCDA.isMismorigdest() ? "checked" : "" %> ></td>
            <td width="49%" class="fila-det-border style1">&nbsp; </td>
          </tr>					
          <tr class="fila-det-bold">
            <td height="26" class="fila-det-border">&nbsp;Observaciones:</td>
            <td height="26" colspan="4" class="fila-det-border"><textarea name="observaciones" cols="80" rows="2" class="campo" id="observaciones"><%//= articulos[4] %></textarea></td>
            </tr>
          </table>
				</td>
			</tr>
			<tr  class="text-globales" height="3">
				<td colspan="10"> </td>
			</tr>	
							
			<tr  class="fila-det-bold">
			 <td width="3%">&nbsp;Sel.</td>
				<td width="12%">&nbsp; Cód.</td>
				<td width="15%">&nbsp; Alias</td>
				<td width="26%">&nbsp; Descripción</td>
			 <td width="14%">Origen</td>				
				<td width="15%"> Destino  </td>
				<td width="15%">&nbsp; Cantidad</td>
			</tr>
			<tr  class="text-globales" height="3">
				<td colspan="10"> </td>
			</tr>		
		<%
	   			Enumeration en = Common.getSetSorted (htArticulosMovimientoIF.keySet());
					while (en.hasMoreElements()) {
						String key = (String) en.nextElement();
						String [] articulos = (String []) htArticulosMovimientoIF.get(key);
						/*
						Hashtable htDepositos = BSCDA.getHtDepositos();
						Enumeration enumDepositos = Common.getSetSorted (htDepositos.keySet());*/

						  %>
		<tr class="fila-det">
			<td class="fila-det-border"><input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo"></td>
			<td class="fila-det-border">&nbsp;<%= articulos[0] %><input  name="keyHashDatosArticulo" type="hidden" value="<%= key %>"></td>
			<td class="fila-det-border">&nbsp;<%= articulos[1] %></td>
			<td class="fila-det-border">&nbsp;<%= articulos[2] %></td>
		 <td class="fila-det-border"><select name="origen" id="origen" class="campo">
																																<option value="-1">Seleccionar</option>
        <% 
																											            Iterator iter = BSCDA.getListDepositos().iterator();					
																																	while(iter.hasNext()){
																																			String keyDep[] = (String[]) iter.next();
																																			String sel = "";

																																			if(articulos[5].equals(keyDep[0])) sel = "selected";
																																			else sel = "";
																																			
																																			  %>
        <option value="<%= keyDep[0] %>" <%= sel %>><%= keyDep[1] %></option>
        <%
																																	}
																																																															
																																 %>
																														 </select>
																														 	 </td>
			
			<td class="fila-det-border"><select name="destino" id="destino" class="campo">
        <option value="-1">Seleccionar</option>
        <% 
																											            iter = BSCDA.getListDepositos().iterator();					
																																	while(iter.hasNext()){
																																			String keyDep[] = (String[]) iter.next();
																																			String sel = "";

																																			if(articulos[6].equals(keyDep[0])) sel = "selected";
																																			else sel = "";
																																			
																																			  %>
        <option value="<%= keyDep[0] %>" <%= sel %>><%= keyDep[1] %></option>
        <%
																																	}
																																																															
																																 %>
      </select>
			</td>
			<td class="fila-det-border">&nbsp;
		  <input type="text" class="campo" name="cantidad" value="<%= articulos[4]  %>" size="10"></td>
		</tr>							
		<%
					}
	  %>
			<tr  class="text-globales" height="3">
				<td colspan="10"> </td>
			</tr>					
			<tr  class="fila-det-bold">
			 <td height="39" colspan="8">			   
			   <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
           <tr>
             <td width="12%"><input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();"></td>
             <td width="88%"><input name="confirmar" type="button" class="boton" id="confirmar2" value="Confirma" onClick="confirmarDetalle();"></td>
          </tr>
         </table></td>
			</tr>			
	</table>
<% } %>
   <input name="accion" value="" type="hidden">
	 <input name="usuario" type="hidden" id="usuario" value="<%= usuarioalt %>">
	 <input name="idempresa" type="hidden" id="usuario" value="<%= idempresa %>">	 
</form>
</body>
</html>
<% 

 }
catch (Exception ex) {
   //java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   //java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
  // ex.printStackTrace(pw);
  System.out.println("ERROR (DespachoStock.jsp) : "+ex);    
}%>