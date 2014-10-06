<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.sql.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ page import="ar.com.syswarp.ejb.*" %> 
<%@ page import="java.math.BigDecimal" %>
<%
String titulo     = "CONSULTA DE REMITOS DE SOCIOS";
String mensajeerror = "";
Strings str = new Strings();
String iddeposito   = str.esNulo(request.getParameter("iddeposito"));
String deposito = str.esNulo(request.getParameter("deposito"));

String cambioDeposito = str.esNulo(request.getParameter("cambioDeposito"));
String usuarioalt = str.esNulo( request.getParameter("usuario")  != null ? request.getParameter("usuario")  + "" :   session.getAttribute("usuario") + "");
String usuarioact = str.esNulo(request.getParameter("usuarioact"));
String fechaDesde   = str.esNulo(request.getParameter("fechaDesde"));
String fechaHasta   = str.esNulo(request.getParameter("fechaHasta"));
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
	<meta name="robots" content="index,follow">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link rel="stylesheet" type="text/css" href="scripts/calendar/calendar.css">
	<script language="JavaScript" src="scripts/calendar/calendarcode.js"></script>
	<script language="JavaScript" src="scripts/forms.js"></script>
	<script language="JavaScript" src="scripts/overlib/overlib.js"></script>  
    <script language="JavaScript">  

		function validar(ejecutar){
		
		  if (document.frm.iddeposito.value==""){
			 alert("Por favor seleccione el deposito Origen!");
			 return false;
		  }
		  
		  if (document.frm.fechaDesde.value==""){
			 alert("Por favor ingrese fecha desde!");
			 return false;
		  }	
		  
		  if (document.frm.fechaHasta.value==""){
			 alert("Por favor ingrese fecha hasta!");
			 return false;
		  }				  	  
			
		  document.frm.ejecutar.value = 1;
          document.frm.submit();

		}

        /////////////////////////////////////////////////////////////////

		function validarAbreVentana(pagina){
		  abreVentana(pagina, 750, 350);
		}
       

	
</script>
<link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
</head>
<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div> 
<div id="popupcalendar" class="text"></div>

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
		javax.naming.Context context = new javax.naming.InitialContext();
		Object object = context.lookup("BC");
		BCHome sHome = (BCHome) javax.rmi.PortableRemoteObject.narrow(object, BCHome.class);
		repo = sHome.create();


		System.out.println(mensajeerror);	
		
		if(!iddeposito.equals("")){
		  ResultSet rsEquivelencia = repo.getTransaccion("spInterfacesConjuntoDepositoDeltaOne", iddeposito);
          String idconjunto = "0";
		  if(rsEquivelencia != null && rsEquivelencia.next()){
            idconjunto = rsEquivelencia.getString(2);
			System.out.println("idconjunto: " + idconjunto);
		    rsStockConjunto = repo.getTransaccion("spInterfacesRemitosExnetConjunto", idconjunto + ", '"+  fechaDesde + "', '" + fechaHasta + "'");
          }
        }
		
	}		

 %>


<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td>
	<form action="consultaRemitosExtranet.jsp" name="frm" method="post">
	<input name="ejecutar" type="hidden" id="ejecutar" value="0">
	<input name="usuario" type="hidden" id="usuario" value="<%= usuarioalt %>">
	 <input name="idempresa" type="hidden" id="idempresa" value="<%= idempresa %>">		
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
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
        <td width="17%" height="23" class="fila-det-border">Dep&oacute;sito Delta:(*)</td>				   
        <td width="18%" class="fila-det-border" ><input name="deposito" type="text" class="campo" id="deposito" value="<%=deposito%>" size="30" readonly></td>
        <td width="65%" class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="validarAbreVentana('../Stock/lov_deposito_informe.jsp')" style="cursor:pointer">
          <input name="iddeposito" type="hidden" id="iddeposito" value="<%=iddeposito%>"></td>
      </tr>
	  <tr>
	    <td height="23" class="fila-det-border">Fecha Desde:(*)</td>
	    <td class="fila-det-border" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechaDesde" value="<%= fechaDesde %>" maxlength="12">
          <a class="so-BtnLink" href="javascript:calClick();return false;" 
											 onMouseOver="calSwapImg('BTN_date_0', 'img_Date_OVER',true);" 
											 onMouseOut="calSwapImg('BTN_date_0', 'img_Date_UP',true);" 
											 onClick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fechaDesde','BTN_date_0');return false;"> <img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17"> </a></td>
	    <td class="fila-det-border" >&nbsp;</td>
	    </tr>
	  <tr>
	    <td height="23" class="fila-det-border">Fecha Hasta:(*)</td>
	    <td class="fila-det-border" ><input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fechaHasta" value="<%= fechaHasta %>" maxlength="12">
          <a class="so-BtnLink" href="javascript:calClick();return false;" 
											 onMouseOver="calSwapImg('BTN_date_1', 'img_Date_OVER',true);" 
											 onMouseOut="calSwapImg('BTN_date_1', 'img_Date_UP',true);" 
											 onClick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fechaHasta','BTN_date_1');return false;"> <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17"> </a></td>
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
	  <td width="17%" ><input type="button" class="boton" name="aceptar" value="Consultar" onClick="validar(1);">
	    &nbsp;</td>
      <td width="21%" >&nbsp;</td>
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
		<td width="66%">Producto</td>
		<td width="6%"><div align="right">Cant.</div></td>
		<td width="11%"><div align="center">Conformado</div></td>
		<td width="14%"><div align="center">Fecha Conformado </div></td>
		<td width="3%">&nbsp;</td>
      </tr>
 <%int r = 0;
    String remito = "";
    while(rsStockConjunto != null && rsStockConjunto.next()){   
      r++;
   	  // estos campos hay que setearlos segun la grilla 
	  if(!remito.equals(rsStockConjunto.getString(7))){
	    if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det"; 
        else color_fondo = "fila-det-verde";
		remito = rsStockConjunto.getString(7);
		
		  %>
		  <tr class="text-catorce" > 
		      <td colspan="9" height="3">Remito: <strong><%= remito %> | </strong>Socio: <%=rsStockConjunto.getString(1)%> - <%=rsStockConjunto.getString(2)%>, <%=rsStockConjunto.getString(3)%> <strong>|</strong> Fecha Remito: <%=rsStockConjunto.getString(8)%></td>
		  </tr>		  
		  
		  <%
		 
	  }%>
			<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" > 
				<td class="fila-det-border" ><%=rsStockConjunto.getString(4)%> - <%=rsStockConjunto.getString(5)%></td> 
				<td class="fila-det-border" ><div align="right"><%=rsStockConjunto.getString(6)%></div></td>  
	            <td class="fila-det-border" ><div align="center"><%=rsStockConjunto.getString(10)%>&nbsp;</div></td>
	            <td class="fila-det-border" >
	              <div align="center"><%=rsStockConjunto.getString(11)%>&nbsp;</div>
				</td>
				<td class="fila-det-border" >
			    <div align="center">
				  <%
				  if(!rsStockConjunto.getString(10).equalsIgnoreCase("PENDIENTE")){%>
			      <img src="../imagenes/default/gnome_tango/apps/config-users.png" width="18" height="18" title="<%=rsStockConjunto.getString(12)%>">
			      <%
				  }
				  else{%>
				  <img src="../imagenes/default/gnome_tango/status/image-missing.png" alt="PENDIENTE" width="18" height="18">
				  <%
				  }%>
			    </div>
			  </td>
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
   System.out.println("ERROR - [INTERFACES/CONSULTA-REMITOS-EXTRANET]: " + ex);
  
}%>
</body>
</html>       
