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
    String titulo      = "PAGOS - EGRESOS DIRECTOS" ;
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
        <jsp:useBean id = "BVCA" class = "ar.com.syswarp.web.ejb.BeanCajaPagosEgresosDirectosAbm" scope = "page"/>
        <%-- EJECUTAR SETEO DE PROPIEDADES --%>

        <jsp:setProperty name = "BVCA" property = "*"/>

    <%
		    BigDecimal idempresa = new BigDecimal( session.getAttribute("empresa").toString() );
        BVCA.setResponse(response);
        BVCA.setRequest(request);
        BVCA.setSession(session);
        BVCA.setUsuarioalt(usuario);
        BVCA.setIdempresa( idempresa );
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

            function seleccionarProveedor()
                {
                abrirVentana('../Proveedores/lov_proveedores.jsp', 'buscar', 700, 400);
                }

            function cambiarProveedor()
                {
                if (confirm("Al cambiar de proveedor, perderá toda la información cargada.\n Continúa de todas formas?"))
                    {
                    seleccionarProveedor();
                    window.location = "CajaPagosAbm.jsp";
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
        //Hashtable htComprobantesProvOK = (Hashtable)session.getAttribute("htComprobantesProvOK");
        Hashtable htMovimientosCancelarOK = (Hashtable)session.getAttribute("htMovimientosCancelarOK");
        Hashtable htIdentificaSalidaPagosOK = (Hashtable)session.getAttribute("htIdentificaSalidaPagosOK");
		    Hashtable htIdentificaEntradaPagosDirectOK = (Hashtable)session.getAttribute("htIdentificaEntradaPagosDirectOK");
        Hashtable htMovimientosEntradaCancelarOK = (Hashtable)session.getAttribute("htMovimientosEntradaCancelarOK"); 
				
    %>

        <body leftmargin = "0" topmargin = "0" marginwidth = "0" marginheight = "0">
            <div id = "overDiv" style = "position:absolute; visibility:hidden; z-index:1000;">
            </div>

            <div id = "popupcalendar" class = "text">
            </div>

            <form action = "cajaPagosEgresosDirectos.jsp" method = "POST" name = "frm">
                <table width = "100%" border = "0" cellspacing = "0" cellpadding = "0"
                       class = color-tabletrim>
                    <tr class = "text-globales">
                        <td width = "100%" height = "24" colspan = "10" background = "imagenes/dialogtop.gif">
                            <table width = "100%" border = "0" cellpadding = "0" cellspacing = "0">
                                <tr>
                                    <td width = "100%" class = "text-globales"><%= titulo %></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>

                <table width = "98%" border = "0" align="center" cellpadding = "0" cellspacing = "0">
                    <tr>
                        <td class = "fila-det-bold-rojo">
                            <jsp:getProperty name = "BVCA" property = "mensaje"/>
														<%
														  if( !BVCA.getNropago().equals("") ){ 
														    String plantillaImpresionJRXML = "pagos_egresos_directos_frame";
  													    String link = "../reportes/jasper/generaPDF.jsp?comprob_mt=" + BVCA.getNropago() + "&plantillaImpresionJRXML=" + plantillaImpresionJRXML + "&idempresa=" + idempresa ; %>
														    <img src="../imagenes/default/gnome_tango/apps/pdf.jpg" title="Imprimir orden de pago." style="cursor:pointer" onClick="abrirVentana('<%= link %>','ORDENPAGO',800, 600)">
														<%} %> 
												</td>
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
                                    <td width = "22%" class = "fila-titulo-dos">Salida</td>

                                    <td width = "78%" class = "fila-titulo-dos">
                                        <!--img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" style="cursor:pointer" onClick="abrirVentana('lov_proveedorsMovCliAbm.jsp?proveedor=' + document.frm.idproveedor.value + '&proveedor=' + document.frm.proveedor.value, 'comprobantes', 700, 400)"-->
                                        <input name = "btndescuentos2"
                                               type = "button"
                                               class = "boton"
                                               id = "btndescuentos2"
                                               style = "cursor:pointer"
                                               onClick = "abrirVentana('lov_cajaIdentificadoresSalidaPagos.jsp?propio=N', 'descuentos', 800, 600)"
                                               value = ">>"></td>
                                </tr>

                                <tr>
                                    <td colspan = "2" class = "fila-titulo-dos">
                                        <table width = "100%" border = "1" align = "center" cellpadding = "0"
                                               cellspacing = "0">
                                            <tr>
                                                <td width = "8%" class = "fila-titulo-dos">Ide.</td>


                                                <td width = "4%" class = "fila-titulo-dos">CC1</td>

                                                <td width = "4%" class = "fila-titulo-dos">CC2</td>

                                                <td width = "7%" class = "fila-titulo-dos">N&uacute;mero</td>

                                                <td width = "29%" class = "fila-titulo-dos">Detalle</td>

                                                <td width = "38%" class = "fila-titulo-dos">Cuenta</td>
                                                <td width = "4%" class = "fila-titulo-dos">Fecha</td>

                                                <td width = "6%" class = "fila-titulo-dos">
                                              <div align = "right">Importe                                              </div></td>
                                            </tr>

                                            <tr height = "3">
                                                <td colspan = "11" class = "text-globales" height = "3">                                                </td>
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
																										String nro_cuenta = (identificadores[4] + "-" + identificadores[37]);		
                                                    
                                                    // for(int m=0;m<identificadores.length;m++)
                                                    //   System.out.println(" ~~ IDENTIFICADORES[" + m + "]: " + identificadores[m]);

  
														
                                        %>

                                                    <tr class = "fila-det">
                                                        <td width = "8%" height = "32"
                                                            class = "fila-det-border">
                                                            <%= identificadores[2] %>-<%= identificadores[1]%></td>

                                                        <td width = "4%"
                                                            class = "fila-det-border"><%= identificadores[20] %>&nbsp;                                                      </td>

                                                      <td width = "4%"
                                                            class = "fila-det-border"><%= identificadores[21] %>&nbsp;</td>

                                                      <td width = "7%"
                                                            class = "fila-det-border"><%= identificadores[30] %>&nbsp;</td>

                                                      <td width = "29%"
                                                            class = "fila-det-border"><%= identificadores[31] %>&nbsp;</td>

                                                      <td width = "38%"
                                                            class = "fila-det-border"><%= nro_cuenta.length() > 50 ? nro_cuenta.substring(0, 50  ) : nro_cuenta  %></td>
                                                        <td width = "4%"
                                                            class = "fila-det-border">&nbsp;</td>

                                                        <td width = "6%" class = "fila-det-border">

                                                      <div align = "right">&nbsp;<%= identificadores[28] %>                                                      </div></td>
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

                                                    //for(int m=0;m<identificadores.length;m++)
                                                    //  System.out.println(" ~. IDENTIFICADORES[" + m + "]: " + identificadores[m]);

                                        %>

                                                            <tr class = "fila-det">
                                                                <td width = "8%" height = "32"
                                                                    class = "fila-det-border">
                                                                    <%= identificadores[2] %>-<%= identificadores[1] %></td>


                                                                <td width = "4%"
                                                                    class = "fila-det-border"><%= identificadores[20] %>&nbsp;                                                              </td>

                                                              <td width = "4%"
                                                                    class = "fila-det-border"><%= identificadores[21] %>&nbsp;</td>

                                                              <td width = "7%"
                                                                    class = "fila-det-border"><%= identificadores[30] %>&nbsp;</td>

                                                              <td width = "29%"
                                                                    class = "fila-det-border"><%= identificadores[29] %>&nbsp;</td> 

                                                              <td width = "38%"
                                                                    class = "fila-det-border">&nbsp;</td>
                                                                <td width = "4%"
                                                                    class = "fila-det-border">&nbsp;</td>

                                                                <td width = "6%"
                                                                    class = "fila-det-border">

                                                              <div align = "right">&nbsp;<%= identificadores[28] %>                                                              </div></td>
                                                            </tr>

                                        <%
                                                    }
                                                }
                                        %>
                                        </table>
                                    </td>
                                </tr>
                            </table>
											</td>
                    </tr>



