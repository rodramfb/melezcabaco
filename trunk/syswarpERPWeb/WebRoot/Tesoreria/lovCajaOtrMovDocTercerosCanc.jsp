<!--fecha  detalle  importe numero-->
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
Strings str = new Strings();
String color_fondo ="";
String titulo = "DOCUMENTOS A CANCELAR ";
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
<jsp:useBean id="BCIA"  class="ar.com.syswarp.web.ejb.BeanLovCajaOtrMovDocTercerosCanc"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCIA" property="*" />
<%
	BCIA.setResponse(response);
	BCIA.setRequest(request);
	BCIA.setSession(session);
	BCIA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
	BCIA.ejecutarValidacion();
	titulo += " - " + BCIA.getCartera().toUpperCase() ;
	
	// titulos para las columnas
	// id / descripcion / cuenta cont / moneda  / tipomov / propio
	tituCol[0] = "Cód.";
	tituCol[1] = "Fecha";
	tituCol[2] = "Detalle";
	tituCol[3] = "Importe";
	tituCol[4] = "Número";
	tituCol[5] = "Proveedor";
	tituCol[6] = "Proveedor";
	java.util.List CajaIdentificadores = new java.util.ArrayList();
	CajaIdentificadores= BCIA.getCajaIdentificadoresList();
	iterCajaIdentificadores = CajaIdentificadores.iterator(); 
	Hashtable htOMDocTercerosCancelarIN = (Hashtable) session.getAttribute("htOMDocTercerosCancelarIN");
%>

<head>
 <title><%= titulo %></title>
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

  function addDocumento( idmovteso ){
    document.frm.accion.value = 'agregar';
    document.frm.idmovteso.value = idmovteso ;
    document.frm.submit();
  }

  function cambioSeleccion(objCmb){
    var _reset = <%= ( htOMDocTercerosCancelarIN != null &&   !htOMDocTercerosCancelarIN.isEmpty()  && htOMDocTercerosCancelarIN.size() >0 ) ? "true" : "false"  %>
    if(_reset){
      var index = 0;
      for(index=0;index<objCmb.options.length;index++){
        if(objCmb.options[index].value == '<%= BCIA.getTipodoc() %>') break;
      }
 
      if(confirm('Ya ingreso documentos del tipo: '  + objCmb.options[index].text + ', estos se eliminarán.\nContinua de todas formas?') ){
        document.frm.accion.value = 'truncar';
        document.frm.submit();
      }
      else objCmb.selectedIndex = index; 
    }    
  }

 </script>
 
  <!-- Copyright 2001, 2002, 2003 Macromedia, Inc. All rights reserved. -->
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lovCajaOtrMovDocTercerosCanc.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales"><%=titulo%></td> 
                </tr>
                <tr>
                   <td width="3%" height="38"><table width="100%" border="0">
                     <tr>
                       <td width="6%" height="26" class="text-globales">Buscar</td>
                       <td width="22%"><input name="ocurrencia" type="text" value="<%=BCIA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                       </td>
                       <td width="72%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                           <tr>
                             <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                 <tr class="text-globales">
                                   <td width="23%" height="19">&nbsp;Total de registros:&nbsp;<%=BCIA.getTotalRegistros()%></td>
                                   <td width="11%" >Visualizar:</td>
                                   <td width="11%"><select name="limit" >
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
                                     </select>                                   </td>
                                   <td width="7%">&nbsp;P&aacute;gina:</td>
                                   <td width="12%"><select name="paginaSeleccion" >
                                       <%for(i=1; i<= BCIA.getTotalPaginas(); i++){%>
                                       <%if ( i==BCIA.getPaginaSeleccion() ){%>
                                       <option value="<%=i%>" selected><%=i%></option>
                                       <%}else{%>
                                       <option value="<%=i%>"><%=i%></option>
                                       <%}%>
                                       <%}%>
                                     </select>                                   </td>
                                   <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                 </tr>
                             </table></td>
                           </tr>
                       </table></td>
                     </tr>
                   </table></td>
              </tr>
                <tr>
                  <td height="38"><table width="100%" border="0">
                    <tr>
                      <td width="6%" height="26" class="text-globales">Tipo</td>
                      <td width="22%">
                      <select name="tipodoc" class="campo" onChange="cambioSeleccion(this);">
                        <option value="">Seleccionar</option>
                        <!--option value="D" <%= BCIA.getTipodoc().equalsIgnoreCase("D") ? "selected" : ""  %>>Descontados</option-->
                        <option value="E" <%= BCIA.getTipodoc().equalsIgnoreCase("E") ? "selected" : ""  %>>Endosados</option>
                      </select>
                      </td>
                      <td width="72%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td width="32%" height="19">&nbsp;</td>
                                  <td width="14%" >&nbsp;</td>
                                  <td width="13%">&nbsp;</td>
                                  <td width="11%">&nbsp;</td>
                                  <td width="16%">&nbsp;</td>
                                  <td width="14%" class="text-globales">&nbsp;</td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                  </table></td>
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
     <td width="5%" ><div align="center"></div></td>
		   <!-- 
			 // id / descripcion / cuenta cont / moneda  / tipomov / propio
			  -->		 
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="44%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
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
        <input name="agregar" type="image" id="agregar"  value="agregar" src="../imagenes/default/nuevo.gif" width="16" height="16"  onClick="addDocumento('<%= sCampos[0] %>');">
      </div></td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[1]), "JSTsToStr")%>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[2])%>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[3])%>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[4])%>&nbsp;</td>
      <td class="fila-det-border" ><%=str.esNulo(sCampos[5])%>&nbsp;</td>
   </tr>
