(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('StateVoyageDeleteController',StateVoyageDeleteController);

    StateVoyageDeleteController.$inject = ['$uibModalInstance', 'entity', 'StateVoyage'];

    function StateVoyageDeleteController($uibModalInstance, entity, StateVoyage) {
        var vm = this;

        vm.stateVoyage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StateVoyage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
