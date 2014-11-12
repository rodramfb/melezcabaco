<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: bacoRefCatalogoCategoria
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Mar 15 09:25:38 ART 2012 
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
String titulo = "ASIGNACION DE PUNTOS DESDE ARCHIVO";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterBacoRefCtaCte   = null;
int totCol = 6; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BBRCCA"  class="ar.com.syswarp.web.ejb.BeanBacoRefCtaCteDesdeArchivo"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BBRCCA" property="*" />
<%
 BBRCCA.setUsuarioalt( usuario ); 
 BBRCCA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BBRCCA.setResponse(response);
 BBRCCA.setRequest(request);
 BBRCCA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script>
function callSubirArchivos(){ 

 //if(document.frm.desdeArchivo.checked){
    abrirVentana('bacoReferidosPuntajeUploadFile.jsp?tipoArchivo=text/plain','upload', 550, 250);
  //}
  //else{
  //  alert('Es necesario tildar el campo Desde Archivo');
  //}

} 

function procesarAcrchivo(){

  if(document.frm.archivo.value != '' && document.frm.impactaTmp.value == 'true'){
  
    document.frm.procesar.value = 'Procesando ....';
	document.frm.procesar.disabled = true;
    document.frm.accion.value = 'procesar'
    document.frm.submit();

  }else if(document.frm.archivo.value != '' && document.frm.impactaTmp.value == 'false'){
  
    alert('Archivo ya procesado, por favor seleccione nuevamente.'); 
	
  }else{
 
    alert('Es necsario seleccionar un archivo a procesar.');  

  }

}

function cargarPuntos(){
  var mensaje = 'Esta operación asignará puntos a la cuenta corriente de los socios existentes en el archivo seleccionado: ' + document.frm.archivo.value + '\nConfirma dicha acción?';
  if(document.frm.archivo.value != '' && document.frm.impactaTmp.value == 'false'){
    if(confirm(mensaje)){
	
	  document.frm.carga.value = 'Procesando ....';
	  document.frm.carga.disabled = true;
      document.frm.accion.value = 'carga';
      document.frm.submit();
	
	}
	
  }else{
     alert('Es necsario seleccionar un archivo y procesarlo, antes de ejecutar la carga de puntos en cuenta corriente.'); 
  }

}



function reiniciarPuntos(){
  var mensaje = 'Confirma eliminar los datos correspondientes al archivo: ' + document.frm.archivo.value + '\nConfirma dicha acción?';
  if(document.frm.archivo.value != '' && document.frm.impactaTmp.value == 'false'){
    if(confirm(mensaje)){
	
	  document.frm.reiniciar.value = 'Procesando ....';
	  document.frm.impactaTmp.value = 'true';
	  document.frm.archivo.value = '';
	  document.frm.reiniciar.disabled = true;
      document.frm.accion.value = 'reiniciar';
      document.frm.submit();
	
	}
	
  }else{
     alert('Es necsario seleccionar un archivo y procesarlo previamente.'); 
  }

}


 </script>




