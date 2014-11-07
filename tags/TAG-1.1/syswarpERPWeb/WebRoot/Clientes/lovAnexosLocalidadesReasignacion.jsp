<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: vClientesAnexosLocalidades
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Mon Aug 09 15:06:37 ART 2010 
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
String titulo = "EXPRESO - ZONA";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterVClientesAnexosLocalidades   = null;
int totCol = 12; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BVCALA"  class="ar.com.syswarp.web.ejb.BeanAnexosLocalidadesReasignacion"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BVCALA" property="*" />
<%
 BVCALA.setResponse(response);
 BVCALA.setRequest(request);
  BVCALA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));  
 BVCALA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
<script>

function bajarDatos(idanexolocalidad, idexpreso, expreso, idzona, zona, idlocalidad, localidad, idprovincia, provincia, cp){
	for(var i = 0; i<10;i++)
		if(opener.document.forms[i].idanexolocalidadDestino)
			break;
    if(opener.document.forms[i].idanexolocalidadDestino)			
	  opener.document.forms[i].idanexolocalidadDestino.value = idanexolocalidad;	
    if(opener.document.forms[i].idexpresoDestino)			
	  opener.document.forms[i].idexpresoDestino.value = idexpreso;	
    if(opener.document.forms[i].expresoDestino)					
	  opener.document.forms[i].expresoDestino.value = expreso;	
    if(opener.document.forms[i].idzonaDestino)				
	  opener.document.forms[i].idzonaDestino.value = idzona;		
    if(opener.document.forms[i].zonaDestino)				
	  opener.document.forms[i].zonaDestino.value = zona;			
    if(opener.document.forms[i].idlocalidadDestino)				
	  opener.document.forms[i].idlocalidadDestino.value = idlocalidad;
    if(opener.document.forms[i].localidadDestino)				
	  opener.document.forms[i].localidadDestino.value = localidad; 
    if(opener.document.forms[i].idprovinciaDestino)				
	  opener.document.forms[i].idprovinciaDestino.value = idprovincia;
    if(opener.document.forms[i].provinciaDestino)				
	  opener.document.forms[i].provinciaDestino.value = provincia; 
    if(opener.document.forms[i].cpostalDestino)			
      opener.document.forms[i].cpostalDestino.value = cp; 
	window.close();
}

</script> 
</head>
<%
// titulos para las columnas
tituCol[0] = "C.Anexo";
tituCol[1] = "C.E-Zona";
tituCol[2] = "C.Expr.";
tituCol[3] = "Expreso";
tituCol[4] = "idzona";
tituCol[5] = "Zona";
tituCol[6] = "idlocalidad";
tituCol[7] = "Localidad";
tituCol[8] = "CP";
tituCol[9] = "idProv";
tituCol[10] = "Provincia";
java.util.List VClientesAnexosLocalidades = new java.util.ArrayList();
VClientesAnexosLocalidades= BVCALA.getVClientesAnexosLocalidadesList();
iterVClientesAnexosLocalidades = VClientesAnexosLocalidades.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lovAnexosLocalidadesReasignacion.jsp" method="POST" name="frm">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" background="imagenes/dialogtop.gif">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td colspan="2"  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="3%" height="38">&nbsp;</td>
                   <td width="97%">
                      <table width="100%" border="0">
                         <tr>
                           <td width="6%" height="26" class="text-globales">Buscar</td>
                           <td width="22%">
                              <input name="ocurrencia" type="text" value="<%=BVCALA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                           </td>
                           <td width="72%">
                             <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr class="text-globales">
                                          <td width="1%" height="19">&nbsp; </td>
                                          <td width="23%">&nbsp;Total de registros:&nbsp;<%=BVCALA.getTotalRegistros()%></td>
                                          <td width="11%" >Visualizar:</td>
                                          <td width="11%">
                                             <select name="limit" >
                                                <%for(i=15; i<= 150 ; i+=15){%>
                                                    <%if(i==BVCALA.getLimit()){%>
                                                        <option value="<%=i%>" selected><%=i%></option>
                                                    <%}else{%>
                                                        <option value="<%=i%>"><%=i%></option>
                                                    <%}
                                                      if( i >= BVCALA.getTotalRegistros() ) break;
                                                    %>
                                                <%}%>
                                                <option value="<%= BVCALA.getTotalRegistros()%>">Todos</option>
                                             </select>                                          </td>
                                          <td width="7%">&nbsp;P&aacute;gina:</td>
                                          <td width="12%">
                                             <select name="paginaSeleccion" >
                                                <%for(i=1; i<= BVCALA.getTotalPaginas(); i++){%>
                                                    <%if ( i==BVCALA.getPaginaSeleccion() ){%>
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
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BVCALA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" >&nbsp;</td>
     <td width="6%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="24%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="16%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
     <td width="29%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[10]%></td>
     <td width="22%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[7]%></td>
    </tr>
   <%int r = 0;
   while(iterVClientesAnexosLocalidades.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterVClientesAnexosLocalidades.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
	  /*
tituCol[0] = "C.Anexo";
tituCol[1] = "C.E-Zona";
tituCol[2] = "C.Expr.";
tituCol[3] = "Expreso";
tituCol[4] = "idzona";
tituCol[5] = "Zona";
tituCol[6] = "idlocalidad";
tituCol[7] = "Localidad";
tituCol[8] = "CP";
tituCol[9] = "idProv";
tituCol[10] = "Provincia";	 
(idanexolocalidad, idexpreso, expreso, idzona, zona, idlocalidad, localidad, idprovincia, provincia, cp) */
	  %>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" onClick="bajarDatos('<%=sCampos[0]%>', '<%=sCampos[2]%>', '<%=sCampos[3]%>', '<%=sCampos[4]%>', '<%=sCampos[5]%>', '<%=sCampos[6]%>', '<%=sCampos[7]%>', '<%=sCampos[9]%>', '<%=sCampos[10]%>', '<%=sCampos[8]%>' )"></td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[10]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[7]%>&nbsp;</td>
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

