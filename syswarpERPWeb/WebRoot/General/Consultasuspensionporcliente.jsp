<%@page language="java"%>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: pedidos_cabe
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Jan 02 09:51:28 GMT-03:00 2007 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias 


*/ 

%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="ar.com.syswarp.api.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ page import="java.util.Iterator" %> 
<%@ include file="session.jspf"%>
<% 
try{
int i = 0;

Iterator iterConsulta1   = null;

Strings str = new Strings();
String color_fondo ="";
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
 String idempresa = session.getAttribute("empresa").toString() ;
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<%-- INSTANCIAR BEAN --%>
	<jsp:useBean id="BPF"
		class="ar.com.syswarp.web.ejb.BeanConsultaSuspensionporCliente" scope="page" />
	<head>
		<title>Consulta Suspensiones por Cliente</title>
		 <link rel = "stylesheet" href = "<%= pathskin %>">
		<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>
		<link rel="stylesheet" href="<%=pathscript%>/calendar/calendar.css">
		<script language="JavaScript" src="vs/forms/forms.js"></script>
    <script language="JavaScript" src="../vs/scripts/overlib.js"></script>
		
		<script>

    function setFBaja(idcodigo, anio, mes){
    
      if(confirm("Confirma cancelar la suspensión de ER para el pedríodo: " + mes + " - " + anio + " ? ")){
        document.frm.idcodigo.value = idcodigo;
        document.frm.accion.value = 'bajalogica';
        document.frm.submit();
      }

    }
 
		</script>
		 
		<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
	</head>
	<BODY>
    <DIV ID="overDiv" STYLE="position:absolute; visibility:hide; z-index:1;"></DIV>
		<div id="popupcalendar" class="text"></div>
		<%-- EJECUTAR SETEO DE PROPIEDADES --%>
		<jsp:setProperty name="BPF" property="*" />
		<% 
 String titulo = "SUSPENSIONES DE CLIENTE: " +  BPF.getCliente()  ;
	
		
		
 BPF.setResponse(response);
 BPF.setRequest(request);
 BPF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BPF.setUsuarioact( session.getAttribute("usuario").toString() );
 BPF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() )); 
 BPF.ejecutarValidacion();
 java.util.List Consulta1 = new java.util.ArrayList();
 Consulta1 = BPF.getMovimientosList();
 

 iterConsulta1 = Consulta1.iterator();
 
 %>
		<form action="Consultasuspensionporcliente.jsp" method="post" name="frm">



							<tr class="text-globales">
								<td>
									<table width="100%" border="0"  cellpadding="0"
										cellspacing="0" class="text-globales">
										<tr>
											<td><%= titulo%></td>
										</tr>
								  </table>
									<table width="100%" border="0"  cellpadding="0"	cellspacing="0">
										<tr class="fila-det-bold-rojo">
											<td class="fila-det-bold-rojo">&nbsp;<jsp:getProperty name="BPF" property="mensaje" /></td>
										</tr>
								  </table>
									
							


	

 <!-- detalle -->  
<table width="100%" border="0" cellspacing="1" cellpadding="1" name="rsTable"   >
  <tr class="fila-encabezado">
    <td width="5%">&nbsp;</td>  
     <td width="5%">A&ntilde;o</td>
     <td width="7%">Mes</td>
     <td width="57%">Motivo</td>
     <td width="22%">F. Baja </td>
     <td width="4%">&nbsp;</td>
  </tr>

  <%int r = 0;
   while(iterConsulta1.hasNext()){
      ++r;
      String[] sCampos = (String[]) iterConsulta1.next(); 
      String imagen ="icon-truck.gif";
      
      if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
      else color_fondo = "fila-det-verde";%>
   <tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" >
     <td class="fila-det-border"><div align="center"><% if(Common.setNotNull(sCampos[4]).equals("")){%><img src="../imagenes/default/gnome_tango/status/image-missing.png" width="18" height="18" onClick="setFBaja(<%=sCampos[0]%>, <%=sCampos[1]%>, '<%=sCampos[2]%>')" title="Anular suspensión.">
       <% }else{ %><img src="../imagenes/default/gnome_tango/actions/lock.png" width="18" height="18"  title="Suspensión anulada.">
       <% } %></div></td> 
      <td height="21" class="fila-det-border">&nbsp;<%=sCampos[1]%></td>   
      <td class="fila-det-border">&nbsp;<%=sCampos[2]%></td>  
      <td class="fila-det-border">&nbsp;<%=Common.setNotNull(sCampos[3])%></td>
      <td class="fila-det-border">&nbsp;<%=!Common.setNotNull(sCampos[4]).equals("") ? Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(sCampos[4]), "JSTsToStrWithHM") : ""%></td>
      <td class="fila-det-border"><div align="center"><a href="javascript:void(0);" onMouseOver="return overlib('<%="<strong>Usuario Alta:</strong> " + str.esNulo(sCampos[5]) + "<br><strong>Usuario Modificaci&oacute;n:</strong> " + str.esNulo(sCampos[6]) + "<br><strong>Fecha Alta:</strong>" + Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(str.esNulo(sCampos[7])), "JSTsToStrWithHM" )  + "<br><strong>Fecha Modificaci&oacute;n:</strong> " + (!str.esNulo(sCampos[8]).equals("")  ?  Common.setObjectToStrOrTime(java.sql.Timestamp.valueOf(str.esNulo(sCampos[8])), "JSTsToStrWithHM" )   : ""   )%>  ', STICKY, CAPTION, 'AUDITORIA',  LEFT, OFFSETX, 0, OFFSETY, 0, DELAY, 5 )" onMouseOut="nd();"><img src="../imagenes/default/gnome_tango/apps/config-users.png" width="18" height="18" style="cursor:pointer" border="0"></a></div></td>
   </tr>
   <%
   }%>
  <!-- final -->  
   </table>

						<input name="accion" type="hidden" value="" >
						<input name="idcodigo" type="hidden" value="false" >
            <input name="primeraCarga" type="hidden" value="false" >
            <input name="idcliente" type="hidden" value="<%= BPF.getIdcliente() %>" >
		</form>

	<p>&nbsp;</p> 
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

