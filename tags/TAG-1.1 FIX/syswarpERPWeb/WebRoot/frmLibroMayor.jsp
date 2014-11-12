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
String grabacion = request.getParameter("grabacion");
String titulo    = "Libro Mayor";


String cuenta = str.esNulo( request.getParameter("cuenta") );
String anio   = str.esNulo( request.getParameter("anio") );
String mes    = str.esNulo( request.getParameter("mes") );
String cent_cost    = str.esNulo( request.getParameter("cent_cost") );
String d_cent_cost    = str.esNulo( request.getParameter("d_cent_cost") );
String cent_cost1    = str.esNulo( request.getParameter("cent_cost1") );
String d_cent_cost1    = str.esNulo( request.getParameter("d_cent_cost1") );
String ayudalink  = "ayuda.jsp?idayuda=10";   

String usuario           = session.getAttribute("usuario").toString();
String ejercicioActivo   = session.getAttribute("ejercicioActivo").toString();
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
	<script language="JavaScript" src="scripts/forms/forms.js"></script>
	<script language="JavaScript" src="scripts/overlib/overlib.js"></script>
	<script language="JavaScript" src="vs/calendar/calendarcode.js"></script><script language="JavaScript">


// generacion de un javascript dinamico a partir del recordset.
function mostrarLOV(pagina) {
		frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=600,height=350,status=yes');
		if (frmLOV.opener == null) 
			frmLOV.opener = self;
}		


function validar(){  
  if (document.frm.cuenta.value.length==0){
     alert('No se puede dejar vacia la cuenta contable');
     document.frm.cuenta.focus();
     return false;
  }	 

  if (document.frm.anio.value==0){
     alert('Debe seleccionar año');
     document.frm.anio.focus();
     return false;
  }	 

  if (document.frm.mes.value==0){
     alert('Debe seleccionar mes');
     document.frm.mes.focus();
     return false;
  }	 
  
  // los centros de costos o pide o no.
  if (document.frm.cent_cost.value.length==0 && !document.frm.cent_cost1.value.length==0 ){
     alert('Los Centros de costos deben estar ambos o ninguno');
     document.frm.cent_cost.focus();
     return false;
  }	 

  if (!document.frm.cent_cost.value.length==0 && document.frm.cent_cost1.value.length==0 ){
     alert('Los Centros de costos deben estar ambos o ninguno');
     document.frm.cent_cost.focus();
     return false;
  }
  
  document.frm.submit();
}

function isCharsInBag (s, bag)
  {
    var i;
    // Search through string's characters one by one.
    // If character is in bag, append to returnString.

    for (i = 0; i < s.length; i++)
    {
        // Check that current character isn't whitespace.
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) {
         if (s.charCodeAt(i) != 10 && s.charCodeAt(i) != 13) return false;
         }
    }
    return true;
  }
  
 
/*
function mOvrOLD(src,clrOver) {
	if (!src.contains(event.fromElement)) {
		src.style.cursor = 'pointer';
		src.bgColor = clrOver;
	}
}

function mOutOLD(src,clrIn) {
	if (!src.contains(event.toElement)) {
		src.style.cursor = 'default';
		src.bgColor = clrIn;
	}
}
*/

function mOvr(src,clrOver) {
		src.style.cursor = 'pointer';
		src.bgColor = clrOver;
}

function mOut(src,clrIn) {
		src.style.cursor = 'default';
		src.bgColor = clrIn;
}
  
  
</script>
<%	
try {
	General general = Common.getGeneral();
	Contable contable = Common.getContable();

    List meses = general.getGlobalMeses();
    IterMeses = meses.iterator();
    
    List ejercicioContables = contable.getEjerciciosAll(new BigDecimal(session.getAttribute("empresa").toString() ));
    iterEjercicios = ejercicioContables.iterator();		
	
}		
					       
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
}  
%>

  <link rel = "stylesheet" href = "<%= pathskin %>">
  </head>
	<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	
