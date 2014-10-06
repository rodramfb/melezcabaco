<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.regex.*" %>
<%//@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%
String ayudalink  = "ayuda.jsp?idayuda=3";  // link a las ayudas en linea de cada punto
Strings str = new Strings();
int decimales = 2;

String pagina = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1 );
String referente = request.getHeader("referer").substring(request.getHeader("referer").lastIndexOf("/") + 1 );

String accion    = str.esNulo(request.getParameter("accion"));
String respuesta = "";
// variables de afuera
String codigo = str.esNulo(request.getParameter("codigo"));
// variables de formulario
String titulo = " Asientos Contables";
String usuario    = session.getAttribute("usuario").toString();
String formulario = "CONTABLEAsientos.jsp";
String action     = "frmCONTABLEAsientos.jsp";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();
if( _nivel == 0 || _nivel == -1)
	response.sendRedirect("errorPage.jsp?error=Acceso denegado a esta aplicacion"); 
String readonly = "";
if(_nivel == 1) readonly = "readonly='true'";
// instancio el contable
Contable repo = null;
General gene =  null;  

String agregar = str.esNulo( request.getParameter("agregar") );
String grabar = str.esNulo( request.getParameter("grabar") );
// Datos Asiento
String fecha = str.esNulo( request.getParameter("fecha") );
String leyenda = str.esNulo( request.getParameter("leyenda") );
String tipo_asiento = "";
// Datos registro
String cuenta = str.esNulo( request.getParameter("cuenta") );
String detalle = str.esNulo( request.getParameter("detalle") );
String importe = str.esNulo( request.getParameter("importe") ); 
String tipomov = str.esNulo( request.getParameter("tipomov") );
String descripcion_mov = str.esNulo( request.getParameter("descripcion_mov") );
String renglon = str.esNulo( request.getParameter("renglon") );
String debe ="";
String haber ="";
BigDecimal totalDebe = new BigDecimal(0);
BigDecimal totalHaber = new BigDecimal(0) ;
boolean esPrimero = true;
BigDecimal[] balanceAsiento = new BigDecimal[3]; 


int nroRenglon = ( session.getAttribute("nroRenglon") != null ? Integer.parseInt((String)session.getAttribute("nroRenglon")) : 0 );
Hashtable ht = new Hashtable();
Hashtable ht2 = ( session.getAttribute("ht2") != null ? (Hashtable)(session.getAttribute("ht2")) : new Hashtable() );


