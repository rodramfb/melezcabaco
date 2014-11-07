<%
response.setHeader("Cache-Control", "no-cache");

response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
/* 
   Grilla para la entidad: vlov_Clientes
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
    String titulo      = "APLICACION DE MOVIMIENTOS DE CLIENTES";
    // variables de entorno
    String pathskin    = session.getAttribute("pathskin").toString();
    String pathscript  = session.getAttribute("pathscript").toString();
    // variables de paginacion
    int i              = 0;
    Iterator iterVlov_Clientes = null;
    int totCol         = 4; // cantidad de columnas
    String [] tituCol  = new String[totCol];
    String usuarioalt  = "";
    String usuarioact  = "";
    String fechaalt    = "";
    String fechaact    = "";
    String usuario     = session.getAttribute("usuario").toString();
%>

    <html>
        <jsp:useBean id = "BCAM" class = "ar.com.syswarp.web.ejb.BeanClientesAplicaMov" scope = "page"/>
        <%-- EJECUTAR SETEO DE PROPIEDADES --%>
        <jsp:setProperty name = "BCAM" property = "*"/>
    <%
        BCAM.setResponse(response);
        BCAM.setRequest(request);
        BCAM.setSession(session);
        BCAM.setUsuarioalt(usuario);
        BCAM.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
        BCAM.ejecutarValidacion();
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
				
            function seleccionarCliente()
                {
                abrirVentana('../Clientes/lov_clientes.jsp', 'buscar', 700, 400);
								//if(ventana && !ventana.closed) ventana.close();
                }

            function cambiarCliente()
                {
                if (confirm("Al cambiar de cliente, perderá toda la información cargada.\n Continúa de todas formas?"))
                    {
                    seleccionarCliente();
                    window.location = "clientesAplicaMov.jsp";
                    }
                }
			function aplicarDocumentos(){
			  document.frm.enviar.disabled = true;
              document.frm.enviar.value = 'Aplicando ...';			
			  document.frm.confirmar.value = 'aplicar';
			  setTimeout('document.frm.submit();', 2000);
			}
        </script>
            </head>

    <%
        // titulos para las columnas
        tituCol[0] = "Cód.";
        tituCol[1] = "Clientes";
        tituCol[2] = "Cód. Cobrador";
        tituCol[3] = "Cobrador";
		/*
        java.util.List Vlov_Clientes = new java.util.ArrayList();
        Hashtable htComprobantesProvOK = (Hashtable)session.getAttribute("htComprobantesProvOK");
        Hashtable htMovimientosCancelarOK = (Hashtable)session.getAttribute("htMovimientosCancelarOK");
        Hashtable htIdentificaSalidaPagosOK = (Hashtable)session.getAttribute("htIdentificaSalidaPagosOK");
		*/
    %> 

        <body leftmargin = "0" topmargin = "0" marginwidth = "0" marginheight = "0">
            <div id = "overDiv" style = "position:absolute; visibility:hidden; z-index:1000;">
            </div>

            <div id = "popupcalendar" class = "text">
            </div>

            <form action = "clientesAplicaMov.jsp" method = "POST" name = "frm">
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
                            <p><jsp:getProperty name = "BCAM" property = "mensaje"/> 
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
                                    <td width = "9%" class = "fila-titulo-dos">Cliente</td>

                                    <td width = "91%">
                                    <%
                                        if ( BCAM.getNrointerno_q_can().longValue() < 0 &&  BCAM.getNrointerno_canc().longValue() < 0)
                                            {
                                    %>

                                            <input name = "btnbuscacliente"
                                                   type = "button"
                                                   class = "boton"
                                                   id = "btnbuscacliente"
                                                   style = "cursor:pointer"
                                                   onClick = "seleccionarCliente();"
                                                   value = ">>">

                                    <%
                                            }

                                        else 
                                            {
                                    %>

                                            <input name = "btncambiacliente" type = "button"
                                                   class = "boton"             id = "btncambiacliente"
                                                   style = "cursor:pointer"    onClick = "cambiarCliente();"
                                                   value = "Cambiar">

                                    <%
                                            }
                                    %>
                                    </td>
                                </tr>

                                <tr>
                                    <td width = "9%"
                                        class = "fila-titulo-dos"><input name = "idcliente"
                                                                         type = "text"
                                                                         class = "campo" 
                                                                         id = "idcliente"
                                                                         size = "10"
                                                                         value = "<%= BCAM.getIdcliente().longValue() > 0 ?  BCAM.getIdcliente().toString() : ""  %>" readonly></td>

                                    <td width = "91%"><span class = "fila-titulo-dos">
                                    <input name = "cliente"
                                           type = "text"
                                           class = "campo"
                                           id = "cliente"
                                           size = "80"
                                           value = "<%= BCAM.getCliente() %>" readonly> </span></td>
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
                                  <input name = "btncomprobantes"
                                               type = "button"
                                               class = "boton"
                                               id = "btncomprobantes"
                                               style = "cursor:pointer"
                                               onClick = "ventana=abrirVentana('lov_clientesMovCliAplicar.jsp?idcliente=' + document.frm.idcliente.value + '&cliente=' + document.frm.cliente.value + '&tipo=aplicar&tipomovIN=3,4', 'buscar', 800, 400)"
                                               value = ">>"></td>
                                </tr>

                                <tr>
                                    <td colspan = "2" class = "fila-titulo-dos">
                                        <table width = "100%" border = "0" align = "center" cellpadding = "0"
                                               cellspacing = "1">
                                            <tr>
                                              <td width = "2%" class = "fila-titulo-dos">&nbsp;</td>
                                                <td width = "22%" class = "fila-titulo-dos">N&deg; Interno                                                </td>

                                                <td width = "43%" class = "fila-titulo-dos">Comprobante</td>

                                                <td width = "16%" class = "fila-titulo-dos">
                                              <div align = "right">Importe Original                                                    </div></td>

                                                <td width = "17%" class = "fila-titulo-dos">
                                              <div align = "right">Saldo                                                    </div></td>
                                            </tr>

                                            <tr height = "3">
                                                <td colspan = "5" class = "text-globales" height = "3">                                                </td>
                                            </tr>


                                                    <tr class = "fila-det">
                                                      <td width = "2%" class = "fila-det-border"><img src="../imagenes/default/gnome_tango/apps/kwin.png" title="Aplicaciones del movimiento." width="18" height="18"  onClick="ventana=abrirVentana('vClientesAplicacionesAbm.jsp?idcliente=<%=  BCAM.getIdcliente() %>&cliente=<%=BCAM.getCliente()%>&nrointerno=<%=  BCAM.getNrointerno_q_can() %>&comprobante=<%= BCAM.getComprob_q_can() %>', 'aplicaci', 700, 300)" style="cursor:pointer"></td>
                                                      <td width = "22%" class = "fila-det-border">
                                                            <%= BCAM.getNrointerno_q_can().longValue() > 0 ?  BCAM.getNrointerno_q_can().toString() : "" %>&nbsp;
                                                      <input name="nrointerno_q_can" type="hidden" id="nrointerno_q_can" value="<%= BCAM.getNrointerno_q_can() %>"></td>

                                                        <td width = "43%" class = "fila-det-border">
                                                            <%= BCAM.getComprob_q_can() %>
                                                            &nbsp;
                                                      <input name="comprob_q_can" type="hidden" id="comprob_q_can" value="<%= BCAM.getComprob_q_can() %>"></td>

                                                        <td width = "16%" class = "fila-det-border"><div align = "right">
                                                          <input name="importe_q_can" type="hidden" id="importe_q_can" value="<%= BCAM.getImporte_q_can() %>">
                                                          <%=  BCAM.getImporte_q_can().toString() %>&nbsp;
                                                      </div></td>

                                                        <td width = "17%" class = "fila-det-border"><div align = "right">
                                                          <input name="saldo_q_can" type="hidden" id="saldo_q_can" value="<%= BCAM.getSaldo_q_can() %>">
                                                      <%=  BCAM.getSaldo_q_can().toString() %>&nbsp;                                                            </div></td>
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
                          <td width = "73%" class = "fila-titulo-dos">
                          <input name = "btncomprobantes2"
                                               type = "button"
                                               class = "boton"
                                               id = "btncomprobantes2"
                                               style = "cursor:pointer"
                                               onclick = "ventana=abrirVentana('lov_clientesMovCliAplicar.jsp?idcliente=' + document.frm.idcliente.value + '&amp;cliente=' + document.frm.cliente.value  + '&tipo=cancelar&tipomovIN=1,2', 'buscar', 800, 400)"
                                               value = "&gt;&gt;" /></td>
                        </tr>
                        <tr>
                          <td colspan = "2" class = "fila-titulo-dos"><table width = "100%" border = "0" align = "center" cellpadding = "0"
                                               cellspacing = "1">
                              <tr>
                                <td width = "2%" class = "fila-titulo-dos">&nbsp;</td>
                                <td width = "22%" class = "fila-titulo-dos">N&deg; Interno </td>
                                <td width = "21%" class = "fila-titulo-dos">Comprobante</td>
                                <td width = "22%" class = "fila-titulo-dos">Fecha Vencimiento </td>
                                <td width = "16%" class = "fila-titulo-dos"><div align = "right">Importe Original </div></td>
                                <td width = "17%" class = "fila-titulo-dos"><div align = "right">Saldo </div></td>
                              </tr>
                              <tr height = "3">
                                <td colspan = "6" class = "text-globales" height = "3"></td>
                              </tr>

                              <tr class = "fila-det">
                                <td width = "2%" class = "fila-det-border"><img src="../imagenes/default/gnome_tango/apps/kwin.png" title="Aplicaciones del movimiento." width="18" height="18"  onClick="ventana=abrirVentana('vClientesAplicacionesAbm.jsp?idcliente=<%=  BCAM.getIdcliente() %>&cliente=<%=BCAM.getCliente()%>&nrointerno=<%=  BCAM.getNrointerno_canc() %>&comprobante=<%= BCAM.getComprob_canc() %>', 'aplicaci', 700, 300)" style="cursor:pointer"></td>
                                <td width = "22%" class = "fila-det-border"><%= BCAM.getNrointerno_canc().longValue() >= 1 || BCAM.getNrointerno_canc().longValue() < -1 ?  BCAM.getNrointerno_canc().toString() : ""%>&nbsp;
                                <input name="nrointerno_canc" type="hidden" id="nrointerno_canc" value="<%= BCAM.getNrointerno_canc() %>"></td>
                                <td width = "21%" class = "fila-det-border"><%= BCAM.getComprob_canc() %>&nbsp;
                                <input name="comprob_canc" type="hidden" id="comprob_canc" value="<%= BCAM.getComprob_canc() %>"></td>
                                <td width = "22%" class = "fila-det-border"><%=BCAM.getFvence_canc()%>
                                  &nbsp;
                                  <input name="fvence_canc" type="hidden" id="fvence_canc" value="<%=BCAM.getFvence_canc()%>"></td>
                                <td width = "16%" class = "fila-det-border"><div align = "right">
                                  <input name="importe_canc" type="hidden" id="importe_canc" value="<%= BCAM.getImporte_canc() %>">
                                <%=  BCAM.getImporte_canc() %>&nbsp; </div></td>
                                <td width = "17%" class = "fila-det-border"><div align = "right">
                                  <input name="saldo_canc" type="hidden" id="saldo_canc" value="<%= BCAM.getSaldo_canc() %>">
                                <%=  BCAM.getSaldo_canc().toString()  %>&nbsp; </div></td>
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
                          <td width = "75%" class = "fila-titulo-dos"><!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_clientesMovCliAbm.jsp?cliente=' + document.frm.idcliente.value + '&cliente=' + document.frm.cliente.value, 'comprobantes', 700, 400)"--></td>
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
                                  <input name="totalaplicado" type="text" id="totalaplicado" value="<%= BCAM.getTotalaplicado() %>" size="12" maxlength="12" class="campo" style="text-align:right">
                                   </div></td>
                              </tr>
                          </table></td>
                        </tr>
                      </table></td>
                    </tr>

                    <tr>
                      <td height = "33"><span class = "fila-titulo-dos">
                        <input name = "enviar" type = "button" class = "boton" id = "enviar"
                               style = "cursor:pointer" value = "Confirmar Aplicaci&oacute;n" <%= BCAM.isDisabled() ? "disabled" : "" %> onClick="aplicarDocumentos();">  
                        <input name = "reiniciar" type = "button" class = "boton" id = "reiniciar"
                               style = "cursor:pointer" value = "Limpiar datos" onClick="document.location = 'clientesAplicaMov.jsp'"></td>  
                    </tr>
                </table>

                <p>&nbsp; </p>

                <input name = "accion" value = "" type = "hidden">
				<input name = "confirmar" value = "" type = "hidden">
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
