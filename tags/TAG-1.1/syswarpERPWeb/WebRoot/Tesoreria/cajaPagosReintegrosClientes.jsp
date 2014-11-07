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
    String titulo      = "PAGOS - REINTEGROS A CLIENTES";
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
        <jsp:useBean id = "BVCA" class = "ar.com.syswarp.web.ejb.BeanCajaPagosReintegrosClientes" scope = "page"/>
        <%-- EJECUTAR SETEO DE PROPIEDADES --%>

        <jsp:setProperty name = "BVCA" property = "*"/>

    <%
        BVCA.setResponse(response);
        BVCA.setRequest(request);
        BVCA.setSession(session);
        BVCA.setUsuarioalt(usuario);
        BVCA.setEjercicio( new BigDecimal( session.getAttribute("ejercicioActivo").toString() ));				
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
            function omitirCobrador()
                {
                document.frm.idcobrador.value = document.frm.cobrador.value = "";
                }

            function seleccionarCliente()
                {
                abrirVentana('../Clientes/lov_clientes.jsp', 'buscar', 700, 400);
                }

            function cambiarCliente()
                {
                if (confirm("Al cambiar de cliente, perderá toda la información cargada.\n Continúa de todas formas?"))
                    {
                    seleccionarCliente();
                    window.location = "cajaPagosReintegrosClientes.jsp";
                    }
                }
        </script>
            </head>

    <% 
        // titulos para las columnas
        tituCol[0] = "Cód.";
        tituCol[1] = "Cliente";
        tituCol[2] = "Cód. Cobrador";
        tituCol[3] = "Cobrador";
        java.util.List Vlov_Clientes = new java.util.ArrayList();
        Hashtable htComprobantesCobCliOK = (Hashtable)session.getAttribute("htComprobantesCobCliOK");
        Hashtable htMovimientosCancelarOK = (Hashtable)session.getAttribute("htMovimientosCancelarOK");
        Hashtable htIdentificaSalidaPagosOK = (Hashtable)session.getAttribute("htIdentificaSalidaPagosOK");
		// System.out.println("htComprobantesCobCliOK::::: " + htComprobantesCobCliOK);
    %>

        <body leftmargin = "0" topmargin = "0" marginwidth = "0" marginheight = "0">
            <div id = "overDiv" style = "position:absolute; visibility:hidden; z-index:1000;">
            </div>

            <div id = "popupcalendar" class = "text">
            </div>

            <form action = "cajaPagosReintegrosClientes.jsp" method = "POST" name = "frm">
                <table width = "100%" border = "0" cellspacing = "0" cellpadding = "0"
                       class = color-tabletrim>
                    <tr class = "text-globales">
                        <td width = "100%" height = "24" colspan = "10" background = "imagenes/dialogtop.gif">
                            <table width = "100%" border = "0" cellpadding = "0" cellspacing = "0">
                                <tr>
                                    <td width = "100%" height="39" class = "text-globales"><%= titulo %></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>

                <table width = "100%" border = "0" cellspacing = "0" cellpadding = "0">
                    <tr>
                      <td class = "fila-det-bold-rojo">
                            <jsp:getProperty name = "BVCA" property = "mensaje"/>  <% if(!BVCA.getNrointerno().equals("")) {%><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?nrointerno=<%= BVCA.getNrointerno() %>&totalAdelanto=<%= BVCA.getTotalAdelanto() %>&plantillaImpresionJRXML=modelo.reintegro', 'pagos', '750', '450');" style="cursor:pointer"><% } %></td>
                    </tr>
                </table>

                &nbsp;

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
                                        if (( htComprobantesCobCliOK == null || htComprobantesCobCliOK.isEmpty() ) &&  BVCA.getIdcliente() == null)
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
                                                                         value = "<%= str.esNulo(BVCA.getIdcliente() + "") %>" readonly></td>

                                    <td width = "91%"><span class = "fila-titulo-dos">
                                    <input name = "cliente"
                                           type = "text"
                                           class = "campo"
                                           id = "cliente"
                                           size = "80"
                                           value = "<%= BVCA.getCliente() %>" readonly> </span></td>
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
                                    <td width = "27%" class = "fila-titulo-dos">Comprobantes a Aplicar - Reintegrar                                    </td>

                                    <td width = "73%" class = "fila-titulo-dos">
                                        <!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_proveedorsMovCliAbm.jsp?proveedor=' + document.frm.idproveedor.value + '&proveedor=' + document.frm.proveedor.value, 'comprobantes', 700, 400)"-->
                                  <input name = "btncomprobantes"
                                               type = "button"
                                               class = "boton"
                                               id = "btncomprobantes"
                                               style = "cursor:pointer"
                                               onClick = "abrirVentana('lov_clientesMovCliCobReintegro.jsp?cliente=' + document.frm.idcliente.value + '&razon=' + document.frm.cliente.value, 'buscar', 800, 400)"
                                               value = ">>"></td>
                                </tr>

                                <tr>
                                    <td colspan = "2" class = "fila-titulo-dos">
                                        <table width = "100%" border = "0" align = "center" cellpadding = "0"
                                               cellspacing = "0">
                                            <tr>
                                                <td width = "25%" class = "fila-titulo-dos">N&deg; Interno
                                                </td>

                                                <td width = "25%" class = "fila-titulo-dos">Comprobante</td>

                                                <td width = "24%" class = "fila-titulo-dos">
                                                    <div align = "right">Importe Original
                                                    </div></td>

                                                <td width = "26%" class = "fila-titulo-dos">
                                                    <div align = "right">A Reintegrar
                                                    </div></td>
                                            </tr>

                                            <tr height = "3">
                                                <td colspan = "4" class = "text-globales" height = "3">
                                                </td>
                                            </tr>

                                        <%
										
										    // System.out.println("htComprobantesCobCliOK --> : " + htComprobantesCobCliOK);
										
                                            if (htComprobantesCobCliOK != null
                                                    && !htComprobantesCobCliOK.isEmpty())
                                                {
                                                Enumeration en = htComprobantesCobCliOK.keys();

                                                while (en.hasMoreElements())
                                                    {
                                                    String key           = (String)en.nextElement();
                                                    String [] comprobantes =
                                                                  (String [])htComprobantesCobCliOK.get(key);
																																	
																																	 for (int t = 0; t < comprobantes.length; t++)
                                                       
                                        %>

                                                    <tr class = "fila-det">
                                                        <td width = "25%" class = "fila-det-border">
                                                            <input  name="nrointerno_cob" id="nrointerno_cob" type="hidden" value="<%= comprobantes[0] %>" >
                                                      <%= comprobantes[0] %>&nbsp;</td>

                                                        <td width = "25%" class = "fila-det-border">
                                                            <%= Common.strZero(comprobantes[9], 4 ) + " - "  + Common.strZero(comprobantes[10], 8) %>&nbsp;</td>

                                                        <td width = "24%" class = "fila-det-border">
                                                            <div align = "right"><%= comprobantes[2] %>&nbsp;
                                                            </div></td>

                                                        <td width = "26%" class = "fila-det-border">
                                                            <div align = "right"><%= comprobantes[1] %>&nbsp;
                                                            </div></td>
                                                    </tr>

                                        <%
                                                    }
                                                }
                                        %>
                                        </table>
                                    </td>
                                </tr>
                            </table></td>
                    </tr>

                    <tr>
                        <td height="40">&nbsp;</td>
                    </tr>

                    <tr>
                        <td>
                            <table width = "100%" border = "0" align = "center" cellpadding = "0"
                                   cellspacing = "0">
                                <tr class = "text-dos" height = "3">
                                    <td height = "3" colspan = "2"></td>
                                </tr>

                                <tr class = "text-globales">
                                    <td width = "27%" class = "fila-titulo-dos">Salida</td> 

                                    <td width = "73%" class = "fila-titulo-dos">
                                        <!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_proveedorsMovCliAbm.jsp?proveedor=' + document.frm.idproveedor.value + '&proveedor=' + document.frm.proveedor.value, 'comprobantes', 700, 400)"-->
                                  <input name = "btndescuentos2"
                                               type = "button"
                                               class = "boton"
                                               id = "btndescuentos2"
                                               style = "cursor:pointer"
                                               onClick = "abrirVentana('lov_cajaIdentificadoresSalidaPagos.jsp?propio=N', 'descuentos', 800, 500)" 
                                               value = ">>"></td> 
                                </tr>

                                <tr>
                                    <td colspan = "2" class = "fila-titulo-dos">
                                        <table width = "100%" border = "0" align = "center" cellpadding = "0"
                                               cellspacing = "0">
                                            <tr>
                                                <td width = "10%" class = "fila-titulo-dos">Ide.</td>

                                                <td width = "9%" class = "fila-titulo-dos">CC1</td>

                                                <td width = "8%" class = "fila-titulo-dos">CC2</td>

                                                <td width = "11%" class = "fila-titulo-dos">N&uacute;mero</td>

                                                <td width = "37%" class = "fila-titulo-dos">Detalle</td>

                                                <td width = "13%" class = "fila-titulo-dos">Fecha</td>

                                                <td width = "12%" class = "fila-titulo-dos">
                                                    <div align = "right">Importe
                                                    </div></td>
                                            </tr>

                                            <tr height = "3">
                                                <td colspan = "9" class = "text-globales" height = "3">
                                                </td>
                                            </tr>

                                        <%
                                            if (htIdentificaSalidaPagosOK != null
                                                    && !htIdentificaSalidaPagosOK.isEmpty())
                                                {
                                                Enumeration en = htIdentificaSalidaPagosOK.keys();

                                                while (en.hasMoreElements())
                                                    {
                                                    String key                = (String)en.nextElement();
                                                    String [] identificadores = (String [])
                                                                                    htIdentificaSalidaPagosOK.get(key);
                                                    //for(int m=0;m<identificadores.length;m++)
                                                    //  System.out.println(" ~~ IDENTIFICADORES[" + m + "]: " + identificadores[m]);
                                        %>

                                                    <tr class = "fila-det">
                                                        <td width = "10%" height = "32"
                                                            class = "fila-det-border">
                                                            <%= identificadores[2] %> &nbsp;</td>

                                                        <td width = "9%"
                                                            class = "fila-det-border"><%= identificadores[20] %>&nbsp;
                                                        </td>

                                                        <td width = "8%"
                                                            class = "fila-det-border"><%= identificadores[21] %>&nbsp;</td>

                                                        <td width = "11%"
                                                            class = "fila-det-border"><%= identificadores[30] %>&nbsp;</td>

                                                        <td width = "37%"
                                                            class = "fila-det-border"><%= identificadores[29] %>&nbsp;</td>

                                                        <td width = "13%"
                                                            class = "fila-det-border"><%= identificadores[31] %>&nbsp;</td>

                                                        <td width = "12%" class = "fila-det-border">&nbsp;

                                                            <div align = "right"><%= identificadores[28] %>
                                                            </div></td>
                                                    </tr>

                                        <%
                                                    }
                                                }
                                        %>

                                        <%
                                            if (htMovimientosCancelarOK != null
                                                    && !htMovimientosCancelarOK.isEmpty())
                                                {
                                                Enumeration en = htMovimientosCancelarOK.keys();

                                                while (en.hasMoreElements())
                                                    {
                                                    String key                = (String)en.nextElement();
                                                    String [] identificadores = (String [])
                                                                                    htMovimientosCancelarOK.get(key);


                                                    //for (int t = 0; t < identificadores.length; t++)
                                                       // System.out.println("Elem " + t + ":"
                                                                               //+ identificadores[t]);
                                        %>

                                                            <tr class = "fila-det">
                                                                <td width = "10%" height = "32"
                                                                    class = "fila-det-border">
                                                                    <%= identificadores[2] %> &nbsp;</td>

                                                                <td width = "9%"
                                                                    class = "fila-det-border"><%= identificadores[20] %>&nbsp;
                                                                </td>

                                                                <td width = "8%"
                                                                    class = "fila-det-border"><%= identificadores[21] %>&nbsp;</td>

                                                                <td width = "11%"
                                                                    class = "fila-det-border"><%= identificadores[37] %>&nbsp;</td>

                                                                <td width = "37%"
                                                                    class = "fila-det-border"><%= identificadores[29] %>&nbsp;</td>

                                                                <td width = "13%"
                                                                    class = "fila-det-border"><%= identificadores[31] %>&nbsp;</td>

                                                                <td width = "12%"
                                                                    class = "fila-det-border">&nbsp;

                                                                    <div align = "right"><%= identificadores[28] %>
                                                                    </div></td>
                                                            </tr>

                                        <%
                                                    }
                                                }
                                        %>
                                        </table>
                                    </td>
                                </tr>
                            </table></td>
                    </tr>

                    <tr>
                        <td>
                            <table width = "100%" border = "0" align = "center" cellpadding = "0"
                                   cellspacing = "0">
                                <tr class = "text-dos" height = "3">
                                    <td height = "3"></td>
                                </tr>

                                <tr class = "text-globales">
                                    <td width = "54%" class = "fila-titulo-dos">Observaciones</td>
                                </tr>

                                <tr class = "fila-det">
                                    <td width = "54%" height = "29" class = "fila-det-border">
                                        <textarea name = "observaciones" cols = "80" rows = "2"
                                                  class = "campo"><%= BVCA.getObservaciones() %></textarea>
                                    </td>
                                </tr>
                            </table></td>
                    </tr>

                    <tr>
                        <td>
                            <table width = "100%" border = "0" align = "center" cellpadding = "0"
                                   cellspacing = "0">
                                <tr class = "text-dos">
                                    <td width = "91%">TOTALES</td>

                                    <td width = "9%">&nbsp;</td>
                                </tr>

                                <tr class = "fila-det">
                                    <td width = "91%" class = "fila-det-border">Total Comprobantes</td>

                                    <td width = "9%" class = "fila-det-border">
                                        <div align = "right"> <%= BVCA.getTotalComprobantes() %>
                                        </div></td>
                                </tr>

                                <tr class = "fila-det">
                                    <td class = "fila-det-border">Total Salida
                                    </td>

                                    <td class = "fila-det-border">
                                        <div align = "right"> <%= BVCA.getTotalSalida() %>
                                        </div></td>
                                </tr>

                                <tr class = "fila-det">
                                    <td class = "fila-det-border">Total Pago
                                    </td>

                                    <td class = "fila-det-border">
                                        <div align = "right"> <%= BVCA.getTotalPago() %>
                                        </div></td>
                                </tr>
                            </table></td>
                    </tr>

                    <tr>
                        <td height = "33"><span class = "fila-titulo-dos">
                        <input name = "confirmar" type = "submit" class = "boton" id = "confirmar"
                               style = "cursor:pointer" value = "Confirmar Reintegro"> 
                        </span></td> 
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