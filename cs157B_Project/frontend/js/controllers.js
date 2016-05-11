var appControllers = angular.module("appControllers",[]);



appControllers.
	controller("CentreCubeCtrl",["$scope","$http","$routeParams","$timeout","FormData", function($scope,$http,$routeParams,$timeout,formData) {
		var url = "api/bi/parser";
		// var url = "data/data.json";
		var parsedParam = formData.parseData( formData.defaultFormValues );

		parsedParam["action"] = "central_cube"; //default action

		$http.get(url, {params: parsedParam}).success(function(data){
			$scope.data = data;
		});
		$scope.visible = true;
		$scope.$on("data_changed", getNewData);		//watch data changes

		function getNewData(e, queryParams) {
			//get data 
			// var newUrl = 'data/data2';
			$http.get(url, {params: queryParams}).success(function(newData){
				$scope.visible = false;				//hide current data

				$scope.data = newData;

				$timeout(function(){				//turn it back on
					$scope.visible = true;
				});

			});
		}
	}]).	
	controller("OptionsCtrl",["$scope","$http","$routeParams","FormData", function($scope,$http,$routeParams,formData) {
		$scope.form = formData.defaultFormValues;
		$scope.disable = false;
		

		$scope.changeView = function(d) {
			//broadcast it to centrecubctrl
			$scope.$broadcast("data_changed", formData.parseData(d) );
		};

		$scope.dimensionCheck = function(dim) {
			var keys = Object.keys(dim);
			var count = 0;
			$scope.disable = false;

			for(var i = 0; i < keys.length; i++) {
				var currentValue = dim[ keys[i] ];
				if( currentValue == false ) {
					count++;
				}
			}
			if(count >= 2) {
				$scope.disable = true;
			}
		}

	}]);