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
<%//@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "MOVIMIENTOS DE STOCK";
// variables de entorno
String pathskin = "../imagenes/default/";
String pathscript = "/scripts/";
String stockMostrarPrecios = "N" ;

// variables de paginacion
int i = 0;
Iterator iterProveedoArticulos   = null;
int totCol = 9; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = str.esNulo( request.getParameter("usuario")  != null ? request.getParameter("usuario")  + "" :   session.getAttribute("usuario") + "");
String idempresa = str.esNulo( request.getParameter("idempresa")  != null ? request.getParameter("idempresa")  + "" :   session.getAttribute("empresa") + "");;
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario")+"";
%>
<html>
<jsp:useBean id="BSCDA"  class="ar.com.syswarp.web.ejb.BeanINTERFACESCambioDepositoArea"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BSCDA" property="*" />
<%

	if(idempresa.equals("")) idempresa = "1";
	//if(usuarioalt.equals("")) usuarioalt = "INTERFAZ"; 
	if(usuarioalt.equals("")) response.sendRedirect("caduco.jsp"); 
	else{
	 if(usuarioalt.length()>17) usuarioalt = usuarioalt.substring(0, 16);
	 if (usuarioalt.indexOf(":IF") == -1) usuarioalt += ":IF" ;
	}

 BSCDA.setCodigo_dt_IN("50, 52, 92");
 BSCDA.setResponse(response); 
 BSCDA.setRequest(request);
 BSCDA.setSession(session); 
 BSCDA.setUsuarioalt( usuarioalt );
 //BSCDA.setIdcontadorcomprobante( new BigDecimal( session.getAttribute("idcontadorremitos1").toString() ));  
 BSCDA.setIdempresa( new BigDecimal( idempresa )); 
 BSCDA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <link rel="stylesheet" type="text/css" href="scripts/calendar/calendar.css">
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="scripts/calendar/calendarcode.js"></script>
 <script >
<!--


