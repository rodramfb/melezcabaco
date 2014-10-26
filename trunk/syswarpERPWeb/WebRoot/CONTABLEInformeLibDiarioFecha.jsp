<%@page import="javax.servlet.http.*"%>
<%@page import="java.security.*"%>
<%@page import="javax.naming.*"%>
<%@page import="javax.naming.directory.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.Common"%>
<%
Strings str = new Strings();
String fecha_desde   = str.esNulo(request.getParameter("fecha_desde"));	
String fecha_hasta   = str.esNulo(request.getParameter("fecha_hasta"));	

//String pathskin = "imagenes/default/"; // cambiar
String pathskin = session.getAttribute("pathskin").toString(); 	
int ejercicioActivo =  Integer.parseInt( (String)session.getAttribute("ejercicioActivo") );
String usuario      = session.getAttribute("usuario").toString();
String pagina = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1 );
//String referente = request.getHeader("referer").substring(request.getHeader("referer").lastIndexOf("/") + 1 );
String titulo = "LIBRO DIARIO POR FECHA"; 
String ayudalink  = "ayuda.jsp?idayuda=11";   

%>
<html>
<head>
<title>B&uacute;squeda de asientos</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 
<script language="JavaScript">


function validarCampos() {
	if (document.frm.fecha_desde.value == "") {
		alert("Debe ingresarse un fecha_desde a buscar");	
		return false;
	}
	document.frm.submit();
}

</script>
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>

<link rel = "stylesheet" href = "<%= pathskin %>">
<link rel="stylesheet" type="text/css" href="vs/calendar/calendar.css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body bgcolor="#FFFFFF" text="#000000">
<div id="popupcalendar" class="text"></div>

<form action="" name="frm">
<table width="95%" border="0" cellpadding="0" cellspacing="0"  align="center">
  <tr valign="top" class="text-globales" >
    <td height="23"  ><%=titulo%></td>
    <td>
      <div align="center">
        <table width="13%" height="22" border="0" align="left" cellpadding="0" cellspacing="0">
          <tr>
            <td width="44%"><a href="javascript:popupHelp('<%=ayudalink%>');" ></a></td>
            <td width="56%"><div align="left"><a href="javascript:popupPrint()"></a></div></td>
          </tr>
        </table>
    </div></td>
  </tr>
</table>
  <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr class="fila-det"> 
      <td width="20%" class="fila-det-border">Fecha  Desde (*)</td>
      <td width="21%" class="fila-det-border">
        <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_desde" value="<%= fecha_desde %>" maxlength="12">
        <a class="so-BtnLink" href="javascript:calClick();return false;" 
					 onmouseover="calSwapImg('BTN_date_0', 'img_Date_OVER',true);" 
					 onmouseout="calSwapImg('BTN_date_0', 'img_Date_UP',true);" 
					 onclick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fecha_desde','BTN_date_0');return false;"> 
					 <img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17">
	    </a>  
	  </td>
      <td width="2%" class="fila-det-border">&nbsp;</td>
      <td width="57%" class="fila-det-border">&nbsp; 
      </td>
    </tr>
    <tr class="fila-det">
      <td class="fila-det-border">Fecha  Hasta (*) </td>
      <td class="fila-det-border">       
        <input class="cal-TextBox" onFocus="this.blur()" size="12" readonly type="text" name="fecha_hasta" value="<%= fecha_hasta %>" maxlength="12">
        <a class="so-BtnLink" href="javascript:calClick();return false;" 
					 onmouseover="calSwapImg('BTN_date_1', 'img_Date_OVER',true);" 
					 onmouseout="calSwapImg('BTN_date_1', 'img_Date_UP',true);" 
					 onclick="calSwapImg('BTN_date_1', 'img_Date_DOWN');showCalendar('frm','fecha_hasta','BTN_date_1');return false;"> 
					 <img align="absmiddle" border="0" name="BTN_date_1" src="vs/calendar/btn_date_up.gif" width="22" height="17">
	    </a> 
	  </td>
      <td class="fila-det-border">&nbsp;</td>
      <td class="fila-det-border"><input name="buscar" type="button" class="boton" onClick="validarCampos()" value="Buscar"></td>
    </tr>
  </table>
	<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr class="fila-det-verde"> 
			<td height="29" class="fila-det-border">&nbsp;(*) Indica que el campo es obligatorio</td>
		</tr>
	</table>	
