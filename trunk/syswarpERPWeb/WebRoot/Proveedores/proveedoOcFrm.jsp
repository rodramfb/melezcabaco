<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: proveedo_Oc_Cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Mar 28 09:44:56 CEST 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias
*/ 
%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
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
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BPOCF"  class="ar.com.syswarp.web.ejb.BeanProveedoOcFrm"   scope="page"/>
<head>
 <title>Emisi&oacute;n de Ordenes de Compra</title>
 <link rel="stylesheet" href="<%=pathskin%>">
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script> 
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script>
    /*-------*/
	function detalleProveedor(){   
	  if (document.frm.idproveedor.value==0 || document.frm.idproveedor.value=="")
		alert("Seleccione proveedor.");	 		
	  else
	    abrirVentana("proveedoProveedDetalle.jsp?idproveedor=" + document.frm.idproveedor.value , 'proveedor',800, 400);  
	}
    /*-------*/
    function buscarGrupoCotizacion(){
 	  var i = 0;
	  for(i=0;i<document.frm.idestadooc.length;i++)	
	    if(document.frm.idestadooc[i].checked ) 
		  break; 
	  if(i==0){
	    alert("Para asociar grupo de cotización seleccione estado 'En cotización'");
		return true;
	  }else if(i==1){
	    abrirVentana('lov_proveedoOcGrupoCotizacion.jsp', 'cotizacion', 700, 400);
		return true;
	  }else{
	    alert("Es necesario seleccionar un estado.");
	  }
	}
    /*-------*/ 
    function verificaEstadoAprobada(){
	  if(document.frm.idgrupooc.value !=0){
	    if(confirm("Confirma inicio de orden de compra aprobada ?")){
		  document.frm.idgrupooc.value = 0;
		  document.frm.grupooc.value = "";
		}else{
		  document.frm.idestadooc[1].checked = true;
		}
	  }
	}
    /*-------*/ 
    function imprimirDocumento(idoc, tipo, fueradestock){
	  var pagina ;
	  if(tipo == "oc"){
	    pagina = "proveedoOcFrameImprimeDoc.jsp?id_oc_cabe="+ idoc + "&tipo=" + tipo +"&fueradestock=" + fueradestock;
        abrirVentana(pagina, tipo, 700, 600);
	  }
	  else if(tipo == "pr") {
	    pagina = "proveedoOcFrameImprimeDoc.jsp?id_oc_cabe="+ idoc + "&tipo=" + tipo +"&fueradestock=" + fueradestock;
		abrirVentana(pagina, tipo, 700, 600);
	  }
	}
 
</script> 
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPOCF" property="*" />
 <%    
 String titulo = "Emisión de Ordenes de Compra" ; 
 String idempresa = session.getAttribute("empresa").toString() ;     
 BPOCF.setResponse(response);
 BPOCF.setRequest(request);
 BPOCF.setSession(session);
 BPOCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPOCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPOCF.setIdempresa( new BigDecimal( idempresa ));
 BPOCF.ejecutarValidacion(); 
 
 %>
