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
String titulo = "MODIFICAR CANTIDAD DE BULTOS POR REMITO";
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
<jsp:useBean id="BBOEA"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosModificaBulto" scope="page"/>
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
<form action="clientesRemitosModificaBulto.jsp" method="POST" name="frm">
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
                      <td width="19%" height="40" class="text-globales">Remito : </td>
                      <td width="81%"><input name="nrosucursal" type="text" value="<%=Common.strZero(BBOEA.getNrosucursal(), 4)%>" id="nrosucursal" size="5" maxlength="4" class="campo"  style="text-align:right" >
                        <input name="nroremitocliente" type="text" value="<%=Common.strZero(BBOEA.getNroremitocliente(), 8)%>" id="nroremitocliente" size="10" maxlength="8" class="campo" style="text-align:right" ></td>
                    </tr>
                    <tr>
                      <td height="36" class="text-globales">Nuevo Total : </td>
                      <td><input name="totalbultos" type="text" value="<%= BBOEA.getTotalbultos() %>" id="totalbultos" size="10" maxlength="8" class="campo" style="text-align:right" ></td>
                    </tr>
                    <tr>
                      <td height="26" class="text-globales"><input name="actualizarbulto" type="button" class="boton" id="actualizarbulto" value="Actualizar Total Bultos" onClick="enviarFrm('actualizarbulto');"></td>
                      <td><input name="consulta" type="button" class="boton" id="consulta" value="Consultar Total Bultos" onClick="enviarFrm('consultarbulto');"></td>
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


  <tr >
    <td height="25" class="text-diez"><%= " ULTIMO COMPROBANTE INGRESADO:  " +Common.strZero( BBOEA.getNrosucursal(), 4) + "-" +  Common.strZero(BBOEA.getNroremitocliente(), 8)  %> &nbsp;</td>
  </tr>
    <tr >
    <td height="25" class="text-diez"><%= " TOTAL DE BULTOS ACTUAL COMPROBANTE:  " + BBOEA.getTotalbultosactual()   %> &nbsp;</td>
  </tr>
  <tr >
    <td height="25" class="text-diez"><%= " NUEVO TOTAL DE BULTOS COMPROBANTE:  " + BBOEA.getTotalbultos()   %> &nbsp;</td>
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

