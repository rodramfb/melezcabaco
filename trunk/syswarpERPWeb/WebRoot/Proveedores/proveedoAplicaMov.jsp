<%
response.setHeader("Cache-Control", "no-cache");

response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
/* 
   Grilla para la entidad: vlov_Proveedors
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Dec 12 14:43:42 GMT-03:00 2006 
   Observaciones: 
      .


*/
%>

<%@ page import = "javax.servlet.http.*"%>
<%@ page import = "java.util.*"%>
<%@ page import = "java.math.*"%>
<%@ page import = "ar.com.syswarp.api.*"%>
<%@ include file = "session.jspf"%>

<%
try
    {
    // captura de variables comunes
    Strings str        = new Strings();
    String color_fondo = "";
    String titulo      = "APLICACION DE MOVIMIENTOS DE PROVEEDORES";
    // variables de entorno
    String pathskin    = session.getAttribute("pathskin").toString();
    String pathscript  = session.getAttribute("pathscript").toString();
    // variables de paginacion
    int i              = 0;
    Iterator iterVlov_Proveedors = null;
    int totCol         = 4; // cantidad de columnas
    String [] tituCol  = new String[totCol];
    String usuarioalt  = "";
    String usuarioact  = "";
    String fechaalt    = "";
    String fechaact    = "";
    String usuario     = session.getAttribute("usuario").toString();
%>

    <html>
        <jsp:useBean id = "BVCA" class = "ar.com.syswarp.web.ejb.BeanProveedoAplicaMov" scope = "page"/>
        <%-- EJECUTAR SETEO DE PROPIEDADES --%>

        <jsp:setProperty name = "BVCA" property = "*"/>

    <%
        BVCA.setResponse(response);
        BVCA.setRequest(request);
        BVCA.setSession(session);
        BVCA.setUsuarioalt(usuario);
        BVCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
        BVCA.ejecutarValidacion();
    %>

            <head>
                <title><%= titulo %></title>

                <meta http-equiv = "Content-Type" content = "text/html; charset=iso-8859-1">
                <link rel = "stylesheet" href = "<%= pathskin %>">
                
                <link rel = "stylesheet" type = "text/css" href = "<%= pathscript %>/calendar/calendar.css">
                <script language = "JavaScript" src = "<%= pathskin %>overlib.js">
                </script>

        <script language = "JavaScript" src = "<%= pathscript %>/forms.js">
        </script>

        <script language = "JavaScript" src = "<%= pathscript %>/calendar/calendarcode.js">
        </script>

        <script>
				    var ventana = null;
				
            function omitirCobrador()
                {
                document.frm.idcobrador.value = document.frm.cobrador.value = "";
                }

            function seleccionarProveedor()
                {
                abrirVentana('../Proveedores/lov_proveedores.jsp', 'buscar', 700, 400);
								//if(ventana && !ventana.closed) ventana.close();
                }

            function cambiarProveedor()
                {
                if (confirm("Al cambiar de proveedor, perderá toda la información cargada.\n Continúa de todas formas?"))
                    {
                    seleccionarProveedor();
                    window.location = "proveedoAplicaMov.jsp";
                    }
                }
        </script>
            </head>

    <%
        // titulos para las columnas
        tituCol[0] = "Cód.";
        tituCol[1] = "Proveedor";
        tituCol[2] = "Cód. Cobrador";
        tituCol[3] = "Cobrador";
        java.util.List Vlov_Proveedors = new java.util.ArrayList();
        Hashtable htComprobantesProvOK = (Hashtable)session.getAttribute("htComprobantesProvOK");
        Hashtable htMovimientosCancelarOK = (Hashtable)session.getAttribute("htMovimientosCancelarOK");
        Hashtable htIdentificaSalidaPagosOK = (Hashtable)session.getAttribute("htIdentificaSalidaPagosOK");
    %> 

        <body leftmargin = "0" topmargin = "0" marginwidth = "0" marginheight = "0">
            <div id = "overDiv" style = "position:absolute; visibility:hidden; z-index:1000;">
            </div>

            <div id = "popupcalendar" class = "text">
            </div>

            <form action = "proveedoAplicaMov.jsp" method = "POST" name = "frm">
                <table width = "100%" border = "0" cellspacing = "0" cellpadding = "0"
                       class = color-tabletrim>
                    <tr class = "text-globales">
                        <td width = "100%" height = "44" colspan = "10" background = "imagenes/dialogtop.gif">
                            <table width = "98%" border = "0" cellpadding = "0" cellspacing = "0" align="center">
                                <tr>
                                    <td width = "100%" height="33" class = "text-globales"><%= titulo %></td>
                                </tr>
                            </table>
                      </td>
                    </tr>
                </table>

                <table width = "98%" border = "0" cellspacing = "0" cellpadding = "0" align = "center" height="34">
                    <tr>
                        <td height="30" class = "fila-det-bold-rojo">
                            <p><jsp:getProperty name = "BVCA" property = "mensaje"/> 
											</td> 
                    </tr>
              </table>
              

                <table width = "98%" border = "1" align = "center" cellpadding = "0" cellspacing = "0">
                    <tr>
                        <td>
                            <table width = "100%" border = "0" align = "center" cellpadding = "0"
                                   cellspacing = "0">
                                <tr class = "text-dos" height = "3">
                                    <td height = "3" colspan = "2"></td>
                                </tr>

                                <tr class = "text-globales">
                                    <td width = "9%" class = "fila-titulo-dos">Proveedor</td>

                                    <td width = "91%">
                                    <%
                                        if ( BVCA.getNrointerno_q_can().longValue() < 0 &&  BVCA.getNrointerno_canc().longValue() < 0)
                                            {
                                    %>

                                            <input name = "btnbuscaproveedor"
                                                   type = "button"
                                                   class = "boton"
                                                   id = "btnbuscaproveedor"
                                                   style = "cursor:pointer"
                                                   onClick = "seleccionarProveedor();"
                                                   value = ">>">

                                    <%
                                            }

                                        else 
                                            {
                                    %>

                                            <input name = "btncambiaproveedor" type = "button"
                                                   class = "boton"             id = "btncambiaproveedor"
                                                   style = "cursor:pointer"    onClick = "cambiarProveedor();"
                                                   value = "Cambiar">

                                    <%
                                            }
                                    %>
                                    </td>
                                </tr>

                                <tr>
                                    <td width = "9%"
                                        class = "fila-titulo-dos"><input name = "idproveedor"
                                                                         type = "text"
                                                                         class = "campo" 
                                                                         id = "idproveedor"
                                                                         size = "10"
                                                                         value = "<%= BVCA.getIdproveedor().longValue() > 0 ?  BVCA.getIdproveedor().toString() : ""  %>"></td>

                                    <td width = "91%"><span class = "fila-titulo-dos">
                                    <input name = "proveedor"
                                           type = "text"
                                           class = "campo"
                                           id = "proveedor"
                                           size = "80"
                                           value = "<%= BVCA.getProveedor() %>"> </span></td>
                                </tr>
                            </table></td>
                    </tr>

                    <tr>
                        <td>&nbsp;</td>
                    </tr>

                    <tr>
                        <td>
                            <table width = "100%" border = "0" align = "center" cellpadding = "0"
                                   cellspacing = "0">
                                <tr class = "text-dos" height = "3">
                                    <td height = "3" colspan = "2"></td>
                                </tr>

                                <tr class = "text-globales">
                                    <td width = "27%" class = "fila-titulo-dos">Movimientos a Aplicar                                    </td>

                                    <td width = "73%" class = "fila-titulo-dos">
                                        <!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_proveedorsMovCliAbm.jsp?proveedor=' + document.frm.idproveedor.value + '&proveedor=' + document.frm.proveedor.value, 'comprobantes', 700, 400)"-->
                                  <input name = "btncomprobantes"
                                               type = "button"
                                               class = "boton"
                                               id = "btncomprobantes"
                                               style = "cursor:pointer"
                                               onClick = "ventana=abrirVentana('lov_proveedoMovProvAplicar.jsp?idproveedor=' + document.frm.idproveedor.value + '&proveedor=' + document.frm.proveedor.value + '&tipo=aplicar&tipomovIN=3,4', 'buscar', 800, 400)"
                                               value = ">>"></td>
                                </tr>

                                <tr>
                                    <td colspan = "2" class = "fila-titulo-dos">
                                        <table width = "100%" border = "0" align = "center" cellpadding = "0"
                                               cellspacing = "0">
                                            <tr>
                                                <td width = "14%" class = "fila-titulo-dos">N&deg; Interno                                                </td>

                                                <td width = "51%" class = "fila-titulo-dos">Comprobante</td>

                                                <td width = "18%" class = "fila-titulo-dos">
                                              <div align = "right">Importe Original                                                    </div></td>

                                                <td width = "17%" class = "fila-titulo-dos">
                                              <div align = "right">Saldo                                                    </div></td>
                                            </tr>

                                            <tr height = "3">
                                                <td colspan = "4" class = "text-globales" height = "3">                                                </td>
                                            </tr>


                                                    <tr class = "fila-det">
                                                      <td width = "14%" class = "fila-det-border">
                                                            <%= BVCA.getNrointerno_q_can().longValue() > 0 ?  BVCA.getNrointerno_q_can().toString() : "" %>&nbsp;
                                                      <input name="nrointerno_q_can" type="hidden" id="nrointerno_q_can" value="<%= BVCA.getNrointerno_q_can() %>"></td>

                                                        <td width = "51%" class = "fila-det-border">
                                                            <%= BVCA.getComprob_q_can() %>
                                                            &nbsp;
                                                      <input name="comprob_q_can" type="hidden" id="comprob_q_can" value="<%= BVCA.getComprob_q_can() %>"></td>

                                                        <td width = "18%" class = "fila-det-border"><div align = "right">
                                                          <input name="importe_q_can" type="hidden" id="importe_q_can" value="<%= BVCA.getImporte_q_can() %>">
                                                          <%= BVCA.getImporte_q_can().longValue() > 0 ?  BVCA.getImporte_q_can().toString() : "" %>&nbsp;
                                                      </div></td>

                                                        <td width = "17%" class = "fila-det-border"><div align = "right">
                                                          <input name="saldo_q_can" type="hidden" id="saldo_q_can" value="<%= BVCA.getSaldo_q_can() %>">
                                                      <%= BVCA.getSaldo_q_can().longValue() > 0 ?  BVCA.getSaldo_q_can().toString() : ""%>&nbsp;                                                            </div></td>
                                                    </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table></td>
                    </tr>

                    <tr>
                        <td ><p>&nbsp;</p></td>
                    </tr>

                    <tr>
                      <td><table width = "100%" border = "0" align = "center" cellpadding = "0"
                                   cellspacing = "0">
                        <tr class = "text-dos" height = "3">
                          <td height = "3" colspan = "2"></td>
                        </tr>
                        <tr class = "text-globales">
                          <td width = "27%" class = "fila-titulo-dos">Comprobantes a Cancelar </td>
                          <td width = "73%" class = "fila-titulo-dos"><!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_proveedorsMovCliAbm.jsp?proveedor=' + document.frm.idproveedor.value + '&proveedor=' + document.frm.proveedor.value, 'comprobantes', 700, 400)"-->
                          <input name = "btncomprobantes2"
                                               type = "button"
                                               class = "boton"
                                               id = "btncomprobantes2"
                                               style = "cursor:pointer"
                                               onclick = "ventana=abrirVentana('lov_proveedoMovProvAplicar.jsp?idproveedor=' + document.frm.idproveedor.value + '&amp;proveedor=' + document.frm.proveedor.value  + '&tipo=cancelar&tipomovIN=1,2', 'buscar', 800, 400)"
                                               value = "&gt;&gt;" /></td>
                        </tr>
                        <tr>
                          <td colspan = "2" class = "fila-titulo-dos"><table width = "100%" border = "0" align = "center" cellpadding = "0"
                                               cellspacing = "0">
                              <tr>
                                <td width = "14%" class = "fila-titulo-dos">N&deg; Interno </td>
                                <td width = "25%" class = "fila-titulo-dos">Comprobante</td>
                                <td width = "26%" class = "fila-titulo-dos">Fecha Vencimiento </td>
                                <td width = "18%" class = "fila-titulo-dos"><div align = "right">Importe Original </div></td>
                                <td width = "17%" class = "fila-titulo-dos"><div align = "right">Saldo </div></td>
                              </tr>
                              <tr height = "3">
                                <td colspan = "5" class = "text-globales" height = "3"></td>
                              </tr>

                              <tr class = "fila-det">
                                <td width = "14%" class = "fila-det-border"><%= BVCA.getNrointerno_canc().longValue() > 0 ?  BVCA.getNrointerno_canc().toString() : ""%>&nbsp;
                                <input name="nrointerno_canc" type="hidden" id="nrointerno_canc" value="<%= BVCA.getNrointerno_canc() %>"></td>
                                <td width = "25%" class = "fila-det-border"><%= BVCA.getComprob_canc() %>&nbsp;
                                <input name="comprob_canc" type="hidden" id="comprob_canc" value="<%= BVCA.getComprob_canc() %>"></td>
                                <td width = "26%" class = "fila-det-border"><%=BVCA.getFvence_canc()%>
                                  &nbsp;
                                  <input name="fvence_canc" type="hidden" id="fvence_canc" value="<%=BVCA.getFvence_canc()%>"></td>
                                <td width = "18%" class = "fila-det-border"><div align = "right">
                                  <input name="importe_canc" type="hidden" id="importe_canc" value="<%= BVCA.getImporte_canc() %>">
                                <%= BVCA.getImporte_canc().longValue() > 0 ?  BVCA.getImporte_canc().toString() : ""%>&nbsp; </div></td>
                                <td width = "17%" class = "fila-det-border"><div align = "right">
                                  <input name="saldo_canc" type="hidden" id="saldo_canc" value="<%= BVCA.getSaldo_canc() %>">
                                <%= BVCA.getSaldo_canc().longValue() > 0 ?  BVCA.getSaldo_canc().toString() : "" %>&nbsp; </div></td>
                              </tr>

                          </table></td>
                        </tr>
                      </table>
                      </td>
                    </tr>

                    <tr>
                      <td><p>&nbsp;</p>
                      </td>
                    </tr>

                    <tr>
                      <td><table width = "100%" border = "0" align = "center" cellpadding = "0"
                                   cellspacing = "0">
                        <tr class = "text-dos" height = "3">
                          <td height = "3" colspan = "2"></td>
                        </tr>
                        <tr class = "text-globales">
                          <td width = "25%" class = "fila-titulo-dos">Importe que aplica </td>
                          <td width = "75%" class = "fila-titulo-dos"><!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_proveedorsMovCliAbm.jsp?proveedor=' + document.frm.idproveedor.value + '&proveedor=' + document.frm.proveedor.value, 'comprobantes', 700, 400)"--></td>
                        </tr>
                        <tr>
                          <td colspan = "2" class = "fila-titulo-dos"><table width = "100%" border = "0" align = "center" cellpadding = "0"
                                               cellspacing = "0">
                              <tr>
                                <td width = "25%" class = "fila-titulo-dos">&nbsp;</td>
                                <td width = "25%" class = "fila-titulo-dos">&nbsp;</td>
                                <td width = "24%" class = "fila-titulo-dos"><div align = "right"></div></td>
                                <td width = "26%" class = "fila-titulo-dos"><div align = "right">Importe</div></td>
                              </tr>
                              <tr height = "3">
                                <td colspan = "4" class = "text-globales" height = "3"></td>
                              </tr>
                              <tr class = "fila-det">
                                <td width = "25%" class = "fila-det-border">&nbsp;</td>
                                <td width = "25%" class = "fila-det-border">&nbsp;</td>
                                <td width = "24%" class = "fila-det-border"><div align = "right">&nbsp; </div></td>
                                <td width = "26%" class = "fila-det-border"><div align = "right">
                                  <input name="totalaplicado" type="text" id="totalaplicado" value="<%= BVCA.getTotalaplicado() %>" size="12" maxlength="12" class="campo" style="text-align:right">
                                   </div></td>
                              </tr>
                          </table></td>
                        </tr>
                      </table></td>
                    </tr>

                    <tr>
                      <td height = "33"><span class = "fila-titulo-dos">
                        <input name = "confirmar" type = "submit" class = "boton" id = "confirmar"
                               style = "cursor:pointer" value = "Confirmar Aplicaci&oacute;n" <%= BVCA.isDisabled() ? "disabled" : "" %>>  
                        <input name = "reiniciar" type = "button" class = "boton" id = "reiniciar"
                               style = "cursor:pointer" value = "Limpiar datos" onClick="document.location = 'proveedoAplicaMov.jsp'"></td>  
                    </tr>
                </table>

                <p>&nbsp; </p>

                <input name = "accion" value = "" type = "hidden">
                <input name = "primerCarga" value = "false" type = "hidden">
            </form>
        </body>
    </html>

<%
    }
catch (Exception ex)
    {
    java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
    java.io.PrintWriter pw = new java.io.PrintWriter(cw, true);
    ex.printStackTrace(pw);
    System.out.println("ERROR (" + pagina + ") : " + ex);
    }
%>