try{
    // instanciar bean general
    javax.naming.Context context = new javax.naming.InitialContext();
   // INSTANCIAR EL MODULO GENERAL 
    Object objgen = context.lookup("General");
    GeneralHome sGen = (GeneralHome) javax.rmi.PortableRemoteObject.narrow(objgen, GeneralHome.class);
    gene =   sGen.create();   	    
    // INSTANCIAR EL MODULO CONTABLE 
    Object object = context.lookup("Contable");
    ContableHome sHome = (ContableHome) javax.rmi.PortableRemoteObject.narrow(object, ContableHome.class);
    repo =   sHome.create();  
		
		Timestamp fDesde = (Timestamp) session.getAttribute("fechaEjercicioActivoDesde");
		Timestamp fHasta = (Timestamp) session.getAttribute("fechaEjercicioActivoHasta");
	  int ejercicioActivo =  Integer.parseInt( (String)session.getAttribute("ejercicioActivo") );				
		String strFDesde = gene.TimestampToStrDDMMYYYY(fDesde) ;
		String strFHasta = gene.TimestampToStrDDMMYYYY(fHasta);



		if(!agregar.equalsIgnoreCase("")){
		  
			nroRenglon++; 
			ht.put("cuenta", cuenta);
			ht.put("detalle", detalle);
		  ht.put("importe", importe  );
			ht.put("tipomov", tipomov);      
			ht.put("descripcion_mov", descripcion_mov);
			ht.put("debe", tipomov.equalsIgnoreCase("1") ? importe   : "" );
			ht.put("haber", tipomov.equalsIgnoreCase("2") ? importe    : "" );
			ht2.put(  nroRenglon < 10 ? "0" + nroRenglon : nroRenglon + "" , ht);
			session.setAttribute("ht2", ht2);
			session.setAttribute("nroRenglon", nroRenglon + "");	
			
		}
		if(!renglon.equalsIgnoreCase("")){
			ht2.remove(renglon);
			session.setAttribute("ht2", ht2);	
		}

		if(accion.equalsIgnoreCase("modificacion") && codigo != null ){   
			if(!grabar.equalsIgnoreCase("")){
				respuesta = repo.AsientoUpd(ejercicioActivo, new Long( Long.parseLong(codigo) ) , gene.StrToTimestampDDMMYYYY(fecha),  leyenda, tipo_asiento,	 ht2, usuario, new BigDecimal(session.getAttribute("empresa").toString() ));
				str.wLog("UPDATE: " + respuesta, 2); 	
				ht2 = new Hashtable();
				session.setAttribute("ht2", ht2); 
			}	
			else{	
				if(ht2.isEmpty() || !pagina.equals(referente)){
					ht2 = new Hashtable();
					java.util.List Asientos =  new ArrayList();
					Iterator iterAsientos=null;			
					Asientos =  repo.getAsientosPK(ejercicioActivo, new Long ( Long.parseLong(codigo) ),new BigDecimal(session.getAttribute("empresa").toString() ) ); 
					iterAsientos = Asientos.iterator();      
					while ( iterAsientos.hasNext() ) {
						ht = new Hashtable();
						String[] sCampos = (String[]) iterAsientos.next(); 
						codigo = sCampos[0] ;
						nroRenglon = Integer.parseInt(sCampos[1] );
						tipomov = sCampos[2] ;
						fecha =  gene.TimestampToStrDDMMYYYY ( gene.StrToTimestampDDMMYYYYHHMISE( sCampos[3] ) )   ;
						detalle =   sCampos[4]  ;
						leyenda =   sCampos[5]  ;
						cuenta =   sCampos[6]  ;
						importe =   sCampos[7]  ;
						ht.put("cuenta", cuenta);
						ht.put("detalle", detalle);
						ht.put("importe",  importe  );
						ht.put("tipomov", tipomov);    
						descripcion_mov = tipomov.equalsIgnoreCase("1") ? "Debe": "Haber" ; 
					  debe = descripcion_mov.equalsIgnoreCase("Debe") ?  sCampos[7]   : "";
						haber = descripcion_mov.equalsIgnoreCase("Haber") ? sCampos[7]  : "";
						ht.put("descripcion_mov", descripcion_mov);  						
						ht.put("debe", debe);  
						ht.put("haber", haber);  
						  
						ht2.put( nroRenglon < 10 ? "0" + nroRenglon : nroRenglon + ""  , ht);
					}				
					session.setAttribute("ht2", ht2);
					session.setAttribute("nroRenglon", nroRenglon + "" );				 
				}
			}
		}
		
		if( accion.equalsIgnoreCase("baja") ){  
			ht2 = new Hashtable();
			session.setAttribute("ht2", ht2); 
			if(!grabar.equalsIgnoreCase("") && codigo !=null ){	
				respuesta = repo.AsientoDel(ejercicioActivo,  new Long(codigo), new BigDecimal(session.getAttribute("empresa").toString() )  );		 
			} 
			else{
				java.util.List Asientos =  new ArrayList();
				Iterator iterAsientos=null;			
				Asientos =  repo.getAsientosPK(ejercicioActivo, new Long ( Long.parseLong(codigo) ),new BigDecimal(session.getAttribute("empresa").toString() ) ); 
				iterAsientos = Asientos.iterator();      
				while ( iterAsientos.hasNext() ) {
					ht = new Hashtable();
					String[] sCampos = (String[]) iterAsientos.next(); 
					codigo = sCampos[0] ;
					nroRenglon = Integer.parseInt(sCampos[1] );
					tipomov = sCampos[2] ;
					fecha =  gene.TimestampToStrDDMMYYYY ( gene.StrToTimestampDDMMYYYYHHMISE( sCampos[3] ) )   ;
					detalle =   sCampos[4]  ;
					leyenda =   sCampos[5]  ;
					cuenta =   sCampos[6]  ;
					importe =   sCampos[7]  ;
					ht.put("cuenta", cuenta);
					ht.put("detalle", detalle);
					ht.put("importe", gene.getNumeroFormateado( Float.parseFloat( importe ) , 10, decimales)  );
					ht.put("tipomov", tipomov);    
					descripcion_mov = tipomov.equalsIgnoreCase("1") ? "Debe": "Haber" ;  
					debe = descripcion_mov.equalsIgnoreCase("Debe") ?  sCampos[7]  : "";
				  haber = descripcion_mov.equalsIgnoreCase("Haber") ?  sCampos[7] : "";
					ht.put("descripcion_mov", descripcion_mov);  
					ht.put("debe", debe);  
				  ht.put("haber", haber);   
					ht2.put(  nroRenglon < 10 ? "0" + nroRenglon : nroRenglon + ""  , ht);
				}
			}		
		}
		
		if(!grabar.equalsIgnoreCase("") && accion.equalsIgnoreCase("alta")){
            respuesta = repo.AsientoSave(ejercicioActivo, null , gene.StrToTimestampDDMMYYYY(fecha),  leyenda, tipo_asiento,	 ht2, usuario, new BigDecimal(session.getAttribute("empresa").toString() ));
			ht2 = new Hashtable();
			session.setAttribute("ht2", ht2); 
		}

    balanceAsiento 	= repo.getBalanceAsiento(ht2);

		//float balancea = repo.getBalanceAsiento(ht2).floatValue();		
		BigDecimal balancea = balanceAsiento[0] ;	
		totalDebe =	balanceAsiento[1] ;	    
		totalHaber =	balanceAsiento[2] ;	    
%>
<html>
  <head>
	<title><%=titulo%> </title>	  
	<link rel="stylesheet" href="jmc.css" type="text/css">
	<meta name="description" content="Free Cross Browser Javascript DHTML Menu Navigation">
	<meta name="keywords" content="JavaScript menu, DHTML menu, client side menu, dropdown menu, pulldown menu, popup menu, web authoring, scripting, freeware, download, shareware, free software, DHTML, Free Menu, site, navigation, html, web, netscape, explorer, IE, opera, DOM, control, cross browser, support, frames, target, download">
	<link rel="shortcut icon" href="http://www.softcomplex.com/products/tigra_menu/favicon.ico">
	<meta name="robots" content="index,follow">
  <link rel="stylesheet" type="text/css" href="vs/calendar/calendar.css">
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	
  <link rel = "stylesheet" href = "<%= pathskin %>">
	
	<script language="JavaScript" src="vs/forms/forms.js"></script>
	<script language="JavaScript" src="vs/overlib/overlib.js"></script>
	<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
  <script language="JavaScript">

	function validar(){
    var accionValidar = document.frm.accionValidar.value.toLowerCase();
		var existeRegistros = <%= ht2.size() %>
		var balancea = '<%= balancea %>';
		var accion = '<%= accion %>';
		
		if(trim(accion) == ""){
		  alert("Seleccione alguna operación.");
			return false;
		}
		
		if( (accionValidar == "alta" || accionValidar == "modificacion")  && existeRegistros < 2){
			 alert( 'Debe cargar al menos dos registros.\n ' );
			 return false;
		}

		if( (accionValidar == "alta" || accionValidar == "modificacion") && parseFloat(balancea) != 0){
			 alert( 'El asiento no está balanceado.\n ' );
			 return false;
		}		
		
		if( trim(document.frm.fecha.value)=="" ){
			 alert( 'Ingrese fecha.\n ' );
			 document.frm.fecha.select();
			 return false;
		}
		
		if( retornaPeriodo(document.frm.fecha.value ) < retornaPeriodo("<%= strFDesde %>")  ||
			retornaPeriodo(document.frm.fecha.value ) > retornaPeriodo("<%= strFHasta %>")  
		){
			alert( 'Fecha debe estar dentro del período <%= strFDesde %> -  <%= strFHasta %>.\n ' );
			document.frm.fecha.select();
			return false;		
		}
		
		if( !isCharsInBag( document.frm.leyenda.value.toLowerCase(), varAlfa + varDec + varChar ) || trim(document.frm.leyenda.value)=="" ){
			alert( 'El campo detalle libro dario solo admite alfanuméricos y no puede estar vacio.\n ' );
			document.frm.leyenda.select();
			return false;
		}
		if(accionValidar == "agregar"){
			if( !isCharsInBag( document.frm.cuenta.value.toLowerCase(), varEnt ) || trim(document.frm.cuenta.value)=="" ){
				alert( 'El campo cuenta solo admite numéricos y no puede estar vacio.\n ' );
				document.frm.cuenta.select();
				return false;
			}
			
			if( !isCharsInBag( document.frm.detalle.value.toLowerCase(), varAlfa + varChar + varDec) || trim(document.frm.detalle.value)=="" ){
				alert( 'El campo detalle libro mayor solo admite alfanuméricos y no puede estar vacio.\n' );
				document.frm.detalle.select();
				return false;
			}
	
			
			if( !isCharsInBag( document.frm.importe.value.toLowerCase(), varDec) || trim(document.frm.importe.value)=="" ){
				alert( 'El campo importe solo admite númericos y no puede estar vacio.\n' );
				document.frm.importe.select();
				return false;
			}	
			
			if(!validarNumerico(document.frm.importe,10, <%= decimales %>)) return false;
			
			if(document.frm.tipomov.selectedIndex == 0) {
				alert( 'Seleccione tipo de registro.\n' );
				document.frm.tipomov.focus();
				return false;	 
			}
		}		
		if ( confirm('Confirma <%= accion %>? ') ){
			return true;
		} 
		else {
			return false;
		}
	}

	function VisualTipoMov(objCmb){
	  document.frm.descripcion_mov.value = objCmb.options[objCmb.selectedIndex].text
	}

	function eliminarRenglon(renglon){
	  if(document.frm.accion.value.toLowerCase() == "baja"){
		 alert("Seleccione Modificación");
		 return false;
		}
		  
	  if(confirm("Confirma Eliminar Registro?")){
		  document.frm.renglon.value = renglon;	
      document.frm.submit() ;
		}
			//document.location = "frmCONTABLEAsientos.jsp?renglon=" + renglon;	
	}
	function  recargarSession(){
     document.frm.submit();
	}
	
</script>  
  </head>
	<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
  <div id="popupcalendar" class="text"></div>	
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
	<form action="<%=action%>" name="frm" method="post"  onSubmit="javascript:return validar(); ">
		<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<td>		  
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
						<tr > 
							<td width="39" height="23"  >&nbsp;</td>
							<td width="243"  >&nbsp; </td>
							<td width="432" >&nbsp;</td>
							<td width="8" >&nbsp;</td>
						</tr>
						<tr valign="top" class="text-globales" > 
							<td width="39" height="23"  >&nbsp;</td>
							<td width="243"  ><%=titulo%> </td>
							<td width="6%"><div align="center"><a href="javascript:popupHelp('<%=ayudalink%>');" ><img src="imagenes/default/gnome_tango/apps/help.png" width="22" height="22" border="0"></a></div></td>
							<td width="432" >&nbsp;</td>
							<td width="8" >&nbsp;</td>
						</tr>
				  </table>
					<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1"  class="fila-det">
						<tr >
							<td width="21%" height="23" class="fila-det-border" >Fecha(*)</td>
							<td colspan="2" class="fila-det-border" >						  
								<input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha" value="<%= fecha %>" maxlength="12">
									<a class="so-BtnLink" href="javascript:calClick();return false;" 
										 onmouseover="calSwapImg('BTN_date_0', 'img_Date_OVER',true);" 
										 onmouseout="calSwapImg('BTN_date_0', 'img_Date_UP',true);" 
										 onclick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fecha','BTN_date_0');return false;"> 
									<img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a>  </td>
						</tr> 
						<tr class="fila-det">
							<td height="23" class="fila-det-border" >Detalle Libro Diario (*)</td>				   
							<td colspan="2" class="fila-det-border" ><input name="leyenda" type="text" class="campo" id="leyenda" value="<%= leyenda %>" size="50" onBlur="document.frm.detalle.value = this.value"></td>
						</tr>				
		
						<tr >
							<td height="3" colspan="3" class="text-globales" ></td>
						</tr>
									
						<tr   class="fila-det">
							<td height="23" class="fila-det-border"  >Cuenta(*)</td>
							<td width="13%" class="fila-det-border" ><input type="text" name="cuenta" class="campo" value="<%= cuenta %>" readonly="yes">				    </td>
							<td width="66%" class="fila-det-border" >
								<img src="imagenes/default/gnome_tango/actions/edit-find.png" width="22" height="22" onClick="mostrarLOV('lov_cuentascontables.jsp')" style="cursor:pointer">
							</td>
						</tr>		
						<tr>
							<td height="23" class="fila-det-border"  >Detalle Libro Mayor (*) </td>
							<td colspan="2" class="fila-det-border" >
								<input name="detalle" type="text" class="campo"  id="detalle"  value="<%= detalle %>" size="50">
							</td>
						</tr>	
						<tr >
							<td width="21%" height="23" class="fila-det-border" >Importe(*)</td>
							<td colspan="2" class="fila-det-border" ><input type="text" name="importe" class="campo"  value="<%= importe %>" ></td>
						</tr>				
						<tr   class="fila-det">
							<td height="23" class="fila-det-border"  >Tipo Registro(*)</td>
							<td colspan="2" class="fila-det-border" >
								<select name="tipomov" class="campo" onChange="VisualTipoMov(this)">
									<option value="0">Seleccionar</option>
									<option value="1">Debe</option>
									<option value="2">Haber</option>
								</select>
								<input type="hidden" name="descripcion_mov" value="<%= descripcion_mov %>" id="descripcion_mov" >
								<input type="hidden" name="debe" value="<%= debe %>" id="debe" >
								<input type="hidden" name="haber" value="<%= haber %>" id="haber" >
							</td>
						</tr>				
						
						<tr >
							<td width="21%" height="23" class="fila-det-border" >Asiento Tipo</td>
							<td colspan="2" class="fila-det-border" >
<img src="imagenes/default/gnome_tango/actions/edit-find.png" width="22" height="22" onClick="mostrarLOV('lov_asientosTipo.jsp')" style="cursor:pointer">							
							</td>
						</tr>							
									
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" >
						<tr class="fila-det-verde"> 
							<td height="29" class="fila-det-border">&nbsp;(*) Indica que el campo es obligatorio</td>
						</tr>
					</table>
					<table width="100%" height="32" border="0" align="center" cellpadding="0" cellspacing="0" >
						<tr class="text-seis">
							<td width="32%">
							<%if(_nivel == 2){%>
									<input name="grabar" type="submit" class="boton"  value="Confirmar <%= accion %>"  onClick="document.frm.accionValidar.value='<%= accion %>'">
							<%}%>		
								&nbsp;
							</td>
							<td width="31%">&nbsp;
								<input name="agregar" type="submit" class="boton" id="agregar" value="Agregar Registro" onClick="document.frm.accionValidar.value='agregar'">
							</td>
							<td width="37%">&nbsp;</td>
						</tr>
					</table>
					
				 <%  
				 if(!ht2.isEmpty()){ %>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
				 <%  
					 //Enumeration enu =  ht2.keys(); 
					 Enumeration enu = Common.getSetSorted(ht2.keySet());
					 String color_fondo = ""; 
					 boolean esFirst = true;
  
					 while(enu.hasMoreElements()){
					 
						 if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
						 else color_fondo = "fila-det-verde"; 			 
						 String key = (String) enu.nextElement();
						 Hashtable ht3 = (Hashtable)(ht2.get(key));
						 //totalDebe +=  ht3.get("debe").equals("") ? 0 : GeneralBean.Round(Float.parseFloat(ht3.get("debe").toString()), 3) ;
						 //totalHaber +=  ht3.get("haber").equals("") ? 0 : GeneralBean.Round(Float.parseFloat(ht3.get("haber").toString()), 3) ;
						 if(esFirst){	 %>				
						<tr class="fila-encabezado"> 
							<td width="16%">&nbsp;Cuenta </td>
							<td width="39%">&nbsp;Detalle</td>
							<td width="21%">&nbsp;Debe </td>
							<td width="22%">&nbsp;Haber </td>
							<td width="2%">&nbsp;</td>
						</tr>
						<% 
							 esFirst = false;				
						 }%>
						<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
							<td class="fila-det-border" >&nbsp;<%= ht3.get("cuenta") %> </td>
							<td class="fila-det-border" >&nbsp;<%= ht3.get("detalle") %> </td>
						    <td class="fila-det-border" >&nbsp;<%= ht3.get("debe") %></td>
							<td class="fila-det-border" >&nbsp;<%= ht3.get("haber") %></td> 	
							<td class="fila-det-border" >
								<div align="center">
								<img src="imagenes/default/gnome_tango/status/gtk-missing-image.png" width="18" height="18" onClick="eliminarRenglon('<%= key %>')" style="cursor:pointer" title="Eliminar Registro.">								</div>							</td>
						</tr>
						
						
				<%  
					 }%>
					 <tr class="text-globales" >
					   <td colspan="5" class="fila-det-border" height="5"></td>
				    </tr>
					 <tr class="fila-det-verde" > 
							<td class="fila-det-border" >&nbsp;  </td> 
							<td class="fila-det-border" >&nbsp;  </td>
					   <td class="fila-det-border" ><div align="left">Total Debe : <%= totalDebe %><%//= gene.getNumeroFormateado( totalDebe, 10, decimales) %>&nbsp;</div></td>
							<td class="fila-det-border" > Total Haber : <%= totalHaber  %><%//= gene.getNumeroFormateado( totalHaber, 10, decimales)  %></td>
							<td class="fila-det-border" >&nbsp;  </td>
					  </tr>
					 <tr class="text-globales" >
					   <td colspan="5" class="fila-det-border" height="5"></td>
				    </tr>						
						<tr class="fila-det-verde" > 
							<td class="fila-det-border" >&nbsp;  </td>
							<td class="fila-det-border" >&nbsp;  </td>
							<td class="fila-det-border" >Balance del Asiento: &nbsp;&nbsp;</td>
							<td class="fila-det-border" >&nbsp;<%= balancea %> </td>
							<td class="fila-det-border" >&nbsp;  </td>
						</tr>				 
					</table>		 	
				<%
				 }%>	
							
				
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr> 
							<td width="27%" class="fila-det-bold-rojo">&nbsp;<%=respuesta.replaceAll("OK", "")%> </td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<input type="hidden" name="accionValidar" value="">
		<input type="hidden" name="accion" value="<%= accion %>">
		<input type="hidden" name="codigo" value="<%= codigo %>">
    <input type="hidden" name="renglon" value="<%= renglon %>">		
	</form>
	</body>
</html>
<% 
}	   
 catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
 	 str.wLog("Error: "  + ex , 3 ) ;
}  
%>