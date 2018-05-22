(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .factory('BookingSearch', BookingSearch);

    BookingSearch.$inject = ['$resource'];

    function BookingSearch($resource) {
        var resourceUrl =  'api/_search/bookings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
