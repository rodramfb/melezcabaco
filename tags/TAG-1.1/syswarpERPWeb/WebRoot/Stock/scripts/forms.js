var varEnt = "1234567890";
var varDec = "1234567890.";
var varAlfa = "qwertyuiopñlkjhgfdsazxcvbnm ";
var varChar = "!|\"·#$%&/()=?¿\\¡`+-*:;_";

function validarRangoFechas(fechaDesde, fechaHasta, nombreDesde, nombreHasta)
{
	var dd_1 = fechaDesde.substring(0, 2); 	var mm_1 = fechaDesde.substring(3, 5); 	var yyyy_1 = fechaDesde.substring(6); 	var fechaD = new Date(yyyy_1, mm_1-1, dd_1);
	var dd_2 = fechaHasta.substring(0, 2); 	var mm_2 = fechaHasta.substring(3, 5); 	var yyyy_2 = fechaHasta.substring(6); 	var fechaH = new Date(yyyy_2, mm_2-1, dd_2);
    if (fechaD.getTime() > fechaH.getTime()) {
	  alert("Atencion: " + nombreDesde + " no puede ser mayor a " + nombreHasta);
	  return false;
	}
  return true;
}   

function validarDV(objNro) {
	var pNro = objNro.value;
   var chk, j , valor,  numero, s , ss, pesos,total,resto,DVE, dvx;
   var indexGuion1 = pNro.indexOf("-");
   var cab, num, dv;
   if (indexGuion1 != -1)
      cab = pNro.substring(0, indexGuion1);
   else {
      alert("Formato inválido");
		objNro.select();
		objNro.focus();
      return false;
   }
   if (cab.length != 2) {
      alert("El Prefijo no tiene dos (2) posiciones");
		objNro.select();
		objNro.focus();
      return false;
   }
   var indexGuion2 = pNro.indexOf("-", indexGuion1+1);
   if (indexGuion2 != -1)
      num = pNro.substring(indexGuion1+1, indexGuion2);
   else {
      alert("Formato inválido");
		objNro.select();
		objNro.focus();
      return false;
   }
   if (num.length != 8) {
      alert("El identificador no tiene ocho (8) posiciones");
		objNro.select();
		objNro.focus();
      return false;
   }
   dv = pNro.substring(indexGuion2+1);
   if (dv.length != 1) {
      alert("El digito verificador no tiene una 1 posición");
		objNro.select();
		objNro.focus();
      return false;
   }
   //compruebo Prefijo valido
   j = /20|23|24|27|30|33|34|50|55|77|88/;
   valor = cab.match(j);
   if (valor == null) {
      alert("El Prefijo no es un numero valido");
		objNro.select();
		objNro.focus();
      return false;
   }
   // cargo los pesos
   var pesos = new Array();
   pesos[0] = 5;
   pesos[1] = 4;
   pesos[2] = 3;
   pesos[3] = 2;
   pesos[4] = 7;
   pesos[5] = 6;
   pesos[6] = 5;
   pesos[7] = 4;
   pesos[8] = 3;
   pesos[9] = 2;
   // genero el array
   s = cab + num;
   ss = s.split("");
   total = 0 ;
   //multiplico y sumo
   for (i = 0; i <= 9; i++)  {
      total = total + ss[i] * pesos[i];
   }
   // saco el mod
   resto = total % 11;
   if (resto == 0) {
      DVE = 0;
   }
   if (resto == 1) {
      DVE = 0;
   }
   if (resto == 0) {
      if (dv == 0)
         return true;
   }
   if (resto == 1) {
      if (dv == 0)
         return true;
   }
   if (resto > 1) {
      if (dv == 11 - resto)
         return true;
   }
   alert("No es un CUIT/CUIL valido");
	objNro.select();
	objNro.focus();
   return false;
}
function validarObjFecha(objName) {
var datefield = objName;
if (chkdate(objName) == false) {
datefield.select();
alert("Fecha Inválida");
datefield.focus();
return false;
}
else {
return true;
   }
}
function chkdate(objName) {
// var strDatestyle = "US"; //United States date style
var strDatestyle = "EU";  //European date style
var strDate;
var strDateArray;
var strDay;
var strMonth;
var strYear;
var intday;
var intMonth;
var intYear;
var booFound = false;
var datefield = objName;
var strSeparatorArray = new Array("-"," ","/",".");
var intElementNr;
var err = 0;
var strMonthArray = new Array(12);
strMonthArray[0] = "Jan";
strMonthArray[1] = "Feb";
strMonthArray[2] = "Mar";
strMonthArray[3] = "Apr";
strMonthArray[4] = "May";
strMonthArray[5] = "Jun";
strMonthArray[6] = "Jul";
strMonthArray[7] = "Aug";
strMonthArray[8] = "Sep";
strMonthArray[9] = "Oct";
strMonthArray[10] = "Nov";
strMonthArray[11] = "Dec";
strDate = datefield.value;
if (strDate.length < 1) {
return true;
}
for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {
	if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) {
		strDateArray = strDate.split(strSeparatorArray[intElementNr]);
		if (strDateArray == null)
			return false;
		if (strDateArray.length != 3) {
			err = 1;
			return false;
		}
		else {
			strDay = strDateArray[0];
			strMonth = strDateArray[1];
			strYear = strDateArray[2];
		}
		booFound = true;
	}
}
if ((strDay == null) || (strMonth == null) || (strYear == null))
	return false;
