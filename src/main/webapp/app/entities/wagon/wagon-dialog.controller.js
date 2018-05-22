(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('WagonDialogController', WagonDialogController);

    WagonDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Wagon', 'ModelCar', 'Train', 'Classe'];

    function WagonDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Wagon, ModelCar, Train, Classe) {
        var vm = this;

        vm.wagon = entity;
        vm.clear = clear;
        vm.save = save;
        vm.modelcars = ModelCar.query();
        vm.trains = Train.query();
        vm.classes = Classe.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.wagon.id !== null) {
                Wagon.update(vm.wagon, onSaveSuccess, onSaveError);
            } else {
                Wagon.save(vm.wagon, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:wagonUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
