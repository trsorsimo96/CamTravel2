(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('CompanyAgencyDeleteController',CompanyAgencyDeleteController);

    CompanyAgencyDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompanyAgency'];

    function CompanyAgencyDeleteController($uibModalInstance, entity, CompanyAgency) {
        var vm = this;

        vm.companyAgency = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CompanyAgency.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
