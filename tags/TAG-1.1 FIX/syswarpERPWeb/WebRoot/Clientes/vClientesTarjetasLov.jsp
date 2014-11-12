<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesTarjetas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Sep 29 16:48:50 ART 2011 
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

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesTarjetas   = null;
int totCol = 13; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCTA"  class="ar.com.syswarp.web.ejb.BeanVClientesTarjetasLov"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCTA" property="*" />
<%
 String titulo = "TARJETAS DEL CLIENTE: " + BVCTA.getIdcliente() + " - " + BVCTA.getCliente() ;
 BVCTA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BVCTA.setResponse(response);
 BVCTA.setRequest(request);
 BVCTA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script >
 
function bajarDatos(idtarjeta, nrotarjeta, tarjetacredito){
    var j = opener.document.forms.length;
	for(var i = 0; i<j;i++){
	    //alert(j);
		if( opener.document.forms[i].idtarjeta  ){
            opener.document.forms[i].idtarjeta.value = idtarjeta;
			opener.document.forms[i].nrotarjeta.value = nrotarjeta; 
			opener.document.forms[i].tarjetacredito.value = tarjetacredito; 			
			break;			
		}

	}
	window.close();
}
 </script> 
</head>
<%
// titulos para las columnas
tituCol[0] = "idtarjeta";
tituCol[1] = "idcliente";
tituCol[2] = "Tarjeta";
tituCol[3] = "razon";
tituCol[4] = "Tipo";
tituCol[5] = "Número";
tituCol[6] = "nrocontrol";
tituCol[7] = "F.Emision";
tituCol[8] = "F.Venc.";
tituCol[9] = "Titular";
tituCol[10] = "orden";
tituCol[11] = "Activa";
java.util.List VClientesTarjetas = new java.util.ArrayList();
VClientesTarjetas= BVCTA.getVClientesTarjetasList();
iterVClientesTarjetas = VClientesTarjetas.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesTarjetasLov.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%" height="38"><table width="100%" border="0">
                    <tr>
                      <td width="72%" height="26"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td width="1%" height="19">&nbsp;</td>
                                  <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVCTA.getTotalRegistros()%></td>
                                  <td width="11%" >Visualizar:</td>
                                  <td width="11%"><select name="limit" >
                                      <%for(i=15; i<= 150 ; i+=15){%>
                                      <%if(i==BVCTA.getLimit()){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}
                                                      if( i >= BVCTA.getTotalRegistros() ) break;
                                                    %>
                                      <%}%>
                                      <option value="<%= BVCTA.getTotalRegistros()%>">Todos</option>
                                    </select>                                  </td>
                                  <td width="7%">&nbsp;P&aacute;gina:</td>
                                  <td width="12%"><select name="paginaSeleccion" >
                                      <%for(i=1; i<= BVCTA.getTotalPaginas(); i++){%>
                                      <%if ( i==BVCTA.getPaginaSeleccion() ){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}%>
                                      <%}%>
                                    </select>                                  </td>
                                  <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                  </table></td>
              </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCTA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="19%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="24%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[7]%></div></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[8]%></div></td>
     <td width="22%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[11]%></td>
   </tr>
   <%int r = 0;
   while(iterVClientesTarjetas.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesTarjetas.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" onClick="bajarDatos('<%=sCampos[0]%>', '<%=sCampos[5]%>', '<%=sCampos[2]%>' )"></td>
      <td class="fila-det-border" ><%=sCampos[2]%></td>
      <td class="fila-det-border" ><%=sCampos[4]%></td>
      <td class="fila-det-border" ><%=sCampos[5]%></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[7]), "JSTsToStr")%></div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[8]), "JSTsToStr")%></div></td>
      <td class="fila-det-border" ><%=sCampos[9]%></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[11]%></td>
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="idcliente" value="<%= BVCTA.getIdcliente() %>" type="hidden">
   <input name="cliente" value="<%= BVCTA.getCliente() %>" type="hidden">
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

