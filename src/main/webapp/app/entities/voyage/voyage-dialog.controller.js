(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('VoyageDialogController', VoyageDialogController);

    VoyageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Voyage', 'Routes', 'Car', 'Train', 'TypeVoyage', 'StateVoyage', 'Booking'];

    function VoyageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Voyage, Routes, Car, Train, TypeVoyage, StateVoyage, Booking) {
        var vm = this;

        vm.voyage = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.voyages = Routes.query({filter: 'itinerary(code)-is-null'});
        $q.all([vm.voyage.$promise, vm.voyages.$promise]).then(function() {
            if (!vm.voyage.voyage || !vm.voyage.voyage.id) {
                return $q.reject();
            }
            return Routes.get({id : vm.voyage.voyage.id}).$promise;
        }).then(function(voyage) {
            vm.voyages.push(voyage);
        });
        vm.cars = Car.query();
        vm.trains = Train.query();
        vm.typevoyages = TypeVoyage.query();
        vm.statevoyages = StateVoyage.query();
        vm.bookings = Booking.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.voyage.id !== null) {
                Voyage.update(vm.voyage, onSaveSuccess, onSaveError);
            } else {
                Voyage.save(vm.voyage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:voyageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datedepart = false;
        vm.datePickerOpenStatus.datearrive = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
