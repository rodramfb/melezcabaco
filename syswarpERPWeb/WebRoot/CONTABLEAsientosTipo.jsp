<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
/*
Programa CONTABLEccontables.jsp
Objetivo: Grilla de AsientosTipo contables
Sistema : Syswarp ERP
Copyrigth: Syswarp S.R.L.
Fecha de creacion: 16/02/2006
Fecha de ultima modificacion: - 
*/

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.security.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.naming.directory.*" %>
<%@ page import="java.util.Iterator" %> 
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="ar.com.syswarp.validar.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="ar.com.syswarp.api.Common"%>

<%
Strings str = new Strings();
String pagina = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1 );
// captura de variables comunes
String codigo         = request.getParameter("codigo");
String buscar         = request.getParameter("txtbuscar");
String color_fondo ="";
String titulo = "Asientos Tipo";
String action = "CONTABLEAsientosTipo.jsp";
String formulario = "frmCONTABLEAsientosTipo.jsp";
String accion    = request.getParameter("accion");
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
int _nivel      = Integer.valueOf(session.getAttribute("nivelusuario").toString()).intValue();
int ejercicioActivo = Integer.valueOf(session.getAttribute("ejercicioActivo").toString()).intValue();
// variables de paginacion
int i = 0;
int totalPaginas = 0;
int posIni = 1;
int posFin = 1;
int rxp =0; 
int curPage = 0; 
Iterator iterAsientosTipo   = null;
int totCol = 10; // cantidad de columnas
int totReg = 0;  // total de registros

String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String ayudalink  = "help/ayudaAsientosTipo.html";  // link a las ayudas en linea de cada punto
String printlink  = "reportes/CONTABLEccontablesRPT.jsp";  // link a las ayudas en linea de cada punto
String usuario    = session.getAttribute("usuario").toString();

String cPgreg = request.getParameter("pgreg");
String cCurpage = request.getParameter("curpage");
	 
if (cPgreg==null)
   cPgreg= session.getAttribute("rxp").toString();    
if (cCurpage==null)
   cCurpage="1";  
	 
if (cPgreg!=null && !cPgreg.equalsIgnoreCase("")) session.setAttribute("rxp", cPgreg.toString());    
int pgRegistros = Integer.valueOf(cPgreg).intValue();
int curpage = Integer.valueOf(cCurpage).intValue();

%>
<html>
<head>
<title><%=titulo%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="../vs/scripts/overlib.js"></script>
<script language="JavaScript" src="forms.js"></script>
<script language="JavaScript">
/*
function mOvrOLD(src,clrOver) {
	if (!src.contains(event.fromElement)) {
		src.style.cursor = 'pointer';
		src.bgColor = clrOver;
	}
}

function mOutOLD(src,clrIn) {
	if (!src.contains(event.toElement)) {
		src.style.cursor = 'default';
		src.bgColor = clrIn;
	}
}
*/

function mOvr(src,clrOver) {
		src.style.cursor = 'pointer';
		src.bgColor = clrOver;
}

