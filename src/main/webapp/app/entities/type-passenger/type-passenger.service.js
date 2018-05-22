(function() {
    'use strict';
    angular
        .module('camTravel2App')
        .factory('TypePassenger', TypePassenger);

    TypePassenger.$inject = ['$resource'];

    function TypePassenger ($resource) {
        var resourceUrl =  'api/type-passengers/:id';

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
