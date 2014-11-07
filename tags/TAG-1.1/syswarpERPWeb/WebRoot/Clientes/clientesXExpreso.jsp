<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesExpresosCPostal
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Apr 19 14:40:35 GMT-03:00 2010 
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
String titulo = "CONSULTA CLIENTES POR EXPRESO ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesExpresosCPostal   = null;
int totCol = 14; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCECPA"  class="ar.com.syswarp.web.ejb.BeanClientesXExpreso"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCECPA" property="*" />
<%
 BCECPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCECPA.setResponse(response);
 BCECPA.setRequest(request);
 BCECPA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>

  function callOverlib(leyenda){
    overlib(leyenda, STICKY, CAPTION, 'PERIODICIDAD DE ENTREGA',TIMEOUT,5000,HAUTO,WIDTH,150,BGCOLOR, '#9999CC', CAPCOLOR, '#FF0000'); 
  }

 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "idexpreso"; 
tituCol[1] = "Expreso";
tituCol[2] = "idcliente";
tituCol[3] = "Cliente";
tituCol[4] = "idestado";
tituCol[5] = "Estado";
tituCol[6] = "idtipoclie";
tituCol[7] = "Tipo";
tituCol[8] = "idlocalidad";
tituCol[9] = "Localidad";
tituCol[10] = "idprovincia";
tituCol[11] = "Provincia";
tituCol[12] = "Periodicodad";
tituCol[13] = "idempresa";
java.util.List ClientesExpresosCPostal = new java.util.ArrayList();
ClientesExpresosCPostal= BCECPA.getClientesExpresosCPostalList();
iterClientesExpresosCPostal = ClientesExpresosCPostal.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesXExpreso.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="46" colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="5%" height="38">&nbsp;</td>
                   <td width="95%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="9%" height="26" class="text-globales">Expreso</td>
                           <td width="22%" class="text-globales"><span class="fila-det-border">
                           <select name="idexpreso" id="idexpreso" class="campo" style="width:80%" >
                               <option value="">Seleccionar</option>
                               <%
      Iterator iter = BCECPA.getListExpresos().iterator();
			while(iter.hasNext()){
			String [] datos = (String[])iter.next();%>
                               <option value="<%= datos[0] %>" <%= datos[0].equals( BCECPA.getIdexpreso().toString()) ? "selected" : "" %>><%= datos[1] %></option>
                               <%  
			}%>
                             </select>
                           </span></td>
                           <td width="69%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>




                                       <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCECPA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCECPA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCECPA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCECPA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCECPA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCECPA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select>                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                        </tr>
                                    </table>                                  </td>
                              </tr>
                           </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCECPA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="21%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
     <td width="21%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[9]%></td>
     <td width="25%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[11]%></td>
    </tr>
   <%int r = 0;
   while(iterClientesExpresosCPostal.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterClientesExpresosCPostal.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><%=sCampos[2]%>-<%=sCampos[3]%>&nbsp;</td>
     <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
     <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
     <td class="fila-det-border" ><%=sCampos[9]%>&nbsp;</td>
     <td class="fila-det-border" ><%=sCampos[11]%>&nbsp;</td>
    </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/date.png" title="Peiodicidad" width="18" height="18" onClick="callOverlib('<%=sCampos[12].replaceAll("-", "<br>")%>')" ></td>
     <td colspan="4" class="fila-det-border" ><%=sCampos[12]%>&nbsp;</td>
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

