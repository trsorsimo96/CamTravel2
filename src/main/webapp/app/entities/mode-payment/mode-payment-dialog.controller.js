(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('ModePaymentDialogController', ModePaymentDialogController);

    ModePaymentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ModePayment', 'Booking', 'Deposit'];

    function ModePaymentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ModePayment, Booking, Deposit) {
        var vm = this;

        vm.modePayment = entity;
        vm.clear = clear;
        vm.save = save;
        vm.bookings = Booking.query();
        vm.deposits = Deposit.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.modePayment.id !== null) {
                ModePayment.update(vm.modePayment, onSaveSuccess, onSaveError);
            } else {
                ModePayment.save(vm.modePayment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:modePaymentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
