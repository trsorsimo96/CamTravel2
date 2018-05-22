(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('ModePaymentDetailController', ModePaymentDetailController);

    ModePaymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ModePayment', 'Booking', 'Deposit'];

    function ModePaymentDetailController($scope, $rootScope, $stateParams, previousState, entity, ModePayment, Booking, Deposit) {
        var vm = this;

        vm.modePayment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:modePaymentUpdate', function(event, result) {
            vm.modePayment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