<form action="proveedoOcFrm.jsp" method="post" name="frm">
   <input name="primeraCarga" type="hidden" value="false" >
   <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>
                <table width="100%" border="0"  cellpadding="0"
										cellspacing="0" class="text-globales">
                  <tr>
                    <td width="367">&nbsp;<%= titulo %></td>
                    <td width="467">&nbsp;</td> 
                    <td width="50">&nbsp;</td>
                    <td width="43"><div align="center"><img src="../imagenes/default/gnome_tango/actions/edit-clear.png" width="22" height="22" title="Limpiar datos" onClick="if(confirm('Confirma limpiar datos ?')) window.location = 'proveedoOcFrm.jsp'" style="cursor:pointer"></div></td>
                    <td width="42"><div align="center"> <a href="javascript:detalleProveedor()"><img
														src="../imagenes/default/gnome_tango/actions/contact-new.png"
														title="Datos del Proveedor" width="22" height="22" border="0"></a> </div></td>
                  </tr>
                </table></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border"><jsp:getProperty name="BPOCF" property="mensaje"/>&nbsp;
								<% if(BPOCF.isImprimePdf()){ 
								     String link = "../reportes/jasper/generaPDF.jsp?idoc=" + BPOCF.getId_oc_cabe().toString() + "&plantillaImpresionJRXML=oc_frame&idempresa=" + idempresa ;
								  %>
								  <a href="javascript:abrirVentana('<%= link %>', '', 800, 600)"><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" border="0" ></a>
							 <% } %></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Estado: (*)</td>
                <td class="fila-det-border"><table width="80%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="fila-det">
                    <td width="49%"><div align="center">Aprobada</div></td>
                    <td width="51%"><div align="center">En Cotizaci&oacute;n </div></td>
                  </tr>
                  <tr class="fila-det-border">
                    <td><div align="center">
                        <input name="idestadooc" type="radio" class="campo" value="2" onClick="verificaEstadoAprobada();" <%= BPOCF.getIdestadooc().toString().equals("2") ? "checked" : "" %>>
                    </div></td>
                    <td><div align="center">
                        <input name="idestadooc" type="radio" class="campo" value="1" <%= BPOCF.getIdestadooc().toString().equals("1") ? "checked" : "" %>>
                    </div></td>
                  </tr>
                </table></td>
                <td class="fila-det-border">Grupo Cotizaci&oacute;n </td>
                <td  class="fila-det-border" ><table width="82%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%"><input name="grupooc" type="text" class="campo" value="<%=BPOCF.getGrupooc()%>" size="30" /></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="buscarGrupoCotizacion();" style="cursor:pointer">
                      <input name="idgrupooc" type="hidden" class="campo" value="<%=BPOCF.getIdgrupooc()%>" size="30" /></td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">Proveedor: (*) </td>
                <td width="36%" class="fila-det-border">
                  <table width="72%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%"><input name="proveedor" type="text" class="campo" value="<%=BPOCF.getProveedor()%>" size="30"></td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
													title="Modificar condici&oacute;n de venta ..." width="22" height="22" style="cursor:pointer"
													onclick="mostrarLOV('lov_proveedores.jsp')" />
                      <input name="idproveedor" type="hidden" class="campo" value="<%=BPOCF.getIdproveedor()%>" size="30"  ></td>
                    </tr>
                  </table></td>
                <td class="fila-det-border">&nbsp;Fecha: (*) </td>
                <td  class="fila-det-border" >&nbsp;<input class="campo" onFocus="this.blur()" size="12" readonly type="text" name="fechaoc" value="<%=BPOCF.getFechaoc()%>" maxlength="12">
                  <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fechaoc','BTN_date_4');return false;"> <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a> </td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">&nbsp;Condici&oacute;n de venta : (*) </td>
                <td width="36%" class="fila-det-border"><table width="72%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%"><input name="condicion" type="text" class="campo"
													id="condicion" value="<%=BPOCF.getCondicion()%>" size="30"
													readonly>                      </td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
													title="Modificar condici&oacute;n de venta ..." width="22" height="22" style="cursor:pointer"
													onClick="abrirVentana('../Clientes/lov_condventa.jsp','lov', 700, 400)">
                      <input name="idcondicion" type="hidden" id="idcondicion"
												value="<%=BPOCF.getIdcondicion()%>"></td>
                  </tr>
                  </table>			    </td>
                <td class="fila-det-border">&nbsp;Moneda: (*) </td>
                <td class="fila-det-border"><table width="84%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%"><input name="moneda" type="text" class="campo" id="moneda"
													value="<%=BPOCF.getMoneda()%>" size="30" readonly>                      </td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
													title="Modificar moneda ..." width="22" height="22" style="cursor:pointer"
													onClick="abrirVentana('../Clientes/lov_moneda.jsp', 'lov', 700, 400)"> <input name="idmoneda" type="hidden" id="idmoneda"
												value="<%=BPOCF.getIdmoneda()%>"> 					  </td>
                    </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">Tipo de Iva : (*) </td>
                <td class="fila-det-border">
				<table width="72%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%"><input name="tipoiva" type="text" class="campo" id="tipoiva"
													value="<%=BPOCF.getTipoiva()%>" size="30" readonly>                      </td>
                      <td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png"
													title="Modificar tipo de iva ..." width="22" height="22" style="cursor:pointer"
													onClick="abrirVentana('../Clientes/lov_Tipoiva.jsp','tipoiva', 700, 400)"> <input name="idtipoiva" type="hidden" id="idtipoiva"
												value="<%=BPOCF.getIdtipoiva()%>">					  </td>
                  </tr>
                </table>				</td>
                <td width="13%" class="fila-det-border">&nbsp;Cotizacion: </td>
                <td width="31%" class="fila-det-border">&nbsp;<input name="cotizacion" type="text"
										value="<%=BPOCF.getCotizacion()%>" class="campo" size="18"
										maxlength="18"></td>
              </tr>
			  <tr class="fila-det">
                <td width="20%" class="fila-det-border">Deposito de Entrega Previsto :  </td>
				<input name="codigo_dt" type="hidden" id="codigo_dt" value="<%=BPOCF.getCodigo_dt()%>" >
                <td colspan="3" class="fila-det-border"><table width="97%" border="0">
                    <tr class="fila-det-border">
                      <td width="23%" ><input name="descrip_dt" type="text" value="<%=BPOCF.getDescrip_dt()%>" class="campo" size="30" maxlength="100" readonly ></td>
                      <td width="14%"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_deposito.jsp','',800,400);"></td>
                      <td width="28%" class="fila-det">Fecha prevista para la entrega</td>
                      <td width="35%"><input name="fecha_entrega_previstaStr" type="text" class="campo" id="fecha_entrega_previstaStr" onFocus="this.blur()" value="<%=BPOCF.getFecha_entrega_previstaStr()%>" size="12" maxlength="12" readonly>
                        <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_4', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_4', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_4', 'img_Date_DOWN');showCalendar('frm','fecha_entrega_previstaStr','BTN_date_4');return false;"> <img align="absmiddle" border="0" name="BTN_date_4" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a></td>
                      
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">Observaciones:  </td>
                <td colspan="3" class="fila-det-border">&nbsp;<textarea name="observaciones" cols="80" rows="2" class="campo"><%=BPOCF.getObservaciones()%></textarea></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td colspan="4" class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
                  <tr class="text-globales">
                    <td width="16%">&nbsp;STOCK </td>
                    <td width="84%" colspan="3"><!-- 1.1 -->
                        <table width="100%" border="0" cellspacing="3" cellpadding="0"
										height="100%">
                          <tr class="text-globales">
                            <td width="20%" height="24"> Ingresar Art&iacute;culos </td>
                            <td width="24%"><input type="button" name="actualiza" value=">>"
													class="boton"
													onClick="abrirVentana('lov_articulos_oc.jsp', 'art', 900, 600)">                            </td> 
                            <td width="25%">Art&iacute;culos Fuera de Stock </td>
                            <td width="31%"><label>
                              <input type="checkbox" name="fueradestock" value="true" onClick="document.frm.totalgeneral.readOnly = !this.checked ; " <%= BPOCF.isFueradestock() ? "checked" : "" %> >
                            </label></td>
                          </tr>
                        </table>
                      <!--1.1 -->                    </td>
                  </tr>
                  <tr class="fila-det">
                    <td colspan="4" valign="top" class="fila-det-border"><!-- 2.1 -->
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr class="fila-det-bold">
                            <td width="62%" class="fila-det-border"> Articulo </td>
                            <td width="11%" class="fila-det-border"><div align="right"> Cantidad </div></td>
                            <td width="8%" class="fila-det-border"><div align="right"> P.U </div></td>
                            <td width="19%" class="fila-det-border"><div align="right">Total </div></td>
                          </tr>
                          <%												
										Hashtable htArticulosInOutOK = (Hashtable)session.getAttribute("htArticulosInOutOK");
												if(htArticulosInOutOK != null){													
													 Enumeration en = htArticulosInOutOK.keys();
													 
													 while(en.hasMoreElements()){
													   String key = (String)en.nextElement();
														 String art[] = (String[])htArticulosInOutOK.get(key);
														 
														 %>
                          <tr class="fila-det">
                            <td height="25" class="fila-det-border"><%= art[0] %> - <%= art[2] %></td>
                            <td class="fila-det-border"><div align="right"> <%= art[10] %> </div></td>
                            <td class="fila-det-border"><div align="right"> <%= art[5] %> </div></td>
                            <td class="fila-det-border"><div align="right"> <%= art[11] %> </div></td>
                          </tr>
                          <% 
													 }
												}
												
												%>
                          <tr class="fila-det-bold">
                            <td colspan="3" class="fila-det-border"><div align="right">Total&nbsp;</div></td>
                            <td class="fila-det-border"><div align="right"><%= BPOCF.getTotalDebe() %></div></td>
                          </tr>
                        </table>
                    <!-- 2.1 -->                    </td>
                  </tr>
                  <tr class="text-globales" height="3">
                    <td width="16%" class="fila-det-border"></td>
                    <td colspan="4" class="fila-det-border"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">&nbsp;</td>
                <td width="36%" class="fila-det-border">&nbsp;</td>
                <td width="13%" class="fila-det-border">&nbsp;</td>
                <td width="31%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td colspan="4" class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det-bold">
                    <td width="45%" class="fila-det-border"><div align="right"> Bonific 1: </div></td>
                    <td width="5%" class="fila-det-border">&nbsp;</td>
                    <td width="15%" class="fila-det-border"><input name="bonific1" type="text"
										value="<%=BPOCF.getBonific1()%>" class="campo" size="10"
										maxlength="18" /></td>
                    <td width="15%" class="fila-det-border"><div align="right"> Recargo 1: </div></td>
                    <td width="5%" class="fila-det-border">&nbsp;</td>
                    <td width="20%" class="fila-det-border"><input name="recargo1" type="text"
										value="<%=BPOCF.getRecargo1()%>" class="campo" size="10"
										maxlength="18" /></td>
                  </tr>
                  <tr class="fila-det-bold">
                    <td class="fila-det-border"><div align="right"> Bonific 2: </div></td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border"><input name="bonific2" type="text"
										value="<%=BPOCF.getBonific2()%>" class="campo" size="10"
										maxlength="18" />
                    </td>
                    <td class="fila-det-border"><div align="right"> Recargo 2: </div></td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border"><input name="recargo2" type="text"
										value="<%=BPOCF.getRecargo2()%>" class="campo" size="10"
										maxlength="18" /></td>
                  </tr>
                  <tr class="fila-det-bold">
                    <td class="fila-det-border"><div align="right"> Bonific 3: </div></td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border"><input name="bonific3" type="text"
										value="<%=BPOCF.getBonific3()%>" class="campo" size="10"
										maxlength="18" />
                        <input name="totalbonificaorigen" type="hidden" value="<%= BPOCF.getTotalbonificaorigen() %>" /></td>
                    <td class="fila-det-border"><div align="right"> Recargo 3: </div></td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border"><input name="recargo3" type="text"
										value="<%=BPOCF.getRecargo3()%>" class="campo" size="10"
										maxlength="18" /></td>
                  </tr>
                  <tr class="fila-det-bold"> 
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border"><div align="right">Recargo 4:</div></td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border"><input name="recargo4" type="text"	value="<%=BPOCF.getRecargo4()%>" class="campo" size="10" maxlength="18" />
                    <input name="totalrecargoorigen" type="hidden" value="<%= BPOCF.getTotalrecargoorigen() %>" /></td>
                  </tr>
                  <tr >
                    <td  height="3" colspan="6" class="permiso-cinco"></td>
                  </tr>
                  <tr class="fila-det-bold">
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border"><div align="right"> Total General: </div></td>
                    <td class="fila-det-border" >&nbsp;</td>
                    <td class="fila-det-border" ><input name="totalgeneral" type="text"	value="<%= BPOCF.getTotalgeneral() %>" class="campo" size="10" maxlength="18" <%= BPOCF.isFueradestock() ? "" : "readonly" %>/>
                      <input name="totalgeneralorigen" type="hidden"	value="<%= BPOCF.getTotalgeneral() %>" /></td>
                  </tr>
                  <tr class="fila-det-bold">
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border">&nbsp;</td>
                    <td class="fila-det-border"><div align="right">Total General IVA:</div></td>
                    <td class="fila-det-border" >&nbsp;</td>
                    <td class="fila-det-border" ><%=BPOCF.getTotaliva() + "" %></td>
                  </tr>
                  <tr >
                    <td  height="3" colspan="6" class="permiso-cinco"></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">&nbsp;</td>
                <td width="36%" class="fila-det-border">&nbsp;</td>
                <td width="13%" class="fila-det-border"><div align="center">
                  <input name="calcular" type="submit" class="boton" id="calcular" value="Calcular Importes">
                </div></td>
                <td width="31%" class="fila-det-border"><div align="center">
                  <input name="validar" type="submit" value="Confirmar Orden" class="boton">
                </div></td>
              </tr>
              <tr class="fila-det">
                <td width="20%" class="fila-det-border">&nbsp;</td>
                <td width="36%" class="fila-det-border">&nbsp;</td>
                <td width="13%" class="fila-det-border">&nbsp;</td>
                <td width="31%" class="fila-det-border">&nbsp;</td>
              </tr>
          </table>
       </td>
     </tr>
  </table>
</form>
<!--script>
  //imprimirDocumento(<%= BPOCF.getId_oc_cabe().toString() %>, '<%= BPOCF.getImprimir() %>', '<%= BPOCF.isFueradestock() %>');  
</script-->
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

