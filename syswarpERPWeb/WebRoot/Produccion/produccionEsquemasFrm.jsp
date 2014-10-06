<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: produccionEsquemas_Cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Feb 13 09:18:39 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="java.math.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
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
<jsp:useBean id="BPECF"  class="ar.com.syswarp.web.ejb.BeanProduccionEsquemasFrm"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="mypage">
 <link rel = "stylesheet" href = "<%= pathskin %>">
 
 <script language="JavaScript" src="vs/forms/forms.js"></script>
<script >
function indice (obj, valor){
	var i = 0;
  for (i=0;i<obj.length;i++)
	  if(obj.options[i].value == valor) break;
	return i;
}

function eliminarDetalle(rengloneliminar){
  
  if(confirm("Confirma eliminar detalle?" )){
	  document.frm.rengloneliminar.value = rengloneliminar;
		document.frm.submit();
	}
}

function modificarDetalle(renglon,tipo,codigo_st,descrip_st,cantidad,entsal,codigo_dt,margen_error,imprime,edita,formula,reutiliza){
   document.frm.renglon.value = renglon;
	 document.frm.tipo.value = tipo;
	 document.frm.codigo_st.value = codigo_st;
	 document.frm.descrip_st.value = descrip_st;	 
	 document.frm.cantidad.value = cantidad;
	 document.frm.entsal.selectedIndex = indice (document.frm.entsal, entsal); 
	 document.frm.codigo_dt.selectedIndex = indice (document.frm.codigo_dt, codigo_dt);
	 document.frm.margen_error.value = margen_error;
	 document.frm.imprime.selectedIndex = indice (document.frm.imprime, imprime); 
	 document.frm.edita.selectedIndex = indice (document.frm.edita, edita); 
 	 document.frm.reutiliza.selectedIndex = indice (document.frm.reutiliza, reutiliza); 
}
</script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BPECF" property="*" />
 <% 
 String titulo = BPECF.getAccion().toUpperCase() + " DE ESQUEMAS DE PRODUCCION" ;
 BPECF.setResponse(response);
 BPECF.setRequest(request);
 BPECF.setSession(session);
 BPECF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPECF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPECF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BPECF.ejecutarValidacion();

 %>