</head>
<%
// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "Cód. Archivo";
tituCol[2] = "Cliente";
tituCol[3] = "idoperacion";
tituCol[4] = "Operacion";
tituCol[5] = "Puntos";
java.util.List BacoRefCtaCte = new java.util.ArrayList();
BacoRefCtaCte= BBRCCA.getBacoRefCtaCteList();
iterBacoRefCtaCte = BacoRefCtaCte.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="bacoRefCtaCteDesdeArchivo.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="../imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td height="39"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="89%"><table width="100%" border="0">
                      <tr>
                        <td width="6%" height="26" class="text-globales">Buscar</td>
                        <td width="22%"><input name="ocurrencia" type="text" value="<%=BBRCCA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                        </td>
                        <td width="72%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                  <tr class="text-globales">
                                    <td width="1%" height="19">&nbsp;</td>
                                    <td width="23%">&nbsp;Total de registros:&nbsp;<%=BBRCCA.getTotalRegistros()%></td>
                                    <td width="11%" >Visualizar:</td>
                                    <td width="11%"><select name="limit" >
                                        <%for(i=15; i<= 150 ; i+=15){%>
                                        <%if(i==BBRCCA.getLimit()){%>
                                        <option value="<%=i%>" selected><%=i%></option>
                                        <%}else{%>
                                        <option value="<%=i%>"><%=i%></option>
                                        <%}
                                                      if( i >= BBRCCA.getTotalRegistros() ) break;
                                                    %>
                                        <%}%>
                                        <option value="<%= BBRCCA.getTotalRegistros()%>">Todos</option>
                                      </select>                                    </td>
                                    <td width="7%">&nbsp;P&aacute;gina:</td>
                                    <td width="12%"><select name="paginaSeleccion" >
                                        <%for(i=1; i<= BBRCCA.getTotalPaginas(); i++){%>
                                        <%if ( i==BBRCCA.getPaginaSeleccion() ){%>
                                        <option value="<%=i%>" selected><%=i%></option>
                                        <%}else{%>
                                        <option value="<%=i%>"><%=i%></option>
                                        <%}%>
                                        <%}%>
                                      </select>                                    </td>
                                    <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                  </tr>
                              </table></td>
                            </tr>
                        </table></td>
                      </tr>
                  </table></td>
              </tr>
                <tr height="3px">
                  <td bgcolor="#FFFFFF" height="3px"></td>
                </tr>
                <tr>
                  <td><table width="100%" border="0" class="text-globales">
                      <tr>
                        <td width="20%">Total de registros inválidos:&nbsp;<%=BBRCCA.getTotalRegInvalidos() + "" %></td>
                        <td width="20%"><input name="subir" type="button" class="boton" id="subir" value="Subir Archivo" onClick="callSubirArchivos();">
						    <input name="archivo" type="hidden" value="<%=BBRCCA.getArchivo()%>" id="archivo" size="30" maxlength="30" readonly />
                            <input name="patharchivo" type="hidden" value="<%=BBRCCA.getPatharchivo()%>" id="patharchivo" size="30" maxlength="30"  />
                            <input name="impactaTmp" type="hidden" value="<%=BBRCCA.isImpactaTmp()%>" id="impactaTmp" size="30" maxlength="30"   /></td>
                        <td width="20%"><input name="procesar" type="button" class="boton" id="procesar" value="Procesar Archivo" onClick="procesarAcrchivo()"></td>
                        <td width="20%"><input name="carga" type="button" class="boton" id="carga" value="Cargar Puntos" onClick="cargarPuntos();"></td>
                        <td width="20%"><input name="reiniciar" type="submit" class="boton" id="reiniciar"  value="Reiniciar Datos" onClick="reiniciarPuntos();"></td>
                      </tr>
                  </table></td>
                </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BBRCCA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[0]%></div></td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[1]%></div></td>
     <td width="72%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="10%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="7%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="right"><%=tituCol[5]%></div></td>
  </tr>
   <%int r = 0;
   while(iterBacoRefCtaCte.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterBacoRefCtaCte.next(); 
	  String colorFontInvalido = "";
	  String bgcolor = "";
      // estos campos hay que setearlos segun la grilla 
	  if(Common.setNotNull(sCampos[1]).equals("-1")  || Common.setNotNull( sCampos[3] ).equals("-1")){
	    color_fondo = "permiso-tres";
		colorFontInvalido = "color=\"#FF6600\"";
	  }
	  else if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det"; 
      else color_fondo = "fila-det-verde";
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" <%= bgcolor %>> 
      <td class="fila-det-border" ><div align="right" ><font <%= colorFontInvalido %>><%=sCampos[0]%></font></div></td>
      <td class="fila-det-border" ><div align="right" ><font <%= colorFontInvalido %>><%=sCampos[1]%></font></div></td>
      <td class="fila-det-border" ><div align="left" ><font <%= colorFontInvalido %>><%=sCampos[2]%></font></div></td>
      <td class="fila-det-border" ><div align="left" ><font <%= colorFontInvalido %>><%=sCampos[4]%></font></div></td>
      <td class="fila-det-border" ><div align="right"><font <%= colorFontInvalido %>><%=sCampos[5]%></font></div></td>
   </tr>
<%
   }%>
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

