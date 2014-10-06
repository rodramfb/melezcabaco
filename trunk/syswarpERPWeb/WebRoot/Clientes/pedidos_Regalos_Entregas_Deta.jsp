<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: pedidos_Regalos_Entregas_Deta
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Nov 13 10:01:11 GMT-03:00 2009 
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
Iterator iterPedidos_Regalos_Entregas_Deta   = null;
int totCol = 11; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BPREDA"  class="ar.com.syswarp.web.ejb.BeanPedidos_Regalos_Entregas_Deta"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BPREDA" property="*" />
<%
 String titulo = "DETELLE DE ENTREGA NUMERO: " +  BPREDA.getIdpedido_regalos_entrega_cabe();
 BPREDA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));   
 BPREDA.setResponse(response);
 BPREDA.setRequest(request);
 BPREDA.ejecutarValidacion();
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

/*

				+ "SELECT ed.idpedido_regalos_entrega_deta,ed.idpedido_regalos_entrega_cabe, "
				+ "       ed.codigo_st, st.descrip_st, ed.fecha, ed.renglon, ed.cantidad, ed.entrega, ed.idremitocliente, "
				+ "       ed.cantidad_sb, ed.compromiso_sb, "

*/

tituCol[0] = "C.Det";
tituCol[1] = "idpedido_regalos_entrega_cabe";
tituCol[2] = "Cód. Art";
tituCol[3] = "Artículo";
tituCol[4] = "Fecha";
tituCol[5] = "Renglon";
tituCol[6] = "Cantidad";
tituCol[7] = "Entrega";
tituCol[8] = "idremitocliente";
tituCol[9] = "cantidad_sb";
tituCol[10] = "compromiso_sb";

java.util.List Pedidos_Regalos_Entregas_Deta = new java.util.ArrayList();
Pedidos_Regalos_Entregas_Deta= BPREDA.getPedidos_Regalos_Entregas_DetaList();
iterPedidos_Regalos_Entregas_Deta = Pedidos_Regalos_Entregas_Deta.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="pedidos_Regalos_Entregas_Deta.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr >
    <td width="100%"  colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#000000">
                <tr>
                   <td height="32"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="11%">
                  <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000">
                    <tr>
                      <td><table width="100%" border="1" align="center" cellpadding="0" cellspacing="0"  class="fila-det">
                        <tr class="text-dos-bold">
                          <td colspan="4" >DOMICILIO</td>
                        </tr>
                        <tr>
                          <td width="11%" class="fila-det-bold">Calle</td>
                          <td width="38%"><%= BPREDA.getCalle() %>&nbsp;</td>
                          <td width="33%" class="fila-det-bold">Nro</td>
                          <td width="18%"><%= BPREDA.getNro() %>&nbsp;</td>
                        </tr>
                        <tr>
                          <td class="fila-det-bold">Piso</td>
                          <td><%= BPREDA.getPiso() %>&nbsp;</td>
                          <td class="fila-det-bold">Depto</td>
                          <td><%= BPREDA.getDepto() %>&nbsp;</td>
                        </tr>
                        <tr>
                          <td class="fila-det-bold">CP</td>
                          <td><%= BPREDA.getPostal() %>&nbsp;</td>
                          <td class="fila-det-bold">Cpa</td>
                          <td><%= BPREDA.getCpa() %>&nbsp;</td>
                        </tr>
                        <tr>
                          <td class="fila-det-bold">Localidad</td>
                          <td><%= BPREDA.getLocalidad() %>&nbsp;</td>
                          <td class="fila-det-bold">Provincia</td>
                          <td><%= BPREDA.getProvincia() %>&nbsp;</td>
                        </tr>
                        <tr>
                          <td class="fila-det-bold">Contacto</td>
                          <td><%= BPREDA.getContacto() %>&nbsp;</td>
                          <td class="fila-det-bold">Cargo</td>
                          <td><%= BPREDA.getCargocontacto() %>&nbsp;</td>
                        </tr>
                        <tr>
                          <td class="fila-det-bold">TE</td>
                          <td><%= BPREDA.getTelefonos() %>&nbsp;</td>
                          <td class="fila-det-bold">CEL</td>
                          <td><%= BPREDA.getCelular() %>&nbsp;</td>
                        </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100%"  border="0" cellspacing="0" cellpadding="0" >
                        <tr >
                          <td height="0" class="fila-det-bold-rojo"><jsp:getProperty name="BPREDA" property="mensaje"/></td>
                        </tr>
                      </table>
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
                        <tr class="fila-encabezado">
                          <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
                          <td width="74%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
                          <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[5]%></div></td>
                          <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%></div></td>
                        </tr>
                        <%int r = 0;
   while(iterPedidos_Regalos_Entregas_Deta.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterPedidos_Regalos_Entregas_Deta.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
                        <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
                          <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
                          <td class="fila-det-border" ><%=sCampos[2]%><%=sCampos[3]%>&nbsp;</td>
                          <td class="fila-det-border" ><div align="right"><%=sCampos[5]%>&nbsp;</div></td>
                          <td class="fila-det-border" ><div align="right"><%= Common.getNumeroFormateado(Double.parseDouble(sCampos[6]), 10, 0)%>&nbsp;</div></td>
                        </tr>
                        <%
   }%>
                      </table></td>
                    </tr>
                  </table>
                 </td>
              </tr>
          </table>
      </td>
    </tr>
  </table>
<input name="idpedido_regalos_entrega_cabe" value="<%= BPREDA.getIdpedido_regalos_entrega_cabe() %>" type="hidden">
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