<form action="produccionEsquemasFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BPECF.getAccion()%>" >
<input name="idesquema" type="hidden" value="<%=BPECF.getIdesquema()%>" >
<input name="rengloneliminar" type="hidden" value="" >
<input name="primerCarga" type="hidden" value="false" >

   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
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
                <td colspan="2" class="fila-det-border"><jsp:getProperty name="BPECF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="10%" class="fila-det-border">Esquema: (*) </td>
                <td colspan="2" class="fila-det-border">&nbsp;<input name="esquema" type="text" value="<%=BPECF.getEsquema()%>" class="campo" size="100" maxlength="100"  ></td>
              </tr>
              <tr class="fila-det">
                <td width="10%" class="fila-det-border">Medida: (*) 
                <input name="codigo_md" type="hidden" value="<%=BPECF.getCodigo_md()%>" class="campo" size="20" maxlength="100"  ></td>
                <td width="58%" class="fila-det-border">&nbsp;<input name="d_codigo_md" type="text" value="<%=BPECF.getD_codigo_md()%>" class="campo" size="100" maxlength="100" readonly >                 </td>
                <td width="32%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_medida.jsp','',800,400);"></td>
              </tr>
              <tr class="fila-det">
                <td width="10%" class="fila-det-border">Observaciones:  </td>
                <td colspan="2" class="fila-det-border">&nbsp;<textarea name="observaciones" cols="70" rows="6" class="campo"><%=BPECF.getObservaciones()%></textarea></td>
              </tr>

              <tr class="text-globales">
                <td height="26" colspan="3" class="text-globales">Detalle de esquema </td>
              </tr>
              <tr class="fila-det">
                <td colspan="3" class="fila-det-border"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr class="fila-det">
                    <td width="19%" class="fila-det-border">Renglon: (*) </td>
                    <td colspan="2" class="fila-det-border">&nbsp;
                        <input name="renglon" type="text" value="<%=BPECF.getRenglon()%>" class="campo" size="10" maxlength="2"  readonly></td>
                    <td width="17%" class="fila-det-border"> Tipo: (*) </td>
                    <td width="17%" class="fila-det-border">&nbsp;
                        <input name="tipo" type="text" value="<%=BPECF.getTipo()%>" class="campo" size="10" maxlength="3"  readonly></td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">Articulo: (*) </td>
                    <td width="39%" class="fila-det-border">&nbsp;
												<input name="descrip_st" type="text" value="<%=BPECF.getDescrip_st()%>" class="campo" size="45" maxlength="15" readonly >
												<input name="codigo_st" type="hidden" value="<%=BPECF.getCodigo_st()%>" class="campo" size="60" maxlength="60" readonly ></td>
                    <td width="8%" class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/find.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_vArticulosEsquemas.jsp','',800,400);"></td>
                    <td class="fila-det-border">Cantidad: (*) </td>
                    <td class="fila-det-border">&nbsp;
                        <input name="cantidad" type="text" value="<%=BPECF.getCantidad()%>" class="campo" size="10" maxlength="10"  ></td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">Dep&oacute;sito (*) </td>
                    <td colspan="2" class="fila-det-border">&nbsp;
												<select name="codigo_dt" id="codigo_dt" class="campo"  >
														<option value="">Seleccionar</option>
														<% 
															Iterator iter = BPECF.getListDepositos().iterator();					
															while(iter.hasNext()){
																	String keyDep[] = (String[]) iter.next();
																	String sel = ""; 

																	if(BPECF.getCodigo_dt().equals(keyDep[0])) sel = "selected";
																	else sel = "";
																	
																		%>
															<option value="<%= keyDep[0] %>" <%= sel %>><%= keyDep[1] %></option>
																			<%
															}
																																													
														 %>
										  </select>										</td>
                    <td class="fila-det-border">Margen Error: (*) </td>
                    <td class="fila-det-border">&nbsp;
                    <input name="margen_error" type="text" value="<%=BPECF.getMargen_error()%>" class="campo" size="10" maxlength="10"  ></td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">Producci&oacute;n - Consumo: (*) </td>
                    <td colspan="2" class="fila-det-border">&nbsp;
                        <select name="entsal" id="entsal" class="campo">
                          <option value="">Seleccionar</option>
                           <option value="C" <%=BPECF.getEntsal().equalsIgnoreCase("C") ? "selected" : ""%> >Producto de Consumo</option>
                           <option value="P" <%=BPECF.getEntsal().equalsIgnoreCase("P") ? "selected" : ""%>>Producto Elaborado (Final) </option>													 
                        </select></td>
                    <td class="fila-det-border">Edita: (*) </td>
                    <td class="fila-det-border">&nbsp;
                      <select name="edita" id="edita" class="campo">
                        <option value="">Seleccionar</option>
                        <option value="S" <%=BPECF.getEdita().equalsIgnoreCase("S") ? "selected" : ""%> >Si</option>
                        <option value="N" <%=BPECF.getEdita().equalsIgnoreCase("N") ? "selected" : ""%>>No</option>
                      </select></td>
                  </tr>
                  <tr class="fila-det">
                    <td class="fila-det-border">Iimprime: (*) </td>
                    <td colspan="2" class="fila-det-border">&nbsp;
                        <select name="imprime" id="imprime" class="campo">
                          <option value="">Seleccionar</option>
                          <option value="S" <%=BPECF.getImprime().equalsIgnoreCase("S") ? "selected" : ""%> >Si</option>
                          <option value="N" <%=BPECF.getImprime().equalsIgnoreCase("N") ? "selected" : ""%>>No</option>
                        </select></td>
                    <td class="fila-det-border">Reutilizable ? </td>
                    <td class="fila-det-border">&nbsp;
                      <select name="reutiliza" id="reutiliza" class="campo">
                        <option value="">Seleccionar</option>
                        <option value="S" <%=BPECF.getReutiliza().equalsIgnoreCase("S") ? "selected" : ""%> >Si</option>
                        <option value="N" <%=BPECF.getReutiliza().equalsIgnoreCase("N") ? "selected" : ""%>>No</option>
                      </select></td>
                  </tr>
                  <tr class="fila-det">
                    <td height="31" class="fila-det-border">&nbsp;</td>
                    <td height="31" colspan="2" class="fila-det-border"><input name="agregar" type="submit" class="boton" id="validarDetalle" value="Actualizar Detalle" <%=BPECF.getHabilitado()%>></td>
                    <td height="31" class="fila-det-border">&nbsp;</td>
                    <td height="31" class="fila-det-border">&nbsp;</td>
                  </tr>
                  <tr class="fila-det">
                    <td height="20" colspan="5" class="fila-det-border"> * Para que los cambios tengan efecto es necesario presionar el boton &quot;Actualizar Detalle&quot; y luego &quot;Confirmar Esquema&quot;. </td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td colspan="3" class="fila-det-border">
								<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr class="text-globales">
                    <td class="fila-det-border">Renglon</td>
                    <td class="fila-det-border">C&oacute;d.</td>
                    <td class="fila-det-border">Descripci&oacute;n</td>
                    <td class="fila-det-border">Cant.</td>
                    <td class="fila-det-border">Final-Cons.</td>
                    <td class="fila-det-border">Tipo</td>
                    <td class="fila-det-border"><div align="center">Mod.</div></td>
                    <td class="fila-det-border"><div align="center">Elim.</div></td>
                  </tr>
									<% 
									 Hashtable htDetalleEsquema = (Hashtable) session.getAttribute("htDetalleEsquema");
									 if( htDetalleEsquema != null && ! htDetalleEsquema.isEmpty() ){
									     // 20111011 - EJV - Mantis 794 -->
										 // Enumeration en = htDetalleEsquema.keys();
										 Enumeration en = Common.getSetSorted(htDetalleEsquema.keySet());
										 // <--
										 while(en.hasMoreElements()){
										    String key = en.nextElement().toString();
										    String [] detalle =  (String []) htDetalleEsquema.get(key);

												//System.out.println("---------------");
												//for(int s=0; s<detalle.length;s++) System.out.println("Detalle " + s + ":" + detalle[s]);

											/*
											 * idesquema,renglon,tipo,codigo_st, descrip_st,cantidad,entsal,codigo_dt,margen_error,
											 * imprime,edita,formula,usuarioalt,usuarioact,fechaalt,fechaact
											 */												

											 %>									
                  <tr class="fila-det">
                    <td width="6%" class="fila-det-border">&nbsp;<%= detalle [1] %></td>
                    <td width="8%" class="fila-det-border">&nbsp;<%= detalle [3] %> </td>
                    <td width="56%" class="fila-det-border"><%= detalle [4] %></td>
                    <td width="5%" class="fila-det-border">&nbsp;<%= detalle [5] %></td>
                    <td width="10%" class="fila-det-border">&nbsp;<%= detalle [6] %></td>
                    <td width="5%" class="fila-det-border">&nbsp;<%= detalle [2] %></td>
                    <td width="5%" class="fila-det-border"><div align="center"><img src="../imagenes/default/btn_edit_norm.gif" width="22" height="21" style="cursor:pointer" onClick="modificarDetalle( '<%= detalle [1] %>', '<%= detalle [2] %>', '<%= detalle [3] %>', '<%= detalle [4] %>', '<%= detalle [5] %>', '<%= detalle [6] %>', '<%= detalle [7] %>', '<%= detalle [8] %>', '<%= detalle [9] %>', '<%= detalle [10] %>' , '<%= detalle [11] %>', '<%= detalle [12] %>')"></div></td>
                    <td width="5%" class="fila-det-border"><div align="center"><img src="../imagenes/default/btn_remove_norm.gif" width="21" height="21" style="cursor:pointer" onClick="eliminarDetalle('<%= key %>')"></div></td>
                  </tr>
									<%
									     }
										 }	
										 %>

                </table>
							 </td> 
              </tr>
              <tr class="fila-det">
                <td height="40" class="fila-det-border">&nbsp;</td>
                <td colspan="2" class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Confirmar Esquema" class="boton" <%=BPECF.getHabilitado()%>>               <input name="volver" type="submit" class="boton" id="volver" value="Volver">                </td>
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

