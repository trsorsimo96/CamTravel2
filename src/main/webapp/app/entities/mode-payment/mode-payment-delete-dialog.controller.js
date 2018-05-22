(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('ModePaymentDeleteController',ModePaymentDeleteController);

    ModePaymentDeleteController.$inject = ['$uibModalInstance', 'entity', 'ModePayment'];

    function ModePaymentDeleteController($uibModalInstance, entity, ModePayment) {
        var vm = this;

        vm.modePayment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ModePayment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
