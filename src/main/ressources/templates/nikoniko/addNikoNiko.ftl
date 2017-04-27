<!DOCTYPE html>
<html>
<head>
	<!-- Encodage -->
	<meta charset="utf-8">

	<!-- Bootstrap -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

	<!-- CSS -->
	<link rel="stylesheet" href="vote.css">

	<!-- Fonts -->
	<link href="https://fonts.googleapis.com/css?family=Gloria+Hallelujah" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=VT323" rel="stylesheet">

    <!-- css -->
	<link href="/css/vote.css"  rel="stylesheet">

	<!-- Title -->
	<title>Satisfaction</title>
</head>
<body>
<!-- HEAD -->
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
		                    <button onclick="location.href='/menu'" class="home"> Retour </button>
		               </div>
	            </div>
	        </div>
	    </div>
	</div>

<hr>

<!-- CONSTRUCTION -->
<form action = "" method = "POST">
<div class="container-fluid">
	<div class="row-fluid">
		<div class="col-lg-12">
			<center>
			<#if mood == 0>
				<img onclick="nikoniko()" src="https://s0.wp.com/wp-content/mu-plugins/wpcom-smileys/twemoji/2/svg/1f914.svg" style ="width : 20%" id="image">
				<div style="background-color : white" id="niko" onclick="nikoniko()"> Ne pas voter </div>
			<#elseif mood !=0>
				<#if mood == 3>
					<img onclick="nikoniko()" src="https://gusandcodotnet.files.wordpress.com/2013/10/glassy-smiley-failure.png" style ="width : 20%" id="image">
					<div style="background-color : red" id="niko" onclick="nikoniko()"> Mauvaise journee </div>
				<#elseif mood == 2>
					<img onclick="nikoniko()" src="https://images.vexels.com/media/users/3/134541/isolated/preview/02f3c0cba01ca5fb7405293c55253afd-emoji-emoticon-by-vexels.png" style ="width : 20%" id="image">
					<div style="background-color : orange" id="niko" onclick="nikoniko()"> Ni bonne, ni mauvaise journee </div>
				<#else>
					<img onclick="nikoniko()" src="https://images.vexels.com/media/users/3/134594/isolated/preview/cb4dd9ad3fa5ad833e9b38cb75baa18a-happy-emoji-emoticon-by-vexels.png" style ="width : 20%" id="image">
					<div style="background-color : red" id="niko" onclick="nikoniko()"> Bonne journee  </div>
				</#if>
			</#if>
			</center>
			<input id = "mood" type="hidden" name = "mood" value="0">
		</div>
	</div>
</div>

<hr>

<!-- COMMENTARY -->

<div class="container-fluid">
	<div class="row-fluid">
		<div class="col-lg-12">
			<center>
				<#include "../includable/security/securityToken.ftl">
				<TEXTAREA style="margin-top: 30px" name="comment" rows=5 cols=60 placeholder ="Ecrire votre commentaire..." >${textAreaOption}</TEXTAREA> <br>
				<button class="buttons" onclick="location.href='/menu'"> Valider </button>
				</form>
			</center>
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


<script type="text/javascript">
var count = ${mood};

function nikoniko() {

	if (count === 0){
    	document.getElementById("niko").style.backgroundColor = 'red';
    	document.getElementById("mood").value="3";
    	document.getElementById("image").src = "https://gusandcodotnet.files.wordpress.com/2013/10/glassy-smiley-failure.png";
    	document.getElementById("niko").innerHTML = 'Mauvaise journee';
    	count = count + 1;
    }

	else if (count === 1){
    	document.getElementById("niko").style.backgroundColor = 'orange';
    	document.getElementById("mood").value="2";
    	document.getElementById("image").src = "https://images.vexels.com/media/users/3/134541/isolated/preview/02f3c0cba01ca5fb7405293c55253afd-emoji-emoticon-by-vexels.png";
    	document.getElementById("niko").innerHTML = 'Ni bonne, ni mauvaise journee';
    	count = count + 1;
    }
    
    else if (count === 2){
    	document.getElementById("niko").innerHTML = 'Bonne journee';
		document.getElementById("niko").style.backgroundColor = 'green';
		document.getElementById("mood").value="1";
		document.getElementById("image").src = "https://images.vexels.com/media/users/3/134594/isolated/preview/cb4dd9ad3fa5ad833e9b38cb75baa18a-happy-emoji-emoticon-by-vexels.png";
    	count = count + 1;    
    }
    
    else{
    	document.getElementById("niko").innerHTML = 'Je ne vote pas';
    	document.getElementById("niko").style.backgroundColor = 'white';
    	document.getElementById("mood").value = "0";
    	document.getElementById("image").src = "https://s0.wp.com/wp-content/mu-plugins/wpcom-smileys/twemoji/2/svg/1f914.svg";
    	count = 0;
    }
    	
};
</script>



</body>
</html>

