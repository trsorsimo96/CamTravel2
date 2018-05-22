(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .factory('ClasseSearch', ClasseSearch);

    ClasseSearch.$inject = ['$resource'];

    function ClasseSearch($resource) {
        var resourceUrl =  'api/_search/classes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
