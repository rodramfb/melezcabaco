marketStockSinAgrupar
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="27%" valign="top"><jsp:include page="index.jsp" flush="true" /></td>
    <td width="73%">
		<form action="marketStockXGrupo.jsp" method="POST" name="frmStockXGrupo">
			<table width="90%"  border="0" cellspacing="0" cellpadding="0">
				<tr >
					<td class="fila-head" width="28%" >P&aacute;gina:
					  <select name="paginaSeleccion" class="combo-size-var">
              <%for(i=1; i<= BMSXG.getTotalPaginas(); i++){%>
              <%if ( i==BMSXG.getPaginaSeleccion() ){%>
              <option value="<%=i%>" selected><%=i%></option>
              <%}else{%>
              <option value="<%=i%>"><%=i%></option>
              <%}%>
              <%}%>
            </select></td>
					<td width="22%" class="fila-head"><input name = "cmpBuscar" id="cmpBuscar" type="text" value="<%= BMSXG.getCmpBuscar() %>"/> </td>
					<td width="40%" class="fila-head"><input name="ir" type="submit" id="ir" value="  >>  " class="boton" /></td>
				  <td width="22%" class="fila-head">Total de registros:&nbsp;</td>
				  <td width="10%" class="fila-head"><%= BMSXG. getTotalRegistros( ) %></td>
				</tr>
				<tr >
				  <td colspan="4" class="mensaje">Familia: <span class="fila-head"><%= BMSXG. getDescrip_fm( ) %></span> -  Grupo:<span class="fila-head"><%= BMSXG. getDescrip_gr( ) %></span></td>
			  </tr> 
				<tr >
				  <td colspan="4" class="mensaje"><jsp:getProperty name="BMSXG" property="mensaje"/>   
			      <input type="hidden" name="codigo_fm" id="codigo_fm" value="<%= BMSXG.getCodigo_fm() %>" />					       
						<input name="codigo_gr" type="hidden" id="codigo_gr" value="<%=BMSXG.getCodigo_gr()%>" />
						<input name="cmpBuscar" type="hidden" id="cmpBuscar" value="<%=BMSXG.getCmpBuscar()%>" />
						<input name="codigo_st" type="hidden" id="codigo_st" value="" />
						&nbsp;
				  </td>
				</tr>
			</table>
			<table width="90%" border="0" cellspacing="0" cellpadding="0"  >
				<tr class="fila-head">
					 <td width="4%">&nbsp;</td>
					 <td width="15%"><%=tituCol[0]%></td>
					 <td width="58%"><%=tituCol[2]%></td>
					 <td width="18%"><div align="right"><%=tituCol[4]%></div></td>
			         <td width="5%">&nbsp;</td>
			  </tr>
				 <%
				 while(iterStockstock.hasNext()){
						String[] sCampos = (String[]) iterStockstock.next();%>
				 <tr> 
						<td >&nbsp;</td>
						<td class="fila-det"><%=sCampos[0]%></td>
						<td ><a href="javascript:sendToDetalle('<%=sCampos[0]%>')" class="link"><%=sCampos[2]%></a>&nbsp;</td>
						<td class="fila-det"><div align="right"><%=sCampos[4]%></div></td>
						<% 
						 String imagenPath =""; 
						 if (sCampos[13]==null){ 
						         imagenPath = "../imagenes/default/gnome_tango/devices/imagen-no-disponible.jpg";
						      }   
						    else {
						    imagenPath ="../imagenes/general/" + sCampos[13];
						    }    
						%>
						
				        <td class="fila-det"><div align="right"><a href="<%=imagenPath%>" rel="lightbox"><img src="../imagenes/default/gnome_tango/devices/camera-photo.png" alt="Imagen" width="22" height="22" border="0" /></a></div></td>
				 </tr>
			<%
				 }%>
			  </table>	
	 </form>
		</td>
  </tr> 
</table>
	
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR (" + pagina + ") : "+ex);   
}%>
