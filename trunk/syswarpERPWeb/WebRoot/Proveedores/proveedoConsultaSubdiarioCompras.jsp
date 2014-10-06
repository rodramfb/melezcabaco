<%@page language="java"%>
<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
/* 
Formulario de carga para la entidad: pedidos_cabe
Copyrigth(r) sysWarp S.R.L. 
Fecha de creacion: Tue Jan 02 09:51:28 GMT-03:00 2007 
Observaciones: 
. Revisar los nombres de las clases instanciadas
. Se prevee el primer campo como autonumerico
. Se omiten los campos de auditorias 


*/ 

%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="ar.com.syswarp.api.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ include file="session.jspf"%>
<% 
try{
	int i = 0;
	Iterator iterConsulta1   = null;
	Iterator iterConsulta2   = null;
	Iterator iterConsulta3   = null;
	
	Strings str = new Strings();
	String color_fondo ="";
	String pathskin = session.getAttribute("pathskin").toString();
	String pathscript = session.getAttribute("pathscript").toString();
%> 
	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
	<html>
		<%-- INSTANCIAR BEAN --%>
		<jsp:useBean id="BPF" class="ar.com.syswarp.web.ejb.BeanProveedoConsultaSubdiarioCompras" scope="page" />
		<head>
			<title>Consuta de Subdiario de Compras a Proveedores</title>
			<link rel = "stylesheet" href = "<%= pathskin %>">
			<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
			<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
			<script language="JavaScript" src="vs/forms/forms.js"></script>
			<script language="JavaScript" src="vs/overlib/overlib.js"></script>
			<script>
				function mostrarLOVDETA(pagina) {
				frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=800,height=450,status=yes');
				if (frmLOV.opener == null) 
				frmLOV.opener = self;
				}	
			</script>
			<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
		</head>
	<body>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
		String titulo = "Consuta de Subdiario de Compras a Proveedores.";
		BPF.setResponse(response);
		BPF.setRequest(request);
		BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
		BPF.setUsuarioact( session.getAttribute("usuario").toString() );
		BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
		BPF.setTotCol(11);
		BPF.ejecutarValidacion();
		java.util.List Consulta1 = new java.util.ArrayList();
		java.util.List Consulta2 = new java.util.ArrayList();
		java.util.List Consulta3 = new java.util.ArrayList();
		Consulta1 = BPF.getSaldoAnteriorList();
		Consulta2 = BPF.getMovimientosList();
		Consulta3 = BPF.getSaldoFinalList();
		iterConsulta1 = Consulta1.iterator();
		iterConsulta2 = Consulta2.iterator();
		iterConsulta3 = Consulta3.iterator();
		%>
		<form action="proveedoConsultaSubdiarioCompras.jsp" method="post" name="frm">
			<input name="accion" type="hidden" value="consulta">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
				<tr class="text-globales">
					<td><%= titulo %>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="fila-det-bold-rojo">
								<td width="12%" class="fila-det-border">&nbsp;</td>
								<td class="fila-det-border">
									<jsp:getProperty name="BPF" property="mensaje" />&nbsp;								
								</td>
							</tr>
							<tr class="fila-det">
								<td height="28" class="fila-det-border"> A&ntilde;o(*) </td>
								<td width="88%" class="fila-det-border">
									<select name="anio_sel" id="anio_sel" class="campo">
									<% 
									Iterator iter = BPF.getAnio().iterator();
									while(iter.hasNext())
									{
									String datos = (String)iter.next();%>
									<option value="<%= datos %>" <%= BPF.getAnio_sel().equalsIgnoreCase(datos) ? "selected" : "" %>  ><%= datos%></option>
									<%}%>
									</select>							  
								</td>
							</tr>
							<tr class="fila-det">
								<td class="fila-det-border">Mes (*) </td>
								<td class="fila-det-border">
									<select name="mes" id="mes" class="campo">
<option value="1" <%=BPF.getMes().equalsIgnoreCase("1")? "selected" : "" %> %>Enero</option>
<option value="2" <%=BPF.getMes().equalsIgnoreCase("2")? "selected" : "" %> %>Febrero</option>
<option value="3" <%=BPF.getMes().equalsIgnoreCase("3")? "selected" : "" %> %>Marzo</option>
<option value="4" <%=BPF.getMes().equalsIgnoreCase("4")? "selected" : "" %> %>Abril</option>
<option value="5" <%=BPF.getMes().equalsIgnoreCase("5")? "selected" : "" %> %>Mayo</option>
<option value="6" <%=BPF.getMes().equalsIgnoreCase("6")? "selected" : "" %> %>Junio</option>
<option value="7" <%=BPF.getMes().equalsIgnoreCase("7")? "selected" : "" %> %>Julio</option>
<option value="8" <%=BPF.getMes().equalsIgnoreCase("8")? "selected" : "" %> %>Agosto</option>
<option value="9" <%=BPF.getMes().equalsIgnoreCase("9")? "selected" : "" %> %>Septiembre</option>
<option value="10" <%=BPF.getMes().equalsIgnoreCase("10")? "selected" : "" %> %>Octubre</option>
<option value="11" <%=BPF.getMes().equalsIgnoreCase("11")? "selected" : "" %> %> Noviembre</option>
<option value="12" <%=BPF.getMes().equalsIgnoreCase("12")? "selected" : "" %> %>Diciembre</option>
</select>
								</td>
							</tr>
							<tr class="fila-det">
								<td width="13%">
									<input name="validar"type="submit" value="Consultar" class="boton">								 	
								</td>
							</tr>
						</table>					
					</td>
				</tr>
			</table>
			<%
			if (BPF.getAccion().equalsIgnoreCase("consulta")) 
			{
				if(BPF.isFlag())
				{%>
			<table width="1100">
				<tr class="fila-det">
					<td width="194" class="fila-det-border">
						El archivo se generó exitosamente. Haga click para descargar.<img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=proveed_subdiario_iva_compras&fechadesde=<%= BPF.getFechadesde() %>&fechahasta=<%= BPF.getFechahasta() %>', 'pdf', 800, 600);"> Exportar a excel <a href="./manejarArchivosDatosToXls.jsp?file=../reportes/reportes/SubDiarioCompras.csv" >
					<input type="image" src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-vnd.ms-excel.png"/></a><%
					session.setAttribute("archivo","SubDiarioCompras.csv");%>
					</td>
				</tr>
			</table>   
				<%
					int r = 0;
					boolean primera = true;
					String idproveedor ="";
					String nrocomprob  ="";
					String nroint = "";
					BigDecimal totalNeto  = new BigDecimal(0);
					BigDecimal totalIva   = new BigDecimal(0);
					BigDecimal totalBruto = new BigDecimal(0);
					BigDecimal totalExento= new BigDecimal(0);
					BigDecimal totalPercep = new BigDecimal(0);
					BigDecimal totalNetoG = new BigDecimal(0);
					BigDecimal totalIvaG  = new BigDecimal(0);
					BigDecimal totalBrutoG= new BigDecimal(0);
					BigDecimal totalExentoG= new BigDecimal(0);
					BigDecimal totalPercepG =  new BigDecimal(0);
					while(iterConsulta2.hasNext())
					{
						++r;
						String[] sCampos = (String[]) iterConsulta2.next();
						if (!idproveedor.equalsIgnoreCase(sCampos[1]))
						{
							if (!primera )
							{%>
				<tr class="fila-encabezado">       
					<td width="10%"></td>
					<td width="5%"><div align="right"></div></td>
					<td width="10%"><div align="right"></div></td>
					<td width="10%"><div align="right"></div></td>
					<td width="15%"><div align="right"><%=totalNeto%></div></td>
					<td width="15%"><div align="right"><%=totalIva%></div></td>
					<td width="15%"><div align="right"><%=totalExento%></div></td>
					<td width="15%"><div align="right"><%=totalBruto%></div></td>
					<td><div align="right"> <%=totalPercep%></div></td>
				</tr>
				</table>
							<%
							}	
							primera = false; 
							totalNeto = new BigDecimal(0);
							totalIva  = new BigDecimal(0);
							totalBruto= new BigDecimal(0);
							totalExento= new BigDecimal(0);
							totalPercep= new BigDecimal(0);%>
			<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable">
				<tr class="fila-encabezado">     
					<td width="5%"><%=sCampos[1]%>-<%=sCampos[2]%></td>     
				</tr>
			</table>

						<%
						}
						idproveedor = sCampos[1];
						if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
							else color_fondo = "fila-det-verde";
						if (!nrocomprob.equalsIgnoreCase(sCampos[5]))
						{	
							nrocomprob = sCampos[5];
							%>
			<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable">
			<tr class="text-globales">
					<td class="fila-det-verde" colspan="9">Nro. Comprob: <%=sCampos[5]%></td>
				</tr>
				<tr class="fila-encabezado">       
					<td width="10%">Fecha</td>
					<td width="5%"><div align="right">Suc</div></td>
					<td width="10%"><div align="right">N.Interno</div></td>
					<td width="10%">T.Mov</td>
					<td width="15%"><div align="right">Neto Gravado</div></td>
					<td width="15%"><div align="right">Iva</div></td>
					<td width="15%"><div align="right">Exento</div></td>
					<td width="15%"><div align="right">Percep.</div></td>
					<td width="15%"><div align="right">Total</div></td> 
				</tr>
				
						<%
						}%>
					<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class= "<%=color_fondo%>" scope="col" > 
					<td class="fila-det-border" ><div align="center"><%=sCampos[3]%></td> 
					<td class="fila-det-border" ><div align="right">&nbsp;<%=sCampos[4]%></div></td> 
					<td class="fila-det-border" ><div align="right">&nbsp;<%=sCampos[6]%></div></td> 
					<td class="fila-det-border" >&nbsp;<%=sCampos[7]%></td> 
					<td class="fila-det-border" ><div align="right">&nbsp;<%=sCampos[8]%></div></td> 
					<td class="fila-det-border" ><div align="right"><%=sCampos[9]%></div></td> 
					<td class="fila-det-border" ><div align="right">&nbsp;<%=sCampos[11]%></div></td>
					<td class="fila-det-border" ><div align="right">&nbsp;<%=sCampos[12]%></div></td> 
					<td class="fila-det-border" ><div align="right">&nbsp;<%=sCampos[10]%></div></td> 
				</tr>

						<%
						//   BigDecimal saldoParcial = new BigDecimal(sCampos[8]);         
						totalNeto = totalNeto.add(new BigDecimal(sCampos[8]));
						totalIva = totalIva.add(new BigDecimal(sCampos[9]));
						totalBruto = totalBruto.add(new BigDecimal(sCampos[10]));
						totalExento = totalExento.add(new BigDecimal(sCampos[11]));
						totalPercep = totalPercep.add(new BigDecimal(sCampos[12]));
						
						totalNetoG = totalNetoG.add(new BigDecimal(sCampos[8]));
						totalIvaG = totalIvaG.add(new BigDecimal(sCampos[9]));
						totalBrutoG = totalBrutoG.add(new BigDecimal(sCampos[10]));
						totalExentoG = totalExentoG.add(new BigDecimal(sCampos[11]));
						totalPercepG = totalPercepG.add(new BigDecimal(sCampos[12]));
						
						//   totalGeneral   = totalGeneral.add(saldoParcial);
						
						
						%>
					<%
					}
					%>
			<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
				<tr class="fila-encabezado">       
					<td width="10%">Total General:</td>
					<td width="5%"><div align="right"></div></td>
					<td width="10%"><div align="right"></div></td>
					<td width="10%"><div align="right"></div></td>
					<td width="15%"><div align="right"><%=totalNetoG%></div></td>
					<td width="15%"><div align="right"><%=totalIvaG%></div></td>
					<td width="15%"><div align="right"><%=totalExentoG%></div></td>
					<td width="15%"><div align="right"><%=totalPercepG%></div></td>
					<td width="15%"><div align="right"><%=totalBrutoG%></div></td> 
					
				</tr>
			</table>
					<%
					
				}else{%>
			<table>
				<tr class="fila-det">
					<td width="1095" colspan="9" class="fila-det-border">
						No existen registros para este rango de fechas.					</td>
				</tr>
			</table>
			<%
				}
			}%>	
			<input name="primeraCarga" type="hidden" value="false" >
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
