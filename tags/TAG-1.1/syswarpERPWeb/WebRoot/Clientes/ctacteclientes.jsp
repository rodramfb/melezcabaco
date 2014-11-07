<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: Lov Clientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Enero 02 16:04 2007 
   Observaciones: 
*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ page import="java.text.*" %> 
<%@ page import="java.util.Locale" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "Consulta de cuentas corrientes de clientes";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterclientesclientes   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();

NumberFormat formatter = NumberFormat.getInstance(Locale.US);

%>
<html>
<jsp:useBean id="BGLA"  class="ar.com.syswarp.web.ejb.BeanCtaCtesClientes"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BGLA" property="*" />
<%
 BGLA.setResponse(response);
 BGLA.setRequest(request);
 BGLA.ejecutarValidacion();
 
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="<%=pathscript%>/calendar/calendarcode.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="vs/overlib/overlib.js"></script>
 <script >
   function validarClientes(){   
       mostrarLOV("lov_clientes_consultas.jsp");  
   } 
 </script>
</head>
<%
// titulos para las columnas


tituCol[0] = "Acciones";
tituCol[1] = "Fecha";
tituCol[2] = "Tipo";
tituCol[3] = "Comprobante";
tituCol[4] = "Debe";
tituCol[5] = "Haber";
tituCol[6] = "Saldo x Mov.";
tituCol[7] = "Saldo Acum.";
tituCol[8] = "Formulario";
tituCol[9] = "Nro. Int.";

java.util.List clientesclientes = new java.util.ArrayList();
clientesclientes = BGLA.getClientesclientesList();
iterclientesclientes = clientesclientes.iterator();
String idcliente =""; 
if (BGLA.getIdcliente() != null) 
   idcliente = BGLA.getIdcliente().toString();

