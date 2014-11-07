<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: bacoRefPrepro
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Jun 16 10:12:02 ART 2010 
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
String titulo = "REFERIDOS - PREPROSPECTOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterBacoRefPrepro   = null;
int totCol = 25; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BBRPA"  class="ar.com.syswarp.web.ejb.BeanBacoRefPreproAbm" scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BBRPA" property="*" />
<%
 BBRPA.setResponse(response);
 BBRPA.setRequest(request);
 BBRPA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRPA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
<!--
function callOverlib(leyenda){
  //  overlib(leyenda, STICKY, CAPTION, 'MAS INFO',TIMEOUT,5000,HAUTO,VAUTO,WIDTH,350,BGCOLOR, '#DBDEEE', CAPCOLOR, '#FF0000');
    overlib(leyenda, STICKY, CAPTION, 'MAS INFO',TIMEOUT,5000,HAUTO,FIXY ,0 ,WIDTH,350,BGCOLOR, '#9999CC', CAPCOLOR, '#FF0000'); 
  }
  
  function validarBaja(){
   if(document.frm.status.value == '1'){
     alert('No es posible efectuar esta operación, el registro ya fue procesado.');
     return false;
  }else{
    if(confirm("Confirma baja?")){
      document.forms['frm'].accion.value = "baja";
	  return true;
	}
	else return false;
  }
 
  return false;   
 
 }

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
//-->

function reestablecerFiltro(filtro){
  // 
  if(filtro == 'filtroVendedor'){
    document.frm.filtroIdvendedor.value = -1;
	document.frm.filtroVendedor.value = '';
  }
}

 function checkUnckeckAll(){
   var objTextTipo = document.getElementById("marca").firstChild;
   var check = true;
   if(objTextTipo.nodeValue == 'Todos') objTextTipo.nodeValue = 'Ninguno';
   else{
     objTextTipo.nodeValue = 'Todos';
     check=false;
   }
   var obj = document.frm.sel;
   if(obj){
     if(obj.length) {
       for(var i = 0;i<obj.length;i++)  {
         obj[i].checked = check;
       }
     }
     else  
       obj.checked = check;
   }    
 }
 
  function imprimirSeleccion(){
 
  var isChecked = false;
  var preprospectosToCollection = '';
  var obj = document.frm.sel;
  
  var fechaDesdeBusqueda = '<%=BBRPA.getFechadesde()%>';
  var fechaHastaBusqueda = '<%=BBRPA.getFechahasta()%>';
  var idvendedorBusqueda= '<%=BBRPA.getFiltroIdvendedor()%>';
  
   if(obj){
     if(obj.length) {
       for(var i = 0;i<obj.length;i++)  {
         if(obj[i].checked){
		   isChecked = true;
		   preprospectosToCollection += obj[i].value + '-';
		 }
       }
     }
     else{  
       isCheked = obj.checked  ;
	   preprospectosToCollection = obj[i].value ;
	 }
   }    
 
   if(idvendedorBusqueda != ''
      || fechaDesdeBusqueda != ''
      || fechaHastaBusqueda != ''){
	   if(isChecked)
		 abrirVentana('../reportes/jasper/generaPDF.jsp?plantillaImpresionJRXML=referidos_preprospectos_vendedor&fechadesde='+fechaDesdeBusqueda+'&fechahasta='+fechaHastaBusqueda+'&preprospectosToCollection=' + preprospectosToCollection, 'preprospectos', 750, 500); 
	   else 
		 alert('Seleccione al menos un registro a imprimir.');
	}else{
		alert('Es necesario filtrar por Fecha y Vendedor.');	
	}		 
 }
 
 window.onload = function() { 
  document.getElementById('marca').onclick =  checkUnckeckAll;
 }