function mOut(src,clrIn) {
		src.style.cursor = 'default';
		src.bgColor = clrIn;
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function gotosite(site) {
if (site != "") {
  document.location="<%=action%>?pgreg="+site+"&curpage=<%=curpage%>&titulo=<%=titulo%>"
  }
}

function gotosite2(site) {
if (site != "") {
  document.location="<%=action%>?pgreg=<%=pgRegistros%>&curpage="+site+"&titulo=<%=titulo%>"
  }
}

function popupHelp()
 {   
   window.open ("<%=ayudalink%>","mywindow","status=1,toolbar=1");  
} 

function popupPrint()
 {   
   window.open ("<%=printlink%>","mywindow","status=1,toolbar=1");  
} 

function getRadioChecked(o) {
if (o.value != "") {
	if (o.checked == true )
		return true;
    for (i = 0; i < o.length; i++) {
       if (o[i].checked == true) {
           return true;
       }
    }
  }
 return false;
}

function validar(axion){
  var o = document.frm.codigo;
  var cod = 0;
  if (getRadioChecked(document.frm.codigo)){

     if (axion == "Modificacion"){
       document.frm.action = "<%=formulario%>?accion=Modificacion";
       document.frm.submit();
     }

     if (axion == "Baja"){
       if (confirm("¿Confirma eliminacion del registro seleccionado?" )){
         for (i = 0; i < o.length; i++){
           if(o[i].checked == true) {
             cod = o[i].value;
           }
         }
         document.frm.action = "<%=action%>?accion=Baja";
         document.frm.submit();
       }
     }
   }
  else{
    if (axion != "Imprimir"){
      alert("Atención:\n debe seleccionar alguno de los registros para poder operar");
    }
    else{
      if (confirm("¿Confirma generacion del reporte?")){
        document.frm.action = "instCompre.jsp?accion=Imprimir";
        document.frm.submit();
      }
    }
  }
}
</script>
</head>
<%
// titulos para las columnas
tituCol[0] = "";
tituCol[1] = "Cód.";
tituCol[2] = "Descripción Asiento Tipo";
tituCol[3] = "Audit.";
try{
	General general = Common.getGeneral();
	Contable contable = Common.getContable(); 	    
   if (accion != null){
	   String resultadoBaja ="";
      if (accion.equalsIgnoreCase("Baja")){		 
	      resultadoBaja = contable.AsientoTipoDel(ejercicioActivo, new Long(Long.parseLong(codigo)),new BigDecimal(session.getAttribute("empresa").toString() )); 
      }    
      if (resultadoBaja.equalsIgnoreCase("OK")){
         %><script>alert('Se Borro la Cuenta ' + <%=Long.parseLong(codigo)%> + ' en forma correcta');</script><%
      }
      else{  
         %><script>alert('<%=resultadoBaja%>');</script><%		     
	  }
   } 
	  
   if( _nivel == 0 || _nivel == -1){
      response.sendRedirect("errorPage.jsp?error=Acceso denegado a esta aplicacion"); 
   }   
   java.util.List AsientosTipo = new java.util.ArrayList();	 
   if (buscar==null ) buscar = "";
   if (buscar.trim().equals("")){	 
      AsientosTipo = contable.getAsientosTipoAll(new BigDecimal(session.getAttribute("empresa").toString() ));       
   }
   else{
	   AsientosTipo = contable.getAsientosTipoOcu( buscar.trim(),new BigDecimal(session.getAttribute("empresa").toString() ) );    
   }
   iterAsientosTipo = AsientosTipo.iterator();      
   totReg = AsientosTipo.size();   
   
   rxp = Integer.valueOf(cPgreg).intValue(); 
   curPage = Integer.valueOf(cCurpage).intValue();    
   totalPaginas = (totReg / rxp ) + 1;
   posIni = rxp * (curPage - 1) + 1;
   posFin = posIni + rxp - 1;
   
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('./imagenes/default/btn_add_over.gif')">
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="<%=action%>" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
	<tr class="text-globales">
          <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                
            <td width="14%" height="38"> 
              <table width="36%" border="0">
							
                    <tr>    
										<%if (_nivel==2){%>                    
                			<td width="27%"><a href="<%=formulario%>?accion=Alta" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('alta','','./imagenes/default/btn_add_over.gif',1)"><img src="./imagenes/default/btn_add_norm.gif" name="alta" border="0"
											   onmouseover="return overlib('Haga click en el boton para ingresar al formulario de alta de un nuevo registro', STICKY, CAPTION, 'Alta de un nuevo registro',TIMEOUT,3000,HAUTO,VAUTO);"
		                           onmouseout="return nd();"></a></td>
									  <%}%>		
                			<td width="27%"><a href="javascript:validar('Modificacion');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('modificacion','','./imagenes/default/btn_edit_over.gif',1)"><img src="./imagenes/default/btn_edit_norm.gif" name="modificacion" border="0"
  										   onmouseover="return overlib('Haga click en el boton para modificar el registro, tenga en cuenta seleccionarlo previamente registro', STICKY, CAPTION, 'Modificacion de un registro',TIMEOUT,3000,HAUTO,VAUTO);"
		                         onmouseout="return nd();"></a></td>
										<%if (_nivel==2){%>                    				 
                      <td width="27%"><a href="javascript:validar('Baja');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('borrar','','./imagenes/default/btn_remove_over.gif',1)"><img src="./imagenes/default/btn_remove_norm.gif" name="borrar" border="0"
											   onmouseover="return overlib('Haga click en el boton eliminar el registro, tenga en cuenta seleccionarlo previamente y que dicho registro no tenga transacciones ya existentes', STICKY, CAPTION, 'Eliminacion de un registro',TIMEOUT,3000,HAUTO,VAUTO);"
		                         onmouseout="return nd();"></a></td>																						
										<%}%>				 
                    </tr>
									
              </table></td>
                <td width="47%"><div align="left">
                    
                <table width="70%" border="0">
                  <tr> 
                    <td width="9%" height="26" class="text-globales">Buscar</td>
                    <td width="32%"><div align="left"> 
                        <input name="txtbuscar" type="text" value="<%=buscar%>" id="txtbuscar" size="30" maxlength="30">
                      </div></td>
                    <td width="11%"> <div align="left"> 
                        <input name="buscar" type="submit" class="boton" id="buscar"  onmouseover="return overlib('Boton para realizar busquedas por ocurrencias. Si despues de hacer una busqueda desea eliminar el filtro debe quitar la ocurrencia y volver a presionar buscar', STICKY, CAPTION, 'Busquedas',TIMEOUT,3000,HAUTO,VAUTO);"	onmouseout="return nd();" value="Buscar">
                      </div></td>
                    <td width="4%">&nbsp;</td>
                    <td width="6%"><div align="center"><a href="javascript:popupHelp();" ><img src="imagenes/default/gnome_tango/apps/help.png" width="22" height="22" border="0"></a></div></td>
                    <td width="5%"><div align="center"><a href="javascript:popupPrint()"><img src="imagenes/default/gnome_tango/actions/gtk-print-preview.png" width="22" height="22" border="0"></a></div></td>
                    <td width="33%">&nbsp;</td>
                  </tr>
                </table>
                  <!-- </div> -->
                <td width="39%"><div align="right"><strong class="titlerev"><%=titulo%>&nbsp;&nbsp;&nbsp;</strong></div></td>
              </tr>
            </table>
          </td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
    <tr class="fila-encabezado">
      <td width="5%" >&nbsp;<%=tituCol[0]%> </td>
      <td width="8%" onClick="javascript:sortTable(<%=totCol+1-4%>, rsTable);"><%=tituCol[1]%></td>
      <td width="82%" onClick="javascript:sortTable(<%=totCol+1-4%>, rsTable);"><%=tituCol[2]%></td>
      <td width="5%"  onClick="javascript:sortTable(<%=totCol+1-4%>, rsTable);"><%=tituCol[3]%></td>
	    </tr>
    <%int r = 0;
    while(iterAsientosTipo.hasNext()){      
     ++r;
     String[] sCampos = (String[]) iterAsientosTipo.next(); 
	 if ( r >= posIni && r <= posFin ){
   	    // estos campos hay que setearlos segun la grilla 
	    String cmp_codigo = sCampos[0];
	           usuarioalt = str.esNulo( sCampos[2] ) ;
	           usuarioact = str.esNulo( sCampos[3] );
	           fechaalt   = str.esNulo( sCampos[4] );
   	         fechaact   = str.esNulo( sCampos[5] );	 
	    if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
          else color_fondo = "fila-det-verde"; 			
  %>
    
		<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
        <td class="fila-det-border" ><input type="radio" name="codigo" value="<%= sCampos[0]%>"></td>
				<td class="fila-det-border" >&nbsp;<%=cmp_codigo%></td>
				<td class="fila-det-border" >&nbsp;<%=sCampos[1]%></td>
        <td class="fila-det-border" > 
				<img src="imagenes/default/gnome_tango/apps/system-users.png" width="22" height="22"  border="0"
	      onclick="return overlib('<BR>Usuario de alta: <%=usuarioalt%></BR> <BR>Usuario ultima Modificacion: <%=usuarioact%></BR><BR>Fecha de Alta: <%=fechaalt%></BR><BR>Fecha ultima modificacion: <%=fechaact%></BR>', STICKY, CAPTION, 'Auditoria',TIMEOUT,5000,HAUTO,VAUTO);"
		    onmouseover="return overlib('haga click en la imagen para ver todos los datos de auditoria', STICKY, CAPTION, 'Datos de Auditoria',TIMEOUT,3000,HAUTO,VAUTO);"
		    onmouseout= "return nd();"><!--</a> </div> -->
      </td>
		 </tr>
    <%}%>
    <%}%>
  </table>

<!-- 
-->
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="fila-titulo-dos">
          <td width="0%" height="19"> <div align="center"></div></td>
          <td width="26%" >&nbsp;Total de registros:&nbsp;<%=totReg%></td>
          <td width="5%" >Registros:</td>
          <td width="4%" > <select name="pgReg" onChange="gotosite(this.options[this.selectedIndex].value)">
              <%for(i=15; i<= 150; i+=15){%>
                <%if(i==pgRegistros){%>
                  <option value="<%=i%>" selected><%=i%></option>
               <%}else{%>
                 <option value="<%=i%>"><%=i%></option>
               <%}%>
              <%}%>
              <option value="<%=totReg%>">Todos</option>
            </select> </td>
          <td width="5%">&nbsp;P&aacute;gina:</td>
          <td width="28%"> 
					<select name="curpage" onChange="gotosite2(this.options[this.selectedIndex].value)">
              <%for(i=1; i<= totalPaginas; i++){%>
              <%if (i==curpage){%>
                  <option value="<%=i%>" selected><%=i%></option>
              <%}else{%>
                  <option value="<%=i%>"><%=i%></option>
                <%}%>
              <%}%>
            </select> </td>
           <td width="5%" class="titulo-dos">&nbsp;</td>
          <td width="27%" class="titulo-dos">&nbsp;</td>
          
        </tr>
      </table>
		</td>
  </tr>
</table>
</form>
</body>
</html>