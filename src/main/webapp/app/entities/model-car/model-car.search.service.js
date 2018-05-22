(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .factory('ModelCarSearch', ModelCarSearch);

    ModelCarSearch.$inject = ['$resource'];

    function ModelCarSearch($resource) {
        var resourceUrl =  'api/_search/model-cars/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
