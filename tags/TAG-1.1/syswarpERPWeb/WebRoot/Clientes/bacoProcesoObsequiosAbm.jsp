<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: bacoProcesoObsequios
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Thu Nov 05 11:24:51 GMT-03:00 2009 
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
String titulo = "PROCESO DE OBSEQUIOS";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterBacoProcesoObsequios   = null;
int totCol = 14; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BBPOA"  class="ar.com.syswarp.web.ejb.BeanBacoProcesoObsequiosAbm"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BBPOA" property="*" />
<%
 BBPOA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BBPOA.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBPOA.setResponse(response);
 BBPOA.setRequest(request);
 BBPOA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
	<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
	<script language="JavaScript" src="vs/forms/forms.js"></script>
	<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
 <script> 

  var vecValueChk = new Array();

  function setAccion(accion){

    if(accion == 'generar'){

/*
      var idtipoobsequioFind = <%=  BBPOA.getIdtipoobsequio() %>;
      var mesCumpleanosFind  = <%=  BBPOA.getMesCumpleanos() %>;
      var anioFind = <%=  BBPOA.getAnio() %>;
      var mesFind  = <%=  BBPOA.getMes() %>;



      var idtipoobsequioActual = (document.frm.idtipoobsequio.options[document.frm.idtipoobsequio.selectedIndex].value);
      var mesCumpleanosActual    = (document.frm.mesCumpleanos.options[document.frm.mesCumpleanos.selectedIndex].value);   
      var anioActual  = (document.frm.anio.options[document.frm.anio.selectedIndex].value);
      var mesActual   = (document.frm.mes.options[document.frm.mes.selectedIndex].value);
*/

      if(cambioParametros()){
				
        if(!confirm('Los parametros actuales, son distintos a los seleccionados \npara realizar la busqueda de los registros actuales.\nConfirma nueva búsqueda?')){
					return false;
				} 
				 else{
					accion = 'buscar';

          document.frm.impactaTmp.value = true;

				}
				

			}
      else{

				if(!confirm('Confirma generar pedidos para el criterio seleccionado?')){
					return false;
				} 
				 else{
					document.frm.generar.value = 'Procesando ...';
					document.frm.generar.disabled = true;
				}

			}


    }

    document.frm.accion.value = accion;
    document.frm.submit();

  }


  function cambioParametros(){

      var idtipoobsequioFind = <%=  BBPOA.getIdtipoobsequio() %>;
      var mesCumpleanosFind  = <%=  BBPOA.getMesCumpleanos() %>;
      var anioFind = <%=  BBPOA.getAnio() %>;
      var mesFind  = <%=  BBPOA.getMes() %>;

      var idtipoobsequioActual = (document.frm.idtipoobsequio.options[document.frm.idtipoobsequio.selectedIndex].value);
      var mesCumpleanosActual    = (document.frm.mesCumpleanos.options[document.frm.mesCumpleanos.selectedIndex].value);   
      var anioActual  = (document.frm.anio.options[document.frm.anio.selectedIndex].value);
      var mesActual   = (document.frm.mes.options[document.frm.mes.selectedIndex].value);

			if( idtipoobsequioFind !=  idtipoobsequioActual || 
					mesCumpleanosFind  != mesCumpleanosActual   ||   
					anioFind           != anioActual            || 
					mesFind            != mesActual            
				){
			  return true;
			}
      else return false;


  }

  function checkEsquema( localidad, cartaregalo, obj ){
    
    var existeesquemaRegalo =<%=  BBPOA.isExisteEsqRegalo() %>;
    var existeesquemaCarta =<%=  BBPOA.isExisteEsqCarta() %>;
    var tipoobsequio = null;
       if(localidad.toUpperCase() == 'INDEFINIDA'){
           alert('Verifique el anexo-localidad asociado al domicilio del cliente, el mismo es inconsistente.' )
           obj.checked =  false;
       }
       else{   
				 if(cartaregalo.toUpperCase() == 'R'){
						if(!existeesquemaRegalo){
							tipoobsequio = ' Regalo';
						}
				 }
				 if(cartaregalo.toUpperCase() == 'C'){
						if(!existeesquemaCarta){
							tipoobsequio = ' Carta';
						}
				 } 
				 
				 if(tipoobsequio != null){
						 alert('No existe esquema definido para el periodo para el tipo de obsequio:' + tipoobsequio)
						 obj.checked =  false;
				 }
       }
    }

	function mostrarMensaje(mensaje){
		overlib( mensaje , STICKY, CAPTION, '[INFO-LOG]',TIMEOUT,25000,FIXX,0,FIXY,0,WIDTH,350,BGCOLOR,'#FF9900');
	}

  function checkEsquemaSilent( localidad, cartaregalo, obj ){
    
    var existeesquemaRegalo =<%=  BBPOA.isExisteEsqRegalo() %>;
    var existeesquemaCarta =<%=  BBPOA.isExisteEsqCarta() %>;
    var tipoobsequio = null;
		 if(localidad.toUpperCase() == 'INDEFINIDA'){
				 //alert('Verifique el anexo-localidad asociado al domicilio del cliente, el mismo es inconsistente.' )
				 obj.checked =  false;
		 }
		 else{   
			 if(cartaregalo.toUpperCase() == 'R'){
					if(!existeesquemaRegalo){
						tipoobsequio = ' Regalo';
					}
			 }
			
			 if(cartaregalo.toUpperCase() == 'C'){
					if(!existeesquemaCarta){
						tipoobsequio = ' Carta';
					}
			 } 
			 
			 if(tipoobsequio != null){
					 //alert('No existe esquema definido para el periodo para el tipo de obsequio:' + tipoobsequio)
					 obj.checked =  false;
			 }
		 }
  }


 function checkUnckeckAll(){

   var vecParam = new Array();
   var objTextTipo = document.getElementById("marca").firstChild;
   var check = true;
   if(objTextTipo.nodeValue == 'Todo') objTextTipo.nodeValue = 'Nada';
   else{
     objTextTipo.nodeValue = 'Todo';
     check=false;
   }
   var obj = document.frm.idcliente;
   if(obj){
     if(obj.length) {
       for(var i = 0;i<obj.length;i++) {
         if(obj[i].disabled) continue;
         obj[i].checked = check;
         if(check){
           vecParam = vecValueChk[obj[i].id].split('|');
           checkEsquemaSilent( vecParam[0], vecParam[1], obj[i] );
         }
       }
     }
     else{  
       if(!obj.disabled){
         vecParam = vecValueChk[obj.id].split('|');
         obj.checked = check;
         if(check)
           checkEsquemaSilent( vecParam[0], vecParam[1], obj );
       }
     }
   }    
 }


