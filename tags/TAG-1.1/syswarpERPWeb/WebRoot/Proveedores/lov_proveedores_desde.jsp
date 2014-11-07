<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: proveedoProveed
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jul 05 15:38:22 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.math.*"%> 
<%@ page import="ar.com.syswarp.api.*" %> 

<%
String  pagina = "";
try{

// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Proveedores";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterProveedoProveed   = null;
int totCol = 37; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPPA"  class="ar.com.syswarp.web.ejb.BeanProveedoProveedAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPPA" property="*" />
<%
 BPPA.setResponse(response);
 BPPA.setRequest(request);
 BPPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPPA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 function passBack(idproveedordesde, dproveedordesde) {
  var openDesde = (opener.location + "").substring(( opener.location + "" ).lastIndexOf("/") + 1 ).toLowerCase();
  var i = 0;
   for(i=0;i<10;i++)
     if(opener.document.forms[i].idproveedordesde)
       break;    
   opener.document.forms[i].idproveedordesde.value = idproveedordesde;
   if(opener.document.forms[i].dproveedordesde)
     opener.document.forms[i].dproveedordesde.value = dproveedordesde;
   if(opener.document.forms[i].proveedor)
	   opener.document.forms[i].proveedor.value = dproveedordesde;
	 if(openDesde == "cajapagosabm.jsp") opener.document.frm.submit();
		 
   close();
}
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Codigo";
tituCol[1] = "Razon Social";
tituCol[2] = "Domicilio";
tituCol[3] = "Localidad";
tituCol[4] = "Provincia";
tituCol[5] = "Postal";
tituCol[6] = "Contacto";
tituCol[7] = "Telefono";
tituCol[8] = "Cuit";
tituCol[9] = "Brutos";
tituCol[10] = "cta pasivo";
tituCol[11] = "cta activo1";
tituCol[12] = "cta activo2";
tituCol[13] = "cta activo3";
tituCol[14] = "cta activo4";
tituCol[15] = "cta iva";
tituCol[16] = "cta ret iva";
tituCol[17] = "Letra iva";
tituCol[18] = "cta documen";
tituCol[19] = "ret gan";
tituCol[20] = "Retencion1";
tituCol[21] = "Retencion2";
tituCol[22] = "Retencion3";
tituCol[23] = "Retencion4";
tituCol[24] = "Retencion5";
tituCol[25] = "cta des";
tituCol[26] = "Stock fact";
tituCol[27] = "Condicion pago";
tituCol[28] = "Cent1";
tituCol[29] = "Cent2";
tituCol[30] = "Cent3";
tituCol[31] = "Cent4";
tituCol[32] = "Cents1";
tituCol[33] = "cents2";
tituCol[34] = "Cents3";
tituCol[35] = "Cents4";
java.util.List ProveedoProveed = new java.util.ArrayList();
ProveedoProveed= BPPA.getProveedoProveedList();
iterProveedoProveed = ProveedoProveed.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_proveedores_desde.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td><%=titulo%></td>
                </tr>
                <tr>
                   <td width="89%" height="38">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BPPA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BPPA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BPPA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BPPA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BPPA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BPPA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BPPA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select> 
                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>
                                 </td>
                              </tr>
                           </table>
                        </td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPPA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="41%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="18%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="19%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="17%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
    </tr>
   <%int r = 0;
   while(iterProveedoProveed.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProveedoProveed.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><img src="../imagenes/default/lupa.gif" width="21" height="17" onClick="passBack(<%=sCampos[0]%>, '<%=sCampos[1]%>')"></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
    </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
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

