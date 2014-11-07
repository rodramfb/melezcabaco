<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.Common"%>
<%
Strings str = new Strings();

String titulo    = "Consulta Saldo Cuentas";
String anio      = str.esNulo( request.getParameter("anio") );
String mesDesde  = str.esNulo( request.getParameter("mesDesde") );
String mesHasta  = str.esNulo( request.getParameter("mesHasta") );
String cent_cost = str.esNulo( request.getParameter("cent_cost") );
String d_cent_cost = str.esNulo( request.getParameter("d_cent_cost") );
String cent_cost1  = str.esNulo( request.getParameter("cent_cost1") );
String d_cent_cost1 = str.esNulo( request.getParameter("d_cent_cost1") );
String usuario      = session.getAttribute("usuario").toString();
String ejercicioActivo   = session.getAttribute("ejercicioActivo").toString();
String pagina = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1 );
//String referente = request.getHeader("referer").substring(request.getHeader("referer").lastIndexOf("/") + 1 );

String clasePos = "texto-saldo-positivo";
String claseNeg = "texto-saldo-negativo";

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();

if( _nivel == 0 || _nivel == -1)
      response.sendRedirect("errorPage.jsp?error=Acceso denegado a esta aplicacion"); 
String readonly = "";
if(_nivel == 1) readonly = "readonly='true'";

Iterator iterEjercicios   = null;
Iterator IterMeses        = null;

%>	
<html>
  <head>	
	<link rel="stylesheet" href="jmc.css" type="text/css">
	<meta name="description" content="Free Cross Browser Javascript DHTML Menu Navigation">
	<meta name="keywords" content="JavaScript menu, DHTML menu, client side menu, dropdown menu, pulldown menu, popup menu, web authoring, scripting, freeware, download, shareware, free software, DHTML, Free Menu, site, navigation, html, web, netscape, explorer, IE, opera, DOM, control, cross browser, support, frames, target, download">
	<link rel="shortcut icon" href="http://www.softcomplex.com/products/tigra_menu/favicon.ico">
	<meta name="robots" content="index,follow">
  <link rel="stylesheet" type="text/css" href="vs/calendar/calendar.css">
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	
	<link rel="stylesheet" href="menu.css">
   
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="scripts/overlib/overlib.js"></script>
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<script language="JavaScript">

	function validar(){ 
	 
		if (document.frm.anio.value==0){
			 alert('Debe seleccionar año');
			 document.frm.anio.focus();
			 return false;
		}	 
		
		if (document.frm.mesDesde.value==0){
			 alert('Debe seleccionar mes desde');
			 document.frmDesde.mesDesde.focus();
			 return false;
		}	 
		
		if (document.frm.mesHasta.value==0){
			 alert('Debe seleccionar mes hasta');
			 document.frm.mesHasta.focus();
			 return false;
		}	 
		
		document.frm.submit(); 
	
	}
	
