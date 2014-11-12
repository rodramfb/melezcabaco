<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: cajaIdentificadores
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Dec 18 15:14:22 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*"%> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Enumeration en = null;
Strings str = new Strings();
String color_fondo ="";
String titulo = "IDENTIFICADORES";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterCajaIdentificadores   = null;
int totCol = 36; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCIA"  class="ar.com.syswarp.web.ejb.BeanLovCajaIdentificadoresSalidaPagos"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCIA" property="*" />
<%
 BCIA.setResponse(response);
 BCIA.setRequest(request);
 BCIA.setSession(session);
 BCIA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCIA.ejecutarValidacion();
%>
<head>
 <title></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
//-->
 </script>
  <script>
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
	
	function generarAdelanto(){
		if(confirm("Confirma ingreso de Adelanto ?")){
				document.frm.accion.value = "adelanto";
				document.frm.submit();
		}
	}

  function addIdentificador( identificador ){
    document.frm.accion.value = 'agregar';
    document.frm.identificador.value = identificador ;
    document.frm.submit();
  }
 </script>
 
</head>
<%
// titulos para las columnas
// id / descripcion / cuenta cont / moneda  / tipomov / propio
tituCol[0] = "Cód.";
tituCol[1] = "Tipo";
tituCol[2] = "Identificador";
tituCol[3] = "Descripcion";
tituCol[4] = "Cuenta";
tituCol[5] = "idmoneda";
tituCol[6] = "Tipo Mov."; 
tituCol[7] = "Propio";
tituCol[8] = "modcta";
tituCol[9] = "factura";
tituCol[10] = "saldo_id";
tituCol[11] = "saldo_disp";
tituCol[12] = "renglones";
tituCol[13] = "ctacaucion";
tituCol[14] = "ctatodoc";
tituCol[15] = "gerencia";
tituCol[16] = "formula";
tituCol[17] = "cuotas";
tituCol[18] = "presentacion";
tituCol[19] = "ctacaudoc";
tituCol[20] = "porcentaje";
tituCol[21] = "ctadtotar";
tituCol[22] = "ctatarjeta";
tituCol[23] = "comhyper";
tituCol[24] = "contador";
tituCol[25] = "afecomicob";
tituCol[26] = "impri_id";
tituCol[27] = "subdiventa";
tituCol[28] = "idcencosto";
tituCol[29] = "idcencosto1";
tituCol[30] = "modicent";
tituCol[31] = "prox_cheq";
tituCol[32] = "prox_reserv";
tituCol[33] = "ulti_cheq";
tituCol[34] = "modsubcent";
tituCol[35] = "res_nro";
java.util.List CajaIdentificadores = new java.util.ArrayList();
CajaIdentificadores= BCIA.getCajaIdentificadoresList();
iterCajaIdentificadores = CajaIdentificadores.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_cajaIdentificadoresSalidaPagos.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="3%" height="38">&nbsp;                  </td>
                   <td width="97%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BCIA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCIA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCIA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCIA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCIA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCIA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCIA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCIA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" ><div align="center"></div></td>
		   <!-- 
			 // id / descripcion / cuenta cont / moneda  / tipomov / propio
			  -->		 
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="31%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="44%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
    </tr>
   <%int r = 0;
   while(iterCajaIdentificadores.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterCajaIdentificadores.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center">
			<%if(  sCampos[6].equalsIgnoreCase("S") || 
			      (sCampos[6].equalsIgnoreCase("D") && sCampos[7].equalsIgnoreCase("N") ) || 
					  (sCampos[6].equalsIgnoreCase("C") && sCampos[7].equalsIgnoreCase("N") ) 
					){  %>
					 <img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" title="<%=sCampos[1].replace ('.', ' ')%> a cancelar." onClick="abrirVentana('lov_cajaMovimientosCancelar.jsp?tipcart_mt=<%= sCampos[6] %>&cartera_mt=<%= sCampos[2] %>&cartera=<%= sCampos[1] %>', 'buscarmov', 700, 350)"> 

			<%}
			   else{%>
			    <img src="../imagenes/default/nuevo.gif" width="16" height="16" onClick="addIdentificador('<%= sCampos[2] %>');">
			<%}%>				 
      </div></td> 
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
    </tr>
<%
   }%>
  </table>
