(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('DepositDetailController', DepositDetailController);

    DepositDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Deposit', 'Agency', 'ModePayment'];

    function DepositDetailController($scope, $rootScope, $stateParams, previousState, entity, Deposit, Agency, ModePayment) {
        var vm = this;

        vm.deposit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:depositUpdate', function(event, result) {
            vm.deposit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
