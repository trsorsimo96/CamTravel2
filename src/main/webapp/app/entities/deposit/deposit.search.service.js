(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .factory('DepositSearch', DepositSearch);

    DepositSearch.$inject = ['$resource'];

    function DepositSearch($resource) {
        var resourceUrl =  'api/_search/deposits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
