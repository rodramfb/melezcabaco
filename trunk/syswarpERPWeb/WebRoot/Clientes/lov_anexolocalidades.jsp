<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Grilla para la entidad: clientesAnexoLocalidades
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Dec 23 14:59:21 GMT-03:00 2008 
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
String titulo = "ANEXO LOCALIDADES";
// variables de entorno
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
// variables de paginacion
int i = 0;
Iterator iterClientesAnexoLocalidades   = null;
int totCol = 14; // cantidad de columnas
String[] tituCol = new String[totCol];
String usuarioalt = "";
String usuarioact = "";
String fechaalt   = "";
String fechaact   = "";
String usuario    = session.getAttribute("usuario").toString();
%>
<html>
<jsp:useBean id="BCALA"  class="ar.com.syswarp.web.ejb.BeanClientesAnexoLocalidadesLov"   scope="page"/>
<%-- EJECUTAR SETEO DE PROPIEDADES --%>
<jsp:setProperty name="BCALA" property="*" />
<%
 BCALA.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BCALA.setResponse(response);
 BCALA.setRequest(request);
 BCALA.ejecutarValidacion();
%>
<head>
<title><%=titulo%></title>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <link rel="stylesheet" href="<%=pathskin%>style.css">
 <link href="<%=pathskin%>" rel="stylesheet" type="text/css">
 <script language="JavaScript" src="<%=pathskin%>/overlib.js"></script>
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script >
function bajarDatos(idanexolocalidad, idexpreso, expreso, idzona, zona, idlocalidad, localidad, idprovincia, provincia, cp){
	for(var i = 0; i<10;i++)
		if(opener.document.forms[i].idlocalidad)
			break;
	opener.document.forms[i].idanexolocalidad.value = idanexolocalidad;	
	opener.document.forms[i].idexpreso.value = idexpreso;		
	opener.document.forms[i].expreso.value = expreso;	
	opener.document.forms[i].idzona.value = idzona;		
	opener.document.forms[i].zona.value = zona;			
	opener.document.forms[i].idlocalidad.value = idlocalidad;
	opener.document.forms[i].localidad.value = localidad; 
	opener.document.forms[i].idprovincia.value = idprovincia;
	opener.document.forms[i].provincia.value = provincia; 
  opener.document.forms[i].postal.value = cp; 
	window.close();
}
 </script> 
</head>
<%
// titulos para las columnas
tituCol[0] = "C.Anexo";
//tituCol[1] = "C.Exp-Zona";
tituCol[1] = "C.Exp-Dist.";
tituCol[2] = "C.Exp.";
//tituCol[3] = "Expreso";
tituCol[3] = "Zona";
tituCol[4] = "C.Dist.";
tituCol[5] = "Dist.";
tituCol[6] = "C.Loca.";
tituCol[7] = "Localidad";
tituCol[8] = "tarandexc";
tituCol[9] = "tarsoc1bulto";
tituCol[10] = "tarsocexc";
tituCol[11] = "cabeoinflu";
tituCol[12] = "norteosur";
tituCol[13] = "idempresa";
java.util.List ClientesAnexoLocalidades = new java.util.ArrayList();
ClientesAnexoLocalidades= BCALA.getClientesAnexoLocalidadesList();
iterClientesAnexoLocalidades = ClientesAnexoLocalidades.iterator();
%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<form action="lov_anexolocalidades.jsp" method="POST" name="frm">


<input name="idlocalidad" value="<%= BCALA.getIdlocalidad() %>" type="hidden"> 
<input name="localidad" value="<%= BCALA.getLocalidad() %>" type="hidden">
<input name="idprovincia" value="<%= BCALA.getIdprovincia() %>" type="hidden">
<input name="provincia" value="<%= BCALA.getProvincia() %>" type="hidden">
<input name="postal" value="<%= BCALA.getPostal() %>" type="hidden">

