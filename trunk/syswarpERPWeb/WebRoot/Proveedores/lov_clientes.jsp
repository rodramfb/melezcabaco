<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: Lov Clientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Enero 02 16:04 2007 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Clientes";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterclientesclientes   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BGLA"  class="ar.com.syswarp.web.ejb.BeanClientesLov"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BGLA" property="*" />
<%
 BGLA.setResponse(response);
 BGLA.setRequest(request);
 BGLA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
  <%-- 20120214 - EJV - Mantis 816   
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script> --%>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script >
 
function bajarDatos(idcliente, cliente, idcondicion, condicion, idvendedor, vendedor, idlista, lista, idmoneda, moneda){
	for(var i = 0; i<10;i++)
		if(opener.document.forms[i].idcliente)
			break;
	opener.document.forms[i].idcliente.value = idcliente;
	opener.document.forms[i].cliente.value = cliente; 
	opener.document.forms[i].idcondicion.value = idcondicion;
	opener.document.forms[i].condicion.value = condicion; 
	opener.document.forms[i].idvendedor.value = idvendedor; 
	opener.document.forms[i].vendedor.value = vendedor; 
	opener.document.forms[i].idlista.value = idlista; 
	opener.document.forms[i].lista.value = lista; 
	opener.document.forms[i].idmoneda.value = idmoneda; 
	opener.document.forms[i].moneda.value = moneda; 
	window.close();
	
}
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Cliente";
tituCol[2] = "Cód. Condicion";
tituCol[3] = "Condicion";
tituCol[4] = "Cód. Vendedor";
tituCol[5] = "Vendedor";
tituCol[6] = "Cód. Lista";
tituCol[7] = "Lista";
tituCol[8] = "Cód. Moneda";
tituCol[9] = "Moneda";

java.util.List clientesclientes = new java.util.ArrayList();
clientesclientes = BGLA.getClientesclientesList();
iterclientesclientes = clientesclientes.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_clientes.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td colspan="2"  ><%=titulo%></td>
                </tr>
                <tr>
                   <td width="3%" height="38">&nbsp;                  </td>
                   <td width="97%">
                      <table width="100%" border="0">
                         <tr>
                           <%-- 20120214 - EJV - Mantis 816
						   <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BGLA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td> --%>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BGLA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BGLA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BGLA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BGLA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BGLA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BGLA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BGLA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="39%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>		
	  <td width="40%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="39%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>	
	 <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>		 
   </tr>
  <tr class="fila-encabezado">
    <td >&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><input name="filtroIdcliente" type="text" value="<%=BGLA.getFiltroIdcliente()%>" id="filtroIdcliente" size="5" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;"></td>
    <td valign="bottom" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><span class="fila-det-border">
      <input name="filtroCliente" type="text" value="<%=BGLA.getFiltroCliente()%>" id="filtroCliente" size="25" maxlength="30">
    </span></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterclientesclientes.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterclientesclientes.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/down.png" width="20" height="20" onClick="bajarDatos('<%=sCampos[0]%>', '<%=sCampos[1]%>', '<%=sCampos[2]%>', '<%=sCampos[3]%>', '<%=sCampos[4]%>', '<%=sCampos[5]%>', '<%=sCampos[6]%>', '<%=sCampos[7]%>', '<%=sCampos[8]%>', '<%=sCampos[9]%>' )"></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[9]%>&nbsp;</td>
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

