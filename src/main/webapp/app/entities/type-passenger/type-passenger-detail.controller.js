(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('TypePassengerDetailController', TypePassengerDetailController);

    TypePassengerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypePassenger', 'Passenger'];

    function TypePassengerDetailController($scope, $rootScope, $stateParams, previousState, entity, TypePassenger, Passenger) {
        var vm = this;

        vm.typePassenger = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('camTravel2App:typePassengerUpdate', function(event, result) {
            vm.typePassenger = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
