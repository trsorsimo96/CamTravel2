(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('CompanyClasseDetailController', CompanyClasseDetailController);

    CompanyClasseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CompanyClasse'];

    function CompanyClasseDetailController($scope, $rootScope, $stateParams, previousState, entity, CompanyClasse) {
        var vm = this;

        vm.companyClasse = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:companyClasseUpdate', function(event, result) {
            vm.companyClasse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