<div id="popupcalendar" class="text"></div>
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
		<form action="frmLibroMayor.jsp" name="frm" method="post">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td>		  
		<table width="100%" border="0" cellpadding="0" cellspacing="0" >
          <tr class="fila-det" > 
            <td width="39" height="23"  class="fila-det-border" >&nbsp;</td>
            <td width="243"  class="fila-det-border" >&nbsp; </td>
            <td width="432" class="fila-det-border" >&nbsp;</td>
            <td width="8" class="fila-det-border" >&nbsp;</td>
          </tr>
          <tr valign="top" class="text-globales" > 
            <td width="39" height="23"  >&nbsp;</td>
            <td width="243"  ><%=titulo%></td>
            <td><div align="center">
                <table width="13%" height="22" border="0" align="left" cellpadding="0" cellspacing="0">
                  <tr class="fila-det" >
                    <td width="44%" class="fila-det-border" ><a href="javascript:popupHelp('<%=ayudalink%>');" ><img src="imagenes/default/gnome_tango/apps/help.png" width="22" height="22" border="0"></a></td>
                    <td width="56%" class="fila-det-border" ><div align="left"><a href="javascript:popupPrint()"><img src="imagenes/default/gnome_tango/actions/gtk-print-preview.png" width="22" height="22" border="0"></a></div></td>
                  </tr>
                </table>
              </div></td>
            <td><div align="center"></div></td>
          </tr>
        </table>
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1"  class="fila-det">

      <tr class="fila-det">
        <td width="29%" height="23" class="fila-det-border"  >Cuenta(*)</td>
        <td width="17%" class="fila-det-border" ><table width="78%" border="0">
          <tr class="fila-det-border">
            <td width="61%" ><input type="text" name="cuenta" class="campo" value="<%= cuenta %>" readonly="yes"></td>
            <td width="39%"><img src="imagenes/default/gnome_tango/actions/edit-find.png" width="22" height="22" onClick="mostrarLOV('lov_cuentascontables.jsp')" style="cursor:pointer"></td>
            <input name="cent_cost2" type="hidden" id="cent_cost22" value="<%=cent_cost%>">
          </tr>
        </table>
        </td>
        <td width="24%" class="fila-det-border" >&nbsp; 
			</td>
				<td class="fila-det-border" >&nbsp;</td>
      </tr>

      <tr class="fila-det">
        <td height="23" class="fila-det-border"  >Año(*)</td>
        <td width="17%" class="fila-det-border" >
          <select name="anio" class="campo">
            <option value="0">Seleccionar</option>
            <%                       
                       while(iterEjercicios.hasNext()){
                         String selected = "";                      
                         String[] sCampos = (String[]) iterEjercicios.next(); 
                         if(anio.equalsIgnoreCase(sCampos[0])) selected = "selected";
                       %>
            <option value="<%=sCampos[0]%>" <%=selected%>><%=sCampos[0]%></option>
            <%}%>
          </select>
        </td>
				<td class="fila-det-border" >&nbsp;</td>
				<td class="fila-det-border" >&nbsp;</td>				
      </tr>

      <tr class="fila-det">
        <td height="23" class="fila-det-border"  >Mes(*)</td>
        <td width="17%" class="fila-det-border" >
          <select name="mes" class="campo">
            <option value="0">Seleccionar</option>
            <%while(IterMeses.hasNext()){
                         String selected = "";                      
                         String[] sCampos = (String[]) IterMeses.next(); 
                         if(mes.equalsIgnoreCase(sCampos[0])) selected = "selected";
                       %>
            <option value="<%=sCampos[0]%>" <%=selected%>><%=sCampos[1]%></option>
            <%}%>
          </select>
        </td>
				<td class="fila-det-border" >&nbsp;</td>
				<td class="fila-det-border" >&nbsp;</td>
      </tr>

      <tr class="fila-det">
        <td height="23" class="fila-det-border"  >Centro de Costo</td>
        <td width="17%" class="fila-det-border"><table width="23%" border="0">
          <tr class="fila-det-border">
            <td width="61%" >
              <input name="d_cent_cost" type="text" class="campo" id="d_cent_cost" value="<%=d_cent_cost%>" size="30" readonly></td>
            <td width="39%"><img src="imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('lov_centro_de_costos1.jsp')" style="cursor:pointer"></td>
            <input name="cent_cost" type="hidden" id="cent_cost" value="<%=cent_cost%>">
          </tr>
        </table></td>
        <td width="24%" class="fila-det-border">&nbsp;</td>
        <td width="30%" class="fila-det-border">&nbsp;</td>
      </tr>

      <tr class="fila-det">
        <td height="23" class="fila-det-border"  >Sub Centro de Costo</td>
        <td width="17%" class="fila-det-border"><table width="23%" border="0">
          <tr class="fila-det-border">
            <td width="61%" >
              <input name="d_cent_cost1" type="text" class="campo" id="d_cent_cost1" value="<%=d_cent_cost1%>" size="30" readonly></td>
            <td width="39%"><img src="imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('lov_centro_de_costos2.jsp')" style="cursor:pointer"></td>
            <input name="cent_cost1" type="hidden" id="cent_cost1" value="<%=cent_cost1%>">
          </tr>
        </table></td>
        <td width="24%" class="fila-det-border">&nbsp;</td>
        <td width="30%" class="fila-det-border">&nbsp;</td>
      </tr>

    </table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr class="fila-det-verde"> 
					<td height="29" class="fila-det-border">&nbsp;(*) Indica que el campo es obligatorio</td>
				</tr>
			</table>
			<input type="hidden" name="grabacion" value="SI">			
			<table width="100%" height="32" border="0" align="center" cellpadding="0" cellspacing="0" >
				<tr class="text-seis">
					<td width="18%">
					<%if(_nivel == 2){%>
							<input name="grabar" type="button" class="boton" onClick="validar();" value="Aceptar">
					<%}%>		
						&nbsp;
					</td>
					<td width="82%">&nbsp;
							<input name="Submit2" type="reset" class="boton" value="Reset">
					</td>
				</tr>
			</table>
			</td>
  </tr>
</table>

    <%
