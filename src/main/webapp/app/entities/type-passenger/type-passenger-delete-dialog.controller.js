(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('TypePassengerDeleteController',TypePassengerDeleteController);

    TypePassengerDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypePassenger'];

    function TypePassengerDeleteController($uibModalInstance, entity, TypePassenger) {
        var vm = this;

        vm.typePassenger = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypePassenger.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
