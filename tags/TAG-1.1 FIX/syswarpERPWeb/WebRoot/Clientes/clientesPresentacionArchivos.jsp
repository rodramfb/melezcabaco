<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: archivosList
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 13 17:49:49 GMT-03:00 2006 
   Observaciones: 
      .

*/ 
%>

<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
int totCol = 3; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
String titulo = "";
String color_fondo = "";
%>
<html>
<jsp:useBean id="BAA"  class="ar.com.syswarp.web.ejb.BeanClientesPresentacionTarjetasArchivos"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BAA" property="*" />
<%


 BAA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BAA.setResponse(response);
 BAA.setRequest(request);
 BAA.ejecutarValidacion(); 

 titulo = "DESCARGAR ARCHIVOS DE PRESENTACION " +   BAA.getTarjetacredito();

 Enumeration enu ; 
 
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
tituCol[0] = "Nombre Archivo";
tituCol[1] = "Fecha";
tituCol[2] = "Tamaño ";

%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="popupcalendar" class="text"></div>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="clientesPresentacionArchivos.jsp" method="POST" name="frm">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" >
		<tr >
			<td width="100%" height="24" colspan="10" >
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr   class="text-globales">
						 <td height="43" colspan="2"><%=titulo%></td>
					</tr>
					<tr class="text-globales">
						 <td width="24%" height="24">&nbsp;</td>
						 <td width="76%">&nbsp;</td>
					</tr>
					<tr class="subtitulo-tres">
						<td height="24">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>
      </td>
    </tr>
  </table> 
	&nbsp;
	<table width="95%"  border="1" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td>
				<table width="100%"  border="0" cellpadding="0" cellspacing="0" >
					<tr class="subtitulo-tres" >
					  <td class="fila-det-border">&nbsp;</td>
					  <td colspan="3" class="fila-det-border"><jsp:getProperty name="BAA" property="mensaje"/>                      
					    &nbsp;</td>
				    </tr>
					<tr class="subtitulo-tres" >
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
									
					  <td width="20%">&nbsp;</td>
                      
                      
					  <td width="28%">&nbsp;</td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" cols=<%=totCol+2-4%> id=rsTable name="rsTable"  >
					<tr class="fila-encabezado">
						 <td width="50%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
						 <td width="24%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
						 <td width="21%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
						 <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
					</tr>
				 <%
					 int r = 0;
					 String fechaArchivos = "";
					 String auxFecha = "";
					 Enumeration enumera = Common.getSetSorted (BAA.getArchivosHash().keySet());
					 while(enumera !=null && enumera.hasMoreElements()){
							++r;

							String key = (String) enumera.nextElement();
							String sCampos[] = (String[]) BAA.getArchivosHash().get(key); 
							auxFecha =  Common.setObjectToStrOrTime( new Timestamp (Long.parseLong(sCampos[1])), "JSTsToStr" ) + "" ;							
							// estos campos hay que setearlos segun la grilla 
							if(!auxFecha.equals(fechaArchivos) || fechaArchivos.equals("")){
							  if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
							  else color_fondo = "fila-det-verde";
							  fechaArchivos =  Common.setObjectToStrOrTime( new Timestamp (Long.parseLong(sCampos[1])), "JSTsToStr" ) + "" ;
							  %>
					<tr class="text-catorce" > 
						 <td colspan="4">Fecha Generación: <%=Common.setObjectToStrOrTime( new Timestamp (Long.parseLong(sCampos[1])), "JSTsToStr" ) %> &nbsp;</td>
				  </tr>							  
						<% 
							} 
							%>
					 <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
							<td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
							<td class="fila-det-border" ><%= Common.setObjectToStrOrTime( new Timestamp (Long.parseLong(sCampos[1])), "JSTsToStrWithHM" )%>&nbsp;</td>
							<td class="fila-det-border" ><%=sCampos[2]%>KB&nbsp;</td>
					   <td class="fila-det-border" ><a href="<%=sCampos[3] + sCampos[0]%>"><img src="../imagenes/default/gnome_tango/devices/3floppy_unmount.png" border="0" longdesc="Descargar"></a></td>								 
					 </tr>
				<%
					 }%>
				</table>
				 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
					 <tr >
						 <td width="21%" class="text-globales">Total de archivos:&nbsp;<%=BAA.getTotalRegistros()%> </td>
						 <td width="79%" class="text-globales"> 
						   <div align="center" class="fila-det-bold-rojo">
							   <jsp:getProperty name="BAA" property="mensaje"/>       
						   </div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
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

