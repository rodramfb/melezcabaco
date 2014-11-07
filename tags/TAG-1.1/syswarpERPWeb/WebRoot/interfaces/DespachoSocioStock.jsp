<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.sql.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ page import="ar.com.syswarp.ejb.*" %> 
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.Common"%>
<%
String titulo     = "Despacho para Socios";
String mensajeerror = "";
Strings str = new Strings();
String deposito1   = str.esNulo(request.getParameter("deposito1"));
String d_deposito1 = str.esNulo(request.getParameter("d_deposito1"));
String deposito2   = str.esNulo(request.getParameter("deposito2"));
String cambioDeposito = str.esNulo(request.getParameter("cambioDeposito"));
String d_deposito2 = str.esNulo(request.getParameter("d_deposito2"));
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
			   elements[i].checked = param;
			 }
		  }  
		}
	
</script>
<link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.Estilo1 {font-size: 8px}
.Estilo2 {font-size: 9px}
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
			    resultado=bc.InterfaseDbBacoDeltaDespachoSocios(deposito1,	deposito2, htSeleccion , "OBS.", usuarioalt,  new BigDecimal(idempresa));
		      for(int j=0;j<3;j++)
					  mensaje += str.esNulo(resultado[j]).equals("") ?  "" : resultado[j] + "<br>";				
				}
				
		  }
			else{
			  mensajeerror = "Es necesario seleccionar al menos un producto para actualizar.";
			} 
		}

		System.out.println(mensajeerror);			  
		//rsStockConjunto = bc.getTransaccion("spInterfacesStockConjunto", deposito2);
		rsStockConjunto = bc.callSpInterfacesStockConjuntoTmp(new BigDecimal(deposito2), new BigDecimal(idempresa));
		
		
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
<form action="DespachoSocioStock.jsp" name="frm" method="post">
<input name="ejecutar" type="hidden" id="ejecutar" value="0">
<input name="usuario" type="hidden" id="usuario" value="<%= usuarioalt %>">
<input name="idempresa" type="hidden" id="usuario" value="<%= idempresa %>">  
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0" background="form_empresas_files/tab_shadow.gif">
    <tbody>
      <tr valign="top"> 
        <td class="text-globales" width="39" height="23">&nbsp; </td>
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
        <td width="17%" class="fila-det-border" ><input name="d_deposito1" type="text" class="campo" id="d_deposito1" value="<%=d_deposito1%>" size="30" readonly></td>
        <td width="60%" class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="validarAbreVentana('lov_conjunto.jsp?cmp_codigo=deposito1&cmp_descripcion=d_deposito1')" style="cursor:pointer">
          <input name="deposito1" type="hidden" id="deposito1" value="<%=deposito1%>"></td>
      </tr>
	  
	  <tr>
        <td width="23%" height="23" class="fila-det-border">Deposito Destino  (*)</td>				   
        <td width="17%" class="fila-det-border" ><input name="d_deposito2" type="text" class="campo" id="d_deposito2" value="<%=d_deposito2%>" size="30" readonly></td>
        <td width="60%" class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="validarAbreVentana('lov_conjunto.jsp?cmp_codigo=deposito2&cmp_descripcion=d_deposito2')" style="cursor:pointer">
          <input name="deposito2" type="hidden" id="deposito2" value="<%=deposito2%>">
		  <input name="cambioDeposito" type="hidden" id="cambioDeposito" value="<%=deposito2%>"></td>
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
        <td width="1%" rowspan="3">&nbsp;</td>
        <td colspan="2" valign="top"  > <div align="center">Seleccionar</div></td>
        <td width="2%" rowspan="3">Tipo</td>
		<td width="5%" rowspan="3">C&oacute;digo</td>
		<td width="38%" rowspan="3">Producto</td>
				<td colspan="4"><div align="center">Cantidades</div></td>
		    </tr>
      <tr class="fila-encabezado">
        <td width="3%" rowspan="2"  ><div align="center" onClick="seleccionar(1)" style="cursor:pointer" ><font style="text-decoration:underline">Todos</font></div></td>
        <td width="7%" rowspan="2"  ><div align="center" onClick="seleccionar(0)" style="cursor:pointer"><font style="text-decoration:underline">Ninguno</font></div></td>
        <td width="20%" valign="bottom"><div align="center">Baco</div></td>
        <td colspan="2"><div align="center">Delta</div></td>
        <td width="7%" rowspan="2" valign="bottom"><div align="right">Despacho</div></td>
      </tr>
      <tr class="fila-encabezado">
        <td width="20%" valign="bottom"><div align="right">Original(<span class="Estilo1"><span class="Estilo2">Pend. Conf</span></span>)</div></td>
        <td width="9%"><div align="right">Disponible</div></td>
        <td width="8%"><div align="right">Reserva</div></td>
      </tr>
 <%int r = 0;
    while(rsStockConjunto != null && rsStockConjunto.next()){   
	 String despacho = ((rsStockConjunto.getLong(7) - rsStockConjunto.getLong(9)) < 0 ? 0 : (rsStockConjunto.getLong(7) - rsStockConjunto.getLong(9)) ) + "";			
	 String tipoCodigo = rsStockConjunto.getString(3) + "#" + rsStockConjunto.getString(4) + "#" + rsStockConjunto.getString(5); 
	 String cant = cantidad == null || cantidad.length == 0 || !cambioDeposito.equals(deposito2)  ?  despacho : cantidad[r]; 
     r++;
   	    // estos campos hay que setearlos segun la grilla 
	    if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
        else color_fondo = "fila-det-verde"; 	
	
  %>
			<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
			    <td class="fila-det-border" ><div align="center">
				<table width="10px" border="0" cellpadding="0" cellspacing="0" <%= despacho.equals("0") ? "bgcolor=\"#00CC00\"" : "bgcolor=\"#FF3300\""%>  >
                  <tr>
                    <td height="5"></td>
                  </tr>
                </table>
			  </div></td>

				<td colspan="2" class="fila-det-border" ><div align="center">
				  <input name="seleccion" type="checkbox" id="seleccion" value="<%= tipoCodigo  %>"  class="campo" <%= rsStockConjunto.getLong(7) <= rsStockConjunto.getLong(9)  ?  "disabled" : ( ( htSeleccion.containsKey( tipoCodigo ) ) ?  "checked" : "" )%> >
			    </div></td>

		      <td class="fila-det-border" ><%=rsStockConjunto.getString(3)%></td>
				  <td class="fila-det-border" ><%=rsStockConjunto.getString(5)%></td> 
				  <td class="fila-det-border" ><%=rsStockConjunto.getString(6)%></td>
			    <td class="fila-det-border" ><div align="right"><%=rsStockConjunto.getString(7)%>&nbsp;</div></td>
		      <td class="fila-det-border" ><div align="right"><%=rsStockConjunto.getString(8)%></div></td>
	        <td class="fila-det-border" ><div align="right"><%=rsStockConjunto.getString(9)%></div></td>
	        <td class="fila-det-border" >  
						
				  <div align="right">
					  <input name="existencia" type="hidden" id="existencia" value="<%=rsStockConjunto.getString(7)%>" size="10">
				    <input name="producto" type="hidden" id="producto" value="<%=tipoCodigo %>" size="10">
                    <input name="cantidad" type="text" class="campo" value="<%=cant%>" size="6" maxlength="5" style="text-align:right">
			                </div></td>
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
   System.out.println("ERROR - [INTERFACES/DESPACHO - SOCIO - STOCK]: " + ex);
  
}%>
</body>
</html>       
