<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vPedidosRegalosValorizacionHijos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Dec 23 09:59:27 ART 2010 
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
Iterator iterVPedidosRegalosValorizacionHijos   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVPRVHA"  class="ar.com.syswarp.web.ejb.BeanVPedidosRegalosValorizacionHijos"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVPRVHA" property="*" />
<%
 String titulo = "DETALLE DE PEDIDOS HIJOS PARA ORDEN " + BVPRVHA.getIdpedido_regalos_padre()+ " - CLIENTE: " + BVPRVHA.getIdcliente() + " - " + BVPRVHA.getCliente();
 BVPRVHA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BVPRVHA.setResponse(response);
 BVPRVHA.setRequest(request);
 BVPRVHA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "idpedido_regalos_padre";
tituCol[1] = "Pedido";
tituCol[2] = "Cód.Art.";
tituCol[3] = "Artículo";
tituCol[4] = "Cantidad";
tituCol[5] = "Precio";
tituCol[6] = "Importe";
tituCol[7] = "ImporteIVA";
tituCol[8] = "Descuento";
tituCol[9] = "idempresa";
java.util.List VPedidosRegalosValorizacionHijos = new java.util.ArrayList();
VPedidosRegalosValorizacionHijos= BVPRVHA.getVPedidosRegalosValorizacionHijosList();
iterVPedidosRegalosValorizacionHijos = VPedidosRegalosValorizacionHijos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vPedidosRegalosValorizacionHijos.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="36" colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%" height="38">&nbsp;</td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="72%" height="26">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                         
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVPRVHA.getTotalRegistros()%></td>
                                       </tr>
                                    </table>                                 </td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVPRVHA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="7%" height="22" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[1]%></div></td>
     <td width="49%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[4]%></div></td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[5]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%></div></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[7]%></div></td>
    </tr>
   <%int r = 0;
   double totalParcialSinIva = 0.00;
   double totalParcialConIva = 0.00;
   while(iterVPedidosRegalosValorizacionHijos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVPedidosRegalosValorizacionHijos.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
  	  totalParcialSinIva += Double.parseDouble(sCampos[6]);
      totalParcialConIva += Double.parseDouble(sCampos[7]);
	  
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="right"><%=sCampos[1]%></div></td>
      <td class="fila-det-border" ><%=sCampos[2]%><%=sCampos[3]%></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[4]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[5]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[6]%></div></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[7]%></div></td>
    </tr>
<%
   }
    if(r>0){
  
   %>
   <tr class="text-dos-bold" > 
      <td colspan="4"><div align="right"> Total </div></td>
      <td  ><div align="right"><%= Common.getNumeroFormateado(totalParcialSinIva, 10, 2)%></div></td>
      <td  ><div align="right"><%= Common.getNumeroFormateado(totalParcialConIva, 10, 2)  %></div></td>
    </tr> 
 <% 
   }
 %>    
  </table>
   <input name="idpedido_regalos_padre" type="hidden" id="idpedido_regalos_padre" value="<%=BVPRVHA.getIdpedido_regalos_padre()%>">
   <input name="idcliente" type="hidden" id="idcliente" value="<%=BVPRVHA.getIdcliente()%>">
   <input name="cliente" type="hidden" id="cliente" value="<%=BVPRVHA.getCliente()%>">
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

