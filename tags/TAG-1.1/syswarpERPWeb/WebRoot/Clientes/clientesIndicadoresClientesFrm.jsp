<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: clientesIndicadoresClientes
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Fri Apr 30 15:18:45 GMT-03:00 2010 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
Strings str = new Strings();
String color_fondo ="";
int i = 0;
int totCol = 7; // cantidad de columnas
String[] tituCol = new String[totCol];
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();

// ---------------------------------------------------------------

// titulos para las columnas
tituCol[0] = "Cód.";
tituCol[1] = "idcliente";
tituCol[2] = "Cliente";
tituCol[3] = "idindicador";
tituCol[4] = "Indicador";
tituCol[5] = "Valor";
tituCol[6] = "idempresa";

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCICF"  class="ar.com.syswarp.web.ejb.BeanClientesIndicadoresClientesFrm"   scope="page"/>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCICF" property="*" />
 <% 
 
 BCICF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCICF.setResponse(response);
 BCICF.setRequest(request);
 BCICF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCICF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCICF.ejecutarValidacion();
 String titulo = BCICF.getAccion().toUpperCase() + " DE INDICADORES POR CLIENTE " ;
 %>
<head>
 <title>Indicadores Por Cliente</title>
 <meta http-equiv="description" content="DELTA">
 <link rel="stylesheet" type="text/css" href="<%=pathskin%>">
 <script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
 <link rel="stylesheet" type="text/css" href="../imagenes/default/erp-style.css">

<script type="text/JavaScript">
<!--
function  confirmarUpd( iddato ){
  document.frm.iddato.value = iddato;
  document.frm.accion.value = 'modificacion';
  document.frm.submit();
}


function  confirmarDel( iddato ){
  if(confirm('Confirma baja del registro seleccionado?')){
		document.frm.iddato.value = iddato;
		document.frm.accion.value = 'baja';
		document.frm.submit();
  }
}


function setObject(objSelect){

	var contieneObj = document.getElementById('contieneObj');
	var obj = document.createElement('input');
  var objDel = document.getElementById('valor');
  //alert('OBJDel: ' + objDel);
  if(objDel != null &&  objDel  ){
    //alert('Destruyendo OBJDel: ' + objDel);
    document.getElementById('contieneObj').removeChild(objDel);
  }
  if(objSelect.options[objSelect.selectedIndex].title == 'texto'){
		obj.setAttribute('type', 'text');
		obj.setAttribute('name', 'valor' );
		obj.setAttribute('id', 'valor' );
		obj.setAttribute('class', 'campo' );
		obj.setAttribute('size', '8');
		contieneObj.appendChild(obj); 
	}else if(objSelect.options[objSelect.selectedIndex].title == 'combo'){
		obj = document.createElement('select');
		obj.setAttribute('name', 'valor' );
		obj.setAttribute('id', 'valor' );
		obj.setAttribute('class', 'campo' );
		obj.options[obj.options.length] = new Option(   'Seleccionar'   , -1);
		obj.options[obj.options.length] = new Option(   'Si'   , 1);
		obj.options[obj.options.length] = new Option(   'No'   , 2);
		contieneObj.appendChild(obj); 
	}
}