</script>
 
 
</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Nombre";
tituCol[2] = "Apellido";
tituCol[3] = "idReferente";
tituCol[4] = "Referente";
tituCol[5] = "idvendedor";
tituCol[6] = "Vendedor";
tituCol[7] = "idFuente";
tituCol[8] = "Fuente";
tituCol[9] = "Fecha";
tituCol[10] = "TE";
tituCol[11] = "CEL";
tituCol[12] = "Email";
tituCol[13] = "idProvincia";
tituCol[14] = "Provincia";
tituCol[15] = "idLocalidad";
tituCol[16] = "Localidad";
tituCol[17] = "Obs.";
tituCol[18] = "Procesado";
tituCol[19] = "Procesado";
tituCol[20] = "idrefestado";
tituCol[21] = "Estado";
tituCol[22] = "idrefsubestado";
tituCol[23] = "SubEstado";
tituCol[24] = "U.Alta";

java.util.List BacoRefPrepro = new java.util.ArrayList();
BacoRefPrepro= BBRPA.getBacoRefPreproList();
iterBacoRefPrepro = BacoRefPrepro.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>
<form action="bacoRefPreproAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="../imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td width="8%" height="35"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="text-globales">
                      <td width="13%"><table width="50%" border="0">
                        <tr>
                          <td width="27%"><input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">                          </td>
                          <td width="27%"><input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">                          </td>
                          <td width="27%"><input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return  validarBaja();">                          </td>
                        </tr>
                      </table></td>
                      <td width="35%">Total de registros:&nbsp;<%=BBRPA.getTotalRegistros()+""%></td>
                      <td width="26%"><table width="99%" border="0" cellspacing="0" cellpadding="0">
                        <tr class="text-globales">
                          <td >Visualizar:</td>
                          <td><select name="limit" >
                              <%for(i=15; i<= 150 ; i+=15){%>
                              <%if(i==BBRPA.getLimit()){%>
                              <option value="<%=i%>" selected><%=i%></option>
                              <%}else{%>
                              <option value="<%=i%>"><%=i%></option>
                              <%}
                                                      if( i >= BBRPA.getTotalRegistros() ) break;
                                                    %>
                              <%}%>
                              <option value="<%= BBRPA.getTotalRegistros()%>">Todos</option>
                            </select>                          </td>
                        </tr>
                      </table></td>
                      <td width="19%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr class="text-globales">
                          <td width="19%">&nbsp;P&aacute;gina:</td>
                          <td width="81%"><select name="paginaSeleccion" >
                              <%for(i=1; i<= BBRPA.getTotalPaginas(); i++){%>
                              <%if ( i==BBRPA.getPaginaSeleccion() ){%>
                              <option value="<%=i%>" selected><%=i%></option>
                              <%}else{%>
                              <option value="<%=i%>"><%=i%></option>
                              <%}%>
                              <%}%>
                            </select>                          </td>
                        </tr>
                      </table></td>
                      <td width="7%"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                    </tr>
                    <tr class="text-globales">
                      <td height="29">Fecha Desde </td>
                      <td><span class="fila-det-border">
                        <input name="fechadesde" type="text" class="cal-TextBox" id="fechadesde" onFocus="this.blur()" value="<%=BBRPA.getFechadesde()%>" size="10" maxlength="12" readonly>
                      <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_7', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_7', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_7', 'img_Date_DOWN');showCalendar('frm','fechadesde','BTN_date_7');return false;"> <img align="absmiddle" border="0" name="BTN_date_7" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a> </span></td>
                      <td>Fecha Hasta </td>
                      <td><span class="fila-det-border">
                        <input name="fechahasta" type="text" class="cal-TextBox" id="fechahasta" onFocus="this.blur()" value="<%=BBRPA.getFechahasta()%>" size="10" maxlength="12" readonly>
                      <a class="so-BtnLink" href="javascript:calClick();return false;"
                  onmouseover="calSwapImg('BTN_date_0', 'img_Date_OVER',true); "
                  onmouseout="calSwapImg('BTN_date_0', 'img_Date_UP',true);"
                  onclick="calSwapImg('BTN_date_0', 'img_Date_DOWN');showCalendar('frm','fechahasta','BTN_date_0');return false;"> <img align="absmiddle" border="0" name="BTN_date_0" src="vs/calendar/btn_date_up.gif" width="22" height="17"></a> </span></td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr class="text-globales">
                      <td height="34">Vendedor
                        <input name="filtroIdvendedor" type="hidden" class="campo" id="filtroIdvendedor" value="<%=BBRPA.getFiltroIdvendedor()%>" size="10" maxlength="10"  ></td>
                      <td><table width="29%" border="0" cellspacing="0" cellpadding="0">
                          <tr >
                            <td width="51%"><input name="filtroVendedor" type="text" class="campo" id="filtroVendedor" value="<%=BBRPA.getFiltroVendedor()%>" size="25" maxlength="10" readonly ></td>
                            <td width="49%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="22" height="22" onClick="abrirVentana('../Clientes/lov_vendedor.jsp', 'vende', 700, 400)" style="cursor:pointer"></td>
                            <td width="49%"><img src="../imagenes/default/gnome_tango/actions/editclear.png" width="22" height="22" title="Reestablecer filtro!" onClick="reestablecerFiltro('filtroVendedor')"></td>
                          </tr>
                      </table>                      </td>
                      <td>Fuente</td>
                      <td><select name="filtroIdfuente" id="filtroIdfuente" class="campo" style="width:80%" >
                          <option value="-1" >Seleccionar</option>
                          <% 
					  Iterator iter = BBRPA.getListFuente().iterator();
					  while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
                          <option value="<%= datos[0] %>" <%= datos[0].equals( BBRPA.getFiltroIdfuente().toString()) ? "selected" : "" %> lang="<%= datos[2]%>"><%= datos[1] %></option>
                          <%  
					  }%>
                        </select>					  </td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr class="text-globales">
                      <td height="34">Estado</td>
                      <td><select name="filtroIdrefestado" id="filtroIdrefestado" class="campo" style="width:50%" >
                          <option value="-1" >Seleccionar</option>
                          <% 
					  iter = BBRPA.getListRefEstados().iterator();
					  while(iter.hasNext()){
						String [] datos = (String[])iter.next();%>
                          <option value="<%= datos[0] %>" <%= datos[0].equals( BBRPA.getFiltroIdrefestado().toString()) ? "selected" : "" %> lang="<%= datos[2]%>"><%= datos[1] %></option>
                          <%  
					  }%>
                        </select> </td>
                      <td><img src="../imagenes/default/gnome_tango/apps/pdf.jpg" width="20" height="20" border="0" style="cursor:pointer" onClick="imprimirSeleccion()" title="Imprimir seleccionados."></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BBRPA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td width="3%" ><a href="#" id="marca">Todo</a></td>
    <td width="2%" >&nbsp;</td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="14%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="17%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="32%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="15%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[6]%></td>
     <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[21]%></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[24]%></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    </tr>
   <%int r = 0;
   while(iterBacoRefPrepro.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterBacoRefPrepro.next(); 
      // estos campos hay que setearlos segun la grilla 
      String masInfo = "";
      if(!Common.setNotNull(sCampos[18]).equals("0") )color_fondo = "permiso-tres";
      else if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  String procesado = sCampos[18];
      for(int m=0;m<tituCol.length; m++) {
        //if(htRestringido.containsKey(m + "")) continue;
		if(m==18){
		  sCampos[m] = sCampos[m].equals("0") ? "PENDIENTE DE PROCESAR" : "PROCESADO";
		}
        masInfo += "<strong>" + tituCol[m] + ": </strong>" + Common.setNotNull(sCampos[m]) + "<br>";
      }	  	  
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" >
         <div align="center">
           <input name="sel" type="checkbox" id="sel" value="<%=sCampos[0]%>">
         </div>      </td>
     <td class="fila-det-border" ><div align="center">
       <input type="radio" name="idpreprospecto" value="<%= sCampos[0]%>" onClick="document.frm.status.value = '<%=procesado %>'">
     </div></td> 
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%></td>
      <td class="fila-det-border" ><%=sCampos[3]%> - <%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
      <td class="fila-det-border" ><%=Common.setNotNull(sCampos[20])%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[24]%></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/status/dialog-information.png" width="18" height="18" onClick="callOverlib('<%=masInfo%>')"  title="Click para ver m&aacute;s info."></div></td>
    </tr>
<%
   }%>
  </table>

   <input name="accion" value="" type="hidden">
   <input name="status" value="" type="hidden">
 
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

