$(document).ready(function() {

//	the basics
//	----------

	var substringMatcher = function(strs) {
		return function findMatches(q, cb) {
			var matches, substringRegex;

			// an array that will be populated with substring matches
			matches = [];

			// regex used to determine if a string contains the substring `q`
			substrRegex = new RegExp(q, 'i');

			// iterate through the pool of strings and for any string that
			// contains the substring `q`, add it to the `matches` array
			$.each(strs, function(i, str) {
				if (substrRegex.test(str)) {
					// the typeahead jQuery plugin expects suggestions to a
					// JavaScript object, refer to typeahead docs for more info
					matches.push({ value: str });
				}
			});

			cb(matches);
		};
	};

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
});