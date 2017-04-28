<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- css -->
<link href="/css/design.css"  rel="stylesheet">

<!-- Fonts -->
<link href="https://fonts.googleapis.com/css?family=Gloria+Hallelujah" rel="stylesheet"> 

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
		               <div align="right" class="row-fluid">
		               		<button onclick="location.href='../' " class="home"> Menu </button>
		               </div>
		               <div align="right" class="row-fluid">
		               		<button onclick="location.href='../' " class="return"> Retour </button>
		               </div>
	            </div>
	        </div>
	    </div>
	</div>
	
	<hr>
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-lg-12">
	<h2 style="text-decoration : underline; margin-bottom : 20px">${page}</h2>
	<table class="table table-bordered table-hover">
		<#list sortedFields as field>
			<#if field != "id">
				<#list item?keys as key>
					<#if key == field>
						<#if key == "login" || key == "password" || key== "id">
						<#else>
						<tr>
							<th>${key}</th>
							<#if item[key]?is_boolean>
								<td>
									${item[key]?c}
								</td>
							<#else>
								<td>
									${item[key]}
								</td>
							</#if>
						</tr>
						</#if>
					</#if>
				</#list>
			</#if>
		</#list>
	</table>
	<ul>
    <li> <a class href="${show_verticale}"> Afficher la verticale </a> </li>
	<li> <a class href="${show_roles}"> Afficher les rôles </a> </li>
	<li> <a class href="${show_teams}"> Afficher les équipes </a> </li>
	<#if roles == "admin">
	<#else>
		<li> <a class href="${go_update}"> Update </a> </li>
		<li> <a class href="${go_delete}"> Supprimer </a> </li>
	</#if>
	<ul>
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