</script>
<%	
// instancio el contable
try {
	General general = Common.getGeneral();

	List meses = general.getGlobalMeses();
	IterMeses = meses.iterator();

	Contable contable = Common.getContable();

	List ejercicioContables = contable.getEjerciciosAll(new BigDecimal(session.getAttribute("empresa").toString() ));
	iterEjercicios = ejercicioContables.iterator();		
	%>
	<link rel = "stylesheet" href = "<%= pathskin %>">
  </head>
	<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<div id="popupcalendar" class="text"></div>
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
	<form action="frmCONTABLESaldoCuentas.jsp" name="frm" method="post"  >
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
						<td width="243"  ><%=titulo%></td>
						<td>
						  <div align="center">
							<table width="13%" height="22" border="0" align="left" cellpadding="0" cellspacing="0">
								<tr>
									<td width="44%"><a href="javascript:popupHelp();" ><img src="imagenes/default/gnome_tango/apps/help.png" width="22" height="22" border="0"></a></td>
									<td width="56%"><div align="left"><a href="javascript:popupPrint()"><img src="imagenes/default/gnome_tango/actions/gtk-print-preview.png" width="22" height="22" border="0"></a></div></td>
								</tr>
							</table>
							</div>
						</td>
						<td><div align="center"></div></td>
					</tr>
			 </table>
				<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1"  class="fila-det">		
					<tr class="fila-det">
						<td width="11%" height="23" class="fila-det-border"  >Año(*)</td>
						<td width="89%" class="fila-det-border" >
						<select name="anio" class="campo">                       
							<option value="0">Seleccionar</option>
						<%                       
							while(iterEjercicios.hasNext()){
								String selected = "";                      
								String[] sCampos = (String[]) iterEjercicios.next(); 
								if(anio.equalsIgnoreCase(sCampos[0])) selected = "selected";
						%>
								<option value="<%=sCampos[0]%>" <%=selected%>><%=sCampos[0]%></option>
						<%
							}%>
						</select>   
						</td>		
					</tr>		
					<tr class="fila-det">
						<td height="23" class="fila-det-border"  >Mes Desde(*)</td>
						<td width="89%" class="fila-det-border" >
						<select name="mesDesde" class="campo">                       
							<option value="0">Seleccionar</option>                      
								<%
								while(IterMeses.hasNext()){
									String selected = "";                      
									String[] sCampos = (String[]) IterMeses.next(); 
									if(mesDesde.equalsIgnoreCase(sCampos[0])) selected = "selected";
							%>
									<option value="<%=sCampos[0]%>" <%=selected%>><%=sCampos[1]%></option>
							<%
								}%>
						</select>   
						</td>		
					</tr>
					<tr class="fila-det">
						<td height="23" class="fila-det-border"  >Mes Hasta(*)</td>
						<td width="89%" class="fila-det-border" >
							<select name="mesHasta" class="campo">
							<% IterMeses  = meses.iterator();  %>                       
								<option value="0">Seleccionar</option>                      
								<%
								while(IterMeses.hasNext()){
									String selected = "";                      
									String[] sCampos = (String[]) IterMeses.next(); 
									if(mesHasta.equalsIgnoreCase(sCampos[0])) selected = "selected";
							%>
									<option value="<%=sCampos[0]%>" <%=selected%>><%=sCampos[1]%></option>
							<%
								}%>
							</select>   
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
						<td width="18%">
						<%if(_nivel == 2){%>
								<input name="aceptar" type="button" class="boton" value="Aceptar" onClick="validar();">
						<%}%>		
							&nbsp;
						</td>
						<td width="82%">&nbsp;
								<input name="bReset" type="reset" class="boton" value="Reset">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<%
	Iterator iterSaldoCuentas=null;
	if( !anio.equalsIgnoreCase("") &&  
	    !mesDesde.equalsIgnoreCase("") &&  
			!mesHasta.equalsIgnoreCase("") ){

		List saldoCuentas    = contable.getSaldoCuentasPeriodoAll( Integer.parseInt(anio),  Integer.parseInt(mesDesde), Integer.parseInt(mesHasta),new BigDecimal(session.getAttribute("empresa").toString() ) );
		iterSaldoCuentas   = saldoCuentas.iterator();
		boolean existenReg = false;
		boolean esPrimero  = true;
		String color_fondo = "";
		String esImputable = "";
		while ( iterSaldoCuentas.hasNext() ) {
			String[] sCampos = (String[]) iterSaldoCuentas.next(); 
			String idcuenta  = str.esNulo(sCampos[0]);
			String cuenta    =  str.esNulo(sCampos[1]);
			String imputable =  str.esNulo(sCampos[2]);
			String nivel     =  str.esNulo(sCampos[3]);
			String resultado =  str.esNulo(sCampos[4]);
			String centcost  =  str.esNulo(sCampos[5]);
			String centcost1 =  str.esNulo(sCampos[6]);			
			String debe      =  str.esNulo(general.getNumeroFormateado( Float.parseFloat(sCampos[7] ), 3 , 3 ) );
			String haber     =  str.esNulo( general.getNumeroFormateado( Float.parseFloat(sCampos[8] ), 3 , 3 ));
			String saldo     =  str.esNulo( general.getNumeroFormateado( Float.parseFloat(sCampos[9] ), 3 , 3 ));
      if(!esImputable.equalsIgnoreCase(imputable)){
			  if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
			  else color_fondo = "fila-det-verde"; 
				esImputable = imputable;
			}
			existenReg = true;
			if (esPrimero) {
				 esPrimero = false; %>
		<table width="95%"  cellPadding=0 cellSpacing=0  border="0" align="center">
			<tr class=fila-encabezado>               
				<td width="13%" height="13">Nro. Cuenta</td>
			  <td width="44%">Cuenta</td>				
			  <td width="15%"><div align="right">Debe</div></td>
			  <td width="14%"><div align="right">Haber</div></td>
			  <td width="14%"><div align="right">Saldo</div></td>
			</tr>

			<%
			} 
			if (imputable.equalsIgnoreCase("n")) {			
			 %>
			<tr class=fila-encabezado>               
				<td colspan="5" height="3"></td>
			</tr>
			<% 
			}
			 %>
			<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="row" > 
				<td width="13%" height="13" class="fila-det-border" ><%= idcuenta  %></td>
			  <td width="44%" class="fila-det-border" >&nbsp; <%= str.getNivelStr(".", Integer.parseInt(nivel) * 2) %> <%= cuenta %></td>
			  <td width="15%" class="fila-det-border" ><div align="right" class="<%=  clasePos %>"> <%= debe %></div></td>
			  <td width="14%" class="fila-det-border" ><div align="right" class="<%=  clasePos %>"> <%= haber %></div></td>
			  <td width="14%" class="fila-det-border" ><div align="right" class="<%= general.colorSaldo(saldo, clasePos, claseNeg) %>"> <%= saldo %></div></td>
			</tr> 
		<%
		}
		%>
		</table>
		<%
	}
}		
catch (Exception ex) {
	java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
	java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
	ex.printStackTrace(pw);
	System.out.println("Error (" + pagina + "): " + ex);
}  
%>
</form>
</body>
</html>