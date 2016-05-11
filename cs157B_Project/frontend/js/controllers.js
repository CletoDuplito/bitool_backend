var appControllers = angular.module("appControllers",[]);



appControllers.
	controller("CentreCubeCtrl",["$scope","$http","$routeParams","$timeout","FormDataParser", function($scope,$http,$routeParams,$timeout,formDataParser) {
		var url = "api/bi/parser";
		// var url = "data/data.json";
		var parsedParam = formDataParser.parseData( formDataParser.defaultFormValues );

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
	controller("OptionsCtrl",["$scope","$http","$routeParams","FormDataParser", function($scope,$http,$routeParams,formDataParser) {
		$scope.changeView = function(d) {
			//broadcast it to centrecubctrl
			$scope.$broadcast("data_changed", formDataParser.parseData(d) );
		};
		$scope.form = formDataParser.defaultFormValues;
	}]);