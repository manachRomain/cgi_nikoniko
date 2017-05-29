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
		                    <button onclick="location.href='../menu' " class="return"> Menu </button>
		               </div>
	            </div>
	        </div>
	    </div>
	</div>
	
	<hr>

	<div class="container-fluid">
		<div class = "row-fluid">
			<div class="col-lg-12">
	<form action = "" method = "POST">
		<#include "../includable/security/securityToken.ftl">
		<#if model = "user">
			<input type="text" class="search" onkeyup="myFunction()" name="name" placeholder="Search for registration" title="Type in a name">
			
		<#elseif model = "team">
			<input type="text" class="search" onkeyup="myFunction()" name="name" placeholder="Search for team names" title="Type in a name">
			
		<#elseif model = "verticale" >
			<input type="text" class="search" onkeyup="myFunction()" name="name" placeholder="Search for verticales names" title="Type in a name">
			
		<#elseif model = "nikoniko">
			<input type="text" class="search" onkeyup="myFunction()" name="name" placeholder="Search for registration_cgi" title="Type in a name">
			
		<#else>
		</#if>
		<#if model == "role">
		<#else>
			<input type="submit" value="Rechercher">
			<hr>
		</#if>
		</div>
		</div>
		</div>
	</form>
	
	
	
	<div class="container-fluid">
		<div class = "row-fluid">
			<div class = "col-lg-12">
	<h2 style = "text-decoration : underline; margin-bottom : 20px">Liste des ${page}s</h2>
	<#if model == "nikoniko" || model == "role">
	<#else>
		 <a style ="margin-bottom : 10px" href="${go_create}"> Creer un nouveau </a>
	</#if>
	<table class="table table-bordered table-hover">
		<tr>
			<#list items as item>
				<#list sortedFields as field>
					<#list item?keys as key>
						<#if key == field>
							<#if key == "login" || key == "password" || key== "id">
							<#elseif item[key]??>
								<th>${key}</th>
							<#else>
							</#if>
						</#if>
					</#list>
				</#list>
				<#break>
			</#list>
			<#if model == "role">
				<th>Actions</th>
			<#else>
				<th colspan = 2>Actions</th>
			</#if>
		</tr>
		<#list items as item>
			<tr>
				<#list sortedFields as field>
					<#list item?keys as key>
						<#if key == field>
							<#if key == "login" || key == "password" || key== "id">
							<#else>
								<#if item[key]??>
									<#if item[key]?is_boolean>
									<td>${item[key]?c}</td>
								<#elseif item[key]?is_date_like>
									<td>${item[key]?string("yyyy/MM/dd")}</td>
								<#else>
									<td>${item[key]}</td>
								</#if>
								<#else>
					 			</#if>
							</#if>
						</#if>
					</#list>
				</#list>
				<#if model == "role">
					<td>
						<#if item["id"]??>
							<a href="${item["id"]}/showUser">Afficher les utilisateurs</a>
						<#else>
							<a href="${item["idLeft"]}/${item["idRight"]}/${go_show}">Select</a>
						</#if>
					</td>
				<#else>
					<td>
						<#if item["id"]??>
							<a href="${item["id"]}/${go_show}">Détails</a>

						<#else>
							<a href="${item["idLeft"]}/${item["idRight"]}/${go_show}">Détails</a>
						</#if>
					</td>
					</#if>
				</tr>
			</#list>
		</table>
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