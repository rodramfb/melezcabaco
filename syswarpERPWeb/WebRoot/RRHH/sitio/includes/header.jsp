<a href="index.jsp" title="Volver a la página principal">
	<img src="sitio/images/logo.jpg" class="logo" alt="Home" />
</a>
<div id="map">

	<a href="company.php" title="Nuestra Empresa">Quienes Somos</a> |	
	<a href="partners.php" title="Newsletter">Partners</a> |
	<a href="consult.php" title="Consultas">Contáctenos</a>
<FORM action=index.php method=post>
	<input class="image" type="image" src="sitio/images/find.gif" alt="Search" tabindex="3"/>
	<INPUT class=inputbox 
		onblur="if(this.value=='') this.value='buscar...';" 
		onfocus="if(this.value=='buscar...') this.value='';" 
		size=23 value=buscar... name=searchword> 
	<INPUT type=hidden value=search name=option> 
</FORM>


</div> 