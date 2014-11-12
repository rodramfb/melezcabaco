<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: bacotmliquidacionresumen
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 02 15:34:16 ART 2011 
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

Iterator iterClientes = null;
Iterator iter = null;
Strings str = new Strings();
String color_fondo ="";
String titulo = " MENU S.A.S.";

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
String diasAuditoria = session.getAttribute("clientesDomiciliosDiasDeAuditoria").toString();
// variables de paginacion
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BMI"  class="ar.com.syswarp.web.ejb.BeanMenuInteractivo"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BMI" property="*" />
<%
 BMI.setResponse(response);
 BMI.setRequest(request);
 BMI.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BMI.ejecutarValidacion();
%>
<head>
<title> <%= titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="scripts/calendar/calendarcode.js"></script> 
 <link rel="stylesheet" type="text/css" href="scripts/calendar/calendar.css"> 
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
<script>
//20120703 - CAMI - Agregado de clientes al menu del sas
function modificarItem(iditem, tipoitem){
	alert("MODIFICAR ITEM");
  	document.frm.idSubAccion.value = iditem;
  	document.frm.subAccion.value = 'modifica' + tipoitem;		
		setAction(tipoitem);
		document.frm.submit();
	}	
	//--
	function setAction(identidad){
	alert(identidad);
	  document.frm.action = 'menuInteractivo.jsp#' + identidad.toLowerCase();
	}
	//--
	function confirmarItem(tipoitem){
	   seleccionMail(); 
		 document.frm.subAccion.value = 'agregar' + tipoitem; 
		 setAction(tipoitem);
		 document.frm.submit();
	}	
//<------------------------------

function abrirVentana(pagina, nombre, ancho, alto) {
	frmLOV = open(pagina,nombre,'scrollbars=yes,resizable=yes,width='+ancho+',height='+alto+',status=yes');
	if (frmLOV.opener == null) 
		frmLOV.opener = self;
		frmLOV.focus();
		return frmLOV;
}	

function getFlashMovieObject(movieName)
{

  if (window.document[movieName]) 
  {
    return window.document[movieName];
  }
  if (navigator.appName.indexOf("Microsoft Internet")==-1)
  {
    if (document.embeds && document.embeds[movieName])
      return document.embeds[movieName]; 
  }
  else // if (navigator.appName.indexOf("Microsoft Internet")!=-1)
  {
    return document.getElementById(movieName);
  }
}
function SendDataToFlashMovie()
{
	// EJV - 20120425 -->
	//if (document.frm.idcli.value>0)
	if (document.frm.idcliente.value>0)
	{
		//alert("SendDataToFlashMovie - EJV: " + document.frm.idcliente.value);
		var flashMovie=getFlashMovieObject("menusas");
		flashMovie.SetVariable("/:codigo", document.frm.idcliente.value);
		flashMovie.SetVariable("/:nombre", document.frm.cliente.value);
		flashMovie.SetVariable("/:idprospecto", document.frm.idprospecto.value);
		//alert("SendDataToFlashMovie: DESPUES DE SETEAR - " + document.frm.idcliente.value);
		//var flashMovie=getFlashMovieObject("menusas");
		//flashMovie.SetVariable("/:codigo", document.frm.idcli.value);
		//flashMovie.SetVariable("/:nombre", document.frm.cli.value);
		//alert("Panel activado");
		//document.frm.activar.enabled.value = false;
		// <--
	}else{
		alert("No hay ningún cliente seleccionado");
	}
}

function ReceiveDataFromFlashMovie()
{
	var flashMovie=getFlashMovieObject("menusas");
	var message=flashMovie.GetVariable("/:mensaje"); 
	var cod = flashMovie.GetVariable("/:codigo");
	document.frm.mensaje.value=message;
}

// EJV - 20120425 -->
window.onload = function(){
  
  var primeraCarga = <%=BMI.isPrimeraCarga()%>;
  
  if(!primeraCarga)
    setTimeout ('SendDataToFlashMovie()', 2000); 
  document.frm.primeraCarga.value = false;

}
// <--

</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<form action="menuInteractivo.jsp" method="POST" name="frm">
  
