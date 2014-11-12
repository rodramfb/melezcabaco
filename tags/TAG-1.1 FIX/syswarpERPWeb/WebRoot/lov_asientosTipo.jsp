<%@page import="javax.servlet.http.*"%>
<%@page import="java.security.*"%>
<%@page import="javax.naming.*"%>
<%@page import="javax.naming.directory.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@page import="ar.com.syswarp.api.Common"%>
<%
Strings str = new Strings();
String asiento   = request.getParameter("asiento");	
if (asiento == null) asiento = "";
//String pathskin = "imagenes/default/"; // cambiar
String pathskin = session.getAttribute("pathskin").toString();	
int ejercicioActivo =  Integer.parseInt( (String)session.getAttribute("ejercicioActivo") );
%>
<html>
<head>
<title>B&uacute;squeda de asientos tipo</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
	function validarCampos() {
		if (document.forms[0].asiento.value == "") {
			alert("Debe ingresarse un asiento tipo a buscar");	
			return false;
		}
		document.forms[0].submit();
	}
	
	function passBack(asiento, d_cContable) {  
		var i = 0;
		 for(i=0;i<10;i++)
			 if(opener.document.forms[i].codigo)
				 break;    
		 opener.document.forms[i].codigo.value = asiento; 
		 close();
	}
	
	function cargarImportes(asiento, d_cContable) {
		 document.forms[0].codigo.value =  asiento;
		 document.forms[0].action = "lov_asientosTipoImportes.jsp";
		 document.forms[0].submit();
	}
</script>
<script language="JavaScript" src="vs/forms/forms.js"></script>

<link rel = "stylesheet" href = "<%= pathskin %>">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0">
<p align="left" class=text-seis>B&uacute;squeda de AsientosTipo </p>
<hr>
<form action="lov_asientosTipo.jsp">
  <table width="59%" border="0" cellspacing="0" cellpadding="0" height="23">
    <tr> 
      <td height="39" width="24%"> 
        <div class=text-seis>Asiento Tipo </div>
      </td>
      <td width="54%" height="39"> 
        <input name="asiento" type="text" class="campo" value="<%=asiento%>" size="25">
      </td>
      <td height="39" width="3%"></td>
      <td height="39" width="19%"> 
        <input name="buscar" type="button" class="boton" onClick="validarCampos()" value="Buscar">
      </td>
    </tr>
  </table>
<%  
java.util.List AsientosTipo =  new ArrayList();
Iterator iterAsientosTipo=null;
int totReg  = 0;
try{ 
	Contable contable = Common.getContable(); 
	General general = Common.getGeneral();
					
	AsientosTipo =  contable.getAsientosTipoDetalleOcu(ejercicioActivo, asiento, new BigDecimal(session.getAttribute("empresa").toString() )); 
	iterAsientosTipo = AsientosTipo.iterator();      
	totReg = AsientosTipo.size();     

	if (!asiento.equals("")) {
		boolean existenReg = false;
		boolean esPrimero = true;
		String color_fondo = "";
		String flagAsiento = "";
		while ( iterAsientosTipo.hasNext() ) {
			String[] sCampos = (String[]) iterAsientosTipo.next(); 
			String cmp_codigo = sCampos[0] ;
			String cmp_cuenta =   sCampos[1]  ;
			String cmp_detalle =   str.esNulo(sCampos[2] ) ;
			String cmp_centcosto = str.esNulo(sCampos[3] ) ;
			String cmp_centcostouno = str.esNulo(sCampos[4] );
			String cmp_leyenda =   str.esNulo(sCampos[5] ) ;
			existenReg = true;
			 // t2.codigo, t2.cuenta, t2.detalle, t2.cent_cost, t2.cent_cost1, t1.leyenda
			if (esPrimero) {
				esPrimero = false; %>
		<table width="100%"  cellPadding=0 cellSpacing=0  border="0">
			<tr class=fila-encabezado> 
				<td width="4%" height="13">&nbsp;</td>
				<td width="16%" height="13">Cuenta</td>
				<td width="6%">C.Costo</td>
				<td width="15%">C.Costo Uno </td>
				<td width="28%" height="13">Detalle Asiento </td>
			</tr>
			<%
			} 		  
			if (!flagAsiento.equals(cmp_codigo) ){
				flagAsiento = cmp_codigo;
				color_fondo = "fila-det-bold";
		%>
			 <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="row" > 
				<td width="4%" class="fila-det-border" ><a href="javascript:cargarImportes('<%=cmp_codigo%>', '<%=cmp_centcosto%>')"><img src="../imagenes/default/gnome_tango/apps/accessories-text-editor.png" width="17" height="15" border="0" > </a> &nbsp; </td>
				<td width="16%" class="fila-det-border" >Nro. Asiento : <%=cmp_codigo%>&nbsp;</td>
				<td colspan="3" class="fila-det-border" >Descripci&oacute;n :&nbsp;<%=cmp_leyenda%></td>
			</tr> 	
		<%
			}
			color_fondo = "fila-det-verde";	%>		
			 <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="row" > 
				<td width="4%" class="fila-det-border" >&nbsp;
					<a href="javascript:passBack('<%=cmp_codigo%>', '<%=cmp_centcosto%>')">
					</a>
				</td>
				<td width="16%" class="fila-det-border" ><%= cmp_cuenta %>&nbsp;</td>
				<td width="6%" class="fila-det-border" ><%=cmp_centcosto%>&nbsp;</td>
				<td width="15%" class="fila-det-border" ><%= cmp_centcostouno %>&nbsp;</td>
				<td width="28%" class="fila-det-border" ><%= cmp_detalle %>&nbsp;</td>
			</tr> 
   <%	
		}
		if (!existenReg) { %>
			<p class=text-nueve >No existen registros para la Búsqueda</p>
		<%	
		}
		else { %>
		</table>
<%	} 
	}
}
catch (Exception ex) {
	java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
	java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
	ex.printStackTrace(pw);
}
%>
	<input type="hidden" name="asiento" value="<%=asiento%>">
	<input type="hidden" name="codigo" value="">
</form>
</body>
</html>