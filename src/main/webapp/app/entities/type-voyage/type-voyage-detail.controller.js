(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('TypeVoyageDetailController', TypeVoyageDetailController);

    TypeVoyageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeVoyage', 'Voyage'];

    function TypeVoyageDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeVoyage, Voyage) {
        var vm = this;

        vm.typeVoyage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:typeVoyageUpdate', function(event, result) {
            vm.typeVoyage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
