<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vreImpresionRecibos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Sep 27 14:09:35 ART 2007 
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
String titulo = "RE-IMPRESION DE REMITOS INTERNOS (Cambios de Depósito)";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVreImpresionRecibos   = null;
int totCol = 6; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BRICD"  class="ar.com.syswarp.web.ejb.BeanReImpresionCambioDeposito"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BRICD" property="*" />
<%
 java.math.BigDecimal idempresa = new java.math.BigDecimal(session.getAttribute("empresa").toString());
 BRICD.setResponse(response);
 BRICD.setRequest(request);
 BRICD.setIdempresa(idempresa);
 BRICD.setUsuario(usuario); 
 BRICD.ejecutarValidacion();
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
tituCol[0] = "Ctrl. Interno";
tituCol[1] = "Fecha";
tituCol[2] = "idOrigen";
tituCol[3] = "Origen";
tituCol[4] = "idDestino";
tituCol[5] = "Destino";
java.util.List VreImpresionRecibos = new java.util.ArrayList();
VreImpresionRecibos= BRICD.getVreImpresionRecibosList();
iterVreImpresionRecibos = VreImpresionRecibos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="reImpresionCambioDeposito.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr >
                   <td class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="94%" height="38">
                     <table width="100%" border="0">
                        <tr>
                          <td width="6%" height="26" class="text-globales">Buscar</td>
                          <td width="22%">
                             <input name="ocurrencia" type="text" value="<%=BRICD.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="1%" height="19">&nbsp; </td>
                                         <td width="23%">&nbsp;Total de registros:&nbsp;<%=BRICD.getTotalRegistros()%></td>
                                         <td width="11%" >Visualizar:</td>
                                         <td width="11%">
                                            <select name="limit" class="campo">
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BRICD.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BRICD.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                            </select>                                          </td>
                                         <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                      </tr>
                                   </table>                                 </td>
                              </tr>
                          </table>                        </td>
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
		P&aacute;gina: <%= Common.getPaginacion(BRICD.getTotalPaginas(),
		BRICD.getTotalRegistros(), BRICD.getPaginaSeleccion(), ( new Integer(BRICD.getLimit() + "" ) ).intValue(),
		BRICD.getOffset()) %> </div></td>
	</tr>		

  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BRICD" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="16%" ><div align="center"><%=tituCol[0]%></div></td>
     <td width="18%" ><%=tituCol[1]%></td>
     <td width="28%" ><%=tituCol[3]%></td>
     <td width="25%" ><%=tituCol[5]%></td> 
     <td width="13%" >&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterVreImpresionRecibos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVreImpresionRecibos.next(); 
	  String link = "../reportes/jasper/generaPDF.jsp?comprob_ms=" + Long.parseLong (sCampos[0]) + "&tipo=Cambio Deposito&plantillaImpresionJRXML=cambio_deposito_frame&idempresa=" + idempresa ;
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><%=sCampos[0]%></div></td>
      <td class="fila-det-border" ><%=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[1]), "JSDateToStr")%></td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td> 
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><a href="javascript:abrirVentana('<%= link %>', '', 800, 600)"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" border="0"></a></div></td>
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

