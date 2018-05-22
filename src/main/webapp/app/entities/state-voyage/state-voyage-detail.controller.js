(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('StateVoyageDetailController', StateVoyageDetailController);

    StateVoyageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StateVoyage', 'Voyage'];

    function StateVoyageDetailController($scope, $rootScope, $stateParams, previousState, entity, StateVoyage, Voyage) {
        var vm = this;

        vm.stateVoyage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:stateVoyageUpdate', function(event, result) {
            vm.stateVoyage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