%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="ctacteclientes.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr class="text-globales">
                   <td colspan="2"  ><%=titulo%></td>
                </tr>
                <tr>
                   <td width="3%" height="38">&nbsp;                  </td>
                   <td width="97%">
                      <table width="100%" border="0">
              <tr  class="text-globales">
                         
                         

                <td width="11%" class="fila-det-border">Cliente: (*) 
                  <input name="idcliente" type="hidden" id="idcliente" value="<%=BGLA.getIdcliente()%>"></td>
                <td width="34%" class="fila-det-border">
                 <table width="41%" border="0">
                  <tr class="fila-det-border">
                    <td width="14%" >&nbsp;</td>
                    <td width="69%"><input name="cliente" type="text" id="cliente" size="45" maxlength="45" readonly="true" value="<%=BGLA.getCliente()%>"></td>
					
                    <td width="17%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="mostrarLOV('../Clientes/lov_clientes.jsp')" style="cursor:pointer"></td>
                  </tr>
                </table></td>
                           
                           
                           <td width="55%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="10%" height="19">&nbsp; </td>

                                          <td width="18%">Tipo:</td>
                                          <td width="58%">
                                          <%
                                            String tipo =  BGLA.getTipo();
                                            if (tipo==null) tipo = "P";
                                          %>
                                          
                                             <select name="tipo" >                                                
                                                <%if(tipo.equalsIgnoreCase("P")){%>
                                                   <option value="P" selected>Pendiente</option>
                                                <%}else{%> 
                                                   <option value="P">Pendiente</option> 
                                                 <%}%> 
                                                 
                                                <%if(tipo.equalsIgnoreCase("H")){%>
                                                   <option value="H" selected>Historico</option>
                                                <%}else{%> 
                                                   <option value="H">Historico</option> 
                                                 <%}%> 
                                             </select>                                          </td>
                                          <td width="14%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                    </table>                                 </td>
                              </tr>
                           </table>                  </td>
                     </tr>
                   </table>
                </td>
            </tr>
          </table>
      </td>
    </tr> 
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BGLA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">  
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="17%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[4]%></div></td>		
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[5]%></div></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[7]%></div></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"></div></td>	
	 <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"></div></td>		 
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
   <%int r = 0;
   String saldoAcumuladoC = "";
   double  saldoAcumulado = 0;
   while(iterclientesclientes.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterclientesclientes.next();       
      String fechamov = (String) Common.setObjectToStrOrTime( java.sql.Timestamp.valueOf(sCampos[2]),"JSTsToStr");
      Double importe  = new Double(sCampos[8]);
      Double saldo    = new Double(sCampos[9]);      
      String importeC = sCampos[8];
      String saldoC   = sCampos[9];
      if (importeC.equalsIgnoreCase("0.00")) importeC ="";
      if (saldoC.equalsIgnoreCase("0.00"))   saldoC ="";
      // armo un debe y un haber para que sea mas facil mostrar en pantalla
      String debeC   = "";
      String haberC  = "";
      if( importeC.compareTo("0.00")>=0 )
         debeC = importe.toString();
      else
         haberC = importe.toString();
    
            
          saldoAcumulado +=  saldo.doubleValue();
          //saldoAcumuladoC = formatter.format(saldoAcumulado);
		  saldoAcumuladoC = (saldoAcumulado) + "";
      
      
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >   
      <td class="fila-det-border" ><%=fechamov%>&nbsp;</td>      
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.strZero(sCampos[3],4)+"-"+Common.strZero(sCampos[4],8) %>&nbsp;</td>
    
      
      <td class="fila-det-border" ><div align="right"><%=Common.visualNumero( debeC, 2)%>&nbsp;</div></td>
	  <td class="fila-det-border" ><div align="right"><%=haberC%>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.visualNumero(saldoC, 2)%>&nbsp;</div></td>
      
      <td class="fila-det-border" ><div align="right"><%=Common.visualNumero( saldoAcumuladoC, 2) %>&nbsp;</div></td>
      
      <td class="fila-det-border" >
	  <%if(sCampos[6].equalsIgnoreCase("4")){  %>


	  <div align="right"><img src="../imagenes/default/gnome_tango/actions/editpaste.png" width="18" height="18" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=modelo.recibo&comprobante=<%=sCampos[4]%>','comprobante',750, 400)" style="cursor:pointer">&nbsp;</div>

	  <%}else if(sCampos[6].equalsIgnoreCase("6")){  %>

	  <div align="right"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="20" height="20" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=modelo.reintegro&nrointerno=<%=sCampos[15]%>','comprobante',750, 400)" style="cursor:pointer">&nbsp;</div>

	  <%}else{  %>

	  <div align="right"><img src="../imagenes/default/gnome_tango/apps/kedit.png" width="18" height="18" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=modelo.factura.generico.consulta&nrointerno=<%=sCampos[15]%>','comprobante',750, 400)" style="cursor:pointer">&nbsp;</div>

	  <%}  %>
	  </td>
	  <td class="fila-det-border" ><div align="center"><% if( Long.parseLong(sCampos[19]) > 0 ) { %><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="18" height="18" border="0" style="cursor:pointer" onClick="abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=remitos_clientes_frame_movcli&nrointerno=<%=sCampos[15]%>', 'remitocliente', 750, 500);"> 
	  <% } else { %> <img src="../imagenes/default/gnome_tango/actions/lock.png" width="18" height="18" title="No hay remito asociado."> 
	  <% } %></div></td> 
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/calc.png" title="Asiento contable del movimiento." width="18" height="18"  onClick="ventana=abrirVentana('vclientesAsientosAbm.jsp?nrointerno=<%= sCampos[15] %>&comprobante=<%=Common.strZero(sCampos[3],4)+"-"+Common.strZero(sCampos[4],8) %>', 'asie', 600, 300)" style="cursor:pointer"></div></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/kwin.png" title="Aplicaciones del movimiento." width="18" height="18"  onClick="ventana=abrirVentana('vClientesAplicacionesAbm.jsp?idcliente=<%=  BGLA.getIdcliente() %>&cliente=<%=BGLA.getCliente()%>&nrointerno=<%= sCampos[15] %>&comprobante=<%=Common.strZero(sCampos[3],4)+"-"+Common.strZero(sCampos[4],8) %>', 'aplicaci', 700, 300)" style="cursor:pointer"></div></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/mimetypes/gnome-mime-application-vnd.ms-excel.png" title="Cuotas del movimiento." width="18" height="18"  onClick="ventana=abrirVentana('vClientesMovCuota.jsp?idcliente=<%=  BGLA.getIdcliente() %>&cliente=<%=  BGLA.getCliente() %>&nrointerno=<%= sCampos[15] %>&comprobante=<%=Common.strZero(sCampos[3],4)+"-"+Common.strZero(sCampos[4],8) %>', 'aplicaci', 700, 300)" style="cursor:pointer"></div></td>
   </tr>
   
<%
   }%>
  <tr class="fila-encabezado">  
     <td></td>
     <td></td>
     <td></td>
     <td><div align="right"></div></td>
     <td><div align="right"></div></td>
     <td><div align="right">Saldo:</div></td>
     <td><div align="right"><%=Common.visualNumero( saldoAcumuladoC, 2)%></div></td>
     <td><div align="right"></div></td>
     <td><div align="right"></div></td>     
     <td></td>
     <td></td>
     <td></td>
  </tr>
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
}%>

