(function() {
    'use strict';
    angular
        .module('camTravel2App')
        .factory('ModePayment', ModePayment);

    ModePayment.$inject = ['$resource'];

    function ModePayment ($resource) {
        var resourceUrl =  'api/mode-payments/:id';

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
