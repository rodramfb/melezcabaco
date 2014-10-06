<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
/*
Programa Ayuda.jsp
Objetivo: mostrar ayudas
Sistema : Syswarp ERP
Copyrigth: Syswarp S.R.L.
Fecha de creacion: 20/06/2006
Fecha de ultima modificacion: - 
*/

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.ejb.*"%>
<%
// captura de variables comunes




String idayuda    = request.getParameter("idayuda");
int ayudaPrevius = 0;
int ayudaNext    = 0;
int ayudaMax     = 9999;
String buscar = request.getParameter("txtbuscar");
String color_fondo ="";
String titulo = "Ayuda";
String action = "ayuda.jsp";
	
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();
if (session.getAttribute("ayudaHome") == null){
   session.setAttribute("ayudaHome",idayuda);
}
 
%>

<html>
<head>
<title><%=titulo%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link href="../imagenes/default/erp-style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
<script language="JavaScript" src="forms.js"></script>

</head>
<%
String referencia = "";
String ayuda = "";
String imagen= "";
try{
   javax.naming.Context context = new javax.naming.InitialContext();
   // INSTANCIAR EL MODULO GENERAL 
   Object objgen = context.lookup("General");
   GeneralHome sGen = (GeneralHome) javax.rmi.PortableRemoteObject.narrow(objgen, GeneralHome.class);
   General gene =   sGen.create();   	       
   java.sql.ResultSet rs = gene.getAyudaPk( new Long(idayuda) );   
   referencia = gene.getReferencia();
   ayuda      = gene.getAyuda();
   imagen     = gene.getImagen();
   ayudaMax   = gene.getAyudaMax();
   ayudaPrevius = Integer.valueOf(idayuda).intValue() - 1;   
   ayudaNext = Integer.valueOf(idayuda).intValue() + 1;   
   
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  
  
%>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="<%=action%>" method="POST" name="frm">
		   <table width="50%"  border="0" cellspacing="0" cellpadding="0">
         <tr>
           <th width="6%" scope="row"><img src="imagenes/default/gnome_tango/apps/help.png" width="22" height="22"></th>
           <td width="30%" class="subtitulo">Ayuda en linea</td>
           <td width="64%"><table width="19%"  border="0" cellspacing="0" cellpadding="0">
             <tr>
              <%if(ayudaPrevius > 0 ){%>
               <th scope="row"><a href="ayuda.jsp?idayuda=<%=ayudaPrevius%>"><img src="imagenes/default/gnome_tango/actions/back.png" width="22" height="22" border="0"></a></th>
              <%}%> 
              <th scope="row"><a href="ayuda.jsp?idayuda=<%=Integer.valueOf(session.getAttribute("ayudaHome").toString()).intValue()%>"><img src="imagenes/default/gnome_tango/actions/gohome.png" width="22" height="22" border="0"></a></th>
              <%if(ayudaNext < ayudaMax ){%>
                <td><div align="center"><a href="ayuda.jsp?idayuda=<%=ayudaNext%>"><img src="imagenes/default/gnome_tango/actions/next.png" width="22" height="22" border="0"></a></div></td>
              <%}%> 
             </tr>
           </table></td>
         </tr>
       </table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td class="fila-encabezado"><%=referencia%></td>
  </tr>
  <tr>
    <td><pre class="fila-det"><%=ayuda%></pre></td>
  </tr>  
</table>
</form>
</body>
</html>