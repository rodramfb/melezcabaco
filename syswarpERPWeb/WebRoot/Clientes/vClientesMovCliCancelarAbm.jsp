<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesMovCliCancelar
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Sep 26 14:22:07 ART 2011 
   Observaciones: 
      .

*/ 
%>

<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.util.*" %> 
<%@ page import="java.math.*" %> 
<%@ page import="ar.com.syswarp.api.*" %> 
<%@ include file="session.jspf"%>
<%
try{
// captura de variables comunes
Strings str = new Strings();
String color_fondo ="";
String titulo = "GENERAR NOTAS DE CREDITO";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesMovCliCancelar   = null;
int totCol = 15; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCMCPA"  class="ar.com.syswarp.web.ejb.BeanVClientesMovCliCancelarAbm" scope="page"/>

<jsp:setProperty name="BVCMCPA" property="*" />
<%
 BVCMCPA.setUsuarioalt( session.getAttribute("usuario").toString() );
 BVCMCPA.setEjercicioactivo( Integer.parseInt( session.getAttribute("ejercicioActivo").toString() ) );
 BVCMCPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BVCMCPA.setResponse(response);
 BVCMCPA.setRequest(request);
 BVCMCPA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
 
 function emitirNotaCredito(nrointerno, comprobante){
 
   var motivo = document.frm.idmotivonc.options[document.frm.idmotivonc.selectedIndex].text.toUpperCase();
   if(confirm('Esta seguro que desea anular el comprobante: ' + comprobante + ',\naplicando un nota de credito, por el motivo: ' +  motivo + ' ?')){
   
     document.frm.nrointerno.value = nrointerno;
	 document.frm.accion.value = 'emitirnc';
	 document.frm.submit();
   
   }
 
 }
 
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "nrointerno";
tituCol[1] = "idcliente";
tituCol[2] = "Cliente";
tituCol[3] = "Comprobante";
tituCol[4] = "tipomov";
tituCol[5] = "tipomovs";
tituCol[6] = "sucursal";
tituCol[7] = "comprob";
tituCol[8] = "Fecha";
tituCol[9] = "Importe";
tituCol[10] = "Saldo";
tituCol[11] = "idclub";
tituCol[12] = "club";
tituCol[13] = "logo";
tituCol[14] = "idempresa";
java.util.List VClientesMovCliCancelar = new java.util.ArrayList();
VClientesMovCliCancelar= BVCMCPA.getVClientesMovCliCancelarList();
iterVClientesMovCliCancelar = VClientesMovCliCancelar.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="vClientesMovCliCancelarAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="31"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>

                   <td width="95%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="72%" height="26">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="21%" height="32">&nbsp;Total de registros:&nbsp;<%=BVCMCPA.getTotalRegistros()+""%></td>
                                          <td width="21%" >Visualizar:</td>
                                          <td width="15%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BVCMCPA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BVCMCPA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BVCMCPA.getTotalRegistros()%>">Todos</option>
                                          </select>                                          </td>
                                          <td width="12%">&nbsp;P&aacute;gina:</td>
                                          <td width="14%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BVCMCPA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BVCMCPA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                          </select>                                          </td>
                                          <td width="17%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                       </tr>
                                        <tr class="text-globales" height="3px" bgcolor="#FFFFFF">
                                          <td height="3px" colspan="6" bgcolor="#FFFFFF"></td>
                                        </tr>
                                        <tr class="text-globales">
                                          <td height="32">&nbsp;</td>
                                          <td >&nbsp;Motivo:</td>
                                          <td><span class="fila-det-border">
                                            <select name="idmotivonc" class="campo" id="idmotivonc">
                                              <option value="-1" >Seleccionar</option>
                                              <% 
					  Iterator iterMotivosNc = BVCMCPA.getListMotivosNc().iterator();
					  while(iterMotivosNc.hasNext()){  
					    String [] datos = (String []) iterMotivosNc.next(); 
					   %>
                                              <option value="<%= datos[0] %>" <%=BVCMCPA.getIdmotivonc().toString().equals(datos[0]) ? "selected" : "" %>><%= datos[1] %></option>
                                              <% 
					  } %>
                                            </select>
                                          </span></td>
                                          <td>&nbsp;</td>
                                          <td>&nbsp;</td>
                                          <td class="text-globales">&nbsp;</td>
                                        </tr>
                                    </table>                                 </td>
                              </tr>
                           </table>                        </td>
                     </tr>
                   </table>                </td>
            </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCMCPA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" ><div align="center"></div></td>
     <td width="2%" ><div align="center"></div></td>
     <td width="2%" >&nbsp;</td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right">
       <input name="filtroIdcliente" type="text" value="<%=BVCMCPA.getFiltroIdcliente()%>" id="filtroIdcliente" size="5" maxlength="10" style="text-align:right"  onKeyPress="if(!validaNumericosFF(event)) return false;">
     </div></td>
     <td width="45%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[3]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[8]%></div></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[9]%></div></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[10]%></div></td>
    </tr>
   <%int r = 0;
   while(iterVClientesMovCliCancelar.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesMovCliCancelar.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  
	  String imgCancelar = ""; 
	  String titleCancelar = "";
	  if(sCampos[14].equals("0")){
	    //imgCancelar ="../imagenes/default/gnome_tango/actions/gnome-lockscreen.png" ;
        //titleCancelar = "EL COMPROBANTE " + sCampos[3] + " NO TIENE COBRANZAS A DESAPLICAR Y SU SALDO ES CERO.";
	    imgCancelar = "<img src=\"../imagenes/default/gnome_tango/actions/gnome-lockscreen.png\" width=\"22\" height=\"22\" title=\"EL COMPROBANTE " + sCampos[3] + " NO TIENE COBRANZAS A DESAPLICAR Y SU SALDO ES CERO.\" >";		
	  }else{
	    //imgCancelar="../imagenes/default/gnome_tango/status/gtk-missing-image.png" ;
		//titleCancelar = "GENERAR NC PARA COMPROBANTE: " + sCampos[3]; 
	  imgCancelar = "<img src=\"../imagenes/default/gnome_tango/status/gtk-missing-image.png\" width=\"22\" height=\"22\" title=\"GENERAR NC PARA COMPROBANTE: " + sCampos[3] +"\"  onClick=\"emitirNotaCredito(" + sCampos[0] + " , '"  + sCampos[3]  + "')\" >";
	  }

	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><img src="<%=sCampos[13]%>" title="<%=sCampos[12]%>"> </div></td>
      <td valign="middle" class="fila-det-border" ><div align="center">
	   <%= imgCancelar %>
	  </div></td>
      <td valign="middle" class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/kwin.png" title="Aplicaciones del movimiento." width="22" height="22"  onClick="ventana=abrirVentana('vClientesAplicacionesAbm.jsp?idcliente=<%=  sCampos[1] %>&cliente=<%=sCampos[2]%>&nrointerno=<%= sCampos[0] %>&comprobante=<%=sCampos[4] %>', 'aplicaci', 700, 300)" style="cursor:pointer"></td>
      <td class="fila-det-border" ><div align="right"><%=sCampos[1]%></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[2]%></td>
      <td class="fila-det-border" ><div align="center"><%=sCampos[3]%></div></td>
      <td class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[8]), "JSTsToStr")%></div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.visualNumero(sCampos[9], 2) %></div></td>
      <td class="fila-det-border" ><div align="right"><%=Common.visualNumero(sCampos[10], 2)%></div></td>
    </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input type="hidden" name="nrointerno" value="0">

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