<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vStockValorizado
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Nov 13 12:17:44 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.math.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "STOCK VALORIZADO";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVStockValorizado   = null;
int totCol = 7; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVSVA"  class="ar.com.syswarp.web.ejb.BeanVStockValorizado"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVSVA" property="*" />
<%
 BVSVA.setResponse(response);
 BVSVA.setRequest(request);
 BVSVA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVSVA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">  
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Articulo.";
tituCol[2] = "Disponible";
tituCol[3] = "Reservado";
tituCol[4] = "Existencia";
tituCol[5] = "Costo";
tituCol[6] = "Total";
java.util.List VStockValorizado = new java.util.ArrayList();
VStockValorizado= BVSVA.getVStockValorizadoList();
iterVStockValorizado = VStockValorizado.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="stockValorizado.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="3%" height="38">&nbsp;                  </td>
                   <td width="97%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="9%" height="26" class="text-globales">Buscar</td>
                           <td width="20%">
                              <input name="ocurrencia" type="text" value="<%=BVSVA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">
                           </td>
                           <td width="71%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVSVA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BVSVA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BVSVA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                             </select>                                          </td>
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
			<tr>
			  <td  colspan="2" class="text-globales"><div align="center">
				  <input name="paginaSeleccion" type="hidden" value="1">
				P&aacute;gina: <%= Common.getPaginacion(BVSVA.getTotalPaginas(),
				BVSVA.getTotalRegistros(), BVSVA.getPaginaSeleccion(), BVSVA.getLimit(),
				BVSVA.getOffset()) %> </div></td>
			</tr>				
			
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVSVA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="5%" ><%=tituCol[0]%></td>
     <td width="45%" ><%=tituCol[1]%></td>
     <td width="10%" ><%=tituCol[2]%></td>
     <td width="10%" ><%=tituCol[3]%></td>
     <td width="10%" ><%=tituCol[4]%></td>
     <td width="10%" ><%=tituCol[5]%></td>
     <td width="10%" ><%=tituCol[6]%></td>
  </tr>
   <%int r = 0;
   while(iterVStockValorizado.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVStockValorizado.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%=sCampos[0]%></td>
      <td class="fila-det-border" ><%=sCampos[1]%></td>
      <td class="fila-det-border" ><%=sCampos[2]%></td>
      <td class="fila-det-border" ><%=sCampos[3]%></td>
      <td class="fila-det-border" ><%=sCampos[4]%></td>
      <td class="fila-det-border" ><%=sCampos[5]%></td>
      <td class="fila-det-border" ><%=sCampos[6]%></td>
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

