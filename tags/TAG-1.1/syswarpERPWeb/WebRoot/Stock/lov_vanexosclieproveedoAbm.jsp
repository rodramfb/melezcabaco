<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: lov_vanexosclieproveedo
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Oct 27 14:42:47 GMT-03:00 2006 
   Observaciones: 
      .
*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.math.*"%>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "BUSQUEDA DE ";

// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterLov_vanexosclieproveedo   = null;
int totCol = 11; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BLA"  class="ar.com.syswarp.web.ejb.BeanLov_vanexosclieproveedoAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BLA" property="*" />
<%
 BLA.setResponse(response);
 BLA.setRequest(request);
 BLA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));    
 BLA.ejecutarValidacion();
 if( BLA.getTipo().equalsIgnoreCase("C") )
   titulo += " CLIENTES";
 else if ( BLA.getTipo().equalsIgnoreCase("P") )
    titulo += " PROVEEDOR";
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 
 <script>
function bajarDatos(codigo, razonsocial, idlocalidad, localidad, domicilio, cp, idprovincia, provincia, cuit, iibb){
	opener.setValoresOrigen(codigo, razonsocial, cuit, iibb, domicilio, cp, provincia, localidad, 'CUIT' );
	
	for(var i = 0; i<10;i++)
		if(opener.document.forms[i].idlocalidad)
			break;

	opener.document.forms[i].idlocalidad.value = idlocalidad;
	opener.document.forms[i].idprovincia.value = idprovincia; 
	opener.document.forms[i].codigo_anexo.value = codigo; 
	
	window.close();
}
 </script>
 
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Razón";
tituCol[2] = "idlocalidad";
tituCol[3] = "Localidad";
tituCol[4] = "Domicilio";
tituCol[5] = "CP";
tituCol[6] = "idprovincia";
tituCol[7] = "Provincia";
tituCol[8] = "cuit";
tituCol[9] = "Ing. Brutos";
tituCol[10] = "tipo";
java.util.List Lov_vanexosclieproveedo = new java.util.ArrayList();
Lov_vanexosclieproveedo= BLA.getLov_vanexosclieproveedoList();
iterLov_vanexosclieproveedo = Lov_vanexosclieproveedo.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_vanexosclieproveedoAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td colspan="2"  ><%=titulo%></td>
                </tr>
                <tr>
                   <td width="2%" height="38">&nbsp;                  </td>
                   <td width="98%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BLA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BLA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BLA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BLA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BLA.getTotalRegistros()%>">Todos</option>
                                             </select>
                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BLA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BLA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BLA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="19%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="19%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
   </tr>
   <%int r = 0;
   while(iterLov_vanexosclieproveedo.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterLov_vanexosclieproveedo.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" onClick="bajarDatos('<%= sCampos[0] %>', '<%= sCampos[1] %>', '<%= sCampos[2] %>', '<%= sCampos[3] %>', '<%= sCampos[4] %>', '<%= sCampos[5] %>', '<%= sCampos[6] %>', '<%= sCampos[7] %>', '<%= sCampos[8] %>', '<%= sCampos[9] %>' )"></div></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[9]%>&nbsp;</td>
   </tr>
<%
   }%>
  </table>
   <input name="accion" value="" type="hidden">
	 <input name="tipo" value="<%=BLA.getTipo()%>" type="hidden">
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

