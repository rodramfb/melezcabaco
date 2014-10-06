<%@page import="javax.servlet.http.*"%>
<%@page import="java.security.*"%>
<%@page import="javax.naming.*"%>
<%@page import="javax.naming.directory.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.math.BigDecimal" %>

<%
	String  PlanAjuste   = request.getParameter("PlanAjuste");	
	if (PlanAjuste == null)  PlanAjuste = "";	
	//String pathskin = "imagenes/default/"; // cambiar
String pathskin = session.getAttribute("pathskin").toString();
	String color_fondo ="";
%>
<html>
<head>
<title>B&uacute;squeda de Indice de Ajustes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="scripts/forms/forms.js"></script>
<script language="JavaScript" src="scripts/overlib/overlib.js"></script>
<script language="JavaScript" src="vs/forms/forms.js"></script>
<script language="JavaScript">


function validarCampos() {

	if (document.forms[0].PlanAjuste.value == "") {
		alert("Debe ingresarse un Indice de Ajuste");	
		return false;
	}
	if (document.forms[0].PlanAjuste.value != "") {
	}
	document.forms[0].submit();
}
function passBack(cent_cost, d_cent_cost) {
  var i = 0;
   for(i=0;i<10;i++)
     if(opener.document.forms[i].indice)
       break;    
   opener.document.forms[i].indice.value = cent_cost;
   opener.document.forms[i].ano.value = d_cent_cost;
   close();
}
function Find_OnClick() {
   document.forms[0].submit();
}
</script>


<link rel="stylesheet" href="jmc.css" type="text/css">
<link rel = "stylesheet" href = "<%= pathskin %>">
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0">

<form action="lov_ContablePlanAjuste.jsp">

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0">
  <p align="left" class=text-seis>B&uacute;squeda de Indice de Ajuste</p>
<hr>

 

  <table width="59%" border="0" cellspacing="0" cellpadding="0" height="23">
    <tr> 
		  <td height="39" width="26%"> 
        <div class=text-seis>Indice de Ajuste</div>
      </td>
      <td width="52%" height="39"> 
			  <input type="text" name="PlanAjuste" size="25" value="<%=PlanAjuste%>">
      </td>
      <td height="39" width="3%"></td>
      <td height="39" width="19%"> 
        <input type="button" name="buscar" value="Buscar" onClick="validarCampos()">
      </td>
    </tr>
  </table>
	
	
<%  
if (!PlanAjuste.equals("*")) {
   java.util.List Ajuste =  new ArrayList();
   Iterator iterPlanAjuste=null;
   int totReg  = 0;
   try{
   	javax.naming.Context context = new javax.naming.InitialContext();
   	// INSTANCIAR EL MODULO CONTABLE 
   	Object object = context.lookup("Contable");
   	ContableHome sHome = (ContableHome) javax.rmi.PortableRemoteObject.narrow(object, ContableHome.class);
   	Contable repo =   sHome.create();   	      
   	Ajuste =  repo.getAjusteOcu(PlanAjuste, new BigDecimal(session.getAttribute("empresa").toString() ));
   	iterPlanAjuste = Ajuste.iterator();      
   	totReg = Ajuste.size();     
   }
   catch (Exception ex) {
     java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
     java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
     ex.printStackTrace(pw);
  }  


	boolean existenReg = false;
	boolean esPrimero = true;
	while (iterPlanAjuste.hasNext() ) {
  if (color_fondo.equals("fila-det-verde")) color_fondo = "fila-det";
  else color_fondo = "fila-det-verde"; 
   		String[] sCampos = (String[]) iterPlanAjuste.next(); 
   		String cmp_codigo = sCampos[0] ;
   		String cmp_descripcion = sCampos[1] ;
			String cmp_descripcion2 = sCampos[2] ;
			String cmp_descripcion3= sCampos[3] ;
   		existenReg = true;
		if (esPrimero) {
			esPrimero = false; %>
  <table width="100%"  cellPadding=1 cellSpacing=1 class=color-tabletrim >
    <tr class=color-columnheaders> 
      <td class="fila-encabezado">
      <td width="6%" height="13" class="fila-encabezado">Codigo</td>
      <td width="22%" height="13" class="fila-encabezado">A&ntilde;o</div></td>
			<td width="25%" height="13" class="fila-encabezado">Mes</div></td>
			<td width="44%" height="13" class="fila-encabezado">Indice</div></td>
    </tr>
    <% } %>
    	<tr onMouseOver="mOvr(this,this.className='fila-det-rojo');" onMouseOut="mOut(this,this.className='<%=color_fondo%>');" class="<%=color_fondo%>" scope="col" > 
      <td width="3%" class="fila-det-border" ><a href="javascript:passBack('<%=cmp_codigo%>', '<%=cmp_descripcion%>', '<%=cmp_descripcion2%>', '<%=cmp_descripcion3%>')"><img src="../imagenes/default/lupa.gif" width="21" height="17" border="0"></a></td>
      <td width="6%" class="fila-det-border" ><%=cmp_codigo%>&nbsp;</td>
      <td width="22%" class="fila-det-border" ><%=cmp_descripcion%>&nbsp;</td>
			<td width="25%" class="fila-det-border" ><%=cmp_descripcion2%>&nbsp;</td>
			<td width="44%" class="fila-det-border" ><%=cmp_descripcion3%>&nbsp;</td>
    <%	}
    if (!existenReg) { %>
    <p class=error>No existen registros para la Búsqueda</p>
    <%	}
			else { %>
  </table>
		<%	} 
	
		}

	%>
	<input type="hidden" name=" PlanAjuste" value="<%= PlanAjuste%>">

</form>

</body>
</html>