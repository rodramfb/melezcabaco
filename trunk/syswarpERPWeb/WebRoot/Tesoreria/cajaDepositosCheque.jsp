<%@page language="java" %>
<%
 response.setHeader("Cache-Control", "no-cache");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader("Expires",0);
/* 
   Formulario de carga para la entidad: Cajaclearing
   Copyrigth(r) sysWarp S.R.L. 
   Fecha de creacion: Tue Aug 01 11:36:49 GMT-03:00 2006 
   Observaciones: 
      . Revisar los nombres de las clases instanciadas
      . Se prevee el primer campo como autonumerico
      . Se omiten los campos de auditorias


*/ 

%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.math.*"%> 
<%@ page import="java.util.*"%> 
<%@ page import="ar.com.syswarp.api.*" %>
<%@ include file="session.jspf"%>
<% 
try{
Strings str = new Strings();
String pathskin = session.getAttribute("pathskin").toString();
String pathscript = session.getAttribute("pathscript").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%-- INSTANCIAR BEAN --%>  
<jsp:useBean id="BCF"  class="ar.com.syswarp.web.ejb.BeanCajaDepositosChequeFrm"   scope="page"/>
<head>
 <title></title>
 <meta http-equiv="description" content="mypage">
 <link rel="stylesheet" href="<%=pathskin%>">
  <link rel="stylesheet" href="../imagenes/default/erp-style.css">
<script language="JavaScript" src="<%=pathscript%>/forms.js"></script> 
<script language="JavaScript" src="vs/calendar/calendarcode.js"></script>

<script>

function callCCDeposito(pagina){
  if(document.frm.identificadorDeposito.value == ''){
	alert ("Es necesario que seleccione Identificador a depositar.");
	return false;		  	
  }

  if(document.frm.modCCDeposito.value == 'false' ){
	alert ("El Identificador a Depositar no permite modificar Centros de Costos."); 
	return false;	  
  }  
  
  abrirVentana(pagina,'CC',750, 350);
}

function callCCBanco(pagina){
  if(document.frm.identificadorBanco.value == ''){
	alert ("Es necesario que seleccione Banco a depositar."); 
	return false;		  	
  }
  
  if(document.frm.modCCBanco.value == 'false' ){
	alert ("El Banco a Depositar no permite modificar Centros de Costos."); 
	return false;	  
  }
  
  abrirVentana(pagina,'CC',750, 350);
}


function buscarCheques(pagina){
  
  var objCmbTipoDeposito = document.frm.cmbTipoDeposito;
  
  if(document.frm.identificadorDeposito.value == ''){
    alert("Seleccione Identificador a Depositar.");
    return false;
  }
  
  if(objCmbTipoDeposito.selectedIndex == 0){
    alert("Seleccione tipo de depósito a efectuar.");
    return false;
  }
  


  if(objCmbTipoDeposito.options[objCmbTipoDeposito.selectedIndex].value == "CAUCIONADOS"){
	  if(document.frm.identificadorBanco.value == ''){
		alert("Seleccione Banco a Depositar.");
		return false;
	  } 
	  pagina +='&banco=' + document.frm.identificadorBanco.value; 
  }
  
  
  document.frm.tipodeposito.value = objCmbTipoDeposito.options[objCmbTipoDeposito.selectedIndex].value;
  objCmbTipoDeposito.disabled = true;
  pagina += '&tipodeposito=' + document.frm.tipodeposito.value;
  abrirVentana(pagina, 'CH', 700, 300);

}

function cambiarTipoDeposito(){
  if(confirm("Al cambiar tipo de depósito los datos que se hayan cargado se perderán.\nContinua de todos modos?")){
    document.frm.tipodeposito.value = '';
	document.frm.accion.value = 'cambiarTipoDeposito';
    document.frm.submit();
  }
}


function abrirDepositos(pagina){

	if(document.frm.identificadorDeposito.value != ''){
	  if(confirm("Al cambiar deposito perderá los cheques cargados. \nContinua de todos modos?")){
		 document.frm.accion.value = 'cambiarTipoDeposito';					  
		 document.frm.submit();
	  } else return false;   
	}
	abrirVentana(pagina, 'deposito', 700, 300)
}

function abrirBancos(pagina){

	if(document.frm.tipodeposito.value == 'CAUCIONADOS'){
	  if(confirm("Al cambiar de banco perderá los cheques cargados. \nContinua de todos modos?")){
		 document.frm.accion.value = 'cambiarTipoDeposito';					  
		 document.frm.submit();
	  } else return false;   
	}
	abrirVentana(pagina, 'banco', 700, 300)
}
</script>
</head>
<BODY  topmargin="0" leftmargin="0" rightmargin="0">
<div id="popupcalendar" class="text"></div>
 <%-- EJECUTAR SETEO DE PROPIEDADES --%>
 <jsp:setProperty name="BCF" property="*" />
 <% 
 String titulo =  " DEPOSITOS DE CHEQUES " ;
 BCF.setResponse(response);
 BCF.setRequest(request);
 BCF.setSession(session);
 BCF.setUsuarioalt( session.getAttribute("usuario").toString() );
 BCF.setUsuarioact( session.getAttribute("usuario").toString() );
 BCF.setIdempresa( new BigDecimal( session.getAttribute("empresa").toString() ));
 BCF.ejecutarValidacion();
 %>
<form action="cajaDepositosCheque.jsp" method="post" name="frm"> 
<input name="accion" type="hidden" value="<%=BCF.getAccion()%>" >
<input name="primeraCarga" type="hidden" value="false" >

   <span class="fila-det-border">
   <input name="tesoFechaCaja" type="hidden" id="tesoFechaCaja" value="<%=BCF.getTesoFechaCaja()%>">
   </span>
   <table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center">
     <tr>
       <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr class="fila-det-bold-rojo">
                <td class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><jsp:getProperty name="BCF" property="mensaje"/>&nbsp;</td>
              </tr>
              <tr class="fila-det">
                <td width="23%" height="41" class="fila-det-border">Identificador a Depositar (*):  </td>
                <td width="77%" class="fila-det-border"><input name="identificadorDeposito" type="text" class="campo" id="identificadorDeposito" value="<%=BCF.getIdentificadorDeposito()%>" size="5" maxlength="3">
                     <input name="descripcionDeposito" type="text" class="campo" id="descripcionDeposito" value="<%=BCF.getDescripcionDeposito()%>" size="60" maxlength="150"  > <span class="fila-titulo-dos">
                  <input name="btnidentificadores" type="button" class="boton" id="btnidentificadores" style="cursor:pointer" onClick="abrirDepositos('lov_cajaIdentificadoresDepositosEfectivo.jsp?propio=N&tipomov=C&cmpCodigo=identificadorDeposito&cmpDescripcion=descripcionDeposito&cmpCC1=cc1Deposito&cmpCC2=cc2Deposito&cmpModCC=modCCDeposito')" value=">>">
                </span></td>
              </tr>
              <tr class="fila-det">    
                <td height="41" class="fila-det-border">Centros de Costos:  </td>
                <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="fila-det">
                      <td colspan="2">CC1</td>
                      <td colspan="2">CC2</td>
                    </tr>
                    <tr class="fila-det">
                      <td width="8%"><input name="cc1Deposito" type="text" class="campo" id="cc1Deposito" value="<%=BCF.getCc1Deposito()%>" size="5" maxlength="5" readonly ></td>
                      <td width="43%"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="callCCDeposito('../Contable/lov_centro_de_costos1.jsp?txtCod=cc1Deposito&txtDesc=descCc1Deposito')" style="cursor:pointer">
                      <input name="descCc1Deposito" type="hidden" class="campo" id="descCc1Deposito" value="<%=BCF.getDescCc1Deposito()%>" size="30" maxlength="100"  readonly>
                      <input name="modCCDeposito" type="hidden" class="campo" id="modCCDeposito" value="<%=BCF.isModCCDeposito()%>" size="30" maxlength="100"  readonly></td>
                      <td width="8%"><input name="cc2Deposito" type="text" class="campo" id="cc2Deposito" value="<%=BCF.getCc2Deposito()%>" size="5" maxlength="5" readonly ></td>
                      <td width="41%"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="callCCDeposito('../Contable/lov_centro_de_costos1.jsp?txtCod=cc2Deposito&txtDesc=descCc2Deposito')" style="cursor:pointer">
                      <input name="descCc2Deposito" type="hidden" class="campo" id="descCc2Deposito" value="<%=BCF.getDescCc2Deposito()%>" size="30" maxlength="1000" readonly ></td>
                    </tr>
                  </table></td>
              </tr>
              <tr class="fila-det">
                <td height="3" class="text-globales"></td>
                <td height="3" class="text-globales"></td>
              </tr>
              <tr class="fila-det"> 
                <td width="23%" height="41" class="fila-det-border">Banco a Depositar (*):  </td>
                <td width="77%" class="fila-det-border"><input name="identificadorBanco" type="text" class="campo" id="identificadorBanco" value="<%=BCF.getIdentificadorBanco()%>" size="5" maxlength="3">
                     <input name="descripcionBanco" type="text" class="campo" id="descripcionBanco" value="<%=BCF.getDescripcionBanco()%>" size="60" maxlength="150"  >
                  <span class="fila-titulo-dos">

                  <input name="btnbancos" type="button" class="boton" id="btnbancos" style="cursor:pointer" onClick="abrirBancos('lov_cajaIdentificadoresDepositosEfectivo.jsp?propio=S&tipomov=C&cmpCodigo=identificadorBanco&cmpDescripcion=descripcionBanco&cmpCC1=cc1Banco&cmpCC2=cc2Banco&cmpModCC=modCCBanco')" value="&gt;&gt;">
                </span></td>
              </tr>
              <tr class="fila-det"> 
                <td height="41" class="fila-det-border">Centros de Costos: </td>
                <td class="fila-det-border"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="fila-det">
                    <td colspan="2">CC1</td>
                    <td colspan="2">CC2</td>
                  </tr>
                  <tr class="fila-det">
                    <td width="8%"><input name="cc1Banco" type="text" class="campo" id="cc1Banco" value="<%=BCF.getCc1Banco()%>" size="5" maxlength="5" readonly  ></td>
                    <td width="43%"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="callCCBanco('../Contable/lov_centro_de_costos1.jsp?txtCod=cc1Banco&txtDesc=descCc1Banco')" style="cursor:pointer">
                    <input name="descCc1Banco" type="hidden" class="campo" id="descCc1Banco" value="<%=BCF.getDescCc1Banco()%>" size="30" maxlength="100" readonly="" >
                    <input name="modCCBanco" type="hidden" class="campo" id="modCCBanco" value="<%=BCF.isModCCBanco()%>" size="30" maxlength="100"  readonly></td>
                    <td width="8%"><input name="cc2Banco" type="text" class="campo" id="cc2Banco" value="<%=BCF.getCc2Banco()%>" size="5" maxlength="5" readonly ></td>
                    <td width="41%"><img src="../imagenes/default/gnome_tango/actions/edit-find.png" width="18" height="18" onClick="callCCBanco('../Contable/lov_centro_de_costos1.jsp?txtCod=cc2Banco&txtDesc=descCc2Banco')" style="cursor:pointer">
                    <input name="descCc2Banco" type="hidden" class="campo" id="descCc2Banco" value="<%=BCF.getDescCc2Banco()%>" size="30" maxlength="100"  readonly></td>
                  </tr>
                </table></td>
              </tr>
              <tr class="fila-det">
                <td height="3" class="text-globales"></td>
                <td height="3" class="text-globales"></td>
              </tr> 
              <tr class="fila-det">
                <td height="34" class="fila-det-border">Tipo de Deposito (*):  </td>
                <td class="fila-det-border">
				<select name="cmbTipoDeposito" id="cmbTipoDeposito" class="campo" <%= !BCF.getTipodeposito().equalsIgnoreCase("") ? "disabled" : "" %>>
                  <option value="">Seleccionar</option>
                  <option value="CARTERA" <%=BCF.getTipodeposito().equalsIgnoreCase("CARTERA") ? "selected" : "" %> >Cheques En Cartera</option>
                  <option value="CAUCIONADOS" <%=BCF.getTipodeposito().equalsIgnoreCase("CAUCIONADOS") ? "selected" : "" %>>Cheques Caucionados</option>
                  <option value="CAUCIONAR" <%=BCF.getTipodeposito().equalsIgnoreCase("CAUCIONAR") ? "selected" : "" %>>Caucionar</option>
                </select>
				<span class="fila-titulo-dos">
				<input name="btncheques" type="button" class="boton" id="btncheques" style="cursor:pointer" onClick="buscarCheques('lov_cajaChequesPendientes.jsp?cartera_mt=' + document.frm.identificadorDeposito.value + '&tipcart_mt=C')" value="&gt;&gt;">
				<input name="btncheques2" type="button" class="boton" id="btncheques2" style="cursor:pointer" onClick="cambiarTipoDeposito()" value="Cambiar tipo de dep&oacute;sito">
				<input name="tipodeposito" type="hidden" id="tipodeposito" value="<%=BCF.getTipodeposito()%>"> 
				</span></td>
              </tr>
              <tr class="fila-det">
                <td height="3" class="text-globales"></td>
                <td height="3" class="text-globales"></td>
              </tr>			  
              <tr class="fila-det">
                <td height="34" class="fila-det-border">Importe a Depositar: </td>
                <td class="fila-det-border"><input name="importeDeposito" type="text" class="campo" id="importeDeposito" value="<%=BCF.getImporteDeposito()%>" size="18" maxlength="12" readonly ></td>
              </tr>
              <tr class="fila-det"> 
                <td height="34" class="fila-det-border">&nbsp;</td>
                <td class="fila-det-border"><input name="validar" type="submit" value="Confirmar Dep&oacute;sito" class="boton"></td>
              </tr>
              <tr class="fila-det">
                <td height="43" colspan="2" >
				 <% 
			  Hashtable htChqDepositarOK = (Hashtable) session.getAttribute("htChqDepositarOK");
				if(htChqDepositarOK != null && !htChqDepositarOK.isEmpty()){
				   %>
				 <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1">
				   <tr class="text-globales">
				     <td height="26" colspan="9"><div align="center">CHEQUES A DEPOSITAR </div></td>
			       </tr>
				   <tr class="text-globales">
					 <td width="6%"><div align="center">Ide</div></td>
					 <td width="7%"><div align="center">CC1</div></td>
					 <td width="5%"><div align="center">CC2</div></td>
					 <td width="12%"><div align="center">N&uacute;mero</div></td>
					 <td width="38%"><div align="center">Detalle</div></td>
					 <td width="8%">Clearing</td>
					 <td width="11%"><div align="center">Fecha</div></td>
					 <!--td width="6%"><div align="center">Cuotas</div></td-->
					 <td width="7%"><div align="justify">Importe</div></td>
					 <td width="6%"><div align="center">Ctas. </div></td>
				   </tr>
				   <%   
							Enumeration en = htChqDepositarOK.keys();
								int element = 0;
								while (en.hasMoreElements()) {
									String key = (String) en.nextElement();
									String [] identificador = (String []) htChqDepositarOK.get(key); 
									/*
									System.out.println("***********************************" );
									System.out.println("KEY: " + key);
									for(int f=0;f<identificador.length;f++){
									  System.out.println("identificador[" +f+ "]: " + identificador[f]);
									}
									 */ 
									  %>
				   <tr class="fila-det">
					 <td height="22" class="fila-det-border">&nbsp;<%= key %>
						 <input  name="keyHashDatosIdentificador" type="hidden" value="<%= key %>"></td>
					 <td class="fila-det-border">&nbsp; <%= identificador[20] %></td>
					 <td class="fila-det-border">&nbsp; <%= identificador[21] %></td>
					 <td class="fila-det-border">&nbsp; <%= identificador[37] %></td>
					 <td class="fila-det-border">&nbsp; <%= identificador[29] %></td>
					 <td class="fila-det-border"><div align="center"> <%= identificador[32] %></div></td>
					 <td class="fila-det-border">&nbsp; <%= identificador[31] %></td>
					 <td class="fila-det-border"><div align="justify"> <%= identificador[28] %> </div></td>
					 <td align="center" valign="middle" class="fila-det-border"><div align="center"> <%= identificador[4] %> </div></td>
				   </tr>
				   <%
									element++;
						}%>
				 </table>
				 <% 
				}%>				
&nbsp;			</td>
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

