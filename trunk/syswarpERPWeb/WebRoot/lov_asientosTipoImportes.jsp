<%@page import="javax.servlet.http.*"%>
<%@page import="java.security.*"%>
<%@page import="javax.naming.*"%>
<%@page import="javax.naming.directory.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.*" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.Common"%>

<%
Strings str = new Strings();

String importes [] = null;
String tiposmov [] = null;
String cuentas  [] = null;
String detalles [] = null;

String cargar = str.esNulo(request.getParameter("cargar")); 
String codigo   = request.getParameter("codigo");	
String leyenda = str.esNulo(request.getParameter("leyenda")); 
if (codigo == null) codigo = "";
//String pathskin = "imagenes/default/"; // cambiar
String pathskin = session.getAttribute("pathskin").toString();	
int ejercicioActivo =  Integer.parseInt( (String)session.getAttribute("ejercicioActivo") );
int decimales = 3;
%>
<html>
<head>
<title>Carga de Importes - Asientos Tipo</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript">
function validar(){
  var balance = 0;
	var auxImporte = 0;
	var i = 0;
	var total = 0;
  for(i = 0 ;i<document.forms[0].elements.length;i++){
	  var element = document.forms[0].elements[i];
		if(element.type == 'text'){
		  total++;
			if(!validarNumerico(element ,10, <%= decimales %>)) return false;
			if(trim(element.value)==""){
			  alert("Ingrese importe.")
				element.focus();
				return false;
			}
			if( parseFloat(element.value) <= 0 ){
			  alert("El importe debe ser mayor a cero.")
				element.select();
				return false;
			}
			auxImporte = element.value;
		}
		if(element.type == 'select-one'){
		  if(element.selectedIndex == 0){
			  alert("Seleccione tipo de registro.")
				element.focus();
				return false;
			}
			balance  = element.value == 1 ?  ( parseFloat(balance) + parseFloat(auxImporte) ) :   ( parseFloat(balance) + parseFloat(-auxImporte) ) ;
		}
	}

  balance =( Math.round(balance * 1000)  / 1000 );

	if ( balance !=0 ){
		alert("El asiento no esta balanceado: " + balance)
		return false;	
	}

	if(confirm("Confirma?")){
	   return true;
	}else{
	  return false;
	}
}
</script>    
<script language="JavaScript" src="vs/forms/forms.js"></script>

<link rel = "stylesheet" href = "<%= pathskin %>">
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0">
<p align="left" class=text-seis>Carga de Importes.</p>
<hr>
<form action="lov_asientosTipoImportes.jsp" onSubmit="javascript:return validar(); ">
<%  
try{ 
	java.util.List AsientosTipo =  new ArrayList();
	Iterator iterAsientosTipo=null;
  int totReg  = 0;	 
	Contable contable = Common.getContable();
	General general = Common.getGeneral();         
		if (!codigo.equals("")) {   	  	      
			AsientosTipo =  contable.getAsientosTipoPK(ejercicioActivo, Integer.parseInt(codigo),new BigDecimal(session.getAttribute("empresa").toString() )); 
			iterAsientosTipo = AsientosTipo.iterator();      
			totReg = AsientosTipo.size();     
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
				if (esPrimero) {
					esPrimero = false; %>
				  <input name="leyenda" type="hidden"  id="leyenda"  value="<%= cmp_leyenda %>">
					<table width="82%" border="0" cellspacing="0" cellpadding="0" height="23">
						<tr  class=text-seis> 
							<td height="39" width="15%"> 
								Asiento Tipo: 
							</td>
							<td width="38%" height="39">&nbsp;<%=cmp_codigo%> -  <%=cmp_leyenda%>
							</td>
							<td height="39" width="47%"><input name="cargar" type="submit" class="boton"   value="Cargar Asiento Tipo" ></td>
						</tr>
					</table>			
					<table width="100%"  cellPadding=0 cellSpacing=0  border="0"> 		
					<tr class=fila-encabezado> 
						<td width="16%" height="13">Cuenta</td>
						<td width="44%">Detalle Asiento </td>
						<td width="13%" height="13">Importe</td>
						<td width="27%">Tipo Registro </td>
					</tr>
					<% 
				} 
		   color_fondo = "fila-det-verde";%>		
				 <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="row" > 
					<td width="16%" class="fila-det-border" >&nbsp;
						<a href="javascript:passBack('<%=cmp_codigo%>', '<%=cmp_centcosto%>')">
						</a><%= cmp_cuenta %>&nbsp;
					</td>
					<td width="44%" class="fila-det-border" ><%= cmp_detalle %>&nbsp;</td>
					<td width="13%" class="fila-det-border" >
					  <input name="importe" type="text" class="campo" id="importe" size="10">
					  <input name="cuenta"  type="hidden" id="cuenta"   value="<%= cmp_cuenta %>">
					  <input name="detalle" type="hidden" id="detalle"  value="<%= cmp_detalle %>">
					</td>
					<td width="27%" class="fila-det-border" >
						<select name="tipomov" class="campo" >
							<option value="0">Seleccionar</option>
							<option value="1">Debe</option>
							<option value="2">Haber</option>
						</select>			 
					</td>
				</tr> 
    <%	
			}
			if (!existenReg){ %>
				<p class=text-nueve >No existen registros para el código seleccionado.</p><%	
			}
			else {%>			
		</table>
		<%	
			} 
		}
		else{
			if(!cargar.equalsIgnoreCase("")){
				int nroRenglon = ( session.getAttribute("nroRenglon") != null ? Integer.parseInt((String)session.getAttribute("nroRenglon")) : 0 );
				Hashtable ht2 = ( session.getAttribute("ht2") != null ? (Hashtable)(session.getAttribute("ht2")) : new Hashtable() );
			  String descripcion_mov = "";
				cuentas = request.getParameterValues("cuenta");
				importes = request.getParameterValues("importe");
				tiposmov = request.getParameterValues("tipomov");
				detalles = request.getParameterValues("detalle");				
				
				for(int i =0;i<importes.length;i++){
				  Hashtable ht = new Hashtable();
					/*
					str.wLog("IMPORTES: " + importes[i] ,1);
					str.wLog("TIPOS   : " + tiposmov[i] ,1);
					str.wLog("DETALLES: " + detalles[i] ,1);
					str.wLog("CUENTAS : " + cuentas[i] ,1);
					*/
          descripcion_mov = tiposmov[i].equalsIgnoreCase("1") ? "Debe" : "Haber" ;
					nroRenglon++; 
					ht.put( "cuenta", cuentas[i] );
					ht.put("detalle", detalles[i]);
					ht.put("importe", general.getNumeroFormateado( Float.parseFloat( importes[i] ) , 10, decimales)  );
					ht.put("tipomov", tiposmov[i] );      
					ht.put("descripcion_mov", descripcion_mov);      	
					ht2.put(  nroRenglon + "" , ht);
				}
				session.setAttribute("ht2", ht2);
				session.setAttribute("nroRenglon", nroRenglon + "");				
		  %>
					<script>
					opener.document.frm.leyenda.value = '<%= leyenda %>';
					opener.recargarSession();
					this.close();
					</script>
			<%
			}			
		}
 }
 catch (Exception ex) {
	 java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
	 java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
	 ex.printStackTrace(pw);
	 str.wLog("ex: "+ ex,2 );
}  		
%>
	<input type="hidden" name="codigo" value="">
</form>
</body>
</html>