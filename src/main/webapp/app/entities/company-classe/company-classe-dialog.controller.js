(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('CompanyClasseDialogController', CompanyClasseDialogController);

    CompanyClasseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CompanyClasse'];

    function CompanyClasseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CompanyClasse) {
        var vm = this;

        vm.companyClasse = entity;
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
            if (vm.companyClasse.id !== null) {
                CompanyClasse.update(vm.companyClasse, onSaveSuccess, onSaveError);
            } else {
                CompanyClasse.save(vm.companyClasse, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:companyClasseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
