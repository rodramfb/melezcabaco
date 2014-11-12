<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
/*
Programa gruposReportesFrm.jsp
Objetivo: Grilla de Reportes por Grupos
Sistema : Syswarp Reporting
Copyrigth: Syswarp S.R.L.
Fecha de creacion: 06/2006
Fecha de ultima modificacion: - 
*/
%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
String color_fondo ="";
String titulo = " REPORTES SIN ASOCIAR AL GRUPO ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterReportes   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <jsp:useBean id="BGRF"  class="ar.com.syswarp.web.ejb.report.BeanGruposReportesFrm"   scope="page"/>
<head>
<meta http-equiv="description" content="Esta es mi pagina">
<title><%=titulo%></title>

<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
<script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
	<%--  
  EJECUTAR SETEO DE PROPIEDADES
	--%>

  <jsp:setProperty name="BGRF" property="*" />  	
  <%  
  BGRF.setResponse(response);    
	BGRF.setRequest(request);
	BGRF.setUsuarioalt( session.getAttribute("usuario").toString() );  	
	BGRF.setUsuarioact( session.getAttribute("usuario").toString() );	
  BGRF.ejecutarValidacion();
	%>
<%
// titulos para las columnas
tituCol[0] = "";
tituCol[1] = "C�digo";
tituCol[2] = "Reporte";
tituCol[3] = "Descripci�n";

java.util.List Reportes = new java.util.ArrayList();	 
Reportes = BGRF.getReportesList();       
iterReportes = Reportes.iterator();      
%>



<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="gruposReportesFrm.jsp" method="POST" name="frm">
     <table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
	      <tr class="text-globales">
          <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td colspan="2"  class="text-globales"><%=titulo + ": " +  BGRF.getGrupo() %></td>
              </tr>
              <tr>
								<td width="27%" height="38"><input type="submit" name="agregar" value="Agregar Seleccionados" class="boton">
							  <input type="submit" name="volver" value="Volver" id="volver" class="boton"></td>
                <td width="73%">
									<table width="100%" border="0">
										<tr> 
											<td width="2%" height="26" class="text-globales"><!-- Buscar --></td>
											<td width="3%"> 
                        <input name="ocurrencia" type="hidden" value="<%=BGRF.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                      </td>
											<td width="95%"> 
												<table width="100%"  border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td>
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr class="text-globales">
																	<td width="3%" height="19">&nbsp; </td>
																	<td width="23%" >&nbsp;Total de registros:&nbsp;<%=BGRF.getTotalRegistros()%>  </td>
																	<td width="16%" >Visualizar:</td>
																	<td width="16%" > 
																		<select name="limit" >
																			<%for(i=15; i<= 150 ; i+=15){%>
																				<%if(i==BGRF.getLimit()){%>
																					<option value="<%=i%>" selected><%=i%></option>
																			 <%}else{%>
																				 <option value="<%=i%>"><%=i%></option>
																			 <%}
																			 if( i >= BGRF.getTotalRegistros() ) break;
																			 %>
																			<%}%>
																			<option value="<%= BGRF.getTotalRegistros()%>">Todos</option>
																		</select> 
																	</td>
																	<td width="11%">&nbsp;P&aacute;gina:</td>
																	<td width="10%"> 
																	<select name="paginaSeleccion" >
																			<%for(i=1; i<= BGRF.getTotalPaginas(); i++){%>
																			<%if ( i==BGRF.getPaginaSeleccion() ){%>
																					<option value="<%=i%>" selected><%=i%></option>
																			<%}else{%>
																					<option value="<%=i%>"><%=i%></option>
																				<%}%>
																			<%}%>
																		</select> 
																	</td>
																	 <td width="21%" class="text-globales">
																	  <input name="ir" type="submit" class="boton" id="ir" value="  >>  "> 
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>										
										 </td>
									</tr>
                </table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="fila-det-bold-rojo"><jsp:getProperty name="BGRF" property="mensaje"/>&nbsp;</td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
			<tr class="fila-encabezado">
				<td width="4%" >&nbsp;<%=tituCol[0]%> </td>
				<td width="7%" onClick="javascript:sortTable(<%=totCol+1-4%>, rsTable);"><%=tituCol[1]%></td>
				<td width="89%" onClick="javascript:sortTable(<%=totCol+1-4%>, rsTable);"><%=tituCol[2]%> </td>			
		  </tr>
  <%int r = 0;
    while(iterReportes.hasNext()){      
			++r;
			String[] sCampos = (String[]) iterReportes.next(); 
					// estos campos hay que setearlos segun la grilla 
			String cmp_codigo = sCampos[0];
			usuarioalt = sCampos[0] ;
			usuarioact = sCampos[0] ;
			fechaalt   = sCampos[0] ;
			fechaact   = sCampos[0] ;	 
	    if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
          else color_fondo = "fila-det-verde";%> 			
			<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
				<td class="fila-det-border" ><input type="checkbox" name="idreportes" value="<%= sCampos[0]%>"></td>
				<td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
				<td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
		  </tr>
    <%
    }%>
		</table>
		<input name="accion" value="" type="hidden">
		<input name="idgrupo" value="<%= BGRF.getIdgrupo() %>" type="hidden">

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
  } 
%>
