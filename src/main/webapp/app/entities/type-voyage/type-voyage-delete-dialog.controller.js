(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('TypeVoyageDeleteController',TypeVoyageDeleteController);

    TypeVoyageDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeVoyage'];

    function TypeVoyageDeleteController($uibModalInstance, entity, TypeVoyage) {
        var vm = this;

        vm.typeVoyage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeVoyage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
