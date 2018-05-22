(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('TypePassengerDialogController', TypePassengerDialogController);

    TypePassengerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypePassenger', 'Passenger'];

    function TypePassengerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypePassenger, Passenger) {
        var vm = this;

        vm.typePassenger = entity;
        vm.clear = clear;
        vm.save = save;
        vm.passengers = Passenger.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.typePassenger.id !== null) {
                TypePassenger.update(vm.typePassenger, onSaveSuccess, onSaveError);
            } else {
                TypePassenger.save(vm.typePassenger, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:typePassengerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
