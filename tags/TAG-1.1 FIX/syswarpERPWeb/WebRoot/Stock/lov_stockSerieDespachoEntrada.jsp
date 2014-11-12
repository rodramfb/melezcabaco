<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: proveedoArticulos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Aug 02 14:39:14 GMT-03:00 2006 
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
Strings str = new Strings();
String color_fondo ="";
String titulo = "INGRESO DE SERIES Y/O DESPACHOS PARA ARTICULO: ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
String stockMostrarPrecios = session.getAttribute("stockMostrarPrecios").toString();

// variables de paginacion
int i = 0;
Iterator iterProveedoArticulos   = null;
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BSSD"  class="ar.com.syswarp.web.ejb.BeanStockSerieDespachoEntrada"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BSSD" property="*" />
<%
 titulo +=  BSSD.getCodigo_st() + " - " + BSSD.getDescrip_st();
 BSSD.setResponse(response);
 BSSD.setRequest(request);
 BSSD.setSession(session);
 BSSD.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BSSD.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script >
<!--

function eliminarRegistro(){
  if(confirm("Confirma eliminar selección ?")){
		  document.frm.accion.value = "eliminar";
		  document.frm.submit();
		}
}

function confirmarDetalle(){
  if(confirm("Confirma datos ingresados ?")){
		  document.frm.accion.value = "confirmar";
		  document.frm.submit();
		}
}

function agregar(){

	document.frm.accion.value = 'agregar';
	document.frm.submit();
 
}

window.onload = function (){
  var accion = '<%=BSSD.getAccion().toLowerCase()%>';
  var mensaje = '<%= BSSD.getMensaje().toLowerCase() %>';
 
  if((accion == 'confirmar' && mensaje == '') || accion == 'eliminar'){
    opener.document.getElementById('<%=BSSD.getIdcantidad()%>').value = <%=BSSD.getCantidadIngresada()%>;
    if(accion == 'confirmar')
      this.close();
  }
}

//-->
</script>
</head>
<%

 
// titulos para las columnas
tituCol[0] = "";

