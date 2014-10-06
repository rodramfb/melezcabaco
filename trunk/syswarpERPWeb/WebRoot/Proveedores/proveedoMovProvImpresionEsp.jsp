<%@page language="java" %>
<%//@ page contentType="application/vnd.ms-excel" %>
<%//response.setHeader("Content-Disposition","attachment;filename=Test.xls"); %>
<%//@ page contentType="application/pdf" %>
<%//response.setHeader("Content-Disposition","attachment;filename=Test.pdf"); %>
<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: proveedoMovProv
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Jul 20 11:33:45 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*"%> 
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<!--meta http-equiv="Content-Type" content="application/pdf;"-->
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BPMPIF"  class="ar.com.syswarp.web.ejb.BeanProveedoMovProvImpresionEspFrm"   scope="page"/>
<head>
<title>CAPTURA COMPROBANTES </title>
<meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">

 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>

</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPMPIF" property="*" />
 <% 
 String titulo = BPMPIF.getAccion().toUpperCase() + " - CAPTURA DOCUMENTOS" ;
 BPMPIF.setResponse(response);
 BPMPIF.setRequest(request);
 BPMPIF.setSession(session);
 BPMPIF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPMPIF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPMPIF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPMPIF.ejecutarValidacion();
 Hashtable htCP = BPMPIF.getHtCP();
 Enumeration enumCP = htCP.keys();
 
 Hashtable htStock = new Hashtable();
 %>
