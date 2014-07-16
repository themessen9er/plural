'use strict';

var baconAnalisys = angular.module('bacon-analisys', []);

baconAnalisys.controller("AnalisysProgressController", ['$rootScope', '$scope', '$http', function($rootScope, $scope, $http) {
	var controllerScope = this;		
	
	this.progress = 0;
	this.busy = false;

	this.getProgress = function() {
		return this.progress;
	};

	this.setProgress = function(p) {
		this.progress = p;
	}
	
	this.watchProgress = function() {
		$http.get("../../ws/analisys/progress").success(function(value) {
			if(value >= controllerScope.getProgress()) {
				controllerScope.setProgress(value);
				setTimeout(controllerScope.watchProgress, 1000);
			} else {
				controllerScope.busy = false;
				$rootScope.$emit('BaconAnalisysCompleted');
			}
		});
	}
	
	this.analize = function() {
		this.progress = 0;
		this.busy = true;
		$scope.$broadcast('BaconAnalisysStarted');
		$http.post("../../ws/analisys").success(function(value) {
			console.log(value);
			controllerScope.watchProgress();
		});
	}

}]);