(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('AgencyDetailController', AgencyDetailController);

    AgencyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Agency', 'Booking', 'Deposit'];

    function AgencyDetailController($scope, $rootScope, $stateParams, previousState, entity, Agency, Booking, Deposit) {
        var vm = this;

        vm.agency = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:agencyUpdate', function(event, result) {
            vm.agency = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
