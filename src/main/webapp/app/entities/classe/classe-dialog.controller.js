(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('ClasseDialogController', ClasseDialogController);

    ClasseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Classe', 'Wagon', 'Car'];

    function ClasseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Classe, Wagon, Car) {
        var vm = this;

        vm.classe = entity;
        vm.clear = clear;
        vm.save = save;
        vm.wagons = Wagon.query();
        vm.cars = Car.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.classe.id !== null) {
                Classe.update(vm.classe, onSaveSuccess, onSaveError);
            } else {
                Classe.save(vm.classe, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:classeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
