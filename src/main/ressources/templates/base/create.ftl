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
	                        <button onclick="location.href='${go_index}'" class="return"> Retour </button>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	<h1>Création d'une nouvelle entité : ${page}</h1>
	<form name="form" action = "${create_item}" method = "POST">
		<#include "../includable/security/securityToken.ftl">
		<table class="table table-bordered table-hover">
			<#list sortedFields as field>
				<#if field != "id">
					<#list item?keys as key>
						<#if key == field>
							<tr>
								<th>${key}</th>
								<td>
									<input id="test" type="text" name = "${key}">
								</td>
							</tr>
						</#if>
					</#list>
				</#if>
			</#list>
		</table>
		<input type="submit" class="logout" value="Créer" onclick="verify()"><br>
	</form>
</body>
</html>