<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.sql.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ page import="ar.com.syswarp.ejb.*" %> 
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.Common"%>
<%
String titulo     = "Despacho Andreani";
String mensajeerror = "";
Strings str = new Strings();
String deposito1   = str.esNulo(request.getParameter("deposito1"));
String d_deposito1 = str.esNulo(request.getParameter("d_deposito1"));
String deposito2   = "28";//str.esNulo(request.getParameter("deposito2"));
String cambioDeposito = str.esNulo(request.getParameter("cambioDeposito"));
String d_deposito2 = str.esNulo(request.getParameter("d_deposito2"));
String hrDesde   = str.esNulo(request.getParameter("hrDesde"));
String hrHasta   = str.esNulo(request.getParameter("hrHasta"));

String usuarioalt = str.esNulo( request.getParameter("usuario")  != null ? request.getParameter("usuario")  + "" :   session.getAttribute("usuario") + "");
String usuarioact = str.esNulo(request.getParameter("usuarioact"));
String fechaalt   = str.esNulo(request.getParameter("fechaalt"));
String fechaact   = str.esNulo(request.getParameter("fechaact"));
String ejecutar   = str.esNulo( request.getParameter("ejecutar") ) == ""  ? "0" : str.esNulo( request.getParameter("ejecutar") );
String mensaje = "";
String [] resultado = new String[3];
String idempresa = str.esNulo( request.getParameter("idempresa")  != null ? request.getParameter("idempresa")  + "" :   session.getAttribute("empresa") + "");;

String [] cantidad   = request.getParameterValues("cantidad");
String [] existencia = request.getParameterValues("existencia");
String [] producto = request.getParameterValues("producto");
String [] seleccion = request.getParameterValues("seleccion");

Iterator iterDespachoSocio   = null;
int totCol = 8; // cantidad de columnas
int totReg = 0;  // total de registros
String[] tituCol = new String[totCol];
String color_fondo ="";
String accion = "B";
int i = 0;
int totalPaginas = 0;
int posIni = 1;
int posFin = 1;
int rxp =0; 
int curPage = 0; 
ResultSet rsStockConjunto = null;
Hashtable htSeleccion = new Hashtable();

%>
<html>
<head>
	<title><%=titulo%></title>	
	<link rel="stylesheet" href="jmc.css" type="text/css">
	<meta name="description" content="Free Cross Browser Javascript DHTML Menu Navigation">
	<meta name="keywords" content="JavaScript menu, DHTML menu, client side menu, dropdown menu, pulldown menu, popup menu, web authoring, scripting, freeware, download, shareware, free software, DHTML, Free Menu, site, navigation, html, web, netscape, explorer, IE, opera, DOM, control, cross browser, support, frames, target, download">
	<link rel="shortcut icon" href="http://www.softcomplex.com/products/tigra_menu/favicon.ico">
	<meta name="robots" content="index,follow">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <script language="JavaScript" src="scripts/forms.js"></script>
	 <script language="JavaScript" src="scripts/overlib/overlib.js"></script>
     <script language="JavaScript">

		function validar(ejecutar){
		
		  if (document.frm.deposito1.value==""){
			 alert("Por favor seleccione el deposito Origen!");
			 return false;
		  }
		  
		  if(trim(document.frm.hrDesde.value) == ''){
		    alert("Ingrese Hoja de Ruta Desde");
		    return false;
		  }
		  

		  if(!isCharsInBag(document.frm.hrDesde.value, '0123456789')){
		    alert("Hoja de Ruta Desde debe ser un valor numérico."); 
		    return false;
		  }	 


		  if(trim(document.frm.hrHasta.value) == ''){
		    alert("Ingrese Hoja de Ruta Hasta");
		    return false;
		  }		 
		  
	  		  
		  if(!isCharsInBag(document.frm.hrHasta.value, '0123456789')){
		    alert("Hoja de Ruta Hasta debe ser un valor numérico."); 
		    return false;
		  }	 
			
		  if( parseInt(document.frm.hrDesde.value)  >  parseInt(document.frm.hrHasta.value) ){
		    alert("Hoja de Ruta Desde debe ser menor o igual que Hoja de Ruta Hasta.");
		    return false;
		  }			
			
			if(ejecutar == 2){
				if (document.frm.deposito2.value==""){
				 alert("Por favor seleccione el deposito Destino!");
				 return false;
				}
				
				if (document.frm.deposito1.value==document.frm.deposito2.value){
				 alert("Origen - Destino deben ser distintos!");
				 return false;
				}
				
				var elementos = document.frm.elements;
				var check = false;
				for(var i=0;i<elementos.length;i++){
				  if(elementos[i].type == 'checkbox'){
					  if(elementos[i].checked){ 
						  check = true;
						  break;
					  }
					}
				}

				if(!check){
				 alert("Es necesario seleccionar al menos un producto para actualizar!");
				 return false;				  
				}
				
			}

			if ( confirm('Confirma?') ){
				document.frm.ejecutar.value = ejecutar;
				document.frm.actualizar.disabled = true;
				document.frm.submit();
			}			
			
			return true;
		}

        /////////////////////////////////////////////////////////////////

		function validarAbreVentana(pagina){
		  abreVentana(pagina, 750, 350);
		}
       
	    ////////////////////////////////////////////////////////////////
		
		function seleccionar(param){
		
		  var elements = document.frm.elements ;
		  for(var i= 0;i<elements.length;i++){
		     if(elements[i].type == 'checkbox'){
			   if(elements[i].alt.toUpperCase() != 'DESPACHADO')
			     elements[i].checked = param;
			   else
			     elements[i].checked = false;
			 }
		  }  
		}
		
		function validarDespacho(despachado, producto, obj){
		  if(despachado=='S'){
		    alert("El producto: " + producto + ", ya fue despachado. ");
		    obj.checked = false;
		  }
		} 
	
