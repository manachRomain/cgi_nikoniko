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

<!-- HEAD -->
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
	                    <button onclick="location.href='${back}'" class="vote"> Retour </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<hr>
					
<div class="container-fluid">
	<div class="row-fluid">
		<div class="col-lg-12">
			<h2 style="text-decoration: underline"> Vos options </h2>
		</div>
	</div>
	<div class="row-fluid">
    	<#if role != "admin">
	        <div class="col-lg-2">
	        	<div style="text-decoration : underline; margin-bottom : 10px"> Calendriers de votre verticale : </div>
	        	<ul>
	            	<li> <a href="/graph/nikonikovert/${idVert}/month" class="myresults"> Calendrier verticale </a> </li>
	            </ul>
	        </div>
	        <div class="col-lg-2">
	        	<div style="text-decoration : underline; margin-bottom : 10px"> Calendriers de vos teams : </div>
	        	<ul>
	            	<#list teamnameid as teamName, idsTeam>
	           			<li> <a style="margin-bottom : 5px" href="/graph/nikonikoteam/${idsTeam}/month" class="myresults"> Calendrier team : ${teamName} </a> </li>
	            	</#list>
	            </ul>
	        </div>
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
</html>

