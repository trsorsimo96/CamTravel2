(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .factory('AgencySearch', AgencySearch);

    AgencySearch.$inject = ['$resource'];

    function AgencySearch($resource) {
        var resourceUrl =  'api/_search/agencies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
