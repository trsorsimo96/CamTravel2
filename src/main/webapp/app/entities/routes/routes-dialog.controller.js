(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('RoutesDialogController', RoutesDialogController);

    RoutesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Routes', 'Voyage', 'City'];

    function RoutesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Routes, Voyage, City) {
        var vm = this;

        vm.routes = entity;
        vm.clear = clear;
        vm.save = save;
        vm.voyages = Voyage.query();
        vm.cities = City.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.routes.id !== null) {
                Routes.update(vm.routes, onSaveSuccess, onSaveError);
            } else {
                Routes.save(vm.routes, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:routesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();