window.onload = function (){

  setObject(document.getElementById('idindicador'));
  var objValor  = document.getElementById('valor');
  var valueObjValor = '<%=BCICF.getValor()%>' ;
  if(objValor == null || objValor == 'undefined'){
    objValor = objValor; // Tonto
  }else if(objValor.type == 'text'){
    objValor.value = valueObjValor;
  }else if(objValor.type == 'select-one'){
    for(var i= 0;i<objValor.options.length;i++)
      if(objValor.options[i].value == valueObjValor)
         objValor.options[i].selected=true ;
	}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
//-->
</script>
</head>
<BODY >
<div id="popupcalendar" class="text"></div>

<form action="clientesIndicadoresClientesFrm.jsp" method="post" name="frm">
<input name="accion" type="hidden" value="<%=BCICF.getAccion()%>" >
<input name="recargar" type="hidden" value="1" >
<input name="iddato" type="hidden" value="<%=BCICF.getIddato()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td height="29">&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td height="27" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCICF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="15%" height="36" class="fila-det-border">Cliente: (*) 
                <input name="idcliente" type="hidden" value="<%=BCICF.getIdcliente()%>"></td>
                <td width="85%" class="fila-det-border"><table width="50%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="fila-det">
                      <td width="38%"><input name="cliente" type="text" class="campo" id="cliente" value="<%=BCICF.getCliente()%>" size="30" maxlength="30"  readonly></td>
                      <td width="62%"><img src="../imagenes/default/gnome_tango/actions/gtk-find.png" width="22" height="22" onClick="abrirVentana('lov_clientes.jsp', 'branch', 750, 450);" style="cursor:pointer"> </td>
                  </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td width="15%" height="35" class="fila-det-border">Indicador: (*) </td>
                <td width="85%" class="fila-det-border">
                 <select name="idindicador" class="campo" id="idindicador" onChange="setObject(this)">
                  <option value="-1" selected>Seleccionar</option>
                  <%  
																 Iterator it =  BCICF.getListIndicadoresClientes().iterator();
																 while(it.hasNext()){
																	 String[] tipo = (String[])it.next(); %>
                  <option value="<%= tipo[0] %>" title="<%= tipo[3] %>" <%= BCICF.getIdindicador().intValue() == Integer.parseInt(tipo[0] )? "selected" : "" %>><%= tipo[1] %></option>
                  <%}  %>
                </select>                </td>
              </tr>
              <tr class="fila-det">
                <td width="15%" height="47" class="fila-det-border">Valor: (*) </td>
                <td width="85%" class="fila-det-border"><div id="contieneObj"></div></td>
              </tr>
              <tr class="fila-det">
                <td height="55" class="fila-det-border">&nbsp;</td> 
                <td class="fila-det-border"><input name="validar" type="submit" value="Enviar" class="boton">
                  <!--input name="volver" type="submit" class="boton" id="volver" value="Volver"--></td>
              </tr>
              <tr class="fila-det">
                <td height="23" class="fila-det-border"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <tr class="subtitulo-tres">
                    <td width="23%" ><div align="center">
                        <input name="alta" id="alta" value="alta" type="image" src="../imagenes/default/nuevo.gif"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('alta','','../imagenes/default/nuevo.gif',1)" onClick="document.frm.accion.value = 'recarga'" >
                    </div></td>
                    <td width="77%" >Nuevo</td>
                  </tr>
                </table></td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td height="55" colspan="2" class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="38"><table width="100%" border="0">
                          <tr>
                            <td width="6%" height="26" class="text-globales">Buscar                            </td>
                            <td width="22%"><input name="ocurrencia" type="text" value="<%=BCICF.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                            </td>
                            <td width="72%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                      <tr class="text-globales">
                                        <td width="1%" height="19">&nbsp;</td>
                                        <td width="23%">&nbsp;Total de registros:&nbsp;<%=BCICF.getTotalRegistros()%></td>
                                        <td width="11%" >Visualizar:</td>
                                        <td width="11%"><select name="limit" >
                                            <%for(i=15; i<= 150 ; i+=15){%>
                                            <%if(i==BCICF.getLimit()){%>
                                            <option value="<%=i%>" selected><%=i%></option>
                                            <%}else{%>
                                            <option value="<%=i%>"><%=i%></option>
                                            <%}
                                                      if( i >= BCICF.getTotalRegistros() ) break;
                                                    %>
                                            <%}%>
                                            <option value="<%= BCICF.getTotalRegistros()%>">Todos</option>
                                          </select>                                        </td>
                                        <td width="7%">&nbsp;P&aacute;gina:</td>
                                        <td width="12%"><select name="paginaSeleccion" >
                                            <%for(i=1; i<= BCICF.getTotalPaginas(); i++){%>
                                            <%if ( i==BCICF.getPaginaSeleccion() ){%>
                                            <option value="<%=i%>" selected><%=i%></option>
                                            <%}else{%>
                                            <option value="<%=i%>"><%=i%></option>
                                            <%}%>
                                            <%}%>
                                          </select>                                        </td>
                                        <td width="10%" class="text-globales"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                      </tr>
                                  </table></td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td height="38"><table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
                        <tr class="fila-encabezado">
                          <td width="2%" >&nbsp;</td>
                          <td width="3%" >&nbsp;</td>
                          <td width="4%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
                          <td width="56%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
                          <td width="23%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
                          <td width="12%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
                        </tr>
                        <%int r = 0;
   List ClientesIndicadoresClientes = BCICF.getClientesIndicadoresClientesList();
   Iterator iterClientesIndicadoresClientes   = iterClientesIndicadoresClientes = ClientesIndicadoresClientes.iterator();
   while(iterClientesIndicadoresClientes.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterClientesIndicadoresClientes.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
                        <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
                          <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/apps/accessories-text-editor.png" width="18" height="18" onClick="confirmarUpd(<%=sCampos[0]%>)"></td>
                          <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/status/image-missing.png" width="18" height="18" onClick="confirmarDel(<%=sCampos[0]%>);"></td>
                          <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
                          <td class="fila-det-border" ><%=sCampos[1]%> - <%=sCampos[2]%>&nbsp;</td>
                          <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
                          <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
                        </tr>
                        <%
   }%>
                      </table></td>
                    </tr>
                                    </table></td>
              </tr>
          </table>
       </td>
      </tr>
    </table>
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

