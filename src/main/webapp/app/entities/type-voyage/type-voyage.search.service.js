(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .factory('TypeVoyageSearch', TypeVoyageSearch);

    TypeVoyageSearch.$inject = ['$resource'];

    function TypeVoyageSearch($resource) {
        var resourceUrl =  'api/_search/type-voyages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
