(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('TypeVoyageDialogController', TypeVoyageDialogController);

    TypeVoyageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeVoyage', 'Voyage'];

    function TypeVoyageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeVoyage, Voyage) {
        var vm = this;

        vm.typeVoyage = entity;
        vm.clear = clear;
        vm.save = save;
        vm.voyages = Voyage.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.typeVoyage.id !== null) {
                TypeVoyage.update(vm.typeVoyage, onSaveSuccess, onSaveError);
            } else {
                TypeVoyage.save(vm.typeVoyage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:typeVoyageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
