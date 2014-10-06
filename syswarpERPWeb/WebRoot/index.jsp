<%@ page import="ar.com.syswarp.ejb.*"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%
String mensaje = request.getParameter("mensaje");
/*
	Properties prop = System.getProperties();
	Enumeration en = prop.propertyNames();
	while(en.hasMoreElements()){
		String pr = en.nextElement().toString();
		System.out.println( pr + " :" + prop.getProperty(pr));
	}
*/
// INSTANCIAR EL MODULO GENERAL
/*
javax.naming.Context context = new javax.naming.InitialContext();
Object object = context.lookup("General");
GeneralHome sHome = (GeneralHome) javax.rmi.PortableRemoteObject.narrow(object, GeneralHome.class);
General repo = sHome.create();
*/
GeneralBean repo = new GeneralBean();


java.util.List empresas = repo.getGlobalempresasAll();
Iterator iterEmpresas = empresas.iterator();
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Delta BacoClub</title>
		<link href="css/bootstrap.css" rel="stylesheet">

		<style>
.avata-login {
	width: 100px;
	margin-bottom: 30px;
	margin-left: 70px;
}

.login-header {
	width: 100%;
	height: 120px;
	background-color: #FFF;
	margin-bottom: 40px;
	padding-top: 20px;
}

.login-titulo {
	margin: 0 auto;
	width: 400px;
	text-align: center;
	color: #555;
}

.text-danger {
	padding-left: 5px;
}
</style>
	</head>
	<body>
		<div class="container" style="width: 300px;">
			<div class="row">
				<div class="col-md-12 text-center">
					<img src="images/logo.png" alt="logo BacoClub" width="120px;" />
					<h2>
						Bienvenido a Delta
					</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 well">
					<img src="images/no_foto.jpg" alt="avatar usuario"
						class="img-circle avata-login" />
					<form method='POST' id='loginForm'
						autocomplete='on' role="form" action="validar.jsp" name="frm">
						<div class="form-group">
							<input type="text" class="form-control input-lg" id="username"
								name="usuario" placeholder="Usuario">
						</div>
						<div class="form-group">
							<input type="password" class="form-control input-lg"
								name="password" id="password" placeholder="Password">
						</div>
						<div class="form-group">
						<select name="empresa" class="form-control input-sx" placeholder="Empresa">
								   <%while(iterEmpresas.hasNext()){%>
								      <%String[] uCampos = (String[]) iterEmpresas.next();%>								      
								      <option value="<%=uCampos[0]%>"><%=uCampos[1]%></option>
								    <%}%>  
								   </select>
						</div>
						<div style="color:red; margin-bottom:10px"> <%if (mensaje != null) out.write(mensaje);%></div>
						<div class="form-group">
							<button class="btn btn-info btn-lg" style="width: 100%"
								type="submit">
								Entrar
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>

	</body>
	<script type="text/javascript" src="js/bootstrap.js"></script>
</html>
