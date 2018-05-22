(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .factory('StateVoyageSearch', StateVoyageSearch);

    StateVoyageSearch.$inject = ['$resource'];

    function StateVoyageSearch($resource) {
        var resourceUrl =  'api/_search/state-voyages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