if (booFound == false) {
	if (strDate.length>5) {
		strDay = strDate.substr(0, 2);
		strMonth = strDate.substr(2, 2);
		strYear = strDate.substr(4);
	}
}
if (strYear.length < 4) {
   return false;
}
// US style
if (strDatestyle == "US") {
strTemp = strDay;
strDay = strMonth;
strMonth = strTemp;
}
intday = parseInt(strDay, 10);
if (isNaN(intday)) {
err = 2;
return false;
}
intMonth = parseInt(strMonth, 10);
if (isNaN(intMonth)) {
for (i = 0;i<12;i++) {
if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase()) {
intMonth = i+1;
strMonth = strMonthArray[i];
i = 12;
   }
}
if (isNaN(intMonth)) {
err = 3;
return false;
   }
}
intYear = parseInt(strYear, 10);
if (isNaN(intYear)) {
err = 4;
return false;
}
if (intMonth>12 || intMonth<1) {
err = 5;
return false;
}
if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1)) {
err = 6;return false;
}
if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) {
err = 7;
return false;
}
if (intMonth == 2) {
if (intday < 1) {
err = 8;
return false;
}
if (LeapYear(intYear) == true) {
if (intday > 29) {
err = 9;
return false;
}
}
else {
if (intday > 28) {
err = 10;
return false;
}
}
}
return true;
}
function LeapYear(intYear) {
if (intYear % 100 == 0) {
if (intYear % 400 == 0) { return true; }
}
else {
if ((intYear % 4) == 0) { return true; }
}
return false;
}
function ltrim(s) {
	return s.replace( /^\s*/, "" )
}
function rtrim(s) {
	return s.replace( /\s*$/, "" );
}
function trim(s) {
	return rtrim(ltrim(s));
}
function validarHora(txtHora) {
	var hora = txtHora.value;
	if (hora != "") {
		hora = trim(hora);
		var i = hora.indexOf(':');
		var j = hora.indexOf('.');
		if ((i == -1) && (j == -1)) {
			alert("Hora Inválida");
			txtHora.focus();
			return false;
		}
		if (j != -1)
			i = j;
		var hh = parseFloat(hora.substring(0, i));
		var mm = parseFloat(hora.substring(i+1));
		if (isNaN(hh)) {
			alert("Hora Inválida");
			txtHora.focus();
			return false;
		}
		if (isNaN(mm)) {
			alert("Hora Inválida");
			txtHora.focus();
			return false;
		}
		if ((hh < 0) || (hh > 23) || (mm < 0) || (mm > 59)) {
			alert("Hora Inválida");
			txtHora.focus();
			return false;
		}
		var hora;
		var minu;
		if (hh == 0)
			hora = "00";
		else if ((hh > 0) && (hh < 10))
			hora = "0" + hh;
		else
			hora = hh;
		if (mm == 0)
			minu = "00";
		else if ((mm > 0) && (mm < 10))
			minu = "0" + mm;
		else
			minu = mm;
		txtHora.value = hora + ':' + minu;
	}
	return true;
}