&nbsp;
     <% 

  Hashtable htIdentificaSalidaPagos = (Hashtable) session.getAttribute("htIdentificaSalidaPagos");
  Hashtable htMovimientosCancelarOK = (Hashtable) session.getAttribute("htMovimientosCancelarOK");
	if((htIdentificaSalidaPagos != null && !htIdentificaSalidaPagos.isEmpty() ) 
	   || 
     (htMovimientosCancelarOK != null && !htMovimientosCancelarOK.isEmpty() )		  
		){ %>
   <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1">
     <tr class="text-globales">

       <td width="2%">Sel</td>
       <td width="6%"><div align="center">Ide</div></td>
       <td width="7%"><div align="center">N&uacute;mero</div></td>  
       <td width="19%"><div align="center">Detalle</div></td>
       <td width="43%">Cuentas. </td>
       <td width="6%"><div align="center">Fecha</div></td>
       <td width="5%"><div align="center">Cuotas</div></td>
       <td width="7%"><div align="justify">Importe</div></td>
       <td width="5%"><div align="center">Clearing</div></td>
     </tr>
     <%
				if(htIdentificaSalidaPagos != null && !htIdentificaSalidaPagos.isEmpty() ){		 
	   			en = htIdentificaSalidaPagos.keys();
					int element = 0;
					while (en.hasMoreElements()) {
					  
						String key = (String) en.nextElement();
						String [] identificador = (String []) htIdentificaSalidaPagos.get(key);
						//for(int m=0;m<identificador.length;m++) System.out.println("~: identificador[" + m + "]: " + identificador[ m ]); 
						String nro_cuenta = (identificador[4] + "-" + identificador[37]);
						  %>
     <tr class="fila-det">
       <td height="22" class="fila-det-border">&nbsp;
         <input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo">       </td>
       <td class="fila-det-border"> &nbsp;<%= identificador[2] %>
           <input  name="keyHashDatosIdentificador" type="hidden" value="<%= key %>">
           <input name="cc1" type="hidden" value="<%= identificador[20] %>" class="campo" id="cc1" size="5" maxlength="10" <%= !str.esNulo(identificador[22]).equalsIgnoreCase("S") ? "readonly" :  "" %>>
           <input name="cc2" type="hidden" value="<%= identificador[21] %>" class="campo" id="cc2" size="5" maxlength="10" <%= !str.esNulo(identificador[23]).equalsIgnoreCase("S") ? "readonly" :  "" %>></td>
       <td class="fila-det-border"><input name="chequenro" type="text" value="<%= identificador[30] %>" class="campo" id="chequenro" size="10" maxlength="10" <%= str.esNulo( identificador[6] ).equalsIgnoreCase("C") ? "": "readonly"%> style="text-align:right"></td>
       <td class="fila-det-border"><input name="detalle" type="text" value="<%= identificador[29] %>" class="campo" id="detalle" size="30" maxlength="50"  <%= str.esNulo( identificador[6] ).equalsIgnoreCase("C") || str.esNulo( identificador[6] ).equalsIgnoreCase("C") ? "": "readonly"%>></td>
       <td class="fila-det-border"><%= nro_cuenta.length() > 25 ? nro_cuenta.substring(0, 25  ) : nro_cuenta  %>
			   <input name="cuenta" type="hidden" id="cuenta<%=element %>" value="<%= identificador[4] %>" class="campo" readonly>
         <input name="descripcion_cuenta" type="hidden" id="descripcion_cuenta<%=element %>" value="<%= identificador[37] %>" class="campo" readonly>
         <% if( str.esNulo(identificador[8]).equalsIgnoreCase("S")){%>
         <img src="../imagenes/default/gnome_tango/apps/accessories-text-editor.png" width="18" height="18" onClick="abrirVentana('lov_contableInfiPlanAbm.jsp?elemento=<%=element %>&ocurrencia=' + document.getElementById ( 'cuenta<%=element %>' ).value , 'plancuentas',700, 200)" style="cursor:pointer" onMouseOver="overlib(  '<%=nro_cuenta %>' , STICKY, CAPTION, 'CUENTA ASOCIADA:  ',TIMEOUT,5000,HAUTO,VAUTO);">
         <% } else out.write("&nbsp;");%></td>
       <td class="fila-det-border"><input name="fecha" type="text" value="<%= identificador[31] %>" class="campo" id="fecha" size="10" maxlength="10" <%= str.esNulo( identificador[6] ).equalsIgnoreCase("C") ? "": "readonly"%> title="Formato: dd/mm/yyyy "></td>
       <td class="fila-det-border"><div align="center">
         <input name="cuotas" type="text" id="cuotas" value="<%= identificador[20] %>" size="3" maxlength="2" class="campo" <%= str.esNulo( identificador[6] ).equalsIgnoreCase("T") ? "": "readonly"%>>
       </div></td>
       <td class="fila-det-border">         <input name="importeingreso" type="text" value="<%= identificador[28] %>" class="campo" size="10" maxlength="15" style="text-align:right">
       </td>
       <td align="center"  class="fila-det-border"><div align="center">
         <div align="center">
           <input name="clearing" type="hidden" id="clearing<%=element %>" value="<%= identificador[32] %>" >
           <% if( str.esNulo(identificador[6]).equalsIgnoreCase("C")){%>
           <img src="../imagenes/default/gnome_tango/apps/date.png" width="18" height="18" onClick="abrirVentana('lov_cajaClearingAbm.jsp?elemento=clearing<%=element %>' , 'clearing',700, 200)" style="cursor:pointer" onMouseOver="this.title='Clearing seleccionado: ' + document.getElementById ( 'clearing<%=element %>' ).value"></div>
         <% } else out.write("&nbsp;");%>
       </div>			 </td>
     </tr>
 <% 
  }
		 
		 				element++;
	        }

	if(htMovimientosCancelarOK != null && !htMovimientosCancelarOK.isEmpty()){
	    %>
     <tr class="permiso-cuatro" >
       <td height="3" class="fila-det-border">       </td>
       <td height="3" class="fila-det-border"></td>
       <td height="3" class="fila-det-border"></td>
       <td height="3" class="fila-det-border"></td>
       <td class="fila-det-border"></td>
       <td height="3" class="fila-det-border"></td>
       <td height="3" class="fila-det-border"></td>
       <td height="3" class="fila-det-border"></td>
       <td height="3" class="fila-det-border"></td>
     </tr>
			
			<%
		en = htMovimientosCancelarOK.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String [] movimientos = (String []) htMovimientosCancelarOK.get(key);
						  %>
     <tr class="fila-det">
       <td height="22" class="fila-det-border"><input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo">       </td>
       <td class="fila-det-border"> &nbsp;<%= key %>
           <input  name="keyHashDatosIdentificador" type="hidden" value="<%= key %>">
           <input name="cc1" type="hidden" value="<%= movimientos[20] %>" class="campo" id="cc1" size="5" maxlength="10" readonly>
           <input name="cc2" type="hidden" value="<%= movimientos[21] %>" class="campo" id="cc2" size="5" maxlength="10" readonly></td>
       <td class="fila-det-border"><input name="chequenro" type="text" value="<%= movimientos[37] %>" class="campo" id="chequenro" size="10" maxlength="10" readonly style="text-align:right"></td>
       <td class="fila-det-border"><input name="detalle" type="text" value="<%= movimientos[29] %>" class="campo" id="detalle" size="30" maxlength="50"  readonly></td>
       <td class="fila-det-border">&nbsp;</td>
       <td class="fila-det-border"><input name="fecha" type="text" value="<%= movimientos[31] %>" class="campo" id="fecha" size="10" maxlength="10" readonly title="Formato: dd/mm/yyyy "></td>
       <td class="fila-det-border"><div align="center">
         <input name="cuotas" type="text" id="cuotas" value="<%= movimientos[20] %>" size="3" maxlength="2" class="campo" readonly>
       </div></td>
       <td class="fila-det-border">         <input name="importeingreso" type="text" value="<%= movimientos[28] %>" class="campo" size="10" maxlength="15" readonly style="text-align:right">
       </td>
       <td align="center" class="fila-det-border">&nbsp;			 </td>
     </tr>

              <% 
		}
	}
	%>		 

     <tr class="fila-det">
       <td height="33" class="fila-det-border">&nbsp;</td>
       <td colspan="2" class="fila-det-border"><input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();">       </td>
       <td  class="fila-det-border"><input name="confirmar" type="button" class="boton" id="confirmar" value="Confirma" onClick="confirmarDetalle();"></td>
       <td  class="fila-det-border">&nbsp;</td>
       <td  class="fila-det-border">&nbsp;</td>
       <td  class="fila-det-border">&nbsp;</td>
       <td  class="fila-det-border">&nbsp;</td>
       <td  class="fila-det-border">&nbsp;</td>
     </tr>
  </table>
   <% 
	}
	%>
	 
		
   <p>
     <input name="accion" value="" type="hidden">
      <input name="tipomov" value="<%= BCIA.getTipomov()%>" type="hidden">
	    <input name="propio" value="<%= BCIA.getPropio()%>" type="hidden">
      <input type="hidden" name="identificador" value="">
   </p>
   <%  
			if(BCIA.getAccion().equalsIgnoreCase("confirmar")  
			   && BCIA.getMensaje().equals("") ){%>
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

