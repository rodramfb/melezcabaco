<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: produccionEsquemas_Cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Feb 13 09:18:39 GMT-03:00 2007 
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
String titulo = "ESQUEMAS DE PRODUCCION";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterProduccionEsquemas   = null;
int totCol = 11; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPECA"  class="ar.com.syswarp.web.ejb.BeanLovPedidosEsquemasContieneArt"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPECA" property="*" />
<%
 BPECA.setResponse(response);
 BPECA.setRequest(request);
 BPECA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPECA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel = "stylesheet" href = "<%= pathskin %>">
  <link rel = "stylesheet" href = "../imagenes/default/erp-style.css">

 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 
  function mostrarMensaje(mensaje){
	  overlib( mensaje , STICKY, CAPTION, '[INFO]',TIMEOUT,25000,HAUTO,VAUTO,WIDTH,350,BGCOLOR,'#FF9900');
  } 
 
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Esquema";
tituCol[2] = "Descripción Esquema";
tituCol[3] = "Obs.";
tituCol[4] = "Articulo";
tituCol[5] = "Articulo";
tituCol[6] = "Cantidad";
tituCol[7] = "Existente";
tituCol[8] = "Reserva";
tituCol[9] = "C.Dép.";
tituCol[10] = "Depósito";

java.util.List ProduccionEsquemas = new java.util.ArrayList();
ProduccionEsquemas= BPECA.getProduccionEsquemasList();
iterProduccionEsquemas = ProduccionEsquemas.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lovPedidosEsquemasContieneArt.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales"><table width="100%" border="0">
                       <tr>
                         <td width="29%" height="26" class="text-globales"><%=titulo%></td>
                         <td width="71%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                             <tr>
                               <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                   <tr class="text-globales">
                                     <td width="34%" height="19">Criterio de busqueda : </td>
                                     <td width="66%"><%=BPECA.getOcurrencia() + ""%>
                                       <input name="ocurrencia" type="hidden" value="<%=BPECA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30" ></td>
                                   </tr>
                               </table></td>
                             </tr>
                         </table></td>
                       </tr>
                     </table></td> 
                </tr>
                <tr>
                  <td width="13%" height="38"><table width="100%" border="0">
                    <tr>
                      <td width="63%" height="26"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td width="5%" height="19">&nbsp;</td>
                                  <td width="24%">&nbsp;Total de registros:&nbsp;<%=BPECA.getTotalRegistros() + ""%></td>
                                  <td width="7%" >Visualizar:</td>
                                  <td width="17%"><select name="limit" >
                                      <%for(i=15; i<= 150 ; i+=15){%>
                                      <%if(i==BPECA.getLimit()){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}
                                                      if( i >= BPECA.getTotalRegistros() ) break;
                                                    %>
                                      <%}%>
                                      <option value="<%= BPECA.getTotalRegistros()%>">Todos</option>
                                    </select>                                  </td>
                                  <td width="7%">&nbsp;P&aacute;gina:</td>
                                  <td width="15%"><select name="paginaSeleccion" >
                                      <%for(i=1; i<= BPECA.getTotalPaginas(); i++){%>
                                      <%if ( i==BPECA.getPaginaSeleccion() ){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}%>
                                      <%}%>
                                    </select>                                  </td>
                                  <td width="25%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BPECA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="2" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td ><%=tituCol[10]%></td>
    <td colspan="5" bgcolor="#FFFFCC" ><div align="center"><%=tituCol[1]%></div></td>
    <td colspan="3" bgcolor="#FFE3C6" ><div align="center"><%=tituCol[4]%></div></td>
    <td ><%=tituCol[3]%></td>
  </tr>
  <tr class="fila-encabezado">
     <td >&nbsp;</td>
     <td bgcolor="#FFFFCC" >C&oacute;d.</td>
     <td bgcolor="#FFFFCC" >Desc.</td>
     <td bgcolor="#FFFFCC" ><div align="right">Exist.</div></td>
     <td bgcolor="#FFFFCC" ><div align="right">Res.</div></td>
     <td bgcolor="#FFFFCC" ><div align="right">Disp.</div></td>
     <td bgcolor="#FFE3C6" >C&oacute;d.</td>
     <td bgcolor="#FFE3C6" ><div align="right">Cant. X Esq. </div></td>
     <td bgcolor="#FFE3C6" ><div align="right">Disp.</div></td>
     <td ><div align="center"></div></td>
   </tr>
   <%int r = 0;
   while(iterProduccionEsquemas.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterProduccionEsquemas.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" >&nbsp;<%=sCampos[10].length() > 20  ? sCampos[10] .substring(0, 20) + "": sCampos[10]%></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><%=sCampos[1]%></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><%=sCampos[2].length() > 10  ? sCampos[2] .substring(0, 10) + "": sCampos[2]%></td> 
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="right"><%=sCampos[7]%></div></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="right"><%=sCampos[8]%></div></td>
      <td bgcolor="#FFFFCC" class="fila-det-border" ><div align="right"><%=  (new BigDecimal( sCampos[7] )).subtract(new BigDecimal(sCampos[8]))   %></div></td>
      <td bgcolor="#FFE3C6" class="fila-det-border" ><%=sCampos[4]%></td>
      <td bgcolor="#FFE3C6" class="fila-det-border" ><div align="right"><%=sCampos[6]%></div></td>
      <td bgcolor="#FFE3C6" class="fila-det-border" ><div align="right"><%= ( (new BigDecimal( sCampos[7] )).subtract(new BigDecimal(sCampos[8])) ).multiply(new BigDecimal(sCampos[6]))  %></div></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/status/stock_dialog-info.png" width="22" height="22" onMouseMove="mostrarMensaje('Observaciones:<br>  <%=sCampos[3] + "<br>Articulo:<br>" + sCampos[4] + " - " + sCampos[5]  %>')"></div></td>
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
	 <input name="replicar" value="false" type="hidden">
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


