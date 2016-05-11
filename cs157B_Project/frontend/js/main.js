/**
 * Main script
 */
var app = angular.module("app", ["ngRoute","appControllers"]);

app.factory("FormDataParser", function(){

	var defaultFormValues = {		//init form values
		action: "roll_up",
		dimension: {
			datetime: true,
			store: true,
			product: true
		},
		category: {
			datetime: "year",
			store: "store_zip",
			product: "department"
		}
	};

	var parseData = function(d) {
		//create query parameters
		var attributes = [];
		Object.keys(d.dimension).forEach(function(value){

			if(d.dimension[value]) { 				//check if dimension is selected				
				attributes.push( [ value,d.category[value] ].join(".") );		//parse attributes
			}
		})

		//parsed params
		var qParams = {
			action: d.action,
			dimension: attributes.join(",")
		}
		return qParams
	}

	return {
		parseData: parseData,
		defaultFormValues: defaultFormValues
	}
});

app.config(["$routeProvider", function($routeProvider) {
	$routeProvider.
		when('/', {
			templateUrl: 'views/table-template.html',
			controller: 'CentreCubeCtrl',
//			controllerAs: "ctrl"
		}).
		otherwise({
			redirectTo: "/"
		});
}]);
