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
	<link href="/css/pie.css"  rel="stylesheet">

    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {

        var data = google.visualization.arrayToDataTable([
          ['NikoNiko', 'Number of Smile'],
          ['Good', ${good}],
          ['Medium', ${medium}],
          ['Bad', ${bad}]
        ]);

        var options = {
          backgroundColor:'transparent',
          legend: 'none',
          slices: {
            0: { color: '#00CC00' },
            1: { color: 'orange' },
            2: { color: '#EE0000' }
          },
          pieSliceText: 'value',
          tooltip: { trigger: 'selectable' },
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        function selectHandler() {
          var selectedItem = chart.getSelection()[0];
          if (selectedItem) {
            var topping = data.getValue(selectedItem.row, 0);
            alert('The user selected ' + topping);
          }
        }

        google.visualization.events.addListener(chart, 'select', selectHandler);

        chart.draw(data, options);
      }
    </script>
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
		               <div align="right" class="row-fluid">
		                    <button onclick="location.href='${lastUrl}'" class="vote"> Retour </button>               
	                </div>
	            </div>
	        </div>
	    </div>
	</div>

	<hr>
	
	<div class="welcome"> ${title} </div>
		<#if motiv != 0 && check == true>
			<#if motiv == 1>
				<img class="piechart" src="https://gusandcodotnet.files.wordpress.com/2013/10/glassy-smiley-failure.png"/>
				<#if textAreaOption == "">
					<div class="welcome"> Pas de commentaire saisit ! </div>
				<#else>
					<div class="welcome"> Votre commentaire sur cette journee : </div>
	    			<div class="comment">"${textAreaOption}" </div>
	    		</#if>
			<#elseif motiv == 2>
				<img class="piechart" src="https://images.vexels.com/media/users/3/134541/isolated/preview/02f3c0cba01ca5fb7405293c55253afd-emoji-emoticon-by-vexels.png" alt="canard"/>
				<#if textAreaOption == "">
					<div class="welcome"> Pas de commentaire saisit !</div>
				<#else>
					<div class="welcome"> Votre commentaire sur cette journee : </div>
	    			<div class="comment">"${textAreaOption}" </div>
	    		</#if>
			<#elseif motiv == 3>
				<img class="piechart" src="https://images.vexels.com/media/users/3/134594/isolated/preview/cb4dd9ad3fa5ad833e9b38cb75baa18a-happy-emoji-emoticon-by-vexels.png" alt="canard"/>
				<#if textAreaOption == "">
					<div class="comment"> Pas de commentaire saisit !</div>
				<#else>
					<div class="welcome"> Votre commentaire sur cette journee : </div>
	    			<div class="comment">"${textAreaOption}" </div>
	    		</#if>
	    	<#else>
	    		<div style="margin-top:20px"><img src="http://www.tagtele.com/img/videos/thumbs640x360/b/f/b/55318_default.jpg" alt="canard"/></div>
			</#if>
		<#else>
			 <div class="piechart" id="piechart" style="width: 700px; height: 400px;"></div>
		</#if>

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

