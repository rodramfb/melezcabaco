<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
/*
Programa contablemonedas.jsp
Objetivo: Grilla de Monedas
Sistema : Syswarp ERP
Copyrigth: Syswarp S.R.L.
Fecha de creacion: 05/06/2006
Fecha de ultima modificacion: - 
*/

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="java.util.*" %> 
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ page import="java.math.BigDecimal" %>
<%
// captura de variables comunes




String color_fondo ="";
String titulo = " ";
String pathskin = "../imagenes/default/erp-style.css";
int totCol = 7; // cantidad de columnas
int totReg = 0;  // total de registros
String[] tituCol = new String[totCol];
 
%>

<html>
<head>
<title><%=titulo%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
<script language="JavaScript" src="vs/forms/forms.js"></script>

</head>

<%


// titulos para las columnas
tituCol[0] = " Pedido";
tituCol[1] = " IdRemito ";
tituCol[2] = " Sucursal";
tituCol[3] = " Remito";
tituCol[4] = " Ctrl";
tituCol[5] = " Cliente";
tituCol[6] = " ";

String nrosucursal =   Common.setNotNull(request.getParameter("nrosucursal")).equals("") ? "1" : Common.setNotNull(request.getParameter("nrosucursal"));
String nroremitocliente = Common.setNotNull(request.getParameter("nroremitocliente")).equals("") ? "0" : Common.setNotNull(request.getParameter("nroremitocliente"));
String idctrlremito = Common.setNotNull(request.getParameter("idctrlremito")).equals("") ? "0" : Common.setNotNull(request.getParameter("idctrlremito"));

try{
  

   
   
%>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('./imagenes/default/btn_add_over.gif','./imagenes/default/btn_add_over.gif','./imagenes/default/btn_edit_over.gif','./imagenes/default/btn_remove_over.gif')">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form  method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
  <tr >
     <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
       <table width="100%" border="0" cellpadding="0" cellspacing="0">
         <tr class="text-globales">
            <td width="14%" height="38">&nbsp;</td>
                <td width="47%"><div align="left">
            <!-- </div> --></td>
              <td width="39%"><div align="right"><%=titulo%>&nbsp;&nbsp;&nbsp;</div></td>
          </tr>
        </table>
      </td>
    </tr>
	</table>
     <table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>   >
      <tr class="fila-encabezado">
        <td width="18%"  ><%=tituCol[3]%></td>
        <td width="6%"   ><%=tituCol[0]%></td>
				<td width="18%"  ><%=tituCol[1]%></td>
				<td width="18%"  ><%=tituCol[2]%></td>
				<td width="28%"  ><%=tituCol[4]%></td>
			 </tr>
 <%
    Iterator iter = Common.getClientes().getRemitoClienteAnularRecursivo(new BigDecimal (nrosucursal),		new BigDecimal (nroremitocliente),  idctrlremito,	1, new Hashtable(),new BigDecimal (1)).iterator();
    int r = 0;
    while(iter.hasNext()){      
     ++r;
     String[] sCampos = (String[]) iter.next(); 
  %>
			<tr  >
			  <td class="fila-det-border" ><%=sCampos[3]%></td>
			  <td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
				<td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
				<td class="fila-det-border" ><%=sCampos[2]%></td>
				<td class="fila-det-border" ><%=sCampos[4]%></td>
			 </tr>
  <%}%>
</table>

<!-- 
-->
<table width="100%" align="center" cellPadding=1 cellSpacing=1 >
  <tr >
    <td width="100%" height="21" colspan="7" background="images/dialogtop.gif">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="fila-titulo-dos">
          <td width="0%" height="19"> <div align="center">
            <label></label>
            <label> <br>
            <input name="nrosucursal" type="text" id="nrosucursal" value="<%= nrosucursal %>">
            <br>
            <br>
            <input name="nroremitocliente" type="text" id="nroremitocliente" value="<%= nroremitocliente %>">
            </label>
            <p>
              <input name="idctrlremito" type="text" id="idctrlremito" value="<%= idctrlremito %>">
            </p>
            <p>
              <input type="submit" name="Submit" value="Enviar">
            </p>
          </div></td>
          </tr>
      </table>
		</td>
  </tr>
</table>
</form>
<% 
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  
  
%>

</body>
</html>