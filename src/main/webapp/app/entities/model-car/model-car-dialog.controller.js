(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('ModelCarDialogController', ModelCarDialogController);

    ModelCarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ModelCar', 'Car', 'Wagon'];

    function ModelCarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, ModelCar, Car, Wagon) {
        var vm = this;

        vm.modelCar = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.cars = Car.query();
        vm.wagons = Wagon.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.modelCar.id !== null) {
                ModelCar.update(vm.modelCar, onSaveSuccess, onSaveError);
            } else {
                ModelCar.save(vm.modelCar, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('camTravel2App:modelCarUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImage = function ($file, modelCar) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        modelCar.image = base64Data;
                        modelCar.imageContentType = $file.type;
                    });
                });
            }
        };

    }
})();
