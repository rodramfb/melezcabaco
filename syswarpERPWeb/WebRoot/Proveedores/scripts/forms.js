
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

function confirmarBaja(formName){
  if(confirm("Confirma Baja ?")){
   document.forms[formName].accion.value = "baja";
   return true;
  }
  else return false; 
}

function mostrarLOV(pagina) {
	frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=600,height=350,status=yes');
	if (frmLOV.opener == null) 
		frmLOV.opener = self;
}	
function abreVentana(pagina, ancho, alto) {
	frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width='+ancho+',height='+alto+',status=yes');
	if (frmLOV.opener == null) 
		frmLOV.opener = self;
}	

function paginacion (paginaSeleccion){
  document.frm.paginaSeleccion.value=paginaSeleccion;
  document.frm.submit();
}

function llamarAccion(obj, id, comprobante, aplica, aplicado, frm){
	var mensaje = "";
	if(obj.name == 'baja'){
		mensaje = "Esta seguro que desea eliminar el comprobante: " + comprobante + "?";
		if(aplica.toUpperCase() == 'S') 
		  mensaje +="\nEl mismo aplica a otros documentos, ser� desaplicado."; 
		if(aplicado.toUpperCase() == 'S') 
		  mensaje +="\nEl mismo esta aplicado por otros documentos, ser�n desaplicados."; 
	  if(!confirm(mensaje))
		  return false;
	}
	
	if(obj.name == 'modificacion'){
		mensaje = "Esta seguro que desea modificar el comprobante: " + comprobante + "?";
		if(aplica.toUpperCase() == 'S') 
		  mensaje +="\nEl mismo aplica a otros documentos, ser� desaplicado."; 
		if(aplicado.toUpperCase() == 'S') 
		  mensaje +="\nEl mismo esta aplicado por otros documentos, ser�n desaplicados."; 		
	  if(!confirm(mensaje))
		  return false;
		
	}	
	
	eval("document." + frm + ".accion.value = '" + obj.name + "';");
	eval("document." + frm + ".nrointerno.value = " + id + ";");
	eval("document." + frm + ".submit(); " );
 	
}

function abrirVentana(pagina, nombre, ancho, alto) {
	frmLOV = open(pagina,nombre,'scrollbars=yes,resizable=yes,width='+ancho+',height='+alto+',status=yes');
	if (frmLOV.opener == null) 
		frmLOV.opener = self;
		frmLOV.focus();

  return frmLOV;			
}	