(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('CompanyDetailController', CompanyDetailController);

    CompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Company', 'Car', 'Train', 'Booking'];

    function CompanyDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Company, Car, Train, Booking) {
        var vm = this;

        vm.company = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('camTravel2App:companyUpdate', function(event, result) {
            vm.company = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
