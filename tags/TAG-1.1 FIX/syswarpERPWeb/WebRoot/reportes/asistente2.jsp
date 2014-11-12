<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.*" %> 
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.*" %>
<%@ page import="ar.com.syswarp.api.Common"%>
<%@ include file="session.jspf"%>

<%
String idreporte = request.getParameter("idreporte");
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
String titulo_general = "Reporte inexistente o con problemas";
java.util.List reportes = new java.util.ArrayList();	 
Iterator iterReportes   = null;
java.util.List param = new java.util.ArrayList();	 
Iterator iterParam   = null;


ResultSet tablas = null;
Hashtable htPar = new Hashtable();
Report repo = null;
int idImagen = 0;
try {
	Report report = Common.getReport();
   if (report.hasTablasReportes(new Integer(idreporte)) ){
      // resuelvo el titulo_general del reporte
     reportes = report.getReportesPK( Long.parseLong(idreporte));
     iterReportes = reportes.iterator();
     while(iterReportes.hasNext()){ 
         String[] sCampos = (String[]) iterReportes.next(); 
         titulo_general = sCampos[1];
         session.setAttribute("titulo_general", titulo_general);
     } 
     // recorro las tablas del reporte para hacer el rejunte de parametros
     tablas = report.getTablasReportes(new Integer(idreporte));
     
     while(tablas.next()){
       // traigo todos los parametros existentes
       String qry = tablas.getString("query_consulta");       
       Hashtable ht = new Hashtable();
       ht = report.getParametros(qry);              
       Enumeration enumE = ht.keys();
	   while(enumE.hasMoreElements()){
           String values = (String) enumE.nextElement();           
           htPar.put(values, ht.get(values));         
       }        
     }
     // aca ya tengo todos los parametros posibles.
     
   }   
   
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  
	
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel = "stylesheet" href = "<%= pathskin %>">

<link rel="stylesheet" type="text/css" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>

<script>
function validarCampos() {
	if ( confirm('Confirma?') ){
		 document.frm.submit();
	} 
	return true;
}
</script>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>



<FORM action="asistente3.jsp" METHOD="post" name="frm">
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr class="titulo">
    <td height="54" ><%=titulo_general%> - Selección de parametros</td>
  </tr>
  <tr >
    <td >
			<table>
			<% 
						param = report.getParametros(htPar);
						iterParam = param.iterator();
						while(iterParam.hasNext()){       
							 String[] sCampos = (String[]) iterParam.next();
							 String idparametro     = sCampos[0];         
							 String parametro       = sCampos[1];         
							 String descripcion     = sCampos[2];
							 String idtipoparametro = sCampos[3];
							 String vq              = sCampos[4];
							 String ds              = sCampos[5];
							 String variable        = report.par2var(parametro);
							 /*
								posibles tipos de parametros
								1. consulta sql
								2. numerico
								3. fecha
								4. Alfanumerico
							 */
							 //todo: resolver todos los tipos.
							 java.sql.ResultSet rsCombo = null;
							 if (idtipoparametro.equalsIgnoreCase("1")){ // query
									rsCombo = report.getConnectionDS(new Integer(ds), vq);
							 }
							 
			%>     
				<tr class="fila-det">
					<td class="fila-det-border"><%=descripcion%></td>    
					<td class="fila-det-border">
					 <%if (idtipoparametro.equalsIgnoreCase("1")){ %>
							<select class="campo" name="<%=variable%>" >
								 <%while(rsCombo!=null && rsCombo.next()){%>  
									 <option value="<%=rsCombo.getString(1)%>"><%=rsCombo.getString(2)%></option>
								 <%}%> 
							</select> 
					 <%}%>
					
					 <%if (idtipoparametro.equalsIgnoreCase("2") || idtipoparametro.equalsIgnoreCase("4") ){ %>
							<input type="text" name="<%=variable%>" class="campo">
					 <%}%>
									 
					 <%
						if (idtipoparametro.equalsIgnoreCase("3")){ // FECHA
							%>
							<input name="<%=variable%>" type="text" class="cal-TextBox" id="<%=variable%>" onFocus="this.blur()" value="" size="12" readonly>
							<a class="so-BtnLink" href="javascript:calClick();return false;" 
									onmouseover="calSwapImg('BTN_date_<%= idImagen %>', 'img_Date_OVER',true);" 
									onmouseout="calSwapImg('BTN_date_<%= idImagen %>', 'img_Date_UP',true);" 
									onclick="calSwapImg('BTN_date_<%= idImagen %>', 'img_Date_DOWN');showCalendar('frm','<%=variable%>','BTN_date_<%= idImagen %>');return false;"> 
									<img align="absmiddle" border="0" name="BTN_date_<%= idImagen %>" src="./scripts/calendar/btn_date_up.gif" width="22" height="17"> 
							</a>				
						<%
						 idImagen++; 
						}%>  				          
						 
					</td>    
				</tr>
			<%}%>
			<%
			// grabo en session el hashtable y lo paso a la proxima pagina.
			session.setAttribute("htPar", htPar);
			%>
			
			</table> 
			<input type="hidden" name="idreporte" value="<%=idreporte%>">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0">
				<tr >
					<td width="9%" class="fila-det-border">
						 <input type="button" name="Siguiente" value="Siguiente" onClick="validarCampos()" class="boton">
					</td> 
					<td width="91%"  class="fila-det-border">
						 <input type="reset" name="Limpiar" class="boton">
					</td>     
				</tr> 
			</table>
    </td>
  </tr>
</table>
</FORM>
</body>
</html>
