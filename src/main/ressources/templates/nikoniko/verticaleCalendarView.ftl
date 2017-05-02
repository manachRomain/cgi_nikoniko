<!DOCTYPE html>
<html>
<head>
<!-- Encodage -->
    <meta charset="utf-8">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- css -->
	<link href="/css/nikoniko.css"  rel="stylesheet">
	
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
		                    <button onclick="location.href='/menu'" class="home"> Menu </button>
		               </div>
	            </div>
	        </div>
	    </div>
	</div>
	
	<hr>
	
	<div class="container-fluid">
	<div class="row-fluid">
	<div class = "date_buttons">
		<div class="col-lg-4 col-xs-4">
		<span class = "previous_button">
			<a href="?month=${monthToUse}&year=${yearToUse?c}&action=previous">Mois précédent</a>
		</span>
		</div>
		<div class="col-lg-4 col-xs-4">
		<span class = "month_year">
			Vos résultats de la verticale ${verticaleName} pour ${monthName} ${yearToUse}
		</span>
		</div>
		<div class="col-lg-4 col-xs-4">
		<span class = "next_button">
			<a href="?month=${monthToUse}&year=${yearToUse?c}&action=next">Mois suivant</a>
		</span>
		</div>
	</div>
	</div>
	</div>
	
	<hr>
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-lg-12">
	<table class="table table-bordered table-hover">
		<tr>
			<#list jourSemaine as jour>
				<#if jour != "Samedi"&&jour != "Dimanche">
					<th class = "daysNames">${jour}</th>
				</#if>
			</#list>
		</tr>
		<#list nbweeks as week>
			<tr>
				<#if week == 1 && firstWeekUncomplete == 1>
					<#list 1..nbJoursSemaineAIgnorer as i>
					<td class = "emptyDay"></td>
					</#list>
				</#if>
				<#list jourSemaine as jour>
					<#list days as map>
				    	<#assign keys = map?keys>
				    		<#list map?keys as key>
								<#if map["uncompleteWeek"] == 1>
									<#if jour == key && week == map["endOfWeek"]&&key != "Samedi"&&key != "Dimanche">
											<td class = "fillableDay">
												<div class = "dayHeader" align="right">
														${map[key]}
												</div>
												<div class = "daynikos" onclick = "location.href='/graph/showgraphverticale/${yearToUse?c}/${monthToUse}/${map[key]}'">
														<div class = "nikoPastille">
														<img src = "https://images.vexels.com/media/users/3/134594/isolated/preview/cb4dd9ad3fa5ad833e9b38cb75baa18a-happy-emoji-emoticon-by-vexels.png"> :  ${map["nikoGood"]}
														</div>
														<div class = "nikoPastille">
														<img src = "https://images.vexels.com/media/users/3/134541/isolated/preview/02f3c0cba01ca5fb7405293c55253afd-emoji-emoticon-by-vexels.png"> :  ${map["nikoNeutral"]}
														</div>
														<div class = "nikoPastille">
														<img src = "https://gusandcodotnet.files.wordpress.com/2013/10/glassy-smiley-failure.png"> :  ${map["nikoBad"]}
														</div>
												</div>
											</td>
									</#if>
								<#else>
									<#if jour == key && week == map["endOfWeek"]&&key != "Samedi"&&key != "Dimanche">
											<td class = "fillableDay">
												<div class = "dayHeader" align="right">
														${map[key]}
												</div>
												<div class = "daynikos" onclick = "location.href='/graph/showgraphverticale/${yearToUse?c}/${monthToUse}/${map[key]}'">
														<div class = "nikoPastille">
														<img src = "https://images.vexels.com/media/users/3/134594/isolated/preview/cb4dd9ad3fa5ad833e9b38cb75baa18a-happy-emoji-emoticon-by-vexels.png"> :  ${map["nikoGood"]}
														</div>
														<div class = "nikoPastille">
														<img src = "https://images.vexels.com/media/users/3/134541/isolated/preview/02f3c0cba01ca5fb7405293c55253afd-emoji-emoticon-by-vexels.png"> :  ${map["nikoNeutral"]}
														</div>
														<div class = "nikoPastille">
														<img src = "https://gusandcodotnet.files.wordpress.com/2013/10/glassy-smiley-failure.png"> :  ${map["nikoBad"]}
														</div>
												</div>
											</td>
									</#if>
								</#if>
							</#list>
						</#list>
					</td>
				</#list>
				<#if week == numberOfWeekInMonth && lastWeekUncomplete == 1>
					<#list 1..nbJoursSemaineAAjouter as i>
					<td class = "emptyDay"></td>
					</#list>
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