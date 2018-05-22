(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .factory('ModePaymentSearch', ModePaymentSearch);

    ModePaymentSearch.$inject = ['$resource'];

    function ModePaymentSearch($resource) {
        var resourceUrl =  'api/_search/mode-payments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
