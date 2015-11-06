var srv = "myServerUrl";

var app = angular.module('app', ['ui.router','ui.bootstrap']);


app.config(function($stateProvider, $urlRouterProvider) {

	$stateProvider.state('monitoring', {
		url : "/",
		templateUrl : "src/views/monitoring.html",
		controller : 'AppCtrl',
	});


	$stateProvider.state('vehicles', {
		url : "/vehicles",
		templateUrl : "src/views/vehicles.html",
		controller : 'AppCtrl',
	});

	$stateProvider.state('trips', {
		url : "/trips/{carId}",
		templateUrl : "src/views/trips.html",
		controller : 'AppCtrl',
	});

	$urlRouterProvider.otherwise("/");
});