<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: rrhhpersonal
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Wed Apr 22 09:44:28 ACT 2009 
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
<jsp:useBean id="BRF"  class="ar.com.syswarp.web.ejb.BeanRrhhPersonalConceptosAsocia"   scope="page"/>
<head>
 <title></title>
 <link rel = "stylesheet" href = "../imagenes/default/erp-style.css">
 <link rel = "stylesheet" href = "<%= pathskin %>">
 <script language="JavaScript" src="<%=pathscript%>/forms.js"></script>
 <script language="JavaScript" src="../vs/scripts/overlib.js"></script>

 <script>
  function callOverlib(leyenda){
  //  overlib(leyenda, STICKY, CAPTION, 'MAS INFO',TIMEOUT,5000,HAUTO,VAUTO,WIDTH,350,BGCOLOR, '#DBDEEE', CAPCOLOR, '#FF0000');
    overlib(leyenda, STICKY, CAPTION, 'MAS INFO',TIMEOUT,5000,HAUTO,WIDTH,350,BGCOLOR, '#9999CC', CAPCOLOR, '#FF0000', ABOVE); 
  }
	
	function seleccionar(objBtn){
	  chk= objBtn.value.toLowerCase() == 'seleccionar todos' ? true : false;
		
	  var objChk =  eval ('document.frm.idconcepto' + objBtn.name);
		
		if(objChk){
		  if(objChk.length){
			  for(var i=0;i<objChk.length;i++){
				  objChk[i].checked = chk;
				}
			}
			else {
			   objChk.checked = chk;
			}
		}
   
	  if(chk) objBtn.value = 'Seleccionar Ninguno';
		else  objBtn.value = 'Seleccionar Todos';

	}
 </script>
</head>

 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BRF" property="*" />
<% 
 int totalConceptos = 4;
 int totalList = 0;
 
 Hashtable htRestringido = new Hashtable();
 int c = 0;
 Iterator iterConceptos; 
 
 String[] tituCol = new String[10]; 
 String titulo = "ADMINISTRACION DE CONCEPTOS POR LEGAJO " ;
 
 
 BRF.setResponse(response);
 BRF.setRequest(request);
 BRF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BRF.setUsuarioact( session.getAttribute("usuario").toString() );
 BRF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BRF.ejecutarValidacion();
//  co.idconcepto,co.concepto,co.imprime,co.formula,co.idctacont, 
//  co.idtipoconcepto,tc.tipoconcepto,co.idtipocantidadconcepto, cc.tipocantidadconcepto 
tituCol[0] = "C.Concepto";
tituCol[1] = "Concepto";
tituCol[2] = "Imprime";
tituCol[3] = "Formula";
tituCol[4] = "Cuenta";
tituCol[5] = "C.T.Concepto";
tituCol[6] = "Tipo Concepto";
tituCol[7] = "C.T.Cant. Concepto";
tituCol[8] = "Tipo Cant. Concepto";
 %> 


<BODY >
<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<div id="popupcalendar" class="text"></div>


