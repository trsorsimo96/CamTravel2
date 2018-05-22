(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('BookingDetailController', BookingDetailController);

    BookingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Booking', 'Passenger', 'Voyage', 'Agency', 'Company', 'ModePayment'];

    function BookingDetailController($scope, $rootScope, $stateParams, previousState, entity, Booking, Passenger, Voyage, Agency, Company, ModePayment) {
        var vm = this;

        vm.booking = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:bookingUpdate', function(event, result) {
            vm.booking = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
