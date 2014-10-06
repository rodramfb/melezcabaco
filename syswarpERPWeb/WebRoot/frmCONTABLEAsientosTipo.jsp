<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="java.util.*"%>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.math.BigDecimal" %>
<%

Strings str = new Strings();

String[] sCampos = new String[6];

String[] cuentas = request.getParameterValues("cuenta");
String[] detalles = request.getParameterValues("detalle");

String pagina = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1 );
String referente = request.getHeader("referer").substring(request.getHeader("referer").lastIndexOf("/") + 1 );
String accion = request.getParameter("accion");
String grabacion = request.getParameter("grabacion");

String agregar = str.esNulo(request.getParameter("agregar"));
String grabar = str.esNulo(request.getParameter("grabar"));
String renglon =  str.esNulo(request.getParameter("renglon"));
String leyenda = str.esNulo(request.getParameter("leyenda"));

// variables de afuera
String codigo = request.getParameter("codigo");

// variables de formulario
String titulo = "Asientos Tipo";
String usuario      = session.getAttribute("usuario").toString();
String formulario = "CONTABLEAsientosTipo.jsp";
String action     = "frmCONTABLEAsientosTipo.jsp";

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();
int ejercicioActivo = Integer.valueOf(session.getAttribute("ejercicioActivo").toString()).intValue();
int id = 0;
if( _nivel == 0 || _nivel == -1)
      response.sendRedirect("errorPage.jsp?error=Acceso denegado a esta aplicacion"); 
String readonly = "";
String readonly1 = "";
List ListaAbmAsientoTipo = ( session.getAttribute("ListaAbmAsientoTipo") != null ? (List)(session.getAttribute("ListaAbmAsientoTipo")) : new ArrayList() ) ;

