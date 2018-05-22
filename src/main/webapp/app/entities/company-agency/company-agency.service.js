(function() {
    'use strict';
    angular
        .module('camTravel2App')
        .factory('CompanyAgency', CompanyAgency);

    CompanyAgency.$inject = ['$resource'];

    function CompanyAgency ($resource) {
        var resourceUrl =  'api/company-agencies/:id';

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