Iterator iterCuentasContables=null;
if(cuenta!=null && anio!=null && mes!=null 
    && !cuenta.equalsIgnoreCase("") && !anio.equalsIgnoreCase("") && !mes.equalsIgnoreCase("")  ){
    List libroMayor = null;
    if(cent_cost!=null && cent_cost1!= null && !cent_cost.equalsIgnoreCase("") && !cent_cost1.equalsIgnoreCase("") ){
       libroMayor = contable.getLibroMayor(Integer.valueOf(ejercicioActivo).intValue(), new Long(cuenta),Integer.valueOf(anio).intValue(),Integer.valueOf(mes).intValue(), new Long(cent_cost), new Long(cent_cost1),new BigDecimal(session.getAttribute("empresa").toString() ));
    } else {
       libroMayor = contable.getLibroMayorSinCC(Integer.valueOf(ejercicioActivo).intValue(), new Long(cuenta),Integer.valueOf(anio).intValue(),Integer.valueOf(mes).intValue(),new BigDecimal(session.getAttribute("empresa").toString() ));
    }

   iterCuentasContables = libroMayor.iterator();
   //(int idEjercicio, Long cuenta, int anio, int mes, Long cc1, Long cc2
   
	boolean existenReg = false;
	boolean esPrimero = true;
	String color_fondo = "";
	BigDecimal totalDebe  = new BigDecimal(0);
	BigDecimal totalHaber = new BigDecimal(0);
	
	while ( iterCuentasContables.hasNext() ) {
	if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
    else color_fondo = "fila-det-verde"; 		  
   		String[] sCampos = (String[]) iterCuentasContables.next(); 
   		String cmp_fecha    = sCampos[0];
   		String cmp_asiento  = sCampos[1];
   		String cmp_renglon  = sCampos[2];
   		String cmp_debe     = sCampos[3];
   		String cmp_haber    = sCampos[4];
		String cmp_cc       = sCampos[5];
		String cmp_scc      = sCampos[6];
		String cmp_detalle  = sCampos[7];      		
		    
  	    totalDebe  = totalDebe.add( new BigDecimal( cmp_debe ) );
  	    totalHaber = totalHaber.add(new BigDecimal( cmp_haber ) );			
		
		
   		existenReg = true;
		if (esPrimero) {
  		   esPrimero = false; %>
  <table width="100%"  cellPadding=0 cellSpacing=0  border="0">
    <tr class=fila-encabezado>               
      <td width="8%" height="13"><div align="center">Fecha</div></td>
      <td width="7%" height="13"><div align="right">NºAsiento</div></td>
      <td width="6%" height="13"><div align="right">Renglon</div></td>
      <td width="10%" height="13"><div align="right">Debe</div></td>
      <td width="11%" height="13"><div align="right">Haber</div></td>
      <td width="19%" height="13"><div align="center">C.Costo</div></td>
      <td width="18%" height="13"><div align="center">Sub.CC</div></td>
      <td width="21%" height="13"><div align="center">Detalle</div></td>            
    </tr>
  <%}%>    	
	 <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="row" > 
      <td width="8%" height="13" class="fila-det-border" ><div align="center"><%=cmp_fecha%></div></td>
      <td width="7%" height="13" class="fila-det-border" ><div align="right"><%=cmp_asiento%></div></td>
      <td width="6%" height="13" class="fila-det-border" ><div align="right"><%=cmp_renglon%></div></td>
      <td width="10%" height="13" class="fila-det-border" ><div align="right"><%=cmp_debe%></div></td>
      <td width="11%" height="13" class="fila-det-border" ><div align="right"><%=cmp_haber%></div></td>
      <td width="19%" height="13" class="fila-det-border" ><div align="center"><%=cmp_cc%></div></td>
      <td width="18%" height="13" class="fila-det-border" ><div align="center"><%=cmp_scc%></div></td>
      <td width="21%" height="13" class="fila-det-border" ><div align="center"><%=cmp_detalle%></div></td>      
	  </tr> 
    <%}
	if (!existenReg) { %>
	   <p class=text-nueve >No existen registros para la Búsqueda</p>
    <%	
	}
	else { %>
  </table>
   <%}%>
   <%
    BigDecimal saldo = totalDebe.subtract(totalHaber);       
   %>
   <table width="30%"  cellPadding=0 cellSpacing=0  border="0">
   
    <tr class=fila-encabezado>               
      <td width="50%" height="13"  ><div align="left">Total Debe:</div></td>
      <td width="50%" height="13"  ><div align="right"><%= totalDebe.toString() %></div></td>
    </tr>
    <tr class=fila-encabezado>               
      <td width="50%" height="13"  ><div align="left">Total Haber:</div></td>
      <td width="50%" height="13"  ><div align="right"><%= totalHaber.toString() %></div></td>
    </tr>

    <tr class=fila-encabezado>               
      <td width="50%" height="13"  ><div align="left">Saldo cuenta:</div></td>
      <td width="50%" height="13"  ><div align="right"><%= saldo.toString() %></div></td>
    </tr>
    
   </table>
<%}%>
</form>
</body>
</html>