/*Agregado 24/06/2003. Ordenamiento de tablas*/

<!-- This script and many more are available free online at -->
<!-- The JavaScript Source!! http://javascript.internet.com -->
<!-- Begin
function setDataType(cValue)
  {
    // THIS FUNCTION CONVERTS DATES AND NUMBERS FOR PROPER ARRAY
    // SORTING WHEN IN THE SORT FUNCTION
    var isDate = new Date(cValue);
    if (isDate == "NaN")
      {
        if (isNaN(cValue))
          {
            // THE VALUE IS A STRING, MAKE ALL CHARACTERS IN
            // STRING UPPER CASE TO ASSURE PROPER A-Z SORT
            cValue = cValue.toUpperCase();
            return cValue;
          }
        else
          {
            // VALUE IS A NUMBER, TO PREVENT STRING SORTING OF A NUMBER
            // ADD AN ADDITIONAL DIGIT THAT IS THE + TO THE LENGTH OF
            // THE NUMBER WHEN IT IS A STRING
            var myNum;
            myNum = String.fromCharCode(48 + cValue.length) + cValue;
            return myNum;
          }
        }
  else
      {
        // VALUE TO SORT IS A DATE, REMOVE ALL OF THE PUNCTUATION AND
        // AND RETURN THE STRING NUMBER
        //BUG - STRING AND NOT NUMERICAL SORT .....
        // ( 1 - 10 - 11 - 2 - 3 - 4 - 41 - 5  etc.)
        var myDate = new String();
/*Modificado del 08/08/2003*/
 /*       myDate = isDate.getFullYear() + " " ;
        myDate = myDate + isDate.getMonth() + " ";
        myDate = myDate + isDate.getDate(); + " ";
        myDate = myDate + isDate.getHours(); + " ";
        myDate = myDate + isDate.getMinutes(); + " ";
        myDate = myDate + isDate.getSeconds();*/
        myDate = cValue.substring(6,10) + cValue.substring(3,5) +cValue.substring(0,2);
/*fin-Modificado del 08/08/2003*/		
		return myDate;
      }
  }
