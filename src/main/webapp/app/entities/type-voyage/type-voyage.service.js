(function() {
    'use strict';
    angular
        .module('camTravel2App')
        .factory('TypeVoyage', TypeVoyage);

    TypeVoyage.$inject = ['$resource'];

    function TypeVoyage ($resource) {
        var resourceUrl =  'api/type-voyages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