<% // EJV - 20120425 --> %>
<%-- 
<input type="hidden" name="idcli"  value="<%=BMI.getIdcliente()%>">
<input type="hidden" name="cli" value="<%=BMI.getCliente()%>">   
--%>
<input type="hidden" name="recargar"  id="recargar" value="1"> 
<input type="hidden" name="accion"  id="accion" value=""> 
<input type="hidden" name="primeraCarga"  id="primeraCarga" value="<%=BMI.isPrimeraCarga()%>">
<% //<-- %>
	<table class="color-tabletrim" width="100%" border="0" cellspacing="0" cellpadding="0">   
		<tr class="text-globales">
			<td height="37"><%= titulo %>&nbsp;</td>
		</tr>
		<tr  bgcolor="#FFFFFF" height="3px">
			<td height="3px" bgcolor="#FFFFFF" ></td>
		</tr>
		<tr class="text-globales">
			<td >
				<table align="left">
					<tr class="text-globales">
						<td class="fila-det-border">Cliente:</td>
						<td  class="fila-det-border">
							<input name="idcliente" type="text" id="idcliente" class="campo" value="<%=BMI.getIdcliente().longValue()<0 ? "" : BMI.getIdcliente().toString() %>" size="10"  readonly="true" maxlength="7">
				        </td>
						<td class="fila-det-border">
						
								<input name="cliente" type="text" class="campo" id="cliente" value="<%=BMI.getCliente()%>" size="40" readonly="true" >
								<input name="codigo_" type="hidden" value="<%=BMI.getCodigo_()%>" >
							  	<img src="../imagenes/default/gnome_tango/actions/filefind.png" width="20" height="20" align="bottom" style="cursor:pointer" onClick="abrirVentana('../Clientes/lov_clientesPedidos.jsp', 'clientes', 800, 400)" >						
					  	
					  	</td>
					</tr>
				</table>		  
			</td>
		</tr>
		<tr class="fila-det-bold-rojo">
          <td height="31"  colspan="2"><div align="center">
            <jsp:getProperty name="BMI" property="mensaje"/>
            &nbsp;</div></td>
        </tr>
        <%

	iterClientes = BMI.getListCliente().iterator();
	while (iterClientes.hasNext())
	{	
		String[] datos = (String[]) iterClientes.next();
	%>	
	<table  width = "100%" border="0" cellspacing="0" cellpadding="0"  >
		<tr class="text-globales" ">
        	<td colspan="4">&nbsp;<img src="../imagenes/default/gnome_tango/apps/config-users.png" width="22" height="22">Datos del socio</td>
        </tr>
        <tr class="fila-det">
			<td class="fila-det-border">Codigo</td>
			<td class="fila-det-border"><%=datos[0]%></td>
			<td class="fila-det-border">Razón Social</td>
			<td class="fila-det-border"><%=datos[1]%></td>
		</tr>
		<tr class="fila-det">
			<td class="fila-det-border">Nro. Documento</td>
			<td class="fila-det-border"><%=datos[2]%></td>
			<td class="fila-det-border">Documento</td>
			<td class="fila-det-border"><%=datos[4]%></td>
		</tr>
		<tr class="fila-det">
			<td class="fila-det-border">Periodicidad</td>
			<td class="fila-det-border" colspan="3"><img src="../imagenes/default/gnome_tango/apps/date.png" width="22" height="22" onClick="abrirVentana('../Clientes/clientesPeriodicidadEntregaFrm.jsp?idcliente=<%=BMI.getIdcliente()%>&accion=consulta', 'periodicidad', 800, 450)" title="Periodicidad" style="cursor:pointer"></td>
		</tr>
		<tr class="fila-det">
        	<td class="fila-det-border">Tipo de cliente:</td>
            <td class="fila-det-border"><%=datos[6]%>
            </td>
            <td class="fila-det-border">Categoria:</td>
            <td class="fila-det-border"><%=datos[8]%>
            </td>
       	</tr>
       	<tr class="fila-det">
        	<td class="fila-det-border">Fecha de nacimiento:</td>
            <td class="fila-det-border"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(datos[9]), "JSTSToSTR" ).toString()%>
            </td>        
        	<td class="fila-det-border">Preferencia:</td>
            <td class="fila-det-border"><%=datos[11]%>
            </td>
		</tr>
		<tr class="fila-det">
			<td class="fila-det-border">Promocion:</td>
            <td class="fila-det-border"><%=Common.setNotNull(datos[13]).equalsIgnoreCase("") ? "No tiene promoción asignada" : datos[13]%>
            </td>
            <td class="fila-det-border">Origen y Sub origen:</td>
            <td class="fila-det-border"><%=datos[15]%>-<%=datos[17]%>
            </td>
		</tr>
		<tr class="fila-det">
			<td class="fila-det-border">Condición de pago:</td>
            <td class="fila-det-border"><%=datos[19]%>
            </td>
            <td class="fila-det-border">Tipo I.V.A. :</td>
            <td class="fila-det-border"><%=datos[21]%>
            </td>
		</tr>
		<tr class="fila-det">
			<td class="fila-det-border">Puntaje referidos</td>
			<td class="fila-det-border"><%=Common.getNumeroFormateado(BMI.getPuntosCtaCte().doubleValue(),3,0)%>
			</td>
			<td class="fila-det-border">Estado</td>
			<td class="fila-det-border">
				<img src="<%=datos[24].toString()%>" title="Estado: <%=datos[22]%> - Motivo: <%=datos[23]%> " width="18" height="18"  > Estado: <%=datos[22]%> -  Motivo: <%=datos[23]%></td>
			</td>
		</tr>
		<input type = "hidden" name = "idprospecto" value = "<%=datos[25]%>"/>
	</table>
	<%}
	
	Enumeration enu;
	Hashtable htDomicilios = (Hashtable)session.getAttribute("htDomiciliosCliente");
	enu = htDomicilios.keys();
	if(!htDomicilios.isEmpty())
	{
							%>
	<table width = "100%" border="0" cellspacing="0" cellpadding="0" >
		<tr class="text-globales">
        	<td colspan="4" class="text-diez" ><img src="../imagenes/default/gnome_tango/actions/go-home.png" width="22" height="22"> DOMICILIOS DEL CLIENTE </td>
        </tr>
		<tr>
			<td>
				<table width="100%" border="1px" cellspacing="0" cellpadding="0" align="center" id="domicilio">
					<tr class="fila-det-bold">
						<td >Calle</td>
						<td >Nro</td>
						<td >Localidad</td>
						<td >Contacto</td>
						<td >Tipo domicilio</td>
						<td></td>
					</tr>
					<%
						while(enu.hasMoreElements()){
						Object [] domicilio = (Object[])htDomicilios.get(enu.nextElement());
					%>
                    <tr class="fila-det">
                        <td >&nbsp;<%= domicilio[4] %></td>
                        <td >&nbsp;<%= domicilio[5] %></td>
                        <td >&nbsp;<%= domicilio[9] %></td>
                        <td >&nbsp;<%= domicilio[12] %></td> 
						<td >&nbsp;<%= domicilio[2] %></td>
						<td width="2%">                        
							<%if( new BigDecimal(domicilio[28].toString()).compareTo(new BigDecimal(diasAuditoria))>= 0 ){ %>
					 	    	<img src="../imagenes/default/gnome_tango/status/important.png" title="Atencion el domicilio necesita ser auditado nuevamente! (Total de dias de la ultima auditoria: <%=domicilio[28].toString()%>)"  width="18" height="18" style="cursor:pointer">
						  	<%}else {%>
						     	<img src="../imagenes/default/gnome_tango/status/dialog-information.png" title="(Total de dias de la ultima auditoria: <%=domicilio[28].toString()%>)"  width="18" height="18" style="cursor:pointer">
					    	<%}%>						
					    </td> 
					</tr>
					<%} %>
					</table>
			</td>
		</tr>
	</table>
	<%}
	Enumeration enuZonas;
	Hashtable htDomiciliosZona = (Hashtable)session.getAttribute("htDomiciliosCliente");
	enuZonas = htDomiciliosZona.keys();
	if(!htDomiciliosZona.isEmpty())
	{
	%>
	<table width = "100%"  border="0" cellspacing="0" cellpadding="0"   >
		<tr class="text-globales">
			<td colspan="4" class="text-diez" ><img src="../imagenes/default/gnome_tango/apps/krfb.png" width="22" height="22"> Distribuci&oacute;n y Log&iacute;stica &nbsp;</td>
       	</tr>
		<tr >
			<td>
				<table width="100%" border="1px" cellspacing="0" cellpadding="0" align="center" id="distribucion">
					<%
						while(enuZonas.hasMoreElements()){
						Object [] domicilioZona = (Object[])htDomiciliosZona.get(enuZonas.nextElement());
					%>
					<tr class="fila-det-bold">
						<td>Distribuidor</td>
						 	<input name="idzona" type="hidden" id="idzona" value="<%=domicilioZona[18]%>">
                    		<input name="idanexolocalidad" type="hidden" id="idanexolocalidad" value="">
                    	<td class="fila-det-border">
                    		<table width="23%" border="0" cellpadding="0" cellspacing="0">
                        		<tr class="fila-det-border">
                          			<td width="61%" >
                          				<input name="zona" type="text" class="campo" id="zona" value="<%=str.esNulo(domicilioZona[19].toString())%>" size="30"  readonly="readonly" style="border-style:none">
                          			</td>
                         		</tr>
                    		</table>
                    	</td>
						<td>Zona</td>
						<input name="idexpreso" type="hidden" id="idexpreso" value="<%=domicilioZona[20]%>">
                    	<td class="fila-det-border">
                    		<table width="23%" border="0" cellpadding="0" cellspacing="0">
                        		<tr class="fila-det-border">
                          			<td width="61%" >
                          				<input name="expreso" type="text" class="campo" id="expreso" value="<%=str.esNulo(domicilioZona[21].toString())%>" size="30" readonly="readonly" style="border-style:none">
                          			</td>
                         		</tr>
                    		</table>
                    	</td>
					</tr>
					<tr class="fila-det-bold">
						<td>Cobrador</td>
							<input name="idcobrador" type="hidden" id="idcobrador" value="<%=domicilioZona[22]%>">
                    	<td class="fila-det-border">
                    		<table width="23%" border="0" cellpadding="0" cellspacing="0">
                        		<tr class="fila-det-border">
                          			<td width="61%" >
                          				<input name="cobrador" type="text" class="campo" id="cobrador" value="<%=str.esNulo(domicilioZona[23].toString())%>" size="30" readonly="readonly" style="border-style:none">
                          			</td>
                        		</tr>
                    		</table>
                    	</td>
                    	<td class="fila-det-border"></td>
                    	<td class="fila-det-border"></td>
					</tr>
					<%}%>		
				</table>
			</td>
		</tr>
	</table>
	<%
	}
	Enumeration enuTarjeta;
	Hashtable htTarjetas = (Hashtable)session.getAttribute("htTarjetasCliente");
	enuTarjeta = htTarjetas.keys();
	if(!htTarjetas.isEmpty()){%>
	
	<table width = "100%"  border="0" cellspacing="0" cellpadding="0"  >
		<tr class="text-globales">
			 <td colspan="4" class="text-dos" > <img src="../imagenes/default/gnome_tango/actions/contact-new.png" width="22" height="22"> TARJETAS DE CREDITO DEL CLIENTE </td>
		</tr>
		<tr>
			<td>
				<table width="100%" border="1px" cellspacing="0" cellpadding="0" align="center" id="tarjeta">
					<tr class="fila-det-bold">
						<td>Tarjeta</td>
						<td>Activa</td>
						<td>Titular</td>
						<td>Nro. tarjeta</td>
						<td>Orden</td>
					</tr>
					<%
						while(enuTarjeta.hasMoreElements())
						{
							Object [] tarjeta = (Object[])htTarjetas.get(enuTarjeta.nextElement());
					%>
                        <tr class="fila-det">
                        	<td width="13%"><%= tarjeta[2] %>&nbsp;</td>
                          	<td width="6%"><%= tarjeta[11] %>&nbsp;</td>
                          	<td width="24%"><%= tarjeta[9] %>&nbsp;</td>
                          	<td width="28%"><%= tarjeta[5] %>&nbsp;</td>
                          	<td width="28%"><%= tarjeta[10] %>&nbsp;</td>
                        </tr>
                       <%}%>
				</table>
			</td>
		</tr>
	</table>
	<%} %>
		<tr>
			<td colspan="2">
				<table width="100%">
					<tr>
						<td>
							<%-- 
							// EJV - 20120425 -->
							<input type="button" name="activar" value="Activar panel" onClick="SendDataToFlashMovie();"/>
							// <--
							 --%>						
							 </td>
					</tr>
					<tr>
						<td valign="middle">
							<div align="center" >
							  <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="810" height="135" id="menusas"  align="middle">
							    <param name="allowScriptAccess" value="sameDomain" />
							    <param name="movie" value="menusas.swf" />
							    <param name="quality" value="high" />
							    <param name="bgcolor" value="#ffffff" />
							    <embed src="menusas.swf" quality="high" bgcolor="#ffffff" width="810" height="135" name="menusas" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />                                
						      </object>
					        </div></td>
					</tr>
			  </table>			</td>
		</tr>
	</table>
	
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