java.util.List ProveedoArticulos = new java.util.ArrayList();
iterProveedoArticulos = ProveedoArticulos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_stockSerieDespachoEntrada.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td height="34" ><%=titulo%></td>
                </tr>
                <tr>
                   <td width="89%" height="38"> 
                      <table width="100%" border="0">
                         <tr>
                           <td width="72%" height="26">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BSSD.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=10; i<= 100 ; i+=10){%>
                                                    <%if(i==BSSD.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BSSD.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BSSD.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BSSD.getTotalPaginas(); i++){%>
                                                    <%if ( i==BSSD.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select>                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>                                 </td>
                              </tr>
                           </table>                        </td>
                     </tr>
                   </table>
                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo">     <jsp:getProperty name="BSSD" property="mensaje"/>     </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >

   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td width="3%" class="fila-det-border" ><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
        <tr class="fila-det-bold-rojo">
          <td class="fila-det-border">&nbsp;</td>
          <td class="fila-det-border">&nbsp;</td>
          <td class="fila-det-border">&nbsp;</td>
        </tr>
        <% 
        if(BSSD.getId_indi_st().equalsIgnoreCase("S")){ %>   
        <tr class="fila-det">
          <td width="24%" height="36" class="fila-det-border">N&uacute;mero de Serie : (*) </td>
          <td width="40%" class="fila-det-border"><input name="nroserie" type="text" class="campo" id="nroserie" value="<%=BSSD.getNroserie()%>" size="30" maxlength="50"></td>
          <td width="36%" class="fila-det-border">&nbsp;</td>
        </tr>
        <%
        } 
        if(BSSD.getDespa_st().equalsIgnoreCase("S")){ %>        
        <tr class="fila-det">
          <td height="34" class="fila-det-border">N&uacute;mero de Despacho : (*) </td>
          <td class="fila-det-border"><input name="nrodespacho" type="text" class="campo" id="nrodespacho" value="<%=BSSD.getNrodespacho()%>" size="30" maxlength="50"></td>
          <td class="fila-det-border">&nbsp;</td>
        </tr>
        <% 
          if(BSSD.getId_indi_st().equalsIgnoreCase("N")){ %>   
        <tr class="fila-det">
          <td height="34" class="fila-det-border">Cantidad Despacho : (*) </td>
          <td class="fila-det-border"><input name="cantdespacho" type="text" class="campo" id="cantdespacho" value="<%=BSSD.getCantdespacho()%>" size="30" maxlength="10"></td>
          <td class="fila-det-border">&nbsp;</td>
        </tr>
        <%
          } 
        }%>
        <tr class="fila-det">
          <td width="24%" height="36" class="fila-det-border">&nbsp;</td>
          <td width="40%" class="fila-det-border"><input name="mas" type="button" class="boton" id="mas" value="Agregar" onClick="agregar()"/></td>
          <td width="36%" class="fila-det-border">&nbsp;</td>
        </tr>

      </table></td>
    </tr>
  </table>
	 <% 
   Hashtable htArticulosSerieDespacho = (Hashtable) session.getAttribute("htArticulosSerieDespacho");
	 if(htArticulosSerieDespacho != null && 
      !htArticulosSerieDespacho.isEmpty() &&  
      htArticulosSerieDespacho.containsKey( BSSD.getRelacionHash() )){
	   %> 	 
		 &nbsp; 
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr  class="text-globales" height="3">
				<td>&nbsp;</td>
			  <td>&nbsp;</td>
			  <td>&nbsp;</td>
			  <td>&nbsp;</td>
			</tr>		
			<tr  class="fila-det-bold">
			 <td width="5%">&nbsp;Sel.</td>
				<td width="15%">&nbsp; Serie </td>
				<td width="39%">&nbsp; Despacho </td>
		    <td width="41%">Cantidad</td>
			</tr>
			<tr  class="text-globales" height="3">
				<td>&nbsp;</td>
			  <td>&nbsp;</td>
			  <td>&nbsp;</td>
		    <td>&nbsp;</td>
			</tr>		
		<%
					String [][] serieDespacho = (String [][]) htArticulosSerieDespacho.get(BSSD.getRelacionHash());
					for (int m=BSSD.getOffset(), r=0;serieDespacho!=null&&m<serieDespacho.length&&r<BSSD.getLimit();m++,r++) {
						/*Hashtable htDepositos = BSSD.getHtDepositos();
						Enumeration enumDepositos = htDepositos.keys();*/ 
						  %>
		<tr class="fila-det">
			<td class="fila-det-border"><input name="delKey" type="checkbox" id="delKey" value="<%= m %>" class="campo">
		  <input  name="keyHashDatosArticulo" type="hidden" value="<%= m %>"></td>
			<td class="fila-det-border">&nbsp;<%= serieDespacho[m][0] %>  </td>
			<td class="fila-det-border">&nbsp;<%= serieDespacho[m][1] %></td>
		  <td class="fila-det-border"><%= serieDespacho[m][2] %></td>
		</tr>							
		<%
					}
	  %>
			<tr  class="text-globales" height="3"> 
				<td>&nbsp;</td>
			  <td>&nbsp;</td>
			  <td>&nbsp;</td>
		    <td>&nbsp;</td>
			</tr>			
		
			<tr  class="fila-det-bold">
			 <td height="40"><input name="eliminar" type="button" class="boton" id="eliminar" value="Eliminar" onClick="eliminarRegistro();"></td>
		   <td height="40">&nbsp;</td>
		   <td height="40"><input name="confirmar" type="button" class="boton" id="confirmar" value="Confirma" onClick="confirmarDetalle();"></td> 
	     <td>&nbsp;</td>
			</tr>			
	</table>
<% } %>
 		  <input type="hidden" name="accion" value="" >
      <input name="codigo_st" type="hidden" id="codigo_st" value="<%=BSSD.getCodigo_st()%>" >
      <input name="descrip_st" type="hidden" id="descrip_st" value="<%=BSSD.getDescrip_st()%>" >  
 		  <input type="hidden" name="relacionHash" id="relacionHash"  value="<%=BSSD.getRelacionHash()%>">
 		  <input name="id_indi_st" type="hidden" id="id_indi_st" value="<%=BSSD.getId_indi_st()%>" > 
			<input name="despa_st" type="hidden" id="despa_st" value="<%=BSSD.getDespa_st()%>" >
			<input name="idcantidad" type="hidden" id="idcantidad" value="<%=BSSD.getIdcantidad()%>" >  
<%--  
			if(BSSD.getAccion().equalsIgnoreCase("confirmar")  
			   && BSSD.getMensaje().equals("") ){%>
			  <script>
					      //opener.document.frm.submit(); 
                opener.document.getElementById('< %=BSSD.getIdcantidad()%>').value = < %=BSSD.getCantidadIngresada()%>;
								this.close();
					</script>			
			< %  
			}--%>
			
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