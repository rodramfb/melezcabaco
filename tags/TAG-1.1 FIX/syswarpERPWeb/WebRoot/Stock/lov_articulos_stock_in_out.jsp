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
<%@ page import="java.math.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "ARTICULOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
String stockMostrarPrecios = session.getAttribute("stockMostrarPrecios").toString();

// variables de paginacion
int i = 0;
Iterator iterProveedoArticulos   = null;
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPAA"  class="ar.com.syswarp.web.ejb.BeanMovInOutArticulosLov"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPAA" property="*" />
<%
 BPAA.setResponse(response);
 BPAA.setRequest(request);
 BPAA.setSession(session);
 BPAA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPAA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script >
<!--

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

function agregar(codigo_st){

  document.frm.codigo_st.value = codigo_st;
	document.frm.accion.value = 'agregar';
	document.frm.submit();
 
}


function callAbrirVentana(url,  tipomov, iddeposito){
  if(tipomov == 'E'){
     abrirVentana(url, 'SD', 750, 450);
  } else {
     var objDeposito = eval(document.getElementById(iddeposito)); 
     if(objDeposito.selectedIndex == 0){
        alert('Seleccione depósito.');
     }else{
       url+= '&codigo_dt=' + objDeposito.options [objDeposito.selectedIndex].value
       abrirVentana(url, 'SD', 750, 450);
     }
  }
}

function resetSerieDeposito(key, obj, deposito, cantidad, id_indi_st, despa_st, idcantidad){
  if(deposito > 0 || cantidad != 0 ){
    if(confirm('Esta acción eliminará los datos cargados anteriormente para este artículo.\nContinua de todos modos?')){
		  document.frm.accion.value = 'cambiodeposito';
      document.getElementById(idcantidad).value = 0;
      document.getElementById('relacionHash').value = key;
		  document.frm.submit();       
    } else {
      for(var r=0;r<obj.length;r++){
        //alert('obj.options['+r+'].value: ' + obj.options[r].value + '  *** - *** deposito: ' + deposito);
        if(obj.options[r].value == deposito){ 
          obj.options[r].selected = true;
          break;
        }
      }
    }
  }
}

window.onload = function(){
  // Cambio aplicado 20100121
	var accion = '<%= BPAA.getAccion() %>';
	
	if(accion == 'cambiodeposito')
		opener.document.frm.submit(); 

}