function agregarArt(codigo_st){
  document.frm.codigo_st.value = codigo_st;
  document.frm.accion.value = 'agregar';
  document.frm.submit();
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
<form action="deltaCambioDepositoBacoArea.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td class="text-globales"><%=titulo%></td> 
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
                                             </select>                                          </td>
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
	<tr>
	  <td  colspan="2" class="text-globales"><div align="center">
		  <input name="paginaSeleccion" type="hidden" value="1">
		P&aacute;gina: <%= Common.getPaginacion(BSCDA.getTotalPaginas(),
		BSCDA.getTotalRegistros(), BSCDA.getPaginaSeleccion(), BSCDA.getLimit(),
		BSCDA.getOffset()) %> </div></td>
	</tr>		
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BSCDA" property="mensaje"/>     </td>
  </tr>
</table>

<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" >&nbsp;</td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[8]%></div></td>		 
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
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
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/nuevo.gif" width="16" height="16" onClick="agregarArt('<%= sCampos[0] %>')"></div></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/gnome-calculator.png" width="18" height="18" onClick="abrirVentana('stockTotalDepositoLov.jsp?codigo_st=<%=sCampos[0]%>', 'totStock', 700, 200)"></div></td>
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
			
  	
   Hashtable htArtMovDepAreaIF = (Hashtable) session.getAttribute("htArtMovDepAreaIF");
	 if(htArtMovDepAreaIF != null && !htArtMovDepAreaIF.isEmpty()){
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
            <td height="15" colspan="4" class="fila-det-rojo">&nbsp;</td>
            </tr>
          <tr class="fila-det-bold">
            <td height="23" class="fila-det-border">Area Origen(*) </td>
            <td colspan="2" class="fila-det-border"><input name="areaorigen" type="text" class="campo" id="areaorigen" value="<%=BSCDA.getAreaorigen()%>" size="80" maxlength="100"  ></td>
            <td width="34%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_bacoAreasTransferencia.jsp?cmpCod=idareaorigen&cmpDesc=areaorigen','interfareas',800,400);">
              <input name="idareaorigen" type="hidden" id="idareaorigen" value="<%=BSCDA.getIdareaorigen()%>" ></td>
          </tr>
          <tr class="fila-det-bold">
            <td height="23" class="fila-det-border">Area Destino(*) </td>
            <td colspan="2" class="fila-det-border"><input name="areadestino" type="text" class="campo" id="areadestino" value="<%=BSCDA.getAreadestino()%>" size="80" maxlength="100"  ></td>
            <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_bacoAreasTransferencia.jsp?cmpCod=idareadestino&cmpDesc=areadestino','interfareas',800,400);">
              <input name="idareadestino" type="hidden" id="idareadestino" value="<%=BSCDA.getIdareadestino()%>" ></td>
          </tr>
          <tr class="fila-det-bold">
            <td height="15" colspan="4" class="fila-det-rojo"></td>
            </tr>
          <tr class="fila-det-bold">
            <td width="20%" height="23" class="fila-det-border">&nbsp;Fecha:</td>
            <td width="15%" class="fila-det-border"><span >
              <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechamov" value="<%= BSCDA.getFechamov() %>" maxlength="12">
              <a class="so-BtnLink" href="javascript:calClick();return false;" 
					 onMouseOver="calSwapImg('BTN_date_1', 'img_Date_OVER',true);" 
					 onMouseOut="calSwapImg('BTN_date_1', 'img_Date_UP',true);" 
					 onClick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fechamov','BTN_date_1');return false;"> <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></span></td>
            <td width="31%" class="fila-det-border">&nbsp;</td>
            <td class="fila-det-border"><span >
              <input name="tipomov" type="hidden" value="CI" class="campo" >
              <input name="mismorigdest" type="hidden"  class="campo" >
            </span></td>
          </tr>					
          <tr class="fila-det-bold">
            <td height="26" class="fila-det-border">&nbsp;Dep&oacute;sito Origen </td>
            <td height="26" colspan="3" class="fila-det-border"><select name="origengral" id="origengral" class="campo">
              <option value="-1">Seleccionar</option>
              <% 
																											            Iterator iter = BSCDA.getListDepositos().iterator();					
																																	while(iter.hasNext()){
																																			String keyDep[] = (String[]) iter.next();
																																			String sel = "";
																																			if(BSCDA.getOrigengral().toString().equals(keyDep[0])) sel = "selected";
																																			else sel = "";
																																			
																																			  %>
              <option value="<%= keyDep[0] %>" <%= sel %>><%= keyDep[1] %></option>
              <%
																																	}
																																																															
																																 %>
            </select></td>
          </tr>
          <tr class="fila-det-bold">
            <td height="26" class="fila-det-border">&nbsp;Dep&oacute;sito Destino </td>
            <td height="26" colspan="3" class="fila-det-border"><select name="destinogral" id="destinogral" class="campo">
              <option value="-1">Seleccionar</option>
              <% 
																											            iter = BSCDA.getListDepositos().iterator();					
																																	while(iter.hasNext()){
																																			String keyDep[] = (String[]) iter.next();
																																			String sel = "";
																																			if(BSCDA.getDestinogral().toString().equals(keyDep[0])) sel = "selected";
																																			else sel = "";
																																			
																																			  %>
              <option value="<%= keyDep[0] %>" <%= sel %>><%= keyDep[1] %></option>
              <%
																																	}
																																																															
																																 %>
            </select></td>
          </tr>
          <tr class="fila-det-bold">
            <td height="26" class="fila-det-border">&nbsp;Observaciones:</td>
            <td height="26" colspan="3" class="fila-det-border">
						<textarea name="observaciones" cols="80" rows="2" class="campo" id="observaciones"><%= BSCDA.getObservaciones() %></textarea></td>
          </tr>
          <tr class="fila-det-bold">
            <td height="26" class="fila-det-border">&nbsp;</td>
            <td height="26" colspan="3" class="fila-det-border">&nbsp;</td>
            </tr>
        </table>				</td>
			</tr>
			<tr  class="text-globales" height="3">
				<td colspan="10"> </td>
			</tr>	
							
			<tr  class="fila-det-bold">
			 <td width="3%">&nbsp;Sel.</td>
				<td width="6%">&nbsp; Cód.</td>
				<td width="9%">&nbsp; Alias</td>
				<td width="56%">&nbsp; Descripción</td>
			 
			  <!--td width="14%">&nbsp;</td>				
				<td width="15%">&nbsp;</td-->

				<td width="26%">&nbsp; Cantidad</td>
			</tr>
			<tr  class="text-globales" height="3">
				<td colspan="10"> </td>
			</tr>		
		<%
	   			Enumeration en = Common.getSetSorted (htArtMovDepAreaIF.keySet());
					while (en.hasMoreElements()) {
						String key = (String) en.nextElement();
						String [] articulos = (String []) htArtMovDepAreaIF.get(key);
						/*
						Hashtable htDepositos = BSCDA.getHtDepositos();
						Enumeration enumDepositos = Common.getSetSorted (htDepositos.keySet());*/

						  %>
		<tr class="fila-det">
			<td class="fila-det-border"><input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo"></td>
			<td class="fila-det-border">&nbsp;<%= articulos[0] %><input  name="keyHashDatosArticulo" type="hidden" value="<%= key %>"></td>
			<td class="fila-det-border">&nbsp;<%= articulos[1] %></td>
			<td class="fila-det-border">&nbsp;<%= articulos[2] %></td>
 		  <!--td class="fila-det-border">&nbsp;</td>
			<td class="fila-det-border">&nbsp;</td-->
			<td class="fila-det-border">&nbsp;
		  <input type="text" class="campo" name="cantidad" value="<%= articulos[4] %>" size="10">
		  <input name="origen" type="hidden" id="origen" value="<%= articulos[5] %>">
		  <input name="destino" type="hidden" id="destino" value="<%= articulos[6] %>"></td>
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
   <input name="primeraCarga" value="false" type="hidden">	 
   <input type="hidden" name="codigo_st" value="">
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
  System.out.println("ERROR (deltaCambioDepositoBacoArea.jsp) : "+ex);    
}%>