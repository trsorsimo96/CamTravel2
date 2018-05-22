(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('CompanyAgencyDetailController', CompanyAgencyDetailController);

    CompanyAgencyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CompanyAgency'];

    function CompanyAgencyDetailController($scope, $rootScope, $stateParams, previousState, entity, CompanyAgency) {
        var vm = this;

        vm.companyAgency = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:companyAgencyUpdate', function(event, result) {
            vm.companyAgency = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
