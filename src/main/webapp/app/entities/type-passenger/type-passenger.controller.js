(function() {
    'use strict';

    angular
        .module('camTravel2App')
        .controller('TypePassengerController', TypePassengerController);

    TypePassengerController.$inject = ['TypePassenger', 'TypePassengerSearch'];

    function TypePassengerController(TypePassenger, TypePassengerSearch) {

        var vm = this;

        vm.typePassengers = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            TypePassenger.query(function(result) {
                vm.typePassengers = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TypePassengerSearch.query({query: vm.searchQuery}, function(result) {
                vm.typePassengers = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
