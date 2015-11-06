app.factory('ServerNotifications',['$rootScope',function($rootScope){

	var tcuEventSource;
	

	function initialize(){

		listenForTcu(tcu);
		
		if(!tcuEventSource && !fleetEventSource){

			$log.debug('Initalizating ServerNotifications service');
		
			if ( !!window.EventSource ) {

				
			
					listenForTcu(tcu);
				
			}else{

				$log.debug('Event EventSource is not supported by this browser');
				alert('Event soruce is not supported');

			}
		}
	}




	function listenForTcu( tcu ){
		stopListeningForTcu();//

		$log.debug('[TCU] ServerNotifications Connecting for '+tcu.id);

		var url = SERVER_NOTIFICATIONS_URL +"/all/" + tcu.id;
		 
		tcuEventSource = new EventSource(url);
		
		tcuEventSource.addEventListener('open', function(e) {
			
			$log.debug("Listening for ServerNotifications for [TCU]",tcu.id);
			
		}, false);

		tcuEventSource.addEventListener('error', function(e) {
			if (e.readyState == EventSource.CLOSED) {
				console.log("ServerNotifications connection to [TCU] was closed.");
			} else {
				console.error("error :",e);
			}
		}, false);


		bindTcuServiceListeners();

	}


	function stopListeningForTcu(){
		if(tcuEventSource){
			tcuEventSource.close();
			$log.debug('[TCU] ServerNotifications Event source was closed');
		}
	}


	function bindTcuServiceListeners(){

		tcuEventSource.addEventListener('fota', function(e) {

			$rootScope.$broadcast( SERVER_EVENTS.FOTA_STATUS ,e.data);

		});

		tcuEventSource.addEventListener('streaming', function(e) {

			$rootScope.$broadcast( SERVER_EVENTS.STREAMING ,e.data);

		});

		tcuEventSource.addEventListener('tm', function(e) {

			$rootScope.$broadcast( SERVER_EVENTS.TRIGGERED_MONITORING ,e.data);

		});
		
		tcuEventSource.addEventListener('rsa', function(e) {

			$rootScope.$broadcast(SERVER_EVENTS.RSA,e.data);

		});
		
		tcuEventSource.addEventListener('geofence', function(e) {

			$rootScope.$broadcast(SERVER_EVENTS.GEOFENCE, e.data);
		});

		tcuEventSource.addEventListener('remotecontrol', function(e) {
			$rootScope.$broadcast(SERVER_EVENTS.REMOTE_CONTROL, e.data);
		});
	}


	/*function bindFleetServiceListeners(){
 

		fleetEventSource.addEventListener('tm', function(e) {

			$rootScope.$broadcast( SERVER_EVENTS.FLEET.TRIGGERED_MONITORING ,e.data);

		});
		
		fleetEventSource.addEventListener('rsa', function(e) {

			$rootScope.$broadcast(SERVER_EVENTS.FLEET.RSA,e.data);

		});
		
		fleetEventSource.addEventListener('geofence', function(e) {

			$rootScope.$broadcast(SERVER_EVENTS.FLEET.GEOFENCE, e.data);
		});

		fleetEventSource.addEventListener('remotecontrol', function(e) {
			$rootScope.$broadcast(SERVER_EVENTS.FLEET.REMOTE_CONTROL, e.data);
		});
	}*/

	return {
		initialize: initialize,
		tcuEventSource: tcuEventSource,
		fleetEventSource: fleetEventSource,
	}
	
}]);
