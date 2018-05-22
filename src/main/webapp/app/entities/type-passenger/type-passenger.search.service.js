(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .factory('TypePassengerSearch', TypePassengerSearch);

    TypePassengerSearch.$inject = ['$resource'];

    function TypePassengerSearch($resource) {
        var resourceUrl =  'api/_search/type-passengers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
