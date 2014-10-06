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
<%//@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Productos / Articulos";
// variables de entorno

// variables de paginacion
int i = 0;
Iterator iterclientesclientes   = null;
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
%>
<html>
<jsp:useBean id="BSPEL"  class="ar.com.syswarp.web.ejb.BeanStockProductoEsquemaLov"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BSPEL" property="*" />
<%
 String idempresa = str.esNulo(request.getParameter("idempresa"));
 if(idempresa.equals("")) idempresa = "1";
 BSPEL.setResponse(response);
 BSPEL.setRequest(request);
 BSPEL.setIdempresa( new BigDecimal( idempresa ));   
 BSPEL.ejecutarValidacion();
%>
<head> 
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script >
 
function bajarDatos(idesquema, esquema, codigo_st, descrip_st){
    var j = opener.document.forms.length;
	for(var i = 0; i<j;i++){

		if( opener.document.forms[i].idesquema  ){
      opener.document.forms[i].idesquema.value = idesquema;
			opener.document.forms[i].esquema.value = esquema; 
			opener.document.forms[i].codigo_st.value = codigo_st;
			opener.document.forms[i].descrip_st.value = descrip_st;
			break;			
		}
	}
	window.close();
	
}
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód. Esq.";
tituCol[1] = "Esquema";
tituCol[2] = "Descripción Esquema";
tituCol[3] = "Cód. Art./ Prod.";
tituCol[4] = "Articulo / Producto";
tituCol[5] = "Es Serializable";
tituCol[6] = "Numero de Serie";

java.util.List clientesclientes = new java.util.ArrayList();
clientesclientes = BSPEL.getClientesclientesList();
iterclientesclientes = clientesclientes.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_producto_esquema.jsp" method="POST" name="frm">
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
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BSPEL.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BSPEL.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BSPEL.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BSPEL.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BSPEL.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BSPEL.getTotalPaginas(); i++){%>
                                                    <%if ( i==BSPEL.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BSPEL" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="4%" >&nbsp;</td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="30%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="37%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
	 <td width="37%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
	 <td width="37%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
	 <td width="6%" ></td>	
   </tr>
   <%int r = 0;
   while(iterclientesclientes.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterclientesclientes.next(); 
      String title = "";
	  
		for(int f=2;f<3;f++)
	    title += tituCol[f] + ": "+  str.esNulo(sCampos[f])+ "\n";
  	
	  // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det"; 
      else color_fondo = "fila-det-verde";
	  
	  %>
	  
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" onClick="bajarDatos('<%=sCampos[0]%>', '<%=sCampos[1]%>', '<%=sCampos[3]%>', '<%=sCampos[4]%>')"></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=sCampos[9]%>&nbsp;</td>
	  <td class="fila-det-border" ><%=str.esNulo(sCampos[10])%>&nbsp;</td>
  
	  <td class="fila-det-border" >
	    <div align="center"><img src="../imagenes/default/gnome_tango/status/info.png" width="22" height="22" title="<%=title%>"></div></td>	  
   </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
	 <input name="idempresa" type="hidden" id="idempresa" value="<%= idempresa %>">	 	 
</form>
</body>
</html>
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  System.out.println("ERROR ( INTERFACES lov_producto_esquema.jsp ) : "+ex);   
}%>