//-->
</script>
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
tituCol[8] = "Ult. Comp.";
tituCol[9] = "Cta. Comp.";
tituCol[10] = "Serie";
tituCol[11] = "Despacho";
java.util.List ProveedoArticulos = new java.util.ArrayList();
ProveedoArticulos= BPAA.getProveedoArticulosList();
iterProveedoArticulos = ProveedoArticulos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_articulos_stock_in_out.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td ><%=titulo%></td>
                </tr>
                <tr>
                   <td width="89%" height="38">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BPAA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BPAA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=10; i<= 100 ; i+=10){%>
                                                    <%if(i==BPAA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BPAA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BPAA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BPAA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BPAA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo">     <jsp:getProperty name="BPAA" property="mensaje"/>     </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" >
		   <div align="center"></div>     </td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="43%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[10]%></div></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[11]%></div></td>
	 <%if(stockMostrarPrecios.equalsIgnoreCase("S")){%>		 
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[4]%></div></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[5]%></div></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%></div></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[7]%></div></td>
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
      <td class="fila-det-border" >
			  <div align="center">
				<%if(!str.esNulo(sCampos[9]).equals("")){%><%--
			    <input type="radio" name="codigo_st" value="<%= sCampos[0]%>">
			     <input name="agregar" id="agregar" value="agregar" type="image" src="../imagenes/default/nuevo.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('agregar','','../imagenes/default/nuevo.gif',1)" onClick="document.frm.accion.value = this.name"> --%>
          <img src="../imagenes/default/nuevo.gif" width="16" height="16" title="Agregar...." onClick="agregar('<%= sCampos[0] %>')" style="cursor:pointer">
        <%} else{%>		
				<img src="../imagenes/default/gnome_tango/status/stock_dialog-warning.png" width="18" height="18" title="Articulo no posee asociada cuenta de compras .....">		  
        <%} %>				  				
		</div>	  </td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[10]%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[11]%>&nbsp;</div></td>
	  <%if(stockMostrarPrecios.equalsIgnoreCase("S")){%>
      <td class="fila-det-border" >&nbsp;
        <div align="right"><%=sCampos[4]%></div></td>
      <td class="fila-det-border" >&nbsp;
        <div align="right"><%=sCampos[5]%></div></td>
      <td class="fila-det-border" >&nbsp;
        <div align="right"><%=sCampos[6]%></div></td>			
      <td class="fila-det-border" >&nbsp;
        <div align="right"><%=sCampos[7]%></div></td>	
	<%}%> 		
   </tr>
<%
   }%>
  </table>
		 &nbsp; 
		<table width="100%"  border="0" cellspacing="1" cellpadding="0">
			<tr  class="text-globales" height="3">
				<td colspan="11"> </td>
			</tr>		
			<tr  class="fila-det-bold">
			 <td width="3%">&nbsp;Sel.</td>
				<td width="5%">&nbsp; Cód.</td>
				<td width="5%">&nbsp; Alias</td>
				<td width="60%">&nbsp; Descripción</td>
			 <td width="10%">D&eacute;posito</td>				
				<td width="5%"><div align="right">Cantidad&nbsp; </div></td>
			  <td width="2%"><div align="center">S-D</div></td>
			</tr>
			<tr  class="text-globales" height="3">
				<td colspan="11"> </td>
			</tr>	

	 <% 
   Hashtable htArticulosInOut = (Hashtable) session.getAttribute("htArticulosInOut");
	 if(htArticulosInOut != null && !htArticulosInOut.isEmpty()){
	   %> 	 

	
		<%
	   			Enumeration en = htArticulosInOut.keys();
					while (en.hasMoreElements()) {
						String key = (String) en.nextElement();
						String [] articulos = (String []) htArticulosInOut.get(key);
						String link = "";
						/*Hashtable htDepositos = BPAA.getHtDepositos();
						Enumeration enumDepositos = htDepositos.keys();*/
            String imgSerieDespacho = "";
            String idcantidad = "cantidad" + articulos[0]+key;
            String jsOnChangeDep = "";
            if(Common.setNotNull(articulos[17]).equalsIgnoreCase("S") && 
               Common.setNotNull(articulos[18]).equalsIgnoreCase("S"))
              imgSerieDespacho = "../imagenes/default/gnome_tango/actions/gtk-find-and-replace.png";
            else if(Common.setNotNull(articulos[17]).equalsIgnoreCase("S") )
              imgSerieDespacho = "../imagenes/default/gnome_tango/apps/text-editor.png";
            else if(Common.setNotNull(articulos[18]).equalsIgnoreCase("S") )
              imgSerieDespacho = "../imagenes/default/gnome_tango/actions/stock_paste.png";

						if(BPAA.getTipomov().equalsIgnoreCase("E")){ 
							link = "lov_stockSerieDespachoEntrada.jsp?relacionHash=" + key +"&id_indi_st=" +  articulos[17] + "&despa_st="+ articulos[18] + "&idcantidad="+ idcantidad +"&codigo_st="+ articulos[0] + "&descrip_st="+ articulos[2] ;
						}
						else if(BPAA.getTipomov().equalsIgnoreCase("S")){ 
							link = "lov_stockSerieDespachoSalida.jsp?relacionHash=" + key +"&id_indi_st=" +  articulos[17] + "&despa_st="+ articulos[18] + "&idcantidad="+ idcantidad +"&codigo_st="+ articulos[0] + "&descrip_st="+ articulos[2]  ;
							if( !imgSerieDespacho.equals("") ){
								jsOnChangeDep = "onChange=\"resetSerieDeposito('"+ key +"', this, "+ articulos[9] +", '"+ articulos[10] +"', '"+ articulos[17] +"', '" + articulos[18] +"', '" + idcantidad +"' )\"";
							}
						}
             
            String iddeposito = "deposito" + key;

						%>
		<tr class="fila-det">
			<td class="fila-det-border"><input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo"></td>
			<td class="fila-det-border">&nbsp;<%= articulos[0] %><input  name="keyHashDatosArticulo" type="hidden" value="<%= key %>"></td>
			<td class="fila-det-border">&nbsp;<%= articulos[1] %>  </td>
			<td class="fila-det-border">&nbsp;<%= articulos[2] %></td>
		 <td class="fila-det-border"><select name="deposito" id="<%= iddeposito %>" class="campo" style="width:100px" <%= jsOnChangeDep %>>
																																<option value="-1">Seleccionar</option>
																																<% 
																											            Iterator iter = BPAA.getListDepositos().iterator();					
																																	while(iter.hasNext()){
																																			String keyDep[] = (String[]) iter.next();
																																			String sel = "";

																																			if(articulos[9].equals(keyDep[0])) sel = "selected";
																																			else sel = "";
																																			
																																			  %>
																													        <option value="<%= keyDep[0] %>" <%= sel %>><%= keyDep[1] %></option>
																																					<%
																																	}
																																																															
																																 %>
																														 </select>		 </td>
			
			<td class="fila-det-border"><div align="right">
			  <input name="cantidad" id="<%= idcantidad %>" type="text" class="campo" style="text-align:right" value="<%= articulos[10] %>" size="7" maxlength="6" <%= !Common.setNotNull(imgSerieDespacho).equalsIgnoreCase("") ? "readonly" : "" %> >
	    </div></td>
		  <td class="fila-det-border">
       <div align="center">
      <%if(!Common.setNotNull(imgSerieDespacho).equalsIgnoreCase("")){%>
         <img src="<%= imgSerieDespacho %>" width="18" height="18" title="Serie/Despacho" style="cursor:pointer" onClick="callAbrirVentana('<%= link %>' , '<%= BPAA.getTipomov() %>', '<%= iddeposito %>')">
         <%}%>
       </div>
      </td>
		</tr>							
		<%
					}
	  %>
			<tr  class="text-globales" height="3">
				<td colspan="11"> </td>
			</tr>					

<% } %>

			<tr  class="fila-det-bold">
			 <td height="40" colspan="9">
			   <input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();"> 			 <input name="confirmar" type="button" class="boton" id="confirmar" value="Confirma" onClick="confirmarDetalle();"></td>
		  </tr>			
	</table>
   <input name="accion" value="" type="hidden">
   <input type="hidden" name="codigo_st" id="codigo_st" value="">
   <input type="hidden" name="relacionHash" id="relacionHash" value="">
   <input type="hidden" name="tipomov" id="tipomov" value="<%= BPAA.getTipomov() %>">
			
			<%  
			if(BPAA.getAccion().equalsIgnoreCase("confirmar")  
			   && BPAA.getMensaje().equals("") ){%>
			  <script>
					   opener.document.frm.submit();
								this.close();
					</script>			
			<%  
			}%>
			
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

