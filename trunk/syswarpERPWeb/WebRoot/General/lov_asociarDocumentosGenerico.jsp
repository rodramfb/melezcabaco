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
<jsp:useBean id="BADLG"  class="ar.com.syswarp.web.ejb.BeanAsociarDocumentosLovGenerico"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BADLG" property="*" />
<%
 BADLG.setResponse(response);
 BADLG.setRequest(request);
 BADLG.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BADLG.ejecutarValidacion();
 columnMetaData = BADLG.getColumnsMetaData();
 
 String color_fondo ="";
 String titulo = BADLG.getDescripcion();
 totCol = columnMetaData == null ? 0 : columnMetaData.length ; 
 
%>
<head>
<title><%=titulo%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
function bajarDatos(codigo, descripcion){
	for(var i = 0; i<10;i++)
		if(opener.document.forms[i].<%= BADLG.getCmpCodigo() %> ){
			if(opener.document.forms[i].<%= BADLG.getCmpCodigo() %>)			
			  opener.document.forms[i].<%= BADLG.getCmpCodigo() %>.value = codigo;
			if(opener.document.forms[i].<%= BADLG.getCmpDescrip() %>)			
			  opener.document.forms[i].<%= BADLG.getCmpDescrip() %>.value = descripcion;
			break;
		}
	window.close();
}
 </script>

</head>
<%
// titulos para las columnas

java.util.List DocumentosGenric = new java.util.ArrayList();
DocumentosGenric= BADLG.getDocumentosGenricList();
iterDocumentosGenric = DocumentosGenric.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_asociarDocumentosGenerico.jsp" method="POST" name="frm">
<input name="identidadesasociables" type="hidden" value="<%= BADLG.getIdentidadesasociables()%>">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="2%" height="38">&nbsp;</td>
                   <td width="98%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BADLG.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BADLG.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BADLG.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BADLG.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BADLG.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BADLG.getTotalPaginas(); i++){%>
                                                    <%if ( i==BADLG.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select>                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>                                 </td>
                              </tr>
                           </table>                        </td>
                     </tr>
                   </table>                </td>
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
<table  border="0" cellspacing="1" cellpadding="1"  width="100%">
  <tr class="fila-encabezado">
     <td width="4%" >&nbsp;</td>
		 <% 
		 for(int j=1;j<totCol;j++){ %>
     <td width="15%" ><%=str.esNulo(columnMetaData[j][0])%></td>
		 <% 
		 }%>
   </tr>
   <%int r = 0;
   while(iterDocumentosGenric.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterDocumentosGenric.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" style="cursor:pointer" onClick="bajarDatos('<%=sCampos[0]%>', '<%=sCampos[1]%>')"></div></td>
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
			 
			 if(campo.length() > 15 ){
			   campo =  " <div align=\"center\"> " + campo.substring(0, 10) + "...<img src=\"../imagenes/default/gnome_tango/actions/gtk-add.png\" width=\"18\" height=\"18\" style=\"cursor:pointer\" title=\"" + campo + "\"></div>";			 
			 }
			 
		  %>
	    <td class="fila-det-border" ><%= campo %></td>
		 <% 
		 }
		  %>			
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="cmpCodigo" value="<%= BADLG.getCmpCodigo() %>" type="hidden">	 
   <input name="cmpDescrip" value="<%= BADLG.getCmpDescrip() %>" type="hidden">	 	 
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