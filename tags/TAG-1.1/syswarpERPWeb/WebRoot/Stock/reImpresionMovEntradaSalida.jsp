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
<jsp:useBean id="BRIMES"  class="ar.com.syswarp.web.ejb.BeanReImpresionMovEntradaSalida"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BRIMES" property="*" />
<%
 String titulo = "RE-IMPRESION DE MOVIMIENTOS DE " + BRIMES.getInOut();
 java.math.BigDecimal idempresa = new java.math.BigDecimal(session.getAttribute("empresa").toString());
 BRIMES.setResponse(response);
 BRIMES.setRequest(request);
 BRIMES.setIdempresa(idempresa);
 BRIMES.setUsuario(usuario);
 BRIMES.ejecutarValidacion();
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
tituCol[0] = "Ctrl-Int.";
tituCol[1] = "Sucursal";
tituCol[2] = "Comprobante";
tituCol[3] = "Fecha";
tituCol[4] = "C.Dep.";
tituCol[5] = "Depósito";

java.util.List VreImpresionRecibos = new java.util.ArrayList();
VreImpresionRecibos= BRIMES.getVreImpresionRecibosList();
iterVreImpresionRecibos = VreImpresionRecibos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="reImpresionMovEntradaSalida.jsp" method="POST" name="frm">
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
                             <input name="ocurrencia" type="text" value="<%=BRIMES.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                          <td width="72%">
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                       <tr class="text-globales">
                                         <td width="1%" height="19">&nbsp; </td>
                                         <td width="23%">&nbsp;Total de registros:&nbsp;<%=BRIMES.getTotalRegistros()%></td>
                                         <td width="11%" >Visualizar:</td>
                                         <td width="11%">
                                            <select name="limit" class="campo">
                                               <%for(i=15; i<= 150 ; i+=15){%>
                                                   <%if(i==BRIMES.getLimit()){%>
                                                       <option value="<%=i%>" selected><%=i%></option>
                                                   <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                   <%}
                                                      if( i >= BRIMES.getTotalRegistros() ) break;
                                                    %>
                                               <%}%>
                                            </select>                                          </td>
                                         <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                      </tr>
                                   </table>                                 </td>
                              </tr>
                          </table>                        </td>
                     </tr>
                        <tr>
                          <td colspan="3" class="text-globales"><table width="60%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td width="9%" class="text-globales">Entrada</td>
                              <td width="45%" ><input name="inOut" type="radio" value="ENTRADA" <%= BRIMES.getInOut().equals("") ||  BRIMES.getInOut().equalsIgnoreCase("ENTRADA") ? "checked": "" %> class="campo"></td>
                              <td width="15%" class="text-globales">Salida</td>
                              <td width="31%" ><input name="inOut" type="radio" value="SALIDA" <%=  BRIMES.getInOut().equalsIgnoreCase("SALIDA") ? "checked": "" %> class="campo"></td>
                            </tr>
                                                                              </table></td>
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
		P&aacute;gina: <%= Common.getPaginacion(BRIMES.getTotalPaginas(),
		BRIMES.getTotalRegistros(), BRIMES.getPaginaSeleccion(), ( new Integer(BRIMES.getLimit() + "" ) ).intValue(),
		BRIMES.getOffset()) %> </div></td>
	</tr>		
	
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BRIMES" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="8%" ><div align="right"><%=tituCol[0]%></div></td>
     <td width="24%" ><div align="center"><%=tituCol[2]%></div></td>
     <td width="17%" ><div align="center"><%=tituCol[3]%></div></td>
		 <td width="47%" ><%=tituCol[5]%></td>
     <td width="4%" >&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterVreImpresionRecibos.hasNext()){  
      ++r;
      String[] sCampos = (String[]) iterVreImpresionRecibos.next(); 
	  String link = "../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=entrada_salida_frame&remito_interno=" + Long.parseLong (sCampos[0]) + "&tipo="+ BRIMES.getInOut() +"&idempresa=" + idempresa ;
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="right"><%= sCampos[0] %></div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.strZero(sCampos[1], 4)  + "-" + Common.strZero(sCampos[2], 8) %></div></td>
      <td class="fila-det-border" ><div align="center">
	   <%=!Common.setNotNull(sCampos[3]).equals("") ?  Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[3]), "JSDateToStr") : "" %>
	  <%//=Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[3]), "JSDateToStr")%>
	  </div></td> 
			<td class="fila-det-border" ><%= sCampos[5] %></td>
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