<%
   }%>
  </table>
 
     <% 
  //Hashtable htOMDocTercerosCancelarIN = (Hashtable) session.getAttribute("htOMDocTercerosCancelarIN");
	if(htOMDocTercerosCancelarIN != null && !htOMDocTercerosCancelarIN.isEmpty()){
	   %>

   <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1">
     <tr class="text-globales">

       <td width="3%">Sel</td>
       <td width="5%"><div align="center">Ide</div></td>
       <td width="6%"><div align="center">CC1</div></td>

       <td width="4%"><div align="center">CC2</div></td>
       <td width="11%"><div align="center">N&uacute;mero</div></td>
       <td width="40%"><div align="center">Detalle</div></td>
       <td width="8%">Clearing</td>
       <td width="6%"><div align="center">Fecha</div></td>
       <!--td width="6%"><div align="center">Cuotas</div></td-->
       <td width="6%"><div align="justify">Importe</div></td>
       <td width="5%"><div align="center">Ctas. </div></td>
     </tr>
     <%   
	   			Enumeration en = htOMDocTercerosCancelarIN.keys();
					int element = 0;
					while (en.hasMoreElements()) {
						String key = (String) en.nextElement();
						String [] identificador = (String []) htOMDocTercerosCancelarIN.get(key); 
						
						  %>
     <tr class="fila-det">
       <td height="22" class="fila-det-border"> 
         <input name="delKey" type="checkbox" id="delKey" value="<%= key %>" class="campo">
       </td>
       <td class="fila-det-border">  <%= key %>
           <input  name="keyHashDatosIdentificador" type="hidden" value="<%= key %>"></td>
					 
       <td class="fila-det-border"> 
				 <input name="cc1" type="text" value="<%= identificador[20] %>" class="campo" id="cc1" size="5" maxlength="10" <%= !str.esNulo(identificador[22]).equalsIgnoreCase("S") ? "readonly" :  "" %>></td>

       <td class="fila-det-border"> 
				 <input name="cc2" type="text" value="<%= identificador[21] %>" class="campo" id="cc2" size="5" maxlength="10" <%= !str.esNulo(identificador[23]).equalsIgnoreCase("S") ? "readonly" :  "" %>></td>

       <td class="fila-det-border"> 
				 <input name="chequenro" type="text" value="<%= identificador[37] %>" class="campo" id="chequenro" size="10" maxlength="10" <%= str.esNulo( identificador[6] ).equalsIgnoreCase("C") ? "": "readonly"%>></td>

       <td class="fila-det-border"> 
				 <input name="detalle" type="text" value="<%= identificador[29] %>" class="campo" id="detalle" size="30" maxlength="50"  <%= str.esNulo( identificador[6] ).equalsIgnoreCase("C") || str.esNulo( identificador[6] ).equalsIgnoreCase("C") ? "": "readonly"%>></td>

       <td class="fila-det-border"><div align="center">
         <input name="clearing" type="hidden" id="clearing<%=element %>" value="<%= identificador[32] %>" >
         <% if( str.esNulo(identificador[6]).equalsIgnoreCase("C")){%>
         <img src="../imagenes/default/gnome_tango/apps/date.png" width="18" height="18" onClick="abrirVentana('lov_cajaClearingAbm.jsp?elemento=clearing<%=element %>' , 'clearing',700, 200)" style="cursor:pointer" onMouseOver="this.title='Clearing seleccionado: ' + document.getElementById ( 'clearing<%=element %>' ).value"></div>
         <% } else out.write("&nbsp;");%></td> 

       <td class="fila-det-border"><input name="fecha" type="text" value="<%= identificador[31] %>" class="campo" id="fecha" size="10" maxlength="10" <%= str.esNulo( identificador[6] ).equalsIgnoreCase("C") ? "": "readonly"%> title="Formato: dd/mm/yyyy "></td>

       <!--td class="fila-det-border"><div align="center">
         <input name="cuotas" type="text" id="cuotas" value="<%= identificador[20] %>" size="3" maxlength="2" class="campo" <%= str.esNulo( identificador[6] ).equalsIgnoreCase("T") ? "": "readonly"%>>
				  </div></td-->

       <td class="fila-det-border"><div align="justify">
         <input name="importeingreso" type="text" value="<%= identificador[28] %>" class="campo" size="10" maxlength="15" <%= !identificador[6].equalsIgnoreCase("T") ? "readonly" : ""%> style="text-align:right"> 
       </div></td>

       <td align="center" valign="middle" class="fila-det-border"><div align="center">
         <input name="cuenta" type="hidden" id="cuenta<%=element %>" value="<%= identificador[4] %>" > 
			 <% if( str.esNulo(identificador[8]).equalsIgnoreCase("S")){%>
         <img src="../imagenes/default/gnome_tango/apps/accessories-text-editor.png" width="18" height="18" onClick="abrirVentana('lov_contableInfiPlanAbm.jsp?elemento=cuenta<%=element %>&ocurrencia=' + document.getElementById ( 'cuenta<%=element %>' ).value , 'plancuentas',700, 200)" style="cursor:pointer" onMouseOver="this.title='Cuenta asociada: ' + document.getElementById ( 'cuenta<%=element %>' ).value"></div>
			 <% } else out.write("&nbsp;");%>
			 </td>
			  
     </tr>
     <%
		 				element++;
	        }%>
     <tr class="fila-det">
       <td height="33" class="fila-det-border">&nbsp;</td>
       <td colspan="2" class="fila-det-border"><input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();">
       </td>
       <td colspan="2"  class="fila-det-border"><input name="confirmar" type="button" class="boton" id="confirmar" value="Confirma" onClick="confirmarDetalle();"></td>
       <td  class="fila-det-border">&nbsp;</td>
       <td  class="fila-det-border">&nbsp;</td>
       <td  class="fila-det-border">&nbsp;</td>
       <td  class="fila-det-border">&nbsp;</td>
       <td  class="fila-det-border">&nbsp;</td>
       <td  class="fila-det-border">&nbsp;</td>
     </tr>
   </table>

<% 
	}%>
   <p>
      <input name="accion" value="" type="hidden">
      <input name="cartera_mt" value="<%= BCIA.getCartera_mt()%>" type="hidden">
	    <input name="tipcart_mt" value="<%= BCIA.getTipcart_mt()%>" type="hidden">
	    <input name="cartera" value="<%= BCIA.getCartera()%>" type="hidden">
      <input name="idmovteso" type="hidden" id="idmovteso" value="">
      <input name="primeraCarga" type="hidden" id="primneraCarga" value="false">
   </p>
   <%  
			if(BCIA.getAccion().equalsIgnoreCase("confirmar")  
			   && BCIA.getMensaje().equals("") ){ 
			  out.write("<script> opener.document.frm.submit(); this.close();</script>");			
			}else if( BCIA.getAccion().equalsIgnoreCase("eliminar") ) {
			  out.write("<script> opener.document.frm.submit();</script>");			      
      }; %>	
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