<form action="rrhhPersonalConceptosAsocia.jsp" method="post" name="frm">
   <input name="accion" type="hidden" value="" >
   <input name="legajoReplicar" type="hidden" id="legajoReplicar" value="" >
   <table width="95%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
         <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
            <tr class="text-globales">
              <td>&nbsp;<%= titulo %></td>
            </tr>
         </table> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BRF" property="mensaje"/>&nbsp;</td>
              </tr>
			  
			  <tr class="fila-det-bold">
                <td width="42%" height="24" class="fila-det-border">Legajo: <%=BRF.getLegajo()%>
                <input name="legajo" type="hidden" value="<%=BRF.getLegajo()%>" class="campo" size="18" maxlength="18"  ></td>
            <td width="58%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det-bold">
                <td width="42%" height="21" class="fila-det-border">Apellido y Nombre: <%=BRF.getApellido()%></td>
                <td width="58%" class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td>
              </tr>
              <tr class="text-dos-bold">
                <td class="fila-det-border">Copiar Desde Legajo </td>
                <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/apps/kuser.png" width="22" height="22" onClick="abrirVentana('rrhhPersonalConceptosReplica.jsp', 'copiar', 750, 500)" style="cursor:pointer"></td>
              </tr>
              <tr class="fila-det">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border">&nbsp;</td> 
              </tr>
              <tr class="text-dos-bold">
                <td class="fila-det-border"><img src="../imagenes/default/gnome_tango/actions/gtk-goto-first-rtl.png" width="18" height="18">  Conceptos Asociados </td>
                <td class="fila-det-border"><input name="eliminar" type="submit" class="boton" id="eliminar" onClick="document.frm.accion.value = 'eliminar'" value="Eliminar Selecci&oacute;n">
                <input name="_asoc" type="button" class="boton" id="_asoc" onClick="seleccionar(this)" value="Seleccionar Todos"></td>
              </tr>
              <tr class="fila-det">
                <td height="52" colspan="2" class="fila-det-border">
				
			
				<!--            ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::            -->
					<table width="100%" border="0" cellpadding="0" cellspacing="1" >
				<%
				

				   if(! BRF.getListConceptosAsociados().isEmpty()){	
					   htRestringido.put("5", "5");
					   htRestringido.put("7", "7");
					   
					   iterConceptos = BRF.getListConceptosAsociados() .iterator();
						 totalList = BRF.getListConceptosAsociados() .size();
	
					   
					   while( iterConceptos.hasNext() ){
							 --totalList;
							 String masInfo = "";
							 String [] datos= (String [] )iterConceptos.next();
							 for(int m=0;m<datos.length; m++) {
								 if(htRestringido.containsKey(m + "")) continue;
								 masInfo += "<strong>" + tituCol[m] + ": </strong>" + Common.setNotNull(datos[m]) + "<br>";
							 }
							 if(c==0){  
						 %>					  
							 <tr class="fila-det">
							 <% 
							 } 
							 c++;
							 %>
								 <td width="5%" class="fila-det-border"><input name="idconcepto_asoc" type="checkbox" class="campo" id="idconcepto_asoc" value="<%= datos[0] %>" >&nbsp;</td>
								<td class="fila-det-border">&nbsp;<img src="../imagenes/default/gnome_tango/categories/applications-other.png" width="15" height="15"  onClick="callOverlib('<%=masInfo%>')"  title="Click para ver más info." style="cursor:pointer"> - <%= datos[1] %></td>	
						<% 
							 if(c==totalConceptos){
								 c=0; 
						 %>
								</tr>
						 <%				
							
							}
								else if(totalList==0){
								for(int z=(totalConceptos-c);z>0;z--){
									%>
									<td class="fila-det-border">&nbsp;</td>
									<td class="fila-det-border">&nbsp;</td>	
									<%
								} 
								
								%>
								</tr>
							 <%
								}    
							 }
				   }
				   else{
						    %>
					  <tr class="fila-det-rojo" >							
						  <td class="fila-det-border" colspan="2">&nbsp;No Existen Conceptos Asociados al Legajo.</td>
					  </tr>						  
							<%				   
				   }
				   
				 %>					  
				    </table>				  
					
					
					<!--            ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::            -->				</td>
              </tr>
			  
	           <tr class="text-dos-bold">
                <td class="fila-det-border"> <img src="../imagenes/default/gnome_tango/actions/go-first.png" width="18" height="18"> Conceptos Pendientes de Asociar </td>
               <td class="fila-det-border"><input name="alta" type="submit" class="boton" id="alta"  onClick="document.frm.accion.value = 'alta'" value="Asociar Selecci&oacute;n">
                <input name="_pend" type="button" class="boton" id="_pend" onClick="seleccionar(this)" value="Seleccionar Todos"></td>
              </tr>		  
              <tr class="fila-det">
                <td height="52" colspan="2" class="fila-det-border">
				
			
				<!--            ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::            -->
					<table width="100%" border="0" cellpadding="0" cellspacing="1" >
				<%
				   if(! BRF.getListConceptosSinAsociar().isEmpty()){					
						 htRestringido.put("5", "5");
						 htRestringido.put("7", "7");
						 
						 iterConceptos = BRF.getListConceptosSinAsociar() .iterator();
						 totalList = BRF.getListConceptosSinAsociar() .size();
						 //String masInfo = "";
							c = 0;
						 while( iterConceptos.hasNext() ){
							 --totalList;				   
								 String masInfo = "";
							 String [] datos= (String [] )iterConceptos.next();
							 for(int m=0;m<datos.length; m++) {
									if(htRestringido.containsKey(m + "")) continue;
							    masInfo += "<strong>" + tituCol[m] + ": </strong>" + Common.setNotNull(datos[m]) + "<br>";
							 }
							 if(c==0){  
					 %>					  
							 <tr class="fila-det">
						 <% 
							 } 
							 c++;
						 %>
							 <td width="5%" class="fila-det-border"><input name="idconcepto_pend" type="checkbox" class="campo" id="idconcepto_pend" value="<%= datos[0] %>">&nbsp;</td>
							<td class="fila-det-border">&nbsp;<img src="../imagenes/default/gnome_tango/categories/package_settings.png" width="15" height="15"  onClick="callOverlib('<%=masInfo%>')"  title="Click para ver más info." style="cursor:pointer"> - <%= datos[1] %></td>	
					<% 
							 if(c==totalConceptos){
								 c=0;  
					 %>
							</tr>
					 <%				
							}
							else if(totalList==0){
								for(int z=(totalConceptos-c);z>0;z--){
									%>
								<td class="fila-det-border">&nbsp;</td>
								<td class="fila-det-border">&nbsp;</td>	
								<%
							} 
								%>
							</tr>
							 <%							
							 
							 }    
						 }
				   }
				   else{
						    %>
					  <tr class="fila-det-rojo" >							
						  <td class="fila-det-border" colspan="2">&nbsp;No Existen Conceptos Pendientes de  Asociar al Legajo.</td>
					  </tr>						  
							<%				   
				   }
				   
				 %>			  
			      </table>				  <!--            ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::            -->				</td>
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