<%  
if (!fecha_desde.equals("")) {
   java.util.List Asientos =  new ArrayList();
   Iterator iterAsientos=null;
   int totReg  = 0;
	 float[] totalesParciales = new float[2];
	 float[] totalesGenerales = new float[2];	 
   try{ 
	General general = Common.getGeneral();
	Contable contable = Common.getContable();

   	Asientos =  contable.getLibroDiario(ejercicioActivo, general.StrToTimestampDDMMYYYY (fecha_desde),general.StrToTimestampDDMMYYYY (fecha_hasta),new BigDecimal(session.getAttribute("empresa").toString() ) ); 
   	iterAsientos = Asientos.iterator();      
   	totReg = Asientos.size();     

		boolean existenReg = false;
		boolean esPrimero = true;
		String color_fondo = "";
		String flagAsiento = "";

		while ( iterAsientos.hasNext() ) {
			String[] sCampos = (String[]) iterAsientos.next(); 
			String cmp_nroasiento = sCampos[0] ;
			String cmp_renglon = sCampos[1] ;
			String cmp_tipomov = sCampos[2] ;
			//String cmp_fecha =   general.TimestampToStrDDMMYYYY ( general.StrToTimestampDDMMYYYYHHMISE( sCampos[3] ) )  ;//sCampos[3]  ; // 
			String cmp_fecha =     sCampos[3]  ; // 15/10/2008 se corrigio un error debido a la formula de arriba.
			String cmp_detalle =   sCampos[4]  ;
			String cmp_leyenda =   sCampos[5]  ;
			String cmp_cuenta =   sCampos[6]  ;
			String cmp_importe =   sCampos[7]  ;
			String cmp_dcuenta =   sCampos[8]  ;			
			existenReg = true;
			if (esPrimero) {
				 %>
		<table width="95%"  cellPadding=0 cellSpacing=0  border="0" align="center">
			<% 
			} 		  
			if (!flagAsiento.equals(cmp_nroasiento) ){
			  flagAsiento = cmp_nroasiento;
			  color_fondo = "fila-det-bold";
			  if(!esPrimero){
		%>
			 <tr  class="fila-det"  > 
				<td width="43%" class="fila-det-border" >Totales Asiento </td>
				<td width="13%" class="fila-det-border" >&nbsp;</td>
				<td width="35%" class="fila-det-border" >&nbsp;</td>
				<td width="9%" class="fila-det-border" ><div align="right"><%= general.getNumeroFormateado(totalesParciales[0], 10, 2) %></div></td> 
				<td width="9%" class="fila-det-border" ><div align="right"><%= general.getNumeroFormateado(totalesParciales[1], 10, 2) %></div></td>
			</tr> 		
			<% 
				  totalesParciales[0]=0;
				  totalesParciales[1]=0;
			  }
			  esPrimero = false;		
			 %>
			 <tr > 
				<td  height="3"  colspan="5" class=fila-encabezado ></td>
			 </tr> 
			 <tr > 
				<td  height="3"  colspan="5" >
					<table width="100%">
					 <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="row" > 
						<td width="16%" class="fila-det" >Nro. Asiento: <%=cmp_nroasiento%>&nbsp;</td>
						<td colspan="2" class="fila-det" >Fecha: <%=cmp_fecha%></td>
						<td width="68%" class="fila-det" >Detalle Diario:&nbsp;<%=cmp_leyenda%></td>
					 </tr> 
					 <tr > 
						<td  height="1"  colspan="4" class=fila-encabezado ></td>
					</tr> 	
					</table>			
				</td>
			</tr> 	  
			 <tr  class="fila-det" > 
				<td width="43%" class="fila-det-border" >Cuenta&nbsp;</td>
				<td width="13%" class="fila-det-border" >Rengl&oacute;n &nbsp;</td>
				<td width="35%" class="fila-det-border" >Detalle &nbsp;</td>
				<td width="9%" class="fila-det-border" ><div align="right">Debe</div></td> 
				<td width="9%" class="fila-det-border" ><div align="right">Haber</div></td>
			</tr> 
		<%
			}
		  color_fondo = "fila-det-verde";
		  if(cmp_tipomov.equalsIgnoreCase("1")){
			  totalesParciales[0]+= Float.parseFloat(cmp_importe);
				totalesGenerales[0]+=Float.parseFloat(cmp_importe);; 
		  }
			else{
			  totalesParciales[1]+= Float.parseFloat(cmp_importe);
				totalesGenerales[1]+=Float.parseFloat(cmp_importe);;		
			}
			
							 
			%>		
			 <tr  class="<%=color_fondo%>"  > 
				<td width="43%" class="fila-det-border" > <%= cmp_cuenta %> - <%= cmp_dcuenta %>&nbsp;</td>
				<td width="13%" class="fila-det-border" ><%=cmp_renglon%>&nbsp;</td>
				<td width="35%" class="fila-det-border" ><%= cmp_detalle %>&nbsp;</td>
				<td width="9%" class="fila-det-border" ><div align="right"><%= cmp_tipomov.equalsIgnoreCase("1") ? cmp_importe : "&nbsp;" %></div></td> 
				<td width="9%" class="fila-det-border" ><div align="right"><%= !cmp_tipomov.equalsIgnoreCase("1") ? cmp_importe : "&nbsp;" %></div></td>
			</tr> 
			<%	
		}
		if (!existenReg) { %>
			<p class=text-nueve >No existen registros para la Búsqueda</p>
		<%	
		}
		else { %>
			 <tr  class="fila-det"  > 
				<td width="43%" class="fila-det-border" >Totales Asiento </td>
				<td width="13%" class="fila-det-border" >&nbsp;</td>
				<td width="35%" class="fila-det-border" >&nbsp;</td>
				<td width="9%" class="fila-det-border" ><div align="right"><%= general.getNumeroFormateado(totalesParciales[0], 10, 2) %></div></td> 
				<td width="9%" class="fila-det-border" ><div align="right"><%= general.getNumeroFormateado(totalesParciales[1], 10, 2) %></div></td>
			</tr> 	
			 <tr > 
				<td  height="3"  colspan="5" class=fila-encabezado ></td>
			 </tr> 
			 <tr  class="fila-det-bold"  > 
				<td colspan="3" class="fila-det-border" ><div align="center">Totales Generales </div></td>
				<td width="9%" class="fila-det-border" >&nbsp;</td> 
				<td width="9%" class="fila-det-border" >&nbsp;</td>
			</tr> 	
			 <tr  class="fila-det-bold"  > 
				<td colspan="3" class="fila-det-border" >Total D&eacute;bito</td>
				<td width="9%" class="fila-det-border" ><div align="right"><%= general.getNumeroFormateado(totalesGenerales[0], 10, 2) %></div></td> 
				<td width="9%" class="fila-det-border" >&nbsp;</td>
			</tr> 
			 <tr  class="fila-det-bold"  > 
				<td colspan="3" class="fila-det-border" >Total Cr&eacute;dito </td>
				<td width="9%" class="fila-det-border" >&nbsp;</td> 
				<td width="9%" class="fila-det-border" ><div align="right"><%= general.getNumeroFormateado(totalesGenerales[1], 10, 2) %></div></td>
			</tr> 													
		</table>
			<%	
		} 
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
		 System.out.println("Error (" + pagina + ")" + ex);
  }  		
}
	%>
</form>

</body>
</html>
