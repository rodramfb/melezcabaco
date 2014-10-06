<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesLCSASVentasDescuentoAplicadoDeta
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri May 04 10:48:13 ART 2012 
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
String titulo = "VENTAS DESCUENTOS APLICADOS DETALLE";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesLCSASVentasDescuentoAplicadoDeta   = null;
int totCol = 22; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCLCSASVDADA"  class="ar.com.syswarp.web.ejb.BeanVClientesLCSASVentasDescuentoAplicadoDetaAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCLCSASVDADA" property="*" />
<%
 BVCLCSASVDADA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BVCLCSASVDADA.setResponse(response);
 BVCLCSASVDADA.setRequest(request);
 BVCLCSASVDADA.ejecutarValidacion();
 Iterator iter;
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>

 <script>
 

 
 
 window.onload = function(){
 
   if(document.frm.soloLectura.value == "true"){ 
 
     document.frm.mes.onfocus =  function(){ document.frm.mes.blur(); }; 
     document.frm.anio.onfocus =  function(){ document.frm.anio.blur(); }; 
     document.frm.idestado.onfocus =  function(){ document.frm.idestado.blur(); }; 
     document.frm.idtipoclie.onfocus =  function(){ document.frm.idtipoclie.blur(); }; 
	 document.frm.porcdesc_apli.onfocus =  function(){ document.frm.porcdesc_apli.blur(); }; 

   }
   
 }
 
 
 </script> 
 
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Razón";
tituCol[2] = "idcategoriasocio";
tituCol[3] = "Categoria";
tituCol[4] = "idtipoclie";
tituCol[5] = "Artículo";
tituCol[6] = "descrip_st";
tituCol[7] = "Cantidad";
tituCol[8] = "Descuento";
tituCol[9] = "idmotivodescuento";
tituCol[10] = "Motivo";
tituCol[11] = "Importe";
tituCol[12] = "F.Entrega";
tituCol[13] = "Origen";
tituCol[14] = "obsentrega";
tituCol[15] = "Pedido";
tituCol[16] = "idestado";
tituCol[17] = "estado";
tituCol[18] = "idempresa";
tituCol[19] = "U.Carga";
tituCol[21] = "F.Carga";
java.util.List VClientesLCSASVentasDescuentoAplicadoDeta = new java.util.ArrayList();
VClientesLCSASVentasDescuentoAplicadoDeta= BVCLCSASVDADA.getVClientesLCSASVentasDescuentoAplicadoDetaList();
iterVClientesLCSASVentasDescuentoAplicadoDeta = VClientesLCSASVentasDescuentoAplicadoDeta.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesLCSASVentasDescuentoAplicadoDetaAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="40"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr height="3px">
                  <td height="3px" bgcolor="#FFFFFF"></td>
                </tr>
                <tr>
                   <td width="11%" height="38"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                     <tr class="text-globales">
                       <td width="11%" height="33">Total de registros:</td>
                       <td width="19%">&nbsp;<%=BVCLCSASVDADA.getTotalRegistros() + ""%></td>
                       <td width="10%" >Visualizar:</td>
                       <td width="17%"><select name="limit" >
                           <%for(i=15; i<= 150 ; i+=15){%>
                           <%if(i==BVCLCSASVDADA.getLimit()){%>
                           <option value="<%=i%>" selected><%=i%></option>
                           <%}else{%>
                           <option value="<%=i%>"><%=i%></option>
                           <%}
                                                      if( i >= BVCLCSASVDADA.getTotalRegistros() ) break;
                                                    %>
                           <%}%>
                           <option value="<%= BVCLCSASVDADA.getTotalRegistros()%>">Todos</option>
                         </select>                       </td>
                       <td width="10%">P&aacute;gina:</td>
                       <td width="21%"><select name="paginaSeleccion" >
                           <%for(i=1; i<= BVCLCSASVDADA.getTotalPaginas(); i++){%>
                           <%if ( i==BVCLCSASVDADA.getPaginaSeleccion() ){%>
                           <option value="<%=i%>" selected><%=i%></option>
                           <%}else{%>
                           <option value="<%=i%>"><%=i%></option>
                           <%}%>
                           <%}%>
                         </select>                       </td>
                       <td width="12%" class="text-globales">&nbsp;</td>
                     </tr>
                     <tr class="text-globales">
                       <td height="32">Periodo(*):</td>
                       <td><table width="66%" border="0" cellspacing="0" cellpadding="0">
                           <tr>
                             <td width="34%"><span class="fila-det-border">
                               <select name="mes" id="mes" class="campo" style="width:75px" >
                                 <option value="-1" selected>Sel.</option>
                                 <% 
									  iter = BVCLCSASVDADA.getListMeses().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                                 <option value="<%= datos[0] %>" <%= Integer.parseInt(datos[0] ) == ( BVCLCSASVDADA.getMes() ) ? "selected" : "" %>><%= datos[1] %></option>
                                 <%  
									  }%>
                               </select>
                             </span></td>
                             <td width="66%"><span class="fila-det-border">
                               <select name="anio" id="anio" class="campo" style="width:75px" >
                                 <option value="-1" selected>Sel.</option>
                                 <% 
									      for(int y = BVCLCSASVDADA.getAnioActual() - 10; y< BVCLCSASVDADA.getAnioActual()+10; y++){%>
                                 <option value="<%= y %>" <%= y ==  BVCLCSASVDADA.getAnio()  ? "selected" : "" %>><%= y %></option>
                                 <%  
									      }%>
                               </select>
                             </span></td>
                           </tr>
                       </table></td>
                       <td >Tipo Cliente(*):</td>
                       <td><span class="fila-det-border">
                         <select name="idtipoclie" id="idtipoclie" class="campo" style="width:80%" >
                           <option value="-1" selected>Sel.</option>
                           <% 
									  iter = BVCLCSASVDADA.getListTipoCliente().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                           <option value="<%= datos[0] %>" <%= datos[0].equals( BVCLCSASVDADA.getIdtipoclie().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                           <%  
									  }%>
                         </select>
                       </span></td>
                       <td>Estado(*):</td>
                       <td><span class="fila-det-border">
                         <select name="idestado" id="idestado" class="campo" style="width:80%" >
                           <option value="-1" >Sel.</option>
                           <% 
									  iter = BVCLCSASVDADA.getListEstados().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();
										//if(datos[0].equals( "1" ))continue;
										%>
                           <option value="<%= datos[0] %>" <%= datos[0].equals( BVCLCSASVDADA.getIdestado().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
                           <%  
									  }%>
                         </select>
                       </span></td>
                       <td class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                     </tr>
                     <tr class="text-globales">
                       <td height="32">% Descuento(*): </td>
                       <td><span class="fila-det-border">
                         <select name="porcdesc_apli" id="porcdesc_apli" class="campo" style="width:80%" >
                           <option value="-1" selected>Sel.</option>
                           <% 
									  iter = BVCLCSASVDADA.getListDescuentos().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
                           <option value="<%= datos[1] %>" <%= Double.parseDouble( datos[1] ) == ( BVCLCSASVDADA.getPorcdesc_apli().doubleValue() ) ? "selected" : "" %>><%= datos[1] %></option>
                           <%  
									  }%>
                         </select>
                       </span></td>
                       <td >&nbsp;</td>
                       <td>&nbsp;</td>
                       <td>&nbsp;</td>
                       <td>&nbsp;</td>
                       <td class="text-globales">&nbsp;</td>
                     </tr>
                   </table></td>
              </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCLCSASVDADA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="text-dos-bold">
    <td colspan="3" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Cliente</div></td>
    <td colspan="9" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center">Pedido</div></td>
    </tr>
  <tr class="text-dos">
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="26%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="17%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[7]%></div></td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[11]%></div></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[12]%></div></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[19]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[21]%></div></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[13]%></div></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[15]%></div></td>
    </tr>
   <%int r = 0;
   while(iterVClientesLCSASVentasDescuentoAplicadoDeta.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesLCSASVentasDescuentoAplicadoDeta.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td bgcolor="#CED6F1" class="fila-det-border" ><div align="right"><%=sCampos[0]%></div></td>
      <td bgcolor="#CED6F1" class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[1])%></td>
      <td bgcolor="#CED6F1" class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[3])%></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[5])%></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Float.parseFloat(sCampos[7]), 10, 0)%></div></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" >&nbsp;<%=Common.setNotNull(sCampos[10])%></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="right"><%=Common.getNumeroFormateado(Float.parseFloat(sCampos[11]), 10, 2)%></div></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[12]), "JSDateToStr")%></div></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[19])%></div></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[21]), "JSTsToStrWithHM")%></div></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="center"><%=Common.setNotNull(sCampos[13])%></div></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="right"><a href="javascript: abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=notas_pedido&idpedido_cabe=<%=sCampos[15]%>','pedido',750, 400)"><%=sCampos[15]%></a></div></td>
    </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
   <input name="soloLectura" type="hidden" id="soloLectura" value="<%= BVCLCSASVDADA.isSoloLectura() %>">
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