if(_nivel == 1) readonly = "readonly='true'";
System.out.println(readonly);
System.out.println("nivel "+ _nivel);
// instancio el contable
Contable repo = null;
try{
	javax.naming.Context context = new javax.naming.InitialContext();   
	Object object = context.lookup("Contable");
	ContableHome sHome = (ContableHome) javax.rmi.PortableRemoteObject.narrow(object, ContableHome.class);
	repo =   sHome.create(); 
  //ACCEDE DESDE OTRA URL, LIMPIA LA LISTA DE SESSION.
	if(referente.indexOf(pagina) < 0){
	  ListaAbmAsientoTipo = new ArrayList();
	  session.setAttribute("ListaAbmAsientoTipo", ListaAbmAsientoTipo);		
	}
  //AÑADE UN NUEVO REGISTRO.
	if(!agregar.equalsIgnoreCase("")){
		sCampos[5] = ""; 
		sCampos[1] = request.getParameter("nueva_cuenta");
		sCampos[2] = request.getParameter("nuevo_detalle");
		//
		sCampos[0] = ""; 
		sCampos[3] = "";
		sCampos[4] = "";	
  	ListaAbmAsientoTipo.add(sCampos);			 
 	  session.setAttribute("ListaAbmAsientoTipo", ListaAbmAsientoTipo);		
		str.wLog("Agrega.", 1);
	}
  //MODIFICA LOS VALORES DE LA LISTA SIEMPRE Y CUANDO NO SE ESTE ELIMINANDO UN REGISTRO.
	if(detalles != null && detalles.length > 0 && renglon.equalsIgnoreCase("")){
	  int indice = 0;
		Iterator it  = ListaAbmAsientoTipo.iterator();	
		while (it.hasNext() && indice<detalles.length){ 
		  Object obj =  it.next();  
	    sCampos    = ( String[] ) obj; 
			sCampos [1] = cuentas[indice];			
			sCampos [2] = detalles[indice];
			ListaAbmAsientoTipo.set(ListaAbmAsientoTipo.indexOf(obj), sCampos);
			indice++;
    }	
		str.wLog("Actualiza detalles.", 1);
	}
  //ELIMINA UN REGISTRO.	
	if(!renglon.equalsIgnoreCase("")){
		str.wLog("Resta.", 1);	
  	ListaAbmAsientoTipo.remove(Integer.parseInt(renglon));			 
 	  session.setAttribute("ListaAbmAsientoTipo", ListaAbmAsientoTipo);
		str.wLog("Resta.", 1);				
	}
  //CARGA LA LISTA CON LOS VALORES DEL ASIENTO TIPO, POR PRIMERA Y UNICA VEZ.	
	if(grabacion == null && accion.equalsIgnoreCase("Modificacion") && codigo != null  ){
		 ListaAbmAsientoTipo = repo.getAsientosTipoPK(ejercicioActivo, Integer.parseInt(codigo), new BigDecimal(session.getAttribute("empresa").toString() )); 
		 session.setAttribute("ListaAbmAsientoTipo", ListaAbmAsientoTipo);
  }	 	      

	if(!grabar.equalsIgnoreCase("") && accion.equalsIgnoreCase("Alta")){ 
    // AsientoTipoSave(int idEjercicio, Long idAsientoUpd,	String leyenda, List AsientoTipo, String usuarioalt)
		String respuesta = repo.AsientoTipoSave(ejercicioActivo, null, leyenda, ListaAbmAsientoTipo, usuario,new BigDecimal(session.getAttribute("empresa").toString() ));
	  ListaAbmAsientoTipo = new ArrayList();
	  session.setAttribute("ListaAbmAsientoTipo", ListaAbmAsientoTipo);			
		if(respuesta.indexOf("OK") < 0 ){
			 %><script>alert('<%=respuesta%>');</script><%  	
		}
		else{				   
			 %><script>alert('Se grabo el Asiento Tipo en forma correcta!');</script><%
			 System.out.println("Respuesta: " + respuesta); 
			 response.sendRedirect(formulario);
		}
	}
	//grabo la modificacion
	if(!grabar.equalsIgnoreCase("") && accion.equalsIgnoreCase("Modificacion") && codigo != null ){  
		String respuesta = repo.AsientoTipoUpd(ejercicioActivo, new Long(Long.parseLong(codigo)), leyenda, ListaAbmAsientoTipo, usuario,new BigDecimal(session.getAttribute("empresa").toString() ));
		if(respuesta.indexOf("OK") < 0 ){
		 %><script>alert('<%=respuesta%>');</script><%  	
		}
		else{				   
			 %><script>alert('Se Modifico el Asiento Tipo en forma correcta!');</script><%
			 System.out.println("Respuesta: " + respuesta);
			 response.sendRedirect(formulario);
		}   
	}
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
	
	<script language="JavaScript" src="vs/forms/forms.js"></script>
	<script language="JavaScript" src="vs/overlib/overlib.js"></script>
<script language="JavaScript">
	function validar(){
		var i = 0;
		var accionValidar = document.frm.accionValidar.value ;
		var existeDetalle = <%= ListaAbmAsientoTipo.size() %>;
	
		if(accionValidar == 'grabar'){
		
			if(existeDetalle==0){
				alert( 'Es necesario cargar al menos una cuenta.\n ' );
				return false;		
			}
		
			if( !isCharsInBag( document.frm.leyenda.value.toLowerCase(), varAlfa + varChar )|| trim(document.frm.leyenda.value)=="" ){
				alert( 'El campo leyenda solo admite alfanuméricos y no puede estar vacio.\n ' );
				document.frm.leyenda.focus();
				return false;
			}			
		}	
		if(accionValidar == 'agregar'){
			if( !isCharsInBag( document.frm.nueva_cuenta.value.toLowerCase(), varEnt )|| trim(document.frm.nueva_cuenta.value)=="" ){
				alert( 'El campo codigo de cuenta nueva solo admite numéricos y no puede estar vacio.\n ' );
				document.frm.nueva_cuenta.focus();
				return false;
			}
		
			if( !isCharsInBag( document.frm.nuevo_detalle.value.toLowerCase(), varAlfa + varChar + varDec)|| trim(document.frm.nuevo_detalle.value)=="" ){
				alert( 'El campo detalle nuevo solo admite alfanuméricos y no puede estar vacio.\n ' );
				document.frm.nuevo_detalle.focus();
				return false;
			}	
		}		
			
		for(i = 0 ;i<document.forms[0].elements.length;i++){
			var element = document.forms[0].elements[i];
			if(element.type == 'text'){
				if(element.name == 'cuenta' &&  trim(element.value)=="" ) {
					alert("Ingrese cuenta."  ) 
					element.style.background = '#AFD1AB'
					element.focus();
					return false;					
				}
				if( element.name == 'detalle' && (!isCharsInBag( element.value.toLowerCase(), varAlfa + varChar + varDec) || trim(element.value) == "" )){
					alert( 'El campo detalle  solo admite alfanuméricos y no puede estar vacio.\n ' );
					element.style.background = '#AFD1AB'				
					element.focus();
					return false;
				}
				element.style.background = '#FFFFFF'				
			}
		}					
		
		if(confirm("Confirma ?") )
			return true;
		else 
			return false;
	}

	function eliminarRenglon(renglon){
    document.frm.accionValidar.value == 'eliminar';
	  if(confirm("Confirma Eliminar Registro?")){
		  document.frm.renglon.value = renglon;	
      document.frm.submit() ;
		}
	}

</script>
<link rel = "stylesheet" href = "<%= pathskin %>">
</head>
<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="<%=action%>" name="frm" method="post" onSubmit="javascript:return validar();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" background="form_empresas_files/tab_shadow.gif">
		<tr valign="top"> 
			<td class="text-globales" width="39" height="23"></td>
			<td class="text-globales" width="656"> <%=titulo%></td>
			<td class="text-globales" width="290"> <%= accion %></td>
			<td class="text-globales" width="8"></td>
		</tr>
	</table>

<% 

			String cmp_leyenda = ""; 
			String cmp_detalle = "";
			String cmp_centcosto = "";
			String cmp_centcosto_uno = "";
			String cmp_cuenta = "";
			
		  List AsientosTipo = ListaAbmAsientoTipo; 
			Iterator iterAsientosTipo  = AsientosTipo.iterator();
			boolean first = true;

		 if(iterAsientosTipo.hasNext()){  
			 sCampos = ( String[] ) iterAsientosTipo.next(); 
			 cmp_leyenda = sCampos[5]; 
			 cmp_detalle = sCampos[2];
			 cmp_centcosto = "";
			 cmp_centcosto_uno = "";
			 cmp_cuenta = sCampos[1];
		 }

     cmp_leyenda = !leyenda.equals("") ? leyenda : cmp_leyenda ;
		       %>

	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="fila-det">

			<tr> 
				<td width="17%" class="fila-det-border">Leyenda del Asiento &nbsp;(*)</td>
				<td colspan="3" class="fila-det-border" >
					<input name="leyenda" type="text" class="campo" id="leyenda" size="50" maxlength="50" value="<%=cmp_leyenda%>"  >
				</td>
			</tr>
			<tr> 
				<td width="17%" class="fila-det-border">Nueva Cuenta&nbsp;(*)</td>
				<td width="12%" class="fila-det-border" >
					<input name="nueva_cuenta" type="text" class="campo" id="1000" size="15" readonly="yes">
				</td>
				<td colspan="2" class="fila-det-border" ><img src="imagenes/default/gnome_tango/actions/edit-find.png" width="22" height="22" onClick="mostrarLOV('lov_cuentascontables.jsp?id=1000&opcion=byId')" style="cursor:pointer"> </td>
			</tr>
			<tr> 
				<td width="17%" class="fila-det-border">Nuevo Detalle&nbsp;(*)</td>
				<td colspan="3" class="fila-det-border" >
					<input name="nuevo_detalle" type="text" class="campo" id="1001" size="50" maxlength="50"  >
				</td>
			</tr>			
			<tr> 
				<td  colspan="4" class="fila-det-border"> 
					<table width="100%" height="32" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr class="text-seis">
							<td width="32%">
							<%if(_nivel == 2){%><input type="submit" class="boton" name="grabar" value="Confirmar <%=accion%>" onClick="document.frm.accionValidar.value='grabar'"><%}%>	&nbsp;</td>
							<td width="68%">							  <div align="left">&nbsp; 
									<input type="submit" name="agregar" value="Agregar" class="boton" onClick="document.frm.accionValidar.value='agregar'">
							  </div>
							</td>
						</tr>
					</table>				
				</td>
 		 </tr>					
			<tr >
				<td height="3" class="text-globales" colspan="4" ></td>
			</tr>	
		<% 
		 if(!AsientosTipo.isEmpty()){
			 do{
					if(first)	first = false;
					else{
						sCampos = ( String[] ) iterAsientosTipo.next(); 
						cmp_detalle = sCampos[2];
						cmp_centcosto = "";
						cmp_centcosto_uno = "";
						cmp_cuenta = sCampos[1];			 
					}
		%>
			<tr> 
				<td width="17%" class="fila-det-border">Cuenta&nbsp;(*)</td>
				<td width="12%" class="fila-det-border" >
					<input name="cuenta" type="text" class="campo" id="<%= id %>" size="15" value="<%=cmp_cuenta%>"  readonly="yes">
				</td>
				<td width="66%" class="fila-det-border" ><img src="imagenes/default/gnome_tango/actions/edit-find.png" width="22" height="22" onClick="mostrarLOV('lov_cuentascontables.jsp?id=<%= id %>&opcion=byId')" style="cursor:pointer"> </td>
			  <td width="5%" class="fila-det-border" ><img src="imagenes/default/gnome_tango/status/dialog-error.png" width="22" height="22" onClick="eliminarRenglon('<%= AsientosTipo.indexOf(sCampos) %>')" style="cursor:pointer" title="Eliminar Registro."></td>
			</tr>
		
			<tr> 
				<td width="17%" class="fila-det-border">Detalle&nbsp;(*)</td>
				<td colspan="3" class="fila-det-border" >
					<input name="detalle" type="text" class="campo" id="<%= ++id %>" size="50" maxlength="50" value="<%=cmp_detalle%>"  >
				</td>
			</tr>			
			<tr >
				<td height="3" class="text-globales" colspan="4" ></td>
			</tr>	
		<% 
				 ++id;
			 }while(iterAsientosTipo.hasNext());
		}	 		
 %>

</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
	<tr class="fila-det-verde"> 
		<td height="29" class="fila-det-border">&nbsp;(*) Indica que el campo es obligatorio</td>
	</tr>
</table>
<input type="hidden" name="grabacion" value="SI">
<input type="hidden" name="accion" value="<%= accion %>">
<input type="hidden" name="codigo" value="<%= codigo %>">
<input type="hidden" name="renglon" value="">
<input type="hidden" name="accionValidar" value="">
</form>
<% 
}
catch (Exception ex) {
	java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
	java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
	ex.printStackTrace(pw);
	System.out.println("ERROR("+ pagina +")\n" + ex);
}  
 %>
</body>
</html>