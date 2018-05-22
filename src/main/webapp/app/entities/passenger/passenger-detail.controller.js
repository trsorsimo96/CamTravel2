(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('PassengerDetailController', PassengerDetailController);

    PassengerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Passenger', 'TypePassenger', 'Booking'];

    function PassengerDetailController($scope, $rootScope, $stateParams, previousState, entity, Passenger, TypePassenger, Booking) {
        var vm = this;

        vm.passenger = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:passengerUpdate', function(event, result) {
            vm.passenger = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