<form action="proveedoMovProvImpresionEsp.jsp" method="post" name="frm">
<div align="right">
<input name="accion" type="hidden" value="<%=BPMPIF.getAccion()%>" >
<input name="nrointerno" type="hidden" value="<%=BPMPIF.getNrointerno()%>" >
	<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td>
				<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
					<tr class="text-globales">
						<td width="23%">&nbsp;COMPROBANTE INTERNO NRO.: <%= BPMPIF.getNrointerno() + "" %> </td>
						<td width="77%">
							<img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?nrointerno=<%= BPMPIF.getNrointerno() %>&plantillaImpresionJRXML=proveed_captura_doc_frame', 'comprobante', 750, 450);" style="cursor:pointer">
						</td>
					</tr>
				</table> 
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
					<tr class="fila-det-bold-rojo">
						<td class="fila-det-border" colspan="3">&nbsp;</td>
						<td class="fila-det-border"><jsp:getProperty name="BPMPIF" property="mensaje"/>&nbsp;</td>
					</tr>
					<tr class="fila-det-bold">
						<td width="16%" height="25" class="fila-det-border">Proveedor:</td>
						<td  class="fila-det-border"><%=BPMPIF.getIdproveedor()%> - <%=BPMPIF.getDproveedor()%>
							<input name="idproveedor" type="hidden" value="<%=BPMPIF.getIdproveedor()%>"  >
							<input name="dproveedor"  type="hidden" value="<%=BPMPIF.getDproveedor()%>" >
						</td>
						<td class="fila-det-border" colspan="2">&nbsp;</td>
					</tr>
					<% 
					// FUE SELECCIONADO UN PROVEEDOR
					if(BPMPIF.getIdproveedor().longValue() !=0) 
					{%>
					<tr class="fila-det-bold">
						<td width="16%" height="25" class="fila-det-border">Condicion Pago:  </td>
						<td width="34%" class="fila-det-border"><%=  BPMPIF.getDcondicionpago() %>&nbsp;
							<input name="idcondicionpago" type="hidden" value="<%=BPMPIF.getIdcondicionpago().longValue()%>"  >
						</td>
						<td width="24%" class="fila-det-border">&nbsp;</td>
						<td width="26%" class="fila-det-border">&nbsp;</td>
					</tr>
					<tr class="fila-det-bold"> 
						<td height="25" class="fila-det-border">Fecha Factura:</td>
						<td class="fila-det-border"><%=BPMPIF.getFechamovStr()%> 
							<input  type="hidden" name="fechamovStr" value="<%=BPMPIF.getFechamovStr()%>" >&nbsp;
						</td>
						<td class="fila-det-border">Fecha Vencimiento:</td>
						<td class="fila-det-border"><%= BPMPIF.getFechavtoStr() %>
							<input  type="hidden" name="fechavtoStr" value="<%=BPMPIF.getFechavtoStr()%>" >&nbsp;
						</td>
						<td width="0%" class="fila-det-border">&nbsp;</td>
					</tr>
					<tr class="fila-det-bold">
						<td height="25" class="fila-det-border">Comprobante: </td>
						<td class="fila-det-border"><%= Common.strZero(BPMPIF.getSucursal().toString(), 4) + " - " + Common.strZero(BPMPIF.getComprob().toString(), 8) %>
							<input name="sucursal" type="hidden" value="<%=BPMPIF.getSucursal()%>" >
							<input name="comprob" type="hidden" value="<%=BPMPIF.getComprob()%>"  >
						</td>
						<td class="fila-det-border">
							<input name="tipomov" type="hidden" value="<%= BPMPIF.getTipomov() %>"> 
							<% 
							if(BPMPIF.getTipomov().intValue()== 1) out.write("Factura"); 
							if(BPMPIF.getTipomov().intValue()== 2) out.write("Nota D&eacute;bito "); 
							if(BPMPIF.getTipomov().intValue()== 3) out.write("Nota Cr&eacute;dito"); 
							if(BPMPIF.getTipomov().intValue() == 4)out.write("Pago"); %>
							<input name="tipomovs" type="hidden" value="<%= BPMPIF.getTipomovs() %>">
							<% 
							if(BPMPIF.getTipomovs().equalsIgnoreCase("A")) out.write("A"); 
							if(BPMPIF.getTipomovs().equalsIgnoreCase("B")) out.write("B");  
							if(BPMPIF.getTipomovs().equalsIgnoreCase("C")) out.write("C"); 
							%>
						</td>
						<td class="fila-det-border">&nbsp;</td>
					</tr>
					<tr class="fila-det-bold">
						<td height="25" class="fila-det-border">Observaciones:
							<input name="observacionesContables" type="hidden" value="<%=BPMPIF.getObservacionesContables()%>"  >
						</td>
						<td class="fila-det-border" colspan="3"><%=BPMPIF.getObservacionesContables()%>&nbsp;</td>
					</tr>
					<%
					//cep cambio para que solo muestre la cabecera para los casos de registro contable
					// FUE SELECCIONADO TIPO MOVIMIENTO
					if(BPMPIF.getTipomov().longValue() > 0) 
					{%>
					<tr class="fila-det">
						<td class="fila-det-border" colspan="4"></td>
					</tr>	
					<tr>            
						<td colspan="4" >&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td><div align="right">........................ </div></td>
						<td><div align="right">........................</div></td>
					</tr>
					<tr>	
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr class="fila-det-bold">
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td><div align="right">Autoriz&oacute;</div></td>
						<td><div align="right">Firma</div></td>
					</tr>
			  </table>
				<table  border="0" align="center" cellpadding="0" cellspacing="0" width="100%" >
					<tr class="text-globales" height="3">
						<td class="fila-det-border" colspan="4"></td>
					</tr>		
					<tr height="10" class="permiso-cinco">
						<td height="3" colspan="4" class="fila-det-border"></td>
					</tr>									
					<tr class="fila-det-bold">
						<td class="fila-det-border">Subtotal Gravado:  </td>
						<td class="fila-det-border" colspan="2">
							<div align="right">
								<span class="fila-det"><%=BPMPIF.getSubTotalGravado()+""%></span>
								<input name="subTotalGravado" type="hidden" value="<%=BPMPIF.getSubTotalGravado()%>" class="campo" size="20" maxlength="20" readonly="yes" >
							</div>
						</td>
						<td class="fila-det-border"></td>
					</tr>
					<tr class="fila-det-bold">
						<td class="fila-det-border">Bonificaci&oacute;n:</td>
						<td class="fila-det-border">
							<div align="right"><%=BPMPIF.getPorcentajeBonificacion()+""%>
								<input name="porcentajeBonificacion" type="hidden" class="campo" id="porcentajeBonificacion" value="<%=BPMPIF.getPorcentajeBonificacion()%>" size="7" maxlength="6"  >%
							</div>
						</td>
						<td class="fila-det-border">
							<div align="right">
								<span class="fila-det"><%=BPMPIF.getBonificacion()+""%></span>
								<input name="bonificacion" type="hidden" value="<%=BPMPIF.getBonificacion()%>" class="campo" size="20" maxlength="20"  readonly="readonly" >
							</div>
						</td>
						<td class="fila-det-border">&nbsp;</td>
					</tr>
					<tr class="fila-det-bold">
						<td class="fila-det-border">Neto Gravado :  </td>
						<td class="fila-det-border" colspan="2"></td>
						<td class="fila-det-border">
							<div align="right">
								<span class="fila-det"><%=BPMPIF.getNetoGravado()+""%></span>
								<input name="netoGravado" type="hidden" value="<%=BPMPIF.getNetoGravado()%>" >											
							</div>
						</td>
						<td class="fila-det-border">&nbsp;</td>
					</tr>
					<tr height="10" class="permiso-cinco">
						<td colspan="4" ></td>
					</tr>
					<tr class="fila-det-bold">
						<td class="fila-det-border">Neto Exento &nbsp;:  </td>
						<td class="fila-det-border" colspan="2"></td>
						<td class="fila-det-border"  >
							<div align="right">
								<span class="fila-det"><%=BPMPIF.getNetoExento()+""%></span>
								<input name="netoExento" type="hidden" value="<%=BPMPIF.getNetoExento()%>"  >
							</div>
						</td>
						<td  class="fila-det-border" >&nbsp;</td>
					</tr>
					<tr  height="10" class="permiso-cinco">
						<td height="3" colspan="4"  class="fila-det-border" ></td>
					</tr>
					<tr class="fila-det-bold">
						<td class="fila-det-border">IVA :  </td>
						<td class="fila-det-border" > 
							<div align="right"><%=BPMPIF.getProvPorcNormalIva()+""%>
								<input name="provPorcNormalIva" type="hidden"  id="provPorcNormalIva" value="<%=BPMPIF.getProvPorcNormalIva()%>" >%
							</div>
						</td>
						<td class="fila-det-border"  >
							<div align="right">
								<span class="fila-det"><%=BPMPIF.getIva()+""%></span>
								<input name="iva" type="hidden" value="<%=BPMPIF.getIva()%>" >
							</div>
						</td>
						<td class="fila-det-border" colspan="2" >&nbsp;</td>
					</tr>
					<tr class="fila-det-bold">
						<td class="fila-det-border">Percepcion IVA &nbsp;:  </td>
						<td class="fila-det-border" >
							<div align="right"><%=BPMPIF.getProvPorcEspecialIvaPercep()+""%>
								<input name="provPorcEspecialIvaPercep" type="hidden" class="campo" id="provPorcEspecialIvaPercep" value="<%=BPMPIF.getProvPorcEspecialIvaPercep()%>" >%
							</div>
						</td>
						<td class="fila-det-border" >
							<div align="right">
								<span class="fila-det"><%=BPMPIF.getPercepcionIva()+""%></span>
								<input name="percepcionIva" type="hidden" value="<%=BPMPIF.getPercepcionIva()%>" >											
							</div>
						</td>
						<td class="fila-det-border"  >&nbsp;</td>
					</tr>
					<tr class="fila-det-bold">
						<td class="fila-det-border">&nbsp;Total:  </td>
						<td class="fila-det-border" >&nbsp;</td>
						<td class="fila-det-border" >
							<div align="right">
								<span class="fila-det"><%=Common.getNumeroFormateado(BPMPIF.getImporte().doubleValue(), 10, 2 )%></span>
								<input name="importe" type="hidden" value="<%=BPMPIF.getImporte()%>">
							</div>
						</td>
						<td class="fila-det-border" >&nbsp;</td>
					</tr>
					<tr class="fila-det-bold" >
						<td class="fila-det-border"  colspan="5">
						<!--INICIA MANEJO DE ASIENTOS TEST -->
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<%
							int tipomov = BPMPIF.getTipomov().intValue();
							if( Double.parseDouble(BPMPIF.getNetoGravado()) > 0){%>									
								<tr class="fila-det-bold">
									<td width="20%" class="text-globales">NETO GRAVADO</td>  
									<td width="80%" class="text-globales" colspan="3" >&nbsp;</td>
								</tr>
								<tr class="fila-det-bold">
									<td class="fila-det-border">Cuenta</td>
									<td class="fila-det-border"><div align="right">Debe</div></td>			
									<td class="fila-det-border"><div align="right">Haber</div></td>																										
									<td class="fila-det-border"></td>
								</tr>
								<tr>
									<td class="text-globales" height="3" colspan="4"></td>
								</tr>													
							<% 
								Hashtable htNG = BPMPIF.getHtNetoGravado();
								Enumeration enumNG = htNG.keys();
								
								while(enumNG.hasMoreElements())
								{
									String  keyAsiento = (String )enumNG.nextElement();
									String [] datosAsiento = (String [])htNG.get(keyAsiento);
							%>
								<tr class="fila-det-bold">
									<td class="fila-det-border"><%= datosAsiento[0] + " - " + datosAsiento[1]%>&nbsp;</td>
									
									
									<td class="fila-det-border"><div align="right"><%= tipomov != 3 ? datosAsiento[2]  : ""%>&nbsp;</div></td>
									<td class="fila-det-border"><div align="right"><%= tipomov == 3 ? datosAsiento[2]  : "" %>&nbsp;</div></td>				
									<td class="fila-det-border"></td>
									
								</tr>
							<%
								}%>
							<%
							} 
							if( Double.parseDouble(BPMPIF.getNetoExento()) > 0 )
							{%>
								<tr class="fila-det-bold" >
									<td class="text-globales" >NETO EXENTO &nbsp;</td>
									<td class="text-globales" colspan="3" >&nbsp;</td>
								</tr>
								<tr class="fila-det-bold">
									<td width="60%" class="fila-det-border">Cuenta </td>
									<td width="20%" class="fila-det-border"><div align="right">Debe </div></td>
									<td width="20%" class="fila-det-border"><div align="right">Haber </div></td>
								</tr>
								<tr>
									<td class="text-globales" height="3" colspan="4"></td>
								</tr>
							<% 
								Hashtable htNE = BPMPIF.getHtNetoExento();
								Enumeration enumNE = htNE.keys();
								
								while(enumNE.hasMoreElements()){
								String  keyAsiento = (String )enumNE.nextElement();
								String [] datosAsiento = (String [])htNE.get(keyAsiento);
							%>
								<tr class="fila-det">
									<td ><%= datosAsiento[0] + " - " + datosAsiento[1]%>&nbsp;</td>
									
									<td ><div align="right"><%= tipomov != 3 ? datosAsiento[2]  : ""%>&nbsp;</div></td>
									<td class="fila-det-border"><div align="right"><%= tipomov == 3 ? datosAsiento[2]  : "" %>&nbsp;</div></td>	
									<td class="fila-det-border"></td>
												
								</tr>
								<%  
								}%>									
							<% 
							}
							if(BPMPIF.getImporte().doubleValue()>0){%>	  									
								<tr class="fila-det">
									<td class="text-globales" colspan="4">IVA Y TOTAL</td>
								</tr>
								<tr class="fila-det">
									<td width="60%" class="fila-det-border">Cuenta </td>
									<td width="20%" class="fila-det-border"><div align="right">Debe </div></td>
									<td width="20%" class="fila-det-border"><div align="right">Haber </div></td>
									<td class="fila-det-border" ></td>
								</tr>
								<tr>
									<td class="text-globales"  height="3" colspan="4"></td>
								</tr>									
							<% 
								Hashtable htIvaTotal = BPMPIF.getHtIvaTotal();
								// Ordenamiento hashtable.
								Vector vecSort = new Vector(htIvaTotal.keySet());
								Collections.sort(vecSort);
								Enumeration enumIVATotal = vecSort.elements();
								while(enumIVATotal.hasMoreElements()){
									String  keyAsiento = (String )enumIVATotal.nextElement();
									String [] datosAsiento = (String [])htIvaTotal.get(keyAsiento);
									if(Double.parseDouble(datosAsiento[2]) > 0)
									{%>
								<tr class="fila-det">
									<td class="fila-det-border"><%= datosAsiento[0] + " - " + datosAsiento[1]%>&nbsp;</td>
									<td class="fila-det-border"><div align="right"><%= ( datosAsiento[3].equalsIgnoreCase("T") ?  (tipomov == 3 ? datosAsiento[2]  : "" ) : (tipomov != 3 ? datosAsiento[2]  : "" ))%>&nbsp;</div></td>
									<td class="fila-det-border"><div align="right"><%= (!datosAsiento[3].equalsIgnoreCase("T") ? (tipomov == 3 ? datosAsiento[2]  : "" ) : (tipomov != 3 ? datosAsiento[2]  : "" ))%>&nbsp;</div></td>
									<td class="fila-det-border"></td>
								</tr>
									<%}
								}
							}  
							%>		
							</table>
						</td>
					</tr>
					<!--FINALIZA MANEJO DE ASIENTOS TEST -->									
					<tr>
						<td height="35" colspan="2">&nbsp;</td>
						<td width="36%" height="21" colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td></td>
						<td>........................</td>
						<td>........................ </td>
						<td>........................</td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr class="fila-det-bold">
						<td>&nbsp;</td>
						<td><div align="center">Vo. Precios </div></td>
						<td><div align="center">Vo. C&aacute;lculos</div></td>
						<td><div align="center">Registro</div></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
<%
//FIN TIPO-MOV. SELECCIONADO.
}
//FIN PROVEEDOR SELECCIONADO.
					}%>
	

</div>
<% 
BPMPIF.destruirHTSession();
%>
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