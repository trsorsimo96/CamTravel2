(function() {
    'use strict';
    angular
        .module('camTravel2App')
        .factory('Deposit', Deposit);

    Deposit.$inject = ['$resource', 'DateUtils'];

    function Deposit ($resource, DateUtils) {
        var resourceUrl =  'api/deposits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
