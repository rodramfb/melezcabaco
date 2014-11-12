<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: bacoTmCategorizaciones
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 04 17:01:51 ART 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.*" %>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BBTCF"  class="ar.com.syswarp.web.ejb.BeanBacoTmCategorizacionesFrm"   scope="page"/>
<head>
 <title></title>
<link rel = "stylesheet" href = "<%= pathskin %>">
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript" src="vs/overlib/overlib.js"></script>
<script>
function setCategoriaOpener(idcategoria, categoria){
   if(categoria != ""){
     if(opener.document.frm.idcategoria){
	   opener.document.frm.idcategoria.value = idcategoria;
	   opener.document.frm.categoria.value = categoria;
	 }
   }
}

function confirmar(){
  if(document.frm.categoria.value != '')
    return confirm("Confirma Recategorización?") ; 
	
  return true;
}
</script>
</head>

 <jsp:setProperty name="BBTCF" property="*" />
 <% 
 String titulo = BBTCF.getAccion().toUpperCase() + " DE CATEGORIZACIONES" ;
 BBTCF.setResponse(response);
 BBTCF.setRequest(request);
 BBTCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BBTCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BBTCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BBTCF.ejecutarValidacion();
 %>

<BODY  onLoad="setCategoriaOpener('<%= BBTCF.getIdcategoria() %>', '<%= str.esNulo(BBTCF.getCategoria()) %>')">

<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>

<form action="bacoTmCategorizacionesFrm.jsp" method="post" name="frm" onSubmit="return confirmar();">
<input name="accion" type="hidden" value="<%=BBTCF.getAccion()%>" >
   <table width="90%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;<input name="idcliente" type="hidden" id="idcliente" value="<%=BBTCF.getIdcliente()%>"></td>
                <td class="fila-det-border"><jsp:getProperty name="BBTCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="21%" class="fila-det-border">Asignar Categoria: (*)                
                <input name="idcategoria" type="hidden" id="idcategoria" value="<%=BBTCF.getIdcategoria()%>"></td> 
              <td width="79%" class="fila-det-border">
			  <table width="23%" border="0">
			<tr class="fila-det-border">
			<td width="61%" ><input name="categoria" type="text" class="campo" id="categoria" value="<%=BBTCF.getCategoria()%>" size="30" readonly></td>
			<td width="39%"><img src="../imagenes/default/gnome_tango/actions/filefind.png" width="18" height="18" onClick="abrirVentana('../Clientes/lov_categoriaSocio.jsp', '', 600, 250)" style="cursor:pointer"></td>
			</tr>
			</table>                
			</td>
              </tr>
              <tr class="text-catorce">
                <td class="fila-det-border">HISTORICO</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td colspan="2" class="fila-det-border"><table width="100%" border="1" cellspacing="0" cellpadding="0"  >
                  <tr class="fila-det-bold">
                    <td width="74%"  >Categoria</td>
                    <td width="26%"  ><div align="center">Fecha Hasta</div></td>
                  </tr>
                 <%int r = 0;
				   Iterator iterBacoTmCategorizaciones   = null;
				   List BacoTmCategorizaciones = new java.util.ArrayList();
				   BacoTmCategorizaciones= BBTCF.getListBacoTmCategorizaciones();
				   iterBacoTmCategorizaciones = BacoTmCategorizaciones.iterator();   				  
				   while(iterBacoTmCategorizaciones.hasNext()){
					  ++r;
					  String[] sCampos = (String[]) iterBacoTmCategorizaciones.next(); 
					  %>
                  <tr  >
                    <td class="fila-det" ><%=sCampos[3]%>&nbsp;</td>
                    <td class="fila-det" ><div align="center"><%= str.esNulo(sCampos[4]).equals("") ? "" : Common.setObjectToStrOrTime(java.sql.Date.valueOf(sCampos[4]), "JSDateToStr")%>&nbsp;</div></td>
                  </tr>
                  <%
				   }%>
                                </table></td>
              </tr>
              <tr class="fila-det">
                <td height="47" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;<input name="validar" type="submit" value="Categorizar Cliente" class="boton"></td>
				<td class="fila-det-border">&nbsp;<input name="validar" type="button" value="Cerrar" class="boton" onClick="window.close()"></td>
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

