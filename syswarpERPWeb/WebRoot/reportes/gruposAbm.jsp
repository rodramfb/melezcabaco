<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
/*
Programa gruposAbm.jsp
Objetivo: Grilla de Grupos 
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
String titulo = "GRUPOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterGrupos   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
  <jsp:useBean id="BGA"  class="ar.com.syswarp.web.ejb.report.BeanGruposAbm"   scope="page"/>
	<%--  
  EJECUTAR SETEO DE PROPIEDADES
	--%>
  <jsp:setProperty name="BGA" property="*" />  	

  <%  
  BGA.setResponse(response);    
	BGA.setRequest(request);  		
  BGA.ejecutarValidacion();
	%>
<head>
<title><%=titulo%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
<script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
</head>
<%
// titulos para las columnas
tituCol[0] = "";
tituCol[1] = "C�digo";
tituCol[2] = "Grupo";
tituCol[3] = "Descripci�n";

java.util.List Grupos = new java.util.ArrayList();	 
Grupos = BGA.getGruposList();       
iterGrupos = Grupos.iterator();      
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="gruposAbm.jsp" method="POST" name="frm">
     <table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
	      <tr class="text-globales">
          <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td  class="text-globales"><%=titulo%></td>
                <td>&nbsp;</td>
              </tr>
              <tr>
								<td width="11%" height="38"> 
									<table width="36%" border="0">
                    <tr>    
                			<td width="27%">
											  <input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">
                      </td>
                			<td width="27%">
											  <input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">
											</td>
                      <td width="27%">
											  <input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return confirmarBaja('frm');">
											</td>		
											<td width="27%">
												<input name="grupos-reportes" id="grupos-reportes" value="grupos-reportes" type="image" src="../imagenes/default/btn_lock_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('grupos-reportes','','../imagenes/default/btn_lock_over.gif',1)" onClick="document.frm.accion.value = this.name">
											</td>																															
                    </tr>
                  </table>
								</td>
                <td width="89%">
									<table width="100%" border="0">
										<tr> 
											<td width="6%" height="26" class="text-globales">Buscar</td>
											<td width="22%"> 
                        <input name="ocurrencia" type="text" value="<%=BGA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                      </td>
											<td width="72%"> 
												<table width="100%"  border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td>
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr class="text-globales">
																	<td width="1%" height="19">&nbsp; </td>
																	<td width="23%" >&nbsp;Total de registros:&nbsp;<%=BGA.getTotalRegistros()%>  </td>
																	<td width="11%" >Visualizar:</td>
																	<td width="11%" > 
																		<select name="limit" >
																			<%for(i=15; i<= 150 ; i+=15){%>
																				<%if(i==BGA.getLimit()){%>
																					<option value="<%=i%>" selected><%=i%></option>
																			 <%}else{%>
																				 <option value="<%=i%>"><%=i%></option>
																			 <%}
																			 if( i >= BGA.getTotalRegistros() ) break;
																			 %>
																			<%}%>
																			<option value="<%= BGA.getTotalRegistros()%>">Todos</option>
																		</select> 
																	</td>
																	<td width="7%">&nbsp;P&aacute;gina:</td>
																	<td width="12%"> 
																	<select name="paginaSeleccion" >
																			<%for(i=1; i<= BGA.getTotalPaginas(); i++){%>
																			<%if ( i==BGA.getPaginaSeleccion() ){%>
																					<option value="<%=i%>" selected><%=i%></option>
																			<%}else{%>
																					<option value="<%=i%>"><%=i%></option>
																				<%}%>
																			<%}%>
																		</select> 
																	</td>
																	 <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
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
				<td class="fila-det-bold-rojo"><jsp:getProperty name="BGA" property="mensaje"/>&nbsp;</td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
			<tr class="fila-encabezado">
				<td width="2%" >&nbsp;<%=tituCol[0]%> </td>
				<td width="9%" onClick="javascript:sortTable(<%=totCol+1-4%>, rsTable);"><%=tituCol[1]%></td>
				<td width="23%" onClick="javascript:sortTable(<%=totCol+1-4%>, rsTable);"><%=tituCol[2]%> </td>			
			  <td width="23%" onClick="javascript:sortTable(<%=totCol+1-4%>, rsTable);"><%=tituCol[3]%></td>
			</tr>
  <%int r = 0;
    while(iterGrupos.hasNext()){      
			++r;
			String[] sCampos = (String[]) iterGrupos.next(); 
					// estos campos hay que setearlos segun la grilla 
			String cmp_codigo = sCampos[0];
			usuarioalt = sCampos[0] ;
			usuarioact = sCampos[0] ;
			fechaalt   = sCampos[0] ;
			fechaact   = sCampos[0] ;	 
	    if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
          else color_fondo = "fila-det-verde";%> 			
			<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
				<td class="fila-det-border" ><input type="radio" name="idgrupo" value="<%= sCampos[0]%>"></td>
				<td class="fila-det-border" >&nbsp;<%=sCampos[0]%></td>
				<td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
			  <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
			</tr>
    <%
    }%>
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
  } 
%>