</script>
<link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
#Layer1 {
	position:absolute;
	left:89px;
	top:338px;
	width:33px;
	height:19px;
	z-index:1001;
	visibility: visible;
	background-color: #CCCCCC;
}
-->
</style>
</head>
<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div> 

<% 
try{ 
    
    BC repo = null;
	System.out.println("SOCIO: |" + usuarioalt + "| ");
	if(str.esNulo(usuarioalt).equals("")) response.sendRedirect("caduco.jsp"); 
    if(Integer.parseInt(ejecutar) > 0 ){ 
		if(idempresa.equals("")) idempresa = "1";
		if(usuarioalt.equals("")) usuarioalt = "INTERFAZ"; 
		else{
         if(usuarioalt.length()>17) usuarioalt = usuarioalt.substring(0, 16);
		 if (usuarioalt.indexOf(":IF") == -1) usuarioalt += ":IF" ;
		}
		
		BC bc = Common.getBc();

    if(Integer.parseInt(ejecutar) == 2){ 
		  
		  if(seleccion!=null){
		  
				for(int j=0;seleccion!=null&&j<seleccion.length;j++){
					htSeleccion.put(seleccion[j], "");
				}
			
			
				for(int j=0;cantidad!=null&&j<cantidad.length;j++){
				
					if( htSeleccion.containsKey(producto[j].toString() )){
		
						if( !Common.esEntero(cantidad[j]) ){
							mensajeerror="Cantidad para producto:  " +  producto[j] + " debe ser un valor entero." ;
						}else if(Integer.parseInt(cantidad[j]) <= 0 ){
							mensajeerror="Cantidad para producto:  " +  producto[j] + " debe ser un valor mayor a 0." ;
						}else if(Integer.parseInt(cantidad[j]) > Integer.parseInt(existencia[j])){
							mensajeerror="Cantidad para producto:  " +  producto[j] + " debe ser un valor menor a la existencia: " + existencia[j] ;
						}else{ 
							System.out.println("ART:" + producto[j] + " OK. ");	
							htSeleccion.put( producto[j].toString(), cantidad[j] );
						}

					}
					if(!mensajeerror.equalsIgnoreCase("")) break;
				}
				
				if(mensajeerror.equalsIgnoreCase("")){
			    resultado=bc.InterfaseDbBacoDeltaDespachoAndreani(deposito1,	deposito2, hrDesde, hrHasta, htSeleccion , "OBS.", usuarioalt,  new BigDecimal(idempresa));
		      for(int j=0;j<3;j++)
					  mensaje += str.esNulo(resultado[j]).equals("") ?  "" : resultado[j] + "<br>";	 			
				}
				
		  }
			else{
			  mensajeerror = "Es necesario seleccionar al menos un producto para actualizar.";
			} 
		}

		System.out.println(mensajeerror);			  
		rsStockConjunto = bc.getTransaccion("spInterfacesStockConjuntoAndreani", deposito2 + ", " + hrDesde + ", " + hrHasta ); 
		//rsStockConjunto = bc.callSpInterfacesStockConjuntoTmp(new BigDecimal(deposito2), new BigDecimal(idempresa));
		
		
		//resultado = bc.InterfaseDbBacoDeltaMovSalida( conjunto,new BigDecimal(idempresa),  usuarioalt);
		/*
		  for(int j=0;j<3;j++)
		  mensaje += str.esNulo(resultado[j]).equals("") ?  "" : resultado[j] + " <br>";
		*/ 
	}		
	//iterDespachoSocio = resultado.iterator();   
 %>


<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td>
<form action="DespachoAndreaniStock.jsp" name="frm" method="post">
<input name="ejecutar" type="hidden" id="ejecutar" value="0">
<input name="usuario" type="hidden" id="usuario" value="<%= usuarioalt %>">
<input name="idempresa" type="hidden" id="idempresa" value="<%= idempresa %>">	
  
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0" background="form_empresas_files/tab_shadow.gif">
    <tbody>
      <tr valign="top"> 
        <td class="text-globales" width="39" height="23"></td>
        <td class="text-globales" width="656"> <%=titulo%></td> 
        <td class="text-globales" width="8"></td>
      </tr>
      <tr >
        <td class="fila-det-bold-rojo" ></td>
        <td class="fila-det-bold-rojo"> 
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="50%" class="fila-det-bold-rojo"><%= mensajeerror %>&nbsp;</td>
						<td width="50%" class="fila-det-bold-rojo"> <%= mensaje %>&nbsp;</td>
					</tr>
				</table>
			  </td>
        <td class="fila-det-bold-rojo"></td>
      </tr>
    </tbody>
  </table>
  <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="fila-det">
    <tbody>
		
      <tr class="fila-det-border">
        <td height="23" class="fila-det-border">&nbsp;</td>
        <td class="fila-det-border">&nbsp;</td> 
        <td class="fila-det-border">&nbsp;</td>
      </tr>
	  <tr>
        <td width="23%" height="23" class="fila-det-border">Deposito Origen(*)</td>				   
        <td width="20%" class="fila-det-border" ><input name="d_deposito1" type="text" class="campo" id="d_deposito1" value="<%=d_deposito1%>" size="30" readonly></td>
        <td width="57%" class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="validarAbreVentana('lov_conjunto.jsp?cmp_codigo=deposito1&cmp_descripcion=d_deposito1')" style="cursor:pointer">
          <input name="deposito1" type="hidden" id="deposito1" value="<%=deposito1%>"></td>
      </tr>
	  <tr>
        <td width="23%" height="23" class="fila-det-border">Deposito Destino  (*)</td>				   
        <td width="20%" class="fila-det-border-bold" >Andreani 
          <input name="d_deposito2" type="hidden" class="campo" id="d_deposito2" value="<%=d_deposito2%>" size="30" readonly></td>
        <td width="57%" class="fila-det-border" ><input name="deposito2" type="hidden" id="deposito2" value="<%=deposito2%>">
		  <input name="cambioDeposito" type="hidden" id="cambioDeposito" value="<%=deposito2%>"></td>
      </tr>
	  <tr>
	    <td height="23" class="fila-det-border">Hoja de Ruta Desde (*)</td>
	    <td class="fila-det-border" ><input name="hrDesde" type="text" class="campo" id="hrDesde" value="<%=hrDesde%>" size="5" maxlength="4" style="text-align:right"></td>
	    <td class="fila-det-border" >&nbsp;</td>
	    </tr>
	  <tr>
	    <td height="23" class="fila-det-border">Hoja de Ruta Hasta (*)</td>
	    <td class="fila-det-border" ><input name="hrHasta" type="text" class="campo" id="hrHasta" value="<%=hrHasta%>" size="5" maxlength="4" style="text-align:right"></td>
	    <td class="fila-det-border" >&nbsp;</td>
	    </tr>
	  <tr>
	    <td height="23" class="fila-det-border">&nbsp;</td>
	    <td class="fila-det-border" >&nbsp;</td>
	    <td class="fila-det-border" >&nbsp;</td>
	    </tr>
    </tbody> 
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b">
    <tr class="fila-det-verde"> 
      <td height="29" class="fila-det-border">&nbsp;(*) Indica que el campo es obligatorio</td> 
    </tr>
  
  </table>   
  
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr >
	  <td width="17%" ><input type="button" class="boton" name="aceptar" value=" Ver Stock " onClick="validar(1);">
	    &nbsp;</td>   
      <td width="21%" ><input name="actualizar" type="button" class="boton" id="actualizar" onClick="validar(2);" value="Actualizar Stock"></td>
      <td width="62%" >&nbsp;</td>
    </tr>
  </table>
  <table width="100%" align="center" cellpadding="1" cellspacing="1" class="color-tabletrim">
    <tbody><tr class="color-tableheader"> 
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tbody><tr> 
            <td width="0%" height="19"> <div align="center"></div></td>
            <td width="26%"><strong class="titlerev"> </strong></td>
            <td width="5%"><strong class="titlerev"> </strong></td>
            <td width="4%">&nbsp; </td>
            <td width="5%">&nbsp;</td>
            <td width="28%"> </td>
            <td width="5%">&nbsp;</td>
            <td width="27%">&nbsp; </td>
          </tr>
        </tbody></table>
    </tr>
  </tbody></table>
<%if(Integer.parseInt(ejecutar)>0){%>
<table width="95%" border="0" cellspacing="1" cellpadding="0" align="center">
      <tr class="fila-encabezado">
	  
        <td width="2%" rowspan="2" valign="middle"  ><div align="center">D </div></td>
        <td colspan="2" valign="top"  ><div align="center">Seleccionar</div></td>
        <td width="5%" rowspan="2">Tipo</td>
		<td width="7%" rowspan="2">C&oacute;digo</td>
		<td width="58%" rowspan="2">Producto</td>
				<td width="14%" rowspan="2">			 
				  <div align="right">Cantidad Original</div>
			    </div></td>
		  </tr>
      <tr class="fila-encabezado">
        <td width="6%"  ><div align="center" onClick="seleccionar(1)" style="cursor:pointer"><font style="text-decoration:underline">Marcar</font></div></td>
        <td width="8%"  ><div align="center" onClick="seleccionar(0)" style="cursor:pointer"><font style="text-decoration:underline">Desmarcar</font></div></td>
        </tr>
 <%int r = 0;
    while(rsStockConjunto != null && rsStockConjunto.next()){   
	 //String despacho = ((rsStockConjunto.getLong(7) - rsStockConjunto.getLong(8)) < 0 ? 0 : (rsStockConjunto.getLong(7) - rsStockConjunto.getLong(8)) ) + "";			
	 String tipoCodigo = rsStockConjunto.getString(3) + "#" + rsStockConjunto.getString(4) + "#" + rsStockConjunto.getString(5) + "#" + rsStockConjunto.getString(8); 
	 //String cant = cantidad == null || cantidad.length == 0 || !cambioDeposito.equals(deposito2)  ?  despacho : cantidad[r];
	 String cant =  rsStockConjunto.getString(7);  
     r++;
   	    // estos campos hay que setearlos segun la grilla 
	    if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
        else color_fondo = "fila-det-verde"; 	
	
  %>

			<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
			    <td class="fila-det-border" ><div align="center"><font color="<%= rsStockConjunto.getString(8).equalsIgnoreCase("S") ? "#00CC00" : "#FF3300"%>" style="font-weight:bold"><%=rsStockConjunto.getString(8)%></font></div></td> 

		        <td colspan="2" class="fila-det-border" ><div align="center">
		          <input name="seleccion" type="checkbox" class="campo" id="seleccion" onClick="validarDespacho('<%=rsStockConjunto.getString(8)%>', '<%=rsStockConjunto.getString(5) + " / "+ rsStockConjunto.getString(6)%>', this); this.checked = false;" value="<%= tipoCodigo  %>" alt="<%=rsStockConjunto.getString(8).equalsIgnoreCase("S") ? "DESPACHADO" : "" %>" <%= htSeleccion.containsKey( tipoCodigo ) ?  "checked" : ""%> >
	            </div></td>
	          <td class="fila-det-border" ><%=rsStockConjunto.getString(3)%></td>
				<td class="fila-det-border" ><%=rsStockConjunto.getString(5)%></td> 
				<td class="fila-det-border" ><%=rsStockConjunto.getString(6)%>
				  <input name="existencia" type="hidden" id="existencia" value="<%=rsStockConjunto.getString(7)%>" size="10">
                  <input name="producto" type="hidden" id="producto" value="<%=tipoCodigo %>" size="10">
                  <input name="cantidad" type="hidden" class="campo" value="<%=cant%>" size="6" maxlength="5" style="text-align:right"></td>
			    <td class="fila-det-border" ><div align="right"><%=rsStockConjunto.getString(7)%>&nbsp;</div></td>
          </tr>

  <%}%>   
</table>  
<%}%>		
</form>
	</td>
  </tr>
</table>

 
<% 
}
catch (Exception ex) {
   System.out.println("ERROR - [INTERFACES/DESPACHO - ANDREANI - STOCK]: " + ex);
  
}%>
</body>
</html>       
