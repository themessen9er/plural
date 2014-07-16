'use strict';

var baconSearch = angular.module('bacon-search', ['bacon-analisys']);

baconSearch.controller('ConceptSearchController', ['$rootScope', '$scope', '$http', function($rootScope, $scope, $http) {
	
	var controllerScope = this;
	
	var concepts = new Bloodhound({
		datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
		queryTokenizer: Bloodhound.tokenizers.whitespace,
		limit: 10,
		prefetch: {
			url: '../../ws/concepts',
			filter: function(list) {
				return $.map(list, function(c) { return { name: c.name }; });
			}
		}
	});

	// kicks off the loading/processing of `local` and `prefetch`
	concepts.initialize();

	// passing in `null` for the `options` arguments will result in the default
	// options being used
	$('#search .typeahead').typeahead(null, {
		name: 'concepts',
		displayKey: 'name',
		// `ttAdapter` wraps the suggestion engine in an adapter that
		// is compatible with the typeahead jQuery plugin
		source: concepts.ttAdapter()
	});
	
}]);
