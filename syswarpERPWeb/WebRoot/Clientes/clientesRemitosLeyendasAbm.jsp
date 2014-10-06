<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesRemitosLeyendas
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Dec 10 09:15:59 GMT-03:00 2009 
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
String titulo = "LEYENDAS DE REMITOS DE CLIENTES POR PERIODO";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesRemitosLeyendas   = null;
Iterator iter   = null;
int totCol = 10; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCRLA"  class="ar.com.syswarp.web.ejb.BeanClientesRemitosLeyendasAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCRLA" property="*" />
<%
 BCRLA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCRLA.setResponse(response);
 BCRLA.setRequest(request);
 BCRLA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
<script>
  function callBajaMultiple(){

   var obj = document.frm.idleyendaMultiple;
   var seleccion = false;
   if(obj){
     //alert('1');
     if(obj.length) {
       //alert('2');
       for(var i = 0;i<obj.length;i++)  {
         //alert('3: ' + obj[i].checked);
         if(obj[i].checked){
           seleccion = true;
           break;
         }
       }
     }
     else  
       seleccion = obj.checked ;
   }   

   if(!seleccion)
     alert('Seleccione al menos un registro a eliminar.');
   else if(confirm('Confirma eliminar los registros seleccionados?')){
     document.frm.accion.value = 'bajaMultiple';
     document.frm.submit();
   }

  } 



function chekUnCheck(){
   var obj = document.frm.idleyendaMultiple;
   var chek = false;
   if(obj){
     if(obj.length) {
       for(var i = 0;i<obj.length;i++)  {
         if(i==0) check = !obj[i].checked;
         obj[i].checked = check;
       }
     }
     else  
       obj.checked = !obj.checked;
   } 
}

function mostrarMensaje(mensaje){
	overlib( mensaje , STICKY, CAPTION, '[INFO]',TIMEOUT,25000,HAUTO,VAUTO,WIDTH,350,BGCOLOR,'#FF9900');
	//overlib( mensaje , STICKY, CAPTION, '[INFO]',TIMEOUT,25000,FIXX,0,FIXY,0,WIDTH,350,BGCOLOR,'#FF9900');  
}

function setModificar(idleyenda){
  document.frm.accion.value = 'modificacion';
  document.frm.idleyenda.value = idleyenda;
  document.frm.submit();
}

</script>
</head>
<%
// titulos para las columnas

/*

+ " SELECT ly.idleyenda, ly.anio, ly.idmes, me.mes, lo.idlocalidad, "
+ "        COALESCE(lo.localidad, 'TODAS'), pr.idprovincia, "
+ "        COALESCE(pr.provincia, 'TODAS'), ly.leyenda,"

*/

