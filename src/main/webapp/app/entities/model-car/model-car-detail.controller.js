(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('ModelCarDetailController', ModelCarDetailController);

    ModelCarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'ModelCar', 'Car', 'Wagon'];

    function ModelCarDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, ModelCar, Car, Wagon) {
        var vm = this;

        vm.modelCar = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('camTravel2App:modelCarUpdate', function(event, result) {
            vm.modelCar = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
