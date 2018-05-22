(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('AgencyDialogController', AgencyDialogController);

    AgencyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Agency', 'Booking', 'Deposit'];

    function AgencyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Agency, Booking, Deposit) {
        var vm = this;

        vm.agency = entity;
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
            if (vm.agency.id !== null) {
                Agency.update(vm.agency, onSaveSuccess, onSaveError);
            } else {
                Agency.save(vm.agency, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:agencyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
