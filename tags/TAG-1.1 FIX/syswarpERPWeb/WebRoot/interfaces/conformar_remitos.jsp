<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientescredcate
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Nov 14 14:36:19 GMT-03:00 2006 
   Observaciones: 
      .


*/ 
%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ page import="java.math.BigDecimal" %>
<%
/*
Pagina de interface
Nota: Es importante destacar que son solamente paginas de transicion para la aplicacion
aca no interesa demasiado la buena utilizacion de la pagina en si
Nota2: no usar variables de sesion ya que la idea es acceder directamente a las mismas
*/

try{
// captura de variables comunes
String color_fondo ="";
String titulo = "Conformacion de remitos";
// variables de entorno // aca se van
String pathskin = "../imagenes/default/";
String pathscript = "scripts";
// variables de paginacion
int i = 0;
Iterator iterClientescredcate   = null;
int totCol = 4; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";

String usuario    = request.getParameter("usuario");
String idempresa  = request.getParameter("idempresa");
if (usuario==null) usuario = "1"; else request.getParameter("usuario").toString();
if (usuario==null) idempresa = "1"; else request.getParameter("idempresa").toString();
%>
<html>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<h1>hola</h1>
<form action="conformar_remitos.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  
</table>
</form>
</body>
</html>
<% 
 }
catch (Exception ex) {
   java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
   java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
   ex.printStackTrace(pw);
  
}%>

