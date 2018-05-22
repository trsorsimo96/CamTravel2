(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('VoyageDetailController', VoyageDetailController);

    VoyageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Voyage', 'Routes', 'Car', 'Train', 'TypeVoyage', 'StateVoyage', 'Booking'];

    function VoyageDetailController($scope, $rootScope, $stateParams, previousState, entity, Voyage, Routes, Car, Train, TypeVoyage, StateVoyage, Booking) {
        var vm = this;

        vm.voyage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:voyageUpdate', function(event, result) {
            vm.voyage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
