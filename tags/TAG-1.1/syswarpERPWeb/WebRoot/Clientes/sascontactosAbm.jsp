<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: sascontactos
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri May 27 12:30:35 ART 2011 
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
String titulo = "SAS - HISTORIAL DE CONTACTOS CLIENTE: ";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterSascontactos   = null;
int totCol = 9; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BSA"  class="ar.com.syswarp.web.ejb.BeanSascontactosAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BSA" property="*" />
<%
 BSA.setResponse(response);
 BSA.setRequest(request);
 BSA.setIdempresa ( new BigDecimal( session.getAttribute("empresa").toString() )  );  
 BSA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">


 <link rel = "stylesheet" href = "<%= pathskin %>">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <script language="JavaScript" src="vs/forms/forms.js"></script>
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
  
 <script>
 
	function mostrarMensaje(mensaje){ 
		//overlib( mensaje , STICKY, CAPTION, '[INFO]',TIMEOUT,25000,FIXX,0,FIXY,0,WIDTH,350,BGCOLOR,'#FF9900'); 
		overlib( mensaje , STICKY, CAPTION, '[AUDITORIA]',TIMEOUT,25000,WIDTH,350,BGCOLOR,'#FF9900');  
	}
	
	window.onload = function (){
	
	  document.getElementById('razonCliente').innerHTML = document.frm.idcliente.value + ' - ' +  document.frm.cliente.value;
	
	}
  
 </script>
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Descripción";
tituCol[2] = "Tipo";
tituCol[3] = "Canal";
tituCol[4] = "Motivo";
tituCol[5] = "Cliente";
tituCol[6] = "Acción";
tituCol[7] = "Resultado";
java.util.List Sascontactos = new java.util.ArrayList();
Sascontactos= BSA.getSascontactosList();
iterSascontactos = Sascontactos.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="sascontactosAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="45" colspan="2"  class="text-globales"><div> <%=titulo%> <span id="razonCliente">  </span></div></td>
                </tr>
                <tr>
                   <td width="11%" height="38">
                      <table width="36%" border="0">
                         <tr>
                            <td width="27%">
                               <input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <td width="27%">
                               <input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <td width="27%">
                               <input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return confirmarBaja('frm');">                            </td>
                         </tr>
                      </table>                   </td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BSA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BSA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BSA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BSA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BSA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BSA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BSA.getPaginaSeleccion() ){%>
                                                       <option value="<%=i%>" selected><%=i%></option> 
                                                    <%}else{%>
                                                       <option value="<%=i%>"><%=i%></option>
                                                    <%}%>
                                                <%}%>
                                             </select>                                          </td>
                                          <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BSA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id="rsTable" cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" >&nbsp;</td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
	 <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[2]%></div></td>
	 <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="25%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
	 <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
	 <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
	 <td width="25%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
   <%int r = 0;
   while(iterSascontactos.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterSascontactos.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  String auditMsg = "";
	  auditMsg = "<strong>U.Alta: </strong>" + sCampos[10] + "<br>";
	  auditMsg += "<strong>U.Mod.: </strong>" + Common.setNotNull(sCampos[11])  + "<br>";
	  auditMsg += "<strong>F.Alta: </strong>" + ( !Common.setNotNull(sCampos[12]).equals("") ?  Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf( sCampos[12] ), "JSTsToStrWithHM" ) : "" ) + "<br>";
	  auditMsg += "<strong>F.Mod.: </strong>" + ( !Common.setNotNull(sCampos[13]).equals("") ?  Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf( sCampos[13] ), "JSTsToStrWithHM" ) : "" );	  	  
	  BSA.setCliente(sCampos[9]);
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><input type="radio" name="idcontacto" value="<%= sCampos[0]%>"></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/<%=sCampos[2].equalsIgnoreCase("1") ? "mail_reply.png" :"mail_forward.png" %>" width="20" height="20" title="<%=sCampos[3]%>"></div></td>
      <td class="fila-det-border" >&nbsp;<%=sCampos[5]%></td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[16]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[18]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>	  
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/apps/config-users.png" width="20" height="20" onMouseOver="mostrarMensaje('<%= auditMsg %>')"></div></td>
   </tr>
<%
   }%>
   </table>
   <input name="accion" value="" type="hidden">
   <input name="cliente" id="cliente"  value="<%= BSA.getCliente() %>" type="hidden"> 
   <input name="idcliente" id="idcliente"  value="<%= BSA.getIdcliente() %>" type="hidden">   
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

