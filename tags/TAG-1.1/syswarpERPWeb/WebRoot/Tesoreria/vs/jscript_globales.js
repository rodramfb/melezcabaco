// ---------------------------------------------------------------------------------------------------------
// Definicion de Javascript globales.
// AUTOR: Carlos Enrique Perez
// ---------------------------------------------------------------------------------------------------------
function abrirVentana(unaPagina, unNombre){
	window.open(unaPagina,unNombre,"menubar=yes,status=no,toolbar=no,location=no,scrollbars=yes,width=790,height=495, left=0, top=0");
}
function abrirVentana2(unaPagina, unNombre){
	window.open(unaPagina,unNombre,"menubar=yes,status=no,toolbar=no,location=no,scrollbars=yes,width=720,height=320, left=0, top=0");
}
function abrirVentanaMenor(unaPagina, unNombre){
	window.open(unaPagina,unNombre,"menubar=no,status=no,toolbar=no,location=no,scrollbars=yes,width=790,height=305, left=0, top=238");
}
function expandirGrilla(unIdGrilla, imagenATratar) {
	imagenATratar.style.visibility 	= "hidden";
	unIdGrilla.style.visibility 	= "visible";
}
function contraerGrilla(unIdGrilla, imagenATratar) {
	imagenATratar.style.visibility 	= "visible";
	unIdGrilla.style.visibility 	= "hidden";
}
function mostrarLOV(pagina) {
   frmLOV = open(pagina, 'winLOV', 'menubar=no,status=no,toolbar=no,location=no,scrollbars=yes,resizable=yes,width=700,height=400,left=20, top=20');
   if (frmLOV.opener == null) {
	  frmLOV.opener = self;
   }
}
function mostrarLOVWH(pagina, width, height, ventana) {
   frmLOV = open(pagina
                , ventana 
                , 'scrollbars=yes,resizable=yes,width=' + width + ',height=' + height);
   if (frmLOV.opener == null) {
      frmLOV.opener = self;
   }	
}
//-------------------------------------------------------------------------------------------------------
function redirectPagina(unaPagina) {
	document.form.action = unaPagina; 
	document.form.submit();
}
//-------------------------------------------------------------------------------------------------------
