(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('TrainDetailController', TrainDetailController);

    TrainDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Train', 'Wagon', 'Voyage', 'Company'];

    function TrainDetailController($scope, $rootScope, $stateParams, previousState, entity, Train, Wagon, Voyage, Company) {
        var vm = this;

        vm.train = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:trainUpdate', function(event, result) {
            vm.train = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