<!---->


									  <tr>
                        <td>
                            <table width = "100%" border = "0" align = "center" cellpadding = "0"
                                   cellspacing = "0">
                                <tr class = "text-dos" height = "3">
                                    <td height = "3" colspan = "2"></td>
                                </tr>

                                <tr class = "text-globales">
                                    <td width = "22%" class = "fila-titulo-dos">Entrada</td>

                                    <td width = "78%" class = "fila-titulo-dos">
                                        <input name = "btndescuentos2"
                                               type = "button"
                                               class = "boton"
                                               id = "btndescuentos2"
                                               style = "cursor:pointer"
                                               onClick = "abrirVentana('lov_cajaIdentifPagosDirectEntrada.jsp?propio=W', 'entrada', 800, 400)"
                                               value = ">>"></td>
                                </tr>

                                <tr>
                                    <td colspan = "2" class = "fila-titulo-dos">
                                        <table width = "100%" border = "1" align = "center" cellpadding = "0"
                                               cellspacing = "0">
                                            <tr>
                                                <td width = "8%" class = "fila-titulo-dos">Ide.</td>

                                                <td width = "4%" class = "fila-titulo-dos">CC1</td>

                                                <td width = "4%" class = "fila-titulo-dos">CC2</td>

                                                <td width = "7%" class = "fila-titulo-dos">N&uacute;mero</td>

                                                <td width = "29%" class = "fila-titulo-dos">Detalle</td>

                                                <td width = "38%" class = "fila-titulo-dos">Cuenta</td>
                                                <td width = "4%" class = "fila-titulo-dos">Fecha</td>

                                                <td width = "6%" class = "fila-titulo-dos">
                                              <div align = "right">Importe                                              </div></td>
                                            </tr>

                                            <tr height = "3">
                                                <td colspan = "10" class = "text-globales" height = "3">                                                </td>
                                            </tr>

                                        <%       
                                            if (htIdentificaEntradaPagosDirectOK != null 
                                                    && !htIdentificaEntradaPagosDirectOK.isEmpty())
                                                {
                                                Enumeration en = htIdentificaEntradaPagosDirectOK.keys();

                                                while (en.hasMoreElements())
                                                    {
                                                    String key                = (String)en.nextElement();
                                                    String [] identificadores = (String [])
                                                                                    htIdentificaEntradaPagosDirectOK.get(key);
																										String nro_cuenta = (identificadores[4] + "-" + identificadores[37]);		
                                                    //for(int m=0;m<identificadores.length;m++)
                                                    //  System.out.println(" ~~ IDENTIFICADORES[" + m + "]: " + identificadores[m]);														
                                        %>

                                                    <tr class = "fila-det">
                                                        <td width = "8%" height = "32"
                                                            class = "fila-det-border">
                                                            <%= identificadores[2] %>-<%= identificadores[1] %></td>

                                                        <td width = "4%"
                                                            class = "fila-det-border"><%= identificadores[20] %>&nbsp;                                                      </td>

                                                      <td width = "4%"
                                                            class = "fila-det-border"><%= identificadores[21] %>&nbsp;</td>

                                                      <td width = "7%"
                                                            class = "fila-det-border"><%= identificadores[30] %>&nbsp;</td>

                                                        <td width = "29%"
                                                            class = "fila-det-border"><%= identificadores[31] %>&nbsp;</td>

                                                        <td width = "38%"
                                                            class = "fila-det-border"><%= nro_cuenta.length() > 50 ? nro_cuenta.substring(0, 50  ) : nro_cuenta  %></td>
                                                        <td width = "4%"
                                                            class = "fila-det-border">&nbsp;</td>

                                                        <td width = "6%" class = "fila-det-border">

                                                      <div align = "right">&nbsp;<%= identificadores[28] %>                                                      </div></td>
                                                    </tr>

                                        <%
                                                    }
                                                }
                                        %>

                                        <%
                                            if (htMovimientosEntradaCancelarOK != null
                                                    && !htMovimientosEntradaCancelarOK.isEmpty())
                                                {
                                                Enumeration en = htMovimientosEntradaCancelarOK.keys();

                                                while (en.hasMoreElements())
                                                    {
                                                    String key                = (String)en.nextElement();
                                                    String [] identificadores = (String [])
                                                                                    htMovimientosEntradaCancelarOK.get(key);

                                                    //for(int m=0;m<identificadores.length;m++)
                                                    //  System.out.println(" ~{ IDENTIFICADORES[" + m + "]: " + identificadores[m]);														

                                        %>

                                                            <tr class = "fila-det">
                                                                <td width = "8%" height = "32"
                                                                    class = "fila-det-border">
                                                                    <%= identificadores[2] %>-<%= identificadores[1] %></td>

                                                                <td width = "4%"
                                                                    class = "fila-det-border"><%= identificadores[20] %>&nbsp;                                                              </td>

                                                              <td width = "4%"
                                                                    class = "fila-det-border"><%= identificadores[21] %>&nbsp;</td>

                                                              <td width = "7%"
                                                                    class = "fila-det-border"><%= identificadores[30] %>&nbsp;</td>

                                                                <td width = "29%"
                                                                    class = "fila-det-border"><%= identificadores[29] %>&nbsp;</td>

                                                                <td width = "38%"
                                                                    class = "fila-det-border">&nbsp;</td>
                                                                <td width = "4%"
                                                                    class = "fila-det-border">&nbsp;</td>

                                                                <td width = "6%"
                                                                    class = "fila-det-border">

                                                              <div align = "right">&nbsp;<%= identificadores[28] %>                                                              </div></td>
                                                            </tr>

                                        <%
                                                    }
                                                }
                                        %>
                                        </table>
                                    </td>
                                </tr>
                            </table>
											</td>
                    </tr>



<!---->



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
                                    <td width = "70%">TOTALES</td>

                                    <td width = "30%">&nbsp;</td>
                                </tr>

                                <tr class = "fila-det">
                                    <td class = "fila-det-border">Total Salida                                    </td>

                                    <td class = "fila-det-border">
                                        <div align = "right"> <%= BVCA.getTotalSalida() %>                                        </div></td>
                                </tr>

                                <tr class = "fila-det">
                                  <td class = "fila-det-border">Total Entrada </td>
                                  <td class = "fila-det-border"><div align = "right"> <%= BVCA.getTotalEntrada() %> </div></td>
                                </tr>
                                <tr class = "fila-det">
                                    <td class = "fila-det-border">Total Pago                                    </td>

                                    <td class = "fila-det-border">
                                        <div align = "right"> <%= BVCA.getTotalPago() %>                                        </div></td>
                                </tr>
                            </table></td>
                    </tr>

                    <tr>
                        <td height = "33"><span class = "fila-titulo-dos">
                        <input name = "confirmar" type = "submit" class = "boton" id = "confirmar"
                               style = "cursor:pointer" value = "Confirmar Pago"> </span></td> 
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
