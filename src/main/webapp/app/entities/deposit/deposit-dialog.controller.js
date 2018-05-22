(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('DepositDialogController', DepositDialogController);

    DepositDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Deposit', 'Agency', 'ModePayment'];

    function DepositDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Deposit, Agency, ModePayment) {
        var vm = this;

        vm.deposit = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.agencies = Agency.query();
        vm.modepayments = ModePayment.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.deposit.id !== null) {
                Deposit.update(vm.deposit, onSaveSuccess, onSaveError);
            } else {
                Deposit.save(vm.deposit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:depositUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
