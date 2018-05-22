(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('StateVoyageDialogController', StateVoyageDialogController);

    StateVoyageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StateVoyage', 'Voyage'];

    function StateVoyageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StateVoyage, Voyage) {
        var vm = this;

        vm.stateVoyage = entity;
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
            if (vm.stateVoyage.id !== null) {
                StateVoyage.update(vm.stateVoyage, onSaveSuccess, onSaveError);
            } else {
                StateVoyage.save(vm.stateVoyage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:stateVoyageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
