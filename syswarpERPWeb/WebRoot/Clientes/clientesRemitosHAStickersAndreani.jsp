<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: Remitos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Nov 06 11:36:23 GMT-03:00 2009 
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
String titulo = "GENERAR STICKERS ANDREANI";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
int r = 0;
Iterator iter   = null;
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BBOEA"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosHAStickersAndreani" scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BBOEA" property="*" />
<%
 BBOEA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBOEA.setUsuarioalt(usuario); 
 BBOEA.setResponse(response);
 BBOEA.setRequest(request);
 BBOEA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
//-----------------------------------------------------------------------

function enviarFrm(accion){

    document.frm.accion.value = accion;
    document.frm.submit();
}

</script>
</head>
<%
/*
pd.idpedido_cabe, pd.idremitocliente, cr.nrosucursal, cr.nroremitocliente, 
cr.idctrlremito, pc.idcliente, pc.idsucuclie, cr.nrohojaarmado, nrohojarutafinal
*/
// titulos para las columnas 
tituCol[0] = "";
tituCol[1] = "";
tituCol[2] = "";
tituCol[3] = "";
tituCol[4] = "";
tituCol[5] = "";
tituCol[6] = "";
tituCol[7] = "";
tituCol[8] = "";
tituCol[9] = "";
tituCol[10] = "";
tituCol[11] = "";

java.util.List ListRemitos = new java.util.ArrayList();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" id="cuerpo">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesRemitosHAStickersAndreani.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales"> 
    <td  height="24" colspan="10" align="center">
            <table width="98%" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                   <td height="31"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td height="5px"> <hr color="#FFFFFF"></td>
                </tr>
                <tr>
                  <td height="38">
				  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr class="text-globales">
                      <td height="26" class="text-globales">&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr class="text-globales">
                      <td width="19%" height="40" class="text-globales">Hoja de Armado : </td>
                      <td width="81%"><input name="nrohojaarmado" type="text" class="campo" id="nrohojaarmado" style="text-align:right" onKeyUp="sendValidacion(this)" size="19" maxlength="100" ></td>
                    </tr>
                    <tr>
                      <td height="26" class="text-globales"><input name="generarstickers" type="button" class="boton" id="generarstickers" value="Generar Stickers" onClick="enviarFrm('generarstickers');"></td>
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
                </tr>
        </table>
      </td>
    </tr>
  </table>



<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr >
    <td height="30" class="fila-det-bold-rojo"><jsp:getProperty name="BBOEA" property="mensaje"/>
      &nbsp;
	 </td>
  </tr>

<%  
if(BBOEA.getMensaje().equalsIgnoreCase("OK")){
%>
  <tr >
    <td height="30" class="fila-det-bold-rojo"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" border="0" style="cursor:pointer" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=andreani_stickers_frame&nrohojaarmado=<%=BBOEA.getNrohojaarmado()%>', 'stickers', 750, 500);">&nbsp;	 </td>
  </tr> 
<%}  %>  
  <tr >
    <td height="30" class="text-diez"><%= " ULTIMA HOJA DE ARMADO INGRESADA:  " + BBOEA.getNrohojaarmado() %> &nbsp;</td>
  </tr>
  
  
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
   <input name="accion" value="" type="hidden" >
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

