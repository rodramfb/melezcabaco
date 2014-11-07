<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: Stockstock
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 04 09:21:30 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
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
<jsp:useBean id="BSF"  class="ar.com.syswarp.web.ejb.BeanStockstockConsultaFrm"   scope="page"/>
<head>
 <title>FRMStockstock.jsp</title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
 
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script></head>
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BSF" property="*" />
 <% 
 String titulo = BSF.getAccion().toUpperCase() + " de Articulos" ;
 BSF.setResponse(response);
 BSF.setRequest(request);
 BSF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BSF.setUsuarioact( session.getAttribute("usuario").toString() );
 String grupo_st      = str.esNulo( request.getParameter("grupo_st") );
 String d_grupo_st    = str.esNulo( request.getParameter("d_grupo_st") );
 String unimed_st     = str.esNulo( request.getParameter("unimed_st") );
 String d_unimed_st   = str.esNulo( request.getParameter("d_unimed_st") );
 String unialt1_st     = str.esNulo( request.getParameter("unialt1_st") );
 String d_unialt1_st   = str.esNulo( request.getParameter("d_unialt1_st") );
 String unialt2_st     = str.esNulo( request.getParameter("unialt2_st") );
 String d_unialt2_st   = str.esNulo( request.getParameter("d_unialt2_st") );
 String unialt3_st     = str.esNulo( request.getParameter("unialt3_st") );
 String d_unialt3_st   = str.esNulo( request.getParameter("d_unialt3_st") );
 String unialt4_st     = str.esNulo( request.getParameter("unialt4_st") );
 String d_unialt4_st   = str.esNulo( request.getParameter("d_unialt4_st") );
 String nom_com_st     = str.esNulo( request.getParameter("nom_com_st") );
 String d_nom_com_st   = str.esNulo( request.getParameter("d_nom_com_st") );

 String proveed_st     = str.esNulo( request.getParameter("proveed_st") );
 String d_proveed_st   = str.esNulo( request.getParameter("d_proveed_st") );
 BSF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BSF.ejecutarValidacion();
 %>