<table width="100%" border="0" cellspacing="0" cellpadding="0" class=color-tabletrim>
  <tr class="text-globales">
    <td width="100%" height="24" colspan="10" >
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                   <td  class="text-globales"><%=titulo%></td>
                </tr>
                <tr>
                  <td width="1%" height="38"><table width="100%" border="0">
                    <tr>
                      <td width="6%" height="26" class="text-globales">Buscar</td>
                      <td width="20%"><input name="ocurrencia" type="text" value="<%=BCALA.getOcurrencia()%>" id="ocurrencia" size="30" maxlength="30">                      </td>
                      <td width="74%"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr class="text-globales">
                                  <td width="22%" height="19">Total de registros:&nbsp;
                                    <%= BCALA.getTotalRegistros() %>                                  </td>
                                  <td width="10%">Visualizar</td>
                                  <td width="22%" >:
                                    <select name="limit" >
                                        <%for(i=15; i<= 150 ; i+=15){%>
                                        <%if(i==BCALA.getLimit()){%>
                                        <option value="<%=i%>" selected><%=i%></option>
                                        <%}else{%>
                                        <option value="<%=i%>"><%=i%></option>
                                        <%}
                                                      if( i >= BCALA.getTotalRegistros() ) break;
                                                    %>
                                        <%}%>
                                        <option value="<%= BCALA.getTotalRegistros()%>">Todos</option>
                                    </select></td>
                                  <td width="8%">P&aacute;gina:</td>
                                  <td width="15%"><select name="paginaSeleccion" >
                                      <%for(i=1; i<= BCALA.getTotalPaginas(); i++){%>
                                      <%if ( i==BCALA.getPaginaSeleccion() ){%>
                                      <option value="<%=i%>" selected><%=i%></option>
                                      <%}else{%>
                                      <option value="<%=i%>"><%=i%></option>
                                      <%}%>
                                      <%}%>
                                  </select></td>
                                  <td width="13%"><input name="ir" type="submit" class="boton" id="ir" value="  >>  "></td>
                                  <td width="10%" class="text-globales"><input name="atraz" type="button" class="boton" id="atraz" value="Volver" onClick="javascript:document.location='lov_localidades_anexos.jsp'">                                  </td>
                                </tr>
                            </table></td>
                          </tr>
                      </table></td>
                    </tr>
                  </table></td>
              </tr>
          </table>
      </td>
    </tr>
  </table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr >
   <td class="fila-det-bold-rojo"><jsp:getProperty name="BCALA" property="mensaje"/></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable" id=rsTable cols=<%=totCol+2-4%>  >
  <tr class="fila-encabezado">
     <td width="3%" >&nbsp;</td>
     <td width="9%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[0]%></td>
     <td width="11%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[1]%></td>
     <td width="26%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[2]%></td>
     <td width="23%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[3]%></td>
     <td width="5%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[4]%></td>
     <td width="23%" onClick="javascript:sortTable(<%=totCol-4%>, rsTable);"><%=tituCol[5]%></td>
    </tr>
   <%int r = 0;
   while(iterClientesAnexoLocalidades.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterClientesAnexoLocalidades.next(); 
      // estos campos hay que setearlos segun la grilla 
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";
			
			//  idanexolocalidad,    idexpreso,         expreso, idzona, zona, idlocalidad, localidad, idprovincia, provincia, cp
			// al.idanexolocalidad,al.idexpresozona, e.idexpreso, e.expreso, z.idzona, z.zona,al.idlocalidad, l.localidad 
			
			%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td class="fila-det-border" ><div align="center"><img src="../imagenes/default/gnome_tango/actions/down.png" width="18" height="18" onClick="bajarDatos('<%=sCampos[0]%>', '<%=sCampos[2]%>', '<%=sCampos[3]%>', '<%=sCampos[4]%>', '<%=sCampos[5]%>', '<%=BCALA.getIdlocalidad()%>', '<%=BCALA.getLocalidad()%>', '<%=BCALA.getIdprovincia()%>', '<%=BCALA.getProvincia()%>', '<%=BCALA.getPostal()%>' )"></div></td>
      <td class="fila-det-border" ><%=sCampos[0]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[1]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[2]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[3]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[4]%>&nbsp;</td>
      <td class="fila-det-border" ><%=sCampos[5]%>&nbsp;</td>
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

