<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: DocumentosGenric
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jan 24 11:42:37 GMT-03:00 2007 
   Observaciones: 
      .
*/ 
%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%
try{
// captura de variables comunes
Strings str = new Strings();
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterDocumentosGenric   = null;
int totCol = 0; // cantidad de columnas
String[][] columnMetaData = new String[][]{{}};
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BADLG"  class="ar.com.syswarp.web.ejb.BeanConsultaGenericaDocsAsociadosDetalle"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BADLG" property="*" />
<%
 String color_fondo ="";
 String titulo = "DETALLE DE DOCUMENTOS AOCIADOS";
 BADLG.setResponse(response);
 BADLG.setRequest(request);
 BADLG.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BADLG.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="consultaGenericaDocsAsociadosDetalle.jsp" method="POST" name="frm">
  <input name="accion" value="" type="hidden">
  <input name="pkorigen" value="<%= BADLG.getPkorigen() %>" type="hidden">	 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
		<tr class="text-globales">
			<td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						 <td  class="text-globales"><%=titulo%></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr >
		 <td class="fila-det-bold-rojo"><jsp:getProperty name="BADLG" property="mensaje"/></td>
		</tr>
	</table>
<% 
	if( BADLG.getHtAsociaciones() != null && !BADLG.getHtAsociaciones().isEmpty()){
		 java.util.Enumeration en = BADLG.getHtAsociaciones().keys(); 
		 while(en.hasMoreElements()){
			String clave = (String)en.nextElement();
			columnMetaData = ( String[][] )BADLG.getHtMetaData().get(clave); 
			java.util.Hashtable ht = (java.util.Hashtable)BADLG.getHtAsociaciones().get(clave) ;
			totCol = columnMetaData == null ? 0 : columnMetaData.length ; %>
			<hr>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr class="text-dos-bold">
				 <td ><%= BADLG.getHtEntidades().get(clave).toString().toUpperCase() %></td>
				</tr>
			</table>  
			<table  border="0" cellspacing="1" cellpadding="1"  width="100%" >
				<tr class="fila-encabezado">
					 <td width="4%" >&nbsp;</td>
					 <% 
					 for(int j=1;j<totCol;j++){ %>
					 <td ><%=str.esNulo(columnMetaData[j][0])%></td>
					 <% 
					 }%>
				 </tr>
				 <%
				 java.util.Enumeration enlist = ht.keys();
				 while(enlist.hasMoreElements()){
					 String claveList = (String) enlist.nextElement();
					 java.util.List DocumentosGenric = (java.util.List) ht.get(claveList) ;
					 
					 iterDocumentosGenric = DocumentosGenric.iterator();
					 while(iterDocumentosGenric.hasNext()){
							String[] sCampos = (String[]) iterDocumentosGenric.next(); 
							// estos campos hay que setearlos segun la grilla 
							if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
							else color_fondo = "fila-det-verde";%>
				 <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
						<td  class="fila-det-border" ><div align="center">&nbsp;</div></td>
					 <% 
						 for(int j=1;j<totCol;j++){
							 String campo  =  str.esNulo(sCampos[j]);
							 if(!campo.equals("") ){
								 if(columnMetaData[j][1].equalsIgnoreCase("timestamp")){
									 campo = (String)Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(campo), "JSTsToStr" );
								 }else if(columnMetaData[j][1].equalsIgnoreCase("date")){
									 campo = (String)Common.setObjectToStrOrTime(java.sql.Date.valueOf(campo), "JSDateToStr" );
								 }
							 } else campo = "&nbsp;";
							 
							 if(campo.length() > 35 ){
								 campo =  " <div align=\"center\"> " + campo.substring(0, 10) + "...<img src=\"../imagenes/default/gnome_tango/actions/gtk-add.png\" width=\"18\" height=\"18\" style=\"cursor:pointer\" title=\"" + campo + "\"></div>";			 
							 }%>
						<td class="fila-det-border"><%= campo %></td>
						 <% 
						 }%>			
				 </tr>
				 <%}
				 }
				 %>
				</table>
	 <%}
	} %>
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