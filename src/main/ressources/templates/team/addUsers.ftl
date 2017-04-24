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
	        <div class="col-lg-2">
	            <img class="logo" src="https://upload.wikimedia.org/wikipedia/fr/5/51/LOGO-CGI-1993-1998.svg">
	        </div>
	        <div class="col-lg-8">
	            <div class="title">Niko-Niko</div>
	        </div>
	        <div class="col-lg-2">
	            <div class="row-fluid">
	                <div class="col-lg-12">
	                    <div class="align">	                        
	                        <button onclick="location.href='/logout' " class="logout"> Déconnexion </button>
	                        <button onclick="location.href='/menu'" class="home"> Menu </button>
	                        <button onclick="location.href='${back}'" class="return"> Retour </button>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	<h1>Utilisateur à ajouter dans l'équipe : ${page}  </h1>
	<form action = "" method = "POST">
		<#include "../includable/security/securityToken.ftl">
		<input type="text" class="search" onkeyup="myFunction()" name="name" placeholder="Search for registration" title="Type in a name">
		<input type="submit" value="Rechercher">
		</form>
		<table class="table table-bordered table-hover">
			<tr>
				<#list items as item>
					<#list sortedFields as field>
						<#list item?keys as key>
							<#if key == field && key != "id">
								<#if key == "login" || key == "password" || key== "id">
								<#else>
								<th>${key}</th>
								</#if>
							</#if>
						</#list>
					</#list>
					<#break>
				</#list>
			</tr>
			<#list items as item>
				<tr>
					<#list sortedFields as field>
						<#list item?keys as key>
							<#if key == field && key != "id">
								<#if key == "login" || key == "password" || key== "id">
								<#else>
									<#if item[key]?is_boolean>
										<td>${item[key]?c}</td>
									<#elseif item[key]?is_date_like>
										<td>${item[key]?string("yyyy:MM:dd HH:mm:ss")}</td>
									<#else>
										<td>${item[key]}</td>
									</#if>
								</#if>
							</#if>
						</#list>
					</#list>
					<td>
						<form action = "" method = "POST">
							<#include "../includable/security/securityToken.ftl">
							<input type="hidden" name = "idUser" value = "${item["id"]}">
							<input type="submit" value="Ajouter"><br>
						</form>
					</td>
				</tr>
			</#list>
		</table>
</body>
</html>