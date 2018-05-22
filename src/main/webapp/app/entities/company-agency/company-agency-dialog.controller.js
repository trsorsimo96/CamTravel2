(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('CompanyAgencyDialogController', CompanyAgencyDialogController);

    CompanyAgencyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CompanyAgency'];

    function CompanyAgencyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CompanyAgency) {
        var vm = this;

        vm.companyAgency = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.companyAgency.id !== null) {
                CompanyAgency.update(vm.companyAgency, onSaveSuccess, onSaveError);
            } else {
                CompanyAgency.save(vm.companyAgency, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:companyAgencyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