function callSubirArchivos(){ 

 if(document.frm.desdeArchivo.checked){
    abrirVentana('bacoProObsequiosUploadFile.jsp?tipoArchivo=text/plain','upload', 550, 250);
  }
  else{
    alert('Es necesario tildar el campo Desde Archivo');
  }

}


function setTMP(){
  if(cambioParametros())
    document.frm.impactaTmp.value = true;
}


function resetArchivo(obj){
  document.frm.verdatos.disabled = !obj.checked;
  document.frm.archivo.value = "";
  document.frm.patharchivo.value = "";
} 

 window.onload = function() { 
  document.getElementById('marca').onclick =  checkUnckeckAll;
 }


 </script>
 
</head>
<%
// titulos para las columnas
tituCol[0] = "idcliente";
tituCol[1] = "Cliente";
tituCol[2] = "F.Nac.";
tituCol[3] = "Domicilio";
tituCol[4] = "idanexolocalidad";
tituCol[5] = "idlocalidad";
tituCol[6] = "localidad";
tituCol[7] = "idprovincia";
tituCol[8] = "provincia";
tituCol[9] = "idestado";
tituCol[10] = "Estado";
tituCol[11] = "cartaoregalo";
tituCol[12] = "C/R";
tituCol[13] = "informecarta";
java.util.List BacoProcesoObsequios = new java.util.ArrayList();
BacoProcesoObsequios= BBPOA.getBacoProcesoObsequiosList();
iterBacoProcesoObsequios = BacoProcesoObsequios.iterator();
Iterator it;
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="bacoProcesoObsequiosAbm.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="51"  class="text-globales"><%=titulo%>
                   <hr color="#FFFFFF"></td>
                </tr>
                <tr>
                  <td width="2%" height="38"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="18%" class="text-globales">Buscar</td>
                      <td width="21%"><input name="ocurrencia" type="text" value="<%=BBPOA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                      </td>
                      <td width="61%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td width="23%" height="19">Total:&nbsp;<%=BBPOA.getTotalRegistros() + ""%></td>
                                  <td width="11%" >Visualizar:</td>
                                  <td width="11%"><select name="limit" >
                                      <%for(i=15; i<= 150 ; i+=15){%>
                                      <%if(i==BBPOA.getLimit()){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}
                                                      if( i >= BBPOA.getTotalRegistros() ) break;
                                                    %>
                                      <%}%>
                                      <option value="<%= BBPOA.getTotalRegistros()%>">Todos</option>
                                    </select>                                  </td>
                                  <td width="7%">&nbsp;P&aacute;gina:</td>
                                  <td width="12%"><select name="paginaSeleccion" >
                                      <%for(i=1; i<= BBPOA.getTotalPaginas(); i++){%>
                                      <%if ( i==BBPOA.getPaginaSeleccion() ){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}%>
                                      <%}%>
                                    </select>                                  </td>
                                  <td width="10%" class="text-globales">                                    <div align="right">
                                    <input name="ir" type="button" class="boton" id="ir" value="  Buscar" onClick="setAccion('buscar')">                                  
                                  </div></td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td height="5" colspan="3" class="text-globales"><hr color="#FFFFFF"></td>
                    </tr>
                    <tr>
                      <td class="text-globales">Tipo Proceso(*) :</td>
                      <td><span class="fila-det-border">
                        <select name="idtipoobsequio" class="campo" id="idtipoobsequio" style="width:200px">
                            <option value="-1">Seleccionar</option>
                            <%  
																 it = BBPOA.getListTipoObsequios().iterator();
																 while(it.hasNext()){
																	 String[] tipo = (String[])it.next(); %>
                            <option value="<%= tipo[0] %>" <%= BBPOA.getIdtipoobsequio().intValue() == Integer.parseInt(tipo[0] )? "selected" : "" %>><%= tipo[1] %></option>
                            <%}  %>
                      </select>
                        </span></td>
                      <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                          <tr class="text-globales">
                            <td width="43%">Mes / A&ntilde;o Entrega(*)</td>
                            <td width="14%" ><select name="mes" class="campo" id="mes">
                              <option value="-1">Seleccionar</option>
                              <%  
                     it = BBPOA.getMesesList().iterator();
                     while(it.hasNext()){
                       String[] meses= (String[])it.next(); %>
                              <option value="<%= meses[0] %>" <%= BBPOA.getMes() == Integer.parseInt(meses[0] )? "selected" : "" %>><%= meses[1] %></option>
                              <%}  %>
                            </select></td>
                            <td width="25%"><span class="fila-det-border">
                              <select name="anio" class="campo" id="anio">
                                <option value="-1">Seleccionar</option>
                                <% 
                            for(int z = BBPOA.getAnioactual(); z<=BBPOA.getAnioactual()+1 ; z++){ %>
                                <option value="<%=  z %>" <%= BBPOA.getAnio() == z ? "selected" : "" %>><%=  z %></option>
                                <%
                            }  %>
                              </select>
                            </span></td>
                            <td width="10%">&nbsp;</td>
                            <td width="8%" class="text-globales">&nbsp;</td>
                          </tr>
                          
                      </table></td>
                    </tr>
                    <tr>
                      <td height="3" colspan="3" class="text-globales"><hr color="#FFFFFF"></td>
                    </tr>
                    <tr>
                      <td height="26" colspan="3" class="text-globales"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td width="18%" class="text-globales">Mes Cumplea&ntilde;os: </td>
                          <td width="21%"><select name="mesCumpleanos" class="campo" id="mesCumpleanos">
                              <option value="-1">Seleccionar</option>
                              <%  
                     it = BBPOA.getMesesList().iterator();
                     while(it.hasNext()){
                       String[] meses= (String[])it.next(); %>
                              <option value="<%= meses[0] %>" <%= BBPOA.getMesCumpleanos() == Integer.parseInt(meses[0] )? "selected" : "" %>><%= meses[1] %></option>
                              <%}  %>
                          </select></td>
                          <td width="61%"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              <tr class="text-globales">
                                <td width="43%">Total Registros Archivo: <%=BBPOA.getTotalLineas() + ""%></td>
                                <td width="14%" >&nbsp;</td>
                                <td width="25%"><span class="fila-det-border"> </span></td>
                                <td width="10%">&nbsp;</td>
                                <td width="8%" class="text-globales">&nbsp;</td> 
                              </tr>
                          </table></td>
                        </tr>
                        <tr>
                          <td class="text-globales">Desde Archivo: </td>
                          <td><input name="desdeArchivo" type="checkbox" id="desdeArchivo" value="true" <%= BBPOA.isDesdeArchivo() ? "checked" : "" %> onClick="resetArchivo(this)"/></td>
                          <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              <tr class="text-globales">
                                <td width="43%">Archivo</td>
                                <td width="57%" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                      <td width="42%"><input name="archivo" type="text" value="<%=BBPOA.getArchivo()%>" id="archivo" size="30" maxlength="30" readonly /></td>
                                      <td width="26%"><img src="../imagenes/default/gnome_tango/apps/system-file-manager.png" width="22" height="22" style="cursor:pointer" onClick="callSubirArchivos();"/>
                                      <input name="patharchivo" type="hidden" value="<%=BBPOA.getPatharchivo()%>" id="patharchivo" size="30" maxlength="30"  />
                                      <input name="impactaTmp" type="hidden" value="<%=BBPOA.isImpactaTmp()%>" id="impactaTmp" size="30" maxlength="30"   /></td>
                                      <td width="26%"><div align="right">
                                        <input name="verdatos" type="button" class="boton" id="verdatos" value="Ver datos"  onClick="setTMP() ;setAccion('verdataosarchivo');" <%= !BBPOA.isDesdeArchivo() ? "disabled" : "" %>>
                                      </div></td>
                                    </tr>
                                    
                                </table></td>
                              </tr>
                          </table></td> 
                        </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td height="3" colspan="3" class="text-globales"><hr color="#FFFFFF"></td>
                    </tr>
                    <tr>
                      <td height="26" class="text-globales"><%//= session.getId().toString().toLowerCase() %>&nbsp;</td>
                      <td>&nbsp;</td> 
                      <td><div align="right">
                        <input name="generar" type="button" class="boton" id="generar" value="  Generar Obsequios"  onClick="setAccion('generar')">
                      </div></td>
                    </tr>
                  </table></td>
              </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo">
      <jsp:getProperty name="BBPOA" property="mensaje"/> 
      <% if(BBPOA.getMensaje().equalsIgnoreCase("Proceso Ejecutado.") ){ %>
       Ver log <img src="../imagenes/default/gnome_tango/status/info.png" width="18" height="18" onClick="mostrarMensaje('<%= BBPOA.getResultado()[1] %>')" style="cursor:pointer">.
      <% } %>
    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >

  <tr class="fila-encabezado">
     <td width="5%" align="center"><a href="#" id="marca">Todo</a> 
       </td>
     <td width="26%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><div align="center"><%=tituCol[2]%></div></td>
     <td width="34%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="8%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
     <td width="13%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[12]%></td>
     <td width="3%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);">&nbsp;</td>
    </tr>
   <%int r = 0;
   String vecValueChk = "";
   while(iterBacoProcesoObsequios.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterBacoProcesoObsequios.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
      String imagenColor = sCampos[14].equalsIgnoreCase("GENERADO") ? "red" : "green";
      String imagen = "<img src=\"../imagenes/default/gnome_tango/colors."+ (  imagenColor  ) +".icon.gif\" width=\"10\" height=\"10\" border=\"0\">";
      vecValueChk += "vecValueChk['idcliente_" + sCampos[0]+ "']='" + sCampos[8]  + "|" +  sCampos[11]  + "';\n";
      %> 
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td rowspan="2" class="fila-det-border"  background="../imagenes/default/gnome_tango/colors.<%= imagenColor %>.icon.gif"><div align="center">
        <% // Habilitar el checkbox solo cuando la localidad tenga definido un tipo de obsequio o cuando no se le haya generado y sea desde archivo %>
        <input type="checkbox" name="idcliente" id="idcliente_<%= sCampos[0]%>" value="<%= sCampos[0]%>" <%= sCampos[11].equalsIgnoreCase("N") || (sCampos[14].equalsIgnoreCase("GENERADO") && !BBPOA.isDesdeArchivo()) ? "disabled" : "" %> onClick="checkEsquema('<%=sCampos[8]%>', '<%= sCampos[11] %>', this)">
        <input name="aux_sel" type="hidden" id="aux_sel">
      </div></td>
      <td class="fila-det-border" style="border-bottom:hidden"><%=sCampos[0]%></td>
      <td rowspan="2" class="fila-det-border" ><div align="center"><%=Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[2]), "JSTsToStr")%></div></td>
      <td  class="fila-det-border" style="border-bottom:hidden" ><%=sCampos[3]%>&nbsp;&nbsp;</td>
      <td rowspan="2" class="fila-det-border" ><%=sCampos[10]%>&nbsp;</td>
      <td rowspan="2" class="fila-det-border" ><%=sCampos[12]%>&nbsp;</td>
      <td rowspan="2" class="fila-det-border" ><%=!Common.setNotNull(sCampos[13]).equals("") ? "<img border=\"0\" src=\"../imagenes/default/gnome_tango/apps/pdf.jpg\" onClick=\"alert('No Disponible.')\">" : ""%>&nbsp;</td>
    </tr>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border" ><%=sCampos[1]%></td>
     <td class="fila-det-border" ><%=sCampos[6]%>&nbsp;- <%=sCampos[8]%></td>
   </tr>
   <%
   }%>
   </table>
   <%= "<script>\n" + vecValueChk + "\n</script>" %>
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

