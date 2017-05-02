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
          pieSliceText: 'value'
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
<body onload = "setColor()">
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
		                    <button onclick="location.href='${lastUrl}'" class="vote"> Retour </button>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>

	<hr>
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-lg-8">
				<div class="welcome"> ${title}
				    <div class="piechart" id="piechart" style="width: 700px; height: 400px;"></div>
				</div>
			</div>
			 <div class="col-lg-2" >
		    	<div class="row-fluid">
		    		<div style ="text-decoration: underline; margin-bottom : 10px"> Commentaires : </div>
					<button class="myresults" onclick="showComment()"> Voir les commentaires </button>
					<table id="comment" style="display:none" class="table table-bordered table-hover">
						<#list mooder as field, mood>
							<tr>
								<td id="${field?counter}" >
									${field}
									<div name="mood" style="display:none"> ${mood} </div>
								</td>
							</tr>
						</#list>
					</table>
				</div>
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
var count = 0;

function showComment() {
	
	if (count%2 === 0){
    	document.getElementById("comment").style.display = 'table';
    	count = count + 1;
    }
    else {
    	document.getElementById("comment").style.display = 'none';
    	count = count + 1;
    }
    	
};

function setColor() {

	var test = document.getElementsByName("mood");
	
	for(var i =0; i < test.length ; i++) {
		if(test[i].innerHTML == 1){
			document.getElementById(i+1).style.backgroundColor = "#EE0000";
		}
		else if(test[i].innerHTML == 2){
			document.getElementById(i+1).style.backgroundColor = "orange";
		}
		else if(test[i].innerHTML == 3){
			document.getElementById(i+1).style.backgroundColor = "#00CC00";
		}	
	}
};

</script>

<body>
</html>

