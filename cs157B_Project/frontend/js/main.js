/**
 * Main script
 */
var app = angular.module("app", ["ngRoute","appControllers"]);

app.factory("FormData", function(){

	var defaultFormValues = {		//init form values
		action: "roll_up",
		dimension: {
			date_time: true,
			store: true,
			product: true
		},
		category: {
			date_time: "year",
			store: "store_state",
			product: "category"
		},
		cardinals: {}
	};

	/*
	 * Parse the dimension params
	 */
	var parseParam = function(d) {
		//create query parameters
		var attributes = {};
		Object.keys(d.dimension).forEach(function(value) {
			if(d.dimension[value]) { 				//check if dimension is selected
				attributes[value] = [ value, d.category[value] ].join(".");		//parse attributes
			}
		});

		var cardinals = {};
		if(d.cardinals) {			//if there is a cardinal property
			Object.keys(d.cardinals).forEach(function(value,index, arr) { //parsing cardinal values
				d.cardinals[value].forEach(function(cardinal,i,arr) {

					if(!cardinals[value])
						cardinals[value] = [];

					if(arr[cardinal]) {			//cardinal value was selected
						cardinals[value].push(cardinal.trim());
					}
				});
				//combine attribute and cardinals
				attributes[value] += cardinals[value].join("+");
			});
		}

		console.log(attributes,cardinals);

		//combining cardinals and attributes

		//parsed params
		var qParams = {
			action: d.action,
			// dimension: attributes.join(","),
			dimension: "",

			// cardinals: cardinals.join('+')
		}

		return qParams;
	}


	/**
	  * Get the cardinal of datas
 	  */
	var parseCardinalValues = function(d) {
		
		var keys = d["attributes"];
		var data = d["data"];
		var result = {};

		data.forEach(function(val,index){
			var cardinalValues = val.key;
			cardinalValues.forEach(function(c,i){
				var castedCardinal = [c," "].join("");	//casting the value to a string
				if(!result[keys[i]]) //base case when there isnt a array initialized
					result[keys[i]] = [];

				if(result[keys[i]].indexOf(castedCardinal) == -1)  //check if value is in there
					result[keys[i]].push(castedCardinal);

				// console.log(result);
			});
		});
		console.log(result);
		return result;
	}

	return {
		parseParam: parseParam,
		parseCardinalValues: parseCardinalValues,
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
