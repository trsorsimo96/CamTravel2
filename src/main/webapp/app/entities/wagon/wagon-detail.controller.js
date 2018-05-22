(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('WagonDetailController', WagonDetailController);

    WagonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Wagon', 'ModelCar', 'Train', 'Classe'];

    function WagonDetailController($scope, $rootScope, $stateParams, previousState, entity, Wagon, ModelCar, Train, Classe) {
        var vm = this;

        vm.wagon = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:wagonUpdate', function(event, result) {
            vm.wagon = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
