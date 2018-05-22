(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('ClasseDetailController', ClasseDetailController);

    ClasseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Classe', 'Wagon', 'Car'];

    function ClasseDetailController($scope, $rootScope, $stateParams, previousState, entity, Classe, Wagon, Car) {
        var vm = this;

        vm.classe = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:classeUpdate', function(event, result) {
            vm.classe = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
