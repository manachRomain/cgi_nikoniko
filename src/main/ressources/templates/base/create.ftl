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
		               		<button onclick="location.href='/menu' " class="home"> Menu </button>
		               </div>
		               <div align="right" class="row-fluid">
		               		<button onclick="location.href='${back}' " class="return"> Retour </button>
		               </div>
	            </div>
	        </div>
	    </div>
	</div>
	
	<hr>
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-lg-12">
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
				</div>
			</div>
		</div>
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-lg-12">
				<input type="submit" value="Créer" onclick="verify()"><br>
			</div>
		</div>
	</div>
	</form>
</body>
</html>