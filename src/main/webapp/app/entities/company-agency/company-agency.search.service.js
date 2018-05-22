(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .factory('CompanyAgencySearch', CompanyAgencySearch);

    CompanyAgencySearch.$inject = ['$resource'];

    function CompanyAgencySearch($resource) {
        var resourceUrl =  'api/_search/company-agencies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
