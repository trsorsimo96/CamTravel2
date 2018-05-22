(function() {
    'use strict';
    angular
        .module('camTravel2App')
        .factory('StateVoyage', StateVoyage);

    StateVoyage.$inject = ['$resource'];

    function StateVoyage ($resource) {
        var resourceUrl =  'api/state-voyages/:id';

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
