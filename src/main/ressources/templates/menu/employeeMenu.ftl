<!DOCTYPE html>
<html>

<head>
	<!-- Encodage -->
	<meta charset="utf-8">

	<!-- Bootstrap -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

	<!-- Fonts -->
	<link href="https://fonts.googleapis.com/css?family=Gloria+Hallelujah" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=VT323" rel="stylesheet">

    <!-- css -->
	<#if roles == "chefProjet">
		<link href="/css/chefprojet.css"  rel="stylesheet">
	<#else>
		<link href="/css/employee.css"  rel="stylesheet">
	</#if>

	<!-- Title -->
	<title>Se connecter</title>

</head>
<body>
	<div class="container-fluid">
	    <div class="row-fluid">
	        <div class="col-lg-2  col-xs-4">
	            <img class="logo" src="https://upload.wikimedia.org/wikipedia/fr/5/51/LOGO-CGI-1993-1998.svg">
	        </div>
	        <div class="col-lg-8 col-xs-4">
	            <div class="title">Niko-Niko</div>
	        </div>
	        <div class="col-lg-2 col-xs-4">
	            <div class="row-fluid">
	                   <div align="right" class="row-fluid">
		                    <button onclick="location.href='/logout' " class="logout"> Deconnexion </button>
		               </div>
		               <#if mood !=0 && status=true>
		                	<div align="right" class="row-fluid">
		                    	<button onclick="location.href='${add_nikoniko}' " class="home"> Modifier vote </button>
		               		</div>
		               	<else>
		               	</#if>
	            </div>
	        </div>
	    </div>
	</div>
<hr>

<h2 style = "text-align : center"> Bienvenue : ${auth} </h2>

<hr>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="col-lg-12">
			<#if roles == "vp">
				<h2> Gestion </h2>
				- <a class = "" href=""> Menu graphes </a> <br>
			<#else>
			</#if>

			<#if roles == "gestionTeam">
				<h2> Gestion </h2>
				- <a class = "" href="/team/"> Gérer équipe </a> <br>
			<#else>
			</#if>
			<h2 style="text-decoration: underline"> Visualisation </h2>
			<ul>
				<li> <a class = "" href="${myCalendar}"> Calendrier personnel </a> </li>
				<li> <a class = ""href="${pie_chart}"> Autres resultats </a> </li>
			</ul>
			<#if mood == 0 || status == false>
				<h2 style="text-decoration: underline"> Vote du jour </h2>
				<ul>
					<li> <a class = "" href="${add_nikoniko}"> Pas de vote enregistré... On vote ? </a> </li>
				</ul>
			<#else>
			</#if>
			<#if lastNiko == true>
				<h2 style="text-decoration: underline"> Vote de la veille </h2>
				<ul>
			 		<li> <a class ="" href="${add_last}"> Voter pour la veille ? </a> </li>
			 	</ul>
			<#else>
			</#if>
		</div>
	</div>
</div>

<!-- FOOTER -->
<div class="container-fluid">
	<div class="row-fluid">
		<div class="col-lg-12">
			<div class="copyright">&copy; Niko-Niko CGI 2017</div>
		</div>
	</div>
</div>

</body>
</html>