<form action="StockstockConsultaFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BSF.getAccion()%>" >
   <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border"><jsp:getProperty name="BSF" property="mensaje"/>&nbsp;</td>
              </tr>
			  
			  <tr class="fila-det">
                <td width="22%" height="22" class="fila-det-border"> Codigo: (*)  </td>
              <td class="fila-det-border">
			  <input name="codigo_st" title ="Codigo del Articulo" type="text" value="<%=BSF.getCodigo_st()%>" class="campo" size="15" maxlength="15"  <%= !BSF.getAccion().equalsIgnoreCase("alta") ? "readonly" : "" %> ></td>
                <td class="fila-det-border">Alias: (*)  </td>
                <td class="fila-det-border">
				<input name="alias_st" title="forma nemotécnica de codificar." type="text" value="<%=BSF.getAlias_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
			  </tr>
			  	
              <tr class="fila-det">
                <td width="22%" height="22" class="fila-det-border">Descripcion: (*) </td>
              <td class="fila-det-border"><input name="descrip_st" title="Descripcion del Articulo" type="text" value="<%=BSF.getDescrip_st()%>" class="campo" size="60" maxlength="50" readonly="false"  ></td>
                <td class="fila-det-border">Descripcion Corta: (*)  </td>
                <td class="fila-det-border"><input name="descri2_st" title="Si con la descripción principal no le alcanza para definir un artículo, Ud. puede extender dicha definición a este segundo renglón. Luego, para facturar puede especificar que ambos renglones salgan en las facturas." type="text" value="<%=BSF.getDescri2_st()%>" class="campo" size="60" maxlength="20" readonly="false"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Costo Promedio Ponderado:  </td>
              <td width="32%" class="fila-det-border"><input name="cost_pp_st" title="Ingrese el costo promedio ponderado del art&iacute;culo. Este campo se actualizar&aacute; autom&aacute;ticamente cada vez que se realice una compra del mismo." type="text" value="<%=BSF.getCost_pp_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
                <td width="19%" class="fila-det-border">Precio Prom. Ponderado: </td>
                <td width="27%" class="fila-det-border"><input name="precipp_st" title="Ingrese el costo promedio ponderado del artículo. Este campo se actualizará automáticamente cada vez que se realice una compra del mismo." type="text" value="<%=BSF.getPrecipp_st()%>" class="campo" size="15" maxlength="15"  readonly="false" ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Costo Ultima Compra: </td>
              <td class="fila-det-border"><input name="cost_uc_st" title="Ingrese el costo de &uacute;ltima compra del art&iacute;culo. Este campo se actualizar&aacute; autom&aacute;ticamente cada vez que se realice una compra del mismo." type="text" value="<%=BSF.getCost_uc_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
                <td class="fila-det-border">Precio Ultima Compra: </td>
                <td class="fila-det-border"><input name="ultcomp_st" title="Ingrese el costo de última compra del artículo. Este campo se actualizará automáticamente cada vez que se realice una compra del mismo." type="text" value="<%=BSF.getUltcomp_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Costo Reposicion:</td>
              <td class="fila-det-border"><input name="cost_re_st" title="Ingrese el costo de reposici&oacute;n del art&iacute;culo. Este campo se actualizar&aacute; autom&aacute;ticamente cada vez que se realice una compra del mismo." type="text" value="<%=BSF.getCost_re_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
                <td class="fila-det-border">Precio Reposicion: </td>
                <td class="fila-det-border"><input name="reposic_st" title="Ingrese el costo de reposición del artículo. Este campo se actualizará automáticamente cada vez que se realice una compra del mismo." type="text" value="<%=BSF.getReposic_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" height="32" class="fila-det-border">Moneda:  </td>
              <td class="fila-det-border"><table width="100%" border="0">
                    <tr class="fila-det-border">
                      <td width="61%" >
                        <input name="d_nom_com_st" title="Es la moneda en que est&aacute;n expresados los precios de Costo del art&iacute;culo. " type="text" class="campo" id="d_nom_com_st" value="<%=str.esNulo(BSF.getD_nom_com_st())%>" size="60" readonly></td>
                      <td width="39%">&nbsp;</td>
                      <input name="nom_com_st" type="hidden" id="nom_com_st" value="<%=BSF.getNom_com_st()%>">
                    </tr>
                  </table></td>
                <td class="fila-det-border">Grupo: (*)  </td>
                <td class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="d_grupo_st" title="Los grupos representan el nivel intermedio de jerarquía en la formación de códigos de artículos de Stock." type="text" class="campo" id="d_grupo_st" value="<%=BSF.getD_grupo_st()%>" size="60" readonly></td>
                    <td width="39%">&nbsp;</td>
                    <input name="grupo_st" type="hidden" id="grupo_st" value="<%=BSF.getGrupo_st()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cantidad Minima:  </td>
              <td class="fila-det-border"><input name="cantmin_st" title="Sirve para chequear el punto de reposición." type="text" value="<%=BSF.getCantmin_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
                <td class="fila-det-border">Unidad de Medida </td>
                <td class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="d_unimed_st" title="Debe haber sido cargada previamente. Si Ud. está cargando un artículo Serializable la cantidad de decimales de la Unidad de Medida seleccionada debe ser cero. " type="text" class="campo" id="d_unimed_st" value="<%=str.esNulo(BSF.getD_unimed_st())%>" size="60" readonly></td>
                    <td width="39%">&nbsp;</td>
                    <input name="unimed_st" type="hidden" id="unimed_st" value="<%=BSF.getUnimed_st()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" height="22" class="fila-det-border">Bonificacion:  </td>
              <td class="fila-det-border"><input name="bonific_st" title="Si Ud. desea que al capturar este art&iacute;culo para su venta le aparezca un porcentaje de bonificaci&oacute;n por defecto deber&aacute; cargarla aqu&iacute;." type="text" value="<%=BSF.getBonific_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
                <td class="fila-det-border">Impuestos Internos: </td>
                <td class="fila-det-border"><input name="impint_st" title="En el caso de que este artículo posea Impuestos Internos por Ventas, cargue dicho porcentaje en este campo y el sistema registrará automáticamente los Impuestos Internos en el momento de la Facturación." type="text" value="<%=BSF.getImpint_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Impuestos Cantidad:  </td>
              <td colspan="3" class="fila-det-border"><input name="impcant_st" title="En el caso de que este art&iacute;culo posea Impuestos por Cantidad en Ventas, cargue dicho porcentaje en este campo y el sistema registrar&aacute; autom&aacute;ticamente los Impuestos por Cantidad en el momento de la Facturaci&oacute;n." type="text" value="<%=BSF.getImpcant_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
              </tr>
              
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">C.Contable Compras:  </td>
                <td class="fila-det-border"> 
								<table width="40%" border="0">
                    <tr class="fila-det-border">
                      <td width="30%"><input name="cuencom_st" title ="en general puede ser Mercaderías de Reventa, cuenta de Activo." type="text" value="<%=str.esNulo(BSF.getCuencom_st())%>" class="campo" size="60" maxlength="15" readonly="false"></td>
                      <td width="70%">&nbsp;</td>
                    </tr>
                  </table></td>
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>              
              
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">C.Contable Ventas:  </td>
                <td class="fila-det-border"><table width="40%" border="0">
                  <tr class="fila-det-border">
                    <td width="30%"><input name="cuenven_st" title="en general puede ser Ventas, cuenta contable de R+." type="text" value="<%=str.esNulo(BSF.getCuenven_st())%>" class="campo" size="60" maxlength="15" readonly="false"  ></td>
                    <td width="70%">&nbsp;</td>
                  </tr>
                </table></td>
                <td class="fila-det-border">C.Contable Ventas2: </td>
                <td class="fila-det-border"><table width="50%" border="0">
                  <tr class="fila-det-border">
                    <td width="30%"><input name="cuenve2_st" title="en general puede ser Ventas, cuenta contable de R+." type="text" value="<%=str.esNulo(BSF.getCuenve2_st())%>" class="campo" size="60" maxlength="15" readonly="false"  ></td>
                    <td width="70%">&nbsp;</td>
                  </tr>
                </table>								</td> 
              </tr> 
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">C.Contable Costos:  </td>
                <td class="fila-det-border"><table width="40%" border="0">
                  <tr class="fila-det-border">
                    <td width="30%"><input name="cuencos_st" title="en general puede ser Costo de la Mercadería Vendida, cuenta contable de R-." type="text" value="<%=str.esNulo(BSF.getCuencos_st())%>" class="campo" size="60" maxlength="15" readonly="false"  ></td>
                    <td width="70%">&nbsp;</td>
                  </tr>
                </table></td>
                <td class="fila-det-border">C.Contable Ajustes: </td>
                <td class="fila-det-border"><table width="50%" border="0">
                  <tr class="fila-det-border">
                    <td width="30%"><input name="cuenaju_st" title="Esta cuenta se utiliza para mantener el Stock existente siempre valorizado contablemente a Costo de Reposición. " type="text" value="<%=str.esNulo(BSF.getCuenaju_st())%>" class="campo" size="60" maxlength="15" readonly="false"  ></td>
                    <td width="70%">&nbsp;</td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Inventariable:  </td>
                <td class="fila-det-border">
                  <select name="inventa_st" disabled="disabled" title="Este campo le permitirá listar (en futuras versiones) los artículos referentes a un tipo de inventario. " id="inventa_st"  >
                    <option value="S" <%= BSF.getInventa_st().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                    <option value="N" <%= BSF.getInventa_st().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                  </select></td>
                <td class="fila-det-border">&nbsp;Reserva Producci&oacute;n: </td>
                <td class="fila-det-border"><input name="cafecga_st" title="es una cantidad destinada a produccion (para informes)." type="text" value="<%=BSF.getCafecga_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Proveedor Habitual:  </td>
                <td colspan="3" class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="d_proveed_st" title="Es el proveedor al que usualmente Ud. le compra este producto. Si los artículos que Ud. declara, poseen más de un proveedor, deje vacío este campo y el que sigue (Código de Artículo para el Proveedor)" type="text" class="campo" id="d_proveed_st" value="<%=str.esNulo(BSF.getD_proveed_st())%>" size="60" readonly="false"></td>
                    <td width="39%">&nbsp;</td>
                    <input name="proveed_st" type="hidden" id="proveed_st" value="<%=BSF.getProveed_st()%>">
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Cod.Art.Proveedor:  </td>
                <td class="fila-det-border"><input name="provart_st" title="Representa el código con el que se referencia al artículo en el proveedor. Estos dos últimos datos serán utilizados en las Ordenes de Compra en futuras versiones." type="text" value="<%=str.esNulo(BSF.getProvart_st())%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
                <td class="fila-det-border">Marca: </td>
                <td class="fila-det-border"><select name="marca_st" title="(para productos especiales)." id="marca_st" disabled="disabled"  >
                  <option value="S" <%= BSF.getMarca_st().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                  <option value="N" <%= BSF.getMarca_st().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Es Serializable? :  </td>
                <td class="fila-det-border"><select name="id_indi_st" id="id_indi_st" disabled="disabled"  >
                  <option value="S" <%= BSF.getId_indi_st().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                  <option value="N" <%= BSF.getId_indi_st().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                </select></td>
                <td class="fila-det-border">Despacho: </td>
                <td class="fila-det-border"><select name="despa_st" title="Con Nro. de Despacho (S/N): Indica si el artículo lleva Despacho de Importación o no." id="despa_st"  disabled="disabled" >
                  <option value="S" <%= BSF.getDespa_st().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                  <option value="N" <%= BSF.getDespa_st().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                </select></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Unidad Alt 1 </td>
                <td class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="d_unialt1_st" title="Debe haber sido cargada previamente. Si Ud. está cargando un artículo Serializable la cantidad de decimales de la Unidad de Medida seleccionada debe ser cero. " type="text" class="campo" id="d_unialt1_st" value="<%=str.esNulo(BSF.getD_unialt1_st())%>" size="60" readonly="false"></td>
                    <td width="39%">&nbsp;</td>
                    <input name="unialt1_st" type="hidden" id="unialt1_st" value="<%=BSF.getUnialt1_st()%>">
                  </tr>
                </table></td>
                <td class="fila-det-border">Factor 1: </td>
                <td class="fila-det-border"><input name="factor1_st" title="es el factor que sirve para multiplicar la unidad alternativa 1" type="text" value="<%=BSF.getFactor1_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Unidad Alt 2 </td>
                <td class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="d_unialt2_st" title="Debe haber sido cargada previamente. Si Ud. está cargando un artículo Serializable la cantidad de decimales de la Unidad de Medida seleccionada debe ser cero. " type="text" class="campo" id="d_unialt2_st" value="<%=str.esNulo(BSF.getD_unialt2_st())%>" size="60" readonly="false"></td>
                    <td width="39%">&nbsp;</td>
                    <input name="unialt2_st" type="hidden" id="unialt2_st" value="<%=BSF.getUnialt2_st()%>">
                  </tr>
                </table></td>
                <td class="fila-det-border">Factor 2: </td>
                <td class="fila-det-border"><input name="factor2_st" title="es el factor que sirve para multiplicar la unidad alternativa 2" type="text" value="<%=BSF.getFactor2_st()%>" class="campo" size="15" maxlength="15"  readonly="false" ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Unidad Alt 3 </td>
                <td class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="d_unialt3_st" title="Debe haber sido cargada previamente. Si Ud. está cargando un artículo Serializable la cantidad de decimales de la Unidad de Medida seleccionada debe ser cero. " type="text" class="campo" id="d_unialt3_st" value="<%=str.esNulo(BSF.getD_unialt3_st())%>" size="60" readonly="false"></td>
                    <td width="39%">&nbsp;</td>
                    <input name="unialt3_st" type="hidden" id="unialt3_st" value="<%=BSF.getUnialt3_st()%>">
                  </tr>
                </table></td>
                <td class="fila-det-border">Factor 3: </td>
                <td class="fila-det-border"><input name="factor3_st" title="es el factor que sirve para multiplicar la unidad alternativa 3" type="text" value="<%=BSF.getFactor3_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Unidad Alt 4 </td>
                <td class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="d_unialt4_st" title="Debe haber sido cargada previamente. Si Ud. está cargando un artículo Serializable la cantidad de decimales de la Unidad de Medida seleccionada debe ser cero. " type="text" class="campo" id="d_unialt4_st" value="<%=str.esNulo(BSF.getD_unialt4_st())%>" size="60" readonly="false"></td>
                    <td width="39%">&nbsp;</td>
                    <input name="unialt4_st" type="hidden" id="unialt4_st" value="<%=BSF.getUnialt4_st()%>">
                  </tr>
                </table></td>
                <td class="fila-det-border">Factor 4: </td>
                <td class="fila-det-border"><input name="factor4_st" title="es el factor que sirve para multiplicar la unidad alternativa 4" type="text" value="<%=BSF.getFactor4_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">Tipo de Iva:  </td>
                <td class="fila-det-border"><table width="23%" border="0">
                  <tr class="fila-det-border">
                    <td width="61%" >
                      <input name="d_tipoiva_st" title="Tipo de Iva" type="text" class="campo" id="d_tipoiva_st" value="<%=str.esNulo(BSF.getD_tipoiva_st())%>" size="60" readonly></td>
                    <td width="39%">&nbsp;</td>
                    <input name="tipoiva_st" type="hidden" id="tipoiva_st" value="<%=BSF.getTipoiva_st()%>">
                  </tr>
                </table></td>
                <td class="fila-det-border">Esquema de Produccion: </td>
                <td class="fila-det-border"><input name="esquema_st" type="text" value="<%=BSF.getEsquema_st()%>" class="campo" size="15" maxlength="15" readonly="false"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="22%" class="fila-det-border">es producto de Ventas?:  </td>
                <td class="fila-det-border">
                  <select name="venta_st" id="venta_st" disabled="disabled"  >
                    <option value="S" <%= BSF.getVenta_st().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                    <option value="N" <%= BSF.getVenta_st().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                  </select></td>
                <td class="fila-det-border">es producto de Compra?: </td>
                <td class="fila-det-border"><select name="compra_st" id="compra_st"  disabled="disabled" >
                  <option value="S" <%= BSF.getCompra_st().equalsIgnoreCase("S") ? "selected" : "" %> >SI</option>
                  <option value="N" <%= BSF.getCompra_st().equalsIgnoreCase("N") ? "selected" : "" %> >NO</option>
                </select></td>
              </tr>
			  
			  
			  
			  
			  
			  
			  
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td colspan="3" class="fila-det-border">&nbsp;               <input name="volver" type="submit" class="boton" id="volver" value="Volver"></td>
             </tr>
         </table>
       </td>
     </tr>
  </table>
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