function sortTable(col, tableToSort)
  {
    var iCurCell = col + tableToSort.cols;
    var totalRows = tableToSort.rows.length;
    var bSort = 0;
    var colArray = new Array();
    var oldIndex = new Array();
    var indexArray = new Array();
    var bArray = new Array();
    var newRow;
    var newCell;
    var i;
    var c;
    var j;
	var strStatusAnterior =	window.status;
	window.status = "Ordenando . . .";

    // ** POPULATE THE ARRAY colArray WITH CONTENTS OF THE COLUMN SELECTED
    for (i=1; i < tableToSort.rows.length; i++)
      {
        colArray[i - 1] = setDataType(tableToSort.cells(iCurCell).innerText);
        iCurCell = iCurCell + tableToSort.cols;
      }
    // ** COPY ARRAY FOR COMPARISON AFTER SORT
    for (i=0; i < colArray.length; i++)
      {
        bArray[i] = colArray[i];
      }
    // ** SORT THE COLUMN ITEMS
    //alert ( colArray );
    colArray.sort();
    //alert ( colArray );
    for (i=0; i < colArray.length; i++)
      { // LOOP THROUGH THE NEW SORTED ARRAY
        indexArray[i] = (i+1);
        for(j=0; j < bArray.length; j++)
          { // LOOP THROUGH THE OLD ARRAY
            if (colArray[i] == bArray[j])
              {  // WHEN THE ITEM IN THE OLD AND NEW MATCH, PLACE THE
                // CURRENT ROW NUMBER IN THE PROPER POSITION IN THE
                // NEW ORDER ARRAY SO ROWS CAN BE MOVED ....
                // MAKE SURE CURRENT ROW NUMBER IS NOT ALREADY IN THE
                // NEW ORDER ARRAY
                for (c=0; c<i; c++)
                  {
                    if ( oldIndex[c] == (j+1) )
                    {
                      bSort = 1;
                    }
                      }
                      if (bSort == 0)
                        {
                          oldIndex[i] = (j+1);
                        }
                          bSort = 0;
                        }
          }
    }
  // ** SORTING COMPLETE, ADD NEW ROWS TO BASE OF TABLE ....
  for (i=0; i<oldIndex.length; i++)
    {
      newRow = tableToSort.insertRow();
      for (c=0; c<tableToSort.cols; c++)
        {
          newCell = newRow.insertCell();
          newCell.innerHTML = tableToSort.rows(oldIndex[i]).cells(c).innerHTML;
        }
      }
  //MOVE NEW ROWS TO TOP OF TABLE ....
/*  for (i=1; i<totalRows; i++)
    {
      tableToSort.moveRow((tableToSort.rows.length -1),1);
   }	*/

//Modificación del original para mantener el estilo de la tabla
	for (i=1;i<totalRows;i++)
/*		for (c=0;c<tableToSort.cols - 1;c++)*/
		for (c=0;c<tableToSort.cols; c++)		
			tableToSort.rows(i).cells(c).innerHTML=tableToSort.rows(i+totalRows-1).cells(c).innerHTML;

  //DELETE THE OLD ROWS FROM THE BOTTOM OF THE TABLE ....
  for (i=1; i<totalRows; i++)
    {
      tableToSort.deleteRow();
    }
	window.status = window.defaultStatus;
  }


//Agregado 14/07/2003

function validarNumerico(obj,iTotal,iDecim) {
	var str=obj.value,minus="",format="",mensaje="";
	var i=0, cntP=0, len=str.length, overf=0;
	if (str.charCodeAt(i) == 45) { /*Eliminar el caracter -*/
		str=str.substr(1,len);
		len--;
		minus="-";
	}		
	if (iDecim>0) 	{
		cntP=0;
		if (str.indexOf(".") > iTotal) mensaje="Desbordamiento de parte entera";
		if ((str.indexOf(".")>0) && (len-str.indexOf(".")-1) > iDecim) mensaje="Desbordamiento de parte decimal";		
		if ( str.indexOf(".")==0 && len == 1 ) mensaje = "Formato inválido.";
		//alert(  str.indexOf(".") + " - " + ( len-str.indexOf(".")-1  ) + " - ");
		if ((str.indexOf(".") ==-1) && (len>iTotal)) mensaje="Desbordamiento de parte entera";
	}					
	else {
		cntP=1;
		if (len>iTotal) mensaje="Desbordamiento de tamaño numérico";
	}		
	if ((iTotal+iDecim+1-cntP)<len) mensaje="Desbordamiento de tamaño numérico";
	for (i=0;i<str.length;i++) {
		if (str.charCodeAt(i) == 46) 
			cntP++;
		else			
			if ((str.charCodeAt(i)<48) || (str.charCodeAt(i)>57)) cntP=3;
	}
	if (cntP>1)	mensaje="Campo numérico";
	if (mensaje.length>0) {
		for(i=0;i<iTotal;i++) format=format + "9";
		if (iDecim>0) format=format + ".";
		for(i=0;i<iDecim;i++) format=format + "9";		
		alert(mensaje + " - " + format + " - " + str);
		obj.select();
 	  return false;
	} 
	 
  return true;
}
//Agregado 04/12/2003

