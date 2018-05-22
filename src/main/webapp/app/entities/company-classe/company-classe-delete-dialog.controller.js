(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('CompanyClasseDeleteController',CompanyClasseDeleteController);

    CompanyClasseDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompanyClasse'];

    function CompanyClasseDeleteController($uibModalInstance, entity, CompanyClasse) {
        var vm = this;

        vm.companyClasse = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CompanyClasse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
