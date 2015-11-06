var global_map;

app.controller('AppCtrl', function($scope,$state,$stateParams,$http){
	$scope.msg= 'this is the templ page';

	$scope.carId = $stateParams.carId;

	$scope.vehiclesMakers = {};

	$scope.cars = 
	[
	{'id':5, 'plate':'B 55 DSG', 'driver':'Vasile Ion'},
	{'id':8, 'plate':'B 45 GSD', 'driver':'Ion Fane'},
	]


	$scope.listenToEventSource = function(){
		var eventSource = new EventSource("/subscribe");

		var marker =  new google.maps.Marker({
			position : {
				lat : 44.435466,
				lng : 26.102558
			},
			animation: google.maps.Animation.DROP,
			
			title : 'Hello World!'
		}); 

		eventSource.addEventListener('location', function(event) {

			var json = event.data;
			var location = JSON.parse(json);

			var latString = location.lat;
			var lngString = location.lng;
			var lat = parseFloat(latString);
			var lng = parseFloat(lngString);

			if(lat != 0 && lng !=0){
				var latlng = new google.maps.LatLng(lat, lng);

				marker.setPosition(latlng);
				marker.setMap(global_map);
				global_map.setCenter(marker.position);
			}
		}, false);


	}

	$scope.getVehicleList = function(){

		$http.get('http://localhost:8080/cars').success(function(data) {
			$scope.cars = data;
		});
	}

	var addVehicleOnMap = function(car){
		//$scope.vehiclesMakers[car.id]= new Marker bla bla

	}

	var moveVehicleMarker = function(carId,lat,lng){
		//var marker = $scope.vehiclesMakers[carId];

		//marker.setPosition(new lat bla bla (lat lng));
	}

	$scope.initalizeMap = function(){
		var map =global_map= $scope.map_object = new google.maps.Map(document.getElementById('map'), {
			zoom : 16,
			center : new google.maps.LatLng(44.435466, 26.102558),
			mapTypeId : google.maps.MapTypeId.ROADMAP,
		});
	}


	$scope.selectVehicle = function(car){
		$state.go('trips', {carId: car.id});
	}

	$scope.showTrip = function(trip){
		//get date bla bla

		//$scope.map_object.setPolyline.....
	}


});