function setFocus() {
var x=0;
  for (var i=0;i<document.frm.length;i++) {	
		if (document.frm.elements[i].type=="text")  {
			if (parseInt(document.frm.elements[i].title)==0) {
			  x=i;
			}
     	}
	}
  
  document.frm.elements[x].focus();
  return true;
}


//Agregado el 05-12-2003
function addFecha(ctrl, dias) { 
	if (ctrl.value.length==10) {
		var f_anio = ctrl.value; 
		var dd = f_anio.substring(0, 2); 
		var mm = f_anio.substring(3, 5); 
		var yyyy = f_anio.substring(6); 
		var curfecha = new Date(yyyy, mm - 1, dd); 
		curfecha.setDate(curfecha.getDate() + dias); 
		var nDia = curfecha.getDate(); 
		var nMes = curfecha.getMonth() + 1; 
		var nAno = curfecha.getYear(); 
/* PFS 16-12-2003 Función getYear devuelve 2 caracteres para años previos a 1999*/
		if (nAno < 2000) nAno = '19' + nAno;
/* Fin 16-12-2003 */		
		var cDia = '' + nDia; 
		var cMes = '' + nMes; 
		var cAno = '' + nAno; 
		if (nDia < 10) cDia = '0' + cDia; 
		if (nMes < 10) cMes = '0' + cMes; 
		var nFecha = cDia +'/'+ cMes + '/' + cAno ; 
		return nFecha; 
	} else {
		return "";
	}				
} 

function soloLectura(solo) {
         if (solo=="True") {
		     for (var i=0;i<document.frm.length;i++) {
			      if (document.frm.elements[i].type!="text" ) {
				            document.frm.elements[i].disabled="true";
			      } else {
				     document.frm.elements[i].readOnly="yes";
					 }
		  }
	 }	  
}

function isCharsInBag (s, bag){
    var i;
    // Search through string's characters one by one.
    // If character is in bag, append to returnString.

    for (i = 0; i < s.length; i++)  {
        // Check that current character isn't whitespace.
        var c = s.charAt(i);
      if (bag.indexOf(c) == -1) {
         if (s.charCodeAt(i) != 10 && s.charCodeAt(i) != 13) return false;
      }
    }
    return true;
  }
	
function retornaPeriodo(fecha){ 
  var auxFecha = new Date();
  var fechaValidar  = new Date( auxFecha.getFullYear(), auxFecha.getMonth(), auxFecha.getDate(), 0, 0);
  var fechaActual  = new Date( auxFecha.getFullYear(), auxFecha.getMonth(), auxFecha.getDate(), 0, 0);
  fechaValidar.setFullYear(  fecha.substring(6));
  fechaValidar.setMonth(  fecha.substring(3, 5) - 1 );
  fechaValidar.setDate(  fecha.substring(0, 2) );
  var retorno =  fechaValidar.getTime() -  fechaActual.getTime();   

 return retorno;            
}

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

function mostrarLOV(pagina) {
	frmLOV = open(pagina,'winLOV','scrollbars=yes,resizable=yes,width=600,height=350,status=yes');
	if (frmLOV.opener == null) 
		frmLOV.opener = self;
}	

function popupHelp(pagina)
 {   
   window.open (pagina,'mywindow','scrollbars=yes,resizable=yes,width=700,height=550,status=1,toolbar=1');  
} 


function abrirVentana(pagina, nombre, ancho, alto) {
	frmLOV = open(pagina,nombre,'scrollbars=yes,resizable=yes,width='+ancho+',height='+alto+',status=yes');
	if (frmLOV.opener == null) 
		frmLOV.opener = self;
		frmLOV.focus();
}	
	
function confirmarBaja(formName){
  if(confirm("Confirma Baja ?")){
   document.forms[formName].accion.value = "baja";
   return true;
  }
  else return false;
}

function paginacion (paginaSeleccion){
  document.frm.paginaSeleccion.value=paginaSeleccion;
  document.frm.submit();
}
//  End -->



     