tituCol[0] = "Cód.";
tituCol[1] = "Año";
tituCol[2] = "IdMes";
tituCol[3] = "Mes";
tituCol[4] = "idLocalidad";
tituCol[5] = "Localidad";
tituCol[6] = "CP";
tituCol[7] = "IdProvincia";
tituCol[8] = "Provincia";
tituCol[9] = "Leyenda";
java.util.List ClientesRemitosLeyendas = new java.util.ArrayList();
ClientesRemitosLeyendas= BCRLA.getClientesRemitosLeyendasList();
iterClientesRemitosLeyendas = ClientesRemitosLeyendas.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div align="center"></div>
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div> 
<form action="clientesRemitosLeyendasAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="25" colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                   <td width="11%" height="38">
                      <table width="36%" border="0">
                         <tr>
                            <td width="27%">
                               <input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/btn_add_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('alta','','../imagenes/default/btn_add_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <%-- <td width="27%">
                               <input name="modificacion" id="modificacion" value="modificacion" type="image" src="../imagenes/default/btn_edit_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('modificacion','','../imagenes/default/btn_edit_over.gif',1)" onClick="document.frm.accion.value = this.name">                            </td>
                            <td width="27%">
                               <input name="baja" id="baja" value="baja" type="image" src="../imagenes/default/btn_remove_norm.gif"  onMouseOut="MM_swapImgRestore() " onMouseOver="MM_swapImage('baja','','../imagenes/default/btn_remove_over.gif',1)" onClick="return confirmarBaja('frm');">                            </td> --%>
                            
                            <td width="27%"><img src="../imagenes/default/gnome_tango/emblems/emblem-unreadable.png" width="22" height="22" onClick="callBajaMultiple();" style="cursor:pointer"></td>
                         </tr>
                      </table>                   </td>
                   <td width="89%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="72%" height="26">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCRLA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BCRLA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BCRLA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BCRLA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BCRLA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BCRLA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCRLA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
    <td colspan="3" ><span class="fila-det-border">
      <select name="filtroClub" id="filtroClub" class="campo" style="width:100%" onChange="document.frm.submit();">
        <option value="" >Sel.</option>
        <% 
									  iter = BCRLA.getListClub().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
        <option value="<%= datos[0] %>" <%= datos[0].equals( BCRLA.getFiltroClub().toString() ) ? "selected" : "" %>><%= datos[1] %></option>
        <%  
									  }%>
      </select>
    </span></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><span class="fila-det-border">
      <select name="filtroAnio" class="campo" id="filtroAnio" style="width:45px">
        <option value="">Sel.</option>
        <% 
                    for(int z = BCRLA.getAnioactual()-5; z<=BCRLA.getAnioactual()+1 ; z++){ %>
        <option value="<%=  z %>" <%= BCRLA.getFiltroAnio().equals("" + z   ) ? "selected" : "" %>><%=  z %></option>
        <% 
                    }  %>
      </select>
    </span></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><span class="fila-det-border">
      <select name="filtroMes" class="campo" id="filtroMes" style="width:45px">
        <option value="">Sel.</option>
        <%  
                     iter = BCRLA.getMesesList().iterator();
                     while(iter.hasNext()){
                       String[] meses= (String[])iter.next(); %>
        <option value="<%= meses[0] %>" <%=  BCRLA.getFiltroMes().equals(meses[0] )? "selected" : "" %>><%= meses[1] %></option>
        <%}  %>
      </select>
    </span></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><span class="fila-det-border">
      <select name="filtroProvincia" id="filtroProvincia" class="campo" style="width:75px" >
        <option value="" selected>Sel.</option>
        <% 
									  iter = BCRLA.getListProvincias().iterator();
									  while(iter.hasNext()){
										String [] datos = (String[])iter.next();%>
        <option value="<%= datos[0] %>" <%= datos[0].equals( BCRLA.getFiltroProvincia()) ? "selected" : "" %>><%= datos[1] %></option>
        <%  
									  }%>
      </select>
    </span></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><span class="fila-det-border">
      <input name="filtroLocalidad" type="text" value="<%=BCRLA.getFiltroLocalidad()%>" id="filtroLocalidad" size="40" maxlength="30">
    </span></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right">
      <input name="filtroPostal" type="text" value="<%=BCRLA.getFiltroPostal()%>" id="filtroPostal" size="8" maxlength="6">
    </div></td>
    <td onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
  </tr>
  <tr class="fila-encabezado">
     <td width="2%" >&nbsp;</td>
     <td width="2%" >&nbsp;</td>
     <td width="2%" ><a href="#" onClick="chekUnCheck()">SEL</a></td>
     <td width="2%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="27%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[8]%></td>
     <td width="46%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[6]%></div></td>
     <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[9]%></div></td>
  </tr>
   <%int r = 0;
   while(iterClientesRemitosLeyendas.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterClientesRemitosLeyendas.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><%-- <input type="radio" name="idleyenda" value="<%= sCampos[0]%>"> --%>
      <img src="../imagenes/default/btn_edit_over.gif" width="22" height="21" onClick="setModificar(<%= sCampos[0]%>)"></td>
      <td class="fila-det-border" ><img src="<%=Common.setNotNull(sCampos[12])%>" title="<%=Common.setNotNull(sCampos[11])%>"></td>
      <td class="fila-det-border" ><input name="idleyendaMultiple" type="checkbox" id="idleyendaMultiple" value="<%= sCampos[0]%>"></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[8]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5] %>&nbsp;</td>
      <td class="fila-det-border" ><div align="right"><%= Common.setNotNull ( sCampos[6] ) %>&nbsp;</div></td>
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/status/info.png" width="22" height="22" onMouseOver="mostrarMensaje('<%= Common.setNotNull(sCampos[9]).trim().replaceAll("\r", "").replaceAll("\n", "") %>')"></div></td>
   </tr>
<%
   }%>
   </table>
   <input name="idleyenda" value="" type="hidden">
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

